package com.hcn.auto.theme.utils;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    public static String getSystemProperty(String property, String defaultValue) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls, new String(property), defaultValue);
        } catch (Exception unused) {
            return null;
        }
    }
}
