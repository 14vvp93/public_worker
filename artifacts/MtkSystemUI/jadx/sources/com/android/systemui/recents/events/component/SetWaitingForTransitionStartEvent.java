package com.android.systemui.recents.events.component;

import com.android.systemui.recents.events.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class SetWaitingForTransitionStartEvent extends EventBus.Event {
    public final boolean waitingForTransitionStart;

    public SetWaitingForTransitionStartEvent(boolean waitingForTransitionStart) {
        this.waitingForTransitionStart = waitingForTransitionStart;
    }
}
