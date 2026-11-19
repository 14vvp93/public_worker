package com.hcn.common.misc;

import android.text.TextUtils;
import android.util.Log;
import com.orhanobut.logger.Logger;
import java.io.File;
import java.nio.file.LinkOption;

/* JADX INFO: loaded from: classes.dex */
public class PathUtils {
    private static final String PATH_USB_PREFIX = "/storage/udisk";
    private static final String STORAGE_PATH = "/storage/";
    private static final String TAG = "PathUtils";
    private static final int VOLUME_UUID_LENGTH = 9;

    private static boolean isExternalVolume(String str) {
        if (!str.startsWith(STORAGE_PATH)) {
            return false;
        }
        String strSubstring = str.substring(9);
        if (TextUtils.isEmpty(strSubstring) || strSubstring.length() != 9) {
            return false;
        }
        int i = 0;
        for (int i2 = 0; i2 < 9; i2++) {
            char cCharAt = strSubstring.charAt(i2);
            if (i2 != 4 || cCharAt != '-') {
                if (!((cCharAt >= '0' && cCharAt <= '9') || (cCharAt >= 'A' && cCharAt <= 'F'))) {
                    break;
                }
            }
            i++;
        }
        return i == 9;
    }

    public static String tryCalibrationExternalVolume(String str) {
        File[] fileArrListFiles;
        if (isExternalVolume(str) && (fileArrListFiles = new File(STORAGE_PATH).listFiles()) != null) {
            for (File file : fileArrListFiles) {
                String absolutePath = file.getAbsolutePath();
                if (absolutePath.startsWith(PATH_USB_PREFIX)) {
                    try {
                        if (str.equals(file.toPath().toRealPath(new LinkOption[0]).toString())) {
                            Logger.t(TAG).v("tryCalibrationExternalVolume: " + str + " # " + absolutePath, new Object[0]);
                            return absolutePath;
                        }
                        continue;
                    } catch (Exception e) {
                        Log.e(TAG, "tryCalibrationExternalVolume: " + e.toString());
                    }
                }
            }
        }
        return str;
    }
}
