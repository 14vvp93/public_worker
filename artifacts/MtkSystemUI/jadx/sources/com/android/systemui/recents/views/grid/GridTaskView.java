package com.android.systemui.recents.views.grid;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.R;
import com.android.systemui.recents.views.TaskView;
import com.android.systemui.shared.recents.view.AnimateableViewBounds;

/* JADX INFO: loaded from: classes.dex */
public class GridTaskView extends TaskView {
    private int mHeaderHeight;

    public GridTaskView(Context context) {
        this(context, null);
    }

    public GridTaskView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridTaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public GridTaskView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mHeaderHeight = context.getResources().getDimensionPixelSize(R.dimen.recents_grid_task_view_header_height);
    }

    @Override // com.android.systemui.recents.views.TaskView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mThumbnailView.setSizeToFit(true);
        this.mThumbnailView.setOverlayHeaderOnThumbnailActionBar(false);
        this.mThumbnailView.updateThumbnailMatrix();
        this.mThumbnailView.setTranslationY(this.mHeaderHeight);
        this.mHeaderView.setShouldDarkenBackgroundColor(true);
    }

    @Override // com.android.systemui.recents.views.TaskView
    protected AnimateableViewBounds createOutlineProvider() {
        return new AnimateableGridViewBounds(this, this.mContext.getResources().getDimensionPixelSize(R.dimen.recents_task_view_shadow_rounded_corners_radius));
    }

    @Override // com.android.systemui.recents.views.TaskView
    protected void onConfigurationChanged() {
        super.onConfigurationChanged();
        this.mHeaderHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.recents_grid_task_view_header_height);
        this.mThumbnailView.setTranslationY(this.mHeaderHeight);
    }
}
