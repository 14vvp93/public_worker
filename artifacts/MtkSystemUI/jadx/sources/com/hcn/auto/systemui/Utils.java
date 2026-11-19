package com.hcn.auto.systemui;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.plugins.R;
import com.hcn.skin.support.SkinCompatManager;
import com.hcn.skin.support.resources.SkinCompatResources;
import dalvik.system.PathClassLoader;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    public static final boolean SUPPORT_FREEFORM = getSystemProperty("ro.hardware", "").contains("8163");
    public static int sBufferSize = 524288;
    private Context mContext;

    public interface OnProgressUpdateListener {
        void onProgressUpdate(double d);
    }

    public interface OnReplaceListener {
        boolean onReplace(File file, File file2);
    }

    public Utils(Context context) {
        this.mContext = context;
    }

    public void init() {
        String configSkin;
        SkinCompatManager.create(this.mContext);
        if ("1".equals(getSystemProperty("independent_systemui_style", "0"))) {
            configSkin = getSystemProperty("persist.sys.systemui.skin", "");
        } else {
            configSkin = getSystemProperty("persist.sys.skin", "");
        }
        String apkPath = null;
        Log.d("Utils", "onCreate " + configSkin);
        if (!"".equals(configSkin)) {
            apkPath = getApkPath(this.mContext, "com.hcn.systemui.theme." + configSkin);
            if (TextUtils.isEmpty(apkPath)) {
                apkPath = getApkPath(this.mContext, "com.hcn.auto.theme." + configSkin);
            }
            Log.v("Utils", "skin pkg " + apkPath);
            if (TextUtils.isEmpty(apkPath)) {
                apkPath = SkinApkManager.getInstance(this.mContext.getApplicationContext()).getApkPathBySkinName(configSkin);
                Log.e("Utils", "skin pkg --is empty" + apkPath);
            } else {
                SkinApkManager.getInstance(this.mContext.getApplicationContext()).setSkinApkPath(apkPath);
                Log.e("Utils", "skin pkg --not empty" + apkPath);
            }
        }
        SkinApkManager.getInstance(this.mContext.getApplicationContext()).loadSkinByAbsolutePath(apkPath);
        applyPlugin(this.mContext.getApplicationContext(), apkPath);
        Configuration configuration = this.mContext.getResources().getConfiguration();
        updateConfig(configuration);
    }

    public void updateConfig(Configuration config) {
        Log.d("Utils", "updateConfig " + config);
        Resources skinResources = SkinCompatResources.getInstance().getSkinResources();
        String skinPackageName = SkinCompatResources.getInstance().getSkinPkgName();
        if (skinResources != null) {
            skinResources.updateConfiguration(config, skinResources.getDisplayMetrics());
            Log.i("Utils", "skin getConfiguration " + skinResources.getConfiguration());
            SkinCompatResources.getInstance().setupSkin(skinResources, skinPackageName);
        }
    }

    public static void applyPlugin(Context context, String apkPath) {
        checkPatch(context, apkPath);
        supportPluginClass(context, apkPath);
    }

    public static void supportPluginClass(Context context, String apkPath) {
        File[] list;
        File[] list2;
        String[] list3;
        Log.e("Utils", "supportPluginClass apkPath:" + apkPath);
        try {
            Class<?> dexPathListClass = Class.forName("dalvik.system.DexPathList");
            Field dexElementsField = dexPathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);
            Class<?> classLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = classLoaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            ClassLoader pathClassLoader = context.getClassLoader();
            Object hostPathList = pathListField.get(pathClassLoader);
            Object[] hostDexElements = (Object[]) dexElementsField.get(hostPathList);
            Log.v("Utils", "hostPathList:" + hostPathList + " " + hostDexElements);
            Object[] pluginDexElements = new Object[0];
            if (!TextUtils.isEmpty(apkPath)) {
                ClassLoader pluginClassLoader = new PathClassLoader(apkPath, pathClassLoader);
                Object pluginPathList = pathListField.get(pluginClassLoader);
                pluginDexElements = (Object[]) dexElementsField.get(pluginPathList);
                Log.v("Utils", "pluginPathList:" + pluginPathList);
            }
            Method makeDexElementsMethod = hostPathList.getClass().getDeclaredMethod("makeDexElements", List.class, File.class, List.class, ClassLoader.class);
            if (makeDexElementsMethod != null) {
                makeDexElementsMethod.setAccessible(true);
            }
            List patchs = new ArrayList();
            File homeDir = new File(getCacheDir(context), "home");
            if (homeDir.exists() && (list3 = homeDir.list()) != null && list3.length > 0) {
                List<String> sortList = Arrays.asList(list3);
                Collections.sort(sortList, new Comparator<String>() { // from class: com.hcn.auto.systemui.Utils.1
                    @Override // java.util.Comparator
                    public int compare(String o1, String o2) {
                        Pattern pattern = Pattern.compile("(-)\\d*(d)?");
                        Matcher matcher1 = pattern.matcher(o1);
                        Matcher matcher2 = pattern.matcher(o2);
                        if (!matcher1.find() || !matcher2.find()) {
                            return -1;
                        }
                        String group1 = matcher1.group();
                        String group2 = matcher2.group();
                        int v1 = Integer.parseInt(group1.substring(1));
                        int v2 = Integer.parseInt(group2.substring(1));
                        return v1 < v2 ? 1 : -1;
                    }
                });
                int i = 0;
                while (i < sortList.size()) {
                    Class<?> dexPathListClass2 = dexPathListClass;
                    File f = new File(homeDir.getAbsolutePath() + File.separator + sortList.get(i));
                    if (f.exists() && f.isFile()) {
                        Log.d("Utils", "----Patch HOME:" + f.getAbsolutePath());
                        patchs.add(f);
                    }
                    i++;
                    dexPathListClass = dexPathListClass2;
                    homeDir = homeDir;
                }
            }
            File skinSupportDir = new File(getCacheDir(context), "skin-support");
            if (skinSupportDir.exists() && (list2 = skinSupportDir.listFiles()) != null && list2.length > 0) {
                for (File file : list2) {
                    File f2 = new File(file.getAbsolutePath());
                    if (f2.exists() && f2.isFile()) {
                        Log.d("Utils", "----Patch skin-support:" + f2.getAbsolutePath());
                        patchs.add(f2);
                    }
                }
            }
            File carhomeDir = new File(getCacheDir(context), "carhome");
            if (carhomeDir.exists() && (list = carhomeDir.listFiles()) != null && list.length > 0) {
                int i2 = 0;
                while (i2 < list.length) {
                    File f3 = new File(list[i2].getAbsolutePath());
                    if (f3.exists() && f3.isFile()) {
                        Log.d("Utils", "----Patch CAR_HOME:" + f3.getAbsolutePath());
                        patchs.add(f3);
                    }
                    i2++;
                    skinSupportDir = skinSupportDir;
                }
            }
            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            Object[] patchElements = (Object[]) makeDexElementsMethod.invoke(null, patchs, null, suppressedExceptions, pathClassLoader);
            int totalLength = hostDexElements.length + pluginDexElements.length + patchElements.length;
            Log.e("Utils", "newElements length:" + totalLength + " host:" + hostDexElements.length + " plugin:" + pluginDexElements.length + " patch:" + patchElements.length);
            Object[] newElements = (Object[]) Array.newInstance(hostDexElements.getClass().getComponentType(), totalLength);
            if (patchElements.length > 0) {
                System.arraycopy(patchElements, 0, newElements, 0, patchElements.length);
            }
            if (hostDexElements.length > 0) {
                System.arraycopy(hostDexElements, 0, newElements, patchElements.length, hostDexElements.length);
            }
            if (pluginDexElements.length > 0) {
                System.arraycopy(pluginDexElements, 0, newElements, patchElements.length + hostDexElements.length, pluginDexElements.length);
            }
            dexElementsField.set(hostPathList, newElements);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setSystemProperty(String property, String value) {
        try {
            Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            Method set = systemProperties.getMethod("set", String.class, String.class);
            Object[] params = {property, value};
            set.invoke(systemProperties, params);
        } catch (Exception e) {
            Log.d("Utils", "Unable to set system properties");
        }
    }

    public static void checkPatch(Context context, String apkPath) {
        Log.e("Utils", "checkPatch apkPath:" + apkPath);
        try {
            boolean haveHomePatch = installHomePatch(context);
            Log.v("Utils", "have HomePatch " + haveHomePatch);
            checkSdcardPatch(context, apkPath, "skin-support");
            boolean haveCarHomePatch = reInstallCarHomePatch(context);
            Log.v("Utils", "have CarHomePatch " + haveCarHomePatch);
            if (!haveCarHomePatch) {
                useDefaultCarHomeIfNeed(context, apkPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean reInstallCarHomePatch(Context context) {
        String patchDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "patch/launcher/carhome";
        File dir = new File(patchDir);
        if (dir.exists() && dir.isDirectory()) {
            String[] homeFiles = dir.list();
            if (homeFiles != null && homeFiles.length > 0) {
                unInstallDefaultCarHome(context);
            } else {
                return false;
            }
        }
        return installPatch(context, patchDir);
    }

    public static void unInstallDefaultCarHome(Context context) {
        try {
            File carhomeDir = new File(getCacheDir(context), "carhome");
            Log.d("Utils", "unInstallDefaultCarHome " + carhomeDir.getAbsolutePath());
            if (carhomeDir.exists()) {
                deleteCache(carhomeDir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void useDefaultCarHomeIfNeed(Context context, String apkPath) {
        try {
            Class cls = loadClassFromApk(context, apkPath, "com.hcn.auto.launcher.BuildConfig");
            Log.d("Utils", "BuildConfig " + cls);
            if (cls != null) {
                Field fieldVersionCode = cls.getDeclaredField("VERSION_CODE");
                Field fieldVersionName = cls.getDeclaredField("VERSION_NAME");
                if (fieldVersionCode != null && fieldVersionName != null) {
                    Object objCode = fieldVersionCode.get(null);
                    Object objName = fieldVersionName.get(null);
                    if (objCode != null && objName != null) {
                        int versionCode = Integer.valueOf(objCode.toString()).intValue();
                        Log.i("Utils", "skin CAR_HOME versionCode:" + versionCode + " versionName:" + objName);
                        if (versionCode <= 1 && !isAutoChipsPlatform()) {
                            String configSkin = getSystemProperty("persist.sys.skin", "");
                            String pkgName = "com.hcn.auto.theme." + configSkin;
                            String installedApkPath = getApkPath(context, pkgName);
                            if (installedApkPath != null) {
                                Log.w("Utils", pkgName + " have installed in " + installedApkPath);
                                checkDefaultCarHome(context);
                                installDefaultCarHome(context);
                            } else {
                                unInstallDefaultCarHome(context);
                            }
                        } else {
                            unInstallDefaultCarHome(context);
                        }
                    }
                }
                return;
            }
            Log.e("Utils", "Using Default CAR_HOME");
            checkDefaultCarHome(context);
            installDefaultCarHome(context);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    public static void installDefaultCarHome(Context context) {
        try {
            Log.d("Utils", "installDefaultCarHome");
            String[] carhomeFiles = context.getAssets().list("carhome");
            for (String fileName : carhomeFiles) {
                File cacheDir = new File(getCacheDir(context), "carhome");
                if (!cacheDir.exists()) {
                    cacheDir.mkdirs();
                }
                String targetDir = cacheDir.getAbsolutePath();
                File file = new File(targetDir, fileName);
                if (!file.exists()) {
                    Log.v("Utils", "copyCarHomeAssetsToDir " + file.getAbsolutePath());
                    copyAssetsToDir(context, fileName, "carhome", targetDir);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String copyAssetsToDir(Context context, String name, String fromDir, String toDir) {
        String toFile = toDir + File.separator + name;
        try {
            InputStream is = context.getAssets().open(fromDir + File.separator + name);
            File fileDir = new File(toDir);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            OutputStream os = new FileOutputStream(toFile);
            byte[] bytes = new byte[1024];
            while (true) {
                int byteCount = is.read(bytes);
                if (byteCount == -1) {
                    break;
                }
                os.write(bytes, 0, byteCount);
            }
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toFile;
    }

    public static void checkDefaultCarHome(Context context) {
        try {
            String[] carhomeAssets = context.getAssets().list("carhome");
            if (carhomeAssets.length == 1) {
                Log.v("Utils", "carhome in Assets contain " + carhomeAssets[0]);
                File carhomeDir = new File(getCacheDir(context), "carhome");
                if (carhomeDir.exists()) {
                    boolean bDefaultChanged = false;
                    String[] carhomeFiles = carhomeDir.list();
                    for (String fileName : carhomeFiles) {
                        if (!TextUtils.isEmpty(fileName) && fileName.startsWith("CAR_HOME")) {
                            Log.v("Utils", "find:" + fileName + " in cache");
                            if (!carhomeAssets[0].equals(fileName)) {
                                Log.e("Utils", fileName + " will remove and replace with " + carhomeAssets[0]);
                                bDefaultChanged = true;
                                break;
                            }
                            File file = new File(carhomeDir.getAbsolutePath() + "/" + fileName);
                            if (file.exists()) {
                                long length = file.length();
                                Log.i("Utils", fileName + " exist, length:" + length);
                                if (length <= 0) {
                                    Log.e("Utils", "what happen!, length == 0");
                                    bDefaultChanged = true;
                                    break;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    if (bDefaultChanged) {
                        deleteCache(carhomeDir);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isAutoChipsPlatform() {
        String platform = getSystemProperty("ro.hardware", "");
        if (platform.toLowerCase().startsWith("ac")) {
            Log.e("Utils", "isAutoChipsPlatform " + platform);
            return true;
        }
        return false;
    }

    public static Class loadClassFromApk(Context context, String apkPath, String fullClassName) {
        Log.v("Utils", "loadClass " + fullClassName + " FromApk " + apkPath);
        try {
            ClassLoader clsLoader = new PathClassLoader(apkPath, context.getClassLoader());
            Class cls = loadClass(clsLoader, fullClassName);
            return cls;
        } catch (Exception e) {
            Log.e("Utils", "Didn't find class " + fullClassName + " in apk " + apkPath);
            return null;
        }
    }

    public static Class loadClass(ClassLoader loader, String fullClassName) {
        if (loader == null) {
            return null;
        }
        try {
            return loader.loadClass(fullClassName);
        } catch (ClassNotFoundException e) {
            Log.e("Utils", "Didn't find class " + fullClassName);
            return null;
        }
    }

    public static void checkSdcardPatch(Context context, String apkPath, String patchDir) {
        boolean havePatch = reInstallPatch(context, patchDir);
        Log.v("Utils", "checkSdcardPatch " + patchDir + " " + havePatch);
        if (!havePatch) {
            useDefaultPluginIfNeed(context, apkPath, patchDir);
        }
    }

    public static void useDefaultPluginIfNeed(Context context, String apkPath, String pluginDir) {
        try {
            Class cls = loadClassFromApk(context, apkPath, "com.hcn.skin.support.BuildConfig");
            Log.d("Utils", "BuildConfig " + cls);
            if (cls != null) {
                unInstallDefaultPlugin(context, pluginDir);
            } else {
                Log.e("Utils", "Using Default " + pluginDir);
                checkDefaultPlugin(context, pluginDir);
                installDefaultPlugin(context, pluginDir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void installDefaultPlugin(Context context, String dir) {
        try {
            Log.d("Utils", "installDefaultPlugin " + dir);
            String[] carhomeFiles = context.getAssets().list(dir);
            for (String fileName : carhomeFiles) {
                File cacheDir = new File(getCacheDir(context), dir);
                if (!cacheDir.exists()) {
                    cacheDir.mkdirs();
                }
                String targetDir = cacheDir.getAbsolutePath();
                File file = new File(targetDir, fileName);
                if (!file.exists()) {
                    Log.v("Utils", "copyAssets " + dir + " To " + file.getAbsolutePath());
                    copyAssetsToDir(context, fileName, dir, targetDir);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkDefaultPlugin(Context context, String dirName) {
        try {
            String[] assets = context.getAssets().list(dirName);
            if (assets.length == 1) {
                Log.v("Utils", dirName + " in Assets contain " + assets[0]);
                File targetDir = new File(getCacheDir(context), dirName);
                if (targetDir.exists()) {
                    boolean bDefaultChanged = false;
                    String[] files = targetDir.list();
                    for (String fileName : files) {
                        if (!TextUtils.isEmpty(fileName) && fileName.startsWith(dirName)) {
                            Log.v("Utils", "find " + fileName + " in cache");
                            if (!assets[0].equals(fileName)) {
                                Log.e("Utils", fileName + " will remove and replace with " + assets[0]);
                                bDefaultChanged = true;
                                break;
                            }
                            File file = new File(targetDir.getAbsolutePath() + "/" + fileName);
                            if (file.exists()) {
                                long length = file.length();
                                Log.i("Utils", fileName + " exist, length:" + length);
                                if (length <= 0) {
                                    Log.e("Utils", "what happen!, length == 0");
                                    bDefaultChanged = true;
                                    break;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    if (bDefaultChanged) {
                        deleteCache(targetDir);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean reInstallPatch(Context context, String targetDirName) {
        String patchDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "patch/launcher/" + targetDirName;
        File dir = new File(patchDir);
        if (dir.exists() && dir.isDirectory()) {
            String[] files = dir.list();
            if (files != null && files.length > 0) {
                unInstallDefaultPlugin(context, targetDirName);
            } else {
                return false;
            }
        }
        return installPatch(context, patchDir);
    }

    public static void unInstallDefaultPlugin(Context context, String dir) {
        try {
            File carhomeDir = new File(getCacheDir(context), dir);
            Log.d("Utils", "unInstallDefaultPlugin " + carhomeDir.getAbsolutePath());
            if (carhomeDir.exists()) {
                deleteCache(carhomeDir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean deleteCache(File file) {
        if (file != null && file.isDirectory()) {
            String[] children = file.list();
            for (String str : children) {
                boolean success = deleteCache(new File(file, str)).booleanValue();
                if (!success) {
                    return false;
                }
            }
        }
        return Boolean.valueOf(file.delete());
    }

    public static boolean installHomePatch(Context context) {
        return installPatch(context, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "patch/launcher/home");
    }

    public static boolean installPatch(Context context, String patchDir) {
        int findResult;
        String[] homeFiles;
        if (TextUtils.isEmpty(patchDir) || (findResult = patchDir.lastIndexOf("/")) < 0) {
            return false;
        }
        String dirName = patchDir.substring(findResult + 1);
        Log.d("Utils", "installPatch " + dirName);
        try {
            File dir = new File(patchDir);
            if (dir.exists() && dir.isDirectory() && (homeFiles = dir.list()) != null && homeFiles.length > 0) {
                File cacheDir = new File(getCacheDir(context), dirName);
                if (!cacheDir.exists()) {
                    cacheDir.mkdirs();
                }
                for (String fileName : homeFiles) {
                    String targetDir = cacheDir.getAbsolutePath();
                    File file = new File(targetDir, fileName);
                    if (!file.exists()) {
                        String src = patchDir + File.separator + fileName;
                        Log.v("Utils", src + " copy to " + file.getAbsolutePath());
                        copy(isSpace(src) ? null : new File(src), isSpace(file.getAbsolutePath()) ? null : new File(file.getAbsolutePath()), (OnReplaceListener) null);
                    }
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isSpace(String s) {
        if (s == null) {
            return true;
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String getCacheDir(Context context) {
        File cacheDir = context.getCacheDir();
        if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
            return cacheDir.getAbsolutePath();
        }
        return cacheDir.getAbsolutePath();
    }

    public static boolean copy(File src, File dest, OnReplaceListener listener) {
        if (src == null) {
            return false;
        }
        return src.isDirectory() ? copyDir(src, dest, listener) : copyOrMoveFile(src, dest, listener, false);
    }

    public static boolean copyDir(File srcDir, File destDir, OnReplaceListener listener) {
        return copyOrMoveDir(srcDir, destDir, listener, false);
    }

    public static boolean copyOrMoveDir(File srcDir, File destDir, OnReplaceListener listener, boolean isMove) {
        if (srcDir == null || destDir == null) {
            return false;
        }
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath) || !srcDir.exists() || !srcDir.isDirectory() || !createOrExistsDir(destDir)) {
            return false;
        }
        File[] files = srcDir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                File oneDestFile = new File(destPath + file.getName());
                if (file.isFile()) {
                    if (!copyOrMoveFile(file, oneDestFile, listener, isMove)) {
                        return false;
                    }
                } else if (file.isDirectory() && !copyOrMoveDir(file, oneDestFile, listener, isMove)) {
                    return false;
                }
            }
        }
        return !isMove || deleteDir(srcDir);
    }

    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }
        if (!dir.exists()) {
            return true;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) {
                        return false;
                    }
                } else if (file.isDirectory() && !deleteDir(file)) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static boolean copyOrMoveFile(File srcFile, File destFile, OnReplaceListener listener, boolean isMove) {
        if (srcFile == null || destFile == null || srcFile.equals(destFile) || !srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        if (destFile.exists()) {
            if (listener != null && !listener.onReplace(srcFile, destFile)) {
                return true;
            }
            if (!destFile.delete()) {
                return false;
            }
        }
        if (!createOrExistsDir(destFile.getParentFile())) {
            return false;
        }
        try {
            if (writeFileFromIS(isSpace(destFile.getAbsolutePath()) ? null : new File(destFile.getAbsolutePath()), new FileInputStream(srcFile), false, (OnProgressUpdateListener) null)) {
                return !isMove || deleteFile(srcFile);
            }
            return false;
        } catch (FileNotFoundException var5) {
            var5.printStackTrace();
            return false;
        }
    }

    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || (file.isFile() && file.delete()));
    }

    public static boolean writeFileFromIS(File file, InputStream is, boolean append, OnProgressUpdateListener listener) {
        if (is != null && createOrExistsFile(file)) {
            OutputStream os = null;
            try {
                try {
                    OutputStream os2 = new BufferedOutputStream(new FileOutputStream(file, append), sBufferSize);
                    if (listener == null) {
                        byte[] data = new byte[sBufferSize];
                        while (true) {
                            int len = is.read(data);
                            if (len == -1) {
                                break;
                            }
                            os2.write(data, 0, len);
                        }
                    } else {
                        double totalSize = is.available();
                        int curSize = 0;
                        listener.onProgressUpdate(0.0d);
                        byte[] data2 = new byte[sBufferSize];
                        while (true) {
                            int len2 = is.read(data2);
                            if (len2 == -1) {
                                break;
                            }
                            os2.write(data2, 0, len2);
                            curSize += len2;
                            listener.onProgressUpdate(((double) curSize) / totalSize);
                        }
                    }
                    try {
                        is.close();
                    } catch (IOException var21) {
                        var21.printStackTrace();
                    }
                    try {
                        os2.close();
                    } catch (IOException var20) {
                        var20.printStackTrace();
                    }
                    return true;
                } catch (Throwable th) {
                    try {
                        is.close();
                    } catch (IOException var22) {
                        var22.printStackTrace();
                    }
                    if (0 != 0) {
                        try {
                            os.close();
                            throw th;
                        } catch (IOException var23) {
                            var23.printStackTrace();
                            throw th;
                        }
                    }
                    throw th;
                }
            } catch (IOException var24) {
                var24.printStackTrace();
                try {
                    is.close();
                } catch (IOException var25) {
                    var25.printStackTrace();
                }
                if (0 != 0) {
                    try {
                        os.close();
                    } catch (IOException var26) {
                        var26.printStackTrace();
                    }
                }
                return false;
            }
        }
        Log.e("FileIOUtils", "create file <" + file + "> failed.");
        return false;
    }

    public static boolean createOrExistsFile(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException var2) {
            var2.printStackTrace();
            return false;
        }
    }

    public static boolean createOrExistsDir(File file) {
        if (file != null) {
            if (file.exists()) {
                if (!file.isDirectory()) {
                    return false;
                }
                return true;
            }
            if (!file.mkdirs()) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static String getSystemProperty(String property, String defaultValue) {
        try {
            Method getter = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class);
            String value = (String) getter.invoke(null, property);
            if (!TextUtils.isEmpty(value)) {
                return value;
            }
            return defaultValue;
        } catch (Exception e) {
            Log.d("Utils", "Unable to read system properties");
        }
    }

    public static String getApkPath(Context context, String pkg) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(pkg, 0);
            if (info == null) {
                return null;
            }
            String apkPath = info.sourceDir;
            Log.i("Utils", "getApkPath apkPath:" + apkPath + " pkg:" + pkg);
            return apkPath;
        } catch (Exception e) {
            Log.e("Utils", "Didn't find package " + pkg);
            return null;
        }
    }

    public static Drawable getCarHomeIconFromTheme(Context context, ComponentName componentName, String beforeIconName, int iconSize, float scale, String iconBackgroundName) {
        Bitmap bitmap;
        String iconName;
        Drawable drawable;
        Log.d("Utils", "getCarHomeIconFromTheme: ");
        if (context == null) {
            return null;
        }
        if (componentName != null && (iconName = IconThemeUtils.getCustomAppIconName(componentName.getPackageName(), componentName.getClassName())) != null && !TextUtils.isEmpty(beforeIconName) && (drawable = SkinCompatResources.getInstance().getDrawable(beforeIconName.concat(iconName))) != null) {
            return drawable;
        }
        Drawable iconBackgroundDrawable = SkinCompatResources.getInstance().getDrawable(iconBackgroundName);
        if (iconBackgroundDrawable == null || (bitmap = IconFactory.obtain(context, iconSize).createScaledBitmapWithoutShadow(getActivityIcon(context, componentName.getPackageName(), componentName.getClassName()), iconBackgroundDrawable, true, scale)) == null) {
            return null;
        }
        return new BitmapDrawable(bitmap);
    }

    public static Drawable getActivityIcon(Context context, String packageName, String className) {
        Log.d("Utils", "getActivityIcon: ");
        if (context == null || TextUtils.isEmpty(packageName)) {
            return null;
        }
        PackageManager pm = context.getPackageManager();
        ComponentName cn = new ComponentName(packageName, className);
        try {
            ActivityInfo info = pm.getActivityInfo(cn, 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static Drawable getActivityIconFromTheme(Context context, ComponentName componentName) {
        StringBuilder sb = new StringBuilder();
        sb.append("getActivityIconFromTheme ");
        sb.append(componentName);
        sb.append(" context = ");
        sb.append(context == null);
        Log.i("Utils", sb.toString());
        if (context == null) {
            return null;
        }
        if (componentName != null) {
            Bitmap bitmap = IconThemeUtils.getCustomAppIconBitmap(componentName.getPackageName(), componentName.getClassName());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("getActivityIconFromTheme: bitmap = ");
            sb2.append(bitmap == null);
            Log.d("Utils", sb2.toString());
            if (bitmap != null) {
                return new BitmapDrawable(bitmap);
            }
        }
        Bitmap bitmap2 = IconFactory.obtain(context, getCarHomeIconSize()).createScaledBitmapWithoutShadow(getActivityIcon(context, componentName.getPackageName(), componentName.getClassName()), true);
        if (bitmap2 != null) {
            return new BitmapDrawable(bitmap2);
        }
        return getActivityIcon(context, componentName.getPackageName(), componentName.getClassName());
    }

    public static Drawable getActivityIconFromTheme(Context context, ComponentName componentName, String xmlName, String iconName) {
        Bitmap bitmap;
        StringBuilder sb = new StringBuilder();
        sb.append("getActivityIconFromTheme ");
        sb.append(componentName);
        sb.append(" context = ");
        sb.append(context == null);
        Log.i("Utils", sb.toString());
        if (context == null) {
            return null;
        }
        if (componentName != null && (bitmap = IconThemeUtils.getCustomAppIconBitmap(componentName.getPackageName(), componentName.getClassName(), xmlName)) != null) {
            return new BitmapDrawable(bitmap);
        }
        Bitmap bitmap2 = IconFactory.obtain(context, getCarHomeIconSize()).createScaledBitmapWithoutShadow(getActivityIcon(context, componentName.getPackageName(), componentName.getClassName()), true, iconName);
        if (bitmap2 != null) {
            return new BitmapDrawable(bitmap2);
        }
        return getActivityIcon(context, componentName.getPackageName(), componentName.getClassName());
    }

    public static int getCarHomeIconSize() {
        Log.d("Utils", "getCarHomeIconSize: ");
        int iconSizeTheme = SkinCompatResources.getInstance().getInteger("icon_size");
        if (iconSizeTheme != 0) {
            return iconSizeTheme;
        }
        return R.styleable.AppCompatTheme_windowNoTitle;
    }
}
