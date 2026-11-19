package com.auto.common;

import android.os.SystemProperties;

/* JADX INFO: loaded from: classes.dex */
public class HConfig {
    public static boolean isDisableStatusBarContents() {
        String statusBarEnable = SystemProperties.get("ro.sys.statusbar.enable", "true");
        return !"true".equals(statusBarEnable);
    }

    public static boolean isDisableDropdownPanelBar() {
        String deviceAlias = SystemProperties.get("ro.fota.device");
        return "CVD7470-AJ".equals(deviceAlias) || isDisableStatusBarContents();
    }

    public static boolean isDisableNotificationShow() {
        String deviceAlias = SystemProperties.get("ro.fota.device");
        return "CVD7470-AJ".equals(deviceAlias);
    }

    public static boolean isChangedAppBlockingDropdownPanelBar() {
        String isBlocking = SystemProperties.get("sys.changedapp.disable.systemui");
        return "1".equals(isBlocking);
    }
}
