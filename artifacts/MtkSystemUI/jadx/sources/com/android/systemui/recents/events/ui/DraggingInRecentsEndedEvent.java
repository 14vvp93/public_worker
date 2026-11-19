package com.android.systemui.recents.events.ui;

import com.android.systemui.recents.events.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class DraggingInRecentsEndedEvent extends EventBus.Event {
    public final float velocity;

    public DraggingInRecentsEndedEvent(float velocity) {
        this.velocity = velocity;
    }
}
