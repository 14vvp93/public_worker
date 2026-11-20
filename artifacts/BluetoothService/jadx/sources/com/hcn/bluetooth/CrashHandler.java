package com.hcn.bluetooth;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;
import com.hcn.skin.support.utils.ShellUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String PATH_SD = Environment.getExternalStorageDirectory().getPath();
    public static final String TAG = "CrashHandler";
    private static CrashHandler instance;
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Map<String, String> infos = new HashMap();
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    public void init(Context context) {
        String str = PATH_SD;
        Toast.makeText(context, str, 1);
        Log.d(TAG, str);
        this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
        Log.e(TAG, "___________ erre start");
        if (!handleException(th) && (uncaughtExceptionHandler = this.mDefaultHandler) != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
            return;
        }
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            Log.e(TAG, "error : ", e);
        }
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    public String getApplicationName() {
        PackageManager packageManager;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = this.mContext.getApplicationContext().getPackageManager();
            try {
                applicationInfo = packageManager.getApplicationInfo(this.mContext.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException unused) {
            }
        } catch (PackageManager.NameNotFoundException unused2) {
            packageManager = null;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.hcn.bluetooth.CrashHandler$1] */
    private boolean handleException(Throwable th) {
        if (th == null) {
            return false;
        }
        collectDeviceInfo(this.mContext);
        new Thread() { // from class: com.hcn.bluetooth.CrashHandler.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                Looper.loop();
            }
        }.start();
        saveCatchInfo2File(th, PATH_SD);
        return true;
    }

    public void collectDeviceInfo(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 1);
            if (packageInfo != null) {
                String str = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String str2 = packageInfo.versionCode + "";
                this.infos.put("versionName", str);
                this.infos.put("versionCode", str2);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        for (Field field : Build.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                this.infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e2) {
                Log.e(TAG, "an error occured when collect crash info", e2);
            }
        }
    }

    private String saveCatchInfo2File(Throwable th, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : this.infos.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + ShellUtil.COMMAND_LINE_END);
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        stringBuffer.append(stringWriter.toString());
        try {
            String str2 = "BluetoothService crash-" + this.formatter.format(new Date()) + "-" + System.currentTimeMillis() + ".txt";
            if (Environment.getExternalStorageState().equals("mounted")) {
                File file = new File(str);
                if (!file.exists()) {
                    file.mkdirs();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(str + File.separator + str2);
                fileOutputStream.write(stringBuffer.toString().getBytes());
                Log.e(TAG, "___________wirt erre info");
                fileOutputStream.close();
            }
            return str2;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v4 */
    private void sendCrashLog2PM(String str) throws Throwable {
        FileInputStream fileInputStream;
        IOException e;
        BufferedReader bufferedReader;
        FileNotFoundException e2;
        if (!new File((String) str).exists()) {
            return;
        }
        try {
            try {
                try {
                    fileInputStream = new FileInputStream((String) str);
                    try {
                        bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "GBK"));
                        while (true) {
                            try {
                                String line = bufferedReader.readLine();
                                if (line == null) {
                                    break;
                                } else {
                                    Log.i("info", line.toString());
                                }
                            } catch (FileNotFoundException e3) {
                                e2 = e3;
                                e2.printStackTrace();
                                bufferedReader.close();
                                fileInputStream.close();
                            } catch (IOException e4) {
                                e = e4;
                                e.printStackTrace();
                                bufferedReader.close();
                                fileInputStream.close();
                            }
                        }
                        bufferedReader.close();
                        fileInputStream.close();
                    } catch (FileNotFoundException e5) {
                        e2 = e5;
                        bufferedReader = null;
                    } catch (IOException e6) {
                        e = e6;
                        bufferedReader = null;
                    } catch (Throwable th) {
                        th = th;
                        str = 0;
                        try {
                            str.close();
                            fileInputStream.close();
                        } catch (IOException e7) {
                            e7.printStackTrace();
                        }
                        throw th;
                    }
                } catch (IOException e8) {
                    e8.printStackTrace();
                }
            } catch (FileNotFoundException e9) {
                fileInputStream = null;
                e2 = e9;
                bufferedReader = null;
            } catch (IOException e10) {
                fileInputStream = null;
                e = e10;
                bufferedReader = null;
            } catch (Throwable th2) {
                fileInputStream = null;
                th = th2;
                str = 0;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }
}
