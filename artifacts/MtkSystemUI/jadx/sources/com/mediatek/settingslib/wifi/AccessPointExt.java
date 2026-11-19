package com.mediatek.settingslib.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;

/* JADX INFO: loaded from: classes.dex */
public class AccessPointExt {
    public AccessPointExt(Context context) {
    }

    public static int getSecurity(WifiConfiguration config) {
        if (config.allowedKeyManagement.get(8)) {
            return 4;
        }
        if (config.allowedKeyManagement.get(9)) {
            return 5;
        }
        return -1;
    }

    public static int getSecurity(ScanResult result) {
        if (result.capabilities.contains("WAPI-PSK")) {
            return 4;
        }
        if (result.capabilities.contains("WAPI-CERT")) {
            return 5;
        }
        return -1;
    }
}
