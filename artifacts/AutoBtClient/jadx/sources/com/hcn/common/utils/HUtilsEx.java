package com.hcn.common.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import com.autochips.bluetooth.skin.SkinID;
import com.hcn.common.lang.HThreadUtils;
import com.hcn.skin.support.utils.ShellUtil;
import com.orhanobut.logger.Logger;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class HUtilsEx {
    private static final String TAG = "HUtilsEx";
    private static Application sApp;

    public interface Consumer<T> {
        void accept(T t);
    }

    public interface Func1<Ret, Par> {
        Ret call(Par par);
    }

    public interface OnAppStatusChangedListener {
        void onBackground(Activity activity);

        void onForeground(Activity activity);
    }

    public interface Supplier<T> {
        T get();
    }

    private HUtilsEx() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(Application application) {
        if (application == null) {
            Log.e("HUtils", "app is null.");
        } else {
            sApp = application;
        }
    }

    public static Application getApp() {
        Application application = sApp;
        if (application != null) {
            return application;
        }
        init(getApplicationByReflect());
        Application application2 = sApp;
        Objects.requireNonNull(application2, "reflect failed.");
        return application2;
    }

    private static Application getApplicationByReflect() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object objInvoke = cls.getMethod("getApplication", new Class[0]).invoke(getActivityThread(), new Object[0]);
            if (objInvoke == null) {
                return null;
            }
            return (Application) objInvoke;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Object getActivityThread() {
        Object activityThreadInActivityThreadStaticField = getActivityThreadInActivityThreadStaticField();
        return activityThreadInActivityThreadStaticField != null ? activityThreadInActivityThreadStaticField : getActivityThreadInActivityThreadStaticMethod();
    }

    private static Object getActivityThreadInActivityThreadStaticField() {
        try {
            Field declaredField = Class.forName("android.app.ActivityThread").getDeclaredField("sCurrentActivityThread");
            declaredField.setAccessible(true);
            return declaredField.get(null);
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticField: " + e.getMessage());
            return null;
        }
    }

    private static Object getActivityThreadInActivityThreadStaticMethod() {
        try {
            return Class.forName("android.app.ActivityThread").getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticMethod: " + e.getMessage());
            return null;
        }
    }

    public static boolean isAppDebug() {
        return isAppDebug(getApp());
    }

    public static boolean isAppDebug(Context context) {
        if (Objects.isNull(context)) {
            return false;
        }
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        return ((applicationInfo == null || (applicationInfo.flags & 2) == 0) && "user".equals(Build.TYPE)) ? false : true;
    }

    public static String getSystemProperty(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, str2);
        } catch (Exception e) {
            Logger.t(TAG).d("getSystemProperty: " + e.toString());
            return null;
        }
    }

    public static Integer getSystemProperty(String str, int i) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            Object objInvoke = cls.getMethod("getInt", String.class, Integer.TYPE).invoke(cls, str, Integer.valueOf(i));
            if (objInvoke instanceof Integer) {
                i = ((Integer) objInvoke).intValue();
            }
        } catch (Exception e) {
            Logger.t(TAG).d("getSystemProperty: " + e.toString());
        }
        return Integer.valueOf(i);
    }

    public static void setSystemProperty(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            cls.getMethod("set", String.class, String.class).invoke(cls, str, str2);
        } catch (Exception e) {
            Logger.t(TAG).d("setSystemProperty: " + e.toString());
        }
    }

    public static void sleep(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean reverseEquals(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            if (str2.equals(str)) {
                return true;
            }
            int length = str.length();
            if (length == str2.length()) {
                int i = length - 1;
                while (true) {
                    int i2 = length - 1;
                    if (length == 0) {
                        return true;
                    }
                    if (str.charAt(i) != str2.charAt(i)) {
                        return false;
                    }
                    i--;
                    length = i2;
                }
            }
        }
        return false;
    }

    public static boolean existInstalledPackage(Context context, String str) {
        if (Objects.isNull(context)) {
            return false;
        }
        for (PackageInfo packageInfo : context.getPackageManager().getInstalledPackages(0)) {
            if (!TextUtils.isEmpty(packageInfo.packageName) && packageInfo.packageName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static IBinder getSystemService(String str) {
        try {
            Class<?> cls = Class.forName("android.os.ServiceManager");
            Object objInvoke = cls.getMethod("getService", String.class).invoke(cls, str);
            if (objInvoke != null) {
                return (IBinder) objInvoke;
            }
            return null;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Logger.t(TAG).d("getAutoService: " + e.toString());
            return null;
        }
    }

    public static Drawable getAppIcon() {
        return getAppIcon(getApp().getPackageName());
    }

    public static Drawable getAppIcon(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            PackageManager packageManager = getApp().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.applicationInfo.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getAppIconId() {
        return getAppIconId(getApp().getPackageName());
    }

    public static int getAppIconId(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            PackageInfo packageInfo = getApp().getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return 0;
            }
            return packageInfo.applicationInfo.icon;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getAppPackageName() {
        return getApp().getPackageName();
    }

    public static String getAppName() {
        return getAppName(getApp().getPackageName());
    }

    public static String getAppName(String str) {
        if (TextUtils.isEmpty(str)) {
            return SkinID.SKIN_NONE;
        }
        try {
            PackageManager packageManager = getApp().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.applicationInfo.loadLabel(packageManager).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return SkinID.SKIN_NONE;
        }
    }

    public static String getAppPath() {
        return getAppPath(getApp().getPackageName());
    }

    public static String getAppPath(String str) {
        if (TextUtils.isEmpty(str)) {
            return SkinID.SKIN_NONE;
        }
        try {
            PackageInfo packageInfo = getApp().getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return SkinID.SKIN_NONE;
        }
    }

    public static String getAppVersionName() {
        return getAppVersionName(getApp().getPackageName());
    }

    public static String getAppVersionName(String str) {
        if (TextUtils.isEmpty(str)) {
            return SkinID.SKIN_NONE;
        }
        try {
            PackageInfo packageInfo = getApp().getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return SkinID.SKIN_NONE;
        }
    }

    public static int getAppVersionCode() {
        return getAppVersionCode(getApp().getPackageName());
    }

    public static int getAppVersionCode(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        try {
            PackageInfo packageInfo = getApp().getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return -1;
            }
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static HSPUtils getSpUtils4Utils() {
        return HSPUtils.getInstance("Utils");
    }

    public static <T> Task<T> doAsync(Task<T> task) {
        HThreadUtils.getCachedPool().execute(task);
        return task;
    }

    public static abstract class Task<Result> extends HThreadUtils.SimpleTask<Result> {
        private Consumer<Result> mConsumer;

        public Task(Consumer<Result> consumer) {
            this.mConsumer = consumer;
        }

        @Override // com.hcn.common.lang.HThreadUtils.Task
        public void onSuccess(Result result) {
            Consumer<Result> consumer = this.mConsumer;
            if (consumer != null) {
                consumer.accept(result);
            }
        }
    }

    public static final class FileHead {
        private final LinkedHashMap<String, String> mFirst = new LinkedHashMap<>();
        private final LinkedHashMap<String, String> mLast = new LinkedHashMap<>();
        private final String mName;

        public FileHead(String str) {
            this.mName = str;
        }

        public void addFirst(String str, String str2) {
            append2Host(this.mFirst, str, str2);
        }

        public void append(Map<String, String> map) {
            append2Host(this.mLast, map);
        }

        public void append(String str, String str2) {
            append2Host(this.mLast, str, str2);
        }

        private void append2Host(Map<String, String> map, Map<String, String> map2) {
            if (map2 == null || map2.isEmpty()) {
                return;
            }
            for (Map.Entry<String, String> entry : map2.entrySet()) {
                append2Host(map, entry.getKey(), entry.getValue());
            }
        }

        private void append2Host(Map<String, String> map, String str, String str2) {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                return;
            }
            int length = 19 - str.length();
            if (length > 0) {
                str = str + "                   ".substring(0, length);
            }
            map.put(str, str2);
        }

        public String getAppended() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : this.mLast.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(ShellUtil.COMMAND_LINE_END);
            }
            return sb.toString();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            String str = "************* " + this.mName + " Head ****************\n";
            sb.append(str);
            for (Map.Entry<String, String> entry : this.mFirst.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(ShellUtil.COMMAND_LINE_END);
            }
            sb.append("Device Manufacturer: ").append(Build.MANUFACTURER).append(ShellUtil.COMMAND_LINE_END);
            sb.append("Device Model       : ").append(Build.MODEL).append(ShellUtil.COMMAND_LINE_END);
            sb.append("Android Version    : ").append(Build.VERSION.RELEASE).append(ShellUtil.COMMAND_LINE_END);
            sb.append("Android SDK        : ").append(Build.VERSION.SDK_INT).append(ShellUtil.COMMAND_LINE_END);
            sb.append("App VersionName    : ").append(HUtilsEx.getAppVersionName()).append(ShellUtil.COMMAND_LINE_END);
            sb.append("App VersionCode    : ").append(HUtilsEx.getAppVersionCode()).append(ShellUtil.COMMAND_LINE_END);
            sb.append(getAppended());
            return sb.append(str).append(ShellUtil.COMMAND_LINE_END).toString();
        }
    }
}
