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
import android.os.PowerManager;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.autochips.bluetooth.C0414R;
import com.autochips.bluetooth.MyApplication;
import com.autochips.bluetooth.skin.SkinID;
import com.autochips.bluetooth.skin.SkinUtils;
import com.hcn.bluetooth.api.BluetoothDeviceInfo;
import com.hcn.bluetooth.api.ConnectionListener;
import com.hcn.bluetooth.api.LocalBluetoothAdapterManager;
import com.hcn.bluetooth.api.Utils;
import com.hcn.skin.support.app.SkinCompatFragment;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class SetupFragment extends SkinCompatFragment implements View.OnClickListener {
    private static final int MAX_PIN_CODE_LENGTH = 16;
    private static final int MIN_PIN_CODE_LENGTH = 4;
    public static final String TAG = "SetupFragment";
    private ViewGroup mLayoutNetwork;
    private ViewGroup mLayoutSetting;
    private View mViewRoot;
    private Dialog mEditBTNameDialog = null;
    private EditText mEditDeviceName = null;
    private Dialog mEditBTPinDialog = null;
    private EditText mEditPin = null;
    private LinearLayout layout_pin = null;
    private Button mAutoConnectButton = null;
    private Button mAutoAnswerButton = null;
    private Button mPowerSwitchButton = null;
    private Button mTipsButton = null;
    private Button mNetWorkButton = null;
    private Button mTipsCloseButton = null;
    private View mA2dpSourceSwitch = null;
    public String mPinCode = "0000";
    public String mDevName = "CarBT";
    private boolean mIsAutoAnswer = false;
    private boolean mIsAutoConnect = false;
    private LocalBluetoothAdapterManager mLocalAdapterManager = null;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.autochips.bluetooth.fragment.SetupFragment.6
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                if (intExtra == 10) {
                    Log.d(SetupFragment.TAG, "onReceive: ACTION_STATE_CHANGED STATE_OFF");
                    SetupFragment.this.mPowerSwitchButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
                } else if (intExtra == 12) {
                    Log.d(SetupFragment.TAG, "onReceive: ACTION_STATE_CHANGED STATE_ON");
                    SetupFragment.this.mPowerSwitchButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
                }
            }
        }
    };
    ConnectionListener mConnectionListener = new ConnectionListener() { // from class: com.autochips.bluetooth.fragment.SetupFragment.7
        @Override // com.hcn.bluetooth.api.ConnectionListener
        public void onServiceDisconnected() {
        }

        @Override // com.hcn.bluetooth.api.ConnectionListener
        public void onServiceConnected() {
            TextView textView = (TextView) SetupFragment.this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.tv_edit_name));
            if (SetupFragment.this.mLocalAdapterManager != null) {
                SetupFragment setupFragment = SetupFragment.this;
                setupFragment.mDevName = setupFragment.mLocalAdapterManager.getBTName();
            }
            if (textView != null) {
                textView.setText(SetupFragment.this.mDevName);
            }
            SetupFragment.this.checkBluetoothState();
        }
    };

    @Override // com.hcn.skin.support.observe.SkinLayoutObserver
    public int getLayoutRes() {
        return C0414R.layout.layout_bt_setting;
    }

    @Override // com.hcn.skin.support.observe.SkinLayoutObserver
    public void onBindViewData() {
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mLocalAdapterManager = MyApplication.getInstance().getAdapterManager();
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (SkinUtils.useSkinPackage()) {
            if (SkinUtils.isResourcesExist(C0414R.layout.layout_bt_setting) > 0) {
                this.mViewRoot = SkinUtils.getLayout(C0414R.layout.layout_bt_setting);
            } else {
                Log.e(TAG, "onCreateView: no layout_bt_setting in skin apk!");
            }
            this.mViewRoot = SkinUtils.getLayout("layout_zx_bt_setting");
        } else {
            this.mViewRoot = layoutInflater.inflate(C0414R.layout.layout_bt_setting, viewGroup, false);
        }
        initView();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        ((Context) Objects.requireNonNull(getContext())).registerReceiver(this.mReceiver, intentFilter);
        return this.mViewRoot;
    }

    private void initView() {
        LocalBluetoothAdapterManager localBluetoothAdapterManager;
        TextView textView = (TextView) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.tv_edit_name));
        TextView textView2 = (TextView) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.tv_edit_pin));
        Button button = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_edit_name_bt));
        Button button2 = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_edit_pin_bt));
        this.mAutoConnectButton = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.switch_autoconnect));
        this.mAutoAnswerButton = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.switch_autoanswer));
        this.mPowerSwitchButton = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.switch_power));
        this.mNetWorkButton = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.network));
        this.mTipsButton = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.network_tips));
        this.mTipsCloseButton = (Button) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.tips_close));
        this.mLayoutNetwork = (ViewGroup) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.network_layout));
        this.mLayoutSetting = (ViewGroup) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.setting_layout));
        if (textView == null || textView2 == null || button == null || button2 == null || this.mAutoConnectButton == null || this.mAutoAnswerButton == null || this.mPowerSwitchButton == null) {
            Log.e(TAG, "Widget missing!");
            return;
        }
        this.mNetWorkButton.setOnClickListener(this);
        this.mTipsButton.setOnClickListener(this);
        this.mTipsCloseButton.setOnClickListener(this);
        this.mPowerSwitchButton.setOnClickListener(new View.OnClickListener() { // from class: com.autochips.bluetooth.fragment.SetupFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SetupFragment.this.mLocalAdapterManager != null) {
                    BluetoothDeviceInfo connectDevice = SetupFragment.this.mLocalAdapterManager.getConnectDevice();
                    if (connectDevice != null) {
                        SetupFragment.this.mLocalAdapterManager.disconnectDevice(connectDevice.getDeviceAddr());
                    }
                    SetupFragment.this.mLocalAdapterManager.resetBT();
                }
            }
        });
        if (!this.mLocalAdapterManager.isReady() && (localBluetoothAdapterManager = this.mLocalAdapterManager) != null) {
            localBluetoothAdapterManager.addConnectListener(this.mConnectionListener);
        }
        LocalBluetoothAdapterManager localBluetoothAdapterManager2 = this.mLocalAdapterManager;
        if (localBluetoothAdapterManager2 != null) {
            this.mDevName = localBluetoothAdapterManager2.getBTName();
        }
        LocalBluetoothAdapterManager localBluetoothAdapterManager3 = this.mLocalAdapterManager;
        if (localBluetoothAdapterManager3 != null) {
            this.mPinCode = localBluetoothAdapterManager3.getBTPincode();
        }
        textView.setText(this.mDevName);
        textView2.setText(this.mPinCode);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        this.mAutoConnectButton.setOnClickListener(this);
        this.mAutoAnswerButton.setOnClickListener(this);
        this.layout_pin = (LinearLayout) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.layout_pin));
        String systemProperty = Utils.getSystemProperty("persist.sys.bt.disablessp", "false");
        if (this.layout_pin != null && "true".equals(systemProperty)) {
            this.layout_pin.setVisibility(0);
        }
        if (MyApplication.isHeadrest()) {
            View viewFindViewById = this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.layout_a2dp_source));
            if (viewFindViewById != null) {
                viewFindViewById.setVisibility(0);
            }
            View viewFindViewById2 = this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.switch_a2dp_source));
            this.mA2dpSourceSwitch = viewFindViewById2;
            if (viewFindViewById2 != null) {
                viewFindViewById2.setOnClickListener(this);
            }
        }
        buildEditNameDialog();
        buildEditPinDialog();
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
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
        getContext().unregisterReceiver(this.mReceiver);
        LocalBluetoothAdapterManager localBluetoothAdapterManager = this.mLocalAdapterManager;
        if (localBluetoothAdapterManager != null) {
            localBluetoothAdapterManager.removeConnectListener(this.mConnectionListener);
        }
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
            case C0414R.id.btn_edit_name_bt /* 2131230844 */:
                Log.d(TAG, "onClick: 2222222");
                Dialog dialog = this.mEditBTNameDialog;
                if (dialog != null && !dialog.isShowing()) {
                    LocalBluetoothAdapterManager localBluetoothAdapterManager = this.mLocalAdapterManager;
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
                Dialog dialog2 = this.mEditBTPinDialog;
                if (dialog2 != null && !dialog2.isShowing()) {
                    LocalBluetoothAdapterManager localBluetoothAdapterManager2 = this.mLocalAdapterManager;
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
            case C0414R.id.switch_a2dp_source /* 2131231031 */:
                boolean z2 = !MyApplication.getInstance().isA2dpSourceRole();
                if (z2) {
                    this.mA2dpSourceSwitch.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
                } else {
                    this.mA2dpSourceSwitch.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
                }
                MyApplication.getInstance().setA2dpSourceRole(z2);
                showRebootDialog();
                break;
            case C0414R.id.switch_autoanswer /* 2131231032 */:
                boolean z3 = !this.mLocalAdapterManager.isBluetoothAutoAnswer();
                this.mIsAutoAnswer = z3;
                if (z3) {
                    this.mAutoAnswerButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
                } else {
                    this.mAutoAnswerButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
                }
                this.mLocalAdapterManager.setBluetoothAutoAnswer(this.mIsAutoAnswer);
                break;
            case C0414R.id.switch_autoconnect /* 2131231033 */:
                boolean z4 = !this.mLocalAdapterManager.isBluetoothAutoConnect();
                this.mIsAutoConnect = z4;
                if (z4) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public void checkBluetoothState() {
        if (this.mLocalAdapterManager.isBluetoothEnable()) {
            this.mPowerSwitchButton.setEnabled(true);
            this.mPowerSwitchButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
        } else {
            this.mPowerSwitchButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
        }
        if (this.mLocalAdapterManager.isBluetoothAutoAnswer()) {
            this.mAutoAnswerButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
        } else {
            this.mAutoAnswerButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
        }
        if (this.mLocalAdapterManager.isBluetoothAutoConnect()) {
            this.mAutoConnectButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
        } else {
            this.mAutoConnectButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
        }
        if (this.mLocalAdapterManager.isAutoConnectedNetwork()) {
            this.mNetWorkButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
        } else {
            this.mNetWorkButton.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
        }
        if (MyApplication.getInstance().isA2dpSourceRole()) {
            View view = this.mA2dpSourceSwitch;
            if (view != null) {
                view.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_on));
                return;
            }
            return;
        }
        View view2 = this.mA2dpSourceSwitch;
        if (view2 != null) {
            view2.setBackground(SkinUtils.getDrawable(C0414R.drawable.set_tumbler_off));
        }
    }

    public static String getEThemeGod() {
        return Utils.getSystemProperty("persist.sys.etheme_god", SkinID.SKIN_NONE);
    }

    public static String getEThemeSub() {
        return Utils.getSystemProperty("persist.sys.etheme_sub", SkinID.SKIN_NONE);
    }

    private void buildEditNameDialog() {
        if (getContext() == null) {
            return;
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        Log.d(TAG, "buildEditNameDialog: " + i + i2);
        if (("400".equals(getEThemeGod()) && "034".equals(getEThemeSub())) || ("400".equals(getEThemeGod()) && i == 1280 && i2 == 480)) {
            View viewInflate = LayoutInflater.from(getContext()).inflate(C0414R.layout.bt_name_change_dialog, (ViewGroup) null);
            this.mEditDeviceName = (EditText) viewInflate.findViewById(C0414R.id.edit_bt_name);
            Button button = (Button) viewInflate.findViewById(C0414R.id.bt_name_confirm);
            Button button2 = (Button) viewInflate.findViewById(C0414R.id.bt_name_cancel);
            if (button2 != null) {
                button2.setText(SkinUtils.getString(C0414R.string.str_edit_cancel));
            }
            if (button != null) {
                button.setText(SkinUtils.getString(C0414R.string.str_edit_ok));
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(viewInflate);
            AlertDialog alertDialogCreate = builder.create();
            this.mEditBTNameDialog = alertDialogCreate;
            final Window window = alertDialogCreate.getWindow();
            this.mEditDeviceName.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.autochips.bluetooth.fragment.SetupFragment$$ExternalSyntheticLambda2
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view, boolean z) {
                    SetupFragment.lambda$buildEditNameDialog$0(window, view, z);
                }
            });
            button.setOnClickListener(new View.OnClickListener() { // from class: com.autochips.bluetooth.fragment.SetupFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m42xdef4ca59(view);
                }
            });
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.autochips.bluetooth.fragment.SetupFragment$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m43x18bf6c38(view);
                }
            });
            return;
        }
        AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext(), R.style.Theme.DeviceDefault.Light.Dialog.Alert);
        builder2.setTitle(SkinUtils.getString(C0414R.string.bt_device_info));
        builder2.setMessage(SkinUtils.getString(C0414R.string.bt_device_name_title));
        EditText editText = new EditText(getContext());
        this.mEditDeviceName = editText;
        editText.setSingleLine(true);
        this.mEditDeviceName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(248)});
        this.mEditDeviceName.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(24, 0, 24, 0);
        linearLayout.addView(this.mEditDeviceName, layoutParams);
        builder2.setView(linearLayout);
        builder2.setNegativeButton(SkinUtils.getString(C0414R.string.str_edit_cancel), (DialogInterface.OnClickListener) null);
        builder2.setPositiveButton(SkinUtils.getString(C0414R.string.str_edit_ok), new DialogInterface.OnClickListener() { // from class: com.autochips.bluetooth.fragment.SetupFragment.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i3) {
                String strTrim = SetupFragment.this.mEditDeviceName.getText().toString().trim();
                if (strTrim.length() > 20) {
                    strTrim = strTrim.substring(0, 19);
                }
                if (!strTrim.isEmpty()) {
                    if (SetupFragment.this.mLocalAdapterManager != null) {
                        SetupFragment.this.mLocalAdapterManager.setBTName(strTrim);
                    }
                    TextView textView = (TextView) SetupFragment.this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.tv_edit_name));
                    if (textView != null) {
                        textView.setText(strTrim);
                    }
                    dialogInterface.dismiss();
                    return;
                }
                Utils.showToast(SetupFragment.this.getContext(), SkinUtils.getString(C0414R.string.bluetooth_device_info_toast));
            }
        });
        this.mEditBTNameDialog = builder2.create();
    }

    static /* synthetic */ void lambda$buildEditNameDialog$0(Window window, View view, boolean z) {
        if (z) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity = 48;
            attributes.y = 20;
            attributes.verticalMargin = 0.0f;
            window.setAttributes(attributes);
            window.setSoftInputMode(53);
        }
    }

    /* JADX INFO: renamed from: lambda$buildEditNameDialog$1$com-autochips-bluetooth-fragment-SetupFragment */
    public /* synthetic */ void m42xdef4ca59(View view) {
        String strTrim = this.mEditDeviceName.getText().toString().trim();
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
            this.mEditBTNameDialog.dismiss();
            return;
        }
        Utils.showToast(getContext(), SkinUtils.getString(C0414R.string.bluetooth_device_info_toast));
    }

    /* JADX INFO: renamed from: lambda$buildEditNameDialog$2$com-autochips-bluetooth-fragment-SetupFragment */
    public /* synthetic */ void m43x18bf6c38(View view) {
        this.mEditBTNameDialog.dismiss();
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
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.autochips.bluetooth.fragment.SetupFragment.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                String strTrim = SetupFragment.this.mEditPin.getText().toString().trim();
                if (strTrim.length() >= 4 && strTrim.length() <= 16) {
                    TextView textView = (TextView) SetupFragment.this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.tv_edit_pin));
                    if (textView != null) {
                        textView.setText(strTrim);
                    }
                    SetupFragment.this.mPinCode = strTrim;
                    SetupFragment.this.mEditBTPinDialog.dismiss();
                    if (SetupFragment.this.mLocalAdapterManager != null) {
                        SetupFragment.this.mLocalAdapterManager.setBTPincode(SetupFragment.this.mPinCode);
                        return;
                    }
                    return;
                }
                Utils.showToast(SetupFragment.this.getContext(), SkinUtils.getString(C0414R.string.bluetooth_device_info_toast));
            }
        });
        this.mEditBTPinDialog = builder.create();
    }

    private void showRebootDialog() {
        if (getContext() == null) {
            return;
        }
        new AlertDialog.Builder(getContext()).setIcon(R.drawable.ic_dialog_alert).setTitle(SkinUtils.getString(C0414R.string.headrest_a2dp_source)).setMessage(SkinUtils.getString(C0414R.string.headrest_reboot_tip)).setCancelable(false).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.autochips.bluetooth.fragment.SetupFragment.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.autochips.bluetooth.fragment.SetupFragment.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                PowerManager powerManager = (PowerManager) SetupFragment.this.getContext().getSystemService("power");
                if (powerManager != null) {
                    powerManager.reboot(SkinID.SKIN_NONE);
                }
            }
        }).create().show();
    }
}
