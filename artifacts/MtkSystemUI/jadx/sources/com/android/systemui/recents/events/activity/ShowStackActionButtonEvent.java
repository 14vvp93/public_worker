package com.android.systemui.recents.events.activity;

import com.android.systemui.recents.events.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class ShowStackActionButtonEvent extends EventBus.Event {
    public final boolean translate;

    public ShowStackActionButtonEvent(boolean translate) {
        this.translate = translate;
    }
}
