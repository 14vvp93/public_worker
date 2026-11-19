package com.hcn.skin.support.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class SkinFileUtils {
    public static final String SKIN_DIR_NAME = "skin";

    public static String copySkinAssetsToDir(Context context, String str, String str2) {
        String str3 = str2 + File.separator + str;
        try {
            InputStream inputStreamOpen = context.getAssets().open(SKIN_DIR_NAME + File.separator + str);
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(str3);
            byte[] bArr = new byte[1024];
            while (true) {
                int i = inputStreamOpen.read(bArr);
                if (i == -1) {
                    break;
                }
                fileOutputStream.write(bArr, 0, i);
            }
            fileOutputStream.close();
            inputStreamOpen.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str3;
    }

    public static String getSkinDir(Context context) {
        File file = new File(getCacheDir(context), SKIN_DIR_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static String getCacheDir(Context context) {
        File externalCacheDir;
        if (Environment.getExternalStorageState().equals("mounted") && (externalCacheDir = context.getExternalCacheDir()) != null && (externalCacheDir.exists() || externalCacheDir.mkdirs())) {
            return externalCacheDir.getAbsolutePath();
        }
        return context.getCacheDir().getAbsolutePath();
    }
}
