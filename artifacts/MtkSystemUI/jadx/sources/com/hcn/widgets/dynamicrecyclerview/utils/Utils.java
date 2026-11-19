package com.hcn.widgets.dynamicrecyclerview.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    public static void startApp(Context context, String packageName, String classname) {
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setComponent(new ComponentName(packageName, classname));
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setFlags(268435456);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<LauncherActivityInfo> getInstalledApps(Context context, String pkg) {
        ArrayList<LauncherActivityInfo> appList = new ArrayList<>();
        if (context != null) {
            LauncherApps mLauncherApps = (LauncherApps) context.getSystemService("launcherapps");
            UserManager userManager = (UserManager) context.getSystemService("user");
            List<UserHandle> profiles = userManager.getUserProfiles();
            for (UserHandle user : profiles) {
                List<LauncherActivityInfo> apps = mLauncherApps.getActivityList(pkg, user);
                appList.addAll(apps);
            }
        }
        return appList;
    }

    public static String getSystemProperty(String property, String defaultValue) {
        try {
            Method getter = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class);
            getter.setAccessible(true);
            String value = (String) getter.invoke(null, property);
            if (!TextUtils.isEmpty(value)) {
                return value;
            }
            return defaultValue;
        } catch (Exception e) {
            Log.d("Utils", "Unable to get system properties");
            e.printStackTrace();
        }
    }

    public static void setSystemProperty(String property, String defaultValue) {
        try {
            Method setter = Class.forName("android.os.SystemProperties").getDeclaredMethod("set", String.class, String.class);
            setter.setAccessible(true);
            setter.invoke(null, property, defaultValue);
        } catch (Exception e) {
            Log.d("Utils", "Unable to set system properties");
            e.printStackTrace();
        }
    }
}
