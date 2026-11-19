package com.android.systemui.recents;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.trust.TrustManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.util.ArraySet;
import android.util.Log;
import android.util.MutableBoolean;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.internal.policy.DockedDividerUtils;
import com.android.systemui.Dependency;
import com.android.systemui.OverviewProxyService;
import com.android.systemui.R;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.events.activity.DockedTopTaskEvent;
import com.android.systemui.recents.events.activity.EnterRecentsWindowLastAnimationFrameEvent;
import com.android.systemui.recents.events.activity.HideRecentsEvent;
import com.android.systemui.recents.events.activity.LaunchMostRecentTaskRequestEvent;
import com.android.systemui.recents.events.activity.LaunchNextTaskRequestEvent;
import com.android.systemui.recents.events.activity.RecentsActivityStartingEvent;
import com.android.systemui.recents.events.activity.ToggleRecentsEvent;
import com.android.systemui.recents.events.component.ActivityPinnedEvent;
import com.android.systemui.recents.events.component.ActivityUnpinnedEvent;
import com.android.systemui.recents.events.component.HidePipMenuEvent;
import com.android.systemui.recents.events.ui.DraggingInRecentsEndedEvent;
import com.android.systemui.recents.events.ui.DraggingInRecentsEvent;
import com.android.systemui.recents.events.ui.TaskSnapshotChangedEvent;
import com.android.systemui.recents.misc.DozeTrigger;
import com.android.systemui.recents.misc.ForegroundThread;
import com.android.systemui.recents.misc.SysUiTaskStackChangeListener;
import com.android.systemui.recents.misc.SystemServicesProxy;
import com.android.systemui.recents.views.TaskStackLayoutAlgorithm;
import com.android.systemui.recents.views.TaskStackView;
import com.android.systemui.recents.views.TaskViewHeader;
import com.android.systemui.recents.views.TaskViewTransform;
import com.android.systemui.recents.views.grid.TaskGridLayoutAlgorithm;
import com.android.systemui.shared.recents.model.RecentsTaskLoadPlan;
import com.android.systemui.shared.recents.model.RecentsTaskLoader;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.TaskStack;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.shared.recents.view.AppTransitionAnimationSpecCompat;
import com.android.systemui.shared.recents.view.AppTransitionAnimationSpecsFuture;
import com.android.systemui.shared.recents.view.RecentsTransition;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.phone.StatusBar;
import com.google.android.collect.Lists;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RecentsImpl implements ActivityOptions.OnAnimationFinishedListener {
    protected static RecentsTaskLoadPlan sInstanceLoadPlan;
    private TaskStackLayoutAlgorithm mBackgroundLayoutAlgorithm;
    protected Context mContext;
    boolean mDraggingInRecents;
    private TaskStackView mDummyStackView;
    TaskViewHeader mHeaderBar;
    protected long mLastToggleTime;
    boolean mLaunchedWhileDocking;
    int mTaskBarHeight;
    TaskStackListenerImpl mTaskStackListener;
    protected boolean mTriggeredFromAltTab;
    private TrustManager mTrustManager;
    private static final ArraySet<Task.TaskKey> EMPTY_SET = new ArraySet<>();
    protected static long sLastPipTime = -1;
    private static boolean mWaitingForTransitionStart = false;
    private static boolean mToggleFollowingTransitionStart = true;
    private Runnable mResetToggleFlagListener = new Runnable() { // from class: com.android.systemui.recents.RecentsImpl.1
        @Override // java.lang.Runnable
        public void run() {
            RecentsImpl.this.setWaitingForTransitionStart(false);
        }
    };
    Rect mTmpBounds = new Rect();
    TaskViewTransform mTmpTransform = new TaskViewTransform();
    final Object mHeaderBarLock = new Object();
    DozeTrigger mFastAltTabTrigger = new DozeTrigger(225, new Runnable() { // from class: com.android.systemui.recents.RecentsImpl.2
        @Override // java.lang.Runnable
        public void run() {
            RecentsImpl.this.showRecents(RecentsImpl.this.mTriggeredFromAltTab, false, true, -1);
        }
    });
    private OverviewProxyService.OverviewProxyListener mOverviewProxyListener = new OverviewProxyService.OverviewProxyListener() { // from class: com.android.systemui.recents.RecentsImpl.3
        @Override // com.android.systemui.OverviewProxyService.OverviewProxyListener
        public void onConnectionChanged(boolean isConnected) {
            if (!isConnected) {
                Recents.getTaskLoader().onTrimMemory(80);
            }
        }
    };
    private final TaskStack mEmptyTaskStack = new TaskStack();
    protected Handler mHandler = new Handler();

    class TaskStackListenerImpl extends SysUiTaskStackChangeListener {
        private OverviewProxyService mOverviewProxyService = (OverviewProxyService) Dependency.get(OverviewProxyService.class);

        public TaskStackListenerImpl() {
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskStackChangedBackground() {
            if (this.mOverviewProxyService.isEnabled() || !checkCurrentUserId(RecentsImpl.this.mContext, false)) {
                return;
            }
            RecentsConfiguration config = Recents.getConfiguration();
            if (config.svelteLevel == 0) {
                Rect windowRect = RecentsImpl.this.getWindowRect(null);
                if (windowRect.isEmpty()) {
                    return;
                }
                ActivityManager.RunningTaskInfo runningTaskInfo = ActivityManagerWrapper.getInstance().getRunningTask();
                RecentsTaskLoader loader = Recents.getTaskLoader();
                RecentsTaskLoadPlan plan = new RecentsTaskLoadPlan(RecentsImpl.this.mContext);
                Log.d("XYG", "onTaskStackChangedBackground");
                loader.preloadTasks(plan, -1);
                TaskStack stack = plan.getTaskStack();
                RecentsActivityLaunchState launchState = new RecentsActivityLaunchState();
                RecentsTaskLoadPlan.Options launchOpts = new RecentsTaskLoadPlan.Options();
                synchronized (RecentsImpl.this.mBackgroundLayoutAlgorithm) {
                    RecentsImpl.this.updateDummyStackViewLayout(RecentsImpl.this.mBackgroundLayoutAlgorithm, stack, windowRect);
                    launchState.launchedFromApp = true;
                    RecentsImpl.this.mBackgroundLayoutAlgorithm.update(plan.getTaskStack(), RecentsImpl.EMPTY_SET, launchState, -1.0f);
                    TaskStackLayoutAlgorithm.VisibilityReport visibilityReport = RecentsImpl.this.mBackgroundLayoutAlgorithm.computeStackVisibilityReport(stack.getTasks());
                    launchOpts.runningTaskId = runningTaskInfo != null ? runningTaskInfo.id : -1;
                    launchOpts.numVisibleTasks = visibilityReport.numVisibleTasks;
                    launchOpts.numVisibleTaskThumbnails = visibilityReport.numVisibleThumbnails;
                    launchOpts.onlyLoadForCache = true;
                    launchOpts.onlyLoadPausedActivities = true;
                    launchOpts.loadThumbnails = true;
                }
                loader.loadTasks(plan, launchOpts);
            }
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityPinned(String packageName, int userId, int taskId, int stackId) {
            if (!checkCurrentUserId(RecentsImpl.this.mContext, false)) {
                return;
            }
            Recents.getConfiguration().getLaunchState().launchedFromPipApp = true;
            Recents.getConfiguration().getLaunchState().launchedWithNextPipApp = false;
            EventBus.getDefault().send(new ActivityPinnedEvent(taskId));
            RecentsImpl.consumeInstanceLoadPlan();
            RecentsImpl.sLastPipTime = System.currentTimeMillis();
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onActivityUnpinned() {
            if (!checkCurrentUserId(RecentsImpl.this.mContext, false)) {
                return;
            }
            EventBus.getDefault().send(new ActivityUnpinnedEvent());
            RecentsImpl.sLastPipTime = -1L;
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskSnapshotChanged(int taskId, ThumbnailData snapshot) {
            if (!checkCurrentUserId(RecentsImpl.this.mContext, false)) {
                return;
            }
            EventBus.getDefault().send(new TaskSnapshotChangedEvent(taskId, snapshot));
        }
    }

    public RecentsImpl(Context context) {
        this.mContext = context;
        this.mBackgroundLayoutAlgorithm = new TaskStackLayoutAlgorithm(context, null);
        ForegroundThread.get();
        this.mTaskStackListener = new TaskStackListenerImpl();
        ActivityManagerWrapper.getInstance().registerTaskStackListener(this.mTaskStackListener);
        this.mDummyStackView = new TaskStackView(this.mContext);
        reloadResources();
        this.mTrustManager = (TrustManager) this.mContext.getSystemService("trust");
    }

    public void onConfigurationChanged() {
        reloadResources();
        this.mDummyStackView.reloadOnConfigurationChange();
        synchronized (this.mBackgroundLayoutAlgorithm) {
            this.mBackgroundLayoutAlgorithm.reloadOnConfigurationChange(this.mContext);
        }
    }

    public void onVisibilityChanged(Context context, boolean visible) {
        Recents.getSystemServices().setRecentsVisibility(visible);
    }

    public void onStartScreenPinning(Context context, int taskId) {
        StatusBar statusBar = getStatusBar();
        if (statusBar != null) {
            statusBar.showScreenPinningRequest(taskId, false);
        }
    }

    public void showRecents(boolean triggeredFromAltTab, boolean draggingInRecents, boolean animate, int growTarget) {
        Recents.getSystemServices();
        boolean z = true;
        MutableBoolean isHomeStackVisible = new MutableBoolean(true);
        boolean isRecentsVisible = Recents.getSystemServices().isRecentsActivityVisible(isHomeStackVisible);
        boolean fromHome = isHomeStackVisible.value;
        boolean launchedWhileDockingTask = Recents.getSystemServices().getSplitScreenPrimaryStack() != null;
        this.mTriggeredFromAltTab = triggeredFromAltTab;
        this.mDraggingInRecents = draggingInRecents;
        this.mLaunchedWhileDocking = launchedWhileDockingTask;
        if (this.mFastAltTabTrigger.isAsleep()) {
            this.mFastAltTabTrigger.stopDozing();
        } else if (this.mFastAltTabTrigger.isDozing()) {
            if (!triggeredFromAltTab) {
                return;
            } else {
                this.mFastAltTabTrigger.stopDozing();
            }
        } else if (triggeredFromAltTab) {
            this.mFastAltTabTrigger.startDozing();
            return;
        }
        boolean forceVisible = launchedWhileDockingTask || draggingInRecents;
        if (forceVisible || !isRecentsVisible) {
            try {
                ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.getInstance().getRunningTask();
                if (!isHomeStackVisible.value && !fromHome) {
                    z = false;
                }
                startRecentsActivityAndDismissKeyguardIfNeeded(runningTask, z, animate, growTarget);
            } catch (ActivityNotFoundException e) {
                Log.e("RecentsImpl", "Failed to launch RecentsActivity", e);
            }
        }
    }

    public void hideRecents(boolean triggeredFromAltTab, boolean triggeredFromHomeKey) {
        if (triggeredFromAltTab && this.mFastAltTabTrigger.isDozing()) {
            showNextTask();
            this.mFastAltTabTrigger.stopDozing();
        } else {
            EventBus.getDefault().post(new HideRecentsEvent(triggeredFromAltTab, triggeredFromHomeKey));
        }
    }

    public void toggleRecents(int growTarget) {
        if (ActivityManagerWrapper.getInstance().isScreenPinningActive() || this.mFastAltTabTrigger.isDozing()) {
            return;
        }
        if (mWaitingForTransitionStart) {
            mToggleFollowingTransitionStart = true;
            return;
        }
        this.mDraggingInRecents = false;
        this.mLaunchedWhileDocking = false;
        this.mTriggeredFromAltTab = false;
        try {
            MutableBoolean isHomeStackVisible = new MutableBoolean(true);
            long elapsedTime = SystemClock.elapsedRealtime() - this.mLastToggleTime;
            SystemServicesProxy ssp = Recents.getSystemServices();
            if (!ssp.isRecentsActivityVisible(isHomeStackVisible)) {
                if (elapsedTime < 350) {
                    return;
                }
                ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.getInstance().getRunningTask();
                startRecentsActivityAndDismissKeyguardIfNeeded(runningTask, isHomeStackVisible.value, true, growTarget);
                ActivityManagerWrapper.getInstance().closeSystemWindows("recentapps");
                this.mLastToggleTime = SystemClock.elapsedRealtime();
                return;
            }
            RecentsConfiguration config = Recents.getConfiguration();
            RecentsActivityLaunchState launchState = config.getLaunchState();
            if (launchState.launchedWithAltTab) {
                if (elapsedTime < 350) {
                    return;
                }
                EventBus.getDefault().post(new ToggleRecentsEvent());
                this.mLastToggleTime = SystemClock.elapsedRealtime();
                return;
            }
            if (Recents.getConfiguration().isGridEnabled) {
                boolean isQuickTap = elapsedTime < ((long) ViewConfiguration.getDoubleTapTimeout());
                if (isQuickTap) {
                    EventBus.getDefault().post(new LaunchNextTaskRequestEvent());
                } else {
                    EventBus.getDefault().post(new LaunchMostRecentTaskRequestEvent());
                }
                return;
            }
            EventBus.getDefault().post(new LaunchNextTaskRequestEvent());
        } catch (ActivityNotFoundException e) {
            Log.e("RecentsImpl", "Failed to launch RecentsActivity", e);
        }
    }

    public void preloadRecents() {
        if (ActivityManagerWrapper.getInstance().isScreenPinningActive()) {
            return;
        }
        StatusBar statusBar = getStatusBar();
        if (statusBar != null && statusBar.isKeyguardShowing()) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.recents.-$$Lambda$RecentsImpl$j3kxRIim5t_10M0HDPUojNhJV5I
            @Override // java.lang.Runnable
            public final void run() {
                RecentsImpl.lambda$preloadRecents$0(this.f$0);
            }
        });
    }

    public static /* synthetic */ void lambda$preloadRecents$0(RecentsImpl recentsImpl) {
        ActivityManager.RunningTaskInfo runningTask;
        SystemServicesProxy ssp = Recents.getSystemServices();
        if (ssp.isRecentsActivityVisible(null) || (runningTask = ActivityManagerWrapper.getInstance().getRunningTask()) == null) {
            return;
        }
        RecentsTaskLoader loader = Recents.getTaskLoader();
        sInstanceLoadPlan = new RecentsTaskLoadPlan(recentsImpl.mContext);
        loader.preloadTasks(sInstanceLoadPlan, runningTask.id);
        TaskStack stack = sInstanceLoadPlan.getTaskStack();
        if (stack.getTaskCount() > 0) {
            recentsImpl.preloadIcon(runningTask.id);
            recentsImpl.updateHeaderBarLayout(stack, null);
        }
    }

    public void cancelPreloadingRecents() {
    }

    public void onDraggingInRecents(float distanceFromTop) {
        EventBus.getDefault().sendOntoMainThread(new DraggingInRecentsEvent(distanceFromTop));
    }

    public void onDraggingInRecentsEnded(float velocity) {
        EventBus.getDefault().sendOntoMainThread(new DraggingInRecentsEndedEvent(velocity));
    }

    public void onShowCurrentUserToast(int msgResId, int msgLength) {
        Toast.makeText(this.mContext, msgResId, msgLength).show();
    }

    public void showNextTask() {
        ActivityManager.RunningTaskInfo runningTask;
        SystemServicesProxy ssp = Recents.getSystemServices();
        RecentsTaskLoader loader = Recents.getTaskLoader();
        RecentsTaskLoadPlan plan = new RecentsTaskLoadPlan(this.mContext);
        loader.preloadTasks(plan, -1);
        TaskStack focusedStack = plan.getTaskStack();
        if (focusedStack == null || focusedStack.getTaskCount() == 0 || (runningTask = ActivityManagerWrapper.getInstance().getRunningTask()) == null) {
            return;
        }
        boolean isRunningTaskInHomeStack = runningTask.configuration.windowConfiguration.getActivityType() == 2;
        ArrayList<Task> tasks = focusedStack.getTasks();
        Task toTask = null;
        ActivityOptions launchOpts = null;
        int taskCount = tasks.size();
        for (int i = taskCount - 1; i >= 1; i--) {
            Task task = tasks.get(i);
            if (isRunningTaskInHomeStack) {
                Task toTask2 = tasks.get(i - 1);
                toTask = toTask2;
                launchOpts = ActivityOptions.makeCustomAnimation(this.mContext, R.anim.recents_launch_next_affiliated_task_target, R.anim.recents_fast_toggle_app_home_exit);
                break;
            } else {
                if (task.key.id == runningTask.id) {
                    Task toTask3 = tasks.get(i - 1);
                    toTask = toTask3;
                    launchOpts = ActivityOptions.makeCustomAnimation(this.mContext, R.anim.recents_launch_prev_affiliated_task_target, R.anim.recents_launch_prev_affiliated_task_source);
                    break;
                }
            }
        }
        if (toTask == null) {
            ssp.startInPlaceAnimationOnFrontMostApplication(ActivityOptions.makeCustomInPlaceAnimation(this.mContext, R.anim.recents_launch_prev_affiliated_task_bounce));
        } else {
            ActivityManagerWrapper.getInstance().startActivityFromRecentsAsync(toTask.key, launchOpts, null, null);
        }
    }

    public void splitPrimaryTask(int taskId, int dragMode, int stackCreateMode, Rect initialBounds) {
        SystemServicesProxy ssp = Recents.getSystemServices();
        if (ssp.setTaskWindowingModeSplitScreenPrimary(taskId, stackCreateMode, initialBounds)) {
            EventBus.getDefault().send(new DockedTopTaskEvent(dragMode, initialBounds));
        }
    }

    public void setWaitingForTransitionStart(boolean waitingForTransitionStart) {
        if (mWaitingForTransitionStart == waitingForTransitionStart) {
            return;
        }
        mWaitingForTransitionStart = waitingForTransitionStart;
        if (!waitingForTransitionStart && mToggleFollowingTransitionStart) {
            this.mHandler.post(new Runnable() { // from class: com.android.systemui.recents.-$$Lambda$RecentsImpl$8KagPhOavaRXCFab5YBHz8Lgk54
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.toggleRecents(-1);
                }
            });
        }
        mToggleFollowingTransitionStart = false;
    }

    public static RecentsTaskLoadPlan consumeInstanceLoadPlan() {
        RecentsTaskLoadPlan plan = sInstanceLoadPlan;
        sInstanceLoadPlan = null;
        return plan;
    }

    public static long getLastPipTime() {
        return sLastPipTime;
    }

    private void reloadResources() {
        Resources res = this.mContext.getResources();
        this.mTaskBarHeight = TaskStackLayoutAlgorithm.getDimensionForDevice(this.mContext, R.dimen.recents_task_view_header_height, R.dimen.recents_task_view_header_height, R.dimen.recents_task_view_header_height, R.dimen.recents_task_view_header_height_tablet_land, R.dimen.recents_task_view_header_height, R.dimen.recents_task_view_header_height_tablet_land, R.dimen.recents_grid_task_view_header_height);
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        this.mHeaderBar = (TaskViewHeader) inflater.inflate(R.layout.recents_task_view_header, (ViewGroup) null, false);
        this.mHeaderBar.setLayoutDirection(res.getConfiguration().getLayoutDirection());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDummyStackViewLayout(TaskStackLayoutAlgorithm stackLayout, TaskStack stack, Rect windowRect) {
        SystemServicesProxy ssp = Recents.getSystemServices();
        Rect displayRect = ssp.getDisplayRect();
        Rect systemInsets = new Rect();
        ssp.getStableInsets(systemInsets);
        if (ssp.hasDockedTask()) {
            if (systemInsets.bottom < windowRect.height()) {
                windowRect.bottom -= systemInsets.bottom;
            }
            systemInsets.bottom = 0;
        }
        calculateWindowStableInsets(systemInsets, windowRect, displayRect);
        windowRect.offsetTo(0, 0);
        stackLayout.setSystemInsets(systemInsets);
        if (stack != null) {
            stackLayout.getTaskStackBounds(displayRect, windowRect, systemInsets.top, systemInsets.left, systemInsets.right, this.mTmpBounds);
            stackLayout.reset();
            stackLayout.initialize(displayRect, windowRect, this.mTmpBounds);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Rect getWindowRect(Rect windowRectOverride) {
        if (windowRectOverride != null) {
            return new Rect(windowRectOverride);
        }
        return Recents.getSystemServices().getWindowRect();
    }

    private void updateHeaderBarLayout(TaskStack stack, Rect windowRectOverride) {
        Rect windowRect = getWindowRect(windowRectOverride);
        int taskViewWidth = 0;
        boolean useGridLayout = this.mDummyStackView.useGridLayout();
        updateDummyStackViewLayout(this.mDummyStackView.getStackAlgorithm(), stack, windowRect);
        if (stack != null) {
            TaskStackLayoutAlgorithm stackLayout = this.mDummyStackView.getStackAlgorithm();
            this.mDummyStackView.getStack().removeAllTasks(false);
            this.mDummyStackView.setTasks(stack, false);
            if (useGridLayout) {
                TaskGridLayoutAlgorithm gridLayout = this.mDummyStackView.getGridAlgorithm();
                gridLayout.initialize(windowRect);
                taskViewWidth = (int) gridLayout.getTransform(0, stack.getTaskCount(), new TaskViewTransform(), stackLayout).rect.width();
            } else {
                Rect taskViewBounds = stackLayout.getUntransformedTaskViewBounds();
                if (!taskViewBounds.isEmpty()) {
                    taskViewWidth = taskViewBounds.width();
                }
            }
        }
        if (stack != null && taskViewWidth > 0) {
            synchronized (this.mHeaderBarLock) {
                if (this.mHeaderBar.getMeasuredWidth() != taskViewWidth || this.mHeaderBar.getMeasuredHeight() != this.mTaskBarHeight) {
                    if (useGridLayout) {
                        this.mHeaderBar.setShouldDarkenBackgroundColor(true);
                        this.mHeaderBar.setNoUserInteractionState();
                    }
                    this.mHeaderBar.forceLayout();
                    this.mHeaderBar.measure(View.MeasureSpec.makeMeasureSpec(taskViewWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mTaskBarHeight, 1073741824));
                }
                this.mHeaderBar.layout(0, 0, taskViewWidth, this.mTaskBarHeight);
            }
        }
    }

    private void calculateWindowStableInsets(Rect inOutInsets, Rect windowRect, Rect displayRect) {
        Rect appRect = new Rect(displayRect);
        appRect.inset(inOutInsets);
        Rect windowRectWithInsets = new Rect(windowRect);
        windowRectWithInsets.intersect(appRect);
        inOutInsets.left = windowRectWithInsets.left - windowRect.left;
        inOutInsets.top = windowRectWithInsets.top - windowRect.top;
        inOutInsets.right = windowRect.right - windowRectWithInsets.right;
        inOutInsets.bottom = windowRect.bottom - windowRectWithInsets.bottom;
    }

    private void preloadIcon(int runningTaskId) {
        RecentsTaskLoadPlan.Options launchOpts = new RecentsTaskLoadPlan.Options();
        launchOpts.runningTaskId = runningTaskId;
        launchOpts.loadThumbnails = false;
        launchOpts.onlyLoadForCache = true;
        Recents.getTaskLoader().loadTasks(sInstanceLoadPlan, launchOpts);
    }

    protected ActivityOptions getUnknownTransitionActivityOptions() {
        return ActivityOptions.makeCustomAnimation(this.mContext, R.anim.recents_from_unknown_enter, R.anim.recents_from_unknown_exit, this.mHandler, null);
    }

    protected ActivityOptions getHomeTransitionActivityOptions() {
        return ActivityOptions.makeCustomAnimation(this.mContext, R.anim.recents_from_launcher_enter, R.anim.recents_from_launcher_exit, this.mHandler, null);
    }

    private Pair<ActivityOptions, AppTransitionAnimationSpecsFuture> getThumbnailTransitionActivityOptions(ActivityManager.RunningTaskInfo runningTask, Rect windowOverrideRect) {
        boolean isLowRamDevice = Recents.getConfiguration().isLowRamDevice;
        final Task toTask = new Task();
        final TaskViewTransform toTransform = getThumbnailTransitionTransform(this.mDummyStackView, toTask, windowOverrideRect);
        final RectF toTaskRect = toTransform.rect;
        AppTransitionAnimationSpecsFuture future = new AppTransitionAnimationSpecsFuture(this.mHandler) { // from class: com.android.systemui.recents.RecentsImpl.4
            @Override // com.android.systemui.shared.recents.view.AppTransitionAnimationSpecsFuture
            public List<AppTransitionAnimationSpecCompat> composeSpecs() {
                Rect rect = new Rect();
                toTaskRect.round(rect);
                Bitmap thumbnail = RecentsImpl.this.drawThumbnailTransitionBitmap(toTask, toTransform);
                return Lists.newArrayList(new AppTransitionAnimationSpecCompat[]{new AppTransitionAnimationSpecCompat(toTask.key.id, thumbnail, rect)});
            }
        };
        return new Pair<>(RecentsTransition.createAspectScaleAnimation(this.mContext, this.mHandler, false, future, isLowRamDevice ? null : this.mResetToggleFlagListener), future);
    }

    private TaskViewTransform getThumbnailTransitionTransform(TaskStackView stackView, Task runningTaskOut, Rect windowOverrideRect) {
        TaskStack stack = stackView.getStack();
        Task launchTask = stack.getLaunchTarget();
        if (launchTask != null) {
            runningTaskOut.copyFrom(launchTask);
        } else {
            launchTask = stack.getFrontMostTask();
            runningTaskOut.copyFrom(launchTask);
        }
        stackView.updateLayoutAlgorithm(true);
        stackView.updateToInitialState();
        stackView.getStackAlgorithm().getStackTransformScreenCoordinates(launchTask, stackView.getScroller().getStackScroll(), this.mTmpTransform, null, windowOverrideRect);
        return this.mTmpTransform;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap drawThumbnailTransitionBitmap(Task toTask, TaskViewTransform toTransform) {
        Bitmap bitmapDrawViewIntoHardwareBitmap;
        SystemServicesProxy ssp = Recents.getSystemServices();
        int width = (int) toTransform.rect.width();
        int height = (int) toTransform.rect.height();
        if (toTransform == null || toTask.key == null || width <= 0 || height <= 0) {
            return null;
        }
        synchronized (this.mHeaderBarLock) {
            boolean disabledInSafeMode = !toTask.isSystemApp && ssp.isInSafeMode();
            this.mHeaderBar.onTaskViewSizeChanged(width, height);
            Drawable icon = this.mHeaderBar.getIconView().getDrawable();
            if (icon != null) {
                icon.setCallback(null);
            }
            this.mHeaderBar.bindToTask(toTask, false, disabledInSafeMode);
            this.mHeaderBar.onTaskDataLoaded();
            this.mHeaderBar.setDimAlpha(toTransform.dimAlpha);
            bitmapDrawViewIntoHardwareBitmap = RecentsTransition.drawViewIntoHardwareBitmap(width, this.mTaskBarHeight, this.mHeaderBar, 1.0f, 0);
        }
        return bitmapDrawViewIntoHardwareBitmap;
    }

    protected void startRecentsActivityAndDismissKeyguardIfNeeded(final ActivityManager.RunningTaskInfo runningTask, final boolean isHomeStackVisible, final boolean animate, final int growTarget) {
        StatusBar statusBar = getStatusBar();
        if (statusBar != null && statusBar.isKeyguardShowing()) {
            statusBar.executeRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.recents.-$$Lambda$RecentsImpl$nl0CQjEHqL97ISAVXWR_Z-bUHGg
                @Override // java.lang.Runnable
                public final void run() {
                    RecentsImpl.lambda$startRecentsActivityAndDismissKeyguardIfNeeded$3(this.f$0, runningTask, isHomeStackVisible, animate, growTarget);
                }
            }, null, true, false, true);
        } else {
            startRecentsActivity(runningTask, isHomeStackVisible, animate, growTarget);
        }
    }

    public static /* synthetic */ void lambda$startRecentsActivityAndDismissKeyguardIfNeeded$3(final RecentsImpl recentsImpl, final ActivityManager.RunningTaskInfo runningTask, final boolean isHomeStackVisible, final boolean animate, final int growTarget) {
        recentsImpl.mTrustManager.reportKeyguardShowingChanged();
        recentsImpl.mHandler.post(new Runnable() { // from class: com.android.systemui.recents.-$$Lambda$RecentsImpl$p86QbP39-AERJwYL1CmDcPooyqY
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.startRecentsActivity(runningTask, isHomeStackVisible, animate, growTarget);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRecentsActivity(ActivityManager.RunningTaskInfo runningTask, boolean isHomeStackVisible, boolean animate, int growTarget) {
        int runningTaskId;
        Pair<ActivityOptions, AppTransitionAnimationSpecsFuture> pair;
        ActivityOptions unknownTransitionActivityOptions;
        RecentsTaskLoader loader = Recents.getTaskLoader();
        RecentsActivityLaunchState launchState = Recents.getConfiguration().getLaunchState();
        if (!this.mLaunchedWhileDocking && runningTask != null) {
            runningTaskId = runningTask.id;
        } else {
            runningTaskId = -1;
        }
        if (this.mLaunchedWhileDocking || this.mTriggeredFromAltTab || sInstanceLoadPlan == null) {
            sInstanceLoadPlan = new RecentsTaskLoadPlan(this.mContext);
        }
        if (this.mLaunchedWhileDocking || this.mTriggeredFromAltTab || !sInstanceLoadPlan.hasTasks()) {
            loader.preloadTasks(sInstanceLoadPlan, runningTaskId);
        }
        TaskStack stack = sInstanceLoadPlan.getTaskStack();
        boolean z = true;
        boolean hasRecentTasks = stack.getTaskCount() > 0;
        boolean useThumbnailTransition = (runningTask == null || isHomeStackVisible || !hasRecentTasks) ? false : true;
        launchState.launchedFromHome = (useThumbnailTransition || this.mLaunchedWhileDocking) ? false : true;
        if (!useThumbnailTransition && !this.mLaunchedWhileDocking) {
            z = false;
        }
        launchState.launchedFromApp = z;
        launchState.launchedFromPipApp = false;
        launchState.launchedWithNextPipApp = stack.isNextLaunchTargetPip(getLastPipTime());
        launchState.launchedViaDockGesture = this.mLaunchedWhileDocking;
        launchState.launchedViaDragGesture = this.mDraggingInRecents;
        launchState.launchedToTaskId = runningTaskId;
        launchState.launchedWithAltTab = this.mTriggeredFromAltTab;
        if (ActivityManager.isUserAMonkey()) {
            Log.w("RecentsImpl", "monkey testing,setWaitingForTransitionStart false");
            setWaitingForTransitionStart(false);
        } else {
            setWaitingForTransitionStart(useThumbnailTransition);
        }
        preloadIcon(runningTaskId);
        Rect windowOverrideRect = getWindowRectOverride(growTarget);
        updateHeaderBarLayout(stack, windowOverrideRect);
        TaskStackLayoutAlgorithm.VisibilityReport stackVr = this.mDummyStackView.computeStackVisibilityReport();
        launchState.launchedNumVisibleTasks = stackVr.numVisibleTasks;
        launchState.launchedNumVisibleThumbnails = stackVr.numVisibleThumbnails;
        if (!animate) {
            startRecentsActivity(ActivityOptions.makeCustomAnimation(this.mContext, -1, -1), null);
            return;
        }
        if (useThumbnailTransition) {
            pair = getThumbnailTransitionActivityOptions(runningTask, windowOverrideRect);
        } else {
            if (hasRecentTasks) {
                unknownTransitionActivityOptions = getHomeTransitionActivityOptions();
            } else {
                unknownTransitionActivityOptions = getUnknownTransitionActivityOptions();
            }
            pair = new Pair<>(unknownTransitionActivityOptions, null);
        }
        startRecentsActivity((ActivityOptions) pair.first, (AppTransitionAnimationSpecsFuture) pair.second);
        this.mLastToggleTime = SystemClock.elapsedRealtime();
    }

    private Rect getWindowRectOverride(int growTarget) {
        if (growTarget == -1) {
            return SystemServicesProxy.getInstance(this.mContext).getWindowRect();
        }
        Rect result = new Rect();
        Rect displayRect = Recents.getSystemServices().getDisplayRect();
        DockedDividerUtils.calculateBoundsForPosition(growTarget, 4, result, displayRect.width(), displayRect.height(), Recents.getSystemServices().getDockedDividerSize(this.mContext));
        return result;
    }

    private StatusBar getStatusBar() {
        return (StatusBar) ((SystemUIApplication) this.mContext).getComponent(StatusBar.class);
    }

    private void startRecentsActivity(final ActivityOptions opts, final AppTransitionAnimationSpecsFuture future) {
        final Intent intent = new Intent();
        intent.setClassName("com.android.systemui", "com.android.systemui.recents.RecentsActivity");
        intent.setFlags(276840448);
        HidePipMenuEvent hideMenuEvent = new HidePipMenuEvent();
        hideMenuEvent.addPostAnimationCallback(new Runnable() { // from class: com.android.systemui.recents.-$$Lambda$RecentsImpl$G7WjMO7A3RzOtnhk-21g2Og8V7I
            @Override // java.lang.Runnable
            public final void run() {
                RecentsImpl.lambda$startRecentsActivity$4(intent, opts, future);
            }
        });
        EventBus.getDefault().send(hideMenuEvent);
        this.mDummyStackView.setTasks(this.mEmptyTaskStack, false);
    }

    static /* synthetic */ void lambda$startRecentsActivity$4(Intent intent, ActivityOptions opts, AppTransitionAnimationSpecsFuture future) {
        Recents.getSystemServices().startActivityAsUserAsync(intent, opts);
        EventBus.getDefault().send(new RecentsActivityStartingEvent());
        if (future != null) {
            future.composeSpecsSynchronous();
        }
    }

    public void onAnimationFinished() {
        EventBus.getDefault().post(new EnterRecentsWindowLastAnimationFrameEvent());
    }
}
