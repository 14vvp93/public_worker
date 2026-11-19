package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.util.ArrayUtils;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.ActivityLaunchAnimator;

/* JADX INFO: loaded from: classes.dex */
public class NotificationBackgroundView extends View {
    private int mActualHeight;
    private float mActualWidth;
    private Drawable mBackground;
    private int mBackgroundTop;
    private boolean mBottomAmountClips;
    private boolean mBottomIsRounded;
    private int mClipBottomAmount;
    private int mClipTopAmount;
    private float[] mCornerRadii;
    private float mDistanceToTopRoundness;
    private final boolean mDontModifyCorners;
    private int mDrawableAlpha;
    private boolean mExpandAnimationRunning;
    private boolean mIsPressedAllowed;
    private int mTintColor;
    private boolean mTopAmountRounded;

    public NotificationBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCornerRadii = new float[8];
        this.mBottomAmountClips = true;
        this.mDrawableAlpha = 255;
        this.mDontModifyCorners = getResources().getBoolean(R.bool.config_clipNotificationsToOutline);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mClipTopAmount + this.mClipBottomAmount < this.mActualHeight - this.mBackgroundTop || this.mExpandAnimationRunning) {
            canvas.save();
            if (!this.mExpandAnimationRunning) {
                canvas.clipRect(0, this.mClipTopAmount, getWidth(), this.mActualHeight - this.mClipBottomAmount);
            }
            draw(canvas, this.mBackground);
            canvas.restore();
        }
    }

    private void draw(Canvas canvas, Drawable drawable) {
        if (drawable != null) {
            int top = this.mBackgroundTop;
            int bottom = this.mActualHeight;
            if (this.mBottomIsRounded && this.mBottomAmountClips && !this.mExpandAnimationRunning) {
                bottom -= this.mClipBottomAmount;
            }
            int left = 0;
            int right = getWidth();
            if (this.mExpandAnimationRunning) {
                left = (int) ((getWidth() - this.mActualWidth) / 2.0f);
                right = (int) (left + this.mActualWidth);
            }
            if (this.mTopAmountRounded) {
                int clipTop = (int) (this.mClipTopAmount - this.mDistanceToTopRoundness);
                top += clipTop;
                if (clipTop >= 0) {
                    bottom += clipTop;
                }
            }
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.mBackground;
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        setState(getDrawableState());
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float x, float y) {
        if (this.mBackground != null) {
            this.mBackground.setHotspot(x, y);
        }
    }

    public void setCustomBackground(Drawable background) {
        if (this.mBackground != null) {
            this.mBackground.setCallback(null);
            unscheduleDrawable(this.mBackground);
        }
        this.mBackground = background;
        this.mBackground.mutate();
        if (this.mBackground != null) {
            this.mBackground.setCallback(this);
            setTint(this.mTintColor);
        }
        if (this.mBackground instanceof RippleDrawable) {
            ((RippleDrawable) this.mBackground).setForceSoftware(true);
        }
        updateBackgroundRadii();
        invalidate();
    }

    public void setCustomBackground(int drawableResId) {
        Drawable d = this.mContext.getDrawable(drawableResId);
        setCustomBackground(d);
    }

    public void setTint(int tintColor) {
        if (tintColor != 0) {
            this.mBackground.setColorFilter(tintColor, PorterDuff.Mode.SRC_ATOP);
        } else {
            this.mBackground.clearColorFilter();
        }
        this.mTintColor = tintColor;
        invalidate();
    }

    public void setActualHeight(int actualHeight) {
        if (this.mExpandAnimationRunning) {
            return;
        }
        this.mActualHeight = actualHeight;
        invalidate();
    }

    public int getActualHeight() {
        return this.mActualHeight;
    }

    public void setClipTopAmount(int clipTopAmount) {
        this.mClipTopAmount = clipTopAmount;
        invalidate();
    }

    public void setClipBottomAmount(int clipBottomAmount) {
        this.mClipBottomAmount = clipBottomAmount;
        invalidate();
    }

    public void setDistanceToTopRoundness(float distanceToTopRoundness) {
        if (distanceToTopRoundness != this.mDistanceToTopRoundness) {
            this.mTopAmountRounded = distanceToTopRoundness >= 0.0f;
            this.mDistanceToTopRoundness = distanceToTopRoundness;
            invalidate();
        }
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public void setState(int[] drawableState) {
        if (this.mBackground != null && this.mBackground.isStateful()) {
            if (!this.mIsPressedAllowed) {
                drawableState = ArrayUtils.removeInt(drawableState, android.R.attr.state_pressed);
            }
            this.mBackground.setState(drawableState);
        }
    }

    public void setRippleColor(int color) {
        if (this.mBackground instanceof RippleDrawable) {
            RippleDrawable ripple = (RippleDrawable) this.mBackground;
            ripple.setColor(ColorStateList.valueOf(color));
        }
    }

    public void setDrawableAlpha(int drawableAlpha) {
        this.mDrawableAlpha = drawableAlpha;
        if (this.mExpandAnimationRunning) {
            return;
        }
        this.mBackground.setAlpha(drawableAlpha);
    }

    public void setRoundness(float topRoundness, float bottomRoundNess) {
        if (topRoundness == this.mCornerRadii[0] && bottomRoundNess == this.mCornerRadii[4]) {
            return;
        }
        this.mBottomIsRounded = bottomRoundNess != 0.0f;
        this.mCornerRadii[0] = topRoundness;
        this.mCornerRadii[1] = topRoundness;
        this.mCornerRadii[2] = topRoundness;
        this.mCornerRadii[3] = topRoundness;
        this.mCornerRadii[4] = bottomRoundNess;
        this.mCornerRadii[5] = bottomRoundNess;
        this.mCornerRadii[6] = bottomRoundNess;
        this.mCornerRadii[7] = bottomRoundNess;
        updateBackgroundRadii();
    }

    public void setBottomAmountClips(boolean clips) {
        if (clips != this.mBottomAmountClips) {
            this.mBottomAmountClips = clips;
            invalidate();
        }
    }

    private void updateBackgroundRadii() {
        if (!this.mDontModifyCorners && (this.mBackground instanceof LayerDrawable)) {
            GradientDrawable gradientDrawable = (GradientDrawable) ((LayerDrawable) this.mBackground).getDrawable(0);
            gradientDrawable.setCornerRadii(this.mCornerRadii);
        }
    }

    public void setBackgroundTop(int backgroundTop) {
        this.mBackgroundTop = backgroundTop;
        invalidate();
    }

    public void setExpandAnimationParams(ActivityLaunchAnimator.ExpandAnimationParameters params) {
        this.mActualHeight = params.getHeight();
        this.mActualWidth = params.getWidth();
        float alphaProgress = Interpolators.ALPHA_IN.getInterpolation(params.getProgress(67L, 200L));
        this.mBackground.setAlpha((int) (this.mDrawableAlpha * (1.0f - alphaProgress)));
        invalidate();
    }

    public void setExpandAnimationRunning(boolean running) {
        this.mExpandAnimationRunning = running;
        if (this.mBackground instanceof LayerDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) ((LayerDrawable) this.mBackground).getDrawable(0);
            gradientDrawable.setXfermode(running ? new PorterDuffXfermode(PorterDuff.Mode.SRC) : null);
            gradientDrawable.setAntiAlias(!running);
        }
        if (!this.mExpandAnimationRunning) {
            setDrawableAlpha(this.mDrawableAlpha);
        }
        invalidate();
    }

    public void setPressedAllowed(boolean allowed) {
        this.mIsPressedAllowed = allowed;
    }
}
