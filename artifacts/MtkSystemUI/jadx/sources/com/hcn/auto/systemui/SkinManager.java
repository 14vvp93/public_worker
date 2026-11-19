package com.hcn.auto.systemui;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import com.hcn.skin.support.SkinCompatManager;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class SkinManager {
    private static volatile SkinManager sInstance;
    private Context mContext;
    private String mSkinPath = null;

    public static SkinManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (SkinManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinManager(context);
                }
            }
        }
        return sInstance;
    }

    private SkinManager(Context context) {
        this.mContext = null;
        this.mContext = context;
        SkinCompatManager.create(context);
    }

    public void loadSkin() {
        String skinName = null;
        String configSkin = SystemProperties.get("persist.sys.systemui.skin", "");
        Log.d("SkinManager", "loadSkin:" + configSkin);
        if (!"".equals(configSkin)) {
            skinName = configSkin;
        } else {
            String launcherSkin = SystemProperties.get("persist.sys.skin", "");
            Log.d("SkinManager", "load launcherSkin:" + launcherSkin);
            if (!"".equals(launcherSkin)) {
                skinName = launcherSkin;
            }
        }
        if (skinName != null) {
            String skinApkPath = getApkPathByPackageName("com.hcn.systemui.theme." + skinName);
            if (skinApkPath == null) {
                skinApkPath = getApkPathBySkinName(skinName);
            }
            if (skinApkPath != null) {
                loadSkinByPath(skinApkPath);
            }
        }
    }

    public boolean loadSkinByPath(String skinApkPath) {
        Log.v("SkinManager", "loadSkinByPath " + skinApkPath);
        if (TextUtils.isEmpty(skinApkPath)) {
            return false;
        }
        String getPath = SkinCompatManager.getInstance().loadSkinByPath(skinApkPath);
        return getPath != null;
    }

    public String getApkPathBySkinName(String skinName) {
        if (TextUtils.isEmpty(skinName) || "".equals(skinName)) {
            return null;
        }
        new StringBuilder();
        String[] searchPaths = {"system/app", "data/config/app", "apd/app", Environment.getExternalStorageDirectory().getAbsolutePath() + "/skin"};
        String fixSkinName = ("SystemUITheme_" + skinName).toLowerCase();
        for (int i = 0; i < searchPaths.length; i++) {
            String path = searchPaths[i];
            Log.i("SkinManager", "----path:" + path);
            String apkPath = null;
            File apkDir = new File(path);
            if (apkDir.exists() && apkDir.isDirectory()) {
                String[] list = apkDir.list();
                for (String name : list) {
                    if (name.toLowerCase().contains(fixSkinName)) {
                        apkPath = name.endsWith(".apk") ? path + "/" + name : path + "/" + name + "/" + name + ".apk";
                        Log.i("SkinManager", "getApkPathBySkinName " + skinName + " path:" + apkPath);
                        break;
                    }
                }
                if (TextUtils.isEmpty(apkPath)) {
                    continue;
                } else {
                    File apk = new File(apkPath);
                    if (apk.exists()) {
                        String skinApkPath = apk.getAbsolutePath();
                        this.mSkinPath = skinApkPath;
                        return skinApkPath;
                    }
                }
            }
        }
        return null;
    }

    public String getApkPathByPackageName(String pkg) {
        try {
            PackageManager pm = this.mContext.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(pkg, 0);
            if (info == null) {
                return null;
            }
            String apkPath = info.sourceDir;
            this.mSkinPath = apkPath;
            Log.i("SkinManager", "getApkPathByPackageName apkPath:" + apkPath + " pkg:" + pkg);
            return apkPath;
        } catch (Exception e) {
            Log.e("SkinManager", "Didn't find package " + pkg);
            return null;
        }
    }
}
