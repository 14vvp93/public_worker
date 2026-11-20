package com.hcn.skin2;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hcn.common.misc.LogUtils;
import com.hcn.common.utils.HFileUtils;
import com.hcn.common.utils.HUtilsEx;
import com.hcn.skin.support.SkinCompatManager;
import com.hcn.skin.support.SkinResourcesConfig;
import com.hcn.skin.support.resources.AsyncLayoutInflaterEx;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.skin2.data.Skin2DataX;
import com.hcn.skin2.enhance.IOverlayCallback;
import com.hcn.skin2.enhance.SkinExCompatResources;
import com.hcn.skin2.enhance.SkinExInflaterFactory2;
import com.hcn.skin2.runtime.RRO;
import com.hcn.skinx_night.NightSkinX;
import com.hcn.skinx_night.NightX;
import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class Skin2 {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static String MODULE_SKIN_PACKAGE_FILE_PREFIX = "";
    private static String MODULE_SKIN_PACKAGE_NAME_PREFIX = "";
    private static String MODULE_SKIN_PROP = "";
    private static final String SKIN2_LOG_LEVEL_PROP = "persist.sys.skin2.log";
    private static final String TAG = "Skin2";
    private static Skin2 sInstance;
    private Reference<Context> mContextRef = new WeakReference(HUtilsEx.getApp());

    /* JADX WARN: Code duplicated, block: B:53:0x00a0  */
    private static int skin2LogLevel() {
        switch (HUtilsEx.getSystemProperty(SKIN2_LOG_LEVEL_PROP, "D")) {
            case "v":
            case "V":
                return 2;
            case "i":
            case "I":
                return 4;
            case "w":
            case "W":
                return 5;
            case "e":
            case "E":
                return 6;
            case "d":
            case "D":
            default:
                return 3;
        }
    }

    private Skin2() {
        LogUtils.getConfig().setConsoleFilter(HUtilsEx.isAppDebug() ? 2 : skin2LogLevel());
        LogUtils.getConfig().setLogHeadSwitch(false);
        LogUtils.getConfig().setBorderSwitch(false);
        RRO.initOverlayManager(HUtilsEx.getApp());
        SkinResourcesConfig.setSkinResourcesClass(SkinExCompatResources.class);
        SkinExCompatResources.asyncLoadSupportEnable();
        SkinCompatManager.setSkinLayoutFactory2(SkinExInflaterFactory2.instance());
        SkinCompatManager.create(HUtilsEx.getApp());
    }

    public static Skin2 instance() {
        if (sInstance == null) {
            sInstance = new Skin2();
        }
        return sInstance;
    }

    public static Skin2 init(Application application) {
        HUtilsEx.init(application);
        return instance();
    }

    public Skin2 rroCallback(IOverlayCallback iOverlayCallback) {
        SkinExInflaterFactory2.setOverlayCallback(iOverlayCallback);
        return this;
    }

    public Skin2 configProp(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("configProp 'prop' is empty!");
        }
        MODULE_SKIN_PROP = str;
        return this;
    }

    public Skin2 configPackageFilePrefix(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("configPackageFilePrefix 'packageFilePrefix' is empty!");
        }
        MODULE_SKIN_PACKAGE_FILE_PREFIX = str;
        return this;
    }

    public Skin2 configPackageNamePrefix(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("configPackageNamePrefix 'packageNamePrefix' is empty!");
        }
        MODULE_SKIN_PACKAGE_NAME_PREFIX = str;
        return this;
    }

    public void setupSkin() {
        String systemProperty = HUtilsEx.getSystemProperty(MODULE_SKIN_PROP, "");
        if (TextUtils.isEmpty(systemProperty)) {
            LogUtils.wTag(TAG, "No extended skin package!");
            return;
        }
        String strCheckAndEnumSkinPackage = checkAndEnumSkinPackage(MODULE_SKIN_PACKAGE_FILE_PREFIX + systemProperty);
        if (TextUtils.isEmpty(strCheckAndEnumSkinPackage)) {
            if (HUtilsEx.isAppDebug()) {
                PackageManager packageManager = context().getPackageManager();
                String str = MODULE_SKIN_PACKAGE_NAME_PREFIX + systemProperty;
                try {
                    strCheckAndEnumSkinPackage = packageManager.getApplicationInfo(str, 0).sourceDir;
                    LogUtils.iTag(TAG, "detected skin file: " + strCheckAndEnumSkinPackage);
                } catch (PackageManager.NameNotFoundException unused) {
                    LogUtils.wTag(TAG, str + " not found!");
                    return;
                }
            } else {
                LogUtils.iTag(TAG, "not found media skin package!");
                return;
            }
        }
        syncLoadSkinPackage(strCheckAndEnumSkinPackage);
    }

    private void syncLoadSkinPackage(String str) {
        String strLoadSkinByPath = SkinCompatManager.getInstance().loadSkinByPath(str);
        if (!TextUtils.isEmpty(strLoadSkinByPath)) {
            LogUtils.wTag(TAG, "load skin package: " + strLoadSkinByPath);
        } else {
            SkinCompatResources.getInstance().resetSkin();
        }
    }

    private String checkAndEnumSkinPackage(String str) {
        String str2 = "/apd/app/" + str + ".apk";
        if (HFileUtils.isFileExists(str2)) {
            return str2;
        }
        String str3 = "/data/config/app/" + str + File.separator + str + ".apk";
        if (HFileUtils.isFileExists(str3)) {
            return str3;
        }
        String str4 = "/system/app" + File.separator + str + File.separator + str + ".apk";
        if (HFileUtils.isFileExists(str4)) {
            return str4;
        }
        File[] fileArrListFiles = new File("/system/app").listFiles();
        if (Objects.isNull(fileArrListFiles)) {
            LogUtils.dTag(TAG, "checkAndEnumSkinPackage: No access permissions to the /system/app directory!");
            return null;
        }
        for (File file : fileArrListFiles) {
            String name = file.getName();
            if (!TextUtils.isEmpty(name) && name.startsWith(MODULE_SKIN_PACKAGE_FILE_PREFIX)) {
                String str5 = ("/system/app" + File.separator + name) + File.separator + name + ".apk";
                if (HFileUtils.isFileExists(str5)) {
                    return str5;
                }
            }
        }
        LogUtils.wTag(TAG, "File: " + str + ", does not exist!");
        return null;
    }

    public static boolean useSkinPackage() {
        return SkinCompatResources.getInstance().getSkinResources() != null;
    }

    public Context context() {
        Context context = this.mContextRef.get();
        if (context != null) {
            return context;
        }
        throw new NullPointerException("The owner context of Skin2 is null!");
    }

    public static Context skinContext() {
        if (useSkinPackage()) {
            return SkinCompatResources.getInstance().getSkinContext();
        }
        throw new RuntimeException("Illegal call, extension skin pack not currently loaded!");
    }

    public static int xId(int i) {
        return useSkinPackage() ? SkinCompatResources.getInstance().getId(i) : i;
    }

    public static int getId(View view) {
        if (useSkinPackage()) {
            return SkinCompatResources.getInstance().getViewId(view);
        }
        return view.getId();
    }

    public static int xId(String str) {
        return xId(str, "id");
    }

    public static int xId(String str, String str2) {
        int skinResId;
        if (useSkinPackage() && (skinResId = SkinCompatResources.getInstance().getSkinResId(str, str2)) > 0) {
            return skinResId;
        }
        Context context = sInstance.context();
        return context.getResources().getIdentifier(str, str2, context.getPackageName());
    }

    private static int xId2(int i, String str) {
        if (!useSkinPackage()) {
            return i;
        }
        if (Skin2DataX.supportNightXDecorProtocol() && NightX.isNightMode()) {
            return NightX.xNightId(i, str);
        }
        int skinResId = SkinCompatResources.getInstance().getSkinResId(i, str);
        return skinResId > 0 ? skinResId : i;
    }

    private static int xSkinId2(String str, String str2) {
        int iXNightSkinId;
        if (useSkinPackage()) {
            int skinResId = SkinCompatResources.getInstance().getSkinResId(str, str2);
            if (Skin2DataX.supportNightXDecorProtocol() && NightX.isNightMode() && (iXNightSkinId = NightX.xNightSkinId(skinResId, str2)) > 0) {
                return iXNightSkinId;
            }
            if (skinResId > 0) {
                return skinResId;
            }
        }
        Context context = sInstance.context();
        return context.getResources().getIdentifier(str, str2, context.getPackageName());
    }

    public static int xDrawableId2(int i) {
        return xId2(i, NightSkinX.ResTypeName.DRAWABLE);
    }

    public static int xDrawableId2(String str) {
        return xSkinId2(str, NightSkinX.ResTypeName.DRAWABLE);
    }

    public static int xColorId2(int i) {
        return xId2(i, NightSkinX.ResTypeName.COLOR);
    }

    public static int xColorId2(String str) {
        return xSkinId2(str, NightSkinX.ResTypeName.COLOR);
    }

    public static int xAppId(int i) {
        if (useSkinPackage()) {
            Context context = sInstance.context();
            try {
                context.getResources().getResourceEntryName(i);
                return i;
            } catch (Resources.NotFoundException unused) {
                LogUtils.vTag(TAG, "It may be a skin package resource ID.");
                try {
                    Resources skinResources = SkinCompatResources.getInstance().getSkinResources();
                    return context.getResources().getIdentifier(skinResources.getResourceEntryName(i), skinResources.getResourceTypeName(i), context.getPackageName());
                } catch (Resources.NotFoundException unused2) {
                    LogUtils.eTag(TAG, "This is an illegal resource ID.");
                }
            }
        }
        return i;
    }

    public static int xAppId4Skin(int i) {
        if (useSkinPackage()) {
            Context context = sInstance.context();
            Resources skinResources = SkinCompatResources.getInstance().getSkinResources();
            try {
                return context.getResources().getIdentifier(skinResources.getResourceEntryName(i), skinResources.getResourceTypeName(i), context.getPackageName());
            } catch (Resources.NotFoundException unused) {
                LogUtils.eTag(TAG, "This is an illegal resource ID.");
            }
        }
        return i;
    }

    public static int getColor(int i) {
        if (useSkinPackage()) {
            return SkinCompatResources.getInstance().getColor(i);
        }
        return instance().context().getColor(i);
    }

    public static int getColor(String str) {
        return SkinCompatResources.getInstance().getColor(str);
    }

    public static int getColor2(String str) {
        if (Skin2DataX.supportNightXDecorProtocol() && NightX.isNightMode()) {
            return SkinCompatResources.getInstance().getColor(str + NightSkinX.NIGHT_RES_SUFFIX);
        }
        return SkinCompatResources.getInstance().getColor(str);
    }

    public static int getColor2(int i) {
        if (useSkinPackage()) {
            try {
                return SkinCompatResources.getInstance().getSkinResources().getColor(xId2(i, NightSkinX.ResTypeName.COLOR));
            } catch (Resources.NotFoundException e) {
                LogUtils.eTag(TAG, "skin getColor2 failed. reason: " + e);
            }
        }
        return instance().context().getColor(i);
    }

    public static ColorStateList getColorStateList(int i) {
        if (useSkinPackage()) {
            return SkinCompatResources.getInstance().getColorStateList(i);
        }
        return instance().context().getColorStateList(i);
    }

    public static ColorStateList getColorStateList(String str) {
        return SkinCompatResources.getInstance().getColorStateList(str);
    }

    public static Drawable getDrawable(int i) {
        if (useSkinPackage()) {
            return SkinCompatResources.getInstance().getDrawable(i);
        }
        return instance().context().getDrawable(i);
    }

    public static Drawable getDrawable(String str) {
        return SkinCompatResources.getInstance().getDrawable(str);
    }

    public static Drawable getDrawable2(String str) {
        if (Skin2DataX.supportNightXDecorProtocol() && NightX.isNightMode()) {
            Drawable drawable = SkinCompatResources.getInstance().getDrawable(str + NightSkinX.NIGHT_RES_SUFFIX);
            if (drawable != null) {
                return drawable;
            }
        }
        return SkinCompatResources.getInstance().getDrawable(str);
    }

    public static Drawable getDrawable2(int i) {
        if (useSkinPackage()) {
            try {
                return SkinCompatResources.getInstance().getSkinResources().getDrawable(xId2(i, NightSkinX.ResTypeName.DRAWABLE));
            } catch (Resources.NotFoundException e) {
                LogUtils.eTag(TAG, "skin getDrawable2 failed. reason: " + e);
            }
        }
        return instance().context().getDrawable(i);
    }

    public static String xString(int i) {
        return getString(xAppId(i));
    }

    public static String xString(int i, boolean z) {
        if (z) {
            return getString(xAppId4Skin(i));
        }
        return getString(i);
    }

    public static String getString(int i) {
        if (useSkinPackage()) {
            return SkinCompatResources.getInstance().getString(i);
        }
        return instance().context().getString(i);
    }

    public static String getString(String str) {
        int skinResId;
        if (RRO.instance().isEnabled()) {
            return SkinCompatResources.getInstance().getString(str);
        }
        if (useSkinPackage() && (skinResId = SkinCompatResources.getInstance().getSkinResId(str, "string")) > 0) {
            try {
                return SkinCompatResources.getInstance().getSkinResources().getString(skinResId);
            } catch (Resources.NotFoundException unused) {
            }
        }
        int appResId = SkinCompatResources.getInstance().getAppResId(str, "string");
        if (appResId <= 0) {
            return "undefined";
        }
        try {
            return instance().context().getResources().getString(appResId);
        } catch (Resources.NotFoundException unused2) {
            return "undefined";
        }
    }

    public static boolean getBoolean(int i) {
        return getBoolean(i, false);
    }

    public static boolean getBoolean(int i, boolean z) {
        if (useSkinPackage()) {
            try {
                int skinResId = SkinCompatResources.getInstance().getSkinResId(i);
                if (skinResId > 0) {
                    return SkinCompatResources.getInstance().getSkinResources().getBoolean(skinResId);
                }
            } catch (Resources.NotFoundException unused) {
            }
        }
        try {
            return instance().context().getResources().getBoolean(i);
        } catch (Resources.NotFoundException unused2) {
            return false;
        }
    }

    public static boolean getBoolean(String str) {
        return getBoolean(str, false);
    }

    public static boolean getBoolean(String str, boolean z) {
        int skinResId;
        if (useSkinPackage() && (skinResId = SkinCompatResources.getInstance().getSkinResId(str, "bool")) > 0) {
            try {
                return SkinCompatResources.getInstance().getSkinResources().getBoolean(skinResId);
            } catch (Resources.NotFoundException unused) {
            }
        }
        int appResId = SkinCompatResources.getInstance().getAppResId(str, "bool");
        if (appResId > 0) {
            try {
                return instance().context().getResources().getBoolean(appResId);
            } catch (Resources.NotFoundException unused2) {
            }
        }
        return z;
    }

    public static int getInteger(int i) {
        if (useSkinPackage()) {
            try {
                int skinResId = SkinCompatResources.getInstance().getSkinResId(i);
                if (skinResId > 0) {
                    return SkinCompatResources.getInstance().getSkinResources().getInteger(skinResId);
                }
            } catch (Resources.NotFoundException unused) {
            }
        }
        try {
            return instance().context().getResources().getInteger(i);
        } catch (Resources.NotFoundException unused2) {
            return 0;
        }
    }

    public static int getInteger(String str) {
        int skinResId;
        if (useSkinPackage() && (skinResId = SkinCompatResources.getInstance().getSkinResId(str, "integer")) > 0) {
            try {
                return SkinCompatResources.getInstance().getSkinResources().getInteger(skinResId);
            } catch (Resources.NotFoundException unused) {
            }
        }
        int appResId = SkinCompatResources.getInstance().getAppResId(str, "integer");
        if (appResId <= 0) {
            return 0;
        }
        try {
            return instance().context().getResources().getInteger(appResId);
        } catch (Resources.NotFoundException unused2) {
            return 0;
        }
    }

    public static float getDimension(int i) {
        if (useSkinPackage()) {
            try {
                int skinResId = SkinCompatResources.getInstance().getSkinResId(i);
                if (skinResId > 0) {
                    return SkinCompatResources.getInstance().getSkinResources().getDimension(skinResId);
                }
            } catch (Resources.NotFoundException unused) {
            }
        }
        try {
            return instance().context().getResources().getDimension(i);
        } catch (Resources.NotFoundException unused2) {
            return 0.0f;
        }
    }

    public static float getDimension(String str) {
        int skinResId;
        if (useSkinPackage() && (skinResId = SkinCompatResources.getInstance().getSkinResId(str, "dimen")) > 0) {
            try {
                return SkinCompatResources.getInstance().getSkinResources().getDimension(skinResId);
            } catch (Resources.NotFoundException unused) {
            }
        }
        int appResId = SkinCompatResources.getInstance().getAppResId(str, "dimen");
        if (appResId <= 0) {
            return 0.0f;
        }
        try {
            return instance().context().getResources().getDimension(appResId);
        } catch (Resources.NotFoundException unused2) {
            return 0.0f;
        }
    }

    public static String[] xStringArray(int i) {
        return getStringArray(xAppId(i));
    }

    public static String[] xStringArray(int i, boolean z) {
        if (z) {
            return getStringArray(xAppId4Skin(i));
        }
        return getStringArray(i);
    }

    public static String[] xStringArrayEx(int i) {
        return getStringArrayEx(xAppId(i));
    }

    public static String[] getStringArray(int i) {
        if (useSkinPackage()) {
            return SkinCompatResources.getInstance().getStringArray(i);
        }
        return instance().context().getResources().getStringArray(i);
    }

    public static String[] getStringArrayEx(int i) {
        return StringArrayCache.getStringArrayEx(i);
    }

    public static int[] xIntArray(int i) {
        return getIntArray(xAppId(i));
    }

    public static int[] xIntArray(int i, boolean z) {
        if (z) {
            return getIntArray(xAppId4Skin(i));
        }
        return getIntArray(i);
    }

    public static int[] getIntArray(int i) {
        if (useSkinPackage()) {
            return SkinCompatResources.getInstance().getIntArray(i);
        }
        return instance().context().getResources().getIntArray(i);
    }

    public static View inflate(int i, ViewGroup viewGroup, boolean z) {
        int skinResId;
        if (useSkinPackage() && (skinResId = SkinCompatResources.getInstance().getSkinResId(i)) > 0) {
            SkinCompatResources.getInstance().skinInflaterFactorySet(SkinExInflaterFactory2.instance());
            View viewInflate = LayoutInflater.from(SkinCompatResources.getInstance().getSkinContext()).inflate(skinResId, viewGroup, z);
            viewInflate.setTag(i, Boolean.TRUE);
            return viewInflate;
        }
        View viewInflate2 = LayoutInflater.from(instance().context()).inflate(i, viewGroup, z);
        viewInflate2.setTag(i, Boolean.FALSE);
        return viewInflate2;
    }

    public static void asyncSkinInflate(int i, AsyncLayoutInflaterEx.OnInflateFinishedListener onInflateFinishedListener) {
        int skinResId;
        if (!useSkinPackage() || (skinResId = SkinCompatResources.getInstance().getSkinResId(i)) <= 0) {
            return;
        }
        SkinCompatResources.getInstance().skinInflaterFactorySet(SkinExInflaterFactory2.instance());
        new AsyncLayoutInflaterEx(skinContext()).inflate(skinResId, null, onInflateFinishedListener);
    }

    public static Locale getCurrentLocale() {
        Configuration configuration = instance().context().getResources().getConfiguration();
        if (Build.VERSION.SDK_INT < 24) {
            return configuration.locale;
        }
        return configuration.getLocales().get(0);
    }

    private static class StringArrayCache {
        private static final String STRING = "#string/";
        private static final LruCache<Integer, String[]> cache = new LruCache<>(50);
        private static Locale lastLocale;

        private StringArrayCache() {
        }

        private static String[] resolveStringArrayId(String[] strArr) {
            if (strArr == null) {
                return new String[0];
            }
            String[] strArr2 = new String[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                String str = strArr[i];
                if (str != null && str.startsWith(STRING)) {
                    strArr2[i] = Skin2.getString(str.substring(8));
                } else {
                    strArr2[i] = str;
                }
            }
            return strArr2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String[] getStringArrayEx(int i) {
            Locale currentLocale = Skin2.getCurrentLocale();
            Locale locale = lastLocale;
            if (locale == null || !locale.equals(currentLocale)) {
                cleanCached();
                lastLocale = currentLocale;
            }
            LruCache<Integer, String[]> lruCache = cache;
            String[] strArr = lruCache.get(Integer.valueOf(i));
            if (strArr != null) {
                return strArr;
            }
            String[] strArrResolveStringArrayId = resolveStringArrayId((String[]) Objects.requireNonNull(Skin2.getStringArray(i)));
            lruCache.put(Integer.valueOf(i), strArrResolveStringArrayId);
            return strArrResolveStringArrayId;
        }

        private static void cleanCached() {
            cache.evictAll();
        }
    }
}
