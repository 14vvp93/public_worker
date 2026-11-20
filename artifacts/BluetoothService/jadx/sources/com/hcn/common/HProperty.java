package com.hcn.common;

import android.text.TextUtils;
import java.security.InvalidParameterException;

/* JADX INFO: loaded from: classes.dex */
public class HProperty {
    public static String D_LOGGABLE = "com.hcn.$(module).logd";
    public static String E_LOGGABLE = "com.hcn.$(module).loge";
    public static String I_LOGGABLE = "com.hcn.$(module).logi";
    public static String V_LOGGABLE = "com.hcn.$(module).logv";
    public static String W_LOGGABLE = "com.hcn.$(module).logw";

    public static void init(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new InvalidParameterException("HProperty init: 'moduleKey' is empty!");
        }
        V_LOGGABLE = V_LOGGABLE.replace("$(module)", str);
        D_LOGGABLE = D_LOGGABLE.replace("$(module)", str);
        I_LOGGABLE = I_LOGGABLE.replace("$(module)", str);
        W_LOGGABLE = W_LOGGABLE.replace("$(module)", str);
        E_LOGGABLE = E_LOGGABLE.replace("$(module)", str);
    }
}
