package com.hcn.skin.support;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import com.hcn.skin.support.observe.SkinObservable;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.skin.support.utils.SkinFileUtils;
import com.hcn.skin.support.utils.SkinLog;
import com.hcn.skin.support.utils.SkinSharedPreferences;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class SkinCompatManager extends SkinObservable {
    private static volatile SkinCompatManager sInstance;
    private final Context mAppContext;
    private final Object mLock = new Object();
    private boolean mLoading = false;
    private String mSkinApkPath = null;

    public interface SkinLoaderListener {
        void onFailed(String str);

        void onStart();

        void onSuccess();
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

    public AsyncTask loadSkin(String skinName) {
        return loadSkin(skinName, null);
    }

    public AsyncTask loadSkin(String skinName, SkinLoaderListener listener) {
        SkinLog.i("SkinCompatManager", "loadSkin " + skinName);
        return new SkinLoadTask(listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, skinName);
    }

    private class SkinLoadTask extends AsyncTask<String, Void, String> {
        private final SkinLoaderListener mListener;

        SkinLoadTask(SkinLoaderListener listener) {
            this.mListener = listener;
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            SkinLog.d("SkinCompatManager", "load skin start");
            if (this.mListener != null) {
                this.mListener.onStart();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public String doInBackground(String... params) {
            synchronized (SkinCompatManager.this.mLock) {
                SkinLog.d("SkinCompatManager", "load skin ing");
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
                if (params.length == 1) {
                    String skinName = SkinCompatManager.this.loadSkinSync(params[0]);
                    if (TextUtils.isEmpty(skinName)) {
                        SkinCompatResources.getInstance().resetSkin();
                        return "";
                    }
                    return params[0];
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            SkinCompatResources.getInstance().resetSkin();
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(String skinName) {
            synchronized (SkinCompatManager.this.mLock) {
                SkinLog.d("SkinCompatManager", "load skin result : " + skinName);
                if (skinName != null) {
                    SkinSharedPreferences.getInstance().setSkinName(skinName).commitEditor();
                    SkinCompatManager.this.notifySkinChanged();
                    if (this.mListener != null) {
                        this.mListener.onSuccess();
                    }
                } else {
                    SkinSharedPreferences.getInstance().setSkinName("").commitEditor();
                    if (this.mListener != null) {
                        this.mListener.onFailed("皮肤资源获取失败");
                    }
                }
                SkinCompatManager.this.mLoading = false;
                SkinCompatManager.this.mLock.notifyAll();
            }
        }
    }

    public String loadSkinSync(String skinName) {
        return loadSkinSync(null, skinName);
    }

    public String loadSkinSync(Context skinContext, String skinName) {
        String skinPkgPath;
        setupSkinFile();
        if (!TextUtils.isEmpty(skinName) && !"".equals(skinName)) {
            StringBuilder sb = new StringBuilder();
            File file = new File(SkinFileUtils.getSkinDir(getContext()), skinName);
            if (file.exists()) {
                skinPkgPath = file.getAbsolutePath();
            } else {
                String path = "/apd/app/" + skinName;
                SkinLog.v("SkinCompatManager", "loadSkinSync " + path);
                File apkInApd = new File(path);
                if (apkInApd.exists()) {
                    skinPkgPath = apkInApd.getAbsolutePath();
                    SkinLog.v("SkinCompatManager", "loadSkinSync from apd " + skinPkgPath);
                } else {
                    skinPkgPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "skin" + File.separator + skinName;
                    SkinLog.v("SkinCompatManager", "loadSkinSync from sdcard " + skinPkgPath);
                }
            }
            this.mSkinApkPath = skinPkgPath;
            Resources resources = getSkinResources(skinPkgPath, sb);
            if (resources != null) {
                SkinCompatResources.getInstance().setupSkin(skinContext, resources, sb.toString());
                return skinName;
            }
            this.mSkinApkPath = null;
        }
        return null;
    }

    public String loadSkinByPath(String skinApkPath) {
        return loadSkinByPath(null, skinApkPath);
    }

    public String loadSkinByPath(Context skinContext, String skinApkPath) {
        SkinLog.v("SkinCompatManager", "loadSkinByPath " + skinApkPath);
        if (TextUtils.isEmpty(skinApkPath)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        this.mSkinApkPath = skinApkPath;
        Resources resources = getSkinResources(skinApkPath, sb);
        if (resources != null) {
            SkinCompatResources.getInstance().setupSkin(skinContext, resources, sb.toString());
            return skinApkPath;
        }
        this.mSkinApkPath = null;
        return null;
    }

    private void setupSkinFile() {
        try {
            Context context = getContext();
            String[] skinFiles = context.getAssets().list("skin");
            for (String fileName : skinFiles) {
                String targetDir = SkinFileUtils.getSkinDir(context);
                File file = new File(targetDir, fileName);
                if (!file.exists()) {
                    SkinLog.v("SkinCompatManager", "copySkinAssetsToDir " + file.getAbsolutePath());
                    SkinFileUtils.copySkinAssetsToDir(context, fileName, targetDir);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerSkinUpdateReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.hcn.skin.support.action.update");
        this.mAppContext.registerReceiver(new BroadcastReceiver() { // from class: com.hcn.skin.support.SkinCompatManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if ("com.hcn.skin.support.action.update".equals(action)) {
                    String name = intent.getStringExtra("KEY_SKIN_NAME");
                    SkinLog.e("SkinCompatManager", "receive broadcast update skin:" + name);
                    if (!TextUtils.isEmpty(name)) {
                        SkinCompatManager.this.loadSkin(name);
                    }
                }
            }
        }, intentFilter);
    }

    private Resources getSkinResources(String skinPkgPath, StringBuilder sb) {
        SkinLog.d("SkinCompatManager", "getSkinResources " + skinPkgPath);
        try {
            File apkFile = new File(skinPkgPath);
            if (!apkFile.exists()) {
                SkinLog.e("SkinCompatManager", "getSkinResources return! " + skinPkgPath + " not exists");
                return null;
            }
            PackageInfo packageInfo = this.mAppContext.getPackageManager().getPackageArchiveInfo(skinPkgPath, 0);
            SkinLog.v("SkinCompatManager", "packageInfo:" + packageInfo);
            packageInfo.applicationInfo.sourceDir = skinPkgPath;
            packageInfo.applicationInfo.publicSourceDir = skinPkgPath;
            sb.append(packageInfo.packageName);
            Resources res = this.mAppContext.getPackageManager().getResourcesForApplication(packageInfo.applicationInfo);
            SkinLog.d("SkinCompatManager", "getSkinResources end");
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
