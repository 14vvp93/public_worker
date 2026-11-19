package com.autochips.bluetooth.fragment;

import android.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.autochips.bluetooth.C0414R;
import com.autochips.bluetooth.IFragmentCallback;
import com.autochips.bluetooth.MyApplication;
import com.autochips.bluetooth.skin.SkinID;
import com.autochips.bluetooth.skin.SkinUtils;
import com.hcn.bluetooth.api.BluetoothDeviceInfo;
import com.hcn.bluetooth.api.ConnectionListener;
import com.hcn.bluetooth.api.LocalBluetoothAdapterManager;
import com.hcn.bluetooth.api.Utils;
import com.hcn.bluetooth.service.IAdapterCallback;
import com.hcn.skin.support.app.SkinCompatFragment;
import com.hcn.skin2.Skin2;
import com.hcn.skin2.enhance.SkinExCompatResources;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PairedFragmentEx extends SkinCompatFragment implements View.OnClickListener {
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
    private static final String TAG = "PairedFragmentEx";
    private CheckBox a2dpSwitchBtn;
    private CheckBox hfpSwitchBtn;
    private LocalBluetoothAdapterManager mAdapterManager;
    private PairedListAdapter mBluetoothPairedDevicesAdapter;
    private PairedListAdapter mBluetoothUnpairedDevicesAdapter;
    private ArrayMap<String, Object> mConfigDeviceMap;
    private TextView mConnectState;
    private Context mContext;
    private TextView mDeviceName;
    private PairedHandler mPairedHandler;
    private PopupWindow popupWindow;
    private View root;
    LinearLayout scanLayout;
    private String strMacAdder;
    private ArrayList<ArrayMap<String, Object>> mBluetoothUnpairedDevices = new ArrayList<>();
    private ArrayList<ArrayMap<String, Object>> mBluetoothPairedDevices = new ArrayList<>();
    private Dialog mDeviceProfileConfigDialog = null;
    private View mPairedPopupView = null;
    private RadioGroup pairedRadioGroup = null;
    private IFragmentCallback fragmentCallback = null;
    private AdapterView.OnItemClickListener mUnpairedClickListener = new AdapterView.OnItemClickListener() { // from class: com.autochips.bluetooth.fragment.PairedFragmentEx.2
        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            if (PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.getPairedStatus()) {
                return;
            }
            PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.setPairedStatus(true);
            String str = (String) PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.getItem(i).get(PairedFragmentEx.REMOTE_DEVICE_MACADDR);
            PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.setSelect(str);
            PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.notifyDataSetChanged();
            PairedFragmentEx.this.mBluetoothPairedDevicesAdapter.setSelect(SkinID.SKIN_NONE);
            PairedFragmentEx.this.mBluetoothPairedDevicesAdapter.notifyDataSetChanged();
            Log.d(PairedFragmentEx.TAG, "connectDevice = " + str);
            PairedFragmentEx.this.mAdapterManager.connectDevice(str);
        }
    };
    private AdapterView.OnItemClickListener mPairedClickListener = new AdapterView.OnItemClickListener() { // from class: com.autochips.bluetooth.fragment.PairedFragmentEx.3
        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.setSelect(SkinID.SKIN_NONE);
            PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.notifyDataSetChanged();
            PairedFragmentEx.this.mBluetoothPairedDevicesAdapter.setSelect((String) PairedFragmentEx.this.mBluetoothPairedDevicesAdapter.getItem(i).get(PairedFragmentEx.REMOTE_DEVICE_MACADDR));
            PairedFragmentEx.this.mBluetoothPairedDevicesAdapter.notifyDataSetChanged();
            PairedFragmentEx.this.showPairedOperationDialog();
        }
    };
    private ConnectionListener mAdapterListener = new ConnectionListener() { // from class: com.autochips.bluetooth.fragment.PairedFragmentEx.5
        @Override // com.hcn.bluetooth.api.ConnectionListener
        public void onServiceConnected() {
            Log.d(PairedFragmentEx.TAG, "onServiceConnected");
            PairedFragmentEx.this.mAdapterManager.registerCallback(PairedFragmentEx.this.mIAdapterCallback);
            PairedFragmentEx.this.mPairedHandler.removeMessages(6);
            PairedFragmentEx.this.mPairedHandler.sendEmptyMessage(6);
        }

        @Override // com.hcn.bluetooth.api.ConnectionListener
        public void onServiceDisconnected() {
            Log.d(PairedFragmentEx.TAG, "onServiceDisconnected");
        }
    };
    private IAdapterCallback mIAdapterCallback = new IAdapterCallback.Stub() { // from class: com.autochips.bluetooth.fragment.PairedFragmentEx.6
        @Override // com.hcn.bluetooth.service.IAdapterCallback
        public void onBluetoothStateChanged(int i) {
            Message messageObtain = Message.obtain(PairedFragmentEx.this.mPairedHandler, 0);
            messageObtain.arg1 = i;
            PairedFragmentEx.this.mPairedHandler.sendMessage(messageObtain);
        }

        @Override // com.hcn.bluetooth.service.IAdapterCallback
        public void onDiscoveryStateChanged(int i) {
            Message messageObtain = Message.obtain(PairedFragmentEx.this.mPairedHandler, 1);
            messageObtain.arg1 = i;
            PairedFragmentEx.this.mPairedHandler.sendMessage(messageObtain);
        }

        @Override // com.hcn.bluetooth.service.IAdapterCallback
        public void onDiscoveryDeviceFound(BluetoothDeviceInfo bluetoothDeviceInfo) {
            Message messageObtain = Message.obtain(PairedFragmentEx.this.mPairedHandler, 2);
            messageObtain.obj = bluetoothDeviceInfo;
            PairedFragmentEx.this.mPairedHandler.sendMessage(messageObtain);
        }

        @Override // com.hcn.bluetooth.service.IAdapterCallback
        public void onDiscoveryDeviceNameChanged(BluetoothDeviceInfo bluetoothDeviceInfo) {
            Message messageObtain = Message.obtain(PairedFragmentEx.this.mPairedHandler, 3);
            messageObtain.obj = bluetoothDeviceInfo;
            PairedFragmentEx.this.mPairedHandler.sendMessage(messageObtain);
        }

        @Override // com.hcn.bluetooth.service.IAdapterCallback
        public void onDeviceBondStateChanged(BluetoothDeviceInfo bluetoothDeviceInfo, int i) {
            Message messageObtain = Message.obtain(PairedFragmentEx.this.mPairedHandler, 4);
            messageObtain.obj = bluetoothDeviceInfo;
            PairedFragmentEx.this.mPairedHandler.sendMessage(messageObtain);
        }

        @Override // com.hcn.bluetooth.service.IAdapterCallback
        public void onConnectionStateChanged(BluetoothDeviceInfo bluetoothDeviceInfo, int i) {
            Message messageObtain = Message.obtain(PairedFragmentEx.this.mPairedHandler, 5);
            messageObtain.arg1 = i;
            messageObtain.obj = bluetoothDeviceInfo;
            PairedFragmentEx.this.mPairedHandler.sendMessage(messageObtain);
        }
    };

    @Override // com.hcn.skin.support.observe.SkinLayoutObserver
    public int getLayoutRes() {
        return C0414R.layout.bt_pairedhistory;
    }

    @Override // com.hcn.skin.support.observe.SkinLayoutObserver
    public void onBindViewData() {
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mPairedHandler = new PairedHandler(Looper.getMainLooper());
        this.mAdapterManager.registerCallback(this.mIAdapterCallback);
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d(TAG, "onCreateView");
        this.root = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mContext = SkinUtils.getContext();
        initPairedDevicesAdapter();
        initUnpairedDevicesAdapter();
        initView();
        buildDeviceProfileConfigDialog();
        return this.root;
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
        LocalBluetoothAdapterManager adapterManager = MyApplication.getInstance().getAdapterManager();
        this.mAdapterManager = adapterManager;
        adapterManager.addConnectListener(this.mAdapterListener);
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        this.mPairedHandler.removeMessages(6);
        this.mPairedHandler.sendEmptyMessage(6);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        this.mAdapterManager.unregisterCallback(this.mIAdapterCallback);
        this.mPairedHandler.removeCallbacksAndMessages(null);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        Log.d(TAG, "onDetach");
        super.onDetach();
        this.mAdapterManager.removeConnectListener(this.mAdapterListener);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (1 == this.mAdapterManager.getThirdPartAPPMode()) {
            Utils.showToast(this.mContext, SkinUtils.getString(C0414R.string.str_zlink_mode));
        }
        int viewId = SkinUtils.getViewId(view);
        if (viewId == C0414R.id.btn_config_profile) {
            this.mConfigDeviceMap = (ArrayMap) view.getTag();
            showDeviceProfileConfigDialog();
            return;
        }
        if (viewId == C0414R.id.btn_scan_bt) {
            if (this.mAdapterManager.isDiscovering()) {
                this.mAdapterManager.stopDiscovery();
                return;
            } else {
                this.mAdapterManager.startDiscovery();
                return;
            }
        }
        switch (viewId) {
            case C0414R.id.bt_paired_connect /* 2131230804 */:
                onClickConnect();
                hidePairedOperationDialog();
                break;
            case C0414R.id.bt_paired_delete /* 2131230805 */:
                onClickUnpair();
                hidePairedOperationDialog();
                break;
            case C0414R.id.bt_paired_disconnect /* 2131230806 */:
                onClickDisconnect();
                hidePairedOperationDialog();
                break;
        }
    }

    public void setFragmentCallback(IFragmentCallback iFragmentCallback) {
        this.fragmentCallback = iFragmentCallback;
    }

    public void showPairedOperationDialog() {
        if (this.popupWindow == null) {
            this.mPairedPopupView = LayoutInflater.from(this.mContext).inflate(SkinUtils.getId(C0414R.layout.pair_list_operation_dialog), (ViewGroup) null);
            this.popupWindow = new PopupWindow(this.mPairedPopupView, -2, -2);
            this.pairedRadioGroup = (RadioGroup) this.mPairedPopupView.findViewById(SkinUtils.getId(C0414R.id.bt_paired_operation_radio_group));
            View viewFindViewById = this.mPairedPopupView.findViewById(SkinUtils.getId(C0414R.id.bt_paired_disconnect));
            View viewFindViewById2 = this.mPairedPopupView.findViewById(SkinUtils.getId(C0414R.id.bt_paired_connect));
            View viewFindViewById3 = this.mPairedPopupView.findViewById(SkinUtils.getId(C0414R.id.bt_paired_delete));
            if (viewFindViewById3 != null) {
                viewFindViewById3.setOnClickListener(this);
            }
            if (viewFindViewById2 != null) {
                viewFindViewById2.setOnClickListener(this);
            }
            if (viewFindViewById != null) {
                viewFindViewById.setOnClickListener(this);
            }
        }
        Log.e(TAG, "PairedFragment2=" + hashCode());
        IFragmentCallback iFragmentCallback = this.fragmentCallback;
        if (iFragmentCallback != null) {
            iFragmentCallback.updateBackground(true);
        } else {
            Log.e(TAG, "fragmentCallback is null");
        }
        RadioGroup radioGroup = this.pairedRadioGroup;
        if (radioGroup != null) {
            radioGroup.clearCheck();
        }
        this.popupWindow.setOutsideTouchable(true);
        this.popupWindow.setFocusable(true);
        this.popupWindow.setAnimationStyle(C0414R.style.PopupAnimation);
        this.popupWindow.showAtLocation(this.root, 17, 0, 30);
        this.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.autochips.bluetooth.fragment.PairedFragmentEx.1
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                if (PairedFragmentEx.this.fragmentCallback != null) {
                    PairedFragmentEx.this.fragmentCallback.updateBackground(false);
                }
            }
        });
    }

    public void hidePairedOperationDialog() {
        PopupWindow popupWindow = this.popupWindow;
        if (popupWindow != null) {
            popupWindow.dismiss();
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

    private void initView() {
        this.scanLayout = (LinearLayout) this.root.findViewById(SkinUtils.getId(C0414R.id.scan_device_layout));
        this.mDeviceName = (TextView) this.root.findViewById(SkinUtils.getId(C0414R.id.bt_device_connected));
        this.mConnectState = (TextView) this.root.findViewById(SkinUtils.getId(C0414R.id.bt_device));
        View viewFindViewById = this.root.findViewById(SkinUtils.getId(C0414R.id.btn_scan_bt));
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(this);
        }
    }

    private void initUnpairedDevicesAdapter() {
        this.mBluetoothUnpairedDevicesAdapter = new PairedListAdapter(this.mContext, this.mBluetoothUnpairedDevices, SkinUtils.getId(C0414R.layout.device_listitem), new String[]{REMOTE_DEVICE_NAME, REMOTE_CONNECT_STATE, REMOTE_DEVICE_MACADDR}, new int[]{SkinUtils.getId(C0414R.id.item_remote_device_name), SkinUtils.getId(C0414R.id.item_remote_connect_status), SkinUtils.getId(C0414R.id.item_remote_device_macaddr)});
        ListView listView = (ListView) this.root.findViewById(SkinUtils.getId(C0414R.id.bluetooth_usable_devices));
        if (listView != null) {
            listView.setAdapter((ListAdapter) this.mBluetoothUnpairedDevicesAdapter);
            listView.setOnItemClickListener(this.mUnpairedClickListener);
            listView.setEnabled(true);
        }
    }

    private void initPairedDevicesAdapter() {
        this.mBluetoothPairedDevicesAdapter = new PairedListAdapter(this.mContext, this.mBluetoothPairedDevices, SkinUtils.getId(C0414R.layout.device_listitem), new String[]{REMOTE_DEVICE_NAME, REMOTE_CONNECT_STATE, REMOTE_DEVICE_MACADDR}, new int[]{SkinUtils.getId(C0414R.id.item_remote_device_name), SkinUtils.getId(C0414R.id.item_remote_connect_status), SkinUtils.getId(C0414R.id.item_remote_device_macaddr)});
        ListView listView = (ListView) this.root.findViewById(SkinUtils.getId(C0414R.id.bluetooth_paired_devices));
        if (listView != null) {
            listView.setAdapter((ListAdapter) this.mBluetoothPairedDevicesAdapter);
            listView.setOnItemClickListener(this.mPairedClickListener);
            listView.setEnabled(true);
        }
    }

    private void buildDeviceProfileConfigDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme.DeviceDefault.Light.Dialog.Alert);
        builder.setTitle(SkinUtils.getString(C0414R.string.bt_device_info));
        CheckBox checkBox = new CheckBox(getContext());
        this.hfpSwitchBtn = checkBox;
        checkBox.setText(SkinUtils.getString(C0414R.string.str_hfp_label));
        this.hfpSwitchBtn.setTextColor(getResources().getColor(R.color.black, null));
        CheckBox checkBox2 = new CheckBox(getContext());
        this.a2dpSwitchBtn = checkBox2;
        checkBox2.setText(SkinUtils.getString(C0414R.string.str_a2dp_label));
        this.a2dpSwitchBtn.setTextColor(getResources().getColor(R.color.black, null));
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(24, 12, 24, 0);
        linearLayout.addView(this.hfpSwitchBtn, layoutParams);
        linearLayout.addView(this.a2dpSwitchBtn, layoutParams);
        builder.setView(linearLayout);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.autochips.bluetooth.fragment.PairedFragmentEx.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                PairedFragmentEx.this.onConfigDeviceProfile();
                dialogInterface.dismiss();
            }
        });
        this.mDeviceProfileConfigDialog = builder.create();
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

    private class PairedHandler extends Handler {
        public PairedHandler(Looper looper) {
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
                    Log.d(PairedFragmentEx.TAG, "MSG_BLUETOOTH_STATE_CHANGE " + message.arg1);
                    removeMessages(6);
                    sendEmptyMessageDelayed(6, 100L);
                    break;
                case 1:
                    if (message.arg1 == 1) {
                        Log.d(PairedFragmentEx.TAG, "MSG_DISCOVERY_STATE_CHANGE start");
                        PairedFragmentEx.this.onActionDiscoveryStarted();
                        PairedFragmentEx.this.updateUnpairedDevicesList();
                    } else {
                        Log.d(PairedFragmentEx.TAG, "MSG_DISCOVERY_STATE_CHANGE end");
                        PairedFragmentEx.this.updateUnpairedDevicesList();
                        PairedFragmentEx.this.onActionDiscoveryFinished();
                    }
                    break;
                case 2:
                    PairedFragmentEx.this.updateUnpairedDevicesList();
                    break;
                case 3:
                    PairedFragmentEx.this.updateUnpairedDevicesList();
                    break;
                case 4:
                    BluetoothDeviceInfo bluetoothDeviceInfo = (BluetoothDeviceInfo) message.obj;
                    Log.d(PairedFragmentEx.TAG, "MSG_BOND_STATE_CHANGE name=" + bluetoothDeviceInfo.getDeviceName() + " status=" + bluetoothDeviceInfo.getDeviceStatus());
                    if (bluetoothDeviceInfo.getDeviceStatus() == 12) {
                        PairedFragmentEx.this.mBluetoothPairedDevicesAdapter.setSelect(bluetoothDeviceInfo.getDeviceAddr());
                        PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.setSelect(SkinID.SKIN_NONE);
                        PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.setPairedStatus(false);
                    } else if (bluetoothDeviceInfo.getDeviceStatus() == 10) {
                        PairedFragmentEx.this.mBluetoothPairedDevicesAdapter.setSelect(SkinID.SKIN_NONE);
                        PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.setSelect(bluetoothDeviceInfo.getDeviceAddr());
                        PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.setPairedStatus(false);
                    } else if (bluetoothDeviceInfo.getDeviceStatus() == 11) {
                        PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.setSelect(bluetoothDeviceInfo.getDeviceAddr());
                        PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.setPairedStatus(true);
                    }
                    PairedFragmentEx.this.updateBondedDevicesList();
                    PairedFragmentEx.this.updateUnpairedDevicesList();
                    break;
                case 5:
                    Log.d(PairedFragmentEx.TAG, "MSG_CONNECT_STATE_CHANGE");
                    BluetoothDeviceInfo bluetoothDeviceInfo2 = (BluetoothDeviceInfo) message.obj;
                    int i2 = message.arg1;
                    String deviceName = bluetoothDeviceInfo2.getDeviceName();
                    if (deviceName == null) {
                        deviceName = SkinID.SKIN_NONE;
                    }
                    if (i2 == 1) {
                        id = SkinUtils.getId(C0414R.string.connecting);
                    } else if (i2 != 2) {
                        if (i2 != 3) {
                            PairedFragmentEx.this.strMacAdder = SkinID.SKIN_NONE;
                            id = SkinUtils.getId(C0414R.string.str_dicconnected);
                            PairedFragmentEx.this.mBluetoothPairedDevicesAdapter.setSelect(SkinID.SKIN_NONE);
                            PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.setSelect(SkinID.SKIN_NONE);
                            PairedFragmentEx.this.updateBondedDevicesList();
                            PairedFragmentEx.this.updateUnpairedDevicesList();
                        } else {
                            id = SkinUtils.getId(C0414R.string.disconnect);
                        }
                        PairedFragmentEx.this.updateConnectState(id, str);
                    } else {
                        id = SkinUtils.getId(C0414R.string.connect);
                        PairedFragmentEx.this.mBluetoothPairedDevicesAdapter.setSelect(bluetoothDeviceInfo2.getDeviceAddr());
                        PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.setSelect(SkinID.SKIN_NONE);
                        PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.setPairedStatus(false);
                        PairedFragmentEx.this.strMacAdder = bluetoothDeviceInfo2.getDeviceAddr();
                        PairedFragmentEx.this.updateBondedDevicesList();
                        PairedFragmentEx.this.updateUnpairedDevicesList();
                    }
                    str = deviceName;
                    PairedFragmentEx.this.updateConnectState(id, str);
                    break;
                case 6:
                    if (PairedFragmentEx.this.mAdapterManager != null) {
                        BluetoothDeviceInfo connectDevice = PairedFragmentEx.this.mAdapterManager.getConnectDevice();
                        if (connectDevice == null) {
                            PairedFragmentEx.this.strMacAdder = SkinID.SKIN_NONE;
                            PairedFragmentEx.this.updateConnectState(SkinUtils.getId(C0414R.string.str_dicconnected), SkinID.SKIN_NONE);
                        } else {
                            PairedFragmentEx.this.strMacAdder = connectDevice.getDeviceAddr();
                            PairedFragmentEx.this.mBluetoothPairedDevicesAdapter.setSelect(connectDevice.getDeviceAddr());
                            PairedFragmentEx.this.mBluetoothUnpairedDevicesAdapter.setSelect(SkinID.SKIN_NONE);
                            PairedFragmentEx.this.updateConnectState(SkinUtils.getId(C0414R.string.connect), connectDevice.getDeviceName());
                        }
                    }
                    PairedFragmentEx.this.updateUnpairedDevicesList();
                    PairedFragmentEx.this.updateBondedDevicesList();
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateConnectState(int i, String str) {
        TextView textView = this.mConnectState;
        if (textView != null) {
            textView.setText(SkinExCompatResources.getInstance().getString(Skin2.xAppId(i)));
        }
        TextView textView2 = this.mDeviceName;
        if (textView2 != null) {
            textView2.setText(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUnpairedDevicesList() {
        if (this.mAdapterManager != null) {
            this.mBluetoothUnpairedDevices.clear();
            List<BluetoothDeviceInfo> deviceList = this.mAdapterManager.getDeviceList();
            if (deviceList != null) {
                Log.d(TAG, "updateUnpairedDevicesList: UnpairedDevices size=" + deviceList.size());
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

    class PairedListAdapter extends SimpleAdapter {
        List<ArrayMap<String, Object>> mDeviceList;
        private String mSelectMacAddress;
        private boolean pairedStatus;
        private String str_hfp;
        private String str_hfp_connected;

        public PairedListAdapter(Context context, List<ArrayMap<String, Object>> list, int i, String[] strArr, int[] iArr) {
            super(context, list, i, strArr, iArr);
            this.mSelectMacAddress = SkinID.SKIN_NONE;
            this.str_hfp = null;
            this.str_hfp_connected = null;
            this.mSelectMacAddress = SkinID.SKIN_NONE;
            this.mDeviceList = list;
            this.str_hfp = SkinUtils.getString(C0414R.string.str_hfp_label);
            this.str_hfp_connected = SkinUtils.getString(C0414R.string.bt_status_connected);
        }

        public void setSelect(String str) {
            this.mSelectMacAddress = str;
        }

        public String getSelect() {
            return this.mSelectMacAddress;
        }

        public void setPairedStatus(boolean z) {
            this.pairedStatus = z;
        }

        public boolean getPairedStatus() {
            return this.pairedStatus;
        }

        @Override // android.widget.SimpleAdapter, android.widget.Adapter
        public ArrayMap<String, Object> getItem(int i) {
            return this.mDeviceList.get(i);
        }

        @Override // android.widget.SimpleAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            String str;
            String str2;
            if (view == null) {
                view = SkinUtils.getLayout(C0414R.layout.device_listitem);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            if (i < this.mDeviceList.size()) {
                ArrayMap<String, Object> arrayMap = this.mDeviceList.get(i);
                str = (String) arrayMap.get(PairedFragmentEx.REMOTE_DEVICE_NAME);
                int iIntValue = ((Integer) arrayMap.get(PairedFragmentEx.REMOTE_CONNECT_STATE)).intValue();
                str2 = (String) arrayMap.get(PairedFragmentEx.REMOTE_DEVICE_MACADDR);
                if (viewHolder.profileConfigImageButton != null) {
                    viewHolder.profileConfigImageButton.setTag(arrayMap);
                    if (iIntValue == -1 || iIntValue != 12) {
                        viewHolder.profileConfigImageButton.setVisibility(4);
                    } else {
                        viewHolder.profileConfigImageButton.setVisibility(0);
                    }
                }
            } else {
                str = SkinID.SKIN_NONE;
                str2 = str;
            }
            if (viewHolder.profileConfigImageButton != null) {
                viewHolder.profileConfigImageButton.setOnClickListener(PairedFragmentEx.this);
            }
            if (viewHolder.nameTextView != null) {
                viewHolder.nameTextView.setText(str);
            }
            if (PairedFragmentEx.this.mAdapterManager != null) {
                if (str2 != null && str2.equals(PairedFragmentEx.this.strMacAdder)) {
                    if (viewHolder.statusTextView != null) {
                        viewHolder.statusTextView.setText(SkinUtils.getString(C0414R.string.bt_status_connected));
                        viewHolder.statusTextView.setTextColor(SkinUtils.getColor(C0414R.color.color_paired_p));
                    }
                    if (viewHolder.nameTextView != null) {
                        viewHolder.nameTextView.setTextColor(SkinUtils.getColor(C0414R.color.color_paired_p));
                    }
                } else {
                    if (viewHolder.statusTextView != null) {
                        viewHolder.statusTextView.setText(SkinID.SKIN_NONE);
                        viewHolder.statusTextView.setTextColor(SkinUtils.getColor(C0414R.color.color_paired_n));
                    }
                    if (viewHolder.nameTextView != null) {
                        viewHolder.nameTextView.setTextColor(SkinUtils.getColor(C0414R.color.color_paired_n));
                    }
                }
            }
            if (viewHolder.progressBar != null) {
                if (this.mSelectMacAddress.length() > 0 && this.mSelectMacAddress.equals(str2) && this.pairedStatus) {
                    viewHolder.progressBar.setVisibility(0);
                } else {
                    viewHolder.progressBar.setVisibility(4);
                }
            }
            return view;
        }

        public final class ViewHolder {
            public View convertView;
            public TextView nameTextView;
            public Button profileConfigImageButton;
            public ProgressBar progressBar;
            public TextView statusTextView;

            public ViewHolder(View view) {
                this.convertView = view;
                if (view != null) {
                    this.nameTextView = (TextView) view.findViewById(SkinUtils.getId(C0414R.id.item_remote_device_name));
                    this.statusTextView = (TextView) this.convertView.findViewById(SkinUtils.getId(C0414R.id.item_remote_connect_status));
                    this.profileConfigImageButton = (Button) this.convertView.findViewById(SkinUtils.getId(C0414R.id.btn_config_profile));
                    this.progressBar = (ProgressBar) this.convertView.findViewById(SkinUtils.getId(C0414R.id.bt_pair_device_progressbar));
                }
            }
        }
    }
}
