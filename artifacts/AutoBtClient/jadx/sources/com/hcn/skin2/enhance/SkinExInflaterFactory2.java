package com.hcn.skin2.enhance;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.hcn.common.misc.LogUtils;
import com.hcn.common.utils.HUtilsEx;
import com.hcn.skin2.base.TextViewAttr;
import com.hcn.skin2.runtime.RRO;
import java.lang.reflect.Constructor;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SkinExInflaterFactory2 implements LayoutInflater.Factory2 {
    private static final String TAG = "SkinExInflaterFactory2";
    private final Map<String, TextViewAttr> sTextViewAttrMap;
    private static final Object[] mConstructorArgs = new Object[2];
    private static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
    private static final String[] sClassPrefixList = {"android.widget.", "android.view."};
    private static final String[] sTextViewClassNameList = {"TextView", "Button", "RadioButton", "CheckBox"};
    private static final String ENHANCE_TEXTVIEW_PROP = "persist.sys.skinx_enhance_textview";
    private static final boolean SUPPORT_ENHANCE_TEXTVIEW = HUtilsEx.getSystemProperty(ENHANCE_TEXTVIEW_PROP, 0).equals(1);
    private static IOverlayCallback mOverlayCallback = null;

    public static void setOverlayCallback(IOverlayCallback iOverlayCallback) {
        mOverlayCallback = iOverlayCallback;
    }

    private static boolean tryCheckGeneralTextView(String str) {
        for (String str2 : sTextViewClassNameList) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private static boolean tryCheckEnhanceTextView(String str) {
        if (SUPPORT_ENHANCE_TEXTVIEW) {
            return str.equals("CheckedTextView") || str.equals("com.hcn.media_view.HTextView");
        }
        return false;
    }

    private static boolean tryCheckCustomizeTextView(String str) {
        IOverlayCallback iOverlayCallback = mOverlayCallback;
        return iOverlayCallback != null && iOverlayCallback.isSupportRROView(str);
    }

    private SkinExInflaterFactory2() {
        this.sTextViewAttrMap = new ArrayMap();
    }

    private static final class SInstanceHolder {
        static final SkinExInflaterFactory2 S_INSTANCE = new SkinExInflaterFactory2();

        private SInstanceHolder() {
        }
    }

    public static SkinExInflaterFactory2 instance() {
        return SInstanceHolder.S_INSTANCE;
    }

    @Override // android.view.LayoutInflater.Factory2
    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        LogUtils.vTag(TAG, "onCreateView(4 parameter)");
        return onCreateView(str, context, attributeSet);
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        int textAttrRid;
        View viewTryReplaceAndCreateView;
        LogUtils.vTag(TAG, "onCreateView(" + str + ", ...)");
        Object[] objArr = mConstructorArgs;
        objArr[0] = context;
        objArr[1] = attributeSet;
        IOverlayCallback iOverlayCallback = mOverlayCallback;
        if (iOverlayCallback != null && (viewTryReplaceAndCreateView = iOverlayCallback.tryReplaceAndCreateView(str, context, attributeSet)) != null) {
            return viewTryReplaceAndCreateView;
        }
        if (!tryCheckGeneralTextView(str) && !tryCheckCustomizeTextView(str) && !tryCheckEnhanceTextView(str)) {
            return null;
        }
        View viewBuildViewFromName = buildViewFromName(context, str, attributeSet);
        if (viewBuildViewFromName instanceof TextView) {
            TextView textView = (TextView) viewBuildViewFromName;
            if (RRO.instance().isEnabled() && (textAttrRid = parseTextAttrRid(attributeSet)) != 0) {
                textView.setText(SkinExCompatResources.getInstance().getString(textAttrRid, true));
            }
        }
        return viewBuildViewFromName;
    }

    public static View buildViewFromName(Context context, String str, AttributeSet attributeSet) {
        try {
            try {
                Object[] objArr = mConstructorArgs;
                objArr[0] = context;
                objArr[1] = attributeSet;
                if (-1 != str.indexOf(46)) {
                    View viewBuildView = buildView(context, str, null);
                    objArr[0] = null;
                    objArr[1] = null;
                    return viewBuildView;
                }
                for (String str2 : sClassPrefixList) {
                    View viewBuildView2 = buildView(context, str, str2);
                    if (viewBuildView2 != null) {
                        Object[] objArr2 = mConstructorArgs;
                        objArr2[0] = null;
                        objArr2[1] = null;
                        return viewBuildView2;
                    }
                }
                Object[] objArr3 = mConstructorArgs;
                objArr3[0] = null;
                objArr3[1] = null;
                return null;
            } catch (Exception e) {
                LogUtils.dTag(TAG, "buildViewFromName: " + str + ", " + e.getMessage());
                Object[] objArr4 = mConstructorArgs;
                objArr4[0] = null;
                objArr4[1] = null;
            }
        } catch (Throwable th) {
            Object[] objArr5 = mConstructorArgs;
            objArr5[0] = null;
            objArr5[1] = null;
            throw th;
        }
    }

    private static View buildView(Context context, String str, String str2) throws InflateException {
        Map<String, Constructor<? extends View>> map = sConstructorMap;
        Constructor<? extends View> constructor = map.get(str);
        String str3 = TextUtils.isEmpty(str2) ? str : str2 + str;
        if (constructor == null) {
            try {
                LogUtils.m48v(TAG, "loadClass: " + str3);
                constructor = context.getClassLoader().loadClass(str3).asSubclass(View.class).getConstructor(sConstructorSignature);
                map.put(str, constructor);
            } catch (Exception unused) {
                LogUtils.m48v("loadClass " + str3 + " Fail!");
                return null;
            }
        }
        constructor.setAccessible(true);
        return constructor.newInstance(mConstructorArgs);
    }

    private int parseTextAttrRid(AttributeSet attributeSet) {
        int attributeCount = attributeSet.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = attributeSet.getAttributeName(i);
            String attributeValue = attributeSet.getAttributeValue(i);
            if ("text".equals(attributeName) && attributeValue.startsWith("@")) {
                try {
                    return Integer.parseInt(attributeValue.substring(1));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
        return 0;
    }
}
