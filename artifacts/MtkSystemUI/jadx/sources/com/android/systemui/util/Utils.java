package com.android.systemui.util;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.android.systemui.SysUiServiceProvider;
import com.android.systemui.statusbar.CommandQueue;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    public static <T> void safeForeach(List<T> list, Consumer<T> c) {
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i) != null) {
                c.accept(list.get(i));
            }
        }
    }

    public static class DisableStateTracker implements View.OnAttachStateChangeListener, CommandQueue.Callbacks {
        private boolean mDisabled;
        private final int mMask1;
        private final int mMask2;
        private View mView;

        public DisableStateTracker(int disableMask, int disable2Mask) {
            this.mMask1 = disableMask;
            this.mMask2 = disable2Mask;
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View v) {
            this.mView = v;
            ((CommandQueue) SysUiServiceProvider.getComponent(v.getContext(), CommandQueue.class)).addCallbacks(this);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View v) {
            ((CommandQueue) SysUiServiceProvider.getComponent(this.mView.getContext(), CommandQueue.class)).removeCallbacks(this);
            this.mView = null;
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public void disable(int state1, int state2, boolean animate) {
            boolean disabled = ((this.mMask1 & state1) == 0 && (this.mMask2 & state2) == 0) ? false : true;
            if (disabled == this.mDisabled) {
                return;
            }
            this.mDisabled = disabled;
            this.mView.setVisibility(disabled ? 8 : 0);
        }
    }

    public static String getSystemProperty(String property, String defaultValue) {
        try {
            Class<?> SystemProperties = Class.forName("android.os.SystemProperties");
            Method get = SystemProperties.getMethod("get", String.class, String.class);
            Object[] params = {new String(property), defaultValue};
            String ret = (String) get.invoke(SystemProperties, params);
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    public static void startActivity(Context context, String packageName, String className) {
        if ((1 == Settings.System.getInt(context.getContentResolver(), "device_lock_status", 0) && !"com.android.settings".equals(packageName)) || context == null || TextUtils.isEmpty(packageName) || TextUtils.isEmpty(className)) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.addFlags(270532608);
            intent.setClassName(packageName, className);
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                if (packageName.equals("com.google.android.youtube")) {
                    Intent intent2 = new Intent();
                    intent2.setAction("android.intent.action.MAIN");
                    intent2.addCategory("android.intent.category.LAUNCHER");
                    intent2.addFlags(270532608);
                    intent2.setClassName(packageName, "com.google.android.apps.youtube.app.application.Shell$HomeActivity");
                    context.startActivity(intent2);
                }
            } catch (Exception e2) {
                Log.e("Utils", "", e2);
            }
            Log.e("Utils", "", e);
        }
    }

    public static boolean is8321() {
        return getSystemProperty("ro.build.product", "").startsWith("tb8321p2");
    }
}
