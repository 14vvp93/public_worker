package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.ExpandableView;
import com.android.systemui.statusbar.FlingAnimationUtils;
import com.android.systemui.statusbar.policy.ScrollAdapter;

/* JADX INFO: loaded from: classes.dex */
public class ExpandHelper {
    private Callback mCallback;
    private Context mContext;
    private float mCurrentHeight;
    private View mEventSource;
    private boolean mExpanding;
    private FlingAnimationUtils mFlingAnimationUtils;
    private boolean mHasPopped;
    private float mInitialTouchFocusY;
    private float mInitialTouchSpan;
    private float mInitialTouchX;
    private float mInitialTouchY;
    private int mLargeSize;
    private float mLastFocusY;
    private float mLastMotionY;
    private float mLastSpanY;
    private float mMaximumStretch;
    private float mNaturalHeight;
    private float mOldHeight;
    private boolean mOnlyMovements;
    private float mPullGestureMinXSpan;
    private ExpandableView mResizedView;
    private ScaleGestureDetector mSGD;
    private ScrollAdapter mScrollAdapter;
    private int mSmallSize;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private boolean mWatchingForPull;
    private int mExpansionStyle = 0;
    private boolean mEnabled = true;
    private ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() { // from class: com.android.systemui.ExpandHelper.1
        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            if (!ExpandHelper.this.mOnlyMovements) {
                ExpandHelper.this.startExpanding(ExpandHelper.this.mResizedView, 4);
            }
            return ExpandHelper.this.mExpanding;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector detector) {
            return true;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    };
    private ViewScaler mScaler = new ViewScaler();
    private int mGravity = 48;
    private ObjectAnimator mScaleAnimation = ObjectAnimator.ofFloat(this.mScaler, "height", 0.0f);

    public interface Callback {
        boolean canChildBeExpanded(View view);

        void expansionStateChanged(boolean z);

        ExpandableView getChildAtPosition(float f, float f2);

        ExpandableView getChildAtRawPosition(float f, float f2);

        int getMaxExpandHeight(ExpandableView expandableView);

        void setExpansionCancelled(View view);

        void setUserExpandedChild(View view, boolean z);

        void setUserLockedChild(View view, boolean z);
    }

    @VisibleForTesting
    ObjectAnimator getScaleAnimation() {
        return this.mScaleAnimation;
    }

    private class ViewScaler {
        ExpandableView mView;

        public ViewScaler() {
        }

        public void setView(ExpandableView v) {
            this.mView = v;
        }

        public void setHeight(float h) {
            this.mView.setActualHeight((int) h);
            ExpandHelper.this.mCurrentHeight = h;
        }

        public float getHeight() {
            return this.mView.getActualHeight();
        }

        public int getNaturalHeight() {
            return ExpandHelper.this.mCallback.getMaxExpandHeight(this.mView);
        }
    }

    public ExpandHelper(Context context, Callback callback, int small, int large) {
        this.mSmallSize = small;
        this.mMaximumStretch = this.mSmallSize * 2.0f;
        this.mLargeSize = large;
        this.mContext = context;
        this.mCallback = callback;
        this.mPullGestureMinXSpan = this.mContext.getResources().getDimension(R.dimen.pull_span_min);
        ViewConfiguration configuration = ViewConfiguration.get(this.mContext);
        this.mTouchSlop = configuration.getScaledTouchSlop();
        this.mSGD = new ScaleGestureDetector(context, this.mScaleGestureListener);
        this.mFlingAnimationUtils = new FlingAnimationUtils(context, 0.3f);
    }

    @VisibleForTesting
    void updateExpansion() {
        float span = (this.mSGD.getCurrentSpan() - this.mInitialTouchSpan) * 1.0f;
        float drag = (this.mSGD.getFocusY() - this.mInitialTouchFocusY) * 1.0f * (this.mGravity == 80 ? -1.0f : 1.0f);
        float pull = Math.abs(drag) + Math.abs(span) + 1.0f;
        float hand = ((Math.abs(drag) * drag) / pull) + ((Math.abs(span) * span) / pull);
        float target = this.mOldHeight + hand;
        float newHeight = clamp(target);
        this.mScaler.setHeight(newHeight);
        this.mLastFocusY = this.mSGD.getFocusY();
        this.mLastSpanY = this.mSGD.getCurrentSpan();
    }

    private float clamp(float target) {
        float out = target < ((float) this.mSmallSize) ? this.mSmallSize : target;
        return out > this.mNaturalHeight ? this.mNaturalHeight : out;
    }

    private ExpandableView findView(float x, float y) {
        if (this.mEventSource != null) {
            int[] location = new int[2];
            this.mEventSource.getLocationOnScreen(location);
            ExpandableView v = this.mCallback.getChildAtRawPosition(x + location[0], y + location[1]);
            return v;
        }
        ExpandableView v2 = this.mCallback.getChildAtPosition(x, y);
        return v2;
    }

    private boolean isInside(View v, float x, float y) {
        if (v == null) {
            return false;
        }
        if (this.mEventSource != null) {
            int[] location = new int[2];
            this.mEventSource.getLocationOnScreen(location);
            x += location[0];
            y += location[1];
        }
        int[] location2 = new int[2];
        v.getLocationOnScreen(location2);
        float x2 = x - location2[0];
        float y2 = y - location2[1];
        if (x2 <= 0.0f || y2 <= 0.0f) {
            return false;
        }
        return ((x2 > ((float) v.getWidth()) ? 1 : (x2 == ((float) v.getWidth()) ? 0 : -1)) < 0) & ((y2 > ((float) v.getHeight()) ? 1 : (y2 == ((float) v.getHeight()) ? 0 : -1)) < 0);
    }

    public void setEventSource(View eventSource) {
        this.mEventSource = eventSource;
    }

    public void setScrollAdapter(ScrollAdapter adapter) {
        this.mScrollAdapter = adapter;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isEnabled()) {
            return false;
        }
        trackVelocity(ev);
        int action = ev.getAction();
        this.mSGD.onTouchEvent(ev);
        int x = (int) this.mSGD.getFocusX();
        int y = (int) this.mSGD.getFocusY();
        this.mInitialTouchFocusY = y;
        this.mInitialTouchSpan = this.mSGD.getCurrentSpan();
        this.mLastFocusY = this.mInitialTouchFocusY;
        this.mLastSpanY = this.mInitialTouchSpan;
        if (this.mExpanding) {
            this.mLastMotionY = ev.getRawY();
            maybeRecycleVelocityTracker(ev);
            return true;
        }
        if (action == 2 && (this.mExpansionStyle & 1) != 0) {
            return true;
        }
        switch (action & 255) {
            case 0:
                this.mWatchingForPull = this.mScrollAdapter != null && isInside(this.mScrollAdapter.getHostView(), (float) x, (float) y) && this.mScrollAdapter.isScrolledToTop();
                this.mResizedView = findView(x, y);
                if (this.mResizedView != null && !this.mCallback.canChildBeExpanded(this.mResizedView)) {
                    this.mResizedView = null;
                    this.mWatchingForPull = false;
                }
                this.mInitialTouchY = ev.getRawY();
                this.mInitialTouchX = ev.getRawX();
                break;
            case 1:
            case 3:
                finishExpanding(ev.getActionMasked() == 3, getCurrentVelocity());
                clearView();
                break;
            case 2:
                float xspan = this.mSGD.getCurrentSpanX();
                if (xspan > this.mPullGestureMinXSpan && xspan > this.mSGD.getCurrentSpanY() && !this.mExpanding) {
                    startExpanding(this.mResizedView, 2);
                    this.mWatchingForPull = false;
                }
                if (this.mWatchingForPull) {
                    float yDiff = ev.getRawY() - this.mInitialTouchY;
                    float xDiff = ev.getRawX() - this.mInitialTouchX;
                    if (yDiff > this.mTouchSlop && yDiff > Math.abs(xDiff)) {
                        this.mWatchingForPull = false;
                        if (this.mResizedView != null && !isFullyExpanded(this.mResizedView) && startExpanding(this.mResizedView, 1)) {
                            this.mLastMotionY = ev.getRawY();
                            this.mInitialTouchY = ev.getRawY();
                            this.mHasPopped = false;
                        }
                    }
                }
                break;
        }
        this.mLastMotionY = ev.getRawY();
        maybeRecycleVelocityTracker(ev);
        return this.mExpanding;
    }

    private void trackVelocity(MotionEvent event) {
        int action = event.getActionMasked();
        if (action == 0) {
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            } else {
                this.mVelocityTracker.clear();
            }
            this.mVelocityTracker.addMovement(event);
            return;
        }
        if (action == 2) {
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(event);
        }
    }

    private void maybeRecycleVelocityTracker(MotionEvent event) {
        if (this.mVelocityTracker != null) {
            if (event.getActionMasked() == 3 || event.getActionMasked() == 1) {
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
        }
    }

    private float getCurrentVelocity() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            return this.mVelocityTracker.getYVelocity();
        }
        return 0.0f;
    }

    public void setEnabled(boolean enable) {
        this.mEnabled = enable;
    }

    private boolean isEnabled() {
        return this.mEnabled;
    }

    private boolean isFullyExpanded(ExpandableView underFocus) {
        return underFocus.getIntrinsicHeight() == underFocus.getMaxContentHeight() && (!underFocus.isSummaryWithChildren() || underFocus.areChildrenExpanded());
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (!isEnabled() && !this.mExpanding) {
            return false;
        }
        trackVelocity(ev);
        int action = ev.getActionMasked();
        this.mSGD.onTouchEvent(ev);
        int x = (int) this.mSGD.getFocusX();
        int y = (int) this.mSGD.getFocusY();
        if (this.mOnlyMovements) {
            this.mLastMotionY = ev.getRawY();
            return false;
        }
        switch (action) {
            case 0:
                this.mWatchingForPull = this.mScrollAdapter != null && isInside(this.mScrollAdapter.getHostView(), (float) x, (float) y);
                this.mResizedView = findView(x, y);
                this.mInitialTouchX = ev.getRawX();
                this.mInitialTouchY = ev.getRawY();
                break;
            case 1:
            case 3:
                finishExpanding(!isEnabled() || ev.getActionMasked() == 3, getCurrentVelocity());
                clearView();
                break;
            case 2:
                if (this.mWatchingForPull) {
                    float yDiff = ev.getRawY() - this.mInitialTouchY;
                    float xDiff = ev.getRawX() - this.mInitialTouchX;
                    if (yDiff > this.mTouchSlop && yDiff > Math.abs(xDiff)) {
                        this.mWatchingForPull = false;
                        if (this.mResizedView != null && !isFullyExpanded(this.mResizedView) && startExpanding(this.mResizedView, 1)) {
                            this.mInitialTouchY = ev.getRawY();
                            this.mLastMotionY = ev.getRawY();
                            this.mHasPopped = false;
                        }
                    }
                }
                if (this.mExpanding && (this.mExpansionStyle & 1) != 0) {
                    float rawHeight = (ev.getRawY() - this.mLastMotionY) + this.mCurrentHeight;
                    float newHeight = clamp(rawHeight);
                    boolean isFinished = false;
                    if (rawHeight > this.mNaturalHeight) {
                        isFinished = true;
                    }
                    if (rawHeight < this.mSmallSize) {
                        isFinished = true;
                    }
                    if (!this.mHasPopped) {
                        if (this.mEventSource != null) {
                            this.mEventSource.performHapticFeedback(1);
                        }
                        this.mHasPopped = true;
                    }
                    this.mScaler.setHeight(newHeight);
                    this.mLastMotionY = ev.getRawY();
                    if (!isFinished) {
                        this.mCallback.expansionStateChanged(true);
                    } else {
                        this.mCallback.expansionStateChanged(false);
                    }
                    return true;
                }
                if (this.mExpanding) {
                    updateExpansion();
                    this.mLastMotionY = ev.getRawY();
                    return true;
                }
                break;
            case 5:
            case 6:
                this.mInitialTouchY += this.mSGD.getFocusY() - this.mLastFocusY;
                this.mInitialTouchSpan += this.mSGD.getCurrentSpan() - this.mLastSpanY;
                break;
        }
        this.mLastMotionY = ev.getRawY();
        maybeRecycleVelocityTracker(ev);
        return this.mResizedView != null;
    }

    @VisibleForTesting
    boolean startExpanding(ExpandableView v, int expandType) {
        if (!(v instanceof ExpandableNotificationRow)) {
            return false;
        }
        this.mExpansionStyle = expandType;
        if (this.mExpanding && v == this.mResizedView) {
            return true;
        }
        this.mExpanding = true;
        this.mCallback.expansionStateChanged(true);
        this.mCallback.setUserLockedChild(v, true);
        this.mScaler.setView(v);
        this.mOldHeight = this.mScaler.getHeight();
        this.mCurrentHeight = this.mOldHeight;
        boolean canBeExpanded = this.mCallback.canChildBeExpanded(v);
        if (canBeExpanded) {
            this.mNaturalHeight = this.mScaler.getNaturalHeight();
            this.mSmallSize = v.getCollapsedHeight();
        } else {
            this.mNaturalHeight = this.mOldHeight;
        }
        return true;
    }

    @VisibleForTesting
    void finishExpanding(boolean forceAbort, float velocity) {
        finishExpanding(forceAbort, velocity, true);
    }

    private void finishExpanding(boolean forceAbort, float velocity, boolean allowAnimation) {
        boolean nowExpanded;
        boolean nowExpanded2;
        if (this.mExpanding) {
            float currentHeight = this.mScaler.getHeight();
            boolean wasClosed = this.mOldHeight == ((float) this.mSmallSize);
            if (!forceAbort) {
                if (wasClosed) {
                    nowExpanded2 = currentHeight > this.mOldHeight && velocity >= 0.0f;
                } else {
                    nowExpanded2 = currentHeight >= this.mOldHeight || velocity > 0.0f;
                }
                nowExpanded = nowExpanded2 | (this.mNaturalHeight == ((float) this.mSmallSize));
            } else {
                nowExpanded = !wasClosed;
            }
            if (this.mScaleAnimation.isRunning()) {
                this.mScaleAnimation.cancel();
            }
            this.mCallback.expansionStateChanged(false);
            int naturalHeight = this.mScaler.getNaturalHeight();
            float targetHeight = nowExpanded ? naturalHeight : this.mSmallSize;
            if (targetHeight != currentHeight && this.mEnabled && allowAnimation) {
                this.mScaleAnimation.setFloatValues(targetHeight);
                this.mScaleAnimation.setupStartValues();
                final View scaledView = this.mResizedView;
                final boolean expand = nowExpanded;
                this.mScaleAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.ExpandHelper.2
                    public boolean mCancelled;

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        if (!this.mCancelled) {
                            ExpandHelper.this.mCallback.setUserExpandedChild(scaledView, expand);
                            if (!ExpandHelper.this.mExpanding) {
                                ExpandHelper.this.mScaler.setView(null);
                            }
                        } else {
                            ExpandHelper.this.mCallback.setExpansionCancelled(scaledView);
                        }
                        ExpandHelper.this.mCallback.setUserLockedChild(scaledView, false);
                        ExpandHelper.this.mScaleAnimation.removeListener(this);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animation) {
                        this.mCancelled = true;
                    }
                });
                this.mFlingAnimationUtils.apply(this.mScaleAnimation, currentHeight, targetHeight, nowExpanded == (velocity >= 0.0f) ? velocity : 0.0f);
                this.mScaleAnimation.start();
            } else {
                if (targetHeight != currentHeight) {
                    this.mScaler.setHeight(targetHeight);
                }
                this.mCallback.setUserExpandedChild(this.mResizedView, nowExpanded);
                this.mCallback.setUserLockedChild(this.mResizedView, false);
                this.mScaler.setView(null);
            }
            this.mExpanding = false;
            this.mExpansionStyle = 0;
        }
    }

    private void clearView() {
        this.mResizedView = null;
    }

    public void cancelImmediately() {
        cancel(false);
    }

    public void cancel() {
        cancel(true);
    }

    private void cancel(boolean allowAnimation) {
        finishExpanding(true, 0.0f, allowAnimation);
        clearView();
        this.mSGD = new ScaleGestureDetector(this.mContext, this.mScaleGestureListener);
    }

    public void onlyObserveMovements(boolean onlyMovements) {
        this.mOnlyMovements = onlyMovements;
    }
}
