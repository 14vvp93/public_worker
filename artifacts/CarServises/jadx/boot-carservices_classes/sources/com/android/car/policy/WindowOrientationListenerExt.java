package com.android.car.policy;

import android.Configures.HConfig;
import android.os.IBinder;
import android.os.Parcel;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.server.LocalServices;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class WindowOrientationListenerExt {
    private static final String TAG = "WindowOrientationListenerExt";
    private static PowerManagerInternal mPowerManagerInternal = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);

    public static void onProposedRotationChanged(int proposedRotation) {
        Log.i(TAG, "onProposedRotationChanged: proposedRotation=" + proposedRotation);
        mPowerManagerInternal.powerHint(2, 0);
        IBinder binder = ServiceManager.getService("mcu_service");
        if (binder != null) {
            Parcel _data = Parcel.obtain();
            try {
                _data.writeInterfaceToken("android.carsource.McuService");
                _data.writeString(proposedRotation == 0 ? "landscape" : "portrait");
                binder.transact(HConfig.MBoardCode.NAKAMICHI_MP5_BOARD, _data, null, 1);
            } catch (RemoteException e) {
            } finally {
                _data.recycle();
            }
        }
    }
}
