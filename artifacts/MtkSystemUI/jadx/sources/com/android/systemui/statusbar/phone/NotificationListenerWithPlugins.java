package com.android.systemui.statusbar.phone;

import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.NotificationListenerController;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import java.util.ArrayList;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class NotificationListenerWithPlugins extends NotificationListenerService implements PluginListener<NotificationListenerController> {
    private boolean mConnected;
    private ArrayList<NotificationListenerController> mPlugins = new ArrayList<>();

    public void registerAsSystemService(Context context, ComponentName componentName, int currentUser) throws RemoteException {
        super.registerAsSystemService(context, componentName, currentUser);
        ((PluginManager) Dependency.get(PluginManager.class)).addPluginListener(this, NotificationListenerController.class);
    }

    public void unregisterAsSystemService() throws RemoteException {
        super.unregisterAsSystemService();
        ((PluginManager) Dependency.get(PluginManager.class)).removePluginListener(this);
    }

    @Override // android.service.notification.NotificationListenerService
    public StatusBarNotification[] getActiveNotifications() {
        StatusBarNotification[] activeNotifications = super.getActiveNotifications();
        for (NotificationListenerController plugin : this.mPlugins) {
            activeNotifications = plugin.getActiveNotifications(activeNotifications);
        }
        return activeNotifications;
    }

    @Override // android.service.notification.NotificationListenerService
    public NotificationListenerService.RankingMap getCurrentRanking() {
        NotificationListenerService.RankingMap currentRanking = super.getCurrentRanking();
        for (NotificationListenerController plugin : this.mPlugins) {
            currentRanking = plugin.getCurrentRanking(currentRanking);
        }
        return currentRanking;
    }

    public void onPluginConnected() {
        this.mConnected = true;
        this.mPlugins.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NotificationListenerWithPlugins$AOWJwBGrUF4vFOVx-Lxlu4eVQD0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((NotificationListenerController) obj).onListenerConnected(this.f$0.getProvider());
            }
        });
    }

    public boolean onPluginNotificationPosted(StatusBarNotification sbn, NotificationListenerService.RankingMap rankingMap) {
        for (NotificationListenerController plugin : this.mPlugins) {
            if (plugin.onNotificationPosted(sbn, rankingMap)) {
                if (StatusBar.DEBUG) {
                    Log.d("NotificationListenerWithPlugins", "onPluginNotificationPosted, plugin: " + plugin + ", sbn: " + sbn);
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    public boolean onPluginNotificationRemoved(StatusBarNotification sbn, NotificationListenerService.RankingMap rankingMap) {
        for (NotificationListenerController plugin : this.mPlugins) {
            if (plugin.onNotificationRemoved(sbn, rankingMap)) {
                return true;
            }
        }
        return false;
    }

    public NotificationListenerService.RankingMap onPluginRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        return getCurrentRanking();
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginConnected(NotificationListenerController plugin, Context pluginContext) {
        this.mPlugins.add(plugin);
        if (this.mConnected) {
            plugin.onListenerConnected(getProvider());
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginDisconnected(NotificationListenerController plugin) {
        this.mPlugins.remove(plugin);
    }

    private NotificationListenerController.NotificationProvider getProvider() {
        return new NotificationListenerController.NotificationProvider() { // from class: com.android.systemui.statusbar.phone.NotificationListenerWithPlugins.1
            @Override // com.android.systemui.plugins.NotificationListenerController.NotificationProvider
            public StatusBarNotification[] getActiveNotifications() {
                return NotificationListenerWithPlugins.super.getActiveNotifications();
            }

            @Override // com.android.systemui.plugins.NotificationListenerController.NotificationProvider
            public NotificationListenerService.RankingMap getRankingMap() {
                return NotificationListenerWithPlugins.super.getCurrentRanking();
            }

            @Override // com.android.systemui.plugins.NotificationListenerController.NotificationProvider
            public void addNotification(StatusBarNotification sbn) {
                NotificationListenerWithPlugins.this.onNotificationPosted(sbn, getRankingMap());
            }

            @Override // com.android.systemui.plugins.NotificationListenerController.NotificationProvider
            public void removeNotification(StatusBarNotification sbn) {
                NotificationListenerWithPlugins.this.onNotificationRemoved(sbn, getRankingMap());
            }

            @Override // com.android.systemui.plugins.NotificationListenerController.NotificationProvider
            public void updateRanking() {
                NotificationListenerWithPlugins.this.onNotificationRankingUpdate(getRankingMap());
            }
        };
    }
}
