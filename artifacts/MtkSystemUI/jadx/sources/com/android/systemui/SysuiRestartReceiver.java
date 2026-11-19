package com.android.systemui;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

/* JADX INFO: loaded from: classes.dex */
public class SysuiRestartReceiver extends BroadcastReceiver {
    public static String ACTION = "com.android.systemui.action.RESTART";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (ACTION.equals(intent.getAction())) {
            String pkg = intent.getData().toString().substring(10);
            NotificationManager.from(context).cancel(pkg, 6);
            Process.killProcess(Process.myPid());
        }
    }
}
