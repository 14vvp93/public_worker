package com.android.systemui.recents.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import com.android.systemui.recents.Recents;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.view.AppTransitionAnimationSpecCompat;
import com.android.systemui.shared.recents.view.RecentsTransition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RecentsTransitionComposer {
    private Context mContext;
    private TaskViewTransform mTmpTransform = new TaskViewTransform();

    public RecentsTransitionComposer(Context context) {
        this.mContext = context;
    }

    private static AppTransitionAnimationSpecCompat composeAnimationSpec(TaskStackView stackView, TaskView taskView, TaskViewTransform transform, boolean addHeaderBitmap) {
        Bitmap b = null;
        if (addHeaderBitmap && (b = composeHeaderBitmap(taskView, transform)) == null) {
            return null;
        }
        Rect taskRect = new Rect();
        transform.rect.round(taskRect);
        if (!Recents.getConfiguration().isLowRamDevice && taskView.getTask() != stackView.getStack().getFrontMostTask()) {
            taskRect.bottom = taskRect.top + stackView.getMeasuredHeight();
        }
        return new AppTransitionAnimationSpecCompat(taskView.getTask().key.id, b, taskRect);
    }

    public List<AppTransitionAnimationSpecCompat> composeDockAnimationSpec(TaskView taskView, Rect bounds) {
        this.mTmpTransform.fillIn(taskView);
        Task task = taskView.getTask();
        Bitmap buffer = composeTaskBitmap(taskView, this.mTmpTransform);
        return Collections.singletonList(new AppTransitionAnimationSpecCompat(task.key.id, buffer, bounds));
    }

    public List<AppTransitionAnimationSpecCompat> composeAnimationSpecs(Task task, TaskStackView stackView, int windowingMode, int activityType, Rect windowRect) {
        TaskView taskView = stackView.getChildViewForTask(task);
        TaskStackLayoutAlgorithm stackLayout = stackView.getStackAlgorithm();
        Rect offscreenTaskRect = new Rect();
        stackLayout.getFrontOfStackTransform().rect.round(offscreenTaskRect);
        if (windowingMode == 1 || windowingMode == 3 || windowingMode == 4 || activityType == 4 || windowingMode == 0) {
            List<AppTransitionAnimationSpecCompat> specs = new ArrayList<>();
            if (taskView == null) {
                specs.add(composeOffscreenAnimationSpec(task, offscreenTaskRect));
            } else {
                this.mTmpTransform.fillIn(taskView);
                stackLayout.transformToScreenCoordinates(this.mTmpTransform, windowRect);
                AppTransitionAnimationSpecCompat spec = composeAnimationSpec(stackView, taskView, this.mTmpTransform, true);
                if (spec != null) {
                    specs.add(spec);
                }
            }
            return specs;
        }
        return Collections.emptyList();
    }

    private static AppTransitionAnimationSpecCompat composeOffscreenAnimationSpec(Task task, Rect taskRect) {
        return new AppTransitionAnimationSpecCompat(task.key.id, null, taskRect);
    }

    public static Bitmap composeTaskBitmap(TaskView taskView, TaskViewTransform transform) {
        float scale = transform.scale;
        int fromWidth = (int) (transform.rect.width() * scale);
        int fromHeight = (int) (transform.rect.height() * scale);
        if (fromWidth == 0 || fromHeight == 0) {
            Log.e("RecentsTransitionComposer", "Could not compose thumbnail for task: " + taskView.getTask() + " at transform: " + transform);
            return RecentsTransition.drawViewIntoHardwareBitmap(1, 1, null, 1.0f, 16777215);
        }
        return RecentsTransition.drawViewIntoHardwareBitmap(fromWidth, fromHeight, taskView, scale, 0);
    }

    private static Bitmap composeHeaderBitmap(TaskView taskView, TaskViewTransform transform) {
        float scale = transform.scale;
        int headerWidth = (int) transform.rect.width();
        int headerHeight = (int) (taskView.mHeaderView.getMeasuredHeight() * scale);
        if (headerWidth == 0 || headerHeight == 0) {
            return null;
        }
        return RecentsTransition.drawViewIntoHardwareBitmap(headerWidth, headerHeight, taskView.mHeaderView, scale, 0);
    }
}
