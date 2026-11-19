package com.android.systemui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class SystemUISecondaryUserService extends Service {
    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        ((SystemUIApplication) getApplication()).startSecondaryUserServicesIfNeeded();
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
                if (ui != null) {
                    pw.println("dumping service: " + ui.getClass().getName());
                    ui.dump(fd, pw, args);
                }
                i++;
            }
            return;
        }
        String svc = args[0];
        int length2 = services.length;
        while (i < length2) {
            SystemUI ui2 = services[i];
            if (ui2 != null) {
                String name = ui2.getClass().getName();
                if (name.endsWith(svc)) {
                    ui2.dump(fd, pw, args);
                }
            }
            i++;
        }
    }
}
