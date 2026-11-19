package com.hcn.common.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

/* JADX INFO: loaded from: classes.dex */
public class HHandler extends Handler {
    public HHandler(Looper looper) {
        super(looper);
    }

    public final boolean sendEmptyUniqueMessage(int i) {
        return sendEmptyUniqueMessageDelayed(i, 0L);
    }

    public final boolean sendEmptyUniqueMessageDelayed(int i, long j) {
        if (hasMessages(i)) {
            removeMessages(i);
        }
        Message messageObtain = Message.obtain();
        messageObtain.what = i;
        long jUptimeMillis = SystemClock.uptimeMillis();
        if (j < 0) {
            j = 0;
        }
        return sendMessageAtTime(messageObtain, jUptimeMillis + j);
    }

    public final boolean postUniqueDelayed(Runnable runnable, long j) {
        removeCallbacks(runnable);
        return postDelayed(runnable, j);
    }
}
