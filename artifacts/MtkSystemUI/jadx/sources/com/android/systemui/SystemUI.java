package com.android.systemui;

import android.app.Notification;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class SystemUI implements SysUiServiceProvider {
    public Map<Class<?>, Object> mComponents;
    public Context mContext;

    public abstract void start();

    protected void onConfigurationChanged(Configuration newConfig) {
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
    }

    protected void onBootCompleted() {
    }

    @Override // com.android.systemui.SysUiServiceProvider
    public <T> T getComponent(Class<T> cls) {
        if (this.mComponents != null) {
            return (T) this.mComponents.get(cls);
        }
        return null;
    }

    public <T, C extends T> void putComponent(Class<T> interfaceType, C component) {
        if (this.mComponents != null) {
            this.mComponents.put(interfaceType, component);
        }
    }

    public static void overrideNotificationAppName(Context context, Notification.Builder n, boolean system) {
        String appName;
        Bundle extras = new Bundle();
        if (system) {
            appName = context.getString(android.R.string.ext_media_unsupported_notification_title);
        } else {
            appName = context.getString(android.R.string.ext_media_unsupported_notification_message);
        }
        extras.putString("android.substName", appName);
        n.addExtras(extras);
    }
}
