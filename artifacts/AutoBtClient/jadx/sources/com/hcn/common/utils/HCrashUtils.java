package com.hcn.common.utils;

import android.text.TextUtils;
import com.autochips.bluetooth.skin.SkinID;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class HCrashUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final Thread.UncaughtExceptionHandler DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler();

    public interface OnCrashListener {
        void onCrash(CrashInfo crashInfo);
    }

    private HCrashUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init() {
        init(SkinID.SKIN_NONE);
    }

    public static void init(File file) {
        init(file.getAbsolutePath(), (OnCrashListener) null);
    }

    public static void init(String str) {
        init(str, (OnCrashListener) null);
    }

    public static void init(OnCrashListener onCrashListener) {
        init(SkinID.SKIN_NONE, onCrashListener);
    }

    public static void init(File file, OnCrashListener onCrashListener) {
        init(file.getAbsolutePath(), onCrashListener);
    }

    public static void init(String str, OnCrashListener onCrashListener) {
        if (TextUtils.isEmpty(str)) {
            if (HSDCardUtils.isSDCardEnableByEnvironment() && HUtilsEx.getApp().getExternalFilesDir(null) != null) {
                StringBuilder sbAppend = new StringBuilder().append(HUtilsEx.getApp().getExternalFilesDir(null));
                String str2 = FILE_SEP;
                str = sbAppend.append(str2).append("crash").append(str2).toString();
            } else {
                StringBuilder sbAppend2 = new StringBuilder().append(HUtilsEx.getApp().getFilesDir());
                String str3 = FILE_SEP;
                str = sbAppend2.append(str3).append("crash").append(str3).toString();
            }
        } else {
            String str4 = FILE_SEP;
            if (!str.endsWith(str4)) {
                str = str + str4;
            }
        }
        Thread.setDefaultUncaughtExceptionHandler(getUncaughtExceptionHandler(str, onCrashListener));
    }

    private static Thread.UncaughtExceptionHandler getUncaughtExceptionHandler(final String str, final OnCrashListener onCrashListener) {
        return new Thread.UncaughtExceptionHandler() { // from class: com.hcn.common.utils.HCrashUtils.1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread, Throwable th) {
                String str2 = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date());
                CrashInfo crashInfo = new CrashInfo(str2, th);
                OnCrashListener onCrashListener2 = onCrashListener;
                if (onCrashListener2 != null) {
                    onCrashListener2.onCrash(crashInfo);
                }
                HFileIOUtils.writeFileFromString(str + str2 + ".txt", crashInfo.toString(), true);
                if (HCrashUtils.DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null) {
                    HCrashUtils.DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(thread, th);
                }
            }
        };
    }

    public static final class CrashInfo {
        private final HUtilsEx.FileHead mFileHeadProvider;
        private final Throwable mThrowable;

        private CrashInfo(String str, Throwable th) {
            this.mThrowable = th;
            HUtilsEx.FileHead fileHead = new HUtilsEx.FileHead("Crash");
            this.mFileHeadProvider = fileHead;
            fileHead.addFirst("Time Of Crash", str);
        }

        public final void addExtraHead(Map<String, String> map) {
            this.mFileHeadProvider.append(map);
        }

        public final void addExtraHead(String str, String str2) {
            this.mFileHeadProvider.append(str, str2);
        }

        public final Throwable getThrowable() {
            return this.mThrowable;
        }

        public String toString() {
            return this.mFileHeadProvider.toString() + HThrowableUtils.getFullStackTrace(this.mThrowable);
        }
    }
}
