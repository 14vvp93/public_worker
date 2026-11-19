package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingManager;
import com.android.systemui.doze.DozeLog;
import com.android.systemui.statusbar.FlingAnimationUtils;
import com.android.systemui.statusbar.VibratorHelper;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes.dex */
public abstract class PanelView extends FrameLayout {
    public static final String TAG = PanelView.class.getSimpleName();
    private boolean mAnimateAfterExpanding;
    private boolean mAnimatingOnDown;
    PanelBar mBar;
    private Interpolator mBounceInterpolator;
    private boolean mClosing;
    private boolean mCollapsedAndHeadsUpOnDown;
    private long mDownTime;
    private boolean mExpandLatencyTracking;
    private float mExpandedFraction;
    protected float mExpandedHeight;
    protected boolean mExpanding;
    private BiConsumer<Float, Boolean> mExpansionListener;
    private FalsingManager mFalsingManager;
    private int mFixedDuration;
    private FlingAnimationUtils mFlingAnimationUtils;
    private FlingAnimationUtils mFlingAnimationUtilsClosing;
    private FlingAnimationUtils mFlingAnimationUtilsDismissing;
    private final Runnable mFlingCollapseRunnable;
    private boolean mGestureWaitForTouchSlop;
    private boolean mHasLayoutedSinceDown;
    protected HeadsUpManagerPhone mHeadsUpManager;
    private ValueAnimator mHeightAnimator;
    protected boolean mHintAnimationRunning;
    private float mHintDistance;
    private boolean mIgnoreXTouchSlop;
    private float mInitialOffsetOnTouch;
    private float mInitialTouchX;
    private float mInitialTouchY;
    private boolean mInstantExpanding;
    private boolean mJustPeeked;
    protected KeyguardBottomAreaView mKeyguardBottomArea;
    protected boolean mLaunchingNotification;
    private LockscreenGestureLogger mLockscreenGestureLogger;
    private float mMinExpandHeight;
    private boolean mMotionAborted;
    private float mNextCollapseSpeedUpFactor;
    private boolean mNotificationsDragEnabled;
    private boolean mOverExpandedBeforeFling;
    private boolean mPanelClosedOnDown;
    private boolean mPanelUpdateWhenAnimatorEnds;
    private ObjectAnimator mPeekAnimator;
    private float mPeekHeight;
    private boolean mPeekTouching;
    protected final Runnable mPostCollapseRunnable;
    protected StatusBar mStatusBar;
    private boolean mTouchAboveFalsingThreshold;
    private boolean mTouchDisabled;
    protected int mTouchSlop;
    private boolean mTouchSlopExceeded;
    private boolean mTouchStartedInEmptyArea;
    protected boolean mTracking;
    private int mTrackingPointer;
    private int mUnlockFalsingThreshold;
    private boolean mUpdateFlingOnLayout;
    private float mUpdateFlingVelocity;
    private boolean mUpwardsWhenTresholdReached;
    private VelocityTrackerInterface mVelocityTracker;
    private boolean mVibrateOnOpening;
    private final VibratorHelper mVibratorHelper;
    private String mViewName;

    protected abstract boolean fullyExpandedClearAllVisible();

    protected abstract int getClearAllHeight();

    protected abstract int getMaxPanelHeight();

    protected abstract float getOpeningHeight();

    protected abstract float getOverExpansionAmount();

    protected abstract float getOverExpansionPixels();

    protected abstract float getPeekHeight();

    protected abstract boolean hasConflictingGestures();

    protected abstract boolean isClearAllVisible();

    protected abstract boolean isInContentBounds(float f, float f2);

    protected abstract boolean isPanelVisibleBecauseOfHeadsUp();

    protected abstract boolean isTrackingBlocked();

    protected abstract void onHeightUpdated(float f);

    protected abstract boolean onMiddleClicked();

    public abstract void resetViews();

    protected abstract void setOverExpansion(float f, boolean z);

    protected abstract boolean shouldGestureIgnoreXTouchSlop(float f, float f2);

    protected abstract boolean shouldUseDismissingAnimation();

    protected void onExpandingFinished() {
        this.mBar.onExpandingFinished();
    }

    protected void onExpandingStarted() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyExpandingStarted() {
        if (!this.mExpanding) {
            this.mExpanding = true;
            onExpandingStarted();
        }
    }

    protected final void notifyExpandingFinished() {
        endClosing();
        if (this.mExpanding) {
            this.mExpanding = false;
            onExpandingFinished();
        }
    }

    private void runPeekAnimation(long duration, float peekHeight, final boolean collapseWhenFinished) {
        this.mPeekHeight = peekHeight;
        if (this.mHeightAnimator != null) {
            return;
        }
        if (this.mPeekAnimator != null) {
            this.mPeekAnimator.cancel();
        }
        this.mPeekAnimator = ObjectAnimator.ofFloat(this, "expandedHeight", this.mPeekHeight).setDuration(duration);
        this.mPeekAnimator.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        this.mPeekAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.PanelView.1
            private boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                this.mCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                PanelView.this.mPeekAnimator = null;
                if (!this.mCancelled && collapseWhenFinished) {
                    PanelView.this.postOnAnimation(PanelView.this.mPostCollapseRunnable);
                }
            }
        });
        notifyExpandingStarted();
        this.mPeekAnimator.start();
        this.mJustPeeked = true;
    }

    public PanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mLockscreenGestureLogger = new LockscreenGestureLogger();
        this.mFixedDuration = -1;
        this.mExpandedFraction = 0.0f;
        this.mExpandedHeight = 0.0f;
        this.mNextCollapseSpeedUpFactor = 1.0f;
        this.mFlingCollapseRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.PanelView.3
            @Override // java.lang.Runnable
            public void run() {
                PanelView.this.fling(0.0f, false, PanelView.this.mNextCollapseSpeedUpFactor, false);
            }
        };
        this.mPostCollapseRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.PanelView.8
            @Override // java.lang.Runnable
            public void run() {
                PanelView.this.collapse(false, 1.0f);
            }
        };
        this.mFlingAnimationUtils = new FlingAnimationUtils(context, 0.6f, 0.6f);
        this.mFlingAnimationUtilsClosing = new FlingAnimationUtils(context, 0.5f, 0.6f);
        this.mFlingAnimationUtilsDismissing = new FlingAnimationUtils(context, 0.5f, 0.2f, 0.6f, 0.84f);
        this.mBounceInterpolator = new BounceInterpolator();
        this.mFalsingManager = FalsingManager.getInstance(context);
        this.mNotificationsDragEnabled = getResources().getBoolean(R.bool.config_enableNotificationShadeDrag);
        this.mVibratorHelper = (VibratorHelper) Dependency.get(VibratorHelper.class);
        this.mVibrateOnOpening = this.mContext.getResources().getBoolean(R.bool.config_vibrateOnIconAnimation);
    }

    protected void loadDimens() {
        Resources res = getContext().getResources();
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        this.mTouchSlop = configuration.getScaledTouchSlop();
        this.mHintDistance = res.getDimension(R.dimen.hint_move_distance);
        this.mUnlockFalsingThreshold = res.getDimensionPixelSize(R.dimen.unlock_falsing_threshold);
    }

    private void trackMovement(MotionEvent event) {
        float deltaX = event.getRawX() - event.getX();
        float deltaY = event.getRawY() - event.getY();
        event.offsetLocation(deltaX, deltaY);
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(event);
        }
        event.offsetLocation(-deltaX, -deltaY);
    }

    public void setTouchDisabled(boolean disabled) {
        this.mTouchDisabled = disabled;
        if (this.mTouchDisabled) {
            cancelHeightAnimator();
            if (this.mTracking) {
                onTrackingStopped(true);
            }
            notifyExpandingFinished();
        }
    }

    public void startExpandLatencyTracking() {
        if (LatencyTracker.isEnabled(this.mContext)) {
            LatencyTracker.getInstance(this.mContext).onActionStart(0);
            this.mExpandLatencyTracking = true;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mInstantExpanding || ((this.mTouchDisabled && event.getActionMasked() != 3) || (this.mMotionAborted && event.getActionMasked() != 0))) {
            return false;
        }
        if (!this.mNotificationsDragEnabled) {
            if (this.mTracking) {
                onTrackingStopped(true);
            }
            return false;
        }
        if (isFullyCollapsed() && event.isFromSource(8194)) {
            if (event.getAction() == 1) {
                expand(true);
            }
            return true;
        }
        int pointerIndex = event.findPointerIndex(this.mTrackingPointer);
        if (pointerIndex < 0) {
            pointerIndex = 0;
            this.mTrackingPointer = event.getPointerId(0);
        }
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);
        if (event.getActionMasked() == 0) {
            this.mGestureWaitForTouchSlop = isFullyCollapsed() || hasConflictingGestures();
            this.mIgnoreXTouchSlop = isFullyCollapsed() || shouldGestureIgnoreXTouchSlop(x, y);
        }
        switch (event.getActionMasked()) {
            case 0:
                startExpandMotion(x, y, false, this.mExpandedHeight);
                this.mJustPeeked = false;
                this.mMinExpandHeight = 0.0f;
                this.mPanelClosedOnDown = isFullyCollapsed();
                this.mHasLayoutedSinceDown = false;
                this.mUpdateFlingOnLayout = false;
                this.mMotionAborted = false;
                this.mPeekTouching = this.mPanelClosedOnDown;
                this.mDownTime = SystemClock.uptimeMillis();
                this.mTouchAboveFalsingThreshold = false;
                this.mCollapsedAndHeadsUpOnDown = isFullyCollapsed() && this.mHeadsUpManager.hasPinnedHeadsUp();
                if (this.mVelocityTracker == null) {
                    initVelocityTracker();
                }
                trackMovement(event);
                if (!this.mGestureWaitForTouchSlop || ((this.mHeightAnimator != null && !this.mHintAnimationRunning) || this.mPeekAnimator != null)) {
                    this.mTouchSlopExceeded = ((this.mHeightAnimator == null || this.mHintAnimationRunning) && this.mPeekAnimator == null) ? false : true;
                    cancelHeightAnimator();
                    cancelPeek();
                    onTrackingStarted();
                }
                if (isFullyCollapsed() && !this.mHeadsUpManager.hasPinnedHeadsUp() && !this.mStatusBar.isBouncerShowing()) {
                    startOpening(event);
                }
                break;
            case 1:
            case 3:
                trackMovement(event);
                endMotionEvent(event, x, y, false);
                break;
            case 2:
                trackMovement(event);
                float h = y - this.mInitialTouchY;
                if (Math.abs(h) > this.mTouchSlop && (Math.abs(h) > Math.abs(x - this.mInitialTouchX) || this.mIgnoreXTouchSlop)) {
                    this.mTouchSlopExceeded = true;
                    if (this.mGestureWaitForTouchSlop && !this.mTracking && !this.mCollapsedAndHeadsUpOnDown) {
                        if (!this.mJustPeeked && this.mInitialOffsetOnTouch != 0.0f) {
                            startExpandMotion(x, y, false, this.mExpandedHeight);
                            h = 0.0f;
                        }
                        cancelHeightAnimator();
                        onTrackingStarted();
                    }
                }
                float newHeight = Math.max(0.0f, this.mInitialOffsetOnTouch + h);
                if (newHeight > this.mPeekHeight) {
                    if (this.mPeekAnimator != null) {
                        this.mPeekAnimator.cancel();
                    }
                    this.mJustPeeked = false;
                } else if (this.mPeekAnimator == null && this.mJustPeeked) {
                    this.mInitialOffsetOnTouch = this.mExpandedHeight;
                    this.mInitialTouchY = y;
                    this.mMinExpandHeight = this.mExpandedHeight;
                    this.mJustPeeked = false;
                }
                float newHeight2 = Math.max(newHeight, this.mMinExpandHeight);
                if ((-h) >= getFalsingThreshold()) {
                    this.mTouchAboveFalsingThreshold = true;
                    this.mUpwardsWhenTresholdReached = isDirectionUpwards(x, y);
                }
                if (!this.mJustPeeked && ((!this.mGestureWaitForTouchSlop || this.mTracking) && !isTrackingBlocked())) {
                    setExpandedHeightInternal(newHeight2);
                }
                break;
            case 5:
                if (this.mStatusBar.getBarState() == 1) {
                    this.mMotionAborted = true;
                    endMotionEvent(event, x, y, true);
                    return false;
                }
                break;
            case 6:
                int upPointer = event.getPointerId(event.getActionIndex());
                if (this.mTrackingPointer == upPointer) {
                    int newIndex = event.getPointerId(0) != upPointer ? 0 : 1;
                    float newY = event.getY(newIndex);
                    float newX = event.getX(newIndex);
                    this.mTrackingPointer = event.getPointerId(newIndex);
                    startExpandMotion(newX, newY, true, this.mExpandedHeight);
                }
                break;
        }
        return !this.mGestureWaitForTouchSlop || this.mTracking;
    }

    private void startOpening(MotionEvent event) {
        runPeekAnimation(200L, getOpeningHeight(), false);
        notifyBarPanelExpansionChanged();
        if (this.mVibrateOnOpening) {
            this.mVibratorHelper.vibrate(2);
        }
        float width = this.mStatusBar.getDisplayWidth();
        float height = this.mStatusBar.getDisplayHeight();
        int rot = this.mStatusBar.getRotation();
        this.mLockscreenGestureLogger.writeAtFractionalPosition(1328, (int) ((event.getX() / width) * 100.0f), (int) ((event.getY() / height) * 100.0f), rot);
    }

    private boolean isDirectionUpwards(float x, float y) {
        float xDiff = x - this.mInitialTouchX;
        float yDiff = y - this.mInitialTouchY;
        return yDiff < 0.0f && Math.abs(yDiff) >= Math.abs(xDiff);
    }

    protected void startExpandingFromPeek() {
        this.mStatusBar.handlePeekToExpandTransistion();
    }

    protected void startExpandMotion(float newX, float newY, boolean startTracking, float expandedHeight) {
        this.mInitialOffsetOnTouch = expandedHeight;
        this.mInitialTouchY = newY;
        this.mInitialTouchX = newX;
        if (startTracking) {
            this.mTouchSlopExceeded = true;
            setExpandedHeight(this.mInitialOffsetOnTouch);
            onTrackingStarted();
        }
    }

    private void endMotionEvent(MotionEvent event, float x, float y, boolean forceCancel) {
        this.mTrackingPointer = -1;
        if ((this.mTracking && this.mTouchSlopExceeded) || Math.abs(x - this.mInitialTouchX) > this.mTouchSlop || Math.abs(y - this.mInitialTouchY) > this.mTouchSlop || event.getActionMasked() == 3 || forceCancel) {
            float vel = 0.0f;
            float vectorVel = 0.0f;
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.computeCurrentVelocity(1000);
                vel = this.mVelocityTracker.getYVelocity();
                vectorVel = (float) Math.hypot(this.mVelocityTracker.getXVelocity(), this.mVelocityTracker.getYVelocity());
            }
            boolean expand = flingExpands(vel, vectorVel, x, y) || event.getActionMasked() == 3 || forceCancel;
            DozeLog.traceFling(expand, this.mTouchAboveFalsingThreshold, this.mStatusBar.isFalsingThresholdNeeded(), this.mStatusBar.isWakeUpComingFromTouch());
            if (!expand && this.mStatusBar.getBarState() == 1) {
                float displayDensity = this.mStatusBar.getDisplayDensity();
                int heightDp = (int) Math.abs((y - this.mInitialTouchY) / displayDensity);
                int velocityDp = (int) Math.abs(vel / displayDensity);
                this.mLockscreenGestureLogger.write(186, heightDp, velocityDp);
            }
            fling(vel, expand, isFalseTouch(x, y));
            onTrackingStopped(expand);
            this.mUpdateFlingOnLayout = expand && this.mPanelClosedOnDown && !this.mHasLayoutedSinceDown;
            if (this.mUpdateFlingOnLayout) {
                this.mUpdateFlingVelocity = vel;
            }
        } else if (this.mPanelClosedOnDown && !this.mHeadsUpManager.hasPinnedHeadsUp() && !this.mTracking && !this.mStatusBar.isBouncerShowing() && !this.mStatusBar.isKeyguardFadingAway()) {
            long timePassed = SystemClock.uptimeMillis() - this.mDownTime;
            if (timePassed < ViewConfiguration.getLongPressTimeout()) {
                runPeekAnimation(360L, getPeekHeight(), true);
            } else {
                postOnAnimation(this.mPostCollapseRunnable);
            }
        } else if (!this.mStatusBar.isBouncerShowing()) {
            boolean expands = onEmptySpaceClick(this.mInitialTouchX);
            onTrackingStopped(expands);
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        this.mPeekTouching = false;
    }

    protected float getCurrentExpandVelocity() {
        if (this.mVelocityTracker == null) {
            return 0.0f;
        }
        this.mVelocityTracker.computeCurrentVelocity(1000);
        return this.mVelocityTracker.getYVelocity();
    }

    private int getFalsingThreshold() {
        float factor = this.mStatusBar.isWakeUpComingFromTouch() ? 1.5f : 1.0f;
        return (int) (this.mUnlockFalsingThreshold * factor);
    }

    protected void onTrackingStopped(boolean expand) {
        this.mTracking = false;
        this.mBar.onTrackingStopped(expand);
        notifyBarPanelExpansionChanged();
    }

    protected void onTrackingStarted() {
        endClosing();
        this.mTracking = true;
        this.mBar.onTrackingStarted();
        notifyExpandingStarted();
        notifyBarPanelExpansionChanged();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.mInstantExpanding || !this.mNotificationsDragEnabled || this.mTouchDisabled || (this.mMotionAborted && event.getActionMasked() != 0)) {
            return false;
        }
        int pointerIndex = event.findPointerIndex(this.mTrackingPointer);
        if (pointerIndex < 0) {
            pointerIndex = 0;
            this.mTrackingPointer = event.getPointerId(0);
        }
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);
        boolean scrolledToBottom = isScrolledToBottom();
        switch (event.getActionMasked()) {
            case 0:
                this.mStatusBar.userActivity();
                this.mAnimatingOnDown = this.mHeightAnimator != null;
                this.mMinExpandHeight = 0.0f;
                this.mDownTime = SystemClock.uptimeMillis();
                if ((this.mAnimatingOnDown && this.mClosing && !this.mHintAnimationRunning) || this.mPeekAnimator != null) {
                    cancelHeightAnimator();
                    cancelPeek();
                    this.mTouchSlopExceeded = true;
                    return true;
                }
                this.mInitialTouchY = y;
                this.mInitialTouchX = x;
                this.mTouchStartedInEmptyArea = !isInContentBounds(x, y);
                this.mTouchSlopExceeded = false;
                this.mJustPeeked = false;
                this.mMotionAborted = false;
                this.mPanelClosedOnDown = isFullyCollapsed();
                this.mCollapsedAndHeadsUpOnDown = false;
                this.mHasLayoutedSinceDown = false;
                this.mUpdateFlingOnLayout = false;
                this.mTouchAboveFalsingThreshold = false;
                initVelocityTracker();
                trackMovement(event);
                return false;
            case 1:
            case 3:
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                }
                return false;
            case 2:
                float h = y - this.mInitialTouchY;
                trackMovement(event);
                if (scrolledToBottom || this.mTouchStartedInEmptyArea || this.mAnimatingOnDown) {
                    float hAbs = Math.abs(h);
                    if ((h < (-this.mTouchSlop) || (this.mAnimatingOnDown && hAbs > this.mTouchSlop)) && hAbs > Math.abs(x - this.mInitialTouchX)) {
                        cancelHeightAnimator();
                        startExpandMotion(x, y, true, this.mExpandedHeight);
                        return true;
                    }
                }
                return false;
            case 4:
            default:
                return false;
            case 5:
                if (this.mStatusBar.getBarState() == 1) {
                    this.mMotionAborted = true;
                    if (this.mVelocityTracker != null) {
                        this.mVelocityTracker.recycle();
                        this.mVelocityTracker = null;
                    }
                }
                return false;
            case 6:
                int upPointer = event.getPointerId(event.getActionIndex());
                if (this.mTrackingPointer == upPointer) {
                    int newIndex = event.getPointerId(0) != upPointer ? 0 : 1;
                    this.mTrackingPointer = event.getPointerId(newIndex);
                    this.mInitialTouchX = event.getX(newIndex);
                    this.mInitialTouchY = event.getY(newIndex);
                }
                return false;
        }
    }

    protected void cancelHeightAnimator() {
        if (this.mHeightAnimator != null) {
            if (this.mHeightAnimator.isRunning()) {
                this.mPanelUpdateWhenAnimatorEnds = false;
            }
            this.mHeightAnimator.cancel();
        }
        endClosing();
    }

    private void endClosing() {
        if (this.mClosing) {
            this.mClosing = false;
            onClosingFinished();
        }
    }

    private void initVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
        }
        this.mVelocityTracker = VelocityTrackerFactory.obtain(getContext());
    }

    protected boolean isScrolledToBottom() {
        return true;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        loadDimens();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        loadDimens();
    }

    protected boolean flingExpands(float vel, float vectorVel, float x, float y) {
        if (isFalseTouch(x, y)) {
            return true;
        }
        if (Math.abs(vectorVel) < this.mFlingAnimationUtils.getMinVelocityPxPerSecond()) {
            return getExpandedFraction() > 0.5f;
        }
        return vel > 0.0f;
    }

    private boolean isFalseTouch(float x, float y) {
        if (!this.mStatusBar.isFalsingThresholdNeeded()) {
            return false;
        }
        if (this.mFalsingManager.isClassiferEnabled()) {
            return this.mFalsingManager.isFalseTouch();
        }
        if (!this.mTouchAboveFalsingThreshold) {
            return true;
        }
        if (this.mUpwardsWhenTresholdReached) {
            return false;
        }
        return !isDirectionUpwards(x, y);
    }

    protected void fling(float vel, boolean expand) {
        fling(vel, expand, 1.0f, false);
    }

    protected void fling(float vel, boolean expand, boolean expandBecauseOfFalsing) {
        fling(vel, expand, 1.0f, expandBecauseOfFalsing);
    }

    protected void fling(float vel, boolean expand, float collapseSpeedUpFactor, boolean expandBecauseOfFalsing) {
        cancelPeek();
        float target = expand ? getMaxPanelHeight() : 0.0f;
        if (!expand) {
            this.mClosing = true;
        }
        flingToHeight(vel, expand, target, collapseSpeedUpFactor, expandBecauseOfFalsing);
    }

    protected void flingToHeight(float vel, boolean expand, float target, float collapseSpeedUpFactor, boolean expandBecauseOfFalsing) {
        float target2;
        ValueAnimator animator;
        float vel2;
        final boolean clearAllExpandHack = expand && fullyExpandedClearAllVisible() && this.mExpandedHeight < ((float) (getMaxPanelHeight() - getClearAllHeight())) && !isClearAllVisible();
        if (clearAllExpandHack) {
            float target3 = getMaxPanelHeight() - getClearAllHeight();
            target2 = target3;
        } else {
            target2 = target;
        }
        if (target2 == this.mExpandedHeight || (getOverExpansionAmount() > 0.0f && expand)) {
            notifyExpandingFinished();
            return;
        }
        this.mOverExpandedBeforeFling = getOverExpansionAmount() > 0.0f;
        ValueAnimator animator2 = createHeightAnimator(target2);
        if (expand) {
            if (expandBecauseOfFalsing && vel < 0.0f) {
                vel2 = 0.0f;
            } else {
                vel2 = vel;
            }
            this.mFlingAnimationUtils.apply(animator2, this.mExpandedHeight, target2, vel2, getHeight());
            if (vel2 == 0.0f) {
                animator2.setDuration(350L);
            }
            animator = animator2;
        } else {
            if (!shouldUseDismissingAnimation()) {
                animator = animator2;
                this.mFlingAnimationUtilsClosing.apply(animator, this.mExpandedHeight, target2, vel, getHeight());
                animator.setDuration(48L);
            } else if (vel == 0.0f) {
                animator2.setInterpolator(Interpolators.PANEL_CLOSE_ACCELERATED);
                long duration = (long) (200.0f + ((this.mExpandedHeight / getHeight()) * 100.0f));
                animator2.setDuration(duration);
                animator = animator2;
            } else {
                FlingAnimationUtils flingAnimationUtils = this.mFlingAnimationUtilsDismissing;
                float f = this.mExpandedHeight;
                float target4 = getHeight();
                animator = animator2;
                flingAnimationUtils.apply(animator2, f, target2, vel, target4);
                animator.setDuration(48L);
            }
            if (vel == 0.0f) {
                animator.setDuration((long) (animator.getDuration() / collapseSpeedUpFactor));
            }
            if (this.mFixedDuration != -1) {
                animator.setDuration(this.mFixedDuration);
            }
        }
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.PanelView.2
            private boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                this.mCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (clearAllExpandHack && !this.mCancelled) {
                    PanelView.this.setExpandedHeightInternal(PanelView.this.getMaxPanelHeight());
                }
                PanelView.this.setAnimator(null);
                if (!this.mCancelled) {
                    PanelView.this.notifyExpandingFinished();
                }
                PanelView.this.notifyBarPanelExpansionChanged();
            }
        });
        setAnimator(animator);
        animator.start();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mViewName = getResources().getResourceName(getId());
    }

    public void setExpandedHeight(float height) {
        setExpandedHeightInternal(getOverExpansionPixels() + height);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.mStatusBar.onPanelLaidOut();
        requestPanelHeightUpdate();
        this.mHasLayoutedSinceDown = true;
        if (this.mUpdateFlingOnLayout) {
            abortAnimations();
            fling(this.mUpdateFlingVelocity, true);
            this.mUpdateFlingOnLayout = false;
        }
    }

    protected void requestPanelHeightUpdate() {
        float currentMaxPanelHeight = getMaxPanelHeight();
        if (isFullyCollapsed() || currentMaxPanelHeight == this.mExpandedHeight || this.mPeekAnimator != null || this.mPeekTouching) {
            return;
        }
        if (this.mTracking && !isTrackingBlocked()) {
            return;
        }
        if (this.mHeightAnimator != null) {
            this.mPanelUpdateWhenAnimatorEnds = true;
        } else {
            setExpandedHeight(currentMaxPanelHeight);
        }
    }

    public void setExpandedHeightInternal(float h) {
        if (this.mExpandLatencyTracking && h != 0.0f) {
            DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$PanelView$NcQsgCLFImw9_GKeELFJ1HpKiII
                @Override // java.lang.Runnable
                public final void run() {
                    LatencyTracker.getInstance(this.f$0.mContext).onActionEnd(0);
                }
            });
            this.mExpandLatencyTracking = false;
        }
        float fhWithoutOverExpansion = getMaxPanelHeight() - getOverExpansionAmount();
        if (this.mHeightAnimator == null) {
            float overExpansionPixels = Math.max(0.0f, h - fhWithoutOverExpansion);
            if (getOverExpansionPixels() != overExpansionPixels && this.mTracking) {
                setOverExpansion(overExpansionPixels, true);
            }
            this.mExpandedHeight = Math.min(h, fhWithoutOverExpansion) + getOverExpansionAmount();
        } else {
            this.mExpandedHeight = h;
            if (this.mOverExpandedBeforeFling) {
                setOverExpansion(Math.max(0.0f, h - fhWithoutOverExpansion), false);
            }
        }
        if (this.mExpandedHeight < 1.0f && this.mExpandedHeight != 0.0f && this.mClosing) {
            this.mExpandedHeight = 0.0f;
            if (this.mHeightAnimator != null) {
                this.mHeightAnimator.end();
            }
        }
        this.mExpandedFraction = Math.min(1.0f, fhWithoutOverExpansion != 0.0f ? this.mExpandedHeight / fhWithoutOverExpansion : 0.0f);
        onHeightUpdated(this.mExpandedHeight);
        notifyBarPanelExpansionChanged();
    }

    public void setExpandedFraction(float frac) {
        setExpandedHeight(getMaxPanelHeight() * frac);
    }

    public float getExpandedHeight() {
        return this.mExpandedHeight;
    }

    public float getExpandedFraction() {
        return this.mExpandedFraction;
    }

    public boolean isFullyExpanded() {
        return this.mExpandedHeight >= ((float) getMaxPanelHeight());
    }

    public boolean isFullyCollapsed() {
        return this.mExpandedFraction <= 0.0f;
    }

    public boolean isCollapsing() {
        return this.mClosing || this.mLaunchingNotification;
    }

    public boolean isTracking() {
        return this.mTracking;
    }

    public void setBar(PanelBar panelBar) {
        this.mBar = panelBar;
    }

    public void collapse(boolean delayed, float speedUpFactor) {
        if (canPanelBeCollapsed()) {
            cancelHeightAnimator();
            notifyExpandingStarted();
            this.mClosing = true;
            if (delayed) {
                this.mNextCollapseSpeedUpFactor = speedUpFactor;
                postDelayed(this.mFlingCollapseRunnable, 120L);
            } else {
                fling(0.0f, false, speedUpFactor, false);
            }
        }
    }

    public boolean canPanelBeCollapsed() {
        return (isFullyCollapsed() || this.mTracking || this.mClosing) ? false : true;
    }

    public void cancelPeek() {
        boolean cancelled = false;
        if (this.mPeekAnimator != null) {
            cancelled = true;
            this.mPeekAnimator.cancel();
        }
        if (cancelled) {
            notifyBarPanelExpansionChanged();
        }
    }

    public void expand(boolean animate) {
        if (!isFullyCollapsed() && !isCollapsing()) {
            return;
        }
        this.mInstantExpanding = true;
        this.mAnimateAfterExpanding = animate;
        this.mUpdateFlingOnLayout = false;
        abortAnimations();
        cancelPeek();
        if (this.mTracking) {
            onTrackingStopped(true);
        }
        if (this.mExpanding) {
            notifyExpandingFinished();
        }
        notifyBarPanelExpansionChanged();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.statusbar.phone.PanelView.4
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (!PanelView.this.mInstantExpanding) {
                    PanelView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    return;
                }
                if (PanelView.this.mStatusBar.getStatusBarWindow().getHeight() != PanelView.this.mStatusBar.getStatusBarHeight()) {
                    PanelView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    if (PanelView.this.mAnimateAfterExpanding) {
                        PanelView.this.notifyExpandingStarted();
                        PanelView.this.fling(0.0f, true);
                    } else {
                        PanelView.this.setExpandedFraction(1.0f);
                    }
                    PanelView.this.mInstantExpanding = false;
                }
            }
        });
        requestLayout();
    }

    public void instantCollapse() {
        abortAnimations();
        setExpandedFraction(0.0f);
        if (this.mExpanding) {
            notifyExpandingFinished();
        }
        if (this.mInstantExpanding) {
            this.mInstantExpanding = false;
            notifyBarPanelExpansionChanged();
        }
    }

    private void abortAnimations() {
        cancelPeek();
        cancelHeightAnimator();
        removeCallbacks(this.mPostCollapseRunnable);
        removeCallbacks(this.mFlingCollapseRunnable);
    }

    protected void onClosingFinished() {
        this.mBar.onClosingFinished();
    }

    protected void startUnlockHintAnimation() {
        if (this.mHeightAnimator != null || this.mTracking) {
            return;
        }
        cancelPeek();
        notifyExpandingStarted();
        startUnlockHintAnimationPhase1(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$PanelView$tAljekoGx9mlKIleW6Fmi59MCOs
            @Override // java.lang.Runnable
            public final void run() {
                PanelView.lambda$startUnlockHintAnimation$1(this.f$0);
            }
        });
        onUnlockHintStarted();
        this.mHintAnimationRunning = true;
    }

    public static /* synthetic */ void lambda$startUnlockHintAnimation$1(PanelView panelView) {
        panelView.notifyExpandingFinished();
        panelView.onUnlockHintFinished();
        panelView.mHintAnimationRunning = false;
    }

    protected void onUnlockHintFinished() {
        this.mStatusBar.onHintFinished();
    }

    protected void onUnlockHintStarted() {
        this.mStatusBar.onUnlockHintStarted();
    }

    public boolean isUnlockHintRunning() {
        return this.mHintAnimationRunning;
    }

    private void startUnlockHintAnimationPhase1(final Runnable onAnimationFinished) {
        float target = Math.max(0.0f, getMaxPanelHeight() - this.mHintDistance);
        ValueAnimator animator = createHeightAnimator(target);
        animator.setDuration(250L);
        animator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.PanelView.5
            private boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                this.mCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (this.mCancelled) {
                    PanelView.this.setAnimator(null);
                    onAnimationFinished.run();
                } else {
                    PanelView.this.startUnlockHintAnimationPhase2(onAnimationFinished);
                }
            }
        });
        animator.start();
        setAnimator(animator);
        View[] viewsToAnimate = {this.mKeyguardBottomArea.getIndicationArea(), this.mStatusBar.getAmbientIndicationContainer()};
        for (final View v : viewsToAnimate) {
            if (v != null) {
                v.animate().translationY(-this.mHintDistance).setDuration(250L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$PanelView$NxwxpLj3ZElyZw-bMmniBqBlhdY
                    @Override // java.lang.Runnable
                    public final void run() {
                        v.animate().translationY(0.0f).setDuration(450L).setInterpolator(this.f$0.mBounceInterpolator).start();
                    }
                }).start();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAnimator(ValueAnimator animator) {
        this.mHeightAnimator = animator;
        if (animator == null && this.mPanelUpdateWhenAnimatorEnds) {
            this.mPanelUpdateWhenAnimatorEnds = false;
            requestPanelHeightUpdate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startUnlockHintAnimationPhase2(final Runnable onAnimationFinished) {
        ValueAnimator animator = createHeightAnimator(getMaxPanelHeight());
        animator.setDuration(450L);
        animator.setInterpolator(this.mBounceInterpolator);
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.PanelView.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                PanelView.this.setAnimator(null);
                onAnimationFinished.run();
                PanelView.this.notifyBarPanelExpansionChanged();
            }
        });
        animator.start();
        setAnimator(animator);
    }

    private ValueAnimator createHeightAnimator(float targetHeight) {
        ValueAnimator animator = ValueAnimator.ofFloat(this.mExpandedHeight, targetHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.PanelView.7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                PanelView.this.setExpandedHeightInternal(((Float) animation.getAnimatedValue()).floatValue());
            }
        });
        return animator;
    }

    protected void notifyBarPanelExpansionChanged() {
        this.mBar.panelExpansionChanged(this.mExpandedFraction, this.mExpandedFraction > 0.0f || this.mPeekAnimator != null || this.mInstantExpanding || isPanelVisibleBecauseOfHeadsUp() || this.mTracking || this.mHeightAnimator != null);
        if (this.mExpansionListener != null) {
            this.mExpansionListener.accept(Float.valueOf(this.mExpandedFraction), Boolean.valueOf(this.mTracking));
        }
    }

    public void setExpansionListener(BiConsumer<Float, Boolean> consumer) {
        this.mExpansionListener = consumer;
    }

    protected boolean onEmptySpaceClick(float x) {
        if (this.mHintAnimationRunning) {
            return true;
        }
        return onMiddleClicked();
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        Object[] objArr = new Object[11];
        objArr[0] = getClass().getSimpleName();
        objArr[1] = Float.valueOf(getExpandedHeight());
        objArr[2] = Integer.valueOf(getMaxPanelHeight());
        objArr[3] = this.mClosing ? "T" : "f";
        objArr[4] = this.mTracking ? "T" : "f";
        objArr[5] = this.mJustPeeked ? "T" : "f";
        objArr[6] = this.mPeekAnimator;
        objArr[7] = (this.mPeekAnimator == null || !this.mPeekAnimator.isStarted()) ? "" : " (started)";
        objArr[8] = this.mHeightAnimator;
        objArr[9] = (this.mHeightAnimator == null || !this.mHeightAnimator.isStarted()) ? "" : " (started)";
        objArr[10] = this.mTouchDisabled ? "T" : "f";
        pw.println(String.format("[PanelView(%s): expandedHeight=%f maxPanelHeight=%d closing=%s tracking=%s justPeeked=%s peekAnim=%s%s timeAnim=%s%s touchDisabled=%s]", objArr));
    }

    public void setHeadsUpManager(HeadsUpManagerPhone headsUpManager) {
        this.mHeadsUpManager = headsUpManager;
    }

    public void setLaunchingNotification(boolean launchingNotification) {
        this.mLaunchingNotification = launchingNotification;
    }

    public void collapseWithDuration(int animationDuration) {
        this.mFixedDuration = animationDuration;
        collapse(false, 1.0f);
        this.mFixedDuration = -1;
    }
}
