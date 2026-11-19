package com.hcn.auto.theme.utils;

import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    public static String getSystemProperty(String property, String defaultValue) {
        try {
            Class<?> SystemProperties = Class.forName("android.os.SystemProperties");
            Method get = SystemProperties.getMethod("get", String.class, String.class);
            Object[] params = {new String(property), defaultValue};
            String ret = (String) get.invoke(SystemProperties, params);
            return ret;
        } catch (Exception e) {
            return null;
        }
    }
}
