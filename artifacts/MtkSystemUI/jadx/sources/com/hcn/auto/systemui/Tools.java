package com.hcn.auto.systemui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class Tools {
    public static boolean isAppInstalled(String pkgName, Context context) {
        if (isSpace(pkgName)) {
            return false;
        }
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getApplicationInfo(pkgName, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isSpace(String s) {
        if (s == null) {
            return true;
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void startActivity(Context context, String packageName, String className) {
        if ((1 == Settings.System.getInt(context.getContentResolver(), "device_lock_status", 0) && !"com.android.settings".equals(packageName)) || context == null || TextUtils.isEmpty(packageName) || TextUtils.isEmpty(className)) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.addFlags(270532608);
            intent.setClassName(packageName, className);
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                if (packageName.equals("com.google.android.youtube")) {
                    Intent intent2 = new Intent();
                    intent2.setAction("android.intent.action.MAIN");
                    intent2.addCategory("android.intent.category.LAUNCHER");
                    intent2.addFlags(270532608);
                    intent2.setClassName(packageName, "com.google.android.apps.youtube.app.application.Shell$HomeActivity");
                    context.startActivity(intent2);
                }
            } catch (Exception e2) {
                Log.e("Tools", "", e2);
            }
            Log.e("Tools", "", e);
        }
    }
}
