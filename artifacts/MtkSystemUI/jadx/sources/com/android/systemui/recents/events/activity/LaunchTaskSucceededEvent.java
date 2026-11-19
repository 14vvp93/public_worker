package com.android.systemui.recents.events.activity;

import com.android.systemui.recents.events.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class LaunchTaskSucceededEvent extends EventBus.Event {
    public final int taskIndexFromStackFront;

    public LaunchTaskSucceededEvent(int taskIndexFromStackFront) {
        this.taskIndexFromStackFront = taskIndexFromStackFront;
    }
}
