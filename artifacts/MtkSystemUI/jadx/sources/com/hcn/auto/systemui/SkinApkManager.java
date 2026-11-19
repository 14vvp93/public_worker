package com.hcn.auto.systemui;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.hcn.skin.support.resources.SkinCompatResources;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class SkinApkManager {
    private static volatile SkinApkManager sInstance;
    private Context mContext;
    private String mSkinApkPath = null;

    public static SkinApkManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (SkinApkManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinApkManager(context);
                }
            }
        }
        return sInstance;
    }

    private SkinApkManager(Context context) {
        this.mContext = null;
        this.mContext = context;
    }

    public void setSkinApkPath(String skinApkPath) {
        this.mSkinApkPath = skinApkPath;
        Log.v("SkinApkManager", "setSkinApkPath " + this.mSkinApkPath);
    }

    public boolean loadSkinByAbsolutePath(String skinApkPath) {
        StringBuilder sb;
        Resources resources;
        Log.v("SkinApkManager", "loadSkinByAbsolutePath " + skinApkPath);
        if (TextUtils.isEmpty(skinApkPath) || (resources = getSkinResources(skinApkPath, (sb = new StringBuilder()))) == null) {
            return false;
        }
        SkinCompatResources.getInstance().setupSkin(resources, sb.toString());
        return true;
    }

    public String getApkPathBySkinName(String skinName) {
        if (TextUtils.isEmpty(skinName) || "".equals(skinName)) {
            return null;
        }
        new StringBuilder();
        String[] searchPaths = {"data/config/app", "apd/app", Environment.getExternalStorageDirectory().getAbsolutePath() + "/skin"};
        String fixSkinName = ("AutoTheme_" + skinName).toLowerCase();
        for (int i = 0; i < searchPaths.length; i++) {
            String path = searchPaths[i];
            Log.i("SkinApkManager", "----path:" + path);
            String apkPath = null;
            File apkDir = new File(path);
            if (apkDir.exists() && apkDir.isDirectory()) {
                String[] list = apkDir.list();
                for (String name : list) {
                    if (name.toLowerCase().contains(fixSkinName)) {
                        apkPath = name.endsWith(".apk") ? path + "/" + name : path + "/" + name + "/" + name + ".apk";
                        Log.i("SkinApkManager", "getApkPathBySkinName " + skinName + " path:" + apkPath);
                        break;
                    }
                }
                if (TextUtils.isEmpty(apkPath)) {
                    continue;
                } else {
                    File apk = new File(apkPath);
                    if (apk.exists()) {
                        String skinApkPath = apk.getAbsolutePath();
                        this.mSkinApkPath = skinApkPath;
                        return skinApkPath;
                    }
                }
            }
        }
        return null;
    }

    private Resources getSkinResources(String skinPkgPath, StringBuilder sb) {
        Log.d("SkinApkManager", "getSkinResources " + skinPkgPath);
        try {
            File apkFile = new File(skinPkgPath);
            if (!apkFile.exists()) {
                Log.e("SkinApkManager", "getSkinResources return! " + skinPkgPath + " not exists");
                return null;
            }
            PackageInfo packageInfo = this.mContext.getPackageManager().getPackageArchiveInfo(skinPkgPath, 0);
            packageInfo.applicationInfo.sourceDir = skinPkgPath;
            packageInfo.applicationInfo.publicSourceDir = skinPkgPath;
            sb.append(packageInfo.packageName);
            Resources res = this.mContext.getPackageManager().getResourcesForApplication(packageInfo.applicationInfo);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
