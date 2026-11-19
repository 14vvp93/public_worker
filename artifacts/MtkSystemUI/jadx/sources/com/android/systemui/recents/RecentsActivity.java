package com.android.systemui.recents;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.content.PackageMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.events.activity.CancelEnterRecentsWindowAnimationEvent;
import com.android.systemui.recents.events.activity.ConfigurationChangedEvent;
import com.android.systemui.recents.events.activity.DismissRecentsToHomeAnimationStarted;
import com.android.systemui.recents.events.activity.DockedFirstAnimationFrameEvent;
import com.android.systemui.recents.events.activity.DockedTopTaskEvent;
import com.android.systemui.recents.events.activity.EnterRecentsWindowAnimationCompletedEvent;
import com.android.systemui.recents.events.activity.EnterRecentsWindowLastAnimationFrameEvent;
import com.android.systemui.recents.events.activity.ExitRecentsWindowFirstAnimationFrameEvent;
import com.android.systemui.recents.events.activity.HideRecentsEvent;
import com.android.systemui.recents.events.activity.LaunchTaskFailedEvent;
import com.android.systemui.recents.events.activity.LaunchTaskSucceededEvent;
import com.android.systemui.recents.events.activity.MultiWindowStateChangedEvent;
import com.android.systemui.recents.events.activity.PackagesChangedEvent;
import com.android.systemui.recents.events.activity.RecentsActivityStartingEvent;
import com.android.systemui.recents.events.activity.ToggleRecentsEvent;
import com.android.systemui.recents.events.component.ActivityUnpinnedEvent;
import com.android.systemui.recents.events.component.RecentsVisibilityChangedEvent;
import com.android.systemui.recents.events.component.ScreenPinningRequestEvent;
import com.android.systemui.recents.events.component.SetWaitingForTransitionStartEvent;
import com.android.systemui.recents.events.ui.AllTaskViewsDismissedEvent;
import com.android.systemui.recents.events.ui.DeleteTaskDataEvent;
import com.android.systemui.recents.events.ui.HideIncompatibleAppOverlayEvent;
import com.android.systemui.recents.events.ui.RecentsDrawnEvent;
import com.android.systemui.recents.events.ui.ShowApplicationInfoEvent;
import com.android.systemui.recents.events.ui.ShowIncompatibleAppOverlayEvent;
import com.android.systemui.recents.events.ui.StackViewScrolledEvent;
import com.android.systemui.recents.events.ui.TaskViewDismissedEvent;
import com.android.systemui.recents.events.ui.UserInteractionEvent;
import com.android.systemui.recents.events.ui.focus.DismissFocusedTaskViewEvent;
import com.android.systemui.recents.events.ui.focus.FocusNextTaskViewEvent;
import com.android.systemui.recents.events.ui.focus.FocusPreviousTaskViewEvent;
import com.android.systemui.recents.events.ui.focus.NavigateTaskViewEvent;
import com.android.systemui.recents.misc.SystemServicesProxy;
import com.android.systemui.recents.views.RecentsView;
import com.android.systemui.recents.views.SystemBarScrimViews;
import com.android.systemui.shared.recents.model.RecentsTaskLoadPlan;
import com.android.systemui.shared.recents.model.RecentsTaskLoader;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.TaskStack;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RecentsActivity extends Activity implements ViewTreeObserver.OnPreDrawListener, ColorExtractor.OnColorsChangedListener {
    private SysuiColorExtractor mColorExtractor;
    private boolean mFinishedOnStartup;
    private Intent mHomeIntent;
    private boolean mIgnoreAltTabRelease;
    private View mIncompatibleAppOverlay;
    private boolean mIsVisible;
    private Configuration mLastConfig;
    private long mLastTabKeyEventTime;
    private boolean mRecentsStartRequested;
    private RecentsView mRecentsView;
    private SystemBarScrimViews mScrimViews;
    private boolean mUsingDarkText;
    private PackageMonitor mPackageMonitor = new PackageMonitor() { // from class: com.android.systemui.recents.RecentsActivity.1
        public void onPackageRemoved(String packageName, int uid) {
            RecentsActivity.this.onPackageChanged(packageName, getChangingUserId());
        }

        public boolean onPackageChanged(String packageName, int uid, String[] components) {
            RecentsActivity.this.onPackageChanged(packageName, getChangingUserId());
            return true;
        }

        public void onPackageModified(String packageName) {
            RecentsActivity.this.onPackageChanged(packageName, getChangingUserId());
        }
    };
    private Handler mHandler = new Handler();
    private final UserInteractionEvent mUserInteractionEvent = new UserInteractionEvent();
    final BroadcastReceiver mSystemBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.recents.RecentsActivity.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context ctx, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.SCREEN_OFF")) {
                RecentsActivity.this.dismissRecentsToHomeIfVisible(false);
            } else if (action.equals("android.intent.action.USER_SWITCHED")) {
                RecentsActivity.this.finish();
            }
        }
    };
    private final ViewTreeObserver.OnPreDrawListener mRecentsDrawnEventListener = new AnonymousClass3();

    class LaunchHomeRunnable implements Runnable {
        Intent mLaunchIntent;
        ActivityOptions mOpts;

        public LaunchHomeRunnable(Intent launchIntent, ActivityOptions opts) {
            this.mLaunchIntent = launchIntent;
            this.mOpts = opts;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                RecentsActivity.this.mHandler.post(new Runnable() { // from class: com.android.systemui.recents.-$$Lambda$RecentsActivity$LaunchHomeRunnable$K3jCoKVe41-EkTmSKH7i98xFt8k
                    @Override // java.lang.Runnable
                    public final void run() {
                        RecentsActivity.LaunchHomeRunnable.lambda$run$0(this.f$0);
                    }
                });
            } catch (Exception e) {
                Log.e("RecentsActivity", RecentsActivity.this.getString(R.string.recents_launch_error_message, new Object[]{"Home"}), e);
            }
        }

        public static /* synthetic */ void lambda$run$0(LaunchHomeRunnable launchHomeRunnable) {
            ActivityOptions opts = launchHomeRunnable.mOpts;
            if (opts == null) {
                opts = ActivityOptions.makeCustomAnimation(RecentsActivity.this, R.anim.recents_to_launcher_enter, R.anim.recents_to_launcher_exit);
            }
            RecentsActivity.this.startActivityAsUser(launchHomeRunnable.mLaunchIntent, opts.toBundle(), UserHandle.CURRENT);
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.recents.RecentsActivity$3, reason: invalid class name */
    class AnonymousClass3 implements ViewTreeObserver.OnPreDrawListener {
        AnonymousClass3() {
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            RecentsActivity.this.mRecentsView.getViewTreeObserver().removeOnPreDrawListener(this);
            EventBus.getDefault().post(new RecentsDrawnEvent());
            if (LatencyTracker.isEnabled(RecentsActivity.this.getApplicationContext())) {
                DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.recents.-$$Lambda$RecentsActivity$3$yqqbbfyTHFuJpHT3gETj09GBDFY
                    @Override // java.lang.Runnable
                    public final void run() {
                        LatencyTracker.getInstance(RecentsActivity.this.getApplicationContext()).onActionEnd(1);
                    }
                });
            }
            DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.recents.-$$Lambda$RecentsActivity$3$SXW_26Z-B_jvwD3qSXfVNpRowHM
                @Override // java.lang.Runnable
                public final void run() {
                    RecentsActivity.AnonymousClass3.lambda$onPreDraw$1(this.f$0);
                }
            });
            return true;
        }

        public static /* synthetic */ void lambda$onPreDraw$1(AnonymousClass3 anonymousClass3) {
            Recents.getTaskLoader().startLoader(RecentsActivity.this);
            Recents.getTaskLoader().getHighResThumbnailLoader().setVisible(true);
        }
    }

    boolean dismissRecentsToLaunchTargetTaskOrHome() {
        SystemServicesProxy ssp = Recents.getSystemServices();
        if (ssp.isRecentsActivityVisible()) {
            if (this.mRecentsView.launchPreviousTask()) {
                return true;
            }
            dismissRecentsToHome(true);
            return false;
        }
        return false;
    }

    boolean dismissRecentsToFocusedTaskOrHome() {
        SystemServicesProxy ssp = Recents.getSystemServices();
        if (!ssp.isRecentsActivityVisible()) {
            return false;
        }
        if (this.mRecentsView.launchFocusedTask(0)) {
            return true;
        }
        dismissRecentsToHome(true);
        return true;
    }

    void dismissRecentsToHome(boolean animateTaskViews) {
        dismissRecentsToHome(animateTaskViews, null);
    }

    void dismissRecentsToHome(boolean animateTaskViews, ActivityOptions overrideAnimation) {
        DismissRecentsToHomeAnimationStarted dismissEvent = new DismissRecentsToHomeAnimationStarted(animateTaskViews);
        dismissEvent.addPostAnimationCallback(new LaunchHomeRunnable(this.mHomeIntent, overrideAnimation));
        ActivityManagerWrapper.getInstance().closeSystemWindows("homekey");
        EventBus.getDefault().send(dismissEvent);
    }

    boolean dismissRecentsToHomeIfVisible(boolean animated) {
        SystemServicesProxy ssp = Recents.getSystemServices();
        if (ssp.isRecentsActivityVisible()) {
            dismissRecentsToHome(animated);
            return true;
        }
        return false;
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mFinishedOnStartup = false;
        SystemServicesProxy ssp = Recents.getSystemServices();
        if (ssp == null) {
            this.mFinishedOnStartup = true;
            finish();
            return;
        }
        EventBus.getDefault().register(this, 2);
        this.mPackageMonitor.register(this, Looper.getMainLooper(), UserHandle.ALL, true);
        this.mColorExtractor = (SysuiColorExtractor) Dependency.get(SysuiColorExtractor.class);
        this.mColorExtractor.addOnColorsChangedListener(this);
        this.mUsingDarkText = this.mColorExtractor.getColors(1, 1, true).supportsDarkText();
        setTheme(this.mUsingDarkText ? 2131886358 : 2131886357);
        setContentView(R.layout.recents);
        takeKeyEvents(true);
        this.mRecentsView = (RecentsView) findViewById(R.id.recents_view);
        this.mScrimViews = new SystemBarScrimViews(this);
        getWindow().getAttributes().privateFlags |= 16384;
        if (Recents.getConfiguration().isLowRamDevice) {
            getWindow().addFlags(1024);
        }
        this.mLastConfig = new Configuration(Utilities.getAppConfiguration(this));
        this.mRecentsView.updateBackgroundScrim(getWindow(), isInMultiWindowMode());
        this.mHomeIntent = new Intent("android.intent.action.MAIN", (Uri) null);
        this.mHomeIntent.addCategory("android.intent.category.HOME");
        this.mHomeIntent.addFlags(270532608);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.USER_SWITCHED");
        registerReceiver(this.mSystemBroadcastReceiver, filter);
        getWindow().addPrivateFlags(64);
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        reloadStackView();
        EventBus.getDefault().send(new RecentsVisibilityChangedEvent(this, true));
        MetricsLogger.visible(this, 224);
        ColorExtractor.GradientColors systemColors = this.mColorExtractor.getColors(1, 1, true);
        this.mRecentsView.setScrimColors(systemColors, false);
        this.mRecentsView.getViewTreeObserver().addOnPreDrawListener(this.mRecentsDrawnEventListener);
        Object isRelaunching = getLastNonConfigurationInstance();
        if (isRelaunching != null && (isRelaunching instanceof Boolean) && ((Boolean) isRelaunching).booleanValue()) {
            RecentsActivityLaunchState launchState = Recents.getConfiguration().getLaunchState();
            launchState.launchedViaDockGesture = false;
            launchState.launchedFromApp = false;
            launchState.launchedFromHome = false;
            onEnterAnimationComplete();
        }
        this.mRecentsStartRequested = false;
    }

    public void onColorsChanged(ColorExtractor colorExtractor, int which) {
        if ((which & 1) != 0) {
            ColorExtractor.GradientColors colors = this.mColorExtractor.getColors(1, 1, true);
            boolean darkText = colors.supportsDarkText();
            if (darkText != this.mUsingDarkText) {
                this.mUsingDarkText = darkText;
                setTheme(this.mUsingDarkText ? 2131886358 : 2131886357);
                this.mRecentsView.reevaluateStyles();
            }
            this.mRecentsView.setScrimColors(colors, true);
        }
    }

    private void reloadStackView() {
        RecentsTaskLoader loader = Recents.getTaskLoader();
        RecentsTaskLoadPlan loadPlan = RecentsImpl.consumeInstanceLoadPlan();
        if (loadPlan == null) {
            loadPlan = new RecentsTaskLoadPlan(this);
        }
        RecentsConfiguration config = Recents.getConfiguration();
        RecentsActivityLaunchState launchState = config.getLaunchState();
        if (!loadPlan.hasTasks()) {
            loader.preloadTasks(loadPlan, launchState.launchedToTaskId);
        }
        RecentsTaskLoadPlan.Options loadOpts = new RecentsTaskLoadPlan.Options();
        loadOpts.runningTaskId = launchState.launchedToTaskId;
        loadOpts.numVisibleTasks = launchState.launchedNumVisibleTasks;
        loadOpts.numVisibleTaskThumbnails = launchState.launchedNumVisibleThumbnails;
        loader.loadTasks(loadPlan, loadOpts);
        TaskStack stack = loadPlan.getTaskStack();
        this.mRecentsView.onReload(stack, this.mIsVisible);
        boolean animateNavBarScrim = !launchState.launchedViaDockGesture;
        this.mScrimViews.updateNavBarScrim(animateNavBarScrim, stack.getTaskCount() > 0, null);
        boolean wasLaunchedByAm = (launchState.launchedFromHome || launchState.launchedFromApp) ? false : true;
        if (wasLaunchedByAm) {
            EventBus.getDefault().send(new EnterRecentsWindowAnimationCompletedEvent());
        }
        if (launchState.launchedWithAltTab) {
            MetricsLogger.count(this, "overview_trigger_alttab", 1);
        } else {
            MetricsLogger.count(this, "overview_trigger_nav_btn", 1);
        }
        if (launchState.launchedFromApp) {
            Task launchTarget = stack.getLaunchTarget();
            int launchTaskIndexInStack = launchTarget != null ? stack.indexOfTask(launchTarget) : 0;
            MetricsLogger.count(this, "overview_source_app", 1);
            MetricsLogger.histogram(this, "overview_source_app_index", launchTaskIndexInStack);
        } else {
            MetricsLogger.count(this, "overview_source_home", 1);
        }
        int taskCount = this.mRecentsView.getStack().getTaskCount();
        MetricsLogger.histogram(this, "overview_task_count", taskCount);
        this.mIsVisible = true;
    }

    @Override // android.app.Activity
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        EventBus.getDefault().send(new EnterRecentsWindowAnimationCompletedEvent());
        EventBus.getDefault().send(new SetWaitingForTransitionStartEvent(false));
    }

    @Override // android.app.Activity
    public Object onRetainNonConfigurationInstance() {
        return true;
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.mIgnoreAltTabRelease = false;
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Configuration newDeviceConfiguration = Utilities.getAppConfiguration(this);
        int numStackTasks = this.mRecentsView.getStack().getTaskCount();
        Log.d("RecentsActivity", "onConfigurationChanged newConfig : " + newConfig.toString());
        Log.d("RecentsActivity", "onConfigurationChanged newDeviceConfiguration : " + newDeviceConfiguration.toString());
        Log.d("RecentsActivity", "onConfigurationChanged numStackTasks : " + numStackTasks);
        Rect appBounds = newConfig.windowConfiguration.getAppBounds();
        int width = appBounds.right - appBounds.left;
        int height = appBounds.top;
        appBounds.bottom = height;
        Point displaySize = new Point();
        getDisplay().getRealSize(displaySize);
        Log.d("RecentsActivity", "onConfigurationChanged appBounds : " + appBounds + ", width : " + width + ", height : " + height);
        StringBuilder sb = new StringBuilder();
        sb.append("onConfigurationChanged displaySize : ");
        sb.append(displaySize);
        Log.d("RecentsActivity", sb.toString());
        Log.d("RecentsActivity", "onConfigurationChanged isInMultiWindowMode : " + isInMultiWindowMode());
        int i = 0;
        if (width != displaySize.x && height != displaySize.y && newConfig.windowConfiguration.getWindowingMode() == 1) {
            Log.e("RecentsActivity", "exception on split screen");
            Rect fullscreenRect = new Rect(0, 0, displaySize.x, displaySize.y);
            try {
                List<ActivityManager.StackInfo> allStack = ActivityManager.getService().getAllStackInfos();
                if (allStack != null) {
                    while (true) {
                        int i2 = i;
                        int i3 = allStack.size();
                        if (i2 >= i3) {
                            break;
                        }
                        ActivityManager.StackInfo stackInfo = allStack.get(i2);
                        if (stackInfo != null) {
                            Log.d("RecentsActivity", "resize stack id : " + stackInfo.stackId);
                            ActivityManager.getService().resizeStack(stackInfo.stackId, fullscreenRect, false, false, false, 0);
                        }
                        i = i2 + 1;
                    }
                }
            } catch (RemoteException e) {
                Log.w("RecentsActivity", "Failed to resize stack: " + e);
            }
            recreate();
            return;
        }
        EventBus.getDefault().send(new ConfigurationChangedEvent(false, this.mLastConfig.orientation != newDeviceConfiguration.orientation, this.mLastConfig.densityDpi != newDeviceConfiguration.densityDpi, numStackTasks > 0));
        this.mLastConfig.updateFrom(newDeviceConfiguration);
    }

    @Override // android.app.Activity
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        Log.d("XYG", "onMultiWindowModeChanged " + isInMultiWindowMode);
        if (!isInMultiWindowMode) {
            ActivityManagerWrapper.currentDockActivity = null;
        }
        this.mRecentsView.updateBackgroundScrim(getWindow(), isInMultiWindowMode);
        if (this.mIsVisible) {
            reloadTaskStack(isInMultiWindowMode, true);
        }
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        this.mIsVisible = false;
        EventBus.getDefault().send(new RecentsVisibilityChangedEvent(this, false));
        MetricsLogger.hidden(this, 224);
        Recents.getTaskLoader().getHighResThumbnailLoader().setVisible(false);
        if (!isChangingConfigurations() && !this.mRecentsStartRequested) {
            RecentsConfiguration config = Recents.getConfiguration();
            RecentsActivityLaunchState launchState = config.getLaunchState();
            launchState.reset();
        }
        Recents.getSystemServices().gc();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (this.mFinishedOnStartup) {
            return;
        }
        unregisterReceiver(this.mSystemBroadcastReceiver);
        this.mPackageMonitor.unregister();
        EventBus.getDefault().unregister(this);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this.mScrimViews, 2);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this.mScrimViews);
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks2
    public void onTrimMemory(int level) {
        RecentsTaskLoader loader = Recents.getTaskLoader();
        if (loader != null) {
            loader.onTrimMemory(level);
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 61) {
            int altTabKeyDelay = getResources().getInteger(R.integer.recents_alt_tab_key_delay);
            boolean hasRepKeyTimeElapsed = SystemClock.elapsedRealtime() - this.mLastTabKeyEventTime > ((long) altTabKeyDelay);
            if (event.getRepeatCount() <= 0 || hasRepKeyTimeElapsed) {
                boolean backward = event.isShiftPressed();
                if (backward) {
                    EventBus.getDefault().send(new FocusPreviousTaskViewEvent());
                } else {
                    EventBus.getDefault().send(new FocusNextTaskViewEvent());
                }
                this.mLastTabKeyEventTime = SystemClock.elapsedRealtime();
                if (event.isAltPressed()) {
                    this.mIgnoreAltTabRelease = false;
                }
            }
            return true;
        }
        if (keyCode != 67 && keyCode != 112) {
            switch (keyCode) {
                case 19:
                case 20:
                case 21:
                case 22:
                    NavigateTaskViewEvent.Direction direction = NavigateTaskViewEvent.getDirectionFromKeyCode(keyCode);
                    EventBus.getDefault().send(new NavigateTaskViewEvent(direction));
                    return true;
            }
        }
        if (event.getRepeatCount() <= 0) {
            EventBus.getDefault().send(new DismissFocusedTaskViewEvent());
            MetricsLogger.histogram(this, "overview_task_dismissed_source", 0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override // android.app.Activity
    public void onUserInteraction() {
        EventBus.getDefault().send(this.mUserInteractionEvent);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        EventBus.getDefault().send(new ToggleRecentsEvent());
    }

    public final void onBusEvent(ToggleRecentsEvent event) {
        RecentsActivityLaunchState launchState = Recents.getConfiguration().getLaunchState();
        if (launchState.launchedFromHome) {
            dismissRecentsToHome(true);
        } else {
            dismissRecentsToLaunchTargetTaskOrHome();
        }
    }

    public final void onBusEvent(RecentsActivityStartingEvent event) {
        this.mRecentsStartRequested = true;
    }

    public final void onBusEvent(HideRecentsEvent event) {
        if (event.triggeredFromAltTab) {
            if (!this.mIgnoreAltTabRelease) {
                dismissRecentsToFocusedTaskOrHome();
            }
        } else if (event.triggeredFromHomeKey) {
            dismissRecentsToHome(true);
            EventBus.getDefault().send(this.mUserInteractionEvent);
        }
    }

    public final void onBusEvent(EnterRecentsWindowLastAnimationFrameEvent event) {
        this.mRecentsView.getViewTreeObserver().addOnPreDrawListener(this);
        this.mRecentsView.invalidate();
    }

    public final void onBusEvent(ExitRecentsWindowFirstAnimationFrameEvent event) {
        this.mRecentsView.getViewTreeObserver().addOnPreDrawListener(this);
        this.mRecentsView.invalidate();
    }

    public final void onBusEvent(DockedFirstAnimationFrameEvent event) {
        this.mRecentsView.getViewTreeObserver().addOnPreDrawListener(this);
        this.mRecentsView.invalidate();
    }

    public final void onBusEvent(CancelEnterRecentsWindowAnimationEvent event) {
        RecentsActivityLaunchState launchState = Recents.getConfiguration().getLaunchState();
        int launchToTaskId = launchState.launchedToTaskId;
        if (launchToTaskId != -1) {
            if (event.launchTask == null || launchToTaskId != event.launchTask.key.id) {
                ActivityManagerWrapper am = ActivityManagerWrapper.getInstance();
                am.cancelWindowTransition(launchState.launchedToTaskId);
            }
        }
    }

    public final void onBusEvent(ShowApplicationInfoEvent event) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", event.task.key.getComponent().getPackageName(), null));
        intent.setComponent(intent.resolveActivity(getPackageManager()));
        TaskStackBuilder.create(this).addNextIntentWithParentStack(intent).startActivities(null, new UserHandle(event.task.key.userId));
        MetricsLogger.count(this, "overview_app_info", 1);
    }

    public final void onBusEvent(ShowIncompatibleAppOverlayEvent event) {
        if (this.mIncompatibleAppOverlay == null) {
            this.mIncompatibleAppOverlay = Utilities.findViewStubById(this, R.id.incompatible_app_overlay_stub).inflate();
            this.mIncompatibleAppOverlay.setWillNotDraw(false);
            this.mIncompatibleAppOverlay.setVisibility(0);
        }
        this.mIncompatibleAppOverlay.animate().alpha(1.0f).setDuration(150L).setInterpolator(Interpolators.ALPHA_IN).start();
    }

    public final void onBusEvent(HideIncompatibleAppOverlayEvent event) {
        if (this.mIncompatibleAppOverlay != null) {
            this.mIncompatibleAppOverlay.animate().alpha(0.0f).setDuration(150L).setInterpolator(Interpolators.ALPHA_OUT).start();
        }
    }

    public final void onBusEvent(DeleteTaskDataEvent event) {
        Log.d("XYG", "onBusEvent removeTask " + event.task.key.id + "/" + event.task.key.getComponent().getPackageName());
        RecentsTaskLoader loader = Recents.getTaskLoader();
        loader.deleteTaskData(event.task, false);
        ActivityManagerWrapper.getInstance().removeTask(event.task.key.id);
        ActivityManager mAm = (ActivityManager) getApplicationContext().getSystemService("activity");
        mAm.forceStopPackage(event.task.key.getComponent().getPackageName());
    }

    public final void onBusEvent(TaskViewDismissedEvent event) {
        this.mRecentsView.updateScrimOpacity();
    }

    public final void onBusEvent(AllTaskViewsDismissedEvent event) {
        if (!this.mIsVisible) {
            return;
        }
        SystemServicesProxy ssp = Recents.getSystemServices();
        if (ssp.hasDockedTask()) {
            this.mRecentsView.showEmptyView(event.msgResId);
        } else {
            dismissRecentsToHome(false);
        }
        MetricsLogger.count(this, "overview_task_all_dismissed", 1);
    }

    public final void onBusEvent(LaunchTaskSucceededEvent event) {
        MetricsLogger.histogram(this, "overview_task_launch_index", event.taskIndexFromStackFront);
    }

    public final void onBusEvent(LaunchTaskFailedEvent event) {
        dismissRecentsToHome(true);
        MetricsLogger.count(this, "overview_task_launch_failed", 1);
    }

    public final void onBusEvent(ScreenPinningRequestEvent event) {
        MetricsLogger.count(this, "overview_screen_pinned", 1);
    }

    public final void onBusEvent(StackViewScrolledEvent event) {
        this.mIgnoreAltTabRelease = true;
    }

    public final void onBusEvent(DockedTopTaskEvent event) {
        this.mRecentsView.getViewTreeObserver().addOnPreDrawListener(this.mRecentsDrawnEventListener);
        this.mRecentsView.invalidate();
    }

    public final void onBusEvent(ActivityUnpinnedEvent event) {
        if (this.mIsVisible) {
            reloadTaskStack(isInMultiWindowMode(), false);
        }
    }

    private void reloadTaskStack(boolean isInMultiWindowMode, boolean sendConfigChangedEvent) {
        Log.d("XYG", "reloadTaskStack");
        RecentsConfiguration config = Recents.getConfiguration();
        RecentsActivityLaunchState launchState = config.getLaunchState();
        RecentsTaskLoader loader = Recents.getTaskLoader();
        RecentsTaskLoadPlan loadPlan = new RecentsTaskLoadPlan(this);
        loader.preloadTasks(loadPlan, -1);
        RecentsTaskLoadPlan.Options loadOpts = new RecentsTaskLoadPlan.Options();
        loadOpts.numVisibleTasks = launchState.launchedNumVisibleTasks;
        loadOpts.numVisibleTaskThumbnails = launchState.launchedNumVisibleThumbnails;
        loader.loadTasks(loadPlan, loadOpts);
        TaskStack stack = loadPlan.getTaskStack();
        int numStackTasks = stack.getTaskCount();
        boolean showDeferredAnimation = numStackTasks > 0;
        if (sendConfigChangedEvent) {
            EventBus.getDefault().send(new ConfigurationChangedEvent(true, false, false, numStackTasks > 0));
        }
        EventBus.getDefault().send(new MultiWindowStateChangedEvent(isInMultiWindowMode, showDeferredAnimation, stack));
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public boolean onPreDraw() {
        this.mRecentsView.getViewTreeObserver().removeOnPreDrawListener(this);
        return true;
    }

    public void onPackageChanged(String packageName, int userId) {
        Recents.getTaskLoader().onPackageChanged(packageName);
        EventBus.getDefault().send(new PackagesChangedEvent(packageName, userId));
    }

    @Override // android.app.Activity
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
        EventBus.getDefault().dump(prefix, writer);
        Recents.getTaskLoader().dump(prefix, writer);
        String id = Integer.toHexString(System.identityHashCode(this));
        writer.print(prefix);
        writer.print("RecentsActivity");
        writer.print(" visible=");
        writer.print(this.mIsVisible ? "Y" : "N");
        writer.print(" currentTime=");
        writer.print(System.currentTimeMillis());
        writer.print(" [0x");
        writer.print(id);
        writer.print("]");
        writer.println();
        if (this.mRecentsView != null) {
            this.mRecentsView.dump(prefix, writer);
        }
    }
}
