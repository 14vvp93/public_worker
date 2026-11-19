package com.android.systemui.recents.events.component;

import com.android.systemui.recents.events.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class ShowUserToastEvent extends EventBus.Event {
    public final int msgLength;
    public final int msgResId;

    public ShowUserToastEvent(int msgResId, int msgLength) {
        this.msgResId = msgResId;
        this.msgLength = msgLength;
    }
}
