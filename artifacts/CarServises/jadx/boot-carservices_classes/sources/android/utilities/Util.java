package android.utilities;

import android.Configures.HConfig;
import android.app.ActivityManager;
import android.carsource.McuManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.FileUtils;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import libcore.io.Libcore;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public final class Util {
    public static final String BOOT_REASON_PROP = "sys.powerup.reason";
    public static final String DEFAULT_MAP = "com.autonavi.amapauto";
    public static final String FAN_PACKAGE_NAME = "com.hcn.autofan";
    public static final String NAVI_PACKAGE = "com.ritu.rtnavi,com.navitel,com.navngo.igo.javaclient,com.baidu.BaiduMap,com.baidu.navi,com.autonavi.minimap,com.mxnavi.mxnavi,com.autonavi.xmgd.navigator,com.tencent.mm.title.navi,com.txznet.nav,com.autonavi.amapauto,com.autonavi.amapautolite";
    private static final String TAG = "Util";
    private static final ConcurrentHashMap<String, String> sPackageNameMap = new ConcurrentHashMap<>();
    private static int sDebugFlag = -1;

    public static native void nativeSync();

    public static boolean isDebugVersion() {
        if (sDebugFlag == -1) {
            sDebugFlag = 0;
            if ("user".equals(Build.TYPE)) {
                sDebugFlag = 1;
            }
        }
        return sDebugFlag == 0;
    }

    private Util() {
    }

    public static boolean isNavigation(String pkg) {
        if (pkg == null || pkg.length() < 8) {
            return false;
        }
        String pkg2 = pkg.replaceAll("/.*", "");
        if (!NAVI_PACKAGE.contains(pkg2) && !pkg2.contains("cld.navi.") && !SystemProperties.get("sys.navi.packages").contains(pkg2)) {
            return false;
        }
        return true;
    }

    public static String getLineInfo() {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        return " " + ste.getFileName() + ": Line " + ste.getLineNumber();
    }

    public static int parseInt(String str) {
        int radix = 10;
        String s = str.trim();
        if (s.startsWith("0x") || s.startsWith("0X")) {
            s = s.substring(2);
            radix = 16;
        }
        return Integer.parseInt(s, radix);
    }

    public static String getProcessName(int pid) {
        int[] PROCESS_FULL_STATS_FORMAT = {4096};
        String[] name = new String[1];
        Process.readProcFile("/proc/" + pid + "/cmdline", PROCESS_FULL_STATS_FORMAT, name, null, null);
        return name[0];
    }

    public static void stringToFileSync(String filename, String string) throws IOException {
        byte[] content = string.getBytes(StandardCharsets.UTF_8);
        FileOutputStream fos = new FileOutputStream(filename);
        Throwable th = null;
        try {
            fos.write(content);
            fos.flush();
            fos.getFD().sync();
            fos.close();
        } catch (Throwable th2) {
            if (th != null) {
                try {
                    fos.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
            } else {
                fos.close();
            }
            throw th2;
        }
    }

    public static boolean writeStringToFile(String path, String str) {
        try {
            if (Libcore.os.access(path, 0)) {
                FileUtils.stringToFile(path, str);
                return true;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String readStringFromFile(String path, int length) {
        try {
            if (Libcore.os.access(path, 0)) {
                File file = new File(path);
                return FileUtils.readTextFile(file, length, null);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static void launchNavigation(Context context) {
        ContentResolver cr = context.getContentResolver();
        String defPkg = Settings.System.getString(cr, HConfig.preferred_navigator);
        if (defPkg == null || defPkg.length() < 5) {
            defPkg = DEFAULT_MAP;
        }
        boolean success = startPackage(context, defPkg);
        if (!success) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("geo:"));
                context.startActivityAsUser(intent, UserHandle.CURRENT);
            } catch (Exception e) {
            }
        }
    }

    public static void startActivityByClassName(Context context, String pkg, String classname) {
        startActivityByClassName(context, pkg, classname, McuManager.CNACEL_KEY);
    }

    public static void startActivityByClassName(Context context, String pkg, String classname, int flag) {
        Intent i = new Intent();
        i.setFlags(flag);
        i.setClassName(pkg, classname);
        try {
            context.startActivityAsUser(i, UserHandle.CURRENT);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, e.toString() + getLineInfo());
        } catch (NullPointerException e2) {
            Log.e(TAG, e2.toString() + getLineInfo());
        }
    }

    public static boolean startPackage(Context context, String pkg) {
        if (getTopComponent(context).getPackageName().equals(pkg)) {
            return true;
        }
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(pkg);
        try {
            context.startActivityAsUser(intent, UserHandle.CURRENT);
            return true;
        } catch (ActivityNotFoundException e) {
            Log.w(TAG, e.toString() + getLineInfo());
            return false;
        } catch (NullPointerException e2) {
            Log.w(TAG, e2.toString() + getLineInfo());
            return false;
        }
    }

    public static boolean startActivitybyClass(Context context, String component) {
        return startActivitybyClass(context, component, false);
    }

    public static boolean startActivitybyClass(Context context, String component, boolean noAnimation) {
        ComponentName cn = ComponentName.unflattenFromString(component);
        Intent i = new Intent();
        i.addCategory("android.intent.category.LAUNCHER");
        i.setComponent(cn);
        if (noAnimation) {
            i.setFlags(3211264);
        } else {
            i.setFlags(3145728);
        }
        try {
            context.startActivityAsUser(i, UserHandle.CURRENT);
            return true;
        } catch (ActivityNotFoundException | NullPointerException e) {
            Log.w(TAG, e.toString() + getLineInfo());
            return true;
        }
    }

    public static String getLauncherClass(Context context, String pkg) {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(pkg);
        if (intent == null) {
            return "";
        }
        ComponentName cn = intent.resolveActivity(pm);
        String cls = cn.getClassName();
        return cls;
    }

    public static ComponentName getTopComponent(Context context) {
        ComponentName cn = new ComponentName("", "");
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(2);
        if (!taskInfo.isEmpty()) {
            ComponentName cn2 = taskInfo.get(0).topActivity;
            return cn2;
        }
        return cn;
    }

    public static String findPackageName(Context context, String app, boolean force) {
        String pkg;
        if (!force && (pkg = sPackageNameMap.get(app)) != null) {
            return pkg;
        }
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo p : packages) {
            if (app.startsWith(p.packageName) && !p.packageName.equals("android")) {
                if (!force) {
                    sPackageNameMap.put(app, p.packageName);
                }
                return p.packageName;
            }
        }
        return null;
    }

    public static void forceStopPackage(String pkg) {
        long callingId = Binder.clearCallingIdentity();
        try {
            ActivityManager.getService().forceStopPackage(pkg, -1);
        } catch (RemoteException e) {
        } finally {
            Binder.restoreCallingIdentity(callingId);
        }
    }
}
