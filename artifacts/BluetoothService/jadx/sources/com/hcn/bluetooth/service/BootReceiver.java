package com.hcn.bluetooth.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
            Log.d(TAG, "ACTION_BOOT_COMPLETED");
        } else if (action.equals("android.intent.action.MEDIA_BUTTON")) {
            BluetoothMusicService.getInstance().onMediaKeyEvent(intent);
        }
    }
}
