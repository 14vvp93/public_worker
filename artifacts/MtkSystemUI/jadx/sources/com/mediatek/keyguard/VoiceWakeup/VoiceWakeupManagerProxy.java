package com.mediatek.keyguard.VoiceWakeup;

import android.util.Log;
import com.android.keyguard.KeyguardSecurityModel;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class VoiceWakeupManagerProxy {
    private static VoiceWakeupManagerProxy sInstance = null;
    private static Class<?> mVoiceWakeupClsObj = null;
    private static Object mVoiceWakeupInstance = null;

    public VoiceWakeupManagerProxy() {
        Log.d("VoiceWakeupManagerProxy", "constructor is called.");
    }

    public static VoiceWakeupManagerProxy getInstance() {
        if (sInstance == null) {
            Log.d("VoiceWakeupManagerProxy", "getInstance(...) create one.");
            sInstance = new VoiceWakeupManagerProxy();
            createVoiceWakeupManagerInstance();
        }
        return sInstance;
    }

    private static void createVoiceWakeupManagerInstance() {
        try {
            mVoiceWakeupClsObj = Class.forName("com.mediatek.keyguard.VoiceWakeup.VoiceWakeupManager");
            if (mVoiceWakeupClsObj != null) {
                Method vowInstance = mVoiceWakeupClsObj.getDeclaredMethod("getInstance", new Class[0]);
                mVoiceWakeupInstance = vowInstance.invoke(mVoiceWakeupClsObj, new Object[0]);
            }
        } catch (Exception e) {
            Log.e("VoiceWakeupManagerProxy", "createVoiceWakeupManagerInstance error: " + e);
        }
    }

    public boolean isDismissAndLaunchApp() {
        try {
            if (mVoiceWakeupInstance != null) {
                Method method = mVoiceWakeupClsObj.getDeclaredMethod("isDismissAndLaunchApp", new Class[0]);
                boolean result = ((Boolean) method.invoke(mVoiceWakeupInstance, new Object[0])).booleanValue();
                return result;
            }
            return false;
        } catch (Exception e) {
            Log.e("VoiceWakeupManagerProxy", "reflect isDismissAndLaunchApp error");
            Log.e("VoiceWakeupManagerProxy", Log.getStackTraceString(e));
            return false;
        }
    }

    public boolean onDismiss() {
        try {
            if (mVoiceWakeupInstance != null) {
                Method method = mVoiceWakeupClsObj.getDeclaredMethod("onDismiss", new Class[0]);
                boolean result = ((Boolean) method.invoke(mVoiceWakeupInstance, new Object[0])).booleanValue();
                return result;
            }
            return false;
        } catch (Exception e) {
            Log.e("VoiceWakeupManagerProxy", "reflect onDismiss error");
            Log.e("VoiceWakeupManagerProxy", Log.getStackTraceString(e));
            return false;
        }
    }

    public void notifySecurityModeChange(KeyguardSecurityModel.SecurityMode currentMode, KeyguardSecurityModel.SecurityMode nextMode) {
        try {
            if (mVoiceWakeupInstance != null) {
                Method method = mVoiceWakeupClsObj.getDeclaredMethod("notifySecurityModeChange", KeyguardSecurityModel.SecurityMode.class, KeyguardSecurityModel.SecurityMode.class);
                method.invoke(mVoiceWakeupInstance, currentMode, nextMode);
            }
        } catch (Exception e) {
            Log.e("VoiceWakeupManagerProxy", "reflect notifySecurityModeChange error");
            Log.e("VoiceWakeupManagerProxy", Log.getStackTraceString(e));
        }
    }
}
