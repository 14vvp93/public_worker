package com.android.systemui.util;

import android.text.TextUtils;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class ExternalParameterManager {
    public static int ETHEME_GOD_400 = 400;
    public static int ETHEME_SUB_16 = 16;
    public static int ETHEME_SUB_22 = 22;
    public static int ETHEME_SUB_37 = 37;
    public static int ETHEME_SUB_65 = 65;
    public static final int ETHEME_GOD = parseIntProperty("persist.sys.etheme_god", String.valueOf(0));
    public static final int ETHEME_SUB = parseIntProperty("persist.sys.etheme_sub", String.valueOf(0));

    public static String getASystemProperty(String property, String defaultValue) {
        try {
            Method getter = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class);
            String value = (String) getter.invoke(null, property);
            if (!TextUtils.isEmpty(value)) {
                return value;
            }
            return defaultValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int parseIntProperty(String propName, String defValue) {
        String propValue = getASystemProperty(propName, defValue);
        try {
            if (!TextUtils.isEmpty(propValue)) {
                return Integer.parseInt(propValue.trim());
            }
            return 0;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
