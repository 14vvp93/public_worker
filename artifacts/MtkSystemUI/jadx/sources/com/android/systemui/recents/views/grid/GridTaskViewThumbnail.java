package com.android.systemui.recents.views.grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import com.android.systemui.R;
import com.android.systemui.recents.views.TaskViewThumbnail;

/* JADX INFO: loaded from: classes.dex */
public class GridTaskViewThumbnail extends TaskViewThumbnail {
    private final Path mRestBackgroundOutline;
    private final Path mThumbnailOutline;
    private boolean mUpdateThumbnailOutline;

    public GridTaskViewThumbnail(Context context) {
        this(context, null);
    }

    public GridTaskViewThumbnail(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridTaskViewThumbnail(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public GridTaskViewThumbnail(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mThumbnailOutline = new Path();
        this.mRestBackgroundOutline = new Path();
        this.mUpdateThumbnailOutline = true;
        this.mCornerRadius = getResources().getDimensionPixelSize(R.dimen.recents_grid_task_view_rounded_corners_radius);
    }

    @Override // com.android.systemui.recents.views.TaskViewThumbnail
    public void onTaskViewSizeChanged(int width, int height) {
        this.mUpdateThumbnailOutline = true;
        super.onTaskViewSizeChanged(width, height);
    }

    @Override // com.android.systemui.recents.views.TaskViewThumbnail
    public void updateThumbnailMatrix() {
        this.mUpdateThumbnailOutline = true;
        super.updateThumbnailMatrix();
    }

    private void updateThumbnailOutline() {
        int titleHeight = getResources().getDimensionPixelSize(R.dimen.recents_grid_task_view_header_height);
        int viewWidth = this.mTaskViewRect.width();
        int viewHeight = this.mTaskViewRect.height() - titleHeight;
        int thumbnailWidth = Math.min(viewWidth, (int) (this.mThumbnailRect.width() * this.mThumbnailScale));
        int thumbnailHeight = Math.min(viewHeight, (int) (this.mThumbnailRect.height() * this.mThumbnailScale));
        if (this.mBitmapShader == null || thumbnailWidth <= 0 || thumbnailHeight <= 0) {
            createThumbnailPath(0, 0, viewWidth, viewHeight, this.mThumbnailOutline);
            return;
        }
        int outerRight = 0 + thumbnailWidth;
        int outerBottom = 0 + thumbnailHeight;
        createThumbnailPath(0, 0, outerRight, outerBottom, this.mThumbnailOutline);
        if (thumbnailWidth < viewWidth) {
            int l = Math.max(0, outerRight - this.mCornerRadius);
            this.mRestBackgroundOutline.reset();
            this.mRestBackgroundOutline.moveTo(l, 0.0f);
            this.mRestBackgroundOutline.lineTo(outerRight, 0.0f);
            this.mRestBackgroundOutline.lineTo(outerRight, outerBottom - this.mCornerRadius);
            this.mRestBackgroundOutline.arcTo(outerRight - (this.mCornerRadius * 2), outerBottom - (this.mCornerRadius * 2), outerRight, outerBottom, 0.0f, 90.0f, false);
            this.mRestBackgroundOutline.lineTo(l, outerBottom);
            this.mRestBackgroundOutline.lineTo(l, 0.0f);
            this.mRestBackgroundOutline.close();
        }
        if (thumbnailHeight < viewHeight) {
            int t = Math.max(0, thumbnailHeight - this.mCornerRadius);
            this.mRestBackgroundOutline.reset();
            this.mRestBackgroundOutline.moveTo(0.0f, t);
            this.mRestBackgroundOutline.lineTo(outerRight, t);
            this.mRestBackgroundOutline.lineTo(outerRight, outerBottom - this.mCornerRadius);
            this.mRestBackgroundOutline.arcTo(outerRight - (this.mCornerRadius * 2), outerBottom - (this.mCornerRadius * 2), outerRight, outerBottom, 0.0f, 90.0f, false);
            this.mRestBackgroundOutline.lineTo(0 + this.mCornerRadius, outerBottom);
            this.mRestBackgroundOutline.arcTo(0.0f, outerBottom - (this.mCornerRadius * 2), 0 + (2 * this.mCornerRadius), outerBottom, 90.0f, 90.0f, false);
            this.mRestBackgroundOutline.lineTo(0.0f, t);
            this.mRestBackgroundOutline.close();
        }
    }

    private void createThumbnailPath(int outerLeft, int outerTop, int outerRight, int outerBottom, Path outPath) {
        outPath.reset();
        outPath.moveTo(outerLeft, outerTop);
        outPath.lineTo(outerRight, outerTop);
        outPath.lineTo(outerRight, outerBottom - this.mCornerRadius);
        outPath.arcTo(outerRight - (this.mCornerRadius * 2), outerBottom - (this.mCornerRadius * 2), outerRight, outerBottom, 0.0f, 90.0f, false);
        outPath.lineTo(this.mCornerRadius + outerLeft, outerBottom);
        outPath.arcTo(outerLeft, outerBottom - (this.mCornerRadius * 2), outerLeft + (2 * this.mCornerRadius), outerBottom, 90.0f, 90.0f, false);
        outPath.lineTo(outerLeft, outerTop);
        outPath.close();
    }

    @Override // com.android.systemui.recents.views.TaskViewThumbnail, android.view.View
    protected void onDraw(Canvas canvas) {
        int titleHeight = getResources().getDimensionPixelSize(R.dimen.recents_grid_task_view_header_height);
        int viewWidth = this.mTaskViewRect.width();
        int viewHeight = this.mTaskViewRect.height() - titleHeight;
        int thumbnailWidth = Math.min(viewWidth, (int) (this.mThumbnailRect.width() * this.mThumbnailScale));
        int thumbnailHeight = Math.min(viewHeight, (int) (this.mThumbnailRect.height() * this.mThumbnailScale));
        if (this.mUpdateThumbnailOutline) {
            updateThumbnailOutline();
            this.mUpdateThumbnailOutline = false;
        }
        if (this.mUserLocked) {
            canvas.drawPath(this.mThumbnailOutline, this.mLockedPaint);
            return;
        }
        if (this.mBitmapShader != null && thumbnailWidth > 0 && thumbnailHeight > 0) {
            if (thumbnailWidth < viewWidth) {
                canvas.drawPath(this.mRestBackgroundOutline, this.mBgFillPaint);
            }
            if (thumbnailHeight < viewHeight) {
                canvas.drawPath(this.mRestBackgroundOutline, this.mBgFillPaint);
            }
            canvas.drawPath(this.mThumbnailOutline, getDrawPaint());
            return;
        }
        canvas.drawPath(this.mThumbnailOutline, this.mBgFillPaint);
    }
}
