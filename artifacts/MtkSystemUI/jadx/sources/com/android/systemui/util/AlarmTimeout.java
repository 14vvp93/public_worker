package com.android.systemui.util;

import android.app.AlarmManager;
import android.os.Handler;
import android.os.SystemClock;

/* JADX INFO: loaded from: classes.dex */
public class AlarmTimeout implements AlarmManager.OnAlarmListener {
    private final AlarmManager mAlarmManager;
    private final Handler mHandler;
    private final AlarmManager.OnAlarmListener mListener;
    private boolean mScheduled;
    private final String mTag;

    public AlarmTimeout(AlarmManager alarmManager, AlarmManager.OnAlarmListener listener, String tag, Handler handler) {
        this.mAlarmManager = alarmManager;
        this.mListener = listener;
        this.mTag = tag;
        this.mHandler = handler;
    }

    public void schedule(long timeout, int mode) {
        switch (mode) {
            case 0:
                if (this.mScheduled) {
                    throw new IllegalStateException(this.mTag + " timeout is already scheduled");
                }
                break;
            case 1:
                if (this.mScheduled) {
                    return;
                }
                break;
            case 2:
                if (this.mScheduled) {
                    cancel();
                }
                break;
            default:
                throw new IllegalArgumentException("Illegal mode: " + mode);
        }
        this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + timeout, this.mTag, this, this.mHandler);
        this.mScheduled = true;
    }

    public boolean isScheduled() {
        return this.mScheduled;
    }

    public void cancel() {
        if (this.mScheduled) {
            this.mAlarmManager.cancel(this);
            this.mScheduled = false;
        }
    }

    @Override // android.app.AlarmManager.OnAlarmListener
    public void onAlarm() {
        if (!this.mScheduled) {
            return;
        }
        this.mScheduled = false;
        this.mListener.onAlarm();
    }
}
