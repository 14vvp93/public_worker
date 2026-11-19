package com.android.systemui.recents.views.lowram;

import android.content.Context;
import android.graphics.Rect;
import android.view.ViewConfiguration;
import com.android.systemui.R;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.RecentsActivityLaunchState;
import com.android.systemui.recents.views.TaskStackLayoutAlgorithm;
import com.android.systemui.recents.views.TaskViewTransform;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.utilities.Utilities;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class TaskStackLowRamLayoutAlgorithm {
    private int mFlingThreshold;
    private int mPadding;
    private int mPaddingEndTopBottom;
    private int mPaddingLeftRight;
    private int mTopOffset;
    private Rect mWindowRect;
    private Rect mTaskRect = new Rect();
    private Rect mSystemInsets = new Rect();

    public TaskStackLowRamLayoutAlgorithm(Context context) {
        reloadOnConfigurationChange(context);
    }

    public void reloadOnConfigurationChange(Context context) {
        this.mPadding = context.getResources().getDimensionPixelSize(R.dimen.recents_layout_side_margin_phone);
        this.mFlingThreshold = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    public void initialize(Rect windowRect) {
        this.mWindowRect = windowRect;
        if (this.mWindowRect.height() > 0) {
            int windowHeight = this.mWindowRect.height() - this.mSystemInsets.bottom;
            int windowWidth = (this.mWindowRect.width() - this.mSystemInsets.right) - this.mSystemInsets.left;
            int width = Math.min(windowWidth, windowHeight) - (this.mPadding * 2);
            boolean isLandscape = windowWidth > windowHeight;
            this.mTaskRect.set(0, 0, width, isLandscape ? (width * 2) / 3 : width);
            this.mPaddingLeftRight = (windowWidth - this.mTaskRect.width()) / 2;
            this.mPaddingEndTopBottom = (windowHeight - this.mTaskRect.height()) / 2;
            this.mTopOffset = (getTotalHeightOfTasks(9) - windowHeight) / 2;
        }
    }

    public void setSystemInsets(Rect systemInsets) {
        this.mSystemInsets = systemInsets;
    }

    public TaskStackLayoutAlgorithm.VisibilityReport computeStackVisibilityReport(ArrayList<Task> tasks) {
        int maxVisible;
        RecentsActivityLaunchState launchState = Recents.getConfiguration().getLaunchState();
        if (launchState.launchedFromHome || launchState.launchedFromPipApp || launchState.launchedWithNextPipApp) {
            maxVisible = 2;
        } else {
            maxVisible = 3;
        }
        int visibleCount = Math.min(maxVisible, tasks.size());
        return new TaskStackLayoutAlgorithm.VisibilityReport(visibleCount, visibleCount);
    }

    public void getFrontOfStackTransform(TaskViewTransform transformOut, TaskStackLayoutAlgorithm stackLayout) {
        if (this.mWindowRect == null) {
            transformOut.reset();
            return;
        }
        int windowHeight = this.mWindowRect.height() - this.mSystemInsets.bottom;
        int bottomOfCurrentTask = (this.mTaskRect.height() + windowHeight) / 2;
        int y = this.mTaskRect.height() + bottomOfCurrentTask + (this.mPadding * 2);
        fillStackTransform(transformOut, y, stackLayout.mMaxTranslationZ, true);
    }

    public void getBackOfStackTransform(TaskViewTransform transformOut, TaskStackLayoutAlgorithm stackLayout) {
        if (this.mWindowRect == null) {
            transformOut.reset();
            return;
        }
        int windowHeight = this.mWindowRect.height() - this.mSystemInsets.bottom;
        int topOfCurrentTask = (windowHeight - this.mTaskRect.height()) / 2;
        int y = topOfCurrentTask - ((this.mTaskRect.height() + this.mPadding) * 2);
        fillStackTransform(transformOut, y, stackLayout.mMaxTranslationZ, true);
    }

    public TaskViewTransform getTransform(int taskIndex, float stackScroll, TaskViewTransform transformOut, int taskCount, TaskStackLayoutAlgorithm stackLayout) {
        int y;
        if (taskCount == 0) {
            transformOut.reset();
            return transformOut;
        }
        boolean visible = true;
        if (taskCount > 1) {
            y = getTaskTopFromIndex(taskIndex) - percentageToScroll(stackScroll);
            visible = (this.mPadding + y) + getTaskRect().height() > 0;
        } else {
            int windowHeight = this.mWindowRect.height() - this.mSystemInsets.bottom;
            y = ((windowHeight - this.mTaskRect.height()) / 2) - percentageToScroll(stackScroll);
        }
        int windowHeight2 = y;
        int y2 = stackLayout.mMaxTranslationZ;
        fillStackTransform(transformOut, windowHeight2, y2, visible);
        return transformOut;
    }

    public float getClosestTaskP(float scrollP, int numTasks, int velocity) {
        boolean useNext;
        int y = percentageToScroll(scrollP);
        boolean z = false;
        int lastY = getTaskTopFromIndex(0) - this.mPaddingEndTopBottom;
        int lastY2 = lastY;
        for (int lastY3 = 1; lastY3 < numTasks; lastY3++) {
            int taskY = getTaskTopFromIndex(lastY3) - this.mPaddingEndTopBottom;
            int diff = taskY - y;
            if (diff > 0) {
                int diffPrev = Math.abs(y - lastY2);
                if (diff <= diffPrev) {
                    useNext = false;
                } else {
                    useNext = true;
                }
                if (Math.abs(velocity) > this.mFlingThreshold) {
                    if (velocity > 0) {
                        z = true;
                    }
                    useNext = z;
                }
                return useNext ? scrollToPercentage(lastY2) : scrollToPercentage(taskY);
            }
            lastY2 = taskY;
        }
        return scrollToPercentage(lastY2);
    }

    public float scrollToPercentage(int scroll) {
        return scroll / (this.mTaskRect.height() + this.mPadding);
    }

    public int percentageToScroll(float p) {
        return (int) ((this.mTaskRect.height() + this.mPadding) * p);
    }

    public float getMinScrollP() {
        return getScrollPForTask(0);
    }

    public float getMaxScrollP(int taskCount) {
        return getScrollPForTask(taskCount - 1);
    }

    public float getInitialScrollP(int taskCount, boolean fromHome) {
        if (fromHome) {
            return getMaxScrollP(taskCount);
        }
        if (taskCount < 2) {
            return 0.0f;
        }
        return getScrollPForTask(taskCount - 2);
    }

    public float getScrollPForTask(int taskIndex) {
        return scrollToPercentage(getTaskTopFromIndex(taskIndex) - this.mPaddingEndTopBottom);
    }

    public Rect getTaskRect() {
        return this.mTaskRect;
    }

    public float getMaxOverscroll() {
        return 0.6666666f;
    }

    private int getTaskTopFromIndex(int index) {
        return getTotalHeightOfTasks(index) - this.mTopOffset;
    }

    private int getTotalHeightOfTasks(int taskCount) {
        return (this.mTaskRect.height() * taskCount) + ((taskCount + 1) * this.mPadding);
    }

    private void fillStackTransform(TaskViewTransform transformOut, int y, int translationZ, boolean visible) {
        transformOut.scale = 1.0f;
        transformOut.alpha = 1.0f;
        transformOut.translationZ = translationZ;
        transformOut.dimAlpha = 0.0f;
        transformOut.viewOutlineAlpha = 1.0f;
        transformOut.rect.set(getTaskRect());
        transformOut.rect.offset(this.mPaddingLeftRight + this.mSystemInsets.left, y);
        Utilities.scaleRectAboutCenter(transformOut.rect, transformOut.scale);
        transformOut.visible = visible;
    }
}
