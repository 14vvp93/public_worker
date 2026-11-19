package com.hcn.skin2;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hcn.common.misc.LogUtils;
import com.hcn.common.utils.HUtilsEx;
import com.hcn.skin.support.SkinCompatManager;
import com.hcn.skin.support.SkinResourcesConfig;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.skin2.enhance.IOverlayCallback;
import com.hcn.skin2.enhance.SkinExCompatResources;
import com.hcn.skin2.enhance.SkinExInflaterFactory2;
import com.hcn.skin2.runtime.RRO;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class Skin2 {
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

    public static boolean useSkinPackage() {
        return SkinCompatResources.getInstance().getSkinResources() != null;
    }

    public Context context() {
        Context context = this.mContextRef.get();
        Objects.requireNonNull(context, "The owner context of Skin2 is null!");
        return context;
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

    public static String[] xStringArray(int i) {
        return getStringArray(xAppId(i));
    }

    public static String[] xStringArray(int i, boolean z) {
        if (z) {
            return getStringArray(xAppId4Skin(i));
        }
        return getStringArray(i);
    }

    public static String[] getStringArray(int i) {
        if (useSkinPackage()) {
            return SkinCompatResources.getInstance().getStringArray(i);
        }
        return instance().context().getResources().getStringArray(i);
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
}
