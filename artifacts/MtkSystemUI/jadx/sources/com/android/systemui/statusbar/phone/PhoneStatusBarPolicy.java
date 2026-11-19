package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppGlobals;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SynchronousUserSwitchObserver;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.graphics.drawable.Icon;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.notification.StatusBarNotification;
import android.service.notification.ZenModeConfig;
import android.telecom.TelecomManager;
import android.text.format.DateFormat;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.IccCardConstants;
import com.android.systemui.Dependency;
import com.android.systemui.DockedStackExistsListener;
import com.android.systemui.R;
import com.android.systemui.SysUiServiceProvider;
import com.android.systemui.UiOffloadThread;
import com.android.systemui.qs.tiles.DndTile;
import com.android.systemui.qs.tiles.RotationLockTile;
import com.android.systemui.recents.misc.SysUiTaskStackChangeListener;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.KeyguardMonitor;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.util.NotificationChannels;
import com.android.systemui.util.Utils;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class PhoneStatusBarPolicy implements CommandQueue.Callbacks, BluetoothController.Callback, DataSaverController.Listener, DeviceProvisionedController.DeviceProvisionedListener, KeyguardMonitor.Callback, LocationController.LocationChangeCallback, RotationLockController.RotationLockControllerCallback, ZenModeController.Callback {
    private static final boolean DEBUG = Log.isLoggable("PhoneStatusBarPolicy", 3);
    private final AlarmManager mAlarmManager;
    private final Context mContext;
    private boolean mCurrentUserSetup;
    private boolean mDockedStackExists;
    private final StatusBarIconController mIconController;
    private AlarmManager.AlarmClockInfo mNextAlarm;
    private final String mSlotAlarmClock;
    private final String mSlotBluetooth;
    private final String mSlotBluetoothPan;
    private final String mSlotCast;
    private final String mSlotDataSaver;
    private final String mSlotHeadset;
    private final String mSlotHotspot;
    private final String mSlotLocation;
    private final String mSlotManagedProfile;
    private final String mSlotMute;
    private final String mSlotRotate;
    private final String mSlotTty;
    private final String mSlotVolume;
    private final String mSlotZen;
    private final UserManager mUserManager;
    private boolean mVolumeVisible;
    private boolean mZenVisible;
    private final Handler mHandler = new Handler();
    private final ArraySet<Pair<String, Integer>> mCurrentNotifs = new ArraySet<>();
    private final UiOffloadThread mUiOffloadThread = (UiOffloadThread) Dependency.get(UiOffloadThread.class);
    IccCardConstants.State mSimState = IccCardConstants.State.READY;
    private boolean mManagedProfileIconVisible = false;
    private final Handler mUpdateIconHandler = new UpdateIconHandler(this, null);
    private final SynchronousUserSwitchObserver mUserSwitchListener = new AnonymousClass1();
    private final HotspotController.Callback mHotspotCallback = new HotspotController.Callback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.2
        @Override // com.android.systemui.statusbar.policy.HotspotController.Callback
        public void onHotspotChanged(boolean enabled, int numDevices) {
            PhoneStatusBarPolicy.this.mIconController.setIconVisibility(PhoneStatusBarPolicy.this.mSlotHotspot, enabled);
        }
    };
    private final CastController.Callback mCastCallback = new CastController.Callback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.3
        @Override // com.android.systemui.statusbar.policy.CastController.Callback
        public void onCastDevicesChanged() {
            PhoneStatusBarPolicy.this.updateCast();
        }
    };
    private final NextAlarmController.NextAlarmChangeCallback mNextAlarmCallback = new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.4
        @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
        public void onNextAlarmChanged(AlarmManager.AlarmClockInfo nextAlarm) {
            PhoneStatusBarPolicy.this.mNextAlarm = nextAlarm;
            PhoneStatusBarPolicy.this.updateAlarm();
        }
    };
    private final SysUiTaskStackChangeListener mTaskListener = new SysUiTaskStackChangeListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.5
        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskStackChanged() {
            PhoneStatusBarPolicy.this.updateForegroundInstantApps();
        }
    };

    @VisibleForTesting
    BroadcastReceiver mIntentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.6
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "android.media.RINGER_MODE_CHANGED":
                case "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION":
                    PhoneStatusBarPolicy.this.updateVolumeZen();
                    break;
                case "android.media.STREAM_MUTE_CHANGED_ACTION":
                    int streamType = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                    boolean volumeMuted = intent.getBooleanExtra("android.media.EXTRA_STREAM_VOLUME_MUTED", false);
                    Log.d("PhoneStatusBarPolicy", "onReceive streamType : " + streamType + ", volumeMuted : " + volumeMuted);
                    if (streamType == 3) {
                        PhoneStatusBarPolicy.this.updateMute(volumeMuted);
                        break;
                    }
                    break;
                case "android.intent.action.SIM_STATE_CHANGED":
                    if (!intent.getBooleanExtra("rebroadcastOnUnlock", false)) {
                        PhoneStatusBarPolicy.this.updateSimState(intent);
                        break;
                    }
                    break;
                case "android.telecom.action.CURRENT_TTY_MODE_CHANGED":
                    PhoneStatusBarPolicy.this.updateTTY(intent.getIntExtra("android.telecom.intent.extra.CURRENT_TTY_MODE", 0));
                    break;
                case "android.intent.action.MANAGED_PROFILE_AVAILABLE":
                case "android.intent.action.MANAGED_PROFILE_UNAVAILABLE":
                case "android.intent.action.MANAGED_PROFILE_REMOVED":
                    PhoneStatusBarPolicy.this.updateManagedProfile();
                    break;
                case "android.intent.action.HEADSET_PLUG":
                    PhoneStatusBarPolicy.this.updateHeadsetPlug(intent);
                    break;
                case "android.intent.action.USER_SWITCHED":
                    PhoneStatusBarPolicy.this.updateAlarm();
                    int newUserId = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    PhoneStatusBarPolicy.this.registerAlarmClockChanged(newUserId, true);
                    break;
                case "mediatek.intent.action.EMBMS_SESSION_STATUS_CHANGED":
                    PhoneStatusBarPolicy.this.updateEmbmsState(intent);
                    break;
                case "android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED":
                    int state = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
                    Log.i("PhoneStatusBarPolicy", "bluetooth pan state changed by system, state : " + state);
                    Message message = new Message();
                    message.what = 1004;
                    message.arg1 = state;
                    PhoneStatusBarPolicy.this.mUpdateIconHandler.removeMessages(1004);
                    PhoneStatusBarPolicy.this.mUpdateIconHandler.sendMessageDelayed(message, 800L);
                    break;
                case "com.hcn.bluetooth.pan.state.changed":
                    boolean state2 = intent.getBooleanExtra("extra_state", false);
                    Log.i("PhoneStatusBarPolicy", "bluetooth pan state changed by user, state : " + state2);
                    if (state2) {
                        PhoneStatusBarPolicy.this.mIconController.setIcon(PhoneStatusBarPolicy.this.mSlotBluetoothPan, R.drawable.stat_sys_bluetooth_pan_n, "");
                    }
                    PhoneStatusBarPolicy.this.mIconController.setIconVisibility(PhoneStatusBarPolicy.this.mSlotBluetoothPan, state2);
                    break;
            }
        }
    };
    private Runnable mRemoveCastIconRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.7
        @Override // java.lang.Runnable
        public void run() {
            if (PhoneStatusBarPolicy.DEBUG) {
                Log.v("PhoneStatusBarPolicy", "updateCast: hiding icon NOW");
            }
            PhoneStatusBarPolicy.this.mIconController.setIconVisibility(PhoneStatusBarPolicy.this.mSlotCast, false);
        }
    };
    private BroadcastReceiver mAlarmIntentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy.8
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("PhoneStatusBarPolicy", "onReceive:" + action);
            if (action.equals("android.app.action.NEXT_ALARM_CLOCK_CHANGED")) {
                PhoneStatusBarPolicy.this.updateAlarm();
            }
        }
    };
    private final CastController mCast = (CastController) Dependency.get(CastController.class);
    private final HotspotController mHotspot = (HotspotController) Dependency.get(HotspotController.class);
    private BluetoothController mBluetooth = (BluetoothController) Dependency.get(BluetoothController.class);
    private final NextAlarmController mNextAlarmController = (NextAlarmController) Dependency.get(NextAlarmController.class);
    private final UserInfoController mUserInfoController = (UserInfoController) Dependency.get(UserInfoController.class);
    private final RotationLockController mRotationLockController = (RotationLockController) Dependency.get(RotationLockController.class);
    private final DataSaverController mDataSaver = (DataSaverController) Dependency.get(DataSaverController.class);
    private final ZenModeController mZenController = (ZenModeController) Dependency.get(ZenModeController.class);
    private final DeviceProvisionedController mProvisionedController = (DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class);
    private final KeyguardMonitor mKeyguardMonitor = (KeyguardMonitor) Dependency.get(KeyguardMonitor.class);
    private final LocationController mLocationController = (LocationController) Dependency.get(LocationController.class);
    private final String mSlotEmbms = "embms";

    @VisibleForTesting
    public PhoneStatusBarPolicy(Context context, StatusBarIconController iconController) {
        this.mContext = context;
        this.mIconController = iconController;
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        this.mUserManager = (UserManager) this.mContext.getSystemService("user");
        this.mSlotCast = context.getString(android.R.string.mediasize_iso_b8);
        this.mSlotHotspot = context.getString(android.R.string.mediasize_iso_c4);
        this.mSlotBluetooth = context.getString(android.R.string.mediasize_iso_b6);
        this.mSlotBluetoothPan = context.getString(android.R.string.mediasize_iso_b7);
        this.mSlotTty = context.getString(android.R.string.mediasize_japanese_jis_b3);
        this.mSlotZen = context.getString(android.R.string.mediasize_japanese_jis_b7);
        this.mSlotVolume = context.getString(android.R.string.mediasize_japanese_jis_b4);
        this.mSlotMute = context.getString(android.R.string.mediasize_iso_c9);
        this.mSlotAlarmClock = context.getString(android.R.string.mediasize_iso_b4);
        this.mSlotManagedProfile = context.getString(android.R.string.mediasize_iso_c7);
        this.mSlotRotate = context.getString(android.R.string.mediasize_japanese_hagaki);
        this.mSlotHeadset = context.getString(android.R.string.mediasize_iso_c3);
        this.mSlotDataSaver = context.getString(android.R.string.mediasize_iso_c10);
        this.mSlotLocation = context.getString(android.R.string.mediasize_iso_c6);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.RINGER_MODE_CHANGED");
        filter.addAction("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION");
        filter.addAction("android.intent.action.SIM_STATE_CHANGED");
        filter.addAction("android.telecom.action.CURRENT_TTY_MODE_CHANGED");
        filter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
        filter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        filter.addAction("android.intent.action.USER_SWITCHED");
        filter.addAction("android.intent.action.MANAGED_PROFILE_REMOVED");
        filter.addAction("android.media.STREAM_MUTE_CHANGED_ACTION");
        filter.addAction("android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED");
        filter.addAction("com.hcn.bluetooth.pan.state.changed");
        filter.addAction("mediatek.intent.action.EMBMS_SESSION_STATUS_CHANGED");
        this.mContext.registerReceiver(this.mIntentReceiver, filter, null, this.mHandler);
        registerAlarmClockChanged(0, false);
        try {
            ActivityManager.getService().registerUserSwitchObserver(this.mUserSwitchListener, "PhoneStatusBarPolicy");
        } catch (RemoteException e) {
        }
        updateTTY();
        updateBluetooth();
        this.mIconController.setIcon(this.mSlotEmbms, R.drawable.stat_sys_embms, null);
        this.mIconController.setIconVisibility(this.mSlotEmbms, false);
        this.mIconController.setIcon(this.mSlotAlarmClock, R.drawable.stat_sys_alarm, null);
        this.mIconController.setIconVisibility(this.mSlotAlarmClock, false);
        this.mIconController.setIcon(this.mSlotZen, R.drawable.stat_sys_zen_important, null);
        this.mIconController.setIconVisibility(this.mSlotZen, false);
        this.mIconController.setIcon(this.mSlotVolume, R.drawable.stat_sys_ringer_vibrate, null);
        this.mIconController.setIconVisibility(this.mSlotVolume, false);
        updateVolumeZen();
        this.mIconController.setIcon(this.mSlotMute, R.drawable.stat_sys_volume_off, null);
        this.mIconController.setIconVisibility(this.mSlotMute, false);
        AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
        updateMute(audioManager.isStreamMute(3));
        this.mIconController.setIcon(this.mSlotCast, R.drawable.stat_sys_cast, null);
        this.mIconController.setIconVisibility(this.mSlotCast, false);
        this.mIconController.setIcon(this.mSlotHotspot, R.drawable.stat_sys_hotspot, this.mContext.getString(R.string.accessibility_status_bar_hotspot));
        this.mIconController.setIconVisibility(this.mSlotHotspot, this.mHotspot.isHotspotEnabled());
        this.mIconController.setIcon(this.mSlotManagedProfile, R.drawable.stat_sys_managed_profile_status, this.mContext.getString(R.string.accessibility_managed_profile));
        this.mIconController.setIconVisibility(this.mSlotManagedProfile, this.mManagedProfileIconVisible);
        this.mIconController.setIcon(this.mSlotDataSaver, R.drawable.stat_sys_data_saver, context.getString(R.string.accessibility_data_saver_on));
        this.mIconController.setIconVisibility(this.mSlotDataSaver, false);
        this.mRotationLockController.addCallback(this);
        this.mBluetooth.addCallback(this);
        this.mProvisionedController.addCallback(this);
        this.mZenController.addCallback(this);
        this.mCast.addCallback(this.mCastCallback);
        this.mHotspot.addCallback(this.mHotspotCallback);
        this.mNextAlarmController.addCallback(this.mNextAlarmCallback);
        this.mDataSaver.addCallback(this);
        this.mKeyguardMonitor.addCallback(this);
        this.mLocationController.addCallback(this);
        ((CommandQueue) SysUiServiceProvider.getComponent(this.mContext, CommandQueue.class)).addCallbacks(this);
        ActivityManagerWrapper.getInstance().registerTaskStackListener(this.mTaskListener);
        NotificationManager noMan = (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
        for (StatusBarNotification notification : noMan.getActiveNotifications()) {
            if (notification.getId() == 7) {
                noMan.cancel(notification.getTag(), notification.getId());
            }
        }
        DockedStackExistsListener.register(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$PhoneStatusBarPolicy$eamN1_naA3onhFWH3wlclHRzu6s
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PhoneStatusBarPolicy.lambda$new$0(this.f$0, (Boolean) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$new$0(PhoneStatusBarPolicy phoneStatusBarPolicy, Boolean exists) {
        phoneStatusBarPolicy.mDockedStackExists = exists.booleanValue();
        phoneStatusBarPolicy.updateForegroundInstantApps();
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public void onZenChanged(int zen) {
        updateVolumeZen();
    }

    @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
    public void onConfigChanged(ZenModeConfig config) {
        updateVolumeZen();
    }

    @Override // com.android.systemui.statusbar.policy.LocationController.LocationChangeCallback
    public void onLocationActiveChanged(boolean active) {
        updateLocation();
    }

    private void updateLocation() {
        if (this.mLocationController.isLocationActive() && !"CVD7470-AJ".equals(Utils.getSystemProperty("ro.fota.device", ""))) {
            this.mIconController.setIcon(this.mSlotLocation, R.drawable.stat_sys_location, this.mContext.getString(R.string.accessibility_location_active));
        } else {
            this.mIconController.removeAllIconsForSlot(this.mSlotLocation);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAlarm() {
        AlarmManager.AlarmClockInfo alarm = this.mAlarmManager.getNextAlarmClock(-2);
        boolean z = false;
        boolean hasAlarm = alarm != null && alarm.getTriggerTime() > 0;
        int zen = this.mZenController.getZen();
        boolean zenNone = zen == 2;
        this.mIconController.setIcon(this.mSlotAlarmClock, zenNone ? R.drawable.stat_sys_alarm_dim : R.drawable.stat_sys_alarm, buildAlarmContentDescription());
        StatusBarIconController statusBarIconController = this.mIconController;
        String str = this.mSlotAlarmClock;
        if (this.mCurrentUserSetup && hasAlarm) {
            z = true;
        }
        statusBarIconController.setIconVisibility(str, z);
    }

    private String buildAlarmContentDescription() {
        if (this.mNextAlarm == null) {
            return this.mContext.getString(R.string.status_bar_alarm);
        }
        return formatNextAlarm(this.mNextAlarm, this.mContext);
    }

    private static String formatNextAlarm(AlarmManager.AlarmClockInfo info, Context context) {
        if (info == null) {
            return "";
        }
        String skeleton = DateFormat.is24HourFormat(context, ActivityManager.getCurrentUser()) ? "EHm" : "Ehma";
        String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), skeleton);
        String dateString = DateFormat.format(pattern, info.getTriggerTime()).toString();
        return context.getString(R.string.accessibility_quick_settings_alarm, dateString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateSimState(Intent intent) {
        String stateExtra = intent.getStringExtra("ss");
        if ("ABSENT".equals(stateExtra)) {
            this.mSimState = IccCardConstants.State.ABSENT;
            return;
        }
        if ("CARD_IO_ERROR".equals(stateExtra)) {
            this.mSimState = IccCardConstants.State.CARD_IO_ERROR;
            return;
        }
        if ("CARD_RESTRICTED".equals(stateExtra)) {
            this.mSimState = IccCardConstants.State.CARD_RESTRICTED;
            return;
        }
        if ("READY".equals(stateExtra)) {
            this.mSimState = IccCardConstants.State.READY;
            return;
        }
        if ("LOCKED".equals(stateExtra)) {
            String lockedReason = intent.getStringExtra("reason");
            if ("PIN".equals(lockedReason)) {
                this.mSimState = IccCardConstants.State.PIN_REQUIRED;
                return;
            } else if ("PUK".equals(lockedReason)) {
                this.mSimState = IccCardConstants.State.PUK_REQUIRED;
                return;
            } else {
                this.mSimState = IccCardConstants.State.NETWORK_LOCKED;
                return;
            }
        }
        this.mSimState = IccCardConstants.State.UNKNOWN;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateVolumeZen() {
        AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
        boolean zenVisible = false;
        int zenIconId = 0;
        String zenDescription = null;
        boolean volumeVisible = false;
        int volumeIconId = 0;
        String volumeDescription = null;
        int zen = this.mZenController.getZen();
        if (DndTile.isVisible(this.mContext) || DndTile.isCombinedIcon(this.mContext)) {
            zenVisible = zen != 0;
            zenIconId = R.drawable.stat_sys_dnd;
            zenDescription = this.mContext.getString(R.string.quick_settings_dnd_label);
        } else if (zen == 2) {
            zenVisible = true;
            zenIconId = R.drawable.stat_sys_zen_none;
            zenDescription = this.mContext.getString(R.string.interruption_level_none);
        } else if (zen == 1) {
            zenVisible = true;
            zenIconId = R.drawable.stat_sys_zen_important;
            zenDescription = this.mContext.getString(R.string.interruption_level_priority);
        }
        if (!ZenModeConfig.isZenOverridingRinger(zen, this.mZenController.getConfig())) {
            if (audioManager.getRingerModeInternal() == 1) {
                volumeVisible = true;
                volumeIconId = R.drawable.stat_sys_ringer_vibrate;
                volumeDescription = this.mContext.getString(R.string.accessibility_ringer_vibrate);
            } else if (audioManager.getRingerModeInternal() == 0) {
                volumeVisible = true;
                volumeIconId = R.drawable.stat_sys_ringer_silent;
                volumeDescription = this.mContext.getString(R.string.accessibility_ringer_silent);
            }
        }
        if (zenVisible) {
            this.mIconController.setIcon(this.mSlotZen, zenIconId, zenDescription);
        }
        if (zenVisible != this.mZenVisible) {
            this.mIconController.setIconVisibility(this.mSlotZen, zenVisible);
            this.mZenVisible = zenVisible;
        }
        if (volumeVisible) {
            this.mIconController.setIcon(this.mSlotVolume, volumeIconId, volumeDescription);
        }
        if (volumeVisible != this.mVolumeVisible) {
            this.mIconController.setIconVisibility(this.mSlotVolume, volumeVisible);
            this.mVolumeVisible = volumeVisible;
        }
        updateAlarm();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMute(boolean volumeMuted) {
        this.mIconController.setIconVisibility(this.mSlotMute, volumeMuted);
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
    public void onBluetoothDevicesChanged() {
        updateBluetooth();
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
    public void onBluetoothStateChange(boolean enabled) {
        updateBluetooth();
    }

    private final void updateBluetooth() {
        int iconId = R.drawable.stat_sys_data_bluetooth;
        String contentDescription = this.mContext.getString(R.string.accessibility_quick_settings_bluetooth_on);
        boolean bluetoothVisible = false;
        if (this.mBluetooth != null) {
            if (this.mBluetooth.isBluetoothConnected()) {
                iconId = R.drawable.stat_sys_data_bluetooth_connected;
                contentDescription = this.mContext.getString(R.string.accessibility_bluetooth_connected);
                this.mBluetooth.isBluetoothEnabled();
            }
            bluetoothVisible = this.mBluetooth.isBluetoothEnabled();
        }
        this.mIconController.setIcon(this.mSlotBluetooth, iconId, contentDescription);
        this.mIconController.setIconVisibility(this.mSlotBluetooth, bluetoothVisible);
    }

    private final void updateTTY() {
        TelecomManager telecomManager = (TelecomManager) this.mContext.getSystemService("telecom");
        if (telecomManager == null) {
            updateTTY(0);
        } else {
            updateTTY(telecomManager.getCurrentTtyMode());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateTTY(int currentTtyMode) {
        boolean enabled = currentTtyMode != 0;
        if (DEBUG) {
            Log.v("PhoneStatusBarPolicy", "updateTTY: enabled: " + enabled);
        }
        if (enabled) {
            if (DEBUG) {
                Log.v("PhoneStatusBarPolicy", "updateTTY: set TTY on");
            }
            this.mIconController.setIcon(this.mSlotTty, R.drawable.stat_sys_tty_mode, this.mContext.getString(R.string.accessibility_tty_enabled));
            this.mIconController.setIconVisibility(this.mSlotTty, true);
            return;
        }
        if (DEBUG) {
            Log.v("PhoneStatusBarPolicy", "updateTTY: set TTY off");
        }
        this.mIconController.setIconVisibility(this.mSlotTty, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCast() {
        boolean isCasting = false;
        for (CastController.CastDevice device : this.mCast.getCastDevices()) {
            if (device.state == 1 || device.state == 2) {
                isCasting = true;
                break;
            }
        }
        if (DEBUG) {
            Log.v("PhoneStatusBarPolicy", "updateCast: isCasting: " + isCasting);
        }
        this.mHandler.removeCallbacks(this.mRemoveCastIconRunnable);
        if (isCasting) {
            this.mIconController.setIcon(this.mSlotCast, R.drawable.stat_sys_cast, this.mContext.getString(R.string.accessibility_casting));
            this.mIconController.setIconVisibility(this.mSlotCast, true);
        } else {
            if (DEBUG) {
                Log.v("PhoneStatusBarPolicy", "updateCast: hiding icon in 3 sec...");
            }
            this.mHandler.postDelayed(this.mRemoveCastIconRunnable, 3000L);
        }
    }

    protected void updateEmbmsState(Intent intent) {
        int isActive = intent.getIntExtra("isActived", 0);
        if (DEBUG) {
            Log.d("PhoneStatusBarPolicy", "updateEmbmsState  active = " + isActive);
        }
        if (isActive == 1) {
            this.mIconController.setIcon(this.mSlotEmbms, R.drawable.stat_sys_embms, this.mContext.getString(R.string.accessibility_embms_enabled));
            this.mIconController.setIconVisibility(this.mSlotEmbms, true);
        } else {
            this.mIconController.setIconVisibility(this.mSlotEmbms, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateManagedProfile() {
        this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$PhoneStatusBarPolicy$Z14_E32gJZONWu0xka6llWomWrI
            @Override // java.lang.Runnable
            public final void run() {
                PhoneStatusBarPolicy.lambda$updateManagedProfile$3(this.f$0);
            }
        });
    }

    public static /* synthetic */ void lambda$updateManagedProfile$3(final PhoneStatusBarPolicy phoneStatusBarPolicy) {
        try {
            int userId = ActivityManager.getService().getLastResumedActivityUserId();
            final boolean isManagedProfile = phoneStatusBarPolicy.mUserManager.isManagedProfile(userId);
            phoneStatusBarPolicy.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$PhoneStatusBarPolicy$t6hzEfridDOsU9CjkO4yADRJvjA
                @Override // java.lang.Runnable
                public final void run() {
                    PhoneStatusBarPolicy.lambda$updateManagedProfile$2(this.f$0, isManagedProfile);
                }
            });
        } catch (RemoteException e) {
            Log.w("PhoneStatusBarPolicy", "updateManagedProfile: ", e);
        }
    }

    public static /* synthetic */ void lambda$updateManagedProfile$2(PhoneStatusBarPolicy phoneStatusBarPolicy, boolean isManagedProfile) {
        boolean showIcon;
        if (isManagedProfile && (!phoneStatusBarPolicy.mKeyguardMonitor.isShowing() || phoneStatusBarPolicy.mKeyguardMonitor.isOccluded())) {
            showIcon = true;
            phoneStatusBarPolicy.mIconController.setIcon(phoneStatusBarPolicy.mSlotManagedProfile, R.drawable.stat_sys_managed_profile_status, phoneStatusBarPolicy.mContext.getString(R.string.accessibility_managed_profile));
        } else {
            showIcon = false;
        }
        if (phoneStatusBarPolicy.mManagedProfileIconVisible != showIcon) {
            phoneStatusBarPolicy.mIconController.setIconVisibility(phoneStatusBarPolicy.mSlotManagedProfile, showIcon);
            phoneStatusBarPolicy.mManagedProfileIconVisible = showIcon;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateForegroundInstantApps() {
        final NotificationManager noMan = (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
        final ArraySet<Pair<String, Integer>> notifs = new ArraySet<>(this.mCurrentNotifs);
        final IPackageManager pm = AppGlobals.getPackageManager();
        this.mCurrentNotifs.clear();
        this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$PhoneStatusBarPolicy$LDjOSwhc6lGaPoGcAB44Ceqmrn0
            @Override // java.lang.Runnable
            public final void run() {
                PhoneStatusBarPolicy.lambda$updateForegroundInstantApps$5(this.f$0, notifs, noMan, pm);
            }
        });
    }

    public static /* synthetic */ void lambda$updateForegroundInstantApps$5(PhoneStatusBarPolicy phoneStatusBarPolicy, ArraySet notifs, final NotificationManager noMan, IPackageManager pm) {
        int windowingMode;
        try {
            ActivityManager.StackInfo focusedStack = ActivityManager.getService().getFocusedStackInfo();
            if (focusedStack != null && ((windowingMode = focusedStack.configuration.windowConfiguration.getWindowingMode()) == 1 || windowingMode == 4)) {
                phoneStatusBarPolicy.checkStack(focusedStack, notifs, noMan, pm);
            }
            if (phoneStatusBarPolicy.mDockedStackExists) {
                phoneStatusBarPolicy.checkStack(3, 0, notifs, noMan, pm);
            }
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        notifs.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$PhoneStatusBarPolicy$wi5igeUw1IMOscl7OYC68EMvMy0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Pair pair = (Pair) obj;
                noMan.cancelAsUser((String) pair.first, 7, new UserHandle(((Integer) pair.second).intValue()));
            }
        });
    }

    private void checkStack(int windowingMode, int activityType, ArraySet<Pair<String, Integer>> notifs, NotificationManager noMan, IPackageManager pm) {
        try {
            ActivityManager.StackInfo info = ActivityManager.getService().getStackInfo(windowingMode, activityType);
            checkStack(info, notifs, noMan, pm);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    private void checkStack(ActivityManager.StackInfo info, ArraySet<Pair<String, Integer>> notifs, NotificationManager noMan, IPackageManager pm) {
        if (info == null) {
            return;
        }
        try {
            if (info.topActivity == null) {
                return;
            }
            String pkg = info.topActivity.getPackageName();
            if (!hasNotif(notifs, pkg, info.userId)) {
                ApplicationInfo appInfo = pm.getApplicationInfo(pkg, 8192, info.userId);
                if (appInfo.isInstantApp()) {
                    postEphemeralNotif(pkg, info.userId, appInfo, noMan, info.taskIds[info.taskIds.length - 1]);
                }
            }
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    private void postEphemeralNotif(String pkg, int userId, ApplicationInfo appInfo, NotificationManager noMan, int taskId) {
        Bundle extras = new Bundle();
        extras.putString("android.substName", this.mContext.getString(R.string.instant_apps));
        this.mCurrentNotifs.add(new Pair<>(pkg, Integer.valueOf(userId)));
        String message = this.mContext.getString(R.string.instant_apps_message);
        PendingIntent appInfoAction = PendingIntent.getActivity(this.mContext, 0, new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", pkg, null)), 0);
        Notification.Action action = new Notification.Action.Builder((Icon) null, this.mContext.getString(R.string.app_info), appInfoAction).build();
        Intent browserIntent = getTaskIntent(taskId, userId);
        Notification.Builder builder = new Notification.Builder(this.mContext, NotificationChannels.GENERAL);
        if (browserIntent != null && browserIntent.isWebIntent()) {
            browserIntent.setComponent(null).setPackage(null).addFlags(512).addFlags(268435456);
            PendingIntent pendingIntent = PendingIntent.getActivity(this.mContext, 0, browserIntent, 0);
            ComponentName aiaComponent = null;
            try {
                aiaComponent = AppGlobals.getPackageManager().getInstantAppInstallerComponent();
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            Intent goToWebIntent = new Intent().setComponent(aiaComponent).setAction("android.intent.action.VIEW").addCategory("android.intent.category.BROWSABLE").addCategory("unique:" + System.currentTimeMillis()).putExtra("android.intent.extra.PACKAGE_NAME", appInfo.packageName).putExtra("android.intent.extra.VERSION_CODE", appInfo.versionCode & Integer.MAX_VALUE).putExtra("android.intent.extra.LONG_VERSION_CODE", appInfo.versionCode).putExtra("android.intent.extra.EPHEMERAL_FAILURE", pendingIntent).putExtra("android.intent.extra.INSTANT_APP_FAILURE", pendingIntent);
            PendingIntent webPendingIntent = PendingIntent.getActivity(this.mContext, 0, goToWebIntent, 0);
            Notification.Action webAction = new Notification.Action.Builder((Icon) null, this.mContext.getString(R.string.go_to_web), webPendingIntent).build();
            builder.addAction(webAction);
        }
        noMan.notifyAsUser(pkg, 7, builder.addExtras(extras).addAction(action).setContentIntent(appInfoAction).setColor(this.mContext.getColor(R.color.instant_apps_color)).setContentTitle(appInfo.loadLabel(this.mContext.getPackageManager())).setLargeIcon(Icon.createWithResource(pkg, appInfo.icon)).setSmallIcon(Icon.createWithResource(this.mContext.getPackageName(), R.drawable.instant_icon)).setContentText(message).setOngoing(true).build(), new UserHandle(userId));
    }

    private Intent getTaskIntent(int taskId, int userId) {
        try {
            int i = 0;
            List<ActivityManager.RecentTaskInfo> tasks = ActivityManager.getService().getRecentTasks(5, 0, userId).getList();
            while (true) {
                int i2 = i;
                int i3 = tasks.size();
                if (i2 < i3) {
                    if (tasks.get(i2).id != taskId) {
                        i = i2 + 1;
                    } else {
                        return tasks.get(i2).baseIntent;
                    }
                } else {
                    return null;
                }
            }
        } catch (RemoteException e) {
            return null;
        }
    }

    private boolean hasNotif(ArraySet<Pair<String, Integer>> notifs, String pkg, int userId) {
        Pair<String, Integer> key = new Pair<>(pkg, Integer.valueOf(userId));
        if (notifs.remove(key)) {
            this.mCurrentNotifs.add(key);
            return true;
        }
        return false;
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$1, reason: invalid class name */
    class AnonymousClass1 extends SynchronousUserSwitchObserver {
        AnonymousClass1() {
        }

        public void onUserSwitching(int newUserId) throws RemoteException {
            PhoneStatusBarPolicy.this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$PhoneStatusBarPolicy$1$4_BI5ieR2ylfAj9z5SwNfbqaqk4
                @Override // java.lang.Runnable
                public final void run() {
                    PhoneStatusBarPolicy.this.mUserInfoController.reloadUserInfo();
                }
            });
        }

        public void onUserSwitchComplete(int newUserId) throws RemoteException {
            PhoneStatusBarPolicy.this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$PhoneStatusBarPolicy$1$lONTSmykfPe64DIHRuLayVCRwlI
                @Override // java.lang.Runnable
                public final void run() {
                    PhoneStatusBarPolicy.AnonymousClass1.lambda$onUserSwitchComplete$1(this.f$0);
                }
            });
        }

        public static /* synthetic */ void lambda$onUserSwitchComplete$1(AnonymousClass1 anonymousClass1) {
            PhoneStatusBarPolicy.this.updateAlarm();
            PhoneStatusBarPolicy.this.updateManagedProfile();
            PhoneStatusBarPolicy.this.updateForegroundInstantApps();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void appTransitionStarting(long startTime, long duration, boolean forced) {
        updateManagedProfile();
        updateForegroundInstantApps();
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardMonitor.Callback
    public void onKeyguardShowingChanged() {
        updateManagedProfile();
        updateForegroundInstantApps();
    }

    @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
    public void onUserSetupChanged() {
        boolean userSetup = this.mProvisionedController.isUserSetup(this.mProvisionedController.getCurrentUser());
        if (this.mCurrentUserSetup == userSetup) {
            return;
        }
        this.mCurrentUserSetup = userSetup;
        updateAlarm();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void preloadRecentApps() {
        updateForegroundInstantApps();
    }

    @Override // com.android.systemui.statusbar.policy.RotationLockController.RotationLockControllerCallback
    public void onRotationLockStateChanged(boolean rotationLocked, boolean affordanceVisible) {
        boolean portrait = RotationLockTile.isCurrentOrientationLockPortrait(this.mRotationLockController, this.mContext);
        if (rotationLocked) {
            if (portrait) {
                this.mIconController.setIcon(this.mSlotRotate, R.drawable.stat_sys_rotate_portrait, this.mContext.getString(R.string.accessibility_rotation_lock_on_portrait));
            } else {
                this.mIconController.setIcon(this.mSlotRotate, R.drawable.stat_sys_rotate_landscape, this.mContext.getString(R.string.accessibility_rotation_lock_on_landscape));
            }
            this.mIconController.setIconVisibility(this.mSlotRotate, true);
            return;
        }
        this.mIconController.setIconVisibility(this.mSlotRotate, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHeadsetPlug(Intent intent) {
        int i;
        boolean connected = intent.getIntExtra("state", 0) != 0;
        boolean hasMic = intent.getIntExtra("microphone", 0) != 0;
        Log.d("PhoneStatusBarPolicy", "updateHeadsetPlug connected:" + connected + ",hasMic:" + hasMic);
        if (connected) {
            Context context = this.mContext;
            if (hasMic) {
                i = R.string.accessibility_status_bar_headset;
            } else {
                i = R.string.accessibility_status_bar_headphones;
            }
            String contentDescription = context.getString(i);
            this.mIconController.setIcon(this.mSlotHeadset, hasMic ? R.drawable.ic_headset_mic : R.drawable.ic_headset, contentDescription);
            this.mIconController.setIconVisibility(this.mSlotHeadset, true);
            return;
        }
        this.mIconController.setIconVisibility(this.mSlotHeadset, false);
    }

    @Override // com.android.systemui.statusbar.policy.DataSaverController.Listener
    public void onDataSaverChanged(boolean isDataSaving) {
        this.mIconController.setIconVisibility(this.mSlotDataSaver, isDataSaving);
    }

    private class UpdateIconHandler extends Handler {
        private UpdateIconHandler() {
        }

        /* synthetic */ UpdateIconHandler(PhoneStatusBarPolicy x0, AnonymousClass1 x1) {
            this();
        }

        @Override // android.os.Handler
        public void handleMessage(Message m) {
            if (m.what == 1004) {
                int state = m.arg1;
                if (state == 2) {
                    PhoneStatusBarPolicy.this.mIconController.setIcon(PhoneStatusBarPolicy.this.mSlotBluetoothPan, R.drawable.stat_sys_bluetooth_pan_f, "");
                    PhoneStatusBarPolicy.this.mIconController.setIconVisibility(PhoneStatusBarPolicy.this.mSlotBluetoothPan, true);
                } else {
                    PhoneStatusBarPolicy.this.mIconController.setIcon(PhoneStatusBarPolicy.this.mSlotBluetoothPan, R.drawable.stat_sys_bluetooth_pan_n, "");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerAlarmClockChanged(int newUserId, boolean userSwitch) {
        if (userSwitch) {
            this.mContext.unregisterReceiver(this.mAlarmIntentReceiver);
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.app.action.NEXT_ALARM_CLOCK_CHANGED");
        Log.d("PhoneStatusBarPolicy", "registerAlarmClockChanged:" + newUserId);
        UserHandle newUserHandle = new UserHandle(newUserId);
        this.mContext.registerReceiverAsUser(this.mAlarmIntentReceiver, newUserHandle, filter, null, this.mHandler);
    }
}
