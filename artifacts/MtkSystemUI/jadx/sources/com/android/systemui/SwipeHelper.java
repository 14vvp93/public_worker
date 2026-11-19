package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Property;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.systemui.classifier.FalsingManager;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.FlingAnimationUtils;

/* JADX INFO: loaded from: classes.dex */
public class SwipeHelper {
    private final Callback mCallback;
    private boolean mCanCurrViewBeDimissed;
    private final Context mContext;
    private View mCurrView;
    private float mDensityScale;
    private boolean mDisableHwLayers;
    private boolean mDragging;
    private final boolean mFadeDependingOnAmountSwiped;
    private final FalsingManager mFalsingManager;
    private final int mFalsingThreshold;
    private final FlingAnimationUtils mFlingAnimationUtils;
    private float mInitialTouchPos;
    private boolean mLongPressSent;
    private boolean mMenuRowIntercepting;
    private float mPagingTouchSlop;
    private float mPerpendicularInitialTouchPos;
    private boolean mSnappingChild;
    private final int mSwipeDirection;
    private boolean mTouchAboveFalsingThreshold;
    private Runnable mWatchLongPress;
    private float mMinSwipeProgress = 0.0f;
    private float mMaxSwipeProgress = 1.0f;
    private float mTranslation = 0.0f;
    private final int[] mTmpPos = new int[2];
    private final ArrayMap<View, Animator> mDismissPendingMap = new ArrayMap<>();
    private final Handler mHandler = new Handler();
    private final VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    private final long mLongPressTimeout = (long) (ViewConfiguration.getLongPressTimeout() * 1.5f);

    public SwipeHelper(int swipeDirection, Callback callback, Context context) {
        this.mContext = context;
        this.mCallback = callback;
        this.mSwipeDirection = swipeDirection;
        this.mPagingTouchSlop = ViewConfiguration.get(context).getScaledPagingTouchSlop();
        Resources res = context.getResources();
        this.mDensityScale = res.getDisplayMetrics().density;
        this.mFalsingThreshold = res.getDimensionPixelSize(R.dimen.swipe_helper_falsing_threshold);
        this.mFadeDependingOnAmountSwiped = res.getBoolean(R.bool.config_fadeDependingOnAmountSwiped);
        this.mFalsingManager = FalsingManager.getInstance(context);
        this.mFlingAnimationUtils = new FlingAnimationUtils(context, getMaxEscapeAnimDuration() / 1000.0f);
    }

    public void setDensityScale(float densityScale) {
        this.mDensityScale = densityScale;
    }

    public void setPagingTouchSlop(float pagingTouchSlop) {
        this.mPagingTouchSlop = pagingTouchSlop;
    }

    public void setDisableHardwareLayers(boolean disableHwLayers) {
        this.mDisableHwLayers = disableHwLayers;
    }

    private float getPos(MotionEvent ev) {
        return this.mSwipeDirection == 0 ? ev.getX() : ev.getY();
    }

    private float getPerpendicularPos(MotionEvent ev) {
        return this.mSwipeDirection == 0 ? ev.getY() : ev.getX();
    }

    protected float getTranslation(View v) {
        return this.mSwipeDirection == 0 ? v.getTranslationX() : v.getTranslationY();
    }

    private float getVelocity(VelocityTracker vt) {
        return this.mSwipeDirection == 0 ? vt.getXVelocity() : vt.getYVelocity();
    }

    protected ObjectAnimator createTranslationAnimation(View v, float newPos) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(v, (Property<View, Float>) (this.mSwipeDirection == 0 ? View.TRANSLATION_X : View.TRANSLATION_Y), newPos);
        return anim;
    }

    protected Animator getViewTranslationAnimator(View v, float target, ValueAnimator.AnimatorUpdateListener listener) {
        ObjectAnimator anim = createTranslationAnimation(v, target);
        if (listener != null) {
            anim.addUpdateListener(listener);
        }
        return anim;
    }

    protected void setTranslation(View v, float translate) {
        if (v == null) {
            return;
        }
        if (this.mSwipeDirection == 0) {
            v.setTranslationX(translate);
        } else {
            v.setTranslationY(translate);
        }
    }

    protected float getSize(View v) {
        return this.mSwipeDirection == 0 ? v.getMeasuredWidth() : v.getMeasuredHeight();
    }

    private float getSwipeProgressForOffset(View view, float translation) {
        float viewSize = getSize(view);
        float result = Math.abs(translation / viewSize);
        return Math.min(Math.max(this.mMinSwipeProgress, result), this.mMaxSwipeProgress);
    }

    private float getSwipeAlpha(float progress) {
        if (this.mFadeDependingOnAmountSwiped) {
            return Math.max(1.0f - progress, 0.0f);
        }
        return 1.0f - Math.max(0.0f, Math.min(1.0f, progress / 0.5f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSwipeProgressFromOffset(View animView, boolean dismissable) {
        updateSwipeProgressFromOffset(animView, dismissable, getTranslation(animView));
    }

    private void updateSwipeProgressFromOffset(View animView, boolean dismissable, float translation) {
        float swipeProgress = getSwipeProgressForOffset(animView, translation);
        if (!this.mCallback.updateSwipeProgress(animView, dismissable, swipeProgress) && dismissable) {
            if (!this.mDisableHwLayers) {
                if (swipeProgress != 0.0f && swipeProgress != 1.0f) {
                    animView.setLayerType(2, null);
                } else {
                    animView.setLayerType(0, null);
                }
            }
            animView.setAlpha(getSwipeAlpha(swipeProgress));
        }
        invalidateGlobalRegion(animView);
    }

    public static void invalidateGlobalRegion(View view) {
        invalidateGlobalRegion(view, new RectF(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
    }

    public static void invalidateGlobalRegion(View view, RectF childBounds) {
        while (view.getParent() != null && (view.getParent() instanceof View)) {
            view = (View) view.getParent();
            view.getMatrix().mapRect(childBounds);
            view.invalidate((int) Math.floor(childBounds.left), (int) Math.floor(childBounds.top), (int) Math.ceil(childBounds.right), (int) Math.ceil(childBounds.bottom));
        }
    }

    public void cancelLongPress() {
        if (this.mWatchLongPress != null) {
            this.mHandler.removeCallbacks(this.mWatchLongPress);
            this.mWatchLongPress = null;
        }
    }

    public boolean onInterceptTouchEvent(final MotionEvent ev) {
        if (this.mCurrView instanceof ExpandableNotificationRow) {
            NotificationMenuRowPlugin nmr = ((ExpandableNotificationRow) this.mCurrView).getProvider();
            this.mMenuRowIntercepting = nmr.onInterceptTouchEvent(this.mCurrView, ev);
        }
        int action = ev.getAction();
        switch (action) {
            case 0:
                this.mTouchAboveFalsingThreshold = false;
                this.mDragging = false;
                this.mSnappingChild = false;
                this.mLongPressSent = false;
                this.mVelocityTracker.clear();
                this.mCurrView = this.mCallback.getChildAtPosition(ev);
                if (this.mCurrView != null) {
                    onDownUpdate(this.mCurrView, ev);
                    this.mCanCurrViewBeDimissed = this.mCallback.canChildBeDismissed(this.mCurrView);
                    this.mVelocityTracker.addMovement(ev);
                    this.mInitialTouchPos = getPos(ev);
                    this.mPerpendicularInitialTouchPos = getPerpendicularPos(ev);
                    this.mTranslation = getTranslation(this.mCurrView);
                    if (this.mWatchLongPress == null) {
                        this.mWatchLongPress = new Runnable() { // from class: com.android.systemui.SwipeHelper.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (SwipeHelper.this.mCurrView != null && !SwipeHelper.this.mLongPressSent) {
                                    SwipeHelper.this.mLongPressSent = true;
                                    SwipeHelper.this.mCurrView.getLocationOnScreen(SwipeHelper.this.mTmpPos);
                                    int x = ((int) ev.getRawX()) - SwipeHelper.this.mTmpPos[0];
                                    int y = ((int) ev.getRawY()) - SwipeHelper.this.mTmpPos[1];
                                    if (SwipeHelper.this.mCurrView instanceof ExpandableNotificationRow) {
                                        SwipeHelper.this.mCurrView.sendAccessibilityEvent(2);
                                        ExpandableNotificationRow currRow = (ExpandableNotificationRow) SwipeHelper.this.mCurrView;
                                        currRow.doLongClickCallback(x, y);
                                    }
                                }
                            }
                        };
                    }
                    this.mHandler.postDelayed(this.mWatchLongPress, this.mLongPressTimeout);
                }
                break;
            case 1:
            case 3:
                boolean captured = this.mDragging || this.mLongPressSent || this.mMenuRowIntercepting;
                this.mDragging = false;
                this.mCurrView = null;
                this.mLongPressSent = false;
                this.mMenuRowIntercepting = false;
                cancelLongPress();
                if (captured) {
                    return true;
                }
                break;
            case 2:
                if (this.mCurrView != null && !this.mLongPressSent) {
                    this.mVelocityTracker.addMovement(ev);
                    float pos = getPos(ev);
                    float perpendicularPos = getPerpendicularPos(ev);
                    float delta = pos - this.mInitialTouchPos;
                    float deltaPerpendicular = perpendicularPos - this.mPerpendicularInitialTouchPos;
                    if (Math.abs(delta) > this.mPagingTouchSlop && Math.abs(delta) > Math.abs(deltaPerpendicular)) {
                        if (this.mCallback.canChildBeDragged(this.mCurrView)) {
                            this.mCallback.onBeginDrag(this.mCurrView);
                            this.mDragging = true;
                            this.mInitialTouchPos = getPos(ev);
                            this.mTranslation = getTranslation(this.mCurrView);
                        }
                        cancelLongPress();
                    }
                }
                break;
        }
        return this.mDragging || this.mLongPressSent || this.mMenuRowIntercepting;
    }

    public void dismissChild(View view, float velocity, boolean useAccelerateInterpolator) {
        dismissChild(view, velocity, null, 0L, useAccelerateInterpolator, 0L, false);
    }

    public void dismissChild(final View animView, float velocity, final Runnable endAction, long delay, boolean useAccelerateInterpolator, long fixedDuration, boolean isDismissAll) {
        float newPos;
        long duration;
        Animator anim;
        final boolean canBeDismissed = this.mCallback.canChildBeDismissed(animView);
        boolean z = true;
        boolean isLayoutRtl = animView.getLayoutDirection() == 1;
        boolean animateUpForMenu = velocity == 0.0f && (getTranslation(animView) == 0.0f || isDismissAll) && this.mSwipeDirection == 1;
        boolean animateLeftForRtl = velocity == 0.0f && (getTranslation(animView) == 0.0f || isDismissAll) && isLayoutRtl;
        if ((Math.abs(velocity) <= getEscapeVelocity() || velocity >= 0.0f) && (getTranslation(animView) >= 0.0f || isDismissAll)) {
            z = false;
        }
        boolean animateLeft = z;
        if (animateLeft || animateLeftForRtl || animateUpForMenu) {
            newPos = -getSize(animView);
        } else {
            newPos = getSize(animView);
        }
        float newPos2 = newPos;
        if (fixedDuration != 0) {
            duration = fixedDuration;
        } else if (velocity != 0.0f) {
            duration = Math.min(400L, (int) ((Math.abs(newPos2 - getTranslation(animView)) * 1000.0f) / Math.abs(velocity)));
        } else {
            duration = 200;
        }
        long duration2 = duration;
        if (!this.mDisableHwLayers) {
            animView.setLayerType(2, null);
        }
        ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.SwipeHelper.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                SwipeHelper.this.onTranslationUpdate(animView, ((Float) animation.getAnimatedValue()).floatValue(), canBeDismissed);
            }
        };
        Animator anim2 = getViewTranslationAnimator(animView, newPos2, updateListener);
        if (anim2 == null) {
            return;
        }
        if (useAccelerateInterpolator) {
            anim2.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
            anim2.setDuration(duration2);
        } else {
            FlingAnimationUtils flingAnimationUtils = this.mFlingAnimationUtils;
            float newPos3 = getTranslation(animView);
            float newPos4 = getSize(animView);
            flingAnimationUtils.applyDismissing(anim2, newPos3, newPos2, velocity, newPos4);
        }
        if (delay > 0) {
            anim = anim2;
            anim.setStartDelay(delay);
        } else {
            anim = anim2;
        }
        anim.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.SwipeHelper.3
            private boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                this.mCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                SwipeHelper.this.updateSwipeProgressFromOffset(animView, canBeDismissed);
                SwipeHelper.this.mDismissPendingMap.remove(animView);
                boolean wasRemoved = false;
                if (animView instanceof ExpandableNotificationRow) {
                    ExpandableNotificationRow row = (ExpandableNotificationRow) animView;
                    wasRemoved = row.isRemoved();
                }
                if (!this.mCancelled || wasRemoved) {
                    SwipeHelper.this.mCallback.onChildDismissed(animView);
                }
                if (endAction != null) {
                    endAction.run();
                }
                if (!SwipeHelper.this.mDisableHwLayers) {
                    animView.setLayerType(0, null);
                }
            }
        });
        prepareDismissAnimation(animView, anim);
        this.mDismissPendingMap.put(animView, anim);
        anim.start();
    }

    protected void prepareDismissAnimation(View view, Animator anim) {
    }

    public void snapChild(final View animView, final float targetLeft, float velocity) {
        final boolean canBeDismissed = this.mCallback.canChildBeDismissed(animView);
        ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.SwipeHelper.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                SwipeHelper.this.onTranslationUpdate(animView, ((Float) animation.getAnimatedValue()).floatValue(), canBeDismissed);
            }
        };
        Animator anim = getViewTranslationAnimator(animView, targetLeft, updateListener);
        if (anim == null) {
            return;
        }
        anim.setDuration(150);
        anim.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.SwipeHelper.5
            boolean wasCancelled = false;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                this.wasCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SwipeHelper.this.mSnappingChild = false;
                if (!this.wasCancelled) {
                    SwipeHelper.this.updateSwipeProgressFromOffset(animView, canBeDismissed);
                    SwipeHelper.this.mCallback.onChildSnappedBack(animView, targetLeft);
                }
            }
        });
        prepareSnapBackAnimation(animView, anim);
        this.mSnappingChild = true;
        anim.start();
    }

    protected void prepareSnapBackAnimation(View view, Animator anim) {
    }

    public void onDownUpdate(View currView, MotionEvent ev) {
    }

    protected void onMoveUpdate(View view, MotionEvent ev, float totalTranslation, float delta) {
    }

    public void onTranslationUpdate(View animView, float value, boolean canBeDismissed) {
        updateSwipeProgressFromOffset(animView, canBeDismissed, value);
    }

    private void snapChildInstantly(View view) {
        boolean canAnimViewBeDismissed = this.mCallback.canChildBeDismissed(view);
        setTranslation(view, 0.0f);
        updateSwipeProgressFromOffset(view, canAnimViewBeDismissed);
    }

    public void snapChildIfNeeded(View view, boolean animate, float targetLeft) {
        if ((this.mDragging && this.mCurrView == view) || this.mSnappingChild) {
            return;
        }
        boolean needToSnap = false;
        Animator dismissPendingAnim = this.mDismissPendingMap.get(view);
        if (dismissPendingAnim != null) {
            needToSnap = true;
            dismissPendingAnim.cancel();
        } else if (getTranslation(view) != 0.0f) {
            needToSnap = true;
        }
        if (needToSnap) {
            if (animate) {
                snapChild(view, targetLeft, 0.0f);
            } else {
                snapChildInstantly(view);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.mLongPressSent && !this.mMenuRowIntercepting) {
            return true;
        }
        if (!this.mDragging && !this.mMenuRowIntercepting) {
            if (this.mCallback.getChildAtPosition(ev) != null) {
                onInterceptTouchEvent(ev);
                return true;
            }
            cancelLongPress();
            return false;
        }
        this.mVelocityTracker.addMovement(ev);
        int action = ev.getAction();
        switch (action) {
            case 1:
            case 3:
                if (this.mCurrView != null) {
                    this.mVelocityTracker.computeCurrentVelocity(1000, getMaxVelocity());
                    float velocity = getVelocity(this.mVelocityTracker);
                    if (!handleUpEvent(ev, this.mCurrView, velocity, getTranslation(this.mCurrView))) {
                        if (isDismissGesture(ev)) {
                            dismissChild(this.mCurrView, velocity, !swipedFastEnough());
                        } else {
                            this.mCallback.onDragCancelled(this.mCurrView);
                            snapChild(this.mCurrView, 0.0f, velocity);
                        }
                        this.mCurrView = null;
                    }
                    this.mDragging = false;
                }
                return true;
            case 2:
            case 4:
                if (this.mCurrView != null) {
                    float delta = getPos(ev) - this.mInitialTouchPos;
                    float absDelta = Math.abs(delta);
                    if (absDelta >= getFalsingThreshold()) {
                        this.mTouchAboveFalsingThreshold = true;
                    }
                    if (!this.mCallback.canChildBeDismissed(this.mCurrView)) {
                        float size = getSize(this.mCurrView);
                        float maxScrollDistance = 0.3f * size;
                        if (absDelta >= size) {
                            delta = delta > 0.0f ? maxScrollDistance : -maxScrollDistance;
                        } else {
                            delta = maxScrollDistance * ((float) Math.sin(((double) (delta / size)) * 1.5707963267948966d));
                        }
                    }
                    setTranslation(this.mCurrView, this.mTranslation + delta);
                    updateSwipeProgressFromOffset(this.mCurrView, this.mCanCurrViewBeDimissed);
                    onMoveUpdate(this.mCurrView, ev, this.mTranslation + delta, delta);
                }
                return true;
            default:
                return true;
        }
    }

    private int getFalsingThreshold() {
        float factor = this.mCallback.getFalsingThresholdFactor();
        return (int) (this.mFalsingThreshold * factor);
    }

    private float getMaxVelocity() {
        return 4000.0f * this.mDensityScale;
    }

    protected float getEscapeVelocity() {
        return getUnscaledEscapeVelocity() * this.mDensityScale;
    }

    protected float getUnscaledEscapeVelocity() {
        return 500.0f;
    }

    protected long getMaxEscapeAnimDuration() {
        return 400L;
    }

    protected boolean swipedFarEnough() {
        float translation = getTranslation(this.mCurrView);
        return Math.abs(translation) > 0.6f * getSize(this.mCurrView);
    }

    public boolean isDismissGesture(MotionEvent ev) {
        return ev.getActionMasked() == 1 && !isFalseGesture(ev) && (swipedFastEnough() || swipedFarEnough()) && this.mCallback.canChildBeDismissed(this.mCurrView);
    }

    public boolean isFalseGesture(MotionEvent ev) {
        boolean falsingDetected = this.mCallback.isAntiFalsingNeeded();
        boolean falsingDetected2 = false;
        if (this.mFalsingManager.isClassiferEnabled()) {
            if (falsingDetected && this.mFalsingManager.isFalseTouch()) {
                falsingDetected2 = true;
            }
            return falsingDetected2;
        }
        if (falsingDetected && !this.mTouchAboveFalsingThreshold) {
            falsingDetected2 = true;
        }
        return falsingDetected2;
    }

    protected boolean swipedFastEnough() {
        float velocity = getVelocity(this.mVelocityTracker);
        float translation = getTranslation(this.mCurrView);
        boolean z = false;
        if (Math.abs(velocity) > getEscapeVelocity()) {
            if ((velocity > 0.0f) == (translation > 0.0f)) {
                z = true;
            }
        }
        boolean ret = z;
        return ret;
    }

    protected boolean handleUpEvent(MotionEvent ev, View animView, float velocity, float translation) {
        return false;
    }

    public interface Callback {
        boolean canChildBeDismissed(View view);

        View getChildAtPosition(MotionEvent motionEvent);

        float getFalsingThresholdFactor();

        boolean isAntiFalsingNeeded();

        void onBeginDrag(View view);

        void onChildDismissed(View view);

        void onChildSnappedBack(View view, float f);

        void onDragCancelled(View view);

        boolean updateSwipeProgress(View view, boolean z, float f);

        default boolean canChildBeDragged(View animView) {
            return true;
        }
    }
}
