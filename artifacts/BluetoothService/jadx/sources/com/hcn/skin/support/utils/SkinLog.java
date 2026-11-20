package com.hcn.skin.support.utils;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class SkinLog {
    private static boolean D_LOGGABLE = false;
    private static boolean E_LOGGABLE = false;
    private static boolean I_LOGGABLE = false;
    private static final String TAG = "skin-support";
    private static boolean V_LOGGABLE = false;
    private static boolean W_LOGGABLE = false;

    public interface IProperty {
        public static final String D_LOGGABLE = "persist.sys.skin-support.logd";
        public static final String E_LOGGABLE = "persist.sys.skin-support.loge";
        public static final String I_LOGGABLE = "persist.sys.skin-support.logi";
        public static final String V_LOGGABLE = "persist.sys.skin-support.logv";
        public static final String W_LOGGABLE = "persist.sys.skin-support.logw";
    }

    public static void loggerConfig() {
        V_LOGGABLE = "1".equals(SkinSystemUtils.getSystemProperty(IProperty.V_LOGGABLE, "0"));
        D_LOGGABLE = "1".equals(SkinSystemUtils.getSystemProperty(IProperty.D_LOGGABLE, "0"));
        I_LOGGABLE = "1".equals(SkinSystemUtils.getSystemProperty(IProperty.I_LOGGABLE, "0"));
        W_LOGGABLE = "1".equals(SkinSystemUtils.getSystemProperty(IProperty.W_LOGGABLE, "1"));
        E_LOGGABLE = "1".equals(SkinSystemUtils.getSystemProperty(IProperty.E_LOGGABLE, "1"));
    }

    public static void init() {
        loggerConfig();
    }

    public static void v(String str) {
        if (V_LOGGABLE) {
            Log.v(TAG, str);
        }
    }

    public static void v(String str, String str2) {
        if (V_LOGGABLE) {
            Log.v(TAG, str + ": " + str2);
        }
    }

    public static void d(String str) {
        if (D_LOGGABLE) {
            Log.d(TAG, str);
        }
    }

    public static void d(String str, String str2) {
        if (D_LOGGABLE) {
            Log.d(TAG, str + ": " + str2);
        }
    }

    public static void i(String str) {
        if (I_LOGGABLE) {
            Log.i(TAG, str);
        }
    }

    public static void i(String str, String str2) {
        if (I_LOGGABLE) {
            Log.i(TAG, str + ": " + str2);
        }
    }

    public static void w(String str) {
        if (W_LOGGABLE) {
            Log.w(TAG, str);
        }
    }

    public static void w(String str, String str2) {
        if (W_LOGGABLE) {
            Log.w(TAG, str + ": " + str2);
        }
    }

    public static void e(String str) {
        if (E_LOGGABLE) {
            Log.e(TAG, str);
        }
    }

    public static void e(String str, String str2) {
        if (E_LOGGABLE) {
            Log.e(TAG, str + ": " + str2);
        }
    }
}
