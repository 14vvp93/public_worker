package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.statusbar.ExpandableNotificationRow;

/* JADX INFO: loaded from: classes.dex */
public class NotificationCustomViewWrapper extends NotificationViewWrapper {
    private boolean mIsLegacy;
    private int mLegacyColor;

    protected NotificationCustomViewWrapper(Context ctx, View view, ExpandableNotificationRow row) {
        super(ctx, view, row);
        this.mLegacyColor = row.getContext().getColor(R.color.notification_legacy_background_color);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper, com.android.systemui.statusbar.TransformableView
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        this.mView.setAlpha(visible ? 1.0f : 0.0f);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    protected boolean shouldClearBackgroundOnReapply() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    public int getCustomBackgroundColor() {
        int customBackgroundColor = super.getCustomBackgroundColor();
        if (customBackgroundColor == 0 && this.mIsLegacy) {
            return this.mLegacyColor;
        }
        return customBackgroundColor;
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    public void setLegacy(boolean legacy) {
        super.setLegacy(legacy);
        this.mIsLegacy = legacy;
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    public boolean shouldClipToRounding(boolean topRounded, boolean bottomRounded) {
        return true;
    }
}
