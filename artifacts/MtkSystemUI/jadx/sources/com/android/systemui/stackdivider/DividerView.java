package com.android.systemui.stackdivider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.policy.DividerSnapAlgorithm;
import com.android.internal.policy.DockedDividerUtils;
import com.android.internal.view.SurfaceFlingerVsyncChoreographer;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.events.activity.DockedFirstAnimationFrameEvent;
import com.android.systemui.recents.events.activity.DockedTopTaskEvent;
import com.android.systemui.recents.events.activity.RecentsActivityStartingEvent;
import com.android.systemui.recents.events.activity.UndockingTaskEvent;
import com.android.systemui.recents.events.ui.RecentsGrowingEvent;
import com.android.systemui.recents.misc.SystemServicesProxy;
import com.android.systemui.stackdivider.events.StartedDragingEvent;
import com.android.systemui.stackdivider.events.StoppedDragingEvent;
import com.android.systemui.statusbar.FlingAnimationUtils;

/* JADX INFO: loaded from: classes.dex */
public class DividerView extends FrameLayout implements View.OnTouchListener, ViewTreeObserver.OnComputeInternalInsetsListener {
    private boolean mAdjustedForIme;
    private View mBackground;
    private boolean mBackgroundLifted;
    private ValueAnimator mCurrentAnimator;
    private final Display mDefaultDisplay;
    private int mDisplayHeight;
    private int mDisplayRotation;
    private int mDisplayWidth;
    private int mDividerInsets;
    private int mDividerSize;
    private int mDividerWindowWidth;
    private int mDockSide;
    private final Rect mDockedInsetRect;
    private final Rect mDockedRect;
    private boolean mDockedStackMinimized;
    private final Rect mDockedTaskRect;
    private boolean mEntranceAnimationRunning;
    private boolean mExitAnimationRunning;
    private int mExitStartPosition;
    private FlingAnimationUtils mFlingAnimationUtils;
    private boolean mGrowRecents;
    private DividerHandleView mHandle;
    private final View.AccessibilityDelegate mHandleDelegate;
    private final Handler mHandler;
    private boolean mHomeStackResizable;
    private boolean mIsInMinimizeInteraction;
    private final Rect mLastResizeRect;
    private int mLongPressEntraceAnimDuration;
    private MinimizedDockShadow mMinimizedShadow;
    private DividerSnapAlgorithm mMinimizedSnapAlgorithm;
    private boolean mMoving;
    private final Rect mOtherInsetRect;
    private final Rect mOtherRect;
    private final Rect mOtherTaskRect;
    private boolean mRemoved;
    private final Runnable mResetBackgroundRunnable;
    private final SurfaceFlingerVsyncChoreographer mSfChoreographer;
    private DividerSnapAlgorithm mSnapAlgorithm;
    private DividerSnapAlgorithm.SnapTarget mSnapTargetBeforeMinimized;
    private final Rect mStableInsets;
    private int mStartPosition;
    private int mStartX;
    private int mStartY;
    private DividerState mState;
    private final int[] mTempInt2;
    private final Rect mTmpRect;
    private int mTouchElevation;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private DividerWindowManager mWindowManager;
    private final WindowManagerProxy mWindowManagerProxy;
    private static final PathInterpolator SLOWDOWN_INTERPOLATOR = new PathInterpolator(0.5f, 1.0f, 0.5f, 1.0f);
    private static final PathInterpolator DIM_INTERPOLATOR = new PathInterpolator(0.23f, 0.87f, 0.52f, -0.11f);
    private static final Interpolator IME_ADJUST_INTERPOLATOR = new PathInterpolator(0.2f, 0.0f, 0.1f, 1.0f);

    public DividerView(Context context) {
        this(context, null);
    }

    public DividerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DividerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DividerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mTempInt2 = new int[2];
        this.mDockedRect = new Rect();
        this.mDockedTaskRect = new Rect();
        this.mOtherTaskRect = new Rect();
        this.mOtherRect = new Rect();
        this.mDockedInsetRect = new Rect();
        this.mOtherInsetRect = new Rect();
        this.mLastResizeRect = new Rect();
        this.mTmpRect = new Rect();
        this.mWindowManagerProxy = WindowManagerProxy.getInstance();
        this.mStableInsets = new Rect();
        this.mHandler = new Handler() { // from class: com.android.systemui.stackdivider.DividerView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    DividerView.this.resizeStack(msg.arg1, msg.arg2, (DividerSnapAlgorithm.SnapTarget) msg.obj);
                } else {
                    super.handleMessage(msg);
                }
            }
        };
        this.mHandleDelegate = new View.AccessibilityDelegate() { // from class: com.android.systemui.stackdivider.DividerView.2
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                DividerSnapAlgorithm snapAlgorithm = DividerView.this.getSnapAlgorithm();
                if (DividerView.this.isHorizontalDivision()) {
                    info.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, DividerView.this.mContext.getString(R.string.accessibility_action_divider_top_full)));
                    if (snapAlgorithm.isFirstSplitTargetAvailable()) {
                        info.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_70, DividerView.this.mContext.getString(R.string.accessibility_action_divider_top_70)));
                    }
                    if (snapAlgorithm.showMiddleSplitTargetForAccessibility()) {
                        info.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, DividerView.this.mContext.getString(R.string.accessibility_action_divider_top_50)));
                    }
                    if (snapAlgorithm.isLastSplitTargetAvailable()) {
                        info.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_30, DividerView.this.mContext.getString(R.string.accessibility_action_divider_top_30)));
                    }
                    info.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, DividerView.this.mContext.getString(R.string.accessibility_action_divider_bottom_full)));
                    return;
                }
                info.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, DividerView.this.mContext.getString(R.string.accessibility_action_divider_left_full)));
                if (snapAlgorithm.isFirstSplitTargetAvailable()) {
                    info.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_70, DividerView.this.mContext.getString(R.string.accessibility_action_divider_left_70)));
                }
                if (snapAlgorithm.showMiddleSplitTargetForAccessibility()) {
                    info.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, DividerView.this.mContext.getString(R.string.accessibility_action_divider_left_50)));
                }
                if (snapAlgorithm.isLastSplitTargetAvailable()) {
                    info.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_30, DividerView.this.mContext.getString(R.string.accessibility_action_divider_left_30)));
                }
                info.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, DividerView.this.mContext.getString(R.string.accessibility_action_divider_right_full)));
            }

            @Override // android.view.View.AccessibilityDelegate
            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                int currentPosition = DividerView.this.getCurrentPosition();
                DividerSnapAlgorithm.SnapTarget nextTarget = null;
                switch (action) {
                    case R.id.action_move_rb_full /* 2131361834 */:
                        nextTarget = DividerView.this.mSnapAlgorithm.getDismissStartTarget();
                        break;
                    case R.id.action_move_tl_30 /* 2131361835 */:
                        nextTarget = DividerView.this.mSnapAlgorithm.getFirstSplitTarget();
                        break;
                    case R.id.action_move_tl_50 /* 2131361836 */:
                        nextTarget = DividerView.this.mSnapAlgorithm.getMiddleTarget();
                        break;
                    case R.id.action_move_tl_70 /* 2131361837 */:
                        nextTarget = DividerView.this.mSnapAlgorithm.getLastSplitTarget();
                        break;
                    case R.id.action_move_tl_full /* 2131361838 */:
                        nextTarget = DividerView.this.mSnapAlgorithm.getDismissEndTarget();
                        break;
                }
                DividerSnapAlgorithm.SnapTarget nextTarget2 = nextTarget;
                if (nextTarget2 != null) {
                    DividerView.this.startDragging(true, false);
                    DividerView.this.stopDragging(currentPosition, nextTarget2, 250L, Interpolators.FAST_OUT_SLOW_IN);
                    return true;
                }
                return super.performAccessibilityAction(host, action, args);
            }
        };
        this.mResetBackgroundRunnable = new Runnable() { // from class: com.android.systemui.stackdivider.DividerView.3
            @Override // java.lang.Runnable
            public void run() {
                DividerView.this.resetBackground();
            }
        };
        this.mSfChoreographer = new SurfaceFlingerVsyncChoreographer(this.mHandler, context.getDisplay(), Choreographer.getInstance());
        DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
        this.mDefaultDisplay = displayManager.getDisplay(0);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mHandle = (DividerHandleView) findViewById(R.id.docked_divider_handle);
        this.mBackground = findViewById(R.id.docked_divider_background);
        this.mMinimizedShadow = (MinimizedDockShadow) findViewById(R.id.minimized_dock_shadow);
        this.mHandle.setOnTouchListener(this);
        this.mDividerWindowWidth = getResources().getDimensionPixelSize(android.R.dimen.car_button_height);
        this.mDividerInsets = getResources().getDimensionPixelSize(android.R.dimen.car_borderless_button_horizontal_padding);
        this.mDividerSize = this.mDividerWindowWidth - (this.mDividerInsets * 2);
        this.mTouchElevation = getResources().getDimensionPixelSize(R.dimen.docked_stack_divider_lift_elevation);
        this.mLongPressEntraceAnimDuration = getResources().getInteger(R.integer.long_press_dock_anim_duration);
        this.mGrowRecents = getResources().getBoolean(R.bool.recents_grow_in_multiwindow);
        this.mTouchSlop = ViewConfiguration.get(this.mContext).getScaledTouchSlop();
        this.mFlingAnimationUtils = new FlingAnimationUtils(getContext(), 0.3f);
        updateDisplayInfo();
        boolean landscape = getResources().getConfiguration().orientation == 2;
        this.mHandle.setPointerIcon(PointerIcon.getSystemIcon(getContext(), landscape ? 1014 : 1015));
        getViewTreeObserver().addOnComputeInternalInsetsListener(this);
        this.mHandle.setAccessibilityDelegate(this.mHandleDelegate);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
        if (this.mHomeStackResizable && this.mDockSide != -1 && !this.mIsInMinimizeInteraction) {
            saveSnapTargetBeforeMinimized(this.mSnapTargetBeforeMinimized);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    void onDividerRemoved() {
        this.mRemoved = true;
        this.mHandler.removeMessages(0);
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (this.mStableInsets.left != insets.getStableInsetLeft() || this.mStableInsets.top != insets.getStableInsetTop() || this.mStableInsets.right != insets.getStableInsetRight() || this.mStableInsets.bottom != insets.getStableInsetBottom()) {
            this.mStableInsets.set(insets.getStableInsetLeft(), insets.getStableInsetTop(), insets.getStableInsetRight(), insets.getStableInsetBottom());
            if (this.mSnapAlgorithm != null || this.mMinimizedSnapAlgorithm != null) {
                this.mSnapAlgorithm = null;
                this.mMinimizedSnapAlgorithm = null;
                initializeSnapAlgorithm();
            }
        }
        return super.onApplyWindowInsets(insets);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int minimizeLeft = 0;
        int minimizeTop = 0;
        if (this.mDockSide == 2) {
            minimizeTop = this.mBackground.getTop();
        } else if (this.mDockSide == 1) {
            minimizeLeft = this.mBackground.getLeft();
        } else if (this.mDockSide == 3) {
            minimizeLeft = this.mBackground.getRight() - this.mMinimizedShadow.getWidth();
        }
        this.mMinimizedShadow.layout(minimizeLeft, minimizeTop, this.mMinimizedShadow.getMeasuredWidth() + minimizeLeft, this.mMinimizedShadow.getMeasuredHeight() + minimizeTop);
        if (changed) {
            this.mWindowManagerProxy.setTouchRegion(new Rect(this.mHandle.getLeft(), this.mHandle.getTop(), this.mHandle.getRight(), this.mHandle.getBottom()));
        }
    }

    public void injectDependencies(DividerWindowManager windowManager, DividerState dividerState) {
        this.mWindowManager = windowManager;
        this.mState = dividerState;
        if (this.mStableInsets.isEmpty()) {
            SystemServicesProxy.getInstance(this.mContext).getStableInsets(this.mStableInsets);
        }
        if (this.mState.mRatioPositionBeforeMinimized == 0.0f) {
            this.mSnapTargetBeforeMinimized = this.mSnapAlgorithm.getMiddleTarget();
        } else {
            repositionSnapTargetBeforeMinimized();
        }
    }

    public WindowManagerProxy getWindowManagerProxy() {
        return this.mWindowManagerProxy;
    }

    public Rect getNonMinimizedSplitScreenSecondaryBounds() {
        calculateBoundsForPosition(this.mSnapTargetBeforeMinimized.position, DockedDividerUtils.invertDockSide(this.mDockSide), this.mOtherTaskRect);
        this.mOtherTaskRect.bottom -= this.mStableInsets.bottom;
        int i = this.mDockSide;
        if (i == 1) {
            this.mOtherTaskRect.top += this.mStableInsets.top;
            this.mOtherTaskRect.right -= this.mStableInsets.right;
        } else if (i == 3) {
            this.mOtherTaskRect.top += this.mStableInsets.top;
            this.mOtherTaskRect.left += this.mStableInsets.left;
        }
        return this.mOtherTaskRect;
    }

    public boolean startDragging(boolean animate, boolean touching) {
        cancelFlingAnimation();
        if (touching) {
            this.mHandle.setTouching(true, animate);
        }
        this.mDockSide = this.mWindowManagerProxy.getDockSide();
        if (this.mDisplayRotation != this.mDefaultDisplay.getRotation()) {
            updateDisplayInfo();
        }
        initializeSnapAlgorithm();
        this.mWindowManagerProxy.setResizing(true);
        if (touching) {
            this.mWindowManager.setSlippery(false);
            liftBackground();
        }
        EventBus.getDefault().send(new StartedDragingEvent());
        if (this.mDockSide != -1) {
            return true;
        }
        return false;
    }

    public void stopDragging(int position, float velocity, boolean avoidDismissStart, boolean logMetrics) {
        this.mHandle.setTouching(false, true);
        fling(position, velocity, avoidDismissStart, logMetrics);
        this.mWindowManager.setSlippery(true);
        releaseBackground();
    }

    public void stopDragging(int position, DividerSnapAlgorithm.SnapTarget target, long duration, Interpolator interpolator) {
        stopDragging(position, target, duration, 0L, 0L, interpolator);
    }

    public void stopDragging(int position, DividerSnapAlgorithm.SnapTarget target, long duration, Interpolator interpolator, long endDelay) {
        stopDragging(position, target, duration, 0L, endDelay, interpolator);
    }

    public void stopDragging(int position, DividerSnapAlgorithm.SnapTarget target, long duration, long startDelay, long endDelay, Interpolator interpolator) {
        this.mHandle.setTouching(false, true);
        flingTo(position, target, duration, startDelay, endDelay, interpolator);
        this.mWindowManager.setSlippery(true);
        releaseBackground();
    }

    private void stopDragging() {
        this.mHandle.setTouching(false, true);
        this.mWindowManager.setSlippery(true);
        releaseBackground();
    }

    private void updateDockSide() {
        this.mDockSide = this.mWindowManagerProxy.getDockSide();
        this.mMinimizedShadow.setDockSide(this.mDockSide);
    }

    private void initializeSnapAlgorithm() {
        if (this.mSnapAlgorithm == null) {
            this.mSnapAlgorithm = new DividerSnapAlgorithm(getContext().getResources(), this.mDisplayWidth, this.mDisplayHeight, this.mDividerSize, isHorizontalDivision(), this.mStableInsets, this.mDockSide);
        }
        if (this.mMinimizedSnapAlgorithm == null) {
            this.mMinimizedSnapAlgorithm = new DividerSnapAlgorithm(getContext().getResources(), this.mDisplayWidth, this.mDisplayHeight, this.mDividerSize, isHorizontalDivision(), this.mStableInsets, this.mDockSide, this.mDockedStackMinimized && this.mHomeStackResizable);
        }
    }

    public DividerSnapAlgorithm getSnapAlgorithm() {
        initializeSnapAlgorithm();
        return (this.mDockedStackMinimized && this.mHomeStackResizable) ? this.mMinimizedSnapAlgorithm : this.mSnapAlgorithm;
    }

    public int getCurrentPosition() {
        getLocationOnScreen(this.mTempInt2);
        if (isHorizontalDivision()) {
            return this.mTempInt2[1] + this.mDividerInsets;
        }
        return this.mTempInt2[0] + this.mDividerInsets;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        convertToScreenCoordinates(event);
        int action = event.getAction() & 255;
        switch (action) {
            case 0:
                this.mVelocityTracker = VelocityTracker.obtain();
                this.mVelocityTracker.addMovement(event);
                this.mStartX = (int) event.getX();
                this.mStartY = (int) event.getY();
                boolean result = startDragging(true, true);
                if (!result) {
                    stopDragging();
                }
                this.mStartPosition = getCurrentPosition();
                this.mMoving = false;
                return result;
            case 1:
            case 3:
                this.mVelocityTracker.addMovement(event);
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                this.mVelocityTracker.computeCurrentVelocity(1000);
                int position = calculatePosition(x, y);
                stopDragging(position, isHorizontalDivision() ? this.mVelocityTracker.getYVelocity() : this.mVelocityTracker.getXVelocity(), false, true);
                this.mMoving = false;
                return true;
            case 2:
                this.mVelocityTracker.addMovement(event);
                int x2 = (int) event.getX();
                int y2 = (int) event.getY();
                boolean exceededTouchSlop = (isHorizontalDivision() && Math.abs(y2 - this.mStartY) > this.mTouchSlop) || (!isHorizontalDivision() && Math.abs(x2 - this.mStartX) > this.mTouchSlop);
                if (!this.mMoving && exceededTouchSlop) {
                    this.mStartX = x2;
                    this.mStartY = y2;
                    this.mMoving = true;
                }
                if (this.mMoving && this.mDockSide != -1) {
                    DividerSnapAlgorithm.SnapTarget snapTarget = getSnapAlgorithm().calculateSnapTarget(this.mStartPosition, 0.0f, false);
                    resizeStackDelayed(calculatePosition(x2, y2), this.mStartPosition, snapTarget);
                }
                return true;
            default:
                return true;
        }
    }

    private void logResizeEvent(DividerSnapAlgorithm.SnapTarget snapTarget) {
        if (snapTarget == this.mSnapAlgorithm.getDismissStartTarget()) {
            MetricsLogger.action(this.mContext, 390, dockSideTopLeft(this.mDockSide) ? 1 : 0);
            return;
        }
        if (snapTarget == this.mSnapAlgorithm.getDismissEndTarget()) {
            MetricsLogger.action(this.mContext, 390, dockSideBottomRight(this.mDockSide) ? 1 : 0);
            return;
        }
        if (snapTarget == this.mSnapAlgorithm.getMiddleTarget()) {
            MetricsLogger.action(this.mContext, 389, 0);
            return;
        }
        if (snapTarget == this.mSnapAlgorithm.getFirstSplitTarget()) {
            MetricsLogger.action(this.mContext, 389, dockSideTopLeft(this.mDockSide) ? 1 : 2);
        } else if (snapTarget == this.mSnapAlgorithm.getLastSplitTarget()) {
            MetricsLogger.action(this.mContext, 389, dockSideTopLeft(this.mDockSide) ? 2 : 1);
        }
    }

    private void convertToScreenCoordinates(MotionEvent event) {
        event.setLocation(event.getRawX(), event.getRawY());
    }

    private void fling(int position, float velocity, boolean avoidDismissStart, boolean logMetrics) {
        DividerSnapAlgorithm currentSnapAlgorithm = getSnapAlgorithm();
        DividerSnapAlgorithm.SnapTarget snapTarget = currentSnapAlgorithm.calculateSnapTarget(position, velocity);
        if (avoidDismissStart && snapTarget == currentSnapAlgorithm.getDismissStartTarget()) {
            snapTarget = currentSnapAlgorithm.getFirstSplitTarget();
        }
        if (logMetrics) {
            logResizeEvent(snapTarget);
        }
        ValueAnimator anim = getFlingAnimator(position, snapTarget, 0L);
        this.mFlingAnimationUtils.apply(anim, position, snapTarget.position, velocity);
        anim.start();
    }

    private void flingTo(int position, DividerSnapAlgorithm.SnapTarget target, long duration, long startDelay, long endDelay, Interpolator interpolator) {
        ValueAnimator anim = getFlingAnimator(position, target, endDelay);
        anim.setDuration(duration);
        anim.setStartDelay(startDelay);
        anim.setInterpolator(interpolator);
        anim.start();
    }

    private ValueAnimator getFlingAnimator(int position, final DividerSnapAlgorithm.SnapTarget snapTarget, final long endDelay) {
        if (this.mCurrentAnimator != null) {
            cancelFlingAnimation();
            updateDockSide();
        }
        final boolean taskPositionSameAtEnd = snapTarget.flag == 0;
        ValueAnimator anim = ValueAnimator.ofInt(position, snapTarget.position);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.stackdivider.-$$Lambda$DividerView$o4c7SI5Mz67OwDjq6n3ndBTEfNk
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DividerView.lambda$getFlingAnimator$0(this.f$0, taskPositionSameAtEnd, snapTarget, valueAnimator);
            }
        });
        final Runnable endAction = new Runnable() { // from class: com.android.systemui.stackdivider.-$$Lambda$DividerView$fuKXuLmLQTYwkdmcrCKfS4vkKk8
            @Override // java.lang.Runnable
            public final void run() {
                DividerView.lambda$getFlingAnimator$1(this.f$0, snapTarget);
            }
        };
        final Runnable notCancelledEndAction = new Runnable() { // from class: com.android.systemui.stackdivider.-$$Lambda$DividerView$PQ9UBhgXlQLedw0JrRm_JR-uZkk
            @Override // java.lang.Runnable
            public final void run() {
                DividerView.lambda$getFlingAnimator$2(this.f$0);
            }
        };
        anim.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.stackdivider.DividerView.4
            private boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                DividerView.this.mHandler.removeMessages(0);
                this.mCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                long delay = 0;
                if (endDelay != 0) {
                    delay = endDelay;
                } else if (this.mCancelled) {
                    delay = 0;
                } else if (DividerView.this.mSfChoreographer.getSurfaceFlingerOffsetMs() > 0) {
                    delay = DividerView.this.mSfChoreographer.getSurfaceFlingerOffsetMs();
                }
                if (delay == 0) {
                    if (!this.mCancelled) {
                        notCancelledEndAction.run();
                    }
                    endAction.run();
                } else {
                    if (!this.mCancelled) {
                        DividerView.this.mHandler.postDelayed(notCancelledEndAction, delay);
                    }
                    DividerView.this.mHandler.postDelayed(endAction, delay);
                }
            }
        });
        this.mCurrentAnimator = anim;
        return anim;
    }

    public static /* synthetic */ void lambda$getFlingAnimator$0(DividerView dividerView, boolean taskPositionSameAtEnd, DividerSnapAlgorithm.SnapTarget snapTarget, ValueAnimator animation) {
        int i;
        int iIntValue = ((Integer) animation.getAnimatedValue()).intValue();
        if (taskPositionSameAtEnd && animation.getAnimatedFraction() == 1.0f) {
            i = Integer.MAX_VALUE;
        } else {
            i = snapTarget.taskPosition;
        }
        dividerView.resizeStackDelayed(iIntValue, i, snapTarget);
    }

    public static /* synthetic */ void lambda$getFlingAnimator$1(DividerView dividerView, DividerSnapAlgorithm.SnapTarget snapTarget) {
        DividerSnapAlgorithm.SnapTarget saveTarget;
        dividerView.commitSnapFlags(snapTarget);
        dividerView.mWindowManagerProxy.setResizing(false);
        dividerView.updateDockSide();
        dividerView.mCurrentAnimator = null;
        dividerView.mEntranceAnimationRunning = false;
        dividerView.mExitAnimationRunning = false;
        EventBus.getDefault().send(new StoppedDragingEvent());
        if (dividerView.mHomeStackResizable && !dividerView.mIsInMinimizeInteraction) {
            if (snapTarget.position < 0) {
                saveTarget = dividerView.mSnapAlgorithm.getMiddleTarget();
            } else {
                saveTarget = snapTarget;
            }
            if (saveTarget.position != dividerView.mSnapAlgorithm.getDismissEndTarget().position && saveTarget.position != dividerView.mSnapAlgorithm.getDismissStartTarget().position) {
                dividerView.saveSnapTargetBeforeMinimized(saveTarget);
            }
        }
    }

    public static /* synthetic */ void lambda$getFlingAnimator$2(DividerView dividerView) {
        if (!dividerView.mDockedStackMinimized && dividerView.mIsInMinimizeInteraction) {
            dividerView.mIsInMinimizeInteraction = false;
        }
    }

    private void cancelFlingAnimation() {
        if (this.mCurrentAnimator != null) {
            this.mCurrentAnimator.cancel();
        }
    }

    private void commitSnapFlags(DividerSnapAlgorithm.SnapTarget target) {
        boolean dismissOrMaximize;
        if (target.flag == 0) {
            return;
        }
        boolean z = true;
        if (target.flag == 1) {
            if (this.mDockSide != 1 && this.mDockSide != 2) {
                z = false;
            }
            dismissOrMaximize = z;
        } else {
            dismissOrMaximize = this.mDockSide == 3 || this.mDockSide == 4;
        }
        if (dismissOrMaximize) {
            this.mWindowManagerProxy.dismissDockedStack();
        } else {
            this.mWindowManagerProxy.maximizeDockedStack();
        }
        this.mWindowManagerProxy.setResizeDimLayer(false, 0, 0.0f);
    }

    private void liftBackground() {
        if (this.mBackgroundLifted) {
            return;
        }
        if (isHorizontalDivision()) {
            this.mBackground.animate().scaleY(1.4f);
        } else {
            this.mBackground.animate().scaleX(1.4f);
        }
        this.mBackground.animate().setInterpolator(Interpolators.TOUCH_RESPONSE).setDuration(150L).translationZ(this.mTouchElevation).start();
        this.mHandle.animate().setInterpolator(Interpolators.TOUCH_RESPONSE).setDuration(150L).translationZ(this.mTouchElevation).start();
        this.mBackgroundLifted = true;
    }

    private void releaseBackground() {
        if (!this.mBackgroundLifted) {
            return;
        }
        this.mBackground.animate().setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setDuration(200L).translationZ(0.0f).scaleX(1.0f).scaleY(1.0f).start();
        this.mHandle.animate().setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setDuration(200L).translationZ(0.0f).start();
        this.mBackgroundLifted = false;
    }

    public void setMinimizedDockStack(boolean minimized, boolean isHomeStackResizable) {
        float width;
        this.mHomeStackResizable = isHomeStackResizable;
        updateDockSide();
        if (!minimized) {
            resetBackground();
        } else if (!isHomeStackResizable) {
            if (this.mDockSide != 2) {
                if (this.mDockSide == 1 || this.mDockSide == 3) {
                    View view = this.mBackground;
                    if (this.mDockSide == 1) {
                        width = 0.0f;
                    } else {
                        width = this.mBackground.getWidth();
                    }
                    view.setPivotX(width);
                    this.mBackground.setScaleX(0.0f);
                }
            } else {
                this.mBackground.setPivotY(0.0f);
                this.mBackground.setScaleY(0.0f);
            }
        }
        this.mMinimizedShadow.setAlpha(minimized ? 1.0f : 0.0f);
        if (!isHomeStackResizable) {
            this.mHandle.setAlpha(minimized ? 0.0f : 1.0f);
            this.mDockedStackMinimized = minimized;
            return;
        }
        if (this.mDockedStackMinimized != minimized) {
            this.mDockedStackMinimized = minimized;
            if (this.mDisplayRotation != this.mDefaultDisplay.getRotation()) {
                SystemServicesProxy.getInstance(this.mContext).getStableInsets(this.mStableInsets);
                repositionSnapTargetBeforeMinimized();
                updateDisplayInfo();
            } else {
                this.mMinimizedSnapAlgorithm = null;
                initializeSnapAlgorithm();
            }
            if (this.mIsInMinimizeInteraction != minimized || this.mCurrentAnimator != null) {
                cancelFlingAnimation();
                if (minimized) {
                    requestLayout();
                    this.mIsInMinimizeInteraction = true;
                    resizeStack(this.mMinimizedSnapAlgorithm.getMiddleTarget());
                } else {
                    resizeStack(this.mSnapTargetBeforeMinimized);
                    this.mIsInMinimizeInteraction = false;
                }
            }
        }
    }

    public void setMinimizedDockStack(boolean minimized, long animDuration, boolean isHomeStackResizable) {
        int currentPosition;
        float width;
        this.mHomeStackResizable = isHomeStackResizable;
        updateDockSide();
        if (!isHomeStackResizable) {
            this.mMinimizedShadow.animate().alpha(minimized ? 1.0f : 0.0f).setInterpolator(Interpolators.ALPHA_IN).setDuration(animDuration).start();
            this.mHandle.animate().setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setDuration(animDuration).alpha(minimized ? 0.0f : 1.0f).start();
            if (this.mDockSide != 2) {
                if (this.mDockSide == 1 || this.mDockSide == 3) {
                    View view = this.mBackground;
                    if (this.mDockSide == 1) {
                        width = 0.0f;
                    } else {
                        width = this.mBackground.getWidth();
                    }
                    view.setPivotX(width);
                    this.mBackground.animate().scaleX(minimized ? 0.0f : 1.0f);
                }
            } else {
                this.mBackground.setPivotY(0.0f);
                this.mBackground.animate().scaleY(minimized ? 0.0f : 1.0f);
            }
            this.mDockedStackMinimized = minimized;
        } else if (this.mDockedStackMinimized != minimized) {
            this.mIsInMinimizeInteraction = true;
            this.mMinimizedSnapAlgorithm = null;
            this.mDockedStackMinimized = minimized;
            initializeSnapAlgorithm();
            if (minimized) {
                currentPosition = this.mSnapTargetBeforeMinimized.position;
            } else {
                currentPosition = getCurrentPosition();
            }
            stopDragging(currentPosition, minimized ? this.mMinimizedSnapAlgorithm.getMiddleTarget() : this.mSnapTargetBeforeMinimized, animDuration, Interpolators.FAST_OUT_SLOW_IN, 0L);
            setAdjustedForIme(false, animDuration);
        }
        if (!minimized) {
            this.mBackground.animate().withEndAction(this.mResetBackgroundRunnable);
        }
        this.mBackground.animate().setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setDuration(animDuration).start();
    }

    public void setAdjustedForIme(boolean adjustedForIme) {
        updateDockSide();
        this.mHandle.setAlpha(adjustedForIme ? 0.0f : 1.0f);
        if (!adjustedForIme) {
            resetBackground();
        } else if (this.mDockSide == 2) {
            this.mBackground.setPivotY(0.0f);
            this.mBackground.setScaleY(0.5f);
        }
        this.mAdjustedForIme = adjustedForIme;
    }

    public void setAdjustedForIme(boolean adjustedForIme, long animDuration) {
        updateDockSide();
        this.mHandle.animate().setInterpolator(IME_ADJUST_INTERPOLATOR).setDuration(animDuration).alpha(adjustedForIme ? 0.0f : 1.0f).start();
        if (this.mDockSide == 2) {
            this.mBackground.setPivotY(0.0f);
            this.mBackground.animate().scaleY(adjustedForIme ? 0.5f : 1.0f);
        }
        if (!adjustedForIme) {
            this.mBackground.animate().withEndAction(this.mResetBackgroundRunnable);
        }
        this.mBackground.animate().setInterpolator(IME_ADJUST_INTERPOLATOR).setDuration(animDuration).start();
        this.mAdjustedForIme = adjustedForIme;
    }

    private void saveSnapTargetBeforeMinimized(DividerSnapAlgorithm.SnapTarget target) {
        this.mSnapTargetBeforeMinimized = target;
        this.mState.mRatioPositionBeforeMinimized = target.position / (isHorizontalDivision() ? this.mDisplayHeight : this.mDisplayWidth);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetBackground() {
        this.mBackground.setPivotX(this.mBackground.getWidth() / 2);
        this.mBackground.setPivotY(this.mBackground.getHeight() / 2);
        this.mBackground.setScaleX(1.0f);
        this.mBackground.setScaleY(1.0f);
        this.mMinimizedShadow.setAlpha(0.0f);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateDisplayInfo();
    }

    public void notifyDockSideChanged(int newDockSide) {
        int oldDockSide = this.mDockSide;
        this.mDockSide = newDockSide;
        this.mMinimizedShadow.setDockSide(this.mDockSide);
        requestLayout();
        SystemServicesProxy.getInstance(this.mContext).getStableInsets(this.mStableInsets);
        this.mMinimizedSnapAlgorithm = null;
        initializeSnapAlgorithm();
        if ((oldDockSide == 1 && this.mDockSide == 3) || (oldDockSide == 3 && this.mDockSide == 1)) {
            repositionSnapTargetBeforeMinimized();
        }
        if (this.mHomeStackResizable && this.mDockedStackMinimized) {
            resizeStack(this.mMinimizedSnapAlgorithm.getMiddleTarget());
        }
    }

    private void repositionSnapTargetBeforeMinimized() {
        int position = (int) (this.mState.mRatioPositionBeforeMinimized * (isHorizontalDivision() ? this.mDisplayHeight : this.mDisplayWidth));
        this.mSnapAlgorithm = null;
        initializeSnapAlgorithm();
        this.mSnapTargetBeforeMinimized = this.mSnapAlgorithm.calculateNonDismissingSnapTarget(position);
    }

    private void updateDisplayInfo() {
        this.mDisplayRotation = this.mDefaultDisplay.getRotation();
        DisplayInfo info = new DisplayInfo();
        this.mDefaultDisplay.getDisplayInfo(info);
        this.mDisplayWidth = info.logicalWidth;
        this.mDisplayHeight = info.logicalHeight;
        this.mSnapAlgorithm = null;
        this.mMinimizedSnapAlgorithm = null;
        initializeSnapAlgorithm();
    }

    private int calculatePosition(int touchX, int touchY) {
        return isHorizontalDivision() ? calculateYPosition(touchY) : calculateXPosition(touchX);
    }

    public boolean isHorizontalDivision() {
        return getResources().getConfiguration().orientation == 1;
    }

    private int calculateXPosition(int touchX) {
        return (this.mStartPosition + touchX) - this.mStartX;
    }

    private int calculateYPosition(int touchY) {
        return (this.mStartPosition + touchY) - this.mStartY;
    }

    private void alignTopLeft(Rect containingRect, Rect rect) {
        int width = rect.width();
        int height = rect.height();
        rect.set(containingRect.left, containingRect.top, containingRect.left + width, containingRect.top + height);
    }

    private void alignBottomRight(Rect containingRect, Rect rect) {
        int width = rect.width();
        int height = rect.height();
        rect.set(containingRect.right - width, containingRect.bottom - height, containingRect.right, containingRect.bottom);
    }

    public void calculateBoundsForPosition(int position, int dockSide, Rect outRect) {
        DockedDividerUtils.calculateBoundsForPosition(position, dockSide, outRect, this.mDisplayWidth, this.mDisplayHeight, this.mDividerSize);
    }

    public void resizeStackDelayed(int position, int taskPosition, DividerSnapAlgorithm.SnapTarget taskSnapTarget) {
        Message message = this.mHandler.obtainMessage(0, position, taskPosition, taskSnapTarget);
        message.setAsynchronous(true);
        this.mSfChoreographer.scheduleAtSfVsync(this.mHandler, message);
    }

    private void resizeStack(DividerSnapAlgorithm.SnapTarget taskSnapTarget) {
        resizeStack(taskSnapTarget.position, taskSnapTarget.position, taskSnapTarget);
    }

    public void resizeStack(int position, int taskPosition, DividerSnapAlgorithm.SnapTarget taskSnapTarget) {
        if (this.mRemoved) {
            return;
        }
        calculateBoundsForPosition(position, this.mDockSide, this.mDockedRect);
        if (this.mDockedRect.equals(this.mLastResizeRect) && !this.mEntranceAnimationRunning) {
            return;
        }
        if (this.mBackground.getZ() > 0.0f) {
            this.mBackground.invalidate();
        }
        this.mLastResizeRect.set(this.mDockedRect);
        if (this.mHomeStackResizable && this.mIsInMinimizeInteraction) {
            calculateBoundsForPosition(this.mSnapTargetBeforeMinimized.position, this.mDockSide, this.mDockedTaskRect);
            calculateBoundsForPosition(this.mSnapTargetBeforeMinimized.position, DockedDividerUtils.invertDockSide(this.mDockSide), this.mOtherTaskRect);
            if (this.mDockSide == 3) {
                this.mDockedTaskRect.offset((Math.max(position, this.mStableInsets.left - this.mDividerSize) - this.mDockedTaskRect.left) + this.mDividerSize, 0);
            }
            this.mWindowManagerProxy.resizeDockedStack(this.mDockedRect, this.mDockedTaskRect, this.mDockedTaskRect, this.mOtherTaskRect, null);
            return;
        }
        if (this.mEntranceAnimationRunning && taskPosition != Integer.MAX_VALUE) {
            calculateBoundsForPosition(taskPosition, this.mDockSide, this.mDockedTaskRect);
            if (this.mDockSide == 3) {
                this.mDockedTaskRect.offset((Math.max(position, this.mStableInsets.left - this.mDividerSize) - this.mDockedTaskRect.left) + this.mDividerSize, 0);
            }
            calculateBoundsForPosition(taskPosition, DockedDividerUtils.invertDockSide(this.mDockSide), this.mOtherTaskRect);
            this.mWindowManagerProxy.resizeDockedStack(this.mDockedRect, this.mDockedTaskRect, null, this.mOtherTaskRect, null);
        } else if (this.mExitAnimationRunning && taskPosition != Integer.MAX_VALUE) {
            calculateBoundsForPosition(taskPosition, this.mDockSide, this.mDockedTaskRect);
            this.mDockedInsetRect.set(this.mDockedTaskRect);
            calculateBoundsForPosition(this.mExitStartPosition, DockedDividerUtils.invertDockSide(this.mDockSide), this.mOtherTaskRect);
            this.mOtherInsetRect.set(this.mOtherTaskRect);
            applyExitAnimationParallax(this.mOtherTaskRect, position);
            if (this.mDockSide == 3) {
                this.mDockedTaskRect.offset((position - this.mStableInsets.left) + this.mDividerSize, 0);
            }
            this.mWindowManagerProxy.resizeDockedStack(this.mDockedRect, this.mDockedTaskRect, this.mDockedInsetRect, this.mOtherTaskRect, this.mOtherInsetRect);
        } else if (taskPosition != Integer.MAX_VALUE) {
            calculateBoundsForPosition(position, DockedDividerUtils.invertDockSide(this.mDockSide), this.mOtherRect);
            int dockSideInverted = DockedDividerUtils.invertDockSide(this.mDockSide);
            int taskPositionDocked = restrictDismissingTaskPosition(taskPosition, this.mDockSide, taskSnapTarget);
            int taskPositionOther = restrictDismissingTaskPosition(taskPosition, dockSideInverted, taskSnapTarget);
            calculateBoundsForPosition(taskPositionDocked, this.mDockSide, this.mDockedTaskRect);
            calculateBoundsForPosition(taskPositionOther, dockSideInverted, this.mOtherTaskRect);
            this.mTmpRect.set(0, 0, this.mDisplayWidth, this.mDisplayHeight);
            alignTopLeft(this.mDockedRect, this.mDockedTaskRect);
            alignTopLeft(this.mOtherRect, this.mOtherTaskRect);
            this.mDockedInsetRect.set(this.mDockedTaskRect);
            this.mOtherInsetRect.set(this.mOtherTaskRect);
            if (dockSideTopLeft(this.mDockSide)) {
                alignTopLeft(this.mTmpRect, this.mDockedInsetRect);
                alignBottomRight(this.mTmpRect, this.mOtherInsetRect);
            } else {
                alignBottomRight(this.mTmpRect, this.mDockedInsetRect);
                alignTopLeft(this.mTmpRect, this.mOtherInsetRect);
            }
            applyDismissingParallax(this.mDockedTaskRect, this.mDockSide, taskSnapTarget, position, taskPositionDocked);
            applyDismissingParallax(this.mOtherTaskRect, dockSideInverted, taskSnapTarget, position, taskPositionOther);
            this.mWindowManagerProxy.resizeDockedStack(this.mDockedRect, this.mDockedTaskRect, this.mDockedInsetRect, this.mOtherTaskRect, this.mOtherInsetRect);
        } else {
            this.mWindowManagerProxy.resizeDockedStack(this.mDockedRect, null, null, null, null);
        }
        DividerSnapAlgorithm.SnapTarget closestDismissTarget = getSnapAlgorithm().getClosestDismissTarget(position);
        float dimFraction = getDimFraction(position, closestDismissTarget);
        this.mWindowManagerProxy.setResizeDimLayer(dimFraction != 0.0f, getWindowingModeForDismissTarget(closestDismissTarget), dimFraction);
    }

    private void applyExitAnimationParallax(Rect taskRect, int position) {
        if (this.mDockSide == 2) {
            taskRect.offset(0, (int) ((position - this.mExitStartPosition) * 0.25f));
        } else if (this.mDockSide == 1) {
            taskRect.offset((int) ((position - this.mExitStartPosition) * 0.25f), 0);
        } else if (this.mDockSide == 3) {
            taskRect.offset((int) ((this.mExitStartPosition - position) * 0.25f), 0);
        }
    }

    private float getDimFraction(int position, DividerSnapAlgorithm.SnapTarget dismissTarget) {
        if (this.mEntranceAnimationRunning) {
            return 0.0f;
        }
        float fraction = getSnapAlgorithm().calculateDismissingFraction(position);
        float fraction2 = DIM_INTERPOLATOR.getInterpolation(Math.max(0.0f, Math.min(fraction, 1.0f)));
        if (hasInsetsAtDismissTarget(dismissTarget)) {
            return fraction2 * 0.8f;
        }
        return fraction2;
    }

    private boolean hasInsetsAtDismissTarget(DividerSnapAlgorithm.SnapTarget dismissTarget) {
        if (isHorizontalDivision()) {
            if (dismissTarget == getSnapAlgorithm().getDismissStartTarget()) {
                return this.mStableInsets.top != 0;
            }
            return this.mStableInsets.bottom != 0;
        }
        if (dismissTarget == getSnapAlgorithm().getDismissStartTarget()) {
            return this.mStableInsets.left != 0;
        }
        return this.mStableInsets.right != 0;
    }

    private int restrictDismissingTaskPosition(int taskPosition, int dockSide, DividerSnapAlgorithm.SnapTarget snapTarget) {
        if (snapTarget.flag == 1 && dockSideTopLeft(dockSide)) {
            return Math.max(this.mSnapAlgorithm.getFirstSplitTarget().position, this.mStartPosition);
        }
        if (snapTarget.flag == 2 && dockSideBottomRight(dockSide)) {
            return Math.min(this.mSnapAlgorithm.getLastSplitTarget().position, this.mStartPosition);
        }
        return taskPosition;
    }

    private void applyDismissingParallax(Rect taskRect, int dockSide, DividerSnapAlgorithm.SnapTarget snapTarget, int position, int taskPosition) {
        float fraction = Math.min(1.0f, Math.max(0.0f, this.mSnapAlgorithm.calculateDismissingFraction(position)));
        DividerSnapAlgorithm.SnapTarget dismissTarget = null;
        DividerSnapAlgorithm.SnapTarget splitTarget = null;
        int start = 0;
        if (position <= this.mSnapAlgorithm.getLastSplitTarget().position && dockSideTopLeft(dockSide)) {
            dismissTarget = this.mSnapAlgorithm.getDismissStartTarget();
            splitTarget = this.mSnapAlgorithm.getFirstSplitTarget();
            start = taskPosition;
        } else if (position >= this.mSnapAlgorithm.getLastSplitTarget().position && dockSideBottomRight(dockSide)) {
            dismissTarget = this.mSnapAlgorithm.getDismissEndTarget();
            splitTarget = this.mSnapAlgorithm.getLastSplitTarget();
            start = splitTarget.position;
        }
        if (dismissTarget != null && fraction > 0.0f && isDismissing(splitTarget, position, dockSide)) {
            int offsetPosition = (int) (start + ((dismissTarget.position - splitTarget.position) * calculateParallaxDismissingFraction(fraction, dockSide)));
            int width = taskRect.width();
            int height = taskRect.height();
            switch (dockSide) {
                case 1:
                    taskRect.left = offsetPosition - width;
                    taskRect.right = offsetPosition;
                    break;
                case 2:
                    taskRect.top = offsetPosition - height;
                    taskRect.bottom = offsetPosition;
                    break;
                case 3:
                    taskRect.left = this.mDividerSize + offsetPosition;
                    taskRect.right = offsetPosition + width + this.mDividerSize;
                    break;
                case 4:
                    taskRect.top = this.mDividerSize + offsetPosition;
                    taskRect.bottom = offsetPosition + height + this.mDividerSize;
                    break;
            }
        }
    }

    private static float calculateParallaxDismissingFraction(float fraction, int dockSide) {
        float result = SLOWDOWN_INTERPOLATOR.getInterpolation(fraction) / 3.5f;
        if (dockSide == 2) {
            return result / 2.0f;
        }
        return result;
    }

    private static boolean isDismissing(DividerSnapAlgorithm.SnapTarget snapTarget, int position, int dockSide) {
        if (dockSide == 2 || dockSide == 1) {
            return position < snapTarget.position;
        }
        return position > snapTarget.position;
    }

    private int getWindowingModeForDismissTarget(DividerSnapAlgorithm.SnapTarget dismissTarget) {
        if (dismissTarget.flag != 1 || !dockSideTopLeft(this.mDockSide)) {
            if (dismissTarget.flag == 2 && dockSideBottomRight(this.mDockSide)) {
                return 3;
            }
            return 4;
        }
        return 3;
    }

    private static boolean dockSideTopLeft(int dockSide) {
        return dockSide == 2 || dockSide == 1;
    }

    private static boolean dockSideBottomRight(int dockSide) {
        return dockSide == 4 || dockSide == 3;
    }

    public void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo inoutInfo) {
        inoutInfo.setTouchableInsets(3);
        inoutInfo.touchableRegion.set(this.mHandle.getLeft(), this.mHandle.getTop(), this.mHandle.getRight(), this.mHandle.getBottom());
        inoutInfo.touchableRegion.op(this.mBackground.getLeft(), this.mBackground.getTop(), this.mBackground.getRight(), this.mBackground.getBottom(), Region.Op.UNION);
    }

    public int growsRecents() {
        boolean result = this.mGrowRecents && this.mDockSide == 2 && getCurrentPosition() == getSnapAlgorithm().getLastSplitTarget().position;
        if (result) {
            return getSnapAlgorithm().getMiddleTarget().position;
        }
        return -1;
    }

    public final void onBusEvent(RecentsActivityStartingEvent recentsActivityStartingEvent) {
        if (this.mGrowRecents && this.mDockSide == 2 && getSnapAlgorithm().getMiddleTarget() != getSnapAlgorithm().getLastSplitTarget() && getCurrentPosition() == getSnapAlgorithm().getLastSplitTarget().position) {
            this.mState.growAfterRecentsDrawn = true;
            startDragging(false, false);
        }
    }

    public final void onBusEvent(DockedFirstAnimationFrameEvent event) {
        saveSnapTargetBeforeMinimized(this.mSnapAlgorithm.getMiddleTarget());
    }

    public final void onBusEvent(DockedTopTaskEvent event) {
        if (event.dragMode == -1) {
            this.mState.growAfterRecentsDrawn = false;
            this.mState.animateAfterRecentsDrawn = true;
            startDragging(false, false);
        }
        updateDockSide();
        this.mEntranceAnimationRunning = true;
        resizeStack(calculatePositionForInsetBounds(), this.mSnapAlgorithm.getMiddleTarget().position, this.mSnapAlgorithm.getMiddleTarget());
    }

    public void onRecentsDrawn() {
        updateDockSide();
        final int position = calculatePositionForInsetBounds();
        if (this.mState.animateAfterRecentsDrawn) {
            this.mState.animateAfterRecentsDrawn = false;
            this.mHandler.post(new Runnable() { // from class: com.android.systemui.stackdivider.-$$Lambda$DividerView$6QXub7h9g3mZn2yBpbDKCxY_TW4
                @Override // java.lang.Runnable
                public final void run() {
                    DividerView dividerView = this.f$0;
                    dividerView.stopDragging(position, dividerView.getSnapAlgorithm().getMiddleTarget(), dividerView.mLongPressEntraceAnimDuration, Interpolators.FAST_OUT_SLOW_IN, 200L);
                }
            });
        }
        if (this.mState.growAfterRecentsDrawn) {
            this.mState.growAfterRecentsDrawn = false;
            updateDockSide();
            EventBus.getDefault().send(new RecentsGrowingEvent());
            stopDragging(position, getSnapAlgorithm().getMiddleTarget(), 336L, Interpolators.FAST_OUT_SLOW_IN);
        }
    }

    public final void onBusEvent(UndockingTaskEvent undockingTaskEvent) {
        int dockSide = this.mWindowManagerProxy.getDockSide();
        if (dockSide != -1) {
            if (this.mHomeStackResizable || !this.mDockedStackMinimized) {
                startDragging(false, false);
                DividerSnapAlgorithm.SnapTarget target = dockSideTopLeft(dockSide) ? this.mSnapAlgorithm.getDismissEndTarget() : this.mSnapAlgorithm.getDismissStartTarget();
                this.mExitAnimationRunning = true;
                this.mExitStartPosition = getCurrentPosition();
                stopDragging(this.mExitStartPosition, target, 336L, 100L, 0L, Interpolators.FAST_OUT_SLOW_IN);
            }
        }
    }

    private int calculatePositionForInsetBounds() {
        this.mTmpRect.set(0, 0, this.mDisplayWidth, this.mDisplayHeight);
        this.mTmpRect.inset(this.mStableInsets);
        return DockedDividerUtils.calculatePositionForBounds(this.mTmpRect, this.mDockSide, this.mDividerSize);
    }
}
