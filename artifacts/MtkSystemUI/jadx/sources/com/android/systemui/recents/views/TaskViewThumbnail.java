package com.android.systemui.recents.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewDebug;
import com.android.systemui.R;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.events.ui.TaskSnapshotChangedEvent;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.shared.recents.utilities.Utilities;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class TaskViewThumbnail extends View {
    protected Paint mBgFillPaint;
    protected BitmapShader mBitmapShader;
    protected int mCornerRadius;

    @ViewDebug.ExportedProperty(category = "recents")
    private float mDimAlpha;

    @ViewDebug.ExportedProperty(category = "recents")
    private boolean mDisabledInSafeMode;
    private int mDisplayOrientation;
    private Rect mDisplayRect;
    private Paint mDrawPaint;
    private float mFullscreenThumbnailScale;

    @ViewDebug.ExportedProperty(category = "recents")
    private boolean mInvisible;
    private LightingColorFilter mLightingColorFilter;
    protected Paint mLockedPaint;
    private Matrix mMatrix;
    private boolean mOverlayHeaderOnThumbnailActionBar;
    private boolean mSizeToFit;
    private Task mTask;
    private View mTaskBar;

    @ViewDebug.ExportedProperty(category = "recents")
    protected Rect mTaskViewRect;
    private ThumbnailData mThumbnailData;

    @ViewDebug.ExportedProperty(category = "recents")
    protected Rect mThumbnailRect;

    @ViewDebug.ExportedProperty(category = "recents")
    protected float mThumbnailScale;
    private int mTitleBarHeight;
    protected boolean mUserLocked;
    private static final ColorMatrix TMP_FILTER_COLOR_MATRIX = new ColorMatrix();
    private static final ColorMatrix TMP_BRIGHTNESS_COLOR_MATRIX = new ColorMatrix();

    public TaskViewThumbnail(Context context) {
        this(context, null);
    }

    public TaskViewThumbnail(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaskViewThumbnail(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TaskViewThumbnail(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mDisplayOrientation = 0;
        this.mDisplayRect = new Rect();
        this.mTaskViewRect = new Rect();
        this.mThumbnailRect = new Rect();
        this.mFullscreenThumbnailScale = 1.0f;
        this.mSizeToFit = true;
        this.mOverlayHeaderOnThumbnailActionBar = true;
        this.mMatrix = new Matrix();
        this.mDrawPaint = new Paint();
        this.mLockedPaint = new Paint();
        this.mBgFillPaint = new Paint();
        this.mUserLocked = false;
        this.mLightingColorFilter = new LightingColorFilter(-1, 0);
        this.mDrawPaint.setColorFilter(this.mLightingColorFilter);
        this.mDrawPaint.setFilterBitmap(true);
        this.mDrawPaint.setAntiAlias(true);
        Resources res = getResources();
        this.mCornerRadius = res.getDimensionPixelSize(R.dimen.recents_task_view_rounded_corners_radius);
        this.mBgFillPaint.setColor(-1);
        this.mLockedPaint.setColor(-1);
        this.mTitleBarHeight = res.getDimensionPixelSize(R.dimen.recents_grid_task_view_header_height);
    }

    public void onTaskViewSizeChanged(int width, int height) {
        if (this.mTaskViewRect.width() == width && this.mTaskViewRect.height() == height) {
            return;
        }
        this.mTaskViewRect.set(0, 0, width, height);
        setLeftTopRightBottom(0, 0, width, height);
        updateThumbnailMatrix();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mInvisible) {
            return;
        }
        int viewWidth = this.mTaskViewRect.width();
        int viewHeight = this.mTaskViewRect.height();
        int thumbnailWidth = Math.min(viewWidth, (int) (this.mThumbnailRect.width() * this.mThumbnailScale));
        int thumbnailHeight = Math.min(viewHeight, (int) (this.mThumbnailRect.height() * this.mThumbnailScale));
        if (this.mUserLocked) {
            canvas.drawRoundRect(0.0f, 0.0f, viewWidth, viewHeight, this.mCornerRadius, this.mCornerRadius, this.mLockedPaint);
            return;
        }
        if (this.mBitmapShader != null && thumbnailWidth > 0 && thumbnailHeight > 0) {
            int topOffset = 0;
            if (this.mTaskBar != null && this.mOverlayHeaderOnThumbnailActionBar) {
                topOffset = this.mTaskBar.getHeight() - this.mCornerRadius;
            }
            if (thumbnailWidth < viewWidth) {
                canvas.drawRoundRect(Math.max(0, thumbnailWidth - this.mCornerRadius), topOffset, viewWidth, viewHeight, this.mCornerRadius, this.mCornerRadius, this.mBgFillPaint);
            }
            if (thumbnailHeight < viewHeight) {
                canvas.drawRoundRect(0.0f, Math.max(topOffset, thumbnailHeight - this.mCornerRadius), viewWidth, viewHeight, this.mCornerRadius, this.mCornerRadius, this.mBgFillPaint);
            }
            canvas.drawRoundRect(0.0f, topOffset, thumbnailWidth, thumbnailHeight, this.mCornerRadius, this.mCornerRadius, this.mDrawPaint);
            return;
        }
        canvas.drawRoundRect(0.0f, 0.0f, viewWidth, viewHeight, this.mCornerRadius, this.mCornerRadius, this.mBgFillPaint);
    }

    void setThumbnail(ThumbnailData thumbnailData) {
        if (thumbnailData != null && thumbnailData.thumbnail != null) {
            Bitmap bm = thumbnailData.thumbnail;
            bm.prepareToDraw();
            this.mFullscreenThumbnailScale = thumbnailData.scale;
            this.mBitmapShader = new BitmapShader(bm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.mDrawPaint.setShader(this.mBitmapShader);
            this.mThumbnailRect.set(0, 0, (bm.getWidth() - thumbnailData.insets.left) - thumbnailData.insets.right, (bm.getHeight() - thumbnailData.insets.top) - thumbnailData.insets.bottom);
            this.mThumbnailData = thumbnailData;
            updateThumbnailMatrix();
            updateThumbnailPaintFilter();
            return;
        }
        this.mBitmapShader = null;
        this.mDrawPaint.setShader(null);
        this.mThumbnailRect.setEmpty();
        this.mThumbnailData = null;
    }

    void updateThumbnailPaintFilter() {
        if (this.mInvisible) {
            return;
        }
        int mul = (int) ((1.0f - this.mDimAlpha) * 255.0f);
        if (this.mBitmapShader != null) {
            if (this.mDisabledInSafeMode) {
                TMP_FILTER_COLOR_MATRIX.setSaturation(0.0f);
                float scale = 1.0f - this.mDimAlpha;
                float[] mat = TMP_BRIGHTNESS_COLOR_MATRIX.getArray();
                mat[0] = scale;
                mat[6] = scale;
                mat[12] = scale;
                mat[4] = this.mDimAlpha * 255.0f;
                mat[9] = this.mDimAlpha * 255.0f;
                mat[14] = this.mDimAlpha * 255.0f;
                TMP_FILTER_COLOR_MATRIX.preConcat(TMP_BRIGHTNESS_COLOR_MATRIX);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(TMP_FILTER_COLOR_MATRIX);
                this.mDrawPaint.setColorFilter(filter);
                this.mBgFillPaint.setColorFilter(filter);
                this.mLockedPaint.setColorFilter(filter);
            } else {
                this.mLightingColorFilter.setColorMultiply(Color.argb(255, mul, mul, mul));
                this.mDrawPaint.setColorFilter(this.mLightingColorFilter);
                this.mDrawPaint.setColor(-1);
                this.mBgFillPaint.setColorFilter(this.mLightingColorFilter);
                this.mLockedPaint.setColorFilter(this.mLightingColorFilter);
            }
        } else {
            this.mDrawPaint.setColorFilter(null);
            this.mDrawPaint.setColor(Color.argb(255, mul, mul, mul));
        }
        if (!this.mInvisible) {
            invalidate();
        }
    }

    public void updateThumbnailMatrix() {
        this.mThumbnailScale = 1.0f;
        if (this.mBitmapShader != null && this.mThumbnailData != null) {
            if (this.mTaskViewRect.isEmpty()) {
                this.mThumbnailScale = 0.0f;
            } else if (!this.mSizeToFit) {
                float invThumbnailScale = 1.0f / this.mFullscreenThumbnailScale;
                if (this.mDisplayOrientation == 1) {
                    if (this.mThumbnailData.orientation == 1) {
                        this.mThumbnailScale = this.mTaskViewRect.width() / this.mThumbnailRect.width();
                    } else {
                        this.mThumbnailScale = (this.mTaskViewRect.width() / this.mDisplayRect.width()) * invThumbnailScale;
                    }
                } else {
                    this.mThumbnailScale = invThumbnailScale;
                }
            } else {
                float viewAspectRatio = this.mTaskViewRect.width() / (this.mTaskViewRect.height() - this.mTitleBarHeight);
                float thumbnailAspectRatio = this.mThumbnailRect.width() / this.mThumbnailRect.height();
                if (viewAspectRatio > thumbnailAspectRatio) {
                    this.mThumbnailScale = this.mTaskViewRect.width() / this.mThumbnailRect.width();
                } else {
                    this.mThumbnailScale = (this.mTaskViewRect.height() - this.mTitleBarHeight) / this.mThumbnailRect.height();
                }
            }
            this.mMatrix.setTranslate((-this.mThumbnailData.insets.left) * this.mFullscreenThumbnailScale, this.mThumbnailData.insets.top * this.mFullscreenThumbnailScale);
            this.mMatrix.postScale(this.mThumbnailScale, this.mThumbnailScale);
            this.mBitmapShader.setLocalMatrix(this.mMatrix);
        }
        if (!this.mInvisible) {
            invalidate();
        }
    }

    public void setSizeToFit(boolean flag) {
        this.mSizeToFit = flag;
    }

    public void setOverlayHeaderOnThumbnailActionBar(boolean flag) {
        this.mOverlayHeaderOnThumbnailActionBar = flag;
    }

    void updateClipToTaskBar(View taskBar) {
        this.mTaskBar = taskBar;
        invalidate();
    }

    void updateThumbnailVisibility(int clipBottom) {
        boolean invisible = this.mTaskBar != null && getHeight() - clipBottom <= this.mTaskBar.getHeight();
        if (invisible != this.mInvisible) {
            this.mInvisible = invisible;
            if (!this.mInvisible) {
                updateThumbnailPaintFilter();
            }
        }
    }

    public void setDimAlpha(float dimAlpha) {
        this.mDimAlpha = dimAlpha;
        updateThumbnailPaintFilter();
    }

    protected Paint getDrawPaint() {
        if (this.mUserLocked) {
            return this.mLockedPaint;
        }
        return this.mDrawPaint;
    }

    void bindToTask(Task t, boolean disabledInSafeMode, int displayOrientation, Rect displayRect) {
        this.mTask = t;
        this.mDisabledInSafeMode = disabledInSafeMode;
        this.mDisplayOrientation = displayOrientation;
        this.mDisplayRect.set(displayRect);
        if (t.colorBackground != 0) {
            this.mBgFillPaint.setColor(t.colorBackground);
        }
        if (t.colorPrimary != 0) {
            this.mLockedPaint.setColor(t.colorPrimary);
        }
        this.mUserLocked = t.isLocked;
        EventBus.getDefault().register(this);
    }

    void onTaskDataLoaded(ThumbnailData thumbnailData) {
        setThumbnail(thumbnailData);
    }

    void unbindFromTask() {
        this.mTask = null;
        setThumbnail(null);
        EventBus.getDefault().unregister(this);
    }

    public final void onBusEvent(TaskSnapshotChangedEvent event) {
        if (this.mTask == null || event.taskId != this.mTask.key.id || event.thumbnailData == null || event.thumbnailData.thumbnail == null) {
            return;
        }
        setThumbnail(event.thumbnailData);
    }

    public void dump(String prefix, PrintWriter writer) {
        writer.print(prefix);
        writer.print("TaskViewThumbnail");
        writer.print(" mTaskViewRect=");
        writer.print(Utilities.dumpRect(this.mTaskViewRect));
        writer.print(" mThumbnailRect=");
        writer.print(Utilities.dumpRect(this.mThumbnailRect));
        writer.print(" mThumbnailScale=");
        writer.print(this.mThumbnailScale);
        writer.print(" mDimAlpha=");
        writer.print(this.mDimAlpha);
        writer.println();
    }
}
