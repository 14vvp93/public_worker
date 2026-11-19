package com.hcn.skin.support.app;

import android.content.Context;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;
import com.hcn.skin.support.utils.SkinLog;
import java.lang.reflect.Constructor;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SkinViewInflater {
    private static final String TAG = "SkinViewInflater";
    private static final Object[] mConstructorArgs = new Object[2];
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
    private static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    private static final String[] sClassPrefixList = {"android.widget.", "android.view.", "android.webkit."};

    public static View createViewFromTag(Context context, String str, AttributeSet attributeSet) {
        if ("view".equals(str)) {
            str = attributeSet.getAttributeValue(null, "class");
        }
        try {
            Object[] objArr = mConstructorArgs;
            objArr[0] = context;
            objArr[1] = attributeSet;
            if (-1 != str.indexOf(46)) {
                View viewCreateView = createView(context, str, null);
                objArr[0] = null;
                objArr[1] = null;
                return viewCreateView;
            }
            int i = 0;
            while (true) {
                String[] strArr = sClassPrefixList;
                if (i >= strArr.length) {
                    Object[] objArr2 = mConstructorArgs;
                    objArr2[0] = null;
                    objArr2[1] = null;
                    return null;
                }
                View viewCreateView2 = createView(context, str, strArr[i]);
                if (viewCreateView2 != null) {
                    Object[] objArr3 = mConstructorArgs;
                    objArr3[0] = null;
                    objArr3[1] = null;
                    return viewCreateView2;
                }
                i++;
            }
        } catch (Exception unused) {
            Object[] objArr4 = mConstructorArgs;
            objArr4[0] = null;
            objArr4[1] = null;
            return null;
        } catch (Throwable th) {
            Object[] objArr5 = mConstructorArgs;
            objArr5[0] = null;
            objArr5[1] = null;
            throw th;
        }
    }

    private static View createView(Context context, String str, String str2) throws InflateException, ClassNotFoundException {
        Map<String, Constructor<? extends View>> map = sConstructorMap;
        Constructor<? extends View> constructor = map.get(str);
        if (constructor == null) {
            try {
                SkinLog.m56v("loadClass:" + (str2 != null ? str2 + str : str));
                constructor = context.getClassLoader().loadClass(str2 != null ? str2 + str : str).asSubclass(View.class).getConstructor(sConstructorSignature);
                map.put(str, constructor);
            } catch (Exception unused) {
                StringBuilder sbAppend = new StringBuilder().append("loadClass ");
                if (str2 != null) {
                    str = str2 + str;
                }
                SkinLog.m52e(sbAppend.append(str).append(" Fail!").toString());
                return null;
            }
        }
        constructor.setAccessible(true);
        return constructor.newInstance(mConstructorArgs);
    }
}
