package com.hcn.auto_compat.os;

import android.os.IBinder;
import com.hcn.common.misc.LogUtils;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes.dex */
public class ServiceManagerCompat {
    private static final String TAG = "ServiceManagerCompat";

    public static IBinder getService(String str) {
        try {
            Class<?> cls = Class.forName("android.os.ServiceManager");
            Object objInvoke = cls.getMethod("getService", String.class).invoke(cls, str);
            if (objInvoke != null) {
                return (IBinder) objInvoke;
            }
            return null;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            LogUtils.wTag(TAG, "getService: " + e.toString());
            return null;
        }
    }

    public static void addService(String str, IBinder iBinder) {
        try {
            Class<?> cls = Class.forName("android.os.ServiceManager");
            cls.getMethod("addService", String.class, IBinder.class).invoke(cls, str, iBinder);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            LogUtils.wTag(TAG, "addService: " + e.toString());
        }
    }
}
