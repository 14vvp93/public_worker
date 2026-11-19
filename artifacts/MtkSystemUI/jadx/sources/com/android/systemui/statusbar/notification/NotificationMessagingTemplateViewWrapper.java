package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.view.View;
import com.android.internal.widget.MessagingLayout;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.systemui.R;
import com.android.systemui.statusbar.ExpandableNotificationRow;

/* JADX INFO: loaded from: classes.dex */
public class NotificationMessagingTemplateViewWrapper extends NotificationTemplateViewWrapper {
    private MessagingLayout mMessagingLayout;
    private MessagingLinearLayout mMessagingLinearLayout;
    private final int mMinHeightWithActions;

    protected NotificationMessagingTemplateViewWrapper(Context ctx, View view, ExpandableNotificationRow row) {
        super(ctx, view, row);
        this.mMessagingLayout = (MessagingLayout) view;
        this.mMinHeightWithActions = NotificationUtils.getFontScaledHeight(ctx, R.dimen.notification_messaging_actions_min_height);
    }

    private void resolveViews() {
        this.mMessagingLinearLayout = this.mMessagingLayout.getMessagingLinearLayout();
    }

    @Override // com.android.systemui.statusbar.notification.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.NotificationViewWrapper
    public void onContentUpdated(ExpandableNotificationRow row) {
        resolveViews();
        super.onContentUpdated(row);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.NotificationHeaderViewWrapper
    protected void updateTransformedTypes() {
        super.updateTransformedTypes();
        if (this.mMessagingLinearLayout != null) {
            this.mTransformationHelper.addTransformedView(this.mMessagingLinearLayout.getId(), this.mMessagingLinearLayout);
        }
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    public void setRemoteInputVisible(boolean visible) {
        this.mMessagingLayout.showHistoricMessages(visible);
    }

    @Override // com.android.systemui.statusbar.notification.NotificationViewWrapper
    public int getMinLayoutHeight() {
        if (this.mActionsContainer != null && this.mActionsContainer.getVisibility() != 8) {
            return this.mMinHeightWithActions;
        }
        return super.getMinLayoutHeight();
    }
}
