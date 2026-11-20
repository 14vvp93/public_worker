package com.hcn.bluetooth;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import com.hcn.bluetooth.api.Utils;
import com.hcn.bluetooth.config.ThemeConfig;
import com.hcn.bluetooth.service.BluetoothAdapterManagerService;
import com.hcn.bluetooth.service.BluetoothHfpclientService;
import com.hcn.bluetooth.service.BluetoothMusicService;
import com.hcn.skin2.Skin2;

/* JADX INFO: loaded from: classes.dex */
public class MyApplication extends Application {
    private static final String TAG = "BTApplication";

    public void startService() {
        startService(new Intent(this, (Class<?>) BluetoothAdapterManagerService.class));
        startService(new Intent(this, (Class<?>) BluetoothHfpclientService.class));
        startService(new Intent(this, (Class<?>) BluetoothMusicService.class));
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, " bluetooth service onCreate");
        Skin2.init(this).configProp(ThemeConfig.MODULE_SKIN_PROP).configPackageFilePrefix(ThemeConfig.MODULE_SKIN_PACKAGE_FILE_PREFIX).configPackageNamePrefix(ThemeConfig.MODULE_SKIN_PACKAGE_NAME_PREFIX).setupSkin();
        CrashHandler.getInstance().init(this);
        startService();
        Utils.CreateHciLogPath();
    }
}
