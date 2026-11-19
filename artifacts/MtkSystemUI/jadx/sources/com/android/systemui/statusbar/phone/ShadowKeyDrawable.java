package com.android.systemui.statusbar.phone;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
public class ShadowKeyDrawable extends Drawable {
    private final Paint mPaint;
    private final ShadowDrawableState mState;

    public ShadowKeyDrawable(Drawable d) {
        this(d, new ShadowDrawableState());
    }

    private ShadowKeyDrawable(Drawable d, ShadowDrawableState state) {
        this.mPaint = new Paint(3);
        this.mState = state;
        if (d != null) {
            this.mState.mBaseHeight = d.getIntrinsicHeight();
            this.mState.mBaseWidth = d.getIntrinsicWidth();
            this.mState.mChangingConfigurations = d.getChangingConfigurations();
            this.mState.mChildState = d.getConstantState();
        }
    }

    public void setRotation(float degrees) {
        if (this.mState.mRotateDegrees != degrees) {
            this.mState.mRotateDegrees = degrees;
            this.mState.mLastDrawnBitmap = null;
            invalidateSelf();
        }
    }

    public void setShadowProperties(int x, int y, int size, int color) {
        if (this.mState.mShadowOffsetX != x || this.mState.mShadowOffsetY != y || this.mState.mShadowSize != size || this.mState.mShadowColor != color) {
            this.mState.mShadowOffsetX = x;
            this.mState.mShadowOffsetY = y;
            this.mState.mShadowSize = size;
            this.mState.mShadowColor = color;
            this.mState.mLastDrawnBitmap = null;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        if (bounds.isEmpty()) {
            return;
        }
        if (this.mState.mLastDrawnBitmap == null) {
            regenerateBitmapCache();
        }
        canvas.drawBitmap(this.mState.mLastDrawnBitmap, (Rect) null, bounds, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public void setTint(int tintColor) {
        super.setTint(tintColor);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.mState;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mState.mBaseHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mState.mBaseWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        return this.mState.canApplyTheme();
    }

    private void regenerateBitmapCache() {
        int width = getIntrinsicWidth();
        int height = getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.save();
        float radians = (float) ((((double) this.mState.mRotateDegrees) * 3.141592653589793d) / 180.0d);
        if (this.mState.mShadowSize == 0) {
            canvas.rotate(this.mState.mRotateDegrees, width / 2, height / 2);
        }
        Drawable d = this.mState.mChildState.newDrawable().mutate();
        d.setBounds(0, 0, this.mState.mBaseWidth, this.mState.mBaseHeight);
        d.draw(canvas);
        if (this.mState.mShadowSize > 0) {
            Paint paint = new Paint(3);
            paint.setMaskFilter(new BlurMaskFilter(this.mState.mShadowSize, BlurMaskFilter.Blur.NORMAL));
            int[] offset = new int[2];
            Bitmap shadow = bitmap.extractAlpha(paint, offset);
            paint.setMaskFilter(null);
            paint.setColor(this.mState.mShadowColor);
            bitmap.eraseColor(0);
            canvas.rotate(this.mState.mRotateDegrees, width / 2, height / 2);
            float shadowOffsetX = (float) ((Math.sin(radians) * ((double) this.mState.mShadowOffsetY)) + (Math.cos(radians) * ((double) this.mState.mShadowOffsetX)));
            float shadowOffsetY = (float) ((Math.cos(radians) * ((double) this.mState.mShadowOffsetY)) - (Math.sin(radians) * ((double) this.mState.mShadowOffsetX)));
            canvas.drawBitmap(shadow, offset[0] + shadowOffsetX, offset[1] + shadowOffsetY, paint);
            d.draw(canvas);
        }
        this.mState.mLastDrawnBitmap = bitmap.copy(Bitmap.Config.HARDWARE, false);
        canvas.restore();
    }

    private static class ShadowDrawableState extends Drawable.ConstantState {
        int mBaseHeight;
        int mBaseWidth;
        int mChangingConfigurations;
        Drawable.ConstantState mChildState;
        Bitmap mLastDrawnBitmap;
        float mRotateDegrees;
        int mShadowColor;
        int mShadowOffsetX;
        int mShadowOffsetY;
        int mShadowSize;

        private ShadowDrawableState() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new ShadowKeyDrawable(null, this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return true;
        }
    }
}
