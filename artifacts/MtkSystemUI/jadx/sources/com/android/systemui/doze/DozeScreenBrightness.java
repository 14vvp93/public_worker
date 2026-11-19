package com.android.systemui.doze;

import android.R;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Trace;
import com.android.internal.annotations.VisibleForTesting;

/* JADX INFO: loaded from: classes.dex */
public class DozeScreenBrightness implements SensorEventListener, DozeMachine.Part {
    private final Context mContext;
    private int mDefaultDozeBrightness;
    private final DozeHost mDozeHost;
    private final DozeMachine.Service mDozeService;
    private final Handler mHandler;
    private int mLastSensorValue;
    private final Sensor mLightSensor;
    private boolean mPaused;
    private boolean mRegistered;
    private final SensorManager mSensorManager;
    private final int[] mSensorToBrightness;
    private final int[] mSensorToScrimOpacity;

    public DozeScreenBrightness(Context context, DozeMachine.Service service, SensorManager sensorManager, Sensor lightSensor, DozeHost host, Handler handler, int defaultDozeBrightness, int[] sensorToBrightness, int[] sensorToScrimOpacity) {
        this.mPaused = false;
        this.mLastSensorValue = -1;
        this.mContext = context;
        this.mDozeService = service;
        this.mSensorManager = sensorManager;
        this.mLightSensor = lightSensor;
        this.mDozeHost = host;
        this.mHandler = handler;
        this.mDefaultDozeBrightness = defaultDozeBrightness;
        this.mSensorToBrightness = sensorToBrightness;
        this.mSensorToScrimOpacity = sensorToScrimOpacity;
    }

    @VisibleForTesting
    public DozeScreenBrightness(Context context, DozeMachine.Service service, SensorManager sensorManager, Sensor lightSensor, DozeHost host, Handler handler, AlwaysOnDisplayPolicy policy) {
        this(context, service, sensorManager, lightSensor, host, handler, context.getResources().getInteger(R.integer.config_doubleTapPowerGestureMode), policy.screenBrightnessArray, policy.dimmingScrimArray);
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State oldState, DozeMachine.State newState) {
        switch (newState) {
            case INITIALIZED:
                resetBrightnessToDefault();
                break;
            case DOZE_AOD:
            case DOZE_REQUEST_PULSE:
                setLightSensorEnabled(true);
                break;
            case DOZE:
                setLightSensorEnabled(false);
                resetBrightnessToDefault();
                break;
            case FINISH:
                setLightSensorEnabled(false);
                break;
        }
        if (newState != DozeMachine.State.FINISH) {
            setPaused(newState == DozeMachine.State.DOZE_AOD_PAUSED);
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent event) {
        Trace.beginSection("DozeScreenBrightness.onSensorChanged" + event.values[0]);
        try {
            if (this.mRegistered) {
                this.mLastSensorValue = (int) event.values[0];
                updateBrightnessAndReady();
            }
        } finally {
            Trace.endSection();
        }
    }

    private void updateBrightnessAndReady() {
        if (this.mRegistered) {
            int brightness = computeBrightness(this.mLastSensorValue);
            boolean brightnessReady = brightness > 0;
            if (brightnessReady) {
                this.mDozeService.setDozeScreenBrightness(brightness);
            }
            int scrimOpacity = -1;
            if (this.mPaused) {
                scrimOpacity = 255;
            } else if (brightnessReady) {
                scrimOpacity = computeScrimOpacity(this.mLastSensorValue);
            }
            if (scrimOpacity >= 0) {
                this.mDozeHost.setAodDimmingScrim(scrimOpacity / 255.0f);
            }
        }
    }

    private int computeScrimOpacity(int sensorValue) {
        if (sensorValue < 0 || sensorValue >= this.mSensorToScrimOpacity.length) {
            return -1;
        }
        return this.mSensorToScrimOpacity[sensorValue];
    }

    private int computeBrightness(int sensorValue) {
        if (sensorValue < 0 || sensorValue >= this.mSensorToBrightness.length) {
            return -1;
        }
        return this.mSensorToBrightness[sensorValue];
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void resetBrightnessToDefault() {
        this.mDozeService.setDozeScreenBrightness(this.mDefaultDozeBrightness);
        this.mDozeHost.setAodDimmingScrim(0.0f);
    }

    private void setLightSensorEnabled(boolean enabled) {
        if (enabled && !this.mRegistered && this.mLightSensor != null) {
            this.mRegistered = this.mSensorManager.registerListener(this, this.mLightSensor, 3, this.mHandler);
            this.mLastSensorValue = -1;
        } else if (!enabled && this.mRegistered) {
            this.mSensorManager.unregisterListener(this);
            this.mRegistered = false;
            this.mLastSensorValue = -1;
        }
    }

    private void setPaused(boolean paused) {
        if (this.mPaused != paused) {
            this.mPaused = paused;
            updateBrightnessAndReady();
        }
    }
}
