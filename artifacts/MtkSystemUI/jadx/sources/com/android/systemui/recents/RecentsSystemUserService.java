package com.android.systemui.recents;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.android.systemui.SystemUIApplication;

/* JADX INFO: loaded from: classes.dex */
public class RecentsSystemUserService extends Service {
    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        SystemUIApplication app = (SystemUIApplication) getApplication();
        Recents recents = (Recents) app.getComponent(Recents.class);
        if (recents != null) {
            return recents.getSystemUserCallbacks();
        }
        return null;
    }
}
