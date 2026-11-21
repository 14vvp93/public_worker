package com.android.car.usb;

import android.sourceservice.SourceService;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public final class UsbProfileGroupSettingsManagerExt {
    public static boolean isDefaultActivityInfo(String packageName) {
        return SourceService.ACTIVE_EASYCONN.equals(packageName);
    }
}
