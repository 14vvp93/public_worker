package com.android.systemui.recents.events.activity;

import com.android.systemui.recents.events.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class PackagesChangedEvent extends EventBus.Event {
    public final String packageName;
    public final int userId;

    public PackagesChangedEvent(String packageName, int userId) {
        this.packageName = packageName;
        this.userId = userId;
    }
}
