package com.hcn.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import com.hcn.common.utils.HUtilsEx;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/* JADX INFO: loaded from: classes.dex */
public class HConfig {
    private static boolean D_LOGGABLE = false;
    private static boolean E_LOGGABLE = false;
    public static final String INTENT_REASON_EXTRA_KEY = "intent_reason";
    private static boolean I_LOGGABLE = false;
    public static final String REASON_ACTIVITY_CREATE = "activity_created";
    public static final String REASON_APPLICATION_CREATE = "application_created";
    public static final String REASON_FRAGMENT_CREATE = "fragment_created";
    public static final String START_REASON_EXTRA_KEY = "start_reason";
    private static boolean V_LOGGABLE = false;
    private static boolean W_LOGGABLE = false;
    private static BroadcastReceiver sConfigBroadcastReceiver;
    private static boolean sIsDebug;

    public static boolean isDebug() {
        return sIsDebug;
    }

    public static void initConfig(Context context, String str, String str2) {
        sIsDebug = HUtilsEx.isAppDebug(context);
        HProperty.init(str);
        HBroadcast.init(str);
        loggerConfig(true);
        initDebugConfig(context, str2);
    }

    public static void loggerConfig(boolean z) {
        V_LOGGABLE = "1".equals(HUtilsEx.getSystemProperty(HProperty.V_LOGGABLE, "0"));
        D_LOGGABLE = "1".equals(HUtilsEx.getSystemProperty(HProperty.D_LOGGABLE, "1"));
        I_LOGGABLE = "1".equals(HUtilsEx.getSystemProperty(HProperty.I_LOGGABLE, "0"));
        W_LOGGABLE = "1".equals(HUtilsEx.getSystemProperty(HProperty.W_LOGGABLE, "1"));
        E_LOGGABLE = "1".equals(HUtilsEx.getSystemProperty(HProperty.E_LOGGABLE, "1"));
        if (z && isDebug()) {
            V_LOGGABLE = true;
        }
    }

    public static void initDebugConfig(Context context, String str) {
        Logger.addLogAdapter(new HLogAdapter(PrettyFormatStrategy.newBuilder().showThreadInfo(false).methodCount(0).methodOffset(5).logStrategy(new LogcatLogStrategy()).tag(str).build()));
        if (context != null) {
            if (sConfigBroadcastReceiver == null) {
                sConfigBroadcastReceiver = new HBroadcast();
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(HBroadcast.DEBUG_CONFIG_ACTION);
            context.registerReceiver(sConfigBroadcastReceiver, intentFilter);
        }
    }

    private static class HLogAdapter extends AndroidLogAdapter {
        public HLogAdapter(FormatStrategy formatStrategy) {
            super(formatStrategy);
        }

        public boolean isLoggable(int i, String str) {
            if (i == 2) {
                return HConfig.V_LOGGABLE;
            }
            if (i == 3) {
                return HConfig.D_LOGGABLE;
            }
            if (i == 4) {
                return HConfig.I_LOGGABLE;
            }
            if (i == 5) {
                return HConfig.W_LOGGABLE;
            }
            if (i == 6) {
                return HConfig.E_LOGGABLE;
            }
            return super.isLoggable(i, str);
        }
    }
}
