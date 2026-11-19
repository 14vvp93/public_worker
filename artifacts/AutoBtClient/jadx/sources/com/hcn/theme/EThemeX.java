package com.hcn.theme;

import android.text.TextUtils;
import com.hcn.common.utils.HUtilsEx;

/* JADX INFO: loaded from: classes.dex */
public class EThemeX {
    public static final int E_THEME_GOD = parseIntProperty("persist.sys.etheme_god", String.valueOf(0));
    public static final int E_THEME_SUB = parseIntProperty("persist.sys.etheme_sub", String.valueOf(0));

    private static int parseIntProperty(String str, String str2) {
        String systemProperty = HUtilsEx.getSystemProperty(str, str2);
        try {
            if (TextUtils.isEmpty(systemProperty)) {
                return 0;
            }
            return Integer.parseInt(systemProperty.trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean isThemeGod(int i) {
        return E_THEME_GOD == i;
    }

    public static boolean isThemeSub(int i) {
        return E_THEME_SUB == i;
    }

    public static boolean isThemeX(int i) {
        return eThemeWeight() == i;
    }

    public static int eThemeWeight() {
        int i = E_THEME_GOD;
        int i2 = E_THEME_SUB;
        return !isThemeSub(0) ? (i * 1000) + i2 : i + i2;
    }
}
