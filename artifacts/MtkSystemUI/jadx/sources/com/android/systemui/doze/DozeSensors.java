package com.android.systemui.doze;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.hardware.AmbientDisplayConfiguration;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.AlarmTimeout;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class DozeSensors {
    private static final boolean DEBUG = DozeService.DEBUG;
    private final AlarmManager mAlarmManager;
    private final Callback mCallback;
    private final AmbientDisplayConfiguration mConfig;
    private final Context mContext;
    private final DozeParameters mDozeParameters;
    private final TriggerSensor mPickupSensor;
    private final Consumer<Boolean> mProxCallback;
    private final ProxSensor mProxSensor;
    private final ContentResolver mResolver;
    private final SensorManager mSensorManager;
    private final TriggerSensor[] mSensors;
    private final WakeLock mWakeLock;
    private final Handler mHandler = new Handler();
    private final ContentObserver mSettingsObserver = new ContentObserver(this.mHandler) { // from class: com.android.systemui.doze.DozeSensors.1
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri, int userId) {
            if (userId == ActivityManager.getCurrentUser()) {
                for (TriggerSensor s : DozeSensors.this.mSensors) {
                    s.updateListener();
                }
            }
        }
    };

    public interface Callback {
        void onSensorPulse(int i, boolean z, float f, float f2);
    }

    public DozeSensors(Context context, AlarmManager alarmManager, SensorManager sensorManager, DozeParameters dozeParameters, AmbientDisplayConfiguration config, WakeLock wakeLock, Callback callback, Consumer<Boolean> proxCallback, AlwaysOnDisplayPolicy policy) {
        this.mContext = context;
        this.mAlarmManager = alarmManager;
        this.mSensorManager = sensorManager;
        this.mDozeParameters = dozeParameters;
        this.mConfig = config;
        this.mWakeLock = wakeLock;
        this.mProxCallback = proxCallback;
        this.mResolver = this.mContext.getContentResolver();
        TriggerSensor triggerSensor = new TriggerSensor(this, this.mSensorManager.getDefaultSensor(25), "doze_pulse_on_pick_up", config.pulseOnPickupAvailable(), 3, false, false);
        this.mPickupSensor = triggerSensor;
        this.mSensors = new TriggerSensor[]{new TriggerSensor(this, this.mSensorManager.getDefaultSensor(17), null, dozeParameters.getPulseOnSigMotion(), 2, false, false), triggerSensor, new TriggerSensor(this, findSensorWithType(config.doubleTapSensorType()), "doze_pulse_on_double_tap", true, 4, dozeParameters.doubleTapReportsTouchCoordinates(), true), new TriggerSensor(findSensorWithType(config.longPressSensorType()), "doze_pulse_on_long_press", false, true, 5, true, true)};
        this.mProxSensor = new ProxSensor(policy);
        this.mCallback = callback;
    }

    private Sensor findSensorWithType(String type) {
        return findSensorWithType(this.mSensorManager, type);
    }

    static Sensor findSensorWithType(SensorManager sensorManager, String type) {
        if (TextUtils.isEmpty(type)) {
            return null;
        }
        List<Sensor> sensorList = sensorManager.getSensorList(-1);
        for (Sensor s : sensorList) {
            if (type.equals(s.getStringType())) {
                return s;
            }
        }
        return null;
    }

    public void setListening(boolean listen) {
        for (TriggerSensor s : this.mSensors) {
            s.setListening(listen);
            if (listen) {
                s.registerSettingsObserver(this.mSettingsObserver);
            }
        }
        if (!listen) {
            this.mResolver.unregisterContentObserver(this.mSettingsObserver);
        }
    }

    public void setTouchscreenSensorsListening(boolean listening) {
        for (TriggerSensor sensor : this.mSensors) {
            if (sensor.mRequiresTouchscreen) {
                sensor.setListening(listening);
            }
        }
    }

    public void reregisterAllSensors() {
        for (TriggerSensor s : this.mSensors) {
            s.setListening(false);
        }
        for (TriggerSensor s2 : this.mSensors) {
            s2.setListening(true);
        }
    }

    public void onUserSwitched() {
        for (TriggerSensor s : this.mSensors) {
            s.updateListener();
        }
    }

    public void setProxListening(boolean listen) {
        this.mProxSensor.setRequested(listen);
    }

    public void setDisableSensorsInterferingWithProximity(boolean disable) {
        this.mPickupSensor.setDisabled(disable);
    }

    public void dump(PrintWriter pw) {
        for (TriggerSensor s : this.mSensors) {
            pw.print("Sensor: ");
            pw.println(s.toString());
        }
        pw.print("ProxSensor: ");
        pw.println(this.mProxSensor.toString());
    }

    public Boolean isProximityCurrentlyFar() {
        return this.mProxSensor.mCurrentlyFar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ProxSensor implements SensorEventListener {
        final AlarmTimeout mCooldownTimer;
        Boolean mCurrentlyFar;
        long mLastNear;
        final AlwaysOnDisplayPolicy mPolicy;
        boolean mRegistered;
        boolean mRequested;

        public ProxSensor(AlwaysOnDisplayPolicy policy) {
            this.mPolicy = policy;
            this.mCooldownTimer = new AlarmTimeout(DozeSensors.this.mAlarmManager, new AlarmManager.OnAlarmListener() { // from class: com.android.systemui.doze.-$$Lambda$DozeSensors$ProxSensor$1rrJyrK-R8bANwbetqs61eKIcvs
                @Override // android.app.AlarmManager.OnAlarmListener
                public final void onAlarm() {
                    this.f$0.updateRegistered();
                }
            }, "prox_cooldown", DozeSensors.this.mHandler);
        }

        void setRequested(boolean requested) {
            if (this.mRequested == requested) {
                DozeSensors.this.mHandler.post(new Runnable() { // from class: com.android.systemui.doze.-$$Lambda$DozeSensors$ProxSensor$ocSoA7n0sI8mkM1nacSopw2_2Oc
                    @Override // java.lang.Runnable
                    public final void run() {
                        DozeSensors.ProxSensor.lambda$setRequested$0(this.f$0);
                    }
                });
            } else {
                this.mRequested = requested;
                updateRegistered();
            }
        }

        public static /* synthetic */ void lambda$setRequested$0(ProxSensor proxSensor) {
            if (proxSensor.mCurrentlyFar != null) {
                DozeSensors.this.mProxCallback.accept(proxSensor.mCurrentlyFar);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateRegistered() {
            setRegistered(this.mRequested && !this.mCooldownTimer.isScheduled());
        }

        private void setRegistered(boolean register) {
            if (this.mRegistered == register) {
                return;
            }
            if (!register) {
                DozeSensors.this.mSensorManager.unregisterListener(this);
                this.mRegistered = false;
                this.mCurrentlyFar = null;
                return;
            }
            this.mRegistered = DozeSensors.this.mSensorManager.registerListener(this, DozeSensors.this.mSensorManager.getDefaultSensor(8), 3, DozeSensors.this.mHandler);
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent event) {
            if (DozeSensors.DEBUG) {
                Log.d("DozeSensors", "onSensorChanged " + event);
            }
            this.mCurrentlyFar = Boolean.valueOf(event.values[0] >= event.sensor.getMaximumRange());
            DozeSensors.this.mProxCallback.accept(this.mCurrentlyFar);
            long now = SystemClock.elapsedRealtime();
            if (this.mCurrentlyFar != null) {
                if (!this.mCurrentlyFar.booleanValue()) {
                    this.mLastNear = now;
                } else if (this.mCurrentlyFar.booleanValue() && now - this.mLastNear < this.mPolicy.proxCooldownTriggerMs) {
                    this.mCooldownTimer.schedule(this.mPolicy.proxCooldownPeriodMs, 1);
                    updateRegistered();
                }
            }
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public String toString() {
            return String.format("{registered=%s, requested=%s, coolingDown=%s, currentlyFar=%s}", Boolean.valueOf(this.mRegistered), Boolean.valueOf(this.mRequested), Boolean.valueOf(this.mCooldownTimer.isScheduled()), this.mCurrentlyFar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class TriggerSensor extends TriggerEventListener {
        final boolean mConfigured;
        private boolean mDisabled;
        final int mPulseReason;
        private boolean mRegistered;
        final boolean mReportsTouchCoordinates;
        private boolean mRequested;
        final boolean mRequiresTouchscreen;
        final Sensor mSensor;
        final String mSetting;
        final boolean mSettingDefault;

        public TriggerSensor(DozeSensors dozeSensors, Sensor sensor, String setting, boolean configured, int pulseReason, boolean reportsTouchCoordinates, boolean requiresTouchscreen) {
            this(sensor, setting, true, configured, pulseReason, reportsTouchCoordinates, requiresTouchscreen);
        }

        public TriggerSensor(Sensor sensor, String setting, boolean settingDef, boolean configured, int pulseReason, boolean reportsTouchCoordinates, boolean requiresTouchscreen) {
            this.mSensor = sensor;
            this.mSetting = setting;
            this.mSettingDefault = settingDef;
            this.mConfigured = configured;
            this.mPulseReason = pulseReason;
            this.mReportsTouchCoordinates = reportsTouchCoordinates;
            this.mRequiresTouchscreen = requiresTouchscreen;
        }

        public void setListening(boolean listen) {
            if (this.mRequested == listen) {
                return;
            }
            this.mRequested = listen;
            updateListener();
        }

        public void setDisabled(boolean disabled) {
            if (this.mDisabled == disabled) {
                return;
            }
            this.mDisabled = disabled;
            updateListener();
        }

        public void updateListener() {
            if (!this.mConfigured || this.mSensor == null) {
                return;
            }
            if (this.mRequested && !this.mDisabled && enabledBySetting() && !this.mRegistered) {
                this.mRegistered = DozeSensors.this.mSensorManager.requestTriggerSensor(this, this.mSensor);
                if (DozeSensors.DEBUG) {
                    Log.d("DozeSensors", "requestTriggerSensor " + this.mRegistered);
                    return;
                }
                return;
            }
            if (this.mRegistered) {
                boolean rt = DozeSensors.this.mSensorManager.cancelTriggerSensor(this, this.mSensor);
                if (DozeSensors.DEBUG) {
                    Log.d("DozeSensors", "cancelTriggerSensor " + rt);
                }
                this.mRegistered = false;
            }
        }

        private boolean enabledBySetting() {
            return TextUtils.isEmpty(this.mSetting) || Settings.Secure.getIntForUser(DozeSensors.this.mResolver, this.mSetting, this.mSettingDefault ? 1 : 0, -2) != 0;
        }

        public String toString() {
            return "{mRegistered=" + this.mRegistered + ", mRequested=" + this.mRequested + ", mDisabled=" + this.mDisabled + ", mConfigured=" + this.mConfigured + ", mSensor=" + this.mSensor + "}";
        }

        @Override // android.hardware.TriggerEventListener
        public void onTrigger(final TriggerEvent event) {
            DozeLog.traceSensor(DozeSensors.this.mContext, this.mPulseReason);
            DozeSensors.this.mHandler.post(DozeSensors.this.mWakeLock.wrap(new Runnable() { // from class: com.android.systemui.doze.-$$Lambda$DozeSensors$TriggerSensor$O2XJN2HKJ96bSF_1qNx6jPK-eFk
                @Override // java.lang.Runnable
                public final void run() {
                    DozeSensors.TriggerSensor.lambda$onTrigger$0(this.f$0, event);
                }
            }));
        }

        public static /* synthetic */ void lambda$onTrigger$0(TriggerSensor triggerSensor, TriggerEvent event) {
            if (DozeSensors.DEBUG) {
                Log.d("DozeSensors", "onTrigger: " + triggerSensor.triggerEventToString(event));
            }
            boolean sensorPerformsProxCheck = false;
            if (triggerSensor.mSensor.getType() == 25) {
                int subType = (int) event.values[0];
                MetricsLogger.action(DozeSensors.this.mContext, 411, subType);
                sensorPerformsProxCheck = DozeSensors.this.mDozeParameters.getPickupSubtypePerformsProxCheck(subType);
            }
            triggerSensor.mRegistered = false;
            float screenX = -1.0f;
            float screenY = -1.0f;
            if (triggerSensor.mReportsTouchCoordinates && event.values.length >= 2) {
                screenX = event.values[0];
                screenY = event.values[1];
            }
            DozeSensors.this.mCallback.onSensorPulse(triggerSensor.mPulseReason, sensorPerformsProxCheck, screenX, screenY);
            triggerSensor.updateListener();
        }

        public void registerSettingsObserver(ContentObserver settingsObserver) {
            if (this.mConfigured && !TextUtils.isEmpty(this.mSetting)) {
                DozeSensors.this.mResolver.registerContentObserver(Settings.Secure.getUriFor(this.mSetting), false, DozeSensors.this.mSettingsObserver, -1);
            }
        }

        private String triggerEventToString(TriggerEvent event) {
            if (event == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder("TriggerEvent[");
            sb.append(event.timestamp);
            sb.append(',');
            StringBuilder sb2 = sb.append(event.sensor.getName());
            if (event.values != null) {
                for (int i = 0; i < event.values.length; i++) {
                    sb2.append(',');
                    sb2.append(event.values[i]);
                }
            }
            sb2.append(']');
            return sb2.toString();
        }
    }
}
