package com.mediatek.keyguard.PowerOffAlarm;

import android.content.Context;
import android.text.format.DateFormat;

/* JADX INFO: loaded from: classes.dex */
public class Alarms {
    public static boolean get24HourMode(Context context) {
        return DateFormat.is24HourFormat(context);
    }
}
