package com.android.systemui;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.internal.os.BinderInternal;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.PluginManagerImpl;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class SystemUIService extends Service {
    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        ((SystemUIApplication) getApplication()).startServicesIfNeeded();
        if (Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.crash_sysui", false)) {
            throw new RuntimeException();
        }
        if (Build.IS_DEBUGGABLE) {
            BinderInternal.nSetBinderProxyCountEnabled(true);
            BinderInternal.nSetBinderProxyCountWatermarks(1000, 900);
            BinderInternal.setBinderProxyCountCallback(new BinderInternal.BinderProxyLimitListener() { // from class: com.android.systemui.SystemUIService.1
                public void onLimitReached(int uid) {
                    Slog.w("SystemUIService", "uid " + uid + " sent too many Binder proxies to uid " + Process.myUid());
                }
            }, (Handler) Dependency.get(Dependency.MAIN_HANDLER));
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    protected void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        SystemUI[] services = ((SystemUIApplication) getApplication()).getServices();
        int i = 0;
        if (args == null || args.length == 0) {
            int length = services.length;
            while (i < length) {
                SystemUI ui = services[i];
                pw.println("dumping service: " + ui.getClass().getName());
                ui.dump(fd, pw, args);
                i++;
            }
            if (Build.IS_DEBUGGABLE) {
                pw.println("dumping plugins:");
                ((PluginManagerImpl) Dependency.get(PluginManager.class)).dump(fd, pw, args);
                return;
            }
            return;
        }
        String svc = args[0];
        int length2 = services.length;
        while (i < length2) {
            SystemUI ui2 = services[i];
            String name = ui2.getClass().getName();
            if (name.endsWith(svc)) {
                ui2.dump(fd, pw, args);
            }
            i++;
        }
    }
}
