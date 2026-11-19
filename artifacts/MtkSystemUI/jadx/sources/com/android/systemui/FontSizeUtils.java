package com.android.systemui;

import android.view.View;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class FontSizeUtils {
    public static void updateFontSize(View parent, int viewId, int dimensId) {
        updateFontSize((TextView) parent.findViewById(viewId), dimensId);
    }

    public static void updateFontSize(TextView v, int dimensId) {
        if (v != null) {
            v.setTextSize(0, v.getResources().getDimensionPixelSize(dimensId));
        }
    }
}
