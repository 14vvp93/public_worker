package com.android.systemui.statusbar.notification;

import android.R;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.NotificationHeaderView;
import android.view.View;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.TransformableView;

/* JADX INFO: loaded from: classes.dex */
public abstract class NotificationViewWrapper implements TransformableView {
    private int mBackgroundColor = 0;
    protected final ExpandableNotificationRow mRow;
    protected final View mView;

    public static NotificationViewWrapper wrap(Context ctx, View v, ExpandableNotificationRow row) {
        if (v.getId() == 16909350) {
            if ("bigPicture".equals(v.getTag())) {
                return new NotificationBigPictureTemplateViewWrapper(ctx, v, row);
            }
            if ("bigText".equals(v.getTag())) {
                return new NotificationBigTextTemplateViewWrapper(ctx, v, row);
            }
            if ("media".equals(v.getTag()) || "bigMediaNarrow".equals(v.getTag())) {
                return new NotificationMediaTemplateViewWrapper(ctx, v, row);
            }
            if ("messaging".equals(v.getTag())) {
                return new NotificationMessagingTemplateViewWrapper(ctx, v, row);
            }
            return new NotificationTemplateViewWrapper(ctx, v, row);
        }
        if (v instanceof NotificationHeaderView) {
            return new NotificationHeaderViewWrapper(ctx, v, row);
        }
        return new NotificationCustomViewWrapper(ctx, v, row);
    }

    protected NotificationViewWrapper(Context ctx, View view, ExpandableNotificationRow row) {
        this.mView = view;
        this.mRow = row;
        onReinflated();
    }

    public void onContentUpdated(ExpandableNotificationRow row) {
    }

    public void onReinflated() {
        if (shouldClearBackgroundOnReapply()) {
            this.mBackgroundColor = 0;
        }
        Drawable background = this.mView.getBackground();
        if (background instanceof ColorDrawable) {
            this.mBackgroundColor = ((ColorDrawable) background).getColor();
            this.mView.setBackground(null);
        }
    }

    protected boolean shouldClearBackgroundOnReapply() {
        return true;
    }

    public void updateExpandability(boolean expandable, View.OnClickListener onClickListener) {
    }

    public NotificationHeaderView getNotificationHeader() {
        return null;
    }

    public int getHeaderTranslation() {
        return 0;
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public TransformState getCurrentState(int fadingView) {
        return null;
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public void transformTo(TransformableView notification, Runnable endRunnable) {
        CrossFadeHelper.fadeOut(this.mView, endRunnable);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public void transformTo(TransformableView notification, float transformationAmount) {
        CrossFadeHelper.fadeOut(this.mView, transformationAmount);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public void transformFrom(TransformableView notification) {
        CrossFadeHelper.fadeIn(this.mView);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public void transformFrom(TransformableView notification, float transformationAmount) {
        CrossFadeHelper.fadeIn(this.mView, transformationAmount);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public void setVisible(boolean visible) {
        this.mView.animate().cancel();
        this.mView.setVisibility(visible ? 0 : 4);
    }

    public int getCustomBackgroundColor() {
        if (this.mRow.isSummaryWithChildren()) {
            return 0;
        }
        return this.mBackgroundColor;
    }

    protected int resolveBackgroundColor() {
        int customBackgroundColor = getCustomBackgroundColor();
        if (customBackgroundColor != 0) {
            return customBackgroundColor;
        }
        return this.mView.getContext().getColor(R.color.accessibility_magnification_thumbnail_background_color);
    }

    public void setLegacy(boolean legacy) {
    }

    public void setContentHeight(int contentHeight, int minHeightHint) {
    }

    public void setRemoteInputVisible(boolean visible) {
    }

    public void setIsChildInGroup(boolean isChildInGroup) {
    }

    public boolean isDimmable() {
        return true;
    }

    public boolean disallowSingleClick(float x, float y) {
        return false;
    }

    public int getMinLayoutHeight() {
        return 0;
    }

    public boolean shouldClipToRounding(boolean topRounded, boolean bottomRounded) {
        return false;
    }

    public void setHeaderVisibleAmount(float headerVisibleAmount) {
    }

    public int getExtraMeasureHeight() {
        return 0;
    }
}
