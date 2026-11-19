package com.hcn.config;

import com.autochips.bluetooth.skin.SkinID;
import com.hcn.common.utils.HUtilsEx;

/* JADX INFO: loaded from: classes.dex */
public class Customer {
    public static final String FOTA_FEATURE = HUtilsEx.getSystemProperty("ro.fota.device", "None");
    public static final int NAVIGATION_BAR_HEIGHT = HUtilsEx.getSystemProperty("ro.sys.customer.naviheight", 0).intValue();
    public static final String ZE_XUN_ELECTRONICS = "ZXDZ";
    private static String sCustomerName;

    public static String name() {
        if (sCustomerName == null) {
            sCustomerName = HUtilsEx.getSystemProperty("persist.sys.customer", SkinID.SKIN_NONE);
        }
        return sCustomerName;
    }
}
