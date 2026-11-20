package com.hcn.auto_compat.os;

import android.os.Handler;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class HandlerCompat {
    public static boolean hasCallbacks(Handler handler, Runnable runnable) {
        if (handler == null || runnable == null) {
            return false;
        }
        try {
            Method declaredMethod = Handler.class.getDeclaredMethod("hasCallbacks", Runnable.class);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(handler, runnable);
            if (objInvoke instanceof Boolean) {
                return ((Boolean) objInvoke).booleanValue();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
