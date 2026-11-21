package android.sourceservice;

import android.Configures.HConfig;
import android.app.ActivityManager;
import android.audio.AudioEffect;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadsetClientCall;
import android.carsource.McuConstant;
import android.carsource.McuService;
import android.carstatus.CarStatus;
import android.carstatus.CarStatusService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.link.LinkManager;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.utilities.Util;
import com.android.server.LocalServices;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class SourceService extends ISourceService.Stub {
    public static final String ACTION_TOP_ACTIVITY = "android.sourceservices.action.TopActivity";
    public static final String ACTIVE_AUX = "com.auto.hcamera";
    public static final String ACTIVE_BT = "com.autochips.bluetooth";
    public static final String ACTIVE_CMMB = "com.cardroid.tv";
    public static final String ACTIVE_DAB = "com.hcn.autodab";
    public static final String ACTIVE_EASYCONN = "net.easyconn";
    public static final String ACTIVE_HUAWEI = "com.huawei.dmsdpdevice";
    public static final String ACTIVE_LT_CARPLAY = "com.carletter.car";
    public static final String ACTIVE_MPEG = "com.cardroid.disc";
    public static final String ACTIVE_ONSTAR = "com.hcn.autocan";
    public static final String ACTIVE_RADIO = "com.hcn.autoradio";
    public static final String ACTIVE_SIRI = "com.hcn.siriusservice";
    public static final String ACTIVE_SXM = "com.hcn.siriusxm";
    public static final String ACTIVE_TV = "com.cardroid.simtv";
    public static final String ACTIVE_YOUTUBE = "com.google.android.youtube";
    public static final String ACTIVE_ZJ_CARPLAY = "com.zjinnova.zlink";
    static final String BT_MUSIC = "com.tima.carnet.vt,net.easyconn";
    private static final String DESCRIPTOR = "android.sourceservice.ISourceInfo";
    public static final String EXTRA_TOP_ACTIVITY_PACKAGE = "package";
    public static final String HCAMERA_PACKAGE_NAME = "com.auto.hcamera";
    public static final String HCN_CAMERA_CLASS_SET = ".BackCarActivity.BackCarActivity_Can360.VideoInActivityTV.FCameraVideoInActivity.BackCarActivity_DashCam.";
    public static final String HCN_DASH_CAMERA_CLS = "BackCarActivity_DashCam";
    public static final String HCN_FRONT_CAMERA_CLS = "FCameraVideoInActivity";
    static final String HCN_MEDIA_PACKAGE_NAME = "com.hcn.AutoMediaPlayer";
    static final String HcnMediaCLASS = "com.hcn.MediaActivity.VideoPlayerUiActivity,com.hcn.MediaActivity.MusicPlayerUiActivity,com.hcn.AutoMediaPlayer/background";
    static final String MUSICPKG = "cn.kuwo.kwmusiccar,com.kaolafm.kradio.k_radio_horizontal,com.edog.car,cn.kuwo.tingshucar";
    public static final String SYSTEM_PACKAGES = "com.android.bluetooth,net.easyconn,com.goodocom.gocsdk,com.autochips.bluetooth,com.hcn.siriusservice,com.auto.hcamera,com.autochips.carplayapp" + SystemProperties.get("sys.navi.packages") + "com.zjinnova.zlink,com.hcn.autocan,com.hcn.audioinputsource,com.huawei.dmsdpdevice,com.carletter.car,com.carletter.ceb,";
    static final String TAG = "SourceService";
    static final String VOICEPKG = "com.txznet.txz";
    private final boolean mAffectbyOversea;
    private int mBtMusicSession;
    private final Context mContext;
    private Record mMixRecord;
    private final ParkingObserver mParkingObserver;
    private SourcePolicy mPolicy;
    private SettingsObserver mSettingsObserver;
    private Record mVoiceMixRecord;
    private int DEBUG = 5;
    private final String TOP_ACTIVITY_PROPERTY = "sys.top.activity";
    private static boolean mBackCarMediaPlayStatus = false;
    private final AtomicReference<String> mAudioFocusPackage = new AtomicReference<>();
    private final AtomicReference<String> mAudioVoicePackage = new AtomicReference<>();
    private final AtomicReference<String> mAudioMixPackage = new AtomicReference<>();
    private String CurrentSource = "";
    private String mCurrentClass = "";
    private final MusicInfo mMusicInfo = new MusicInfo();
    private final AtomicReference<String> mPerpareToKill = new AtomicReference<>();
    private boolean mShutdown = false;
    private String mTopPackage = "";
    static int mDebugLevel = 0;
    int mBluetoothVoicing = 7;
    private EnumSet<PATH> mAudioPathStatus = EnumSet.noneOf(PATH.class);
    private final EnumSet<PATH> EMPTY_PATH = EnumSet.range(PATH.RADIO, PATH.MPEG);
    private final int MSG_BT_VOICE = 1;
    private final int MSG_MUSIC_FADEIN = 2;
    private final int MSG_VOICE_DONE = 3;
    private final int MSG_KILL_APP = 4;
    private final int MSG_BT_CONNECTION = 5;
    private final int MSG_CLEAR_RECORD = 6;
    private final int MSG_UPDATE_VOL = 7;
    private final Handler mFadeHandler = new Handler() { // from class: android.sourceservice.SourceService.2
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SourceService.this.onBluetoothVoiceStatus(msg.arg1);
                    break;
                case 2:
                    SourceService.this.audioFadein();
                    break;
                case 3:
                    SourceService.this.fadeinByVoice();
                    break;
                case 4:
                    String app = (String) msg.obj;
                    if (!SourceService.SYSTEM_PACKAGES.contains(app) && !SourceService.this.CurrentSource.equals(app)) {
                        Log.i(SourceService.TAG, "forceStopPackage Kill " + app);
                        SourceService.this.forceStopPackage(app);
                    }
                    sendEmptyMessageDelayed(6, 600L);
                    break;
                case 5:
                    SourceService.this.onBluetoothConnectStatus(msg.arg1 == 1);
                    break;
                case 6:
                    SourceService.this.mPerpareToKill.set("");
                    break;
                case 7:
                    Log.i(SourceService.TAG, "MSG_UPDATE_VOL updateVolume tick:" + msg.arg1);
                    if (msg.arg1 > 0) {
                        Message msg2 = obtainMessage(7, msg.arg1 - 1, 0);
                        sendMessageDelayed(msg2, 100L);
                    }
                    SourceService.this.updateVolume();
                    break;
            }
        }
    };
    private boolean mColdstartAutoLaunchAppFlag = false;
    boolean mActiveRingStream = false;
    boolean mActiveCallStream = false;
    private final int ACTIVITY_CALL_TRANSACTION = HConfig.MBoardCode.FEIYIN_DSP_BOARD;
    private final int MEDIAPLAYER_CALL_TRANSACTION = HConfig.MBoardCode.NAKAMICHI_MP5_BOARD;
    private final int RADIO_CALL_TRANSACTION = 301;
    private final int AUDIOTRACK_CALL_TRANSACTION = 401;
    private final int WINDOW_CALL_TRANSACTION = 501;
    private final int MEDIA_FOCUS_CALL_TRANSACTION = 601;
    private final int AUTO_MEDIAPLAYER_CALL_TRANSACTION = 701;
    private final ArrayList<ISourceCallback> mCallBacks = new ArrayList<>();

    enum PATH {
        RADIO,
        A2DP,
        BT_UI,
        AUX,
        AUX2,
        DAB,
        TV,
        CAN,
        MUSIC,
        MPEG,
        MIX,
        MIXVOC,
        HFP,
        RDS,
        ONSTAR,
        BT_CONNTECTED,
        RGEAR,
        NONE
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Record {
        long endat;
        String pkt;
        int session;

        public Record(String pkt, long endat, int session) {
            this.pkt = pkt;
            this.endat = endat;
            this.session = session;
        }
    }

    public SourceService(Context context) {
        boolean z = false;
        this.mContext = context;
        this.mPolicy = new SourcePolicy(context);
        SystemProperties.set("sys.sourcing.package", "");
        ExtAudioMuxer.nativeInit();
        ExtAudioMuxer.setMasterVolumeMax(SystemProperties.getInt("ro.config.max_vol_steps", 40));
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        filter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        filter.addAction("android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED");
        filter.addAction("android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED");
        filter.addAction("android.bluetooth.headsetclient.profile.action.AG_EVENT");
        filter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        filter.addAction("android.intent.action.ACTION_SHUTDOWN");
        filter.addAction("android.intent.action.SCREEN_ON");
        filter.addAction(CarStatus.ACTION_REVSTATUS);
        filter.addAction("net.easyconn.a2dp.acquire");
        filter.addAction(CarStatus.ACTION_PARKING);
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: android.sourceservice.SourceService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                int delay;
                String action = intent.getAction();
                if (action.equals("android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED")) {
                    BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    int state = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                    Log.i(SourceService.TAG, "headset state changed. device : " + device + ", state : " + state);
                    if (state == 0) {
                        SourceService.this.mFadeHandler.obtainMessage(5, 0, 0).sendToTarget();
                        return;
                    } else {
                        if (state == 2) {
                            SourceService.this.mFadeHandler.obtainMessage(5, 1, 1).sendToTarget();
                            return;
                        }
                        return;
                    }
                }
                if (action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                    SourceService.this.mFadeHandler.obtainMessage(5, 1, 1).sendToTarget();
                    return;
                }
                if (action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
                    SourceService.this.mFadeHandler.obtainMessage(5, 0, 0).sendToTarget();
                    return;
                }
                if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                    int state2 = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                    Log.i(SourceService.TAG, "bluetooth power state " + BluetoothAdapter.nameForState(state2));
                    if (state2 == 10 || state2 == 13) {
                        SourceService.this.mFadeHandler.obtainMessage(5, 0, 0).sendToTarget();
                        return;
                    }
                    return;
                }
                if (action.equals("android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED")) {
                    BluetoothHeadsetClientCall c = intent.getParcelableExtra("android.bluetooth.headsetclient.extra.CALL");
                    if (c != null) {
                        int calling = c.getState();
                        delay = calling != 7 ? 0 : 2000;
                        Message msg = SourceService.this.mFadeHandler.obtainMessage(1, calling, 1);
                        SourceService.this.mFadeHandler.removeMessages(1);
                        SourceService.this.mFadeHandler.sendMessageDelayed(msg, delay);
                        return;
                    }
                    return;
                }
                if (action.equals("android.bluetooth.headsetclient.profile.action.AG_EVENT")) {
                    int show = intent.getIntExtra("android.bluetooth.headsetclient.extra.VOICE_RECOGNITION", -1);
                    Log.i(SourceService.TAG, "siri state to " + show);
                    if (show >= 0) {
                        delay = show > 0 ? 0 : 2000;
                        Message msg2 = SourceService.this.mFadeHandler.obtainMessage(1, show <= 0 ? 7 : 0, 1);
                        SourceService.this.mFadeHandler.removeMessages(1);
                        SourceService.this.mFadeHandler.sendMessageDelayed(msg2, delay);
                        return;
                    }
                    return;
                }
                if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {
                    SourceService.this.mShutdown = true;
                    Log.d(SourceService.TAG, "onReceive: ACTION_SHUTDOWN");
                    return;
                }
                if (action.equals("android.intent.action.SCREEN_ON")) {
                    SourceService.this.fadeinByVoice();
                    return;
                }
                if (!action.equals("net.easyconn.a2dp.acquire")) {
                    if (!action.equals(CarStatus.ACTION_REVSTATUS)) {
                        if (action.equals(CarStatus.ACTION_PARKING)) {
                            SourceService.this.mParkingObserver.checkParkingView(SourceService.this.mTopPackage);
                            return;
                        }
                        return;
                    } else {
                        boolean rev = intent.getBooleanExtra(CarStatus.EXTRA_REVSTATUS, false);
                        if (SourceService.this.mAffectbyOversea) {
                            SourceService.this.onRevbyOversea(rev);
                            return;
                        }
                        return;
                    }
                }
                Log.d(SourceService.TAG, "onReceive: net.easyconn.a2dp.acquire");
                IBinder service = ServiceManager.getService("sourceinfo");
                if (service != null) {
                    Parcel _data = Parcel.obtain();
                    try {
                        try {
                            _data.writeInterfaceToken(SourceService.DESCRIPTOR);
                            _data.writeString("OnResume");
                            _data.writeString("net.easyconn/.AUDIO");
                            service.transact(HConfig.MBoardCode.FEIYIN_DSP_BOARD, _data, null, 1);
                        } catch (Exception e) {
                            Log.w(SourceService.TAG, "sourceinfo binder failed");
                        }
                    } finally {
                        _data.recycle();
                    }
                }
            }
        }, filter, null, this.mFadeHandler);
        String version = SystemProperties.get(HConfig.PROP_CUSTOM_SYSTEM_VERSION_PREFFIX).toUpperCase();
        if (ExtAudioMuxer.ExtAudioAvailable && version.startsWith("DUAL")) {
            z = true;
        }
        this.mAffectbyOversea = z;
        this.mSettingsObserver = new SettingsObserver();
        this.mParkingObserver = new ParkingObserver(this.mContext);
        loadNaviStreamGain();
        LinkManager.getInstance().init(this.mContext);
    }

    private void resetStreamVolume(int stream) {
        long callingId = Binder.clearCallingIdentity();
        AudioManager am = (AudioManager) this.mContext.getSystemService("audio");
        int index = am.getStreamVolume(stream);
        if (mDebugLevel >= 1000) {
            Log.i(TAG, "resetStreamVolume stream:" + stream + " index:" + index);
        }
        if (index > 0) {
            am.adjustStreamVolume(stream, -1, 0);
            am.adjustStreamVolume(stream, 1, 0);
        }
        Binder.restoreCallingIdentity(callingId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBluetoothVoiceStatus(int status) {
        if (status != this.mBluetoothVoicing) {
            boolean prevstat = !this.mAudioPathStatus.contains(PATH.HFP);
            this.mBluetoothVoicing = status;
            boolean hangup = status == 7;
            if (hangup) {
                this.mAudioPathStatus.remove(PATH.HFP);
            } else {
                this.mAudioPathStatus.add(PATH.HFP);
            }
            if (hangup != prevstat) {
                Log.i(TAG, "onBluetoothVoiceStatus : " + this.mAudioPathStatus);
                ExtAudioMuxer.setMasterMute(true, 3);
                this.mPolicy.onBluetoothVoiceStatus(!hangup, this.mAudioPathStatus);
                int stream = hangup ? 3 : 0;
                AudioManager am = (AudioManager) this.mContext.getSystemService("audio");
                int index = am.getStreamVolume(stream);
                if (index > 0) {
                    am.adjustStreamVolume(stream, -1, 0);
                    am.adjustStreamVolume(stream, 1, 0);
                }
                ExtAudioMuxer.setMasterMute(false, 3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBluetoothConnectStatus(boolean state) {
        boolean prevstat = this.mAudioPathStatus.contains(PATH.A2DP);
        boolean prevconn = this.mAudioPathStatus.contains(PATH.BT_CONNTECTED);
        if (state) {
            this.mAudioPathStatus.add(PATH.BT_CONNTECTED);
            if (this.mAudioPathStatus.contains(PATH.BT_UI)) {
                this.mAudioPathStatus.add(PATH.A2DP);
            }
        } else {
            this.mBtMusicSession = 0;
            onBluetoothVoiceStatus(7);
        }
        if (prevconn != this.mAudioPathStatus.contains(PATH.BT_CONNTECTED)) {
            Log.i(TAG, "onBluetoothConnectStatus : " + this.mAudioPathStatus);
            if (prevstat != this.mAudioPathStatus.contains(PATH.A2DP)) {
                this.mPolicy.onBluetoothConnectStatus(!prevstat, this.mAudioPathStatus);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRevbyOversea(boolean revon) {
        EnumSet<E> enumSetClone = this.mAudioPathStatus.clone();
        if (revon) {
            this.mAudioPathStatus.add(PATH.RGEAR);
        } else {
            this.mAudioPathStatus.remove(PATH.RGEAR);
        }
        if (!enumSetClone.equals(this.mAudioPathStatus)) {
            Log.i(TAG, "onRevbyOversea : " + enumSetClone + " --> " + this.mAudioPathStatus);
            this.mPolicy.onNormalAudioPath(this.mAudioPathStatus);
        }
    }

    public static boolean isCameraUiModeTop(Context context) {
        ComponentName topcn = Util.getTopComponent(context);
        String topClassName = topcn.getClassName();
        if (!topClassName.startsWith("com.auto.hcamera")) {
            return false;
        }
        String topClassName2 = topClassName.replaceAll(".*/", "").replaceAll("com.auto.hcamera", "");
        boolean isCameraUiModeTop = HCN_CAMERA_CLASS_SET.contains(topClassName2 + ".");
        Log.i(TAG, "isCameraUiModeTop: " + isCameraUiModeTop + " | " + topClassName2);
        return isCameraUiModeTop;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:31:0x00f2  */
    public void onActivityStatus(String app, String type, int extra, String reason) {
        boolean z;
        EnumSet<E> enumSetClone = this.mAudioPathStatus.clone();
        String prevApp = this.CurrentSource;
        String prevClass = this.mCurrentClass;
        int session = 0;
        boolean isdied = false;
        String pkg = app.replaceAll("/.*", "");
        String cls = app.replaceAll(".*/", "");
        if (!"net.easyconn/.ui.MainActivity".equals(app) && !"net.easyconn/.ui.EcSdkActivity".equals(app)) {
            Log.i(TAG, "Activity " + type + "(" + reason + "), " + app + " | " + cls);
            if (!this.mColdstartAutoLaunchAppFlag && !this.mShutdown && "onStart".equals(type) && !TextUtils.isEmpty(app) && app.contains("com.android.launcher3")) {
                this.mColdstartAutoLaunchAppFlag = true;
                final String launchApp = Settings.System.getString(this.mContext.getContentResolver(), HConfig.coldstart_auto_launch_app);
                Log.i(TAG, "cold start auto launch: " + launchApp);
                if (!TextUtils.isEmpty(launchApp)) {
                    this.mFadeHandler.post(new Runnable() { // from class: android.sourceservice.-$$Lambda$SourceService$zFh2ChEhGGyLl5zpnSfrS7ImZHg
                        @Override // java.lang.Runnable
                        public final void run() {
                            Util.startActivitybyClass(SourceService.this.mContext, launchApp, true);
                        }
                    });
                }
            }
            CarStatusService carStatus = (CarStatusService) LocalServices.getService(CarStatusService.class);
            boolean isReversing = carStatus.getReversing() > 0;
            if ("com.auto.hcamera".equals(pkg)) {
                if (TextUtils.isEmpty(cls)) {
                    z = false;
                } else {
                    if (HCN_CAMERA_CLASS_SET.contains(cls + ".")) {
                        z = true;
                    } else {
                        z = false;
                    }
                }
                boolean isCameraUiModeTop = z;
                if (!TextUtils.isEmpty(cls) && isCameraUiModeTop) {
                    if ("onCreate".equals(type)) {
                        McuService.sendCmdCarStatus("360module=1");
                    } else if ("DESTROY".equals(type) && !isReversing) {
                        McuService.sendCmdCarStatus("360module=0");
                    }
                } else if ("DIED".equals(type) && !isReversing) {
                    McuService.sendCmdCarStatus("360module=0");
                }
            }
            if ("OnResume".contains(type)) {
                if (pkg.equals(this.mPerpareToKill.get())) {
                    this.mPerpareToKill.set("");
                }
                if (IsSourceActivity(pkg) && extra < 0) {
                    if (pkg.contains(ACTIVE_RADIO)) {
                        this.mAudioPathStatus.removeAll(this.EMPTY_PATH);
                        this.mAudioPathStatus.add(PATH.RADIO);
                        this.CurrentSource = pkg;
                        this.mCurrentClass = cls;
                    } else if (pkg.contains(ACTIVE_DAB) || pkg.contains(ACTIVE_SXM)) {
                        this.mAudioPathStatus.removeAll(this.EMPTY_PATH);
                        this.mAudioPathStatus.add(PATH.DAB);
                        this.CurrentSource = pkg;
                        this.mCurrentClass = cls;
                    } else if (pkg.contains(ACTIVE_MPEG)) {
                        this.mAudioPathStatus.removeAll(this.EMPTY_PATH);
                        this.mAudioPathStatus.add(PATH.MPEG);
                        this.CurrentSource = pkg;
                        this.mCurrentClass = cls;
                    } else if ((app.startsWith("com.auto.hcamera/.VideoInActivity") && ExtAudioMuxer.ExtAudioAvailable && isAuxAudioEnable()) || pkg.contains(ACTIVE_SIRI) || pkg.contains(ACTIVE_TV) || pkg.contains(ACTIVE_CMMB)) {
                        this.mAudioPathStatus.removeAll(this.EMPTY_PATH);
                        this.mAudioPathStatus.add(PATH.AUX);
                        this.CurrentSource = pkg;
                        this.mCurrentClass = cls;
                    } else if (app.startsWith("com.auto.hcamera/.DualVideoInActivity")) {
                        this.mAudioPathStatus.removeAll(this.EMPTY_PATH);
                        this.mAudioPathStatus.add(PATH.AUX2);
                        this.CurrentSource = pkg;
                        this.mCurrentClass = cls;
                    } else if (app.contains("com.autochips.bluetooth/.BtMusicActivity")) {
                        this.mAudioPathStatus.removeAll(this.EMPTY_PATH);
                        this.mAudioPathStatus.add(PATH.BT_UI);
                        this.CurrentSource = ACTIVE_BT;
                        this.mCurrentClass = cls;
                        this.mAudioPathStatus.add(PATH.A2DP);
                        session = this.mBtMusicSession;
                    }
                } else if (MUSICPKG.contains(pkg) || HcnMediaCLASS.contains(cls)) {
                    this.mAudioPathStatus.removeAll(this.EMPTY_PATH);
                    this.mAudioPathStatus.add(PATH.MUSIC);
                    this.CurrentSource = pkg;
                    this.mCurrentClass = cls;
                } else if (BT_MUSIC.contains(pkg)) {
                    this.mAudioPathStatus.removeAll(this.EMPTY_PATH);
                    this.mAudioPathStatus.add(PATH.BT_UI);
                    this.mAudioPathStatus.add(PATH.A2DP);
                    int session2 = this.mBtMusicSession;
                    this.CurrentSource = ACTIVE_BT;
                    this.mCurrentClass = cls;
                    Log.i(TAG, "BT_MUSIC pkg:" + pkg);
                    if ("com.tima.carnet.vt".equals(pkg)) {
                        Intent intent = new Intent("net.easyconn.a2dp.acquire");
                        this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT, null);
                        Log.i(TAG, "sendBroadcastAsUser net.easyconn.a2dp.acquire");
                    }
                    session = session2;
                } else if (ACTIVE_ZJ_CARPLAY.equals(pkg)) {
                    String foucsPkg = this.mAudioFocusPackage.get();
                    if (ACTIVE_ZJ_CARPLAY.equals(foucsPkg)) {
                        this.mAudioPathStatus.removeAll(this.EMPTY_PATH);
                        this.mAudioPathStatus.add(PATH.MUSIC);
                        this.CurrentSource = foucsPkg;
                        this.mCurrentClass = cls;
                    }
                } else if (ACTIVE_LT_CARPLAY.equals(pkg)) {
                    String foucsPkg2 = this.mAudioFocusPackage.get();
                    if (ACTIVE_HUAWEI.equals(foucsPkg2)) {
                        this.mAudioPathStatus.removeAll(this.EMPTY_PATH);
                        this.mAudioPathStatus.add(PATH.MUSIC);
                        this.CurrentSource = foucsPkg2;
                        if (ACTIVE_HUAWEI.equals(foucsPkg2)) {
                            this.mCurrentClass = cls;
                        }
                    } else if (ACTIVE_LT_CARPLAY.equals(foucsPkg2) && !this.CurrentSource.equals(ACTIVE_LT_CARPLAY) && !this.CurrentSource.equals("/system/bin//LinkSDKServer")) {
                        this.mAudioPathStatus.removeAll(this.EMPTY_PATH);
                        this.mAudioPathStatus.add(PATH.MUSIC);
                        this.CurrentSource = foucsPkg2;
                        this.mCurrentClass = cls;
                    }
                }
            } else if ("DESTROY".equals(type) && "app-request".equals(reason)) {
                if (pkg.contains("com.auto.hcamera") && cls.equals(this.mCurrentClass) && (this.mAudioPathStatus.contains(PATH.AUX) || this.mAudioPathStatus.contains(PATH.AUX2))) {
                    this.mAudioPathStatus.removeAll(this.EMPTY_PATH);
                    this.CurrentSource = "";
                    this.mCurrentClass = "";
                }
            } else if ("DIED".equals(type)) {
                isdied = true;
                if (pkg.equals(this.CurrentSource) && this.mAudioPathStatus.contains(PATH.MUSIC)) {
                    this.mAudioPathStatus.removeAll(this.EMPTY_PATH);
                    this.CurrentSource = "";
                    this.mCurrentClass = "";
                }
                if (IsSourceActivity(pkg) && ((pkg.contains(ACTIVE_RADIO) && this.mAudioPathStatus.contains(PATH.RADIO)) || ((pkg.contains("com.auto.hcamera") && this.mAudioPathStatus.contains(PATH.AUX)) || ((pkg.contains("com.auto.hcamera") && this.mAudioPathStatus.contains(PATH.AUX2)) || ((pkg.contains(ACTIVE_SIRI) && this.mAudioPathStatus.contains(PATH.AUX)) || ((pkg.contains(ACTIVE_CMMB) && this.mAudioPathStatus.contains(PATH.TV)) || ((pkg.contains(ACTIVE_BT) && this.mAudioPathStatus.contains(PATH.BT_UI)) || ((pkg.contains(ACTIVE_MPEG) && this.mAudioPathStatus.contains(PATH.MPEG)) || (pkg.contains(ACTIVE_TV) && this.mAudioPathStatus.contains(PATH.TV)))))))))) {
                    this.mAudioPathStatus.removeAll(this.EMPTY_PATH);
                    this.CurrentSource = "";
                    this.mCurrentClass = "";
                }
                LinkManager.getInstance().activityDied(pkg);
                if (ACTIVE_YOUTUBE.equals(pkg)) {
                    mBackCarMediaPlayStatus = false;
                }
            }
            if (!isdied && !this.CurrentSource.equals(prevApp)) {
                Log.i(TAG, "onActivityStatus prepare to Kill " + prevApp);
                if (!prevApp.equals(ACTIVE_HUAWEI)) {
                    this.mPolicy.setMusicFadeout();
                }
                if (!pkg.equals(prevApp)) {
                    this.mPerpareToKill.set(prevApp);
                    LinkManager.getInstance().changeSource(prevApp, pkg);
                    Message msg = this.mFadeHandler.obtainMessage(4, prevApp);
                    this.mFadeHandler.sendMessageDelayed(msg, 1500L);
                    this.mFadeHandler.removeMessages(6);
                }
            }
            if (!enumSetClone.equals(this.mAudioPathStatus) || !this.CurrentSource.equals(prevApp) || !prevClass.equals(this.mCurrentClass)) {
                Log.i(TAG, "onActivityStatus : " + enumSetClone + " --> " + this.mAudioPathStatus + ", from " + this.CurrentSource);
                this.mPolicy.onNormalAudioPath(this.mAudioPathStatus);
                SystemProperties.set("sys.sourcing.package", this.CurrentSource);
                if (this.CurrentSource.contains(ACTIVE_RADIO)) {
                    session = AudioEffect.DONOTCARE;
                }
                this.mMusicInfo.className = this.mCurrentClass;
                this.mMusicInfo.fileName = "";
                this.mMusicInfo.duration = 0;
                this.mMusicInfo.position = 0;
                this.mMusicInfo.state = "";
                this.mPolicy.onSessionChanged(session);
                callManager(this.CurrentSource, 100);
                setSourceChanged4Camerad(this.CurrentSource);
            }
            if (!this.CurrentSource.equals(prevApp) || (HCN_MEDIA_PACKAGE_NAME.equals(pkg) && !prevClass.equals(this.mCurrentClass))) {
                McuService.getMcuService().onSourceChanged(this.CurrentSource);
            }
        }
    }

    private void voiceFocus(final boolean isVoiceFocus, final String app, final boolean forceRemove) {
        this.mFadeHandler.post(new Runnable() { // from class: android.sourceservice.-$$Lambda$SourceService$HAo-U6gkzLf0OA8dWEl48zwmaRM
            @Override // java.lang.Runnable
            public final void run() {
                SourceService.lambda$voiceFocus$1(SourceService.this, app, isVoiceFocus, forceRemove);
            }
        });
    }

    public static /* synthetic */ void lambda$voiceFocus$1(SourceService sourceService, String app, boolean isVoiceFocus, boolean forceRemove) {
        Log.i(TAG, "voiceFocus app:" + app + " isVoiceFocus:" + isVoiceFocus + " forceRemove:" + forceRemove);
        if (isVoiceFocus) {
            sourceService.mAudioVoicePackage.set(app);
            sourceService.mVoiceMixRecord = sourceService.new Record(app, 2147483647L, -1);
            sourceService.fadeoutByVoice();
            return;
        }
        Log.i(TAG, "voiceFocus mAudioVoicePackage:" + sourceService.mAudioVoicePackage.get());
        if (app.equals(sourceService.mAudioVoicePackage.get()) || forceRemove) {
            if (TextUtils.isEmpty(sourceService.mAudioVoicePackage.get())) {
                sourceService.updateVolume();
                return;
            }
            sourceService.mAudioVoicePackage.set("");
            sourceService.fadeinByVoice();
            sourceService.mFadeHandler.removeMessages(7);
            sourceService.mFadeHandler.sendMessageDelayed(sourceService.mFadeHandler.obtainMessage(7, 10, 0), 100L);
        }
    }

    private synchronized void onFocusStateChange(String app, int requestResult, int contentType, int gainRequest, int grant) {
        Log.i(TAG, String.format("onFocusStateChange [%s] requestResult %d, contentType:%d,gainRequest:%d, grant:%d", app, Integer.valueOf(requestResult), Integer.valueOf(contentType), Integer.valueOf(gainRequest), Integer.valueOf(grant)));
        if (requestResult == 1) {
            if (grant == 1 && gainRequest == 1) {
                this.mAudioFocusPackage.set(app);
                Log.i(TAG, String.format("onFocusStateChange mAudioFocusPackage:%s", this.mAudioFocusPackage.get()));
                voiceFocus(false, app, true);
            }
            if ((contentType == 2 || contentType == 1 || contentType == 4) && app.equals(ACTIVE_HUAWEI)) {
                if (grant == 1) {
                    if (gainRequest == 1) {
                        SystemProperties.set("sys.sourcing.huaweinavi", "0");
                        if (!ACTIVE_HUAWEI.equals(this.CurrentSource)) {
                            muxMediaPlayer(app, -1000);
                        }
                    } else if (gainRequest == 3 || gainRequest == 2) {
                        SystemProperties.set("sys.sourcing.huaweinavi", "1");
                    }
                } else {
                    if (gainRequest == 3 || gainRequest == 2) {
                        SystemProperties.set("sys.sourcing.huaweinavi", "0");
                    }
                    updateVolume();
                }
            }
            if (app.equals(ACTIVE_ZJ_CARPLAY) || app.equals(ACTIVE_LT_CARPLAY)) {
                if (grant == 1) {
                    if (contentType == 1) {
                        if (gainRequest == 3 || gainRequest == 2) {
                            voiceFocus(true, app, false);
                        }
                    } else if (contentType == 2 && gainRequest == 1) {
                        if (app.equals(this.mAudioMixPackage.get())) {
                            this.mAudioMixPackage.set("");
                        }
                        if (!this.CurrentSource.equals(ACTIVE_ZJ_CARPLAY) || (!this.CurrentSource.equals(ACTIVE_LT_CARPLAY) && !this.CurrentSource.equals("/system/bin//LinkSDKServer"))) {
                            muxMediaPlayer(app, -1000);
                        }
                    } else if ((contentType == 2 || contentType == 4) && gainRequest == 3 && LinkManager.getInstance().isHicarConnect()) {
                        this.mAudioMixPackage.set(app);
                    }
                } else {
                    if (contentType == 1) {
                        if (gainRequest == 3 || gainRequest == 2) {
                            voiceFocus(false, app, false);
                        }
                    } else if ((contentType == 2 || contentType == 4) && gainRequest == 3 && app.equals(this.mAudioMixPackage.get())) {
                        this.mAudioMixPackage.set("");
                    }
                    updateVolume();
                }
            }
            if (contentType == 2 && "com.google android.googlequicksearchbox".equals(app)) {
                if (grant == 1) {
                    if (gainRequest == 4) {
                        voiceFocus(true, app, false);
                    } else {
                        voiceFocus(false, app, false);
                    }
                } else if (gainRequest == 4) {
                    voiceFocus(false, app, false);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMediaStatus(String app, int duration, String name, int session) {
        if ("1".equals(SystemProperties.get("sys.sourcing.huaweinavi")) && app.equals(ACTIVE_HUAWEI)) {
            duration = 20;
        }
        if (duration > 2000 && ((ACTIVE_ZJ_CARPLAY.equals(this.mAudioVoicePackage.get()) && app.equals(this.mAudioVoicePackage.get())) || (ACTIVE_ZJ_CARPLAY.equals(this.mAudioMixPackage.get()) && app.equals(this.mAudioMixPackage.get())))) {
            duration = 20;
            if (mDebugLevel > 2000) {
                Log.i(TAG, String.format("onMediaStatus zj  duration = 2000, app:%s, session %d", app, Integer.valueOf(session)));
            }
        } else if (ACTIVE_LT_CARPLAY.equals(this.mAudioVoicePackage.get()) && duration > 2000 && app.equals("/system/bin//LinkSDKServer")) {
            duration = 20;
            if (mDebugLevel > 2000) {
                Log.i(TAG, String.format("onMediaStatus LT  duration = 2000, app:%s, session %d", app, Integer.valueOf(session)));
            }
        }
        if (mDebugLevel > 1) {
            Log.i(TAG, String.format("Media from [%s] duration %d ms, name:%s, session %d", app, Integer.valueOf(duration), name, Integer.valueOf(session)));
        }
        String app2 = app.replaceAll(":.*", "");
        if (app2.equals(this.mPerpareToKill.get())) {
            return;
        }
        if ("com.hcn.audioinputsource".equals(app2) && !this.mTopPackage.startsWith("com.auto.hcamera")) {
            return;
        }
        if (app2.equals("com.android.bluetooth")) {
            if (duration > 20000) {
                this.mBtMusicSession = session;
                if (session != this.mPolicy.mSessionId) {
                    Log.i(TAG, "bluetooth music session " + session);
                }
                if (session != this.mPolicy.mSessionId && this.mAudioPathStatus.contains(PATH.BT_UI)) {
                    this.mPolicy.onSessionChanged(session);
                    return;
                }
                return;
            }
            return;
        }
        if (MUSICPKG.contains(app2)) {
            if (session != this.mPolicy.mSessionId) {
                this.mPolicy.onSessionChanged(session);
                return;
            }
            return;
        }
        String[] t = app2.split("\\.");
        if (t.length > 4 && !app2.contains("cardroid")) {
            int pos = app2.lastIndexOf(".");
            app2 = app2.substring(0, pos);
        }
        if (duration > 20000) {
            muxMediaPlayer(app2, session);
            return;
        }
        if (duration >= 0) {
            long endat = SystemClock.uptimeMillis() + ((long) duration) + 2000;
            if (VOICEPKG.contains(app2) || app2.equals(this.mAudioVoicePackage.get()) || (ACTIVE_LT_CARPLAY.equals(this.mAudioVoicePackage.get()) && app2.equals("/system/bin//LinkSDKServer"))) {
                long xendat = this.mVoiceMixRecord != null ? Math.max(this.mVoiceMixRecord.endat, 1000 + endat) : 1000 + endat;
                this.mVoiceMixRecord = new Record(app2, xendat, session);
                fadeoutByVoice();
                this.mPolicy.onMixSessionChanged(session);
                return;
            }
            if (this.mMixRecord == null) {
                this.mMixRecord = new Record(app2, endat, session);
                audioFadeout();
                this.mPolicy.onMixSessionChanged(session);
            } else {
                if (this.mMixRecord.pkt.equals(app2)) {
                    this.mMixRecord.endat = Math.max(this.mMixRecord.endat, endat);
                    audioFadeout();
                    this.mPolicy.onMixSessionChanged(session);
                    return;
                }
                Log.w(TAG, "navigation sound conflict," + this.mMixRecord.pkt + " and " + app2);
                this.mMixRecord.endat = Math.max(this.mMixRecord.endat, endat);
                audioFadeout();
                this.mPolicy.onMixSessionChanged(session);
            }
        }
    }

    private void muxMediaPlayer(String pkg, int session) {
        EnumSet<E> enumSetClone = this.mAudioPathStatus.clone();
        String prevApp = this.CurrentSource;
        this.mAudioPathStatus.removeAll(this.EMPTY_PATH);
        this.mAudioPathStatus.add(PATH.MUSIC);
        this.CurrentSource = pkg;
        if (!enumSetClone.equals(this.mAudioPathStatus)) {
            Log.i(TAG, "muxMediaPlayer : " + enumSetClone + " --> " + this.mAudioPathStatus + ", session " + session);
            this.mPolicy.onNormalAudioPath(this.mAudioPathStatus);
        }
        if (!this.CurrentSource.equals(prevApp)) {
            Log.i(TAG, "muxMediaPlayer prepare to Kill " + prevApp + ", new package " + pkg + ", session " + session);
            if (!prevApp.equals(ACTIVE_HUAWEI)) {
                this.mPolicy.setMusicFadeout();
            }
            this.mPerpareToKill.set(prevApp);
            Message msg = this.mFadeHandler.obtainMessage(4, prevApp);
            this.mFadeHandler.sendMessageDelayed(msg, 1500L);
            this.mFadeHandler.removeMessages(6);
            this.mMusicInfo.className = this.CurrentSource;
            this.mMusicInfo.fileName = "";
            this.mMusicInfo.duration = 0;
            this.mMusicInfo.position = 0;
            this.mMusicInfo.state = "";
            SystemProperties.set("sys.sourcing.package", this.CurrentSource);
            callManager(this.CurrentSource, HConfig.MBoardCode.FEIYIN_DSP_BOARD);
            McuService.getMcuService().onSourceChanged(this.CurrentSource);
            setSourceChanged4Camerad(this.CurrentSource);
        }
        if (session > 0 && session != this.mPolicy.mSessionId) {
            this.mPolicy.onSessionChanged(session);
        }
    }

    private void audioFadeout() {
        McuService.getMcuService().requestNavigation(true);
        if (!this.mAudioPathStatus.contains(PATH.MIX)) {
            Log.v(TAG, "navigation audioFadeout by " + this.mMixRecord.pkt + ", session " + this.mMixRecord.session);
            this.mAudioPathStatus.add(PATH.MIX);
            StringBuilder sb = new StringBuilder();
            sb.append("audioFadeout : ");
            sb.append(this.mAudioPathStatus);
            Log.i(TAG, sb.toString());
            this.mPolicy.onMixingStatus(this.mAudioPathStatus);
        }
        this.mFadeHandler.removeMessages(2);
        this.mFadeHandler.sendEmptyMessageAtTime(2, this.mMixRecord.endat);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void audioFadein() {
        McuService.getMcuService().requestNavigation(false);
        if (this.mAudioPathStatus.contains(PATH.MIX)) {
            Log.v(TAG, "navigation sound finished by " + this.mMixRecord.pkt);
            updateVolume();
            this.mAudioPathStatus.remove(PATH.MIX);
            this.mMixRecord = null;
            Log.i(TAG, "audioFadein : " + this.mAudioPathStatus);
            this.mPolicy.onMixingStatus(this.mAudioPathStatus);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abortMix(String app, int session) {
        Log.i(TAG, String.format("abort mix media from [%s] session %d", app, Integer.valueOf(session)));
        if (this.mMixRecord != null && this.mMixRecord.session == session) {
            this.mMixRecord.endat = SystemClock.uptimeMillis() + 300;
            this.mFadeHandler.removeMessages(2);
            this.mFadeHandler.sendEmptyMessageAtTime(2, this.mMixRecord.endat);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fadeoutByVoice(String pkg, int endat, int session) {
        this.mVoiceMixRecord = new Record(pkg, endat, session);
        fadeoutByVoice();
    }

    private void fadeoutByVoice() {
        if (!this.mAudioPathStatus.contains(PATH.MIXVOC)) {
            Log.v(TAG, "voice fadeoutByVoice by " + this.mVoiceMixRecord.pkt + ", session " + this.mVoiceMixRecord.session);
            this.mAudioPathStatus.add(PATH.MIXVOC);
            StringBuilder sb = new StringBuilder();
            sb.append("fadeoutByVoice : ");
            sb.append(this.mAudioPathStatus);
            Log.i(TAG, sb.toString());
            this.mPolicy.onMixingStatus(this.mAudioPathStatus);
        }
        this.mFadeHandler.removeMessages(3);
        this.mFadeHandler.sendEmptyMessageAtTime(3, this.mVoiceMixRecord.endat);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fadeinByVoice() {
        if (this.mAudioPathStatus.contains(PATH.MIXVOC)) {
            Log.v(TAG, "voice sound finished by " + this.mVoiceMixRecord.pkt);
            updateVolume();
            this.mAudioPathStatus.remove(PATH.MIXVOC);
            this.mVoiceMixRecord = null;
            Log.i(TAG, "audioFadein : " + this.mAudioPathStatus);
            this.mPolicy.onMixingStatus(this.mAudioPathStatus);
            this.mFadeHandler.removeMessages(3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRDSActivie(boolean active) {
        EnumSet<E> enumSetClone = this.mAudioPathStatus.clone();
        if (active) {
            this.mAudioPathStatus.add(PATH.RDS);
        } else {
            this.mAudioPathStatus.remove(PATH.RDS);
        }
        if (!enumSetClone.equals(this.mAudioPathStatus)) {
            Log.i(TAG, "onRDSActivie : " + enumSetClone + " --> " + this.mAudioPathStatus);
            this.mPolicy.onNormalAudioPath(this.mAudioPathStatus);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBackAuxActivie(String packageName, boolean active, boolean exclusiveMode) {
        EnumSet<E> enumSetClone = this.mAudioPathStatus.clone();
        if (!exclusiveMode) {
            if (active) {
                this.mAudioPathStatus.add(PATH.AUX);
                SystemProperties.set(HConfig.exclusive_audio_focus, "");
            } else {
                this.mAudioPathStatus.remove(PATH.AUX);
            }
            if (!enumSetClone.equals(this.mAudioPathStatus)) {
                Log.i(TAG, "onBackAuxActivie : " + enumSetClone + " --> " + this.mAudioPathStatus);
                this.mPolicy.onNormalAudioPath(this.mAudioPathStatus);
                return;
            }
            return;
        }
        if (active) {
            if (!this.mAudioPathStatus.contains(PATH.ONSTAR)) {
                this.mAudioPathStatus.add(PATH.ONSTAR);
                Log.i(TAG, "enter onstar : " + this.mAudioPathStatus);
                this.mPolicy.onMixingStatus(this.mAudioPathStatus);
                SystemProperties.set(HConfig.exclusive_audio_focus, packageName);
            }
            if (!enumSetClone.equals(this.mAudioPathStatus)) {
                Log.i(TAG, "onBackAuxActivie : " + enumSetClone + " --> " + this.mAudioPathStatus);
                this.mPolicy.onNormalAudioPath(this.mAudioPathStatus);
                return;
            }
            return;
        }
        if (this.mAudioPathStatus.contains(PATH.ONSTAR)) {
            this.mAudioPathStatus.remove(PATH.ONSTAR);
            Log.i(TAG, "exit onstar:" + this.mAudioPathStatus);
            this.mPolicy.onMixingStatus(this.mAudioPathStatus);
            SystemProperties.set(HConfig.exclusive_audio_focus, "");
        }
        if (!enumSetClone.equals(this.mAudioPathStatus)) {
            Log.i(TAG, "onBackAuxActivie : " + enumSetClone + " --> " + this.mAudioPathStatus);
            this.mPolicy.onNormalAudioPath(this.mAudioPathStatus);
        }
    }

    private boolean IsSourceActivity(String app) {
        return app.contains(ACTIVE_RADIO) || app.contains(ACTIVE_MPEG) || app.contains("com.auto.hcamera") || app.contains(ACTIVE_SIRI) || app.contains(ACTIVE_TV) || app.contains(ACTIVE_BT) || app.contains(ACTIVE_CMMB) || app.contains(ACTIVE_ONSTAR) || app.contains(ACTIVE_DAB) || app.contains(ACTIVE_SXM);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVolume() {
        boolean activeRingStream = AudioSystem.isStreamActive(2, 0);
        boolean activeCallStream = AudioSystem.isStreamActive(0, 0);
        if (activeCallStream) {
            if (!this.mActiveCallStream) {
                resetStreamVolume(0);
                this.mFadeHandler.removeMessages(7);
            }
        } else if (activeRingStream && !this.mActiveRingStream) {
            resetStreamVolume(2);
            this.mFadeHandler.removeMessages(7);
        }
        if (mDebugLevel >= 100000) {
            Log.i(TAG, "activeCallStream:" + activeCallStream + " mActiveCallStream:" + this.mActiveCallStream);
            Log.i(TAG, "activeRingStream:" + activeRingStream + " mActiveRingStream:" + this.mActiveRingStream);
        }
        if (!activeRingStream && !activeCallStream && (this.mActiveRingStream != activeRingStream || this.mActiveCallStream != activeCallStream)) {
            resetStreamVolume(3);
            this.mFadeHandler.removeMessages(7);
        }
        this.mActiveRingStream = activeRingStream;
        this.mActiveCallStream = activeCallStream;
    }

    @Override // android.sourceservice.ISourceService.Stub, android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws Throwable {
        int position;
        if (code == 101) {
            data.enforceInterface(DESCRIPTOR);
            final String action = data.readString();
            final String pkg = data.readString();
            int extra = -1;
            if (action.equals("OnResume") && pkg.contains("com.auto.hcamera")) {
                Intent intent = (Intent) Intent.CREATOR.createFromParcel(data);
                extra = intent.getIntExtra("VIDEO_TYPE", -1);
            }
            final int extra2 = extra;
            final String reason = action.equals("DESTROY") ? data.readString() : "";
            this.mFadeHandler.post(new Runnable() { // from class: android.sourceservice.-$$Lambda$SourceService$h5pNto14qaVNvuLM2IAjhM-FLDA
                @Override // java.lang.Runnable
                public final void run() {
                    SourceService.lambda$onTransact$2(SourceService.this, action, pkg, extra2, reason);
                }
            });
            return true;
        }
        if (code == 201) {
            data.enforceInterface(DESCRIPTOR);
            final String app = data.readString();
            final int session = data.readInt();
            final String path = data.readString();
            String state = data.readString();
            final int duration = data.readInt();
            int position2 = data.readInt();
            if ("start".equals(state)) {
                position = position2;
                this.mFadeHandler.post(new Runnable() { // from class: android.sourceservice.-$$Lambda$SourceService$dqYwL8JC28y-8Y2JBEN5Eti-oJs
                    @Override // java.lang.Runnable
                    public final void run() {
                        SourceService.this.onMediaStatus(app, duration, path, session);
                    }
                });
                updateVolume();
            } else {
                position = position2;
                if (duration < 20000 && "stopped".equals(state)) {
                    this.mFadeHandler.post(new Runnable() { // from class: android.sourceservice.-$$Lambda$SourceService$aVodCqy0sq0-5NsZfihn3r1iW_k
                        @Override // java.lang.Runnable
                        public final void run() {
                            SourceService.this.abortMix(app, session);
                        }
                    });
                }
            }
            if (app.startsWith(this.CurrentSource) && !TextUtils.isEmpty(this.CurrentSource)) {
                this.mMusicInfo.className = this.mCurrentClass;
                this.mMusicInfo.state = state;
                if ("stopped".equals(state)) {
                    this.mMusicInfo.duration = 0;
                    this.mMusicInfo.position = 0;
                    this.mMusicInfo.fileName = "";
                } else {
                    this.mMusicInfo.duration = duration;
                    this.mMusicInfo.fileName = path;
                    if (position >= 0) {
                        this.mMusicInfo.position = position;
                    }
                }
                callManager(this.mMusicInfo);
                return true;
            }
            return true;
        }
        if (code == 301) {
            data.enforceInterface(DESCRIPTOR);
            final boolean rdson = data.readBoolean();
            this.mFadeHandler.post(new Runnable() { // from class: android.sourceservice.-$$Lambda$SourceService$Eqa5X3uMJrp197hGuuxq8_Tmybs
                @Override // java.lang.Runnable
                public final void run() {
                    SourceService.this.onRDSActivie(rdson);
                }
            });
            return true;
        }
        if (code != 401) {
            if (code == 501) {
                data.enforceInterface(DESCRIPTOR);
                String action2 = data.readString();
                final String pkg2 = data.readString();
                if (VOICEPKG.contains(pkg2)) {
                    if (action2.equals("SHOW")) {
                        Log.i(TAG, "Voice Window showing");
                        this.mFadeHandler.post(new Runnable() { // from class: android.sourceservice.-$$Lambda$SourceService$yRNBJronzfowX_RbOpVSfqN0eX0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SourceService.this.fadeoutByVoice(pkg2, AudioEffect.DONOTCARE, -1);
                            }
                        });
                        McuService.getMcuService().onVoiceWindows(true);
                    } else {
                        Log.i(TAG, "Voice Window dismiss");
                        this.mFadeHandler.post(new Runnable() { // from class: android.sourceservice.-$$Lambda$SourceService$6I2ftTHR-V2JrZDWGek_HZ5esKg
                            @Override // java.lang.Runnable
                            public final void run() {
                                SourceService.this.fadeinByVoice();
                            }
                        });
                        McuService.getMcuService().onVoiceWindows(false);
                    }
                }
                return true;
            }
            if (code == 601) {
                data.enforceInterface(DESCRIPTOR);
                String app2 = data.readString();
                int requestResult = data.readInt();
                int contentType = data.readInt();
                int gainRequest = data.readInt();
                int gain = data.readInt();
                onFocusStateChange(app2, requestResult, contentType, gainRequest, gain);
                return true;
            }
            if (code == 701) {
                data.enforceInterface(DESCRIPTOR);
                MusicInfo info = (MusicInfo) data.readParcelable(MusicInfo.class.getClassLoader());
                if (info != null && !TextUtils.isEmpty(info.className) && (info.className.contains(this.CurrentSource) || (HcnMediaCLASS.contains(info.className) && HCN_MEDIA_PACKAGE_NAME.equals(this.CurrentSource)))) {
                    this.mMusicInfo.className = info.className;
                    this.mMusicInfo.fileName = info.fileName;
                    this.mMusicInfo.state = info.state;
                    this.mMusicInfo.duration = info.duration;
                    this.mMusicInfo.position = info.position;
                    callManager(this.mMusicInfo);
                }
                return true;
            }
            return super.onTransact(code, data, reply, flags);
        }
        data.enforceInterface(DESCRIPTOR);
        final String app3 = data.readString();
        final int session2 = data.readInt();
        final int duration2 = data.readInt();
        String state2 = data.readString();
        if (Util.isDebugVersion()) {
            Log.d(TAG, "onTransact/AUDIOTRACK_CALL_TRANSACTION app:" + app3 + " session:" + session2 + " state:" + state2 + " duration:" + duration2);
        }
        if (!"stopped".equals(state2) && !"paused".equals(state2) && ((!ACTIVE_HUAWEI.equals(app3) && !ACTIVE_ZJ_CARPLAY.equals(app3)) || state2.equals("write"))) {
            this.mFadeHandler.post(new Runnable() { // from class: android.sourceservice.-$$Lambda$SourceService$qyqofvz928TjT11cwBaiJnK0Zws
                @Override // java.lang.Runnable
                public final void run() {
                    SourceService.this.onMediaStatus(app3, duration2, "AudioTrack", session2);
                }
            });
        }
        if (state2.equals("write")) {
            updateVolume();
            state2 = "start";
        }
        if (state2.equals("stopped") && app3.startsWith(this.CurrentSource) && !TextUtils.isEmpty(this.CurrentSource)) {
            this.mMusicInfo.duration = 0;
            this.mMusicInfo.position = 0;
            this.mMusicInfo.fileName = "";
            this.mMusicInfo.className = "";
            this.mMusicInfo.state = "stop";
            SystemProperties.set("sys.sourcing.package", this.CurrentSource);
            this.mPolicy.onSessionChanged(0);
            callManager(this.CurrentSource, AudioEffect.PATH_RING);
        }
        if (app3.startsWith(this.CurrentSource) && !TextUtils.isEmpty(this.CurrentSource) && duration2 > 20000 && !ACTIVE_RADIO.startsWith(this.CurrentSource)) {
            this.mMusicInfo.state = state2;
            callManager(this.mMusicInfo);
        }
        boolean isReversing = CarStatusService.getCarStatusService().getReversing() > 0;
        if (isReversing && ACTIVE_YOUTUBE.contains(app3) && "start".equals(state2)) {
            mBackCarMediaPlayStatus = true;
            McuService.getMcuService().BoardCastMediaKey(McuConstant.K_PAUSE, 1, 0);
            McuService.getMcuService().BoardCastMediaKey(McuConstant.K_PAUSE, 5, 0);
        }
        return true;
    }

    public static /* synthetic */ void lambda$onTransact$2(SourceService sourceService, String action, String pkg, int exv, String reason) {
        String str;
        if ("OnResume".equals(action)) {
            sourceService.mTopPackage = pkg;
            if (pkg.length() <= 90) {
                str = pkg;
            } else {
                str = pkg.substring(0, 87) + "...";
            }
            SystemProperties.set("sys.top.activity", str);
            McuService.getMcuService().onActivityChanged(pkg);
            sourceService.setTopActivity4Camerad(pkg);
            sourceService.mParkingObserver.checkParkingView(pkg);
            Intent intentTopActivity = new Intent(ACTION_TOP_ACTIVITY);
            intentTopActivity.putExtra(EXTRA_TOP_ACTIVITY_PACKAGE, pkg);
            sourceService.mContext.sendBroadcastAsUser(intentTopActivity, UserHandle.CURRENT, null);
        }
        sourceService.onActivityStatus(pkg, action, exv, reason);
    }

    @Override // android.sourceservice.ISourceService
    public MusicInfo getMusicInfo() {
        return this.mMusicInfo;
    }

    @Override // android.sourceservice.ISourceService
    public void setcallback(ISourceCallback callback) {
        synchronized (this.mCallBacks) {
            this.mCallBacks.remove(callback);
            this.mCallBacks.add(callback);
        }
    }

    @Override // android.sourceservice.ISourceService
    public void removecallback(ISourceCallback callback) {
        synchronized (this.mCallBacks) {
            this.mCallBacks.remove(callback);
        }
    }

    @Override // android.sourceservice.ISourceService
    public void onRequestPlayAudio(final String cls) {
        this.mFadeHandler.post(new Runnable() { // from class: android.sourceservice.-$$Lambda$SourceService$6smpX_6jOs5Tf233Vw8pOLFEM9A
            @Override // java.lang.Runnable
            public final void run() {
                SourceService.this.onActivityStatus(cls, "OnResume", -1, "");
            }
        });
    }

    @Override // android.sourceservice.ISourceService
    public void onRequestAuxAudio(final String packageName, final boolean active, final boolean exclusiveMode) {
        this.mFadeHandler.post(new Runnable() { // from class: android.sourceservice.-$$Lambda$SourceService$Ib0KsX89kpiQKOsGv95i9keSxhc
            @Override // java.lang.Runnable
            public final void run() {
                SourceService.this.onBackAuxActivie(packageName, active, exclusiveMode);
            }
        });
    }

    private synchronized void callManager(String pkg, int reason) {
        if (Util.isDebugVersion()) {
            Log.d(TAG, "onSourceChanged: reason = " + reason);
        }
        synchronized (this.mCallBacks) {
            Iterator<ISourceCallback> iter = this.mCallBacks.iterator();
            while (iter.hasNext()) {
                ISourceCallback c = iter.next();
                try {
                    c.onSourceChanged(pkg);
                } catch (RemoteException e) {
                    Log.w(TAG, "callManager " + c + ", lost");
                    iter.remove();
                }
            }
        }
    }

    private synchronized void callManager(MusicInfo info) {
        synchronized (this.mCallBacks) {
            Iterator<ISourceCallback> iter = this.mCallBacks.iterator();
            while (iter.hasNext()) {
                ISourceCallback c = iter.next();
                try {
                    c.onMusicEvent(info);
                } catch (RemoteException e) {
                    Log.w(TAG, "callManager " + c + ", lost");
                    iter.remove();
                }
            }
        }
    }

    private void setSourceChanged4Camerad(String source) {
        IBinder service = ServiceManager.getService("camerad");
        Parcel _data = Parcel.obtain();
        try {
            try {
                _data.writeInterfaceToken("android.carstatus.CarStatusService");
                _data.writeString(source);
                service.transact(3, _data, null, 1);
            } catch (Exception e) {
                Log.w(TAG, "setSourceChanged4Camerad:" + e);
            }
        } finally {
            _data.recycle();
        }
    }

    private void setTopActivity4Camerad(String activity) {
        IBinder service = ServiceManager.getService("camerad");
        if (service == null) {
            return;
        }
        Parcel _data = Parcel.obtain();
        try {
            try {
                _data.writeInterfaceToken("android.carstatus.CarStatusService");
                _data.writeString(activity);
                service.transact(4, _data, null, 1);
            } catch (Exception e) {
                Log.w(TAG, "setTopActivity4Camerad:" + e);
            }
        } finally {
            _data.recycle();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forceStopPackage(String app) {
        long callingId = Binder.clearCallingIdentity();
        try {
            String pkg = Util.findPackageName(this.mContext, app, false);
            if (!TextUtils.isEmpty(app)) {
                ActivityManager.getService().forceStopPackage(pkg, -1);
            }
        } catch (RemoteException e) {
        } finally {
            Binder.restoreCallingIdentity(callingId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadNaviStreamGain() {
        int gain = Settings.System.getInt(this.mContext.getContentResolver(), HConfig.media_navi_gain, 0);
        this.mPolicy.setStreamVolumeGain(5, gain);
    }

    @Override // android.sourceservice.ISourceService
    public int doExtAudioEffect(int cmd, int[] data) {
        if (cmd == 1000) {
            SystemProperties.set("sys.audio.lr.balance", String.format(Locale.ENGLISH, "%d,%d", Integer.valueOf(data[0]), Integer.valueOf(data[1])));
            return 0;
        }
        return ExtAudioMuxer.SendCmdAudioMux(cmd, data);
    }

    private class SettingsObserver extends ContentObserver {
        SettingsObserver() {
            super(new Handler());
            ContentResolver cr = SourceService.this.mContext.getContentResolver();
            cr.registerContentObserver(Settings.System.getUriFor(HConfig.media_navi_gain), false, this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange);
            SourceService.this.mContext.getContentResolver();
            String key = uri.toString().replace("content://settings/system/", "");
            Log.i(SourceService.TAG, "Setting changed " + uri + ", " + key);
            if (((key.hashCode() == -263078435 && key.equals(HConfig.media_navi_gain)) ? (byte) 0 : (byte) -1) == 0) {
                SourceService.this.loadNaviStreamGain();
            }
        }
    }

    private void setOnActivityResume(String pkg) {
        Log.i(TAG, "setOnActivityResume pkg" + pkg);
        if (ACTIVE_LT_CARPLAY.equals(pkg)) {
            forceStopPackage("com.tima.carnet.vt");
            forceStopPackage(ACTIVE_EASYCONN);
            int value = SystemProperties.getInt("service.ltlink.enable", 0);
            if (value == 0) {
                SystemProperties.set("service.ltlink.enable", "1");
                return;
            }
            return;
        }
        if (ACTIVE_ZJ_CARPLAY.equals(pkg)) {
            forceStopPackage("com.tima.carnet.vt");
            forceStopPackage(ACTIVE_EASYCONN);
            int value2 = SystemProperties.getInt("service.zlink.enable", 0);
            if (value2 == 0) {
                SystemProperties.set("service.zlink.enable", "1");
                return;
            }
            return;
        }
        if ("com.tima.carnet.vt".equals(pkg)) {
            forceStopPackage(ACTIVE_EASYCONN);
            forceStopPackage(ACTIVE_ZJ_CARPLAY);
            int value3 = SystemProperties.getInt("service.zlink.enable", 0);
            if (value3 == 1) {
                SystemProperties.set("service.zlink.enable", "0");
                return;
            }
            return;
        }
        if (ACTIVE_EASYCONN.equals(pkg)) {
            forceStopPackage(ACTIVE_ZJ_CARPLAY);
            int value4 = SystemProperties.getInt("service.zlink.enable", 0);
            if (value4 == 1) {
                SystemProperties.set("service.zlink.enable", "0");
            }
            forceStopPackage("com.tima.carnet.vt");
        }
    }

    private boolean isAuxAudioEnable() {
        int i = Settings.System.getInt(this.mContext.getContentResolver(), HConfig.aux_audio_status, ExtAudioMuxer.ExtAudioAvailable ? 1 : 0);
        StringBuilder sb = new StringBuilder();
        sb.append("isAuxAudioEnable: ");
        sb.append(i == 1);
        sb.append(",ExtAudioAvailable:");
        sb.append(ExtAudioMuxer.ExtAudioAvailable);
        Log.d(TAG, sb.toString());
        return i == 1;
    }

    public static void setBackCarMediaPlay() throws Throwable {
        if (mBackCarMediaPlayStatus) {
            mBackCarMediaPlayStatus = false;
            McuService.getMcuService().BoardCastMediaKey(McuConstant.K_PLAY, 1, 0);
            McuService.getMcuService().BoardCastMediaKey(McuConstant.K_PLAY, 5, 0);
        }
    }

    @Override // android.os.Binder
    protected void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        String opt;
        pw.println("Source info Manager (dumpsys sourceinfo)\n");
        pw.append("Top activity=").append((CharSequence) this.mTopPackage).append("\n");
        pw.append("Package=").append((CharSequence) this.CurrentSource).append("\n");
        pw.println("Music:");
        pw.println("    className :" + this.mMusicInfo.className);
        pw.println("    Name    :" + this.mMusicInfo.fileName);
        pw.println("    State   :" + this.mMusicInfo.state);
        pw.println("    Duration:" + this.mMusicInfo.duration);
        pw.println("    Position:" + this.mMusicInfo.position);
        pw.println("    Session:" + this.mPolicy.mSessionId);
        pw.append("Path=").append((CharSequence) this.mAudioPathStatus.toString()).append("\n");
        if (this.mMixRecord != null) {
            pw.println("MIX=" + this.mMixRecord.pkt + ",session=" + this.mMixRecord.session);
        }
        if (this.mVoiceMixRecord != null) {
            pw.println("VOCMIX=" + this.mVoiceMixRecord.pkt + ",session=" + this.mVoiceMixRecord.session);
        }
        pw.append((CharSequence) ("BT Music Session=" + this.mBtMusicSession)).append("\n");
        pw.append((CharSequence) ("BluetoothVoicing=" + this.mBluetoothVoicing)).append("\n");
        pw.println("ExtAudioAvailable : " + ExtAudioMuxer.ExtAudioAvailable);
        pw.println("mAudioFocusPackage : " + this.mAudioFocusPackage.get());
        pw.println("mAudioVoicePackage : " + this.mAudioVoicePackage.get());
        pw.println("mAudioMixPackage : " + this.mAudioMixPackage.get());
        int opti = 0;
        while (opti < args.length && (opt = args[opti]) != null && opt.length() > 0 && opt.charAt(0) == '-') {
            opti++;
            if ("-d".equals(opt)) {
                if (opti < args.length) {
                    mDebugLevel = Integer.parseInt(args[opti]);
                    opti++;
                } else {
                    pw.println("Error: -d option requires debug level");
                    return;
                }
            }
        }
    }

    @Override // android.sourceservice.ISourceService
    public void setInputPathMute(int path, boolean mute) {
        Log.d(TAG, "setInputPathMute path:" + path + " mute:" + mute);
        ExtAudioMuxer.setInputPathMute(path, mute, 4);
    }
}
