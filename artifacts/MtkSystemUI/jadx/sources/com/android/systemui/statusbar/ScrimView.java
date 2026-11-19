package com.android.systemui.statusbar;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.colorextraction.drawable.GradientDrawable;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* JADX INFO: loaded from: classes.dex */
public class ScrimView extends View implements ConfigurationController.ConfigurationListener {
    private ValueAnimator mAlphaAnimator;
    private ValueAnimator.AnimatorUpdateListener mAlphaUpdateListener;
    private Runnable mChangeRunnable;
    private AnimatorListenerAdapter mClearAnimatorListener;
    private PorterDuffColorFilter mColorFilter;
    private final ColorExtractor.GradientColors mColors;
    private int mCornerRadius;
    private int mDensity;
    private boolean mDrawAsSrc;
    private Drawable mDrawable;
    private Rect mExcludedRect;
    private boolean mHasExcludedArea;
    private int mTintColor;
    private float mViewAlpha;

    public static /* synthetic */ void lambda$new$0(ScrimView scrimView, ValueAnimator animation) {
        if (scrimView.mDrawable == null) {
            Log.w("ScrimView", "Trying to animate null drawable");
        } else {
            scrimView.mDrawable.setAlpha((int) (255.0f * ((Float) animation.getAnimatedValue()).floatValue()));
        }
    }

    public ScrimView(Context context) {
        this(context, null);
    }

    public ScrimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrimView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ScrimView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mViewAlpha = 1.0f;
        this.mExcludedRect = new Rect();
        this.mAlphaUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.-$$Lambda$ScrimView$sc4BNvVbdVkUsFhB3vCR-4vbL8o
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScrimView.lambda$new$0(this.f$0, valueAnimator);
            }
        };
        this.mClearAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.ScrimView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                ScrimView.this.mAlphaAnimator = null;
            }
        };
        this.mDrawable = new GradientDrawable(context);
        this.mDrawable.setCallback(this);
        this.mColors = new ColorExtractor.GradientColors();
        updateScreenSize();
        updateColorWithTint(false);
        initView();
        Configuration currentConfig = this.mContext.getResources().getConfiguration();
        this.mDensity = currentConfig.densityDpi;
    }

    private void initView() {
        this.mCornerRadius = getResources().getDimensionPixelSize(Utils.getThemeAttr(this.mContext, R.attr.dialogCornerRadius));
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int densityDpi = newConfig.densityDpi;
        if (this.mDensity != densityDpi) {
            this.mDensity = densityDpi;
            initView();
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ConfigurationController config = (ConfigurationController) Dependency.get(ConfigurationController.class);
        config.addCallback(this);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ConfigurationController config = (ConfigurationController) Dependency.get(ConfigurationController.class);
        config.removeCallback(this);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mDrawAsSrc || this.mDrawable.getAlpha() > 0) {
            if (!this.mHasExcludedArea) {
                this.mDrawable.draw(canvas);
                return;
            }
            if (this.mExcludedRect.top > 0) {
                canvas.save();
                canvas.clipRect(0, 0, getWidth(), this.mExcludedRect.top);
                this.mDrawable.draw(canvas);
                canvas.restore();
            }
            if (this.mExcludedRect.left > 0) {
                canvas.save();
                canvas.clipRect(0, this.mExcludedRect.top, this.mExcludedRect.left, this.mExcludedRect.bottom);
                this.mDrawable.draw(canvas);
                canvas.restore();
            }
            if (this.mExcludedRect.right < getWidth()) {
                canvas.save();
                canvas.clipRect(this.mExcludedRect.right, this.mExcludedRect.top, getWidth(), this.mExcludedRect.bottom);
                this.mDrawable.draw(canvas);
                canvas.restore();
            }
            if (this.mExcludedRect.bottom < getHeight()) {
                canvas.save();
                canvas.clipRect(0, this.mExcludedRect.bottom, getWidth(), getHeight());
                this.mDrawable.draw(canvas);
                canvas.restore();
            }
            canvas.save();
            canvas.clipRect(this.mExcludedRect.left, this.mExcludedRect.top, this.mExcludedRect.left + this.mCornerRadius, this.mExcludedRect.top + this.mCornerRadius);
            this.mDrawable.draw(canvas);
            canvas.restore();
            canvas.save();
            canvas.clipRect(this.mExcludedRect.right - this.mCornerRadius, this.mExcludedRect.top, this.mExcludedRect.right, this.mExcludedRect.top + this.mCornerRadius);
            this.mDrawable.draw(canvas);
            canvas.restore();
            canvas.save();
            canvas.clipRect(this.mExcludedRect.left, this.mExcludedRect.bottom - this.mCornerRadius, this.mExcludedRect.left + this.mCornerRadius, this.mExcludedRect.bottom);
            this.mDrawable.draw(canvas);
            canvas.restore();
            canvas.save();
            canvas.clipRect(this.mExcludedRect.right - this.mCornerRadius, this.mExcludedRect.bottom - this.mCornerRadius, this.mExcludedRect.right, this.mExcludedRect.bottom);
            this.mDrawable.draw(canvas);
            canvas.restore();
        }
    }

    public void setDrawable(Drawable drawable) {
        this.mDrawable = drawable;
        this.mDrawable.setCallback(this);
        this.mDrawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
        this.mDrawable.setAlpha((int) (255.0f * this.mViewAlpha));
        setDrawAsSrc(this.mDrawAsSrc);
        updateScreenSize();
        invalidate();
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        super.invalidateDrawable(drawable);
        if (drawable == this.mDrawable) {
            invalidate();
        }
    }

    public void setDrawAsSrc(boolean asSrc) {
        this.mDrawAsSrc = asSrc;
        PorterDuff.Mode mode = asSrc ? PorterDuff.Mode.SRC : PorterDuff.Mode.SRC_OVER;
        this.mDrawable.setXfermode(new PorterDuffXfermode(mode));
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            this.mDrawable.setBounds(left, top, right, bottom);
            invalidate();
        }
    }

    public void setColors(ColorExtractor.GradientColors colors, boolean animated) {
        if (colors == null) {
            throw new IllegalArgumentException("Colors cannot be null");
        }
        if (this.mColors.equals(colors)) {
            return;
        }
        this.mColors.set(colors);
        updateColorWithTint(animated);
    }

    @VisibleForTesting
    Drawable getDrawable() {
        return this.mDrawable;
    }

    public ColorExtractor.GradientColors getColors() {
        return this.mColors;
    }

    public void setTint(int color) {
        setTint(color, false);
    }

    public void setTint(int color, boolean animated) {
        if (this.mTintColor == color) {
            return;
        }
        this.mTintColor = color;
        updateColorWithTint(animated);
    }

    private void updateColorWithTint(boolean animated) {
        if (this.mDrawable instanceof GradientDrawable) {
            GradientDrawable drawable = this.mDrawable;
            float tintAmount = Color.alpha(this.mTintColor) / 255.0f;
            int mainTinted = ColorUtils.blendARGB(this.mColors.getMainColor(), this.mTintColor, tintAmount);
            int secondaryTinted = ColorUtils.blendARGB(this.mColors.getSecondaryColor(), this.mTintColor, tintAmount);
            drawable.setColors(mainTinted, secondaryTinted, animated);
        } else {
            if (this.mColorFilter == null) {
                this.mColorFilter = new PorterDuffColorFilter(this.mTintColor, PorterDuff.Mode.SRC_OVER);
            } else {
                this.mColorFilter.setColor(this.mTintColor);
            }
            this.mDrawable.setColorFilter(Color.alpha(this.mTintColor) == 0 ? null : this.mColorFilter);
            this.mDrawable.invalidateSelf();
        }
        if (this.mChangeRunnable != null) {
            this.mChangeRunnable.run();
        }
    }

    public int getTint() {
        return this.mTintColor;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public void setViewAlpha(float alpha) {
        if (alpha != this.mViewAlpha) {
            this.mViewAlpha = alpha;
            if (this.mAlphaAnimator != null) {
                this.mAlphaAnimator.cancel();
            }
            this.mDrawable.setAlpha((int) (255.0f * alpha));
            if (this.mChangeRunnable != null) {
                this.mChangeRunnable.run();
            }
        }
    }

    public float getViewAlpha() {
        return this.mViewAlpha;
    }

    public void setExcludedArea(Rect area) {
        boolean z = false;
        if (area != null) {
            int left = Math.max(area.left, 0);
            int top = Math.max(area.top, 0);
            int right = Math.min(area.right, getWidth());
            int bottom = Math.min(area.bottom, getHeight());
            this.mExcludedRect.set(left, top, right, bottom);
            if (left < right && top < bottom) {
                z = true;
            }
            this.mHasExcludedArea = z;
            invalidate();
            return;
        }
        this.mHasExcludedArea = false;
        invalidate();
    }

    public void setChangeRunnable(Runnable changeRunnable) {
        this.mChangeRunnable = changeRunnable;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onConfigChanged(Configuration newConfig) {
        updateScreenSize();
    }

    private void updateScreenSize() {
        if (this.mDrawable instanceof GradientDrawable) {
            WindowManager wm = (WindowManager) this.mContext.getSystemService(WindowManager.class);
            if (wm == null) {
                Log.w("ScrimView", "Can't resize gradient drawable to fit the screen");
                return;
            }
            Display display = wm.getDefaultDisplay();
            if (display != null) {
                Point size = new Point();
                display.getRealSize(size);
                this.mDrawable.setScreenSize(size.x, size.y);
            }
        }
    }
}
