package com.android.systemui.recents.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.MutableBoolean;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.RecentsActivityLaunchState;
import com.android.systemui.recents.RecentsConfiguration;
import com.android.systemui.recents.RecentsImpl;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.events.activity.CancelEnterRecentsWindowAnimationEvent;
import com.android.systemui.recents.events.activity.ConfigurationChangedEvent;
import com.android.systemui.recents.events.activity.DismissRecentsToHomeAnimationStarted;
import com.android.systemui.recents.events.activity.EnterRecentsWindowAnimationCompletedEvent;
import com.android.systemui.recents.events.activity.HideRecentsEvent;
import com.android.systemui.recents.events.activity.HideStackActionButtonEvent;
import com.android.systemui.recents.events.activity.LaunchMostRecentTaskRequestEvent;
import com.android.systemui.recents.events.activity.LaunchNextTaskRequestEvent;
import com.android.systemui.recents.events.activity.LaunchTaskEvent;
import com.android.systemui.recents.events.activity.LaunchTaskStartedEvent;
import com.android.systemui.recents.events.activity.MultiWindowStateChangedEvent;
import com.android.systemui.recents.events.activity.PackagesChangedEvent;
import com.android.systemui.recents.events.activity.ShowEmptyViewEvent;
import com.android.systemui.recents.events.activity.ShowStackActionButtonEvent;
import com.android.systemui.recents.events.component.ActivityPinnedEvent;
import com.android.systemui.recents.events.component.ExpandPipEvent;
import com.android.systemui.recents.events.component.HidePipMenuEvent;
import com.android.systemui.recents.events.component.RecentsVisibilityChangedEvent;
import com.android.systemui.recents.events.ui.AllTaskViewsDismissedEvent;
import com.android.systemui.recents.events.ui.DeleteTaskDataEvent;
import com.android.systemui.recents.events.ui.DismissAllTaskViewsEvent;
import com.android.systemui.recents.events.ui.DismissTaskViewEvent;
import com.android.systemui.recents.events.ui.RecentsGrowingEvent;
import com.android.systemui.recents.events.ui.TaskViewDismissedEvent;
import com.android.systemui.recents.events.ui.UserInteractionEvent;
import com.android.systemui.recents.events.ui.dragndrop.DragDropTargetChangedEvent;
import com.android.systemui.recents.events.ui.dragndrop.DragEndCancelledEvent;
import com.android.systemui.recents.events.ui.dragndrop.DragEndEvent;
import com.android.systemui.recents.events.ui.dragndrop.DragStartEvent;
import com.android.systemui.recents.events.ui.focus.DismissFocusedTaskViewEvent;
import com.android.systemui.recents.events.ui.focus.FocusNextTaskViewEvent;
import com.android.systemui.recents.events.ui.focus.FocusPreviousTaskViewEvent;
import com.android.systemui.recents.events.ui.focus.NavigateTaskViewEvent;
import com.android.systemui.recents.misc.DozeTrigger;
import com.android.systemui.recents.misc.ReferenceCountedTrigger;
import com.android.systemui.recents.misc.SystemServicesProxy;
import com.android.systemui.recents.views.grid.GridTaskView;
import com.android.systemui.recents.views.grid.TaskGridLayoutAlgorithm;
import com.android.systemui.recents.views.grid.TaskViewFocusFrame;
import com.android.systemui.shared.recents.model.HighResThumbnailLoader;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.TaskStack;
import com.android.systemui.shared.recents.utilities.AnimationProps;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TaskStackView extends FrameLayout implements TaskStackLayoutAlgorithm.TaskStackLayoutAlgorithmCallbacks, TaskStackViewScroller.TaskStackViewScrollerCallbacks, TaskView.TaskViewCallbacks, ViewPool.ViewPoolConsumer<TaskView, Task>, TaskStack.TaskStackCallbacks {
    private TaskStackAnimationHelper mAnimationHelper;
    private ArrayList<TaskViewTransform> mCurrentTaskTransforms;
    private AnimationProps mDeferredTaskViewLayoutAnimation;

    @ViewDebug.ExportedProperty(category = "recents")
    private int mDisplayOrientation;

    @ViewDebug.ExportedProperty(category = "recents")
    private Rect mDisplayRect;
    private int mDividerSize;
    private Task mDraggedTask;

    @ViewDebug.ExportedProperty(category = "recents")
    private boolean mEnterAnimationComplete;
    private final float mFastFlingVelocity;

    @ViewDebug.ExportedProperty(category = "recents")
    private boolean mFinishedLayoutAfterStackReload;

    @ViewDebug.ExportedProperty(deepExport = true, prefix = "focused_task_")
    private Task mFocusedTask;
    private ArraySet<Task.TaskKey> mIgnoreTasks;

    @ViewDebug.ExportedProperty(category = "recents")
    private boolean mInMeasureLayout;
    private LayoutInflater mInflater;

    @ViewDebug.ExportedProperty(category = "recents")
    private int mInitialState;
    private boolean mIsDraggingToLeftDock;
    private int mLastHeight;
    private float mLastScrollPPercent;
    private int mLastWidth;

    @ViewDebug.ExportedProperty(category = "recents")
    private boolean mLaunchNextAfterFirstMeasure;

    @ViewDebug.ExportedProperty(deepExport = true, prefix = "layout_")
    TaskStackLayoutAlgorithm mLayoutAlgorithm;
    private Task mPrefetchingTask;
    private ValueAnimator.AnimatorUpdateListener mRequestUpdateClippingListener;
    private boolean mResetToInitialStateWhenResized;

    @ViewDebug.ExportedProperty(category = "recents")
    boolean mScreenPinningEnabled;
    private TaskStackLayoutAlgorithm mStableLayoutAlgorithm;

    @ViewDebug.ExportedProperty(category = "recents")
    private Rect mStableStackBounds;

    @ViewDebug.ExportedProperty(category = "recents")
    private Rect mStableWindowRect;
    private TaskStack mStack;
    private boolean mStackActionButtonVisible;

    @ViewDebug.ExportedProperty(category = "recents")
    private Rect mStackBounds;
    private DropTarget mStackDropTarget;

    @ViewDebug.ExportedProperty(category = "recents")
    private boolean mStackReloaded;

    @ViewDebug.ExportedProperty(deepExport = true, prefix = "scroller_")
    private TaskStackViewScroller mStackScroller;
    private int mStartTimerIndicatorDuration;
    private int mTaskCornerRadiusPx;
    private TaskViewFocusFrame mTaskViewFocusFrame;
    private ArrayList<TaskView> mTaskViews;

    @ViewDebug.ExportedProperty(category = "recents")
    private boolean mTaskViewsClipDirty;
    private int[] mTmpIntPair;
    private Rect mTmpRect;
    private ArrayMap<Task.TaskKey, TaskView> mTmpTaskViewMap;
    private List<TaskView> mTmpTaskViews;
    private TaskViewTransform mTmpTransform;

    @ViewDebug.ExportedProperty(category = "recents")
    boolean mTouchExplorationEnabled;

    @ViewDebug.ExportedProperty(deepExport = true, prefix = "touch_")
    private TaskStackViewTouchHandler mTouchHandler;

    @ViewDebug.ExportedProperty(deepExport = true, prefix = "doze_")
    private DozeTrigger mUIDozeTrigger;
    private ViewPool<TaskView, Task> mViewPool;

    @ViewDebug.ExportedProperty(category = "recents")
    private Rect mWindowRect;

    public TaskStackView(Context context) {
        int dimensionPixelSize;
        super(context);
        this.mStack = new TaskStack();
        this.mTaskViews = new ArrayList<>();
        this.mCurrentTaskTransforms = new ArrayList<>();
        this.mIgnoreTasks = new ArraySet<>();
        this.mDeferredTaskViewLayoutAnimation = null;
        this.mTaskViewsClipDirty = true;
        this.mEnterAnimationComplete = false;
        this.mStackReloaded = false;
        this.mFinishedLayoutAfterStackReload = false;
        this.mLaunchNextAfterFirstMeasure = false;
        this.mInitialState = 1;
        this.mInMeasureLayout = false;
        this.mStableStackBounds = new Rect();
        this.mStackBounds = new Rect();
        this.mStableWindowRect = new Rect();
        this.mWindowRect = new Rect();
        this.mDisplayRect = new Rect();
        this.mDisplayOrientation = 0;
        this.mTmpRect = new Rect();
        this.mTmpTaskViewMap = new ArrayMap<>();
        this.mTmpTaskViews = new ArrayList();
        this.mTmpTransform = new TaskViewTransform();
        this.mTmpIntPair = new int[2];
        this.mLastScrollPPercent = -1.0f;
        this.mRequestUpdateClippingListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.recents.views.TaskStackView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!TaskStackView.this.mTaskViewsClipDirty) {
                    TaskStackView.this.mTaskViewsClipDirty = true;
                    TaskStackView.this.invalidate();
                }
            }
        };
        this.mStackDropTarget = new DropTarget() { // from class: com.android.systemui.recents.views.TaskStackView.2
            @Override // com.android.systemui.recents.views.DropTarget
            public boolean acceptsDrop(int x, int y, int width, int height, Rect insets, boolean isCurrentTarget) {
                if (!isCurrentTarget) {
                    return TaskStackView.this.mLayoutAlgorithm.mStackRect.contains(x, y);
                }
                return false;
            }
        };
        this.mDraggedTask = null;
        this.mIsDraggingToLeftDock = false;
        SystemServicesProxy ssp = Recents.getSystemServices();
        Resources res = context.getResources();
        this.mStack.setCallbacks(this);
        this.mViewPool = new ViewPool<>(context, this);
        this.mInflater = LayoutInflater.from(context);
        this.mLayoutAlgorithm = new TaskStackLayoutAlgorithm(context, this);
        this.mStableLayoutAlgorithm = new TaskStackLayoutAlgorithm(context, null);
        this.mStackScroller = new TaskStackViewScroller(context, this, this.mLayoutAlgorithm);
        this.mTouchHandler = new TaskStackViewTouchHandler(context, this, this.mStackScroller);
        this.mAnimationHelper = new TaskStackAnimationHelper(context, this);
        if (Recents.getConfiguration().isGridEnabled) {
            dimensionPixelSize = res.getDimensionPixelSize(R.dimen.recents_grid_task_view_rounded_corners_radius);
        } else {
            dimensionPixelSize = res.getDimensionPixelSize(R.dimen.recents_task_view_rounded_corners_radius);
        }
        this.mTaskCornerRadiusPx = dimensionPixelSize;
        this.mFastFlingVelocity = res.getDimensionPixelSize(R.dimen.recents_fast_fling_velocity);
        this.mDividerSize = ssp.getDockedDividerSize(context);
        this.mDisplayOrientation = Utilities.getAppConfiguration(this.mContext).orientation;
        this.mDisplayRect = ssp.getDisplayRect();
        this.mStackActionButtonVisible = false;
        if (Recents.getConfiguration().isGridEnabled) {
            this.mTaskViewFocusFrame = new TaskViewFocusFrame(this.mContext, this, this.mLayoutAlgorithm.mTaskGridLayoutAlgorithm);
            addView(this.mTaskViewFocusFrame);
            getViewTreeObserver().addOnGlobalFocusChangeListener(this.mTaskViewFocusFrame);
        }
        int taskBarDismissDozeDelaySeconds = getResources().getInteger(R.integer.recents_task_bar_dismiss_delay_seconds);
        this.mUIDozeTrigger = new DozeTrigger(taskBarDismissDozeDelaySeconds, new Runnable() { // from class: com.android.systemui.recents.views.TaskStackView.3
            @Override // java.lang.Runnable
            public void run() {
                List<TaskView> taskViews = TaskStackView.this.getTaskViews();
                int taskViewCount = taskViews.size();
                for (int i = 0; i < taskViewCount; i++) {
                    TaskView tv = taskViews.get(i);
                    tv.startNoUserInteractionAnimation();
                }
            }
        });
        setImportantForAccessibility(1);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        EventBus.getDefault().register(this, 3);
        super.onAttachedToWindow();
        readSystemFlags();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    void onReload(boolean isResumingFromVisible) {
        if (!isResumingFromVisible) {
            resetFocusedTask(getFocusedTask());
        }
        List<TaskView> taskViews = new ArrayList<>();
        taskViews.addAll(getTaskViews());
        taskViews.addAll(this.mViewPool.getViews());
        for (int i = taskViews.size() - 1; i >= 0; i--) {
            taskViews.get(i).onReload(isResumingFromVisible);
        }
        readSystemFlags();
        this.mTaskViewsClipDirty = true;
        this.mUIDozeTrigger.stopDozing();
        if (!isResumingFromVisible) {
            this.mStackScroller.reset();
            this.mStableLayoutAlgorithm.reset();
            this.mLayoutAlgorithm.reset();
            this.mLastScrollPPercent = -1.0f;
        }
        this.mStackReloaded = true;
        this.mFinishedLayoutAfterStackReload = false;
        this.mLaunchNextAfterFirstMeasure = false;
        this.mInitialState = 1;
        requestLayout();
    }

    public void setTasks(TaskStack stack, boolean allowNotifyStackChanges) {
        boolean isInitialized = this.mLayoutAlgorithm.isInitialized();
        this.mStack.setTasks(stack, allowNotifyStackChanges && isInitialized);
    }

    public TaskStack getStack() {
        return this.mStack;
    }

    public void updateToInitialState() {
        this.mStackScroller.setStackScrollToInitialState();
        this.mLayoutAlgorithm.setTaskOverridesForInitialState(this.mStack, false);
    }

    void updateTaskViewsList() {
        this.mTaskViews.clear();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            if (v instanceof TaskView) {
                this.mTaskViews.add((TaskView) v);
            }
        }
    }

    List<TaskView> getTaskViews() {
        return this.mTaskViews;
    }

    private TaskView getFrontMostTaskView() {
        List<TaskView> taskViews = getTaskViews();
        if (taskViews.isEmpty()) {
            return null;
        }
        return taskViews.get(taskViews.size() - 1);
    }

    public TaskView getChildViewForTask(Task t) {
        List<TaskView> taskViews = getTaskViews();
        int taskViewCount = taskViews.size();
        for (int i = 0; i < taskViewCount; i++) {
            TaskView tv = taskViews.get(i);
            if (tv.getTask() == t) {
                return tv;
            }
        }
        return null;
    }

    public TaskStackLayoutAlgorithm getStackAlgorithm() {
        return this.mLayoutAlgorithm;
    }

    public TaskGridLayoutAlgorithm getGridAlgorithm() {
        return this.mLayoutAlgorithm.mTaskGridLayoutAlgorithm;
    }

    public TaskStackViewTouchHandler getTouchHandler() {
        return this.mTouchHandler;
    }

    void addIgnoreTask(Task task) {
        this.mIgnoreTasks.add(task.key);
    }

    void removeIgnoreTask(Task task) {
        this.mIgnoreTasks.remove(task.key);
    }

    boolean isIgnoredTask(Task task) {
        return this.mIgnoreTasks.contains(task.key);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x006c A[PHI: r8 r9
      0x006c: PHI (r8v4 'frontTransform' com.android.systemui.recents.views.TaskViewTransform) = 
      (r8v1 'frontTransform' com.android.systemui.recents.views.TaskViewTransform)
      (r8v2 'frontTransform' com.android.systemui.recents.views.TaskViewTransform)
     binds: [B:17:0x0069, B:20:0x0072] A[DONT_GENERATE, DONT_INLINE]
      0x006c: PHI (r9v4 'frontTransformAtTarget' com.android.systemui.recents.views.TaskViewTransform) = 
      (r9v1 'frontTransformAtTarget' com.android.systemui.recents.views.TaskViewTransform)
      (r9v2 'frontTransformAtTarget' com.android.systemui.recents.views.TaskViewTransform)
     binds: [B:17:0x0069, B:20:0x0072] A[DONT_GENERATE, DONT_INLINE]] */
    int[] computeVisibleTaskTransforms(ArrayList<TaskViewTransform> taskTransforms, ArrayList<Task> tasks, float curStackScroll, float targetStackScroll, ArraySet<Task.TaskKey> ignoreTasksSet, boolean ignoreTaskOverrides) {
        boolean z;
        int taskCount = tasks.size();
        int[] visibleTaskRange = this.mTmpIntPair;
        visibleTaskRange[0] = -1;
        visibleTaskRange[1] = -1;
        boolean useTargetStackScroll = Float.compare(curStackScroll, targetStackScroll) != 0;
        matchTaskListSize(tasks, taskTransforms);
        TaskViewTransform frontTransform = null;
        TaskViewTransform frontTransformAtTarget = null;
        TaskViewTransform transformAtTarget = null;
        for (int i = taskCount - 1; i >= 0; i--) {
            Task task = tasks.get(i);
            TaskViewTransform transform = this.mLayoutAlgorithm.getStackTransform(task, curStackScroll, taskTransforms.get(i), frontTransform, ignoreTaskOverrides);
            if (useTargetStackScroll && !transform.visible) {
                transformAtTarget = this.mLayoutAlgorithm.getStackTransform(task, targetStackScroll, new TaskViewTransform(), frontTransformAtTarget);
                if (transformAtTarget.visible) {
                    transform.copyFrom(transformAtTarget);
                }
            }
            if (ignoreTasksSet.contains(task.key)) {
                z = true;
            } else {
                frontTransform = transform;
                frontTransformAtTarget = transformAtTarget;
                if (!transform.visible) {
                    z = true;
                } else {
                    if (visibleTaskRange[0] < 0) {
                        visibleTaskRange[0] = i;
                    }
                    z = true;
                    visibleTaskRange[1] = i;
                }
            }
        }
        return visibleTaskRange;
    }

    void bindVisibleTaskViews(float targetStackScroll) {
        bindVisibleTaskViews(targetStackScroll, false);
    }

    void bindVisibleTaskViews(float targetStackScroll, boolean ignoreTaskOverrides) {
        int newFocusedTaskIndex;
        ArrayList<Task> tasks = this.mStack.getTasks();
        int[] visibleTaskRange = computeVisibleTaskTransforms(this.mCurrentTaskTransforms, tasks, this.mStackScroller.getStackScroll(), targetStackScroll, this.mIgnoreTasks, ignoreTaskOverrides);
        this.mTmpTaskViewMap.clear();
        List<TaskView> taskViews = getTaskViews();
        int lastFocusedTaskIndex = -1;
        int taskViewCount = taskViews.size();
        for (int i = taskViewCount - 1; i >= 0; i--) {
            TaskView tv = taskViews.get(i);
            Task task = tv.getTask();
            if (!this.mIgnoreTasks.contains(task.key)) {
                int taskIndex = this.mStack.indexOfTask(task);
                TaskViewTransform transform = null;
                if (taskIndex != -1) {
                    transform = this.mCurrentTaskTransforms.get(taskIndex);
                }
                if (transform != null && transform.visible) {
                    this.mTmpTaskViewMap.put(task.key, tv);
                } else {
                    if (this.mTouchExplorationEnabled && Utilities.isDescendentAccessibilityFocused(tv)) {
                        lastFocusedTaskIndex = taskIndex;
                        resetFocusedTask(task);
                    }
                    this.mViewPool.returnViewToPool(tv);
                }
            }
        }
        int i2 = tasks.size();
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            Task task2 = tasks.get(i3);
            TaskViewTransform transform2 = this.mCurrentTaskTransforms.get(i3);
            if (!this.mIgnoreTasks.contains(task2.key) && transform2.visible) {
                TaskView tv2 = this.mTmpTaskViewMap.get(task2.key);
                if (tv2 == null) {
                    TaskView tv3 = this.mViewPool.pickUpViewFromPool(task2, task2);
                    if (transform2.rect.top <= this.mLayoutAlgorithm.mStackRect.top) {
                        updateTaskViewToTransform(tv3, this.mLayoutAlgorithm.getBackOfStackTransform(), AnimationProps.IMMEDIATE);
                    } else {
                        updateTaskViewToTransform(tv3, this.mLayoutAlgorithm.getFrontOfStackTransform(), AnimationProps.IMMEDIATE);
                    }
                } else {
                    int insertIndex = findTaskViewInsertIndex(task2, this.mStack.indexOfTask(task2));
                    if (insertIndex != getTaskViews().indexOf(tv2)) {
                        detachViewFromParent(tv2);
                        attachViewToParent(tv2, insertIndex, tv2.getLayoutParams());
                        updateTaskViewsList();
                    }
                }
            }
        }
        updatePrefetchingTask(tasks, visibleTaskRange[0], visibleTaskRange[1]);
        if (lastFocusedTaskIndex != -1) {
            if (lastFocusedTaskIndex < visibleTaskRange[1]) {
                newFocusedTaskIndex = visibleTaskRange[1];
            } else {
                newFocusedTaskIndex = visibleTaskRange[0];
            }
            setFocusedTask(newFocusedTaskIndex, false, true);
            TaskView focusedTaskView = getChildViewForTask(this.mFocusedTask);
            if (focusedTaskView != null) {
                focusedTaskView.requestAccessibilityFocus();
            }
        }
    }

    public void relayoutTaskViews(AnimationProps animation) {
        relayoutTaskViews(animation, null, false);
    }

    private void relayoutTaskViews(AnimationProps animation, ArrayMap<Task, AnimationProps> animationOverrides, boolean ignoreTaskOverrides) {
        cancelDeferredTaskViewLayoutAnimation();
        bindVisibleTaskViews(this.mStackScroller.getStackScroll(), ignoreTaskOverrides);
        List<TaskView> taskViews = getTaskViews();
        int taskViewCount = taskViews.size();
        for (int i = 0; i < taskViewCount; i++) {
            TaskView tv = taskViews.get(i);
            Task task = tv.getTask();
            if (!this.mIgnoreTasks.contains(task.key)) {
                int taskIndex = this.mStack.indexOfTask(task);
                if (taskIndex == -1) {
                    Log.w("TaskStackView", "relayoutTaskViews() task index = -1");
                } else {
                    TaskViewTransform transform = this.mCurrentTaskTransforms.get(taskIndex);
                    if (animationOverrides != null && animationOverrides.containsKey(task)) {
                        AnimationProps animation2 = animationOverrides.get(task);
                        animation = animation2;
                    }
                    updateTaskViewToTransform(tv, transform, animation);
                }
            }
        }
        if (this.mDraggedTask != null && this.mIsDraggingToLeftDock) {
            adjustNonDraggedTasksToRight();
        }
    }

    private void adjustNonDraggedTasksToRight() {
        int taskIndex;
        List<TaskView> taskViews = getTaskViews();
        int screenWidth = this.mContext.getResources().getDisplayMetrics().widthPixels;
        for (TaskView tv : taskViews) {
            if (tv.getTask() != this.mDraggedTask && !isIgnoredTask(tv.getTask()) && (taskIndex = this.mStack.indexOfTask(tv.getTask())) != -1) {
                TaskViewTransform transform = this.mCurrentTaskTransforms.get(taskIndex);
                float width = transform.rect.width();
                transform.rect.right = screenWidth;
                transform.rect.left = screenWidth - width;
                updateTaskViewToTransform(tv, transform, AnimationProps.IMMEDIATE);
            }
        }
    }

    void relayoutTaskViewsOnNextFrame(AnimationProps animation) {
        this.mDeferredTaskViewLayoutAnimation = animation;
        invalidate();
    }

    public void updateTaskViewToTransform(TaskView taskView, TaskViewTransform transform, AnimationProps animation) {
        if (taskView.isAnimatingTo(transform)) {
            return;
        }
        taskView.cancelTransformAnimation();
        taskView.updateViewPropertiesToTaskTransform(transform, animation, this.mRequestUpdateClippingListener);
    }

    public void getCurrentTaskTransforms(ArrayList<Task> tasks, ArrayList<TaskViewTransform> transformsOut) {
        matchTaskListSize(tasks, transformsOut);
        int focusState = this.mLayoutAlgorithm.getFocusState();
        int i = tasks.size() - 1;
        while (true) {
            int i2 = i;
            if (i2 < 0) {
                return;
            }
            Task task = tasks.get(i2);
            TaskViewTransform transform = transformsOut.get(i2);
            TaskView tv = getChildViewForTask(task);
            if (tv != null) {
                transform.fillIn(tv);
            } else {
                this.mLayoutAlgorithm.getStackTransform(task, this.mStackScroller.getStackScroll(), focusState, transform, null, true, false);
            }
            transform.visible = true;
            i = i2 - 1;
        }
    }

    public void getLayoutTaskTransforms(float stackScroll, int focusState, ArrayList<Task> tasks, boolean ignoreTaskOverrides, ArrayList<TaskViewTransform> transformsOut) {
        matchTaskListSize(tasks, transformsOut);
        for (int i = tasks.size() - 1; i >= 0; i--) {
            Task task = tasks.get(i);
            TaskViewTransform transform = transformsOut.get(i);
            this.mLayoutAlgorithm.getStackTransform(task, stackScroll, focusState, transform, null, true, ignoreTaskOverrides);
            transform.visible = true;
        }
    }

    void cancelDeferredTaskViewLayoutAnimation() {
        this.mDeferredTaskViewLayoutAnimation = null;
    }

    void cancelAllTaskViewAnimations() {
        List<TaskView> taskViews = getTaskViews();
        for (int i = taskViews.size() - 1; i >= 0; i--) {
            TaskView tv = taskViews.get(i);
            if (!this.mIgnoreTasks.contains(tv.getTask().key)) {
                tv.cancelTransformAnimation();
            }
        }
    }

    private void clipTaskViews() {
        if (Recents.getConfiguration().isGridEnabled) {
            return;
        }
        List<TaskView> taskViews = getTaskViews();
        int taskViewCount = taskViews.size();
        TaskView prevVisibleTv = null;
        for (int i = 0; i < taskViewCount; i++) {
            TaskView tv = taskViews.get(i);
            TaskView frontTv = null;
            int clipBottom = 0;
            if (isIgnoredTask(tv.getTask()) && prevVisibleTv != null) {
                tv.setTranslationZ(Math.max(tv.getTranslationZ(), prevVisibleTv.getTranslationZ() + 0.1f));
            }
            if (i < taskViewCount - 1 && tv.shouldClipViewInStack()) {
                for (int j = i + 1; j < taskViewCount; j++) {
                    TaskView tmpTv = taskViews.get(j);
                    if (tmpTv.shouldClipViewInStack()) {
                        frontTv = tmpTv;
                        break;
                    }
                }
                if (frontTv != null) {
                    float taskBottom = tv.getBottom();
                    float frontTaskTop = frontTv.getTop();
                    if (frontTaskTop < taskBottom) {
                        clipBottom = ((int) (taskBottom - frontTaskTop)) - this.mTaskCornerRadiusPx;
                    }
                }
            }
            tv.getViewBounds().setClipBottom(clipBottom);
            tv.mThumbnailView.updateThumbnailVisibility(clipBottom - tv.getPaddingBottom());
            prevVisibleTv = tv;
        }
        this.mTaskViewsClipDirty = false;
    }

    public void updateLayoutAlgorithm(boolean boundScrollToNewMinMax) {
        updateLayoutAlgorithm(boundScrollToNewMinMax, Recents.getConfiguration().getLaunchState());
    }

    public void updateLayoutAlgorithm(boolean boundScrollToNewMinMax, RecentsActivityLaunchState launchState) {
        this.mLayoutAlgorithm.update(this.mStack, this.mIgnoreTasks, launchState, this.mLastScrollPPercent);
        if (boundScrollToNewMinMax) {
            this.mStackScroller.boundScroll();
        }
    }

    private void updateLayoutToStableBounds() {
        this.mWindowRect.set(this.mStableWindowRect);
        this.mStackBounds.set(this.mStableStackBounds);
        this.mLayoutAlgorithm.setSystemInsets(this.mStableLayoutAlgorithm.mSystemInsets);
        this.mLayoutAlgorithm.initialize(this.mDisplayRect, this.mWindowRect, this.mStackBounds);
        updateLayoutAlgorithm(true);
    }

    public TaskStackViewScroller getScroller() {
        return this.mStackScroller;
    }

    public boolean setFocusedTask(int taskIndex, boolean scrollToTask, boolean requestViewFocus) {
        return setFocusedTask(taskIndex, scrollToTask, requestViewFocus, 0);
    }

    public boolean setFocusedTask(int focusTaskIndex, boolean scrollToTask, boolean requestViewFocus, int timerIndicatorDuration) {
        TaskView tv;
        int newFocusedTaskIndex = this.mStack.getTaskCount() > 0 ? Utilities.clamp(focusTaskIndex, 0, this.mStack.getTaskCount() - 1) : -1;
        Task newFocusedTask = newFocusedTaskIndex != -1 ? this.mStack.getTasks().get(newFocusedTaskIndex) : null;
        if (this.mFocusedTask != null) {
            if (timerIndicatorDuration > 0 && (tv = getChildViewForTask(this.mFocusedTask)) != null) {
                tv.getHeaderView().cancelFocusTimerIndicator();
            }
            resetFocusedTask(this.mFocusedTask);
        }
        boolean willScroll = false;
        this.mFocusedTask = newFocusedTask;
        if (newFocusedTask != null) {
            if (timerIndicatorDuration > 0) {
                TaskView tv2 = getChildViewForTask(this.mFocusedTask);
                if (tv2 != null) {
                    tv2.getHeaderView().startFocusTimerIndicator(timerIndicatorDuration);
                } else {
                    this.mStartTimerIndicatorDuration = timerIndicatorDuration;
                }
            }
            if (scrollToTask) {
                if (!this.mEnterAnimationComplete) {
                    cancelAllTaskViewAnimations();
                }
                this.mLayoutAlgorithm.clearUnfocusedTaskOverrides();
                boolean willScroll2 = this.mAnimationHelper.startScrollToFocusedTaskAnimation(newFocusedTask, requestViewFocus);
                if (willScroll2) {
                    sendAccessibilityEvent(4096);
                }
                willScroll = willScroll2;
            } else {
                TaskView newFocusedTaskView = getChildViewForTask(newFocusedTask);
                if (newFocusedTaskView != null) {
                    newFocusedTaskView.setFocusedState(true, requestViewFocus);
                }
            }
            if (this.mTaskViewFocusFrame != null) {
                this.mTaskViewFocusFrame.moveGridTaskViewFocus(getChildViewForTask(newFocusedTask));
            }
        }
        return willScroll;
    }

    public void setRelativeFocusedTask(boolean forward, boolean stackTasksOnly, boolean animated) {
        setRelativeFocusedTask(forward, stackTasksOnly, animated, false, 0);
    }

    public void setRelativeFocusedTask(boolean forward, boolean stackTasksOnly, boolean animated, boolean cancelWindowAnimations, int timerIndicatorDuration) {
        Task focusedTask = getFocusedTask();
        int newIndex = this.mStack.indexOfTask(focusedTask);
        if (focusedTask != null) {
            if (stackTasksOnly) {
                List<Task> tasks = this.mStack.getTasks();
                int tmpNewIndex = (forward ? -1 : 1) + newIndex;
                if (tmpNewIndex >= 0 && tmpNewIndex < tasks.size()) {
                    newIndex = tmpNewIndex;
                }
            } else {
                int taskCount = this.mStack.getTaskCount();
                newIndex = (((forward ? -1 : 1) + newIndex) + taskCount) % taskCount;
            }
        } else {
            float stackScroll = this.mStackScroller.getStackScroll();
            ArrayList<Task> tasks2 = this.mStack.getTasks();
            int taskCount2 = tasks2.size();
            if (useGridLayout()) {
                newIndex = taskCount2 - 1;
            } else if (forward) {
                newIndex = taskCount2 - 1;
                while (newIndex >= 0) {
                    float taskP = this.mLayoutAlgorithm.getStackScrollForTask(tasks2.get(newIndex));
                    if (Float.compare(taskP, stackScroll) <= 0) {
                        break;
                    } else {
                        newIndex--;
                    }
                }
            } else {
                newIndex = 0;
                while (newIndex < taskCount2) {
                    float taskP2 = this.mLayoutAlgorithm.getStackScrollForTask(tasks2.get(newIndex));
                    if (Float.compare(taskP2, stackScroll) >= 0) {
                        break;
                    } else {
                        newIndex++;
                    }
                }
            }
        }
        if (newIndex != -1) {
            boolean willScroll = setFocusedTask(newIndex, true, true, timerIndicatorDuration);
            if (willScroll && cancelWindowAnimations) {
                EventBus.getDefault().send(new CancelEnterRecentsWindowAnimationEvent(null));
            }
        }
    }

    public void resetFocusedTask(Task task) {
        TaskView tv;
        if (task != null && (tv = getChildViewForTask(task)) != null) {
            tv.setFocusedState(false, false);
        }
        if (this.mTaskViewFocusFrame != null) {
            this.mTaskViewFocusFrame.moveGridTaskViewFocus(null);
        }
        this.mFocusedTask = null;
    }

    public Task getFocusedTask() {
        return this.mFocusedTask;
    }

    Task getAccessibilityFocusedTask() {
        List<TaskView> taskViews = getTaskViews();
        int taskViewCount = taskViews.size();
        for (int i = 0; i < taskViewCount; i++) {
            TaskView tv = taskViews.get(i);
            if (Utilities.isDescendentAccessibilityFocused(tv)) {
                return tv.getTask();
            }
        }
        TaskView frontTv = getFrontMostTaskView();
        if (frontTv != null) {
            return frontTv.getTask();
        }
        return null;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        List<TaskView> taskViews = getTaskViews();
        int taskViewCount = taskViews.size();
        if (taskViewCount > 0) {
            TaskView backMostTask = taskViews.get(0);
            TaskView frontMostTask = taskViews.get(taskViewCount - 1);
            event.setFromIndex(this.mStack.indexOfTask(backMostTask.getTask()));
            event.setToIndex(this.mStack.indexOfTask(frontMostTask.getTask()));
            event.setContentDescription(frontMostTask.getTask().title);
        }
        event.setItemCount(this.mStack.getTaskCount());
        int stackHeight = this.mLayoutAlgorithm.mStackRect.height();
        event.setScrollY((int) (this.mStackScroller.getStackScroll() * stackHeight));
        event.setMaxScrollY((int) (this.mLayoutAlgorithm.mMaxScrollP * stackHeight));
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        List<TaskView> taskViews = getTaskViews();
        int taskViewCount = taskViews.size();
        if (taskViewCount > 1) {
            Task focusedTask = getAccessibilityFocusedTask();
            info.setScrollable(true);
            int focusedTaskIndex = this.mStack.indexOfTask(focusedTask);
            if (focusedTaskIndex > 0 || !this.mStackActionButtonVisible) {
                info.addAction(8192);
            }
            if (focusedTaskIndex >= 0 && focusedTaskIndex < this.mStack.getTaskCount() - 1) {
                info.addAction(4096);
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return ScrollView.class.getName();
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int action, Bundle arguments) {
        if (super.performAccessibilityAction(action, arguments)) {
            return true;
        }
        Task focusedTask = getAccessibilityFocusedTask();
        int taskIndex = this.mStack.indexOfTask(focusedTask);
        if (taskIndex >= 0 && taskIndex < this.mStack.getTaskCount()) {
            if (action == 4096) {
                setFocusedTask(taskIndex + 1, true, true, 0);
                return true;
            }
            if (action == 8192) {
                setFocusedTask(taskIndex - 1, true, true, 0);
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.mTouchHandler.onInterceptTouchEvent(ev);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        return this.mTouchHandler.onTouchEvent(ev);
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent ev) {
        return this.mTouchHandler.onGenericMotionEvent(ev);
    }

    @Override // android.view.View
    public void computeScroll() {
        boolean z;
        if (this.mStackScroller.computeScroll()) {
            sendAccessibilityEvent(4096);
            HighResThumbnailLoader highResThumbnailLoader = Recents.getTaskLoader().getHighResThumbnailLoader();
            if (this.mStackScroller.getScrollVelocity() > this.mFastFlingVelocity) {
                z = true;
            } else {
                z = false;
            }
            highResThumbnailLoader.setFlingingFast(z);
        }
        if (this.mDeferredTaskViewLayoutAnimation != null) {
            relayoutTaskViews(this.mDeferredTaskViewLayoutAnimation);
            this.mTaskViewsClipDirty = true;
            this.mDeferredTaskViewLayoutAnimation = null;
        }
        if (this.mTaskViewsClipDirty) {
            clipTaskViews();
        }
        this.mLastScrollPPercent = Utilities.clamp(Utilities.unmapRange(this.mStackScroller.getStackScroll(), this.mLayoutAlgorithm.mMinScrollP, this.mLayoutAlgorithm.mMaxScrollP), 0.0f, 1.0f);
    }

    public TaskStackLayoutAlgorithm.VisibilityReport computeStackVisibilityReport() {
        return this.mLayoutAlgorithm.computeStackVisibilityReport(this.mStack.getTasks());
    }

    public void setSystemInsets(Rect systemInsets) {
        boolean changed = false | this.mStableLayoutAlgorithm.setSystemInsets(systemInsets);
        if (changed | this.mLayoutAlgorithm.setSystemInsets(systemInsets)) {
            requestLayout();
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mInMeasureLayout = true;
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        this.mLayoutAlgorithm.getTaskStackBounds(this.mDisplayRect, new Rect(0, 0, width, height), this.mLayoutAlgorithm.mSystemInsets.top, this.mLayoutAlgorithm.mSystemInsets.left, this.mLayoutAlgorithm.mSystemInsets.right, this.mTmpRect);
        if (!this.mTmpRect.equals(this.mStableStackBounds)) {
            this.mStableStackBounds.set(this.mTmpRect);
            this.mStackBounds.set(this.mTmpRect);
            this.mStableWindowRect.set(0, 0, width, height);
            this.mWindowRect.set(0, 0, width, height);
        }
        this.mStableLayoutAlgorithm.initialize(this.mDisplayRect, this.mStableWindowRect, this.mStableStackBounds);
        this.mLayoutAlgorithm.initialize(this.mDisplayRect, this.mWindowRect, this.mStackBounds);
        updateLayoutAlgorithm(false);
        boolean resetToInitialState = !(width == this.mLastWidth && height == this.mLastHeight) && this.mResetToInitialStateWhenResized;
        if (!this.mFinishedLayoutAfterStackReload || this.mInitialState != 0 || resetToInitialState) {
            if (this.mInitialState != 2 || resetToInitialState) {
                updateToInitialState();
                this.mResetToInitialStateWhenResized = false;
            }
            if (this.mFinishedLayoutAfterStackReload) {
                this.mInitialState = 0;
            }
        }
        if (this.mLaunchNextAfterFirstMeasure) {
            this.mLaunchNextAfterFirstMeasure = false;
            EventBus.getDefault().post(new LaunchNextTaskRequestEvent());
        }
        bindVisibleTaskViews(this.mStackScroller.getStackScroll(), false);
        this.mTmpTaskViews.clear();
        this.mTmpTaskViews.addAll(getTaskViews());
        this.mTmpTaskViews.addAll(this.mViewPool.getViews());
        int taskViewCount = this.mTmpTaskViews.size();
        for (int i = 0; i < taskViewCount; i++) {
            measureTaskView(this.mTmpTaskViews.get(i));
        }
        if (this.mTaskViewFocusFrame != null) {
            this.mTaskViewFocusFrame.measure();
        }
        setMeasuredDimension(width, height);
        this.mLastWidth = width;
        this.mLastHeight = height;
        this.mInMeasureLayout = false;
    }

    private void measureTaskView(TaskView tv) {
        Rect padding = new Rect();
        if (tv.getBackground() != null) {
            tv.getBackground().getPadding(padding);
        }
        this.mTmpRect.set(this.mStableLayoutAlgorithm.getTaskRect());
        this.mTmpRect.union(this.mLayoutAlgorithm.getTaskRect());
        tv.measure(View.MeasureSpec.makeMeasureSpec(this.mTmpRect.width() + padding.left + padding.right, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mTmpRect.height() + padding.top + padding.bottom, 1073741824));
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.mTmpTaskViews.clear();
        this.mTmpTaskViews.addAll(getTaskViews());
        this.mTmpTaskViews.addAll(this.mViewPool.getViews());
        int taskViewCount = this.mTmpTaskViews.size();
        for (int i = 0; i < taskViewCount; i++) {
            layoutTaskView(changed, this.mTmpTaskViews.get(i));
        }
        if (this.mTaskViewFocusFrame != null) {
            this.mTaskViewFocusFrame.layout();
        }
        if (changed && this.mStackScroller.isScrollOutOfBounds()) {
            this.mStackScroller.boundScroll();
        }
        relayoutTaskViews(AnimationProps.IMMEDIATE);
        clipTaskViews();
        if (!this.mFinishedLayoutAfterStackReload) {
            this.mInitialState = 0;
            onFirstLayout();
            if (this.mStackReloaded) {
                this.mFinishedLayoutAfterStackReload = true;
                tryStartEnterAnimation();
            }
        }
    }

    private void layoutTaskView(boolean changed, TaskView tv) {
        if (changed) {
            Rect padding = new Rect();
            if (tv.getBackground() != null) {
                tv.getBackground().getPadding(padding);
            }
            this.mTmpRect.set(this.mStableLayoutAlgorithm.getTaskRect());
            this.mTmpRect.union(this.mLayoutAlgorithm.getTaskRect());
            tv.cancelTransformAnimation();
            tv.layout(this.mTmpRect.left - padding.left, this.mTmpRect.top - padding.top, this.mTmpRect.right + padding.right, this.mTmpRect.bottom + padding.bottom);
            return;
        }
        tv.layout(tv.getLeft(), tv.getTop(), tv.getRight(), tv.getBottom());
    }

    void onFirstLayout() {
        int focusedTaskIndex;
        this.mAnimationHelper.prepareForEnterAnimation();
        RecentsConfiguration config = Recents.getConfiguration();
        RecentsActivityLaunchState launchState = config.getLaunchState();
        boolean setFocusOnFirstLayout = !useGridLayout() || launchState.launchedWithAltTab;
        if (setFocusOnFirstLayout && (focusedTaskIndex = getInitialFocusTaskIndex(launchState, this.mStack.getTaskCount(), useGridLayout())) != -1) {
            setFocusedTask(focusedTaskIndex, false, false);
        }
        updateStackActionButtonVisibility();
    }

    public boolean isTouchPointInView(float x, float y, TaskView tv) {
        this.mTmpRect.set(tv.getLeft(), tv.getTop(), tv.getRight(), tv.getBottom());
        this.mTmpRect.offset((int) tv.getTranslationX(), (int) tv.getTranslationY());
        return this.mTmpRect.contains((int) x, (int) y);
    }

    public Task findAnchorTask(List<Task> tasks, MutableBoolean isFrontMostTask) {
        for (int i = tasks.size() - 1; i >= 0; i--) {
            Task task = tasks.get(i);
            if (isIgnoredTask(task)) {
                if (i == tasks.size() - 1) {
                    isFrontMostTask.value = true;
                }
            } else {
                return task;
            }
        }
        return null;
    }

    @Override // com.android.systemui.shared.recents.model.TaskStack.TaskStackCallbacks
    public void onStackTaskAdded(TaskStack stack, Task newTask) {
        AnimationProps animationProps;
        updateLayoutAlgorithm(true);
        if (!this.mFinishedLayoutAfterStackReload) {
            animationProps = AnimationProps.IMMEDIATE;
        } else {
            animationProps = new AnimationProps(200, Interpolators.FAST_OUT_SLOW_IN);
        }
        relayoutTaskViews(animationProps);
    }

    @Override // com.android.systemui.shared.recents.model.TaskStack.TaskStackCallbacks
    public void onStackTaskRemoved(TaskStack stack, Task removedTask, Task newFrontMostTask, AnimationProps animation, boolean fromDockGesture, boolean dismissRecentsIfAllRemoved) {
        int i;
        TaskView frontTv;
        if (this.mFocusedTask == removedTask) {
            resetFocusedTask(removedTask);
        }
        TaskView tv = getChildViewForTask(removedTask);
        if (tv != null) {
            this.mViewPool.returnViewToPool(tv);
        }
        removeIgnoreTask(removedTask);
        if (animation != null) {
            updateLayoutAlgorithm(true);
            relayoutTaskViews(animation);
        }
        if (this.mScreenPinningEnabled && newFrontMostTask != null && (frontTv = getChildViewForTask(newFrontMostTask)) != null) {
            frontTv.showActionButton(true, 200);
        }
        if (this.mStack.getTaskCount() == 0) {
            if (dismissRecentsIfAllRemoved) {
                EventBus eventBus = EventBus.getDefault();
                if (fromDockGesture) {
                    i = R.string.recents_empty_message;
                } else {
                    i = R.string.recents_empty_message_dismissed_all;
                }
                eventBus.send(new AllTaskViewsDismissedEvent(i));
                return;
            }
            EventBus.getDefault().send(new ShowEmptyViewEvent());
        }
    }

    @Override // com.android.systemui.shared.recents.model.TaskStack.TaskStackCallbacks
    public void onStackTasksRemoved(TaskStack stack) {
        resetFocusedTask(getFocusedTask());
        List<TaskView> taskViews = new ArrayList<>();
        taskViews.addAll(getTaskViews());
        for (int i = taskViews.size() - 1; i >= 0; i--) {
            this.mViewPool.returnViewToPool(taskViews.get(i));
        }
        this.mIgnoreTasks.clear();
        EventBus.getDefault().send(new AllTaskViewsDismissedEvent(R.string.recents_empty_message_dismissed_all));
    }

    @Override // com.android.systemui.shared.recents.model.TaskStack.TaskStackCallbacks
    public void onStackTasksUpdated(TaskStack stack) {
        if (!this.mFinishedLayoutAfterStackReload) {
            return;
        }
        updateLayoutAlgorithm(false);
        relayoutTaskViews(AnimationProps.IMMEDIATE);
        List<TaskView> taskViews = getTaskViews();
        int taskViewCount = taskViews.size();
        for (int i = 0; i < taskViewCount; i++) {
            TaskView tv = taskViews.get(i);
            bindTaskView(tv, tv.getTask());
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.recents.views.ViewPool.ViewPoolConsumer
    public TaskView createView(Context context) {
        if (Recents.getConfiguration().isGridEnabled) {
            return (GridTaskView) this.mInflater.inflate(R.layout.recents_grid_task_view, (ViewGroup) this, false);
        }
        return (TaskView) this.mInflater.inflate(R.layout.recents_task_view, (ViewGroup) this, false);
    }

    @Override // com.android.systemui.recents.views.ViewPool.ViewPoolConsumer
    public void onReturnViewToPool(TaskView tv) {
        Task task = tv.getTask();
        unbindTaskView(tv, task);
        tv.clearAccessibilityFocus();
        tv.resetViewProperties();
        tv.setFocusedState(false, false);
        tv.setClipViewInStack(false);
        if (this.mScreenPinningEnabled) {
            tv.hideActionButton(false, 0, false, null);
        }
        detachViewFromParent(tv);
        updateTaskViewsList();
    }

    @Override // com.android.systemui.recents.views.ViewPool.ViewPoolConsumer
    public void onPickUpViewFromPool(TaskView tv, Task task, boolean isNewView) {
        int taskIndex = this.mStack.indexOfTask(task);
        int insertIndex = findTaskViewInsertIndex(task, taskIndex);
        if (isNewView) {
            if (this.mInMeasureLayout) {
                addView(tv, insertIndex);
            } else {
                ViewGroup.LayoutParams params = tv.getLayoutParams();
                if (params == null) {
                    params = generateDefaultLayoutParams();
                }
                addViewInLayout(tv, insertIndex, params, true);
                measureTaskView(tv);
                layoutTaskView(true, tv);
            }
        } else {
            attachViewToParent(tv, insertIndex, tv.getLayoutParams());
        }
        updateTaskViewsList();
        bindTaskView(tv, task);
        tv.setCallbacks(this);
        tv.setTouchEnabled(true);
        tv.setClipViewInStack(true);
        if (this.mFocusedTask == task) {
            tv.setFocusedState(true, false);
            if (this.mStartTimerIndicatorDuration > 0) {
                tv.getHeaderView().startFocusTimerIndicator(this.mStartTimerIndicatorDuration);
                this.mStartTimerIndicatorDuration = 0;
            }
        }
        if (this.mScreenPinningEnabled && tv.getTask() == this.mStack.getFrontMostTask()) {
            tv.showActionButton(false, 0);
        }
    }

    @Override // com.android.systemui.recents.views.ViewPool.ViewPoolConsumer
    public boolean hasPreferredData(TaskView tv, Task preferredData) {
        return tv.getTask() == preferredData;
    }

    private void bindTaskView(TaskView tv, Task task) {
        tv.onTaskBound(task, this.mTouchExplorationEnabled, this.mDisplayOrientation, this.mDisplayRect);
        if (this.mUIDozeTrigger.isAsleep() || useGridLayout() || Recents.getConfiguration().isLowRamDevice) {
            tv.setNoUserInteractionState();
        }
        if (task == this.mPrefetchingTask) {
            task.notifyTaskDataLoaded(task.thumbnail, task.icon);
        } else {
            Recents.getTaskLoader().loadTaskData(task);
        }
        Recents.getTaskLoader().getHighResThumbnailLoader().onTaskVisible(task);
    }

    private void unbindTaskView(TaskView tv, Task task) {
        if (task != this.mPrefetchingTask) {
            Recents.getTaskLoader().unloadTaskData(task);
        }
        Recents.getTaskLoader().getHighResThumbnailLoader().onTaskInvisible(task);
    }

    private void updatePrefetchingTask(ArrayList<Task> tasks, int frontIndex, int backIndex) {
        int index;
        Task t = null;
        boolean somethingVisible = (frontIndex == -1 || backIndex == -1) ? false : true;
        if (somethingVisible && frontIndex < tasks.size() - 1) {
            t = tasks.get(frontIndex + 1);
        }
        if (this.mPrefetchingTask != t) {
            if (this.mPrefetchingTask != null && ((index = tasks.indexOf(this.mPrefetchingTask)) < backIndex || index > frontIndex)) {
                Recents.getTaskLoader().unloadTaskData(this.mPrefetchingTask);
            }
            this.mPrefetchingTask = t;
            if (t != null) {
                Recents.getTaskLoader().loadTaskData(t);
            }
        }
    }

    private void clearPrefetchingTask() {
        if (this.mPrefetchingTask != null) {
            Recents.getTaskLoader().unloadTaskData(this.mPrefetchingTask);
        }
        this.mPrefetchingTask = null;
    }

    @Override // com.android.systemui.recents.views.TaskView.TaskViewCallbacks
    public void onTaskViewClipStateChanged(TaskView tv) {
        if (!this.mTaskViewsClipDirty) {
            this.mTaskViewsClipDirty = true;
            invalidate();
        }
    }

    @Override // com.android.systemui.recents.views.TaskStackLayoutAlgorithm.TaskStackLayoutAlgorithmCallbacks
    public void onFocusStateChanged(int prevFocusState, int curFocusState) {
        if (this.mDeferredTaskViewLayoutAnimation == null) {
            this.mUIDozeTrigger.poke();
            relayoutTaskViewsOnNextFrame(AnimationProps.IMMEDIATE);
        }
    }

    @Override // com.android.systemui.recents.views.TaskStackViewScroller.TaskStackViewScrollerCallbacks
    public void onStackScrollChanged(float prevScroll, float curScroll, AnimationProps animation) {
        this.mUIDozeTrigger.poke();
        if (animation != null) {
            relayoutTaskViewsOnNextFrame(animation);
        }
        if (this.mEnterAnimationComplete || this.mStack.getTaskCount() > 0) {
            EventBus.getDefault().send(new ShowStackActionButtonEvent(true));
        }
    }

    public final void onBusEvent(PackagesChangedEvent event) {
        ArraySet<ComponentName> removedComponents = this.mStack.computeComponentsRemoved(event.packageName, event.userId);
        ArrayList<Task> tasks = this.mStack.getTasks();
        for (int i = tasks.size() - 1; i >= 0; i--) {
            Task t = tasks.get(i);
            if (removedComponents.contains(t.key.getComponent())) {
                TaskView tv = getChildViewForTask(t);
                if (tv != null) {
                    tv.dismissTask();
                } else {
                    this.mStack.removeTask(t, AnimationProps.IMMEDIATE, false);
                }
            }
        }
    }

    public final void onBusEvent(LaunchTaskEvent event) {
        this.mUIDozeTrigger.stopDozing();
    }

    public final void onBusEvent(LaunchMostRecentTaskRequestEvent event) {
        if (this.mStack.getTaskCount() > 0) {
            Task mostRecentTask = this.mStack.getFrontMostTask();
            launchTask(mostRecentTask);
        }
    }

    public final void onBusEvent(ShowStackActionButtonEvent event) {
        this.mStackActionButtonVisible = true;
    }

    public final void onBusEvent(HideStackActionButtonEvent event) {
        this.mStackActionButtonVisible = false;
    }

    public final void onBusEvent(LaunchNextTaskRequestEvent event) {
        if (!this.mFinishedLayoutAfterStackReload) {
            this.mLaunchNextAfterFirstMeasure = true;
            return;
        }
        if (this.mStack.getTaskCount() == 0) {
            if (RecentsImpl.getLastPipTime() != -1) {
                EventBus.getDefault().send(new ExpandPipEvent());
                MetricsLogger.action(getContext(), 318, "pip");
                return;
            } else {
                EventBus.getDefault().send(new HideRecentsEvent(false, true));
                return;
            }
        }
        if (!Recents.getConfiguration().getLaunchState().launchedFromPipApp && this.mStack.isNextLaunchTargetPip(RecentsImpl.getLastPipTime())) {
            EventBus.getDefault().send(new ExpandPipEvent());
            MetricsLogger.action(getContext(), 318, "pip");
            return;
        }
        final Task launchTask = this.mStack.getNextLaunchTarget();
        if (launchTask != null) {
            HidePipMenuEvent hideMenuEvent = new HidePipMenuEvent();
            hideMenuEvent.addPostAnimationCallback(new Runnable() { // from class: com.android.systemui.recents.views.-$$Lambda$TaskStackView$eeuGItB18dVOcE3IB2KYHvY1WRM
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.launchTask(launchTask);
                }
            });
            EventBus.getDefault().send(hideMenuEvent);
            MetricsLogger.action(getContext(), 318, launchTask.key.getComponent().toString());
        }
    }

    public final void onBusEvent(LaunchTaskStartedEvent event) {
        this.mAnimationHelper.startLaunchTaskAnimation(event.taskView, event.screenPinningRequested, event.getAnimationTrigger());
    }

    public final void onBusEvent(DismissRecentsToHomeAnimationStarted event) {
        this.mTouchHandler.cancelNonDismissTaskAnimations();
        this.mStackScroller.stopScroller();
        this.mStackScroller.stopBoundScrollAnimation();
        cancelDeferredTaskViewLayoutAnimation();
        this.mAnimationHelper.startExitToHomeAnimation(event.animated, event.getAnimationTrigger());
        if (this.mTaskViewFocusFrame != null) {
            this.mTaskViewFocusFrame.moveGridTaskViewFocus(null);
        }
    }

    public final void onBusEvent(DismissFocusedTaskViewEvent event) {
        if (this.mFocusedTask != null) {
            if (this.mTaskViewFocusFrame != null) {
                this.mTaskViewFocusFrame.moveGridTaskViewFocus(null);
            }
            TaskView tv = getChildViewForTask(this.mFocusedTask);
            if (tv != null) {
                tv.dismissTask();
            }
            resetFocusedTask(this.mFocusedTask);
        }
    }

    public final void onBusEvent(DismissTaskViewEvent event) {
        this.mAnimationHelper.startDeleteTaskAnimation(event.taskView, useGridLayout(), event.getAnimationTrigger());
    }

    public final void onBusEvent(DismissAllTaskViewsEvent event) {
        final ArrayList<Task> tasks = new ArrayList<>(this.mStack.getTasks());
        this.mAnimationHelper.startDeleteAllTasksAnimation(getTaskViews(), useGridLayout(), event.getAnimationTrigger());
        event.addPostAnimationCallback(new Runnable() { // from class: com.android.systemui.recents.views.TaskStackView.4
            @Override // java.lang.Runnable
            public void run() {
                TaskStackView.this.announceForAccessibility(TaskStackView.this.getContext().getString(R.string.accessibility_recents_all_items_dismissed));
                TaskStackView.this.mStack.removeAllTasks(true);
                for (int i = tasks.size() - 1; i >= 0; i--) {
                    EventBus.getDefault().send(new DeleteTaskDataEvent((Task) tasks.get(i)));
                }
                MetricsLogger.action(TaskStackView.this.getContext(), 357);
            }
        });
    }

    public final void onBusEvent(TaskViewDismissedEvent event) {
        announceForAccessibility(getContext().getString(R.string.accessibility_recents_item_dismissed, event.task.title));
        if (useGridLayout() && event.animation != null) {
            event.animation.setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.recents.views.TaskStackView.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (TaskStackView.this.mTaskViewFocusFrame != null) {
                        TaskStackView.this.mTaskViewFocusFrame.resize();
                    }
                }
            });
        }
        this.mStack.removeTask(event.task, event.animation, false);
        EventBus.getDefault().send(new DeleteTaskDataEvent(event.task));
        if (this.mStack.getTaskCount() > 0 && Recents.getConfiguration().isLowRamDevice) {
            EventBus.getDefault().send(new ShowStackActionButtonEvent(false));
        }
        MetricsLogger.action(getContext(), 289, event.task.key.getComponent().toString());
    }

    public final void onBusEvent(FocusNextTaskViewEvent event) {
        this.mStackScroller.stopScroller();
        this.mStackScroller.stopBoundScrollAnimation();
        setRelativeFocusedTask(true, false, true, false, 0);
    }

    public final void onBusEvent(FocusPreviousTaskViewEvent event) {
        this.mStackScroller.stopScroller();
        this.mStackScroller.stopBoundScrollAnimation();
        setRelativeFocusedTask(false, false, true);
    }

    public final void onBusEvent(NavigateTaskViewEvent event) {
        if (useGridLayout()) {
            int taskCount = this.mStack.getTaskCount();
            int currentIndex = this.mStack.indexOfTask(getFocusedTask());
            int nextIndex = this.mLayoutAlgorithm.mTaskGridLayoutAlgorithm.navigateFocus(taskCount, currentIndex, event.direction);
            setFocusedTask(nextIndex, false, true);
            return;
        }
        switch (event.direction) {
            case UP:
                EventBus.getDefault().send(new FocusPreviousTaskViewEvent());
                break;
            case DOWN:
                EventBus.getDefault().send(new FocusNextTaskViewEvent());
                break;
        }
    }

    public final void onBusEvent(UserInteractionEvent event) {
        TaskView tv;
        this.mUIDozeTrigger.poke();
        Recents.getDebugFlags();
        if (this.mFocusedTask != null && (tv = getChildViewForTask(this.mFocusedTask)) != null) {
            tv.getHeaderView().cancelFocusTimerIndicator();
        }
    }

    public final void onBusEvent(DragStartEvent event) {
        this.mDraggedTask = event.task;
        this.mIsDraggingToLeftDock = true;
        addIgnoreTask(event.task);
        updateLayoutAlgorithm(true);
        relayoutTaskViews(new AnimationProps(175, Interpolators.FAST_OUT_SLOW_IN));
    }

    public final void onBusEvent(DragDropTargetChangedEvent event) {
        AnimationProps animation = new AnimationProps(250, Interpolators.FAST_OUT_SLOW_IN);
        boolean ignoreTaskOverrides = false;
        if (event.dropTarget instanceof DockState) {
            DockState dock = (DockState) event.dropTarget;
            this.mIsDraggingToLeftDock = dock.createMode == 0;
            DockState dockState = (DockState) event.dropTarget;
            Rect systemInsets = new Rect(this.mStableLayoutAlgorithm.mSystemInsets);
            int height = getMeasuredHeight();
            int height2 = height - systemInsets.bottom;
            systemInsets.bottom = 0;
            this.mStackBounds.set(dockState.getDockedTaskStackBounds(this.mDisplayRect, getMeasuredWidth(), height2, this.mDividerSize, systemInsets, this.mLayoutAlgorithm, getResources(), this.mWindowRect));
            this.mLayoutAlgorithm.setSystemInsets(systemInsets);
            this.mLayoutAlgorithm.initialize(this.mDisplayRect, this.mWindowRect, this.mStackBounds);
            updateLayoutAlgorithm(true);
            ignoreTaskOverrides = true;
        } else {
            removeIgnoreTask(event.task);
            updateLayoutToStableBounds();
            addIgnoreTask(event.task);
        }
        relayoutTaskViews(animation, null, ignoreTaskOverrides);
    }

    public final void onBusEvent(DragEndEvent event) {
        this.mDraggedTask = null;
        this.mIsDraggingToLeftDock = false;
        if (event.dropTarget instanceof DockState) {
            this.mLayoutAlgorithm.clearUnfocusedTaskOverrides();
            return;
        }
        removeIgnoreTask(event.task);
        Utilities.setViewFrameFromTranslation(event.taskView);
        ArrayMap<Task, AnimationProps> animationOverrides = new ArrayMap<>();
        animationOverrides.put(event.task, new AnimationProps(250, Interpolators.FAST_OUT_SLOW_IN, event.getAnimationTrigger().decrementOnAnimationEnd()));
        relayoutTaskViews(new AnimationProps(250, Interpolators.FAST_OUT_SLOW_IN));
        event.getAnimationTrigger().increment();
    }

    public final void onBusEvent(DragEndCancelledEvent event) {
        removeIgnoreTask(event.task);
        updateLayoutToStableBounds();
        Utilities.setViewFrameFromTranslation(event.taskView);
        ArrayMap<Task, AnimationProps> animationOverrides = new ArrayMap<>();
        animationOverrides.put(event.task, new AnimationProps(250, Interpolators.FAST_OUT_SLOW_IN, event.getAnimationTrigger().decrementOnAnimationEnd()));
        relayoutTaskViews(new AnimationProps(250, Interpolators.FAST_OUT_SLOW_IN));
        event.getAnimationTrigger().increment();
    }

    public final void onBusEvent(EnterRecentsWindowAnimationCompletedEvent event) {
        this.mEnterAnimationComplete = true;
        tryStartEnterAnimation();
    }

    private void tryStartEnterAnimation() {
        if (!this.mStackReloaded || !this.mFinishedLayoutAfterStackReload || !this.mEnterAnimationComplete) {
            return;
        }
        if (this.mStack.getTaskCount() > 0) {
            ReferenceCountedTrigger trigger = new ReferenceCountedTrigger();
            this.mAnimationHelper.startEnterAnimation(trigger);
            trigger.addLastDecrementRunnable(new Runnable() { // from class: com.android.systemui.recents.views.-$$Lambda$TaskStackView$W6l3huwmJgGI98GCJCCawhoJNm0
                @Override // java.lang.Runnable
                public final void run() {
                    TaskStackView.lambda$tryStartEnterAnimation$1(this.f$0);
                }
            });
        }
        this.mStackReloaded = false;
    }

    public static /* synthetic */ void lambda$tryStartEnterAnimation$1(TaskStackView taskStackView) {
        taskStackView.mUIDozeTrigger.startDozing();
        if (taskStackView.mFocusedTask != null) {
            RecentsConfiguration config = Recents.getConfiguration();
            RecentsActivityLaunchState launchState = config.getLaunchState();
            taskStackView.setFocusedTask(taskStackView.mStack.indexOfTask(taskStackView.mFocusedTask), false, launchState.launchedWithAltTab);
            TaskView focusedTaskView = taskStackView.getChildViewForTask(taskStackView.mFocusedTask);
            if (taskStackView.mTouchExplorationEnabled && focusedTaskView != null) {
                focusedTaskView.requestAccessibilityFocus();
            }
        }
    }

    public final void onBusEvent(final MultiWindowStateChangedEvent event) {
        if (event.inMultiWindow || !event.showDeferredAnimation) {
            setTasks(event.stack, true);
            return;
        }
        RecentsActivityLaunchState launchState = Recents.getConfiguration().getLaunchState();
        launchState.reset();
        event.getAnimationTrigger().increment();
        post(new Runnable() { // from class: com.android.systemui.recents.views.TaskStackView.6
            @Override // java.lang.Runnable
            public void run() {
                TaskStackView.this.mAnimationHelper.startNewStackScrollAnimation(event.stack, event.getAnimationTrigger());
                event.getAnimationTrigger().decrement();
            }
        });
    }

    public final void onBusEvent(ConfigurationChangedEvent event) {
        if (event.fromDeviceOrientationChange) {
            this.mDisplayOrientation = Utilities.getAppConfiguration(this.mContext).orientation;
            this.mDisplayRect = Recents.getSystemServices().getDisplayRect();
            this.mStackScroller.stopScroller();
        }
        reloadOnConfigurationChange();
        if (!event.fromMultiWindow) {
            this.mTmpTaskViews.clear();
            this.mTmpTaskViews.addAll(getTaskViews());
            this.mTmpTaskViews.addAll(this.mViewPool.getViews());
            int taskViewCount = this.mTmpTaskViews.size();
            for (int i = 0; i < taskViewCount; i++) {
                this.mTmpTaskViews.get(i).onConfigurationChanged();
            }
        }
        updateStackActionButtonVisibility();
        if (event.fromMultiWindow && this.mInitialState == 0) {
            this.mInitialState = 2;
            requestLayout();
        } else if (event.fromDeviceOrientationChange) {
            this.mInitialState = 1;
            requestLayout();
        }
    }

    public final void onBusEvent(RecentsGrowingEvent event) {
        this.mResetToInitialStateWhenResized = true;
    }

    public final void onBusEvent(RecentsVisibilityChangedEvent event) {
        if (!event.visible) {
            if (this.mTaskViewFocusFrame != null) {
                this.mTaskViewFocusFrame.moveGridTaskViewFocus(null);
            }
            List<TaskView> taskViews = new ArrayList<>(getTaskViews());
            for (int i = 0; i < taskViews.size(); i++) {
                this.mViewPool.returnViewToPool(taskViews.get(i));
            }
            clearPrefetchingTask();
            this.mEnterAnimationComplete = false;
        }
    }

    public final void onBusEvent(ActivityPinnedEvent event) {
        Task removeTask = this.mStack.findTaskWithId(event.taskId);
        if (removeTask != null) {
            this.mStack.removeTask(removeTask, AnimationProps.IMMEDIATE, false, false);
        }
        updateLayoutAlgorithm(false);
        updateToInitialState();
    }

    public void reloadOnConfigurationChange() {
        this.mStableLayoutAlgorithm.reloadOnConfigurationChange(getContext());
        this.mLayoutAlgorithm.reloadOnConfigurationChange(getContext());
    }

    private int findTaskViewInsertIndex(Task task, int taskIndex) {
        if (taskIndex != -1) {
            List<TaskView> taskViews = getTaskViews();
            boolean foundTaskView = false;
            int taskViewCount = taskViews.size();
            for (int i = 0; i < taskViewCount; i++) {
                Task tvTask = taskViews.get(i).getTask();
                if (tvTask == task) {
                    foundTaskView = true;
                } else if (taskIndex < this.mStack.indexOfTask(tvTask)) {
                    if (foundTaskView) {
                        return i - 1;
                    }
                    return i;
                }
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchTask(final Task task) {
        cancelAllTaskViewAnimations();
        float curScroll = this.mStackScroller.getStackScroll();
        float targetScroll = this.mLayoutAlgorithm.getStackScrollForTaskAtInitialOffset(task);
        float absScrollDiff = Math.abs(targetScroll - curScroll);
        if (getChildViewForTask(task) == null || absScrollDiff > 0.35f) {
            int duration = (int) (216.0f + (32.0f * absScrollDiff));
            this.mStackScroller.animateScroll(targetScroll, duration, new Runnable() { // from class: com.android.systemui.recents.views.TaskStackView.7
                @Override // java.lang.Runnable
                public void run() {
                    EventBus.getDefault().send(new LaunchTaskEvent(TaskStackView.this.getChildViewForTask(task), task, null, false));
                }
            });
        } else {
            EventBus.getDefault().send(new LaunchTaskEvent(getChildViewForTask(task), task, null, false));
        }
    }

    public boolean useGridLayout() {
        return this.mLayoutAlgorithm.useGridLayout();
    }

    private void readSystemFlags() {
        SystemServicesProxy ssp = Recents.getSystemServices();
        this.mTouchExplorationEnabled = ssp.isTouchExplorationEnabled();
        this.mScreenPinningEnabled = ActivityManagerWrapper.getInstance().isScreenPinningEnabled() && !ActivityManagerWrapper.getInstance().isLockToAppActive();
    }

    private void updateStackActionButtonVisibility() {
        if (Recents.getConfiguration().isLowRamDevice) {
            return;
        }
        if (useGridLayout() || this.mStack.getTaskCount() > 0) {
            EventBus.getDefault().send(new ShowStackActionButtonEvent(false));
        } else {
            EventBus.getDefault().send(new HideStackActionButtonEvent());
        }
    }

    private int getInitialFocusTaskIndex(RecentsActivityLaunchState launchState, int numTasks, boolean useGridLayout) {
        if (launchState.launchedFromApp) {
            if (useGridLayout) {
                return numTasks - 1;
            }
            return Math.max(0, numTasks - 2);
        }
        return numTasks - 1;
    }

    private void matchTaskListSize(List<Task> tasks, List<TaskViewTransform> transforms) {
        int taskTransformCount = transforms.size();
        int taskCount = tasks.size();
        if (taskTransformCount < taskCount) {
            for (int i = taskTransformCount; i < taskCount; i++) {
                transforms.add(new TaskViewTransform());
            }
            return;
        }
        if (taskTransformCount > taskCount) {
            transforms.subList(taskCount, taskTransformCount).clear();
        }
    }

    public void dump(String prefix, PrintWriter writer) {
        String innerPrefix = prefix + "  ";
        String id = Integer.toHexString(System.identityHashCode(this));
        writer.print(prefix);
        writer.print("TaskStackView");
        writer.print(" hasDefRelayout=");
        writer.print(this.mDeferredTaskViewLayoutAnimation != null ? "Y" : "N");
        writer.print(" clipDirty=");
        writer.print(this.mTaskViewsClipDirty ? "Y" : "N");
        writer.print(" awaitingStackReload=");
        writer.print(this.mFinishedLayoutAfterStackReload ? "Y" : "N");
        writer.print(" initialState=");
        writer.print(this.mInitialState);
        writer.print(" inMeasureLayout=");
        writer.print(this.mInMeasureLayout ? "Y" : "N");
        writer.print(" enterAnimCompleted=");
        writer.print(this.mEnterAnimationComplete ? "Y" : "N");
        writer.print(" touchExplorationOn=");
        writer.print(this.mTouchExplorationEnabled ? "Y" : "N");
        writer.print(" screenPinningOn=");
        writer.print(this.mScreenPinningEnabled ? "Y" : "N");
        writer.print(" numIgnoreTasks=");
        writer.print(this.mIgnoreTasks.size());
        writer.print(" numViewPool=");
        writer.print(this.mViewPool.getViews().size());
        writer.print(" stableStackBounds=");
        writer.print(Utilities.dumpRect(this.mStableStackBounds));
        writer.print(" stackBounds=");
        writer.print(Utilities.dumpRect(this.mStackBounds));
        writer.print(" stableWindow=");
        writer.print(Utilities.dumpRect(this.mStableWindowRect));
        writer.print(" window=");
        writer.print(Utilities.dumpRect(this.mWindowRect));
        writer.print(" display=");
        writer.print(Utilities.dumpRect(this.mDisplayRect));
        writer.print(" orientation=");
        writer.print(this.mDisplayOrientation);
        writer.print(" [0x");
        writer.print(id);
        writer.print("]");
        writer.println();
        if (this.mFocusedTask != null) {
            writer.print(innerPrefix);
            writer.print("Focused task: ");
            this.mFocusedTask.dump("", writer);
        }
        int numTaskViews = this.mTaskViews.size();
        for (int i = 0; i < numTaskViews; i++) {
            this.mTaskViews.get(i).dump(innerPrefix, writer);
        }
        this.mLayoutAlgorithm.dump(innerPrefix, writer);
        this.mStackScroller.dump(innerPrefix, writer);
    }
}
