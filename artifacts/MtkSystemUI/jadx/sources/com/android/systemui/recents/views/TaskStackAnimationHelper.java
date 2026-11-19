package com.android.systemui.recents.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.RecentsActivityLaunchState;
import com.android.systemui.recents.RecentsConfiguration;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.events.component.SetWaitingForTransitionStartEvent;
import com.android.systemui.recents.misc.ReferenceCountedTrigger;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.TaskStack;
import com.android.systemui.shared.recents.utilities.AnimationProps;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TaskStackAnimationHelper {
    private final int mEnterAndExitFromHomeTranslationOffset;
    private TaskStackView mStackView;
    private static final Interpolator ENTER_FROM_HOME_ALPHA_INTERPOLATOR = Interpolators.LINEAR;
    private static final Interpolator EXIT_TO_HOME_TRANSLATION_INTERPOLATOR = new PathInterpolator(0.4f, 0.0f, 0.6f, 1.0f);
    private static final Interpolator DISMISS_ALL_TRANSLATION_INTERPOLATOR = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
    private static final Interpolator FOCUS_NEXT_TASK_INTERPOLATOR = new PathInterpolator(0.4f, 0.0f, 0.0f, 1.0f);
    private static final Interpolator FOCUS_IN_FRONT_NEXT_TASK_INTERPOLATOR = new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f);
    private static final Interpolator FOCUS_BEHIND_NEXT_TASK_INTERPOLATOR = Interpolators.LINEAR_OUT_SLOW_IN;
    private static final Interpolator ENTER_WHILE_DOCKING_INTERPOLATOR = Interpolators.LINEAR_OUT_SLOW_IN;
    private TaskViewTransform mTmpTransform = new TaskViewTransform();
    private ArrayList<TaskViewTransform> mTmpCurrentTaskTransforms = new ArrayList<>();
    private ArrayList<TaskViewTransform> mTmpFinalTaskTransforms = new ArrayList<>();

    public TaskStackAnimationHelper(Context context, TaskStackView stackView) {
        this.mStackView = stackView;
        this.mEnterAndExitFromHomeTranslationOffset = Recents.getConfiguration().isGridEnabled ? 0 : 33;
    }

    public void prepareForEnterAnimation() {
        List<TaskView> taskViews;
        float f;
        RecentsConfiguration config = Recents.getConfiguration();
        RecentsActivityLaunchState launchState = config.getLaunchState();
        Resources res = this.mStackView.getResources();
        Resources appResources = this.mStackView.getContext().getApplicationContext().getResources();
        TaskStackLayoutAlgorithm stackLayout = this.mStackView.getStackAlgorithm();
        TaskStackViewScroller stackScroller = this.mStackView.getScroller();
        TaskStack stack = this.mStackView.getStack();
        Task launchTargetTask = stack.getLaunchTarget();
        if (stack.getTaskCount() == 0) {
            return;
        }
        int offscreenYOffset = stackLayout.mStackRect.height();
        int taskViewAffiliateGroupEnterOffset = res.getDimensionPixelSize(R.dimen.recents_task_stack_animation_affiliate_enter_offset);
        int launchedWhileDockingOffset = res.getDimensionPixelSize(R.dimen.recents_task_stack_animation_launched_while_docking_offset);
        boolean isLandscape = appResources.getConfiguration().orientation == 2;
        float top = 0.0f;
        boolean isLowRamDevice = Recents.getConfiguration().isLowRamDevice;
        if (isLowRamDevice && launchState.launchedFromApp && !launchState.launchedViaDockGesture) {
            stackLayout.getStackTransform(launchTargetTask, stackScroller.getStackScroll(), this.mTmpTransform, null);
            top = this.mTmpTransform.rect.top;
        }
        List<TaskView> taskViews2 = this.mStackView.getTaskViews();
        int i = taskViews2.size() - 1;
        while (i >= 0) {
            TaskView tv = taskViews2.get(i);
            Resources appResources2 = appResources;
            Task task = tv.getTask();
            TaskStack stack2 = stack;
            Task launchTargetTask2 = launchTargetTask;
            int taskViewAffiliateGroupEnterOffset2 = taskViewAffiliateGroupEnterOffset;
            stackLayout.getStackTransform(task, stackScroller.getStackScroll(), this.mTmpTransform, null);
            if (!launchState.launchedFromApp || launchState.launchedViaDockGesture) {
                taskViews = taskViews2;
                if (launchState.launchedFromHome) {
                    if (isLowRamDevice) {
                        f = 0.0f;
                        this.mTmpTransform.rect.offset(0.0f, stackLayout.getTaskRect().height() / 4);
                    } else {
                        f = 0.0f;
                        this.mTmpTransform.rect.offset(0.0f, offscreenYOffset);
                    }
                    this.mTmpTransform.alpha = f;
                    this.mStackView.updateTaskViewToTransform(tv, this.mTmpTransform, AnimationProps.IMMEDIATE);
                } else if (launchState.launchedViaDockGesture) {
                    int offset = isLandscape ? launchedWhileDockingOffset : (int) (offscreenYOffset * 0.9f);
                    this.mTmpTransform.rect.offset(0.0f, offset);
                    this.mTmpTransform.alpha = 0.0f;
                    this.mStackView.updateTaskViewToTransform(tv, this.mTmpTransform, AnimationProps.IMMEDIATE);
                }
            } else if (task.isLaunchTarget) {
                tv.onPrepareLaunchTargetForEnterAnimation();
                taskViews = taskViews2;
            } else if (!isLowRamDevice || i < taskViews2.size() - 10) {
                taskViews = taskViews2;
            } else {
                stackLayout.getStackTransform(task, stackScroller.getStackScroll(), this.mTmpTransform, null);
                this.mTmpTransform.rect.offset(0.0f, -top);
                this.mTmpTransform.alpha = 0.0f;
                this.mStackView.updateTaskViewToTransform(tv, this.mTmpTransform, AnimationProps.IMMEDIATE);
                stackLayout.getStackTransform(task, stackScroller.getStackScroll(), this.mTmpTransform, null);
                this.mTmpTransform.alpha = 1.0f;
                taskViews = taskViews2;
                this.mStackView.updateTaskViewToTransform(tv, this.mTmpTransform, new AnimationProps(336, Interpolators.FAST_OUT_SLOW_IN));
            }
            i--;
            appResources = appResources2;
            stack = stack2;
            launchTargetTask = launchTargetTask2;
            taskViewAffiliateGroupEnterOffset = taskViewAffiliateGroupEnterOffset2;
            taskViews2 = taskViews;
        }
    }

    public void startEnterAnimation(ReferenceCountedTrigger postAnimationTrigger) {
        TaskStackLayoutAlgorithm stackLayout;
        RecentsConfiguration config = Recents.getConfiguration();
        RecentsActivityLaunchState launchState = config.getLaunchState();
        Resources res = this.mStackView.getResources();
        Resources appRes = this.mStackView.getContext().getApplicationContext().getResources();
        TaskStackLayoutAlgorithm stackLayout2 = this.mStackView.getStackAlgorithm();
        TaskStackViewScroller stackScroller = this.mStackView.getScroller();
        TaskStack stack = this.mStackView.getStack();
        Task launchTargetTask = stack.getLaunchTarget();
        if (stack.getTaskCount() == 0) {
            return;
        }
        boolean isLowRamDevice = Recents.getConfiguration().isLowRamDevice;
        int taskViewEnterFromAppDuration = res.getInteger(R.integer.recents_task_enter_from_app_duration);
        int taskViewEnterFromAffiliatedAppDuration = res.getInteger(R.integer.recents_task_enter_from_affiliated_app_duration);
        int dockGestureAnimDuration = appRes.getInteger(R.integer.long_press_dock_anim_duration);
        if (launchState.launchedFromApp && !launchState.launchedViaDockGesture && isLowRamDevice) {
            postAnimationTrigger.addLastDecrementRunnable(new Runnable() { // from class: com.android.systemui.recents.views.-$$Lambda$TaskStackAnimationHelper$Z1ye5IT0uybrzSDdzPdGEoeTWaY
                @Override // java.lang.Runnable
                public final void run() {
                    EventBus.getDefault().send(new SetWaitingForTransitionStartEvent(false));
                }
            });
        }
        List<TaskView> taskViews = this.mStackView.getTaskViews();
        int taskViewCount = taskViews.size();
        int taskIndexFromBack = taskViewCount - 1;
        while (true) {
            int i = taskIndexFromBack;
            RecentsConfiguration config2 = config;
            if (i < 0) {
                return;
            }
            Resources res2 = res;
            int taskIndexFromFront = (taskViewCount - i) - 1;
            Resources appRes2 = appRes;
            TaskView tv = taskViews.get(i);
            TaskStack stack2 = stack;
            Task task = tv.getTask();
            Task launchTargetTask2 = launchTargetTask;
            TaskStackViewScroller stackScroller2 = stackScroller;
            int taskViewEnterFromAffiliatedAppDuration2 = taskViewEnterFromAffiliatedAppDuration;
            stackLayout2.getStackTransform(task, stackScroller.getStackScroll(), this.mTmpTransform, null);
            if (launchState.launchedFromApp && !launchState.launchedViaDockGesture) {
                if (task.isLaunchTarget) {
                    tv.onStartLaunchTargetEnterAnimation(this.mTmpTransform, taskViewEnterFromAppDuration, this.mStackView.mScreenPinningEnabled, postAnimationTrigger);
                }
                stackLayout = stackLayout2;
            } else if (launchState.launchedFromHome) {
                float startOffsetFraction = (Math.min(5, taskIndexFromFront) * this.mEnterAndExitFromHomeTranslationOffset) / 300.0f;
                stackLayout = stackLayout2;
                AnimationProps taskAnimation = new AnimationProps().setInterpolator(4, ENTER_FROM_HOME_ALPHA_INTERPOLATOR).setListener(postAnimationTrigger.decrementOnAnimationEnd());
                if (isLowRamDevice) {
                    taskAnimation.setInterpolator(6, Interpolators.FAST_OUT_SLOW_IN).setDuration(6, 150).setDuration(4, 150);
                } else {
                    taskAnimation.setStartDelay(4, Math.min(5, taskIndexFromFront) * 16).setInterpolator(6, new RecentsEntrancePathInterpolator(0.0f, 0.0f, 0.2f, 1.0f, startOffsetFraction)).setDuration(6, 300).setDuration(4, 100);
                }
                postAnimationTrigger.increment();
                this.mStackView.updateTaskViewToTransform(tv, this.mTmpTransform, taskAnimation);
                if (i == taskViewCount - 1) {
                    tv.onStartFrontTaskEnterAnimation(this.mStackView.mScreenPinningEnabled);
                }
            } else {
                stackLayout = stackLayout2;
                if (launchState.launchedViaDockGesture) {
                    AnimationProps taskAnimation2 = new AnimationProps().setDuration(6, (i * 33) + dockGestureAnimDuration).setInterpolator(6, ENTER_WHILE_DOCKING_INTERPOLATOR).setStartDelay(6, 48).setListener(postAnimationTrigger.decrementOnAnimationEnd());
                    postAnimationTrigger.increment();
                    this.mStackView.updateTaskViewToTransform(tv, this.mTmpTransform, taskAnimation2);
                }
            }
            taskIndexFromBack = i - 1;
            config = config2;
            res = res2;
            appRes = appRes2;
            stack = stack2;
            launchTargetTask = launchTargetTask2;
            stackScroller = stackScroller2;
            taskViewEnterFromAffiliatedAppDuration = taskViewEnterFromAffiliatedAppDuration2;
            stackLayout2 = stackLayout;
        }
    }

    public void startExitToHomeAnimation(boolean animated, ReferenceCountedTrigger postAnimationTrigger) {
        AnimationProps taskAnimation;
        TaskStackLayoutAlgorithm stackLayout = this.mStackView.getStackAlgorithm();
        TaskStack stack = this.mStackView.getStack();
        if (stack.getTaskCount() == 0) {
            return;
        }
        int offscreenYOffset = stackLayout.mStackRect.height();
        List<TaskView> taskViews = this.mStackView.getTaskViews();
        int taskViewCount = taskViews.size();
        for (int i = 0; i < taskViewCount; i++) {
            int taskIndexFromFront = (taskViewCount - i) - 1;
            TaskView tv = taskViews.get(i);
            Task task = tv.getTask();
            if (!this.mStackView.isIgnoredTask(task)) {
                if (animated) {
                    int delay = Math.min(5, taskIndexFromFront) * this.mEnterAndExitFromHomeTranslationOffset;
                    taskAnimation = new AnimationProps().setDuration(6, 200).setListener(postAnimationTrigger.decrementOnAnimationEnd());
                    if (Recents.getConfiguration().isLowRamDevice) {
                        taskAnimation.setInterpolator(6, Interpolators.FAST_OUT_SLOW_IN);
                    } else {
                        taskAnimation.setStartDelay(6, delay).setInterpolator(6, EXIT_TO_HOME_TRANSLATION_INTERPOLATOR);
                    }
                    postAnimationTrigger.increment();
                } else {
                    taskAnimation = AnimationProps.IMMEDIATE;
                }
                AnimationProps taskAnimation2 = taskAnimation;
                this.mTmpTransform.fillIn(tv);
                if (Recents.getConfiguration().isLowRamDevice) {
                    taskAnimation2.setInterpolator(4, EXIT_TO_HOME_TRANSLATION_INTERPOLATOR).setDuration(4, 200);
                    this.mTmpTransform.rect.offset(0.0f, stackLayout.mTaskStackLowRamLayoutAlgorithm.getTaskRect().height() / 4);
                    this.mTmpTransform.alpha = 0.0f;
                } else {
                    this.mTmpTransform.rect.offset(0.0f, offscreenYOffset);
                }
                this.mStackView.updateTaskViewToTransform(tv, this.mTmpTransform, taskAnimation2);
            }
        }
    }

    public void startLaunchTaskAnimation(TaskView launchingTaskView, boolean screenPinningRequested, ReferenceCountedTrigger postAnimationTrigger) {
        Resources res = this.mStackView.getResources();
        int taskViewExitToAppDuration = res.getInteger(R.integer.recents_task_exit_to_app_duration);
        res.getDimensionPixelSize(R.dimen.recents_task_stack_animation_affiliate_enter_offset);
        launchingTaskView.getTask();
        List<TaskView> taskViews = this.mStackView.getTaskViews();
        int taskViewCount = taskViews.size();
        for (int i = 0; i < taskViewCount; i++) {
            final TaskView tv = taskViews.get(i);
            tv.getTask();
            if (tv == launchingTaskView) {
                tv.setClipViewInStack(false);
                postAnimationTrigger.addLastDecrementRunnable(new Runnable() { // from class: com.android.systemui.recents.views.TaskStackAnimationHelper.1
                    @Override // java.lang.Runnable
                    public void run() {
                        tv.setClipViewInStack(true);
                    }
                });
                tv.onStartLaunchTargetLaunchAnimation(taskViewExitToAppDuration, screenPinningRequested, postAnimationTrigger);
            }
        }
    }

    public void startDeleteTaskAnimation(TaskView deleteTaskView, boolean gridLayout, ReferenceCountedTrigger postAnimationTrigger) {
        if (gridLayout) {
            startTaskGridDeleteTaskAnimation(deleteTaskView, postAnimationTrigger);
        } else {
            startTaskStackDeleteTaskAnimation(deleteTaskView, postAnimationTrigger);
        }
    }

    public void startDeleteAllTasksAnimation(List<TaskView> taskViews, boolean gridLayout, ReferenceCountedTrigger postAnimationTrigger) {
        if (gridLayout) {
            for (int i = 0; i < taskViews.size(); i++) {
                startTaskGridDeleteTaskAnimation(taskViews.get(i), postAnimationTrigger);
            }
            return;
        }
        startTaskStackDeleteAllTasksAnimation(taskViews, postAnimationTrigger);
    }

    public boolean startScrollToFocusedTaskAnimation(Task newFocusedTask, boolean requestViewFocus) {
        ArrayList<Task> stackTasks;
        int duration;
        Interpolator interpolator;
        TaskStackLayoutAlgorithm stackLayout = this.mStackView.getStackAlgorithm();
        TaskStackViewScroller stackScroller = this.mStackView.getScroller();
        TaskStack stack = this.mStackView.getStack();
        float curScroll = stackScroller.getStackScroll();
        final float newScroll = stackScroller.getBoundedStackScroll(stackLayout.getStackScrollForTask(newFocusedTask));
        int i = 0;
        boolean willScrollToFront = newScroll > curScroll;
        boolean willScroll = Float.compare(newScroll, curScroll) != 0;
        int taskViewCount = this.mStackView.getTaskViews().size();
        ArrayList<Task> stackTasks2 = stack.getTasks();
        this.mStackView.getCurrentTaskTransforms(stackTasks2, this.mTmpCurrentTaskTransforms);
        this.mStackView.bindVisibleTaskViews(newScroll);
        stackLayout.setFocusState(1);
        stackScroller.setStackScroll(newScroll, null);
        this.mStackView.cancelDeferredTaskViewLayoutAnimation();
        ArrayList<Task> stackTasks3 = stackTasks2;
        this.mStackView.getLayoutTaskTransforms(newScroll, stackLayout.getFocusState(), stackTasks2, true, this.mTmpFinalTaskTransforms);
        TaskView newFocusedTaskView = this.mStackView.getChildViewForTask(newFocusedTask);
        if (newFocusedTaskView == null) {
            Log.e("TaskStackAnimationHelper", "b/27389156 null-task-view prebind:" + taskViewCount + " postbind:" + this.mStackView.getTaskViews().size() + " prescroll:" + curScroll + " postscroll: " + newScroll);
            return false;
        }
        newFocusedTaskView.setFocusedState(true, requestViewFocus);
        ReferenceCountedTrigger postAnimTrigger = new ReferenceCountedTrigger();
        postAnimTrigger.addLastDecrementRunnable(new Runnable() { // from class: com.android.systemui.recents.views.TaskStackAnimationHelper.2
            @Override // java.lang.Runnable
            public void run() {
                TaskStackAnimationHelper.this.mStackView.bindVisibleTaskViews(newScroll);
            }
        });
        List<TaskView> taskViews = this.mStackView.getTaskViews();
        int taskViewCount2 = taskViews.size();
        int newFocusTaskViewIndex = taskViews.indexOf(newFocusedTaskView);
        while (i < taskViewCount2) {
            TaskView tv = taskViews.get(i);
            Task task = tv.getTask();
            TaskStackLayoutAlgorithm stackLayout2 = stackLayout;
            if (this.mStackView.isIgnoredTask(task)) {
                stackTasks = stackTasks3;
            } else {
                ArrayList<Task> stackTasks4 = stackTasks3;
                int taskIndex = stackTasks4.indexOf(task);
                TaskViewTransform fromTransform = this.mTmpCurrentTaskTransforms.get(taskIndex);
                stackTasks = stackTasks4;
                TaskViewTransform toTransform = this.mTmpFinalTaskTransforms.get(taskIndex);
                this.mStackView.updateTaskViewToTransform(tv, fromTransform, AnimationProps.IMMEDIATE);
                if (willScrollToFront) {
                    duration = calculateStaggeredAnimDuration(i);
                    interpolator = FOCUS_BEHIND_NEXT_TASK_INTERPOLATOR;
                } else if (i < newFocusTaskViewIndex) {
                    duration = 150 + (((newFocusTaskViewIndex - i) - 1) * 50);
                    interpolator = FOCUS_BEHIND_NEXT_TASK_INTERPOLATOR;
                } else if (i > newFocusTaskViewIndex) {
                    duration = Math.max(100, 150 - (((i - newFocusTaskViewIndex) - 1) * 50));
                    interpolator = FOCUS_IN_FRONT_NEXT_TASK_INTERPOLATOR;
                } else {
                    duration = 200;
                    interpolator = FOCUS_NEXT_TASK_INTERPOLATOR;
                }
                AnimationProps anim = new AnimationProps().setDuration(6, duration).setInterpolator(6, interpolator).setListener(postAnimTrigger.decrementOnAnimationEnd());
                postAnimTrigger.increment();
                this.mStackView.updateTaskViewToTransform(tv, toTransform, anim);
            }
            i++;
            stackLayout = stackLayout2;
            stackScroller = stackScroller;
            stackTasks3 = stackTasks;
            stack = stack;
            curScroll = curScroll;
        }
        return willScroll;
    }

    public void startNewStackScrollAnimation(TaskStack newStack, ReferenceCountedTrigger animationTrigger) {
        TaskStackLayoutAlgorithm stackLayout = this.mStackView.getStackAlgorithm();
        TaskStackViewScroller stackScroller = this.mStackView.getScroller();
        ArrayList<Task> stackTasks = newStack.getTasks();
        this.mStackView.getCurrentTaskTransforms(stackTasks, this.mTmpCurrentTaskTransforms);
        int i = 0;
        this.mStackView.setTasks(newStack, false);
        this.mStackView.updateLayoutAlgorithm(false);
        final float newScroll = stackLayout.mInitialScrollP;
        this.mStackView.bindVisibleTaskViews(newScroll);
        stackLayout.setFocusState(0);
        stackLayout.setTaskOverridesForInitialState(newStack, true);
        stackScroller.setStackScroll(newScroll);
        this.mStackView.cancelDeferredTaskViewLayoutAnimation();
        this.mStackView.getLayoutTaskTransforms(newScroll, stackLayout.getFocusState(), stackTasks, false, this.mTmpFinalTaskTransforms);
        Task frontMostTask = newStack.getFrontMostTask();
        final TaskView frontMostTaskView = this.mStackView.getChildViewForTask(frontMostTask);
        final TaskViewTransform frontMostTransform = this.mTmpFinalTaskTransforms.get(stackTasks.indexOf(frontMostTask));
        if (frontMostTaskView != null) {
            this.mStackView.updateTaskViewToTransform(frontMostTaskView, stackLayout.getFrontOfStackTransform(), AnimationProps.IMMEDIATE);
        }
        animationTrigger.addLastDecrementRunnable(new Runnable() { // from class: com.android.systemui.recents.views.TaskStackAnimationHelper.3
            @Override // java.lang.Runnable
            public void run() {
                TaskStackAnimationHelper.this.mStackView.bindVisibleTaskViews(newScroll);
                if (frontMostTaskView != null) {
                    TaskStackAnimationHelper.this.mStackView.updateTaskViewToTransform(frontMostTaskView, frontMostTransform, new AnimationProps(75, 250, TaskStackAnimationHelper.FOCUS_BEHIND_NEXT_TASK_INTERPOLATOR));
                }
            }
        });
        List<TaskView> taskViews = this.mStackView.getTaskViews();
        int taskViewCount = taskViews.size();
        while (i < taskViewCount) {
            TaskView tv = taskViews.get(i);
            Task task = tv.getTask();
            if (!this.mStackView.isIgnoredTask(task) && (task != frontMostTask || frontMostTaskView == null)) {
                int taskIndex = stackTasks.indexOf(task);
                if (taskIndex == -1) {
                    Log.w("TaskStackAnimationHelper", "startNewStackScrollAnimation() task index = -1");
                } else {
                    stackLayout = stackLayout;
                    TaskViewTransform fromTransform = this.mTmpCurrentTaskTransforms.get(taskIndex);
                    TaskViewTransform toTransform = this.mTmpFinalTaskTransforms.get(taskIndex);
                    this.mStackView.updateTaskViewToTransform(tv, fromTransform, AnimationProps.IMMEDIATE);
                    int duration = calculateStaggeredAnimDuration(i);
                    Interpolator interpolator = FOCUS_BEHIND_NEXT_TASK_INTERPOLATOR;
                    AnimationProps anim = new AnimationProps().setDuration(6, duration).setInterpolator(6, interpolator).setListener(animationTrigger.decrementOnAnimationEnd());
                    animationTrigger.increment();
                    this.mStackView.updateTaskViewToTransform(tv, toTransform, anim);
                }
                i++;
                stackLayout = stackLayout;
                stackScroller = stackScroller;
                frontMostTask = frontMostTask;
                frontMostTaskView = frontMostTaskView;
            }
            i++;
            stackLayout = stackLayout;
            stackScroller = stackScroller;
            frontMostTask = frontMostTask;
            frontMostTaskView = frontMostTaskView;
        }
    }

    private int calculateStaggeredAnimDuration(int i) {
        return Math.max(100, ((i - 1) * 50) + 100);
    }

    private void startTaskGridDeleteTaskAnimation(final TaskView deleteTaskView, final ReferenceCountedTrigger postAnimationTrigger) {
        postAnimationTrigger.increment();
        postAnimationTrigger.addLastDecrementRunnable(new Runnable() { // from class: com.android.systemui.recents.views.-$$Lambda$TaskStackAnimationHelper$8-n9X8WiqU8WjSQafJipbVZD-LA
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mStackView.getTouchHandler().onChildDismissed(deleteTaskView);
            }
        });
        deleteTaskView.animate().setDuration(300L).scaleX(0.9f).scaleY(0.9f).alpha(0.0f).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.recents.views.TaskStackAnimationHelper.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                postAnimationTrigger.decrement();
            }
        }).start();
    }

    private void startTaskStackDeleteTaskAnimation(final TaskView deleteTaskView, final ReferenceCountedTrigger postAnimationTrigger) {
        final TaskStackViewTouchHandler touchHandler = this.mStackView.getTouchHandler();
        touchHandler.onBeginManualDrag(deleteTaskView);
        postAnimationTrigger.increment();
        postAnimationTrigger.addLastDecrementRunnable(new Runnable() { // from class: com.android.systemui.recents.views.-$$Lambda$TaskStackAnimationHelper$ax6dOg8GHbAwig9kBnwP5_DTcLA
            @Override // java.lang.Runnable
            public final void run() {
                touchHandler.onChildDismissed(deleteTaskView);
            }
        });
        final float dismissSize = touchHandler.getScaledDismissSize();
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(400L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.recents.views.-$$Lambda$TaskStackAnimationHelper$DBVHlVbyKhFHpm00avfl8nT1DCw
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                TaskStackAnimationHelper.lambda$startTaskStackDeleteTaskAnimation$3(deleteTaskView, dismissSize, touchHandler, valueAnimator);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.recents.views.TaskStackAnimationHelper.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                postAnimationTrigger.decrement();
            }
        });
        animator.start();
    }

    static /* synthetic */ void lambda$startTaskStackDeleteTaskAnimation$3(TaskView deleteTaskView, float dismissSize, TaskStackViewTouchHandler touchHandler, ValueAnimator animation) {
        float progress = ((Float) animation.getAnimatedValue()).floatValue();
        deleteTaskView.setTranslationX(progress * dismissSize);
        touchHandler.updateSwipeProgress(deleteTaskView, true, progress);
    }

    private void startTaskStackDeleteAllTasksAnimation(List<TaskView> taskViews, final ReferenceCountedTrigger postAnimationTrigger) {
        TaskStackLayoutAlgorithm stackLayout = this.mStackView.getStackAlgorithm();
        int offscreenXOffset = this.mStackView.getMeasuredWidth() - stackLayout.getTaskRect().left;
        int taskViewCount = taskViews.size();
        for (int i = taskViewCount - 1; i >= 0; i--) {
            final TaskView tv = taskViews.get(i);
            int taskIndexFromFront = (taskViewCount - i) - 1;
            int startDelay = taskIndexFromFront * 33;
            tv.setClipViewInStack(false);
            AnimationProps taskAnimation = new AnimationProps(startDelay, 200, DISMISS_ALL_TRANSLATION_INTERPOLATOR, new AnimatorListenerAdapter() { // from class: com.android.systemui.recents.views.TaskStackAnimationHelper.6
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    postAnimationTrigger.decrement();
                    tv.setClipViewInStack(true);
                }
            });
            postAnimationTrigger.increment();
            this.mTmpTransform.fillIn(tv);
            this.mTmpTransform.rect.offset(offscreenXOffset, 0.0f);
            this.mStackView.updateTaskViewToTransform(tv, this.mTmpTransform, taskAnimation);
        }
    }
}
