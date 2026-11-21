package android.link;

import android.Configures.HConfig;
import android.Configures.HConstant;
import android.carsource.McuConstant;
import android.carsource.McuService;
import android.carstatus.CarStatusService;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.sourceservice.SourceService;
import android.util.DisplayMetrics;
import android.util.Log;
import android.utilities.Util;
import android.view.WindowManager;
import java.io.File;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class CarZlinkInterface implements LinkManager.LinkInterface {
    public static final String BROAD_GOTO_ZLINK_PAGE = "com.link.page";
    public static final String DATA_PAGE_AUTO = "auto";
    public static final String DATA_PAGE_CARLIFE = "carlife";
    public static final String DATA_PAGE_CARPLAY = "carplay";
    public static final String DATA_PAGE_DLNA = "dlna";
    public static final String DATA_PAGE_HICAR = "hicar";
    public static final String DATA_PAGE_MIRROR = "mirror";
    public static final String EXTRA_PAGE = "page";
    private static final int MSG_UPDATE_STATE_AFTER_RUNAPP = 0;
    private static final String TAG = "LinkManager.CarZlinkInterface";
    Context mContext;
    private int mZlinkVersion;
    private boolean mIsCarPlayPhoneCall = false;
    private boolean mIsZlinkMirrorLink = false;
    private String mUsbPath = "/sys/devices/platform/mt_usb/usb_mode";
    private boolean mZlinkInfoConnect = false;
    private boolean mZlinkInfoAndroidAutoCall = false;
    private boolean mZlinkHicarCloseBt = true;
    private int mLastAcc = 1;
    Handler myHandler = new Handler() { // from class: android.link.CarZlinkInterface.2
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                CarZlinkInterface.this.updateSystemStateAfterRunApp();
            }
        }
    };

    public CarZlinkInterface(Context context) {
        this.mContext = null;
        this.mContext = context;
        IntentFilter filter = new IntentFilter();
        filter.addAction(SourceService.ACTIVE_ZJ_CARPLAY);
        filter.addAction(BROAD_GOTO_ZLINK_PAGE);
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: android.link.CarZlinkInterface.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) throws Throwable {
                String action = intent.getAction();
                if (action.equals(SourceService.ACTIVE_ZJ_CARPLAY)) {
                    String status = intent.getStringExtra("status");
                    String phoneMode = intent.getStringExtra("phoneMode");
                    String phoneType = intent.getStringExtra("phoneType");
                    CarZlinkInterface.this.onBroadZlink(status, phoneMode, phoneType);
                    return;
                }
                if (action.equals(CarZlinkInterface.BROAD_GOTO_ZLINK_PAGE)) {
                    String zlinkpage = intent.getStringExtra(CarZlinkInterface.EXTRA_PAGE);
                    CarZlinkInterface.this.gotoPage(zlinkpage);
                }
            }
        }, filter);
        SystemProperties.set("rw.zlink.hu.acc", "0");
        SystemProperties.set("persist.sys.linktype", "zlink");
        SystemProperties.set("rw.zlink.audiostream.speech", "3");
        SystemProperties.set("persist.sys.lic.dir", "/apd/zlink/");
        this.mZlinkVersion = LinkManager.getVersionCode(this.mContext, SourceService.ACTIVE_ZJ_CARPLAY);
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) this.mContext.getSystemService("window");
        wm.getDefaultDisplay().getRealMetrics(dm);
        Log.i(TAG, "CarZlinkInterface getRealMetrics widthPixels:" + dm.widthPixels + " heightPixels:" + dm.heightPixels);
        try {
            if ((dm.heightPixels == 360 && dm.widthPixels == 960) || (dm.heightPixels == 960 && dm.widthPixels == 360)) {
                SystemProperties.set("ro.zlink.platform_product", "8163_960x360");
            } else {
                SystemProperties.set("ro.zlink.platform_product", "8163");
            }
        } catch (Exception e) {
        }
        localeChange(false);
        SystemProperties.set("service.zlink.enable", "1");
        this.myHandler.removeMessages(0);
        this.myHandler.sendEmptyMessageDelayed(0, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void gotoPage(String zlinkpage) {
        String mode = SystemProperties.get("zj.launcher.mode");
        String currType = SystemProperties.get("zj.phonelink.type", "");
        String wilePage = zlinkpage;
        Log.i(TAG, " gotoPage mode:" + mode + " currType:" + currType);
        if (mode != null && mode.equals("multi_launcher_mode")) {
            if (zlinkpage == null || zlinkpage.isEmpty()) {
                wilePage = currType;
            }
            if (wilePage.contains(DATA_PAGE_AUTO)) {
                Util.startActivityByClassName(this.mContext, SourceService.ACTIVE_ZJ_CARPLAY, "com.zjinnova.android.zlink.features.launcher.AutoActivity");
                return;
            }
            if (wilePage.contains(DATA_PAGE_CARPLAY)) {
                Util.startActivityByClassName(this.mContext, SourceService.ACTIVE_ZJ_CARPLAY, "com.zjinnova.android.zlink.features.launcher.CarPlayActivity");
                return;
            }
            if (wilePage.contains(DATA_PAGE_MIRROR)) {
                Util.startActivityByClassName(this.mContext, SourceService.ACTIVE_ZJ_CARPLAY, "com.zjinnova.android.zlink.features.launcher.MirrorActivity");
                return;
            }
            if (wilePage.contains(DATA_PAGE_HICAR)) {
                Util.startActivityByClassName(this.mContext, SourceService.ACTIVE_ZJ_CARPLAY, "com.zjinnova.android.zlink.features.launcher.HiCarActivity");
                return;
            } else if (wilePage.contains(DATA_PAGE_DLNA)) {
                Util.startActivityByClassName(this.mContext, SourceService.ACTIVE_ZJ_CARPLAY, "com.zjinnova.android.zlink.features.launcher.DlnaActivity");
                return;
            } else {
                if (wilePage.contains(DATA_PAGE_CARLIFE)) {
                    Util.startActivityByClassName(this.mContext, SourceService.ACTIVE_ZJ_CARPLAY, "com.zjinnova.android.zlink.features.launcher.CarLifeActivity");
                    return;
                }
                return;
            }
        }
        Util.startPackage(this.mContext, SourceService.ACTIVE_ZJ_CARPLAY);
    }

    private void sendBroadCast(String state, String phoneMode, String phoneType) {
        if (state == null) {
            return;
        }
        boolean needSend = true;
        if (state.equals("DISCONNECT")) {
            this.mZlinkInfoConnect = false;
            Intent intentdisconnect = new Intent("com.hcn.link");
            intentdisconnect.putExtra("status", "DISCONNECTED");
            if (phoneMode != null && phoneMode.contains(DATA_PAGE_HICAR)) {
                intentdisconnect.putExtra("type", DATA_PAGE_HICAR);
                if (this.mZlinkHicarCloseBt) {
                    intentdisconnect.putExtra("bluetooth", "openBt");
                } else {
                    intentdisconnect.putExtra("bluetooth", "resumeA2dp");
                }
            } else if (phoneMode != null && phoneMode.contains("airplay")) {
                intentdisconnect.putExtra("type", "airplay");
                intentdisconnect.putExtra("bluetooth", "NULL");
            } else if (phoneMode != null && phoneMode.contains(DATA_PAGE_CARLIFE)) {
                intentdisconnect.putExtra("type", DATA_PAGE_CARLIFE);
                intentdisconnect.putExtra("bluetooth", "NULL");
            } else if (phoneMode != null && phoneMode.contains(DATA_PAGE_AUTO)) {
                intentdisconnect.putExtra("type", "androidauto");
                intentdisconnect.putExtra("bluetooth", "resumeA2dp");
            } else if (phoneMode != null && phoneMode.contains(DATA_PAGE_MIRROR)) {
                intentdisconnect.putExtra("type", "androidMirror");
                intentdisconnect.putExtra("bluetooth", "NULL");
            } else if (phoneMode != null && phoneMode.contains(DATA_PAGE_CARPLAY)) {
                intentdisconnect.putExtra("type", DATA_PAGE_CARPLAY);
                intentdisconnect.putExtra("bluetooth", "openBt");
            } else {
                needSend = false;
            }
            if (needSend) {
                this.mContext.sendBroadcastAsUser(intentdisconnect, UserHandle.CURRENT, null);
                return;
            }
            return;
        }
        if (state.equals("CONNECTED")) {
            this.mZlinkInfoConnect = true;
            Intent intentconnect = new Intent("com.hcn.link");
            intentconnect.putExtra("status", "CONNECTED");
            if (phoneMode.contains(DATA_PAGE_HICAR)) {
                try {
                    this.mZlinkHicarCloseBt = SystemProperties.getBoolean("zj.hicar.disable.bt", false);
                } catch (Exception e) {
                    Log.i(TAG, "getprop zj.hicar.disable.bt error");
                }
                intentconnect.putExtra("type", DATA_PAGE_HICAR);
                if (this.mZlinkHicarCloseBt) {
                    intentconnect.putExtra("bluetooth", "closeBt");
                } else {
                    intentconnect.putExtra("bluetooth", "stopA2dp");
                }
                updateSystemStateAfterConnect();
            } else if (phoneMode != null && phoneMode.contains("airplay")) {
                intentconnect.putExtra("type", "airplay");
                intentconnect.putExtra("bluetooth", "NULL");
            } else if (phoneMode != null && phoneMode.contains(DATA_PAGE_CARLIFE)) {
                intentconnect.putExtra("type", DATA_PAGE_CARLIFE);
                intentconnect.putExtra("bluetooth", "NULL");
                updateSystemStateAfterConnect();
            } else if (phoneMode != null && phoneMode.contains(DATA_PAGE_AUTO)) {
                intentconnect.putExtra("type", "androidauto");
                intentconnect.putExtra("bluetooth", "stopA2dp");
                updateSystemStateAfterConnect();
            } else if (phoneMode != null && phoneMode.contains(DATA_PAGE_MIRROR)) {
                intentconnect.putExtra("type", "androidMirror");
                intentconnect.putExtra("bluetooth", "NULL");
            } else if (phoneMode != null && phoneMode.contains(DATA_PAGE_CARPLAY)) {
                intentconnect.putExtra("type", DATA_PAGE_CARPLAY);
                intentconnect.putExtra("bluetooth", "closeBt");
                updateSystemStateAfterConnect();
            } else {
                needSend = false;
            }
            if (needSend) {
                this.mContext.sendBroadcastAsUser(intentconnect, UserHandle.CURRENT, null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBroadZlink(String state, String phoneMode, String phoneType) throws Throwable {
        Log.i(TAG, "onBroadZlink state:" + state + " phoneMode:" + phoneMode + "phoneType:" + phoneType);
        if (state == null) {
            return;
        }
        sendBroadCast(state, phoneMode, phoneType);
        boolean useAmv = false;
        if (((state.equals("PHONE_CALL_OFF") && phoneMode != null && (phoneMode.contains(DATA_PAGE_HICAR) || phoneMode.contains(DATA_PAGE_CARPLAY))) || state.equals("DISCONNECT")) && this.mIsCarPlayPhoneCall) {
            this.mIsCarPlayPhoneCall = false;
            SystemProperties.set("sys.nonsystem.callstate", "0");
            Intent intent = new Intent(HConstant.CARPLAY_PHONE_CALL_OFF);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT, null);
            this.mZlinkInfoAndroidAutoCall = false;
        } else if (state.equals("PHONE_CALL_ON") && phoneMode != null && (phoneMode.contains(DATA_PAGE_HICAR) || phoneMode.contains(DATA_PAGE_CARPLAY))) {
            Intent intent2 = new Intent(HConstant.CARPLAY_PHONE_CALL_ON);
            this.mContext.sendBroadcastAsUser(intent2, UserHandle.CURRENT, null);
            this.mIsCarPlayPhoneCall = true;
            SystemProperties.set("sys.nonsystem.callstate", "1");
            McuService.getMcuService().onLinkCall();
        } else if (state.equals("CONNECTED") && phoneMode != null && (phoneMode.equals("android_mirror_wired") || phoneMode.equals("android_mirror_wireless") || phoneMode.equals("airplay_wired"))) {
            String ver = SystemProperties.get("zj.zlink.mirror.ver", "0");
            if ("1".equals(ver) && this.mZlinkVersion >= 600000) {
                useAmv = true;
            }
            SystemProperties.set("rw.zlink.supprot.amv", String.valueOf(useAmv));
            String byWifi = SystemProperties.get("rw.zlink.supprot.amv", "false");
            if (!"1".equals(ver) || byWifi.equals("false") || "airplay_wired".equals(phoneMode)) {
                this.mIsZlinkMirrorLink = true;
                Intent intent3 = new Intent("net.easyconn.a2dp.acquire");
                this.mContext.sendBroadcastAsUser(intent3, UserHandle.CURRENT, null);
            }
        } else if (state.equals("MAIN_PAGE_SHOW") && this.mIsZlinkMirrorLink) {
            String ver2 = SystemProperties.get("zj.zlink.mirror.ver", "0");
            if ("1".equals(ver2) && this.mZlinkVersion >= 600000) {
                useAmv = true;
            }
            SystemProperties.set("rw.zlink.supprot.amv", String.valueOf(useAmv));
            String byWifi2 = SystemProperties.get("rw.zlink.supprot.amv", "false");
            if (!"1".equals(ver2) || byWifi2.equals("false") || "airplay_wired".equals(phoneMode)) {
                this.mIsZlinkMirrorLink = true;
                Intent intent4 = new Intent("net.easyconn.a2dp.acquire");
                this.mContext.sendBroadcastAsUser(intent4, UserHandle.CURRENT, null);
            }
        } else if (state.equals("SIRI_ON")) {
            Intent intent5 = new Intent(HConstant.CARPLAY_PHONE_CALL_ON);
            this.mContext.sendBroadcastAsUser(intent5, UserHandle.CURRENT, null);
        } else if (state.equals("SIRI_OFF")) {
            Intent intent6 = new Intent(HConstant.CARPLAY_PHONE_CALL_OFF);
            this.mContext.sendBroadcastAsUser(intent6, UserHandle.CURRENT, null);
        } else if (state.equals("DISCONNECT")) {
            this.mIsZlinkMirrorLink = false;
            this.mZlinkInfoAndroidAutoCall = false;
        } else if (state.equals("PHONE_CALL_ON") && phoneMode != null && phoneMode.contains(DATA_PAGE_AUTO)) {
            this.mZlinkInfoAndroidAutoCall = true;
        } else if (state.equals("PHONE_CALL_OFF") && phoneMode != null && phoneMode.contains(DATA_PAGE_AUTO)) {
            this.mZlinkInfoAndroidAutoCall = false;
        } else if (state.equals("BT_REQ_AUDIO_FOCUS")) {
            this.mIsZlinkMirrorLink = true;
            Intent intent7 = new Intent("net.easyconn.a2dp.acquire");
            this.mContext.sendBroadcastAsUser(intent7, UserHandle.CURRENT, null);
        } else {
            state.equals("BT_ABANDON_AUDIO_FOCUS");
        }
        Log.i(TAG, "onBroadZlink mIsCarPlayPhoneCall:" + this.mIsCarPlayPhoneCall + " mIsZlinkMirrorLink:" + this.mIsZlinkMirrorLink);
    }

    @Override // android.link.LinkManager.LinkInterface
    public void sendReverseState(int reverse) {
        Intent intent2;
        Log.i(TAG, "sendReverseState reverse：" + reverse);
        if (reverse > 0) {
            intent2 = new Intent("com.zjinnova.zlink.action.BACKCAR_START");
        } else {
            intent2 = new Intent("com.zjinnova.zlink.action.BACKCAR_STOP");
        }
        intent2.setPackage(SourceService.ACTIVE_ZJ_CARPLAY);
        ContentResolver cr = this.mContext.getContentResolver();
        if (Settings.System.getInt(cr, HConfig.audio_rev_volume, 0) > 0) {
            intent2.putExtra("muteMedia", false);
        } else {
            intent2.putExtra("muteMedia", true);
        }
        this.mContext.sendBroadcastAsUser(intent2, UserHandle.CURRENT, null);
    }

    @Override // android.link.LinkManager.LinkInterface
    public void sendAccState(int acc) {
        Log.i(TAG, "sendAccState acc=" + acc + " mLastAcc:" + this.mLastAcc);
        if (acc == 0) {
            SystemProperties.set("rw.zlink.hu.acc", "1");
            Intent intentcarplay = new Intent("com.zjinnova.zlink.action.POWER_OFF");
            intentcarplay.setPackage(SourceService.ACTIVE_ZJ_CARPLAY);
            this.mContext.sendBroadcastAsUser(intentcarplay, UserHandle.CURRENT, null);
            SystemProperties.set("service.zlink.enable", "0");
            Util.forceStopPackage(SourceService.ACTIVE_ZJ_CARPLAY);
            this.myHandler.removeMessages(0);
        } else if (this.mLastAcc == 0) {
            try {
                String usbmode = FileUtils.readTextFile(new File(this.mUsbPath), 10, null).trim();
                Log.i(TAG, "sendAccState usbmode=" + usbmode);
                if ("device".equals(usbmode)) {
                    FileUtils.stringToFile(this.mUsbPath, "host");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            SystemProperties.set("rw.zlink.hu.acc", "0");
            SystemProperties.set("service.zlink.enable", "1");
            Intent intentcarplay2 = new Intent("com.zjinnova.zlink.action.POWER_ON");
            intentcarplay2.setPackage(SourceService.ACTIVE_ZJ_CARPLAY);
            this.mContext.sendBroadcastAsUser(intentcarplay2, UserHandle.CURRENT, null);
            this.myHandler.removeMessages(0);
            this.myHandler.sendEmptyMessageDelayed(0, 2000L);
        }
        this.mLastAcc = acc;
    }

    @Override // android.link.LinkManager.LinkInterface
    public void startLinkService() {
    }

    @Override // android.link.LinkManager.LinkInterface
    public boolean sendKeyButton(int key, int action) {
        if (this.mZlinkInfoAndroidAutoCall) {
            return false;
        }
        if (key == 231) {
            if (action == 1) {
                Intent i = new Intent(SourceService.ACTIVE_ZJ_CARPLAY);
                i.setPackage(SourceService.ACTIVE_ZJ_CARPLAY);
                i.putExtra("command", "REQ_SPEC_FUNC_CMD");
                i.putExtra("specFuncCode", 1500);
                this.mContext.sendBroadcastAsUser(i, UserHandle.CURRENT, null);
            }
            return true;
        }
        if (key != 302) {
            if (key != 307) {
                return false;
            }
            if (action == 1) {
                Log.i(TAG, "sendKeyButton key:" + key);
                Intent intentkey = new Intent(SourceService.ACTIVE_ZJ_CARPLAY);
                intentkey.setPackage(SourceService.ACTIVE_ZJ_CARPLAY);
                intentkey.putExtra("command", "REQ_SPEC_FUNC_CMD");
                intentkey.putExtra("specFuncCode", 6);
                this.mContext.sendBroadcastAsUser(intentkey, UserHandle.CURRENT, null);
            }
            return true;
        }
        if (action == 1) {
            int callstate = SystemProperties.getInt("sys.nonsystem.callstate", 0);
            Log.i(TAG, "sendKeyButton key:" + key + " callstate:" + callstate);
            if (callstate == 1) {
                Intent intentkey2 = new Intent(SourceService.ACTIVE_ZJ_CARPLAY);
                intentkey2.setPackage(SourceService.ACTIVE_ZJ_CARPLAY);
                intentkey2.putExtra("command", "REQ_SPEC_FUNC_CMD");
                intentkey2.putExtra("specFuncCode", 5);
                this.mContext.sendBroadcastAsUser(intentkey2, UserHandle.CURRENT, null);
            }
        }
        return true;
    }

    @Override // android.link.LinkManager.LinkInterface
    public boolean sendMediaButton(int keycode, int action) {
        String curSource = SystemProperties.get("sys.sourcing.package");
        if (SourceService.ACTIVE_ZJ_CARPLAY.equals(curSource)) {
            if (keycode == 85 || keycode == 126 || keycode == 87 || keycode == 88 || keycode == 127) {
                if (!isCarPlayCall() && (action == 1 || action == 1)) {
                    Intent intent = new Intent();
                    intent.setAction(SourceService.ACTIVE_ZJ_CARPLAY);
                    intent.setPackage(SourceService.ACTIVE_ZJ_CARPLAY);
                    intent.putExtra("command", "REQ_SPEC_FUNC_CMD");
                    intent.putExtra("specFuncCode", keycode);
                    this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT, null);
                }
                Log.i(TAG, "sendkeyTozlink keycode:" + keycode + " action:" + action);
                return true;
            }
            return false;
        }
        return false;
    }

    private void sendDisConnectBroadcast(Context content) {
        boolean needSend = true;
        SystemProperties.set("sys.nonsystem.callstate", "0");
        String currType = SystemProperties.get("zj.phonelink.type", "");
        Log.i(TAG, "sendDisConnectBroadcast currType:" + currType);
        Intent intentDisconnect = new Intent("com.hcn.link");
        intentDisconnect.putExtra("status", "DISCONNECTED");
        if (currType.contains(DATA_PAGE_CARPLAY)) {
            intentDisconnect.putExtra("type", DATA_PAGE_CARPLAY);
            intentDisconnect.putExtra("bluetooth", "openBt");
            if (this.mZlinkInfoConnect) {
                try {
                    String usbmode = FileUtils.readTextFile(new File(this.mUsbPath), 10, null).trim();
                    Log.i(TAG, "sendDisConnectBroadcast usbmode=" + usbmode);
                    if ("device".equals(usbmode)) {
                        FileUtils.stringToFile(this.mUsbPath, "host");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.i(TAG, "sendDisConnectBroadcast CARPLAY disconnect");
        } else if (currType.contains(DATA_PAGE_HICAR)) {
            intentDisconnect.putExtra("type", DATA_PAGE_HICAR);
            if (this.mZlinkHicarCloseBt) {
                intentDisconnect.putExtra("bluetooth", "openBt");
            } else {
                intentDisconnect.putExtra("bluetooth", "resumeA2dp");
            }
        } else if (currType != null && currType.contains("airplay")) {
            intentDisconnect.putExtra("type", "airplay");
            intentDisconnect.putExtra("bluetooth", "NULL");
        } else if (currType != null && currType.contains(DATA_PAGE_CARLIFE)) {
            intentDisconnect.putExtra("type", DATA_PAGE_CARLIFE);
            intentDisconnect.putExtra("bluetooth", "NULL");
        } else if (currType != null && currType.contains(DATA_PAGE_AUTO)) {
            intentDisconnect.putExtra("type", "androidauto");
            intentDisconnect.putExtra("bluetooth", "stopA2dp");
        } else if (currType != null && currType.contains(DATA_PAGE_MIRROR)) {
            intentDisconnect.putExtra("type", "androidMirror");
            intentDisconnect.putExtra("bluetooth", "NULL");
        } else {
            needSend = false;
        }
        if (needSend) {
            this.mContext.sendBroadcastAsUser(intentDisconnect, UserHandle.CURRENT, null);
        }
        this.mZlinkInfoConnect = false;
    }

    @Override // android.link.LinkManager.LinkInterface
    public boolean activityDied(String pkg) {
        if (SourceService.ACTIVE_ZJ_CARPLAY.equals(pkg)) {
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
    public void sendDayNightMode(boolean isDay) {
        Log.i(TAG, "sendDayNightMode " + isDay);
        if (isDay) {
            Intent intent = new Intent("com.zjinnova.zlink.action.OUT_DARK_STOP");
            intent.setPackage(SourceService.ACTIVE_ZJ_CARPLAY);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT, null);
            SystemProperties.set("rw.out.dark", "0");
            return;
        }
        if (!isDay) {
            Intent intent2 = new Intent("com.zjinnova.zlink.action.OUT_DARK_START");
            intent2.setPackage(SourceService.ACTIVE_ZJ_CARPLAY);
            this.mContext.sendBroadcastAsUser(intent2, UserHandle.CURRENT, null);
            SystemProperties.set("rw.out.dark", "1");
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
        if (SourceService.ACTIVE_ZJ_CARPLAY.equals(preApp) && !SourceService.ACTIVE_ZJ_CARPLAY.equals(curApp)) {
            sendMediaButton(McuConstant.K_PAUSE, 1);
            return false;
        }
        return false;
    }

    @Override // android.link.LinkManager.LinkInterface
    public boolean isHicarConnect() {
        String currType = SystemProperties.get("zj.phonelink.type", "");
        if (this.mZlinkInfoConnect && currType.contains(DATA_PAGE_HICAR)) {
            return true;
        }
        return false;
    }

    @Override // android.link.LinkManager.LinkInterface
    public boolean isNeedPowerOnPlayMusic() {
        SystemProperties.get("sys.sourcing.package");
        return SystemProperties.getInt("sys.nonsystem.callstate", 0) != 1;
    }

    @Override // android.link.LinkManager.LinkInterface
    public void bluetoothCallOnChange(boolean calling) {
        String currType = SystemProperties.get("zj.phonelink.type", "");
        if (this.mZlinkInfoConnect && currType.contains(DATA_PAGE_CARLIFE)) {
            if (calling) {
                Intent intent = new Intent("com.zjinnova.zlink.action.bluetooth.PHONE_STATUS");
                intent.setPackage(SourceService.ACTIVE_ZJ_CARPLAY);
                intent.putExtra("phoneStatus", true);
                this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT, null);
                return;
            }
            Intent intent2 = new Intent("com.zjinnova.zlink.action.bluetooth.PHONE_STATUS");
            intent2.setPackage(SourceService.ACTIVE_ZJ_CARPLAY);
            intent2.putExtra("phoneStatus", false);
            this.mContext.sendBroadcastAsUser(intent2, UserHandle.CURRENT, null);
        }
    }

    @Override // android.link.LinkManager.LinkInterface
    public boolean isCarPlayCall() {
        int callstate = SystemProperties.getInt("sys.nonsystem.callstate", 0);
        return callstate > 0;
    }

    @Override // android.link.LinkManager.LinkInterface
    public void bluetoothNameChange(String name) {
        try {
            SystemProperties.set("rw.zlink.bt.name", name);
            Log.i(TAG, "bluetoothNameChange:" + name);
        } catch (Exception e) {
        }
    }

    @Override // android.link.LinkManager.LinkInterface
    public void localeChange(boolean restart) {
        Resources resources = this.mContext.getResources();
        Configuration configuration = resources.getConfiguration();
        String cuntry = configuration.locale.getCountry();
        String language = configuration.locale.getLanguage();
        Log.i(TAG, "localeChange cuntry: " + cuntry + " language: " + language);
        if ("CN".equals(cuntry) && "zh".equals(language)) {
            SystemProperties.set("rw.zlink.disable.features", "ab");
        } else if ("zh".equals(language)) {
            SystemProperties.set("rw.zlink.disable.features", "ce");
        } else {
            SystemProperties.set("rw.zlink.disable.features", "hice");
        }
        if (restart) {
            Util.forceStopPackage(SourceService.ACTIVE_ZJ_CARPLAY);
        }
    }
}
