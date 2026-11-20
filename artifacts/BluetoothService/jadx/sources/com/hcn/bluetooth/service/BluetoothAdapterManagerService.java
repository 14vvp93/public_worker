package com.hcn.bluetooth.service;

import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadsetClientCall;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.KeyEvent;
import com.hcn.bluetooth.api.BluetoothDeviceInfo;
import com.hcn.bluetooth.api.LocalBTMusicManager;
import com.hcn.bluetooth.api.LocalBluetoothAdapterManager;
import com.hcn.bluetooth.api.Utils;
import com.hcn.bluetooth.le.BleDeviceHelper;
import com.hcn.bluetooth.protocol.A2dpSinkProfile;
import com.hcn.bluetooth.protocol.CachedBluetoothDevice;
import com.hcn.bluetooth.protocol.CachedBluetoothDeviceManager;
import com.hcn.bluetooth.protocol.HeadsetClientProfile;
import com.hcn.bluetooth.protocol.LocalBluetoothAdapter;
import com.hcn.bluetooth.protocol.LocalBluetoothManager;
import com.hcn.bluetooth.protocol.LocalBluetoothProfileManager;
import com.hcn.bluetooth.protocol.PanProfile;
import com.hcn.bluetooth.utils.MacAddressUtil;
import com.hcn.bluetoothservice.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class BluetoothAdapterManagerService extends Service {
    public static final int ACC_OFF_DELAY_CLOSE_BT = 2000;
    public static final String ACTION_ACC = "android.mcu.device.action.ACC";
    public static String ACTION_EASYCONN_BT_SETTING = "net.easyconn.bt.setting";
    public static final String ACTION_HCN_LINK = "com.hcn.link";
    public static String ACTION_HICAR_LINK = "com.hicar.connect.status";
    private static final String ACTION_LETTER_CAR_LIFE_LINK = "com.hcn.link";
    public static final String ACTION_SILENCE_BUTTON = "android.mcu.device.action.SILENCE_BUTTON";
    public static final int AUTO_CONNECT_DELAY = 5000;
    public static final int AUTO_NETWORK_CONNECT_DELAY = 10000;
    public static final int CALL_BLUETOOTH_STATE_CHANGED = 1;
    public static final int CALL_BOND_STATE_CHANGED = 5;
    public static final int CALL_CONNECTION_STATE_CHANGED = 6;
    public static final int CALL_DISCOVERY_DEVICE_FOUND = 3;
    public static final int CALL_DISCOVERY_NAME_CHANGED = 4;
    public static final int CALL_DISCOVERY_STATE_CHANGED = 2;
    private static final String CAR_LIFE_MODE = "carlife";
    public static final String DATA_BLUETOOTH_CLOSE_BT = "closeBt";
    public static final String DATA_BLUETOOTH_NULL = "NULL";
    public static final String DATA_BLUETOOTH_OPEN_BT = "openBt";
    public static final String DATA_BLUETOOTH_RESUME_A2DP = "resumeA2dp";
    public static final String DATA_BLUETOOTH_STOP_A2DP = "stopA2dp";
    public static final String DATA_STATUS_CONNECTED = "CONNECTED";
    public static final String DATA_STATUS_DISCONNECTED = "DISCONNECTED";
    public static final String DATA_TYPE_AUTO = "androidauto";
    public static final String DATA_TYPE_CARPLAY = "carplay";
    public static final String DATA_TYPE_HICAR = "hicar";
    public static final int DEFAULT_BT_RESET_TIME = 1000;
    public static final String DEFAULT_NAME = "Car BT";
    public static final String DEFAULT_PINCODE = "0000";
    public static final String EXTRA_BLUETOOTH = "bluetooth";
    public static String EXTRA_BT_ADDRESS = "btAddress";
    public static final String EXTRA_CONNECT_STATE = "status";
    public static final String EXTRA_TYPE = "type";
    public static int KEYEVENT_POWER_OFF = 32;
    public static int KEYEVENT_POWER_ON = 48;
    private static final String LETTER_ANDROID_AUTO_WIRED = "aauto_wired";
    private static final String LETTER_ANDROID_AUTO_WIRELESS = "aauto_wireless";
    private static final int MSG_AUTO_CONNECT = 5;
    private static final int MSG_AUTO_CONNECT_NETWORK = 6;
    private static final int MSG_BT_INIT = 0;
    private static final int MSG_BT_OFF = 2;
    private static final int MSG_BT_ON = 1;
    private static final int MSG_CHECK_BT_IS_OPEN_STATE = 8;
    private static final int MSG_CLOSE_BT = 4;
    private static final int MSG_DISCONNECT_A2DP = 7;
    private static final int MSG_OPEN_BT = 3;
    public static final int POWER_OFF_DELAY_CLOSE_BT = 500;
    public static String STATUS_CONNECT = "CONNECTED";
    public static String STATUS_DISCONNECT = "DISCONNECT";
    public static final String TAG = "BTAdapterService";
    private static final String ZJ_ANDROID_AUTO_WIRED = "auto_wired";
    private static final String ZJ_ANDROID_AUTO_WIRELESS = "auto_wireless";
    private static final String ZJ_CARPLAY_WIRED = "carplay_wired";
    private static final String ZJ_CARPLAY_WIRELESS = "carplay_wireless";
    public static final String ZJ_MIRROR_CARPLAY_STATUS = "com.zjinnova.zlink";
    private static final String ZJ_MIRROR_WIRED = "android_mirror_wired";
    private static final String ZJ_MIRROR_WIRELESS = "android_mirror_wireless";
    private static String mLastConnectBtAddress;
    private static BluetoothAdapterManagerService sInstance;
    private BleDeviceHelper mBleDeviceHelp;
    BluetoothPbapClientHelper mBluetoothPbapClientHelper;
    private RemoteCallbackList<IAdapterCallback> mCallBackList;
    private Context mContext;
    private String mDelayConnectAddress;
    private boolean mAccStatus = true;
    private boolean isInLatterHiCarMode = false;
    private boolean isInZjHiCarMode = false;
    private int mThirdPartMode = 0;
    private LocalBluetoothManager mLocalManager = null;
    private LocalBluetoothAdapter mLocalAdapter = null;
    private LocalBluetoothProfileManager mProfileManager = null;
    private CachedBluetoothDeviceManager mCachedDeviceManager = null;
    private boolean mIsDisconnectByUser = false;
    private List<BluetoothDeviceInfo> mBondedDevices = null;
    private List<BluetoothDeviceInfo> mDiscoveryDevices = null;
    private IBluetoothAdapterManagerService.Stub mBinder = null;
    private int mScanMode = 23;
    private String mBTName = null;
    private String mBTPincode = null;
    private int mNetworkErrorTimes = 0;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.hcn.bluetooth.service.BluetoothAdapterManagerService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                BluetoothAdapterManagerService.this.handleStateChanged(intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10));
                return;
            }
            if (action.equals("android.bluetooth.adapter.action.DISCOVERY_STARTED")) {
                Log.i(BluetoothAdapterManagerService.TAG, "ACTION_DISCOVERY_STARTED");
                BluetoothAdapterManagerService.this.callListener(2, null, 1);
                return;
            }
            if (action.equals("android.bluetooth.device.action.FOUND")) {
                BluetoothAdapterManagerService.this.handleDeviceFound((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"));
                return;
            }
            if (action.equals("android.bluetooth.adapter.action.DISCOVERY_FINISHED")) {
                Log.i(BluetoothAdapterManagerService.TAG, "ACTION_DISCOVERY_FINISHED");
                BluetoothAdapterManagerService.this.callListener(2, null, 0);
                return;
            }
            if (action.equals("android.bluetooth.adapter.action.SCAN_MODE_CHANGED")) {
                BluetoothAdapterManagerService.this.handleScanModeChanged(intent.getIntExtra("android.bluetooth.adapter.extra.SCAN_MODE", Integer.MIN_VALUE));
                return;
            }
            if (action.equals("android.bluetooth.device.action.BOND_STATE_CHANGED")) {
                BluetoothAdapterManagerService.this.handleBondStateChange((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"), intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", 10), intent.getIntExtra("android.bluetooth.device.extra.REASON", Integer.MIN_VALUE));
                return;
            }
            if (action.equals("android.bluetooth.adapter.action.LOCAL_NAME_CHANGED")) {
                String stringExtra = intent.getStringExtra("android.bluetooth.adapter.extra.LOCAL_NAME");
                Log.e(BluetoothAdapterManagerService.TAG, "ACTION_LOCAL_NAME_CHANGED:" + stringExtra);
                if (stringExtra.equals(BluetoothAdapterManagerService.this.getBTName())) {
                    return;
                }
                Log.e(BluetoothAdapterManagerService.TAG, "name error,need set again!");
                BluetoothAdapterManagerService bluetoothAdapterManagerService = BluetoothAdapterManagerService.this;
                bluetoothAdapterManagerService.setBTName(bluetoothAdapterManagerService.getBTName(), false);
                return;
            }
            if (action.equals("android.bluetooth.device.action.ALIAS_CHANGED") || action.equals("android.bluetooth.device.action.NAME_CHANGED")) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (bluetoothDevice != null) {
                    for (BluetoothDeviceInfo bluetoothDeviceInfo : BluetoothAdapterManagerService.this.mDiscoveryDevices) {
                        if (bluetoothDeviceInfo.getDeviceAddr().equals(bluetoothDevice.getAddress())) {
                            Log.i(BluetoothAdapterManagerService.TAG, "update device name");
                            String aliasName = bluetoothDevice.getAliasName();
                            String name = bluetoothDevice.getName();
                            if (!TextUtils.isEmpty(aliasName)) {
                                bluetoothDeviceInfo.mDeviceName = aliasName;
                            } else if (!TextUtils.isEmpty(name)) {
                                bluetoothDeviceInfo.mDeviceName = name;
                            } else {
                                bluetoothDeviceInfo.mDeviceName = bluetoothDevice.getAddress();
                            }
                            BluetoothAdapterManagerService.this.callListener(4, bluetoothDeviceInfo, 0);
                        }
                    }
                    return;
                }
                return;
            }
            if (action.equals(LocalBluetoothAdapterManager.ACTION_CONNECTION_STATE_CHANGED)) {
                BluetoothAdapterManagerService.this.handleConnectStateChanged((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"), intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0));
                return;
            }
            if (action.equals(LocalBTMusicManager.ACTION_CONNECTION_STATE_CHANGED)) {
                BluetoothAdapterManagerService.this.handleA2dpsinkConnectStatus((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"), intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0));
                return;
            }
            if (action.equals("android.bluetooth.device.action.PAIRING_REQUEST")) {
                BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                int intExtra = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_VARIANT", -1);
                int intExtra2 = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", -1);
                if (bluetoothDevice2 == null) {
                    return;
                }
                BluetoothClass bluetoothClass = bluetoothDevice2.getBluetoothClass();
                Log.d(BluetoothAdapterManagerService.TAG, "Pairing type=" + BluetoothAdapterManagerService.this.deviceTypeToString(bluetoothClass != null ? bluetoothClass.getMajorDeviceClass() : 512) + " PAIR_VARIANT:" + intExtra + " passkey:" + intExtra2);
                switch (intExtra) {
                    case 0:
                    case 7:
                        byte[] bArrConvertPinToBytes = BluetoothDevice.convertPinToBytes(BluetoothAdapterManagerService.this.getBTPincode());
                        if (bArrConvertPinToBytes != null) {
                            bluetoothDevice2.setPin(bArrConvertPinToBytes);
                        }
                        abortBroadcast();
                        break;
                    case 1:
                        bluetoothDevice2.setPasskey(intExtra2);
                        break;
                    case 2:
                    case 3:
                        bluetoothDevice2.setPairingConfirmation(true);
                        abortBroadcast();
                        break;
                    case 4:
                    case 5:
                        break;
                    case 6:
                        bluetoothDevice2.setRemoteOutOfBandData();
                        break;
                    default:
                        Log.e(BluetoothAdapterManagerService.TAG, "Incorrect pairing type received");
                        break;
                }
                return;
            }
            if (action.equals("android.bluetooth.device.action.PAIRING_CANCEL")) {
                Log.e(BluetoothAdapterManagerService.TAG, "ACTION_PAIRING_CANCEL");
                return;
            }
            if (action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                Log.e(BluetoothAdapterManagerService.TAG, "ACTION_ACL_CONNECTED");
                BluetoothDevice bluetoothDevice3 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                BluetoothAdapterManagerService.this.handleACLConnected(bluetoothDevice3);
                if (Utils.isPlatform8581() && BluetoothAdapterManagerService.this.mHanlder.hasMessages(5)) {
                    BluetoothAdapterManagerService.this.mHanlder.removeMessages(5);
                    if (bluetoothDevice3.getAddress().equals(BluetoothAdapterManagerService.this.readLastConnectedDevice())) {
                        BluetoothAdapterManagerService.this.mHanlder.sendEmptyMessageDelayed(5, 5000L);
                        return;
                    }
                    return;
                }
                return;
            }
            if (action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
                Log.e(BluetoothAdapterManagerService.TAG, "ACTION_ACL_DISCONNECTED");
                BluetoothAdapterManagerService.this.handleACLDisconnected((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"));
                return;
            }
            if (action.equals("android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED")) {
                Log.e(BluetoothAdapterManagerService.TAG, "ACTION_ACL_DISCONNECT_REQUESTED");
                return;
            }
            if (action.equals(BluetoothAdapterManagerService.ACTION_ACC)) {
                BluetoothAdapterManagerService.this.mAccStatus = intent.getBooleanExtra("AccStatus", true);
                if (BluetoothAdapterManagerService.this.mAccStatus) {
                    BluetoothAdapterManagerService.this.mHanlder.removeMessages(4);
                    BluetoothAdapterManagerService.this.mHanlder.removeMessages(3);
                    if (BluetoothAdapterManagerService.this.getSavedBtStatus()) {
                        BluetoothAdapterManagerService.this.mHanlder.sendEmptyMessage(3);
                        BluetoothAdapterManagerService.this.mHanlder.sendEmptyMessageDelayed(8, 2000L);
                        return;
                    }
                    return;
                }
                BluetoothAdapterManagerService.this.mHanlder.removeMessages(4);
                BluetoothAdapterManagerService.this.mHanlder.removeMessages(3);
                BluetoothAdapterManagerService.this.mHanlder.sendEmptyMessageDelayed(4, 2000L);
                return;
            }
            if (action.equals(BluetoothAdapterManagerService.ACTION_SILENCE_BUTTON)) {
                if (!BluetoothAdapterManagerService.this.mAccStatus) {
                    Log.e(BluetoothAdapterManagerService.TAG, "Acc off ignore Silence Button event!");
                    return;
                }
                KeyEvent keyEvent = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT");
                if (keyEvent.getKeyCode() == 26) {
                    if (keyEvent.getMetaState() == BluetoothAdapterManagerService.KEYEVENT_POWER_OFF) {
                        Log.d(BluetoothAdapterManagerService.TAG, "onSilenceButton power off");
                        BluetoothAdapterManagerService.this.mHanlder.removeMessages(3);
                        BluetoothAdapterManagerService.this.mHanlder.removeMessages(4);
                        BluetoothAdapterManagerService.this.mHanlder.sendEmptyMessageDelayed(4, 500L);
                        return;
                    }
                    if (keyEvent.getMetaState() == BluetoothAdapterManagerService.KEYEVENT_POWER_ON) {
                        Log.d(BluetoothAdapterManagerService.TAG, "onSilenceButton power on");
                        BluetoothAdapterManagerService.this.mHanlder.removeMessages(4);
                        BluetoothAdapterManagerService.this.mHanlder.removeMessages(3);
                        if (BluetoothAdapterManagerService.this.getSavedBtStatus()) {
                            BluetoothAdapterManagerService.this.mHanlder.sendEmptyMessageDelayed(3, 1500L);
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            if (action.equals("android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED")) {
                int intExtra3 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                Log.d(BluetoothAdapterManagerService.TAG, "onReceive: PanProfile state=" + intExtra3);
                if (intExtra3 == 0) {
                    BluetoothAdapterManagerService.this.mNetworkErrorTimes = 0;
                    return;
                }
                return;
            }
            if (action.equals(BluetoothAdapterManagerService.ZJ_MIRROR_CARPLAY_STATUS)) {
                BluetoothAdapterManagerService.this.handleZJStatus(intent);
                return;
            }
            if (action.equals(BluetoothAdapterManagerService.ACTION_HICAR_LINK)) {
                BluetoothAdapterManagerService.this.handleHiCarStatus(intent);
                return;
            }
            if (action.equals("com.hcn.link")) {
                BluetoothAdapterManagerService.this.handleHcnLinkStatus(intent);
            } else if (action.equals(BluetoothAdapterManagerService.ACTION_EASYCONN_BT_SETTING)) {
                Utils.startApp(BluetoothAdapterManagerService.this.mContext, "com.autochips.bluetooth", "com.autochips.bluetooth.MainBluetoothActivity");
            } else if (action.equals("com.hcn.link")) {
                BluetoothAdapterManagerService.this.handleLetterCarLife(intent);
            }
        }
    };
    private int mAutoConnectTimes = 0;
    private int mAutoConnectIndex = 0;
    private final Handler mHanlder = new Handler() { // from class: com.hcn.bluetooth.service.BluetoothAdapterManagerService.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            HeadsetClientProfile headsetClientProfile;
            if (message.what == 0) {
                int state = BluetoothAdapterManagerService.this.mLocalAdapter.getState();
                Log.d(BluetoothAdapterManagerService.TAG, "handleMessage: MSG_BT_INIT state=" + state);
                if (10 == state) {
                    if (BluetoothAdapterManagerService.this.getSavedBtStatus()) {
                        BluetoothAdapterManagerService.this.setBluetoothEnable(true, false);
                    }
                } else if (12 == state) {
                    Intent intent = new Intent("android.bluetooth.adapter.action.STATE_CHANGED");
                    intent.putExtra("android.bluetooth.adapter.extra.STATE", 12);
                    BluetoothAdapterManagerService.this.sendBroadcast(intent);
                }
            } else {
                int i = message.what;
                int i2 = BluetoothAdapterManagerService.AUTO_NETWORK_CONNECT_DELAY;
                if (i == 5) {
                    Log.d(BluetoothAdapterManagerService.TAG, "MSG_AutoConnect");
                    if (BluetoothAdapterManagerService.this.isBluetoothEnable()) {
                        String lastConnectedDevice = BluetoothAdapterManagerService.this.readLastConnectedDevice();
                        Log.d(BluetoothAdapterManagerService.TAG, "handleMessage: ready to connect address=" + lastConnectedDevice);
                        if (!TextUtils.isEmpty(lastConnectedDevice)) {
                            try {
                                BluetoothDevice remoteDevice = BluetoothAdapterManagerService.this.mLocalAdapter.getRemoteDevice(lastConnectedDevice);
                                if (BluetoothAdapterManagerService.this.mProfileManager != null && (headsetClientProfile = BluetoothAdapterManagerService.this.mProfileManager.getHeadsetClientProfile()) != null && headsetClientProfile.getConnectionStatus(remoteDevice) == 2) {
                                    Log.e(BluetoothAdapterManagerService.TAG, "MSG_AUTO_CONNECT Bluetooth is already Connected!!");
                                    BluetoothAdapterManagerService.this.stopAutoConnect();
                                    return;
                                }
                                if (remoteDevice.getBondState() == 12) {
                                    if (BluetoothAdapterManagerService.this.mAutoConnectTimes >= 12) {
                                        i2 = 30000;
                                    } else if (MacAddressUtil.MAX_SIZE > 1) {
                                        i2 = 15000;
                                    }
                                    Log.d(BluetoothAdapterManagerService.TAG, "MSG_AUTO_CONNECT times=" + BluetoothAdapterManagerService.this.mAutoConnectTimes + " delay:" + i2);
                                    BluetoothAdapterManagerService.this.mHanlder.removeMessages(5);
                                    BluetoothAdapterManagerService.this.mHanlder.sendEmptyMessageDelayed(5, i2);
                                    BluetoothAdapterManagerService.access$2408(BluetoothAdapterManagerService.this);
                                    BluetoothAdapterManagerService.this.connectDevice(lastConnectedDevice);
                                } else if (MacAddressUtil.MAX_SIZE <= 1 || MacAddressUtil.getMacAddresses(BluetoothAdapterManagerService.this.mContext).size() <= 1) {
                                    Log.e(BluetoothAdapterManagerService.TAG, "handleMessage AutoConnect device not bonded");
                                    BluetoothAdapterManagerService.this.stopAutoConnect();
                                }
                            } catch (IllegalArgumentException unused) {
                                Log.e(BluetoothAdapterManagerService.TAG, "MSG_AUTO_CONNECT getRemoteDevice failed!!");
                                BluetoothAdapterManagerService.this.stopAutoConnect();
                                return;
                            }
                        } else {
                            Log.e(BluetoothAdapterManagerService.TAG, "MSG_AUTO_CONNECT address is Empty");
                            BluetoothAdapterManagerService.this.stopAutoConnect();
                            return;
                        }
                    } else {
                        Log.e(BluetoothAdapterManagerService.TAG, "MSG_AUTO_CONNECT Bluetooth is not enabled");
                        BluetoothAdapterManagerService.this.stopAutoConnect();
                        return;
                    }
                } else if (message.what == 1) {
                    Log.d(BluetoothAdapterManagerService.TAG, "MSG_BTON");
                    BluetoothAdapterManagerService.this.generalSetting();
                    if (BluetoothAdapterManagerService.this.mDiscoveryDevices != null) {
                        BluetoothAdapterManagerService.this.mDiscoveryDevices.clear();
                    }
                    BluetoothAdapterManagerService.this.startAutoConnect(1000);
                    BluetoothAdapterManagerService bluetoothAdapterManagerService = BluetoothAdapterManagerService.this;
                    bluetoothAdapterManagerService.notifySystemUINetworkState(bluetoothAdapterManagerService.isAutoConnectedNetwork());
                } else if (message.what == 2) {
                    Log.d(BluetoothAdapterManagerService.TAG, "MSG_BTOFF");
                    if (BluetoothAdapterManagerService.this.mDiscoveryDevices != null) {
                        BluetoothAdapterManagerService.this.mDiscoveryDevices.clear();
                    }
                    BluetoothAdapterManagerService.this.stopAutoConnect();
                    BluetoothAdapterManagerService.this.stopAutoConnectedNetwork();
                    BluetoothAdapterManagerService.this.notifySystemUINetworkState(false);
                } else if (message.what == 3) {
                    BluetoothAdapterManagerService.this.setBluetoothEnable(true, false);
                } else if (message.what == 4) {
                    BluetoothDeviceInfo connectDevice = BluetoothAdapterManagerService.this.getConnectDevice();
                    if (connectDevice != null) {
                        BluetoothAdapterManagerService.this.disconnectDevice(connectDevice.getDeviceAddr());
                    }
                    BluetoothAdapterManagerService.this.setBluetoothEnable(false, false);
                } else if (message.what == 6) {
                    HeadsetClientProfile headsetClientProfile2 = BluetoothAdapterManagerService.this.mProfileManager.getHeadsetClientProfile();
                    PanProfile panProfile = BluetoothAdapterManagerService.this.mProfileManager.getPanProfile();
                    if (headsetClientProfile2 == null) {
                        Log.e(BluetoothAdapterManagerService.TAG, "MSG_AUTO_CONNECT_NETWORK headsetClientProfile null");
                    } else if (panProfile != null) {
                        List<BluetoothDevice> connectedDevices = headsetClientProfile2.getConnectedDevices();
                        if (!connectedDevices.isEmpty()) {
                            BluetoothDevice bluetoothDevice = connectedDevices.get(0);
                            int connectionStatus = panProfile.getConnectionStatus(bluetoothDevice);
                            if (connectionStatus == 0) {
                                Log.d(BluetoothAdapterManagerService.TAG, "Pan auto connect " + bluetoothDevice.getAddress());
                                panProfile.connect(bluetoothDevice);
                            } else if (connectionStatus == 2) {
                                if (!panProfile.isBluetoothNetworkError()) {
                                    BluetoothAdapterManagerService.this.mNetworkErrorTimes = 0;
                                } else {
                                    BluetoothAdapterManagerService.access$1508(BluetoothAdapterManagerService.this);
                                    if (BluetoothAdapterManagerService.this.mNetworkErrorTimes >= 2) {
                                        Log.e(BluetoothAdapterManagerService.TAG, "Pan error disconnect!");
                                        panProfile.disconnect(bluetoothDevice);
                                        BluetoothAdapterManagerService.this.mNetworkErrorTimes = 0;
                                    }
                                }
                            }
                        }
                    } else {
                        Log.e(BluetoothAdapterManagerService.TAG, "MSG_AUTO_CONNECT_NETWORK panProfile null");
                    }
                    BluetoothAdapterManagerService.this.startAutoConnectedNetwork(BluetoothAdapterManagerService.AUTO_NETWORK_CONNECT_DELAY);
                } else if (message.what == 7) {
                    BluetoothAdapterManagerService.this.disconnectA2dp();
                } else if (message.what == 8 && !BluetoothAdapterManagerService.this.isBluetoothEnable()) {
                    BluetoothAdapterManagerService.this.setBluetoothEnable(true, false);
                }
            }
            super.handleMessage(message);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public String deviceTypeToString(int i) {
        if (i == 0) {
            return "misc";
        }
        if (i == 256) {
            return "computer";
        }
        if (i == 512) {
            return "phone";
        }
        if (i == 768) {
            return "networking";
        }
        if (i == 1024) {
            return "audio_video";
        }
        if (i == 1280) {
            return "peripheral";
        }
        if (i == 1536) {
            return "imaging";
        }
        if (i == 1792) {
            return "wearable";
        }
        if (i != 2048) {
            return i != 2304 ? "" : "health";
        }
        return "toy";
    }

    static /* synthetic */ int access$1508(BluetoothAdapterManagerService bluetoothAdapterManagerService) {
        int i = bluetoothAdapterManagerService.mNetworkErrorTimes;
        bluetoothAdapterManagerService.mNetworkErrorTimes = i + 1;
        return i;
    }

    static /* synthetic */ int access$2408(BluetoothAdapterManagerService bluetoothAdapterManagerService) {
        int i = bluetoothAdapterManagerService.mAutoConnectTimes;
        bluetoothAdapterManagerService.mAutoConnectTimes = i + 1;
        return i;
    }

    public static synchronized BluetoothAdapterManagerService getInstance() {
        if (sInstance == null) {
            sInstance = new BluetoothAdapterManagerService();
        }
        return sInstance;
    }

    @Override // android.app.Service
    public void onCreate() {
        Log.d(TAG, "onCreate");
        init();
        super.onCreate();
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        deinit();
        BleDeviceHelper bleDeviceHelper = this.mBleDeviceHelp;
        if (bleDeviceHelper != null) {
            bleDeviceHelper.onDestroy();
        }
        if (this.mBinder != null) {
            this.mBinder = null;
        }
        super.onDestroy();
    }

    @Override // android.app.Service
    public void onStart(Intent intent, int i) {
        Log.d(TAG, "onStart");
        super.onStart(intent, i);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        if (this.mBinder == null) {
            this.mBinder = new BluetoothAdapterManagerBinder();
        }
        return this.mBinder;
    }

    public void init() {
        Log.i(TAG, "init");
        sInstance = this;
        this.mContext = getApplicationContext();
        this.mCallBackList = new RemoteCallbackList<>();
        LocalBluetoothManager localBluetoothManager = LocalBluetoothManager.getInstance(this.mContext, null);
        this.mLocalManager = localBluetoothManager;
        this.mProfileManager = localBluetoothManager.getProfileManager();
        this.mLocalAdapter = this.mLocalManager.getBluetoothAdapter();
        this.mCachedDeviceManager = this.mLocalManager.getCachedDeviceManager();
        this.mBluetoothPbapClientHelper = new BluetoothPbapClientHelper(this, this.mProfileManager);
        if (!BleDeviceHelper.supportDevice.isEmpty()) {
            this.mBleDeviceHelp = new BleDeviceHelper(this.mContext);
        }
        this.mBondedDevices = new ArrayList();
        this.mDiscoveryDevices = new ArrayList();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.adapter.action.SCAN_MODE_CHANGED");
        intentFilter.addAction("android.bluetooth.adapter.action.LOCAL_NAME_CHANGED");
        intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
        intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        intentFilter.addAction("android.bluetooth.device.action.FOUND");
        intentFilter.addAction("android.bluetooth.device.action.ALIAS_CHANGED");
        intentFilter.addAction("android.bluetooth.device.action.NAME_CHANGED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED");
        intentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.device.action.PAIRING_REQUEST");
        intentFilter.addAction("android.bluetooth.device.action.PAIRING_CANCEL");
        intentFilter.addAction(LocalBluetoothAdapterManager.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(LocalBTMusicManager.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction("android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction(ACTION_ACC);
        intentFilter.addAction(ACTION_SILENCE_BUTTON);
        intentFilter.addAction(ZJ_MIRROR_CARPLAY_STATUS);
        intentFilter.addAction(ACTION_HICAR_LINK);
        intentFilter.addAction(ACTION_EASYCONN_BT_SETTING);
        intentFilter.addAction("com.hcn.link");
        intentFilter.addAction("com.hcn.link");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        this.mBluetoothPbapClientHelper.registerReceiver();
        this.mHanlder.sendEmptyMessageDelayed(0, 500L);
    }

    public void deinit() {
        this.mContext.unregisterReceiver(this.mReceiver);
        this.mBluetoothPbapClientHelper.unRegisterReceiver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void generalSetting() {
        if (getThirdPartAPPMode() == 0) {
            this.mLocalAdapter.setScanMode(23, 0);
        } else {
            this.mLocalAdapter.setScanMode(20, 0);
        }
        setBTName(getBTName(), false);
    }

    private void writeLastConnectedDevice(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        mLastConnectBtAddress = str;
        MacAddressUtil.addMacAddressToHead(this, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String readLastConnectedDevice() {
        List<String> macAddresses = MacAddressUtil.getMacAddresses(this);
        Log.e(TAG, "readLastConnectedDevice: connectMacAddress size=" + macAddresses.size() + ",mAutoConnectIndex=" + this.mAutoConnectIndex);
        if (!macAddresses.isEmpty()) {
            mLastConnectBtAddress = macAddresses.get(this.mAutoConnectIndex % Math.min(macAddresses.size(), MacAddressUtil.MAX_SIZE));
        } else if (mLastConnectBtAddress == null) {
            mLastConnectBtAddress = SystemProperties.get("persist.sys.bt_address", "");
        }
        return mLastConnectBtAddress;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStateChanged(int i) {
        Log.d(TAG, "handleStateChanged:state->" + i);
        switch (i) {
            case 10:
                Log.e(TAG, "BT_OFF");
                this.mHanlder.sendEmptyMessage(2);
                break;
            case 11:
                Log.d(TAG, "TURNING_ON");
                break;
            case 12:
                Log.e(TAG, "BT_ON");
                this.mHanlder.sendEmptyMessage(1);
                break;
            case 13:
                Log.e(TAG, "TURNING_OFF");
                break;
            default:
                Log.e(TAG, "BT_UNKOWN");
                break;
        }
        this.mBluetoothPbapClientHelper.handleAdapterStateChange(i);
        BleDeviceHelper bleDeviceHelper = this.mBleDeviceHelp;
        if (bleDeviceHelper != null) {
            bleDeviceHelper.handleAdapterStateChange(i);
        }
        callListener(1, null, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScanModeChanged(int i) {
        this.mScanMode = i;
        if (i == 20) {
            Log.d(TAG, "handleScanModeChanged SCAN_MODE_NONE");
        } else if (i == 21) {
            Log.d(TAG, "handleScanModeChanged SCAN_MODE_CONNECTABLE");
        } else if (i == 23) {
            Log.d(TAG, "handleScanModeChanged SCAN_MODE_CONNECTABLE_DISCOVERABLE");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBondStateChange(BluetoothDevice bluetoothDevice, int i, int i2) {
        Log.d(TAG, bluetoothDevice + " ACTION_BOND_STATE_CHANGED " + i);
        if (bluetoothDevice == null) {
            return;
        }
        int i3 = 0;
        if (i == 12) {
            while (i3 < this.mDiscoveryDevices.size()) {
                if (this.mDiscoveryDevices.get(i3).getDeviceAddr().equals(bluetoothDevice.getAddress())) {
                    this.mDiscoveryDevices.remove(i3);
                    break;
                }
                i3++;
            }
        } else if (i == 10) {
            for (int i4 = 0; i4 < this.mDiscoveryDevices.size(); i4++) {
                if (this.mDiscoveryDevices.get(i4).getDeviceAddr().equals(bluetoothDevice.getAddress())) {
                    i3 = 1;
                    break;
                }
            }
            if (i3 == 0) {
                BluetoothDeviceInfo bluetoothDeviceInfo = new BluetoothDeviceInfo();
                String name = bluetoothDevice.getName();
                if (TextUtils.isEmpty(name)) {
                    bluetoothDeviceInfo.setDeviceName(bluetoothDevice.getAddress());
                } else {
                    bluetoothDeviceInfo.setDeviceName(name);
                }
                bluetoothDeviceInfo.setDeviceStatus(5);
                bluetoothDeviceInfo.setDeviceAddr(bluetoothDevice.getAddress());
                this.mDiscoveryDevices.add(bluetoothDeviceInfo);
            }
            switch (i2) {
                case 1:
                    Log.d(TAG, "handleBondChanged: " + getString(R.string.bluetooth_pairing_pin_error_message));
                    break;
                case 2:
                    Log.d(TAG, "handleBondChanged: " + getString(R.string.bluetooth_pairing_rejected_error_message));
                    break;
                case 4:
                    Log.d(TAG, "handleBondChanged: " + getString(R.string.bluetooth_pairing_device_down_error_message));
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                    Log.d(TAG, "handleBondChanged: " + getString(R.string.bluetooth_pairing_error_message));
                    break;
            }
        }
        BluetoothDeviceInfo bluetoothDeviceInfo2 = new BluetoothDeviceInfo();
        bluetoothDeviceInfo2.setDeviceName(bluetoothDevice.getName());
        bluetoothDeviceInfo2.setDeviceAddr(bluetoothDevice.getAddress());
        bluetoothDeviceInfo2.setDeviceStatus(bluetoothDevice.getBondState());
        callListener(5, bluetoothDeviceInfo2, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDeviceFound(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice != null) {
            Log.d(TAG, "Found Device: " + bluetoothDevice.getName() + " " + bluetoothDevice.getAddress() + " type:" + bluetoothDevice.getType());
            if (TextUtils.isEmpty(bluetoothDevice.getName())) {
                return;
            }
            if (this.mDiscoveryDevices == null) {
                this.mDiscoveryDevices = new ArrayList();
            }
            for (BluetoothDeviceInfo bluetoothDeviceInfo : this.mDiscoveryDevices) {
                if (bluetoothDeviceInfo.getDeviceAddr().equals(bluetoothDevice.getAddress())) {
                    if (bluetoothDevice.getAliasName() != null) {
                        bluetoothDeviceInfo.mDeviceName = bluetoothDevice.getAliasName();
                    } else if (bluetoothDevice.getName() != null) {
                        bluetoothDeviceInfo.mDeviceName = bluetoothDevice.getName();
                    } else {
                        bluetoothDeviceInfo.mDeviceName = bluetoothDevice.getAddress();
                    }
                    Log.i(TAG, "update Info");
                    callListener(3, bluetoothDeviceInfo, 0);
                    return;
                }
            }
            if (bluetoothDevice.getBondState() != 12) {
                BluetoothDeviceInfo bluetoothDeviceInfo2 = new BluetoothDeviceInfo();
                if (bluetoothDevice.getAliasName() != null) {
                    bluetoothDeviceInfo2.mDeviceName = bluetoothDevice.getAliasName();
                } else if (bluetoothDevice.getName() != null) {
                    bluetoothDeviceInfo2.mDeviceName = bluetoothDevice.getName();
                } else {
                    bluetoothDeviceInfo2.mDeviceName = bluetoothDevice.getAddress();
                }
                bluetoothDeviceInfo2.mDeviceStatus = -1;
                bluetoothDeviceInfo2.mDeviceAddr = bluetoothDevice.getAddress();
                this.mDiscoveryDevices.add(bluetoothDeviceInfo2);
                callListener(3, bluetoothDeviceInfo2, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleConnectStateChanged(BluetoothDevice bluetoothDevice, int i) {
        if (bluetoothDevice == null) {
            return;
        }
        Log.d(TAG, "onReceive: HFP ACTION_CONNECTION_STATE_CHANGED " + i);
        if (i == 0) {
            if (this.mIsDisconnectByUser) {
                Log.d(TAG, "handleConnectStateChanged DisconnectByUser");
                this.mIsDisconnectByUser = false;
            } else if (!this.mHanlder.hasMessages(5)) {
                startAutoConnect(5000);
            }
            stopAutoConnectedNetwork();
        } else if (i == 2) {
            if (!Utils.isT5Platform()) {
                Utils.showToast(this.mContext, R.string.str_handsfree_device_connected);
            }
            stopAutoConnect();
            if (isAutoConnectedNetwork()) {
                startAutoConnectedNetwork(AUTO_NETWORK_CONNECT_DELAY);
            }
            writeLastConnectedDevice(bluetoothDevice.getAddress());
        }
        BluetoothDeviceInfo bluetoothDeviceInfo = new BluetoothDeviceInfo();
        bluetoothDeviceInfo.setDeviceAddr(bluetoothDevice.getAddress());
        bluetoothDeviceInfo.setDeviceName(bluetoothDevice.getName());
        bluetoothDeviceInfo.setDeviceStatus(bluetoothDevice.getBondState());
        callListener(6, bluetoothDeviceInfo, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleA2dpsinkConnectStatus(BluetoothDevice bluetoothDevice, int i) {
        Log.d(TAG, "a2dpsink connectstatus:" + i);
        if (i == 2 && !Utils.isT5Platform()) {
            if (this.isInZjHiCarMode) {
                Log.d(TAG, "isInZjHiCarMode ->disconnect a2dp device:" + bluetoothDevice.getAddress() + " after 1 seconds");
                this.mHanlder.removeMessages(7);
                this.mHanlder.sendEmptyMessageDelayed(7, 1000L);
                return;
            }
            Utils.showToast(this.mContext, R.string.str_a2dp_device_connected);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleACLConnected(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "handleACLConnected");
        this.mDelayConnectAddress = "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleACLDisconnected(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            Log.e(TAG, "handleRemoteDeviceDisconnected device is null");
        } else {
            if (TextUtils.isEmpty(this.mDelayConnectAddress)) {
                return;
            }
            Log.d(TAG, "handleACLDisconnected try connect " + this.mDelayConnectAddress);
            connectDevice(this.mDelayConnectAddress);
        }
    }

    private boolean isAndroidAutoMode(String str) {
        return ZJ_ANDROID_AUTO_WIRED.equals(str) || ZJ_ANDROID_AUTO_WIRELESS.equals(str) || LETTER_ANDROID_AUTO_WIRED.equals(str) || LETTER_ANDROID_AUTO_WIRELESS.equals(str);
    }

    private boolean isCarplayMode(String str) {
        return ZJ_CARPLAY_WIRED.equals(str) || ZJ_CARPLAY_WIRELESS.equals(str);
    }

    private boolean isMirrorMode(String str) {
        return ZJ_MIRROR_WIRED.equals(str) || ZJ_MIRROR_WIRELESS.equals(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleZJStatus(Intent intent) {
        Log.d(TAG, "ZJ_MIRROR_CARPLAY_STATUS status=" + intent.getStringExtra("status") + " phoneMode=" + intent.getStringExtra("phoneMode"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disconnectA2dp() {
        List<BluetoothDevice> connectedDevices;
        A2dpSinkProfile a2dpSinkProfile = this.mProfileManager.getA2dpSinkProfile();
        if (a2dpSinkProfile == null || (connectedDevices = a2dpSinkProfile.getConnectedDevices()) == null) {
            return;
        }
        Iterator<BluetoothDevice> it = connectedDevices.iterator();
        while (it.hasNext()) {
            disconnectA2dp(it.next().getAddress());
        }
    }

    private void restoreConnectA2dp() {
        List<BluetoothDevice> connectedDevices;
        A2dpSinkProfile a2dpSinkProfile = this.mProfileManager.getA2dpSinkProfile();
        HeadsetClientProfile headsetClientProfile = this.mProfileManager.getHeadsetClientProfile();
        if (a2dpSinkProfile == null || headsetClientProfile == null || (connectedDevices = headsetClientProfile.getConnectedDevices()) == null || connectedDevices.isEmpty()) {
            return;
        }
        connectA2dp(connectedDevices.get(0).getAddress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHiCarStatus(Intent intent) {
        BluetoothHfpclientService bluetoothHfpclientService;
        List<BluetoothHeadsetClientCall> currentCalls;
        if (intent == null) {
            return;
        }
        String stringExtra = intent.getStringExtra("status");
        Log.d(TAG, "handleHiCarStatus status=" + stringExtra);
        if (STATUS_CONNECT.equals(stringExtra)) {
            String stringExtra2 = intent.getStringExtra(EXTRA_BT_ADDRESS);
            if (TextUtils.isEmpty(stringExtra2)) {
                Log.e(TAG, "handleHiCarStatus address is empty !");
                return;
            }
            HeadsetClientProfile headsetClientProfile = this.mProfileManager.getHeadsetClientProfile();
            if (headsetClientProfile == null || headsetClientProfile.getConnectionStatus(this.mLocalAdapter.getRemoteDevice(stringExtra2)) != 0) {
                return;
            }
            connectDevice(stringExtra2);
            return;
        }
        if (!STATUS_DISCONNECT.equals(stringExtra) || (bluetoothHfpclientService = BluetoothHfpclientService.getInstance()) == null || (currentCalls = bluetoothHfpclientService.getCurrentCalls()) == null || currentCalls.isEmpty()) {
            return;
        }
        resetBT();
        Log.d(TAG, "handleHiCarStatus resetBT！！");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHcnLinkStatus(Intent intent) {
        if (intent == null) {
            return;
        }
        String stringExtra = intent.getStringExtra(EXTRA_BLUETOOTH);
        String stringExtra2 = intent.getStringExtra(EXTRA_TYPE);
        String stringExtra3 = intent.getStringExtra("status");
        Log.d(TAG, "handleHcnLinkStatus type=" + stringExtra2 + " state=" + stringExtra3 + " btCmd=" + stringExtra);
        if (DATA_BLUETOOTH_STOP_A2DP.equals(stringExtra)) {
            this.isInZjHiCarMode = true;
            disconnectA2dp();
        } else if (DATA_BLUETOOTH_RESUME_A2DP.equals(stringExtra) && this.isInZjHiCarMode) {
            this.isInZjHiCarMode = false;
            this.mHanlder.removeMessages(7);
            restoreConnectA2dp();
        } else if (DATA_BLUETOOTH_CLOSE_BT.equals(stringExtra)) {
            this.mThirdPartMode = 2;
            this.isInLatterHiCarMode = true;
            setBluetoothEnable(false, false);
        } else if (DATA_BLUETOOTH_OPEN_BT.equals(stringExtra) && this.isInLatterHiCarMode) {
            this.mThirdPartMode = 0;
            this.isInLatterHiCarMode = false;
            setBluetoothEnable(true, false);
        } else if (DATA_BLUETOOTH_NULL.equals(stringExtra)) {
            if (this.isInZjHiCarMode) {
                this.isInZjHiCarMode = false;
                this.mHanlder.removeMessages(7);
                restoreConnectA2dp();
            } else if (this.isInLatterHiCarMode) {
                this.isInLatterHiCarMode = false;
                this.mThirdPartMode = 0;
                setBluetoothEnable(true, false);
            }
        }
        if (DATA_TYPE_AUTO.equals(stringExtra2) || DATA_TYPE_HICAR.equals(stringExtra2)) {
            BluetoothHfpclientService bluetoothHfpclientService = BluetoothHfpclientService.getInstance();
            if (DATA_STATUS_CONNECTED.equals(stringExtra3)) {
                if (bluetoothHfpclientService != null) {
                    bluetoothHfpclientService.onSetCallViewEnable(false);
                }
            } else {
                if (!DATA_STATUS_DISCONNECTED.equals(stringExtra3) || bluetoothHfpclientService == null) {
                    return;
                }
                bluetoothHfpclientService.onSetCallViewEnable(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLetterCarLife(Intent intent) {
        if (intent == null) {
            return;
        }
        String stringExtra = intent.getStringExtra("status");
        String stringExtra2 = intent.getStringExtra("phoneMode");
        Log.d(TAG, "handleLetterCarLife status=" + stringExtra + " phoneMode=" + stringExtra2);
        if (STATUS_CONNECT.equals(stringExtra)) {
            if (stringExtra2.contains(CAR_LIFE_MODE)) {
                disconnectA2dp();
            }
        } else if (STATUS_DISCONNECT.equals(stringExtra) && stringExtra2.contains(CAR_LIFE_MODE)) {
            restoreConnectA2dp();
        }
    }

    public synchronized List<BluetoothDeviceInfo> getDeviceList() {
        Log.d(TAG, "getDeviceList:" + this.mDiscoveryDevices.size());
        return this.mDiscoveryDevices;
    }

    public synchronized List<BluetoothDeviceInfo> getBondedDevices() {
        Set<BluetoothDevice> bondedDevices = this.mLocalAdapter.getBondedDevices();
        if (bondedDevices == null) {
            Log.e(TAG, "getBondedDevices is null!!");
            return null;
        }
        Log.i(TAG, "getBondedDevices count = " + bondedDevices.size());
        this.mBondedDevices.clear();
        Iterator<BluetoothDevice> it = bondedDevices.iterator();
        while (it.hasNext()) {
            this.mBondedDevices.add(new BluetoothDeviceInfo(it.next()));
        }
        return this.mBondedDevices;
    }

    public synchronized BluetoothDeviceInfo getConnectDevice() {
        Log.d(TAG, "getConnectDevice");
        HeadsetClientProfile headsetClientProfile = this.mProfileManager.getHeadsetClientProfile();
        if (headsetClientProfile != null) {
            List<BluetoothDevice> devicesMatchingConnectionStates = headsetClientProfile.getDevicesMatchingConnectionStates(new int[]{2});
            if (!devicesMatchingConnectionStates.isEmpty()) {
                BluetoothDevice bluetoothDevice = devicesMatchingConnectionStates.get(0);
                BluetoothDeviceInfo bluetoothDeviceInfo = new BluetoothDeviceInfo();
                bluetoothDeviceInfo.setDeviceName(bluetoothDevice.getName());
                bluetoothDeviceInfo.setDeviceStatus(1);
                bluetoothDeviceInfo.setDeviceAddr(bluetoothDevice.getAddress());
                return bluetoothDeviceInfo;
            }
        }
        return null;
    }

    public synchronized void pairDevice(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e(TAG, "pairDevice address=null");
            return;
        }
        stopDiscovery();
        CachedBluetoothDevice cachedBluetoothDeviceFindDevice = this.mCachedDeviceManager.findDevice(this.mLocalAdapter.getRemoteDevice(str));
        if (cachedBluetoothDeviceFindDevice != null) {
            cachedBluetoothDeviceFindDevice.setConnectAfterPairing(false);
            cachedBluetoothDeviceFindDevice.startPairing();
        }
    }

    public synchronized void connectDevice(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e(TAG, "connectDevice address=null");
            return;
        }
        CachedBluetoothDevice cachedBluetoothDeviceFindDeviceByAddr = this.mCachedDeviceManager.findDeviceByAddr(str);
        if (cachedBluetoothDeviceFindDeviceByAddr == null) {
            Log.e(TAG, "connectDevice cachedDevice null!");
            return;
        }
        if (this.mProfileManager.isBusy()) {
            Log.e(TAG, "connectDevice ProfileManager isBusy");
            return;
        }
        this.mAutoConnectIndex++;
        ArraySet<BluetoothDevice> connectedDevices = this.mProfileManager.getConnectedDevices();
        if (!connectedDevices.isEmpty()) {
            Iterator<BluetoothDevice> it = connectedDevices.iterator();
            boolean z = false;
            while (it.hasNext()) {
                String address = it.next().getAddress();
                if (!str.equals(address)) {
                    this.mDelayConnectAddress = str;
                    Log.d(TAG, "connectDevice disconnect " + address);
                    disconnectDevice(address);
                    z = true;
                }
            }
            if (z) {
                this.mAutoConnectIndex--;
                return;
            }
        }
        this.mDelayConnectAddress = "";
        int bondState = cachedBluetoothDeviceFindDeviceByAddr.getBondState();
        if (bondState == 12) {
            if (getThirdPartAPPMode() == 0) {
                Log.d(TAG, "startConnect " + cachedBluetoothDeviceFindDeviceByAddr.getAddress());
                cachedBluetoothDeviceFindDeviceByAddr.connect(false);
            } else {
                Log.e(TAG, "connectDevice failed mThirdPartMode=" + getThirdPartAPPMode());
            }
        } else if (bondState == 10) {
            Log.d(TAG, "startPairing " + cachedBluetoothDeviceFindDeviceByAddr.getAddress());
            cachedBluetoothDeviceFindDeviceByAddr.setConnectAfterPairing(true);
            cachedBluetoothDeviceFindDeviceByAddr.startPairing();
        } else {
            Log.e(TAG, "connectDevice failed bondState=" + bondState);
        }
    }

    public synchronized void disconnectDevice(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e(TAG, "disconnectDevice address isEmpty!");
            return;
        }
        CachedBluetoothDevice cachedBluetoothDeviceFindDeviceByAddr = this.mCachedDeviceManager.findDeviceByAddr(str);
        if (cachedBluetoothDeviceFindDeviceByAddr == null) {
            Log.e(TAG, "disconnectDevice cacheDevice == Null");
        } else {
            cachedBluetoothDeviceFindDeviceByAddr.disconnect();
            Log.i(TAG, "disconnectDevice:" + str);
        }
    }

    public synchronized void unpairDevice(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e(TAG, "deleteDevice address=null");
            return;
        }
        Log.i(TAG, "deleteDevice");
        stopDiscovery();
        CachedBluetoothDevice cachedBluetoothDeviceFindDeviceByAddr = this.mCachedDeviceManager.findDeviceByAddr(str);
        if (cachedBluetoothDeviceFindDeviceByAddr != null) {
            if (cachedBluetoothDeviceFindDeviceByAddr.isConnected()) {
                cachedBluetoothDeviceFindDeviceByAddr.setUnpairAfterDisconnect(true);
                cachedBluetoothDeviceFindDeviceByAddr.disconnect();
            } else if (cachedBluetoothDeviceFindDeviceByAddr.getBondState() == 12) {
                cachedBluetoothDeviceFindDeviceByAddr.unpair();
            }
        }
    }

    public synchronized boolean isBluetoothEnable() {
        boolean zIsEnabled;
        zIsEnabled = this.mLocalAdapter.isEnabled();
        Log.d(TAG, "isBluetoothEnable:" + zIsEnabled);
        return zIsEnabled;
    }

    public synchronized boolean isBluetoothConnected() {
        HeadsetClientProfile headsetClientProfile = this.mProfileManager.getHeadsetClientProfile();
        if (headsetClientProfile == null) {
            return false;
        }
        return !headsetClientProfile.getDevicesMatchingConnectionStates(new int[]{2}).isEmpty();
    }

    public synchronized void setBluetoothEnable(boolean z, boolean z2) {
        if (z) {
            if (!this.mAccStatus) {
                Log.e(TAG, "Acc off can not enable bluetooth !!!");
                return;
            }
        }
        Log.i(TAG, "setBluetoothEnable:" + z);
        this.mHanlder.removeMessages(4);
        this.mHanlder.removeMessages(3);
        this.mLocalAdapter.setBluetoothEnabled(z);
        if (z2) {
            SystemProperties.set("persist.sys.BT_Status", String.valueOf(z));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getSavedBtStatus() {
        return SystemProperties.getBoolean("persist.sys.BT_Status", true);
    }

    public synchronized String getBTName() {
        if (this.mBTName == null) {
            this.mBTName = SystemProperties.get("persist.sys.BTName", DEFAULT_NAME);
        }
        Log.d(TAG, "getBTName: " + this.mBTName);
        return this.mBTName;
    }

    public synchronized void setBTName(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Log.d(TAG, "setBTName: " + str + " save=" + z);
        this.mBTName = str;
        this.mLocalAdapter.setName(str);
        if (z) {
            SystemProperties.set("persist.sys.BTName", str);
        }
    }

    public synchronized String getBTPincode() {
        if (this.mBTPincode == null) {
            this.mBTPincode = SystemProperties.get("persist.sys.bt.pin.code", DEFAULT_PINCODE);
        }
        Log.d(TAG, "getBTPincode " + this.mBTPincode);
        return this.mBTPincode;
    }

    public synchronized void setBTPincode(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String strTrim = str.trim();
        if (strTrim.length() >= 4 && strTrim.length() <= 16) {
            this.mBTPincode = strTrim;
            Log.d(TAG, "setBTPincode =" + this.mBTPincode);
            SystemProperties.set("persist.sys.bt.pin.code", strTrim);
        }
    }

    public synchronized boolean isBluetoothAutoAnswer() {
        boolean z;
        z = SystemProperties.getBoolean("persist.sys.AutoAnswerBt", false);
        Log.i(TAG, "isBluetoothAutoAnswer " + z);
        return z;
    }

    public synchronized void setBluetoothAutoAnswer(boolean z) {
        Log.i(TAG, "setBluetoothAutoAnswer:" + z);
        SystemProperties.set("persist.sys.AutoAnswerBt", String.valueOf(z));
    }

    public synchronized void resetBT() {
        Log.i(TAG, "resetBT:");
        if (this.mLocalAdapter.getBluetoothState() == 12) {
            setBluetoothEnable(false, false);
        }
        if (!this.mHanlder.hasMessages(3)) {
            this.mHanlder.sendEmptyMessageDelayed(3, 1000L);
        }
    }

    public synchronized void setBluetoothAutoConnect(boolean z) {
        Log.i(TAG, "setBluetoothAutoConnect:" + z);
        SystemProperties.set("persist.sys.AutoConnectBt", String.valueOf(z));
        if (z) {
            startAutoConnect(0);
        } else {
            stopAutoConnect();
        }
    }

    public synchronized boolean isBluetoothAutoConnect() {
        return SystemProperties.getBoolean("persist.sys.AutoConnectBt", true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAutoConnect(int i) {
        this.mAutoConnectTimes = 0;
        this.mAutoConnectIndex = 0;
        if (isBluetoothAutoConnect()) {
            this.mHanlder.removeMessages(5);
            Log.d(TAG, "Bluetooth will auto connect after " + i);
            this.mHanlder.sendEmptyMessageDelayed(5, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopAutoConnect() {
        Log.d(TAG, "stopAutoConnect !!!!");
        this.mAutoConnectTimes = 0;
        this.mAutoConnectIndex = 0;
        this.mHanlder.removeMessages(5);
    }

    public synchronized void setAutoConnectedNetwork(boolean z) {
        PanProfile panProfile = this.mProfileManager.getPanProfile();
        if (panProfile == null) {
            return;
        }
        if (z) {
            startAutoConnectedNetwork(0);
        } else {
            stopAutoConnectedNetwork();
            panProfile.disconnect();
        }
        notifySystemUINetworkState(z);
        panProfile.saveAutoConnectState(z);
    }

    public synchronized boolean isAutoConnectedNetwork() {
        PanProfile panProfile = this.mProfileManager.getPanProfile();
        if (panProfile == null) {
            return false;
        }
        return panProfile.isAutoConnectable();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAutoConnectedNetwork(int i) {
        this.mHanlder.removeMessages(6);
        this.mHanlder.sendEmptyMessageDelayed(6, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopAutoConnectedNetwork() {
        Log.d(TAG, "stopAutoConnectedNetwork!!!");
        this.mHanlder.removeMessages(6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySystemUINetworkState(boolean z) {
        PanProfile panProfile = this.mProfileManager.getPanProfile();
        if (panProfile == null) {
            return;
        }
        panProfile.notifySystemUINetworkState(z);
    }

    public synchronized void connectA2dp(String str) {
        CachedBluetoothDevice cachedBluetoothDeviceFindDeviceByAddr = this.mCachedDeviceManager.findDeviceByAddr(str);
        if (cachedBluetoothDeviceFindDeviceByAddr == null) {
            Log.e(TAG, "connectA2dp: cachedDevice null");
        } else {
            if (cachedBluetoothDeviceFindDeviceByAddr.isConnectedProfile(this.mProfileManager.getHeadsetClientProfile())) {
                cachedBluetoothDeviceFindDeviceByAddr.connectProfile(this.mProfileManager.getA2dpSinkProfile());
            }
        }
    }

    public synchronized void disconnectA2dp(String str) {
        CachedBluetoothDevice cachedBluetoothDeviceFindDeviceByAddr = this.mCachedDeviceManager.findDeviceByAddr(str);
        if (cachedBluetoothDeviceFindDeviceByAddr == null) {
            Log.e(TAG, "disconnectA2dp: cachedDevice null");
            return;
        }
        A2dpSinkProfile a2dpSinkProfile = this.mProfileManager.getA2dpSinkProfile();
        if (cachedBluetoothDeviceFindDeviceByAddr.isConnectedProfile(a2dpSinkProfile)) {
            cachedBluetoothDeviceFindDeviceByAddr.disconnect(a2dpSinkProfile);
        }
    }

    public synchronized boolean isA2dpConnected(String str) {
        try {
            CachedBluetoothDevice cachedBluetoothDeviceFindDevice = this.mCachedDeviceManager.findDevice(this.mLocalAdapter.getRemoteDevice(str));
            if (cachedBluetoothDeviceFindDevice == null) {
                return false;
            }
            return cachedBluetoothDeviceFindDevice.isConnectedProfile(this.mProfileManager.getA2dpSinkProfile());
        } catch (Exception unused) {
            Log.d(TAG, "isA2dpConnected: address=" + str + " Exception!!!");
            return false;
        }
    }

    public synchronized void startDiscovery() {
        List<BluetoothDeviceInfo> list = this.mDiscoveryDevices;
        if (list != null) {
            list.clear();
        }
        Log.i(TAG, "start.........discovery");
        this.mLocalAdapter.startScanning(true);
    }

    public synchronized void stopDiscovery() {
        if (isDiscovering()) {
            this.mLocalAdapter.stopScanning();
        }
    }

    public synchronized boolean isDiscovering() {
        return this.mLocalAdapter.isDiscovering();
    }

    public synchronized void registerCallback(IAdapterCallback iAdapterCallback) {
        if (iAdapterCallback == null) {
            return;
        }
        this.mCallBackList.unregister(iAdapterCallback);
        this.mCallBackList.register(iAdapterCallback);
    }

    public synchronized void unregisterCallback(IAdapterCallback iAdapterCallback) {
        if (iAdapterCallback == null) {
            return;
        }
        this.mCallBackList.unregister(iAdapterCallback);
    }

    public synchronized void callListener(int i, BluetoothDeviceInfo bluetoothDeviceInfo, int i2) {
        RemoteCallbackList<IAdapterCallback> remoteCallbackList;
        int iBeginBroadcast = this.mCallBackList.beginBroadcast();
        Log.d(TAG, "callListener: count=" + iBeginBroadcast);
        for (int i3 = 0; i3 < iBeginBroadcast; i3++) {
            try {
                IAdapterCallback iAdapterCallback = (IAdapterCallback) this.mCallBackList.getBroadcastItem(i3);
                if (i == 1) {
                    iAdapterCallback.onBluetoothStateChanged(i2);
                } else if (i == 2) {
                    iAdapterCallback.onDiscoveryStateChanged(i2);
                } else if (i == 3) {
                    iAdapterCallback.onDiscoveryDeviceFound(bluetoothDeviceInfo);
                } else if (i == 5) {
                    iAdapterCallback.onDeviceBondStateChanged(bluetoothDeviceInfo, i2);
                } else if (i == 6) {
                    iAdapterCallback.onConnectionStateChanged(bluetoothDeviceInfo, i2);
                }
            } catch (RemoteException unused) {
                remoteCallbackList = this.mCallBackList;
            } catch (Throwable th) {
                this.mCallBackList.finishBroadcast();
                throw th;
            }
        }
        remoteCallbackList = this.mCallBackList;
        remoteCallbackList.finishBroadcast();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getThirdPartAPPMode() {
        return this.mThirdPartMode;
    }

    private final class BluetoothAdapterManagerBinder extends IBluetoothAdapterManagerService.Stub {
        private BluetoothAdapterManagerBinder() {
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public List<BluetoothDeviceInfo> getDeviceList() {
            return BluetoothAdapterManagerService.this.getDeviceList();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public List<BluetoothDeviceInfo> getBondedDevices() {
            return BluetoothAdapterManagerService.this.getBondedDevices();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public BluetoothDeviceInfo getConnectDevice() {
            return BluetoothAdapterManagerService.this.getConnectDevice();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void pairDevice(String str) {
            BluetoothAdapterManagerService.this.pairDevice(str);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void unpairDevice(String str) {
            BluetoothAdapterManagerService.this.unpairDevice(str);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void connectDevice(String str) {
            if (BluetoothAdapterManagerService.this.isBluetoothAutoConnect()) {
                BluetoothAdapterManagerService.this.mHanlder.removeMessages(5);
                Log.d(BluetoothAdapterManagerService.TAG, "connectDevice will auto connect after 20000");
                BluetoothAdapterManagerService.this.mHanlder.sendEmptyMessageDelayed(5, 20000L);
            }
            stopDiscovery();
            Log.d(BluetoothAdapterManagerService.TAG, "connectDevice stopDiscovery");
            BluetoothAdapterManagerService.this.connectDevice(str);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void disconnectDevice(String str) {
            if (BluetoothAdapterManagerService.this.isBluetoothConnected()) {
                BluetoothAdapterManagerService.this.mIsDisconnectByUser = true;
            }
            BluetoothAdapterManagerService.this.disconnectDevice(str);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public boolean isBluetoothEnable() {
            return BluetoothAdapterManagerService.this.isBluetoothEnable();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public boolean isBluetoothConnected() {
            return BluetoothAdapterManagerService.this.isBluetoothConnected();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void setBluetoothEnable(boolean z) {
            BluetoothAdapterManagerService.this.setBluetoothEnable(z, true);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public String getBTName() {
            Log.d(BluetoothAdapterManagerService.TAG, "binder getBTName");
            return BluetoothAdapterManagerService.this.getBTName();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void setBTName(String str) {
            Log.d(BluetoothAdapterManagerService.TAG, "binder setBTName");
            BluetoothAdapterManagerService.this.setBTName(str, true);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public String getBTPincode() {
            return BluetoothAdapterManagerService.this.getBTPincode();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void setBTPincode(String str) {
            BluetoothAdapterManagerService.this.setBTPincode(str);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public boolean isBluetoothAutoAnswer() {
            return BluetoothAdapterManagerService.this.isBluetoothAutoAnswer();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void setBluetoothAutoAnswer(boolean z) {
            BluetoothAdapterManagerService.this.setBluetoothAutoAnswer(z);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public boolean isBluetoothAutoConnect() {
            return BluetoothAdapterManagerService.this.isBluetoothAutoConnect();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void setBluetoothAutoConnect(boolean z) {
            BluetoothAdapterManagerService.this.setBluetoothAutoConnect(z);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void resetBT() {
            BluetoothAdapterManagerService.this.mThirdPartMode = 0;
            BluetoothAdapterManagerService.this.resetBT();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void startDiscovery() {
            BluetoothAdapterManagerService.this.startDiscovery();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void stopDiscovery() {
            BluetoothAdapterManagerService.this.stopDiscovery();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public boolean isDiscovering() {
            return BluetoothAdapterManagerService.this.isDiscovering();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void setAutoConnectedNetwork(boolean z) {
            BluetoothAdapterManagerService.this.setAutoConnectedNetwork(z);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public boolean isAutoConnectedNetwork() {
            return BluetoothAdapterManagerService.this.isAutoConnectedNetwork();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void connectA2dp(String str) {
            BluetoothAdapterManagerService.this.connectA2dp(str);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void disconnectA2dp(String str) {
            BluetoothAdapterManagerService.this.disconnectA2dp(str);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public boolean isA2dpConnected(String str) {
            return BluetoothAdapterManagerService.this.isA2dpConnected(str);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public boolean isPbapConnected() {
            return BluetoothAdapterManagerService.this.mBluetoothPbapClientHelper.isPbapConnected();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void connectPbap() {
            BluetoothAdapterManagerService.this.mBluetoothPbapClientHelper.connectPbap();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void disConnectPbap() {
            BluetoothAdapterManagerService.this.mBluetoothPbapClientHelper.disConnectPbap();
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public boolean pbapStartDownLoad(int i) {
            return BluetoothAdapterManagerService.this.mBluetoothPbapClientHelper.pbapStartDownLoad(i);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public boolean getPbapDownLoadState(int i) {
            return BluetoothAdapterManagerService.this.mBluetoothPbapClientHelper.getPbapDownLoadState(i);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void registerPbapCallback(IPbapCallback iPbapCallback) {
            BluetoothAdapterManagerService.this.mBluetoothPbapClientHelper.registerPbapCallback(iPbapCallback);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void unregisterPbapCallback(IPbapCallback iPbapCallback) {
            BluetoothAdapterManagerService.this.mBluetoothPbapClientHelper.unregisterPbapCallback(iPbapCallback);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void registerCallback(IAdapterCallback iAdapterCallback) {
            BluetoothAdapterManagerService.this.registerCallback(iAdapterCallback);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public void unregisterCallback(IAdapterCallback iAdapterCallback) {
            BluetoothAdapterManagerService.this.unregisterCallback(iAdapterCallback);
        }

        @Override // com.hcn.bluetooth.service.IBluetoothAdapterManagerService
        public int getThirdPartAPPMode() {
            return BluetoothAdapterManagerService.this.getThirdPartAPPMode();
        }
    }
}
