package com.hcn.skin.support;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import com.hcn.skin.support.app.attr.base.AttrFactory;
import com.hcn.skin.support.app.attr.base.SkinAttr;
import com.hcn.skin.support.observe.SkinObservable;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.skin.support.utils.SkinFileUtils;
import com.hcn.skin.support.utils.SkinLog;
import com.hcn.skin.support.utils.SkinSharedPreferences;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class SkinCompatManager extends SkinObservable {
    private static final String TAG = "SkinCompatManager";
    private static LayoutInflater.Factory2 mSkinLayoutFactory2;
    private static volatile SkinCompatManager sInstance;
    private final Context mAppContext;
    private final Object mLock = new Object();
    private boolean mLoading = false;
    private String mSkinApkPath = null;
    private boolean mSupportSkinInflaterFactory = false;

    public interface SkinLoaderListener {
        void onFailed(String str);

        void onStart();

        void onSuccess();
    }

    public static void setSkinLayoutFactory2(LayoutInflater.Factory2 factory2) {
        mSkinLayoutFactory2 = factory2;
    }

    public static LayoutInflater.Factory2 skinLayoutFactory2() {
        return mSkinLayoutFactory2;
    }

    public static SkinCompatManager create(Context context) {
        if (sInstance == null) {
            synchronized (SkinCompatManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinCompatManager(context);
                }
            }
        }
        return sInstance;
    }

    public static SkinCompatManager getInstance() {
        return sInstance;
    }

    private SkinCompatManager(Context context) {
        this.mAppContext = context.getApplicationContext();
        SkinLog.init();
        SkinSharedPreferences.init(context);
        registerSkinUpdateReceiver();
    }

    public Context getContext() {
        return this.mAppContext;
    }

    public void restoreDefaultSkin() {
        SkinLog.i(TAG, "restoreDefaultSkin");
        loadSkin("");
    }

    public String loadSkin() {
        String skinName = SkinSharedPreferences.getInstance().getSkinName();
        SkinLog.v(TAG, "loadSkin SharedPreferences " + skinName);
        if (TextUtils.isEmpty(skinName)) {
            return null;
        }
        return loadSkinSync(skinName);
    }

    public AsyncTask loadSkin(String str) {
        return loadSkin(str, null);
    }

    public AsyncTask loadSkin(String str, SkinLoaderListener skinLoaderListener) {
        SkinLog.i(TAG, "loadSkin " + str);
        return new SkinLoadTask(skinLoaderListener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, str);
    }

    private class SkinLoadTask extends AsyncTask<String, Void, String> {
        private final SkinLoaderListener mListener;

        SkinLoadTask(SkinLoaderListener skinLoaderListener) {
            this.mListener = skinLoaderListener;
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            SkinLog.d(SkinCompatManager.TAG, "load skin start");
            SkinLoaderListener skinLoaderListener = this.mListener;
            if (skinLoaderListener != null) {
                skinLoaderListener.onStart();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public String doInBackground(String... strArr) {
            synchronized (SkinCompatManager.this.mLock) {
                SkinLog.d(SkinCompatManager.TAG, "load skin ing");
                while (SkinCompatManager.this.mLoading) {
                    try {
                        SkinCompatManager.this.mLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                SkinCompatManager.this.mLoading = true;
            }
            try {
                if (strArr.length == 1) {
                    if (TextUtils.isEmpty(SkinCompatManager.this.loadSkinSync(strArr[0]))) {
                        SkinCompatResources.getInstance().resetSkin();
                        return "";
                    }
                    return strArr[0];
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            SkinCompatResources.getInstance().resetSkin();
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(String str) {
            synchronized (SkinCompatManager.this.mLock) {
                SkinLog.d(SkinCompatManager.TAG, "load skin result : " + str);
                if (str != null) {
                    SkinSharedPreferences.getInstance().setSkinName(str).commitEditor();
                    SkinCompatManager.this.notifySkinChanged();
                    SkinLoaderListener skinLoaderListener = this.mListener;
                    if (skinLoaderListener != null) {
                        skinLoaderListener.onSuccess();
                    }
                } else {
                    SkinSharedPreferences.getInstance().setSkinName("").commitEditor();
                    SkinLoaderListener skinLoaderListener2 = this.mListener;
                    if (skinLoaderListener2 != null) {
                        skinLoaderListener2.onFailed("皮肤资源获取失败");
                    }
                }
                SkinCompatManager.this.mLoading = false;
                SkinCompatManager.this.mLock.notifyAll();
            }
        }
    }

    public String loadSkinSync(String str) {
        return loadSkinSync(null, str);
    }

    public String loadSkinSync(Context context, String str) {
        String absolutePath;
        setupSkinFile();
        if (!TextUtils.isEmpty(str) && !"".equals(str)) {
            StringBuilder sb = new StringBuilder();
            File file = new File(SkinFileUtils.getSkinDir(getContext()), str);
            if (file.exists()) {
                absolutePath = file.getAbsolutePath();
            } else {
                String str2 = "/apd/app/" + str;
                SkinLog.v(TAG, "loadSkinSync " + str2);
                File file2 = new File(str2);
                if (file2.exists()) {
                    absolutePath = file2.getAbsolutePath();
                    SkinLog.v(TAG, "loadSkinSync from apd " + absolutePath);
                } else {
                    absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + SkinFileUtils.SKIN_DIR_NAME + File.separator + str;
                    SkinLog.v(TAG, "loadSkinSync from sdcard " + absolutePath);
                }
            }
            this.mSkinApkPath = absolutePath;
            Resources skinResources = getSkinResources(absolutePath, sb);
            if (skinResources != null) {
                SkinCompatResources.getInstance().setupSkin(context, skinResources, sb.toString());
                return str;
            }
            this.mSkinApkPath = null;
        }
        return null;
    }

    public String loadSkinByPath(String str) {
        return loadSkinByPath(null, str);
    }

    public String loadSkinByPath(Context context, String str) {
        SkinLog.v(TAG, "loadSkinByPath " + str);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        this.mSkinApkPath = str;
        Resources skinResources = getSkinResources(str, sb);
        if (skinResources != null) {
            SkinCompatResources.getInstance().setupSkin(context, skinResources, sb.toString());
            return str;
        }
        this.mSkinApkPath = null;
        return null;
    }

    public String getSkinPath() {
        return this.mSkinApkPath;
    }

    private void setupSkinFile() {
        try {
            Context context = getContext();
            for (String str : context.getAssets().list(SkinFileUtils.SKIN_DIR_NAME)) {
                String skinDir = SkinFileUtils.getSkinDir(context);
                File file = new File(skinDir, str);
                if (!file.exists()) {
                    SkinLog.v(TAG, "copySkinAssetsToDir " + file.getAbsolutePath());
                    SkinFileUtils.copySkinAssetsToDir(context, str, skinDir);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSkinByPackage(String str) {
        Resources skinResourcesByPackage;
        if (TextUtils.isEmpty(str) || "".equals(str) || (skinResourcesByPackage = getSkinResourcesByPackage(str)) == null) {
            return;
        }
        SkinCompatResources.getInstance().setupSkin(skinResourcesByPackage, str);
    }

    private void registerSkinUpdateReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.hcn.skin.support.action.update");
        this.mAppContext.registerReceiver(new BroadcastReceiver() { // from class: com.hcn.skin.support.SkinCompatManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("com.hcn.skin.support.action.update".equals(intent.getAction())) {
                    String stringExtra = intent.getStringExtra("KEY_SKIN_NAME");
                    SkinLog.e(SkinCompatManager.TAG, "receive broadcast update skin:" + stringExtra);
                    if (TextUtils.isEmpty(stringExtra)) {
                        return;
                    }
                    SkinCompatManager.this.loadSkin(stringExtra);
                }
            }
        }, intentFilter);
    }

    public String getSkinPackageName(String str) {
        SkinLog.d(TAG, "getSkinPackageName " + str);
        try {
            PackageInfo packageArchiveInfo = this.mAppContext.getPackageManager().getPackageArchiveInfo(str, 1);
            SkinLog.d(TAG, "getSkinPackageName end");
            return packageArchiveInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public Resources getSkinResources(String str) {
        SkinLog.d(TAG, "getSkinResources " + str);
        try {
            PackageInfo packageArchiveInfo = this.mAppContext.getPackageManager().getPackageArchiveInfo(str, 0);
            packageArchiveInfo.applicationInfo.sourceDir = str;
            packageArchiveInfo.applicationInfo.publicSourceDir = str;
            SkinLog.v(TAG, "getSkinResources:" + str + " package:" + packageArchiveInfo.applicationInfo.packageName);
            Resources resourcesForApplication = this.mAppContext.getPackageManager().getResourcesForApplication(packageArchiveInfo.applicationInfo);
            Resources resources = this.mAppContext.getResources();
            SkinLog.d(TAG, "getSkinResources end");
            return new Resources(resourcesForApplication.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Resources getSkinResources(String str, StringBuilder sb) {
        SkinLog.d(TAG, "getSkinResources " + str);
        try {
            if (!new File(str).exists()) {
                SkinLog.e(TAG, "getSkinResources return! " + str + " not exists");
                return null;
            }
            PackageInfo packageArchiveInfo = this.mAppContext.getPackageManager().getPackageArchiveInfo(str, 0);
            SkinLog.v(TAG, "packageInfo:" + packageArchiveInfo);
            packageArchiveInfo.applicationInfo.sourceDir = str;
            packageArchiveInfo.applicationInfo.publicSourceDir = str;
            sb.append(packageArchiveInfo.packageName);
            Resources resourcesForApplication = this.mAppContext.getPackageManager().getResourcesForApplication(packageArchiveInfo.applicationInfo);
            SkinLog.d(TAG, "getSkinResources end");
            return resourcesForApplication;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Resources getSkinResourcesByPackage(String str) {
        SkinLog.d(TAG, "getSkinResourcesByPackage " + str);
        try {
            ApplicationInfo applicationInfo = this.mAppContext.getPackageManager().getApplicationInfo(str, 0);
            if (applicationInfo != null) {
                SkinLog.v(TAG, "getApplicationInfo " + applicationInfo.sourceDir);
            }
            return this.mAppContext.getPackageManager().getResourcesForApplication(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateConfig(Configuration configuration) {
        Resources skinResources = SkinCompatResources.getInstance().getSkinResources();
        String skinPkgName = SkinCompatResources.getInstance().getSkinPkgName();
        if (skinResources != null) {
            skinResources.updateConfiguration(configuration, skinResources.getDisplayMetrics());
            SkinLog.v(TAG, "updateConfig " + skinResources.getConfiguration());
            SkinCompatResources.getInstance().setupSkin(SkinCompatResources.getInstance().getSkinContext(), skinResources, skinPkgName);
        }
    }

    public Class loadSkinClass(String str) {
        String skinPkgName = SkinCompatResources.getInstance().getSkinPkgName();
        if (skinPkgName == null || (skinPkgName != null && "".equals(skinPkgName))) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = getContext().getPackageManager().getApplicationInfo(skinPkgName, 0);
            if (applicationInfo == null) {
                return null;
            }
            String str2 = applicationInfo.sourceDir;
            String absolutePath = getContext().getCodeCacheDir().getAbsolutePath();
            String str3 = applicationInfo.nativeLibraryDir;
            SkinLog.v(TAG, "loadSkinClass apkPath:" + str2 + " optPath:" + absolutePath + " libPath:" + str3);
            return new DexClassLoader(str2, absolutePath, str3, getClass().getClassLoader()).loadClass(skinPkgName + "." + str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addSupportAttr(String str, SkinAttr skinAttr) {
        AttrFactory.addSupportAttr(str, skinAttr);
    }

    public boolean supportSkinInflaterFactory() {
        return this.mSupportSkinInflaterFactory;
    }

    public void configSkinInflaterFactory(boolean z) {
        this.mSupportSkinInflaterFactory = z;
    }
}
