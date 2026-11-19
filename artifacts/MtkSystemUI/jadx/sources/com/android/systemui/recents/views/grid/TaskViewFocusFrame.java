package com.android.systemui.recents.views.grid;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import com.android.systemui.R;
import com.android.systemui.recents.views.TaskStackView;
import com.android.systemui.shared.recents.model.TaskStack;

/* JADX INFO: loaded from: classes.dex */
public class TaskViewFocusFrame extends View implements ViewTreeObserver.OnGlobalFocusChangeListener {
    private TaskStackView mSv;
    private TaskGridLayoutAlgorithm mTaskGridLayoutAlgorithm;

    public TaskViewFocusFrame(Context context) {
        this(context, null);
    }

    public TaskViewFocusFrame(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaskViewFocusFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TaskViewFocusFrame(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setBackground(this.mContext.getDrawable(R.drawable.recents_grid_task_view_focus_frame_background));
        setFocusable(false);
        hide();
    }

    public TaskViewFocusFrame(Context context, TaskStackView stackView, TaskGridLayoutAlgorithm taskGridLayoutAlgorithm) {
        this(context);
        this.mSv = stackView;
        this.mTaskGridLayoutAlgorithm = taskGridLayoutAlgorithm;
    }

    public void measure() {
        int thickness = this.mTaskGridLayoutAlgorithm.getFocusFrameThickness();
        Rect rect = this.mTaskGridLayoutAlgorithm.getTaskGridRect();
        measure(View.MeasureSpec.makeMeasureSpec(rect.width() + (thickness * 2), 1073741824), View.MeasureSpec.makeMeasureSpec(rect.height() + (thickness * 2), 1073741824));
    }

    public void layout() {
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    public void resize() {
        if (this.mSv.useGridLayout()) {
            this.mTaskGridLayoutAlgorithm.updateTaskGridRect(this.mSv.getStack().getTaskCount());
            measure();
            requestLayout();
        }
    }

    public void moveGridTaskViewFocus(View newFocus) {
        if (this.mSv.useGridLayout()) {
            if (newFocus instanceof GridTaskView) {
                int[] location = new int[2];
                newFocus.getLocationInWindow(location);
                int thickness = this.mTaskGridLayoutAlgorithm.getFocusFrameThickness();
                setTranslationX(location[0] - thickness);
                setTranslationY(location[1] - thickness);
                show();
                return;
            }
            hide();
        }
    }

    @Override // android.view.ViewTreeObserver.OnGlobalFocusChangeListener
    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
        if (!this.mSv.useGridLayout()) {
            return;
        }
        if (newFocus == null) {
            moveGridTaskViewFocus(null);
            return;
        }
        if (oldFocus == null) {
            TaskStack stack = this.mSv.getStack();
            int taskCount = stack.getTaskCount();
            int k = stack.indexOfTask(this.mSv.getFocusedTask());
            int taskIndexToFocus = k == -1 ? taskCount - 1 : k % taskCount;
            this.mSv.setFocusedTask(taskIndexToFocus, false, true);
        }
    }

    private void show() {
        setAlpha(1.0f);
    }

    private void hide() {
        setAlpha(0.0f);
    }
}
