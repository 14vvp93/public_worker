package com.android.systemui.statusbar;

import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.Dependency;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class SmartReplyController {
    private Set<String> mSendingKeys = new ArraySet();
    private IStatusBarService mBarService = (IStatusBarService) Dependency.get(IStatusBarService.class);

    public void smartReplySent(NotificationData.Entry entry, int replyIndex, CharSequence reply) {
        NotificationEntryManager notificationEntryManager = (NotificationEntryManager) Dependency.get(NotificationEntryManager.class);
        StatusBarNotification newSbn = notificationEntryManager.rebuildNotificationWithRemoteInput(entry, reply, true);
        notificationEntryManager.updateNotification(newSbn, null);
        this.mSendingKeys.add(entry.key);
        try {
            this.mBarService.onNotificationSmartReplySent(entry.notification.getKey(), replyIndex);
        } catch (RemoteException e) {
        }
    }

    public boolean isSendingSmartReply(String key) {
        return this.mSendingKeys.contains(key);
    }

    public void smartRepliesAdded(NotificationData.Entry entry, int replyCount) {
        try {
            this.mBarService.onNotificationSmartRepliesAdded(entry.notification.getKey(), replyCount);
        } catch (RemoteException e) {
        }
    }

    public void stopSending(NotificationData.Entry entry) {
        if (entry != null) {
            this.mSendingKeys.remove(entry.notification.getKey());
        }
    }
}
