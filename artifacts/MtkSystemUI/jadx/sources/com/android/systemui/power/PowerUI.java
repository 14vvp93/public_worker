package com.android.systemui.power;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.HardwarePropertiesManager;
import android.os.IBinder;
import android.os.IThermalEventListener;
import android.os.IThermalService;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Temperature;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.Dependency;
import com.android.systemui.SystemUI;
import com.android.systemui.statusbar.phone.StatusBar;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class PowerUI extends SystemUI {
    static final boolean DEBUG = Log.isLoggable("PowerUI", 3);
    private static final long SIX_HOURS_MILLIS = Duration.ofHours(6).toMillis();
    private EnhancedEstimates mEnhancedEstimates;
    private HardwarePropertiesManager mHardwarePropertiesManager;
    private int mLowBatteryAlertCloseLevel;
    private boolean mLowWarningShownThisChargeCycle;
    private long mNextLogTime;
    private int mNumTemps;
    private PowerManager mPowerManager;
    private boolean mSevereWarningShownThisChargeCycle;
    private IThermalService mThermalService;
    private float mThresholdTemp;
    private WarningsUI mWarnings;
    private final Handler mHandler = new Handler();

    @VisibleForTesting
    final Receiver mReceiver = new Receiver();
    private final Configuration mLastConfiguration = new Configuration();
    private long mTimeRemaining = Long.MAX_VALUE;
    private int mPlugType = 0;
    private int mInvalidCharger = 0;
    private final int[] mLowBatteryReminderLevels = new int[2];
    private long mScreenOffTime = -1;
    private float[] mRecentTemps = new float[125];

    @VisibleForTesting
    int mBatteryLevel = 100;

    @VisibleForTesting
    int mBatteryStatus = 1;
    private final Runnable mUpdateTempCallback = new Runnable() { // from class: com.android.systemui.power.-$$Lambda$Xy1vzUdmCVpH_1hTsQ2L8yUibUw
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.updateTemperatureWarning();
        }
    };

    public interface WarningsUI {
        void dismissHighTemperatureWarning();

        void dismissInvalidChargerWarning();

        void dismissLowBatteryWarning();

        void dump(PrintWriter printWriter);

        boolean isInvalidChargerWarningShowing();

        void showHighTemperatureWarning();

        void showInvalidChargerWarning();

        void showLowBatteryWarning(boolean z);

        void showThermalShutdownWarning();

        void update(int i, int i2, long j);

        void updateEstimate(Estimate estimate);

        void updateLowBatteryWarning();

        void updateThresholds(long j, long j2);

        void userSwitched();
    }

    @Override // com.android.systemui.SystemUI
    public void start() {
        this.mPowerManager = (PowerManager) this.mContext.getSystemService("power");
        this.mHardwarePropertiesManager = (HardwarePropertiesManager) this.mContext.getSystemService("hardware_properties");
        this.mScreenOffTime = this.mPowerManager.isScreenOn() ? -1L : SystemClock.elapsedRealtime();
        this.mWarnings = (WarningsUI) Dependency.get(WarningsUI.class);
        this.mEnhancedEstimates = (EnhancedEstimates) Dependency.get(EnhancedEstimates.class);
        this.mLastConfiguration.setTo(this.mContext.getResources().getConfiguration());
        ContentObserver obs = new ContentObserver(this.mHandler) { // from class: com.android.systemui.power.PowerUI.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                PowerUI.this.updateBatteryWarningLevels();
            }
        };
        ContentResolver resolver = this.mContext.getContentResolver();
        resolver.registerContentObserver(Settings.Global.getUriFor("low_power_trigger_level"), false, obs, -1);
        updateBatteryWarningLevels();
        this.mReceiver.init();
        showThermalShutdownDialog();
        initTemperatureWarning();
    }

    @Override // com.android.systemui.SystemUI
    protected void onConfigurationChanged(Configuration newConfig) {
        if ((this.mLastConfiguration.updateFrom(newConfig) & 3) != 0) {
            this.mHandler.post(new Runnable() { // from class: com.android.systemui.power.-$$Lambda$PowerUI$UShUnCLjyLd54pAqMZk3QJ-gMNs
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.initTemperatureWarning();
                }
            });
        }
    }

    void updateBatteryWarningLevels() {
        int critLevel = this.mContext.getResources().getInteger(R.integer.config_autoBrightnessLightSensorRate);
        int warnLevel = this.mContext.getResources().getInteger(R.integer.config_defaultBinderHeavyHitterWatcherBatchSize);
        if (warnLevel < critLevel) {
            warnLevel = critLevel;
        }
        this.mLowBatteryReminderLevels[0] = warnLevel;
        this.mLowBatteryReminderLevels[1] = critLevel;
        this.mLowBatteryAlertCloseLevel = this.mLowBatteryReminderLevels[0] + this.mContext.getResources().getInteger(R.integer.config_defaultBinderHeavyHitterAutoSamplerBatchSize);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int findBatteryLevelBucket(int level) {
        if (level >= this.mLowBatteryAlertCloseLevel) {
            return 1;
        }
        if (level > this.mLowBatteryReminderLevels[0]) {
            return 0;
        }
        int N = this.mLowBatteryReminderLevels.length;
        for (int i = N - 1; i >= 0; i--) {
            if (level <= this.mLowBatteryReminderLevels[i]) {
                return (-1) - i;
            }
        }
        throw new RuntimeException("not possible!");
    }

    @VisibleForTesting
    final class Receiver extends BroadcastReceiver {
        Receiver() {
        }

        public void init() {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            filter.addAction("android.intent.action.BATTERY_CHANGED");
            filter.addAction("android.intent.action.SCREEN_OFF");
            filter.addAction("android.intent.action.SCREEN_ON");
            filter.addAction("android.intent.action.USER_SWITCHED");
            PowerUI.this.mContext.registerReceiver(this, filter, null, PowerUI.this.mHandler);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.os.action.POWER_SAVE_MODE_CHANGED".equals(action)) {
                ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.power.-$$Lambda$PowerUI$Receiver$r1RcZjs8DVXWaC4Afqm8W0WAvm8
                    @Override // java.lang.Runnable
                    public final void run() {
                        PowerUI.Receiver.lambda$onReceive$0(this.f$0);
                    }
                });
                return;
            }
            if (!"android.intent.action.BATTERY_CHANGED".equals(action)) {
                if ("android.intent.action.SCREEN_OFF".equals(action)) {
                    PowerUI.this.mScreenOffTime = SystemClock.elapsedRealtime();
                    return;
                }
                if ("android.intent.action.SCREEN_ON".equals(action)) {
                    PowerUI.this.mScreenOffTime = -1L;
                    return;
                }
                if ("android.intent.action.USER_SWITCHED".equals(action)) {
                    PowerUI.this.mWarnings.userSwitched();
                    return;
                }
                Slog.w("PowerUI", "unknown intent: " + intent);
                return;
            }
            int oldBatteryLevel = PowerUI.this.mBatteryLevel;
            PowerUI.this.mBatteryLevel = intent.getIntExtra("level", 100);
            int oldBatteryStatus = PowerUI.this.mBatteryStatus;
            PowerUI.this.mBatteryStatus = intent.getIntExtra("status", 1);
            int oldPlugType = PowerUI.this.mPlugType;
            PowerUI.this.mPlugType = intent.getIntExtra("plugged", 1);
            int oldInvalidCharger = PowerUI.this.mInvalidCharger;
            PowerUI.this.mInvalidCharger = intent.getIntExtra("invalid_charger", 0);
            final boolean plugged = PowerUI.this.mPlugType != 0;
            final boolean oldPlugged = oldPlugType != 0;
            final int oldBucket = PowerUI.this.findBatteryLevelBucket(oldBatteryLevel);
            final int bucket = PowerUI.this.findBatteryLevelBucket(PowerUI.this.mBatteryLevel);
            if (PowerUI.DEBUG) {
                Slog.d("PowerUI", "buckets   ....." + PowerUI.this.mLowBatteryAlertCloseLevel + " .. " + PowerUI.this.mLowBatteryReminderLevels[0] + " .. " + PowerUI.this.mLowBatteryReminderLevels[1]);
                StringBuilder sb = new StringBuilder();
                sb.append("level          ");
                sb.append(oldBatteryLevel);
                sb.append(" --> ");
                sb.append(PowerUI.this.mBatteryLevel);
                Slog.d("PowerUI", sb.toString());
                Slog.d("PowerUI", "status         " + oldBatteryStatus + " --> " + PowerUI.this.mBatteryStatus);
                Slog.d("PowerUI", "plugType       " + oldPlugType + " --> " + PowerUI.this.mPlugType);
                Slog.d("PowerUI", "invalidCharger " + oldInvalidCharger + " --> " + PowerUI.this.mInvalidCharger);
                Slog.d("PowerUI", "bucket         " + oldBucket + " --> " + bucket);
                Slog.d("PowerUI", "plugged        " + oldPlugged + " --> " + plugged);
            }
            PowerUI.this.mWarnings.update(PowerUI.this.mBatteryLevel, bucket, PowerUI.this.mScreenOffTime);
            if (oldInvalidCharger == 0 && PowerUI.this.mInvalidCharger != 0) {
                Slog.d("PowerUI", "showing invalid charger warning");
                PowerUI.this.mWarnings.showInvalidChargerWarning();
                return;
            }
            if (oldInvalidCharger == 0 || PowerUI.this.mInvalidCharger != 0) {
                if (PowerUI.this.mWarnings.isInvalidChargerWarningShowing()) {
                    return;
                }
            } else {
                PowerUI.this.mWarnings.dismissInvalidChargerWarning();
            }
            ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.power.-$$Lambda$PowerUI$Receiver$XpoeE7Ggw3F6pRcjNXG1tX721LM
                @Override // java.lang.Runnable
                public final void run() {
                    PowerUI.this.maybeShowBatteryWarning(plugged, oldPlugged, oldBucket, bucket);
                }
            });
        }

        public static /* synthetic */ void lambda$onReceive$0(Receiver receiver) {
            if (PowerUI.this.mPowerManager.isPowerSaveMode()) {
                PowerUI.this.mWarnings.dismissLowBatteryWarning();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void maybeShowBatteryWarning(boolean plugged, boolean oldPlugged, int oldBucket, int bucket) {
        Estimate estimate;
        boolean isPowerSaver = this.mPowerManager.isPowerSaveMode();
        boolean playSound = bucket != oldBucket || oldPlugged;
        boolean hybridEnabled = this.mEnhancedEstimates.isHybridNotificationEnabled();
        if (hybridEnabled && (estimate = this.mEnhancedEstimates.getEstimate()) != null) {
            this.mTimeRemaining = estimate.estimateMillis;
            this.mWarnings.updateEstimate(estimate);
            this.mWarnings.updateThresholds(this.mEnhancedEstimates.getLowWarningThreshold(), this.mEnhancedEstimates.getSevereWarningThreshold());
            if (this.mBatteryLevel >= 45 && this.mTimeRemaining > SIX_HOURS_MILLIS) {
                this.mLowWarningShownThisChargeCycle = false;
                this.mSevereWarningShownThisChargeCycle = false;
            }
        }
        if (shouldShowLowBatteryWarning(plugged, oldPlugged, oldBucket, bucket, this.mTimeRemaining, isPowerSaver, this.mBatteryStatus)) {
            this.mWarnings.showLowBatteryWarning(playSound);
            if (hybridEnabled) {
                if (this.mTimeRemaining < this.mEnhancedEstimates.getSevereWarningThreshold() || this.mBatteryLevel < this.mLowBatteryReminderLevels[1]) {
                    this.mSevereWarningShownThisChargeCycle = true;
                    return;
                } else {
                    this.mLowWarningShownThisChargeCycle = true;
                    return;
                }
            }
            return;
        }
        if (shouldDismissLowBatteryWarning(plugged, oldBucket, bucket, this.mTimeRemaining, isPowerSaver)) {
            this.mWarnings.dismissLowBatteryWarning();
        } else {
            this.mWarnings.updateLowBatteryWarning();
        }
    }

    @VisibleForTesting
    boolean shouldShowLowBatteryWarning(boolean plugged, boolean oldPlugged, int oldBucket, int bucket, long timeRemaining, boolean isPowerSaver, int batteryStatus) {
        if (this.mEnhancedEstimates.isHybridNotificationEnabled()) {
            return isEnhancedTrigger(plugged, timeRemaining, isPowerSaver, batteryStatus);
        }
        return (plugged || isPowerSaver || (bucket >= oldBucket && !oldPlugged) || bucket >= 0 || batteryStatus == 1) ? false : true;
    }

    @VisibleForTesting
    boolean shouldDismissLowBatteryWarning(boolean plugged, int oldBucket, int bucket, long timeRemaining, boolean isPowerSaver) {
        boolean hybridWouldDismiss = this.mEnhancedEstimates.isHybridNotificationEnabled() && timeRemaining > this.mEnhancedEstimates.getLowWarningThreshold();
        boolean standardWouldDismiss = bucket > oldBucket && bucket > 0;
        if (!isPowerSaver && !plugged) {
            if (!standardWouldDismiss) {
                return false;
            }
            if (this.mEnhancedEstimates.isHybridNotificationEnabled() && !hybridWouldDismiss) {
                return false;
            }
        }
        return true;
    }

    private boolean isEnhancedTrigger(boolean plugged, long timeRemaining, boolean isPowerSaver, int batteryStatus) {
        if (plugged || isPowerSaver || batteryStatus == 1) {
            return false;
        }
        int warnLevel = this.mLowBatteryReminderLevels[0];
        int critLevel = this.mLowBatteryReminderLevels[1];
        boolean canShowWarning = !this.mLowWarningShownThisChargeCycle && (timeRemaining < this.mEnhancedEstimates.getLowWarningThreshold() || this.mBatteryLevel <= warnLevel);
        boolean canShowSevereWarning = !this.mSevereWarningShownThisChargeCycle && (timeRemaining < this.mEnhancedEstimates.getSevereWarningThreshold() || this.mBatteryLevel <= critLevel);
        return canShowWarning || canShowSevereWarning;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTemperatureWarning() {
        ContentResolver resolver = this.mContext.getContentResolver();
        Resources resources = this.mContext.getResources();
        if (Settings.Global.getInt(resolver, "show_temperature_warning", resources.getInteger(com.android.systemui.R.integer.config_showTemperatureWarning)) == 0) {
            return;
        }
        this.mThresholdTemp = Settings.Global.getFloat(resolver, "warning_temperature", resources.getInteger(com.android.systemui.R.integer.config_warningTemperature));
        if (this.mThresholdTemp < 0.0f) {
            float[] throttlingTemps = this.mHardwarePropertiesManager.getDeviceTemperatures(3, 2);
            if (throttlingTemps == null || throttlingTemps.length == 0 || throttlingTemps[0] == -3.4028235E38f) {
                return;
            } else {
                this.mThresholdTemp = throttlingTemps[0] - resources.getInteger(com.android.systemui.R.integer.config_warningTemperatureTolerance);
            }
        }
        if (this.mThermalService == null) {
            IBinder b = ServiceManager.getService("thermalservice");
            if (b != null) {
                this.mThermalService = IThermalService.Stub.asInterface(b);
                try {
                    this.mThermalService.registerThermalEventListener(new ThermalEventListener());
                } catch (RemoteException e) {
                }
            } else {
                Slog.w("PowerUI", "cannot find thermalservice, no throttling push notifications");
            }
        }
        setNextLogTime();
        this.mHandler.removeCallbacks(this.mUpdateTempCallback);
        updateTemperatureWarning();
    }

    private void showThermalShutdownDialog() {
        if (this.mPowerManager.getLastShutdownReason() == 4) {
            this.mWarnings.showThermalShutdownWarning();
        }
    }

    @VisibleForTesting
    protected void updateTemperatureWarning() {
        float[] temps = this.mHardwarePropertiesManager.getDeviceTemperatures(3, 0);
        if (temps.length != 0) {
            float temp = temps[0];
            float[] fArr = this.mRecentTemps;
            int i = this.mNumTemps;
            this.mNumTemps = i + 1;
            fArr[i] = temp;
            StatusBar statusBar = (StatusBar) getComponent(StatusBar.class);
            if (statusBar != null && !statusBar.isDeviceInVrMode() && temp >= this.mThresholdTemp) {
                logAtTemperatureThreshold(temp);
                this.mWarnings.showHighTemperatureWarning();
            } else {
                this.mWarnings.dismissHighTemperatureWarning();
            }
        }
        logTemperatureStats();
        this.mHandler.postDelayed(this.mUpdateTempCallback, 30000L);
    }

    private void logAtTemperatureThreshold(float temp) {
        StringBuilder sb = new StringBuilder();
        sb.append("currentTemp=");
        sb.append(temp);
        sb.append(",thresholdTemp=");
        sb.append(this.mThresholdTemp);
        sb.append(",batteryStatus=");
        sb.append(this.mBatteryStatus);
        sb.append(",recentTemps=");
        for (int i = 0; i < this.mNumTemps; i++) {
            sb.append(this.mRecentTemps[i]);
            sb.append(',');
        }
        Slog.i("PowerUI", sb.toString());
    }

    private void logTemperatureStats() {
        if (this.mNextLogTime > System.currentTimeMillis() && this.mNumTemps != 125) {
            return;
        }
        if (this.mNumTemps > 0) {
            float sum = this.mRecentTemps[0];
            float min = this.mRecentTemps[0];
            float max = this.mRecentTemps[0];
            for (int i = 1; i < this.mNumTemps; i++) {
                float temp = this.mRecentTemps[i];
                sum += temp;
                if (temp > max) {
                    max = temp;
                }
                if (temp < min) {
                    min = temp;
                }
            }
            int i2 = this.mNumTemps;
            float avg = sum / i2;
            Slog.i("PowerUI", "avg=" + avg + ",min=" + min + ",max=" + max);
            MetricsLogger.histogram(this.mContext, "device_skin_temp_avg", (int) avg);
            MetricsLogger.histogram(this.mContext, "device_skin_temp_min", (int) min);
            MetricsLogger.histogram(this.mContext, "device_skin_temp_max", (int) max);
        }
        setNextLogTime();
        this.mNumTemps = 0;
    }

    private void setNextLogTime() {
        this.mNextLogTime = System.currentTimeMillis() + 3600000;
    }

    @Override // com.android.systemui.SystemUI
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.print("mLowBatteryAlertCloseLevel=");
        pw.println(this.mLowBatteryAlertCloseLevel);
        pw.print("mLowBatteryReminderLevels=");
        pw.println(Arrays.toString(this.mLowBatteryReminderLevels));
        pw.print("mBatteryLevel=");
        pw.println(Integer.toString(this.mBatteryLevel));
        pw.print("mBatteryStatus=");
        pw.println(Integer.toString(this.mBatteryStatus));
        pw.print("mPlugType=");
        pw.println(Integer.toString(this.mPlugType));
        pw.print("mInvalidCharger=");
        pw.println(Integer.toString(this.mInvalidCharger));
        pw.print("mScreenOffTime=");
        pw.print(this.mScreenOffTime);
        if (this.mScreenOffTime >= 0) {
            pw.print(" (");
            pw.print(SystemClock.elapsedRealtime() - this.mScreenOffTime);
            pw.print(" ago)");
        }
        pw.println();
        pw.print("soundTimeout=");
        pw.println(Settings.Global.getInt(this.mContext.getContentResolver(), "low_battery_sound_timeout", 0));
        pw.print("bucket: ");
        pw.println(Integer.toString(findBatteryLevelBucket(this.mBatteryLevel)));
        pw.print("mThresholdTemp=");
        pw.println(Float.toString(this.mThresholdTemp));
        pw.print("mNextLogTime=");
        pw.println(Long.toString(this.mNextLogTime));
        this.mWarnings.dump(pw);
    }

    private final class ThermalEventListener extends IThermalEventListener.Stub {
        private ThermalEventListener() {
        }

        public void notifyThrottling(boolean isThrottling, Temperature temp) {
            PowerUI.this.mHandler.removeCallbacks(PowerUI.this.mUpdateTempCallback);
            PowerUI.this.updateTemperatureWarning();
        }
    }
}
