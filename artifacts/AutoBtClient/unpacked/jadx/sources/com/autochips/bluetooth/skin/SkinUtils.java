package com.autochips.bluetooth.skin;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.hcn.skin.support.SkinCompatManager;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.skin.support.utils.SkinFileUtils;
import com.hcn.skin2.Skin2;
import com.hcn.skin2.enhance.IOverlayCallback;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class SkinUtils {
    private static final String BLUETOOTH_SKIN_APK_PREFIX = "BTSkins_";
    private static final String BLUETOOTH_SKIN_PACKAGE_PREFIX = "com.hcn.bluetooth.skins.";
    private static final String BLUETOOTH_SKIN_PROP = "persist.sys.bluetooth.skins";
    private static final String TAG = "SkinUtils";
    private static Context mContext = null;
    private static String mCurrentSkinID = "";
    private static SkinCompatManager mSkinManager;

    public static void init(Context context) {
        if (mContext != null) {
            Log.w(TAG, "repeated calls to initialization functions!");
            return;
        }
        mContext = context;
        Skin2.init((Application) context);
        mSkinManager = SkinCompatManager.getInstance();
        Skin2.instance().rroCallback(new IOverlayCallback() { // from class: com.autochips.bluetooth.skin.SkinUtils.1
            @Override // com.hcn.skin2.enhance.IOverlayCallback
            public boolean isSupportRROView(String str) {
                return false;
            }

            @Override // com.hcn.skin2.enhance.IOverlayCallback
            public View tryReplaceAndCreateView(String str, Context context2, AttributeSet attributeSet) {
                if ("android.support.v7.widget.RecyclerView".equals(str)) {
                    return new RecyclerView(context2, attributeSet);
                }
                return null;
            }
        });
        String systemProp = getSystemProp(BLUETOOTH_SKIN_PROP, SkinID.SKIN_NONE);
        String str = BLUETOOTH_SKIN_PACKAGE_PREFIX + systemProp;
        String str2 = TAG;
        Log.d(str2, "Bluetooth skin init: skinName = " + systemProp);
        if (TextUtils.isEmpty(systemProp)) {
            return;
        }
        try {
            if (context.getPackageManager().getApplicationInfo(str, 0) != null) {
                Log.d(str2, "Load bluetooth skin by packageName = " + str);
                mSkinManager.loadSkinByPackage(str);
                mCurrentSkinID = systemProp.toLowerCase();
            }
        } catch (PackageManager.NameNotFoundException unused) {
            String str3 = TAG;
            Log.d(str3, "can't not find packageName: " + str);
            String str4 = BLUETOOTH_SKIN_APK_PREFIX + systemProp.toLowerCase() + ".apk";
            String str5 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + SkinFileUtils.SKIN_DIR_NAME + File.separator + str4;
            if (isFileExists(str5)) {
                Log.d(str3, "Load bluetooth skin by apk = " + str5);
                mSkinManager.loadSkinSync(str4);
                mCurrentSkinID = systemProp.toLowerCase();
            } else {
                Log.d(str3, str5 + ", does not exist!");
                mCurrentSkinID = SkinID.SKIN_NONE;
            }
        }
    }

    public static String getCurrentSkinID() {
        return mCurrentSkinID;
    }

    public static boolean isSkinID(String str) {
        return Objects.equals(mCurrentSkinID, str);
    }

    public static boolean useSkinPackage() {
        return SkinCompatResources.getInstance().getSkinResources() != null;
    }

    public static Context context() {
        Context context = mContext;
        Objects.requireNonNull(context, "The owner context is null!");
        return context;
    }

    public static Context getContext() {
        if (useSkinPackage()) {
            return SkinCompatResources.getInstance().getSkinContext();
        }
        return context();
    }

    public static int getId(int i) {
        return Skin2.xId(i);
    }

    public static String getString(int i) {
        if (useSkinPackage()) {
            return SkinCompatResources.getInstance().getString(i);
        }
        return context().getString(i);
    }

    public static int getColor(int i) {
        if (useSkinPackage()) {
            return SkinCompatResources.getInstance().getColor(i);
        }
        return context().getColor(i);
    }

    public static int getInteger(int i) {
        if (isResourcesExist(i) > 0) {
            return SkinCompatResources.getInstance().getInteger(i);
        }
        return context().getResources().getInteger(i);
    }

    public static int getViewId(View view) {
        return SkinCompatResources.getInstance().getViewId(view);
    }

    public static int getResId(int i) {
        return useSkinPackage() ? SkinCompatResources.getInstance().getSkinResId(i) : i;
    }

    public static Drawable getDrawable(int i) {
        if (useSkinPackage()) {
            return SkinCompatResources.getInstance().getDrawable(i);
        }
        return context().getResources().getDrawable(i, null);
    }

    public static View getLayout(int i) {
        return Skin2.inflate(i, null, false);
    }

    public static View getLayout(String str) {
        if (useSkinPackage()) {
            return SkinCompatResources.getInstance().getLayout(str);
        }
        return LayoutInflater.from(context()).inflate(mContext.getResources().getIdentifier(str, "layout", mContext.getPackageName()), (ViewGroup) null);
    }

    public static int isResourcesExist(int i) {
        if (useSkinPackage()) {
            return SkinCompatResources.getInstance().getSkinResId(i);
        }
        return 0;
    }

    public static int isResourcesExist(String str, String str2) {
        if (useSkinPackage()) {
            try {
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    return SkinCompatResources.getInstance().getSkinResources().getIdentifier(str, str2, SkinCompatResources.getInstance().getSkinPkgName());
                }
            } catch (Exception unused) {
            }
        }
        return 0;
    }

    public static File getFileByPath(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return new File(str);
    }

    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    public static boolean isFileExists(String str) {
        return isFileExists(getFileByPath(str));
    }

    public static int getIntProp(String str, int i) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return ((Integer) cls.getMethod("getInt", String.class, Integer.TYPE).invoke(cls, str, Integer.valueOf(i))).intValue();
        } catch (Exception unused) {
            return i;
        }
    }

    public static String getSystemProp(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, str2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return SkinID.SKIN_NONE;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return SkinID.SKIN_NONE;
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
            return SkinID.SKIN_NONE;
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
            return SkinID.SKIN_NONE;
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
            return SkinID.SKIN_NONE;
        }
    }

    public static void setSystemProp(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            cls.getMethod("set", String.class, String.class).invoke(cls, new String(str), str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
