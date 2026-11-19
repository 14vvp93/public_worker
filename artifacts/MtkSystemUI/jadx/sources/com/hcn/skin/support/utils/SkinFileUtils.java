package com.hcn.skin.support.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class SkinFileUtils {
    public static String copySkinAssetsToDir(Context context, String name, String toDir) {
        String toFile = toDir + File.separator + name;
        try {
            InputStream is = context.getAssets().open("skin" + File.separator + name);
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

    public static String getSkinDir(Context context) {
        File skinDir = new File(getCacheDir(context), "skin");
        if (!skinDir.exists()) {
            skinDir.mkdirs();
        }
        return skinDir.getAbsolutePath();
    }

    public static String getCacheDir(Context context) {
        File cacheDir;
        if (Environment.getExternalStorageState().equals("mounted") && (cacheDir = context.getExternalCacheDir()) != null && (cacheDir.exists() || cacheDir.mkdirs())) {
            return cacheDir.getAbsolutePath();
        }
        return context.getCacheDir().getAbsolutePath();
    }
}
