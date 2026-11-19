package android.support.v17.leanback.widget;

import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
final class ShadowHelper {
    static boolean supportsDynamicShadow() {
        return Build.VERSION.SDK_INT >= 21;
    }
}
