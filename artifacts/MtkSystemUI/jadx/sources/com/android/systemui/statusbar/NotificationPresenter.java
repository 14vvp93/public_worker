package com.android.systemui.statusbar;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface NotificationPresenter extends ActivatableNotificationView.OnActivatedListener, ExpandableNotificationRow.OnExpandClickListener, NotificationData.Environment, NotificationEntryManager.Callback, NotificationRemoteInputManager.Callback {
    Handler getHandler();

    int getMaxNotificationsWhileLocked(boolean z);

    boolean isDeviceInVrMode();

    boolean isDeviceLocked(int i);

    boolean isPresenterFullyCollapsed();

    boolean isPresenterLocked();

    void onUpdateRowStates();

    void onUserSwitched(int i);

    void onWorkChallengeChanged();

    void startNotificationGutsIntent(Intent intent, int i, ExpandableNotificationRow expandableNotificationRow);

    void updateMediaMetaData(boolean z, boolean z2);

    void updateNotificationViews();

    void wakeUpIfDozing(long j, View view);
}
