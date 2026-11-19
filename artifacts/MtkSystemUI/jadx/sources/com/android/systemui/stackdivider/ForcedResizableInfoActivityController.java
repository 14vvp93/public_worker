package com.android.systemui.stackdivider;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.UserHandle;
import android.util.ArraySet;
import com.android.systemui.R;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.events.activity.AppTransitionFinishedEvent;
import com.android.systemui.recents.events.component.ShowUserToastEvent;
import com.android.systemui.recents.misc.SysUiTaskStackChangeListener;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.stackdivider.events.StartedDragingEvent;
import com.android.systemui.stackdivider.events.StoppedDragingEvent;

/* JADX INFO: loaded from: classes.dex */
public class ForcedResizableInfoActivityController {
    private final Context mContext;
    private boolean mDividerDraging;
    private final Handler mHandler = new Handler();
    private final ArraySet<PendingTaskRecord> mPendingTasks = new ArraySet<>();
    private final ArraySet<String> mPackagesShownInSession = new ArraySet<>();
    private final Runnable mTimeoutRunnable = new Runnable() { // from class: com.android.systemui.stackdivider.ForcedResizableInfoActivityController.1
        @Override // java.lang.Runnable
        public void run() {
            ForcedResizableInfoActivityController.this.showPending();
        }
    };

    private class PendingTaskRecord {
        int reason;
        int taskId;

        PendingTaskRecord(int taskId, int reason) {
            this.taskId = taskId;
            this.reason = reason;
        }
    }

    public ForcedResizableInfoActivityController(Context context) {
        this.mContext = context;
        EventBus.getDefault().register(this);
        ActivityManagerWrapper.getInstance().registerTaskStackListener(new SysUiTaskStackChangeListener() { // from class: com.android.systemui.stackdivider.ForcedResizableInfoActivityController.2
            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public void onActivityForcedResizable(String packageName, int taskId, int reason) {
                ForcedResizableInfoActivityController.this.activityForcedResizable(packageName, taskId, reason);
            }

            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public void onActivityDismissingDockedStack() {
                ForcedResizableInfoActivityController.this.activityDismissingDockedStack();
            }

            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public void onActivityLaunchOnSecondaryDisplayFailed() {
                ForcedResizableInfoActivityController.this.activityLaunchOnSecondaryDisplayFailed();
            }
        });
    }

    public void notifyDockedStackExistsChanged(boolean exists) {
        if (!exists) {
            this.mPackagesShownInSession.clear();
        }
    }

    public final void onBusEvent(AppTransitionFinishedEvent event) {
        if (!this.mDividerDraging) {
            showPending();
        }
    }

    public final void onBusEvent(StartedDragingEvent event) {
        this.mDividerDraging = true;
        this.mHandler.removeCallbacks(this.mTimeoutRunnable);
    }

    public final void onBusEvent(StoppedDragingEvent event) {
        this.mDividerDraging = false;
        showPending();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void activityForcedResizable(String packageName, int taskId, int reason) {
        if (debounce(packageName)) {
            return;
        }
        this.mPendingTasks.add(new PendingTaskRecord(taskId, reason));
        postTimeout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void activityDismissingDockedStack() {
        EventBus.getDefault().send(new ShowUserToastEvent(R.string.dock_non_resizeble_failed_to_dock_text, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void activityLaunchOnSecondaryDisplayFailed() {
        EventBus.getDefault().send(new ShowUserToastEvent(R.string.activity_launch_on_secondary_display_failed_text, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPending() {
        this.mHandler.removeCallbacks(this.mTimeoutRunnable);
        for (int i = this.mPendingTasks.size() - 1; i >= 0; i--) {
            PendingTaskRecord pendingRecord = this.mPendingTasks.valueAt(i);
            Intent intent = new Intent(this.mContext, (Class<?>) ForcedResizableInfoActivity.class);
            ActivityOptions options = ActivityOptions.makeBasic();
            options.setLaunchTaskId(pendingRecord.taskId);
            options.setTaskOverlay(true, true);
            intent.putExtra("extra_forced_resizeable_reason", pendingRecord.reason);
            this.mContext.startActivityAsUser(intent, options.toBundle(), UserHandle.CURRENT);
        }
        this.mPendingTasks.clear();
    }

    private void postTimeout() {
        this.mHandler.removeCallbacks(this.mTimeoutRunnable);
        this.mHandler.postDelayed(this.mTimeoutRunnable, 1000L);
    }

    private boolean debounce(String packageName) {
        if (packageName == null) {
            return false;
        }
        if ("com.android.systemui".equals(packageName)) {
            return true;
        }
        boolean debounce = this.mPackagesShownInSession.contains(packageName);
        this.mPackagesShownInSession.add(packageName);
        return debounce;
    }
}
