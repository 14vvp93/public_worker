package com.android.car;

import android.content.Context;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Slog;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class CarServiceManager {
    private static final int SERVICE_CALL_WARN_TIME_MS = 50;
    private static final String TAG = "CarServiceManager";
    private final Context mContext;
    private final ArrayList<CarService> mServices = new ArrayList<>();
    private int mCurrentPhase = -1;

    public CarServiceManager(Context context) {
        this.mContext = context;
    }

    public CarService startService(String className) {
        try {
            return startService(Class.forName(className));
        } catch (ClassNotFoundException ex) {
            Slog.i(TAG, "Starting " + className);
            throw new RuntimeException("Failed to create service " + className + ": service class not found, ", ex);
        }
    }

    public <T extends CarService> T startService(Class<T> serviceClass) {
        try {
            String name = serviceClass.getName();
            Slog.i(TAG, "Starting " + name);
            Trace.traceBegin(524288L, "StartService " + name);
            if (!CarService.class.isAssignableFrom(serviceClass)) {
                throw new RuntimeException("Failed to create " + name + ": service must extend " + CarService.class.getName());
            }
            try {
                try {
                    try {
                        Constructor<T> constructor = serviceClass.getConstructor(Context.class);
                        T service = constructor.newInstance(this.mContext);
                        startService(service);
                        Trace.traceEnd(524288L);
                        return service;
                    } catch (InstantiationException ex) {
                        throw new RuntimeException("Failed to create service " + name + ": service could not be instantiated", ex);
                    }
                } catch (InvocationTargetException ex2) {
                    throw new RuntimeException("Failed to create service " + name + ": service constructor threw an exception", ex2);
                }
            } catch (IllegalAccessException ex3) {
                throw new RuntimeException("Failed to create service " + name + ": service must have a public constructor with a Context argument", ex3);
            } catch (NoSuchMethodException ex4) {
                throw new RuntimeException("Failed to create service " + name + ": service must have a public constructor with a Context argument", ex4);
            }
        } catch (Throwable th) {
            Trace.traceEnd(524288L);
            throw th;
        }
    }

    public void startService(CarService service) {
        this.mServices.add(service);
        long time = SystemClock.elapsedRealtime();
        try {
            service.onStart();
            warnIfTooLong(SystemClock.elapsedRealtime() - time, service, "onStart");
        } catch (RuntimeException ex) {
            throw new RuntimeException("Failed to start service " + service.getClass().getName() + ": onStart threw an exception", ex);
        }
    }

    public void startBootPhase(int phase) {
        if (phase <= this.mCurrentPhase) {
            throw new IllegalArgumentException("Next phase must be larger than previous");
        }
        this.mCurrentPhase = phase;
        Slog.i(TAG, "Starting phase " + this.mCurrentPhase);
        try {
            Trace.traceBegin(524288L, "OnBootPhase " + phase);
            int serviceLen = this.mServices.size();
            for (int i = 0; i < serviceLen; i++) {
                CarService service = this.mServices.get(i);
                long time = SystemClock.elapsedRealtime();
                Trace.traceBegin(524288L, service.getClass().getName());
                try {
                    service.onBootPhase(this.mCurrentPhase);
                    warnIfTooLong(SystemClock.elapsedRealtime() - time, service, "onBootPhase");
                    Trace.traceEnd(524288L);
                } catch (Exception ex) {
                    throw new RuntimeException("Failed to boot service " + service.getClass().getName() + ": onBootPhase threw an exception during phase " + this.mCurrentPhase, ex);
                }
            }
            Trace.traceEnd(524288L);
        } catch (Throwable th) {
            Trace.traceEnd(524288L);
            throw th;
        }
    }

    public boolean isBootCompleted() {
        return this.mCurrentPhase >= 1000;
    }

    private void warnIfTooLong(long duration, CarService service, String operation) {
        if (duration > 50) {
            Slog.w(TAG, "Service " + service.getClass().getName() + " took " + duration + " ms in " + operation);
        }
    }

    public void dump() {
        StringBuilder builder = new StringBuilder();
        builder.append("Current phase: ");
        builder.append(this.mCurrentPhase);
        builder.append("\n");
        builder.append("Services:\n");
        int startedLen = this.mServices.size();
        for (int i = 0; i < startedLen; i++) {
            CarService service = this.mServices.get(i);
            builder.append("\t");
            builder.append(service.getClass().getSimpleName());
            builder.append("\n");
        }
        Slog.e(TAG, builder.toString());
    }
}
