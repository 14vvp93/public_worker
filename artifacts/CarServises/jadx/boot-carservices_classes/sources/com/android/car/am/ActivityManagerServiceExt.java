package com.android.car.am;

import android.car.PackageConfig;
import android.content.Context;
import android.content.Intent;
import android.os.FileUtils;
import android.os.Handler;
import android.system.ErrnoException;
import android.system.Os;
import android.text.TextUtils;
import android.util.Slog;
import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public final class ActivityManagerServiceExt {
    private static final boolean DEBUG = false;
    private static final String TAG = "ActivityManagerServiceExt";
    private final ConcurrentHashMap<String, Boolean> mFotaBootFlags = new ConcurrentHashMap<>();
    private static ActivityManagerServiceExt sInstance = null;
    private Context mContext = null;
    private Reference<Handler> mHandlerRef = null;

    public static ActivityManagerServiceExt instance() {
        if (sInstance == null) {
            sInstance = new ActivityManagerServiceExt();
        }
        return sInstance;
    }

    private ActivityManagerServiceExt() {
        String[] fotaPackages = "com.abfota.systemUpdate,com.adups.fota".split(",");
        for (String fota : fotaPackages) {
            this.mFotaBootFlags.put(fota, false);
        }
        checkFotaAutoBootFlag();
    }

    public void init(Context content, Handler handler) {
        if (!Objects.isNull(this.mContext)) {
            Slog.w(TAG, "repeated calls to initialization functions!");
        } else {
            this.mContext = content;
            this.mHandlerRef = new WeakReference(handler);
        }
    }

    public void release() {
    }

    private void checkFotaAutoBootFlag() {
        File targetDirFile = new File("/data/auto_boot");
        if (!targetDirFile.exists()) {
            targetDirFile.mkdir();
            try {
                Os.chmod(targetDirFile.getAbsolutePath(), 511);
            } catch (ErrnoException e) {
                Slog.i(TAG, "Failed to chmod: " + e.getMessage());
            }
        }
        String[] sourceFiles = targetDirFile.list();
        for (String sourceName : sourceFiles) {
            if ("com.abfota.systemUpdate,com.adups.fota".contains(sourceName)) {
                this.mFotaBootFlags.put(sourceName, true);
            } else {
                new File(targetDirFile, sourceName).delete();
                Slog.i(TAG, "checkFotaAutoBootFlag, delete file: " + sourceName);
            }
        }
    }

    public void systemReady() {
        if (Objects.isNull(this.mContext)) {
            Slog.e(TAG, "The current module is not initialized.");
        }
    }

    public void startProcessHook(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return;
        }
        String[] packageNames = packageName.split(":");
        if ("com.abfota.systemUpdate,com.adups.fota".contains(packageNames[0])) {
            this.mFotaBootFlags.put(packageNames[0], true);
            String targetName = "/data/auto_boot" + File.separator + packageNames[0];
            File targetFile = new File(targetName);
            if (!targetFile.exists()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateTime = sdf.format(new Date());
                    FileUtils.stringToFile(targetFile, dateTime + "\n");
                } catch (IOException e) {
                    Slog.i(TAG, "create file " + targetName + "failed, " + e.getMessage());
                }
            }
        }
    }

    public boolean processNextBroadcastHook(Intent intent, String packageName) {
        if (Objects.isNull(intent) || TextUtils.isEmpty(packageName)) {
            return false;
        }
        if (Objects.isNull(this.mContext)) {
            Slog.e(TAG, "The current module is not initialized.");
            return false;
        }
        String[] packageNames = packageName.split(":");
        Object obj = this.mFotaBootFlags.get(packageNames[0]);
        if (obj instanceof Boolean) {
            Boolean allowedBoot = (Boolean) obj;
            if (allowedBoot.booleanValue()) {
                return false;
            }
            Slog.e(TAG, "processNextBroadcastHook, fota '" + packageName + "' not active.");
            return true;
        }
        String action = intent.getAction();
        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
            boolean bUserSend = "carservices".equals(intent.getExtra("send.reason.key", "default"));
            if (!bUserSend) {
                return PackageConfig.notAllowAutoBoot(packageNames[0]);
            }
        }
        return false;
    }
}
