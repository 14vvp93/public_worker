package com.hcn.auto_compat.app;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import com.hcn.common.misc.LogUtils;
import com.hcn.common.utils.HUtilsEx;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class NotificationUtils {
    private static final String DESCRIPTOR = "android.app.INotificationManager";
    private static final String TAG = "NotificationUtils";
    private static final int TRANSACTION_cancelNotificationWithTag = 7;
    private static IBinder sService;

    public static void init() {
        if (sService != null) {
            LogUtils.wTag(TAG, "Repeated call, initialized.");
            return;
        }
        IBinder systemService = HUtilsEx.getSystemService("notification");
        sService = systemService;
        if (Objects.isNull(systemService)) {
            LogUtils.wTag(TAG, "Get system service 'notification' failed.");
        }
    }

    public static void cancelAsPackage(String str, String str2, int i, int i2) {
        if (sService == null) {
            init();
        }
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(DESCRIPTOR);
            parcelObtain.writeString(str);
            parcelObtain.writeString(str2);
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            sService.transact(7, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } catch (RemoteException unused) {
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    public static int getIdentifier(UserHandle userHandle) {
        int iHashCode = userHandle.hashCode();
        try {
            return ((Integer) Class.forName("android.os.UserHandle").getMethod("getIdentifier", new Class[0]).invoke(userHandle, new Object[0])).intValue();
        } catch (Exception e) {
            LogUtils.wTag(TAG, "getIdentifier: " + e.toString());
            return iHashCode;
        }
    }
}
