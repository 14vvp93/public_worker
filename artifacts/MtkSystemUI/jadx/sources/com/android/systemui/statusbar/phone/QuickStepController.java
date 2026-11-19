package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.RemoteException;
import android.util.FloatProperty;
import android.util.Log;
import android.util.Slog;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManagerGlobal;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Interpolators;
import com.android.systemui.OverviewProxyService;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.phone.NavGesture;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.system.NavigationBarCompat;

/* JADX INFO: loaded from: classes.dex */
public class QuickStepController implements NavGesture.GestureHelper {
    private boolean mAllowGestureDetection;
    private final Context mContext;
    private View mCurrentNavigationBarView;
    private float mDarkIntensity;
    private int mDarkTrackColor;
    private boolean mDragPositive;
    private ButtonDispatcher mHitTarget;
    private boolean mIsRTL;
    private boolean mIsVertical;
    private int mLightTrackColor;
    private NavigationBarView mNavigationBarView;
    private final OverviewProxyService mOverviewEventSender;
    private boolean mQuickScrubActive;
    private boolean mQuickStepStarted;
    private int mTouchDownX;
    private int mTouchDownY;
    private float mTrackAlpha;
    private AnimatorSet mTrackAnimator;
    private final Drawable mTrackDrawable;
    private final int mTrackEndPadding;
    private final int mTrackThickness;
    private float mTrackScale = 0.95f;
    private final Handler mHandler = new Handler();
    private final Rect mTrackRect = new Rect();
    private final Matrix mTransformGlobalMatrix = new Matrix();
    private final Matrix mTransformLocalMatrix = new Matrix();
    private final ArgbEvaluator mTrackColorEvaluator = new ArgbEvaluator();
    private final FloatProperty<QuickStepController> mTrackAlphaProperty = new FloatProperty<QuickStepController>("TrackAlpha") { // from class: com.android.systemui.statusbar.phone.QuickStepController.1
        @Override // android.util.FloatProperty
        public void setValue(QuickStepController controller, float alpha) {
            QuickStepController.this.mTrackAlpha = alpha;
            QuickStepController.this.mNavigationBarView.invalidate();
        }

        @Override // android.util.Property
        public Float get(QuickStepController controller) {
            return Float.valueOf(QuickStepController.this.mTrackAlpha);
        }
    };
    private final FloatProperty<QuickStepController> mTrackScaleProperty = new FloatProperty<QuickStepController>("TrackScale") { // from class: com.android.systemui.statusbar.phone.QuickStepController.2
        @Override // android.util.FloatProperty
        public void setValue(QuickStepController controller, float scale) {
            QuickStepController.this.mTrackScale = scale;
            QuickStepController.this.mNavigationBarView.invalidate();
        }

        @Override // android.util.Property
        public Float get(QuickStepController controller) {
            return Float.valueOf(QuickStepController.this.mTrackScale);
        }
    };
    private final FloatProperty<QuickStepController> mNavBarAlphaProperty = new FloatProperty<QuickStepController>("NavBarAlpha") { // from class: com.android.systemui.statusbar.phone.QuickStepController.3
        @Override // android.util.FloatProperty
        public void setValue(QuickStepController controller, float alpha) {
            if (QuickStepController.this.mCurrentNavigationBarView != null) {
                QuickStepController.this.mCurrentNavigationBarView.setAlpha(alpha);
            }
        }

        @Override // android.util.Property
        public Float get(QuickStepController controller) {
            if (QuickStepController.this.mCurrentNavigationBarView != null) {
                return Float.valueOf(QuickStepController.this.mCurrentNavigationBarView.getAlpha());
            }
            return Float.valueOf(1.0f);
        }
    };
    private AnimatorListenerAdapter mQuickScrubEndListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.QuickStepController.4
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            QuickStepController.this.resetQuickScrub();
        }
    };

    public QuickStepController(Context context) {
        Resources res = context.getResources();
        this.mContext = context;
        this.mOverviewEventSender = (OverviewProxyService) Dependency.get(OverviewProxyService.class);
        this.mTrackThickness = res.getDimensionPixelSize(R.dimen.nav_quick_scrub_track_thickness);
        this.mTrackEndPadding = res.getDimensionPixelSize(R.dimen.nav_quick_scrub_track_edge_padding);
        this.mTrackDrawable = context.getDrawable(R.drawable.qs_scrubber_track).mutate();
    }

    public void setComponents(NavigationBarView navigationBarView) {
        this.mNavigationBarView = navigationBarView;
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavGesture.GestureHelper
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return handleTouchEvent(event);
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavGesture.GestureHelper
    public boolean onTouchEvent(MotionEvent event) {
        boolean ignoreProxyDownEvent = event.getAction() == 0 && this.mOverviewEventSender.getProxy() != null;
        return ignoreProxyDownEvent || handleTouchEvent(event);
    }

    private boolean handleTouchEvent(MotionEvent event) {
        boolean z;
        boolean exceededScrubTouchSlop;
        boolean exceededSwipeUpTouchSlop;
        int pos;
        int touchDown;
        int offset;
        int trackSize;
        if (this.mOverviewEventSender.getProxy() == null || !(this.mNavigationBarView.isQuickScrubEnabled() || this.mNavigationBarView.isQuickStepSwipeUpEnabled())) {
            return false;
        }
        this.mNavigationBarView.requestUnbufferedDispatch(event);
        int action = event.getActionMasked();
        switch (action) {
            case 0:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (this.mTrackAnimator != null) {
                    this.mTrackAnimator.end();
                    this.mTrackAnimator = null;
                }
                this.mCurrentNavigationBarView = this.mNavigationBarView.getCurrentView();
                this.mHitTarget = this.mNavigationBarView.getButtonAtPosition(x, y);
                if (this.mHitTarget != null) {
                    this.mHitTarget.setDelayTouchFeedback(true);
                }
                this.mTouchDownX = x;
                this.mTouchDownY = y;
                this.mTransformGlobalMatrix.set(Matrix.IDENTITY_MATRIX);
                this.mTransformLocalMatrix.set(Matrix.IDENTITY_MATRIX);
                this.mNavigationBarView.transformMatrixToGlobal(this.mTransformGlobalMatrix);
                this.mNavigationBarView.transformMatrixToLocal(this.mTransformLocalMatrix);
                this.mQuickStepStarted = false;
                this.mAllowGestureDetection = true;
                break;
            case 1:
            case 3:
                endQuickScrub(true);
                break;
            case 2:
                if (!this.mQuickStepStarted && this.mAllowGestureDetection) {
                    int x2 = (int) event.getX();
                    int y2 = (int) event.getY();
                    int xDiff = Math.abs(x2 - this.mTouchDownX);
                    int yDiff = Math.abs(y2 - this.mTouchDownY);
                    if (this.mIsVertical) {
                        exceededScrubTouchSlop = yDiff > NavigationBarCompat.getQuickScrubTouchSlopPx() && yDiff > xDiff;
                        exceededSwipeUpTouchSlop = xDiff > NavigationBarCompat.getQuickStepTouchSlopPx() && xDiff > yDiff;
                        pos = y2;
                        touchDown = this.mTouchDownY;
                        offset = pos - this.mTrackRect.top;
                        trackSize = this.mTrackRect.height();
                    } else {
                        exceededScrubTouchSlop = xDiff > NavigationBarCompat.getQuickScrubTouchSlopPx() && xDiff > yDiff;
                        exceededSwipeUpTouchSlop = yDiff > NavigationBarCompat.getQuickStepTouchSlopPx() && yDiff > xDiff;
                        pos = x2;
                        touchDown = this.mTouchDownX;
                        offset = pos - this.mTrackRect.left;
                        trackSize = this.mTrackRect.width();
                    }
                    int pos2 = pos;
                    boolean exceededScrubTouchSlop2 = exceededScrubTouchSlop;
                    int offset2 = offset;
                    int touchDown2 = touchDown;
                    if (!this.mQuickScrubActive && exceededSwipeUpTouchSlop) {
                        if (this.mNavigationBarView.isQuickStepSwipeUpEnabled()) {
                            startQuickStep(event);
                        }
                    } else if (this.mNavigationBarView.isQuickScrubEnabled()) {
                        if (!this.mDragPositive) {
                            offset2 -= this.mIsVertical ? this.mTrackRect.height() : this.mTrackRect.width();
                        }
                        int offset3 = offset2;
                        boolean allowDrag = this.mDragPositive ? !(offset3 < 0 || pos2 <= touchDown2) : !(offset3 >= 0 || pos2 >= touchDown2);
                        float scrubFraction = Utilities.clamp((Math.abs(offset3) * 1.0f) / trackSize, 0.0f, 1.0f);
                        if (allowDrag && !this.mQuickScrubActive && exceededScrubTouchSlop2) {
                            startQuickScrub();
                        }
                        if (this.mQuickScrubActive && ((this.mDragPositive && offset3 >= 0) || (!this.mDragPositive && offset3 <= 0))) {
                            try {
                                this.mOverviewEventSender.getProxy().onQuickScrubProgress(scrubFraction);
                            } catch (RemoteException e) {
                                Log.e("QuickStepController", "Failed to send progress of quick scrub.", e);
                            }
                        }
                    }
                }
                break;
        }
        if (this.mQuickScrubActive) {
            z = true;
        } else {
            if (this.mAllowGestureDetection || action == 3) {
                z = true;
            } else {
                z = true;
                if (action == 1) {
                }
            }
            proxyMotionEvents(event);
        }
        if (this.mQuickScrubActive || this.mQuickStepStarted) {
            return z;
        }
        return false;
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavGesture.GestureHelper
    public void onDraw(Canvas canvas) {
        if (!this.mNavigationBarView.isQuickScrubEnabled()) {
            return;
        }
        int color = ((Integer) this.mTrackColorEvaluator.evaluate(this.mDarkIntensity, Integer.valueOf(this.mLightTrackColor), Integer.valueOf(this.mDarkTrackColor))).intValue();
        int colorAlpha = ColorUtils.setAlphaComponent(color, (int) (Color.alpha(color) * this.mTrackAlpha));
        this.mTrackDrawable.setTint(colorAlpha);
        canvas.save();
        canvas.scale(this.mTrackScale / this.mNavigationBarView.getScaleX(), 1.0f / this.mNavigationBarView.getScaleY(), this.mTrackRect.centerX(), this.mTrackRect.centerY());
        this.mTrackDrawable.draw(canvas);
        canvas.restore();
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavGesture.GestureHelper
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int y1;
        int y2;
        int x1;
        int x2;
        int paddingLeft = this.mNavigationBarView.getPaddingLeft();
        int paddingTop = this.mNavigationBarView.getPaddingTop();
        int paddingRight = this.mNavigationBarView.getPaddingRight();
        int paddingBottom = this.mNavigationBarView.getPaddingBottom();
        int width = ((right - left) - paddingRight) - paddingLeft;
        int height = ((bottom - top) - paddingBottom) - paddingTop;
        if (this.mIsVertical) {
            x1 = ((width - this.mTrackThickness) / 2) + paddingLeft;
            x2 = this.mTrackThickness + x1;
            y1 = this.mTrackEndPadding + paddingTop;
            y2 = (y1 + height) - (2 * this.mTrackEndPadding);
        } else {
            y1 = ((height - this.mTrackThickness) / 2) + paddingTop;
            y2 = y1 + this.mTrackThickness;
            x1 = this.mTrackEndPadding + this.mNavigationBarView.getPaddingStart();
            x2 = (x1 + width) - (2 * this.mTrackEndPadding);
        }
        this.mTrackRect.set(x1, y1, x2, y2);
        this.mTrackDrawable.setBounds(this.mTrackRect);
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavGesture.GestureHelper
    public void onDarkIntensityChange(float intensity) {
        this.mDarkIntensity = intensity;
        this.mNavigationBarView.invalidate();
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavGesture.GestureHelper
    public void setBarState(boolean isVertical, boolean isRTL) {
        boolean z = false;
        boolean changed = (this.mIsVertical == isVertical && this.mIsRTL == isRTL) ? false : true;
        if (changed) {
            endQuickScrub(false);
        }
        this.mIsVertical = isVertical;
        this.mIsRTL = isRTL;
        try {
            int navbarPos = WindowManagerGlobal.getWindowManagerService().getNavBarPosition();
            if (navbarPos == 1 || navbarPos == 4) {
                z = true;
            }
            this.mDragPositive = z;
            if (isRTL) {
                this.mDragPositive = !this.mDragPositive;
            }
        } catch (RemoteException e) {
            Slog.e("QuickStepController", "Failed to get nav bar position.", e);
        }
    }

    @Override // com.android.systemui.plugins.statusbar.phone.NavGesture.GestureHelper
    public void onNavigationButtonLongPress(View v) {
        this.mAllowGestureDetection = false;
        this.mHandler.removeCallbacksAndMessages(null);
    }

    private void startQuickStep(MotionEvent event) {
        this.mQuickStepStarted = true;
        event.transform(this.mTransformGlobalMatrix);
        try {
            try {
                this.mOverviewEventSender.getProxy().onQuickStep(event);
            } catch (RemoteException e) {
                Log.e("QuickStepController", "Failed to send quick step started.", e);
            }
            event.transform(this.mTransformLocalMatrix);
            this.mOverviewEventSender.notifyQuickStepStarted();
            this.mHandler.removeCallbacksAndMessages(null);
            if (this.mHitTarget != null) {
                this.mHitTarget.abortCurrentGesture();
            }
            if (this.mQuickScrubActive) {
                animateEnd();
            }
        } catch (Throwable th) {
            event.transform(this.mTransformLocalMatrix);
            throw th;
        }
    }

    private void startQuickScrub() {
        if (!this.mQuickScrubActive) {
            this.mQuickScrubActive = true;
            this.mLightTrackColor = this.mContext.getColor(R.color.quick_step_track_background_light);
            this.mDarkTrackColor = this.mContext.getColor(R.color.quick_step_track_background_dark);
            ObjectAnimator trackAnimator = ObjectAnimator.ofPropertyValuesHolder(this, PropertyValuesHolder.ofFloat(this.mTrackAlphaProperty, 1.0f), PropertyValuesHolder.ofFloat(this.mTrackScaleProperty, 1.0f));
            trackAnimator.setInterpolator(Interpolators.ALPHA_IN);
            trackAnimator.setDuration(150L);
            ObjectAnimator navBarAnimator = ObjectAnimator.ofFloat(this, this.mNavBarAlphaProperty, 0.0f);
            navBarAnimator.setInterpolator(Interpolators.ALPHA_OUT);
            navBarAnimator.setDuration(134L);
            this.mTrackAnimator = new AnimatorSet();
            this.mTrackAnimator.playTogether(trackAnimator, navBarAnimator);
            this.mTrackAnimator.start();
            try {
                this.mOverviewEventSender.getProxy().onQuickScrubStart();
            } catch (RemoteException e) {
                Log.e("QuickStepController", "Failed to send start of quick scrub.", e);
            }
            this.mOverviewEventSender.notifyQuickScrubStarted();
            if (this.mHitTarget != null) {
                this.mHitTarget.abortCurrentGesture();
            }
        }
    }

    private void endQuickScrub(boolean animate) {
        if (this.mQuickScrubActive) {
            animateEnd();
            try {
                this.mOverviewEventSender.getProxy().onQuickScrubEnd();
            } catch (RemoteException e) {
                Log.e("QuickStepController", "Failed to send end of quick scrub.", e);
            }
        }
        if (!animate && this.mTrackAnimator != null) {
            this.mTrackAnimator.end();
            this.mTrackAnimator = null;
        }
    }

    private void animateEnd() {
        if (this.mTrackAnimator != null) {
            this.mTrackAnimator.cancel();
        }
        ObjectAnimator trackAnimator = ObjectAnimator.ofPropertyValuesHolder(this, PropertyValuesHolder.ofFloat(this.mTrackAlphaProperty, 0.0f), PropertyValuesHolder.ofFloat(this.mTrackScaleProperty, 0.95f));
        trackAnimator.setInterpolator(Interpolators.ALPHA_OUT);
        trackAnimator.setDuration(134L);
        ObjectAnimator navBarAnimator = ObjectAnimator.ofFloat(this, this.mNavBarAlphaProperty, 1.0f);
        navBarAnimator.setInterpolator(Interpolators.ALPHA_IN);
        navBarAnimator.setDuration(150L);
        this.mTrackAnimator = new AnimatorSet();
        this.mTrackAnimator.playTogether(trackAnimator, navBarAnimator);
        this.mTrackAnimator.addListener(this.mQuickScrubEndListener);
        this.mTrackAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetQuickScrub() {
        this.mQuickScrubActive = false;
        this.mAllowGestureDetection = false;
        this.mCurrentNavigationBarView = null;
    }

    private boolean proxyMotionEvents(MotionEvent event) {
        Matrix matrix;
        IOverviewProxy overviewProxy = this.mOverviewEventSender.getProxy();
        event.transform(this.mTransformGlobalMatrix);
        try {
            if (event.getActionMasked() == 0) {
                overviewProxy.onPreMotionEvent(this.mNavigationBarView.getDownHitTarget());
            }
            overviewProxy.onMotionEvent(event);
            return true;
        } catch (RemoteException e) {
            Log.e("QuickStepController", "Callback failed", e);
            return false;
        } finally {
            event.transform(this.mTransformLocalMatrix);
        }
    }
}
