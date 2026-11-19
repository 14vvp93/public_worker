package com.hcn.auto_compat.app;

import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
public class WindowConfiguration {
    public static final int WINDOWING_MODE_FREEFORM = 5;
    public static final int WINDOWING_MODE_FULLSCREEN = 1;
    public static final int WINDOWING_MODE_FULLSCREEN_OR_SPLIT_SCREEN_SECONDARY = 4;
    public static final int WINDOWING_MODE_PINNED = 2;
    public static final int WINDOWING_MODE_SPLIT_SCREEN_PRIMARY = 3;
    public static final int WINDOWING_MODE_SPLIT_SCREEN_SECONDARY = 4;
    public static final int WINDOWING_MODE_UNDEFINED = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface WindowingMode {
    }

    public static String windowingModeToString(int i) {
        if (i == 0) {
            return "undefined";
        }
        if (i == 1) {
            return "fullscreen";
        }
        if (i == 2) {
            return "pinned";
        }
        if (i == 3) {
            return "split-screen-primary";
        }
        if (i != 4) {
            return i != 5 ? String.valueOf(i) : "freeform";
        }
        return "split-screen-secondary";
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:23:0x004a  */
    public static int windowingMode(String str) {
        byte b;
        switch (str) {
            case "freeform":
                b = 4;
                break;
            case "undefined":
                b = 5;
                break;
            case "pinned":
                b = 1;
                break;
            case "split-screen-secondary":
                b = 3;
                break;
            case "fullscreen":
                b = 0;
                break;
            case "split-screen-primary":
                b = 2;
                break;
            default:
                b = -1;
                break;
        }
        if (b == 0) {
            return 1;
        }
        if (b == 1) {
            return 2;
        }
        if (b == 2) {
            return 3;
        }
        if (b != 3) {
            return b != 4 ? 0 : 5;
        }
        return 4;
    }

    public static int getWindowingMode(Configuration configuration) {
        Object objInvoke;
        if (Build.VERSION.SDK_INT < 28) {
            return -1;
        }
        try {
            Field declaredField = Class.forName("android.content.res.Configuration").getDeclaredField("windowConfiguration");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(configuration);
            if (obj != null && (objInvoke = Class.forName("android.app.WindowConfiguration").getMethod("getWindowingMode", new Class[0]).invoke(obj, new Object[0])) != null) {
                return ((Integer) objInvoke).intValue();
            }
        } catch (Exception e) {
            Log.d("PlatformUtils", "requestPlayAudio: " + e);
        }
        return -1;
    }
}
