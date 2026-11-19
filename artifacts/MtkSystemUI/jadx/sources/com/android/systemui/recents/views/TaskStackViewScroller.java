package com.android.systemui.recents.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.FloatProperty;
import android.util.Property;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.widget.OverScroller;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.views.lowram.TaskStackLowRamLayoutAlgorithm;
import com.android.systemui.shared.recents.utilities.AnimationProps;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.statusbar.FlingAnimationUtils;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class TaskStackViewScroller {
    private static final Property<TaskStackViewScroller, Float> STACK_SCROLL = new FloatProperty<TaskStackViewScroller>("stackScroll") { // from class: com.android.systemui.recents.views.TaskStackViewScroller.1
        @Override // android.util.FloatProperty
        public void setValue(TaskStackViewScroller object, float value) {
            object.setStackScroll(value);
        }

        @Override // android.util.Property
        public Float get(TaskStackViewScroller object) {
            return Float.valueOf(object.getStackScroll());
        }
    };
    TaskStackViewScrollerCallbacks mCb;
    Context mContext;
    float mFinalAnimatedScroll;
    final FlingAnimationUtils mFlingAnimationUtils;
    float mFlingDownScrollP;
    int mFlingDownY;

    @ViewDebug.ExportedProperty(category = "recents")
    float mLastDeltaP = 0.0f;
    TaskStackLayoutAlgorithm mLayoutAlgorithm;
    ObjectAnimator mScrollAnimator;
    OverScroller mScroller;

    @ViewDebug.ExportedProperty(category = "recents")
    float mStackScrollP;

    public interface TaskStackViewScrollerCallbacks {
        void onStackScrollChanged(float f, float f2, AnimationProps animationProps);
    }

    public TaskStackViewScroller(Context context, TaskStackViewScrollerCallbacks cb, TaskStackLayoutAlgorithm layoutAlgorithm) {
        this.mContext = context;
        this.mCb = cb;
        this.mScroller = new OverScroller(context);
        if (Recents.getConfiguration().isLowRamDevice) {
            this.mScroller.setFriction(0.06f);
        }
        this.mLayoutAlgorithm = layoutAlgorithm;
        this.mFlingAnimationUtils = new FlingAnimationUtils(context, 0.3f);
    }

    void reset() {
        this.mStackScrollP = 0.0f;
        this.mLastDeltaP = 0.0f;
    }

    void resetDeltaScroll() {
        this.mLastDeltaP = 0.0f;
    }

    public float getStackScroll() {
        return this.mStackScrollP;
    }

    public void setStackScroll(float s) {
        setStackScroll(s, AnimationProps.IMMEDIATE);
    }

    public float setDeltaStackScroll(float downP, float deltaP) {
        float targetScroll = downP + deltaP;
        float newScroll = this.mLayoutAlgorithm.updateFocusStateOnScroll(this.mLastDeltaP + downP, targetScroll, this.mStackScrollP);
        setStackScroll(newScroll, AnimationProps.IMMEDIATE);
        this.mLastDeltaP = deltaP;
        return newScroll - targetScroll;
    }

    public void setStackScroll(float newScroll, AnimationProps animation) {
        float prevScroll = this.mStackScrollP;
        this.mStackScrollP = newScroll;
        if (this.mCb != null) {
            this.mCb.onStackScrollChanged(prevScroll, this.mStackScrollP, animation);
        }
    }

    public boolean setStackScrollToInitialState() {
        float prevScroll = this.mStackScrollP;
        setStackScroll(this.mLayoutAlgorithm.mInitialScrollP);
        return Float.compare(prevScroll, this.mStackScrollP) != 0;
    }

    public void fling(float downScrollP, int downY, int y, int velY, int minY, int maxY, int overscroll) {
        this.mFlingDownScrollP = downScrollP;
        this.mFlingDownY = downY;
        this.mScroller.fling(0, y, 0, velY, 0, 0, minY, maxY, 0, overscroll);
    }

    public boolean boundScroll() {
        float curScroll = getStackScroll();
        float newScroll = getBoundedStackScroll(curScroll);
        if (Float.compare(newScroll, curScroll) != 0) {
            setStackScroll(newScroll);
            return true;
        }
        return false;
    }

    float getBoundedStackScroll(float scroll) {
        return Utilities.clamp(scroll, this.mLayoutAlgorithm.mMinScrollP, this.mLayoutAlgorithm.mMaxScrollP);
    }

    float getScrollAmountOutOfBounds(float scroll) {
        if (scroll < this.mLayoutAlgorithm.mMinScrollP) {
            return Math.abs(scroll - this.mLayoutAlgorithm.mMinScrollP);
        }
        if (scroll > this.mLayoutAlgorithm.mMaxScrollP) {
            return Math.abs(scroll - this.mLayoutAlgorithm.mMaxScrollP);
        }
        return 0.0f;
    }

    boolean isScrollOutOfBounds() {
        return Float.compare(getScrollAmountOutOfBounds(this.mStackScrollP), 0.0f) != 0;
    }

    void scrollToClosestTask(int velocity) {
        float stackScroll = getStackScroll();
        if (!Recents.getConfiguration().isLowRamDevice || stackScroll < this.mLayoutAlgorithm.mMinScrollP || stackScroll > this.mLayoutAlgorithm.mMaxScrollP) {
            return;
        }
        TaskStackLowRamLayoutAlgorithm algorithm = this.mLayoutAlgorithm.mTaskStackLowRamLayoutAlgorithm;
        float flingThreshold = ViewConfiguration.get(this.mContext).getScaledMinimumFlingVelocity();
        if (Math.abs(velocity) > flingThreshold) {
            int minY = algorithm.percentageToScroll(this.mLayoutAlgorithm.mMinScrollP);
            int maxY = algorithm.percentageToScroll(this.mLayoutAlgorithm.mMaxScrollP);
            fling(0.0f, 0, algorithm.percentageToScroll(stackScroll), -velocity, minY, maxY, 0);
            float pos = algorithm.scrollToPercentage(this.mScroller.getFinalY());
            float newScrollP = algorithm.getClosestTaskP(pos, this.mLayoutAlgorithm.mNumStackTasks, velocity);
            ValueAnimator animator = ObjectAnimator.ofFloat(stackScroll, newScrollP);
            this.mFlingAnimationUtils.apply(animator, algorithm.percentageToScroll(stackScroll), algorithm.percentageToScroll(newScrollP), velocity);
            animateScroll(newScrollP, (int) animator.getDuration(), animator.getInterpolator(), null);
            return;
        }
        animateScroll(algorithm.getClosestTaskP(stackScroll, this.mLayoutAlgorithm.mNumStackTasks, velocity), 300, Interpolators.ACCELERATE_DECELERATE, null);
    }

    ObjectAnimator animateBoundScroll() {
        float curScroll = getStackScroll();
        float newScroll = getBoundedStackScroll(curScroll);
        if (Float.compare(newScroll, curScroll) != 0) {
            animateScroll(newScroll, null);
        }
        return this.mScrollAnimator;
    }

    void animateScroll(float newScroll, Runnable postRunnable) {
        int duration = this.mContext.getResources().getInteger(R.integer.recents_animate_task_stack_scroll_duration);
        animateScroll(newScroll, duration, postRunnable);
    }

    void animateScroll(float newScroll, int duration, Runnable postRunnable) {
        animateScroll(newScroll, duration, Interpolators.LINEAR_OUT_SLOW_IN, postRunnable);
    }

    void animateScroll(float newScroll, int duration, TimeInterpolator interpolator, Runnable postRunnable) {
        ObjectAnimator an = ObjectAnimator.ofFloat(this, STACK_SCROLL, getStackScroll(), newScroll);
        an.setDuration(duration);
        an.setInterpolator(interpolator);
        animateScroll(newScroll, an, postRunnable);
    }

    private void animateScroll(float newScroll, ObjectAnimator animator, final Runnable postRunnable) {
        if (this.mScrollAnimator != null && this.mScrollAnimator.isRunning()) {
            setStackScroll(this.mFinalAnimatedScroll);
            this.mScroller.forceFinished(true);
        }
        stopScroller();
        stopBoundScrollAnimation();
        if (Float.compare(this.mStackScrollP, newScroll) != 0) {
            this.mFinalAnimatedScroll = newScroll;
            this.mScrollAnimator = animator;
            this.mScrollAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.recents.views.TaskStackViewScroller.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    if (postRunnable != null) {
                        postRunnable.run();
                    }
                    TaskStackViewScroller.this.mScrollAnimator.removeAllListeners();
                }
            });
            this.mScrollAnimator.start();
            return;
        }
        if (postRunnable != null) {
            postRunnable.run();
        }
    }

    void stopBoundScrollAnimation() {
        Utilities.cancelAnimationWithoutCallbacks(this.mScrollAnimator);
    }

    boolean computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            float deltaP = this.mLayoutAlgorithm.getDeltaPForY(this.mFlingDownY, this.mScroller.getCurrY());
            this.mFlingDownScrollP += setDeltaStackScroll(this.mFlingDownScrollP, deltaP);
            return true;
        }
        return false;
    }

    float getScrollVelocity() {
        return this.mScroller.getCurrVelocity();
    }

    void stopScroller() {
        if (!this.mScroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
    }

    public void dump(String prefix, PrintWriter writer) {
        writer.print(prefix);
        writer.print("TaskStackViewScroller");
        writer.print(" stackScroll:");
        writer.print(this.mStackScrollP);
        writer.println();
    }
}
