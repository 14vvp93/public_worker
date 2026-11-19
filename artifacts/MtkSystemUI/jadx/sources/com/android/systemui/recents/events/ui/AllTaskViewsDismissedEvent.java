package com.android.systemui.recents.events.ui;

import com.android.systemui.recents.events.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class AllTaskViewsDismissedEvent extends EventBus.Event {
    public final int msgResId;

    public AllTaskViewsDismissedEvent(int msgResId) {
        this.msgResId = msgResId;
    }
}
