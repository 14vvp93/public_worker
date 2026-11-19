package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerSaveState;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class BatteryControllerImpl extends BroadcastReceiver implements BatteryController {
    private static final boolean DEBUG = Log.isLoggable("BatteryController", 3);
    protected boolean mAodPowerSave;
    private final ArrayList<BatteryController.BatteryStateChangeCallback> mChangeCallbacks;
    protected boolean mCharged;
    protected boolean mCharging;
    private final Context mContext;
    private boolean mDemoMode;
    private final Handler mHandler;
    private boolean mHasReceivedBattery;
    protected int mLevel;
    protected boolean mPluggedIn;
    private final PowerManager mPowerManager;
    protected boolean mPowerSave;
    private boolean mTestmode;

    public BatteryControllerImpl(Context context) {
        this(context, (PowerManager) context.getSystemService(PowerManager.class));
    }

    @VisibleForTesting
    BatteryControllerImpl(Context context, PowerManager powerManager) {
        this.mChangeCallbacks = new ArrayList<>();
        this.mTestmode = false;
        this.mHasReceivedBattery = false;
        this.mContext = context;
        this.mHandler = new Handler();
        this.mPowerManager = powerManager;
        registerReceiver();
        updatePowerSave();
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.BATTERY_CHANGED");
        filter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        filter.addAction("android.os.action.POWER_SAVE_MODE_CHANGING");
        filter.addAction("com.android.systemui.BATTERY_LEVEL_TEST");
        this.mContext.registerReceiver(this, filter);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("BatteryController state:");
        pw.print("  mLevel=");
        pw.println(this.mLevel);
        pw.print("  mPluggedIn=");
        pw.println(this.mPluggedIn);
        pw.print("  mCharging=");
        pw.println(this.mCharging);
        pw.print("  mCharged=");
        pw.println(this.mCharged);
        pw.print("  mPowerSave=");
        pw.println(this.mPowerSave);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(BatteryController.BatteryStateChangeCallback cb) {
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.add(cb);
        }
        if (this.mHasReceivedBattery) {
            cb.onBatteryLevelChanged(this.mLevel, this.mPluggedIn, this.mCharging);
            cb.onPowerSaveChanged(this.mPowerSave);
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(BatteryController.BatteryStateChangeCallback cb) {
        synchronized (this.mChangeCallbacks) {
            this.mChangeCallbacks.remove(cb);
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        boolean z = true;
        if (!action.equals("android.intent.action.BATTERY_CHANGED")) {
            if (action.equals("android.os.action.POWER_SAVE_MODE_CHANGED")) {
                updatePowerSave();
                return;
            }
            if (action.equals("android.os.action.POWER_SAVE_MODE_CHANGING")) {
                setPowerSave(intent.getBooleanExtra("mode", false));
                return;
            } else {
                if (action.equals("com.android.systemui.BATTERY_LEVEL_TEST")) {
                    this.mTestmode = true;
                    this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.BatteryControllerImpl.1
                        int saveLevel;
                        boolean savePlugged;
                        int curLevel = 0;
                        int incr = 1;
                        Intent dummy = new Intent("android.intent.action.BATTERY_CHANGED");

                        {
                            this.saveLevel = BatteryControllerImpl.this.mLevel;
                            this.savePlugged = BatteryControllerImpl.this.mPluggedIn;
                        }

                        @Override // java.lang.Runnable
                        public void run() {
                            if (this.curLevel < 0) {
                                BatteryControllerImpl.this.mTestmode = false;
                                this.dummy.putExtra("level", this.saveLevel);
                                this.dummy.putExtra("plugged", this.savePlugged);
                                this.dummy.putExtra("testmode", false);
                            } else {
                                this.dummy.putExtra("level", this.curLevel);
                                this.dummy.putExtra("plugged", this.incr > 0 ? 1 : 0);
                                this.dummy.putExtra("testmode", true);
                            }
                            context.sendBroadcast(this.dummy);
                            if (BatteryControllerImpl.this.mTestmode) {
                                this.curLevel += this.incr;
                                if (this.curLevel == 100) {
                                    this.incr *= -1;
                                }
                                BatteryControllerImpl.this.mHandler.postDelayed(this, 200L);
                            }
                        }
                    });
                    return;
                }
                return;
            }
        }
        if (!this.mTestmode || intent.getBooleanExtra("testmode", false)) {
            this.mHasReceivedBattery = true;
            this.mLevel = (int) ((100.0f * intent.getIntExtra("level", 0)) / intent.getIntExtra("scale", 100));
            this.mPluggedIn = intent.getIntExtra("plugged", 0) != 0;
            int status = intent.getIntExtra("status", 1);
            this.mCharged = status == 5;
            if (!this.mCharged && status != 2) {
                z = false;
            }
            this.mCharging = z;
            fireBatteryLevelChanged();
        }
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController
    public boolean isPowerSave() {
        return this.mPowerSave;
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController
    public boolean isAodPowerSave() {
        return this.mAodPowerSave;
    }

    private void updatePowerSave() {
        setPowerSave(this.mPowerManager.isPowerSaveMode());
    }

    private void setPowerSave(boolean powerSave) {
        if (powerSave == this.mPowerSave) {
            return;
        }
        this.mPowerSave = powerSave;
        PowerSaveState state = this.mPowerManager.getPowerSaveState(14);
        this.mAodPowerSave = state.batterySaverEnabled;
        if (DEBUG) {
            StringBuilder sb = new StringBuilder();
            sb.append("Power save is ");
            sb.append(this.mPowerSave ? "on" : "off");
            Log.d("BatteryController", sb.toString());
        }
        firePowerSaveChanged();
    }

    protected void fireBatteryLevelChanged() {
        synchronized (this.mChangeCallbacks) {
            int N = this.mChangeCallbacks.size();
            for (int i = 0; i < N; i++) {
                this.mChangeCallbacks.get(i).onBatteryLevelChanged(this.mLevel, this.mPluggedIn, this.mCharging);
            }
        }
    }

    private void firePowerSaveChanged() {
        synchronized (this.mChangeCallbacks) {
            int N = this.mChangeCallbacks.size();
            for (int i = 0; i < N; i++) {
                this.mChangeCallbacks.get(i).onPowerSaveChanged(this.mPowerSave);
            }
        }
    }

    @Override // com.android.systemui.DemoMode
    public void dispatchDemoCommand(String command, Bundle args) {
        if (!this.mDemoMode && command.equals("enter")) {
            this.mDemoMode = true;
            this.mContext.unregisterReceiver(this);
            return;
        }
        if (this.mDemoMode && command.equals("exit")) {
            this.mDemoMode = false;
            registerReceiver();
            updatePowerSave();
        } else if (this.mDemoMode && command.equals("battery")) {
            String level = args.getString("level");
            String plugged = args.getString("plugged");
            String powerSave = args.getString("powersave");
            if (level != null) {
                this.mLevel = Math.min(Math.max(Integer.parseInt(level), 0), 100);
            }
            if (plugged != null) {
                this.mPluggedIn = Boolean.parseBoolean(plugged);
            }
            if (powerSave != null) {
                this.mPowerSave = powerSave.equals("true");
                firePowerSaveChanged();
            }
            fireBatteryLevelChanged();
        }
    }
}
