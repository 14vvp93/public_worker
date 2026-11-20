package com.hcn.common.utils;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import androidx.core.content.FileProvider;
import com.orhanobut.logger.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class HUriUtils {
    private static final String TAG = "HUriUtils";

    private HUriUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Uri res2Uri(String str) {
        return Uri.parse("android.resource://" + HUtilsEx.getApp().getPackageName() + "/" + str);
    }

    public static Uri file2Uri(File file) {
        if (!HFileUtils.isFileExists(file)) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            return FileProvider.getUriForFile(HUtilsEx.getApp(), HUtilsEx.getApp().getPackageName() + ".common.fileprovider", file);
        }
        return Uri.fromFile(file);
    }

    public static File uri2File(Uri uri) {
        if (uri == null) {
            return null;
        }
        File fileUri2FileReal = uri2FileReal(uri);
        return fileUri2FileReal != null ? fileUri2FileReal : copyUri2Cache(uri);
    }

    public static File uri2FileNoCacheCopy(Uri uri) {
        if (uri == null) {
            return null;
        }
        return uri2FileReal(uri);
    }

    private static File uri2FileReal(Uri uri) {
        Uri uri2;
        String str;
        File file;
        Logger.t(TAG).v(uri.toString(), new Object[0]);
        String authority = uri.getAuthority();
        String scheme = uri.getScheme();
        String path = uri.getPath();
        if (Build.VERSION.SDK_INT >= 24 && path != null) {
            String[] strArr = {"/external/", "/external_path/"};
            for (int i = 0; i < 2; i++) {
                String str2 = strArr[i];
                if (path.startsWith(str2)) {
                    File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + path.replace(str2, "/"));
                    if (file2.exists()) {
                        Logger.t(TAG).v(uri.toString() + " -> " + str2, new Object[0]);
                        return file2;
                    }
                }
            }
            if (path.startsWith("/files_path/")) {
                file = new File(HUtilsEx.getApp().getFilesDir().getAbsolutePath() + path.replace("/files_path/", "/"));
            } else if (path.startsWith("/cache_path/")) {
                file = new File(HUtilsEx.getApp().getCacheDir().getAbsolutePath() + path.replace("/cache_path/", "/"));
            } else if (path.startsWith("/external_files_path/")) {
                file = new File(HUtilsEx.getApp().getExternalFilesDir(null).getAbsolutePath() + path.replace("/external_files_path/", "/"));
            } else {
                file = path.startsWith("/external_cache_path/") ? new File(HUtilsEx.getApp().getExternalCacheDir().getAbsolutePath() + path.replace("/external_cache_path/", "/")) : null;
            }
            if (file != null && file.exists()) {
                Logger.t(TAG).v(uri.toString() + " -> " + path, new Object[0]);
                return file;
            }
        }
        if ("file".equals(scheme)) {
            if (path != null) {
                return new File(path);
            }
            Logger.t(TAG).v(uri.toString() + " parse failed. -> 0", new Object[0]);
            return null;
        }
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(HUtilsEx.getApp(), uri)) {
            if ("com.android.externalstorage.documents".equals(authority)) {
                String[] strArrSplit = DocumentsContract.getDocumentId(uri).split(":");
                String str3 = strArrSplit[0];
                if ("primary".equalsIgnoreCase(str3)) {
                    return new File(Environment.getExternalStorageDirectory() + "/" + strArrSplit[1]);
                }
                StorageManager storageManager = (StorageManager) HUtilsEx.getApp().getSystemService("storage");
                try {
                    Class<?> cls = Class.forName("android.os.storage.StorageVolume");
                    Method method = storageManager.getClass().getMethod("getVolumeList", new Class[0]);
                    Method method2 = cls.getMethod("getUuid", new Class[0]);
                    Method method3 = cls.getMethod("getState", new Class[0]);
                    Method method4 = cls.getMethod("getPath", new Class[0]);
                    Method method5 = cls.getMethod("isPrimary", new Class[0]);
                    Method method6 = cls.getMethod("isEmulated", new Class[0]);
                    Object objInvoke = method.invoke(storageManager, new Object[0]);
                    int length = Array.getLength(objInvoke);
                    for (int i2 = 0; i2 < length; i2++) {
                        Object obj = Array.get(objInvoke, i2);
                        if (("mounted".equals(method3.invoke(obj, new Object[0])) || "mounted_ro".equals(method3.invoke(obj, new Object[0]))) && ((!((Boolean) method5.invoke(obj, new Object[0])).booleanValue() || !((Boolean) method6.invoke(obj, new Object[0])).booleanValue()) && (str = (String) method2.invoke(obj, new Object[0])) != null && str.equals(str3))) {
                            return new File(method4.invoke(obj, new Object[0]) + "/" + strArrSplit[1]);
                        }
                    }
                } catch (Exception e) {
                    Logger.t(TAG).v(uri.toString() + " parse failed. " + e.toString() + " -> 1_0", new Object[0]);
                }
                Logger.t(TAG).v(uri.toString() + " parse failed. -> 1_0", new Object[0]);
                return null;
            }
            if ("com.android.providers.downloads.documents".equals(authority)) {
                String documentId = DocumentsContract.getDocumentId(uri);
                if (TextUtils.isEmpty(documentId)) {
                    Logger.t(TAG).v(uri.toString() + " parse failed(id is null). -> 1_1", new Object[0]);
                    return null;
                }
                if (documentId.startsWith("raw:")) {
                    return new File(documentId.substring(4));
                }
                if (documentId.startsWith("msf:")) {
                    documentId = documentId.split(":")[1];
                }
                try {
                    long j = Long.parseLong(documentId);
                    String[] strArr2 = {"content://downloads/public_downloads", "content://downloads/all_downloads", "content://downloads/my_downloads"};
                    for (int i3 = 0; i3 < 3; i3++) {
                        try {
                            File fileFromUri = getFileFromUri(ContentUris.withAppendedId(Uri.parse(strArr2[i3]), j), "1_1");
                            if (fileFromUri != null) {
                                return fileFromUri;
                            }
                        } catch (Exception unused) {
                        }
                    }
                    Logger.t(TAG).v(uri.toString() + " parse failed. -> 1_1", new Object[0]);
                    return null;
                } catch (Exception unused2) {
                    return null;
                }
            }
            if ("com.android.providers.media.documents".equals(authority)) {
                String[] strArrSplit2 = DocumentsContract.getDocumentId(uri).split(":");
                String str4 = strArrSplit2[0];
                if ("image".equals(str4)) {
                    uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(str4)) {
                    uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(str4)) {
                    uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                } else {
                    Logger.t(TAG).v(uri.toString() + " parse failed. -> 1_2", new Object[0]);
                    return null;
                }
                return getFileFromUri(uri2, "_id=?", new String[]{strArrSplit2[1]}, "1_2");
            }
            if ("content".equals(scheme)) {
                return getFileFromUri(uri, "1_3");
            }
            Logger.t(TAG).v(uri.toString() + " parse failed. -> 1_4", new Object[0]);
            return null;
        }
        if ("content".equals(scheme)) {
            return getFileFromUri(uri, "2");
        }
        Logger.t(TAG).v(uri.toString() + " parse failed. -> 3", new Object[0]);
        return null;
    }

    private static File getFileFromUri(Uri uri, String str) {
        return getFileFromUri(uri, null, null, str);
    }

    private static File getFileFromUri(Uri uri, String str, String[] strArr, String str2) {
        if ("com.google.android.apps.photos.content".equals(uri.getAuthority())) {
            if (!TextUtils.isEmpty(uri.getLastPathSegment())) {
                return new File(uri.getLastPathSegment());
            }
        } else if ("com.tencent.mtt.fileprovider".equals(uri.getAuthority())) {
            String path = uri.getPath();
            if (!TextUtils.isEmpty(path)) {
                return new File(Environment.getExternalStorageDirectory(), path.substring(10));
            }
        }
        String authority = uri.getAuthority();
        if (!TextUtils.isEmpty(authority) && authority.startsWith("com.") && authority.endsWith(".fileprovider")) {
            String path2 = uri.getPath();
            HashMap map = new HashMap();
            map.put("/root", "/storage");
            map.put("/app-dir", "/storage/emulated/0/");
            for (String str3 : map.keySet()) {
                if (!TextUtils.isEmpty(path2) && path2.startsWith(str3)) {
                    path2 = path2.substring(str3.length());
                    String str4 = ((String) map.get(str3)) + path2;
                    if (HFileUtils.isFileExists(path2) || HFileUtils.isFileExists(str4)) {
                        return new File(str4);
                    }
                }
            }
        }
        Cursor cursorQuery = HUtilsEx.getApp().getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
        try {
            try {
                if (cursorQuery == null) {
                    Logger.t(TAG).v(uri.toString() + " parse failed(cursor is null). -> " + str2, new Object[0]);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
                if (!cursorQuery.moveToFirst()) {
                    Logger.t(TAG).v(uri.toString() + " parse failed(moveToFirst return false). -> " + str2, new Object[0]);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
                int columnIndex = cursorQuery.getColumnIndex("_data");
                if (columnIndex > -1) {
                    File file = new File(cursorQuery.getString(columnIndex));
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return file;
                }
                Logger.t(TAG).v(uri.toString() + " parse failed(columnIndex: " + columnIndex + " is wrong). -> " + str2, new Object[0]);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return null;
            } catch (Throwable th) {
                if (cursorQuery != null) {
                    try {
                        cursorQuery.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Exception unused) {
            Logger.t(TAG).v(uri.toString() + " parse failed. -> " + str2, new Object[0]);
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:36:0x006b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private static File copyUri2Cache(Uri uri) throws Throwable {
        Throwable th;
        InputStream inputStreamOpenInputStream;
        Logger.t(TAG).v("copyUri2Cache() called", new Object[0]);
        try {
            inputStreamOpenInputStream = HUtilsEx.getApp().getContentResolver().openInputStream(uri);
            try {
                try {
                    File file = new File(HUtilsEx.getApp().getCacheDir(), "" + System.currentTimeMillis());
                    HFileIOUtils.writeFileFromIS(file.getAbsolutePath(), inputStreamOpenInputStream);
                    if (inputStreamOpenInputStream != null) {
                        try {
                            inputStreamOpenInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return file;
                } catch (FileNotFoundException e2) {
                    e = e2;
                    e.printStackTrace();
                    if (inputStreamOpenInputStream != null) {
                        try {
                            inputStreamOpenInputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    return null;
                }
            } catch (Throwable th2) {
                th = th2;
                if (inputStreamOpenInputStream != null) {
                    try {
                        inputStreamOpenInputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e5) {
            e = e5;
            inputStreamOpenInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            inputStreamOpenInputStream = null;
            if (inputStreamOpenInputStream != null) {
                inputStreamOpenInputStream.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:40:0x0047 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static byte[] uri2Bytes(Uri uri) throws Throwable {
        Throwable th;
        InputStream inputStreamOpenInputStream;
        if (uri == null) {
            return null;
        }
        try {
            inputStreamOpenInputStream = HUtilsEx.getApp().getContentResolver().openInputStream(uri);
            try {
                try {
                    byte[] bArrInputStream2Bytes = HConvertUtils.inputStream2Bytes(inputStreamOpenInputStream);
                    if (inputStreamOpenInputStream != null) {
                        try {
                            inputStreamOpenInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return bArrInputStream2Bytes;
                } catch (FileNotFoundException e2) {
                    e = e2;
                    e.printStackTrace();
                    Logger.t(TAG).v("uri to bytes failed.", new Object[0]);
                    if (inputStreamOpenInputStream != null) {
                        try {
                            inputStreamOpenInputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    return null;
                }
            } catch (Throwable th2) {
                th = th2;
                if (inputStreamOpenInputStream != null) {
                    try {
                        inputStreamOpenInputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e5) {
            e = e5;
            inputStreamOpenInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            inputStreamOpenInputStream = null;
            if (inputStreamOpenInputStream != null) {
                inputStreamOpenInputStream.close();
            }
            throw th;
        }
    }
}
