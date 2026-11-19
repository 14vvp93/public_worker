package com.android.systemui.doze;

import android.app.AlarmManager;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.util.Log;
import com.android.internal.hardware.AmbientDisplayConfiguration;
import com.android.internal.util.Preconditions;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.Assert;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.PrintWriter;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* JADX INFO: loaded from: classes.dex */
public class DozeTriggers implements DozeMachine.Part {
    private static final boolean DEBUG = DozeService.DEBUG;
    private final boolean mAllowPulseTriggers;
    private final AmbientDisplayConfiguration mConfig;
    private final Context mContext;
    private final DozeHost mDozeHost;
    private final DozeParameters mDozeParameters;
    private final DozeSensors mDozeSensors;
    private final Handler mHandler;
    private final DozeMachine mMachine;
    private long mNotificationPulseTime;
    private boolean mPulsePending;
    private final SensorManager mSensorManager;
    private final UiModeManager mUiModeManager;
    private final WakeLock mWakeLock;
    private final TriggerReceiver mBroadcastReceiver = new TriggerReceiver();
    private DozeHost.Callback mHostCallback = new DozeHost.Callback() { // from class: com.android.systemui.doze.DozeTriggers.2
        @Override // com.android.systemui.doze.DozeHost.Callback
        public void onNotificationHeadsUp() {
            DozeTriggers.this.onNotification();
        }

        @Override // com.android.systemui.doze.DozeHost.Callback
        public void onPowerSaveChanged(boolean active) {
            if (active) {
                DozeTriggers.this.mMachine.requestState(DozeMachine.State.FINISH);
            }
        }
    };

    public DozeTriggers(Context context, DozeMachine machine, DozeHost dozeHost, AlarmManager alarmManager, AmbientDisplayConfiguration config, DozeParameters dozeParameters, SensorManager sensorManager, Handler handler, WakeLock wakeLock, boolean allowPulseTriggers) {
        this.mContext = context;
        this.mMachine = machine;
        this.mDozeHost = dozeHost;
        this.mConfig = config;
        this.mDozeParameters = dozeParameters;
        this.mSensorManager = sensorManager;
        this.mHandler = handler;
        this.mWakeLock = wakeLock;
        this.mAllowPulseTriggers = allowPulseTriggers;
        this.mDozeSensors = new DozeSensors(context, alarmManager, this.mSensorManager, dozeParameters, config, wakeLock, new DozeSensors.Callback() { // from class: com.android.systemui.doze.-$$Lambda$DozeTriggers$XvluYZ5eADBR1VYFZ4qS9j4p2P0
            @Override // com.android.systemui.doze.DozeSensors.Callback
            public final void onSensorPulse(int i, boolean z, float f, float f2) {
                this.f$0.onSensor(i, z, f, f2);
            }
        }, new Consumer() { // from class: com.android.systemui.doze.-$$Lambda$DozeTriggers$ulqUMEXi8OgK7771oZ9BOr21BBk
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.onProximityFar(((Boolean) obj).booleanValue());
            }
        }, dozeParameters.getPolicy());
        this.mUiModeManager = (UiModeManager) this.mContext.getSystemService(UiModeManager.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotification() {
        if (DozeMachine.DEBUG) {
            Log.d("DozeTriggers", "requestNotificationPulse");
        }
        this.mNotificationPulseTime = SystemClock.elapsedRealtime();
        if (this.mConfig.pulseOnNotificationEnabled(-2)) {
            requestPulse(1, false);
            DozeLog.traceNotificationPulse(this.mContext);
        }
    }

    private void proximityCheckThenCall(final IntConsumer callback, boolean alreadyPerformedProxCheck, final int pulseReason) {
        Boolean cachedProxFar = this.mDozeSensors.isProximityCurrentlyFar();
        if (alreadyPerformedProxCheck) {
            callback.accept(3);
        } else if (cachedProxFar != null) {
            callback.accept(cachedProxFar.booleanValue() ? 2 : 1);
        } else {
            final long start = SystemClock.uptimeMillis();
            new ProximityCheck() { // from class: com.android.systemui.doze.DozeTriggers.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // com.android.systemui.doze.DozeTriggers.ProximityCheck
                public void onProximityResult(int result) {
                    long end = SystemClock.uptimeMillis();
                    DozeLog.traceProximityResult(DozeTriggers.this.mContext, result == 1, end - start, pulseReason);
                    callback.accept(result);
                }
            }.check();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSensor(int pulseReason, boolean sensorPerformedProxCheck, final float screenX, final float screenY) {
        final boolean isDoubleTap = pulseReason == 4;
        boolean isPickup = pulseReason == 3;
        boolean isLongPress = pulseReason == 5;
        if (this.mConfig.alwaysOnEnabled(-2) && !isLongPress) {
            proximityCheckThenCall(new IntConsumer() { // from class: com.android.systemui.doze.-$$Lambda$DozeTriggers$Cox3oanmitA51LErVAi1Gs5cbKs
                @Override // java.util.function.IntConsumer
                public final void accept(int i) {
                    DozeTriggers.lambda$onSensor$0(this.f$0, isDoubleTap, screenX, screenY, i);
                }
            }, sensorPerformedProxCheck, pulseReason);
            return;
        }
        requestPulse(pulseReason, sensorPerformedProxCheck);
        if (isPickup) {
            long timeSinceNotification = SystemClock.elapsedRealtime() - this.mNotificationPulseTime;
            boolean withinVibrationThreshold = timeSinceNotification < ((long) this.mDozeParameters.getPickupVibrationThreshold());
            DozeLog.tracePickupPulse(this.mContext, withinVibrationThreshold);
        }
    }

    public static /* synthetic */ void lambda$onSensor$0(DozeTriggers dozeTriggers, boolean isDoubleTap, float screenX, float screenY, int result) {
        if (result == 1) {
            return;
        }
        if (isDoubleTap) {
            dozeTriggers.mDozeHost.onDoubleTap(screenX, screenY);
            dozeTriggers.mMachine.wakeUp();
        } else {
            dozeTriggers.mDozeHost.extendPulse();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProximityFar(boolean far) {
        boolean near = !far;
        DozeMachine.State state = this.mMachine.getState();
        boolean paused = state == DozeMachine.State.DOZE_AOD_PAUSED;
        boolean pausing = state == DozeMachine.State.DOZE_AOD_PAUSING;
        boolean aod = state == DozeMachine.State.DOZE_AOD;
        if (state == DozeMachine.State.DOZE_PULSING) {
            if (DEBUG) {
                Log.i("DozeTriggers", "Prox changed, ignore touch = " + near);
            }
            this.mDozeHost.onIgnoreTouchWhilePulsing(near);
        }
        if (far && (paused || pausing)) {
            if (DEBUG) {
                Log.i("DozeTriggers", "Prox FAR, unpausing AOD");
            }
            this.mMachine.requestState(DozeMachine.State.DOZE_AOD);
        } else if (near && aod) {
            if (DEBUG) {
                Log.i("DozeTriggers", "Prox NEAR, pausing AOD");
            }
            this.mMachine.requestState(DozeMachine.State.DOZE_AOD_PAUSING);
        }
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State oldState, DozeMachine.State newState) {
        switch (newState) {
            case INITIALIZED:
                this.mBroadcastReceiver.register(this.mContext);
                this.mDozeHost.addCallback(this.mHostCallback);
                checkTriggersAtInit();
                break;
            case DOZE:
            case DOZE_AOD:
                this.mDozeSensors.setProxListening(newState != DozeMachine.State.DOZE);
                if (oldState != DozeMachine.State.INITIALIZED) {
                    this.mDozeSensors.reregisterAllSensors();
                }
                this.mDozeSensors.setListening(true);
                break;
            case DOZE_AOD_PAUSED:
            case DOZE_AOD_PAUSING:
                this.mDozeSensors.setProxListening(true);
                this.mDozeSensors.setListening(false);
                break;
            case DOZE_PULSING:
                this.mDozeSensors.setTouchscreenSensorsListening(false);
                this.mDozeSensors.setProxListening(true);
                break;
            case FINISH:
                this.mBroadcastReceiver.unregister(this.mContext);
                this.mDozeHost.removeCallback(this.mHostCallback);
                this.mDozeSensors.setListening(false);
                this.mDozeSensors.setProxListening(false);
                break;
        }
    }

    private void checkTriggersAtInit() {
        if (this.mUiModeManager.getCurrentModeType() == 3 || this.mDozeHost.isPowerSaveActive() || this.mDozeHost.isBlockingDoze() || !this.mDozeHost.isProvisioned()) {
            this.mMachine.requestState(DozeMachine.State.FINISH);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestPulse(final int reason, boolean performedProxCheck) {
        Assert.isMainThread();
        this.mDozeHost.extendPulse();
        if (this.mPulsePending || !this.mAllowPulseTriggers || !canPulse()) {
            if (this.mAllowPulseTriggers) {
                DozeLog.tracePulseDropped(this.mContext, this.mPulsePending, this.mMachine.getState(), this.mDozeHost.isPulsingBlocked());
                return;
            }
            return;
        }
        boolean z = true;
        this.mPulsePending = true;
        IntConsumer intConsumer = new IntConsumer() { // from class: com.android.systemui.doze.-$$Lambda$DozeTriggers$hOBqImh7g4o_iKhiP9d1qETIOWo
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                DozeTriggers.lambda$requestPulse$1(this.f$0, reason, i);
            }
        };
        if (this.mDozeParameters.getProxCheckBeforePulse() && !performedProxCheck) {
            z = false;
        }
        proximityCheckThenCall(intConsumer, z, reason);
    }

    public static /* synthetic */ void lambda$requestPulse$1(DozeTriggers dozeTriggers, int reason, int result) {
        if (result == 1) {
            dozeTriggers.mPulsePending = false;
        } else {
            dozeTriggers.continuePulseRequest(reason);
        }
    }

    private boolean canPulse() {
        return this.mMachine.getState() == DozeMachine.State.DOZE || this.mMachine.getState() == DozeMachine.State.DOZE_AOD;
    }

    private void continuePulseRequest(int reason) {
        this.mPulsePending = false;
        if (this.mDozeHost.isPulsingBlocked() || !canPulse()) {
            DozeLog.tracePulseDropped(this.mContext, this.mPulsePending, this.mMachine.getState(), this.mDozeHost.isPulsingBlocked());
        } else {
            this.mMachine.requestPulse(reason);
        }
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void dump(PrintWriter pw) {
        pw.print(" notificationPulseTime=");
        pw.println(Formatter.formatShortElapsedTime(this.mContext, this.mNotificationPulseTime));
        pw.print(" pulsePending=");
        pw.println(this.mPulsePending);
        pw.println("DozeSensors:");
        this.mDozeSensors.dump(pw);
    }

    private abstract class ProximityCheck implements SensorEventListener, Runnable {
        private boolean mFinished;
        private float mMaxRange;
        private boolean mRegistered;

        protected abstract void onProximityResult(int i);

        private ProximityCheck() {
        }

        public void check() {
            Preconditions.checkState((this.mFinished || this.mRegistered) ? false : true);
            Sensor sensor = DozeTriggers.this.mSensorManager.getDefaultSensor(8);
            if (sensor != null) {
                DozeTriggers.this.mDozeSensors.setDisableSensorsInterferingWithProximity(true);
                this.mMaxRange = sensor.getMaximumRange();
                DozeTriggers.this.mSensorManager.registerListener(this, sensor, 3, 0, DozeTriggers.this.mHandler);
                DozeTriggers.this.mHandler.postDelayed(this, 500L);
                DozeTriggers.this.mWakeLock.acquire();
                this.mRegistered = true;
                return;
            }
            if (DozeMachine.DEBUG) {
                Log.d("DozeTriggers", "ProxCheck: No sensor found");
            }
            finishWithResult(0);
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent event) {
            if (event.values.length == 0) {
                if (DozeMachine.DEBUG) {
                    Log.d("DozeTriggers", "ProxCheck: Event has no values!");
                }
                finishWithResult(0);
                return;
            }
            if (DozeMachine.DEBUG) {
                Log.d("DozeTriggers", "ProxCheck: Event: value=" + event.values[0] + " max=" + this.mMaxRange);
            }
            boolean isNear = event.values[0] < this.mMaxRange;
            finishWithResult(isNear ? 1 : 2);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (DozeMachine.DEBUG) {
                Log.d("DozeTriggers", "ProxCheck: No event received before timeout");
            }
            finishWithResult(0);
        }

        private void finishWithResult(int result) {
            if (this.mFinished) {
                return;
            }
            boolean wasRegistered = this.mRegistered;
            if (this.mRegistered) {
                DozeTriggers.this.mHandler.removeCallbacks(this);
                DozeTriggers.this.mSensorManager.unregisterListener(this);
                DozeTriggers.this.mDozeSensors.setDisableSensorsInterferingWithProximity(false);
                this.mRegistered = false;
            }
            onProximityResult(result);
            if (wasRegistered) {
                DozeTriggers.this.mWakeLock.release();
            }
            this.mFinished = true;
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    private class TriggerReceiver extends BroadcastReceiver {
        private boolean mRegistered;

        private TriggerReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("com.android.systemui.doze.pulse".equals(intent.getAction())) {
                if (DozeMachine.DEBUG) {
                    Log.d("DozeTriggers", "Received pulse intent");
                }
                DozeTriggers.this.requestPulse(0, false);
            }
            if (UiModeManager.ACTION_ENTER_CAR_MODE.equals(intent.getAction())) {
                DozeTriggers.this.mMachine.requestState(DozeMachine.State.FINISH);
            }
            if ("android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                DozeTriggers.this.mDozeSensors.onUserSwitched();
            }
        }

        public void register(Context context) {
            if (this.mRegistered) {
                return;
            }
            IntentFilter filter = new IntentFilter("com.android.systemui.doze.pulse");
            filter.addAction(UiModeManager.ACTION_ENTER_CAR_MODE);
            filter.addAction("android.intent.action.USER_SWITCHED");
            context.registerReceiver(this, filter);
            this.mRegistered = true;
        }

        public void unregister(Context context) {
            if (!this.mRegistered) {
                return;
            }
            context.unregisterReceiver(this);
            this.mRegistered = false;
        }
    }
}
