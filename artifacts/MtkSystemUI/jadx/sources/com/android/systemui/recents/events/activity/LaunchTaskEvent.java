package com.android.systemui.recents.events.activity;

import android.graphics.Rect;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.views.TaskView;
import com.android.systemui.shared.recents.model.Task;

/* JADX INFO: loaded from: classes.dex */
public class LaunchTaskEvent extends EventBus.Event {
    public final boolean screenPinningRequested;
    public final int targetActivityType;
    public final Rect targetTaskBounds;
    public final int targetWindowingMode;
    public final Task task;
    public final TaskView taskView;

    public LaunchTaskEvent(TaskView taskView, Task task, Rect targetTaskBounds, boolean screenPinningRequested) {
        this(taskView, task, targetTaskBounds, screenPinningRequested, 0, 0);
    }

    public LaunchTaskEvent(TaskView taskView, Task task, Rect targetTaskBounds, boolean screenPinningRequested, int windowingMode, int activityType) {
        this.taskView = taskView;
        this.task = task;
        this.targetTaskBounds = targetTaskBounds;
        this.targetWindowingMode = windowingMode;
        this.targetActivityType = activityType;
        this.screenPinningRequested = screenPinningRequested;
    }
}
