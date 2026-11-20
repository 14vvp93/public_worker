package com.hcn.common.app;

import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.hcn.common.utils.HConvertUtils;
import com.hcn.common.utils.HFileIOUtils;
import com.hcn.common.utils.HStringUtils;
import com.hcn.common.utils.HUtilsEx;
import com.hcn.skinx_night.NightSkinX;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HResourceUtils {
    private static final int BUFFER_SIZE = 8192;

    private HResourceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Drawable getDrawable(int i) {
        return ContextCompat.getDrawable(HUtilsEx.getApp(), i);
    }

    public static int getIdByName(String str) {
        return HUtilsEx.getApp().getResources().getIdentifier(str, "id", HUtilsEx.getApp().getPackageName());
    }

    public static int getStringIdByName(String str) {
        return HUtilsEx.getApp().getResources().getIdentifier(str, "string", HUtilsEx.getApp().getPackageName());
    }

    public static int getColorIdByName(String str) {
        return HUtilsEx.getApp().getResources().getIdentifier(str, NightSkinX.ResTypeName.COLOR, HUtilsEx.getApp().getPackageName());
    }

    public static int getDimenIdByName(String str) {
        return HUtilsEx.getApp().getResources().getIdentifier(str, "dimen", HUtilsEx.getApp().getPackageName());
    }

    public static int getDrawableIdByName(String str) {
        return HUtilsEx.getApp().getResources().getIdentifier(str, NightSkinX.ResTypeName.DRAWABLE, HUtilsEx.getApp().getPackageName());
    }

    public static int getMipmapIdByName(String str) {
        return HUtilsEx.getApp().getResources().getIdentifier(str, NightSkinX.ResTypeName.MIPMAP, HUtilsEx.getApp().getPackageName());
    }

    public static int getLayoutIdByName(String str) {
        return HUtilsEx.getApp().getResources().getIdentifier(str, "layout", HUtilsEx.getApp().getPackageName());
    }

    public static int getStyleIdByName(String str) {
        return HUtilsEx.getApp().getResources().getIdentifier(str, "style", HUtilsEx.getApp().getPackageName());
    }

    public static int getAnimIdByName(String str) {
        return HUtilsEx.getApp().getResources().getIdentifier(str, "anim", HUtilsEx.getApp().getPackageName());
    }

    public static int getMenuIdByName(String str) {
        return HUtilsEx.getApp().getResources().getIdentifier(str, "menu", HUtilsEx.getApp().getPackageName());
    }

    public static boolean copyFileFromAssets(String str, String str2) {
        try {
            String[] list = HUtilsEx.getApp().getAssets().list(str);
            if (list != null && list.length > 0) {
                boolean zCopyFileFromAssets = true;
                for (String str3 : list) {
                    zCopyFileFromAssets &= copyFileFromAssets(str + "/" + str3, str2 + "/" + str3);
                }
                return zCopyFileFromAssets;
            }
            return HFileIOUtils.writeFileFromIS(str2, HUtilsEx.getApp().getAssets().open(str));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String readAssets2String(String str) {
        return readAssets2String(str, null);
    }

    public static String readAssets2String(String str, String str2) {
        try {
            byte[] bArrInputStream2Bytes = HConvertUtils.inputStream2Bytes(HUtilsEx.getApp().getAssets().open(str));
            if (bArrInputStream2Bytes == null) {
                return "";
            }
            if (HStringUtils.isSpace(str2)) {
                return new String(bArrInputStream2Bytes);
            }
            try {
                return new String(bArrInputStream2Bytes, str2);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static List<String> readAssets2List(String str) {
        return readAssets2List(str, "");
    }

    public static List<String> readAssets2List(String str, String str2) {
        try {
            return HConvertUtils.inputStream2Lines(HUtilsEx.getApp().getResources().getAssets().open(str), str2);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static boolean copyFileFromRaw(int i, String str) {
        return HFileIOUtils.writeFileFromIS(str, HUtilsEx.getApp().getResources().openRawResource(i));
    }

    public static String readRaw2String(int i) {
        return readRaw2String(i, null);
    }

    public static String readRaw2String(int i, String str) {
        byte[] bArrInputStream2Bytes = HConvertUtils.inputStream2Bytes(HUtilsEx.getApp().getResources().openRawResource(i));
        if (bArrInputStream2Bytes == null) {
            return null;
        }
        if (HStringUtils.isSpace(str)) {
            return new String(bArrInputStream2Bytes);
        }
        try {
            return new String(bArrInputStream2Bytes, str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static List<String> readRaw2List(int i) {
        return readRaw2List(i, "");
    }

    public static List<String> readRaw2List(int i, String str) {
        return HConvertUtils.inputStream2Lines(HUtilsEx.getApp().getResources().openRawResource(i), str);
    }
}
