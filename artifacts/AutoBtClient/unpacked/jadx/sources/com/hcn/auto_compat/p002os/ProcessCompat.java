package com.hcn.auto_compat.p002os;

import android.content.Context;
import android.os.Process;
import com.hcn.common.app.HAppUtils;
import com.hcn.common.utils.HUtilsEx;

/* JADX INFO: loaded from: classes.dex */
public class ProcessCompat {
    public static final int THREAD_PRIORITY_TOP_APP_BOOST = -10;
    private static final boolean sSupportAdjustThreadPriority = "1".equals(HUtilsEx.getSystemProperty("ro.media.support.adj_priority", "0"));
    private static boolean sAdjustedThreadPriority = false;

    public static void setThreadPriority(Context context, int i) {
        if (sSupportAdjustThreadPriority) {
            if (i <= -16) {
                if (context != null ? HAppUtils.isAppForeground(context.getPackageName()) : true) {
                    sAdjustedThreadPriority = true;
                    Process.setThreadPriority(i);
                    return;
                }
                return;
            }
            if (sAdjustedThreadPriority) {
                sAdjustedThreadPriority = false;
                Process.setThreadPriority(i);
            }
        }
    }
}
