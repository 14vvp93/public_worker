package com.android.systemui.recents.events.ui.dragndrop;

import android.graphics.Point;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.views.TaskView;
import com.android.systemui.shared.recents.model.Task;

/* JADX INFO: loaded from: classes.dex */
public class DragStartEvent extends EventBus.Event {
    public final boolean isUserTouchInitiated;
    public final Task task;
    public final TaskView taskView;
    public final Point tlOffset;

    public DragStartEvent(Task task, TaskView taskView, Point tlOffset) {
        this(task, taskView, tlOffset, true);
    }

    public DragStartEvent(Task task, TaskView taskView, Point tlOffset, boolean isUserTouchInitiated) {
        this.task = task;
        this.taskView = taskView;
        this.tlOffset = tlOffset;
        this.isUserTouchInitiated = isUserTouchInitiated;
    }
}
