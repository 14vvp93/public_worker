package com.autochips.bluetooth.fragment;

import android.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.autochips.bluetooth.C0414R;
import com.autochips.bluetooth.MyApplication;
import com.autochips.bluetooth.skin.SkinID;
import com.autochips.bluetooth.skin.SkinUtils;
import com.hcn.bluetooth.api.BluetoothDeviceInfo;
import com.hcn.bluetooth.api.ConnectionListener;
import com.hcn.bluetooth.api.LocalBluetoothAdapterManager;
import com.hcn.bluetooth.api.Utils;
import com.hcn.bluetooth.service.IAdapterCallback;
import com.hcn.skin.support.app.SkinCompatFragment;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.skin2.Skin2;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class PairedFragment extends SkinCompatFragment implements View.OnClickListener {
    private static final int MAX_PIN_CODE_LENGTH = 16;
    private static final int MIN_PIN_CODE_LENGTH = 4;
    private static final int MSG_BLUETOOTH_STATE_CHANGE = 0;
    private static final int MSG_BOND_STATE_CHANGE = 4;
    private static final int MSG_CONNECT_STATE_CHANGE = 5;
    private static final int MSG_DISCOVERY_DEVICE_FOUND = 2;
    private static final int MSG_DISCOVERY_DEVICE_NAME_CHANGE = 3;
    private static final int MSG_DISCOVERY_STATE_CHANGE = 1;
    private static final int MSG_UPDATE_FRAGMENT = 6;
    private static final String REMOTE_CONNECT_STATE = "remote_connect_status";
    private static final String REMOTE_DEVICE_MACADDR = "remote_device_macaddr";
    private static final String REMOTE_DEVICE_NAME = "remote_device_name";
    private static final String TAG = "ParedFragment";
    private CheckBox a2dpSwitchBtn;
    private CheckBox hfpSwitchBtn;
    private LocalBluetoothAdapterManager mAdapterManager;
    private ListAdapter mBluetoothPairedDevicesAdapter;
    private ListAdapter mBluetoothUnpairedDevicesAdapter;
    private ArrayMap<String, Object> mConfigDeviceMap;
    private TextView mConnectState;
    private Context mContext;
    private Button mDeviceName;
    private ViewGroup mLayoutNetwork;
    private ViewGroup mLayoutPaired;
    private MainHandler mMainHandler;
    private TextView mTvBTName;
    private TextView mTvBTPinCode;
    private View root;
    LinearLayout scanLayout;
    private ArrayList<ArrayMap<String, Object>> mBluetoothUnpairedDevices = new ArrayList<>();
    private ArrayList<ArrayMap<String, Object>> mBluetoothPairedDevices = new ArrayList<>();
    private boolean isMarqueeOn = false;
    private Dialog mDeviceProfileConfigDialog = null;
    private Dialog mEditBTNameDialog = null;
    private EditText mEditDeviceName = null;
    private Dialog mEditBTPinDialog = null;
    private EditText mEditPin = null;
    private Button mAutoConnectButton = null;
    private Button mAutoAnswerButton = null;
    private Button mPowerSwitchButton = null;
    private Button mTipsButton = null;
    private Button mNetWorkButton = null;
    private Button mTipsCloseButton = null;
    public String mPinCode = "0000";
    public String mDevName = "CarBT";
    private boolean mIsAutoAnswer = false;
    private boolean mIsAutoConnect = false;
    private AdapterView.OnItemClickListener mUnpairedClickListener = new AdapterView.OnItemClickListener() { // from class: com.autochips.bluetooth.fragment.PairedFragment.1
        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            PairedFragment.this.mBluetoothUnpairedDevicesAdapter.setSelect((String) PairedFragment.this.mBluetoothUnpairedDevicesAdapter.getItem(i).get(PairedFragment.REMOTE_DEVICE_MACADDR));
            PairedFragment.this.mBluetoothUnpairedDevicesAdapter.notifyDataSetChanged();
            PairedFragment.this.mBluetoothPairedDevicesAdapter.setSelect(SkinID.SKIN_NONE);
            PairedFragment.this.mBluetoothPairedDevicesAdapter.notifyDataSetChanged();
        }
    };
    private AdapterView.OnItemClickListener mPairedClickListener = new AdapterView.OnItemClickListener() { // from class: com.autochips.bluetooth.fragment.PairedFragment.2
        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            PairedFragment.this.mBluetoothUnpairedDevicesAdapter.setSelect(SkinID.SKIN_NONE);
            PairedFragment.this.mBluetoothUnpairedDevicesAdapter.notifyDataSetChanged();
            PairedFragment.this.mBluetoothPairedDevicesAdapter.setSelect((String) PairedFragment.this.mBluetoothPairedDevicesAdapter.getItem(i).get(PairedFragment.REMOTE_DEVICE_MACADDR));
            PairedFragment.this.mBluetoothPairedDevicesAdapter.notifyDataSetChanged();
        }
    };
    private ConnectionListener mAdapterListener = new ConnectionListener() { // from class: com.autochips.bluetooth.fragment.PairedFragment.5
        @Override // com.hcn.bluetooth.api.ConnectionListener
        public void onServiceDisconnected() {
        }

        @Override // com.hcn.bluetooth.api.ConnectionListener
        public void onServiceConnected() {
            Log.d(PairedFragment.TAG, "onServiceConnected");
            PairedFragment.this.mAdapterManager.registerCallback(PairedFragment.this.mIAdapterCallback);
            PairedFragment.this.mMainHandler.removeMessages(6);
            PairedFragment.this.mMainHandler.sendEmptyMessage(6);
        }
    };
    private IAdapterCallback mIAdapterCallback = new IAdapterCallback.Stub() { // from class: com.autochips.bluetooth.fragment.PairedFragment.6
        @Override // com.hcn.bluetooth.service.IAdapterCallback
        public void onBluetoothStateChanged(int i) throws RemoteException {
            Message messageObtain = Message.obtain(PairedFragment.this.mMainHandler, 0);
            messageObtain.arg1 = i;
            PairedFragment.this.mMainHandler.sendMessage(messageObtain);
        }

        @Override // com.hcn.bluetooth.service.IAdapterCallback
        public void onDiscoveryStateChanged(int i) throws RemoteException {
            Message messageObtain = Message.obtain(PairedFragment.this.mMainHandler, 1);
            messageObtain.arg1 = i;
            PairedFragment.this.mMainHandler.sendMessage(messageObtain);
        }

        @Override // com.hcn.bluetooth.service.IAdapterCallback
        public void onDiscoveryDeviceFound(BluetoothDeviceInfo bluetoothDeviceInfo) throws RemoteException {
            Message messageObtain = Message.obtain(PairedFragment.this.mMainHandler, 2);
            messageObtain.obj = bluetoothDeviceInfo;
            PairedFragment.this.mMainHandler.sendMessage(messageObtain);
        }

        @Override // com.hcn.bluetooth.service.IAdapterCallback
        public void onDiscoveryDeviceNameChanged(BluetoothDeviceInfo bluetoothDeviceInfo) throws RemoteException {
            Message messageObtain = Message.obtain(PairedFragment.this.mMainHandler, 3);
            messageObtain.obj = bluetoothDeviceInfo;
            PairedFragment.this.mMainHandler.sendMessage(messageObtain);
        }

        @Override // com.hcn.bluetooth.service.IAdapterCallback
        public void onDeviceBondStateChanged(BluetoothDeviceInfo bluetoothDeviceInfo, int i) throws RemoteException {
            Message messageObtain = Message.obtain(PairedFragment.this.mMainHandler, 4);
            messageObtain.obj = bluetoothDeviceInfo;
            PairedFragment.this.mMainHandler.sendMessage(messageObtain);
        }

        @Override // com.hcn.bluetooth.service.IAdapterCallback
        public void onConnectionStateChanged(BluetoothDeviceInfo bluetoothDeviceInfo, int i) throws RemoteException {
            Message messageObtain = Message.obtain(PairedFragment.this.mMainHandler, 5);
            messageObtain.arg1 = i;
            messageObtain.obj = bluetoothDeviceInfo;
            PairedFragment.this.mMainHandler.sendMessage(messageObtain);
        }
    };
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.autochips.bluetooth.fragment.PairedFragment.9
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                if (intExtra == 10) {
                    Log.d(PairedFragment.TAG, "onReceive: ACTION_STATE_CHANGED STATE_OFF");
                    if (PairedFragment.this.mPowerSwitchButton != null) {
                        PairedFragment.this.mPowerSwitchButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
                        return;
                    }
                    return;
                }
                if (intExtra == 12) {
                    Log.d(PairedFragment.TAG, "onReceive: ACTION_STATE_CHANGED STATE_ON");
                    if (PairedFragment.this.mPowerSwitchButton != null) {
                        PairedFragment.this.mPowerSwitchButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
                    }
                }
            }
        }
    };

    @Override // com.hcn.skin.support.observe.SkinLayoutObserver
    public int getLayoutRes() {
        return C0414R.layout.bt_pairedhistory;
    }

    @Override // com.hcn.skin.support.observe.SkinLayoutObserver
    public void onBindViewData() {
    }

    private class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int id;
            super.handleMessage(message);
            int i = message.what;
            String str = SkinID.SKIN_NONE;
            switch (i) {
                case 0:
                    Log.d(PairedFragment.TAG, "MSG_BLUETOOTH_STATE_CHANGE " + message.arg1);
                    removeMessages(6);
                    sendEmptyMessageDelayed(6, 100L);
                    break;
                case 1:
                    if (message.arg1 == 1) {
                        Log.d(PairedFragment.TAG, "MSG_DISCOVERY_STATE_CHANGE start");
                        PairedFragment.this.onActionDiscoveryStarted();
                        PairedFragment.this.updateUnpairedDevicesList();
                    } else {
                        Log.d(PairedFragment.TAG, "MSG_DISCOVERY_STATE_CHANGE end");
                        PairedFragment.this.updateUnpairedDevicesList();
                        PairedFragment.this.onActionDiscoveryFinished();
                    }
                    break;
                case 2:
                    PairedFragment.this.updateUnpairedDevicesList();
                    break;
                case 3:
                    PairedFragment.this.updateUnpairedDevicesList();
                    break;
                case 4:
                    BluetoothDeviceInfo bluetoothDeviceInfo = (BluetoothDeviceInfo) message.obj;
                    Log.d(PairedFragment.TAG, "MSG_BOND_STATE_CHANGE name=" + bluetoothDeviceInfo.getDeviceName() + " status=" + bluetoothDeviceInfo.getDeviceStatus());
                    if (bluetoothDeviceInfo.getDeviceStatus() == 12) {
                        PairedFragment.this.mBluetoothPairedDevicesAdapter.setSelect(bluetoothDeviceInfo.getDeviceAddr());
                        PairedFragment.this.mBluetoothUnpairedDevicesAdapter.setSelect(SkinID.SKIN_NONE);
                    } else if (bluetoothDeviceInfo.getDeviceStatus() == 10) {
                        PairedFragment.this.mBluetoothPairedDevicesAdapter.setSelect(SkinID.SKIN_NONE);
                        PairedFragment.this.mBluetoothUnpairedDevicesAdapter.setSelect(bluetoothDeviceInfo.getDeviceAddr());
                    }
                    PairedFragment.this.updateBondedDevicesList();
                    PairedFragment.this.updateUnpairedDevicesList();
                    break;
                case 5:
                    Log.d(PairedFragment.TAG, "MSG_CONNECT_STATE_CHANGE");
                    BluetoothDeviceInfo bluetoothDeviceInfo2 = (BluetoothDeviceInfo) message.obj;
                    int i2 = message.arg1;
                    String deviceName = bluetoothDeviceInfo2.getDeviceName();
                    if (deviceName == null) {
                        deviceName = SkinID.SKIN_NONE;
                    }
                    if (i2 == 1) {
                        id = SkinUtils.getId(C0414R.string.connecting);
                    } else if (i2 != 2) {
                        if (i2 == 3) {
                            id = SkinUtils.getId(C0414R.string.disconnect);
                        } else {
                            id = SkinUtils.getId(C0414R.string.str_dicconnected);
                        }
                        PairedFragment.this.updateConnectState(id, str);
                    } else {
                        id = SkinUtils.getId(C0414R.string.connect);
                        PairedFragment.this.mBluetoothPairedDevicesAdapter.setSelect(bluetoothDeviceInfo2.getDeviceAddr());
                        PairedFragment.this.mBluetoothUnpairedDevicesAdapter.setSelect(SkinID.SKIN_NONE);
                        PairedFragment.this.updateBondedDevicesList();
                        PairedFragment.this.updateUnpairedDevicesList();
                    }
                    str = deviceName;
                    PairedFragment.this.updateConnectState(id, str);
                    break;
                case 6:
                    if (PairedFragment.this.mAdapterManager != null) {
                        BluetoothDeviceInfo connectDevice = PairedFragment.this.mAdapterManager.getConnectDevice();
                        if (connectDevice != null) {
                            PairedFragment.this.mBluetoothPairedDevicesAdapter.setSelect(connectDevice.getDeviceAddr());
                            PairedFragment.this.mBluetoothUnpairedDevicesAdapter.setSelect(SkinID.SKIN_NONE);
                            PairedFragment.this.updateConnectState(SkinUtils.getId(C0414R.string.connect), connectDevice.getDeviceName());
                        } else {
                            PairedFragment.this.updateConnectState(SkinUtils.getId(C0414R.string.str_dicconnected), SkinID.SKIN_NONE);
                        }
                    }
                    PairedFragment.this.updateUnpairedDevicesList();
                    PairedFragment.this.updateBondedDevicesList();
                    PairedFragment.this.updateSettingView();
                    break;
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        Log.d(TAG, "onHiddenChanged: ");
        super.onHiddenChanged(z);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach: ");
        super.onAttach(context);
        LocalBluetoothAdapterManager adapterManager = MyApplication.getInstance().getAdapterManager();
        this.mAdapterManager = adapterManager;
        adapterManager.addConnectListener(this.mAdapterListener);
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(bundle);
        this.mMainHandler = new MainHandler(Looper.getMainLooper());
        this.mAdapterManager.registerCallback(this.mIAdapterCallback);
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d(TAG, "onCreateView");
        this.root = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mContext = SkinUtils.getContext();
        this.mBluetoothUnpairedDevicesAdapter = new ListAdapter(this.mContext, this.mBluetoothUnpairedDevices, SkinUtils.getId(C0414R.layout.device_listitem), new String[]{REMOTE_DEVICE_NAME, REMOTE_CONNECT_STATE, REMOTE_DEVICE_MACADDR}, new int[]{SkinUtils.getId(C0414R.id.item_remote_device_name), SkinUtils.getId(C0414R.id.item_remote_connect_status), SkinUtils.getId(C0414R.id.item_remote_device_macaddr)});
        ListView listView = (ListView) this.root.findViewById(SkinUtils.getId(C0414R.id.bluetooth_usable_devices));
        if (listView != null) {
            listView.setAdapter((android.widget.ListAdapter) this.mBluetoothUnpairedDevicesAdapter);
            listView.setOnItemClickListener(this.mUnpairedClickListener);
            listView.setEnabled(true);
        }
        this.mBluetoothPairedDevicesAdapter = new ListAdapter(this.mContext, this.mBluetoothPairedDevices, SkinUtils.getId(C0414R.layout.device_listitem), new String[]{REMOTE_DEVICE_NAME, REMOTE_CONNECT_STATE, REMOTE_DEVICE_MACADDR}, new int[]{SkinUtils.getId(C0414R.id.item_remote_device_name), SkinUtils.getId(C0414R.id.item_remote_connect_status), SkinUtils.getId(C0414R.id.item_remote_device_macaddr)});
        ListView listView2 = (ListView) this.root.findViewById(SkinUtils.getId(C0414R.id.bluetooth_paired_devices));
        if (listView2 != null) {
            listView2.setAdapter((android.widget.ListAdapter) this.mBluetoothPairedDevicesAdapter);
            listView2.setOnItemClickListener(this.mPairedClickListener);
            listView2.setEnabled(true);
        }
        this.scanLayout = (LinearLayout) this.root.findViewById(SkinUtils.getId(C0414R.id.scan_device_layout));
        this.mDeviceName = (Button) this.root.findViewById(SkinUtils.getId(C0414R.id.bt_device_connected));
        this.mConnectState = (TextView) this.root.findViewById(SkinUtils.getId(C0414R.id.bt_device));
        View viewFindViewById = this.root.findViewById(SkinUtils.getId(C0414R.id.btn_scan_bt));
        View viewFindViewById2 = this.root.findViewById(SkinUtils.getId(C0414R.id.btn_unpair_bt));
        View viewFindViewById3 = this.root.findViewById(SkinUtils.getId(C0414R.id.btn_connect_bt));
        View viewFindViewById4 = this.root.findViewById(SkinUtils.getId(C0414R.id.btn_disconnect_bt));
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(this);
        }
        if (viewFindViewById2 != null) {
            viewFindViewById2.setOnClickListener(this);
        }
        if (viewFindViewById3 != null) {
            viewFindViewById3.setOnClickListener(this);
        }
        if (viewFindViewById4 != null) {
            viewFindViewById4.setOnClickListener(this);
        }
        buildDeviceProfileConfigDialog();
        initSettingView();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        ((Context) Objects.requireNonNull(getContext())).registerReceiver(this.mReceiver, intentFilter);
        return this.root;
    }

    private void initSettingView() {
        this.mTvBTName = (TextView) this.root.findViewById(SkinUtils.getId(C0414R.id.tv_edit_name));
        this.mTvBTPinCode = (TextView) this.root.findViewById(SkinUtils.getId(C0414R.id.tv_edit_pin));
        Button button = (Button) this.root.findViewById(SkinUtils.getId(C0414R.id.btn_edit_name_bt));
        Button button2 = (Button) this.root.findViewById(SkinUtils.getId(C0414R.id.btn_edit_pin_bt));
        this.mAutoConnectButton = (Button) this.root.findViewById(SkinUtils.getId(C0414R.id.switch_autoconnect));
        this.mAutoAnswerButton = (Button) this.root.findViewById(SkinUtils.getId(C0414R.id.switch_autoanswer));
        this.mPowerSwitchButton = (Button) this.root.findViewById(SkinUtils.getId(C0414R.id.switch_power));
        this.mNetWorkButton = (Button) this.root.findViewById(SkinUtils.getId(C0414R.id.network));
        this.mTipsButton = (Button) this.root.findViewById(SkinUtils.getId(C0414R.id.network_tips));
        this.mTipsCloseButton = (Button) this.root.findViewById(SkinUtils.getId(C0414R.id.tips_close));
        this.mLayoutNetwork = (ViewGroup) this.root.findViewById(SkinUtils.getId(C0414R.id.network_layout));
        this.mLayoutPaired = (ViewGroup) this.root.findViewById(SkinUtils.getId(C0414R.id.paired_layout));
        Button button3 = this.mNetWorkButton;
        if (button3 != null) {
            button3.setOnClickListener(this);
        }
        Button button4 = this.mTipsButton;
        if (button4 != null) {
            button4.setOnClickListener(this);
        }
        Button button5 = this.mTipsCloseButton;
        if (button5 != null) {
            button5.setOnClickListener(this);
        }
        Button button6 = this.mPowerSwitchButton;
        if (button6 != null) {
            button6.setOnClickListener(this);
        }
        if (button != null) {
            button.setOnClickListener(this);
        }
        if (button2 != null) {
            button2.setOnClickListener(this);
        }
        Button button7 = this.mAutoConnectButton;
        if (button7 != null) {
            button7.setOnClickListener(this);
        }
        Button button8 = this.mAutoAnswerButton;
        if (button8 != null) {
            button8.setOnClickListener(this);
        }
        ViewGroup viewGroup = (ViewGroup) this.root.findViewById(SkinUtils.getId(C0414R.id.layout_pin));
        String systemProperty = Utils.getSystemProperty("persist.sys.bt.disablessp", "false");
        if (viewGroup == null || !"true".equals(systemProperty)) {
            return;
        }
        viewGroup.setVisibility(0);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (1 == this.mAdapterManager.getThirdPartAPPMode()) {
            Utils.showToast(this.mContext, SkinUtils.getString(C0414R.string.str_zlink_mode));
        }
        switch (SkinUtils.getViewId(view)) {
            case C0414R.id.btn_config_profile /* 2131230838 */:
                this.mConfigDeviceMap = (ArrayMap) view.getTag();
                showDeviceProfileConfigDialog();
                break;
            case C0414R.id.btn_connect_bt /* 2131230839 */:
                onClickConnect();
                break;
            case C0414R.id.btn_disconnect_bt /* 2131230843 */:
                onClickDisconnect();
                break;
            case C0414R.id.btn_edit_name_bt /* 2131230844 */:
                if (this.mEditBTNameDialog == null) {
                    buildEditNameDialog();
                }
                Dialog dialog = this.mEditBTNameDialog;
                if (dialog != null && !dialog.isShowing()) {
                    LocalBluetoothAdapterManager localBluetoothAdapterManager = this.mAdapterManager;
                    if (localBluetoothAdapterManager != null && this.mEditDeviceName != null) {
                        String bTName = localBluetoothAdapterManager.getBTName();
                        this.mDevName = bTName;
                        this.mEditDeviceName.setText(bTName);
                    }
                    this.mEditBTNameDialog.show();
                    break;
                }
                break;
            case C0414R.id.btn_edit_pin_bt /* 2131230845 */:
                if (this.mEditBTPinDialog == null) {
                    buildEditPinDialog();
                }
                Dialog dialog2 = this.mEditBTPinDialog;
                if (dialog2 != null && !dialog2.isShowing()) {
                    LocalBluetoothAdapterManager localBluetoothAdapterManager2 = this.mAdapterManager;
                    if (localBluetoothAdapterManager2 != null && this.mEditDeviceName != null) {
                        String bTPincode = localBluetoothAdapterManager2.getBTPincode();
                        this.mPinCode = bTPincode;
                        EditText editText = this.mEditPin;
                        if (editText != null) {
                            editText.setText(bTPincode);
                        }
                    }
                    this.mEditBTPinDialog.show();
                    break;
                }
                break;
            case C0414R.id.btn_scan_bt /* 2131230867 */:
                if (this.mAdapterManager.isDiscovering()) {
                    this.mAdapterManager.stopDiscovery();
                } else {
                    this.mAdapterManager.startDiscovery();
                }
                break;
            case C0414R.id.btn_unpair_bt /* 2131230872 */:
                onClickUnpair();
                break;
            case C0414R.id.network /* 2131230965 */:
                LocalBluetoothAdapterManager localBluetoothAdapterManager3 = this.mAdapterManager;
                if (localBluetoothAdapterManager3 != null) {
                    boolean z = !localBluetoothAdapterManager3.isAutoConnectedNetwork();
                    this.mAdapterManager.setAutoConnectedNetwork(z);
                    if (z) {
                        this.mNetWorkButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
                    } else {
                        this.mNetWorkButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
                    }
                }
                break;
            case C0414R.id.network_tips /* 2131230967 */:
                showNetworkTips(true);
                break;
            case C0414R.id.switch_autoanswer /* 2131231032 */:
                LocalBluetoothAdapterManager localBluetoothAdapterManager4 = this.mAdapterManager;
                if (localBluetoothAdapterManager4 != null) {
                    boolean z2 = !localBluetoothAdapterManager4.isBluetoothAutoAnswer();
                    this.mIsAutoAnswer = z2;
                    if (z2) {
                        this.mAutoAnswerButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
                    } else {
                        this.mAutoAnswerButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
                    }
                    this.mAdapterManager.setBluetoothAutoAnswer(this.mIsAutoAnswer);
                }
                break;
            case C0414R.id.switch_autoconnect /* 2131231033 */:
                LocalBluetoothAdapterManager localBluetoothAdapterManager5 = this.mAdapterManager;
                if (localBluetoothAdapterManager5 != null) {
                    boolean z3 = !localBluetoothAdapterManager5.isBluetoothAutoConnect();
                    this.mIsAutoConnect = z3;
                    if (z3) {
                        this.mAutoConnectButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
                    } else {
                        this.mAutoConnectButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
                    }
                    this.mAdapterManager.setBluetoothAutoConnect(this.mIsAutoConnect);
                }
                break;
            case C0414R.id.switch_power /* 2131231034 */:
                LocalBluetoothAdapterManager localBluetoothAdapterManager6 = this.mAdapterManager;
                if (localBluetoothAdapterManager6 != null) {
                    BluetoothDeviceInfo connectDevice = localBluetoothAdapterManager6.getConnectDevice();
                    if (connectDevice != null) {
                        this.mAdapterManager.disconnectDevice(connectDevice.getDeviceAddr());
                    }
                    this.mAdapterManager.resetBT();
                }
                break;
            case C0414R.id.tips_close /* 2131231069 */:
                showNetworkTips(false);
                break;
        }
    }

    private void onClickConnect() {
        if (this.mAdapterManager == null) {
            return;
        }
        String select = this.mBluetoothUnpairedDevicesAdapter.getSelect();
        if (select.length() == 0) {
            select = this.mBluetoothPairedDevicesAdapter.getSelect();
        }
        if (select.length() == 0) {
            return;
        }
        this.mAdapterManager.connectDevice(select);
    }

    private void onClickDisconnect() {
        if (this.mAdapterManager == null) {
            return;
        }
        String select = this.mBluetoothPairedDevicesAdapter.getSelect();
        if (select.length() == 0) {
            return;
        }
        this.mAdapterManager.disconnectDevice(select);
    }

    private void onClickUnpair() {
        if (this.mAdapterManager == null) {
            return;
        }
        String select = this.mBluetoothPairedDevicesAdapter.getSelect();
        if (select.length() > 0) {
            this.mAdapterManager.unpairDevice(select);
        }
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onActionDiscoveryStarted() {
        LinearLayout linearLayout = this.scanLayout;
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onActionDiscoveryFinished() {
        LinearLayout linearLayout = this.scanLayout;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateConnectState(int i, String str) {
        this.mConnectState.setText(SkinCompatResources.getInstance().getString(Skin2.xAppId(i)));
        this.mDeviceName.setText(str);
        this.mDeviceName.setOnClickListener(new View.OnClickListener() { // from class: com.autochips.bluetooth.fragment.PairedFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!PairedFragment.this.isMarqueeOn) {
                    PairedFragment.this.mDeviceName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    PairedFragment.this.mDeviceName.setMarqueeRepeatLimit(-1);
                    PairedFragment.this.mDeviceName.setSelected(true);
                    PairedFragment.this.isMarqueeOn = true;
                    return;
                }
                PairedFragment.this.mDeviceName.setEllipsize(null);
                PairedFragment.this.mDeviceName.setMarqueeRepeatLimit(0);
                PairedFragment.this.mDeviceName.setSelected(false);
                PairedFragment.this.isMarqueeOn = false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUnpairedDevicesList() {
        if (this.mAdapterManager != null) {
            this.mBluetoothUnpairedDevices.clear();
            List<BluetoothDeviceInfo> deviceList = this.mAdapterManager.getDeviceList();
            if (deviceList != null) {
                for (BluetoothDeviceInfo bluetoothDeviceInfo : deviceList) {
                    ArrayMap<String, Object> arrayMap = new ArrayMap<>();
                    arrayMap.put(REMOTE_DEVICE_NAME, bluetoothDeviceInfo.getDeviceName());
                    arrayMap.put(REMOTE_DEVICE_MACADDR, bluetoothDeviceInfo.getDeviceAddr());
                    arrayMap.put(REMOTE_CONNECT_STATE, Integer.valueOf(bluetoothDeviceInfo.getDeviceStatus()));
                    this.mBluetoothUnpairedDevices.add(arrayMap);
                }
            }
            this.mBluetoothUnpairedDevicesAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBondedDevicesList() {
        if (this.mAdapterManager != null) {
            this.mBluetoothPairedDevices.clear();
            List<BluetoothDeviceInfo> bondedDevices = this.mAdapterManager.getBondedDevices();
            if (bondedDevices != null) {
                Log.d(TAG, "updateBondedDevicesList: getBondedDevices size=" + bondedDevices.size());
                for (BluetoothDeviceInfo bluetoothDeviceInfo : bondedDevices) {
                    ArrayMap<String, Object> arrayMap = new ArrayMap<>();
                    arrayMap.put(REMOTE_DEVICE_NAME, bluetoothDeviceInfo.getDeviceName());
                    arrayMap.put(REMOTE_DEVICE_MACADDR, bluetoothDeviceInfo.getDeviceAddr());
                    arrayMap.put(REMOTE_CONNECT_STATE, Integer.valueOf(bluetoothDeviceInfo.getDeviceStatus()));
                    this.mBluetoothPairedDevices.add(arrayMap);
                }
            }
            this.mBluetoothPairedDevicesAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        this.mMainHandler.removeMessages(6);
        this.mMainHandler.sendEmptyMessage(6);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
        ((Context) Objects.requireNonNull(getContext())).unregisterReceiver(this.mReceiver);
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        this.mAdapterManager.unregisterCallback(this.mIAdapterCallback);
        this.mMainHandler.removeCallbacksAndMessages(null);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        super.onDetach();
        this.mAdapterManager.removeConnectListener(this.mAdapterListener);
    }

    class ListAdapter extends SimpleAdapter {
        List<ArrayMap<String, Object>> mDeviceList;
        private String mSelectAddr;
        private String str_hfp;
        private String str_hfp_connected;

        public ListAdapter(Context context, List<ArrayMap<String, Object>> list, int i, String[] strArr, int[] iArr) {
            super(context, list, i, strArr, iArr);
            this.mSelectAddr = SkinID.SKIN_NONE;
            this.str_hfp = null;
            this.str_hfp_connected = null;
            this.mSelectAddr = SkinID.SKIN_NONE;
            this.mDeviceList = list;
            this.str_hfp = SkinUtils.getString(C0414R.string.str_hfp_label);
            this.str_hfp_connected = SkinUtils.getString(C0414R.string.bt_status_connected);
        }

        public void setSelect(String str) {
            this.mSelectAddr = str;
        }

        public String getSelect() {
            return this.mSelectAddr;
        }

        @Override // android.widget.SimpleAdapter, android.widget.Adapter
        public ArrayMap<String, Object> getItem(int i) {
            return this.mDeviceList.get(i);
        }

        @Override // android.widget.SimpleAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            View layout;
            ViewHolder viewHolder;
            String str;
            if (view == null) {
                viewHolder = new ViewHolder();
                layout = SkinUtils.getLayout(C0414R.layout.device_listitem);
                viewHolder.nameTextView = (TextView) layout.findViewById(SkinUtils.getId(C0414R.id.item_remote_device_name));
                viewHolder.statusTextView = (TextView) layout.findViewById(SkinUtils.getId(C0414R.id.item_remote_connect_status));
                viewHolder.macaddTextView = (TextView) layout.findViewById(SkinUtils.getId(C0414R.id.item_remote_device_macaddr));
                viewHolder.profileConfigImageButton = layout.findViewById(SkinUtils.getId(C0414R.id.btn_config_profile));
                if (viewHolder.profileConfigImageButton != null) {
                    viewHolder.profileConfigImageButton.setOnClickListener(PairedFragment.this);
                }
                layout.setTag(viewHolder);
            } else {
                layout = view;
                viewHolder = (ViewHolder) view.getTag();
            }
            int size = this.mDeviceList.size();
            int i2 = -1;
            String str2 = SkinID.SKIN_NONE;
            if (i < size) {
                ArrayMap<String, Object> arrayMap = this.mDeviceList.get(i);
                str2 = (String) arrayMap.get(PairedFragment.REMOTE_DEVICE_NAME);
                int iIntValue = ((Integer) arrayMap.get(PairedFragment.REMOTE_CONNECT_STATE)).intValue();
                str = (String) arrayMap.get(PairedFragment.REMOTE_DEVICE_MACADDR);
                if (viewHolder.profileConfigImageButton != null) {
                    viewHolder.profileConfigImageButton.setTag(arrayMap);
                    if (iIntValue == -1 || iIntValue != 12) {
                        viewHolder.profileConfigImageButton.setVisibility(4);
                    } else {
                        viewHolder.profileConfigImageButton.setVisibility(0);
                    }
                }
                i2 = iIntValue;
            } else {
                str = SkinID.SKIN_NONE;
            }
            if (viewHolder.nameTextView != null) {
                viewHolder.nameTextView.setText(str2);
            }
            if (viewHolder.statusTextView != null) {
                viewHolder.statusTextView.setText(String.valueOf(i2));
            }
            if (viewHolder.macaddTextView != null) {
                viewHolder.macaddTextView.setText(str);
            }
            ImageView imageView = (ImageView) layout.findViewById(SkinUtils.getId(C0414R.id.device_select));
            if (imageView != null) {
                if (this.mSelectAddr.length() > 0 && this.mSelectAddr.equals(str)) {
                    imageView.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_rb_on));
                } else {
                    imageView.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_rb_off));
                }
            }
            return layout;
        }

        public final class ViewHolder {
            public TextView macaddTextView;
            public TextView nameTextView;
            public View profileConfigImageButton;
            public TextView statusTextView;

            public ViewHolder() {
            }
        }
    }

    private void showDeviceProfileConfigDialog() {
        if (this.mDeviceProfileConfigDialog == null) {
            return;
        }
        ArrayMap<String, Object> arrayMap = this.mConfigDeviceMap;
        if (arrayMap == null) {
            Log.e(TAG, "showDeviceProfile mConfigDeviceMap is null !");
            return;
        }
        String str = (String) arrayMap.get(REMOTE_DEVICE_NAME);
        String str2 = (String) this.mConfigDeviceMap.get(REMOTE_DEVICE_MACADDR);
        BluetoothDeviceInfo connectDevice = this.mAdapterManager.getConnectDevice();
        if (this.mDeviceProfileConfigDialog.isShowing()) {
            return;
        }
        this.mDeviceProfileConfigDialog.setTitle(str);
        if (this.hfpSwitchBtn != null) {
            if (connectDevice != null && connectDevice.getDeviceAddr().equals(str2)) {
                this.hfpSwitchBtn.setChecked(true);
            } else {
                this.hfpSwitchBtn.setChecked(false);
            }
        }
        if (this.a2dpSwitchBtn != null) {
            if (this.mAdapterManager.isA2dpConnected(str2)) {
                this.a2dpSwitchBtn.setChecked(true);
            } else {
                this.a2dpSwitchBtn.setChecked(false);
            }
        }
        this.mDeviceProfileConfigDialog.show();
    }

    private void buildDeviceProfileConfigDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme.DeviceDefault.Light.Dialog.Alert);
        builder.setTitle(SkinUtils.getString(C0414R.string.bt_device_info));
        CheckBox checkBox = new CheckBox(getContext());
        this.hfpSwitchBtn = checkBox;
        checkBox.setText(SkinUtils.getString(C0414R.string.str_hfp_label));
        this.hfpSwitchBtn.setTextColor(SkinUtils.getColor(R.color.black));
        CheckBox checkBox2 = new CheckBox(getContext());
        this.a2dpSwitchBtn = checkBox2;
        checkBox2.setText(SkinUtils.getString(C0414R.string.str_a2dp_label));
        this.a2dpSwitchBtn.setTextColor(SkinUtils.getColor(R.color.black));
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(24, 12, 24, 0);
        linearLayout.addView(this.hfpSwitchBtn, layoutParams);
        linearLayout.addView(this.a2dpSwitchBtn, layoutParams);
        builder.setView(linearLayout);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.autochips.bluetooth.fragment.PairedFragment.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                PairedFragment.this.onConfigDeviceProfile();
                dialogInterface.dismiss();
            }
        });
        this.mDeviceProfileConfigDialog = builder.create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConfigDeviceProfile() {
        if (this.mConfigDeviceMap == null) {
            Log.e(TAG, "onConfigDeviceProfile mConfigDeviceMap is null !");
            return;
        }
        CheckBox checkBox = this.hfpSwitchBtn;
        boolean z = checkBox != null && checkBox.isChecked();
        CheckBox checkBox2 = this.a2dpSwitchBtn;
        boolean z2 = checkBox2 != null && checkBox2.isChecked();
        String str = (String) this.mConfigDeviceMap.get(REMOTE_DEVICE_MACADDR);
        BluetoothDeviceInfo connectDevice = this.mAdapterManager.getConnectDevice();
        boolean z3 = connectDevice != null && connectDevice.getDeviceAddr().equals(str);
        if (z && !z3) {
            this.mAdapterManager.connectDevice(str);
        } else if (!z && z3) {
            this.mAdapterManager.disconnectDevice(str);
        }
        boolean zIsA2dpConnected = this.mAdapterManager.isA2dpConnected(str);
        if (z2 && z3 && !zIsA2dpConnected) {
            this.mAdapterManager.connectA2dp(str);
        } else {
            if (z2 || !zIsA2dpConnected) {
                return;
            }
            this.mAdapterManager.disconnectA2dp(str);
        }
    }

    private void showNetworkTips(boolean z) {
        Log.d(TAG, "showNetworkTips show=" + z);
        if (z) {
            ViewGroup viewGroup = this.mLayoutNetwork;
            if (viewGroup != null) {
                viewGroup.setVisibility(0);
            }
            ViewGroup viewGroup2 = this.mLayoutPaired;
            if (viewGroup2 != null) {
                viewGroup2.setVisibility(4);
                return;
            }
            return;
        }
        ViewGroup viewGroup3 = this.mLayoutNetwork;
        if (viewGroup3 != null) {
            viewGroup3.setVisibility(8);
        }
        ViewGroup viewGroup4 = this.mLayoutPaired;
        if (viewGroup4 != null) {
            viewGroup4.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSettingView() {
        LocalBluetoothAdapterManager localBluetoothAdapterManager = this.mAdapterManager;
        if (localBluetoothAdapterManager == null) {
            Log.e(TAG, "updateSettingView: mAdapterManager is null!");
            return;
        }
        this.mDevName = localBluetoothAdapterManager.getBTName();
        this.mPinCode = this.mAdapterManager.getBTPincode();
        TextView textView = this.mTvBTName;
        if (textView != null) {
            textView.setText(this.mDevName);
        }
        TextView textView2 = this.mTvBTPinCode;
        if (textView2 != null) {
            textView2.setText(this.mPinCode);
        }
        if (this.mAdapterManager.isBluetoothEnable()) {
            Button button = this.mPowerSwitchButton;
            if (button != null) {
                button.setEnabled(true);
                this.mPowerSwitchButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
            }
        } else {
            Button button2 = this.mPowerSwitchButton;
            if (button2 != null) {
                button2.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
            }
        }
        if (this.mAdapterManager.isBluetoothAutoAnswer()) {
            Button button3 = this.mAutoAnswerButton;
            if (button3 != null) {
                button3.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
            }
        } else {
            Button button4 = this.mAutoAnswerButton;
            if (button4 != null) {
                button4.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
            }
        }
        if (this.mAdapterManager.isBluetoothAutoConnect()) {
            Button button5 = this.mAutoConnectButton;
            if (button5 != null) {
                button5.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
            }
        } else {
            Button button6 = this.mAutoConnectButton;
            if (button6 != null) {
                button6.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
            }
        }
        if (this.mAdapterManager.isAutoConnectedNetwork()) {
            Button button7 = this.mNetWorkButton;
            if (button7 != null) {
                button7.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
                return;
            }
            return;
        }
        Button button8 = this.mNetWorkButton;
        if (button8 != null) {
            button8.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
        }
    }

    private void buildEditNameDialog() {
        if (getContext() == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme.DeviceDefault.Light.Dialog.Alert);
        builder.setTitle(SkinUtils.getString(C0414R.string.bt_device_info));
        builder.setMessage(SkinUtils.getString(C0414R.string.bt_device_name_title));
        EditText editText = new EditText(getContext());
        this.mEditDeviceName = editText;
        editText.setSingleLine(true);
        this.mEditDeviceName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(248)});
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(24, 0, 24, 0);
        linearLayout.addView(this.mEditDeviceName, layoutParams);
        builder.setView(linearLayout);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.autochips.bluetooth.fragment.PairedFragment.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                String strTrim = PairedFragment.this.mEditDeviceName.getText().toString().trim();
                if (strTrim.length() > 20) {
                    strTrim = strTrim.substring(0, 19);
                }
                if (!strTrim.isEmpty()) {
                    if (PairedFragment.this.mAdapterManager != null) {
                        PairedFragment.this.mAdapterManager.setBTName(strTrim);
                    }
                    TextView textView = (TextView) PairedFragment.this.root.findViewById(SkinUtils.getId(C0414R.id.tv_edit_name));
                    if (textView != null) {
                        textView.setText(strTrim);
                    }
                    dialogInterface.dismiss();
                    return;
                }
                Utils.showToast(PairedFragment.this.getContext(), SkinUtils.getString(C0414R.string.bluetooth_device_info_toast));
            }
        });
        this.mEditBTNameDialog = builder.create();
    }

    private void buildEditPinDialog() {
        if (getContext() == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme.DeviceDefault.Light.Dialog.Alert);
        builder.setTitle(SkinUtils.getString(C0414R.string.bt_device_info));
        builder.setMessage(SkinUtils.getString(C0414R.string.bt_device_pin_title));
        EditText editText = new EditText(getContext());
        this.mEditPin = editText;
        editText.setSingleLine(true);
        this.mEditPin.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
        this.mEditPin.setInputType(2);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(24, 0, 24, 0);
        linearLayout.addView(this.mEditPin, layoutParams);
        builder.setView(linearLayout);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.autochips.bluetooth.fragment.PairedFragment.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                String strTrim = PairedFragment.this.mEditPin.getText().toString().trim();
                if (strTrim.length() >= 4 && strTrim.length() <= 16) {
                    TextView textView = (TextView) PairedFragment.this.root.findViewById(SkinUtils.getId(C0414R.id.tv_edit_pin));
                    if (textView != null) {
                        textView.setText(strTrim);
                    }
                    PairedFragment.this.mPinCode = strTrim;
                    PairedFragment.this.mEditBTPinDialog.dismiss();
                    if (PairedFragment.this.mAdapterManager != null) {
                        PairedFragment.this.mAdapterManager.setBTPincode(PairedFragment.this.mPinCode);
                        return;
                    }
                    return;
                }
                Utils.showToast(PairedFragment.this.getContext(), SkinUtils.getString(C0414R.string.bluetooth_device_info_toast));
            }
        });
        this.mEditBTPinDialog = builder.create();
    }
}
