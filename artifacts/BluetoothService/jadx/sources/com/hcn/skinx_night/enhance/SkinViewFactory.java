package com.hcn.skinx_night.enhance;

import android.content.Context;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;
import com.hcn.common.misc.LogUtils;
import java.lang.reflect.Constructor;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SkinViewFactory {
    private static final String TAG = "SkinViewFactory";
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
    private static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    private static final Object[] mConstructorArgs = new Object[2];

    public static View inflateViewByClassName(String str, Context context, AttributeSet attributeSet) throws InflateException {
        View viewNewInstance;
        if (str == null || context == null) {
            return null;
        }
        Map<String, Constructor<? extends View>> map = sConstructorMap;
        Constructor<? extends View> constructor = map.get(str);
        try {
            if (constructor == null) {
                try {
                    constructor = context.getClassLoader().loadClass(str).asSubclass(View.class).getConstructor(sConstructorSignature);
                    map.put(str, constructor);
                } catch (ClassNotFoundException unused) {
                    LogUtils.wTag(TAG, "View class not found: " + str);
                    Object[] objArr = mConstructorArgs;
                    objArr[0] = null;
                    objArr[1] = null;
                    return null;
                } catch (ReflectiveOperationException e) {
                    LogUtils.eTag(TAG, "Failed to inflate class: " + str + ", reason: " + e.getMessage());
                    Object[] objArr2 = mConstructorArgs;
                    objArr2[0] = null;
                    objArr2[1] = null;
                    return null;
                }
            }
            constructor.setAccessible(true);
            Object[] objArr3 = mConstructorArgs;
            synchronized (objArr3) {
                objArr3[0] = context;
                objArr3[1] = attributeSet;
                LogUtils.iTag(TAG, " New View class : " + str);
                viewNewInstance = constructor.newInstance(objArr3);
            }
            objArr3[0] = null;
            objArr3[1] = null;
            return viewNewInstance;
        } catch (Throwable th) {
            Object[] objArr4 = mConstructorArgs;
            objArr4[0] = null;
            objArr4[1] = null;
            throw th;
        }
    }

    public static void clearConstructorCache() {
        sConstructorMap.clear();
    }
}
