package com.android.systemui.classifier;

import android.app.ActivityThread;
import android.app.Application;
import android.os.Build;
import android.os.SystemProperties;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class FalsingLog {
    public static final boolean ENABLED = SystemProperties.getBoolean("debug.falsing_log", Build.IS_DEBUGGABLE);
    private static final boolean LOGCAT = SystemProperties.getBoolean("debug.falsing_logcat", false);
    private static final int MAX_SIZE = SystemProperties.getInt("debug.falsing_log_size", 100);
    private static FalsingLog sInstance;
    private final ArrayDeque<String> mLog = new ArrayDeque<>(MAX_SIZE);
    private final SimpleDateFormat mFormat = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.US);

    private FalsingLog() {
    }

    public static void i(String tag, String s) {
        if (LOGCAT) {
            Log.i("FalsingLog", tag + "\t" + s);
        }
        log("I", tag, s);
    }

    public static void wLogcat(String tag, String s) {
        Log.w("FalsingLog", tag + "\t" + s);
        log("W", tag, s);
    }

    public static void e(String tag, String s) {
        if (LOGCAT) {
            Log.e("FalsingLog", tag + "\t" + s);
        }
        log("E", tag, s);
    }

    public static synchronized void log(String level, String tag, String s) {
        if (ENABLED) {
            if (sInstance == null) {
                sInstance = new FalsingLog();
            }
            if (sInstance.mLog.size() >= MAX_SIZE) {
                sInstance.mLog.removeFirst();
            }
            String entry = sInstance.mFormat.format(new Date()) + " " + level + " " + tag + " " + s;
            sInstance.mLog.add(entry);
        }
    }

    public static synchronized void dump(PrintWriter pw) {
        pw.println("FALSING LOG:");
        if (!ENABLED) {
            pw.println("Disabled, to enable: setprop debug.falsing_log 1");
            pw.println();
            return;
        }
        if (sInstance != null && !sInstance.mLog.isEmpty()) {
            for (String s : sInstance.mLog) {
                pw.println(s);
            }
            pw.println();
            return;
        }
        pw.println("<empty>");
        pw.println();
    }

    public static synchronized void wtf(String tag, String s, Throwable here) {
        if (ENABLED) {
            e(tag, s);
            Application application = ActivityThread.currentApplication();
            String fileMessage = "";
            if (!Build.IS_DEBUGGABLE || application == null) {
                Log.e("FalsingLog", "Unable to write log, build must be debuggable.");
            } else {
                File f = new File(application.getDataDir(), "falsing-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".txt");
                PrintWriter pw = null;
                try {
                    try {
                        pw = new PrintWriter(f);
                        dump(pw);
                        pw.close();
                        fileMessage = "Log written to " + f.getAbsolutePath();
                    } catch (IOException e) {
                        Log.e("FalsingLog", "Unable to write falsing log", e);
                        if (pw != null) {
                        }
                        Log.e("FalsingLog", tag + " " + s + "; " + fileMessage);
                    }
                    pw.close();
                } catch (Throwable th) {
                    if (pw != null) {
                        pw.close();
                    }
                    throw th;
                }
            }
            Log.e("FalsingLog", tag + " " + s + "; " + fileMessage);
        }
    }
}
