package com.hcn.auto.systemui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import com.android.launcher3.icons.GraphicsUtils;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class IconNormalizer {
    private float mAdaptiveIconScale;
    private final Bitmap mBitmap;
    private final Canvas mCanvas;
    private boolean mEnableShapeDetection;
    private final float[] mLeftBorder;
    private final Matrix mMatrix;
    private final int mMaxSize;
    private final Paint mPaintMaskShapeOutline;
    private final byte[] mPixels;
    private final float[] mRightBorder;
    private final Path mShapePath;
    private final Rect mBounds = new Rect();
    private final RectF mAdaptiveIconBounds = new RectF();
    private final Paint mPaintMaskShape = new Paint();

    IconNormalizer(Context context, int iconBitmapSize, boolean shapeDetection) {
        this.mMaxSize = iconBitmapSize * 2;
        this.mBitmap = Bitmap.createBitmap(this.mMaxSize, this.mMaxSize, Bitmap.Config.ALPHA_8);
        this.mCanvas = new Canvas(this.mBitmap);
        this.mPixels = new byte[this.mMaxSize * this.mMaxSize];
        this.mLeftBorder = new float[this.mMaxSize];
        this.mRightBorder = new float[this.mMaxSize];
        this.mPaintMaskShape.setColor(-65536);
        this.mPaintMaskShape.setStyle(Paint.Style.FILL);
        this.mPaintMaskShape.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        this.mPaintMaskShapeOutline = new Paint();
        this.mPaintMaskShapeOutline.setStrokeWidth(2.0f * context.getResources().getDisplayMetrics().density);
        this.mPaintMaskShapeOutline.setStyle(Paint.Style.STROKE);
        this.mPaintMaskShapeOutline.setColor(-16777216);
        this.mPaintMaskShapeOutline.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.mShapePath = new Path();
        this.mMatrix = new Matrix();
        this.mAdaptiveIconScale = 0.0f;
        this.mEnableShapeDetection = shapeDetection;
    }

    private static float getScale(float hullArea, float boundingArea, float fullArea) {
        float scaleRequired;
        float hullByRect = hullArea / boundingArea;
        if (hullByRect < 0.7853982f) {
            scaleRequired = 0.6597222f;
        } else {
            scaleRequired = 0.6510417f + (0.040449437f * (1.0f - hullByRect));
        }
        float areaScale = hullArea / fullArea;
        if (areaScale > scaleRequired) {
            return (float) Math.sqrt(scaleRequired / areaScale);
        }
        return 1.0f;
    }

    @TargetApi(26)
    public static float normalizeAdaptiveIcon(Drawable d, int size, RectF outBounds) {
        Rect tmpBounds = new Rect(d.getBounds());
        d.setBounds(0, 0, size, size);
        Path path = ((AdaptiveIconDrawable) d).getIconMask();
        Region region = new Region();
        region.setPath(path, new Region(0, 0, size, size));
        Rect hullBounds = region.getBounds();
        int hullArea = GraphicsUtils.getArea(region);
        if (outBounds != null) {
            float sizeF = size;
            outBounds.set(hullBounds.left / sizeF, hullBounds.top / sizeF, 1.0f - (hullBounds.right / sizeF), 1.0f - (hullBounds.bottom / sizeF));
        }
        d.setBounds(tmpBounds);
        return getScale(hullArea, hullArea, size * size);
    }

    private boolean isShape(Path maskPath) {
        float iconRatio = this.mBounds.width() / this.mBounds.height();
        if (Math.abs(iconRatio - 1.0f) > 0.05f) {
            return false;
        }
        this.mMatrix.reset();
        this.mMatrix.setScale(this.mBounds.width(), this.mBounds.height());
        this.mMatrix.postTranslate(this.mBounds.left, this.mBounds.top);
        maskPath.transform(this.mMatrix, this.mShapePath);
        this.mCanvas.drawPath(this.mShapePath, this.mPaintMaskShape);
        this.mCanvas.drawPath(this.mShapePath, this.mPaintMaskShapeOutline);
        return isTransparentBitmap();
    }

    private boolean isTransparentBitmap() {
        ByteBuffer buffer = ByteBuffer.wrap(this.mPixels);
        buffer.rewind();
        this.mBitmap.copyPixelsToBuffer(buffer);
        int y = this.mBounds.top;
        int index = this.mMaxSize * y;
        int rowSizeDiff = this.mMaxSize - this.mBounds.right;
        int index2 = index;
        int sum = 0;
        for (int index3 = y; index3 < this.mBounds.bottom; index3++) {
            int index4 = index2 + this.mBounds.left;
            for (int x = this.mBounds.left; x < this.mBounds.right; x++) {
                if ((this.mPixels[index4] & 255) > 40) {
                    sum++;
                }
                index4++;
            }
            index2 = index4 + rowSizeDiff;
        }
        float percentageDiffPixels = sum / (this.mBounds.width() * this.mBounds.height());
        return percentageDiffPixels < 0.005f;
    }

    public synchronized float getScale(Drawable d, RectF outBounds, Path path, boolean[] outMaskShape) {
        if (BaseIconFactory.ATLEAST_OREO && (d instanceof AdaptiveIconDrawable)) {
            if (this.mAdaptiveIconScale == 0.0f) {
                this.mAdaptiveIconScale = normalizeAdaptiveIcon(d, this.mMaxSize, this.mAdaptiveIconBounds);
            }
            if (outBounds != null) {
                outBounds.set(this.mAdaptiveIconBounds);
            }
            return this.mAdaptiveIconScale;
        }
        int width = d.getIntrinsicWidth();
        int height = d.getIntrinsicHeight();
        if (width > 0 && height > 0) {
            if (width > this.mMaxSize || height > this.mMaxSize) {
                int max = Math.max(width, height);
                width = (this.mMaxSize * width) / max;
                height = (this.mMaxSize * height) / max;
            }
        } else {
            width = (width <= 0 || width > this.mMaxSize) ? this.mMaxSize : width;
            height = (height <= 0 || height > this.mMaxSize) ? this.mMaxSize : height;
        }
        this.mBitmap.eraseColor(0);
        d.setBounds(0, 0, width, height);
        d.draw(this.mCanvas);
        ByteBuffer buffer = ByteBuffer.wrap(this.mPixels);
        buffer.rewind();
        this.mBitmap.copyPixelsToBuffer(buffer);
        int leftX = this.mMaxSize + 1;
        int rightX = -1;
        int rowSizeDiff = this.mMaxSize - width;
        int index = 0;
        int leftX2 = leftX;
        int bottomY = -1;
        int topY = -1;
        int topY2 = 0;
        while (topY2 < height) {
            int z = 0;
            int lastX = -1;
            int firstX = -1;
            while (z < width) {
                if ((this.mPixels[index] & 255) > 40) {
                    if (firstX == -1) {
                        firstX = z;
                    }
                    int lastX2 = z;
                    lastX = lastX2;
                }
                index++;
                z++;
            }
            index += rowSizeDiff;
            this.mLeftBorder[z] = firstX;
            ByteBuffer buffer2 = buffer;
            int lastX3 = lastX;
            this.mRightBorder[z] = lastX3;
            if (firstX != -1) {
                int bottomY2 = z;
                if (topY == -1) {
                    topY = z;
                }
                leftX2 = Math.min(leftX2, firstX);
                bottomY = bottomY2;
                rightX = Math.max(rightX, lastX3);
            }
            topY2 = z + 1;
            buffer = buffer2;
        }
        if (topY == -1 || rightX == -1) {
            return 1.0f;
        }
        convertToConvexArray(this.mLeftBorder, 1, topY, bottomY);
        convertToConvexArray(this.mRightBorder, -1, topY, bottomY);
        float area = 0.0f;
        for (int y = 0; y < height; y++) {
            if (this.mLeftBorder[y] > -1.0f) {
                area += (this.mRightBorder[y] - this.mLeftBorder[y]) + 1.0f;
            }
        }
        this.mBounds.left = leftX2;
        this.mBounds.right = rightX;
        this.mBounds.top = topY;
        this.mBounds.bottom = bottomY;
        if (outBounds != null) {
            outBounds.set(this.mBounds.left / width, this.mBounds.top / height, 1.0f - (this.mBounds.right / width), 1.0f - (this.mBounds.bottom / height));
        }
        if (outMaskShape != null && this.mEnableShapeDetection && outMaskShape.length > 0) {
            outMaskShape[0] = isShape(path);
        }
        float rectArea = ((bottomY + 1) - topY) * ((rightX + 1) - leftX2);
        return getScale(area, rectArea, width * height);
    }

    private static void convertToConvexArray(float[] xCoordinates, int direction, int topY, int bottomY) {
        int start;
        int total = xCoordinates.length;
        float[] angles = new float[total - 1];
        int last = -1;
        float lastAngle = Float.MAX_VALUE;
        for (int i = topY + 1; i <= bottomY; i++) {
            if (xCoordinates[i] > -1.0f) {
                if (lastAngle == Float.MAX_VALUE) {
                    start = topY;
                } else {
                    float currentAngle = (xCoordinates[i] - xCoordinates[last]) / (i - last);
                    int start2 = last;
                    if ((currentAngle - lastAngle) * direction < 0.0f) {
                        start = start2;
                        while (start > topY) {
                            start--;
                            float currentAngle2 = (xCoordinates[i] - xCoordinates[start]) / (i - start);
                            if ((currentAngle2 - angles[start]) * direction >= 0.0f) {
                                break;
                            }
                        }
                    } else {
                        start = start2;
                    }
                }
                float lastAngle2 = (xCoordinates[i] - xCoordinates[start]) / (i - start);
                for (int j = start; j < i; j++) {
                    angles[j] = lastAngle2;
                    xCoordinates[j] = xCoordinates[start] + ((j - start) * lastAngle2);
                }
                last = i;
                lastAngle = lastAngle2;
            }
        }
    }
}
