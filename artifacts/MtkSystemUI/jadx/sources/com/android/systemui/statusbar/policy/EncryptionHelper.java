package com.android.systemui.statusbar.policy;

import android.os.SystemProperties;

/* JADX INFO: loaded from: classes.dex */
public class EncryptionHelper {
    public static final boolean IS_DATA_ENCRYPTED = isDataEncrypted();

    private static boolean isDataEncrypted() {
        String voldState = SystemProperties.get("vold.decrypt");
        return "1".equals(voldState) || "trigger_restart_min_framework".equals(voldState);
    }
}
