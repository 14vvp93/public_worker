package com.hcn.auto_compat.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.UserHandle;
import android.util.Log;
import com.hcn.common.misc.LogUtils;

/* JADX INFO: loaded from: classes.dex */
public class CompatUtils {
    private static final String TAG = "CompatUtils";

    public static ComponentName startServiceAsUser(Context context, Intent intent, UserHandle userHandle) {
        try {
            return (ComponentName) Class.forName("android.content.ContextWrapper").getMethod("startServiceAsUser", Intent.class, UserHandle.class).invoke(context, intent, userHandle);
        } catch (Exception e) {
            LogUtils.wTag(TAG, "startServiceAsUser: " + e.toString());
            return null;
        }
    }

    public static Boolean bindServiceAsUser(Context context, Intent intent, ServiceConnection serviceConnection, int i, UserHandle userHandle) {
        try {
            return (Boolean) Class.forName("android.content.ContextWrapper").getMethod("bindServiceAsUser", Intent.class, ServiceConnection.class, Integer.TYPE, UserHandle.class).invoke(context, intent, serviceConnection, Integer.valueOf(i), userHandle);
        } catch (Exception e) {
            Log.d(TAG, "bindServiceAsUser: " + e.toString());
            return false;
        }
    }

    public static ComponentName startForegroundServiceAsUser(Context context, Intent intent, UserHandle userHandle) {
        try {
            return (ComponentName) Class.forName("android.content.ContextWrapper").getMethod("startForegroundServiceAsUser", Intent.class, UserHandle.class).invoke(context, intent, userHandle);
        } catch (Exception e) {
            LogUtils.wTag(TAG, "startForegroundServiceAsUser: " + e.toString());
            return null;
        }
    }

    public static int getCurrentUserID() {
        try {
            return ((Integer) Class.forName("android.app.ActivityManager").getMethod("getCurrentUser", new Class[0]).invoke(null, new Object[0])).intValue();
        } catch (Exception e) {
            LogUtils.wTag(TAG, "getCurrentUserID: " + e.toString());
            return 0;
        }
    }
}
