package com.hcn.skinx_night;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import com.hcn.common.misc.LogUtils;
import com.hcn.common.utils.HUtilsEx;
import com.hcn.skin.share.ISkinCompatResources;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class NightSkinX {
    public static final String NIGHT_RES_SUFFIX = "_night";
    private static final String TAG = "NightSkinX";
    private static WeakReference<ISkinCompatResources> mResourcesRef;
    private static ResInfo sResInfoCache = new ResInfo();

    private interface ResTypeName {
        public static final String COLOR = "color";
        public static final String DRAWABLE = "drawable";
        public static final String MIPMAP = "mipmap";
    }

    public static boolean isDrawable(String str) {
        return ResTypeName.DRAWABLE.equals(str) || ResTypeName.MIPMAP.equals(str);
    }

    public static boolean isColor(String str) {
        return ResTypeName.COLOR.equals(str);
    }

    public static void setSkinCompatResources(ISkinCompatResources iSkinCompatResources) {
        if (mResourcesRef != null) {
            throw new RuntimeException("The 'setSkinCompatResources(...)' method is repeatedly called!");
        }
        mResourcesRef = new WeakReference<>(iSkinCompatResources);
    }

    public static boolean useSkinPackage() {
        ISkinCompatResources iSkinCompatResources = mResourcesRef.get();
        return (iSkinCompatResources == null || iSkinCompatResources.getSkinResources() == null) ? false : true;
    }

    public static int getSkinResId(int i, String str) {
        ISkinCompatResources iSkinCompatResources = mResourcesRef.get();
        return iSkinCompatResources == null ? i : iSkinCompatResources.getSkinResId(i, str);
    }

    public static ResInfo findNightResInfo(int i) {
        sResInfoCache.reset();
        try {
            ISkinCompatResources iSkinCompatResources = mResourcesRef.get();
            if (iSkinCompatResources == null) {
                LogUtils.wTag(TAG, "findNightResInfo: ISkinCompatResources null");
                return sResInfoCache;
            }
            Resources skinResources = iSkinCompatResources.getSkinResources();
            String resourceTypeName = skinResources.getResourceTypeName(i);
            String str = skinResources.getResourceEntryName(i) + NIGHT_RES_SUFFIX;
            sResInfoCache.type = resourceTypeName;
            sResInfoCache.name = str;
            int skinResId = iSkinCompatResources.getSkinResId(str, resourceTypeName);
            if (skinResId > 0) {
                sResInfoCache.id = skinResId;
            }
            LogUtils.vTag(TAG, "findNightResInfo info.type:  " + sResInfoCache.type + " info.name: " + sResInfoCache.name + " info.id: " + sResInfoCache.id);
            return sResInfoCache;
        } catch (Exception e) {
            LogUtils.eTag(TAG, "This is an illegal resource ID. " + e);
        }
    }

    public static ResInfo findNightResInfo(int i, String str) {
        sResInfoCache.reset();
        try {
            ISkinCompatResources iSkinCompatResources = mResourcesRef.get();
            if (iSkinCompatResources == null) {
                LogUtils.wTag(TAG, "findNightResInfo failed: ISkinCompatResources == null");
                return sResInfoCache;
            }
            String str2 = iSkinCompatResources.getSkinResources().getResourceEntryName(i) + NIGHT_RES_SUFFIX;
            sResInfoCache.type = str;
            sResInfoCache.name = str2;
            int skinResId = iSkinCompatResources.getSkinResId(str2, str);
            if (skinResId > 0) {
                sResInfoCache.id = skinResId;
            }
            LogUtils.vTag(TAG, "findNightResInfo info.type:  " + sResInfoCache.type + " info.name: " + sResInfoCache.name + " info.id: " + sResInfoCache.id);
            return sResInfoCache;
        } catch (Exception e) {
            LogUtils.eTag(TAG, "This is an illegal resource ID. " + e);
        }
    }

    public static void updateDecoration(View view, NightAttrs nightAttrs, boolean z) {
        Boolean bool = (Boolean) view.getTag(NightX.TAG_KEY_NIGHT_MODE);
        if (bool == null && !z) {
            LogUtils.vTag(TAG, "no-updateDecoration reason: (day init) don't need update ");
            return;
        }
        if (bool != null && bool.booleanValue() == z) {
            LogUtils.vTag(TAG, "no-updateDecoration reason: repeat the update ");
            return;
        }
        updateBaseAttrRes(view, nightAttrs, z);
        int iWidgetType = nightAttrs.widgetType();
        if (iWidgetType != 7) {
            switch (iWidgetType) {
                case 32:
                case 33:
                    updateImageAttrRes(view, nightAttrs, z);
                    break;
                case 34:
                case 35:
                case 36:
                case 37:
                    updateTextAttrRes(view, nightAttrs, z);
                    break;
                case 38:
                case 39:
                    updateSeekBarAttrRes(view, nightAttrs, z);
                    break;
            }
        } else {
            updateListViewAttrRes(view, nightAttrs, z);
        }
        if (z) {
            LogUtils.vTag(TAG, "updateDecoration-night: " + nightAttrs.widgetType());
        } else {
            LogUtils.vTag(TAG, "updateDecoration-day: " + nightAttrs.widgetType());
        }
        view.setTag(NightX.TAG_KEY_NIGHT_MODE, Boolean.valueOf(z));
    }

    private static ResInfo getResInfo(NightAttrs nightAttrs, int i, boolean z) {
        sResInfoCache.reset();
        String strResType4Attr = nightAttrs.resType4Attr(i);
        int iNightResId4Attr = z ? nightAttrs.nightResId4Attr(i) : nightAttrs.dayResId4Attr(i);
        if (!isValidResource(strResType4Attr, iNightResId4Attr)) {
            return null;
        }
        sResInfoCache.type = strResType4Attr;
        sResInfoCache.id = iNightResId4Attr;
        return sResInfoCache;
    }

    private static boolean isValidResource(String str, int i) {
        return i > 0 && !TextUtils.isEmpty(str);
    }

    private static void updateBaseAttrRes(View view, NightAttrs nightAttrs, boolean z) {
        ResInfo resInfo = getResInfo(nightAttrs, 1, z);
        if (resInfo != null) {
            view.setBackgroundResource(resInfo.id);
        }
        int iWidgetType = nightAttrs.widgetType();
        if ((iWidgetType == 7 || iWidgetType == 8) && Build.VERSION.SDK_INT >= 29) {
            updateScrollBarAttrRes(view, nightAttrs, z);
        }
    }

    private static void updateScrollBarAttrRes(View view, NightAttrs nightAttrs, boolean z) {
        Application app = HUtilsEx.getApp();
        ISkinCompatResources iSkinCompatResources = mResourcesRef.get();
        if (iSkinCompatResources == null) {
            return;
        }
        Resources skinResources = iSkinCompatResources.getSkinResources();
        ResInfo resInfo = getResInfo(nightAttrs, 8, z);
        if (resInfo != null) {
            view.setVerticalScrollbarThumbDrawable(skinResources.getDrawable(resInfo.id, app.getTheme()));
        }
        ResInfo resInfo2 = getResInfo(nightAttrs, 16, z);
        if (resInfo2 != null) {
            view.setVerticalScrollbarTrackDrawable(skinResources.getDrawable(resInfo2.id, app.getTheme()));
        }
        ResInfo resInfo3 = getResInfo(nightAttrs, 32, z);
        if (resInfo3 != null) {
            view.setHorizontalScrollbarThumbDrawable(skinResources.getDrawable(resInfo3.id, app.getTheme()));
        }
        ResInfo resInfo4 = getResInfo(nightAttrs, 64, z);
        if (resInfo4 != null) {
            view.setHorizontalScrollbarTrackDrawable(skinResources.getDrawable(resInfo4.id, app.getTheme()));
        }
    }

    private static void updateImageAttrRes(View view, NightAttrs nightAttrs, boolean z) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            ResInfo resInfo = getResInfo(nightAttrs, 4, z);
            if (resInfo != null) {
                imageView.setImageResource(resInfo.id);
            }
        }
    }

    private static void updateTextAttrRes(View view, NightAttrs nightAttrs, boolean z) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            Application app = HUtilsEx.getApp();
            ISkinCompatResources iSkinCompatResources = mResourcesRef.get();
            if (iSkinCompatResources == null) {
                return;
            }
            Resources skinResources = iSkinCompatResources.getSkinResources();
            ResInfo resInfo = getResInfo(nightAttrs, 2, z);
            if (resInfo != null) {
                if (isDrawable(resInfo.type)) {
                    textView.setTextColor(skinResources.getColorStateList(resInfo.id));
                } else if (isColor(resInfo.type)) {
                    textView.setTextColor(skinResources.getColorStateList(resInfo.id, app.getTheme()));
                }
            }
            ResInfo resInfo2 = getResInfo(nightAttrs, 128, z);
            if (resInfo2 != null) {
                if (isDrawable(resInfo2.type)) {
                    textView.setTextColor(skinResources.getColorStateList(resInfo2.id));
                } else if (isColor(resInfo2.type)) {
                    textView.setHintTextColor(skinResources.getColorStateList(resInfo2.id, app.getTheme()));
                }
            }
            updateTextDrawableAttrRes(textView, nightAttrs, z);
        }
    }

    private static void updateTextDrawableAttrRes(TextView textView, NightAttrs nightAttrs, boolean z) {
        Application app = HUtilsEx.getApp();
        ISkinCompatResources iSkinCompatResources = mResourcesRef.get();
        if (iSkinCompatResources == null) {
            return;
        }
        Resources skinResources = iSkinCompatResources.getSkinResources();
        ResInfo resInfo = getResInfo(nightAttrs, 256, z);
        Drawable drawable = resInfo != null ? skinResources.getDrawable(resInfo.id, app.getTheme()) : null;
        ResInfo resInfo2 = getResInfo(nightAttrs, 512, z);
        Drawable drawable2 = resInfo2 != null ? skinResources.getDrawable(resInfo2.id, app.getTheme()) : null;
        ResInfo resInfo3 = getResInfo(nightAttrs, 1024, z);
        Drawable drawable3 = resInfo3 != null ? skinResources.getDrawable(resInfo3.id, app.getTheme()) : null;
        ResInfo resInfo4 = getResInfo(nightAttrs, 2048, z);
        Drawable drawable4 = resInfo4 != null ? skinResources.getDrawable(resInfo4.id, app.getTheme()) : null;
        ResInfo resInfo5 = getResInfo(nightAttrs, 4096, z);
        Drawable drawable5 = resInfo5 != null ? skinResources.getDrawable(resInfo5.id, app.getTheme()) : null;
        ResInfo resInfo6 = getResInfo(nightAttrs, 8192, z);
        Drawable drawable6 = resInfo6 != null ? skinResources.getDrawable(resInfo6.id, app.getTheme()) : null;
        if (drawable5 != null || drawable6 != null) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable5, drawable, drawable6, drawable2);
        } else {
            if (drawable == null && drawable2 == null && drawable3 == null && drawable4 == null) {
                return;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable3, drawable, drawable4, drawable2);
        }
    }

    private static void updateSeekBarAttrRes(View view, NightAttrs nightAttrs, boolean z) {
        ResInfo resInfo;
        int color;
        Application app = HUtilsEx.getApp();
        ISkinCompatResources iSkinCompatResources = mResourcesRef.get();
        if (iSkinCompatResources == null) {
            return;
        }
        Resources skinResources = iSkinCompatResources.getSkinResources();
        ResInfo resInfo2 = getResInfo(nightAttrs, 1024, z);
        if (resInfo2 != null && (view instanceof ProgressBar)) {
            ProgressBar progressBar = (ProgressBar) view;
            if (isDrawable(resInfo2.type)) {
                Drawable drawable = skinResources.getDrawable(resInfo2.id, app.getTheme());
                if (drawable != null) {
                    progressBar.setProgressDrawableTiled(drawable);
                }
            } else if (isColor(resInfo2.type) && (color = skinResources.getColor(resInfo2.id, app.getTheme())) > 0) {
                progressBar.setProgressDrawableTiled(new ColorDrawable(color));
            }
        }
        if (nightAttrs.widgetType() == 39 && (resInfo = getResInfo(nightAttrs, 512, z)) != null && isDrawable(resInfo.type) && (view instanceof SeekBar)) {
            SeekBar seekBar = (SeekBar) view;
            Drawable drawable2 = skinResources.getDrawable(resInfo.id, app.getTheme());
            if (drawable2 != null) {
                seekBar.setThumb(drawable2);
            }
        }
    }

    private static void updateListViewAttrRes(View view, NightAttrs nightAttrs, boolean z) {
        int color;
        if (view instanceof ListView) {
            ListView listView = (ListView) view;
            Application app = HUtilsEx.getApp();
            ISkinCompatResources iSkinCompatResources = mResourcesRef.get();
            if (iSkinCompatResources == null) {
                return;
            }
            Resources skinResources = iSkinCompatResources.getSkinResources();
            ResInfo resInfo = getResInfo(nightAttrs, 256, z);
            if (resInfo != null) {
                if (isDrawable(resInfo.type)) {
                    Drawable drawable = skinResources.getDrawable(resInfo.id, app.getTheme());
                    if (drawable != null) {
                        listView.setDivider(drawable);
                        return;
                    }
                    return;
                }
                if (!isColor(resInfo.type) || (color = skinResources.getColor(resInfo.id, app.getTheme())) <= 0) {
                    return;
                }
                listView.setDivider(new ColorDrawable(color));
            }
        }
    }
}
