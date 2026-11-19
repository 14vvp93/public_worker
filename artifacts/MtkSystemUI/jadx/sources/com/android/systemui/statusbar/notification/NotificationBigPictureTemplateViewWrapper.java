package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.statusbar.ExpandableNotificationRow;

/* JADX INFO: loaded from: classes.dex */
public class NotificationBigPictureTemplateViewWrapper extends NotificationTemplateViewWrapper {
    protected NotificationBigPictureTemplateViewWrapper(Context ctx, View view, ExpandableNotificationRow row) {
        super(ctx, view, row);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.NotificationViewWrapper
    public void onContentUpdated(ExpandableNotificationRow row) {
        super.onContentUpdated(row);
        updateImageTag(row.getStatusBarNotification());
    }

    private void updateImageTag(StatusBarNotification notification) {
        Bundle extras = notification.getNotification().extras;
        Icon overRiddenIcon = (Icon) extras.getParcelable("android.largeIcon.big");
        if (overRiddenIcon != null) {
            this.mPicture.setTag(R.id.image_icon_tag, overRiddenIcon);
        }
    }
}
