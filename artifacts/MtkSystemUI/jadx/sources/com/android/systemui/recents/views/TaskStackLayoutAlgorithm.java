package com.android.systemui.recents.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.ViewDebug;
import com.android.systemui.R;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.RecentsActivityLaunchState;
import com.android.systemui.recents.RecentsConfiguration;
import com.android.systemui.recents.misc.FreePathInterpolator;
import com.android.systemui.recents.views.grid.TaskGridLayoutAlgorithm;
import com.android.systemui.recents.views.lowram.TaskStackLowRamLayoutAlgorithm;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.TaskStack;
import com.android.systemui.shared.recents.utilities.Utilities;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TaskStackLayoutAlgorithm {

    @ViewDebug.ExportedProperty(category = "recents")
    private int mBaseBottomMargin;
    private int mBaseInitialBottomOffset;
    private int mBaseInitialTopOffset;

    @ViewDebug.ExportedProperty(category = "recents")
    private int mBaseSideMargin;

    @ViewDebug.ExportedProperty(category = "recents")
    private int mBaseTopMargin;
    private TaskStackLayoutAlgorithmCallbacks mCb;
    Context mContext;

    @ViewDebug.ExportedProperty(category = "recents")
    private int mFocusState;

    @ViewDebug.ExportedProperty(category = "recents")
    private int mFocusedBottomPeekHeight;
    private Path mFocusedCurve;
    private FreePathInterpolator mFocusedCurveInterpolator;
    private Path mFocusedDimCurve;
    private FreePathInterpolator mFocusedDimCurveInterpolator;
    private Range mFocusedRange;

    @ViewDebug.ExportedProperty(category = "recents")
    private int mFocusedTopPeekHeight;

    @ViewDebug.ExportedProperty(category = "recents")
    float mFrontMostTaskP;

    @ViewDebug.ExportedProperty(category = "recents")
    private int mInitialBottomOffset;

    @ViewDebug.ExportedProperty(category = "recents")
    float mInitialScrollP;

    @ViewDebug.ExportedProperty(category = "recents")
    private int mInitialTopOffset;

    @ViewDebug.ExportedProperty(category = "recents")
    float mMaxScrollP;

    @ViewDebug.ExportedProperty(category = "recents")
    public int mMaxTranslationZ;
    private int mMinMargin;

    @ViewDebug.ExportedProperty(category = "recents")
    float mMinScrollP;

    @ViewDebug.ExportedProperty(category = "recents")
    int mMinTranslationZ;

    @ViewDebug.ExportedProperty(category = "recents")
    int mNumStackTasks;

    @ViewDebug.ExportedProperty(category = "recents")
    private int mStackBottomOffset;
    TaskGridLayoutAlgorithm mTaskGridLayoutAlgorithm;
    TaskStackLowRamLayoutAlgorithm mTaskStackLowRamLayoutAlgorithm;
    private int mTitleBarHeight;
    private Path mUnfocusedCurve;
    private FreePathInterpolator mUnfocusedCurveInterpolator;
    private Path mUnfocusedDimCurve;
    private FreePathInterpolator mUnfocusedDimCurveInterpolator;
    private Range mUnfocusedRange;

    @ViewDebug.ExportedProperty(category = "recents")
    public Rect mTaskRect = new Rect();

    @ViewDebug.ExportedProperty(category = "recents")
    public Rect mStackRect = new Rect();

    @ViewDebug.ExportedProperty(category = "recents")
    public Rect mSystemInsets = new Rect();

    @ViewDebug.ExportedProperty(category = "recents")
    private Rect mStackActionButtonRect = new Rect();
    private SparseIntArray mTaskIndexMap = new SparseIntArray();
    private SparseArray<Float> mTaskIndexOverrideMap = new SparseArray<>();
    TaskViewTransform mBackOfStackTransform = new TaskViewTransform();
    TaskViewTransform mFrontOfStackTransform = new TaskViewTransform();

    public interface TaskStackLayoutAlgorithmCallbacks {
        void onFocusStateChanged(int i, int i2);
    }

    boolean useGridLayout() {
        return Recents.getConfiguration().isGridEnabled;
    }

    public static class VisibilityReport {
        public int numVisibleTasks;
        public int numVisibleThumbnails;

        public VisibilityReport(int tasks, int thumbnails) {
            this.numVisibleTasks = tasks;
            this.numVisibleThumbnails = thumbnails;
        }
    }

    public TaskStackLayoutAlgorithm(Context context, TaskStackLayoutAlgorithmCallbacks cb) {
        this.mContext = context;
        this.mCb = cb;
        this.mTaskGridLayoutAlgorithm = new TaskGridLayoutAlgorithm(context);
        this.mTaskStackLowRamLayoutAlgorithm = new TaskStackLowRamLayoutAlgorithm(context);
        reloadOnConfigurationChange(context);
    }

    public void reloadOnConfigurationChange(Context context) {
        Resources res = context.getResources();
        this.mFocusedRange = new Range(res.getFloat(R.integer.recents_layout_focused_range_min), res.getFloat(R.integer.recents_layout_focused_range_max));
        this.mUnfocusedRange = new Range(res.getFloat(R.integer.recents_layout_unfocused_range_min), res.getFloat(R.integer.recents_layout_unfocused_range_max));
        this.mFocusState = getInitialFocusState();
        this.mFocusedTopPeekHeight = res.getDimensionPixelSize(R.dimen.recents_layout_top_peek_size);
        this.mFocusedBottomPeekHeight = res.getDimensionPixelSize(R.dimen.recents_layout_bottom_peek_size);
        this.mMinTranslationZ = res.getDimensionPixelSize(R.dimen.recents_layout_z_min);
        this.mMaxTranslationZ = res.getDimensionPixelSize(R.dimen.recents_layout_z_max);
        this.mBaseInitialTopOffset = getDimensionForDevice(context, R.dimen.recents_layout_initial_top_offset_phone_port, R.dimen.recents_layout_initial_top_offset_phone_land, R.dimen.recents_layout_initial_top_offset_tablet, R.dimen.recents_layout_initial_top_offset_tablet, R.dimen.recents_layout_initial_top_offset_tablet, R.dimen.recents_layout_initial_top_offset_tablet, R.dimen.recents_layout_initial_top_offset_tablet);
        this.mBaseInitialBottomOffset = getDimensionForDevice(context, R.dimen.recents_layout_initial_bottom_offset_phone_port, R.dimen.recents_layout_initial_bottom_offset_phone_land, R.dimen.recents_layout_initial_bottom_offset_tablet, R.dimen.recents_layout_initial_bottom_offset_tablet, R.dimen.recents_layout_initial_bottom_offset_tablet, R.dimen.recents_layout_initial_bottom_offset_tablet, R.dimen.recents_layout_initial_bottom_offset_tablet);
        this.mTaskGridLayoutAlgorithm.reloadOnConfigurationChange(context);
        this.mTaskStackLowRamLayoutAlgorithm.reloadOnConfigurationChange(context);
        this.mMinMargin = res.getDimensionPixelSize(R.dimen.recents_layout_min_margin);
        this.mBaseTopMargin = getDimensionForDevice(context, R.dimen.recents_layout_top_margin_phone, R.dimen.recents_layout_top_margin_tablet, R.dimen.recents_layout_top_margin_tablet_xlarge, R.dimen.recents_layout_top_margin_tablet);
        this.mBaseSideMargin = getDimensionForDevice(context, R.dimen.recents_layout_side_margin_phone, R.dimen.recents_layout_side_margin_tablet, R.dimen.recents_layout_side_margin_tablet_xlarge, R.dimen.recents_layout_side_margin_tablet);
        this.mBaseBottomMargin = res.getDimensionPixelSize(R.dimen.recents_layout_bottom_margin);
        this.mTitleBarHeight = getDimensionForDevice(this.mContext, R.dimen.recents_task_view_header_height, R.dimen.recents_task_view_header_height, R.dimen.recents_task_view_header_height, R.dimen.recents_task_view_header_height_tablet_land, R.dimen.recents_task_view_header_height, R.dimen.recents_task_view_header_height_tablet_land, R.dimen.recents_grid_task_view_header_height);
    }

    public void reset() {
        this.mTaskIndexOverrideMap.clear();
        setFocusState(getInitialFocusState());
    }

    public boolean setSystemInsets(Rect systemInsets) {
        boolean changed = !this.mSystemInsets.equals(systemInsets);
        this.mSystemInsets.set(systemInsets);
        this.mTaskGridLayoutAlgorithm.setSystemInsets(systemInsets);
        this.mTaskStackLowRamLayoutAlgorithm.setSystemInsets(systemInsets);
        return changed;
    }

    public void setFocusState(int focusState) {
        int prevFocusState = this.mFocusState;
        this.mFocusState = focusState;
        updateFrontBackTransforms();
        if (this.mCb != null) {
            this.mCb.onFocusStateChanged(prevFocusState, focusState);
        }
    }

    public int getFocusState() {
        return this.mFocusState;
    }

    public void initialize(Rect displayRect, Rect windowRect, Rect taskStackBounds) {
        Rect lastStackRect = new Rect(this.mStackRect);
        int topMargin = getScaleForExtent(windowRect, displayRect, this.mBaseTopMargin, this.mMinMargin, 1);
        int bottomMargin = getScaleForExtent(windowRect, displayRect, this.mBaseBottomMargin, this.mMinMargin, 1);
        this.mInitialTopOffset = getScaleForExtent(windowRect, displayRect, this.mBaseInitialTopOffset, this.mMinMargin, 1);
        this.mInitialBottomOffset = this.mBaseInitialBottomOffset;
        this.mStackBottomOffset = this.mSystemInsets.bottom + bottomMargin;
        this.mStackRect.set(taskStackBounds);
        this.mStackRect.top += topMargin;
        this.mStackActionButtonRect.set(this.mStackRect.left, this.mStackRect.top - topMargin, this.mStackRect.right, this.mStackRect.top + this.mFocusedTopPeekHeight);
        int height = (this.mStackRect.height() - this.mInitialTopOffset) - this.mStackBottomOffset;
        this.mTaskRect.set(this.mStackRect.left, this.mStackRect.top, this.mStackRect.right, this.mStackRect.top + height);
        if (this.mTaskRect.width() <= 0 || this.mTaskRect.height() <= 0) {
            Log.e("TaskStackLayoutAlgorithm", "Invalid task rect: taskRect=" + this.mTaskRect + " stackRect=" + this.mStackRect + " displayRect=" + displayRect + " windowRect=" + windowRect + " taskStackBounds=" + taskStackBounds);
        }
        if (!lastStackRect.equals(this.mStackRect)) {
            this.mUnfocusedCurve = constructUnfocusedCurve();
            this.mUnfocusedCurveInterpolator = new FreePathInterpolator(this.mUnfocusedCurve);
            this.mFocusedCurve = constructFocusedCurve();
            this.mFocusedCurveInterpolator = new FreePathInterpolator(this.mFocusedCurve);
            this.mUnfocusedDimCurve = constructUnfocusedDimCurve();
            this.mUnfocusedDimCurveInterpolator = new FreePathInterpolator(this.mUnfocusedDimCurve);
            this.mFocusedDimCurve = constructFocusedDimCurve();
            this.mFocusedDimCurveInterpolator = new FreePathInterpolator(this.mFocusedDimCurve);
            updateFrontBackTransforms();
        }
        this.mTaskGridLayoutAlgorithm.initialize(windowRect);
        this.mTaskStackLowRamLayoutAlgorithm.initialize(windowRect);
    }

    public void update(TaskStack stack, ArraySet<Task.TaskKey> ignoreTasksSet, RecentsActivityLaunchState launchState, float lastScrollPPercent) {
        int launchTaskIndex;
        float minScrollP;
        float fMax;
        Recents.getSystemServices();
        this.mTaskIndexMap.clear();
        ArrayList<Task> tasks = stack.getTasks();
        if (tasks.isEmpty()) {
            this.mFrontMostTaskP = 0.0f;
            this.mInitialScrollP = 0.0f;
            this.mMaxScrollP = 0.0f;
            this.mMinScrollP = 0.0f;
            this.mNumStackTasks = 0;
            return;
        }
        ArrayList<Task> stackTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (!ignoreTasksSet.contains(task.key)) {
                stackTasks.add(task);
            }
        }
        this.mNumStackTasks = stackTasks.size();
        int taskCount = stackTasks.size();
        for (int i2 = 0; i2 < taskCount; i2++) {
            this.mTaskIndexMap.put(stackTasks.get(i2).key.id, i2);
        }
        Task launchTask = stack.getLaunchTarget();
        if (launchTask == null) {
            launchTaskIndex = this.mNumStackTasks - 1;
        } else {
            launchTaskIndex = stack.indexOfTask(launchTask);
        }
        if (getInitialFocusState() != 1) {
            if (this.mNumStackTasks == 1) {
                this.mMinScrollP = 0.0f;
                this.mMaxScrollP = 0.0f;
                this.mInitialScrollP = 0.0f;
                return;
            }
            if (this.mUnfocusedCurveInterpolator == null) {
                Log.e("TaskStackLayoutAlgorithm", "ATC: update canceled for mUnfocusedCurveInterpolator is null");
                return;
            }
            int maxBottomOffset = this.mStackBottomOffset + this.mTaskRect.height();
            float maxBottomNormX = getNormalizedXFromUnfocusedY(maxBottomOffset, 1);
            this.mUnfocusedRange.offset(0.0f);
            if (Recents.getConfiguration().isLowRamDevice) {
                minScrollP = this.mTaskStackLowRamLayoutAlgorithm.getMinScrollP();
            } else {
                minScrollP = 0.0f;
            }
            this.mMinScrollP = minScrollP;
            if (Recents.getConfiguration().isLowRamDevice) {
                fMax = this.mTaskStackLowRamLayoutAlgorithm.getMaxScrollP(taskCount);
            } else {
                fMax = Math.max(this.mMinScrollP, (this.mNumStackTasks - 1) - Math.max(0.0f, this.mUnfocusedRange.getAbsoluteX(maxBottomNormX)));
            }
            this.mMaxScrollP = fMax;
            boolean scrollToFront = launchState.launchedFromHome || launchState.launchedFromPipApp || launchState.launchedWithNextPipApp || launchState.launchedViaDockGesture;
            if (launchState.launchedWithAltTab) {
                this.mInitialScrollP = Utilities.clamp(launchTaskIndex, this.mMinScrollP, this.mMaxScrollP);
                return;
            }
            if (0.0f <= lastScrollPPercent && lastScrollPPercent <= 1.0f) {
                this.mInitialScrollP = Utilities.mapRange(lastScrollPPercent, this.mMinScrollP, this.mMaxScrollP);
                return;
            }
            if (Recents.getConfiguration().isLowRamDevice) {
                this.mInitialScrollP = this.mTaskStackLowRamLayoutAlgorithm.getInitialScrollP(this.mNumStackTasks, scrollToFront);
                return;
            } else if (scrollToFront) {
                this.mInitialScrollP = Utilities.clamp(launchTaskIndex, this.mMinScrollP, this.mMaxScrollP);
                return;
            } else {
                float initialTopNormX = getNormalizedXFromUnfocusedY(this.mInitialTopOffset, 0);
                this.mInitialScrollP = Math.max(this.mMinScrollP, Math.min(this.mMaxScrollP, this.mNumStackTasks - 2) - Math.max(0.0f, this.mUnfocusedRange.getAbsoluteX(initialTopNormX)));
                return;
            }
        }
        int maxBottomOffset2 = this.mStackBottomOffset + this.mTaskRect.height();
        float maxBottomNormX2 = getNormalizedXFromFocusedY(maxBottomOffset2, 1);
        this.mFocusedRange.offset(0.0f);
        this.mMinScrollP = 0.0f;
        this.mMaxScrollP = Math.max(this.mMinScrollP, (this.mNumStackTasks - 1) - Math.max(0.0f, this.mFocusedRange.getAbsoluteX(maxBottomNormX2)));
        if (launchState.launchedFromHome || launchState.launchedFromPipApp || launchState.launchedWithNextPipApp) {
            this.mInitialScrollP = Utilities.clamp(launchTaskIndex, this.mMinScrollP, this.mMaxScrollP);
        } else {
            this.mInitialScrollP = Utilities.clamp(launchTaskIndex - 1, this.mMinScrollP, this.mMaxScrollP);
        }
    }

    public void setTaskOverridesForInitialState(TaskStack stack, boolean ignoreScrollToFront) {
        float[] initialNormX;
        RecentsActivityLaunchState launchState = Recents.getConfiguration().getLaunchState();
        this.mTaskIndexOverrideMap.clear();
        boolean scrollToFront = launchState.launchedFromHome || launchState.launchedFromPipApp || launchState.launchedWithNextPipApp || launchState.launchedViaDockGesture;
        if (getInitialFocusState() == 0 && this.mNumStackTasks > 1) {
            if (ignoreScrollToFront || (!launchState.launchedWithAltTab && !scrollToFront)) {
                float minBottomTaskNormX = getNormalizedXFromUnfocusedY(this.mSystemInsets.bottom + this.mInitialBottomOffset, 1);
                float maxBottomTaskNormX = getNormalizedXFromUnfocusedY((this.mFocusedTopPeekHeight + this.mTaskRect.height()) - this.mMinMargin, 0);
                if (this.mNumStackTasks <= 2) {
                    initialNormX = new float[]{Math.min(maxBottomTaskNormX, minBottomTaskNormX), getNormalizedXFromUnfocusedY(this.mFocusedTopPeekHeight, 0)};
                } else {
                    initialNormX = new float[]{minBottomTaskNormX, getNormalizedXFromUnfocusedY(this.mInitialTopOffset, 0)};
                }
                this.mUnfocusedRange.offset(0.0f);
                List<Task> tasks = stack.getTasks();
                int taskCount = tasks.size();
                for (int i = taskCount - 1; i >= 0; i--) {
                    int indexFromFront = (taskCount - i) - 1;
                    if (indexFromFront < initialNormX.length) {
                        float newTaskProgress = this.mInitialScrollP + this.mUnfocusedRange.getAbsoluteX(initialNormX[indexFromFront]);
                        this.mTaskIndexOverrideMap.put(tasks.get(i).key.id, Float.valueOf(newTaskProgress));
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public void addUnfocusedTaskOverride(Task task, float stackScroll) {
        if (this.mFocusState != 0) {
            this.mFocusedRange.offset(stackScroll);
            this.mUnfocusedRange.offset(stackScroll);
            float focusedRangeX = this.mFocusedRange.getNormalizedX(this.mTaskIndexMap.get(task.key.id));
            float focusedY = this.mFocusedCurveInterpolator.getInterpolation(focusedRangeX);
            float unfocusedRangeX = this.mUnfocusedCurveInterpolator.getX(focusedY);
            float unfocusedTaskProgress = this.mUnfocusedRange.getAbsoluteX(unfocusedRangeX) + stackScroll;
            if (Float.compare(focusedRangeX, unfocusedRangeX) != 0) {
                this.mTaskIndexOverrideMap.put(task.key.id, Float.valueOf(unfocusedTaskProgress));
            }
        }
    }

    public void addUnfocusedTaskOverride(TaskView taskView, float stackScroll) {
        this.mFocusedRange.offset(stackScroll);
        this.mUnfocusedRange.offset(stackScroll);
        Task task = taskView.getTask();
        int top = taskView.getTop() - this.mTaskRect.top;
        float focusedRangeX = getNormalizedXFromFocusedY(top, 0);
        float unfocusedRangeX = getNormalizedXFromUnfocusedY(top, 0);
        float unfocusedTaskProgress = this.mUnfocusedRange.getAbsoluteX(unfocusedRangeX) + stackScroll;
        if (Float.compare(focusedRangeX, unfocusedRangeX) != 0) {
            this.mTaskIndexOverrideMap.put(task.key.id, Float.valueOf(unfocusedTaskProgress));
        }
    }

    public void clearUnfocusedTaskOverrides() {
        this.mTaskIndexOverrideMap.clear();
    }

    public float updateFocusStateOnScroll(float lastTargetStackScroll, float targetStackScroll, float lastStackScroll) {
        if (targetStackScroll == lastStackScroll || Recents.getConfiguration().isLowRamDevice) {
            return targetStackScroll;
        }
        float deltaScroll = targetStackScroll - lastStackScroll;
        float deltaTargetScroll = targetStackScroll - lastTargetStackScroll;
        float newScroll = targetStackScroll;
        this.mUnfocusedRange.offset(targetStackScroll);
        for (int i = this.mTaskIndexOverrideMap.size() - 1; i >= 0; i--) {
            int taskId = this.mTaskIndexOverrideMap.keyAt(i);
            float x = this.mTaskIndexMap.get(taskId);
            float overrideX = this.mTaskIndexOverrideMap.get(taskId, Float.valueOf(0.0f)).floatValue();
            float newOverrideX = overrideX + deltaScroll;
            if (isInvalidOverrideX(x, overrideX, newOverrideX)) {
                this.mTaskIndexOverrideMap.removeAt(i);
            } else if ((overrideX >= x && deltaScroll <= 0.0f) || (overrideX <= x && deltaScroll >= 0.0f)) {
                this.mTaskIndexOverrideMap.put(taskId, Float.valueOf(newOverrideX));
            } else {
                newScroll = lastStackScroll;
                float newOverrideX2 = overrideX - deltaTargetScroll;
                if (isInvalidOverrideX(x, overrideX, newOverrideX2)) {
                    this.mTaskIndexOverrideMap.removeAt(i);
                } else {
                    this.mTaskIndexOverrideMap.put(taskId, Float.valueOf(newOverrideX2));
                }
            }
        }
        return newScroll;
    }

    private boolean isInvalidOverrideX(float x, float overrideX, float newOverrideX) {
        boolean outOfBounds = this.mUnfocusedRange.getNormalizedX(newOverrideX) < 0.0f || this.mUnfocusedRange.getNormalizedX(newOverrideX) > 1.0f;
        if (outOfBounds) {
            return true;
        }
        if (overrideX < x || x < newOverrideX) {
            return overrideX <= x && x <= newOverrideX;
        }
        return true;
    }

    public int getInitialFocusState() {
        RecentsActivityLaunchState launchState = Recents.getConfiguration().getLaunchState();
        Recents.getDebugFlags();
        if (launchState.launchedWithAltTab) {
            return 1;
        }
        return 0;
    }

    public Rect getStackActionButtonRect() {
        return useGridLayout() ? this.mTaskGridLayoutAlgorithm.getStackActionButtonRect() : this.mStackActionButtonRect;
    }

    public TaskViewTransform getBackOfStackTransform() {
        return this.mBackOfStackTransform;
    }

    public TaskViewTransform getFrontOfStackTransform() {
        return this.mFrontOfStackTransform;
    }

    public boolean isInitialized() {
        return !this.mStackRect.isEmpty();
    }

    public VisibilityReport computeStackVisibilityReport(ArrayList<Task> tasks) {
        int numVisibleThumbnails;
        int numVisibleTasks;
        int i;
        if (useGridLayout()) {
            return this.mTaskGridLayoutAlgorithm.computeStackVisibilityReport(tasks);
        }
        if (Recents.getConfiguration().isLowRamDevice) {
            return this.mTaskStackLowRamLayoutAlgorithm.computeStackVisibilityReport(tasks);
        }
        if (tasks.size() <= 1) {
            return new VisibilityReport(1, 1);
        }
        TaskViewTransform tmpTransform = new TaskViewTransform();
        Range currentRange = ((float) getInitialFocusState()) > 0.0f ? this.mFocusedRange : this.mUnfocusedRange;
        currentRange.offset(this.mInitialScrollP);
        int taskBarHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.recents_task_view_header_height);
        int i2 = tasks.size() - 1;
        int numVisibleTasks2 = 0;
        int numVisibleThumbnails2 = 0;
        float prevScreenY = 2.14748365E9f;
        while (true) {
            int i3 = i2;
            if (i3 < 0) {
                numVisibleThumbnails = numVisibleThumbnails2;
                numVisibleTasks = numVisibleTasks2;
                break;
            }
            Task task = tasks.get(i3);
            float taskProgress = getStackScrollForTask(task);
            if (!currentRange.isInRange(taskProgress)) {
                i = i3;
            } else {
                float taskProgress2 = taskProgress;
                i = i3;
                numVisibleThumbnails = numVisibleThumbnails2;
                getStackTransform(taskProgress, taskProgress, this.mInitialScrollP, this.mFocusState, tmpTransform, null, false, false);
                float screenY = tmpTransform.rect.top;
                boolean hasVisibleThumbnail = prevScreenY - screenY > ((float) taskBarHeight);
                if (hasVisibleThumbnail) {
                    numVisibleThumbnails2 = numVisibleThumbnails + 1;
                    numVisibleTasks2++;
                    prevScreenY = screenY;
                } else {
                    int j = i;
                    while (j >= 0) {
                        float taskProgress3 = getStackScrollForTask(tasks.get(j));
                        if (!currentRange.isInRange(taskProgress3)) {
                            break;
                        }
                        numVisibleTasks2++;
                        j--;
                        taskProgress2 = taskProgress3;
                    }
                    numVisibleTasks = numVisibleTasks2;
                    break;
                }
            }
            i2 = i - 1;
        }
        return new VisibilityReport(numVisibleTasks, numVisibleThumbnails);
    }

    public TaskViewTransform getStackTransform(Task task, float stackScroll, TaskViewTransform transformOut, TaskViewTransform frontTransform) {
        return getStackTransform(task, stackScroll, this.mFocusState, transformOut, frontTransform, false, false);
    }

    public TaskViewTransform getStackTransform(Task task, float stackScroll, TaskViewTransform transformOut, TaskViewTransform frontTransform, boolean ignoreTaskOverrides) {
        return getStackTransform(task, stackScroll, this.mFocusState, transformOut, frontTransform, false, ignoreTaskOverrides);
    }

    public TaskViewTransform getStackTransform(Task task, float stackScroll, int focusState, TaskViewTransform transformOut, TaskViewTransform frontTransform, boolean forceUpdate, boolean ignoreTaskOverrides) {
        if (useGridLayout()) {
            int taskIndex = this.mTaskIndexMap.get(task.key.id);
            int taskCount = this.mTaskIndexMap.size();
            this.mTaskGridLayoutAlgorithm.getTransform(taskIndex, taskCount, transformOut, this);
            return transformOut;
        }
        if (Recents.getConfiguration().isLowRamDevice) {
            if (task == null) {
                transformOut.reset();
                return transformOut;
            }
            int taskIndex2 = this.mTaskIndexMap.get(task.key.id);
            this.mTaskStackLowRamLayoutAlgorithm.getTransform(taskIndex2, stackScroll, transformOut, this.mNumStackTasks, this);
            return transformOut;
        }
        int nonOverrideTaskProgress = this.mTaskIndexMap.get(task.key.id, -1);
        if (task == null || nonOverrideTaskProgress == -1) {
            transformOut.reset();
            return transformOut;
        }
        float taskProgress = ignoreTaskOverrides ? nonOverrideTaskProgress : getStackScrollForTask(task);
        getStackTransform(taskProgress, nonOverrideTaskProgress, stackScroll, focusState, transformOut, frontTransform, false, forceUpdate);
        return transformOut;
    }

    public TaskViewTransform getStackTransformScreenCoordinates(Task task, float stackScroll, TaskViewTransform transformOut, TaskViewTransform frontTransform, Rect windowOverrideRect) {
        TaskViewTransform transform = getStackTransform(task, stackScroll, this.mFocusState, transformOut, frontTransform, true, false);
        return transformToScreenCoordinates(transform, windowOverrideRect);
    }

    TaskViewTransform transformToScreenCoordinates(TaskViewTransform transformOut, Rect windowOverrideRect) {
        Rect windowRect = windowOverrideRect != null ? windowOverrideRect : Recents.getSystemServices().getWindowRect();
        transformOut.rect.offset(windowRect.left, windowRect.top);
        if (useGridLayout()) {
            transformOut.rect.offset(0.0f, this.mTitleBarHeight);
        }
        return transformOut;
    }

    public void getStackTransform(float taskProgress, float nonOverrideTaskProgress, float stackScroll, int focusState, TaskViewTransform transformOut, TaskViewTransform frontTransform, boolean ignoreSingleTaskCase, boolean forceUpdate) {
        int y;
        float tmpP;
        float dimAlpha;
        float z;
        Recents.getSystemServices();
        this.mUnfocusedRange.offset(stackScroll);
        this.mFocusedRange.offset(stackScroll);
        boolean unfocusedVisible = this.mUnfocusedRange.isInRange(taskProgress);
        boolean focusedVisible = this.mFocusedRange.isInRange(taskProgress);
        if (!forceUpdate && !unfocusedVisible && !focusedVisible) {
            transformOut.reset();
            return;
        }
        this.mUnfocusedRange.offset(stackScroll);
        this.mFocusedRange.offset(stackScroll);
        float unfocusedRangeX = this.mUnfocusedRange.getNormalizedX(taskProgress);
        float focusedRangeX = this.mFocusedRange.getNormalizedX(taskProgress);
        float boundedStackScroll = Utilities.clamp(stackScroll, this.mMinScrollP, this.mMaxScrollP);
        this.mUnfocusedRange.offset(boundedStackScroll);
        this.mFocusedRange.offset(boundedStackScroll);
        float boundedScrollUnfocusedRangeX = this.mUnfocusedRange.getNormalizedX(taskProgress);
        float boundedScrollUnfocusedNonOverrideRangeX = this.mUnfocusedRange.getNormalizedX(nonOverrideTaskProgress);
        float lowerBoundedStackScroll = Utilities.clamp(stackScroll, -3.4028235E38f, this.mMaxScrollP);
        this.mUnfocusedRange.offset(lowerBoundedStackScroll);
        this.mFocusedRange.offset(lowerBoundedStackScroll);
        float lowerBoundedUnfocusedRangeX = this.mUnfocusedRange.getNormalizedX(taskProgress);
        float lowerBoundedFocusedRangeX = this.mFocusedRange.getNormalizedX(taskProgress);
        int x = (this.mStackRect.width() - this.mTaskRect.width()) / 2;
        if (this.mNumStackTasks == 1 && !ignoreSingleTaskCase) {
            float tmpP2 = (this.mMinScrollP - stackScroll) / this.mNumStackTasks;
            int centerYOffset = (this.mStackRect.top - this.mTaskRect.top) + (((this.mStackRect.height() - this.mSystemInsets.bottom) - this.mTaskRect.height()) / 2);
            y = getYForDeltaP(tmpP2, 0.0f) + centerYOffset;
            z = this.mMaxTranslationZ;
            tmpP = 1.0f;
            dimAlpha = 0.0f;
        } else {
            int unfocusedY = (int) ((1.0f - this.mUnfocusedCurveInterpolator.getInterpolation(unfocusedRangeX)) * this.mStackRect.height());
            int focusedY = (int) ((1.0f - this.mFocusedCurveInterpolator.getInterpolation(focusedRangeX)) * this.mStackRect.height());
            float unfocusedDim = this.mUnfocusedDimCurveInterpolator.getInterpolation(lowerBoundedUnfocusedRangeX);
            float focusedDim = this.mFocusedDimCurveInterpolator.getInterpolation(lowerBoundedFocusedRangeX);
            if (this.mNumStackTasks <= 2 && nonOverrideTaskProgress == 0.0f) {
                if (boundedScrollUnfocusedRangeX < 0.5f) {
                    float offset = this.mUnfocusedDimCurveInterpolator.getInterpolation(0.5f);
                    unfocusedDim = (unfocusedDim - offset) * (0.25f / (0.25f - offset));
                } else {
                    unfocusedDim = 0.0f;
                }
            }
            y = (this.mStackRect.top - this.mTaskRect.top) + ((int) Utilities.mapRange(focusState, unfocusedY, focusedY));
            float z2 = Utilities.mapRange(Utilities.clamp01(boundedScrollUnfocusedNonOverrideRangeX), this.mMinTranslationZ, this.mMaxTranslationZ);
            float dimAlpha2 = Utilities.mapRange(focusState, unfocusedDim, focusedDim);
            tmpP = Utilities.mapRange(Utilities.clamp01(boundedScrollUnfocusedRangeX), 0.0f, 2.0f);
            dimAlpha = dimAlpha2;
            z = z2;
        }
        float viewOutlineAlpha = tmpP;
        transformOut.scale = 1.0f;
        transformOut.alpha = 1.0f;
        transformOut.translationZ = z;
        transformOut.dimAlpha = dimAlpha;
        transformOut.viewOutlineAlpha = viewOutlineAlpha;
        transformOut.rect.set(this.mTaskRect);
        transformOut.rect.offset(x, y);
        Utilities.scaleRectAboutCenter(transformOut.rect, transformOut.scale);
        transformOut.visible = transformOut.rect.top < ((float) this.mStackRect.bottom) && (frontTransform == null || transformOut.rect.top != frontTransform.rect.top);
    }

    public Rect getUntransformedTaskViewBounds() {
        return new Rect(this.mTaskRect);
    }

    float getStackScrollForTask(Task t) {
        Float overrideP = this.mTaskIndexOverrideMap.get(t.key.id, null);
        if (Recents.getConfiguration().isLowRamDevice || overrideP == null) {
            return this.mTaskIndexMap.get(t.key.id, 0);
        }
        return overrideP.floatValue();
    }

    float getStackScrollForTaskIgnoreOverrides(Task t) {
        return this.mTaskIndexMap.get(t.key.id, 0);
    }

    float getStackScrollForTaskAtInitialOffset(Task t) {
        if (Recents.getConfiguration().isLowRamDevice) {
            RecentsActivityLaunchState launchState = Recents.getConfiguration().getLaunchState();
            return this.mTaskStackLowRamLayoutAlgorithm.getInitialScrollP(this.mNumStackTasks, launchState.launchedFromHome || launchState.launchedFromPipApp || launchState.launchedWithNextPipApp);
        }
        float normX = getNormalizedXFromUnfocusedY(this.mInitialTopOffset, 0);
        this.mUnfocusedRange.offset(0.0f);
        return Utilities.clamp(this.mTaskIndexMap.get(t.key.id, 0) - Math.max(0.0f, this.mUnfocusedRange.getAbsoluteX(normX)), this.mMinScrollP, this.mMaxScrollP);
    }

    public float getDeltaPForY(int downY, int y) {
        if (Recents.getConfiguration().isLowRamDevice) {
            return this.mTaskStackLowRamLayoutAlgorithm.scrollToPercentage(downY - y);
        }
        float deltaP = ((y - downY) / this.mStackRect.height()) * this.mUnfocusedCurveInterpolator.getArcLength();
        return -deltaP;
    }

    public int getYForDeltaP(float downScrollP, float p) {
        if (Recents.getConfiguration().isLowRamDevice) {
            return this.mTaskStackLowRamLayoutAlgorithm.percentageToScroll(downScrollP - p);
        }
        int y = (int) ((p - downScrollP) * this.mStackRect.height() * (1.0f / this.mUnfocusedCurveInterpolator.getArcLength()));
        return -y;
    }

    public void getTaskStackBounds(Rect displayRect, Rect windowRect, int topInset, int leftInset, int rightInset, Rect taskStackBounds) {
        taskStackBounds.set(windowRect.left + leftInset, windowRect.top + topInset, windowRect.right - rightInset, windowRect.bottom);
        int sideMargin = getScaleForExtent(windowRect, displayRect, this.mBaseSideMargin, this.mMinMargin, 0);
        int targetStackWidth = taskStackBounds.width() - (2 * sideMargin);
        if (Utilities.getAppConfiguration(this.mContext).orientation == 2) {
            Rect portraitDisplayRect = new Rect(0, 0, Math.min(displayRect.width(), displayRect.height()), Math.max(displayRect.width(), displayRect.height()));
            int portraitSideMargin = getScaleForExtent(portraitDisplayRect, portraitDisplayRect, this.mBaseSideMargin, this.mMinMargin, 0);
            targetStackWidth = Math.min(targetStackWidth, portraitDisplayRect.width() - (2 * portraitSideMargin));
        }
        int portraitSideMargin2 = taskStackBounds.width();
        taskStackBounds.inset((portraitSideMargin2 - targetStackWidth) / 2, 0);
    }

    public static int getDimensionForDevice(Context ctx, int phoneResId, int tabletResId, int xlargeTabletResId, int gridLayoutResId) {
        return getDimensionForDevice(ctx, phoneResId, phoneResId, tabletResId, tabletResId, xlargeTabletResId, xlargeTabletResId, gridLayoutResId);
    }

    public static int getDimensionForDevice(Context ctx, int phonePortResId, int phoneLandResId, int tabletPortResId, int tabletLandResId, int xlargeTabletPortResId, int xlargeTabletLandResId, int gridLayoutResId) {
        RecentsConfiguration config = Recents.getConfiguration();
        Resources res = ctx.getResources();
        boolean isLandscape = Utilities.getAppConfiguration(ctx).orientation == 2;
        if (config.isGridEnabled) {
            return res.getDimensionPixelSize(gridLayoutResId);
        }
        if (config.isXLargeScreen) {
            return res.getDimensionPixelSize(isLandscape ? xlargeTabletLandResId : xlargeTabletPortResId);
        }
        if (config.isLargeScreen) {
            return res.getDimensionPixelSize(isLandscape ? tabletLandResId : tabletPortResId);
        }
        return res.getDimensionPixelSize(isLandscape ? phoneLandResId : phonePortResId);
    }

    private float getNormalizedXFromUnfocusedY(float y, int fromSide) {
        float offset;
        if (fromSide == 0) {
            offset = this.mStackRect.height() - y;
        } else {
            offset = y;
        }
        float offsetPct = offset / this.mStackRect.height();
        return this.mUnfocusedCurveInterpolator.getX(offsetPct);
    }

    private float getNormalizedXFromFocusedY(float y, int fromSide) {
        float offset;
        if (fromSide == 0) {
            offset = this.mStackRect.height() - y;
        } else {
            offset = y;
        }
        float offsetPct = offset / this.mStackRect.height();
        return this.mFocusedCurveInterpolator.getX(offsetPct);
    }

    private Path constructFocusedCurve() {
        float topPeekHeightPct = this.mFocusedTopPeekHeight / this.mStackRect.height();
        float bottomPeekHeightPct = (this.mStackBottomOffset + this.mFocusedBottomPeekHeight) / this.mStackRect.height();
        float minBottomPeekHeightPct = ((this.mFocusedTopPeekHeight + this.mTaskRect.height()) - this.mMinMargin) / this.mStackRect.height();
        Path p = new Path();
        p.moveTo(0.0f, 1.0f);
        p.lineTo(0.5f, 1.0f - topPeekHeightPct);
        p.lineTo(1.0f - (0.5f / this.mFocusedRange.relativeMax), Math.max(1.0f - minBottomPeekHeightPct, bottomPeekHeightPct));
        p.lineTo(1.0f, 0.0f);
        return p;
    }

    private Path constructUnfocusedCurve() {
        float topPeekHeightPct = this.mFocusedTopPeekHeight / this.mStackRect.height();
        float slope = ((1.0f - topPeekHeightPct) - 0.975f) / (0.5f - 0.4f);
        float b = 1.0f - (slope * 0.4f);
        float cpoint2Y = (slope * 0.65f) + b;
        Path p = new Path();
        p.moveTo(0.0f, 1.0f);
        p.cubicTo(0.0f, 1.0f, 0.4f, 0.975f, 0.5f, 1.0f - topPeekHeightPct);
        p.cubicTo(0.5f, 1.0f - topPeekHeightPct, 0.65f, cpoint2Y, 1.0f, 0.0f);
        return p;
    }

    private Path constructFocusedDimCurve() {
        Path p = new Path();
        p.moveTo(0.0f, 0.25f);
        p.lineTo(0.5f, 0.0f);
        p.lineTo(0.5f + (0.5f / this.mFocusedRange.relativeMax), 0.25f);
        p.lineTo(1.0f, 0.25f);
        return p;
    }

    private Path constructUnfocusedDimCurve() {
        float focusX = getNormalizedXFromUnfocusedY(this.mInitialTopOffset, 0);
        float cpoint2X = ((1.0f - focusX) / 2.0f) + focusX;
        Path p = new Path();
        p.moveTo(0.0f, 0.25f);
        p.cubicTo(focusX * 0.5f, 0.25f, focusX * 0.75f, 0.1875f, focusX, 0.0f);
        p.cubicTo(cpoint2X, 0.0f, cpoint2X, 0.15f, 1.0f, 0.15f);
        return p;
    }

    private int getScaleForExtent(Rect instance, Rect other, int value, int minValue, int extent) {
        if (extent == 0) {
            float scale = Utilities.clamp01(instance.width() / other.width());
            return Math.max(minValue, (int) (value * scale));
        }
        if (extent == 1) {
            float scale2 = Utilities.clamp01(instance.height() / other.height());
            return Math.max(minValue, (int) (value * scale2));
        }
        return value;
    }

    private void updateFrontBackTransforms() {
        if (this.mStackRect.isEmpty()) {
            return;
        }
        if (Recents.getConfiguration().isLowRamDevice) {
            this.mTaskStackLowRamLayoutAlgorithm.getBackOfStackTransform(this.mBackOfStackTransform, this);
            this.mTaskStackLowRamLayoutAlgorithm.getFrontOfStackTransform(this.mFrontOfStackTransform, this);
            return;
        }
        float min = Utilities.mapRange(this.mFocusState, this.mUnfocusedRange.relativeMin, this.mFocusedRange.relativeMin);
        float max = Utilities.mapRange(this.mFocusState, this.mUnfocusedRange.relativeMax, this.mFocusedRange.relativeMax);
        getStackTransform(min, min, 0.0f, this.mFocusState, this.mBackOfStackTransform, null, true, true);
        getStackTransform(max, max, 0.0f, this.mFocusState, this.mFrontOfStackTransform, null, true, true);
        this.mBackOfStackTransform.visible = true;
        this.mFrontOfStackTransform.visible = true;
    }

    public Rect getTaskRect() {
        if (Recents.getConfiguration().isLowRamDevice) {
            return this.mTaskStackLowRamLayoutAlgorithm.getTaskRect();
        }
        return useGridLayout() ? this.mTaskGridLayoutAlgorithm.getTaskGridRect() : this.mTaskRect;
    }

    public void dump(String prefix, PrintWriter writer) {
        String innerPrefix = prefix + "  ";
        writer.print(prefix);
        writer.print("TaskStackLayoutAlgorithm");
        writer.write(" numStackTasks=");
        writer.print(this.mNumStackTasks);
        writer.println();
        writer.print(innerPrefix);
        writer.print("insets=");
        writer.print(Utilities.dumpRect(this.mSystemInsets));
        writer.print(" stack=");
        writer.print(Utilities.dumpRect(this.mStackRect));
        writer.print(" task=");
        writer.print(Utilities.dumpRect(this.mTaskRect));
        writer.print(" actionButton=");
        writer.print(Utilities.dumpRect(this.mStackActionButtonRect));
        writer.println();
        writer.print(innerPrefix);
        writer.print("minScroll=");
        writer.print(this.mMinScrollP);
        writer.print(" maxScroll=");
        writer.print(this.mMaxScrollP);
        writer.print(" initialScroll=");
        writer.print(this.mInitialScrollP);
        writer.println();
        writer.print(innerPrefix);
        writer.print("focusState=");
        writer.print(this.mFocusState);
        writer.println();
        if (this.mTaskIndexOverrideMap.size() > 0) {
            for (int i = this.mTaskIndexOverrideMap.size() - 1; i >= 0; i--) {
                int taskId = this.mTaskIndexOverrideMap.keyAt(i);
                float x = this.mTaskIndexMap.get(taskId);
                float overrideX = this.mTaskIndexOverrideMap.get(taskId, Float.valueOf(0.0f)).floatValue();
                writer.print(innerPrefix);
                writer.print("taskId= ");
                writer.print(taskId);
                writer.print(" x= ");
                writer.print(x);
                writer.print(" overrideX= ");
                writer.print(overrideX);
                writer.println();
            }
        }
    }
}
