package com.hcn.common.app;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import com.hcn.common.utils.HUtilsEx;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class HServiceUtils {
    private HServiceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Set<String> getAllRunningServices() {
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) HUtilsEx.getApp().getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
        HashSet hashSet = new HashSet();
        if (runningServices == null || runningServices.size() == 0) {
            return null;
        }
        Iterator<ActivityManager.RunningServiceInfo> it = runningServices.iterator();
        while (it.hasNext()) {
            hashSet.add(it.next().service.getClassName());
        }
        return hashSet;
    }

    public static void startService(String str) {
        try {
            startService(Class.forName(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startService(Class<?> cls) {
        startService(new Intent(HUtilsEx.getApp(), cls));
    }

    public static void startService(Intent intent) {
        try {
            intent.setFlags(32);
            if (Build.VERSION.SDK_INT >= 26) {
                HUtilsEx.getApp().startForegroundService(intent);
            } else {
                HUtilsEx.getApp().startService(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean stopService(String str) {
        try {
            return stopService(Class.forName(str));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean stopService(Class<?> cls) {
        return stopService(new Intent(HUtilsEx.getApp(), cls));
    }

    public static boolean stopService(Intent intent) {
        try {
            return HUtilsEx.getApp().stopService(intent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void bindService(String str, ServiceConnection serviceConnection, int i) {
        try {
            bindService(Class.forName(str), serviceConnection, i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bindService(Class<?> cls, ServiceConnection serviceConnection, int i) {
        bindService(new Intent(HUtilsEx.getApp(), cls), serviceConnection, i);
    }

    public static void bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        try {
            HUtilsEx.getApp().bindService(intent, serviceConnection, i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unbindService(ServiceConnection serviceConnection) {
        HUtilsEx.getApp().unbindService(serviceConnection);
    }

    public static boolean isServiceRunning(Class<?> cls) {
        return isServiceRunning(cls.getName());
    }

    public static boolean isServiceRunning(String str) {
        try {
            List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) HUtilsEx.getApp().getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
            if (runningServices != null && runningServices.size() != 0) {
                Iterator<ActivityManager.RunningServiceInfo> it = runningServices.iterator();
                while (it.hasNext()) {
                    if (str.equals(it.next().service.getClassName())) {
                        return true;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }
}
