package com.android.systemui.recents.events.activity;

import com.android.systemui.recents.events.EventBus;
import com.android.systemui.shared.recents.model.Task;

/* JADX INFO: loaded from: classes.dex */
public class CancelEnterRecentsWindowAnimationEvent extends EventBus.Event {
    public final Task launchTask;

    public CancelEnterRecentsWindowAnimationEvent(Task launchTask) {
        this.launchTask = launchTask;
    }
}
