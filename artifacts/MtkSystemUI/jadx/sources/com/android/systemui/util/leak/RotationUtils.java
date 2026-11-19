package com.android.systemui.util.leak;

import android.content.Context;
import android.content.res.Configuration;

/* JADX INFO: loaded from: classes.dex */
public class RotationUtils {
    public static int getRotation(Context context) {
        Configuration config = context.getResources().getConfiguration();
        int rot = context.getDisplay().getRotation();
        if (config.smallestScreenWidthDp < 600) {
            if (rot == 1) {
                return 1;
            }
            if (rot == 3) {
                return 2;
            }
            return 0;
        }
        return 0;
    }

    public static int getExactRotation(Context context) {
        Configuration config = context.getResources().getConfiguration();
        int rot = context.getDisplay().getRotation();
        if (config.smallestScreenWidthDp < 600) {
            if (rot == 1) {
                return 1;
            }
            if (rot == 3) {
                return 2;
            }
            return rot == 2 ? 3 : 0;
        }
        return 0;
    }
}
