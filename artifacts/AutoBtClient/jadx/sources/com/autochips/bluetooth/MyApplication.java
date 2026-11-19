package com.autochips.bluetooth;

import android.app.Application;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import com.autochips.bluetooth.skin.SkinID;
import com.autochips.bluetooth.skin.SkinUtils;
import com.hcn.auto.theme.utils.ThemeUtils;
import com.hcn.bluetooth.api.LocalBluetoothAdapterManager;
import com.hcn.bluetooth.api.LocalBluetoothHfpclientManager;
import com.hcn.bluetooth.api.Utils;

/* JADX INFO: loaded from: classes.dex */
public class MyApplication extends Application {
    private static final String BT_A2DP_ROLE_PROP = "persist.bluetooth.a2dp.role";
    private static final String TAG = "MyApplication";
    private static final boolean isHeadrest = "1".equals(Utils.getSystemProperty("ro.headrest.pad", SkinID.SKIN_NONE));
    private static MyApplication sInstance;
    private IBinder mBinder = null;
    private LocalBluetoothAdapterManager mLocalBluetoothAdapterManager = null;
    private LocalBluetoothHfpclientManager mBluetoothHfpclientManager = null;

    public static MyApplication getInstance() {
        return sInstance;
    }

    public IBinder getBinder() {
        return this.mBinder;
    }

    private void init(Context context) {
        if (this.mLocalBluetoothAdapterManager == null) {
            LocalBluetoothAdapterManager localBluetoothAdapterManager = LocalBluetoothAdapterManager.getInstance();
            this.mLocalBluetoothAdapterManager = localBluetoothAdapterManager;
            localBluetoothAdapterManager.init(context);
        }
        if (this.mBluetoothHfpclientManager == null) {
            LocalBluetoothHfpclientManager localBluetoothHfpclientManager = LocalBluetoothHfpclientManager.getInstance();
            this.mBluetoothHfpclientManager = localBluetoothHfpclientManager;
            localBluetoothHfpclientManager.init(context);
        }
        ThemeUtils.init(this);
    }

    public LocalBluetoothAdapterManager getAdapterManager() {
        return this.mLocalBluetoothAdapterManager;
    }

    public LocalBluetoothHfpclientManager getHfpclientManager() {
        return this.mBluetoothHfpclientManager;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        SkinUtils.init(this);
        sInstance = this;
        this.mBinder = new Binder();
        init(this);
        CrashHandler.getInstance().init(this);
    }

    public static boolean isHeadrest() {
        return isHeadrest;
    }

    public boolean isA2dpSourceRole() {
        return "source".equals(Utils.getSystemProperty(BT_A2DP_ROLE_PROP, "sink").trim());
    }

    public void setA2dpSourceRole(boolean z) {
        if (z) {
            Utils.setSystemProperty(BT_A2DP_ROLE_PROP, "source");
        } else {
            Utils.setSystemProperty(BT_A2DP_ROLE_PROP, "sink");
        }
    }
}
