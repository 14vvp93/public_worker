package com.android.systemui.recents.views.grid;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.systemui.recents.events.ui.focus.NavigateTaskViewEvent;
import com.android.systemui.recents.views.TaskStackLayoutAlgorithm;
import com.android.systemui.recents.views.TaskViewTransform;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.utilities.Utilities;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class TaskGridLayoutAlgorithm {
    private float mAppAspectRatio;
    private int mFocusedFrameThickness;
    private int mPaddingLeftRight;
    private int mPaddingTaskView;
    private int mPaddingTopBottom;
    private Rect mTaskGridRect;
    private TaskGridRectInfo[] mTaskGridRectInfoList;
    private int mTitleBarHeight;
    private Rect mWindowRect;
    private final String TAG = "TaskGridLayoutAlgorithm";
    private Point mScreenSize = new Point();
    private Rect mSystemInsets = new Rect();

    class TaskGridRectInfo {
        int lines;
        Rect size = new Rect();
        int tasksPerLine;
        final /* synthetic */ TaskGridLayoutAlgorithm this$0;
        int[] xOffsets;
        int[] yOffsets;

        TaskGridRectInfo(TaskGridLayoutAlgorithm this$0, int taskCount) {
            int taskHeight;
            int taskHeight2;
            int i = taskCount;
            this.this$0 = this$0;
            this.xOffsets = new int[i];
            this.yOffsets = new int[i];
            int layoutTaskCount = Math.min(8, i);
            this.tasksPerLine = getTasksPerLine(layoutTaskCount);
            int i2 = 1;
            this.lines = layoutTaskCount < 4 ? 1 : 2;
            int taskLayoutIndex = 0;
            boolean landscapeWindow = this$0.mWindowRect.width() > this$0.mWindowRect.height();
            boolean landscapeTaskView = this$0.mAppAspectRatio > 1.0f;
            if (!landscapeWindow && landscapeTaskView) {
                this.tasksPerLine = layoutTaskCount < 2 ? 1 : 2;
                int i3 = 3;
                if (layoutTaskCount >= 3) {
                    if (layoutTaskCount >= 5) {
                        if (layoutTaskCount >= 7) {
                            i3 = 4;
                        }
                    } else {
                        i3 = 2;
                    }
                } else {
                    i3 = 1;
                }
                this.lines = i3;
            }
            if (landscapeWindow && !landscapeTaskView) {
                this.tasksPerLine = layoutTaskCount < 7 ? layoutTaskCount : 6;
                this.lines = layoutTaskCount < 7 ? 1 : 2;
            }
            int maxTaskWidth = ((this$0.mWindowRect.width() - (this$0.mPaddingLeftRight * 2)) - ((this.tasksPerLine - 1) * this$0.mPaddingTaskView)) / this.tasksPerLine;
            int maxTaskHeight = ((this$0.mWindowRect.height() - (this$0.mPaddingTopBottom * 2)) - ((this.lines - 1) * this$0.mPaddingTaskView)) / this.lines;
            if (maxTaskHeight >= (maxTaskWidth / this$0.mAppAspectRatio) + this$0.mTitleBarHeight) {
                taskHeight2 = maxTaskWidth;
                taskHeight = (int) (((double) ((maxTaskWidth / this$0.mAppAspectRatio) + this$0.mTitleBarHeight)) + 0.5d);
            } else {
                taskHeight = maxTaskHeight;
                taskHeight2 = (int) (((double) ((maxTaskHeight - this$0.mTitleBarHeight) * this$0.mAppAspectRatio)) + 0.5d);
            }
            this.size.set(0, 0, taskHeight2, taskHeight);
            int emptySpaceX = ((this$0.mWindowRect.width() - (this$0.mPaddingLeftRight * 2)) - (this.tasksPerLine * taskHeight2)) - ((this.tasksPerLine - 1) * this$0.mPaddingTaskView);
            int emptySpaceY = ((this$0.mWindowRect.height() - (2 * this$0.mPaddingTopBottom)) - (this.lines * taskHeight)) - ((this.lines - 1) * this$0.mPaddingTaskView);
            while (true) {
                int taskIndex = taskLayoutIndex;
                if (taskIndex >= i) {
                    return;
                }
                int taskLayoutIndex2 = (i - taskIndex) - i2;
                int xIndex = taskLayoutIndex2 % this.tasksPerLine;
                int yIndex = taskLayoutIndex2 / this.tasksPerLine;
                this.xOffsets[taskIndex] = this$0.mWindowRect.left + (emptySpaceX / 2) + this$0.mPaddingLeftRight + ((taskHeight2 + this$0.mPaddingTaskView) * xIndex);
                this.yOffsets[taskIndex] = this$0.mWindowRect.top + (emptySpaceY / 2) + this$0.mPaddingTopBottom + ((taskHeight + this$0.mPaddingTaskView) * yIndex);
                taskLayoutIndex = taskIndex + 1;
                layoutTaskCount = layoutTaskCount;
                i = taskCount;
                i2 = 1;
            }
        }

        private int getTasksPerLine(int taskCount) {
            switch (taskCount) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                case 4:
                    return 2;
                case 3:
                case 5:
                case 6:
                    return 3;
                case 7:
                case 8:
                    return 4;
                default:
                    throw new IllegalArgumentException("Unsupported task count " + taskCount);
            }
        }
    }

    public TaskGridLayoutAlgorithm(Context context) {
        reloadOnConfigurationChange(context);
    }

    public void reloadOnConfigurationChange(Context context) {
        Resources res = context.getResources();
        this.mPaddingTaskView = res.getDimensionPixelSize(R.dimen.recents_grid_padding_task_view);
        this.mFocusedFrameThickness = res.getDimensionPixelSize(R.dimen.recents_grid_task_view_focused_frame_thickness);
        this.mTaskGridRect = new Rect();
        this.mTitleBarHeight = res.getDimensionPixelSize(R.dimen.recents_grid_task_view_header_height);
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        windowManager.getDefaultDisplay().getRealSize(this.mScreenSize);
        updateAppAspectRatio();
    }

    public TaskViewTransform getTransform(int taskIndex, int taskCount, TaskViewTransform transformOut, TaskStackLayoutAlgorithm stackLayout) {
        if (taskCount == 0) {
            transformOut.reset();
            return transformOut;
        }
        TaskGridRectInfo gridInfo = this.mTaskGridRectInfoList[taskCount - 1];
        this.mTaskGridRect.set(gridInfo.size);
        int x = gridInfo.xOffsets[taskIndex];
        int y = gridInfo.yOffsets[taskIndex];
        float z = stackLayout.mMaxTranslationZ;
        int taskLayoutIndex = (taskCount - taskIndex) - 1;
        boolean isTaskViewVisible = taskLayoutIndex < 8;
        transformOut.scale = 1.0f;
        transformOut.alpha = isTaskViewVisible ? 1.0f : 0.0f;
        transformOut.translationZ = z;
        transformOut.dimAlpha = 0.0f;
        transformOut.viewOutlineAlpha = 1.0f;
        transformOut.rect.set(this.mTaskGridRect);
        transformOut.rect.offset(x, y);
        Utilities.scaleRectAboutCenter(transformOut.rect, transformOut.scale);
        transformOut.visible = isTaskViewVisible;
        return transformOut;
    }

    public int navigateFocus(int taskCount, int currentFocusedIndex, NavigateTaskViewEvent.Direction direction) {
        if (taskCount < 1 || taskCount > 8) {
            return -1;
        }
        if (currentFocusedIndex == -1) {
            return 0;
        }
        TaskGridRectInfo gridInfo = this.mTaskGridRectInfoList[taskCount - 1];
        int currentLine = ((taskCount - 1) - currentFocusedIndex) / gridInfo.tasksPerLine;
        switch (direction) {
            case UP:
                int newIndex = currentFocusedIndex + gridInfo.tasksPerLine;
                return newIndex >= taskCount ? currentFocusedIndex : newIndex;
            case DOWN:
                int leftMostIndex = gridInfo.tasksPerLine;
                int newIndex2 = currentFocusedIndex - leftMostIndex;
                return newIndex2 < 0 ? currentFocusedIndex : newIndex2;
            case LEFT:
                int newIndex3 = currentFocusedIndex + 1;
                int leftMostIndex2 = (taskCount - 1) - (gridInfo.tasksPerLine * currentLine);
                return newIndex3 > leftMostIndex2 ? currentFocusedIndex : newIndex3;
            case RIGHT:
                int newIndex4 = currentFocusedIndex - 1;
                int rightMostIndex = 1 + ((taskCount - 1) - ((currentLine + 1) * gridInfo.tasksPerLine));
                return newIndex4 < (rightMostIndex >= 0 ? rightMostIndex : 0) ? currentFocusedIndex : newIndex4;
            default:
                return currentFocusedIndex;
        }
    }

    public void initialize(Rect windowRect) {
        this.mWindowRect = windowRect;
        this.mPaddingLeftRight = (int) (0.025f * Math.min(this.mWindowRect.width(), this.mWindowRect.height()));
        this.mPaddingTopBottom = (int) (0.1d * ((double) this.mWindowRect.height()));
        this.mTaskGridRectInfoList = new TaskGridRectInfo[8];
        for (int i = 0; i < 8; i++) {
            this.mTaskGridRectInfoList[i] = new TaskGridRectInfo(this, i + 1);
        }
    }

    public void setSystemInsets(Rect systemInsets) {
        this.mSystemInsets = systemInsets;
        updateAppAspectRatio();
    }

    private void updateAppAspectRatio() {
        int usableWidth = (this.mScreenSize.x - this.mSystemInsets.left) - this.mSystemInsets.right;
        int usableHeight = (this.mScreenSize.y - this.mSystemInsets.top) - this.mSystemInsets.bottom;
        this.mAppAspectRatio = usableWidth / usableHeight;
    }

    public Rect getStackActionButtonRect() {
        Rect buttonRect = new Rect(this.mWindowRect);
        buttonRect.right -= this.mPaddingLeftRight;
        buttonRect.left += this.mPaddingLeftRight;
        buttonRect.bottom = buttonRect.top + this.mPaddingTopBottom;
        return buttonRect;
    }

    public void updateTaskGridRect(int taskCount) {
        if (taskCount > 0) {
            TaskGridRectInfo gridInfo = this.mTaskGridRectInfoList[taskCount - 1];
            this.mTaskGridRect.set(gridInfo.size);
        }
    }

    public Rect getTaskGridRect() {
        return this.mTaskGridRect;
    }

    public int getFocusFrameThickness() {
        return this.mFocusedFrameThickness;
    }

    public TaskStackLayoutAlgorithm.VisibilityReport computeStackVisibilityReport(ArrayList<Task> tasks) {
        int visibleCount = Math.min(8, tasks.size());
        return new TaskStackLayoutAlgorithm.VisibilityReport(visibleCount, visibleCount);
    }
}
