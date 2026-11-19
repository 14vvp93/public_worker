package com.hcn.auto_compat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.sourceservice.ExtAudioMuxer;
import android.text.TextUtils;
import android.util.Log;
import com.autochips.bluetooth.skin.SkinID;
import com.hcn.auto.launcher.constants.PackageInfo;
import com.hcn.common.utils.HUtilsEx;

/* JADX INFO: loaded from: classes.dex */
public class PlatformUtils {
    private static final String EQ_CHIP_TYPE = "ro.hw.dsp.chip";
    private static final String FORCE_USED_INTERNAL = "force_used_internal";
    public static final String MT8321 = "mt8321";
    public static final String QCOM_LABLE = "qcom";
    public static final String SM6225 = "sm6225";
    public static final String UIS8581 = "uis8581";
    private static final boolean sIsDisplayOverscanning = "1".equals(HUtilsEx.getSystemProperty("ro.debug.overscanning", "0"));

    public static boolean isHardware(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (Build.HARDWARE.equalsIgnoreCase(QCOM_LABLE)) {
            return HUtilsEx.getSystemProperty("ro.hardware.soc_name", "none").toLowerCase().contains(str.toLowerCase());
        }
        return Build.HARDWARE.toLowerCase().contains(str.toLowerCase());
    }

    public static boolean noExistExternalDSPChip() {
        String systemProperty = HUtilsEx.getSystemProperty(EQ_CHIP_TYPE, SkinID.SKIN_NONE);
        return "pt2348".equals(systemProperty) || "cd3313".equals(systemProperty) || TextUtils.isEmpty(systemProperty) || systemProperty.contains("asp");
    }

    public static boolean usedInternalDSP(Context context) {
        if (!ExtAudioMuxer.ExtAudioAvailable) {
            return !checkAppInstall(context, PackageInfo.EQ_PACKAGE_NAME);
        }
        String systemProperty = HUtilsEx.getSystemProperty(EQ_CHIP_TYPE, SkinID.SKIN_NONE);
        return ("pt2348".equals(systemProperty) || "cd3313".equals(systemProperty)) && 1 == Settings.System.getInt(context.getContentResolver(), FORCE_USED_INTERNAL, 0);
    }

    public static boolean isDisplayOverscanning() {
        return sIsDisplayOverscanning;
    }

    public static boolean checkAppInstall(Context context, String str) {
        if (str != null && !SkinID.SKIN_NONE.equals(str)) {
            try {
                context.getPackageManager().getApplicationInfo(str, 8192);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return false;
    }

    public static boolean requestPlayAudio(Context context) {
        try {
            Class<?> cls = Class.forName("android.sourceservice.SourceInfo");
            Object objInvoke = cls.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
            if (objInvoke != null) {
                cls.getMethod("onRequestPlayAudio", Context.class).invoke(objInvoke, context);
                return true;
            }
        } catch (Exception e) {
            Log.d("PlatformUtils", "requestPlayAudio: " + e.toString());
        }
        return false;
    }

    public static boolean requestPlayAudio(String str) {
        try {
            Class<?> cls = Class.forName("android.sourceservice.SourceInfo");
            Object objInvoke = cls.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
            if (objInvoke != null) {
                cls.getMethod("onRequestPlayAudio", String.class).invoke(objInvoke, str);
                return true;
            }
        } catch (Exception e) {
            Log.d("PlatformUtils", "requestPlayAudio: " + e.toString());
        }
        return false;
    }
}
