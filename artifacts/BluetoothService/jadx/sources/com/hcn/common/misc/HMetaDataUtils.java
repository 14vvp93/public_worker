package com.hcn.common.misc;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import com.hcn.common.utils.HUtilsEx;

/* JADX INFO: loaded from: classes.dex */
public class HMetaDataUtils {
    private HMetaDataUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String getMetaDataInApp(String str) {
        try {
            return String.valueOf(HUtilsEx.getApp().getPackageManager().getApplicationInfo(HUtilsEx.getApp().getPackageName(), 128).metaData.get(str));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getMetaDataInActivity(Activity activity, String str) {
        return getMetaDataInActivity((Class<? extends Activity>) activity.getClass(), str);
    }

    public static String getMetaDataInActivity(Class<? extends Activity> cls, String str) {
        try {
            return String.valueOf(HUtilsEx.getApp().getPackageManager().getActivityInfo(new ComponentName(HUtilsEx.getApp(), cls), 128).metaData.get(str));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getMetaDataInService(Service service, String str) {
        return getMetaDataInService((Class<? extends Service>) service.getClass(), str);
    }

    public static String getMetaDataInService(Class<? extends Service> cls, String str) {
        try {
            return String.valueOf(HUtilsEx.getApp().getPackageManager().getServiceInfo(new ComponentName(HUtilsEx.getApp(), cls), 128).metaData.get(str));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getMetaDataInReceiver(BroadcastReceiver broadcastReceiver, String str) {
        return getMetaDataInReceiver((Class<? extends BroadcastReceiver>) broadcastReceiver.getClass(), str);
    }

    public static String getMetaDataInReceiver(Class<? extends BroadcastReceiver> cls, String str) {
        try {
            return String.valueOf(HUtilsEx.getApp().getPackageManager().getReceiverInfo(new ComponentName(HUtilsEx.getApp(), cls), 128).metaData.get(str));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
