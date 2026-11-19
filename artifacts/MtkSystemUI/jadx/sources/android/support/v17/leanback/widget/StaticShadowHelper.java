package android.support.v17.leanback.widget;

import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
final class StaticShadowHelper {
    static boolean supportsShadow() {
        return Build.VERSION.SDK_INT >= 21;
    }
}
