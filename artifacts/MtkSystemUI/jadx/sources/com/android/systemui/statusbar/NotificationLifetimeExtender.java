package com.android.systemui.statusbar;

/* JADX INFO: loaded from: classes.dex */
public interface NotificationLifetimeExtender {

    public interface NotificationSafeToRemoveCallback {
        void onSafeToRemove(String str);
    }

    void setCallback(NotificationSafeToRemoveCallback notificationSafeToRemoveCallback);

    void setShouldManageLifetime(NotificationData.Entry entry, boolean z);

    boolean shouldExtendLifetime(NotificationData.Entry entry);

    default boolean shouldExtendLifetimeForPendingNotification(NotificationData.Entry pendingEntry) {
        return false;
    }
}
