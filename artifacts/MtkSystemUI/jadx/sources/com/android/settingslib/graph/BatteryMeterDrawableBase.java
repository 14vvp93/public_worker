package com.android.settingslib.graph;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import com.android.settingslib.R;
import com.android.settingslib.Utils;

/* JADX INFO: loaded from: classes.dex */
public class BatteryMeterDrawableBase extends Drawable {
    public static final String TAG = BatteryMeterDrawableBase.class.getSimpleName();
    protected final Paint mBatteryPaint;
    protected final Paint mBoltPaint;
    private final float[] mBoltPoints;
    protected float mButtonHeightFraction;
    private int mChargeColor;
    private boolean mCharging;
    private final int[] mColors;
    protected final Context mContext;
    private final int mCriticalLevel;
    protected final Paint mFramePaint;
    private int mHeight;
    private final int mIntrinsicHeight;
    private final int mIntrinsicWidth;
    protected final Paint mPlusPaint;
    private final float[] mPlusPoints;
    private boolean mPowerSaveEnabled;
    protected final Paint mPowersavePaint;
    private boolean mShowPercent;
    private float mSubpixelSmoothingLeft;
    private float mSubpixelSmoothingRight;
    private float mTextHeight;
    protected final Paint mTextPaint;
    private String mWarningString;
    private float mWarningTextHeight;
    protected final Paint mWarningTextPaint;
    private int mWidth;
    private int mLevel = -1;
    protected boolean mPowerSaveAsColorError = true;
    private int mIconTint = -1;
    private float mOldDarkIntensity = -1.0f;
    private final Path mBoltPath = new Path();
    private final Path mPlusPath = new Path();
    private final Rect mPadding = new Rect();
    private final RectF mFrame = new RectF();
    private final RectF mButtonFrame = new RectF();
    private final RectF mBoltFrame = new RectF();
    private final RectF mPlusFrame = new RectF();
    private final Path mShapePath = new Path();
    private final Path mOutlinePath = new Path();
    private final Path mTextPath = new Path();

    public BatteryMeterDrawableBase(Context context, int frameColor) {
        this.mContext = context;
        Resources res = context.getResources();
        TypedArray levels = res.obtainTypedArray(R.array.batterymeter_color_levels);
        TypedArray colors = res.obtainTypedArray(R.array.batterymeter_color_values);
        int N = levels.length();
        this.mColors = new int[2 * N];
        for (int i = 0; i < N; i++) {
            this.mColors[2 * i] = levels.getInt(i, 0);
            if (colors.getType(i) == 2) {
                this.mColors[(2 * i) + 1] = Utils.getColorAttr(context, colors.getThemeAttributeId(i, 0));
            } else {
                this.mColors[(2 * i) + 1] = colors.getColor(i, 0);
            }
        }
        levels.recycle();
        colors.recycle();
        this.mWarningString = context.getString(R.string.battery_meter_very_low_overlay_symbol);
        this.mCriticalLevel = this.mContext.getResources().getInteger(android.R.integer.config_autoBrightnessLightSensorRate);
        this.mButtonHeightFraction = context.getResources().getFraction(R.fraction.battery_button_height_fraction, 1, 1);
        this.mSubpixelSmoothingLeft = context.getResources().getFraction(R.fraction.battery_subpixel_smoothing_left, 1, 1);
        this.mSubpixelSmoothingRight = context.getResources().getFraction(R.fraction.battery_subpixel_smoothing_right, 1, 1);
        this.mFramePaint = new Paint(1);
        this.mFramePaint.setColor(frameColor);
        this.mFramePaint.setDither(true);
        this.mFramePaint.setStrokeWidth(0.0f);
        this.mFramePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mBatteryPaint = new Paint(1);
        this.mBatteryPaint.setDither(true);
        this.mBatteryPaint.setStrokeWidth(0.0f);
        this.mBatteryPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mTextPaint = new Paint(1);
        Typeface font = Typeface.create("sans-serif-condensed", 1);
        this.mTextPaint.setTypeface(font);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mWarningTextPaint = new Paint(1);
        Typeface font2 = Typeface.create("sans-serif", 1);
        this.mWarningTextPaint.setTypeface(font2);
        this.mWarningTextPaint.setTextAlign(Paint.Align.CENTER);
        if (this.mColors.length > 1) {
            this.mWarningTextPaint.setColor(this.mColors[1]);
        }
        this.mChargeColor = Utils.getDefaultColor(this.mContext, R.color.meter_consumed_color);
        this.mBoltPaint = new Paint(1);
        this.mBoltPaint.setColor(Utils.getDefaultColor(this.mContext, R.color.batterymeter_bolt_color));
        this.mBoltPoints = loadPoints(res, R.array.batterymeter_bolt_points);
        this.mPlusPaint = new Paint(1);
        this.mPlusPaint.setColor(Utils.getDefaultColor(this.mContext, R.color.batterymeter_plus_color));
        this.mPlusPoints = loadPoints(res, R.array.batterymeter_plus_points);
        this.mPowersavePaint = new Paint(1);
        this.mPowersavePaint.setColor(this.mPlusPaint.getColor());
        this.mPowersavePaint.setStyle(Paint.Style.STROKE);
        this.mPowersavePaint.setStrokeWidth(context.getResources().getDimensionPixelSize(R.dimen.battery_powersave_outline_thickness));
        this.mIntrinsicWidth = context.getResources().getDimensionPixelSize(R.dimen.battery_width);
        this.mIntrinsicHeight = context.getResources().getDimensionPixelSize(R.dimen.battery_height);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mIntrinsicHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mIntrinsicWidth;
    }

    public void setCharging(boolean val) {
        this.mCharging = val;
        postInvalidate();
    }

    public void setBatteryLevel(int val) {
        this.mLevel = val;
        postInvalidate();
    }

    public void setPowerSave(boolean val) {
        this.mPowerSaveEnabled = val;
        postInvalidate();
    }

    protected void postInvalidate() {
        unscheduleSelf(new Runnable() { // from class: com.android.settingslib.graph.-$$Lambda$BatteryMeterDrawableBase$ExJ0HHRzS2_LMtcBJqtFiovbn0w
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.invalidateSelf();
            }
        });
        scheduleSelf(new Runnable() { // from class: com.android.settingslib.graph.-$$Lambda$BatteryMeterDrawableBase$ExJ0HHRzS2_LMtcBJqtFiovbn0w
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.invalidateSelf();
            }
        }, 0L);
    }

    private static float[] loadPoints(Resources res, int pointArrayRes) {
        int[] pts = res.getIntArray(pointArrayRes);
        int maxY = 0;
        int maxX = 0;
        for (int maxX2 = 0; maxX2 < pts.length; maxX2 += 2) {
            maxX = Math.max(maxX, pts[maxX2]);
            maxY = Math.max(maxY, pts[maxX2 + 1]);
        }
        int i = pts.length;
        float[] ptsF = new float[i];
        for (int i2 = 0; i2 < pts.length; i2 += 2) {
            ptsF[i2] = pts[i2] / maxX;
            ptsF[i2 + 1] = pts[i2 + 1] / maxY;
        }
        return ptsF;
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        updateSize();
    }

    private void updateSize() {
        Rect bounds = getBounds();
        this.mHeight = (bounds.bottom - this.mPadding.bottom) - (bounds.top + this.mPadding.top);
        this.mWidth = (bounds.right - this.mPadding.right) - (bounds.left + this.mPadding.left);
        this.mWarningTextPaint.setTextSize(this.mHeight * 0.75f);
        this.mWarningTextHeight = -this.mWarningTextPaint.getFontMetrics().ascent;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect padding) {
        if (this.mPadding.left == 0 && this.mPadding.top == 0 && this.mPadding.right == 0 && this.mPadding.bottom == 0) {
            return super.getPadding(padding);
        }
        padding.set(this.mPadding);
        return true;
    }

    public void setPadding(int left, int top, int right, int bottom) {
        this.mPadding.left = left;
        this.mPadding.top = top;
        this.mPadding.right = right;
        this.mPadding.bottom = bottom;
        updateSize();
    }

    private int getColorForLevel(int percent) {
        int color = 0;
        for (int i = 0; i < this.mColors.length; i += 2) {
            int thresh = this.mColors[i];
            color = this.mColors[i + 1];
            if (percent <= thresh) {
                if (i == this.mColors.length - 2) {
                    return this.mIconTint;
                }
                return color;
            }
        }
        return color;
    }

    public void setColors(int fillColor, int backgroundColor) {
        this.mIconTint = fillColor;
        this.mFramePaint.setColor(backgroundColor);
        this.mBoltPaint.setColor(fillColor);
        this.mChargeColor = fillColor;
        invalidateSelf();
    }

    protected int batteryColorForLevel(int level) {
        if (this.mCharging || (this.mPowerSaveEnabled && this.mPowerSaveAsColorError)) {
            return this.mChargeColor;
        }
        return getColorForLevel(level);
    }

    /* JADX WARN: Code duplicated, block: B:68:0x0387  */
    /* JADX WARN: Code duplicated, block: B:69:0x038b  */
    /* JADX WARN: Code duplicated, block: B:73:0x03bb  */
    /* JADX WARN: Code duplicated, block: B:76:0x03c1  */
    /* JADX WARN: Code duplicated, block: B:83:0x040f  */
    /* JADX WARN: Code duplicated, block: B:84:0x042b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:85:0x042d  */
    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas c) {
        boolean z;
        boolean pctOpaque;
        float pctX;
        float pctY;
        String pctText;
        float f;
        int level = this.mLevel;
        Rect bounds = getBounds();
        if (level == -1) {
            return;
        }
        float drawFrac = level / 100.0f;
        int height = this.mHeight;
        int width = (int) (getAspectRatio() * this.mHeight);
        int px = (this.mWidth - width) / 2;
        int buttonHeight = Math.round(height * this.mButtonHeightFraction);
        int left = this.mPadding.left + bounds.left;
        int top = (bounds.bottom - this.mPadding.bottom) - height;
        this.mFrame.set(left, top, width + left, height + top);
        this.mFrame.offset(px, 0.0f);
        this.mButtonFrame.set(this.mFrame.left + Math.round(width * 0.28f), this.mFrame.top, this.mFrame.right - Math.round(width * 0.28f), this.mFrame.top + buttonHeight);
        this.mFrame.top += buttonHeight;
        this.mBatteryPaint.setColor(batteryColorForLevel(level));
        if (level >= 96) {
            drawFrac = 1.0f;
        } else if (level <= this.mCriticalLevel) {
            drawFrac = 0.0f;
        }
        float levelTop = drawFrac == 1.0f ? this.mButtonFrame.top : this.mFrame.top + (this.mFrame.height() * (1.0f - drawFrac));
        this.mShapePath.reset();
        this.mOutlinePath.reset();
        float radius = getRadiusRatio() * (this.mFrame.height() + buttonHeight);
        this.mShapePath.setFillType(Path.FillType.WINDING);
        this.mShapePath.addRoundRect(this.mFrame, radius, radius, Path.Direction.CW);
        this.mShapePath.addRect(this.mButtonFrame, Path.Direction.CW);
        this.mOutlinePath.addRoundRect(this.mFrame, radius, radius, Path.Direction.CW);
        Path p = new Path();
        p.addRect(this.mButtonFrame, Path.Direction.CW);
        this.mOutlinePath.op(p, Path.Op.XOR);
        if (this.mCharging) {
            float bl = this.mFrame.left + (this.mFrame.width() / 4.0f) + 1.0f;
            float bt = this.mFrame.top + (this.mFrame.height() / 6.0f);
            float f2 = this.mFrame.right;
            float drawFrac2 = this.mFrame.width();
            float br = (f2 - (drawFrac2 / 4.0f)) + 1.0f;
            float bb = this.mFrame.bottom - (this.mFrame.height() / 10.0f);
            if (this.mBoltFrame.left != bl || this.mBoltFrame.top != bt || this.mBoltFrame.right != br || this.mBoltFrame.bottom != bb) {
                this.mBoltFrame.set(bl, bt, br, bb);
                this.mBoltPath.reset();
                this.mBoltPath.moveTo(this.mBoltFrame.left + (this.mBoltPoints[0] * this.mBoltFrame.width()), this.mBoltFrame.top + (this.mBoltPoints[1] * this.mBoltFrame.height()));
                int i = 2;
                while (true) {
                    int i2 = i;
                    if (i2 >= this.mBoltPoints.length) {
                        break;
                    }
                    this.mBoltPath.lineTo(this.mBoltFrame.left + (this.mBoltPoints[i2] * this.mBoltFrame.width()), this.mBoltFrame.top + (this.mBoltPoints[i2 + 1] * this.mBoltFrame.height()));
                    i = i2 + 2;
                    radius = radius;
                }
                this.mBoltPath.lineTo(this.mBoltFrame.left + (this.mBoltPoints[0] * this.mBoltFrame.width()), this.mBoltFrame.top + (this.mBoltPoints[1] * this.mBoltFrame.height()));
            }
            float boltPct = (this.mBoltFrame.bottom - levelTop) / (this.mBoltFrame.bottom - this.mBoltFrame.top);
            if (Math.min(Math.max(boltPct, 0.0f), 1.0f) <= 0.3f) {
                c.drawPath(this.mBoltPath, this.mBoltPaint);
            } else {
                this.mShapePath.op(this.mBoltPath, Path.Op.DIFFERENCE);
            }
        } else {
            if (this.mPowerSaveEnabled) {
                float pw = (this.mFrame.width() * 2.0f) / 3.0f;
                float pl = this.mFrame.left + ((this.mFrame.width() - pw) / 2.0f);
                float pt = this.mFrame.top + ((this.mFrame.height() - pw) / 2.0f);
                float pr = this.mFrame.right - ((this.mFrame.width() - pw) / 2.0f);
                float pb = this.mFrame.bottom - ((this.mFrame.height() - pw) / 2.0f);
                if (this.mPlusFrame.left == pl && this.mPlusFrame.top == pt && this.mPlusFrame.right == pr && this.mPlusFrame.bottom == pb) {
                    z = false;
                } else {
                    this.mPlusFrame.set(pl, pt, pr, pb);
                    this.mPlusPath.reset();
                    this.mPlusPath.moveTo(this.mPlusFrame.left + (this.mPlusPoints[0] * this.mPlusFrame.width()), this.mPlusFrame.top + (this.mPlusPoints[1] * this.mPlusFrame.height()));
                    int i3 = 2;
                    while (true) {
                        int i4 = i3;
                        if (i4 >= this.mPlusPoints.length) {
                            break;
                        }
                        this.mPlusPath.lineTo(this.mPlusFrame.left + (this.mPlusPoints[i4] * this.mPlusFrame.width()), this.mPlusFrame.top + (this.mPlusPoints[i4 + 1] * this.mPlusFrame.height()));
                        i3 = i4 + 2;
                        pt = pt;
                    }
                    z = false;
                    this.mPlusPath.lineTo(this.mPlusFrame.left + (this.mPlusPoints[0] * this.mPlusFrame.width()), this.mPlusFrame.top + (this.mPlusPoints[1] * this.mPlusFrame.height()));
                }
                this.mShapePath.op(this.mPlusPath, Path.Op.DIFFERENCE);
                if (this.mPowerSaveAsColorError) {
                    c.drawPath(this.mPlusPath, this.mPlusPaint);
                }
            }
            pctOpaque = false;
            pctX = 0.0f;
            pctY = 0.0f;
            pctText = null;
            if (!this.mCharging && !this.mPowerSaveEnabled && level > this.mCriticalLevel && this.mShowPercent) {
                this.mTextPaint.setColor(getColorForLevel(level));
                Paint paint = this.mTextPaint;
                float f3 = height;
                if (this.mLevel == 100) {
                    f = 0.38f;
                } else {
                    f = 0.5f;
                }
                paint.setTextSize(f3 * f);
                this.mTextHeight = -this.mTextPaint.getFontMetrics().ascent;
                pctText = String.valueOf(level);
                pctX = (this.mWidth * 0.5f) + left;
                pctY = ((this.mHeight + this.mTextHeight) * 0.47f) + top;
                pctOpaque = levelTop <= pctY ? z : true;
                if (!pctOpaque) {
                    this.mTextPath.reset();
                    this.mTextPaint.getTextPath(pctText, 0, pctText.length(), pctX, pctY, this.mTextPath);
                    this.mShapePath.op(this.mTextPath, Path.Op.DIFFERENCE);
                }
            }
            c.drawPath(this.mShapePath, this.mFramePaint);
            this.mFrame.top = levelTop;
            c.save();
            c.clipRect(this.mFrame);
            c.drawPath(this.mShapePath, this.mBatteryPaint);
            c.restore();
            if (!this.mCharging && !this.mPowerSaveEnabled) {
                if (level <= this.mCriticalLevel) {
                    float x = (this.mWidth * 0.5f) + left;
                    float y = ((this.mHeight + this.mWarningTextHeight) * 0.48f) + top;
                    c.drawText(this.mWarningString, x, y, this.mWarningTextPaint);
                } else if (pctOpaque) {
                    c.drawText(pctText, pctX, pctY, this.mTextPaint);
                }
            }
            if (this.mCharging && this.mPowerSaveEnabled && this.mPowerSaveAsColorError) {
                c.drawPath(this.mOutlinePath, this.mPowersavePaint);
                return;
            }
            return;
        }
        z = false;
        pctOpaque = false;
        pctX = 0.0f;
        pctY = 0.0f;
        pctText = null;
        if (!this.mCharging) {
            this.mTextPaint.setColor(getColorForLevel(level));
            Paint paint2 = this.mTextPaint;
            float f4 = height;
            if (this.mLevel == 100) {
                f = 0.38f;
            } else {
                f = 0.5f;
            }
            paint2.setTextSize(f4 * f);
            this.mTextHeight = -this.mTextPaint.getFontMetrics().ascent;
            pctText = String.valueOf(level);
            pctX = (this.mWidth * 0.5f) + left;
            pctY = ((this.mHeight + this.mTextHeight) * 0.47f) + top;
            pctOpaque = levelTop <= pctY ? z : true;
            if (!pctOpaque) {
                this.mTextPath.reset();
                this.mTextPaint.getTextPath(pctText, 0, pctText.length(), pctX, pctY, this.mTextPath);
                this.mShapePath.op(this.mTextPath, Path.Op.DIFFERENCE);
            }
        }
        c.drawPath(this.mShapePath, this.mFramePaint);
        this.mFrame.top = levelTop;
        c.save();
        c.clipRect(this.mFrame);
        c.drawPath(this.mShapePath, this.mBatteryPaint);
        c.restore();
        if (!this.mCharging) {
            if (level <= this.mCriticalLevel) {
                float x2 = (this.mWidth * 0.5f) + left;
                float y2 = ((this.mHeight + this.mWarningTextHeight) * 0.48f) + top;
                c.drawText(this.mWarningString, x2, y2, this.mWarningTextPaint);
            } else if (pctOpaque) {
                c.drawText(pctText, pctX, pctY, this.mTextPaint);
            }
        }
        if (this.mCharging) {
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mFramePaint.setColorFilter(colorFilter);
        this.mBatteryPaint.setColorFilter(colorFilter);
        this.mWarningTextPaint.setColorFilter(colorFilter);
        this.mBoltPaint.setColorFilter(colorFilter);
        this.mPlusPaint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    protected float getAspectRatio() {
        return 0.58f;
    }

    protected float getRadiusRatio() {
        return 0.05882353f;
    }
}
