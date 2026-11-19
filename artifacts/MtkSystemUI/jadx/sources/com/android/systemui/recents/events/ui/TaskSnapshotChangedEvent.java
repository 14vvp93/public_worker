package com.android.systemui.recents.events.ui;

import com.android.systemui.recents.events.EventBus;
import com.android.systemui.shared.recents.model.ThumbnailData;

/* JADX INFO: loaded from: classes.dex */
public class TaskSnapshotChangedEvent extends EventBus.Event {
    public final int taskId;
    public final ThumbnailData thumbnailData;

    public TaskSnapshotChangedEvent(int taskId, ThumbnailData thumbnailData) {
        this.taskId = taskId;
        this.thumbnailData = thumbnailData;
    }
}
