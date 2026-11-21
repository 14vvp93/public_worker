package com.android.car;

import android.app.ActivityThread;
import android.content.Context;
import android.os.IBinder;
import android.os.ServiceManager;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public abstract class CarService {
    public static final int PHASE_ACTIVITY_MANAGER_READY = 550;
    public static final int PHASE_BOOT_COMPLETED = 1000;
    private final Context mContext;

    public abstract void onStart();

    public CarService(Context context) {
        this.mContext = context;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Context getUiContext() {
        return ActivityThread.currentActivityThread().getSystemUiContext();
    }

    public void onBootPhase(int phase) {
    }

    protected final void publishBinderService(String name, IBinder service) {
        publishBinderService(name, service, false);
    }

    protected final void publishBinderService(String name, IBinder service, boolean allowIsolated) {
        publishBinderService(name, service, allowIsolated, 8);
    }

    protected final void publishBinderService(String name, IBinder service, boolean allowIsolated, int dumpPriority) {
        ServiceManager.addService(name, service, allowIsolated, dumpPriority);
    }

    protected final IBinder getBinderService(String name) {
        return ServiceManager.getService(name);
    }
}
