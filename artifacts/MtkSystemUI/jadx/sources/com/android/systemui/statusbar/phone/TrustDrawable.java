package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;
import com.android.settingslib.Utils;
import com.android.systemui.Interpolators;
import com.android.systemui.R;

/* JADX INFO: loaded from: classes.dex */
public class TrustDrawable extends Drawable {
    private int mAlpha;
    private boolean mAnimating;
    private int mCurAlpha;
    private Animator mCurAnimator;
    private float mCurInnerRadius;
    private final float mInnerRadiusEnter;
    private final float mInnerRadiusExit;
    private final float mInnerRadiusVisibleMax;
    private final float mInnerRadiusVisibleMin;
    private Paint mPaint;
    private final float mThickness;
    private boolean mTrustManaged;
    private final Animator mVisibleAnimator;
    private int mState = -1;
    private final ValueAnimator.AnimatorUpdateListener mAlphaUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.TrustDrawable.1
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator animation) {
            TrustDrawable.this.mCurAlpha = ((Integer) animation.getAnimatedValue()).intValue();
            TrustDrawable.this.invalidateSelf();
        }
    };
    private final ValueAnimator.AnimatorUpdateListener mRadiusUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.TrustDrawable.2
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator animation) {
            TrustDrawable.this.mCurInnerRadius = ((Float) animation.getAnimatedValue()).floatValue();
            TrustDrawable.this.invalidateSelf();
        }
    };

    public TrustDrawable(Context context) {
        Resources r = context.getResources();
        this.mInnerRadiusVisibleMin = r.getDimension(R.dimen.trust_circle_inner_radius_visible_min);
        this.mInnerRadiusVisibleMax = r.getDimension(R.dimen.trust_circle_inner_radius_visible_max);
        this.mInnerRadiusExit = r.getDimension(R.dimen.trust_circle_inner_radius_exit);
        this.mInnerRadiusEnter = r.getDimension(R.dimen.trust_circle_inner_radius_enter);
        this.mThickness = r.getDimension(R.dimen.trust_circle_thickness);
        this.mCurInnerRadius = this.mInnerRadiusEnter;
        this.mVisibleAnimator = makeVisibleAnimator();
        this.mPaint = new Paint();
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setColor(Utils.getColorAttr(context, R.attr.wallpaperTextColor));
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeWidth(this.mThickness);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int newAlpha = (this.mCurAlpha * this.mAlpha) / 256;
        if (newAlpha == 0) {
            return;
        }
        Rect r = getBounds();
        this.mPaint.setAlpha(newAlpha);
        canvas.drawCircle(r.exactCenterX(), r.exactCenterY(), this.mCurInnerRadius, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        this.mAlpha = alpha;
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public void start() {
        if (!this.mAnimating) {
            this.mAnimating = true;
            updateState(true);
            invalidateSelf();
        }
    }

    public void stop() {
        if (this.mAnimating) {
            this.mAnimating = false;
            if (this.mCurAnimator != null) {
                this.mCurAnimator.cancel();
                this.mCurAnimator = null;
            }
            this.mState = -1;
            this.mCurAlpha = 0;
            this.mCurInnerRadius = this.mInnerRadiusEnter;
            invalidateSelf();
        }
    }

    public void setTrustManaged(boolean trustManaged) {
        if (trustManaged != this.mTrustManaged || this.mState == -1) {
            this.mTrustManaged = trustManaged;
            updateState(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7, types: [int] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    public void updateState(boolean z) {
        ?? r0;
        if (!this.mAnimating) {
            return;
        }
        int i = this.mState;
        if (this.mState == -1) {
            r0 = this.mTrustManaged;
        } else if (this.mState == 0) {
            if (this.mTrustManaged) {
                r0 = i;
                r0 = 1;
            }
        } else if (this.mState == 1) {
            if (!this.mTrustManaged) {
                r0 = i;
                r0 = 3;
            }
        } else if (this.mState == 2) {
            if (!this.mTrustManaged) {
                r0 = i;
                r0 = 3;
            }
        } else if (this.mState == 3 && this.mTrustManaged) {
            r0 = i;
            r0 = i;
            r0 = 1;
        }
        if (!z) {
            if (r0 == 1) {
                r0 = 2;
            }
            if (r0 == 3) {
                r0 = 0;
            }
        }
        if (r0 != this.mState) {
            if (this.mCurAnimator != null) {
                this.mCurAnimator.cancel();
                this.mCurAnimator = null;
            }
            if (r0 == 0) {
                this.mCurAlpha = 0;
                this.mCurInnerRadius = this.mInnerRadiusEnter;
            } else if (r0 == 1) {
                this.mCurAnimator = makeEnterAnimator(this.mCurInnerRadius, this.mCurAlpha);
                if (this.mState == -1) {
                    this.mCurAnimator.setStartDelay(200L);
                }
            } else if (r0 == 2) {
                this.mCurAlpha = 76;
                this.mCurInnerRadius = this.mInnerRadiusVisibleMax;
                this.mCurAnimator = this.mVisibleAnimator;
            } else if (r0 == 3) {
                this.mCurAnimator = makeExitAnimator(this.mCurInnerRadius, this.mCurAlpha);
            }
            this.mState = r0;
            if (this.mCurAnimator != null) {
                this.mCurAnimator.start();
            }
            invalidateSelf();
        }
    }

    private Animator makeVisibleAnimator() {
        return makeAnimators(this.mInnerRadiusVisibleMax, this.mInnerRadiusVisibleMin, 76, 38, 1000L, Interpolators.ACCELERATE_DECELERATE, true, false);
    }

    private Animator makeEnterAnimator(float radius, int alpha) {
        return makeAnimators(radius, this.mInnerRadiusVisibleMax, alpha, 76, 500L, Interpolators.LINEAR_OUT_SLOW_IN, false, true);
    }

    private Animator makeExitAnimator(float radius, int alpha) {
        return makeAnimators(radius, this.mInnerRadiusExit, alpha, 0, 500L, Interpolators.FAST_OUT_SLOW_IN, false, true);
    }

    private Animator makeAnimators(float startRadius, float endRadius, int startAlpha, int endAlpha, long duration, Interpolator interpolator, boolean repeating, boolean stateUpdateListener) {
        ValueAnimator alphaAnimator = configureAnimator(ValueAnimator.ofInt(startAlpha, endAlpha), duration, this.mAlphaUpdateListener, interpolator, repeating);
        ValueAnimator sizeAnimator = configureAnimator(ValueAnimator.ofFloat(startRadius, endRadius), duration, this.mRadiusUpdateListener, interpolator, repeating);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alphaAnimator, sizeAnimator);
        if (stateUpdateListener) {
            set.addListener(new StateUpdateAnimatorListener());
        }
        return set;
    }

    private ValueAnimator configureAnimator(ValueAnimator animator, long duration, ValueAnimator.AnimatorUpdateListener updateListener, Interpolator interpolator, boolean repeating) {
        animator.setDuration(duration);
        animator.addUpdateListener(updateListener);
        animator.setInterpolator(interpolator);
        if (repeating) {
            animator.setRepeatCount(-1);
            animator.setRepeatMode(2);
        }
        return animator;
    }

    private class StateUpdateAnimatorListener extends AnimatorListenerAdapter {
        boolean mCancelled;

        private StateUpdateAnimatorListener() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animation) {
            this.mCancelled = false;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animation) {
            this.mCancelled = true;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            if (!this.mCancelled) {
                TrustDrawable.this.updateState(false);
            }
        }
    }
}
