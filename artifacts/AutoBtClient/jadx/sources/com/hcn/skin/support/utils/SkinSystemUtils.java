package com.hcn.skin.support.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public class SkinSystemUtils {
    public static void setSystemProperty(String str, String str2) {
        try {
            Class.forName("android.os.SystemProperties").getDeclaredMethod("set", String.class, String.class).invoke(null, str, str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSystemProperty(String str, String str2) {
        try {
            String str3 = (String) Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class).invoke(null, str);
            return !TextUtils.isEmpty(str3) ? str3 : str2;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getApkPath(Context context, String str) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 0);
            if (applicationInfo == null) {
                return null;
            }
            return applicationInfo.sourceDir;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
