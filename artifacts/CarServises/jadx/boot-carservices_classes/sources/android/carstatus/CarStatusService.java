package android.carstatus;

import android.Configures.HConfig;
import android.app.ActivityManager;
import android.app.UiModeManager;
import android.carsource.McuConstant;
import android.carsource.McuService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.Color;
import android.link.CarletterInterface;
import android.link.LinkManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.provider.Settings;
import android.radio.RadioService;
import android.sourceservice.ExtAudioMuxer;
import android.sourceservice.SourceService;
import android.system.Os;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.utilities.Util;
import android.view.WindowManager;
import android.widget.TextView;
import com.mediatek.galleryfeature.pq.filter.Filter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class CarStatusService extends ICarStatus.Stub {
    private static final String AUTONAVI_BROADCAST_RECV = "AUTONAVI_STANDARD_BROADCAST_SEND";
    private static final String AUTONAVI_BROADCAST_SEND = "AUTONAVI_STANDARD_BROADCAST_RECV";
    private static final String BACKLIGHT_BRIGHT_D = "day_screen_brightness";
    private static final String BACKLIGHT_BRIGHT_N = "night_screen_brightness";
    private static final String GSENSOR_ACTION = "android.mcu.device.action.GSENSOR";
    private static final int KEY_TYPE_GET_STATUS = 10016;
    private static final int KEY_TYPE_SEND_STATUS = 10017;
    private static final String TESTING_TAG = "CarStatusService.debug";
    private static CarStatusService mCarStatusService;
    private Context mContext;
    private TextView mDebugView;
    private boolean mHeadlightEnable;
    private int mLedColor;
    private PowerManager mPowerManager;
    private final SettingsObserver mSettingsObserver;
    private UiModeManager mUiModeManager;
    private final String TAG = "CarStatusService";
    private final String BACKLIGHT_MASK = "/sys/class/leds/lcd-backlight/mask";
    private final String ANDROID_RB_PROPERTY = "sys.powerctl";
    private final String sLedSettingFileName = "/dev/socket/teststub";
    private final File CarStatusFile = new File("/dev/carstatus");
    private final File LifeTimeFile = new File("/sys/block/mmcblk0/device/life_time");
    private final File DateFile = new File("/sys/block/mmcblk0/device/date");
    private final SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");
    private final File mCarStatusKvFile = new File("/tmp/carinfo/carstatus.kv");
    private final ConcurrentHashMap<String, String> mCarStatusMap = new ConcurrentHashMap<>();
    private int mIsReversing = 0;
    private int mParking = -1;
    private int mTelMute = 1;
    private int mHeadLight = -1;
    private int mDashboardBrightness = 0;
    private volatile int mAcc = 1;
    private boolean mBootCompleted = false;
    private int mCheckWakeLockTimes = 0;
    private int mAccoffMillis = -1;
    private int mPowerOn = 1;
    private int mAccTimes = 0;
    private final int MSG_BKLIGHT_SETTINGS = 1;
    private final int MSG_ACC_DELAYED = 2;
    private final int MSG_SHOW_DEBUGVIEW = 3;
    private final int MSG_STATUS_CHANGED = 4;
    private final int MSG_GOINGTO_SLEEP = 5;
    private final int MSG_REV_EVENT = 6;
    private final int MSG_CHECK_WAKLOCK = 7;
    private final int MSG_INIT_VOLUME = 8;
    private final int MSG_SAVE_SETTING = 9;
    private final int MSG_LAUNCH_SERVICES = 10;
    private final int MSG_ACC_UNMUTE = 11;
    private final int MSG_LAUNCH_APPS = 12;
    private final int MSG_HEADLIGHT_DELAYED = 13;
    private Handler mWorkingHandler = new Handler() { // from class: android.carstatus.CarStatusService.2
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CarStatusService.this.headlighSettingChanged();
                    break;
                case 2:
                    CarStatusService.this.onAccStatusInner(msg.arg1);
                    break;
                case 3:
                    CarStatusService.this.showDebugView();
                    break;
                case 4:
                    CarStatusService.this.readStatus();
                    break;
                case 5:
                    CarStatusService.this.goingToSleep();
                    break;
                case 6:
                    CarStatusService.this.setReversingInner(msg.arg1, msg.arg2);
                    break;
                case 7:
                    if (CarStatusService.this.mAcc == 0) {
                        CarStatusService.access$608(CarStatusService.this);
                        CarStatusService.this.checkWakeLock(CarStatusService.this.mCheckWakeLockTimes > 15);
                        CarStatusService.this.mWorkingHandler.sendEmptyMessageDelayed(7, 5000L);
                    }
                    break;
                case 8:
                    Filter.loadPQSettings();
                    CarStatusService.this.setDefaultVolume();
                    break;
                case McuConstant.K_2 /* 9 */:
                    CarStatusService.this.saveLedSettings();
                    break;
                case McuConstant.K_3 /* 10 */:
                    CarStatusService.this.startAllServices();
                    break;
                case McuConstant.K_4 /* 11 */:
                    if (CarStatusService.this.mAcc > 0) {
                        McuService.sendCmdCarStatus("mute=10000");
                        ExtAudioMuxer.setMasterMute(false, 2);
                    }
                    break;
                case McuConstant.K_5 /* 12 */:
                    CarStatusService.this.startNavi();
                    break;
                case McuConstant.K_6 /* 13 */:
                    CarStatusService.this.onMsgHeadlightChanged(msg.arg1);
                    break;
            }
        }
    };
    private int mReadacc = -1;
    private UEventObserver mStatusObserver = new UEventObserver() { // from class: android.carstatus.CarStatusService.3
        public void onUEvent(UEventObserver.UEvent event) {
            CarStatusService.this.mWorkingHandler.removeMessages(4);
            CarStatusService.this.mWorkingHandler.sendEmptyMessageDelayed(4, 10L);
        }
    };
    private String[] SERVICE_ON_ACC = {"com.hcn.autoeq", "com.hcn.autoeq.service.RemoteControlService"};

    static /* synthetic */ int access$608(CarStatusService x0) {
        int i = x0.mCheckWakeLockTimes;
        x0.mCheckWakeLockTimes = i + 1;
        return i;
    }

    public static CarStatusService getCarStatusService() {
        return mCarStatusService;
    }

    public CarStatusService(Context context) {
        this.mHeadlightEnable = false;
        this.mLedColor = 0;
        this.mContext = context;
        mCarStatusService = this;
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        this.mUiModeManager = (UiModeManager) context.getSystemService("uimode");
        final ContentResolver cr = this.mContext.getContentResolver();
        int set = Settings.System.getInt(cr, HConfig.headlight_setting, 1);
        Log.i("CarStatusService", "Headlight setting changed to " + set);
        this.mHeadlightEnable = set > 0;
        this.mLedColor = Settings.System.getInt(cr, HConfig.pannel_led_color, -1);
        this.mWorkingHandler.sendEmptyMessageDelayed(4, 10L);
        McuService.sendCmdCarStatus("rev=0");
        if (Settings.System.getInt(cr, "screen_brightness", -1000) == -1000) {
            int brightness = this.mPowerManager.getDefaultScreenBrightnessSetting();
            Settings.System.putInt(cr, "screen_brightness", brightness);
        }
        if (Settings.System.getInt(cr, BACKLIGHT_BRIGHT_D, -1000) == -1000) {
            int bright = Settings.System.getInt(cr, "screen_brightness", 110);
            Settings.System.putInt(cr, BACKLIGHT_BRIGHT_D, bright);
        }
        int fcamera_sw = Settings.System.getInt(cr, HConfig.fcamera_switch_key, 0);
        int dash_camera_en = Settings.System.getInt(cr, HConfig.dash_camera_icon_enable, 0);
        if (fcamera_sw > 0 || dash_camera_en > 0) {
            McuService.sendCmdCarStatus("360module=0");
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.BOOT_COMPLETED");
        filter.addAction("android.intent.action.SCREEN_ON");
        filter.addAction("android.mcu.device.action.GSENSOR");
        filter.addAction(AUTONAVI_BROADCAST_RECV);
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: android.carstatus.CarStatusService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action.equals("android.intent.action.BOOT_COMPLETED")) {
                    CarStatusService.this.mBootCompleted = true;
                    CarStatusService.this.mStatusObserver.startObserving("DEVPATH=/devices/platform/carstatus");
                    CarStatusService.this.mWorkingHandler.sendEmptyMessage(4);
                    CarStatusService.this.mWorkingHandler.sendEmptyMessage(1);
                    CarStatusService.this.mWorkingHandler.sendEmptyMessageDelayed(10, 200L);
                    CarStatusService.this.mWorkingHandler.sendEmptyMessageDelayed(12, 2000L);
                    if (Settings.System.getInt(cr, CarStatusService.TESTING_TAG, 0) > 0) {
                        CarStatusService.this.enableDebugView();
                        return;
                    } else {
                        CarStatusService.this.disableDebugView();
                        return;
                    }
                }
                if (action.equals("android.intent.action.SCREEN_ON")) {
                    CarStatusService.this.mWorkingHandler.sendEmptyMessageDelayed(8, 300L);
                    return;
                }
                if (action.equals("android.mcu.device.action.GSENSOR")) {
                    if (CarStatusService.this.mAcc == 0) {
                        Log.i("CarStatusService", "g-sensor wakeup...");
                        CarStatusService.this.mCheckWakeLockTimes = 0;
                        CarStatusService.this.mAccoffMillis = (int) (SystemClock.uptimeMillis() / 1000);
                        return;
                    }
                    return;
                }
                if (action.equals(CarStatusService.AUTONAVI_BROADCAST_RECV)) {
                    int key_type = intent.getIntExtra("KEY_TYPE", -1);
                    intent.getIntExtra("EXTRA_TYPE", -1);
                    if (key_type == CarStatusService.KEY_TYPE_GET_STATUS) {
                        CarStatusService.this.sendStatusToAutonavi(0, CarStatusService.this.mHeadLight > 0 ? 0 : 1);
                    }
                }
            }
        }, filter);
        this.mSettingsObserver = new SettingsObserver();
        initLogbyEmmcLifeTime();
    }

    @Override // android.carstatus.ICarStatus
    public boolean getHeadLightStatus() {
        return this.mHeadLight > 0;
    }

    @Override // android.carstatus.ICarStatus
    public void setDashboardBrightness(int value) {
        if (this.mDashboardBrightness == value) {
            return;
        }
        this.mDashboardBrightness = value;
    }

    public void setHeadLightStatus(int onoff) {
        int bright;
        if (this.mHeadLight == onoff) {
            return;
        }
        this.mHeadLight = onoff;
        ContentResolver cr = this.mContext.getContentResolver();
        if (onoff > 0) {
            bright = Settings.System.getInt(cr, BACKLIGHT_BRIGHT_N, 30);
        } else {
            bright = Settings.System.getInt(cr, BACKLIGHT_BRIGHT_D, McuConstant.K_SWCSETTING);
        }
        setPanelLed();
        Log.i("CarStatusService", "Headlight brightness to " + bright);
        Settings.System.putInt(cr, "screen_brightness", bright);
        this.mWorkingHandler.removeMessages(13);
        Message msg = this.mWorkingHandler.obtainMessage(13, onoff, 0);
        this.mWorkingHandler.sendMessageDelayed(msg, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void headlighSettingChanged() {
        ContentResolver cr = this.mContext.getContentResolver();
        int set = Settings.System.getInt(cr, HConfig.headlight_setting, 1);
        Log.i("CarStatusService", "Headlight setting changed to " + set);
        this.mHeadlightEnable = set > 0;
        setPanelLed();
    }

    @Override // android.carstatus.ICarStatus
    public int getParkingStatus() {
        return this.mParking;
    }

    @Override // android.carstatus.ICarStatus
    public void setParkingStatus(int park) {
        if (!this.mBootCompleted || this.mParking == park) {
            return;
        }
        this.mParking = park;
        Log.i("CarStatusService", "Parking State Changed to " + park);
        Intent i = new Intent(CarStatus.ACTION_PARKING);
        i.putExtra(CarStatus.EXTRA_PARKING, this.mParking == 1);
        this.mContext.sendBroadcastAsUser(i, UserHandle.CURRENT, null);
    }

    @Override // android.carstatus.ICarStatus
    public int getReversing() {
        return this.mIsReversing;
    }

    @Override // android.carstatus.ICarStatus
    public void setReversing(int status) {
        if (!this.mBootCompleted || status < 0) {
            return;
        }
        int pid = Binder.getCallingPid();
        if (pid != Process.myPid()) {
            McuService.sendCmdCarStatus("rev=" + status);
        }
        Message msg = this.mWorkingHandler.obtainMessage(6, status, pid);
        this.mWorkingHandler.removeMessages(6);
        int debounce = Math.max(0, Math.min(4000, Settings.System.getInt(this.mContext.getContentResolver(), HConfig.rev_debounce, McuConstant.K_DVD)));
        this.mWorkingHandler.sendMessageDelayed(msg, status > 0 ? debounce : 0L);
        if (status == 0) {
            SourceService.setBackCarMediaPlay();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setReversingInner(int status, int pid) {
        if (status > 0 && ActivityManager.isSystemReady()) {
            try {
                ActivityManager.getService().closeSystemDialogs("setRev");
            } catch (RemoteException e) {
            }
        }
        if (this.mIsReversing != status) {
            this.mIsReversing = status;
            Log.i("CarStatusService", String.format("Reversing changing to %d from pid %d", Integer.valueOf(status), Integer.valueOf(pid)));
            if (this.mIsReversing > 0) {
                McuService.sendCmdCarStatus("360module=1");
            } else {
                boolean isCameraUiModeTop = SourceService.isCameraUiModeTop(this.mContext);
                if (!isCameraUiModeTop) {
                    McuService.sendCmdCarStatus("360module=0");
                }
            }
            McuService.getMcuService().onReversChanged(status > 0);
            backlightOnoff(status > 0 ? 16777472 : 16777216);
            AudioManager am = (AudioManager) this.mContext.getSystemService("audio");
            int index = am.getStreamVolume(3);
            if (index > 0) {
                am.adjustStreamVolume(3, -1, 0);
                am.adjustStreamVolume(3, 1, 0);
            }
            Intent i = new Intent(CarStatus.ACTION_REVSTATUS);
            i.putExtra(CarStatus.EXTRA_REVSTATUS, status > 0);
            this.mContext.sendBroadcastAsUser(i, UserHandle.CURRENT, null);
            if (status > 0) {
                McuService.getMcuService().ResetBacklightStatus();
            }
        }
    }

    private void sendRevSettings(int code, int value) {
        IBinder service = ServiceManager.getService("camerad");
        this.mContext.getContentResolver();
        Parcel _data = Parcel.obtain();
        try {
            try {
                _data.writeInterfaceToken("android.carstatus.CarStatusService");
                _data.writeInt(value);
                service.transact(code, _data, null, 1);
            } catch (Exception e) {
                Log.w("CarStatusService", "sendRevSettings error " + e);
            }
        } finally {
            _data.recycle();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendStatusToAutonavi(int extra_type, int extra_state) {
        Intent intent = new Intent();
        intent.setAction(AUTONAVI_BROADCAST_SEND);
        intent.setPackage(Util.DEFAULT_MAP);
        intent.putExtra("KEY_TYPE", KEY_TYPE_SEND_STATUS);
        intent.putExtra("EXTRA_HEADLIGHT_STATE", extra_state);
        intent.putExtra("EXTRA_TYPE", extra_type);
        intent.putExtra("EXTRA_STATE", extra_state);
        this.mContext.sendBroadcast(intent);
    }

    void sendRevSettings() {
        ContentResolver cr = this.mContext.getContentResolver();
        int flip = Settings.System.getInt(cr, HConfig.rev_videoflip, 0);
        sendRevSettings(1, flip | (Settings.System.getInt(cr, HConfig.front_videoflip, 1) << 8) | (Settings.System.getInt(cr, HConfig.avin_videoflip, 0) << 16));
        int debounce = Settings.System.getInt(cr, HConfig.rev_debounce, McuConstant.K_DVD);
        sendRevSettings(2, debounce);
        int showline = Settings.System.getInt(cr, HConfig.rev_static_line, 0);
        sendRevSettings(5, showline);
        int rcamera = Settings.System.getInt(cr, HConfig.right_SideCamera, 0);
        sendRevSettings(6, rcamera);
        int backCameraSelect = Settings.System.getInt(cr, HConfig.back_camera_select, 0);
        sendRevSettings(7, backCameraSelect);
    }

    @Override // android.carstatus.ICarStatus
    public int getAccStatus() {
        return this.mAcc;
    }

    private void setAccStatus(int acc) {
        if (!this.mBootCompleted || this.mReadacc == acc) {
            return;
        }
        if (this.mReadacc == -1) {
            publishAccStatus(acc);
        }
        this.mReadacc = acc;
        backlightOnoff(acc > 0 ? 65536 : 65537);
        this.mWorkingHandler.removeMessages(2);
        this.mWorkingHandler.removeMessages(5);
        boolean isReversing = this.mIsReversing > 0;
        boolean isCameraUiModeTop = SourceService.isCameraUiModeTop(this.mContext);
        if (acc == 0) {
            McuService.sendCmdCarStatus("mute=10001");
            ExtAudioMuxer.setMasterMute(true, 2);
            this.mWorkingHandler.removeMessages(11);
            Message msg = this.mWorkingHandler.obtainMessage(2, acc, 0);
            this.mWorkingHandler.sendMessageDelayed(msg, 2500L);
            if (!isReversing && isCameraUiModeTop) {
                McuService.sendCmdCarStatus("360module=0");
            }
            RadioService rs = RadioService.getInstance();
            int scantype = rs.getRadioInfo().mScanType;
            if (scantype > 0) {
                rs.stopScan();
                return;
            }
            return;
        }
        if (isReversing || isCameraUiModeTop) {
            McuService.sendCmdCarStatus("360module=1");
        }
        this.mWorkingHandler.sendEmptyMessageDelayed(11, 800L);
        Message msg2 = this.mWorkingHandler.obtainMessage(2, acc, 0);
        this.mWorkingHandler.sendMessageDelayed(msg2, 20L);
        this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), "android.policy:POWER");
        McuService.getMcuService().bootphase("completed");
        this.mCheckWakeLockTimes = 0;
        this.mAccoffMillis = -1;
        this.mWorkingHandler.removeMessages(7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAccStatusInner(int acc) {
        if (this.mAcc != acc) {
            this.mAcc = acc;
            Log.i("CarStatusService", "ACC State Changed to " + acc);
            Intent i = new Intent(CarStatus.ACTION_ACC);
            i.putExtra(CarStatus.EXTRA_ACC, acc == 1);
            i.addFlags(16);
            this.mContext.sendBroadcastAsUser(i, UserHandle.CURRENT, null);
            setPanelLed();
            if (acc == 1) {
                this.mWorkingHandler.sendEmptyMessageDelayed(10, 500L);
                this.mWorkingHandler.sendEmptyMessageDelayed(12, 1000L);
                RadioService.nativeSetPower(1);
                McuService.sendCmdCarStatus("usb5v=1");
                mountStorage();
            } else {
                ContentResolver cr = this.mContext.getContentResolver();
                int delay = Settings.System.getInt(cr, HConfig.power_down_afteraccoff, 30000);
                this.mWorkingHandler.sendEmptyMessageDelayed(5, delay);
            }
            if (TextUtils.isEmpty(Util.findPackageName(this.mContext, Util.FAN_PACKAGE_NAME, true))) {
                int mainBoardType = HConfig.instance().mainBoardCode();
                if (mainBoardType == 101) {
                    McuService.sendCmdCarStatus("fan_power=0");
                } else {
                    McuService.sendCmdCarStatus("amp_power=" + (this.mPowerOn & this.mAcc));
                }
            }
            publishAccStatus(acc);
        }
    }

    private void publishAccStatus(int acc) {
        this.mCarStatusMap.put("acc", Integer.toString(acc));
        StringBuilder builder = new StringBuilder(CarletterInterface.KEYCODE_KNOB_LEFT);
        for (String key : this.mCarStatusMap.keySet()) {
            String value = this.mCarStatusMap.get(key);
            builder.append(key);
            builder.append("=");
            builder.append(value);
            builder.append("\n");
        }
        try {
            FileUtils.stringToFile(this.mCarStatusKvFile, builder.toString());
            Os.chmod(this.mCarStatusKvFile.getAbsolutePath(), 420);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void backlightOnoff(int mask) {
        try {
            FileUtils.stringToFile("/sys/class/leds/lcd-backlight/mask", Integer.toHexString(mask));
        } catch (IOException ex) {
            Log.w("CarStatusService", ex.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goingToSleep() {
        Log.i("CarStatusService", "goingToSleep ...");
        unmountStorage();
        int reboot = 0;
        RadioService.nativeSetPower(0);
        McuService.getMcuService().bootphase("suspend");
        LinkManager.getInstance().sendAccState(0);
        this.mAccTimes++;
        ContentResolver cr = this.mContext.getContentResolver();
        int v = Settings.System.getInt(cr, HConfig.reboot_insteadof_suspend, 50);
        if (v > 0 && v <= this.mAccTimes) {
            reboot = 1;
        }
        McuService.getMcuService().setAccoffMode(reboot);
        this.mPowerManager.goToSleep(SystemClock.uptimeMillis());
        this.mWorkingHandler.sendEmptyMessageDelayed(7, 5000L);
        this.mAccoffMillis = (int) (SystemClock.uptimeMillis() / 1000);
        McuService.sendCmdCarStatus("usb5v=0");
    }

    private void unmountStorage() {
        StorageManager sm = (StorageManager) this.mContext.getSystemService("storage");
        for (VolumeInfo volume : sm.getVolumes()) {
            if (volume.getType() == 0) {
                String volumeId = volume.getId();
                Log.i("CarStatusService", "unmount " + volumeId);
                try {
                    sm.unmount(volumeId);
                } catch (Exception e) {
                    Log.w("CarStatusService", "unmount " + e.toString());
                }
            }
        }
    }

    private void mountStorage() {
        StorageManager sm = (StorageManager) this.mContext.getSystemService("storage");
        for (VolumeInfo volume : sm.getVolumes()) {
            if (volume.getType() == 0 && volume.getState() == 0) {
                String volumeId = volume.getId();
                Log.i("CarStatusService", "mount " + volumeId);
                try {
                    sm.mount(volumeId);
                } catch (Exception e) {
                    Log.w("CarStatusService", "unmount " + e.toString());
                }
            }
        }
    }

    private void onTelMute(int state) {
        if (this.mTelMute != state && this.mBootCompleted) {
            this.mTelMute = state;
            Log.i("CarStatusService", "TelMute State Changed to " + state);
            ContentResolver cr = this.mContext.getContentResolver();
            boolean rcamera = Settings.System.getInt(cr, HConfig.right_SideCamera, 0) > 0;
            Intent i = new Intent(CarStatus.ACTION_TELMUTE);
            i.putExtra(CarStatus.EXTRA_TELMUTE, state);
            if (rcamera) {
                i.setClassName("com.auto.hcamera", "com.auto.hcamera.BootCompletedReceiver");
            } else {
                i.setClassName(SourceService.ACTIVE_ONSTAR, "com.hcn.autocan.BootCompletedReceiver");
            }
            this.mContext.sendBroadcastAsUser(i, UserHandle.CURRENT, null);
        }
    }

    @Override // android.carstatus.ICarStatus
    public int getTelMuteSatus() {
        return this.mTelMute;
    }

    private void findAndkillbyUid(String v) {
        int uid1;
        Pattern p2 = Pattern.compile(".*WorkSource.(\\d+)");
        Matcher matcher = p2.matcher(v);
        if (matcher.find() && (uid1 = Integer.parseInt(matcher.group(1))) > 10000) {
            String packagename = this.mContext.getPackageManager().getNameForUid(uid1);
            Log.w("CarStatusService", "checkWakeLock while kill uid1 :" + uid1 + " packagename:" + packagename);
            if (packagename != null) {
                Util.forceStopPackage(packagename);
                Log.w("CarStatusService", "forceStopPackage:" + packagename);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkWakeLock(boolean clean) {
        String locks = this.mPowerManager.getWakeLockList();
        Log.w("CarStatusService", "Current wake_lock:\n" + locks);
        String[] vs = locks.split(System.getProperty("line.separator"));
        try {
            Pattern p = Pattern.compile(".*uid=(\\d+).*pid=(\\d+)");
            for (String v : vs) {
                Matcher matcher = p.matcher(v);
                if (!matcher.find()) {
                    if (clean) {
                        findAndkillbyUid(v);
                    }
                } else {
                    Integer.parseInt(matcher.group(1));
                    int pid = Integer.parseInt(matcher.group(2));
                    if (clean) {
                        if (pid != Process.myPid()) {
                            Log.w("CarStatusService", "killProcess pid:" + pid);
                            Process.killProcess(pid);
                        } else {
                            findAndkillbyUid(v);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("CarStatusService", e.toString() + Util.getLineInfo());
        }
        int timeout = SystemProperties.getInt("sys.reboot.timeout", 180);
        int now = (int) (SystemClock.uptimeMillis() / 1000);
        if (this.mAccoffMillis > 0 && now - this.mAccoffMillis >= timeout) {
            Log.e("CarStatusService", "Unable to suspend, have to reboot now");
            SystemProperties.set("sys.powerctl", "reboot");
        }
    }

    private class SettingsObserver extends ContentObserver {
        SettingsObserver() {
            super(new Handler());
            ContentResolver cr = CarStatusService.this.mContext.getContentResolver();
            cr.registerContentObserver(Settings.System.getUriFor("screen_brightness"), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.headlight_setting), false, this);
            cr.registerContentObserver(Settings.System.getUriFor("auto_time"), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(CarStatusService.TESTING_TAG), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.rev_videoflip), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.front_videoflip), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.avin_videoflip), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.rev_debounce), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.rev_static_line), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.right_SideCamera), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.back_camera_select), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.setting_skin_mode_enable), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.setting_day_night_mode_choose), false, this);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code duplicated, block: B:41:0x00a2  */
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            ContentResolver cr = CarStatusService.this.mContext.getContentResolver();
            super.onChange(selfChange);
            String key = uri.toString().replace("content://settings/system/", "");
            switch (key) {
                case "headlight_setting":
                    CarStatusService.this.mWorkingHandler.removeMessages(1);
                    CarStatusService.this.mWorkingHandler.sendEmptyMessage(1);
                    break;
                case "screen_brightness":
                    SaveBacklightAdjust();
                    break;
                case "CarStatusService.debug":
                    if (Settings.System.getInt(cr, CarStatusService.TESTING_TAG, 0) > 0) {
                        CarStatusService.this.enableDebugView();
                        break;
                    } else {
                        CarStatusService.this.disableDebugView();
                        break;
                    }
                    break;
                case "rev_videoflip":
                case "rev_debounce":
                case "rev_static_line":
                case "front_videoflip":
                case "avin_videoflip":
                case "hcnhw/right_SideCamera":
                case "back_camera_select_setting":
                    CarStatusService.this.sendRevSettings();
                    break;
                case "autosetting_skin_mode_enable":
                    CarStatusService.this.uiModeEnableChanged();
                    break;
                case "setting_day_night_mode_choose":
                    CarStatusService.this.uiModeTypeChanged();
                    break;
            }
        }

        private void SaveBacklightAdjust() {
            if (CarStatusService.this.mAcc != 0) {
                ContentResolver cr = CarStatusService.this.mContext.getContentResolver();
                int bright = Settings.System.getInt(cr, "screen_brightness", McuConstant.K_SWCSETTING);
                if (CarStatusService.this.mHeadLight > 0) {
                    Settings.System.putInt(cr, CarStatusService.BACKLIGHT_BRIGHT_N, bright);
                } else {
                    Settings.System.putInt(cr, CarStatusService.BACKLIGHT_BRIGHT_D, bright);
                }
            }
        }
    }

    private int getStatus(String status, String name) {
        try {
            Pattern p = Pattern.compile(String.format("\\b%s\\s*=\\s*(\\d+)", name));
            Matcher m = p.matcher(status);
            if (!m.find()) {
                return -1;
            }
            int st = Integer.parseInt(m.group(1));
            return st;
        } catch (Exception e) {
            Log.e("CarStatusService", e + status + Util.getLineInfo());
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readStatus() {
        byte[] mBuf = new byte[128];
        int count = 0;
        FileInputStream is = null;
        try {
            if (0 == 0) {
                is = new FileInputStream(this.CarStatusFile);
            }
            count = is.read(mBuf);
            is.close();
        } catch (Exception e) {
            Log.e("CarStatusService", e.toString() + Util.getLineInfo());
        }
        String str = new String(mBuf, 0, count);
        Log.i("CarStatusService", "Read Status:" + str);
        int st = getStatus(str, "acc");
        if (st >= 0) {
            setAccStatus(st);
        }
        int st2 = getStatus(str, "reversing");
        if (st2 >= 0) {
            setReversing(st2);
        }
        int st3 = getStatus(str, "parking");
        if (st3 >= 0) {
            setParkingStatus(st3);
        }
        int st4 = getStatus(str, "headlight");
        if (st4 >= 0) {
            setHeadLightStatus(st4);
        }
        int st5 = getStatus(str, "telmute");
        if (st5 >= 0) {
            onTelMute(st5);
        }
    }

    private StringBuilder readCpuFreq() {
        StringBuilder sb = new StringBuilder(1024);
        sb.append("CPU:");
        try {
            String freq = FileUtils.readTextFile(new File("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_cur_freq"), 16, null).trim();
            sb.append(Integer.parseInt(freq) / 1000);
            sb.append('M');
            sb.append(',');
            for (int i = 1; i < 8; i++) {
                File onlinefile = new File(String.format(Locale.US, "/sys/devices/system/cpu/cpu%d/online", Integer.valueOf(i)));
                if (!onlinefile.exists()) {
                    break;
                }
                String freqfile = String.format(Locale.US, "/sys/devices/system/cpu/cpu%d/cpufreq/cpuinfo_cur_freq", Integer.valueOf(i));
                String online = FileUtils.readTextFile(onlinefile, 12, null);
                if ("0".equals(online.trim())) {
                    sb.append("0,");
                } else {
                    String fr = FileUtils.readTextFile(new File(freqfile), 16, null).trim();
                    sb.append(Integer.parseInt(fr) / 1000);
                    sb.append('M');
                    sb.append(",");
                }
            }
        } catch (Exception e) {
            Log.w("CarStatusService", e.toString());
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDebugView() {
        File tempFile1 = new File("/proc/driver/thermal/tzcpu_read_temperature");
        if (this.mDebugView == null) {
            return;
        }
        StringBuilder sb = new StringBuilder(1024);
        sb.append((CharSequence) readCpuFreq());
        sb.append("\n");
        try {
            String tm = FileUtils.readTextFile(tempFile1, CarletterInterface.KEYCODE_KNOB_LEFT, null);
            Pattern p = Pattern.compile("current temp\\s*:\\s*(\\d+)");
            Matcher m = p.matcher(tm);
            if (m.find()) {
                sb.append("TEMP : ");
                sb.append(m.group(1));
                sb.append("\n");
            }
        } catch (Exception e) {
        }
        sb.append("B+  times : ");
        sb.append(SystemProperties.getInt("ro.system.power.times", -1));
        sb.append("\n");
        try {
            String carmsg = FileUtils.readTextFile(this.CarStatusFile, CarletterInterface.KEYCODE_KNOB_LEFT, null);
            int acctimes = getStatus(carmsg, "acc_times");
            int revtimes = getStatus(carmsg, "rev_times");
            sb.append("ACC times : ");
            sb.append(acctimes);
            sb.append("\n");
            sb.append("REV times : ");
            sb.append(revtimes);
            sb.append("\n");
        } catch (Exception e2) {
        }
        long elapsed = (SystemClock.elapsedRealtime() / 1000) / 60;
        sb.append("Uptime : ");
        sb.append(elapsed / 60);
        sb.append("h");
        sb.append(elapsed % 60);
        sb.append("mins");
        sb.append("\n");
        sb.append("Battery : ");
        sb.append(String.format("%.2fV", Float.valueOf(McuService.getMcuService().getBattery())));
        sb.append("\n");
        try {
            if (this.LifeTimeFile.exists()) {
                String lifeTime = FileUtils.readTextFile(this.LifeTimeFile, 16, null).trim();
                String date = FileUtils.readTextFile(this.DateFile, 16, null).trim();
                sb.append("LT/DATE: ");
                sb.append(lifeTime);
                sb.append(" ");
                sb.append(date);
                sb.append("\n");
            } else {
                sb.append("LT/DATE: unsupport\n");
            }
        } catch (Exception e3) {
        }
        SpannableString msp = new SpannableString(sb);
        msp.setSpan(new ForegroundColorSpan(Color.argb(180, McuConstant.K_SWCSETTING, 187, 153)), 0, sb.length(), 33);
        msp.setSpan(new BackgroundColorSpan(Color.argb(128, 128, 128, 128)), 0, sb.length(), 33);
        this.mDebugView.setText(msp);
        this.mWorkingHandler.sendEmptyMessageDelayed(3, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableDebugView() {
        if (this.mDebugView == null) {
            this.mDebugView = new TextView(this.mContext);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams(-1, -1);
            lp.type = 2015;
            lp.flags = 1304;
            if (ActivityManager.isHighEndGfx()) {
                lp.flags |= 16777216;
                lp.privateFlags |= 2;
            }
            lp.format = -3;
            lp.setTitle("DebugView");
            WindowManager wm = (WindowManager) this.mContext.getSystemService("window");
            lp.inputFeatures |= 2;
            this.mDebugView.setTextSize(25.0f * this.mContext.getResources().getDisplayMetrics().density);
            this.mDebugView.setSingleLine(false);
            wm.addView(this.mDebugView, lp);
        }
        this.mWorkingHandler.sendEmptyMessage(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disableDebugView() {
        if (this.mDebugView != null) {
            WindowManager wm = (WindowManager) this.mContext.getSystemService("window");
            wm.removeView(this.mDebugView);
            this.mDebugView = null;
        }
        this.mWorkingHandler.removeMessages(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDefaultVolume() {
        AudioManager am = (AudioManager) this.mContext.getSystemService("audio");
        int index = am.getStreamVolume(3);
        ContentResolver cr = this.mContext.getContentResolver();
        int defVolume = Settings.System.getInt(cr, HConfig.default_audio_volume, 11);
        int index2 = (index == 0 || index > defVolume) ? defVolume : index;
        Log.i("CarStatusService", "set default volume index to " + index2);
        ExtAudioMuxer.setMasterVolumeIndex(3, index2, 8, am.getMode());
        am.setStreamVolume(3, index2, 0);
        int index3 = am.getStreamVolume(2);
        am.setStreamVolume(2, (index3 == 0 || index3 > defVolume) ? defVolume : index3, 0);
        int index4 = am.getStreamVolume(4);
        am.setStreamVolume(4, (index4 == 0 || index4 > defVolume) ? defVolume : index4, 0);
        int index5 = am.getStreamVolume(0);
        am.setStreamVolume(0, (index5 == 0 || index5 > defVolume) ? defVolume : index5, 0);
    }

    @Override // android.carstatus.ICarStatus
    public void setLed(int index, int red, int green, int blue) {
        String str;
        int color = red < 0 ? -1 : (red << 16) | (green << 8) | blue;
        if (index == 0) {
            this.mLedColor = color;
        }
        if (color < 0) {
            str = String.format(Locale.US, "set_autorun=%d\n", Integer.valueOf(index));
        } else {
            str = String.format(Locale.US, "set_color=%d %d %d %d\n", Integer.valueOf(index), Integer.valueOf(red), Integer.valueOf(green), Integer.valueOf(blue));
        }
        try {
            McuService.sendCmdCarStatus("pled=1");
            FileUtils.stringToFile("/dev/socket/teststub", str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mWorkingHandler.removeMessages(9);
        Message msg = this.mWorkingHandler.obtainMessage(9);
        this.mWorkingHandler.sendMessageDelayed(msg, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveLedSettings() {
        ContentResolver cr = this.mContext.getContentResolver();
        Settings.System.putInt(cr, HConfig.pannel_led_color, this.mLedColor);
    }

    public void onFakePowerState(int on) {
        this.mPowerOn = on;
        if (TextUtils.isEmpty(Util.findPackageName(this.mContext, Util.FAN_PACKAGE_NAME, true))) {
            int mainBoardType = HConfig.instance().mainBoardCode();
            if (mainBoardType == 101) {
                McuService.sendCmdCarStatus("fan_power=0");
            } else {
                McuService.sendCmdCarStatus("amp_power=" + (this.mAcc & on));
            }
        }
        setPanelLed();
        ContentResolver cr = this.mContext.getContentResolver();
        if (on == 0 && this.mAcc > 0 && Settings.System.getInt(cr, HConfig.setting_fakepower_defaultvolume_enable, 0) > 0) {
            setDefaultVolume();
        }
        RadioService rs = RadioService.getInstance();
        int scantype = rs.getRadioInfo().mScanType;
        if (scantype > 0) {
            rs.stopScan();
        }
    }

    private void setPanelLed() {
        String str;
        boolean on = this.mAcc > 0;
        if (on & (!this.mHeadlightEnable ? this.mPowerOn <= 0 : this.mHeadLight <= 0)) {
            if (this.mLedColor < 0) {
                str = String.format(Locale.US, "set_autorun=1\n", new Object[0]);
            } else {
                int r = (this.mLedColor >> 16) & McuConstant.K_SWCSETTING;
                int g = (this.mLedColor >> 8) & McuConstant.K_SWCSETTING;
                int b = this.mLedColor & McuConstant.K_SWCSETTING;
                str = String.format(Locale.US, "set_color=0 %d %d %d\n", Integer.valueOf(r), Integer.valueOf(g), Integer.valueOf(b));
            }
            try {
                FileUtils.stringToFile("/dev/socket/teststub", str);
            } catch (IOException e) {
                e.printStackTrace();
            }
            McuService.sendCmdCarStatus("pled=1");
            return;
        }
        McuService.sendCmdCarStatus("pled=0");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAllServices() {
        if (this.mAcc == 0) {
            Log.i("CarStatusService", "startAllServices return mAcc" + this.mAcc);
            return;
        }
        try {
            int N = this.SERVICE_ON_ACC.length;
            for (int i = 0; i < N; i += 2) {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.setComponent(new ComponentName(this.SERVICE_ON_ACC[i], this.SERVICE_ON_ACC[i + 1]));
                Log.i("CarStatusService", "start service:" + this.SERVICE_ON_ACC[i] + "/" + this.SERVICE_ON_ACC[i + 1]);
                this.mContext.startForegroundServiceAsUser(intent, UserHandle.CURRENT);
            }
            LinkManager.getInstance().startLinkService();
        } catch (Throwable e) {
            Log.e("CarStatusService", "starting servcies", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startNavi() {
        if (this.mAcc == 0) {
            Log.i("CarStatusService", "startNavi return, mAcc" + this.mAcc);
            return;
        }
        try {
            boolean mStartup = Settings.System.getInt(this.mContext.getContentResolver(), HConfig.startup_navigator, 0) > 0;
            if (mStartup) {
                Util.launchNavigation(this.mContext);
            }
        } catch (Throwable e) {
            Log.e("CarStatusService", "starting activity", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uiModeEnableChanged() {
        ContentResolver cr = this.mContext.getContentResolver();
        int uiModeEnable = Settings.System.getInt(cr, HConfig.setting_skin_mode_enable, 0);
        int i = 1;
        if (uiModeEnable != 1) {
            this.mUiModeManager.setNightMode(1);
        }
        int uiModeType = Settings.System.getInt(cr, HConfig.setting_day_night_mode_choose, 0);
        switch (uiModeType) {
            case 0:
                this.mUiModeManager.setNightMode(1);
                break;
            case 1:
                this.mUiModeManager.setNightMode(2);
                break;
            default:
                UiModeManager uiModeManager = this.mUiModeManager;
                if (this.mHeadLight > 0) {
                    i = 2;
                }
                uiModeManager.setNightMode(i);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uiModeTypeChanged() {
        ContentResolver cr = this.mContext.getContentResolver();
        int uiModeEnable = Settings.System.getInt(cr, HConfig.setting_skin_mode_enable, 0);
        int i = 1;
        if (uiModeEnable != 1) {
            Log.e("CarStatusService", "uiModeTypeChanged, no support uiMode.");
        }
        int uiModeType = Settings.System.getInt(cr, HConfig.setting_day_night_mode_choose, 0);
        switch (uiModeType) {
            case 0:
                this.mUiModeManager.setNightMode(1);
                break;
            case 1:
                this.mUiModeManager.setNightMode(2);
                break;
            default:
                UiModeManager uiModeManager = this.mUiModeManager;
                if (this.mHeadLight > 0) {
                    i = 2;
                }
                uiModeManager.setNightMode(i);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMsgHeadlightChanged(int headLight) {
        this.mCarStatusMap.put("headlight", Integer.toString(headLight));
        StringBuilder builder = new StringBuilder(CarletterInterface.KEYCODE_KNOB_LEFT);
        for (String key : this.mCarStatusMap.keySet()) {
            String value = this.mCarStatusMap.get(key);
            builder.append(key);
            builder.append("=");
            builder.append(value);
            builder.append("\n");
        }
        try {
            FileUtils.stringToFile(this.mCarStatusKvFile, builder.toString());
            Os.chmod(this.mCarStatusKvFile.getAbsolutePath(), 420);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ContentResolver cr = this.mContext.getContentResolver();
        int uiModeEnable = Settings.System.getInt(cr, HConfig.setting_skin_mode_enable, 0);
        int uiModeType = Settings.System.getInt(cr, HConfig.setting_day_night_mode_choose, 0);
        if (uiModeEnable == 1) {
            if (uiModeType == 2) {
                this.mUiModeManager.setNightMode(headLight <= 0 ? 1 : 2);
            }
        }
        LinkManager.getInstance().sendDayNightMode(headLight <= 0);
        sendStatusToAutonavi(0, headLight > 0 ? 0 : 1);
    }

    private void setCustDefaultVolume(int defVolume) {
        Log.i("CarStatusService", "set cust default volume index to " + defVolume);
        AudioManager am = (AudioManager) this.mContext.getSystemService("audio");
        ExtAudioMuxer.setMasterVolumeIndex(3, defVolume, 8, am.getMode());
        am.setStreamVolume(3, defVolume, 0);
    }

    public void systemReady() {
        ContentResolver cr = this.mContext.getContentResolver();
        if (Settings.System.getInt(cr, "hcn.auto.clean.launcher", 0) == 1) {
            int defVolume = Settings.System.getInt(this.mContext.getContentResolver(), HConfig.default_audio_volume, 11);
            setCustDefaultVolume(defVolume);
        } else {
            AudioManager am = (AudioManager) this.mContext.getSystemService("audio");
            int index = am.getStreamVolume(3);
            Log.i("CarStatusService", "set default volume index to " + index);
            ExtAudioMuxer.setMasterVolumeIndex(3, index, 8, am.getMode());
        }
        sendRevSettings();
    }

    public void initLogbyEmmcLifeTime() {
        int forceEnableLogcat = SystemProperties.getInt("ro.sys.force.enablelogcat", 0);
        if (forceEnableLogcat < 1) {
            try {
                if (this.LifeTimeFile.exists()) {
                    String lifeTime = FileUtils.readTextFile(this.LifeTimeFile, 10, null).trim();
                    String[] hexNumbers = lifeTime.split(" ");
                    for (String str : hexNumbers) {
                        int lifeTimeValue = Integer.decode(str).intValue();
                        if (lifeTimeValue >= 7) {
                            SystemProperties.set("ctl.stop", "logcat");
                            SystemProperties.set("ctl.stop", "kmesg");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
