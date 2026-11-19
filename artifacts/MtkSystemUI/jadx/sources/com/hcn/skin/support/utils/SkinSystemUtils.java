package com.hcn.skin.support.utils;

import android.text.TextUtils;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class SkinSystemUtils {
    public static String getSystemProperty(String property, String defaultValue) {
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
}
