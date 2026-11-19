package com.android.systemui.recents.events.ui;

import com.android.systemui.recents.events.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class DraggingInRecentsEvent extends EventBus.Event {
    public final float distanceFromTop;

    public DraggingInRecentsEvent(float distanceFromTop) {
        this.distanceFromTop = distanceFromTop;
    }
}
