package com.autochips.bluetooth.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.autochips.bluetooth.C0414R;
import com.autochips.bluetooth.IFragmentCallback;
import com.autochips.bluetooth.MyApplication;
import com.autochips.bluetooth.skin.SkinUtils;
import com.hcn.bluetooth.api.BluetoothDeviceInfo;
import com.hcn.bluetooth.api.LocalBluetoothAdapterManager;
import com.hcn.bluetooth.api.Utils;
import com.hcn.skin.support.app.SkinCompatFragment;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class SetupFragmentEx extends SkinCompatFragment implements View.OnClickListener {
    private static final int MAX_PIN_CODE_LENGTH = 16;
    private static final int MIN_PIN_CODE_LENGTH = 4;
    public static final String TAG = "SetupFragmentEx";
    private Context mContext;
    private ViewGroup mLayoutNetwork;
    private ViewGroup mLayoutSetting;
    private View mViewRoot;
    private LocalBluetoothAdapterManager mLocalAdapterManager = null;
    private Button mAutoConnectButton = null;
    private Button mAutoAnswerButton = null;
    private Button mPowerSwitchButton = null;
    private Button mTipsButton = null;
    private Button mNetWorkButton = null;
    private Button mTipsCloseButton = null;
    private LinearLayout layoutPinCode = null;
    private PopupWindow mEditBTNameDialog = null;
    private EditText mEditDeviceName = null;
    private PopupWindow mEditBTPinDialog = null;
    private EditText mEditPin = null;
    public String mPinCode = "0000";
    public String mDevName = "CarBT";
    private boolean mIsAutoAnswer = false;
    private boolean mIsAutoConnect = false;
    private IFragmentCallback fragmentCallback = null;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.autochips.bluetooth.fragment.SetupFragmentEx.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                if (intExtra == 10) {
                    Log.d(SetupFragmentEx.TAG, "onReceive: ACTION_STATE_CHANGED STATE_OFF");
                    if (SetupFragmentEx.this.mPowerSwitchButton != null) {
                        SetupFragmentEx.this.mPowerSwitchButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
                        return;
                    }
                    return;
                }
                if (intExtra == 12) {
                    Log.d(SetupFragmentEx.TAG, "onReceive: ACTION_STATE_CHANGED STATE_ON");
                    if (SetupFragmentEx.this.mPowerSwitchButton != null) {
                        SetupFragmentEx.this.mPowerSwitchButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
                    }
                }
            }
        }
    };

    @Override // com.hcn.skin.support.observe.SkinLayoutObserver
    public int getLayoutRes() {
        return C0414R.layout.layout_bt_setting;
    }

    @Override // com.hcn.skin.support.observe.SkinLayoutObserver
    public void onBindViewData() {
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mLocalAdapterManager = MyApplication.getInstance().getAdapterManager();
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mViewRoot = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mContext = SkinUtils.getContext();
        initView();
        registerReceiver();
        return this.mViewRoot;
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        ((Context) Objects.requireNonNull(getContext())).registerReceiver(this.mReceiver, intentFilter);
    }

    private void initView() {
        LocalBluetoothAdapterManager localBluetoothAdapterManager = this.mLocalAdapterManager;
        if (localBluetoothAdapterManager != null) {
            this.mDevName = localBluetoothAdapterManager.getBTName();
        }
        LocalBluetoothAdapterManager localBluetoothAdapterManager2 = this.mLocalAdapterManager;
        if (localBluetoothAdapterManager2 != null) {
            this.mPinCode = localBluetoothAdapterManager2.getBTPincode();
        }
        TextView textView = (TextView) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.tv_edit_name));
        if (textView != null) {
            textView.setText(this.mDevName);
        }
        TextView textView2 = (TextView) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.tv_edit_pin));
        if (textView2 != null) {
            textView2.setText(this.mPinCode);
        }
        Button button = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_edit_name_bt));
        if (button != null) {
            button.setOnClickListener(this);
        }
        Button button2 = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_edit_pin_bt));
        if (button2 != null) {
            button2.setOnClickListener(this);
        }
        Button button3 = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.switch_autoconnect));
        this.mAutoConnectButton = button3;
        if (button3 != null) {
            button3.setOnClickListener(this);
        }
        Button button4 = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.switch_autoanswer));
        this.mAutoAnswerButton = button4;
        if (button4 != null) {
            button4.setOnClickListener(this);
        }
        Button button5 = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.network));
        this.mNetWorkButton = button5;
        if (button5 != null) {
            button5.setOnClickListener(this);
        }
        Button button6 = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.network_tips));
        this.mTipsButton = button6;
        if (button6 != null) {
            button6.setOnClickListener(this);
        }
        Button button7 = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.tips_close));
        this.mTipsCloseButton = button7;
        if (button7 != null) {
            button7.setOnClickListener(this);
        }
        this.mLayoutNetwork = (ViewGroup) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.network_layout));
        this.mLayoutSetting = (ViewGroup) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.setting_layout));
        Button button8 = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.switch_power));
        this.mPowerSwitchButton = button8;
        button8.setOnClickListener(new View.OnClickListener() { // from class: com.autochips.bluetooth.fragment.SetupFragmentEx.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SetupFragmentEx.this.mLocalAdapterManager != null) {
                    BluetoothDeviceInfo connectDevice = SetupFragmentEx.this.mLocalAdapterManager.getConnectDevice();
                    if (connectDevice != null) {
                        SetupFragmentEx.this.mLocalAdapterManager.disconnectDevice(connectDevice.getDeviceAddr());
                    }
                    SetupFragmentEx.this.mLocalAdapterManager.resetBT();
                }
            }
        });
        String systemProperty = Utils.getSystemProperty("persist.sys.bt.disablessp", "false");
        LinearLayout linearLayout = (LinearLayout) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.layout_pin));
        this.layoutPinCode = linearLayout;
        if (linearLayout == null || !"true".equals(systemProperty)) {
            return;
        }
        this.layoutPinCode.setVisibility(0);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        checkBluetoothState();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        ((Context) Objects.requireNonNull(getContext())).unregisterReceiver(this.mReceiver);
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (!this.mLocalAdapterManager.isBluetoothEnable()) {
            Utils.showToast(getContext(), SkinUtils.getString(C0414R.string.bluetooth_please_power_on_toast));
        }
        switch (SkinUtils.getViewId(view)) {
            case C0414R.id.bt_name_cancel /* 2131230801 */:
                hideEditNameDialog();
                break;
            case C0414R.id.bt_name_confirm /* 2131230802 */:
                confirmModifyBTName();
                break;
            case C0414R.id.btn_edit_name_bt /* 2131230844 */:
                showEditNameDialog();
                break;
            case C0414R.id.btn_edit_pin_bt /* 2131230845 */:
                showEditPinDialog();
                break;
            case C0414R.id.network /* 2131230965 */:
                Log.d(TAG, "network click");
                boolean z = !this.mLocalAdapterManager.isAutoConnectedNetwork();
                this.mLocalAdapterManager.setAutoConnectedNetwork(z);
                if (z) {
                    this.mNetWorkButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
                } else {
                    this.mNetWorkButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
                }
                break;
            case C0414R.id.network_tips /* 2131230967 */:
                onActionTipsStarted();
                break;
            case C0414R.id.pin_code_cancel /* 2131230987 */:
                hideEditPinDialog();
                break;
            case C0414R.id.pin_code_confirm /* 2131230988 */:
                confirmModifyPinCode();
                break;
            case C0414R.id.switch_autoanswer /* 2131231032 */:
                boolean z2 = !this.mLocalAdapterManager.isBluetoothAutoAnswer();
                this.mIsAutoAnswer = z2;
                if (z2) {
                    this.mAutoAnswerButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
                } else {
                    this.mAutoAnswerButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
                }
                this.mLocalAdapterManager.setBluetoothAutoAnswer(this.mIsAutoAnswer);
                break;
            case C0414R.id.switch_autoconnect /* 2131231033 */:
                boolean z3 = !this.mLocalAdapterManager.isBluetoothAutoConnect();
                this.mIsAutoConnect = z3;
                if (z3) {
                    this.mAutoConnectButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
                } else {
                    this.mAutoConnectButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
                }
                this.mLocalAdapterManager.setBluetoothAutoConnect(this.mIsAutoConnect);
                break;
            case C0414R.id.tips_close /* 2131231069 */:
                onActionTipsFinished();
                break;
        }
    }

    public void setFragmentCallback(IFragmentCallback iFragmentCallback) {
        this.fragmentCallback = iFragmentCallback;
    }

    private void checkBluetoothState() {
        LocalBluetoothAdapterManager localBluetoothAdapterManager = this.mLocalAdapterManager;
        if (localBluetoothAdapterManager == null) {
            Log.e(TAG, "checkBluetoothState: mLocalAdapterManager is null");
            return;
        }
        if (localBluetoothAdapterManager.isBluetoothEnable()) {
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
        if (this.mLocalAdapterManager.isBluetoothAutoAnswer()) {
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
        if (this.mLocalAdapterManager.isBluetoothAutoConnect()) {
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
        if (this.mLocalAdapterManager.isAutoConnectedNetwork()) {
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

    private void onActionTipsStarted() {
        Log.d(TAG, "onActionTipsStarted");
        ViewGroup viewGroup = this.mLayoutNetwork;
        if (viewGroup != null) {
            viewGroup.setVisibility(0);
        }
        ViewGroup viewGroup2 = this.mLayoutSetting;
        if (viewGroup2 != null) {
            viewGroup2.setVisibility(4);
        }
    }

    private void onActionTipsFinished() {
        Log.d(TAG, "onActionTipsFinished");
        ViewGroup viewGroup = this.mLayoutNetwork;
        if (viewGroup != null) {
            viewGroup.setVisibility(8);
        }
        ViewGroup viewGroup2 = this.mLayoutSetting;
        if (viewGroup2 != null) {
            viewGroup2.setVisibility(0);
        }
    }

    private void showEditNameDialog() {
        if (this.mContext == null) {
            Log.e(TAG, "showEditNameDialog: mContext is null");
            return;
        }
        if (this.mEditBTNameDialog == null) {
            View layout = SkinUtils.getLayout(C0414R.layout.bt_name_change_dialog);
            PopupWindow popupWindow = new PopupWindow(layout, -2, -2);
            this.mEditBTNameDialog = popupWindow;
            popupWindow.setSoftInputMode(1);
            this.mEditBTNameDialog.setSoftInputMode(16);
            this.mEditBTNameDialog.setOutsideTouchable(true);
            this.mEditBTNameDialog.setFocusable(true);
            this.mEditBTNameDialog.setAnimationStyle(C0414R.style.PopupAnimation);
            this.mEditBTNameDialog.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.autochips.bluetooth.fragment.SetupFragmentEx.2
                @Override // android.widget.PopupWindow.OnDismissListener
                public void onDismiss() {
                    if (SetupFragmentEx.this.fragmentCallback != null) {
                        SetupFragmentEx.this.fragmentCallback.updateBackground(false);
                    }
                }
            });
            this.mEditDeviceName = (EditText) layout.findViewById(SkinUtils.getId(C0414R.id.edit_bt_name));
            Button button = (Button) layout.findViewById(SkinUtils.getId(C0414R.id.bt_name_confirm));
            if (button != null) {
                button.setOnClickListener(this);
            }
            Button button2 = (Button) layout.findViewById(SkinUtils.getId(C0414R.id.bt_name_cancel));
            if (button2 != null) {
                button2.setOnClickListener(this);
            }
        }
        LocalBluetoothAdapterManager localBluetoothAdapterManager = this.mLocalAdapterManager;
        if (localBluetoothAdapterManager != null && this.mEditDeviceName != null) {
            String bTName = localBluetoothAdapterManager.getBTName();
            this.mDevName = bTName;
            this.mEditDeviceName.setText(bTName);
        }
        IFragmentCallback iFragmentCallback = this.fragmentCallback;
        if (iFragmentCallback != null) {
            iFragmentCallback.updateBackground(true);
        }
        this.mEditBTNameDialog.showAtLocation(this.mViewRoot, 17, 0, 0);
    }

    private void hideEditNameDialog() {
        PopupWindow popupWindow = this.mEditBTNameDialog;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        IFragmentCallback iFragmentCallback = this.fragmentCallback;
        if (iFragmentCallback != null) {
            iFragmentCallback.updateBackground(false);
        }
    }

    private void confirmModifyBTName() {
        EditText editText = this.mEditDeviceName;
        if (editText != null) {
            String strTrim = editText.getText().toString().trim();
            if (strTrim.length() > 20) {
                strTrim = strTrim.substring(0, 19);
            }
            if (!strTrim.isEmpty()) {
                LocalBluetoothAdapterManager localBluetoothAdapterManager = this.mLocalAdapterManager;
                if (localBluetoothAdapterManager != null) {
                    localBluetoothAdapterManager.setBTName(strTrim);
                }
                TextView textView = (TextView) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.tv_edit_name));
                if (textView != null) {
                    textView.setText(strTrim);
                }
                hideEditNameDialog();
                return;
            }
            Utils.showToast(getContext(), SkinUtils.getString(C0414R.string.bluetooth_device_info_toast));
        }
    }

    private void showEditPinDialog() {
        if (this.mContext == null) {
            Log.e(TAG, "buildEditPinDialog: mContext is null");
            return;
        }
        if (this.mEditBTPinDialog == null) {
            View layout = SkinUtils.getLayout(C0414R.layout.pin_code_change_dialog);
            PopupWindow popupWindow = new PopupWindow(layout, -2, -2);
            this.mEditBTPinDialog = popupWindow;
            popupWindow.setSoftInputMode(1);
            this.mEditBTPinDialog.setSoftInputMode(16);
            this.mEditBTPinDialog.setOutsideTouchable(true);
            this.mEditBTPinDialog.setFocusable(true);
            this.mEditBTPinDialog.setAnimationStyle(C0414R.style.PopupAnimation);
            this.mEditBTPinDialog.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.autochips.bluetooth.fragment.SetupFragmentEx.3
                @Override // android.widget.PopupWindow.OnDismissListener
                public void onDismiss() {
                    if (SetupFragmentEx.this.fragmentCallback != null) {
                        SetupFragmentEx.this.fragmentCallback.updateBackground(false);
                    }
                }
            });
            this.mEditPin = (EditText) layout.findViewById(SkinUtils.getId(C0414R.id.edit_pin_code));
            Button button = (Button) layout.findViewById(SkinUtils.getId(C0414R.id.pin_code_confirm));
            if (button != null) {
                button.setOnClickListener(this);
            }
            Button button2 = (Button) layout.findViewById(SkinUtils.getId(C0414R.id.pin_code_cancel));
            if (button2 != null) {
                button2.setOnClickListener(this);
            }
        }
        LocalBluetoothAdapterManager localBluetoothAdapterManager = this.mLocalAdapterManager;
        if (localBluetoothAdapterManager != null && this.mEditPin != null) {
            String bTPincode = localBluetoothAdapterManager.getBTPincode();
            this.mPinCode = bTPincode;
            this.mEditPin.setText(bTPincode);
        }
        IFragmentCallback iFragmentCallback = this.fragmentCallback;
        if (iFragmentCallback != null) {
            iFragmentCallback.updateBackground(true);
        }
        this.mEditBTPinDialog.showAtLocation(this.mViewRoot, 17, 0, 0);
    }

    private void hideEditPinDialog() {
        PopupWindow popupWindow = this.mEditBTPinDialog;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        IFragmentCallback iFragmentCallback = this.fragmentCallback;
        if (iFragmentCallback != null) {
            iFragmentCallback.updateBackground(false);
        }
    }

    private void confirmModifyPinCode() {
        EditText editText = this.mEditPin;
        if (editText != null) {
            String strTrim = editText.getText().toString().trim();
            if (strTrim.length() >= 4 && strTrim.length() <= 16) {
                TextView textView = (TextView) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.tv_edit_pin));
                if (textView != null) {
                    textView.setText(strTrim);
                }
                this.mPinCode = strTrim;
                hideEditPinDialog();
                LocalBluetoothAdapterManager localBluetoothAdapterManager = this.mLocalAdapterManager;
                if (localBluetoothAdapterManager != null) {
                    localBluetoothAdapterManager.setBTPincode(this.mPinCode);
                    return;
                }
                return;
            }
            Utils.showToast(getContext(), SkinUtils.getString(C0414R.string.bluetooth_device_info_toast));
        }
    }
}
