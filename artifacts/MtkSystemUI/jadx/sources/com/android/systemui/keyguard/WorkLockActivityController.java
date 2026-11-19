package com.android.systemui.keyguard;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.IActivityManager;
import android.app.ProfilerInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.recents.misc.SysUiTaskStackChangeListener;
import com.android.systemui.shared.system.ActivityManagerWrapper;

/* JADX INFO: loaded from: classes.dex */
public class WorkLockActivityController {
    private static final String TAG = WorkLockActivityController.class.getSimpleName();
    private final Context mContext;
    private final IActivityManager mIam;
    private final SysUiTaskStackChangeListener mLockListener;

    public WorkLockActivityController(Context context) {
        this(context, ActivityManagerWrapper.getInstance(), ActivityManager.getService());
    }

    @VisibleForTesting
    WorkLockActivityController(Context context, ActivityManagerWrapper am, IActivityManager iAm) {
        this.mLockListener = new SysUiTaskStackChangeListener() { // from class: com.android.systemui.keyguard.WorkLockActivityController.1
            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public void onTaskProfileLocked(int taskId, int userId) {
                WorkLockActivityController.this.startWorkChallengeInTask(taskId, userId);
            }
        };
        this.mContext = context;
        this.mIam = iAm;
        am.registerTaskStackListener(this.mLockListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startWorkChallengeInTask(int taskId, int userId) {
        ActivityManager.TaskDescription taskDescription = null;
        try {
            taskDescription = this.mIam.getTaskDescription(taskId);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to get description for task=" + taskId);
        }
        Intent intent = new Intent("android.app.action.CONFIRM_DEVICE_CREDENTIAL_WITH_USER").setComponent(new ComponentName(this.mContext, (Class<?>) WorkLockActivity.class)).putExtra("android.intent.extra.USER_ID", userId).putExtra("com.android.systemui.keyguard.extra.TASK_DESCRIPTION", taskDescription).addFlags(67239936);
        ActivityOptions options = ActivityOptions.makeBasic();
        options.setLaunchTaskId(taskId);
        options.setTaskOverlay(true, false);
        int result = startActivityAsUser(intent, options.toBundle(), -2);
        if (!ActivityManager.isStartResultSuccessful(result)) {
            try {
                this.mIam.removeTask(taskId);
            } catch (RemoteException e2) {
                Log.w(TAG, "Failed to get description for task=" + taskId);
            }
        }
    }

    private int startActivityAsUser(Intent intent, Bundle options, int userId) {
        try {
            try {
                return this.mIam.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getBasePackageName(), intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, options, userId);
            } catch (RemoteException e) {
                return -96;
            } catch (Exception e2) {
                return -96;
            }
        } catch (RemoteException e3) {
        } catch (Exception e4) {
        }
    }
}
