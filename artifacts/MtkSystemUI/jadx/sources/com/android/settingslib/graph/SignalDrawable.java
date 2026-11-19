package com.android.settingslib.graph;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import com.android.settingslib.R;
import com.android.settingslib.Utils;

/* JADX INFO: loaded from: classes.dex */
public class SignalDrawable extends Drawable {
    private boolean mAnimating;
    private final float mAppliedCornerInset;
    private int mCurrentDot;
    private final int mDarkModeBackgroundColor;
    private final int mDarkModeFillColor;
    private int mIntrinsicSize;
    private int mLevel;
    private final int mLightModeBackgroundColor;
    private final int mLightModeFillColor;
    private int mState;
    private boolean mVisible;
    private static final float[] FIT = {2.26f, -3.02f, 1.76f};
    private static float[][] X_PATH = {new float[]{0.91249996f, 0.7083333f}, new float[]{-0.045833334f, -0.045833334f}, new float[]{-0.079166666f, 0.079166666f}, new float[]{-0.079166666f, -0.079166666f}, new float[]{-0.045833334f, 0.045833334f}, new float[]{0.079166666f, 0.079166666f}, new float[]{-0.079166666f, 0.079166666f}, new float[]{0.045833334f, 0.045833334f}, new float[]{0.079166666f, -0.079166666f}, new float[]{0.079166666f, 0.079166666f}, new float[]{0.045833334f, -0.045833334f}, new float[]{-0.079166666f, -0.079166666f}};
    private static final float INV_TAN = 1.0f / ((float) Math.tan(0.39269908169872414d));
    private final PointF mVirtualTop = new PointF();
    private final PointF mVirtualLeft = new PointF();
    private final Paint mPaint = new Paint(1);
    private final Paint mForegroundPaint = new Paint(1);
    private final Path mFullPath = new Path();
    private final Path mForegroundPath = new Path();
    private final Path mXPath = new Path();
    private final Path mCutPath = new Path();
    private final SlashArtist mSlash = new SlashArtist();
    private float mOldDarkIntensity = -1.0f;
    private float mNumLevels = 1.0f;
    private final Runnable mChangeDot = new Runnable() { // from class: com.android.settingslib.graph.SignalDrawable.1
        @Override // java.lang.Runnable
        public void run() {
            if (SignalDrawable.access$104(SignalDrawable.this) == 3) {
                SignalDrawable.this.mCurrentDot = 0;
            }
            SignalDrawable.this.invalidateSelf();
            SignalDrawable.this.mHandler.postDelayed(SignalDrawable.this.mChangeDot, 1000L);
        }
    };
    private final Handler mHandler = new Handler();

    static /* synthetic */ int access$104(SignalDrawable x0) {
        int i = x0.mCurrentDot + 1;
        x0.mCurrentDot = i;
        return i;
    }

    public SignalDrawable(Context context) {
        this.mDarkModeBackgroundColor = Utils.getDefaultColor(context, R.color.dark_mode_icon_color_dual_tone_background);
        this.mDarkModeFillColor = Utils.getDefaultColor(context, R.color.dark_mode_icon_color_dual_tone_fill);
        this.mLightModeBackgroundColor = Utils.getDefaultColor(context, R.color.light_mode_icon_color_dual_tone_background);
        this.mLightModeFillColor = Utils.getDefaultColor(context, R.color.light_mode_icon_color_dual_tone_fill);
        this.mIntrinsicSize = context.getResources().getDimensionPixelSize(R.dimen.signal_icon_size);
        setDarkIntensity(0.0f);
        this.mAppliedCornerInset = context.getResources().getDimensionPixelSize(R.dimen.stat_sys_mobile_signal_circle_inset);
    }

    public void setIntrinsicSize(int size) {
        this.mIntrinsicSize = size;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mIntrinsicSize;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mIntrinsicSize;
    }

    public void setNumLevels(int levels) {
        if (levels == this.mNumLevels) {
            return;
        }
        this.mNumLevels = levels;
        invalidateSelf();
    }

    private void setSignalState(int state) {
        if (state == this.mState) {
            return;
        }
        this.mState = state;
        updateAnimation();
        invalidateSelf();
    }

    private void updateAnimation() {
        boolean shouldAnimate = this.mState == 3 && this.mVisible;
        if (shouldAnimate == this.mAnimating) {
            return;
        }
        this.mAnimating = shouldAnimate;
        if (shouldAnimate) {
            this.mChangeDot.run();
        } else {
            this.mHandler.removeCallbacks(this.mChangeDot);
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int state) {
        setNumLevels(getNumLevels(state));
        setSignalState(getState(state));
        int level = getLevel(state);
        if (level != this.mLevel) {
            this.mLevel = level;
            invalidateSelf();
            return true;
        }
        return true;
    }

    public void setColors(int background, int foreground) {
        this.mPaint.setColor(background);
        this.mForegroundPaint.setColor(foreground);
    }

    public void setDarkIntensity(float darkIntensity) {
        if (darkIntensity == this.mOldDarkIntensity) {
            return;
        }
        this.mPaint.setColor(getBackgroundColor(darkIntensity));
        this.mForegroundPaint.setColor(getFillColor(darkIntensity));
        this.mOldDarkIntensity = darkIntensity;
        invalidateSelf();
    }

    private int getFillColor(float darkIntensity) {
        return getColorForDarkIntensity(darkIntensity, this.mLightModeFillColor, this.mDarkModeFillColor);
    }

    private int getBackgroundColor(float darkIntensity) {
        return getColorForDarkIntensity(darkIntensity, this.mLightModeBackgroundColor, this.mDarkModeBackgroundColor);
    }

    private int getColorForDarkIntensity(float darkIntensity, int lightColor, int darkColor) {
        return ((Integer) ArgbEvaluator.getInstance().evaluate(darkIntensity, Integer.valueOf(lightColor), Integer.valueOf(darkColor))).intValue();
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int i;
        float padding;
        int i2;
        float width = getBounds().width();
        float height = getBounds().height();
        boolean isRtl = getLayoutDirection() == 1;
        if (isRtl) {
            canvas.save();
            canvas.translate(width, 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        this.mFullPath.reset();
        this.mFullPath.setFillType(Path.FillType.WINDING);
        float padding2 = Math.round(0.083333336f * width);
        float cornerRadius = 0.04411765f * height;
        float diagOffset = 0.707107f * cornerRadius;
        this.mFullPath.moveTo(width - padding2, (height - padding2) - cornerRadius);
        this.mFullPath.lineTo(width - padding2, padding2 + cornerRadius + this.mAppliedCornerInset);
        this.mFullPath.arcTo((width - padding2) - (2.0f * cornerRadius), padding2 + this.mAppliedCornerInset, width - padding2, this.mAppliedCornerInset + padding2 + (2.0f * cornerRadius), 0.0f, -135.0f, false);
        this.mFullPath.lineTo(((this.mAppliedCornerInset + padding2) + cornerRadius) - diagOffset, ((height - padding2) - cornerRadius) - diagOffset);
        this.mFullPath.arcTo(padding2 + this.mAppliedCornerInset, (height - padding2) - (2.0f * cornerRadius), this.mAppliedCornerInset + padding2 + (2.0f * cornerRadius), height - padding2, -135.0f, -135.0f, false);
        this.mFullPath.lineTo((width - padding2) - cornerRadius, height - padding2);
        this.mFullPath.arcTo((width - padding2) - (2.0f * cornerRadius), (height - padding2) - (2.0f * cornerRadius), width - padding2, height - padding2, 90.0f, -90.0f, false);
        if (this.mState == 3) {
            float cutWidth = 0.5833334f * width;
            float cutHeight = 0.16666667f * width;
            float dotSize = 0.125f * height;
            float dotPadding = 0.041666668f * height;
            this.mFullPath.moveTo(width - padding2, height - padding2);
            this.mFullPath.rLineTo(-cutWidth, 0.0f);
            this.mFullPath.rLineTo(0.0f, -cutHeight);
            this.mFullPath.rLineTo(cutWidth, 0.0f);
            this.mFullPath.rLineTo(0.0f, cutHeight);
            float dotSpacing = (dotPadding * 2.0f) + dotSize;
            float x = (width - padding2) - dotSize;
            float y = (height - padding2) - dotSize;
            this.mForegroundPath.reset();
            i = 3;
            i2 = 2;
            padding = padding2;
            drawDot(this.mFullPath, this.mForegroundPath, x, y, dotSize, 2);
            drawDot(this.mFullPath, this.mForegroundPath, x - dotSpacing, y, dotSize, 1);
            drawDot(this.mFullPath, this.mForegroundPath, x - (dotSpacing * 2.0f), y, dotSize, 0);
        } else {
            i = 3;
            padding = padding2;
            i2 = 2;
            if (this.mState == 2) {
                float cut = 0.32916668f * width;
                this.mFullPath.moveTo(width - padding, height - padding);
                this.mFullPath.rLineTo(-cut, 0.0f);
                this.mFullPath.rLineTo(0.0f, -cut);
                this.mFullPath.rLineTo(cut, 0.0f);
                this.mFullPath.rLineTo(0.0f, cut);
            }
        }
        if (this.mState == 1) {
            this.mVirtualTop.set(width - padding, ((padding + cornerRadius) + this.mAppliedCornerInset) - (INV_TAN * cornerRadius));
            this.mVirtualLeft.set(((padding + cornerRadius) + this.mAppliedCornerInset) - (INV_TAN * cornerRadius), height - padding);
            float cutWidth2 = 0.083333336f * height;
            float cutDiagInset = INV_TAN * cutWidth2;
            this.mCutPath.reset();
            this.mCutPath.setFillType(Path.FillType.WINDING);
            this.mCutPath.moveTo((width - padding) - cutWidth2, (height - padding) - cutWidth2);
            this.mCutPath.lineTo((width - padding) - cutWidth2, this.mVirtualTop.y + cutDiagInset);
            this.mCutPath.lineTo(this.mVirtualLeft.x + cutDiagInset, (height - padding) - cutWidth2);
            this.mCutPath.lineTo((width - padding) - cutWidth2, (height - padding) - cutWidth2);
            this.mForegroundPath.reset();
            this.mFullPath.op(this.mCutPath, Path.Op.DIFFERENCE);
        } else if (this.mState == 4) {
            this.mForegroundPath.reset();
            this.mSlash.draw((int) height, (int) width, canvas, this.mPaint);
        } else if (this.mState != i) {
            this.mForegroundPath.reset();
            int sigWidth = Math.round(calcFit(this.mLevel / (this.mNumLevels - 1.0f)) * (width - (2.0f * padding)));
            this.mForegroundPath.addRect(padding, padding, padding + sigWidth, height - padding, Path.Direction.CW);
            this.mForegroundPath.op(this.mFullPath, Path.Op.INTERSECT);
        }
        canvas.drawPath(this.mFullPath, this.mPaint);
        canvas.drawPath(this.mForegroundPath, this.mForegroundPaint);
        if (this.mState == i2) {
            this.mXPath.reset();
            this.mXPath.moveTo(X_PATH[0][0] * width, X_PATH[0][1] * height);
            for (int i3 = 1; i3 < X_PATH.length; i3++) {
                this.mXPath.rLineTo(X_PATH[i3][0] * width, X_PATH[i3][1] * height);
            }
            canvas.drawPath(this.mXPath, this.mForegroundPaint);
        }
        if (isRtl) {
            canvas.restore();
        }
    }

    private void drawDot(Path fullPath, Path foregroundPath, float x, float y, float dotSize, int i) {
        Path p = i == this.mCurrentDot ? foregroundPath : fullPath;
        p.addRect(x, y, x + dotSize, y + dotSize, Path.Direction.CW);
    }

    private float calcFit(float v) {
        float ret = 0.0f;
        float t = v;
        for (int i = 0; i < FIT.length; i++) {
            ret += FIT[i] * t;
            t *= v;
        }
        return ret;
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
        this.mForegroundPaint.setAlpha(alpha);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        this.mForegroundPaint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 255;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean visible, boolean restart) {
        this.mVisible = visible;
        updateAnimation();
        return super.setVisible(visible, restart);
    }

    public static int getLevel(int fullState) {
        return fullState & 255;
    }

    public static int getState(int fullState) {
        return (16711680 & fullState) >> 16;
    }

    public static int getNumLevels(int fullState) {
        return (65280 & fullState) >> 8;
    }

    public static int getState(int level, int numLevels, boolean cutOut) {
        return ((cutOut ? 2 : 0) << 16) | (numLevels << 8) | level;
    }

    public static int getCarrierChangeState(int numLevels) {
        return (numLevels << 8) | 196608;
    }

    public static int getEmptyState(int numLevels) {
        return (numLevels << 8) | 65536;
    }

    public static int getAirplaneModeState(int numLevels) {
        return (numLevels << 8) | 262144;
    }

    private final class SlashArtist {
        private final Path mPath;
        private final RectF mSlashRect;

        private SlashArtist() {
            this.mPath = new Path();
            this.mSlashRect = new RectF();
        }

        void draw(int height, int width, Canvas canvas, Paint paint) {
            Matrix m = new Matrix();
            float radius = scale(1.0f, width);
            updateRect(scale(0.40544835f, width), scale(0.20288496f, height), scale(0.4820516f, width), scale(1.1195517f, height));
            this.mPath.reset();
            this.mPath.addRoundRect(this.mSlashRect, radius, radius, Path.Direction.CW);
            m.setRotate(-45.0f, width / 2, height / 2);
            this.mPath.transform(m);
            canvas.drawPath(this.mPath, paint);
            m.setRotate(45.0f, width / 2, height / 2);
            this.mPath.transform(m);
            m.setTranslate(this.mSlashRect.width(), 0.0f);
            this.mPath.transform(m);
            this.mPath.addRoundRect(this.mSlashRect, radius, radius, Path.Direction.CW);
            m.setRotate(-45.0f, width / 2, height / 2);
            this.mPath.transform(m);
            canvas.clipOutPath(this.mPath);
        }

        void updateRect(float left, float top, float right, float bottom) {
            this.mSlashRect.left = left;
            this.mSlashRect.top = top;
            this.mSlashRect.right = right;
            this.mSlashRect.bottom = bottom;
        }

        private float scale(float frac, int width) {
            return width * frac;
        }
    }
}
