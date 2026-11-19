package com.hcn.skin.support.utils;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class SkinLog {
    private static boolean V_LOGGABLE = false;
    private static boolean D_LOGGABLE = false;
    private static boolean I_LOGGABLE = false;
    private static boolean W_LOGGABLE = false;
    private static boolean E_LOGGABLE = false;

    public static void loggerConfig() {
        V_LOGGABLE = "1".equals(SkinSystemUtils.getSystemProperty("persist.sys.skin-support.logv", "0"));
        D_LOGGABLE = "1".equals(SkinSystemUtils.getSystemProperty("persist.sys.skin-support.logd", "0"));
        I_LOGGABLE = "1".equals(SkinSystemUtils.getSystemProperty("persist.sys.skin-support.logi", "0"));
        W_LOGGABLE = "1".equals(SkinSystemUtils.getSystemProperty("persist.sys.skin-support.logw", "1"));
        E_LOGGABLE = "1".equals(SkinSystemUtils.getSystemProperty("persist.sys.skin-support.loge", "1"));
    }

    public static void init() {
        loggerConfig();
    }

    public static void v(String subtag, String msg) {
        if (V_LOGGABLE) {
            Log.v("skin-support", subtag + ": " + msg);
        }
    }

    public static void d(String subtag, String msg) {
        if (D_LOGGABLE) {
            Log.d("skin-support", subtag + ": " + msg);
        }
    }

    public static void i(String subtag, String msg) {
        if (I_LOGGABLE) {
            Log.i("skin-support", subtag + ": " + msg);
        }
    }

    public static void e(String subtag, String msg) {
        if (E_LOGGABLE) {
            Log.e("skin-support", subtag + ": " + msg);
        }
    }
}
