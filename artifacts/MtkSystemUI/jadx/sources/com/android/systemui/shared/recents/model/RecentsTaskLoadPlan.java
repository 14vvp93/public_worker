package com.android.systemui.shared.recents.model;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseBooleanArray;
import com.android.systemui.shared.R;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RecentsTaskLoadPlan {
    private final Context mContext;
    private final KeyguardManager mKeyguardManager;
    private List<ActivityManager.RecentTaskInfo> mRawTasks;
    private TaskStack mStack;
    private final SparseBooleanArray mTmpLockedUsers = new SparseBooleanArray();

    public static class Options {
        public int runningTaskId = -1;
        public boolean loadIcons = true;
        public boolean loadThumbnails = false;
        public boolean onlyLoadForCache = false;
        public boolean onlyLoadPausedActivities = false;
        public int numVisibleTasks = 0;
        public int numVisibleTaskThumbnails = 0;
    }

    public static class PreloadOptions {
        public boolean loadTitles = true;
    }

    public RecentsTaskLoadPlan(Context context) {
        this.mContext = context;
        this.mKeyguardManager = (KeyguardManager) context.getSystemService("keyguard");
    }

    public void preloadPlan(PreloadOptions opts, RecentsTaskLoader loader, int runningTaskId, int currentUserId) {
        int taskCount;
        boolean z;
        Drawable andUpdateActivityIcon;
        List hiddenList;
        Resources res = this.mContext.getResources();
        List hiddenList2 = Arrays.asList(res.getStringArray(R.array.recents_task_hidden_list));
        ArrayList<Task> allTasks = new ArrayList<>();
        if (this.mRawTasks == null) {
            this.mRawTasks = ActivityManagerWrapper.getInstance().getRecentTasks(ActivityManager.getMaxRecentTasksStatic(), currentUserId);
            Collections.reverse(this.mRawTasks);
        }
        int taskCount2 = this.mRawTasks.size();
        int i = 0;
        while (i < taskCount2) {
            ActivityManager.RecentTaskInfo t = this.mRawTasks.get(i);
            ComponentName sourceComponent = t.origActivity != null ? t.origActivity : t.realActivity;
            int windowingMode = t.configuration.windowConfiguration.getWindowingMode();
            Resources res2 = res;
            int j = windowingMode;
            Task.TaskKey taskKey = new Task.TaskKey(t.persistentId, windowingMode, t.baseIntent, sourceComponent, t.userId, t.lastActiveTime);
            boolean isFreeformTask = j == 5;
            boolean isStackTask = !isFreeformTask;
            boolean isLaunchTarget = taskKey.id == runningTaskId;
            ActivityInfo info = loader.getAndUpdateActivityInfo(taskKey);
            if (info == null) {
                hiddenList = hiddenList2;
                taskCount = taskCount2;
            } else {
                boolean alreadyExist = false;
                int j2 = 0;
                while (true) {
                    int j3 = j2;
                    int windowingMode2 = j;
                    if (j3 >= allTasks.size()) {
                        taskCount = taskCount2;
                        break;
                    }
                    Task task = allTasks.get(j3);
                    taskCount = taskCount2;
                    if (task.getTopComponent().equals(sourceComponent)) {
                        alreadyExist = true;
                        break;
                    } else {
                        j2 = j3 + 1;
                        j = windowingMode2;
                        taskCount2 = taskCount;
                    }
                }
                if (!alreadyExist && (sourceComponent == null || !hiddenList2.contains(sourceComponent.getPackageName()))) {
                    String title = opts.loadTitles ? loader.getAndUpdateActivityTitle(taskKey, t.taskDescription) : "";
                    String titleDescription = opts.loadTitles ? loader.getAndUpdateContentDescription(taskKey, t.taskDescription) : "";
                    if (isStackTask) {
                        z = false;
                        andUpdateActivityIcon = loader.getAndUpdateActivityIcon(taskKey, t.taskDescription, false);
                    } else {
                        z = false;
                        andUpdateActivityIcon = null;
                    }
                    Drawable icon = andUpdateActivityIcon;
                    ThumbnailData thumbnail = loader.getAndUpdateThumbnail(taskKey, z, z);
                    int activityColor = loader.getActivityPrimaryColor(t.taskDescription);
                    int backgroundColor = loader.getActivityBackgroundColor(t.taskDescription);
                    boolean isSystemApp = (info == null || (info.applicationInfo.flags & 1) == 0) ? false : true;
                    hiddenList = hiddenList2;
                    if (this.mTmpLockedUsers.indexOfKey(t.userId) < 0) {
                        this.mTmpLockedUsers.put(t.userId, this.mKeyguardManager.isDeviceLocked(t.userId));
                    }
                    boolean isLocked = this.mTmpLockedUsers.get(t.userId);
                    if (t.realActivity.getClassName().equals(ActivityManagerWrapper.currentDockActivity)) {
                        Log.d("XYG", "addTask equals currentDockActivity -- skip: " + ActivityManagerWrapper.currentDockActivity);
                    } else {
                        Task task2 = new Task(taskKey, icon, thumbnail, title, titleDescription, activityColor, backgroundColor, isLaunchTarget, isStackTask, isSystemApp, t.supportsSplitScreenMultiWindow, t.taskDescription, t.resizeMode, t.topActivity, isLocked);
                        Log.d("XYG", "addTask " + title + " / " + taskKey + " / " + t.realActivity.getClassName());
                        allTasks.add(task2);
                    }
                } else {
                    hiddenList = hiddenList2;
                }
            }
            i++;
            res = res2;
            taskCount2 = taskCount;
            hiddenList2 = hiddenList;
        }
        this.mStack = new TaskStack();
        this.mStack.setTasks((List<Task>) allTasks, false);
    }

    public void executePlan(Options opts, RecentsTaskLoader loader) {
        this.mContext.getResources();
        ArrayList<Task> tasks = this.mStack.getTasks();
        int taskCount = tasks.size();
        int i = 0;
        while (i < taskCount) {
            Task task = tasks.get(i);
            Task.TaskKey taskKey = task.key;
            boolean isRunningTask = task.key.id == opts.runningTaskId;
            boolean isVisibleTask = i >= taskCount - opts.numVisibleTasks;
            boolean isVisibleThumbnail = i >= taskCount - opts.numVisibleTaskThumbnails;
            if (!opts.onlyLoadPausedActivities || !isRunningTask) {
                if (opts.loadIcons && ((isRunningTask || isVisibleTask) && task.icon == null)) {
                    task.icon = loader.getAndUpdateActivityIcon(taskKey, task.taskDescription, true);
                }
                if (opts.loadThumbnails && isVisibleThumbnail) {
                    task.thumbnail = loader.getAndUpdateThumbnail(taskKey, true, true);
                }
            }
            i++;
        }
    }

    public TaskStack getTaskStack() {
        return this.mStack;
    }

    public boolean hasTasks() {
        return this.mStack != null && this.mStack.getTaskCount() > 0;
    }
}
