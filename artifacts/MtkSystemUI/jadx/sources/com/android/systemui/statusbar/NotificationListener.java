package com.android.systemui.statusbar;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.util.NotificationChannels;
import com.auto.common.HConfig;

/* JADX INFO: loaded from: classes.dex */
public class NotificationListener extends NotificationListenerWithPlugins {
    private final Context mContext;
    protected NotificationEntryManager mEntryManager;
    protected NotificationPresenter mPresenter;
    private final NotificationRemoteInputManager mRemoteInputManager = (NotificationRemoteInputManager) Dependency.get(NotificationRemoteInputManager.class);

    public NotificationListener(Context context) {
        this.mContext = context;
    }

    private boolean shouldFilterOut(StatusBarNotification sbn) {
        if (sbn == null) {
            return false;
        }
        if (HConfig.isDisableNotificationShow()) {
            Notification notification = sbn.getNotification();
            if (notification != null) {
                if (NotificationChannels.STORAGE.equals(notification.getChannelId())) {
                    return false;
                }
            }
            return !"com.hcn.AutoMediaPlayer,com.hcn.autoradio,com.autochips.bluetooth".contains(sbn.getPackageName());
        }
        String packageName = sbn.getPackageName();
        if (!TextUtils.isEmpty(packageName) && packageName.contains("zjinnova.zlink")) {
            return true;
        }
        Notification notification2 = sbn.getNotification();
        if (notification2 != null) {
            String channelId = notification2.getChannelId();
            if ("NETWORK_STATUS".equals(channelId) || "MM_MISC_2022-1031-1650".equals(channelId)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.service.notification.NotificationListenerService
    public void onListenerConnected() {
        if (StatusBar.DEBUG) {
            Log.d("NotificationListener", "onListenerConnected");
        }
        onPluginConnected();
        final StatusBarNotification[] notifications = getActiveNotifications();
        if (notifications == null) {
            Log.w("NotificationListener", "onListenerConnected unable to get active notifications.");
        } else {
            final NotificationListenerService.RankingMap currentRanking = getCurrentRanking();
            this.mPresenter.getHandler().post(new Runnable() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationListener$IqvG8K3BFQSXJ_G1S_U_QONW3G4
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationListener.lambda$onListenerConnected$0(this.f$0, notifications, currentRanking);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$onListenerConnected$0(NotificationListener notificationListener, StatusBarNotification[] notifications, NotificationListenerService.RankingMap currentRanking) {
        for (StatusBarNotification sbn : notifications) {
            if (!notificationListener.shouldFilterOut(sbn)) {
                notificationListener.mEntryManager.addNotification(sbn, currentRanking);
            }
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationPosted(final StatusBarNotification sbn, final NotificationListenerService.RankingMap rankingMap) {
        if (StatusBar.DEBUG) {
            Log.d("NotificationListener", "onNotificationPosted: " + sbn);
        }
        if (!shouldFilterOut(sbn) && sbn != null && !onPluginNotificationPosted(sbn, rankingMap)) {
            this.mPresenter.getHandler().post(new Runnable() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationListener$NvFmU0XrVPuc5pizHcri9I0apkw
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationListener.lambda$onNotificationPosted$1(this.f$0, sbn, rankingMap);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$onNotificationPosted$1(NotificationListener notificationListener, StatusBarNotification sbn, NotificationListenerService.RankingMap rankingMap) {
        RemoteInputController.processForRemoteInput(sbn.getNotification(), notificationListener.mContext);
        String key = sbn.getKey();
        notificationListener.mEntryManager.removeKeyKeptForRemoteInput(key);
        boolean isUpdate = notificationListener.mEntryManager.getNotificationData().get(key) != null;
        if (!StatusBar.ENABLE_CHILD_NOTIFICATIONS && notificationListener.mPresenter.getGroupManager().isChildInGroupWithSummary(sbn)) {
            if (StatusBar.DEBUG) {
                Log.d("NotificationListener", "Ignoring group child due to existing summary: " + sbn);
            }
            if (isUpdate) {
                if (StatusBar.DEBUG) {
                    Log.d("NotificationListener", "onNotificationPosted, removeNotification: " + sbn);
                }
                notificationListener.mEntryManager.removeNotification(key, rankingMap);
                return;
            }
            if (StatusBar.DEBUG) {
                Log.d("NotificationListener", "onNotificationPosted, updateRanking: " + sbn);
            }
            notificationListener.mEntryManager.getNotificationData().updateRanking(rankingMap);
            return;
        }
        if (isUpdate) {
            notificationListener.mEntryManager.updateNotification(sbn, rankingMap);
        } else {
            notificationListener.mEntryManager.addNotification(sbn, rankingMap);
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(StatusBarNotification sbn, final NotificationListenerService.RankingMap rankingMap) {
        if (StatusBar.DEBUG) {
            Log.d("NotificationListener", "onNotificationRemoved: " + sbn);
        }
        if (!shouldFilterOut(sbn) && sbn != null && !onPluginNotificationRemoved(sbn, rankingMap)) {
            final String key = sbn.getKey();
            this.mPresenter.getHandler().post(new Runnable() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationListener$15M-1M8BYwmsVSJz5K4jyc_ZHWo
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.mEntryManager.removeNotification(key, rankingMap);
                }
            });
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        if (StatusBar.DEBUG) {
            Log.d("NotificationListener", "onRankingUpdate");
        }
        if (rankingMap != null) {
            final NotificationListenerService.RankingMap r = onPluginRankingUpdate(rankingMap);
            this.mPresenter.getHandler().post(new Runnable() { // from class: com.android.systemui.statusbar.-$$Lambda$NotificationListener$MPB4hTnfgfJz099PViVIkkbEBIE
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.mEntryManager.updateNotificationRanking(r);
                }
            });
        }
    }

    public void setUpWithPresenter(NotificationPresenter presenter, NotificationEntryManager entryManager) {
        this.mPresenter = presenter;
        this.mEntryManager = entryManager;
        try {
            registerAsSystemService(this.mContext, new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName()), -1);
        } catch (RemoteException e) {
            Log.e("NotificationListener", "Unable to register notification listener", e);
        }
    }
}
