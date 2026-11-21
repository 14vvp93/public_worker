package com.android.car.usb;

import android.os.SystemProperties;
import android.sourceservice.SourceService;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public final class UsbUserSettingsManagerExt {
    private static String grantUsbSerialPermissionList = SystemProperties.get("ro.sys.usb_device_permission", "");

    public static boolean needsDefaultDevicePermission(String packageName) {
        return "com.ex.dabplayer.pad".equals(packageName) || "com.williexing.android.apps.xcdvr1".equals(packageName) || grantUsbSerialPermissionList.contains(packageName);
    }

    public static boolean hasDevicePermission(String packageName) {
        return SourceService.ACTIVE_EASYCONN.equals(packageName);
    }
}
