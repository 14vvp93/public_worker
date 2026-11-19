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

    /* JADX INFO: renamed from: v */
    public static void m56v(String str) {
        if (V_LOGGABLE) {
            Log.v(TAG, str);
        }
    }

    /* JADX INFO: renamed from: v */
    public static void m57v(String str, String str2) {
        if (V_LOGGABLE) {
            Log.v(TAG, str + ": " + str2);
        }
    }

    /* JADX INFO: renamed from: d */
    public static void m50d(String str) {
        if (D_LOGGABLE) {
            Log.d(TAG, str);
        }
    }

    /* JADX INFO: renamed from: d */
    public static void m51d(String str, String str2) {
        if (D_LOGGABLE) {
            Log.d(TAG, str + ": " + str2);
        }
    }

    /* JADX INFO: renamed from: i */
    public static void m54i(String str) {
        if (I_LOGGABLE) {
            Log.i(TAG, str);
        }
    }

    /* JADX INFO: renamed from: i */
    public static void m55i(String str, String str2) {
        if (I_LOGGABLE) {
            Log.i(TAG, str + ": " + str2);
        }
    }

    /* JADX INFO: renamed from: w */
    public static void m58w(String str) {
        if (W_LOGGABLE) {
            Log.w(TAG, str);
        }
    }

    /* JADX INFO: renamed from: w */
    public static void m59w(String str, String str2) {
        if (W_LOGGABLE) {
            Log.w(TAG, str + ": " + str2);
        }
    }

    /* JADX INFO: renamed from: e */
    public static void m52e(String str) {
        if (E_LOGGABLE) {
            Log.e(TAG, str);
        }
    }

    /* JADX INFO: renamed from: e */
    public static void m53e(String str, String str2) {
        if (E_LOGGABLE) {
            Log.e(TAG, str + ": " + str2);
        }
    }
}
