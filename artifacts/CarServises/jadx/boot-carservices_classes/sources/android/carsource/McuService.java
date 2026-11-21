package android.carsource;

import android.Configures.HConfig;
import android.app.ActivityManager;
import android.app.StatusBarManager;
import android.audio.AudioEffect;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadsetClientCall;
import android.carstatus.CarStatus;
import android.carstatus.CarStatusService;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.link.CarletterInterface;
import android.link.LinkManager;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.net.Uri;
import android.os.Binder;
import android.os.FileUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.provider.Settings;
import android.radio.RadioService;
import android.sourceservice.ExtAudioMuxer;
import android.sourceservice.SourceService;
import android.system.ErrnoException;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import android.utilities.Util;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.android.car.wm.WindowManagerServiceExt;
import com.android.internal.content.PackageMonitor;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.XmlUtils;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libcore.io.Libcore;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class McuService extends IMcuManager.Stub implements McuConstant {
    static final String BOOTCOMPLETED = "android.intent.action.BOOT_COMPLETED";
    public static final String BTACTION = "com.cardroid.action.START_cardroid.bt";
    private static final String BarCodeFile = "/apd/barcode";
    private static final String DESCRIPTOR = "android.carsource.McuService";
    private static final int EVENT_SEND = 1;
    private static final String GocAcctFile = "/apd/acct.bin";
    private static final String IMEICodeFile = "/apd/IMEIcode";
    private static final String KEY_AUDIO_BALANCE = "sys.audio.lr.balance";
    private static final String KEY_AUDIO_PERCENTAGE = "sys.audio.percentage";
    private static final String KEY_BARCODE = "persist.sys.machinebarcode";
    private static final String KEY_GOC_ACCT = "sys.goc.ecnr_acct";
    private static final String KEY_IMEICODE = "sys.imei.barcode";
    private static final String KEY_LTCODE = "sys.ltlink.barcode";
    private static final String KEY_ZD360CODE = "sys.zd360.barcode";
    private static final String KEY_ZLINKCODE = "sys.zlink.barcode";
    private static final String LTCodeFile = "/apd/ltlinkcode";
    private static final String LTLINKCodeFile = "/apd/ltlinkcode";
    private static final int MCU_MAX_COMMAND_BYTES = 264;
    private final boolean PANEL_KEY_DISABLED;
    private static final String PanelKeyFile = "/apd/keymaps.xml";
    private static final String SNCodeFile = "/apd/.sn";
    private static final String SOCKET_NAME_MCU = "mcud";
    private static final int SOCKET_OPEN_RETRY_MILLS = 4000;
    private static final String SYSTEMUI_MEDIA_ACTION_NEXT = "com.mediatek.systemui.KEYCODE_MEDIA_NEXT";
    private static final String SYSTEMUI_MEDIA_ACTION_PREVIOUS = "com.mediatek.systemui.KEYCODE_MEDIA_PREVIOUS";
    private static final String TAG = "McuService";
    private static final String TouchKeyFile = "/apd/tskeymaps.xml";
    private static final String VOICE_ACTION = "com.hcn.voicesystem.action.StartVoice";
    private static final String VOICE_ACTION_CLS = "com.hcn.voicesystem.receiver.StartVoiceReceiver";
    private static final String VOICE_ACTION_PKG = "com.hcn.voicesystem";
    private static final String VirtualKeyFile = "/apd/virtualkeys";
    private static final String ZD360CodeFile = "/apd/avmbarcode";
    private static final String ZLinkCodeFile = "/apd/zlinkcode";
    private final AudioManager mAudioManager;
    private Context mContext;
    private TextView mDebugView;
    private DisplayManager mDisplayManager;
    IBinder mMcuBinder;
    private static McuService mMcuService;
    private boolean mMotorControlByMcu;
    private PackageMonitor mPackageMonitor;
    private final Screenshot mScreenshot;
    private HandlerThread mSenderThread;
    private SettingsObserver mSettingsObserver;
    private CaliView mTouchCaliView;
    private final String ACTION_KSRC_BUTTON = "android.intent.action.KSRC_BUTTON";
    private final String CAN_PACKAGE = "com.hcn.canupgrade";
    private String mMcuRotionState = "";
    private final REMOTEMAP mPanelMaps = new REMOTEMAP(10);
    private final REMOTEMAP mTouchKeyMaps = new REMOTEMAP(0);
    private final REMOTEMAP mSwcMaps = new REMOTEMAP(10);
    private final REMOTEMAP mSwcSetting = new REMOTEMAP(7);
    private final REMOTEMAP mRemoteMaps = new REMOTEMAP(0);
    private boolean mIsMappingAdcKey = false;
    private boolean mIsMappingPanelKey = false;
    private boolean mIsMappingTouchKey = false;
    private boolean mIsFactoryTestKey = false;
    private boolean mIsMappingRemoteKey = false;
    private final HashMap<Integer, KeyMap> mSWCSettingsMap = new HashMap<>();
    private final HashSet<KeyButton> mPressedKey = new HashSet<>();
    private final Object mKeyLock = new Object();
    private boolean mBootCompleted = false;
    private Handler mMainHandler = new Handler();
    private int mSilenceMode = 0;
    private boolean mPointerLocation = false;
    private int mDebugLevel = 0;
    private int mHFPStatus = 7;
    private AtomicBoolean mMovingVolume = new AtomicBoolean(false);
    private final ArrayList<Record> mRecords = new ArrayList<>();
    private final int MCUD_MESSAGE_CALL_TRANSACTION = HConfig.MBoardCode.FEIYIN_DSP_BOARD;
    private final int MCUD_MESSAGE_ORIENTATION_TRANSACTION = HConfig.MBoardCode.NAKAMICHI_MP5_BOARD;
    private final int MSG_DEBUG_EVENT = 1;
    private final int MSG_WATCHDOG = 2;
    private final int MSG_USER_IDEL = 3;
    private final int MSG_UNMUTE_ANYWAY = 4;
    private final int MSG_TOUCH_CALI = 5;
    private final int MSG_TOUCH_EVENT = 6;
    private final int MSG_BARCODE_EVENT = 7;
    private final int MSG_BARCODE_TIMETOU = 8;
    private final int MSG_ROTATION = 10;
    private final int MSG_REQUST_MCU_MOTORSTATE = 11;
    private final Handler H = new Handler() { // from class: android.carsource.McuService.3
        private final File FILE_WD = new File("/dev/watchdog");
        private String barcode = "";

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (McuService.this.mDebugView != null) {
                        String sb = (String) msg.obj;
                        SpannableString msp = new SpannableString(sb);
                        msp.setSpan(new ForegroundColorSpan(Color.argb(180, McuConstant.K_SWCSETTING, 187, 153)), 0, sb.length(), 33);
                        msp.setSpan(new BackgroundColorSpan(Color.argb(128, 128, 128, 128)), 0, sb.length(), 33);
                        McuService.this.mDebugView.setText(msp);
                    }
                    break;
                case 2:
                    McuService.this.requestWatchDog();
                    try {
                        if (this.FILE_WD.exists()) {
                            FileUtils.stringToFile(this.FILE_WD.getAbsolutePath(), "X");
                        }
                        break;
                    } catch (IOException e) {
                    }
                    sendMessageDelayed(obtainMessage(2), 5000L);
                    break;
                case 3:
                    if (McuService.this.AccStatus()) {
                        Util.startPackage(McuService.this.mContext, "com.hcn.screensaver");
                    }
                    break;
                case 4:
                    McuService.this.unMuteAnyway();
                    break;
                case 5:
                    McuService.this.showCaliView(msg.arg1 > 0);
                    break;
                case 6:
                    if (McuService.this.mTouchCaliView != null) {
                        int x = (msg.arg1 >> 16) & 65535;
                        int y = 65535 & msg.arg1;
                        McuService.this.mTouchCaliView.onTouchEvent(x, y, msg.arg2);
                    }
                    break;
                case 7:
                    String k = (String) msg.obj;
                    if (!k.equals("\n")) {
                        this.barcode += k;
                    } else if (this.barcode.startsWith("HCN")) {
                        Log.i(McuService.TAG, "save barcode " + this.barcode);
                        String[] strs = this.barcode.split("HCN");
                        if (strs == null || strs.length != 2) {
                            Toast.makeText(McuService.this.mContext, "error: barcode:" + this.barcode, 0).show();
                        } else {
                            McuService.this.saveBarcode(this.barcode, McuService.BarCodeFile, McuService.KEY_BARCODE);
                        }
                    } else if (this.barcode.startsWith("HCZJ")) {
                        Log.i(McuService.TAG, "save zlinkcode " + this.barcode);
                        String[] strs2 = this.barcode.split("HCZJ");
                        if (strs2 == null || strs2.length != 2) {
                            Toast.makeText(McuService.this.mContext, "error: barcode(HCZJ):" + this.barcode, 0).show();
                        } else {
                            McuService.this.saveBarcode(this.barcode, McuService.ZLinkCodeFile, McuService.KEY_ZLINKCODE);
                        }
                    } else if (this.barcode.startsWith("ZD360")) {
                        Log.i(McuService.TAG, "save zd360code " + this.barcode);
                        if (this.barcode.length() == 19) {
                            McuService.this.saveBarcode(this.barcode, McuService.ZD360CodeFile, McuService.KEY_ZD360CODE);
                        } else {
                            Toast.makeText(McuService.this.mContext, "error: barcode(ZD360):" + this.barcode, 0).show();
                        }
                    } else if (this.barcode.startsWith("HCLT")) {
                        Log.i(McuService.TAG, "save ltlinkcode " + this.barcode);
                        String[] strs3 = this.barcode.split("HCLT");
                        if (strs3 == null || strs3.length != 2) {
                            Toast.makeText(McuService.this.mContext, "error: barcode(HCLT):" + this.barcode, 0).show();
                        } else {
                            McuService.this.saveBarcode(this.barcode, "/apd/ltlinkcode", McuService.KEY_LTCODE);
                        }
                    } else if (this.barcode.startsWith("8697")) {
                        Log.i(McuService.TAG, "save imeicode " + this.barcode);
                        if (this.barcode.length() == 15) {
                            McuService.this.saveBarcode(this.barcode, McuService.IMEICodeFile, McuService.KEY_IMEICODE);
                        } else {
                            Toast.makeText(McuService.this.mContext, "error: barcode(IMEI):" + this.barcode, 0).show();
                        }
                    } else if (this.barcode.length() == 24) {
                        Log.i(McuService.TAG, "save HCN barcode " + this.barcode);
                        McuService.this.saveHcnBarcode(this.barcode);
                    }
                    removeMessages(8);
                    sendEmptyMessageDelayed(8, 250L);
                    break;
                case 8:
                    this.barcode = "";
                    break;
                case McuConstant.K_3 /* 10 */:
                    McuService.this.setRotation((String) msg.obj);
                    break;
                case McuConstant.K_4 /* 11 */:
                    McuService.this.requestRotingScreen();
                    sendMessageDelayed(obtainMessage(11), 500L);
                    break;
            }
        }
    };
    private String mTopAcitivity = "com.android.launcher3";
    private boolean mReversActive = false;
    private boolean mVoiceWindowShowing = false;
    private ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
    private final HashMap<Integer, KeyButton> mInjectKeyEventMap = new HashMap<>();
    private boolean mRefuseKeybyBacklight = false;
    private boolean mKeytoLaunchRadio = false;
    private KeyButton.KeyCallback KeyCallback = new AnonymousClass5();
    private boolean mMcuUpgradeMode = false;
    private boolean mCanUpgradeMode = false;
    private final int BACKLIGHT_OFF = 1;
    private boolean mKeyBacklightON = true;
    private float mBatteryVoltage = 12.0f;
    private boolean mBatteryLowTriggered = false;
    private float mRebootAtLowVoltage = 5.5f;
    private final String ANDROID_RB_PROPERTY = "sys.powerctl";
    private boolean mIgnoreEvent = false;
    private long mLaunchTouchCaliMillis = 0;
    private final String BACKLIGHT_MASK = "/sys/class/leds/lcd-backlight/mask";
    static final SparseArray<String> KEYCODE_SYMBOLIC_NAMES = new SparseArray<>();
    private String[] TouchCalibFiles = {"/sys/module/gt9xx_driver/parameters/calibration", "/sys/module/hyn_cst3xx/parameters/calibration", "/sys/module/axstouch/parameters/calibration", "/sys/module/jadard_touch/parameters/calibration", "/sys/module/focaltech/parameters/calibration", "/sys/module/tw3118/parameters/calibration"};
    private final String TouchIrqFile = "/sys/module/gt9xx_driver/parameters/irq_edge";
    private UEventObserver mStatusObserver = new UEventObserver() { // from class: android.carsource.McuService.7
        public void onUEvent(UEventObserver.UEvent event) {
            String status = event.get("rotation");
            Log.w(McuService.TAG, "onUEvent:" + status);
            McuService.this.H.removeMessages(10);
            McuService.this.H.sendMessageDelayed(McuService.this.H.obtainMessage(10, status), 10L);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static native String nativeGetTouchInfo();

    private native void nativeInitTouchEvent();

    public static native int nativeSetLogo(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeSetTouchEventReport(boolean z);

    public static native void sendCmdCarStatus(String str);

    private static class Record {
        IBinder binder;
        IMcuListener callback;
        int events;

        private Record() {
        }
    }

    public static McuService getMcuService() {
        return mMcuService;
    }

    public McuService(Context context) {
        boolean z;
        this.mMotorControlByMcu = false;
        mMcuService = this;
        this.mContext = context;
        this.mSenderThread = new HandlerThread("MCUSender");
        this.mSenderThread.start();
        this.mScreenshot = new Screenshot(context);
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        this.mDisplayManager = (DisplayManager) this.mContext.getSystemService("display");
        if ((HConfig.getNvrom(4) & 1) <= 0) {
            z = false;
        } else {
            z = true;
        }
        this.PANEL_KEY_DISABLED = z;
        this.mMotorControlByMcu = SystemProperties.getInt("ro.sys.motor_mcu", 0) > 0;
        IntentFilter filter = new IntentFilter();
        filter.addAction(BOOTCOMPLETED);
        filter.addAction(CarStatus.ACTION_ACC);
        filter.addAction("android.intent.action.ACTION_SHUTDOWN");
        filter.addAction("android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED");
        filter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        filter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        filter.addAction("android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED");
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: android.carsource.McuService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action.equals(McuService.BOOTCOMPLETED)) {
                    McuService.this.mBootCompleted = true;
                    McuService.this.send((byte) 2, (byte) 1, new byte[0]);
                    McuService.this.bootphase("completed");
                    McuService.this.mStatusObserver.startObserving("DEVPATH=/devices/platform/motor");
                    McuService.this.mSettingsObserver = McuService.this.new SettingsObserver();
                    ContentResolver cr = McuService.this.mContext.getContentResolver();
                    McuService.this.mPointerLocation = Settings.System.getInt(cr, "pointer_location", 0) > 0;
                    McuService.this.nativeSetTouchEventReport(McuService.this.mPointerLocation);
                    McuService.this.enableDebugView(McuService.this.mPointerLocation);
                    return;
                }
                if (action.equals(CarStatus.ACTION_ACC)) {
                    boolean acc = intent.getBooleanExtra(CarStatus.EXTRA_ACC, true);
                    if (McuService.this.mTouchCaliView != null) {
                        Message msg = McuService.this.H.obtainMessage(5, 0, 0);
                        msg.sendToTarget();
                    }
                    ContentResolver cr2 = McuService.this.mContext.getContentResolver();
                    if (acc && Settings.System.getInt(cr2, HConfig.reardisplay_status, 0) > 0) {
                        SystemProperties.set("ctl.restart", "uvd");
                        return;
                    }
                    return;
                }
                if (action.equals("android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED")) {
                    BluetoothHeadsetClientCall c = intent.getParcelableExtra("android.bluetooth.headsetclient.extra.CALL");
                    if (c != null) {
                        McuService.this.unMuteAnyway();
                        McuService.this.ResetBacklightStatus();
                        McuService.this.resetUserActivityTimeout();
                        McuService.this.mHFPStatus = c.getState();
                        Log.i(McuService.TAG, "BlueTooth HFP status : " + McuService.this.mHFPStatus);
                        return;
                    }
                    return;
                }
                if (action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
                    McuService.this.mHFPStatus = 7;
                    Log.i(McuService.TAG, "BlueTooth HFP status disconnected");
                    return;
                }
                if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                    int state = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                    Log.i(McuService.TAG, "bluetooth power state " + BluetoothAdapter.nameForState(state));
                    if (state == 10 || state == 13) {
                        McuService.this.mHFPStatus = 7;
                        return;
                    }
                    return;
                }
                if (!action.equals("android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED")) {
                    if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {
                        McuService.this.bootphase("shutdown");
                        return;
                    }
                    return;
                }
                int state2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                Log.i(McuService.TAG, "headset state changing to : " + state2);
                if (state2 == 0) {
                    McuService.this.mHFPStatus = 7;
                }
            }
        }, filter);
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("android.intent.action.SCREEN_ON");
        filter2.addAction("android.intent.action.SCREEN_OFF");
        filter2.setPriority(1000);
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: android.carsource.McuService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) throws Throwable {
                if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
                    McuService.this.send((byte) 2, (byte) 1, new byte[]{0});
                    McuService.this.send((byte) 2, (byte) 1, new byte[]{0});
                    McuService.this.mScreenshot.mPowerKeyTriggered = false;
                    McuService.this.mScreenshot.mVolumeDownKeyTriggered = false;
                    McuService.this.mScreenshot.mVolumeDownKeyConsumedByScreenshotChord = false;
                    McuService.this.exitSilence();
                    return;
                }
                if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                    McuService.this.mKeyBacklightON = true;
                    McuService.this.backlightOnoff(262144);
                    synchronized (McuService.this.mKeyLock) {
                        for (KeyButton key : McuService.this.mPressedKey) {
                            key.Released();
                        }
                        McuService.this.mPressedKey.clear();
                    }
                }
            }
        }, filter2);
        fakeOffBrushBlackBackground(false);
        bootphase("system");
    }

    public boolean exitSilence() throws Throwable {
        if (this.mSilenceMode <= 0) {
            return false;
        }
        this.mScreenshot.mPowerKeyTriggered = false;
        this.mScreenshot.mVolumeDownKeyTriggered = false;
        this.mScreenshot.mVolumeDownKeyConsumedByScreenshotChord = false;
        SilenceMode(false, false, true);
        Log.i(TAG, "exitSilence");
        return true;
    }

    @Override // android.carsource.IMcuManager.Stub, android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (code == 101) {
            data.enforceInterface(DESCRIPTOR);
            this.mMcuBinder = data.readStrongBinder();
            byte[] _arg1 = data.createByteArray();
            McuPacket pkt = new McuPacket(_arg1);
            processMCUCommand(pkt);
            return true;
        }
        if (code == 201) {
            data.enforceInterface(DESCRIPTOR);
            String rotate = data.readString();
            Log.i(TAG, "MCUD_MESSAGE_ORIENTATION_TRANSACTION:rotate=" + rotate);
            setRotation(rotate);
            return true;
        }
        return super.onTransact(code, data, reply, flags);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int send(byte cmd, byte subcmd, byte[] param) {
        int ret = -1;
        byte debug = (byte) (this.mDebugLevel & McuConstant.K_SWCSETTING);
        if (debug == cmd || this.mDebugLevel == 255) {
            StringBuffer sb = new StringBuffer();
            sb.append("Send MCUD McuPacket: ");
            sb.append(String.format(Locale.ENGLISH, "[%02X] %02X : ", Byte.valueOf(cmd), Byte.valueOf(subcmd)));
            for (byte b : param) {
                sb.append(String.format("%02X,", Integer.valueOf(b & 255)));
            }
            sb.append("\n");
            Log.v(TAG, sb.toString());
        }
        if (this.mMcuUpgradeMode && cmd != 21) {
            return -1;
        }
        if (this.mMcuBinder != null) {
            Parcel _data = Parcel.obtain();
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                _data.writeByte(cmd);
                _data.writeByte(subcmd);
                _data.writeByteArray(param);
                this.mMcuBinder.transact(1, _data, null, 1);
                ret = 0;
            } catch (Exception e) {
                this.mMcuBinder = null;
                Log.w(TAG, "remote mcu binder failed");
            } finally {
                _data.recycle();
            }
        } else {
            Log.w(TAG, String.format("send mcu gid [0x%02X, 0x%02X] while not connected.", Byte.valueOf(cmd), Byte.valueOf(subcmd)));
        }
        return ret;
    }

    public void sendVolumeChanged(int stream, int index, int flags) {
        if (this.mMcuBinder != null) {
            Parcel _data = Parcel.obtain();
            try {
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(stream);
                    _data.writeInt(index);
                    _data.writeInt(flags);
                    this.mMcuBinder.transact(2, _data, null, 1);
                } catch (Exception e) {
                    this.mMcuBinder = null;
                    Log.w(TAG, "remote mcu binder failed");
                }
            } finally {
                _data.recycle();
            }
        }
    }

    private void remove(IBinder binder) {
        synchronized (this.mRecords) {
            int N = this.mRecords.size();
            for (int i = 0; i < N; i++) {
                if (this.mRecords.get(i).binder == binder) {
                    this.mRecords.remove(i);
                    return;
                }
            }
        }
    }

    @Override // android.carsource.IMcuManager
    public void setcallback(IMcuListener callback, int events) {
        IBinder b = callback.asBinder();
        Log.i(TAG, "listen " + b.toString());
        synchronized (this.mRecords) {
            int N = this.mRecords.size();
            Record r = null;
            int i = 0;
            while (i < N) {
                r = this.mRecords.get(i);
                if (b == r.binder) {
                    break;
                } else {
                    i++;
                }
            }
            if (i == N) {
                r = new Record();
                this.mRecords.add(r);
            }
            r.binder = b;
            r.callback = callback;
            r.events = events;
            if ((events & 53) > 0) {
                this.mSWCSettingsMap.clear();
                this.mSwcSetting.clear();
            }
            this.mIsMappingAdcKey |= (events & 1) > 0;
            this.mIsMappingPanelKey |= (events & 16) > 0;
            this.mIsMappingTouchKey |= (events & 4) > 0;
            this.mIsFactoryTestKey |= (events & 2) > 0;
            this.mIsMappingRemoteKey = ((events & 32) > 0) | this.mIsMappingRemoteKey;
            if (this.mIsMappingAdcKey) {
                this.mSwcSetting.setTolerance(this.mSwcMaps.TOLERANCE);
                cloneKeymaptoSetting(this.mSwcMaps);
            }
            if (this.mIsMappingPanelKey) {
                this.mSwcSetting.setTolerance(this.mPanelMaps.TOLERANCE);
                cloneKeymaptoSetting(this.mPanelMaps);
            }
            if (this.mIsMappingTouchKey) {
                nativeSetTouchEventReport(true);
            }
            if (this.mIsMappingRemoteKey) {
                this.mSwcSetting.setTolerance(this.mRemoteMaps.TOLERANCE);
                cloneKeymaptoSetting(this.mRemoteMaps);
            }
        }
    }

    @Override // android.carsource.IMcuManager
    public void removecallback(IMcuListener callback, int events) {
        int remoteId;
        IBinder b = callback.asBinder();
        boolean save = (268435456 & events) == 0;
        remove(b);
        if (this.mIsMappingAdcKey && (events & 1) > 0) {
            this.mIsMappingAdcKey = false;
            if (save) {
                ContentResolver cr = this.mContext.getContentResolver();
                int pullup = Settings.System.getInt(cr, HConfig.swc_pullup, 0);
                this.mSwcMaps.save(new File("/data/system/swcmaps.xml"), this.mSWCSettingsMap, pullup);
            }
        }
        if (this.mIsMappingPanelKey && (events & 16) > 0) {
            this.mIsMappingPanelKey = false;
            if (save) {
                this.mPanelMaps.save(new File(PanelKeyFile), this.mSWCSettingsMap, -1);
            }
        }
        if (this.mIsMappingTouchKey && (events & 4) > 0) {
            this.mIsMappingTouchKey = false;
            nativeSetTouchEventReport(false);
            if (save) {
                generateVirtualKey(VirtualKeyFile, this.mSWCSettingsMap);
                Iterator<Map.Entry<Integer, KeyMap>> it = this.mSWCSettingsMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer, KeyMap> entry = it.next();
                    Integer key = entry.getKey();
                    if (key.intValue() < 131 || key.intValue() > 136) {
                        it.remove();
                    }
                }
                this.mTouchKeyMaps.save(new File(TouchKeyFile), this.mSWCSettingsMap, -1);
                this.mTouchKeyMaps.clear();
                this.mTouchKeyMaps.load(new File("/system/etc/keymaps.xml"));
                this.mTouchKeyMaps.load(new File(TouchKeyFile));
            }
            this.mSWCSettingsMap.clear();
        }
        if (this.mIsMappingRemoteKey && (events & 32) > 0) {
            this.mIsMappingRemoteKey = false;
            if (save && (remoteId = this.mSwcSetting.getRemoteId()) >= 0) {
                String fileName = String.format(Locale.US, "/apd/remote-%x.kl", Integer.valueOf(remoteId));
                this.mRemoteMaps.save(new File(fileName), this.mSWCSettingsMap, -1);
            }
        }
        if ((events & 53) > 0) {
            this.mSWCSettingsMap.clear();
            this.mSwcSetting.clear();
        }
        if ((events & 2) > 0) {
            this.mIsFactoryTestKey = false;
        }
    }

    private void cloneKeymaptoSetting(REMOTEMAP maps) {
        ArrayList<KeyMap> keys = new ArrayList<>();
        maps.KeymaptoArray(keys);
        for (KeyMap k : keys) {
            setKeyMaps(k);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetUserActivityTimeout() {
        if (this.mTopAcitivity.contains("com.android.launcher3") && AccStatus() && !this.mReversActive && !this.mVoiceWindowShowing && this.mHFPStatus == 7) {
            this.H.removeMessages(3);
            ContentResolver cr = this.mContext.getContentResolver();
            int timeout = Settings.System.getInt(cr, HConfig.screensaver_timeout, 0);
            if (timeout > 0) {
                this.H.sendEmptyMessageDelayed(3, Math.max(10000, timeout));
                return;
            }
            return;
        }
        this.H.removeMessages(3);
    }

    public void onActivityChanged(String topActivity) {
        this.mTopAcitivity = topActivity;
        this.mCanUpgradeMode = topActivity.contains("com.hcn.canupgrade");
        if (this.mTouchCaliView != null) {
            Message msg = this.H.obtainMessage(5, 0, 0);
            msg.sendToTarget();
        }
        resetUserActivityTimeout();
    }

    public void onReversChanged(boolean revon) {
        this.mReversActive = revon;
        if (this.mTouchCaliView != null) {
            Message msg = this.H.obtainMessage(5, 0, 0);
            msg.sendToTarget();
        }
        resetUserActivityTimeout();
    }

    public void onVoiceWindows(boolean showing) {
        this.mVoiceWindowShowing = showing;
        if (this.mTouchCaliView != null) {
            Message msg = this.H.obtainMessage(5, 0, 0);
            msg.sendToTarget();
        }
        resetUserActivityTimeout();
    }

    public void onSourceChanged(String source) {
        if (source != null && source.length() > 1) {
            this.H.sendEmptyMessageDelayed(4, 1000L);
        }
    }

    public void onLinkCall() throws Throwable {
        exitSilence();
        ResetBacklightStatus();
        this.H.sendEmptyMessage(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendKeyEvent(final int eventCode) {
        this.mExecutorService.execute(new Runnable() { // from class: android.carsource.McuService.4
            @Override // java.lang.Runnable
            public void run() {
                long now = SystemClock.uptimeMillis();
                KeyEvent down = new KeyEvent(now, now, 0, eventCode, 0, 0, -1, 0, 72, CarletterInterface.KEYCODE_KNOB_RIGHT);
                KeyEvent up = new KeyEvent(now, now, 1, eventCode, 0, 0, -1, 0, 72, CarletterInterface.KEYCODE_KNOB_RIGHT);
                InputManager.getInstance().injectInputEvent(down, 2);
                InputManager.getInstance().injectInputEvent(up, 2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRawKeyEvent(int adc, int action) {
        synchronized (this.mRecords) {
            for (int i = this.mRecords.size() - 1; i >= 0; i--) {
                Record r = this.mRecords.get(i);
                if ((r.events & 55) != 0) {
                    try {
                        r.callback.onEvent(adc, action);
                    } catch (RemoteException ex) {
                        Log.e(TAG, "onRawKeyEvent: " + ex);
                        this.mRecords.remove(i);
                        this.mIsMappingAdcKey = false;
                        this.mIsMappingPanelKey = false;
                        this.mIsMappingTouchKey = false;
                        this.mIsFactoryTestKey = false;
                        this.mIsMappingRemoteKey = false;
                        this.mSWCSettingsMap.clear();
                        this.mSwcSetting.clear();
                        nativeSetTouchEventReport(false);
                    }
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x008a A[Catch: all -> 0x004c, TRY_ENTER, TryCatch #4 {all -> 0x004c, blocks: (B:29:0x008a, B:34:0x00bb, B:32:0x00a4, B:17:0x0047, B:24:0x005a), top: B:51:0x001f }] */
    /* JADX WARN: Code duplicated, block: B:30:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:32:0x00a4 A[Catch: all -> 0x004c, TryCatch #4 {all -> 0x004c, blocks: (B:29:0x008a, B:34:0x00bb, B:32:0x00a4, B:17:0x0047, B:24:0x005a), top: B:51:0x001f }] */
    /* JADX WARN: Code duplicated, block: B:34:0x00bb A[Catch: all -> 0x004c, TRY_LEAVE, TryCatch #4 {all -> 0x004c, blocks: (B:29:0x008a, B:34:0x00bb, B:32:0x00a4, B:17:0x0047, B:24:0x005a), top: B:51:0x001f }] */
    public void BoardCastMediaKey(int keycode, int action, int Meta) throws Throwable {
        long token;
        long token2;
        long token3 = Binder.clearCallingIdentity();
        try {
            long now = SystemClock.uptimeMillis();
            KeyEvent event = null;
            if (LinkManager.getInstance().sendMediaButton(keycode, action)) {
                Binder.restoreCallingIdentity(token3);
                return;
            }
            try {
                if (action != 1) {
                    if (action == 5) {
                        try {
                            token2 = token3;
                            event = new KeyEvent(now, now, 1, keycode, 0, Meta);
                        } catch (Throwable th) {
                            th = th;
                            token = token3;
                        }
                    } else {
                        token2 = token3;
                        if (action == 2) {
                            KeyEvent event2 = new KeyEvent(now, now, 0, keycode, 0, Meta);
                            event = KeyEvent.changeFlags(event2, 128);
                        }
                    }
                    Log.d("knight", "BoardCastMediaKey: " + keycode);
                    if (keycode == 87) {
                        Intent intent = new Intent(SYSTEMUI_MEDIA_ACTION_NEXT);
                        this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT, null);
                        Log.d("knight", "BoardCastMediaKey: KEYCODE_MEDIA_NEXT");
                    } else if (keycode == 88) {
                        Intent intent2 = new Intent(SYSTEMUI_MEDIA_ACTION_PREVIOUS);
                        this.mContext.sendBroadcastAsUser(intent2, UserHandle.CURRENT, null);
                        Log.d("knight", "BoardCastMediaKey: KEYCODE_MEDIA_PREVIOUS");
                    }
                    if (event != null) {
                        this.mAudioManager.dispatchMediaKeyEvent(event);
                    }
                    Binder.restoreCallingIdentity(token2);
                    return;
                }
                try {
                    event = new KeyEvent(now, now, 0, keycode, 0, Meta);
                    token2 = token3;
                    try {
                        Log.d("knight", "BoardCastMediaKey: " + keycode);
                        if (keycode == 87) {
                            Intent intent3 = new Intent(SYSTEMUI_MEDIA_ACTION_NEXT);
                            this.mContext.sendBroadcastAsUser(intent3, UserHandle.CURRENT, null);
                            Log.d("knight", "BoardCastMediaKey: KEYCODE_MEDIA_NEXT");
                        } else if (keycode == 88) {
                            Intent intent4 = new Intent(SYSTEMUI_MEDIA_ACTION_PREVIOUS);
                            this.mContext.sendBroadcastAsUser(intent4, UserHandle.CURRENT, null);
                            Log.d("knight", "BoardCastMediaKey: KEYCODE_MEDIA_PREVIOUS");
                        }
                        if (event != null) {
                            this.mAudioManager.dispatchMediaKeyEvent(event);
                        }
                        Binder.restoreCallingIdentity(token2);
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                        token = token2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    token = token3;
                }
            } catch (Throwable th4) {
                th = th4;
                token = token2;
            }
            Binder.restoreCallingIdentity(token);
            throw th;
        } catch (Throwable th5) {
            th = th5;
            token = token3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean startActivitybyAction(String action, String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        try {
            this.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
            return true;
        } catch (ActivityNotFoundException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
            return true;
        } catch (NullPointerException e2) {
            Log.w(TAG, e2.toString() + Util.getLineInfo());
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean startActivitybyClass(String Component) {
        ComponentName cn = ComponentName.unflattenFromString(Component);
        Intent i = new Intent();
        i.addCategory("android.intent.category.LAUNCHER");
        i.setFlags(3145728);
        i.setComponent(cn);
        try {
            this.mContext.startActivityAsUser(i, UserHandle.CURRENT);
            return true;
        } catch (ActivityNotFoundException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
            return true;
        } catch (NullPointerException e2) {
            Log.w(TAG, e2.toString() + Util.getLineInfo());
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startEQactivity() {
        if (!Util.startPackage(this.mContext, "com.hcn.autoeq")) {
            String curSource = SystemProperties.get("sys.sourcing.package");
            if (curSource.equals("com.hcn.AutoMediaPlayer")) {
                startActivitybyClass("com.hcn.AutoMediaPlayer/com.hcn.eq.EQMainUI");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void SilenceMode(boolean touchable, boolean backlighton, boolean send) throws Throwable {
        this.mSilenceMode ^= 1;
        boolean touchable2 = touchable | backlighton;
        if ((this.mSilenceMode & 1) == 0) {
            this.mSilenceMode = 0;
        } else if (touchable2) {
            this.mSilenceMode |= 2;
        }
        Log.i(TAG, "silence mode changed to " + this.mSilenceMode);
        if (this.mSilenceMode > 0) {
            sendCmdCarStatus("mute=20002");
            ExtAudioMuxer.setMasterMute(true, 1);
            sendCmdCarStatus("radio_ant=0");
            SystemClock.sleep(200L);
            if (!backlighton) {
                backlightOnoff(131074);
                BoardCastMediaKey(McuConstant.K_PAUSE, 1, 0);
                BoardCastMediaKey(McuConstant.K_PAUSE, 5, 0);
            }
            try {
                FileUtils.stringToFile("/tmp/tvout/enable", "0\n");
            } catch (IOException e) {
            }
            CarStatusService.getCarStatusService().onFakePowerState(0);
            fakeOffBrushBlackBackground(true);
        } else {
            sendCmdCarStatus("mute=1000100");
            sendCmdCarStatus("mute=20000");
            ExtAudioMuxer.setMasterMute(true, 24);
            ExtAudioMuxer.setMasterMute(false, 1);
            fakeOffBrushBlackBackground(false);
            backlightOnoff(131072);
            if (LinkManager.getInstance().isNeedPowerOnPlayMusic()) {
                BoardCastMediaKey(McuConstant.K_PLAY, 1, 0);
                BoardCastMediaKey(McuConstant.K_PLAY, 5, 0);
            }
            CarStatusService.getCarStatusService().onFakePowerState(1);
            ContentResolver cr = this.mContext.getContentResolver();
            sendCmdCarStatus("radio_ant=" + Settings.System.getInt(cr, HConfig.radio_ant_extpwr, 0));
            try {
                int v = Settings.System.getInt(cr, HConfig.reardisplay_status, 0);
                FileUtils.stringToFile("/tmp/tvout/enable", v > 0 ? "1\n" : "0\n");
            } catch (IOException e2) {
            }
        }
        if (send) {
            long now = SystemClock.uptimeMillis();
            Intent i = new Intent(CarStatus.ACTION_SILENCE_BUTTON);
            int meta = this.mSilenceMode > 0 ? 0 : 16;
            KeyEvent k = new KeyEvent(now, now, 1, 26, 0, (backlighton ? 0 : 32) | meta);
            i.putExtra("android.intent.extra.KEY_EVENT", k);
            i.putExtra("myselftag", Process.myPid());
            i.addFlags(16);
            this.mContext.sendBroadcastAsUser(i, UserHandle.CURRENT, null);
        }
    }

    @Override // android.carsource.IMcuManager
    public void injectKeyEventTimeout(int keycode, int timeout) {
        getInjectedKeyButton(keycode).setPressedTimeout(timeout);
    }

    @Override // android.carsource.IMcuManager
    public void injectKeyEvent(int keycode, int state) {
        KeyButton key = getInjectedKeyButton(keycode);
        String caller = Util.getProcessName(Binder.getCallingPid());
        if (keycode == 25 || keycode == 24) {
            Log.i(TAG, "injectKeyEvent from " + caller + ", state " + KeyEvent.actionToString(state));
        }
        if (state == 0) {
            key.setPressed();
        } else {
            key.Released();
        }
    }

    private KeyButton getInjectedKeyButton(int keycode) {
        KeyButton key = this.mInjectKeyEventMap.get(Integer.valueOf(keycode));
        if (key == null) {
            KeyButton.KeyButtonATTR attr = new KeyButton.KeyButtonATTR();
            attr.KeyCode = keycode;
            attr.Repeat = 0;
            attr.LongPressKeyCode = 0;
            attr.LongPressKeyCode2 = 0;
            attr.LongPressTimeout = 0;
            attr.LongPressTimeout2 = 0;
            if (attr.KeyCode == 24 || attr.KeyCode == 25) {
                attr.Repeat = attr.Repeat == 0 ? 50 : attr.Repeat;
            }
            if ((attr.KeyCode == 24 || attr.KeyCode == 25 || attr.KeyCode == 3) && attr.LongPressTimeout == 0) {
                attr.LongPressTimeout = ViewConfiguration.getLongPressTimeout();
            }
            KeyButton key2 = new KeyButton(attr, 65535, this.mSenderThread.getLooper(), this.KeyCallback);
            this.mInjectKeyEventMap.put(Integer.valueOf(keycode), key2);
            return key2;
        }
        return key;
    }

    private KeyButton buildSwcSettingButton(int keycode, int adc) {
        KeyButton.KeyButtonATTR attr = new KeyButton.KeyButtonATTR();
        attr.KeyCode = keycode;
        attr.Repeat = 0;
        attr.LongPressKeyCode = keycode;
        attr.LongPressKeyCode2 = 0;
        attr.LongPressTimeout = 1600;
        attr.LongPressTimeout2 = 0;
        return new KeyButton(attr, adc, this.mSenderThread.getLooper(), this.KeyCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unMuteAnyway() {
        int index = this.mAudioManager.getStreamVolume(3);
        if (index == 0) {
            ContentResolver cr = this.mContext.getContentResolver();
            int lastAudibleVolume = this.mAudioManager.getLastAudibleStreamVolume(3);
            if (lastAudibleVolume == 0) {
                lastAudibleVolume = Settings.System.getInt(cr, HConfig.default_audio_volume, 11);
            }
            this.mAudioManager.setStreamVolume(3, lastAudibleVolume, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustVolume(int keycode) {
        this.mMovingVolume.set(true);
        int stream = 3;
        if (!AudioSystem.isStreamActive(0, 0)) {
            if (AudioSystem.isStreamActive(2, 0)) {
                stream = 2;
            }
        } else {
            stream = 0;
        }
        if (keycode == 25 || keycode == 24) {
            int dir = keycode == 25 ? -1 : 1;
            if (stream != 3) {
                this.mAudioManager.adjustStreamVolume(3, 100, 0);
            }
            this.mAudioManager.adjustStreamVolume(4, 100, 0);
            this.mAudioManager.adjustStreamVolume(stream, dir, 1);
        }
        if (keycode == 164) {
            this.mAudioManager.adjustStreamVolume(3, HConfig.MBoardCode.FEIYIN_DSP_BOARD, 1);
            this.mAudioManager.adjustStreamVolume(4, HConfig.MBoardCode.FEIYIN_DSP_BOARD, 0);
        }
        this.mMovingVolume.set(false);
    }

    /* JADX INFO: renamed from: android.carsource.McuService$5, reason: invalid class name */
    class AnonymousClass5 implements KeyButton.KeyCallback {
        AnonymousClass5() {
        }

        /* JADX WARN: Code duplicated, block: B:346:0x07af A[FALL_THROUGH] */
        @Override // android.carsource.KeyButton.KeyCallback
        public void onKeyEvent(KeyButton myself, int action, final int keycode, boolean hasLong) throws Throwable {
            if (6 == (McuService.this.mDebugLevel & McuConstant.K_SWCSETTING)) {
                Log.i(McuService.TAG, String.format("onKeyPress action=%d, keycode=0x%x, mAdc=0x%x, hasLong=%s.", Integer.valueOf(action), Integer.valueOf(keycode), Integer.valueOf(myself.mAdc), Boolean.valueOf(hasLong)));
            }
            if (!McuService.this.AccStatus() || McuService.this.mMcuUpgradeMode) {
                synchronized (McuService.this.mKeyLock) {
                    for (KeyButton key : McuService.this.mPressedKey) {
                        key.Released();
                    }
                    McuService.this.mPressedKey.clear();
                }
                return;
            }
            if (!McuService.this.mCanUpgradeMode || (keycode != 3 && keycode != 4)) {
                if ((McuService.this.mHFPStatus == 7 && !LinkManager.getInstance().isCarPlayCall()) || keycode == 25 || keycode == 24 || keycode == 302 || keycode == 307 || keycode == 287 || keycode == 91) {
                    if (!McuService.this.mReversActive || keycode == 25 || keycode == 24 || keycode == 164 || keycode == 302 || keycode == 307) {
                        if (McuService.this.mSilenceMode <= 0 || keycode == 26) {
                            if (McuService.this.mKeyBacklightON || keycode != 309) {
                                if (!McuService.this.mKeyBacklightON && keycode != 25 && keycode != 24) {
                                    McuService.this.mRefuseKeybyBacklight = true;
                                    McuService.this.mScreenshot.mPowerKeyTriggered = false;
                                    if (action == 1) {
                                        McuService.this.ResetBacklightStatus();
                                        return;
                                    }
                                    return;
                                }
                                if (keycode != 25 && keycode != 24) {
                                    McuService.this.resetUserActivityTimeout();
                                }
                                if (McuService.this.mKeyBacklightON && action == 1) {
                                    McuService.this.mRefuseKeybyBacklight = false;
                                }
                                if (McuService.this.mRefuseKeybyBacklight) {
                                    if (action == 5) {
                                        McuService.this.mRefuseKeybyBacklight = false;
                                        return;
                                    }
                                    return;
                                }
                                if (action == 5 && (myself.mAdc & 16711680) != 458752) {
                                    McuService.this.mScreenshot.mPowerKeyTriggered = false;
                                    McuService.this.mScreenshot.mVolumeDownKeyTriggered = false;
                                    if (McuService.this.mScreenshot.mVolumeDownKeyConsumedByScreenshotChord) {
                                        McuService.this.mScreenshot.mVolumeDownKeyConsumedByScreenshotChord = false;
                                        return;
                                    }
                                }
                                if ((keycode == 25 || keycode == 4) && McuService.this.mScreenshot.mPowerKeyTriggered) {
                                    McuService.this.mScreenshot.mVolumeDownKeyTime = SystemClock.uptimeMillis();
                                    McuService.this.mScreenshot.mVolumeDownKeyTriggered = true;
                                    McuService.this.mScreenshot.interceptScreenshotChord();
                                    return;
                                }
                                if (McuService.this.mScreenshot.mVolumeDownKeyConsumedByScreenshotChord) {
                                    return;
                                }
                                int rds = SystemProperties.getInt("sys.radio.rds", 0);
                                if (rds > 0) {
                                    boolean touser = keycode == 24 || keycode == 25 || keycode == 164;
                                    if (!touser) {
                                        return;
                                    }
                                }
                                long now = SystemClock.uptimeMillis();
                                boolean longpressable = myself.mLongpressable;
                                boolean avail = (!longpressable && action == 1) || (longpressable && action == 2) || (!hasLong && longpressable && action == 5);
                                boolean mediakavail = !longpressable || avail;
                                int k = -1;
                                switch (keycode) {
                                    case 3:
                                        if (avail) {
                                            if (action != 2) {
                                                StatusBarManager sbm = (StatusBarManager) McuService.this.mContext.getSystemService("statusbar");
                                                if (sbm != null) {
                                                    sbm.collapsePanels();
                                                }
                                                k = 3;
                                            } else {
                                                Intent i = new Intent();
                                                i.setFlags(McuManager.CNACEL_KEY);
                                                i.setClassName("com.android.systemui", "com.android.systemui.recents.RecentsActivity");
                                                try {
                                                    McuService.this.mContext.startActivityAsUser(i, UserHandle.CURRENT);
                                                } catch (ActivityNotFoundException e) {
                                                    Log.e(McuService.TAG, e.toString() + Util.getLineInfo());
                                                } catch (NullPointerException e2) {
                                                    Log.e(McuService.TAG, e2.toString() + Util.getLineInfo());
                                                }
                                            }
                                        }
                                        break;
                                    default:
                                        switch (keycode) {
                                            default:
                                                switch (keycode) {
                                                    case McuConstant.K_VOLUME_INC /* 24 */:
                                                    case McuConstant.K_VOLUME_DEC /* 25 */:
                                                        if (((action != 5 && myself.mKeyCode == myself.mAttr.LongPressKeyCode) || (avail && myself.mKeyCode != myself.mAttr.LongPressKeyCode)) && !McuService.this.mMovingVolume.get()) {
                                                            McuService.this.mExecutorService.execute(new Runnable() { // from class: android.carsource.-$$Lambda$McuService$5$EVN-ihGOWCTYIKfbYpuUM5S_xwc
                                                                @Override // java.lang.Runnable
                                                                public final void run() {
                                                                    McuService.this.adjustVolume(keycode);
                                                                }
                                                            });
                                                        }
                                                        break;
                                                    case McuConstant.K_POWER /* 26 */:
                                                        if (avail) {
                                                            McuService.this.SilenceMode(false, false, true);
                                                            myself.setCancel();
                                                        }
                                                        break;
                                                    default:
                                                        switch (keycode) {
                                                            default:
                                                                switch (keycode) {
                                                                    case McuConstant.K_PLAY_PAUSE /* 85 */:
                                                                        if (mediakavail) {
                                                                            if (longpressable) {
                                                                                McuService.this.BoardCastMediaKey(85, 1, 0);
                                                                            }
                                                                            McuService.this.BoardCastMediaKey(85, action, 0);
                                                                        }
                                                                        break;
                                                                    case McuConstant.K_STOP /* 86 */:
                                                                        if (mediakavail) {
                                                                            if (longpressable) {
                                                                                McuService.this.BoardCastMediaKey(86, 1, 0);
                                                                            }
                                                                            McuService.this.BoardCastMediaKey(86, action, 0);
                                                                        }
                                                                        break;
                                                                    case McuConstant.K_NEXT /* 87 */:
                                                                        if (mediakavail) {
                                                                            if (longpressable) {
                                                                                McuService.this.BoardCastMediaKey(87, 1, 0);
                                                                            }
                                                                            McuService.this.BoardCastMediaKey(87, action, 0);
                                                                        }
                                                                        break;
                                                                    case McuConstant.K_PREV /* 88 */:
                                                                        if (mediakavail) {
                                                                            if (longpressable) {
                                                                                McuService.this.BoardCastMediaKey(88, 1, 0);
                                                                            }
                                                                            McuService.this.BoardCastMediaKey(88, action, 0);
                                                                        }
                                                                        break;
                                                                    case McuConstant.K_FAST_BACKWARD /* 89 */:
                                                                        if (mediakavail) {
                                                                            if (longpressable) {
                                                                                McuService.this.BoardCastMediaKey(89, 1, 0);
                                                                            }
                                                                            McuService.this.BoardCastMediaKey(89, action, 0);
                                                                        }
                                                                        break;
                                                                    case McuConstant.K_FAST_FORWARD /* 90 */:
                                                                        if (mediakavail) {
                                                                            if (longpressable) {
                                                                                McuService.this.BoardCastMediaKey(90, 1, 0);
                                                                            }
                                                                            McuService.this.BoardCastMediaKey(90, action, 0);
                                                                        }
                                                                        break;
                                                                    default:
                                                                        switch (keycode) {
                                                                            case McuConstant.K_PLAY /* 126 */:
                                                                                if (mediakavail) {
                                                                                    if (longpressable) {
                                                                                        McuService.this.BoardCastMediaKey(McuConstant.K_PLAY, 1, 0);
                                                                                    }
                                                                                    McuService.this.BoardCastMediaKey(McuConstant.K_PLAY, action, 0);
                                                                                }
                                                                                break;
                                                                            case McuConstant.K_PAUSE /* 127 */:
                                                                                if (mediakavail) {
                                                                                    if (longpressable) {
                                                                                        McuService.this.BoardCastMediaKey(McuConstant.K_PAUSE, 1, 0);
                                                                                    }
                                                                                    McuService.this.BoardCastMediaKey(McuConstant.K_PAUSE, action, 0);
                                                                                }
                                                                                break;
                                                                            default:
                                                                                switch (keycode) {
                                                                                    case McuConstant.K_SKIP_FORWARD /* 272 */:
                                                                                        if (mediakavail) {
                                                                                            if (longpressable) {
                                                                                                McuService.this.BoardCastMediaKey(McuConstant.K_SKIP_FORWARD, 1, 0);
                                                                                            }
                                                                                            McuService.this.BoardCastMediaKey(McuConstant.K_SKIP_FORWARD, action, 0);
                                                                                        }
                                                                                        break;
                                                                                    case McuConstant.K_SKIP_BACKWARD /* 273 */:
                                                                                        if (mediakavail) {
                                                                                            if (longpressable) {
                                                                                                McuService.this.BoardCastMediaKey(McuConstant.K_SKIP_BACKWARD, 1, 0);
                                                                                            }
                                                                                            McuService.this.BoardCastMediaKey(McuConstant.K_SKIP_BACKWARD, action, 0);
                                                                                        }
                                                                                        break;
                                                                                    case McuConstant.K_STEP_FORWARD /* 274 */:
                                                                                        if (mediakavail) {
                                                                                            if (longpressable) {
                                                                                                McuService.this.BoardCastMediaKey(McuConstant.K_STEP_FORWARD, 1, 0);
                                                                                            }
                                                                                            McuService.this.BoardCastMediaKey(McuConstant.K_STEP_FORWARD, action, 0);
                                                                                        }
                                                                                        break;
                                                                                    case McuConstant.K_STEP_BACKWARD /* 275 */:
                                                                                        if (mediakavail) {
                                                                                            if (longpressable) {
                                                                                                McuService.this.BoardCastMediaKey(McuConstant.K_STEP_BACKWARD, 1, 0);
                                                                                            }
                                                                                            McuService.this.BoardCastMediaKey(McuConstant.K_STEP_BACKWARD, action, 0);
                                                                                        }
                                                                                        break;
                                                                                    default:
                                                                                        switch (keycode) {
                                                                                            case McuConstant.K_SOURCE /* 285 */:
                                                                                                if (avail) {
                                                                                                    McuService.this.unMuteAnyway();
                                                                                                    Intent intent = new Intent("android.intent.action.KSRC_BUTTON", (Uri) null);
                                                                                                    KeyEvent down = new KeyEvent(now, now, 0, 66, 0, 0);
                                                                                                    intent.putExtra("android.intent.extra.KEY_EVENT", down);
                                                                                                    intent.addFlags(32);
                                                                                                    intent.setFlags(McuManager.CNACEL_KEY);
                                                                                                    McuService.this.mContext.sendOrderedBroadcast(intent, null);
                                                                                                }
                                                                                                break;
                                                                                            case McuConstant.K_REPEAT /* 286 */:
                                                                                                if (mediakavail) {
                                                                                                    if (longpressable) {
                                                                                                        McuService.this.BoardCastMediaKey(46, 1, 0);
                                                                                                    }
                                                                                                    McuService.this.BoardCastMediaKey(46, action, 2);
                                                                                                }
                                                                                                break;
                                                                                            case McuConstant.K_HFP_AUDIO /* 287 */:
                                                                                                break;
                                                                                            default:
                                                                                                switch (keycode) {
                                                                                                    case McuConstant.K_RDSTA /* 294 */:
                                                                                                        RadioService rs = RadioService.getInstance();
                                                                                                        if (rs != null) {
                                                                                                            rs.setTA();
                                                                                                        }
                                                                                                        break;
                                                                                                    case McuConstant.K_SCAN /* 295 */:
                                                                                                        McuService.this.BoardCastMediaKey(31, action, 2);
                                                                                                        break;
                                                                                                    case McuConstant.K_RANDOM /* 296 */:
                                                                                                        McuService.this.BoardCastMediaKey(47, action, 2);
                                                                                                        break;
                                                                                                    case McuConstant.K_AS /* 297 */:
                                                                                                        McuService.this.BoardCastMediaKey(29, action, 2);
                                                                                                        break;
                                                                                                    case McuConstant.K_NAVI /* 298 */:
                                                                                                        if (avail) {
                                                                                                            Util.launchNavigation(McuService.this.mContext);
                                                                                                        }
                                                                                                        break;
                                                                                                    default:
                                                                                                        switch (keycode) {
                                                                                                            case 301:
                                                                                                                if (avail) {
                                                                                                                    if (!Util.getTopComponent(McuService.this.mContext).getClassName().equals("com.auto.hcamera.VideoInActivity")) {
                                                                                                                        McuService.this.startActivitybyClass("com.auto.hcamera/.VideoInActivity");
                                                                                                                    } else {
                                                                                                                        k = 4;
                                                                                                                    }
                                                                                                                }
                                                                                                                break;
                                                                                                            case McuConstant.K_BT /* 302 */:
                                                                                                                if (avail && !LinkManager.getInstance().sendKeyButton(McuConstant.K_BT, action)) {
                                                                                                                    Intent intent2 = new Intent(McuService.BTACTION, (Uri) null);
                                                                                                                    KeyEvent down2 = new KeyEvent(now, now, 0, 5, 0, 0);
                                                                                                                    intent2.putExtra("android.intent.extra.KEY_EVENT", down2);
                                                                                                                    McuService.this.mContext.sendBroadcastAsUser(intent2, UserHandle.CURRENT, null);
                                                                                                                }
                                                                                                                break;
                                                                                                            default:
                                                                                                                switch (keycode) {
                                                                                                                    case McuConstant.K_HANGDOWN /* 307 */:
                                                                                                                        if (avail && !LinkManager.getInstance().sendKeyButton(McuConstant.K_HANGDOWN, action)) {
                                                                                                                            Intent intent3 = new Intent(McuService.BTACTION, (Uri) null);
                                                                                                                            KeyEvent down3 = new KeyEvent(now, now, 0, 6, 0, 0);
                                                                                                                            intent3.putExtra("android.intent.extra.KEY_EVENT", down3);
                                                                                                                            McuService.this.mContext.sendBroadcastAsUser(intent3, UserHandle.CURRENT, null);
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case McuConstant.K_EQ /* 308 */:
                                                                                                                        if (avail) {
                                                                                                                            ComponentName topcn = Util.getTopComponent(McuService.this.mContext);
                                                                                                                            if (!topcn.getClassName().equals("com.hcn.eq.EQMainUI") && !topcn.getPackageName().equals("com.hcn.autoeq")) {
                                                                                                                                McuService.this.startEQactivity();
                                                                                                                            } else {
                                                                                                                                k = 142;
                                                                                                                            }
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case McuConstant.K_BKLIGHT_OFF /* 309 */:
                                                                                                                        if (avail) {
                                                                                                                            McuService.this.mKeyBacklightON = false;
                                                                                                                            McuService.this.backlightOnoff(McuService.this.mKeyBacklightON ? 262144 : 262148);
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case McuConstant.K_BACKLIGHT /* 310 */:
                                                                                                                        if (avail) {
                                                                                                                            McuService.this.mKeyBacklightON = !McuService.this.mKeyBacklightON;
                                                                                                                            McuService.this.backlightOnoff(McuService.this.mKeyBacklightON ? 262144 : 262148);
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case McuConstant.K_BKLIGHT_ON /* 311 */:
                                                                                                                        if (avail) {
                                                                                                                            McuService.this.mKeyBacklightON = true;
                                                                                                                            McuService.this.backlightOnoff(McuService.this.mKeyBacklightON ? 262144 : 262148);
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case McuConstant.K_SCROLL_R /* 312 */:
                                                                                                                        McuService.this.BoardCastMediaKey(46, action, 1);
                                                                                                                        break;
                                                                                                                    case McuConstant.K_SCROLL_L /* 313 */:
                                                                                                                        McuService.this.BoardCastMediaKey(40, action, 1);
                                                                                                                        break;
                                                                                                                    case McuConstant.K_SETUP /* 314 */:
                                                                                                                        if (avail) {
                                                                                                                            McuService.this.startActivitybyAction("android.settings.SETTINGS", "android.intent.category.DEFAULT");
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    case McuConstant.K_REBOOT /* 315 */:
                                                                                                                        PowerManager pm = (PowerManager) McuService.this.mContext.getSystemService("power");
                                                                                                                        pm.reboot(null);
                                                                                                                        break;
                                                                                                                    case McuConstant.K_PS0 /* 316 */:
                                                                                                                    case McuConstant.K_PS1 /* 317 */:
                                                                                                                    case McuConstant.K_PS2 /* 318 */:
                                                                                                                    case McuConstant.K_PS3 /* 319 */:
                                                                                                                    case McuConstant.K_PS4 /* 320 */:
                                                                                                                    case McuConstant.K_PS5 /* 321 */:
                                                                                                                    case McuConstant.K_PS6 /* 322 */:
                                                                                                                    case McuConstant.K_PS7 /* 323 */:
                                                                                                                    case McuConstant.K_PS8 /* 324 */:
                                                                                                                    case McuConstant.K_PS9 /* 325 */:
                                                                                                                        McuService.this.BoardCastMediaKey((keycode - 316) + 7, action, 2);
                                                                                                                        break;
                                                                                                                    default:
                                                                                                                        switch (keycode) {
                                                                                                                            case McuConstant.K_TIMESETTTING /* 329 */:
                                                                                                                                if (avail) {
                                                                                                                                    McuService.this.startActivitybyAction("android.settings.DATE_SETTINGS", "android.intent.category.DEFAULT");
                                                                                                                                }
                                                                                                                                break;
                                                                                                                            case McuConstant.K_USER1 /* 330 */:
                                                                                                                                if (avail) {
                                                                                                                                    String cn = Settings.System.getString(McuService.this.mContext.getContentResolver(), HConfig.user_key1_action);
                                                                                                                                    if (!TextUtils.isEmpty(cn)) {
                                                                                                                                        McuService.this.startActivitybyClass(cn);
                                                                                                                                    }
                                                                                                                                }
                                                                                                                                break;
                                                                                                                            case McuConstant.K_BTMUSIC /* 331 */:
                                                                                                                                if (avail && !Util.getTopComponent(McuService.this.mContext).getClassName().equals("com.autochips.bluetooth/.BtMusicActivity")) {
                                                                                                                                    McuService.this.startActivitybyClass("com.autochips.bluetooth/.BtMusicActivity");
                                                                                                                                }
                                                                                                                                break;
                                                                                                                            case McuConstant.K_VIDEO /* 332 */:
                                                                                                                                if (avail) {
                                                                                                                                    McuService.this.startActivitybyClass("com.hcn.AutoMediaPlayer/com.hcn.MediaActivity.VideoPlayerUiActivity");
                                                                                                                                }
                                                                                                                                break;
                                                                                                                            case McuConstant.K_USER2 /* 333 */:
                                                                                                                                if (avail) {
                                                                                                                                    String cn2 = Settings.System.getString(McuService.this.mContext.getContentResolver(), HConfig.user_key2_action);
                                                                                                                                    if (!TextUtils.isEmpty(cn2)) {
                                                                                                                                        McuService.this.startActivitybyClass(cn2);
                                                                                                                                    }
                                                                                                                                }
                                                                                                                                break;
                                                                                                                            case McuConstant.K_MOTOR /* 334 */:
                                                                                                                                if (avail) {
                                                                                                                                    McuService.this.toggleMotor();
                                                                                                                                }
                                                                                                                                break;
                                                                                                                            case McuConstant.K_LAUNCHER_APPS /* 335 */:
                                                                                                                                if (avail) {
                                                                                                                                    Intent intent4 = new Intent("android.intent.action.ALL_APPS");
                                                                                                                                    intent4.setFlags(McuManager.CNACEL_KEY);
                                                                                                                                    intent4.setPackage("com.android.launcher3");
                                                                                                                                    try {
                                                                                                                                        McuService.this.mContext.startActivityAsUser(intent4, UserHandle.CURRENT);
                                                                                                                                        Log.d(McuService.TAG, "onKeyEvent: K_LAUNCHER_APPS startActivityAsUser");
                                                                                                                                    } catch (ActivityNotFoundException e3) {
                                                                                                                                        Log.e(McuService.TAG, "onKeyEvent: K_LAUNCHER_APPS ActivityNotFoundException");
                                                                                                                                    }
                                                                                                                                }
                                                                                                                                break;
                                                                                                                            case 336:
                                                                                                                                if (avail) {
                                                                                                                                    McuService.this.SilenceMode(true, false, true);
                                                                                                                                    myself.setCancel();
                                                                                                                                }
                                                                                                                                break;
                                                                                                                            case 337:
                                                                                                                                if (McuService.this.whetherEnterScreenSaver()) {
                                                                                                                                    Util.startPackage(McuService.this.mContext, "com.hcn.screensaver");
                                                                                                                                }
                                                                                                                                break;
                                                                                                                            default:
                                                                                                                                switch (keycode) {
                                                                                                                                    case McuConstant.K_SPACE /* 62 */:
                                                                                                                                    case McuConstant.K_MENU /* 82 */:
                                                                                                                                        break;
                                                                                                                                    case McuConstant.K_MUTE /* 164 */:
                                                                                                                                        if (action == 1) {
                                                                                                                                            McuService.this.mScreenshot.mPowerKeyTime = SystemClock.uptimeMillis();
                                                                                                                                            McuService.this.mScreenshot.mPowerKeyTriggered = true;
                                                                                                                                        }
                                                                                                                                        if (avail && !McuService.this.mMovingVolume.get()) {
                                                                                                                                            McuService.this.mExecutorService.execute(new Runnable() { // from class: android.carsource.-$$Lambda$McuService$5$CwVdLgUholmYsf2hHthaPzXCWIM
                                                                                                                                                @Override // java.lang.Runnable
                                                                                                                                                public final void run() {
                                                                                                                                                    McuService.this.adjustVolume(keycode);
                                                                                                                                                }
                                                                                                                                            });
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case McuConstant.K_MUSIC /* 209 */:
                                                                                                                                        if (avail) {
                                                                                                                                            McuService.this.startActivitybyClass("com.hcn.AutoMediaPlayer/com.hcn.MediaActivity.MusicPlayerUiActivity");
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case McuConstant.K_ASSISTANT /* 231 */:
                                                                                                                                        if (avail) {
                                                                                                                                            int index = SystemProperties.getInt("persist.sys.voice_assis", 0);
                                                                                                                                            if (index == 0) {
                                                                                                                                                if (HConfig.isOverseaVersion()) {
                                                                                                                                                    Intent i2 = new Intent("txz.intent.action.smartwakeup.triggerRecordButton");
                                                                                                                                                    i2.setPackage("com.txznet.txz");
                                                                                                                                                    McuService.this.mContext.sendBroadcastAsUser(i2, UserHandle.CURRENT, null);
                                                                                                                                                } else {
                                                                                                                                                    Intent i3 = new Intent(McuService.VOICE_ACTION);
                                                                                                                                                    i3.setFlags(McuManager.CNACEL_KEY);
                                                                                                                                                    i3.setComponent(new ComponentName(McuService.VOICE_ACTION_PKG, McuService.VOICE_ACTION_CLS));
                                                                                                                                                    McuService.this.mContext.sendBroadcastAsUser(i3, UserHandle.CURRENT, null);
                                                                                                                                                }
                                                                                                                                            } else if (index == 1) {
                                                                                                                                                Intent i4 = new Intent(McuService.VOICE_ACTION);
                                                                                                                                                i4.setPackage("com.android.systemui");
                                                                                                                                                McuService.this.mContext.sendBroadcastAsUser(i4, UserHandle.CURRENT, null);
                                                                                                                                            } else if (index == 2) {
                                                                                                                                                try {
                                                                                                                                                    Intent i5 = new Intent("android.intent.action.VOICE_ASSIST");
                                                                                                                                                    i5.addFlags(McuManager.CNACEL_KEY);
                                                                                                                                                    McuService.this.mContext.startActivity(i5);
                                                                                                                                                } catch (ActivityNotFoundException e4) {
                                                                                                                                                    Toast.makeText(McuService.this.mContext, "Install google voice please!", 0).show();
                                                                                                                                                }
                                                                                                                                            } else if (index == 3 || index == 4) {
                                                                                                                                                LinkManager.getInstance().sendKeyButton(McuConstant.K_ASSISTANT, action);
                                                                                                                                            } else if (index == 5) {
                                                                                                                                                try {
                                                                                                                                                    Intent i6 = new Intent();
                                                                                                                                                    i6.setAction("com.atoto.speechtotext.wakeup");
                                                                                                                                                    i6.setClassName("com.atoto.speechtotext", "com.atoto.module.main.service.CarSpeechToTextService");
                                                                                                                                                    i6.putExtra("switch", true);
                                                                                                                                                    McuService.this.mContext.startService(i6);
                                                                                                                                                } catch (ActivityNotFoundException e5) {
                                                                                                                                                    Toast.makeText(McuService.this.mContext, "Install DriveChat please!", 0).show();
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case McuConstant.K_F_AUX_IN /* 247 */:
                                                                                                                                        if (avail) {
                                                                                                                                            if (!Util.getTopComponent(McuService.this.mContext).getClassName().equals("com.auto.hcamera.FCameraVideoInActivity")) {
                                                                                                                                                McuService.this.startActivitybyClass("com.auto.hcamera/.FCameraVideoInActivity");
                                                                                                                                            } else {
                                                                                                                                                k = 4;
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case McuConstant.K_SWCSETTING /* 255 */:
                                                                                                                                        if (action == 2 || (!hasLong && action == 5)) {
                                                                                                                                            McuService.this.onRawKeyEvent(myself.mAdc, action);
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case McuConstant.K_BAND /* 304 */:
                                                                                                                                        boolean topisradio = Util.getTopComponent(McuService.this.mContext).getPackageName().equals(SourceService.ACTIVE_RADIO);
                                                                                                                                        if (avail && !topisradio) {
                                                                                                                                            Util.startPackage(McuService.this.mContext, SourceService.ACTIVE_RADIO);
                                                                                                                                            McuService.this.mKeytoLaunchRadio = true;
                                                                                                                                        }
                                                                                                                                        if (topisradio && !McuService.this.mKeytoLaunchRadio) {
                                                                                                                                            McuService.this.BoardCastMediaKey(30, action, 2);
                                                                                                                                        }
                                                                                                                                        if (action == 5) {
                                                                                                                                            McuService.this.mKeytoLaunchRadio = false;
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                    case McuConstant.K_STEREO /* 327 */:
                                                                                                                                        McuService.this.BoardCastMediaKey(43, action, 1);
                                                                                                                                        break;
                                                                                                                                }
                                                                                                                                break;
                                                                                                                        }
                                                                                                                        break;
                                                                                                                }
                                                                                                                break;
                                                                                                        }
                                                                                                        break;
                                                                                                }
                                                                                                break;
                                                                                        }
                                                                                        break;
                                                                                }
                                                                                break;
                                                                        }
                                                                    case McuConstant.K_MICMUTE /* 91 */:
                                                                        if (avail) {
                                                                            Intent intent5 = new Intent("android.intent.action.GLOBAL_BUTTON", (Uri) null);
                                                                            int a = action == 5 ? 1 : 0;
                                                                            KeyEvent event = new KeyEvent(now, now, a, keycode, 0, 0);
                                                                            intent5.putExtra("android.intent.extra.KEY_EVENT", event);
                                                                            McuService.this.mContext.sendBroadcastAsUser(intent5, UserHandle.CURRENT, null);
                                                                        }
                                                                        break;
                                                                }
                                                            case McuConstant.K_ENTER /* 66 */:
                                                            case McuConstant.K_CLEAR /* 67 */:
                                                                k = keycode;
                                                                break;
                                                        }
                                                        break;
                                                }
                                            case 7:
                                            case 8:
                                            case McuConstant.K_2 /* 9 */:
                                            case McuConstant.K_3 /* 10 */:
                                            case McuConstant.K_4 /* 11 */:
                                            case McuConstant.K_5 /* 12 */:
                                            case McuConstant.K_6 /* 13 */:
                                            case McuConstant.K_7 /* 14 */:
                                            case McuConstant.K_8 /* 15 */:
                                            case 16:
                                            case McuConstant.K_STAR /* 17 */:
                                            case McuConstant.K_POUND /* 18 */:
                                            case McuConstant.K_UP /* 19 */:
                                            case McuConstant.K_DOWN /* 20 */:
                                            case McuConstant.K_LEFT /* 21 */:
                                            case McuConstant.K_RIGHT /* 22 */:
                                                k = keycode;
                                                break;
                                        }
                                    case 4:
                                        k = keycode;
                                        break;
                                }
                                int k2 = k;
                                if (k2 >= 0 && avail) {
                                    McuService.this.sendKeyEvent(k2);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        int longkeycode = myself.mAttr.LongPressKeyCode;
                        McuService.this.mScreenshot.mPowerKeyTriggered = false;
                        if (longkeycode == 26 && action != 5) {
                            McuService.this.SilenceMode(false, false, true);
                            myself.setCancel();
                        }
                        Log.i(McuService.TAG, "Refuse key event, mSilenceMode = " + McuService.this.mSilenceMode);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean whetherEnterScreenSaver() {
        return !LinkManager.getInstance().isCarPlayCall() && this.mSilenceMode <= 0 && AccStatus() && !this.mReversActive && !this.mVoiceWindowShowing && this.mHFPStatus == 7;
    }

    private boolean isPanelkey(int ksrc) {
        if (ksrc != 7) {
            return ksrc >= 1 && ksrc <= 3;
        }
        return true;
    }

    private boolean isSwckey(int ksrc) {
        return ksrc >= 4 && ksrc <= 6;
    }

    private void onReceivedKey(McuPacket pkt) {
        int paramLen = pkt.paramLen();
        int ksrc = pkt.param(0) & 255;
        int adc = ((pkt.param(1) << 8) & 65280) | (pkt.param(2) & 255);
        byte keyState = pkt.param(3);
        if (pkt.subCmd() != 1) {
            return;
        }
        if (this.mPointerLocation) {
            String msg = String.format("src %d, adc %d(0x%x), state %d", Integer.valueOf(ksrc), Integer.valueOf(adc), Integer.valueOf(adc), Byte.valueOf(keyState));
            this.H.obtainMessage(1, msg).sendToTarget();
        }
        if (keyState == 0) {
            synchronized (this.mKeyLock) {
                for (KeyButton key : this.mPressedKey) {
                    key.Released();
                }
                this.mPressedKey.clear();
            }
            return;
        }
        if (paramLen != 4) {
            Log.w(TAG, "Bad packet for onReceivedKey.");
            return;
        }
        if (6 == (this.mDebugLevel & McuConstant.K_SWCSETTING)) {
            Log.w(TAG, String.format("onReceivedKey: ksrc = %d, adc = 0x%x", Integer.valueOf(ksrc), Integer.valueOf(adc)));
        }
        if (ksrc == 7) {
            onAdcKey(ksrc, adc, 4);
            return;
        }
        if ((isPanelkey(ksrc) && !this.PANEL_KEY_DISABLED) || isSwckey(ksrc)) {
            onAdcKey(ksrc, adc, 400);
            return;
        }
        if (ksrc == 10) {
            int id = (adc >> 8) & McuConstant.K_SWCSETTING;
            if (this.mRemoteMaps.getRemoteId() != id) {
                this.mRemoteMaps.load(id);
            }
            if (this.mIsMappingRemoteKey) {
                this.mSwcSetting.setRemoteId(id);
            }
            onAdcKey(ksrc, adc, 400);
        }
    }

    private KeyButton onAdcKey(int ksrc, int adc, int timeout) {
        KeyButton key;
        if (isPanelkey(ksrc)) {
            key = this.mPanelMaps.GetKeyButton((ksrc << 16) + adc);
        } else if (ksrc == 10) {
            key = this.mRemoteMaps.GetKeyButton(255 & adc);
        } else {
            key = this.mSwcMaps.GetKeyButton((ksrc << 16) + adc);
        }
        if (key != null) {
            if (6 == (this.mDebugLevel & McuConstant.K_SWCSETTING)) {
                Log.w(TAG, String.format("onAdcKey: key = %d", Integer.valueOf(key.mKeyCode)));
            }
            adc = key.mAdc & 65535;
        }
        if ((this.mIsMappingAdcKey && isSwckey(ksrc)) || ((this.mIsMappingPanelKey && isPanelkey(ksrc)) || this.mIsFactoryTestKey)) {
            KeyButton k = this.mSwcSetting.GetKeyButton((ksrc << 16) | adc);
            if (k == null) {
                k = buildSwcSettingButton(McuConstant.K_SWCSETTING, (ksrc << 16) | adc);
            }
            KeyButton k2 = k;
            k2.setPressedTimeout(timeout);
            this.mSwcSetting.put(k2);
            synchronized (this.mKeyLock) {
                this.mPressedKey.add(k2);
            }
            return k2;
        }
        if (this.mIsMappingRemoteKey && ksrc == 10) {
            KeyButton k3 = this.mSwcSetting.GetKeyButton(255 & adc);
            if (k3 == null) {
                k3 = buildSwcSettingButton(McuConstant.K_SWCSETTING, 255 & adc);
            }
            k3.setPressedTimeout(timeout);
            this.mSwcSetting.put(k3);
            synchronized (this.mKeyLock) {
                this.mPressedKey.add(k3);
            }
            KeyButton key2 = k3;
            return key2;
        }
        if (key != null) {
            key.setPressedTimeout(timeout);
            synchronized (this.mKeyLock) {
                this.mPressedKey.add(key);
            }
            return key;
        }
        return key;
    }

    @Override // android.carsource.IMcuManager
    public void setKeyMaps(KeyMap key) {
        this.mSWCSettingsMap.get(Integer.valueOf(key.kadc));
        int ksrc = shortToInt((short) (key.kadc >> 16));
        int kadc = shortToInt((short) key.kadc);
        Log.i(TAG, String.format("setKeyMaps adc %d:%d, key %d, lc %d", Integer.valueOf(ksrc), Integer.valueOf(kadc), Integer.valueOf(key.kcode), Integer.valueOf(key.longcode)));
        this.mSWCSettingsMap.put(Integer.valueOf(key.kadc), key);
    }

    @Override // android.carsource.IMcuManager
    public void setTouchKeyMaps(KeyMap key) {
        int x = shortToInt((short) (key.kadc >> 16));
        int y = shortToInt((short) key.kadc);
        Log.i(TAG, String.format("setTouchKeyMaps xy %d:%d, key %d, lc %d", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(key.kcode), Integer.valueOf(key.longcode)));
        if (key.kadc == -1) {
            this.mSWCSettingsMap.remove(Integer.valueOf(key.kcode));
        } else if (key.kadc >= 131 || key.kadc <= 136) {
            this.mSWCSettingsMap.put(Integer.valueOf(key.kadc), key);
        } else {
            this.mSWCSettingsMap.put(Integer.valueOf(key.kcode), key);
        }
    }

    @Override // android.carsource.IMcuManager
    public void getKeySettings(int type, List<KeyMap> keys) {
        if (type == 1) {
            this.mSwcMaps.KeymaptoArray(keys);
        } else if (type == 16) {
            this.mPanelMaps.KeymaptoArray(keys);
        } else if (type == 32) {
            this.mRemoteMaps.KeymaptoArray(keys);
        }
    }

    private void onGetMcuVersion(McuPacket pkt) {
        int paramLen = pkt.paramLen();
        byte[] buf = new byte[paramLen];
        String str = "";
        for (int i = 0; i < paramLen; i++) {
            byte c = (byte) (255 & pkt.param(i));
            buf[i] = c > 0 ? c : McuConstant.GID_XM;
        }
        try {
            str = new String(buf, "GBK").trim();
        } catch (UnsupportedEncodingException uee) {
            Log.e(TAG, "onGetMcuVersion : " + uee + Util.getLineInfo());
        }
        switch (pkt.subCmd()) {
            case 1:
                SystemProperties.set(HConfig.PROP_MCU_VERSION, str);
                break;
            case 2:
                SystemProperties.set("hw.sys.platform.version", str);
                break;
            case 3:
                String ver = String.format(Locale.ENGLISH, "hw.sys.mcu%d.version", Integer.valueOf(255 & pkt.param(0)));
                SystemProperties.set(ver, str);
                break;
        }
    }

    private void onGetCommon(McuPacket pkt) {
        int subcmd = pkt.subCmd() & McuConstant.K_SWCSETTING;
        if (subcmd == 99) {
            Log.i(TAG, "mucd connected");
            initSettings();
        }
    }

    private void onMcuIAP(McuPacket pkt) {
        int subcmd = pkt.subCmd() & 255;
        this.mMcuUpgradeMode = true;
        this.H.obtainMessage(1, "MCU IAP mode").sendToTarget();
        if (this.mBootCompleted && !"com.hcn.mcuupgrade".equals(Util.getTopComponent(this.mContext).getPackageName())) {
            Util.startPackage(this.mContext, "com.hcn.mcuupgrade");
        }
        int N = pkt.paramLen();
        byte[] data = new byte[N];
        System.arraycopy(pkt.mData, 3, data, 0, N);
        synchronized (this.mRecords) {
            int i = this.mRecords.size() - 1;
            while (true) {
                int i2 = i;
                if (i2 >= 0) {
                    Record r = this.mRecords.get(i2);
                    if ((r.events & 8) != 0) {
                        try {
                            r.callback.onByteEvent(subcmd, data);
                        } catch (RemoteException ex) {
                            Log.e(TAG, "onByteEvent: " + ex);
                            this.mRecords.remove(i2);
                        }
                    }
                    i = i2 - 1;
                }
            }
        }
    }

    @Override // android.carsource.IMcuManager
    public void requestMcuIAP(byte subcmd, byte[] data) {
        send((byte) 21, subcmd, data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean AccStatus() {
        return CarStatusService.getCarStatusService().getAccStatus() == 1;
    }

    private void onSystemStatus(McuPacket pkt) {
        if (pkt.subCmd() == 1) {
            boolean hon = (pkt.param(1) & 8) > 0;
            int pwm = pkt.param(3) & 255;
            CarStatusService.getCarStatusService().setDashboardBrightness(hon ? pwm : 0);
        }
    }

    private void onBattery(McuPacket pkt) {
        int subcmd = pkt.subCmd();
        if (subcmd == 3) {
            float vol = (pkt.param(0) & 255) / 10.0f;
            if (vol < 11.5f) {
                Log.i(TAG, "battery voltage going to " + vol);
                this.mBatteryLowTriggered = true;
            } else if (this.mBatteryLowTriggered) {
                Log.i(TAG, "battery voltage going to " + vol);
                this.mBatteryLowTriggered = false;
            }
            this.mBatteryVoltage = vol;
            if (vol < this.mRebootAtLowVoltage) {
                Log.w(TAG, "Reboot due to voltage low at " + vol);
                SystemProperties.set("sys.powerctl", "reboot");
            }
        }
    }

    private void onRotingScreen(McuPacket pkt) {
        int subcmd = pkt.subCmd();
        if (subcmd == 1) {
            int status = pkt.param(0) & McuConstant.K_SWCSETTING;
            String statusStr = status == 1 ? "landscape" : "portrait";
            if (!this.mMcuRotionState.equals(statusStr)) {
                this.mMcuRotionState = statusStr;
                this.H.removeMessages(10);
                this.H.sendMessageDelayed(this.H.obtainMessage(10, this.mMcuRotionState), 10L);
            }
        }
    }

    @Override // android.carsource.IMcuManager
    public float getBattery() {
        return this.mBatteryVoltage;
    }

    private boolean filterEvent() {
        if (this.mMcuUpgradeMode || !AccStatus()) {
            return true;
        }
        return this.mSilenceMode > 0 && !this.mReversActive;
    }

    public boolean interceptFilterEvent(InputEvent event) throws Throwable {
        int action;
        String s;
        boolean bExitFakeOffByTouch = (this.mSilenceMode & 2) != 0;
        boolean bkon = this.mKeyBacklightON;
        if (event instanceof KeyEvent) {
            action = ((KeyEvent) event).getAction();
        } else if (event instanceof MotionEvent) {
            MotionEvent me = (MotionEvent) event;
            action = me.getAction() & McuConstant.K_SWCSETTING;
        } else {
            action = 1;
        }
        boolean ret = (action == 0 || (event instanceof MotionEvent)) ? ResetBacklightStatus() : false;
        if (bExitFakeOffByTouch && action == 0) {
            exitSilence();
        }
        if (6 == (255 & this.mDebugLevel)) {
            Log.i(TAG, "event " + event.toString());
        }
        if (event instanceof MotionEvent) {
            MotionEvent me2 = (MotionEvent) event;
            if (action == 6 || action == 1) {
                synchronized (this.mKeyLock) {
                    Iterator<KeyButton> it = this.mPressedKey.iterator();
                    while (it.hasNext()) {
                        it.next().Released();
                    }
                    this.mPressedKey.clear();
                }
                resetUserActivityTimeout();
            }
            if (me2.getPointerCount() >= 4) {
                if (this.mLaunchTouchCaliMillis == 0) {
                    this.mLaunchTouchCaliMillis = SystemClock.uptimeMillis();
                }
                long diff = SystemClock.uptimeMillis() - this.mLaunchTouchCaliMillis;
                boolean isDisplayOverscanning = SystemProperties.get("ro.debug.overscanning", "0").equals("1");
                if (diff > 2500 && !isDisplayOverscanning) {
                    Log.i(TAG, "Launch touch cali view, diff = " + diff);
                    Message msg = this.H.obtainMessage(5, 1, 0);
                    msg.sendToTarget();
                }
            } else {
                this.mLaunchTouchCaliMillis = 0L;
            }
        }
        if (event instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent) event;
            int keycode = ke.getKeyCode();
            KeyButton key = null;
            if (this.mIsFactoryTestKey || (this.mIsMappingTouchKey && keycode >= 131 && keycode <= 136)) {
                onRawKeyEvent(keycode, action);
                return true;
            }
            if (!bkon || this.mIsMappingTouchKey || bExitFakeOffByTouch) {
                this.mIgnoreEvent = true;
            }
            if (this.mIgnoreEvent && ke.getKeyCode() != 205) {
                if (action == 1) {
                    this.mIgnoreEvent = false;
                    synchronized (this.mKeyLock) {
                        for (KeyButton k : this.mPressedKey) {
                            k.setCancel();
                            k.Released();
                        }
                        this.mPressedKey.clear();
                    }
                }
                return true;
            }
            this.mIgnoreEvent = false;
            boolean ret2 = false;
            if (action == 0) {
                if ((keycode >= 29 && keycode <= 54) || (keycode >= 7 && keycode <= 16)) {
                    if (keycode <= 16) {
                        s = Integer.toString(keycode - 7);
                    } else {
                        s = String.format(Locale.US, "%c", Integer.valueOf((keycode - 29) + 97));
                    }
                    if (ke.isShiftPressed()) {
                        s = s.toUpperCase();
                    }
                    this.H.obtainMessage(7, s).sendToTarget();
                }
                if (keycode >= 66) {
                    this.H.obtainMessage(7, "\n").sendToTarget();
                }
            }
            if (keycode == 26) {
                injectKeyEventTimeout(26, 200);
            } else if (keycode == 140) {
                ret2 = true;
                key = this.mTouchKeyMaps.GetKeyButtonFromCode(McuConstant.K_SOURCE);
            } else if (keycode == 172) {
                ret2 = true;
                injectKeyEvent(McuConstant.K_NAVI, action);
            } else if (keycode != 200) {
                switch (keycode) {
                    case 195:
                        ret2 = true;
                        injectKeyEventTimeout(McuConstant.K_BT, 200);
                        break;
                    case 196:
                        ret2 = true;
                        injectKeyEventTimeout(McuConstant.K_HANGDOWN, 200);
                        break;
                    default:
                        key = this.mTouchKeyMaps.GetKeyButtonNoTolerance(keycode);
                        break;
                }
            } else {
                ret2 = true;
                injectKeyEventTimeout(McuConstant.K_VIDEO, 200);
            }
            if (key != null) {
                if (action == 0) {
                    key.setPressed();
                    synchronized (this.mKeyLock) {
                        this.mPressedKey.add(key);
                    }
                }
                ret2 = true;
            }
            if (action == 1) {
                synchronized (this.mKeyLock) {
                    Iterator<KeyButton> it2 = this.mPressedKey.iterator();
                    while (it2.hasNext()) {
                        it2.next().Released();
                    }
                    this.mPressedKey.clear();
                }
            }
            if (!ret2) {
                boolean ret3 = LinkManager.getInstance().sendMediaButton(keycode, action);
                return ret3;
            }
            return ret2;
        }
        return ret;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void backlightOnoff(int mask) {
        try {
            FileUtils.stringToFile("/sys/class/leds/lcd-backlight/mask", Integer.toHexString(mask));
        } catch (IOException ex) {
            Log.w(TAG, ex.toString());
        }
        if ((mask & 262144) == 262144) {
            Intent intent = new Intent("android.carsource.McuService.BACKLIGHT");
            intent.setFlags(1342177280);
            intent.putExtra("android.intent.extra.EVENT", mask);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT, null);
        }
    }

    public boolean ResetBacklightStatus() {
        if (filterEvent()) {
            return true;
        }
        if (!this.mKeyBacklightON) {
            this.mKeyBacklightON = true;
            backlightOnoff(262144);
            return true;
        }
        return false;
    }

    @Override // android.os.Binder
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        String opt;
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            pw.println("Permission Denial: can't dump mcu_service from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return;
        }
        int opti = 0;
        while (true) {
            if (opti < args.length && (opt = args[opti]) != null && opt.length() > 0 && opt.charAt(0) == '-') {
                opti++;
                if ("-d".equals(opt)) {
                    if (opti < args.length) {
                        this.mDebugLevel = Integer.parseInt(args[opti]);
                        opti++;
                    } else {
                        pw.println("Error: -d option requires debug level");
                        return;
                    }
                } else {
                    if ("-vol".equals(opt)) {
                        if (opti < args.length) {
                            int index = Integer.parseInt(args[opti]);
                            int opti2 = opti + 1;
                            int stream = args.length > opti2 ? Integer.parseInt(args[opti2]) : 3;
                            this.mAudioManager.setStreamVolume(stream, index, 1);
                            pw.println("setStreamVolume(): stream " + stream + ", index " + index);
                            return;
                        }
                        pw.println("Error: -vol index [stream]");
                        return;
                    }
                    if ("-audio".equals(opt)) {
                        for (int stream2 = 0; stream2 < AudioSystem.getNumStreamTypes(); stream2++) {
                            int index2 = this.mAudioManager.getStreamVolume(stream2);
                            int last = this.mAudioManager.getLastAudibleStreamVolume(stream2);
                            boolean active = AudioSystem.isStreamActive(stream2, 0);
                            boolean mute = this.mAudioManager.isStreamMute(stream2);
                            pw.println(String.format(Locale.ENGLISH, "%-26s(%2d) : index %d, last index %d, active %s, mute %s", AudioSystem.STREAM_NAMES[stream2], Integer.valueOf(stream2), Integer.valueOf(index2), Integer.valueOf(last), Boolean.valueOf(active), Boolean.valueOf(mute)));
                        }
                        return;
                    }
                    if ("-k".equals(opt)) {
                        if (args.length > opti) {
                            String kname = args[opti].toUpperCase();
                            int keyCode = keyCodeFromString(kname);
                            if (keyCode == 0) {
                                keyCode = KeyEvent.keyCodeFromString("KEYCODE_" + kname);
                            }
                            int opti3 = opti + 1;
                            int timeout = args.length > opti3 ? Integer.parseInt(args[opti3]) : 50;
                            if (keyCode != 0) {
                                injectKeyEventTimeout(keyCode, timeout);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                }
            } else {
                pw.println("mcu Manager (dumpsys mcu_service)");
                pw.println("filterEvent = " + filterEvent());
                pw.println("    mSilenceMode = 0x" + Integer.toHexString(this.mSilenceMode));
                pw.println("    mKeyBacklightON = " + this.mKeyBacklightON);
                pw.println("    mMcuUpgradeMode = " + this.mMcuUpgradeMode);
                pw.println("    AccStatus = " + AccStatus());
                pw.println("    Reversing = " + this.mReversActive);
                pw.println("mHFPStatus = " + this.mHFPStatus);
                pw.println("mBatteryVoltage = " + this.mBatteryVoltage);
                pw.println("mLastAudibleVolume = " + this.mAudioManager.getLastAudibleStreamVolume(3));
                pw.println("mIsMappingAdcKey = " + this.mIsMappingAdcKey);
                pw.println("mIsMappingPanelKey = " + this.mIsMappingPanelKey);
                pw.println("mIsMappingTouchKey = " + this.mIsMappingTouchKey);
                pw.println("mIsMappingRemoteKey = " + this.mIsMappingRemoteKey);
                pw.println("mIsFactoryTestKey = " + this.mIsFactoryTestKey);
                pw.println("mMotorControlByMcu = " + this.mMotorControlByMcu);
                pw.println("mMcuRotionState = " + this.mMcuRotionState);
                pw.println("mSwcMaps: tolerance " + this.mSwcMaps.TOLERANCE);
                pw.println(this.mSwcMaps.DumpKeymap());
                pw.println("mPanelMaps: tolerance " + this.mPanelMaps.TOLERANCE);
                pw.println("mDebugLevel: " + this.mDebugLevel);
                pw.println(this.mPanelMaps.DumpKeymap());
                return;
            }
        }
    }

    public static class McuPacket {
        private byte[] mData;

        public McuPacket(byte[] data) {
            this.mData = data;
        }

        byte cmd() {
            return this.mData[1];
        }

        public byte subCmd() {
            return this.mData[2];
        }

        public int paramLen() {
            return (this.mData[0] & 255) - 3;
        }

        public byte param(int idx) {
            return this.mData[3 + idx];
        }

        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append("Received MCUD McuPacket: ");
            sb.append(String.format(Locale.ENGLISH, "%02d [%02X] %02X : ", Integer.valueOf(paramLen()), Byte.valueOf(cmd()), Byte.valueOf(subCmd())));
            int N = paramLen();
            for (int i = 0; i < N; i++) {
                sb.append(String.format("%02x,", Integer.valueOf(255 & param(i))));
            }
            sb.append("\n");
            return sb.toString();
        }
    }

    private void processMCUCommand(McuPacket pkt) {
        byte cmd = pkt.cmd();
        byte debug = (byte) (this.mDebugLevel & McuConstant.K_SWCSETTING);
        if (debug == cmd || this.mDebugLevel == 255) {
            Log.v(TAG, pkt.toString());
        }
        if (cmd == -112) {
            onSystemStatus(pkt);
            return;
        }
        if (cmd == 2) {
            onGetCommon(pkt);
            return;
        }
        if (cmd == 4) {
            onGetMcuVersion(pkt);
            return;
        }
        if (cmd == 6) {
            onReceivedKey(pkt);
            return;
        }
        if (cmd == 11) {
            onRotingScreen(pkt);
        } else if (cmd == 21) {
            onMcuIAP(pkt);
        } else if (cmd == 55) {
            onBattery(pkt);
        }
    }

    private void loadLcdSettings() {
        byte[] mBuf = new byte[128];
        Pattern pvcom = Pattern.compile("\\blcdvcom\\s*=\\s*(\\d+)");
        Pattern pavdd = Pattern.compile("\\blcdavdd\\s*=\\s*(\\d+)");
        int count = 0;
        int avdd = -1;
        int vcom = -1;
        try {
            FileInputStream is = new FileInputStream(new File("/dev/carstatus"));
            count = is.read(mBuf);
            is.close();
        } catch (Exception e) {
            Log.e(TAG, e.toString() + Util.getLineInfo());
        }
        String str = new String(mBuf, 0, count);
        int idx = str.indexOf("\n");
        if (idx > 0) {
            str = str.substring(idx + 1);
        }
        try {
            Matcher m = pvcom.matcher(str);
            if (m.find()) {
                vcom = Integer.parseInt(m.group(1));
            }
            Matcher m2 = pavdd.matcher(str);
            if (m2.find()) {
                avdd = Integer.parseInt(m2.group(1));
            }
        } catch (Exception e2) {
            Log.e(TAG, e2 + str + Util.getLineInfo());
        }
        ContentResolver cr = this.mContext.getContentResolver();
        if (vcom > 0) {
            Settings.System.putInt(cr, HConfig.lcd_vcom, vcom);
        }
        if (avdd > 0) {
            Settings.System.putInt(cr, HConfig.lcd_avdd, avdd);
        }
    }

    private void loadLcdBacklightRange() {
        int backlight_range;
        ContentResolver cr = this.mContext.getContentResolver();
        int backlight_range2 = Settings.System.getInt(cr, HConfig.lcd_backlight_range, -1);
        int nvrom_range = HConfig.getNvrom(7);
        if (backlight_range2 > 0 && backlight_range2 != nvrom_range) {
            onBacklightRangeChange(backlight_range2);
            return;
        }
        if (nvrom_range > 0) {
            sendCmdCarStatus("backlight_range=" + nvrom_range);
            return;
        }
        byte[] mBuf = new byte[CarletterInterface.KEYCODE_KNOB_LEFT];
        Pattern pbl_range = Pattern.compile("\\bbacklight_range\\s*=\\s*(0x[0-9a-fA-F]+)");
        int count = 0;
        try {
            FileInputStream is = new FileInputStream(new File("/dev/carstatus"));
            count = is.read(mBuf);
            is.close();
        } catch (Exception e) {
            Log.e(TAG, e.toString() + Util.getLineInfo());
        }
        String str = new String(mBuf, 0, count);
        try {
            Matcher m = pbl_range.matcher(str);
            if (m.find() && (backlight_range = Integer.decode(m.group(1)).intValue()) > 0) {
                HConfig.setNvrom(7, backlight_range);
            }
        } catch (Exception e2) {
            Log.e(TAG, e2 + str + Util.getLineInfo());
        }
    }

    private void initSettings() {
        int usbPath;
        ContentResolver cr = this.mContext.getContentResolver();
        setLowBatteryThreshold(cr);
        setPowerdownAfterAccoff(Settings.System.getInt(cr, HConfig.power_down_afteraccoff, 4320));
        setCanType(Settings.System.getInt(cr, HConfig.can_type_settings, 17));
        setSwcPullup(Settings.System.getInt(cr, HConfig.swc_pullup, 0));
        sendCmdCarStatus("accoff_delay=" + Settings.System.getInt(cr, HConfig.accoff_delay, McuConstant.K_DVD));
        sendCmdCarStatus("radio_ant=" + Settings.System.getInt(cr, HConfig.radio_ant_extpwr, 0));
        int mainBoardType = HConfig.instance().mainBoardCode();
        if (mainBoardType == 301 && (usbPath = Settings.System.getInt(cr, HConfig.usb_work_path, -1)) >= 0 && usbPath <= 1) {
            Log.i(TAG, "set initial usb work path: " + usbPath);
            sendCmdCarStatus("usb_path=" + usbPath);
        }
        int v = Settings.System.getInt(cr, HConfig.reardisplay_status, 0);
        SystemProperties.set(v > 0 ? "ctl.start" : "ctl.stop", "uvd");
        try {
            FileUtils.stringToFile("/tmp/tvout/enable", v > 0 ? "1\n" : "0\n");
        } catch (IOException e) {
        }
        int vcom = Settings.System.getInt(cr, HConfig.lcd_vcom, -1);
        if (vcom >= 0) {
            sendCmdCarStatus("vcom=" + Settings.System.getInt(cr, HConfig.lcd_vcom, 0));
            sendCmdCarStatus("avdd=" + Settings.System.getInt(cr, HConfig.lcd_avdd, 0));
        } else {
            loadLcdSettings();
        }
        loadLcdBacklightRange();
        int mKnobActuatorStep = Settings.System.getInt(cr, HConfig.knob_actuator_step, 0);
        if (mKnobActuatorStep != 0) {
            setKnobActuatorStepToMcu(0, 1, 0);
        }
        int vol = Settings.System.getInt(cr, HConfig.audio_amp_percentage, 100);
        SystemProperties.set(KEY_AUDIO_PERCENTAGE, Integer.toString(vol));
        ExtAudioMuxer.setMastVolumePercentage(vol);
        String strv = Settings.System.getString(cr, HConfig.audio_lr_balance);
        if (!TextUtils.isEmpty(strv)) {
            SystemProperties.set(KEY_AUDIO_BALANCE, strv);
        }
        if (this.mMotorControlByMcu) {
            Log.i(TAG, "setRotationSwToMcu 1");
            setRotationSwToMcu(1);
            this.H.removeMessages(11);
            this.H.sendMessageDelayed(this.H.obtainMessage(11), 500L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLowBatteryThreshold(ContentResolver cr) {
        int mute = Settings.System.getInt(cr, HConfig.power_low_threshold, 90);
        int reboot = Math.min(75, Settings.System.getInt(cr, HConfig.reboot_low_threshold, 55));
        send(McuConstant.MPU_CMD_SETTINGS, (byte) 3, new byte[]{(byte) mute, (byte) reboot});
        this.mRebootAtLowVoltage = reboot / 10.0f;
    }

    private void setPowerdownAfterAccoff(int minutes) {
        send(McuConstant.MPU_CMD_SETTINGS, (byte) 4, new byte[]{(byte) (minutes >> 8), (byte) minutes});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPoweroffTimeout(int minutes) {
        int minutes2 = minutes <= 0 ? 10080 : minutes;
        send(McuConstant.MPU_CMD_SETTINGS, (byte) 9, new byte[]{(byte) minutes2, (byte) (minutes2 >> 8)});
    }

    private void setRotationSwToMcu(int sw) {
        send(McuConstant.MPU_CMD_SETTINGS, (byte) 13, new byte[]{(byte) sw});
    }

    private void setRotationStatusToMcu(int status) {
        send(McuConstant.MPU_CMD_SETTINGS, (byte) 14, new byte[]{(byte) status});
    }

    private void setKnobActuatorStepToMcu(int encoderType, int step, int resistance) {
        send(McuConstant.MPU_CMD_SETTINGS, (byte) 11, new byte[]{(byte) encoderType, (byte) step, (byte) resistance});
    }

    private void setPanelLedColor(int color) {
        send(McuConstant.MPU_CMD_SETTINGS, (byte) 1, new byte[]{(byte) color});
    }

    private void setCanType(int can) {
        send(McuConstant.MPU_CMD_SETTINGS, (byte) 2, new byte[]{(byte) (can >> 8), (byte) can});
    }

    public void setAccoffMode(int mode) {
        send(McuConstant.MPU_CMD_SETTINGS, (byte) 7, new byte[]{(byte) mode});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSwcPullup(int pullup) {
        send(McuConstant.MPU_CMD_SETTINGS, (byte) 6, new byte[]{(byte) pullup});
        sendCmdCarStatus("swcpull=" + pullup);
    }

    @Override // android.carsource.IMcuManager
    public void requestBeep(int type) {
        send((byte) 8, (byte) 19, new byte[]{(byte) (type & McuConstant.K_SWCSETTING)});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestWatchDog() {
        send((byte) 2, (byte) 3, new byte[0]);
    }

    public void sendBacklightBrightness(int bright) {
        byte br = (byte) (((bright & McuConstant.K_SWCSETTING) * 100) >> 8);
        send((byte) 2, (byte) 11, new byte[]{br});
    }

    public void requestNavigation(boolean z) {
        send((byte) 8, (byte) 16, new byte[]{z ? (byte) 1 : (byte) 0});
    }

    public void requestHFP(boolean z) {
        send((byte) 8, (byte) 18, new byte[]{z ? (byte) 1 : (byte) 0});
    }

    public int requestRadioMsg(byte subcmd, byte[] msg) {
        return send((byte) 16, subcmd, msg);
    }

    public void requestSourceMux(byte path) {
        send((byte) 7, (byte) 3, new byte[]{1, path});
    }

    @Override // android.carsource.IMcuManager
    public void requestSendIR(byte[] msg) {
        send((byte) 9, (byte) 1, msg);
    }

    public void requestRotingScreen() {
        send((byte) 11, (byte) 1, new byte[0]);
    }

    @Override // android.carsource.IMcuManager
    public void requestTouchdown(int x, int y) {
        send((byte) 6, (byte) 2, new byte[]{(byte) ((x >> 8) & McuConstant.K_SWCSETTING), (byte) (x & McuConstant.K_SWCSETTING), (byte) ((y >> 8) & McuConstant.K_SWCSETTING), (byte) (y & McuConstant.K_SWCSETTING)});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMcuConfig(int mode) {
        send(McuConstant.MPU_CMD_SETTINGS, (byte) 10, new byte[]{(byte) mode});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRevEnable(int enable) {
        try {
            FileUtils.stringToFile("/dev/carstatus", "rev_disable=" + Integer.toString(enable));
        } catch (IOException e) {
            Log.w(TAG, "ERROR " + Util.getLineInfo());
            e.printStackTrace();
        }
    }

    public void bootphase(String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        SystemProperties.set("sys.running.phase", str);
        Log.i(TAG, "System running phase at " + str);
    }

    private void loadKeymap() {
        File file = new File(PanelKeyFile);
        File file2 = new File("/system/etc/keymaps.xml");
        this.mPanelMaps.setTolerance(Settings.System.getInt(this.mContext.getContentResolver(), HConfig.panel_tolerance, 10));
        if (file.exists()) {
            this.mPanelMaps.load(file);
        } else if (file2.exists() && 1 == SystemProperties.getInt("persist.sys.preset.panel_key", 0)) {
            this.mPanelMaps.load(file2);
        }
        this.mTouchKeyMaps.load(file2);
        this.mTouchKeyMaps.load(new File(TouchKeyFile));
        this.mSwcMaps.setRepeat(Settings.System.getInt(this.mContext.getContentResolver(), HConfig.swc_repeat_time, -1));
        this.mSwcMaps.setLongPressTimeout(Settings.System.getInt(this.mContext.getContentResolver(), HConfig.swc_long_press_time, -1));
        reloadSwc();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reloadSwc() {
        File[] fs;
        String swc = Settings.System.getString(this.mContext.getContentResolver(), HConfig.swc_type_predefined);
        if (!TextUtils.isEmpty(swc)) {
            File swcfile = new File("/apd/swcmaps/" + swc + ".xml");
            if (swcfile.exists()) {
                this.mSwcMaps.clear();
                this.mSwcMaps.load(swcfile);
                Settings.System.putInt(this.mContext.getContentResolver(), HConfig.swc_pullup, this.mSwcMaps.getPullup());
                return;
            }
        }
        File file = new File("/data/system/swcmaps.xml");
        if ("0".equals(swc) || (TextUtils.isEmpty(swc) && file.exists())) {
            this.mSwcMaps.setTolerance(Settings.System.getInt(this.mContext.getContentResolver(), HConfig.swc_tolerance, 10));
            this.mSwcMaps.clear();
            this.mSwcMaps.load(file);
            Settings.System.putInt(this.mContext.getContentResolver(), HConfig.swc_pullup, this.mSwcMaps.getPullup());
            return;
        }
        File file2 = new File("/apd/swcmaps/");
        if (file2.exists() && file2.isDirectory() && (fs = file2.listFiles()) != null && fs.length == 1) {
            Log.i(TAG, "reloadSwc swc file " + fs[0]);
            this.mSwcMaps.clear();
            this.mSwcMaps.load(fs[0]);
            Settings.System.putInt(this.mContext.getContentResolver(), HConfig.swc_pullup, this.mSwcMaps.getPullup());
        }
    }

    @Override // android.carsource.IMcuManager
    public void applyVideoPath(int path) {
        Log.i(TAG, "applyVideoPath to path " + path);
        if (path == 0) {
            ExtAudioMuxer.SendCmdVideoMux("path=AUX");
            return;
        }
        if (path == 1) {
            ExtAudioMuxer.SendCmdVideoMux("path=REV");
            return;
        }
        if (path == 2) {
            ExtAudioMuxer.SendCmdVideoMux("path=CMMB");
            return;
        }
        if (path == 3) {
            ExtAudioMuxer.SendCmdVideoMux("path=FCAMERA");
            return;
        }
        throw new RuntimeException("applyVideoPath(" + path + "), Unsupport path");
    }

    private class REMOTEMAP {
        public int TOLERANCE;
        private int mPullup = 0;
        private final HashMap<Integer, KeyButton> mKeyMap = new HashMap<>();
        private int id = -1;
        private int mRepeatInterval = 50;
        private int mLongPressTimeout = ViewConfiguration.getLongPressTimeout();

        public REMOTEMAP(int tolerance) {
            this.TOLERANCE = tolerance;
        }

        public void setTolerance(int tolerance) {
            this.TOLERANCE = tolerance;
        }

        public void setRepeat(int repeat) {
            if (repeat > 0) {
                this.mRepeatInterval = repeat;
            }
        }

        public void setLongPressTimeout(int longPressTimeout) {
            if (longPressTimeout > 0) {
                this.mLongPressTimeout = longPressTimeout;
            }
        }

        public int getRemoteId() {
            return this.id;
        }

        public void setRemoteId(int id) {
            this.id = id;
        }

        public int getPullup() {
            return this.mPullup;
        }

        boolean load(int id) {
            this.id = id;
            File file = new File(String.format(Locale.US, "/apd/remote-%x.kl", Integer.valueOf(id)));
            if (file.exists()) {
                load(file);
                return true;
            }
            File file2 = new File(String.format(Locale.US, "/system/usr/keylayout/remote-%x.kl", Integer.valueOf(id)));
            if (!file2.exists()) {
                return false;
            }
            load(file2);
            return true;
        }

        boolean load(File file) {
            FileReader reader;
            int adc;
            Log.i(McuService.TAG, "load keymap file " + file);
            try {
                FileReader reader2 = new FileReader(file);
                int i = 1;
                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(reader2);
                    XmlUtils.beginDocument(parser, "keymaps");
                    while (true) {
                        XmlUtils.nextElement(parser);
                        try {
                            if (parser.getEventType() == i) {
                                reader2.close();
                                return true;
                            }
                            String name = parser.getName();
                            try {
                                if ("key".equalsIgnoreCase(name)) {
                                    String value = parser.getAttributeValue(null, "value");
                                    String kname = parser.getAttributeValue(null, "name");
                                    String repeat = parser.getAttributeValue(null, "repeat");
                                    String longpress = parser.getAttributeValue(null, "longpress");
                                    String longpresstimeout = parser.getAttributeValue(null, "longpress_timeout");
                                    String longpress2 = parser.getAttributeValue(null, "longpress2");
                                    String longpresstimeout2 = parser.getAttributeValue(null, "longpress_timeout2");
                                    try {
                                        value = value.trim();
                                        try {
                                            String[] v = value.split(",");
                                            if (v.length == i) {
                                                try {
                                                    adc = 0 + McuService.parseInt(v[0]);
                                                } catch (NullPointerException e) {
                                                    reader = reader2;
                                                    Log.e(McuService.TAG, "NullPointerException:" + value);
                                                    XmlUtils.skipCurrentTag(parser);
                                                    reader2 = reader;
                                                    i = 1;
                                                } catch (NumberFormatException e2) {
                                                    reader = reader2;
                                                    Log.e(McuService.TAG, "NumberFormatException:" + value);
                                                    XmlUtils.skipCurrentTag(parser);
                                                    reader2 = reader;
                                                    i = 1;
                                                }
                                            } else {
                                                int i2 = McuService.parseInt(v[0]) << 16;
                                                int adc2 = McuService.parseInt(v[1]);
                                                adc = adc2 + i2;
                                            }
                                            KeyButton.KeyButtonATTR attr = new KeyButton.KeyButtonATTR();
                                            attr.KeyCode = McuService.keyCodeFromString(kname);
                                            attr.Repeat = repeat == null ? 0 : McuService.parseInt(repeat);
                                            attr.LongPressKeyCode = McuService.keyCodeFromString(longpress);
                                            attr.LongPressKeyCode2 = McuService.keyCodeFromString(longpress2);
                                            attr.LongPressTimeout = longpresstimeout == null ? 0 : McuService.parseInt(longpresstimeout);
                                            attr.LongPressTimeout2 = longpresstimeout2 == null ? 0 : McuService.parseInt(longpresstimeout2);
                                            reader = reader2;
                                            try {
                                                Log.i(McuService.TAG, "adc 0x" + Integer.toHexString(adc) + ", name " + kname + ", KeyCode " + attr.KeyCode);
                                                try {
                                                    if (attr.KeyCode == 24 || attr.KeyCode == 25) {
                                                        attr.Repeat = attr.Repeat == 0 ? this.mRepeatInterval : attr.Repeat;
                                                    }
                                                    if ((attr.KeyCode == 24 || attr.KeyCode == 25 || attr.KeyCode == 3 || attr.KeyCode == 88 || attr.KeyCode == 87) && attr.LongPressTimeout == 0) {
                                                        attr.LongPressTimeout = this.mLongPressTimeout;
                                                    }
                                                    int adc3 = adc & 2147450879;
                                                    try {
                                                        try {
                                                            this.mKeyMap.put(Integer.valueOf(adc3), new KeyButton(attr, adc3, McuService.this.mSenderThread.getLooper(), McuService.this.KeyCallback));
                                                        } catch (NullPointerException e3) {
                                                            value = value;
                                                            Log.e(McuService.TAG, "NullPointerException:" + value);
                                                            XmlUtils.skipCurrentTag(parser);
                                                        } catch (NumberFormatException e4) {
                                                            value = value;
                                                            Log.e(McuService.TAG, "NumberFormatException:" + value);
                                                            XmlUtils.skipCurrentTag(parser);
                                                        }
                                                    } catch (NullPointerException e5) {
                                                        value = value;
                                                    } catch (NumberFormatException e6) {
                                                        value = value;
                                                    }
                                                } catch (NullPointerException e7) {
                                                    Log.e(McuService.TAG, "NullPointerException:" + value);
                                                    XmlUtils.skipCurrentTag(parser);
                                                    reader2 = reader;
                                                    i = 1;
                                                } catch (NumberFormatException e8) {
                                                    Log.e(McuService.TAG, "NumberFormatException:" + value);
                                                    XmlUtils.skipCurrentTag(parser);
                                                    reader2 = reader;
                                                    i = 1;
                                                }
                                            } catch (NullPointerException e9) {
                                            } catch (NumberFormatException e10) {
                                            }
                                        } catch (NullPointerException e11) {
                                            reader = reader2;
                                        } catch (NumberFormatException e12) {
                                            reader = reader2;
                                        }
                                    } catch (NullPointerException e13) {
                                        reader = reader2;
                                    } catch (NumberFormatException e14) {
                                        reader = reader2;
                                    }
                                } else {
                                    reader = reader2;
                                }
                                if ("pullingup".equals(name)) {
                                    String value2 = parser.getAttributeValue(null, "value");
                                    try {
                                        this.mPullup = McuService.parseInt(value2.trim());
                                    } catch (NumberFormatException e15) {
                                    }
                                }
                                if ("tolerance".equalsIgnoreCase(name)) {
                                    String value3 = parser.getAttributeValue(null, "value");
                                    try {
                                        int toler = McuService.parseInt(value3.trim());
                                        this.TOLERANCE = Math.min(Math.max(toler, 5), 40);
                                    } catch (NumberFormatException e16) {
                                    }
                                }
                                XmlUtils.skipCurrentTag(parser);
                                reader2 = reader;
                                i = 1;
                            } catch (IOException e17) {
                                e = e17;
                            } catch (XmlPullParserException e18) {
                                e = e18;
                                Log.w(McuService.TAG, "Got execption remote keylayout.", e);
                                return true;
                            }
                        } catch (IOException e19) {
                            e = e19;
                        } catch (XmlPullParserException e20) {
                            e = e20;
                        }
                        Log.w(McuService.TAG, "Got execption remote keylayout.", e);
                        return true;
                    }
                } catch (IOException e21) {
                    e = e21;
                } catch (XmlPullParserException e22) {
                    e = e22;
                }
            } catch (FileNotFoundException e23) {
                Log.w(McuService.TAG, "Couldn't find or open " + file);
                return false;
            }
        }

        public void save(File file, HashMap<Integer, KeyMap> maps, int Pullup) {
            file.delete();
            File tmp = null;
            try {
                try {
                    tmp = File.createTempFile("journal", "", file.getParentFile());
                    FileOutputStream fstr = new FileOutputStream(tmp);
                    FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
                    fastXmlSerializer.setOutput(fstr, "utf-8");
                    fastXmlSerializer.startDocument(null, true);
                    fastXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    fastXmlSerializer.startTag(null, "keymaps");
                    for (Map.Entry<Integer, KeyMap> entry : maps.entrySet()) {
                        int adc = entry.getKey().intValue();
                        int ksrc = McuService.this.shortToInt((short) (adc >> 16));
                        int kadc = McuService.this.shortToInt((short) adc);
                        String val = Integer.toString(ksrc) + ", " + Integer.toString(kadc);
                        int key = entry.getValue().kcode;
                        int lk = entry.getValue().longcode;
                        if (key != -1 || lk > 0) {
                            fastXmlSerializer.startTag(null, "key");
                            fastXmlSerializer.attribute(null, "value", val);
                            fastXmlSerializer.attribute(null, "name", McuService.keyCodeToString(key));
                            if (lk > 0) {
                                fastXmlSerializer.attribute(null, "longpress", McuService.keyCodeToString(lk));
                            }
                            fastXmlSerializer.endTag(null, "key");
                        }
                    }
                    if (Pullup >= 0) {
                        fastXmlSerializer.startTag(null, "pullingup");
                        fastXmlSerializer.attribute(null, "value", Integer.toString(Pullup));
                        fastXmlSerializer.endTag(null, "pullingup");
                    }
                    fastXmlSerializer.endTag(null, "keymaps");
                    fastXmlSerializer.endDocument();
                    FileUtils.sync(fstr);
                    fstr.close();
                    if (tmp != null) {
                        tmp.renameTo(file);
                        Util.nativeSync();
                    }
                } catch (IOException e) {
                    Log.w(McuService.TAG, "Unable to write keymaps file, changes will be lost at reboot", e);
                    if (tmp != null) {
                    }
                }
                this.mKeyMap.clear();
                load(file);
            } catch (Throwable th) {
                if (tmp != null) {
                    tmp.renameTo(file);
                    Util.nativeSync();
                }
                throw th;
            }
        }

        public void clear() {
            this.mKeyMap.clear();
        }

        public void put(KeyButton key) {
            this.mKeyMap.put(Integer.valueOf(key.mAdc), key);
        }

        public int size() {
            return this.mKeyMap.size();
        }

        public KeyButton GetKeyButtonNoTolerance(int adc) {
            return this.mKeyMap.get(Integer.valueOf(adc));
        }

        public KeyButton GetKeyButton(int adc) {
            int tolrance = this.TOLERANCE;
            if ((16711680 & adc) == 458752) {
                tolrance = 0;
            }
            if (tolrance == 0) {
                return this.mKeyMap.get(Integer.valueOf(adc));
            }
            for (Map.Entry<Integer, KeyButton> entry : this.mKeyMap.entrySet()) {
                int ka = entry.getKey().intValue();
                if (adc + tolrance >= ka && adc - tolrance <= ka) {
                    return entry.getValue();
                }
            }
            return null;
        }

        public KeyButton GetKeyButtonFromCode(int keycode) {
            for (Map.Entry<Integer, KeyButton> entry : this.mKeyMap.entrySet()) {
                KeyButton kb = entry.getValue();
                if (kb.mKeyCode == keycode) {
                    return kb;
                }
            }
            return null;
        }

        public String DumpKeymap() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Integer, KeyButton> entry : this.mKeyMap.entrySet()) {
                int ka = entry.getKey().intValue();
                KeyButton kb = entry.getValue();
                int ksrc = McuService.this.shortToInt((short) (ka >> 16));
                int kadc = McuService.this.shortToInt((short) ka);
                sb.append("ADC ");
                sb.append(ksrc);
                sb.append(", ");
                sb.append(kadc);
                sb.append(", ADC2 0x");
                sb.append(Integer.toHexString(kb.mAdc));
                sb.append(", KeyCode 0x");
                sb.append(Integer.toHexString(kb.mKeyCode));
                sb.append("\n");
            }
            return sb.toString();
        }

        public void KeymaptoArray(List<KeyMap> keys) {
            for (Map.Entry<Integer, KeyButton> entry : this.mKeyMap.entrySet()) {
                KeyButton kb = entry.getValue();
                KeyMap k = new KeyMap();
                k.kadc = kb.mAdc;
                k.kcode = kb.mKeyCode;
                k.longcode = kb.mLongpressable ? kb.mAttr.LongPressKeyCode : -1;
                keys.add(k);
            }
        }

        public int[] GetAdcFromKeycode(int keycode) {
            int[] ret = new int[8];
            int total = 0;
            for (Map.Entry<Integer, KeyButton> entry : this.mKeyMap.entrySet()) {
                int ka = entry.getKey().intValue();
                KeyButton kb = entry.getValue();
                if (kb.mKeyCode == keycode) {
                    ret[total] = ka;
                    total++;
                }
            }
            int[] result = new int[total];
            for (int i = 0; i < total; i++) {
                result[i] = ret[i];
            }
            return result;
        }
    }

    static {
        populateKeycodeSymbolicNames();
    }

    public static String keyCodeToString(int keyCode) {
        String symbolicName = KEYCODE_SYMBOLIC_NAMES.get(keyCode);
        return symbolicName != null ? symbolicName : Integer.toString(keyCode);
    }

    public static int keyCodeFromString(String symbolicName) {
        if (symbolicName == null) {
            return -1;
        }
        int count = KEYCODE_SYMBOLIC_NAMES.size();
        for (int i = 0; i < count; i++) {
            if (symbolicName.equalsIgnoreCase(KEYCODE_SYMBOLIC_NAMES.valueAt(i))) {
                return KEYCODE_SYMBOLIC_NAMES.keyAt(i);
            }
        }
        return -1;
    }

    private static void populateKeycodeSymbolicNames() {
        SparseArray<String> names = KEYCODE_SYMBOLIC_NAMES;
        names.append(McuConstant.K_EJECT, "EJECT");
        names.append(25, "VOL-");
        names.append(24, "VOL+");
        names.append(McuConstant.K_SOURCE, "SOURCE");
        names.append(McuConstant.K_MUTE, "MUTE");
        names.append(91, "MICMUTE");
        names.append(McuConstant.K_REPEAT, "REPEAT");
        names.append(19, "UP");
        names.append(20, "DOWN");
        names.append(21, "LEFT");
        names.append(22, "RIGHT");
        names.append(66, "ENTER");
        names.append(26, "POWER");
        names.append(86, "STOP");
        names.append(7, "K_0");
        names.append(8, "K_1");
        names.append(9, "K_2");
        names.append(10, "K_3");
        names.append(11, "K_4");
        names.append(12, "K_5");
        names.append(13, "K_6");
        names.append(14, "K_7");
        names.append(15, "K_8");
        names.append(16, "K_9");
        names.append(67, "K_CLEAR");
        names.append(McuConstant.K_HFP_AUDIO, "HFP_AUDIO");
        names.append(288, "ANGLE");
        names.append(289, "TITLE");
        names.append(290, "SUB_TITLE");
        names.append(McuConstant.K_DVD_MENU, "DVD_MENU");
        names.append(McuConstant.K_DVD_SLOW, "SLOW");
        names.append(McuConstant.K_DVD_ZOOM, "ZOOM");
        names.append(McuConstant.K_RDSTA, "RDSTA");
        names.append(89, "REWIND");
        names.append(90, "FAST_FORWARD");
        names.append(McuConstant.K_SCAN, "SCAN");
        names.append(McuConstant.K_RANDOM, "RANDOM");
        names.append(McuConstant.K_AS, "AS/PS");
        names.append(McuConstant.K_NAVI, "GPS");
        names.append(McuConstant.K_TV, "TV");
        names.append(McuConstant.K_DVD, "DVD");
        names.append(301, "AUXIN");
        names.append(McuConstant.K_F_AUX_IN, "F_AUXIN");
        names.append(McuConstant.K_BT, "BT");
        names.append(McuConstant.K_BTMUSIC, "BTMUSIC");
        names.append(McuConstant.K_IPOD, "IPOD");
        names.append(McuConstant.K_BAND, "FM/AM");
        names.append(85, "PLAY/PAUSE");
        names.append(4, "BACK");
        names.append(3, "HOME");
        names.append(82, "MENU");
        names.append(McuConstant.K_ANDROID, "ANDROID");
        names.append(McuConstant.K_HANGDOWN, "HANG_DOWN");
        names.append(McuConstant.K_EQ, "EQ");
        names.append(17, "ASTERISK");
        names.append(18, "POUND");
        names.append(McuConstant.K_BACKLIGHT, "BKLIGHT");
        names.append(McuConstant.K_BKLIGHT_OFF, "BKLIGHT_ON");
        names.append(McuConstant.K_BKLIGHT_ON, "BKLIGHT_OFF");
        names.append(McuConstant.K_SCROLL_R, "SCROLL_R");
        names.append(McuConstant.K_SCROLL_L, "SCROLL_L");
        names.append(McuConstant.K_ASSISTANT, "ASSIST");
        names.append(McuConstant.K_SETUP, "SETUP");
        names.append(McuConstant.K_MUSIC, "MUSIC");
        names.append(McuConstant.K_VIDEO, "VIDEO");
        names.append(McuConstant.K_REBOOT, "REBOOT");
        names.append(87, "NEXT");
        names.append(88, "PREV");
        names.append(McuConstant.K_PS0, "P0");
        names.append(McuConstant.K_PS1, "P1");
        names.append(McuConstant.K_PS2, "P2");
        names.append(McuConstant.K_PS3, "P3");
        names.append(McuConstant.K_PS4, "P4");
        names.append(McuConstant.K_PS5, "P5");
        names.append(McuConstant.K_PS6, "P6");
        names.append(McuConstant.K_PS7, "P7");
        names.append(McuConstant.K_PS8, "P8");
        names.append(McuConstant.K_PS9, "P9");
        names.append(McuConstant.K_LOUD, "LOUD");
        names.append(McuConstant.K_STEREO, "STEREO");
        names.append(McuConstant.K_PBC, "PBC");
        names.append(62, "SPACE");
        names.append(McuConstant.K_PLAY, "PLAY");
        names.append(McuConstant.K_PAUSE, "PAUSE");
        names.append(McuConstant.K_TIMESETTTING, "TIMESET");
        names.append(McuConstant.K_USER1, "USER1");
        names.append(McuConstant.K_USER2, "USER2");
        names.append(McuConstant.K_MOTOR, "MOTOR");
        names.append(McuConstant.K_LAUNCHER_APPS, "LAUNCHER_APPS");
        names.append(McuConstant.K_STEP_FORWARD, "STEP_FORWARD");
        names.append(McuConstant.K_STEP_BACKWARD, "STEP_BACKWARD");
        names.append(337, "SCREEN_SAVER");
        names.append(336, "POWER_EX");
    }

    public static int parseInt(String str) {
        int radix = 10;
        String s = str.trim();
        if (s.startsWith("0x") || s.startsWith("0X")) {
            s = s.substring(2);
            radix = 16;
        }
        return Integer.parseInt(s, radix);
    }

    private class SettingsObserver extends ContentObserver {
        SettingsObserver() {
            super(new Handler());
            ContentResolver cr = McuService.this.mContext.getContentResolver();
            cr.registerContentObserver(Settings.System.getUriFor("pointer_location"), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.swc_pullup), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.accoff_delay), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.lcd_vcom), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.lcd_avdd), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.radio_ant_extpwr), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.reardisplay_status), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.swc_type_predefined), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.audio_amp_percentage), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.audio_lr_balance), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.panel_tolerance), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.swc_tolerance), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.car_revs_support), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.poweroff_timeout), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.mcu_aux_mode), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.lcd_backlight_range), false, this);
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.usb_work_path), false, this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange);
            ContentResolver cr = McuService.this.mContext.getContentResolver();
            String key = uri.toString().replace("content://settings/system/", "");
            Log.i(McuService.TAG, "Setting changed " + uri + ", " + key);
            switch (key) {
                case "swc_pullup":
                    McuService.this.setSwcPullup(Settings.System.getInt(cr, HConfig.swc_pullup, 0));
                    break;
                case "pointer_location":
                    McuService.this.mPointerLocation = Settings.System.getInt(cr, "pointer_location", 0) > 0;
                    McuService.this.nativeSetTouchEventReport(McuService.this.mPointerLocation);
                    McuService.this.enableDebugView(McuService.this.mPointerLocation);
                    break;
                case "accoff_delay":
                    McuService.sendCmdCarStatus("accoff_delay=" + Settings.System.getInt(cr, HConfig.accoff_delay, McuConstant.K_DVD));
                    break;
                case "hcnhw/lcd_vcom":
                    int vcom = Settings.System.getInt(cr, HConfig.lcd_vcom, 0);
                    McuService.sendCmdCarStatus("vcom=" + vcom);
                    HConfig.setNvrom(3, vcom);
                    break;
                case "hcnhw/lcd_avdd":
                    int avdd = Settings.System.getInt(cr, HConfig.lcd_avdd, 0);
                    McuService.sendCmdCarStatus("avdd=" + avdd);
                    HConfig.setNvrom(2, avdd);
                    break;
                case "hcnhw/lcd_bl_range":
                    int range = Settings.System.getInt(cr, HConfig.lcd_backlight_range, -1);
                    McuService.this.onBacklightRangeChange(range);
                    break;
                case "radio_ant_extpwr":
                    McuService.sendCmdCarStatus("radio_ant=" + Settings.System.getInt(cr, HConfig.radio_ant_extpwr, 0));
                    break;
                case "reardisplay_status":
                    int v = Settings.System.getInt(cr, HConfig.reardisplay_status, 0);
                    SystemProperties.set(v > 0 ? "ctl.start" : "ctl.stop", "uvd");
                    try {
                        FileUtils.stringToFile("/tmp/tvout/enable", v > 0 ? "1\n" : "0\n");
                        break;
                    } catch (IOException e) {
                        return;
                    }
                    break;
                case "hcnhw/swc_type_predefined":
                    McuService.this.reloadSwc();
                    break;
                case "poweroff_timeout":
                    int minutes = Settings.System.getInt(cr, HConfig.poweroff_timeout, 4320);
                    McuService.this.setPoweroffTimeout(minutes);
                    break;
                case "hcnhw/car_revs_support":
                    McuService.this.setRevEnable(Settings.System.getInt(cr, HConfig.car_revs_support, 0));
                    break;
                case "hcnhw/mcu_aux_mode":
                    McuService.this.setMcuConfig(Settings.System.getInt(cr, HConfig.mcu_aux_mode, 0));
                    break;
                case "hcnhw/audio_amp_percentage":
                    int vol = Settings.System.getInt(cr, HConfig.audio_amp_percentage, 100);
                    SystemProperties.set(McuService.KEY_AUDIO_PERCENTAGE, Integer.toString(vol));
                    ExtAudioMuxer.setMastVolumePercentage(vol);
                    break;
                case "audio_lr_balance":
                    String strv = Settings.System.getString(cr, HConfig.audio_lr_balance);
                    if (!TextUtils.isEmpty(strv)) {
                        SystemProperties.set(McuService.KEY_AUDIO_BALANCE, strv);
                        break;
                    }
                    break;
                case "panel_tolerance":
                    McuService.this.mPanelMaps.setTolerance(Settings.System.getInt(McuService.this.mContext.getContentResolver(), HConfig.panel_tolerance, 10));
                    break;
                case "swc_tolerance":
                    McuService.this.mSwcMaps.setTolerance(Settings.System.getInt(McuService.this.mContext.getContentResolver(), HConfig.swc_tolerance, 7));
                    break;
                case "reboot_low_threshold":
                case "power_low_threshold":
                    McuService.this.setLowBatteryThreshold(cr);
                    break;
                case "usb_switch_key":
                    int mainBoardType = HConfig.instance().mainBoardCode();
                    if (mainBoardType == 301) {
                        int usbPath = Settings.System.getInt(McuService.this.mContext.getContentResolver(), HConfig.usb_work_path, 0);
                        if (usbPath != 1) {
                            usbPath = 0;
                        }
                        Log.i(McuService.TAG, "set cust usb work path: " + usbPath);
                        McuService.sendCmdCarStatus("usb_path=" + usbPath);
                        break;
                    }
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int shortToInt(short arg) {
        int kx = 65535 & arg;
        return kx < 32512 ? kx : kx | (-32768);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveTouchCalibratResult(String str, boolean tosave) {
        for (String CalibFile : this.TouchCalibFiles) {
            Util.writeStringToFile(CalibFile, str);
        }
        if (tosave) {
            Settings.System.putString(this.mContext.getContentResolver(), HConfig.touch_calibraion, str);
        }
    }

    @Override // android.carsource.IMcuManager
    public void setTouchIrqType(int type) {
        String irq = Integer.toString(type);
        try {
            FileUtils.stringToFile("/sys/module/gt9xx_driver/parameters/irq_edge", irq);
        } catch (IOException e) {
            Log.w(TAG, "ERROR " + Util.getLineInfo());
            e.printStackTrace();
        }
        long callingId = Binder.clearCallingIdentity();
        Settings.System.putString(this.mContext.getContentResolver(), HConfig.touch_irq_type, irq);
        Binder.restoreCallingIdentity(callingId);
    }

    private void generateVirtualKey(String filename, HashMap<Integer, KeyMap> maps) {
        McuService mcuService = this;
        HashMap<Integer, Integer> kernel = new HashMap<>();
        kernel.put(3, 172);
        kernel.put(4, 158);
        kernel.put(Integer.valueOf(McuConstant.K_MUTE), 113);
        kernel.put(25, 114);
        kernel.put(24, 115);
        kernel.put(26, 160);
        kernel.put(Integer.valueOf(McuConstant.K_MUSIC), 171);
        kernel.put(87, 163);
        kernel.put(85, Integer.valueOf(McuConstant.K_MUTE));
        kernel.put(88, 165);
        kernel.put(Integer.valueOf(McuConstant.K_NAVI), 362);
        kernel.put(Integer.valueOf(McuConstant.K_SOURCE), 65);
        kernel.put(Integer.valueOf(McuConstant.K_EQ), 66);
        kernel.put(Integer.valueOf(McuConstant.K_MOTOR), 67);
        kernel.put(Integer.valueOf(McuConstant.K_BACKLIGHT), 87);
        kernel.put(Integer.valueOf(McuConstant.K_LAUNCHER_APPS), 88);
        kernel.put(Integer.valueOf(McuConstant.K_REPEAT), Integer.valueOf(CarletterInterface.KEYCODE_KNOB_LEFT));
        kernel.put(Integer.valueOf(McuConstant.K_RANDOM), Integer.valueOf(CarletterInterface.KEYCODE_KNOB_RIGHT));
        kernel.put(Integer.valueOf(McuConstant.K_SETUP), Integer.valueOf(CarletterInterface.KEYCODE_LEFT));
        kernel.put(Integer.valueOf(McuConstant.K_SCAN), Integer.valueOf(CarletterInterface.KEYCODE_RIGHT));
        kernel.put(Integer.valueOf(McuConstant.K_AS), Integer.valueOf(CarletterInterface.KEYCODE_UP));
        kernel.put(Integer.valueOf(McuConstant.K_STEP_FORWARD), Integer.valueOf(CarletterInterface.KEYCODE_DOWN));
        kernel.put(Integer.valueOf(McuConstant.K_STEP_BACKWARD), 262);
        kernel.put(Integer.valueOf(McuConstant.K_BT), 263);
        kernel.put(Integer.valueOf(McuConstant.K_HANGDOWN), Integer.valueOf(MCU_MAX_COMMAND_BYTES));
        kernel.put(Integer.valueOf(McuConstant.K_BAND), 265);
        kernel.put(301, 266);
        kernel.put(Integer.valueOf(McuConstant.K_ASSISTANT), 267);
        kernel.put(Integer.valueOf(McuConstant.K_VIDEO), 268);
        kernel.put(Integer.valueOf(McuConstant.K_F_AUX_IN), 269);
        kernel.put(337, 270);
        kernel.put(89, 168);
        kernel.put(90, 208);
        kernel.put(19, 103);
        kernel.put(20, 108);
        kernel.put(21, Integer.valueOf(AudioEffect.PATH_NAVI));
        kernel.put(22, Integer.valueOf(AudioEffect.PATH_BTVOICE));
        kernel.put(66, 28);
        kernel.put(8, 2);
        kernel.put(9, 3);
        kernel.put(10, 4);
        kernel.put(11, 5);
        kernel.put(12, 6);
        kernel.put(13, 7);
        kernel.put(14, 8);
        kernel.put(15, 9);
        kernel.put(16, 10);
        kernel.put(7, 11);
        kernel.put(82, Integer.valueOf(McuConstant.K_PAUSE));
        kernel.put(336, 271);
        boolean Land = SystemProperties.getInt("sys.disp.force_direct", 1) <= 1;
        int Rotate = Land ? 0 : SystemProperties.getInt("ro.sf.hwrotation", 0);
        boolean mr = SystemProperties.getInt("hw.sys.motor.rotation", 0) == 1;
        boolean R = mr || Rotate == 90 || Rotate == 270;
        int TP_Rotate = SystemProperties.getInt("ro.sys.tp.rotation", -1);
        if (-1 != TP_Rotate) {
            R = TP_Rotate == 1;
        }
        String tinfo = nativeGetTouchInfo();
        String[] xy = tinfo.split(",");
        int W = Integer.parseInt(xy[0]);
        int H = Integer.parseInt(xy[1]);
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) mcuService.mContext.getSystemService("window");
        wm.getDefaultDisplay().getRealMetrics(dm);
        float xScale = (R ? dm.heightPixels : dm.widthPixels) / W;
        float yScale = (R ? dm.widthPixels : dm.heightPixels) / H;
        StringBuilder sb = new StringBuilder(CarletterInterface.KEYCODE_KNOB_LEFT);
        new HashMap();
        Iterator<?> iter = maps.entrySet().iterator();
        while (true) {
            Iterator<?> iter2 = iter;
            int TP_Rotate2 = TP_Rotate;
            if (!iter2.hasNext()) {
                break;
            }
            Map.Entry<Integer, KeyMap> entry = iter2.next();
            KeyMap key = entry.getValue();
            int x = (int) (mcuService.shortToInt((short) (key.kadc >> 16)) * xScale);
            float xScale2 = xScale;
            int y = (int) (mcuService.shortToInt((short) key.kadc) * yScale);
            int keycode = kernel.get(Integer.valueOf(key.kcode)).intValue();
            if (x > 0) {
                sb.append(String.format(Locale.US, "0x01:%d:%d:%d:%d:%d\n", Integer.valueOf(keycode), Integer.valueOf(x), Integer.valueOf(y), 50, 40));
            }
            TP_Rotate = TP_Rotate2;
            iter = iter2;
            xScale = xScale2;
            kernel = kernel;
            yScale = yScale;
            mcuService = this;
        }
        try {
            Util.stringToFileSync(filename + ".tmp", sb.toString());
            Libcore.os.rename(filename + ".tmp", filename);
            Util.nativeSync();
        } catch (ErrnoException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        long callingId = Binder.clearCallingIdentity();
        InputManager.getInstance().setTouchCalibration("virtualkey", 0, null);
        Binder.restoreCallingIdentity(callingId);
    }

    class CaliView extends View {
        private int[] POINTXY;
        private final int SCREENH;
        private final int SCREENW;
        private int indexCali;
        private String mReadTouchCalib;
        private final Paint paint;
        private final int[] touchPositionX;
        private final int[] touchPositionY;

        /* JADX WARN: switch over string: strings are not added: [[NONE]] */
        public CaliView(Context context) {
            super(context);
            this.mReadTouchCalib = null;
            this.paint = new Paint();
            this.indexCali = 0;
            this.touchPositionX = new int[5];
            this.touchPositionY = new int[5];
            DisplayMetrics dm = this.mContext.getResources().getDisplayMetrics();
            this.SCREENW = dm.widthPixels;
            if (SystemProperties.getInt("sys.disp.forceshownavbar", 0) > 0) {
                this.SCREENH = dm.heightPixels - SystemProperties.getInt("ro.sys.customer.naviheight", 0);
            } else {
                this.SCREENH = dm.heightPixels;
            }
            this.POINTXY = new int[]{180, 180, this.SCREENW - 180, 180, this.SCREENW - 180, this.SCREENH - 180, 180, this.SCREENH - 180, this.SCREENW / 2, this.SCREENH / 2};
            String lcmName = WindowManagerServiceExt.readLcmInfoName();
            if (!TextUtils.isEmpty(lcmName)) {
                switch (lcmName) {
                    case "JBS636DV01.3":
                    case "HTF062032SRGB-V1":
                    case "HTF063640SMP-V0":
                        this.POINTXY[1] = 80;
                        this.POINTXY[3] = 80;
                        this.POINTXY[5] = this.SCREENH - 80;
                        this.POINTXY[7] = this.SCREENH - 80;
                        break;
                    case "FY636DV01.3":
                        this.POINTXY[1] = 80;
                        this.POINTXY[3] = 80;
                        this.POINTXY[5] = this.SCREENW - 80;
                        this.POINTXY[7] = this.SCREENW - 80;
                        break;
                }
            }
            for (String CalibFile : McuService.this.TouchCalibFiles) {
                try {
                    String touchCailb = Util.readStringFromFile(CalibFile, 64);
                    if (touchCailb != null) {
                        this.mReadTouchCalib = touchCailb;
                        FileUtils.stringToFile(CalibFile, "1,0,0,0,1,0,1");
                        Log.d(McuService.TAG, "write touch calibration file: " + CalibFile);
                    }
                } catch (Exception e) {
                    Log.w(McuService.TAG, "ERROR " + Util.getLineInfo());
                }
            }
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(-1);
            this.paint.setStrokeWidth(2.0f);
            int cx = this.POINTXY[this.indexCali * 2];
            int cy = this.POINTXY[(this.indexCali * 2) + 1];
            canvas.drawLine(cx - 30, cy, cx + 30, cy, this.paint);
            canvas.drawLine(cx, cy - 30, cx, cy + 30, this.paint);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getActionMasked();
            if (action == 0) {
                int x = (int) event.getX(0);
                int y = (int) event.getY(0);
                Log.d(McuService.TAG, "onTouche x = " + x + ", y = " + y + ", index = " + this.indexCali);
                this.touchPositionX[this.indexCali] = x;
                this.touchPositionY[this.indexCali] = y;
                this.indexCali = this.indexCali + 1;
                if (this.indexCali == 5) {
                    if (doTouchCali(this.touchPositionX, this.touchPositionY, this.POINTXY)) {
                        this.mReadTouchCalib = null;
                        Message msg = McuService.this.H.obtainMessage(5, 0, 0);
                        msg.sendToTarget();
                    }
                    this.indexCali = 0;
                }
                invalidate();
            }
            return true;
        }

        public void onTouchEvent(int x, int y, int state) {
        }

        @Override // android.view.View, android.view.KeyEvent.Callback
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == 4) {
                Message msg = McuService.this.H.obtainMessage(5, 0, 0);
                msg.sendToTarget();
                return true;
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void abortCalibration() {
            if (this.mReadTouchCalib != null) {
                String str = this.mReadTouchCalib;
                this.mReadTouchCalib = null;
                McuService.this.saveTouchCalibratResult(str, false);
            }
        }

        /* JADX WARN: Code duplicated, block: B:38:0x00ed  */
        /* JADX WARN: Code duplicated, block: B:47:0x010a  */
        private boolean doTouchCali(int[] posx, int[] posy, int[] POINTXY) {
            boolean flipx;
            boolean flipy;
            boolean Portrait = (SystemProperties.getInt("sys.disp.force_direct", 1) == 2) ^ (SystemProperties.getInt("hw.sys.motor.rotation", 0) == 1);
            String tinfo = McuService.nativeGetTouchInfo();
            String[] xy = tinfo.split(",");
            int W = Integer.parseInt(xy[0]);
            int H = Integer.parseInt(xy[1]);
            int diffy = Math.abs(posy[0] - posy[1]);
            int diffx = Math.abs(posx[0] - posx[1]);
            boolean swapxy = diffx < 20 && diffy > 20;
            int[] X = swapxy ? posy : posx;
            int[] Y = swapxy ? posx : posy;
            int MIN_GAPY = (int) (this.SCREENH * 0.22f);
            if (Math.abs(X[0] - X[1]) < 250 || Math.abs(X[2] - X[3]) < 250 || Math.abs(Y[0] - Y[3]) < MIN_GAPY) {
                return false;
            }
            if (Math.abs(Y[1] - Y[2]) < MIN_GAPY) {
                return false;
            }
            int averagex = (((X[0] + X[1]) + X[2]) + X[3]) / 4;
            int averagey = (((Y[0] + Y[1]) + Y[2]) + Y[3]) / 4;
            if (Math.abs(averagex - X[4]) <= 15 && Math.abs(averagey - Y[4]) <= 15) {
                if (Portrait) {
                    if (Y[0] > Y[2]) {
                        flipx = true;
                    } else {
                        flipx = false;
                    }
                } else if (X[0] > X[1]) {
                    flipx = true;
                } else {
                    flipx = false;
                }
                if (Portrait) {
                    if (X[0] > X[1]) {
                        flipy = true;
                    } else {
                        flipy = false;
                    }
                } else if (Y[0] > Y[2]) {
                    flipy = true;
                } else {
                    flipy = false;
                }
                int[] result = new int[7];
                result[6] = 1;
                result[0] = flipx ? -1 : 1;
                result[1] = swapxy ? 1 : 0;
                result[2] = flipx ? W : 0;
                result[3] = swapxy ? 1 : 0;
                result[4] = flipy ? -1 : 1;
                result[5] = flipy ? H : 0;
                Log.i(McuService.TAG, "Calibration result " + Arrays.toString(result));
                StringBuilder sb = new StringBuilder();
                sb.append(result[0]);
                sb.append(',');
                int averagey2 = result[1];
                sb.append(averagey2);
                sb.append(',');
                sb.append(result[2]);
                sb.append(',');
                sb.append(result[3]);
                sb.append(',');
                sb.append(result[4]);
                sb.append(',');
                sb.append(result[5]);
                sb.append(',');
                sb.append(result[6]);
                sb.append('\n');
                this.mReadTouchCalib = null;
                String str = sb.toString();
                McuService.this.saveTouchCalibratResult(str, true);
                return true;
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCaliView(boolean enable) {
        if (this.mTouchCaliView == null && enable) {
            this.mTouchCaliView = new CaliView(this.mContext);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams(-2, -2);
            lp.type = 2003;
            lp.flags = CarletterInterface.KEYCODE_KNOB_LEFT;
            lp.setTitle("TouchCaliView");
            WindowManager wm = (WindowManager) this.mContext.getSystemService("window");
            wm.addView(this.mTouchCaliView, lp);
            return;
        }
        if (this.mTouchCaliView != null && !enable) {
            this.mTouchCaliView.abortCalibration();
            WindowManager wm2 = (WindowManager) this.mContext.getSystemService("window");
            wm2.removeView(this.mTouchCaliView);
            this.mTouchCaliView = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableDebugView(boolean enable) {
        if (this.mDebugView != null || !enable) {
            if (this.mDebugView != null && !enable) {
                WindowManager wm = (WindowManager) this.mContext.getSystemService("window");
                wm.removeView(this.mDebugView);
                this.mDebugView = null;
                return;
            }
            return;
        }
        this.mDebugView = new TextView(this.mContext);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(-2, -2);
        lp.type = 2015;
        lp.flags = 1304;
        if (ActivityManager.isHighEndGfx()) {
            lp.flags |= 16777216;
            lp.privateFlags |= 2;
        }
        lp.format = -3;
        lp.gravity = 83;
        lp.setTitle("DebugView");
        WindowManager wm2 = (WindowManager) this.mContext.getSystemService("window");
        lp.inputFeatures |= 2;
        this.mDebugView.setTextSize(25.0f * this.mContext.getResources().getDisplayMetrics().density);
        this.mDebugView.setSingleLine(false);
        wm2.addView(this.mDebugView, lp);
    }

    public void systemReady() {
        loadKeymap();
        this.mPackageMonitor = new PackageMonitor() { // from class: android.carsource.McuService.6
            public void onPackageAdded(String packageName, int uid) {
                McuService.this.buildNaviPackages();
                if ("com.tima.carnet.vt".equals(packageName)) {
                    SystemProperties.set("service.carnet.enable", "1");
                }
                LinkManager.getInstance().initLinkByPackageManager(packageName);
            }

            public void onPackageRemoved(String packageName, int uid) {
                if ("com.tima.carnet.vt".equals(packageName)) {
                    SystemProperties.set("service.carnet.enable", "0");
                }
                LinkManager.getInstance().removeLinkByPackageManager(packageName);
            }
        };
        this.mPackageMonitor.register(this.mContext, this.H.getLooper(), UserHandle.ALL, true);
        loadRotation();
        buildNaviPackages();
        checkAdbServiceifNeeded();
        nativeInitTouchEvent();
        ContentResolver cr = this.mContext.getContentResolver();
        int irq = Settings.System.getInt(cr, HConfig.touch_irq_type, -1);
        setTouchIrqType(irq);
        int minutes = Settings.System.getInt(cr, HConfig.poweroff_timeout, 4320);
        setPoweroffTimeout(minutes);
        setMcuConfig(Settings.System.getInt(cr, HConfig.mcu_aux_mode, 0));
        setRevEnable(Settings.System.getInt(cr, HConfig.car_revs_support, 0));
        String s = Settings.System.getString(cr, HConfig.touch_calibraion);
        if (s != null && s.length() > 1) {
            saveTouchCalibratResult(s, false);
        }
        this.H.sendEmptyMessageDelayed(2, 5000L);
        try {
            String barcode = FileUtils.readTextFile(new File(BarCodeFile), 0, null);
            if (barcode.length() == 0) {
                String emmcID = SystemProperties.get("no.such.thing", "");
                SystemProperties.set(KEY_BARCODE, emmcID.trim());
            } else {
                SystemProperties.set(KEY_BARCODE, barcode);
            }
        } catch (IOException e) {
            String emmcID2 = SystemProperties.get("no.such.thing", "");
            SystemProperties.set(KEY_BARCODE, emmcID2.trim());
        }
        try {
            String barcode2 = FileUtils.readTextFile(new File(SNCodeFile), 0, null);
            SystemProperties.set("ro.hw.device.sn", barcode2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            String barcode3 = FileUtils.readTextFile(new File(ZLinkCodeFile), 0, null);
            SystemProperties.set(KEY_ZLINKCODE, barcode3);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            String barcode4 = FileUtils.readTextFile(new File("/apd/ltlinkcode"), 0, null);
            SystemProperties.set(KEY_LTCODE, barcode4);
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        try {
            String acct = FileUtils.readTextFile(new File(GocAcctFile), 0, null).trim();
            if (!TextUtils.isEmpty(acct)) {
                SystemProperties.set(KEY_GOC_ACCT, acct);
            }
        } catch (Exception e5) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void buildNaviPackages() {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("geo:"));
        PackageManager mPm = this.mContext.getPackageManager();
        List<ResolveInfo> list = mPm.queryIntentActivities(intent, 65600);
        StringBuilder sb = new StringBuilder("");
        for (ResolveInfo resinfo : list) {
            sb.append(resinfo.activityInfo.packageName);
            sb.append(',');
        }
    }

    private void checkAdbServiceifNeeded() {
        PackageManager pm = this.mContext.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo pi : packages) {
            if ("com.tima.carnet.vt".equals(pi.packageName)) {
                SystemProperties.set("service.carnet.enable", "1");
            }
            LinkManager.getInstance().initLinkByPackageManager(pi.packageName);
        }
    }

    private void native_reportTouchEvent(int type, int x, int y, int state) {
        if (this.mPointerLocation) {
            String tt = type == 3 ? "Touch" : "Key";
            String msg = String.format(Locale.US, "type %s, %d, %d, state %d", tt, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(state));
            this.H.obtainMessage(1, msg).sendToTarget();
        }
        if (this.mIsMappingTouchKey && state <= 1 && type == 3 && x > 0 && y >= 0) {
            int action = state != 1 ? 5 : 1;
            onRawKeyEvent((x << 16) | y, action);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveBarcode(String barcode, String codeFile, String savePersist) {
        try {
            Util.stringToFileSync(codeFile + ".tmp", barcode);
            Libcore.os.rename(codeFile + ".tmp", codeFile);
            Util.nativeSync();
            SystemProperties.set(savePersist, barcode);
            Toast.makeText(this.mContext, "barcode:" + barcode, 0).show();
        } catch (ErrnoException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveHcnBarcode(String barcode) {
        try {
            Util.stringToFileSync("/apd/.sn.tmp", barcode);
            Libcore.os.rename("/apd/.sn.tmp", SNCodeFile);
            Util.nativeSync();
            Toast.makeText(this.mContext, "barcode:" + barcode, 0).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            SystemProperties.set("ro.hw.device.sn", barcode);
        } catch (RuntimeException e2) {
        }
    }

    private void native_reportAdckeyEvent(int adc) {
        if (this.mPointerLocation) {
            String msg = String.format(Locale.US, "adckey : 0x%04x", Integer.valueOf(adc));
            this.H.obtainMessage(1, msg).sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleMotor() {
        try {
            String setto = "portrait";
            File f = new File("/dev/motor");
            if (this.mMotorControlByMcu) {
                setto = "landscape".equals(this.mMcuRotionState) ? "portrait" : "landscape";
                int state = "landscape".equals(this.mMcuRotionState) ? 2 : 1;
                setRotationStatusToMcu(state);
            } else if (f.exists()) {
                String now = FileUtils.readTextFile(f, 0, null).trim();
                setto = "landscape".equals(now) ? "portrait" : "landscape";
                FileUtils.stringToFile(f, setto + "\n");
            } else {
                int v = Settings.System.getInt(this.mContext.getContentResolver(), "user_rotation", 0);
                if (v == 1) {
                    setto = "landscape";
                }
            }
            Message msg = this.H.obtainMessage(10, setto);
            this.H.removeMessages(10);
            this.H.sendMessageDelayed(msg, 1000L);
        } catch (IOException e) {
            Log.w(TAG, e.toString() + Util.getLineInfo());
        }
    }

    private void loadRotation() {
        try {
            File f = new File("/dev/motor");
            if (f.exists()) {
                String now = FileUtils.readTextFile(f, 0, null).trim();
                setRotation(now);
            } else {
                int r = Settings.System.getInt(this.mContext.getContentResolver(), "user_rotation", 0);
                SystemProperties.set("hw.sys.motor.rotation", Integer.toString(r));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRotation(String str) {
        int i = !str.equals("landscape") ? 1 : 0;
        SystemProperties.set("hw.sys.motor.rotation", Integer.toString(i));
        Settings.System.putInt(this.mContext.getContentResolver(), "user_rotation", i);
    }

    @Override // android.carsource.IMcuManager
    public int setLogo(String filename) {
        return nativeSetLogo(filename);
    }

    private void fakeOffBrushBlackBackground(boolean need) {
        String setto = need ? "1\n" : "0\n";
        try {
            FileUtils.stringToFile("/tmp/system/fake_off", setto);
        } catch (IOException e) {
            Log.w(TAG, "ERROR " + Util.getLineInfo());
            Log.w(TAG, e.toString());
        }
    }

    private void forceStopPackage(String app) {
        long callingId = Binder.clearCallingIdentity();
        try {
            ActivityManager.getService().forceStopPackage(app, -1);
        } catch (RemoteException e) {
        } finally {
            Binder.restoreCallingIdentity(callingId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBacklightRangeChange(int range) {
        if (range <= 0) {
            return;
        }
        int minVal = 65535 & range;
        int maxVal = Math.min(Math.max((range >> 16) & 65535, 45), 167);
        final int value = (maxVal << 16) | Math.min(Math.max(minVal, 1), maxVal - 1);
        BackgroundThread.getHandler().post(new Runnable() { // from class: android.carsource.McuService.8
            @Override // java.lang.Runnable
            public void run() {
                McuService.sendCmdCarStatus("backlight_range=" + value);
                HConfig.setNvrom(7, value);
                try {
                    int light = Settings.System.getInt(McuService.this.mContext.getContentResolver(), "screen_brightness", McuConstant.K_SWCSETTING);
                    SystemClock.sleep(99L);
                    McuService.this.mDisplayManager.setTemporaryBrightness(light - 1);
                    SystemClock.sleep(50L);
                    McuService.this.mDisplayManager.setTemporaryBrightness(light);
                } catch (Exception e) {
                    Log.e(McuService.TAG, "ERROR " + Util.getLineInfo());
                    Log.e(McuService.TAG, e.toString());
                }
            }
        });
    }
}
