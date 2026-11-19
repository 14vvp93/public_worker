package com.android.systemui.shared.system;

import android.content.res.Resources;

/* JADX INFO: loaded from: classes.dex */
public class NavigationBarCompat {
    public static int getQuickStepTouchSlopPx() {
        return convertDpToPixel(24.0f);
    }

    public static int getQuickScrubTouchSlopPx() {
        return convertDpToPixel(24.0f);
    }

    private static int convertDpToPixel(float dp) {
        return (int) (Resources.getSystem().getDisplayMetrics().density * dp);
    }
}
