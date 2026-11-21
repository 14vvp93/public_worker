package android.link;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadsetClientCall;
import android.carstatus.CarStatus;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.sourceservice.SourceService;
import android.util.Log;

/* JADX INFO: loaded from: /home/runner/work/public_worker/public_worker/artifacts/CarServises/converted/boot-carservices_classes.dex */
public class LinkManager {
    private static final int MSG_DAY_NIGHT_CHANG = 1;
    private static final String TAG = "LinkManager";
    private Context mContext;
    private Handler mSourceHandler;
    public static LinkManager mInstance = null;
    public LinkInterface mLinkInterface = null;
    private int bluetooth_call_status = 7;
    private Handler mHandler = new Handler() { // from class: android.link.LinkManager.2
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                int mode = msg.arg1;
                if (LinkManager.this.mLinkInterface != null) {
                    LinkManager.this.mLinkInterface.sendDayNightMode(mode > 0);
                }
            }
            super.handleMessage(msg);
        }
    };

    interface LinkInterface {
        public static final String DATA_BLUETOOTH_CLOSE_BT = "closeBt";
        public static final String DATA_BLUETOOTH_NULL = "NULL";
        public static final String DATA_BLUETOOTH_OPEN_BT = "openBt";
        public static final String DATA_BLUETOOTH_RESUME_A2DP = "resumeA2dp";
        public static final String DATA_BLUETOOTH_STOP_A2DP = "stopA2dp";
        public static final String DATA_STATUS_CONNECTED = "CONNECTED";
        public static final String DATA_STATUS_DISCONNECTED = "DISCONNECTED";
        public static final String DATA_TYPE_AIRPLAY = "airplay";
        public static final String DATA_TYPE_AUTO = "androidauto";
        public static final String DATA_TYPE_CARLIFE = "carlife";
        public static final String DATA_TYPE_CARPLAY = "carplay";
        public static final String DATA_TYPE_DLNA = "dlna";
        public static final String DATA_TYPE_HICAR = "hicar";
        public static final String DATA_TYPE_MIRROR = "androidMirror";
        public static final String EXTRA_BLUETOOTH = "bluetooth";
        public static final String EXTRA_CONNECT_STATE = "status";
        public static final String EXTRA_TYPE = "type";
        public static final String LINK_BROAD_NAME = "com.hcn.link";

        boolean activityDied(String str);

        void bluetoothCallOnChange(boolean z);

        void bluetoothNameChange(String str);

        boolean changeSource(String str, String str2);

        boolean isCarPlayCall();

        boolean isHicarConnect();

        boolean isNeedPowerOnPlayMusic();

        boolean isNeedSendKeyToLink();

        void localeChange(boolean z);

        void sendAccState(int i);

        void sendDayNightMode(boolean z);

        void sendDisconnect();

        boolean sendKeyButton(int i, int i2);

        boolean sendMediaButton(int i, int i2);

        void sendReverseState(int i);

        void startLinkService();
    }

    public static LinkManager getInstance() {
        if (mInstance == null) {
            mInstance = new LinkManager();
        }
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context;
        IntentFilter filter = new IntentFilter();
        filter.addAction(CarStatus.ACTION_ACC);
        filter.addAction(CarStatus.ACTION_REVSTATUS);
        filter.addAction("android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED");
        filter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        filter.addAction("android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED");
        filter.addAction("android.bluetooth.adapter.action.LOCAL_NAME_CHANGED");
        filter.addAction("android.intent.action.LOCALE_CHANGED");
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: android.link.LinkManager.1
            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action.equals(CarStatus.ACTION_ACC)) {
                    if (intent.getBooleanExtra(CarStatus.EXTRA_ACC, true)) {
                        LinkManager.this.sendAccState(1);
                        return;
                    }
                    return;
                }
                if (action.equals(CarStatus.ACTION_REVSTATUS)) {
                    LinkManager.this.sendReverseState(intent.getBooleanExtra(CarStatus.EXTRA_REVSTATUS, true) ? 1 : 0);
                    return;
                }
                if (action.equals("android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED")) {
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                    Log.i(LinkManager.TAG, "headset state changed. device : " + bluetoothDevice + ", state : " + intExtra);
                    if (intExtra == 0) {
                        LinkManager.this.bluetooth_call_status = 7;
                        LinkManager.this.bluetoothCallOnChange(false);
                        return;
                    }
                    BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                    Log.i(LinkManager.TAG, "bluetoothAdapter:" + defaultAdapter);
                    if (defaultAdapter != null && defaultAdapter.isEnabled()) {
                        LinkManager.this.bluetoothNameChange(defaultAdapter.getName());
                        return;
                    }
                    return;
                }
                if (action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
                    LinkManager.this.bluetooth_call_status = 7;
                    LinkManager.this.bluetoothCallOnChange(false);
                    return;
                }
                if (action.equals("android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED")) {
                    BluetoothHeadsetClientCall parcelableExtra = intent.getParcelableExtra("android.bluetooth.headsetclient.extra.CALL");
                    if (parcelableExtra != null) {
                        LinkManager.this.bluetooth_call_status = parcelableExtra.getState();
                        LinkManager.this.bluetoothCallOnChange(LinkManager.this.bluetooth_call_status != 7);
                        return;
                    }
                    return;
                }
                if (!action.equals("android.bluetooth.adapter.action.LOCAL_NAME_CHANGED")) {
                    if (action.equals("android.intent.action.LOCALE_CHANGED")) {
                        LinkManager.this.localeChange(true);
                    }
                } else {
                    String stringExtra = intent.getStringExtra("android.bluetooth.adapter.extra.LOCAL_NAME");
                    Log.i(LinkManager.TAG, "ACTION_LOCAL_NAME_CHANGED name:" + stringExtra);
                    LinkManager.this.bluetoothNameChange(stringExtra);
                }
            }
        }, filter);
    }

    public boolean isNeedSendKeyToLink() {
        if (this.mLinkInterface != null) {
            return this.mLinkInterface.isNeedSendKeyToLink();
        }
        return false;
    }

    public boolean isHicarConnect() {
        if (this.mLinkInterface != null) {
            return this.mLinkInterface.isHicarConnect();
        }
        return false;
    }

    public boolean isNeedPowerOnPlayMusic() {
        if (this.mLinkInterface != null) {
            return this.mLinkInterface.isNeedPowerOnPlayMusic();
        }
        return true;
    }

    public void initLinkByPackageManager(String pkg) {
        if (SourceService.ACTIVE_ZJ_CARPLAY.equals(pkg) && this.mLinkInterface == null) {
            this.mLinkInterface = new CarZlinkInterface(this.mContext);
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
                bluetoothNameChange(bluetoothAdapter.getName());
            }
        }
        if (SourceService.ACTIVE_LT_CARPLAY.equals(pkg) && this.mLinkInterface == null) {
            this.mLinkInterface = new CarletterInterface(this.mContext);
        }
    }

    public void removeLinkByPackageManager(String pkg) {
        if (SourceService.ACTIVE_LT_CARPLAY.equals(pkg) && this.mLinkInterface != null && (this.mLinkInterface instanceof CarletterInterface)) {
            this.mLinkInterface.sendDisconnect();
            this.mLinkInterface = null;
            SystemProperties.set("service.ltlink.enable", "0");
        }
        if (SourceService.ACTIVE_ZJ_CARPLAY.equals(pkg) && this.mLinkInterface != null && (this.mLinkInterface instanceof CarZlinkInterface)) {
            this.mLinkInterface.sendDisconnect();
            this.mLinkInterface = null;
            SystemProperties.set("service.zlink.enable", "0");
        }
    }

    public static int getVersionCode(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            return -1;
        }
    }

    public void sendReverseState(int reverse) {
        if (this.mLinkInterface != null) {
            this.mLinkInterface.sendReverseState(reverse);
        }
    }

    public void sendAccState(int acc) {
        if (this.mLinkInterface != null) {
            this.mLinkInterface.sendAccState(acc);
        }
    }

    public void startLinkService() {
        if (this.mLinkInterface != null) {
            this.mLinkInterface.startLinkService();
        }
    }

    public boolean sendKeyButton(int key, int action) {
        if (this.mLinkInterface != null) {
            if (isNeedSendKeyToLink() || key == 231) {
                return this.mLinkInterface.sendKeyButton(key, action);
            }
            return false;
        }
        return false;
    }

    public boolean sendMediaButton(int key, int action) {
        if (this.mLinkInterface != null) {
            return this.mLinkInterface.sendMediaButton(key, action);
        }
        return false;
    }

    public boolean activityDied(String pkg) {
        if (this.mLinkInterface != null) {
            return this.mLinkInterface.activityDied(pkg);
        }
        return false;
    }

    public boolean changeSource(String preApp, String curApp) {
        if (this.mLinkInterface != null) {
            return this.mLinkInterface.changeSource(preApp, curApp);
        }
        return false;
    }

    void bluetoothCallOnChange(boolean calling) {
        if (this.mLinkInterface != null) {
            this.mLinkInterface.bluetoothCallOnChange(calling);
        }
    }

    public boolean isCarPlayCall() {
        if (this.mLinkInterface != null) {
            return this.mLinkInterface.isCarPlayCall();
        }
        return false;
    }

    public void bluetoothNameChange(String name) {
        if (this.mLinkInterface != null) {
            this.mLinkInterface.bluetoothNameChange(name);
        }
    }

    public void localeChange(boolean restart) {
        if (this.mLinkInterface != null) {
            this.mLinkInterface.localeChange(restart);
        }
    }

    public void sendDayNightMode(boolean z) {
        if (this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
        }
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, z ? 1 : 0, 0), 1000L);
    }
}
