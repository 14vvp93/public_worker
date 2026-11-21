package android.link;

import android.Configures.HConfig;
import android.Configures.HConstant;
import android.carsource.McuService;
import android.carstatus.CarStatusService;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.sourceservice.SourceService;
import android.util.Log;
import android.utilities.Util;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class CarletterInterface implements LinkManager.LinkInterface {
    public static final String ACC_STATE = "acc_state";
    public static final String ALLOW_AUTO_BOOT_ANDROID_AUTO = "allowAutoBootAndroidAuto";
    public static final String ALLOW_AUTO_BOOT_CARPLAY = "allowAutoBootCarplay";
    public static final String ALLOW_AUTO_START_CARPLAY_BY_BT = "allowAutoStartCarplayByBT";
    public static final String CL_CARLETTER_CONNECT_MODE = "carletter_connect_mode";
    public static final String CL_CARLETTER_CONNECT_STATE = "carletter_connect_state";
    public static final String CL_CARLETTER_MEDIA_STATE = "carletter_media_state";
    public static final String CL_CARLETTER_NAVI_STATE = "carletter_navi_state";
    public static final String CL_CARLETTER_RECORDER_STATE = "carletter_recorder_state";
    public static final String CL_CARLETTER_RUNNING_STATE = "carletter_running_state";
    public static final String CL_CARLETTER_TELEPHONE_STATE = "carletter_telephone_state";
    public static final String CL_LINK_CONTROL = "carletter_link_control";
    public static final String CL_NIGHT_MODE = "carletter_night_mode";
    public static final String CL_RESERVE_STATE = "carletter_reserve_state";
    public static final int KEYCODE_BACK = 289;
    public static final int KEYCODE_DOWN = 261;
    public static final int KEYCODE_ENTER = 288;
    public static final int KEYCODE_HOME = 290;
    public static final int KEYCODE_KNOB_LEFT = 256;
    public static final int KEYCODE_KNOB_RIGHT = 257;
    public static final int KEYCODE_LEFT = 258;
    public static final int KEYCODE_MAP = 593;
    public static final int KEYCODE_MEDIA_NEXT = 339;
    public static final int KEYCODE_MEDIA_PAUSE = 337;
    public static final int KEYCODE_MEDIA_PLAY = 336;
    public static final int KEYCODE_MEDIA_PREV = 340;
    public static final int KEYCODE_MEDIA_TOGGLE_PLAY_PAUSE = 338;
    public static final int KEYCODE_MUSIC = 594;
    public static final int KEYCODE_PHONE = 595;
    public static final int KEYCODE_RIGHT = 259;
    public static final int KEYCODE_TEL_ACCEPT = 512;
    public static final int KEYCODE_TEL_MUTE_MIC = 515;
    public static final int KEYCODE_TEL_REJECT = 513;
    public static final int KEYCODE_TEL_TOGGLE_ACCEPT_REJECT = 514;
    public static final int KEYCODE_UNKNOWN = 0;
    public static final int KEYCODE_UP = 260;
    public static final int KEYCODE_VOICE = 592;
    private static final int MSG_KILL_SERVICE = 2;
    private static final int MSG_LINK_INFO_CHANGE = 3;
    private static final int MSG_SEND_CONNECT_STATE = 0;
    private static final int MSG_START_SERVICE = 1;
    private static final int MSG_UPDATE_STATE_AFTER_RUNAPP = 4;
    private static final String TAG = "LinkManager.CarletterInterface";
    private CarLetterInfo mCarletterInfo;
    private Context mContext;
    private SettingsObserver mSettingsObserver;
    private String mUsbPath = "/sys/devices/platform/extcon-gpio/mode_change";
    private boolean mIsCarPlayPhoneCall = false;
    private boolean mIsZlinkMirrorLink = false;
    private boolean mZlinkInfoAndroidAutoCall = false;
    public static String FRAGMENT_TYPE_TAG = "FragmentTypeTag";
    public static String PACKAGE_NAME = SourceService.ACTIVE_LT_CARPLAY;
    public static String CLASS_NAME = "com.carletter.car.ui.CarletterActivity";
    public static String SERVICE_NAME = "com.carletter.car.service.CarletterService";
    private int mPreConnectMode = 0;
    Handler myHandler = new Handler() { // from class: android.link.CarletterInterface.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) throws Throwable {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    CarletterInterface.this.handlerConnectChang();
                    break;
                case 1:
                    CarletterInterface.this.startCarletterService(CarletterInterface.this.mContext);
                    break;
                case 2:
                    CarletterInterface.this.stopCarletter();
                    break;
                case 3:
                    CarletterInterface.this.onLinkInfoChange();
                    break;
                case 4:
                    CarletterInterface.this.updateSystemStateAfterRunApp();
                    break;
            }
        }
    };

    public interface CarletterConnectMode {
        public static final int AIRPLAY = 1;
        public static final int ANDROID_AUTO = 3;
        public static final int ANDROID_LINK = 4;
        public static final int CARLIFE = 5;
        public static final int CARPLAY = 2;
        public static final int DLNA = 6;
        public static final int HICAR = 7;
        public static final int UNKNOWN = 0;
    }

    public interface CarletterConnectState {
        public static final int CONNECTED = 1;
        public static final int DISCONNECTED = 0;
    }

    public interface CarletterMediaState {
        public static final int START = 1;
        public static final int STOP = 0;
    }

    public interface CarletterNaviState {
        public static final int START = 1;
        public static final int STOP = 0;
    }

    public interface CarletterRecorderState {
        public static final int START = 1;
        public static final int STOP = 0;
    }

    public interface CarletterRunningState {
        public static final int BACKGROUND = 1;
        public static final int FOREGROUND = 0;
    }

    public interface CarletterTelephoneState {
        public static final int ALERT = 1;
        public static final int OFF = 0;
        public static final int TALKING = 2;
    }

    public interface FragmentType {
        public static final int AIRPLAY = 1;
        public static final int ANDROID_AUTO = 3;
        public static final int ANDROID_LINK = 4;
        public static final int CARLIFE = 5;
        public static final int CARPLAY = 2;
        public static final int DLNA = 6;
        public static final int HICAR = 7;
    }

    public CarletterInterface(Context context) {
        this.mContext = context;
        Log.i(TAG, "init");
        this.mCarletterInfo = new CarLetterInfo();
        this.mCarletterInfo.mConnectMode = 0;
        this.mCarletterInfo.mConnectState = 0;
        this.mCarletterInfo.mRunningState = 0;
        this.mCarletterInfo.mMediaState = 0;
        this.mCarletterInfo.mRecorderState = 0;
        this.mCarletterInfo.mTelephoneState = 0;
        this.mCarletterInfo.mNaviState = 0;
        this.mSettingsObserver = new SettingsObserver(context, this.myHandler);
        this.mSettingsObserver.observer();
        SystemProperties.set("persist.sys.linktype", "carletter");
        SystemProperties.set("service.ltlink.enable", "1");
        Settings.System.putInt(this.mContext.getContentResolver(), ACC_STATE, CarStatusService.getCarStatusService().getAccStatus());
        Settings.System.putInt(this.mContext.getContentResolver(), CL_RESERVE_STATE, CarStatusService.getCarStatusService().getReversing());
    }

    public CarLetterInfo getCarLetterInfo() {
        return this.mCarletterInfo;
    }

    @Override // android.link.LinkManager.LinkInterface
    public void sendReverseState(int reverse) {
        sendReverseState(this.mContext, reverse);
    }

    @Override // android.link.LinkManager.LinkInterface
    public void sendAccState(int acc) {
        sendAccState(this.mContext, acc);
    }

    @Override // android.link.LinkManager.LinkInterface
    public void startLinkService() {
        this.myHandler.removeMessages(1);
        this.myHandler.removeMessages(2);
        this.myHandler.sendEmptyMessageDelayed(1, 3000L);
    }

    @Override // android.link.LinkManager.LinkInterface
    public boolean sendKeyButton(int key, int action) {
        if (key == 231) {
            if (action == 1) {
                linkControl(this.mContext, KEYCODE_VOICE);
            }
            return true;
        }
        if (key == 302) {
            if (action == 1) {
                linkControl(this.mContext, KEYCODE_TEL_ACCEPT);
            }
            return true;
        }
        if (key != 307) {
            return false;
        }
        if (action == 1) {
            linkControl(this.mContext, KEYCODE_TEL_REJECT);
        }
        return true;
    }

    @Override // android.link.LinkManager.LinkInterface
    public boolean sendMediaButton(int key, int action) {
        return false;
    }

    @Override // android.link.LinkManager.LinkInterface
    public boolean activityDied(String pkg) {
        if (SourceService.ACTIVE_LT_CARPLAY.equals(pkg)) {
            Log.i(TAG, "activityDied pkg" + pkg);
            sendDisConnectBroadcast(this.mContext);
            return true;
        }
        return false;
    }

    private void updateSystemStateAfterConnect() {
        sendDayNightMode(!CarStatusService.getCarStatusService().getHeadLightStatus());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSystemStateAfterRunApp() {
        sendReverseState(CarStatusService.getCarStatusService().getReversing());
    }

    @Override // android.link.LinkManager.LinkInterface
    public void sendDayNightMode(boolean z) {
        sendNightMode(this.mContext, !z ? 1 : 0);
    }

    public String getFragmentTypeByInt(int index) {
        switch (index) {
            case 1:
                return "AIRPLAY";
            case 2:
                return "CARPLAY";
            case 3:
                return "ANDROID_AUTO";
            case 4:
                return "ANDROID_LINK";
            case 5:
                return "CARLIFE";
            case 6:
                return "DLNA";
            case 7:
                return "HICAR";
            default:
                return "";
        }
    }

    public void startCarLetter(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME);
        try {
            context.startActivityAsUser(intent, UserHandle.CURRENT);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, e.toString());
        } catch (NullPointerException e2) {
            Log.e(TAG, e2.toString());
        }
    }

    public void startCarplay(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        ComponentName componentName = new ComponentName(PACKAGE_NAME, CLASS_NAME);
        intent.setComponent(componentName);
        intent.putExtra(FRAGMENT_TYPE_TAG, 2);
        context.startActivity(intent);
    }

    public void startCarlife(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        ComponentName componentName = new ComponentName(PACKAGE_NAME, CLASS_NAME);
        intent.setComponent(componentName);
        intent.putExtra(FRAGMENT_TYPE_TAG, 5);
        context.startActivity(intent);
    }

    public void startAndroidAuto(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        ComponentName componentName = new ComponentName(PACKAGE_NAME, CLASS_NAME);
        intent.setComponent(componentName);
        intent.putExtra(FRAGMENT_TYPE_TAG, 3);
        context.startActivity(intent);
    }

    public void startAirplay(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        ComponentName componentName = new ComponentName(PACKAGE_NAME, CLASS_NAME);
        intent.setComponent(componentName);
        intent.putExtra(FRAGMENT_TYPE_TAG, 1);
        context.startActivity(intent);
    }

    public void startDLNA(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        ComponentName componentName = new ComponentName(PACKAGE_NAME, CLASS_NAME);
        intent.setComponent(componentName);
        intent.putExtra(FRAGMENT_TYPE_TAG, 6);
        context.startActivity(intent);
    }

    public void startCarletterService(Context context) {
        Log.i(TAG, "startCarletterService");
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(PACKAGE_NAME, SERVICE_NAME);
        intent.setComponent(componentName);
        context.startService(intent);
        this.myHandler.removeMessages(4);
        this.myHandler.sendEmptyMessageDelayed(4, 2000L);
    }

    public void linkControl(Context context, int keycode) {
        Settings.System.putInt(context.getContentResolver(), CL_LINK_CONTROL, keycode);
        Log.i(TAG, "linkControl keycode:" + keycode);
        context.getContentResolver().notifyChange(Settings.System.getUriFor(CL_LINK_CONTROL), null);
    }

    public void allowStartToForeground(Context context, int value) {
        Settings.System.putInt(context.getContentResolver(), ALLOW_AUTO_START_CARPLAY_BY_BT, value);
        context.getContentResolver().notifyChange(Settings.System.getUriFor(ALLOW_AUTO_START_CARPLAY_BY_BT), null);
    }

    public boolean isAllowAutoStartCarplayByBT(Context context) {
        int value = Settings.System.getInt(context.getContentResolver(), ALLOW_AUTO_START_CARPLAY_BY_BT, 0);
        return value < 1;
    }

    public void sendReverseState(Context context, int value) {
        ContentResolver cr = context.getContentResolver();
        if (Settings.System.getInt(cr, HConfig.audio_rev_volume, 0) <= 0) {
            Settings.System.putInt(context.getContentResolver(), CL_RESERVE_STATE, value);
            context.getContentResolver().notifyChange(Settings.System.getUriFor(CL_RESERVE_STATE), null);
        }
    }

    public boolean isNightMode(Context context) {
        int value = Settings.System.getInt(context.getContentResolver(), CL_NIGHT_MODE, 0);
        return value == 1;
    }

    public void sendNightMode(Context context, int value) {
        Settings.System.putInt(context.getContentResolver(), CL_NIGHT_MODE, value);
        context.getContentResolver().notifyChange(Settings.System.getUriFor(CL_NIGHT_MODE), null);
    }

    public void allowAutoBootCarplay(Context context, int value) {
        Settings.System.putInt(context.getContentResolver(), ALLOW_AUTO_BOOT_CARPLAY, value);
        context.getContentResolver().notifyChange(Settings.System.getUriFor(ALLOW_AUTO_BOOT_CARPLAY), null);
    }

    public boolean isAllowAutoBootCarplay(Context context) {
        int value = Settings.System.getInt(context.getContentResolver(), ALLOW_AUTO_BOOT_CARPLAY, 0);
        return value < 1;
    }

    public void allowAutoBootAndroidAuto(Context context, int value) {
        Settings.System.putInt(context.getContentResolver(), ALLOW_AUTO_BOOT_ANDROID_AUTO, value);
        context.getContentResolver().notifyChange(Settings.System.getUriFor(ALLOW_AUTO_BOOT_ANDROID_AUTO), null);
    }

    public boolean isAllowAutoBootAndroidAuto(Context context) {
        int value = Settings.System.getInt(context.getContentResolver(), ALLOW_AUTO_BOOT_ANDROID_AUTO, 0);
        return value < 1;
    }

    public void sendAccState(Context context, int state) {
        Settings.System.putInt(context.getContentResolver(), ACC_STATE, state);
        context.getContentResolver().notifyChange(Settings.System.getUriFor(ACC_STATE), null);
        Log.i(TAG, "sendAccState state:" + state);
        if (state != 0) {
            this.myHandler.removeMessages(2);
            int value = SystemProperties.getInt("service.ltlink.enable", 0);
            if (value == 0) {
                SystemProperties.set("service.ltlink.enable", "1");
            }
            this.myHandler.removeMessages(1);
            this.myHandler.sendEmptyMessageDelayed(1, 3000L);
            return;
        }
        this.myHandler.removeMessages(1);
        this.myHandler.removeMessages(2);
        this.myHandler.sendEmptyMessageDelayed(2, 500L);
    }

    public class CarLetterInfo {
        int mConnectMode;
        int mConnectState;
        int mMediaState;
        int mNaviState;
        int mRecorderState;
        int mRunningState;
        int mTelephoneState;

        public CarLetterInfo() {
        }

        public String toString() {
            return "CarletterInfo{mConnectMode='" + CarletterInterface.this.getCarletterConnectModeToStr(this.mConnectMode) + "', mConnectState='" + CarletterInterface.this.getCarletterConnectStateToStr(this.mConnectState) + "', mRunningState='" + CarletterInterface.this.getCarletterRunningStateToStr(this.mRunningState) + "', mMediaState=" + CarletterInterface.this.getCarletterMediaStateToStr(this.mMediaState) + ", mRecorderState=" + CarletterInterface.this.getCarletterRecorderStateToStr(this.mRecorderState) + ", mTelephoneState=" + CarletterInterface.this.getCarletterTelephoneStateToStr(this.mTelephoneState) + ", mNaviState=" + CarletterInterface.this.getCarletterNaviStateToStr(this.mNaviState) + '}';
        }
    }

    public String getCarletterConnectModeToStr(int index) {
        switch (index) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "AIRPLAY";
            case 2:
                return "CARPLAY";
            case 3:
                return "ANDROID_AUTO";
            case 4:
                return "ANDROID_LINK";
            case 5:
                return "CARLIFE";
            case 6:
                return "DLNA";
            case 7:
                return "HICAR";
            default:
                return "";
        }
    }

    public String getCarletterConnectStateToStr(int index) {
        switch (index) {
            case 0:
                return "DISCONNECTED";
            case 1:
                return "CONNECTED";
            default:
                return "";
        }
    }

    public String getCarletterRunningStateToStr(int index) {
        switch (index) {
            case 0:
                return "FOREGROUND";
            case 1:
                return "BACKGROUND";
            default:
                return "";
        }
    }

    public String getCarletterMediaStateToStr(int index) {
        switch (index) {
            case 0:
                return "STOP";
            case 1:
                return "START";
            default:
                return "";
        }
    }

    public String getCarletterRecorderStateToStr(int index) {
        switch (index) {
            case 0:
                return "STOP";
            case 1:
                return "START";
            default:
                return "";
        }
    }

    public String getCarletterTelephoneStateToStr(int index) {
        switch (index) {
            case 0:
                return "OFF";
            case 1:
                return "ALERT";
            case 2:
                return "TALKING";
            default:
                return "";
        }
    }

    public String getCarletterNaviStateToStr(int index) {
        switch (index) {
            case 0:
                return "STOP";
            case 1:
                return "START";
            default:
                return "";
        }
    }

    @Override // android.link.LinkManager.LinkInterface
    public void sendDisconnect() {
        sendDisConnectBroadcast(this.mContext);
    }

    @Override // android.link.LinkManager.LinkInterface
    public boolean isNeedSendKeyToLink() {
        int callstate = SystemProperties.getInt("sys.nonsystem.callstate", 0);
        return this.mZlinkInfoAndroidAutoCall || callstate == 1;
    }

    @Override // android.link.LinkManager.LinkInterface
    public boolean changeSource(String preApp, String curApp) {
        if ((SourceService.ACTIVE_LT_CARPLAY.equals(preApp) || "/system/bin//LinkSDKServer".equals(preApp)) && !SourceService.ACTIVE_LT_CARPLAY.equals(curApp) && !"/system/bin//LinkSDKServer".equals(curApp)) {
            linkControl(this.mContext, 337);
            return false;
        }
        return false;
    }

    public void sendDisConnectBroadcast(Context content) {
        boolean needSend = true;
        SystemProperties.set("sys.nonsystem.callstate", "0");
        Intent intentDisconnect = new Intent("com.hcn.link");
        intentDisconnect.putExtra("status", "DISCONNECTED");
        if (this.mCarletterInfo.mConnectMode == 2) {
            intentDisconnect.putExtra("type", CarZlinkInterface.DATA_PAGE_CARPLAY);
            intentDisconnect.putExtra("bluetooth", "openBt");
            int i = this.mCarletterInfo.mConnectState;
            Log.i(TAG, "sendDisConnectBroadcast CARPLAY disconnect");
        } else if (this.mCarletterInfo.mConnectMode == 3) {
            intentDisconnect.putExtra("type", "androidauto");
            intentDisconnect.putExtra("bluetooth", "resumeA2dp");
            Log.i(TAG, "sendDisConnectBroadcast ANDROID_AUTO disconnect");
        } else if (this.mCarletterInfo.mConnectMode == 7) {
            intentDisconnect.putExtra("type", CarZlinkInterface.DATA_PAGE_HICAR);
            intentDisconnect.putExtra("bluetooth", "resumeA2dp");
            Log.i(TAG, "sendDisConnectBroadcast hicar disconnect");
        } else if (this.mCarletterInfo.mConnectMode == 1) {
            intentDisconnect.putExtra("type", "airplay");
            intentDisconnect.putExtra("bluetooth", "NULL");
            Log.i(TAG, "handlerConnectChang airplay_wireless CONNECTED");
        } else if (this.mCarletterInfo.mConnectMode == 5) {
            intentDisconnect.putExtra("type", CarZlinkInterface.DATA_PAGE_CARLIFE);
            intentDisconnect.putExtra("bluetooth", "NULL");
            Log.i(TAG, "handlerConnectChang carlife_wireless CONNECTED");
        } else if (this.mCarletterInfo.mConnectMode == 6) {
            intentDisconnect.putExtra("type", CarZlinkInterface.DATA_PAGE_DLNA);
            intentDisconnect.putExtra("bluetooth", "NULL");
        } else if (this.mCarletterInfo.mConnectMode == 4) {
            intentDisconnect.putExtra("type", "androidMirror");
            intentDisconnect.putExtra("bluetooth", "NULL");
        } else {
            needSend = false;
        }
        if (needSend) {
            content.sendBroadcastAsUser(intentDisconnect, UserHandle.CURRENT, null);
        }
    }

    public class SettingsObserver extends ContentObserver {
        private Context mContext;

        public SettingsObserver(Context context, Handler handler) {
            super(handler);
            this.mContext = context;
        }

        public void observer() {
            ContentResolver resolver = this.mContext.getContentResolver();
            resolver.registerContentObserver(Settings.System.getUriFor(CarletterInterface.CL_CARLETTER_CONNECT_MODE), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(CarletterInterface.CL_CARLETTER_CONNECT_STATE), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(CarletterInterface.CL_CARLETTER_RUNNING_STATE), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(CarletterInterface.CL_CARLETTER_MEDIA_STATE), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(CarletterInterface.CL_CARLETTER_RECORDER_STATE), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(CarletterInterface.CL_CARLETTER_TELEPHONE_STATE), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(CarletterInterface.CL_CARLETTER_NAVI_STATE), false, this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            boolean needSendMsg = false;
            int value = Settings.System.getInt(this.mContext.getContentResolver(), CarletterInterface.CL_CARLETTER_CONNECT_MODE, 0);
            if (CarletterInterface.this.mCarletterInfo.mConnectMode != value) {
                CarletterInterface.this.mCarletterInfo.mConnectMode = value;
            }
            int value2 = Settings.System.getInt(this.mContext.getContentResolver(), CarletterInterface.CL_CARLETTER_CONNECT_STATE, 0);
            if (CarletterInterface.this.mCarletterInfo.mConnectState != value2) {
                CarletterInterface.this.mCarletterInfo.mConnectState = value2;
                CarletterInterface.this.myHandler.sendEmptyMessage(0);
                needSendMsg = true;
            }
            int value3 = Settings.System.getInt(this.mContext.getContentResolver(), CarletterInterface.CL_CARLETTER_RUNNING_STATE, 0);
            if (CarletterInterface.this.mCarletterInfo.mRunningState != value3) {
                CarletterInterface.this.mCarletterInfo.mRunningState = value3;
                needSendMsg = true;
            }
            int value4 = Settings.System.getInt(this.mContext.getContentResolver(), CarletterInterface.CL_CARLETTER_MEDIA_STATE, 0);
            if (CarletterInterface.this.mCarletterInfo.mMediaState != value4) {
                CarletterInterface.this.mCarletterInfo.mMediaState = value4;
            }
            int value5 = Settings.System.getInt(this.mContext.getContentResolver(), CarletterInterface.CL_CARLETTER_RECORDER_STATE, 0);
            if (CarletterInterface.this.mCarletterInfo.mRecorderState != value5) {
                CarletterInterface.this.mCarletterInfo.mRecorderState = value5;
                needSendMsg = true;
            }
            int value6 = Settings.System.getInt(this.mContext.getContentResolver(), CarletterInterface.CL_CARLETTER_TELEPHONE_STATE, 0);
            if (CarletterInterface.this.mCarletterInfo.mTelephoneState != value6) {
                CarletterInterface.this.mCarletterInfo.mTelephoneState = value6;
                needSendMsg = true;
            }
            int value7 = Settings.System.getInt(this.mContext.getContentResolver(), CarletterInterface.CL_CARLETTER_NAVI_STATE, 0);
            if (CarletterInterface.this.mCarletterInfo.mNaviState != value7) {
                CarletterInterface.this.mCarletterInfo.mNaviState = value7;
            }
            if (needSendMsg) {
                Log.i(CarletterInterface.TAG, "mCarletterInfo " + CarletterInterface.this.mCarletterInfo.toString());
                Message msg = CarletterInterface.this.myHandler.obtainMessage(3);
                CarletterInterface.this.myHandler.removeMessages(3);
                CarletterInterface.this.myHandler.sendMessage(msg);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlerConnectChang() {
        boolean needSend = true;
        if (this.mCarletterInfo.mConnectState == 1) {
            Intent intentconnect = new Intent("com.hcn.link");
            intentconnect.putExtra("status", "CONNECTED");
            if (this.mCarletterInfo.mConnectMode == 7) {
                intentconnect.putExtra("type", CarZlinkInterface.DATA_PAGE_HICAR);
                intentconnect.putExtra("bluetooth", "stopA2dp");
            } else if (this.mCarletterInfo.mConnectMode == 1) {
                intentconnect.putExtra("type", "airplay");
                intentconnect.putExtra("bluetooth", "NULL");
            } else if (this.mCarletterInfo.mConnectMode == 5) {
                intentconnect.putExtra("type", CarZlinkInterface.DATA_PAGE_CARLIFE);
                intentconnect.putExtra("bluetooth", "NULL");
            } else if (this.mCarletterInfo.mConnectMode == 3) {
                intentconnect.putExtra("type", "androidauto");
                intentconnect.putExtra("bluetooth", "stopA2dp");
            } else if (this.mCarletterInfo.mConnectMode == 4 || this.mCarletterInfo.mConnectMode == 6) {
                intentconnect.putExtra("type", "androidMirror");
                intentconnect.putExtra("bluetooth", "NULL");
            } else if (this.mCarletterInfo.mConnectMode == 2) {
                intentconnect.putExtra("type", CarZlinkInterface.DATA_PAGE_CARPLAY);
                intentconnect.putExtra("bluetooth", "closeBt");
            } else {
                needSend = false;
            }
            if (needSend) {
                Log.i(TAG, "handlerConnectChang CONNECT type:" + this.mCarletterInfo.mConnectMode);
                this.mContext.sendBroadcastAsUser(intentconnect, UserHandle.CURRENT, null);
            }
            this.mPreConnectMode = this.mCarletterInfo.mConnectMode;
            return;
        }
        Intent intentDisconnect = new Intent("com.hcn.link");
        intentDisconnect.putExtra("status", "DISCONNECTED");
        if (this.mPreConnectMode == 7) {
            intentDisconnect.putExtra("type", CarZlinkInterface.DATA_PAGE_HICAR);
            intentDisconnect.putExtra("bluetooth", "resumeA2dp");
            updateSystemStateAfterConnect();
        } else if (this.mPreConnectMode == 1) {
            intentDisconnect.putExtra("type", "airplay");
            intentDisconnect.putExtra("bluetooth", "NULL");
        } else if (this.mPreConnectMode == 5) {
            intentDisconnect.putExtra("type", CarZlinkInterface.DATA_PAGE_CARLIFE);
            intentDisconnect.putExtra("bluetooth", "NULL");
            updateSystemStateAfterConnect();
        } else if (this.mPreConnectMode == 2) {
            intentDisconnect.putExtra("type", CarZlinkInterface.DATA_PAGE_CARPLAY);
            intentDisconnect.putExtra("bluetooth", "openBt");
            updateSystemStateAfterConnect();
        } else if (this.mPreConnectMode == 3) {
            intentDisconnect.putExtra("type", "androidauto");
            intentDisconnect.putExtra("bluetooth", "resumeA2dp");
            updateSystemStateAfterConnect();
        } else if (this.mPreConnectMode == 4) {
            intentDisconnect.putExtra("type", "androidMirror");
            intentDisconnect.putExtra("bluetooth", "NULL");
        } else if (this.mPreConnectMode == 6) {
            intentDisconnect.putExtra("type", CarZlinkInterface.DATA_PAGE_DLNA);
            intentDisconnect.putExtra("bluetooth", "NULL");
        } else {
            needSend = false;
        }
        if (needSend) {
            Log.i(TAG, "handlerConnectChang DISCONNECT type:" + this.mPreConnectMode);
            this.mContext.sendBroadcastAsUser(intentDisconnect, UserHandle.CURRENT, null);
        }
        this.mPreConnectMode = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopCarletter() {
        Log.i(TAG, "stopCarletter");
        Util.forceStopPackage(SourceService.ACTIVE_LT_CARPLAY);
        Util.forceStopPackage("com.huawei.nearby");
        Util.forceStopPackage(SourceService.ACTIVE_HUAWEI);
        Util.forceStopPackage("com.huawei.hisight");
        Util.forceStopPackage("com.huawei.hiviewlite");
        SystemProperties.set("service.ltlink.enable", "0");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLinkInfoChange() throws Throwable {
        if (this.mIsCarPlayPhoneCall && ((this.mCarletterInfo.mTelephoneState == 0 && (this.mCarletterInfo.mConnectMode == 2 || this.mCarletterInfo.mConnectMode == 7)) || this.mCarletterInfo.mConnectState == 0)) {
            this.mIsCarPlayPhoneCall = false;
            SystemProperties.set("sys.nonsystem.callstate", "0");
            Intent intent = new Intent(HConstant.CARPLAY_PHONE_CALL_OFF);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT, null);
            this.mZlinkInfoAndroidAutoCall = false;
        } else if (!this.mIsCarPlayPhoneCall && this.mCarletterInfo.mTelephoneState != 0 && (this.mCarletterInfo.mConnectMode == 2 || this.mCarletterInfo.mConnectMode == 7)) {
            Intent intent2 = new Intent(HConstant.CARPLAY_PHONE_CALL_ON);
            this.mContext.sendBroadcastAsUser(intent2, UserHandle.CURRENT, null);
            this.mIsCarPlayPhoneCall = true;
            SystemProperties.set("sys.nonsystem.callstate", "1");
            McuService.getMcuService().onLinkCall();
        } else if (!this.mIsZlinkMirrorLink && this.mCarletterInfo.mConnectState == 1 && this.mCarletterInfo.mConnectMode == 4) {
            this.mIsZlinkMirrorLink = true;
            Intent intent3 = new Intent("net.easyconn.a2dp.acquire");
            this.mContext.sendBroadcastAsUser(intent3, UserHandle.CURRENT, null);
        } else if (this.mCarletterInfo.mConnectState == 1 && this.mCarletterInfo.mConnectMode == 3) {
            if (this.mCarletterInfo.mTelephoneState != 0) {
                this.mZlinkInfoAndroidAutoCall = true;
            } else {
                this.mZlinkInfoAndroidAutoCall = false;
            }
        } else if (this.mCarletterInfo.mConnectState == 0) {
            this.mIsZlinkMirrorLink = false;
            this.mZlinkInfoAndroidAutoCall = false;
        } else if (this.mIsZlinkMirrorLink && this.mCarletterInfo.mRunningState == 0) {
            Intent intent4 = new Intent("net.easyconn.a2dp.acquire");
            this.mContext.sendBroadcastAsUser(intent4, UserHandle.CURRENT, null);
        }
        Log.i(TAG, "onLinkInfoChange mIsCarPlayPhoneCall:" + this.mIsCarPlayPhoneCall + " mIsZlinkMirrorLink:" + this.mIsZlinkMirrorLink);
    }

    @Override // android.link.LinkManager.LinkInterface
    public boolean isHicarConnect() {
        return this.mCarletterInfo.mConnectState == 1 && this.mCarletterInfo.mConnectMode == 7;
    }

    @Override // android.link.LinkManager.LinkInterface
    public boolean isNeedPowerOnPlayMusic() {
        return SystemProperties.getInt("sys.nonsystem.callstate", 0) != 1;
    }

    @Override // android.link.LinkManager.LinkInterface
    public void bluetoothCallOnChange(boolean calling) {
    }

    @Override // android.link.LinkManager.LinkInterface
    public boolean isCarPlayCall() {
        int callstate = SystemProperties.getInt("sys.nonsystem.callstate", 0);
        return callstate > 0;
    }

    @Override // android.link.LinkManager.LinkInterface
    public void bluetoothNameChange(String name) {
    }

    @Override // android.link.LinkManager.LinkInterface
    public void localeChange(boolean restart) {
    }
}
