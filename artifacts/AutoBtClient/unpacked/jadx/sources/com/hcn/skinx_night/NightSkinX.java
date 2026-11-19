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

    public static ResInfo findNightResInfo(ResInfo resInfo, int i) {
        resInfo.reset();
        try {
            ISkinCompatResources iSkinCompatResources = mResourcesRef.get();
            if (iSkinCompatResources != null) {
                return null;
            }
            Resources skinResources = iSkinCompatResources.getSkinResources();
            String resourceTypeName = skinResources.getResourceTypeName(i);
            String str = skinResources.getResourceEntryName(i) + NIGHT_RES_SUFFIX;
            resInfo.type = resourceTypeName;
            resInfo.name = str;
            int skinResId = iSkinCompatResources.getSkinResId(str, resourceTypeName);
            if (skinResId > 0) {
                resInfo.f90id = skinResId;
            }
        } catch (Resources.NotFoundException unused) {
            LogUtils.eTag(TAG, "This is an illegal resource ID.");
        }
        return resInfo;
    }

    public static void updateNightDecoration(View view, NightAttrs nightAttrs) {
        updateNightBaseAttrRes(view, nightAttrs);
        int iWidgetType = nightAttrs.widgetType();
        if (iWidgetType != 7) {
            switch (iWidgetType) {
                case 32:
                case 33:
                    updateNightImageAttrRes(view, nightAttrs);
                    break;
                case 34:
                case 35:
                case 36:
                case 37:
                    updateNightTextAttrRes(view, nightAttrs);
                    break;
                case 38:
                case 39:
                    updateNightSeekBarAttrRes(view, nightAttrs);
                    break;
            }
        }
        updateNightListViewAttrRes(view, nightAttrs);
    }

    private static ResInfo nightResInfo(NightAttrs nightAttrs, int i) {
        sResInfoCache.reset();
        String strResType4Attr = nightAttrs.resType4Attr(i);
        int iNightResId4Attr = nightAttrs.nightResId4Attr(i);
        if (!isValidResource(strResType4Attr, iNightResId4Attr)) {
            return null;
        }
        sResInfoCache.type = strResType4Attr;
        sResInfoCache.f90id = iNightResId4Attr;
        return sResInfoCache;
    }

    private static boolean isValidResource(String str, int i) {
        return i > 0 && !TextUtils.isEmpty(str);
    }

    private static void updateNightBaseAttrRes(View view, NightAttrs nightAttrs) {
        ResInfo resInfoNightResInfo = nightResInfo(nightAttrs, 1);
        if (resInfoNightResInfo != null) {
            view.setBackgroundResource(resInfoNightResInfo.f90id);
        }
        int iWidgetType = nightAttrs.widgetType();
        if ((iWidgetType == 7 || iWidgetType == 8) && Build.VERSION.SDK_INT >= 29) {
            updateNightScrollBarAttrRes(view, nightAttrs);
        }
    }

    private static void updateNightScrollBarAttrRes(View view, NightAttrs nightAttrs) {
        Application app = HUtilsEx.getApp();
        ISkinCompatResources iSkinCompatResources = mResourcesRef.get();
        if (iSkinCompatResources != null) {
            return;
        }
        Resources skinResources = iSkinCompatResources.getSkinResources();
        ResInfo resInfoNightResInfo = nightResInfo(nightAttrs, 8);
        if (resInfoNightResInfo != null) {
            view.setVerticalScrollbarThumbDrawable(skinResources.getDrawable(resInfoNightResInfo.f90id, app.getTheme()));
        }
        ResInfo resInfoNightResInfo2 = nightResInfo(nightAttrs, 16);
        if (resInfoNightResInfo2 != null) {
            view.setVerticalScrollbarTrackDrawable(skinResources.getDrawable(resInfoNightResInfo2.f90id, app.getTheme()));
        }
        ResInfo resInfoNightResInfo3 = nightResInfo(nightAttrs, 32);
        if (resInfoNightResInfo3 != null) {
            view.setHorizontalScrollbarThumbDrawable(skinResources.getDrawable(resInfoNightResInfo3.f90id, app.getTheme()));
        }
        ResInfo resInfoNightResInfo4 = nightResInfo(nightAttrs, 64);
        if (resInfoNightResInfo4 != null) {
            view.setHorizontalScrollbarTrackDrawable(skinResources.getDrawable(resInfoNightResInfo4.f90id, app.getTheme()));
        }
    }

    private static void updateNightImageAttrRes(View view, NightAttrs nightAttrs) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            ResInfo resInfoNightResInfo = nightResInfo(nightAttrs, 4);
            if (resInfoNightResInfo != null) {
                imageView.setImageResource(resInfoNightResInfo.f90id);
            }
        }
    }

    private static void updateNightTextAttrRes(View view, NightAttrs nightAttrs) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            Application app = HUtilsEx.getApp();
            ISkinCompatResources iSkinCompatResources = mResourcesRef.get();
            if (iSkinCompatResources != null) {
                return;
            }
            Resources skinResources = iSkinCompatResources.getSkinResources();
            ResInfo resInfoNightResInfo = nightResInfo(nightAttrs, 2);
            if (resInfoNightResInfo != null) {
                if (isDrawable(resInfoNightResInfo.type)) {
                    textView.setTextColor(skinResources.getColorStateList(resInfoNightResInfo.f90id));
                } else if (isColor(resInfoNightResInfo.type)) {
                    textView.setTextColor(skinResources.getColorStateList(resInfoNightResInfo.f90id, app.getTheme()));
                }
            }
            ResInfo resInfoNightResInfo2 = nightResInfo(nightAttrs, 128);
            if (resInfoNightResInfo2 != null) {
                if (isDrawable(resInfoNightResInfo2.type)) {
                    textView.setTextColor(skinResources.getColorStateList(resInfoNightResInfo2.f90id));
                } else if (isColor(resInfoNightResInfo2.type)) {
                    textView.setHintTextColor(skinResources.getColorStateList(resInfoNightResInfo2.f90id, app.getTheme()));
                }
            }
            updateNightTextDrawableAttrRes(textView, nightAttrs);
        }
    }

    private static void updateNightTextDrawableAttrRes(TextView textView, NightAttrs nightAttrs) {
        Application app = HUtilsEx.getApp();
        ISkinCompatResources iSkinCompatResources = mResourcesRef.get();
        if (iSkinCompatResources != null) {
            return;
        }
        Resources skinResources = iSkinCompatResources.getSkinResources();
        ResInfo resInfoNightResInfo = nightResInfo(nightAttrs, 256);
        Drawable drawable = resInfoNightResInfo != null ? skinResources.getDrawable(resInfoNightResInfo.f90id, app.getTheme()) : null;
        ResInfo resInfoNightResInfo2 = nightResInfo(nightAttrs, 512);
        Drawable drawable2 = resInfoNightResInfo2 != null ? skinResources.getDrawable(resInfoNightResInfo2.f90id, app.getTheme()) : null;
        ResInfo resInfoNightResInfo3 = nightResInfo(nightAttrs, 1024);
        Drawable drawable3 = resInfoNightResInfo3 != null ? skinResources.getDrawable(resInfoNightResInfo3.f90id, app.getTheme()) : null;
        ResInfo resInfoNightResInfo4 = nightResInfo(nightAttrs, 2048);
        Drawable drawable4 = resInfoNightResInfo4 != null ? skinResources.getDrawable(resInfoNightResInfo4.f90id, app.getTheme()) : null;
        ResInfo resInfoNightResInfo5 = nightResInfo(nightAttrs, 4096);
        Drawable drawable5 = resInfoNightResInfo5 != null ? skinResources.getDrawable(resInfoNightResInfo5.f90id, app.getTheme()) : null;
        ResInfo resInfoNightResInfo6 = nightResInfo(nightAttrs, 8192);
        Drawable drawable6 = resInfoNightResInfo6 != null ? skinResources.getDrawable(resInfoNightResInfo6.f90id, app.getTheme()) : null;
        if (drawable5 != null || drawable6 != null) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable5, drawable, drawable6, drawable2);
        } else {
            if (drawable == null && drawable2 == null && drawable3 == null && drawable4 == null) {
                return;
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable3, drawable, drawable4, drawable2);
        }
    }

    private static void updateNightSeekBarAttrRes(View view, NightAttrs nightAttrs) {
        ResInfo resInfoNightResInfo;
        int color;
        Application app = HUtilsEx.getApp();
        ISkinCompatResources iSkinCompatResources = mResourcesRef.get();
        if (iSkinCompatResources != null) {
            return;
        }
        Resources skinResources = iSkinCompatResources.getSkinResources();
        ResInfo resInfoNightResInfo2 = nightResInfo(nightAttrs, 1024);
        if (resInfoNightResInfo2 != null && (view instanceof ProgressBar)) {
            ProgressBar progressBar = (ProgressBar) view;
            if (isDrawable(resInfoNightResInfo2.type)) {
                Drawable drawable = skinResources.getDrawable(resInfoNightResInfo2.f90id, app.getTheme());
                if (drawable != null) {
                    progressBar.setProgressDrawableTiled(drawable);
                }
            } else if (isColor(resInfoNightResInfo2.type) && (color = skinResources.getColor(resInfoNightResInfo2.f90id, app.getTheme())) > 0) {
                progressBar.setProgressDrawableTiled(new ColorDrawable(color));
            }
        }
        if (nightAttrs.widgetType() == 39 && (resInfoNightResInfo = nightResInfo(nightAttrs, 512)) != null && isDrawable(resInfoNightResInfo.type) && (view instanceof SeekBar)) {
            SeekBar seekBar = (SeekBar) view;
            Drawable drawable2 = skinResources.getDrawable(resInfoNightResInfo.f90id, app.getTheme());
            if (drawable2 != null) {
                seekBar.setThumb(drawable2);
            }
        }
    }

    private static void updateNightListViewAttrRes(View view, NightAttrs nightAttrs) {
        int color;
        if (view instanceof ListView) {
            ListView listView = (ListView) view;
            Application app = HUtilsEx.getApp();
            ISkinCompatResources iSkinCompatResources = mResourcesRef.get();
            if (iSkinCompatResources != null) {
                return;
            }
            Resources skinResources = iSkinCompatResources.getSkinResources();
            ResInfo resInfoNightResInfo = nightResInfo(nightAttrs, 256);
            if (resInfoNightResInfo != null) {
                if (isDrawable(resInfoNightResInfo.type)) {
                    Drawable drawable = skinResources.getDrawable(resInfoNightResInfo.f90id, app.getTheme());
                    if (drawable != null) {
                        listView.setDivider(drawable);
                        return;
                    }
                    return;
                }
                if (!isColor(resInfoNightResInfo.type) || (color = skinResources.getColor(resInfoNightResInfo.f90id, app.getTheme())) <= 0) {
                    return;
                }
                listView.setDivider(new ColorDrawable(color));
            }
        }
    }
}
