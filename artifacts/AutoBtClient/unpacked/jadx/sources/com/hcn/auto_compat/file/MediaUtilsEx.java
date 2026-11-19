package com.hcn.auto_compat.file;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import com.hcn.common.utils.HUriUtils;
import java.io.File;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class MediaUtilsEx {
    private static final String TAG = "MediaUtilsEx";
    public static final String UNSUPPORTED = "unsupported";

    public static String parseAudioUriFilePath(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT <= 27) {
            return UNSUPPORTED;
        }
        File fileUri2File = HUriUtils.uri2File(uri);
        if (Objects.isNull(fileUri2File)) {
            return null;
        }
        return fileUri2File.getAbsolutePath();
    }

    public static String parseVideoUriFilePath(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT <= 27) {
            return UNSUPPORTED;
        }
        File fileUri2File = HUriUtils.uri2File(uri);
        if (Objects.isNull(fileUri2File)) {
            return null;
        }
        return fileUri2File.getAbsolutePath();
    }

    public static String parseImageUriFilePath(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT <= 27) {
            return UNSUPPORTED;
        }
        File fileUri2File = HUriUtils.uri2File(uri);
        if (Objects.isNull(fileUri2File)) {
            return null;
        }
        return fileUri2File.getAbsolutePath();
    }
}
