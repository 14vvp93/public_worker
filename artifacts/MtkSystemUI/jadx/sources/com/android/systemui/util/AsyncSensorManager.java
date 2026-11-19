package com.android.systemui.util;

import android.hardware.HardwareBuffer;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorDirectChannel;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEventListener;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.MemoryFile;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AsyncSensorManager extends SensorManager {

    @VisibleForTesting
    final Handler mHandler;
    private final HandlerThread mHandlerThread = new HandlerThread("async_sensor");
    private final SensorManager mInner;
    private final List<Sensor> mSensorCache;

    public AsyncSensorManager(SensorManager inner) {
        this.mInner = inner;
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
        this.mSensorCache = this.mInner.getSensorList(-1);
    }

    protected List<Sensor> getFullSensorList() {
        return this.mSensorCache;
    }

    protected List<Sensor> getFullDynamicSensorList() {
        return this.mInner.getDynamicSensorList(-1);
    }

    protected boolean registerListenerImpl(final SensorEventListener listener, final Sensor sensor, final int delayUs, final Handler handler, final int maxReportLatencyUs, int reservedFlags) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.util.-$$Lambda$AsyncSensorManager$d7xLBI7qZK784-fy2ffbXtJPEGA
            @Override // java.lang.Runnable
            public final void run() {
                AsyncSensorManager.lambda$registerListenerImpl$0(this.f$0, listener, sensor, delayUs, maxReportLatencyUs, handler);
            }
        });
        return true;
    }

    public static /* synthetic */ void lambda$registerListenerImpl$0(AsyncSensorManager asyncSensorManager, SensorEventListener listener, Sensor sensor, int delayUs, int maxReportLatencyUs, Handler handler) {
        if (!asyncSensorManager.mInner.registerListener(listener, sensor, delayUs, maxReportLatencyUs, handler)) {
            Log.e("AsyncSensorManager", "Registering " + listener + " for " + sensor + " failed.");
        }
    }

    protected boolean flushImpl(SensorEventListener listener) {
        return this.mInner.flush(listener);
    }

    protected SensorDirectChannel createDirectChannelImpl(MemoryFile memoryFile, HardwareBuffer hardwareBuffer) {
        throw new UnsupportedOperationException("not implemented");
    }

    protected void destroyDirectChannelImpl(SensorDirectChannel channel) {
        throw new UnsupportedOperationException("not implemented");
    }

    protected int configureDirectChannelImpl(SensorDirectChannel channel, Sensor s, int rate) {
        throw new UnsupportedOperationException("not implemented");
    }

    protected void registerDynamicSensorCallbackImpl(final SensorManager.DynamicSensorCallback callback, final Handler handler) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.util.-$$Lambda$AsyncSensorManager$Hwm7wwA6xT-rLeZcpNr7J2BNQWE
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mInner.registerDynamicSensorCallback(callback, handler);
            }
        });
    }

    protected void unregisterDynamicSensorCallbackImpl(final SensorManager.DynamicSensorCallback callback) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.util.-$$Lambda$AsyncSensorManager$ioH8tBFNQaFrSnUQEwQdi_ri4K0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mInner.unregisterDynamicSensorCallback(callback);
            }
        });
    }

    protected boolean requestTriggerSensorImpl(final TriggerEventListener listener, final Sensor sensor) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.util.-$$Lambda$AsyncSensorManager$q1TQFoUPad2_Ye0DbYS5yACL5CU
            @Override // java.lang.Runnable
            public final void run() {
                AsyncSensorManager.lambda$requestTriggerSensorImpl$3(this.f$0, listener, sensor);
            }
        });
        return true;
    }

    public static /* synthetic */ void lambda$requestTriggerSensorImpl$3(AsyncSensorManager asyncSensorManager, TriggerEventListener listener, Sensor sensor) {
        if (!asyncSensorManager.mInner.requestTriggerSensor(listener, sensor)) {
            Log.e("AsyncSensorManager", "Requesting " + listener + " for " + sensor + " failed.");
        }
    }

    protected boolean cancelTriggerSensorImpl(final TriggerEventListener listener, final Sensor sensor, boolean disable) {
        Preconditions.checkArgument(disable);
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.util.-$$Lambda$AsyncSensorManager$ssfdtdJfSGgmlHJqcz8km7BLSQE
            @Override // java.lang.Runnable
            public final void run() {
                AsyncSensorManager.lambda$cancelTriggerSensorImpl$4(this.f$0, listener, sensor);
            }
        });
        return true;
    }

    public static /* synthetic */ void lambda$cancelTriggerSensorImpl$4(AsyncSensorManager asyncSensorManager, TriggerEventListener listener, Sensor sensor) {
        if (!asyncSensorManager.mInner.cancelTriggerSensor(listener, sensor)) {
            Log.e("AsyncSensorManager", "Canceling " + listener + " for " + sensor + " failed.");
        }
    }

    protected boolean initDataInjectionImpl(boolean enable) {
        throw new UnsupportedOperationException("not implemented");
    }

    protected boolean injectSensorDataImpl(Sensor sensor, float[] values, int accuracy, long timestamp) {
        throw new UnsupportedOperationException("not implemented");
    }

    protected boolean setOperationParameterImpl(final SensorAdditionalInfo parameter) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.util.-$$Lambda$AsyncSensorManager$yWyG4VnNvYVS3_A55eZfq00QEBQ
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mInner.setOperationParameter(parameter);
            }
        });
        return true;
    }

    protected void unregisterListenerImpl(final SensorEventListener listener, final Sensor sensor) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.util.-$$Lambda$AsyncSensorManager$-X62bKcCGUmPVwchbYAj2vQKUTg
            @Override // java.lang.Runnable
            public final void run() {
                AsyncSensorManager.lambda$unregisterListenerImpl$6(this.f$0, sensor, listener);
            }
        });
    }

    public static /* synthetic */ void lambda$unregisterListenerImpl$6(AsyncSensorManager asyncSensorManager, Sensor sensor, SensorEventListener listener) {
        if (sensor == null) {
            asyncSensorManager.mInner.unregisterListener(listener);
        } else {
            asyncSensorManager.mInner.unregisterListener(listener, sensor);
        }
    }
}
