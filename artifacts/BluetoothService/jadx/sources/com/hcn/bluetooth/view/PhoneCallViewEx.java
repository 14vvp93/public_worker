package com.hcn.bluetooth.view;

import android.bluetooth.BluetoothHeadsetClientCall;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hcn.bluetooth.api.Utils;
import com.hcn.bluetooth.bean.CallInfo;
import com.hcn.bluetooth.service.BluetoothHfpclientService;
import com.hcn.bluetoothservice.R;
import com.hcn.skin2.Skin2;
import com.hcn.skinx_night.NightX;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PhoneCallViewEx extends PhoneCallViewBase implements View.OnClickListener {
    public static final int HALFOPACITY = 100;
    public static final int MAX_CALL_SIZE = 2;
    public static final int OPACITY = 255;
    private static final String TAG = "PhoneCallViewFull";
    private Button acceptHeldButton;
    private Button answerButton;
    TextView callStatusView;
    TextView callingNameView;
    TextView callingTimeView;
    private Button hangupButton;
    private Button heldButton;
    private LinearLayout heldLayout;
    private TextView heldNumView;
    private TextView heldPhoneView;
    private TextView heldStateView;
    private Button keypadButton;
    private Button mCallAddBtn;
    private BluetoothHfpclientService mHFPService;
    private Button mKeyBroadHangup;
    private Button mKeyBroadHide;
    private Map<String, String> mMapData;
    private Button mMicMuteBtn;
    private String mPhone;
    private LinearLayout softkeyPadLayout;
    private EditText subcallNumber;
    private Button swapButton;
    private Button voiceSourceCarButton;
    private Button voiceSourcePhoneButton;

    @Override // com.hcn.bluetooth.view.PhoneCallViewBase
    protected int getLayoutId() {
        return R.layout.bt_calling_status;
    }

    public PhoneCallViewEx(Context context, WindowManager windowManager, AudioManager audioManager) {
        super(context, windowManager, audioManager);
        this.mMapData = new HashMap();
        this.mLayoutParams.type = Skin2.getInteger(R.integer.call_view_window_type);
        this.mLayoutParams.gravity = Skin2.getInteger(R.integer.call_view_window_gravity);
        this.mLayoutParams.width = Skin2.getInteger(R.integer.call_view_window_width);
        this.mLayoutParams.height = Skin2.getInteger(R.integer.call_view_window_height);
        this.mLayoutParams.flags |= 256;
        this.mHFPService = BluetoothHfpclientService.getInstance();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow");
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow");
        LinearLayout linearLayout = this.softkeyPadLayout;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        EditText editText = this.subcallNumber;
        if (editText != null) {
            editText.setText("");
        }
        Map<String, String> map = this.mMapData;
        if (map != null) {
            map.clear();
        }
    }

    @Override // com.hcn.bluetooth.view.PhoneCallViewBase
    public void updateCallTime(Map<String, CallInfo> map) {
        String str;
        if (map.isEmpty() || TextUtils.isEmpty(this.mPhone)) {
            return;
        }
        int callTime = (TextUtils.isEmpty(this.mPhone) || !map.containsKey(this.mPhone)) ? 0 : map.get(this.mPhone).getCallTime();
        Log.d(TAG, " callTime = " + callTime);
        if (callTime > 3600) {
            str = String.format(Locale.ENGLISH, "%02d:%02d:%02d", Integer.valueOf((callTime / 3600) % 60), Integer.valueOf((callTime / 60) % 60), Integer.valueOf(callTime % 60));
        } else {
            str = String.format(Locale.ENGLISH, "%02d:%02d", Integer.valueOf((callTime / 60) % 60), Integer.valueOf(callTime % 60));
        }
        Log.d(TAG, "updateCallTime: time=" + str + " , mPhone = " + this.mPhone);
        TextView textView = this.callingTimeView;
        if (textView != null) {
            textView.setText(str);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = Skin2.getId(view);
        Log.d(TAG, "(onClick)-》" + id);
        switch (id) {
            case R.id.btn_accept_held /* 2131230798 */:
            case R.id.btn_held /* 2131230818 */:
            case R.id.btn_swap /* 2131230821 */:
            case R.id.call_held_layout /* 2131230825 */:
                this.mHFPService.acceptCall(1);
                break;
            case R.id.btn_add /* 2131230800 */:
                addCall();
                BluetoothHfpclientService bluetoothHfpclientService = this.mHFPService;
                if (bluetoothHfpclientService != null) {
                    bluetoothHfpclientService.switchCallViewByManual(1);
                }
                break;
            case R.id.btn_answer /* 2131230801 */:
                this.mHFPService.acceptCall(0);
                break;
            case R.id.btn_calling_asterisk /* 2131230802 */:
                addSubPhoneCallInputString("*");
                this.mHFPService.sendDTMF((byte) 42);
                break;
            case R.id.btn_calling_eight /* 2131230803 */:
                addSubPhoneCallInputString("8");
                this.mHFPService.sendDTMF((byte) 56);
                break;
            case R.id.btn_calling_five /* 2131230804 */:
                addSubPhoneCallInputString("5");
                this.mHFPService.sendDTMF((byte) 53);
                break;
            case R.id.btn_calling_four /* 2131230805 */:
                addSubPhoneCallInputString("4");
                this.mHFPService.sendDTMF((byte) 52);
                break;
            case R.id.btn_calling_hidkeybroad /* 2131230806 */:
            case R.id.btn_softkeypad /* 2131230820 */:
                onClickSoftkeypad();
                break;
            case R.id.btn_calling_hungup /* 2131230807 */:
            case R.id.btn_hangup /* 2131230817 */:
                this.mHFPService.hangup();
                List<BluetoothHeadsetClientCall> currentCalls = this.mHFPService.getCurrentCalls();
                if (currentCalls == null || currentCalls.size() == 0) {
                    Log.e(TAG, "btn_hangup no call!");
                    hideView();
                }
                if (id == R.id.btn_calling_hungup) {
                    onClickSoftkeypad();
                }
                break;
            case R.id.btn_calling_nine /* 2131230808 */:
                addSubPhoneCallInputString("9");
                this.mHFPService.sendDTMF((byte) 57);
                break;
            case R.id.btn_calling_one /* 2131230809 */:
                addSubPhoneCallInputString("1");
                this.mHFPService.sendDTMF((byte) 49);
                break;
            case R.id.btn_calling_pound /* 2131230810 */:
                addSubPhoneCallInputString(NightX.DARK_TAG_SEGMENTS_SEPARATOR);
                this.mHFPService.sendDTMF((byte) 35);
                break;
            case R.id.btn_calling_seven /* 2131230811 */:
                addSubPhoneCallInputString("7");
                this.mHFPService.sendDTMF((byte) 55);
                break;
            case R.id.btn_calling_six /* 2131230812 */:
                addSubPhoneCallInputString("6");
                this.mHFPService.sendDTMF((byte) 54);
                break;
            case R.id.btn_calling_three /* 2131230813 */:
                addSubPhoneCallInputString("3");
                this.mHFPService.sendDTMF((byte) 51);
                break;
            case R.id.btn_calling_two /* 2131230814 */:
                addSubPhoneCallInputString("2");
                this.mHFPService.sendDTMF((byte) 50);
                break;
            case R.id.btn_calling_zero /* 2131230815 */:
                addSubPhoneCallInputString("0");
                this.mHFPService.sendDTMF((byte) 48);
                break;
            case R.id.btn_disable_mic /* 2131230816 */:
                onClickMicSwitch();
                break;
            case R.id.btn_voiceswitch_car /* 2131230822 */:
            case R.id.btn_voiceswitch_phone /* 2131230823 */:
                this.mHFPService.switchAudio();
                break;
        }
    }

    @Override // com.hcn.bluetooth.view.PhoneCallViewBase
    protected void initView() {
        this.callStatusView = (TextView) findViewById(Skin2.xId(R.id.bt_calling_status_tv));
        this.callingNameView = (TextView) findViewById(Skin2.xId(R.id.bt_calling_phone_name));
        this.callingTimeView = (TextView) findViewById(Skin2.xId(R.id.bt_calling_time_text));
        this.heldLayout = (LinearLayout) findViewById(Skin2.xId(R.id.call_held_layout));
        this.heldNumView = (TextView) findViewById(Skin2.xId(R.id.bt_call_held_number));
        this.heldPhoneView = (TextView) findViewById(Skin2.xId(R.id.bt_call_held_phone));
        this.heldStateView = (TextView) findViewById(Skin2.xId(R.id.bt_call_held_state_text));
        this.voiceSourceCarButton = (Button) findViewById(Skin2.xId(R.id.btn_voiceswitch_car));
        this.voiceSourcePhoneButton = (Button) findViewById(Skin2.xId(R.id.btn_voiceswitch_phone));
        this.answerButton = (Button) findViewById(Skin2.xId(R.id.btn_answer));
        this.hangupButton = (Button) findViewById(Skin2.xId(R.id.btn_hangup));
        this.heldButton = (Button) findViewById(Skin2.xId(R.id.btn_held));
        this.acceptHeldButton = (Button) findViewById(Skin2.xId(R.id.btn_accept_held));
        this.swapButton = (Button) findViewById(Skin2.xId(R.id.btn_swap));
        this.mMicMuteBtn = (Button) findViewById(Skin2.xId(R.id.btn_disable_mic));
        this.mCallAddBtn = (Button) findViewById(Skin2.xId(R.id.btn_add));
        this.mKeyBroadHangup = (Button) findViewById(Skin2.xId(R.id.btn_calling_hungup));
        this.mKeyBroadHide = (Button) findViewById(Skin2.xId(R.id.btn_calling_hidkeybroad));
        this.softkeyPadLayout = (LinearLayout) findViewById(Skin2.xId(R.id.calling_softkeypad));
        this.keypadButton = (Button) findViewById(Skin2.xId(R.id.btn_softkeypad));
        this.subcallNumber = (EditText) findViewById(Skin2.xId(R.id.calling_input_et));
        try {
            this.answerButton.setOnClickListener(this);
            this.voiceSourceCarButton.setOnClickListener(this);
            this.voiceSourcePhoneButton.setOnClickListener(this);
            this.hangupButton.setOnClickListener(this);
            this.acceptHeldButton.setOnClickListener(this);
            this.heldButton.setOnClickListener(this);
            this.swapButton.setOnClickListener(this);
            this.mMicMuteBtn.setOnClickListener(this);
            this.mCallAddBtn.setOnClickListener(this);
            this.mKeyBroadHangup.setOnClickListener(this);
            this.mKeyBroadHide.setOnClickListener(this);
            this.heldLayout.setOnClickListener(this);
            this.keypadButton.setOnClickListener(this);
            findViewById(Skin2.xId(R.id.btn_calling_zero)).setOnClickListener(this);
            findViewById(Skin2.xId(R.id.btn_calling_one)).setOnClickListener(this);
            findViewById(Skin2.xId(R.id.btn_calling_two)).setOnClickListener(this);
            findViewById(Skin2.xId(R.id.btn_calling_three)).setOnClickListener(this);
            findViewById(Skin2.xId(R.id.btn_calling_four)).setOnClickListener(this);
            findViewById(Skin2.xId(R.id.btn_calling_five)).setOnClickListener(this);
            findViewById(Skin2.xId(R.id.btn_calling_six)).setOnClickListener(this);
            findViewById(Skin2.xId(R.id.btn_calling_seven)).setOnClickListener(this);
            findViewById(Skin2.xId(R.id.btn_calling_eight)).setOnClickListener(this);
            findViewById(Skin2.xId(R.id.btn_calling_nine)).setOnClickListener(this);
            findViewById(Skin2.xId(R.id.btn_calling_asterisk)).setOnClickListener(this);
            findViewById(Skin2.xId(R.id.btn_calling_pound)).setOnClickListener(this);
            this.subcallNumber.setInputType(0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override // com.hcn.bluetooth.view.PhoneCallViewBase
    public void onActionCallStateChanged(List<BluetoothHeadsetClientCall> list) {
        if (list == null || list.size() == 0) {
            Log.e(TAG, "onActionCallStateChanged no call!");
        } else if (list.size() == 1) {
            onSingleCall(list.get(0));
        } else {
            onMultiCall(list);
        }
    }

    private String getCallName(String str, String str2) {
        Map<String, String> map = this.mMapData;
        if (map != null) {
            String str3 = map.get(str);
            if (!TextUtils.isEmpty(str3)) {
                return str3;
            }
            Log.d(TAG, "getCallName: getContactNameByNumber" + str);
            String contactNameByNumber = Utils.getContactNameByNumber(this.mContext, str, str2);
            if (TextUtils.isEmpty(contactNameByNumber)) {
                contactNameByNumber = str;
            }
            this.mMapData.put(str, contactNameByNumber);
        }
        return str;
    }

    private void onSingleCall(BluetoothHeadsetClientCall bluetoothHeadsetClientCall) {
        TextView textView;
        Button button;
        Log.d(TAG, "onSingleCall");
        if (bluetoothHeadsetClientCall == null) {
            return;
        }
        int state = bluetoothHeadsetClientCall.getState();
        Log.d(TAG, "callState: " + state);
        if ((state == 4 || state == 2 || state == 3) && (textView = this.callingTimeView) != null && textView.getVisibility() == 0) {
            this.callingTimeView.setVisibility(8);
        }
        TextView textView2 = this.callStatusView;
        if (textView2 != null) {
            if (state == 0) {
                textView2.setText(Skin2.getString(R.string.str_bt_call_active));
                TextView textView3 = this.callingTimeView;
                if (textView3 != null && textView3.getVisibility() != 0) {
                    this.callingTimeView.setVisibility(0);
                }
            } else if (state == 1) {
                textView2.setText(Skin2.getString(R.string.str_bt_call_held));
            } else if (state == 2 || state == 3) {
                textView2.setText(Skin2.getString(R.string.str_out_call_status));
            } else if (state == 4) {
                textView2.setText(Skin2.getString(R.string.str_income_call_status));
                Button button2 = this.answerButton;
                if (button2 != null) {
                    button2.setVisibility(0);
                }
            } else if (state == 7) {
                this.mPhone = "";
                this.subcallNumber.setText("");
                return;
            }
        }
        if (state != 4 && (button = this.answerButton) != null && button.getVisibility() == 0) {
            this.answerButton.setVisibility(8);
        }
        String number = bluetoothHeadsetClientCall.getNumber();
        this.mPhone = number;
        String callName = getCallName(number, bluetoothHeadsetClientCall.getDevice().getAddress());
        Log.d(TAG, "callNumber(" + number + "), callName(" + callName + ")");
        TextView textView4 = this.callingNameView;
        if (textView4 != null) {
            if (textView4.getVisibility() != 0) {
                this.callingNameView.setVisibility(0);
            }
            this.callingNameView.setText(callName);
        }
        LinearLayout linearLayout = this.heldLayout;
        if (linearLayout != null && linearLayout.getVisibility() == 0) {
            this.heldLayout.setVisibility(8);
        }
        Button button3 = this.acceptHeldButton;
        if (button3 != null && button3.getVisibility() == 0) {
            this.acceptHeldButton.setVisibility(8);
        }
        Button button4 = this.swapButton;
        if (button4 != null && button4.getVisibility() == 0) {
            this.swapButton.setVisibility(8);
        }
        if (state != 0 && state != 1) {
            Button button5 = this.keypadButton;
            if (button5 != null) {
                button5.setVisibility(8);
            }
            Button button6 = this.mMicMuteBtn;
            if (button6 != null) {
                button6.setVisibility(8);
            }
            Button button7 = this.mCallAddBtn;
            if (button7 != null) {
                button7.setVisibility(8);
            }
            Button button8 = this.heldButton;
            if (button8 != null) {
                button8.setVisibility(8);
            }
            Button button9 = this.voiceSourcePhoneButton;
            if (button9 != null) {
                button9.setVisibility(8);
            }
            Button button10 = this.voiceSourceCarButton;
            if (button10 != null) {
                button10.setVisibility(8);
            }
        } else {
            Button button11 = this.keypadButton;
            if (button11 != null) {
                button11.setVisibility(0);
            }
            Button button12 = this.mMicMuteBtn;
            if (button12 != null) {
                button12.setVisibility(0);
            }
            Button button13 = this.mCallAddBtn;
            if (button13 != null) {
                button13.setVisibility(0);
                this.mCallAddBtn.setEnabled(true);
            }
            Button button14 = this.heldButton;
            if (button14 != null) {
                button14.setVisibility(0);
            }
        }
        updateMicSource(this.mAudioManager.isMicrophoneMute());
        BluetoothHfpclientService bluetoothHfpclientService = this.mHFPService;
        if (bluetoothHfpclientService != null) {
            updateAudioState(bluetoothHfpclientService.getAudioState());
        }
    }

    private void onMultiCall(List<BluetoothHeadsetClientCall> list) {
        String address;
        BluetoothHeadsetClientCall bluetoothHeadsetClientCall;
        if (list == null) {
            return;
        }
        Log.d(TAG, "onMultiCall");
        Button button = this.heldButton;
        if (button != null && button.getVisibility() == 0) {
            this.heldButton.setVisibility(8);
        }
        Button button2 = this.answerButton;
        if (button2 != null && button2.getVisibility() == 0) {
            this.answerButton.setVisibility(8);
        }
        LinearLayout linearLayout = this.heldLayout;
        if (linearLayout != null && linearLayout.getVisibility() != 0) {
            this.heldLayout.setVisibility(0);
        }
        List<BluetoothHeadsetClientCall> call = BluetoothHfpclientService.getCall(list, 5, 4);
        List<BluetoothHeadsetClientCall> call2 = BluetoothHfpclientService.getCall(list, 1);
        List<BluetoothHeadsetClientCall> call3 = BluetoothHfpclientService.getCall(list, 2, 3);
        List<BluetoothHeadsetClientCall> call4 = BluetoothHfpclientService.getCall(list, 0);
        String number = "";
        if (!call.isEmpty()) {
            number = call.get(0).getNumber();
            address = call.get(0).getDevice().getAddress();
        } else if (!call3.isEmpty()) {
            number = call3.get(0).getNumber();
            address = call3.get(0).getDevice().getAddress();
        } else if (!call4.isEmpty()) {
            number = call4.get(0).getNumber();
            address = call4.get(0).getDevice().getAddress();
            this.mPhone = number;
        } else if (call2.size() > 1) {
            number = call2.get(0).getNumber();
            address = call2.get(0).getDevice().getAddress();
        } else {
            address = "";
        }
        String callName = getCallName(number, address);
        TextView textView = this.callingNameView;
        if (textView != null) {
            textView.setVisibility(0);
            this.callingNameView.setText(callName);
        }
        if (!call.isEmpty()) {
            LinearLayout linearLayout2 = this.heldLayout;
            if (linearLayout2 != null) {
                linearLayout2.setEnabled(false);
            }
            if (this.callStatusView != null) {
                TextView textView2 = this.callingTimeView;
                if (textView2 != null) {
                    textView2.setVisibility(8);
                }
                this.callStatusView.setText(Skin2.getString(R.string.str_bt_call_waiting));
            }
            LinearLayout linearLayout3 = this.softkeyPadLayout;
            if (linearLayout3 != null && linearLayout3.getVisibility() == 0) {
                this.softkeyPadLayout.setVisibility(8);
            }
            Button button3 = this.keypadButton;
            if (button3 != null) {
                button3.setVisibility(8);
            }
            Button button4 = this.mMicMuteBtn;
            if (button4 != null) {
                button4.setVisibility(8);
            }
            Button button5 = this.mCallAddBtn;
            if (button5 != null) {
                button5.setVisibility(8);
            }
            Button button6 = this.voiceSourceCarButton;
            if (button6 != null) {
                button6.setVisibility(8);
            }
            Button button7 = this.voiceSourcePhoneButton;
            if (button7 != null) {
                button7.setVisibility(8);
            }
            Button button8 = this.acceptHeldButton;
            if (button8 != null) {
                button8.getBackground().setAlpha(255);
                if (list.size() > 2) {
                    this.acceptHeldButton.getBackground().setAlpha(100);
                }
                this.acceptHeldButton.setVisibility(0);
            }
            Button button9 = this.swapButton;
            if (button9 == null || button9.getVisibility() != 0) {
                return;
            }
            this.swapButton.setVisibility(8);
            return;
        }
        Button button10 = this.acceptHeldButton;
        if (button10 != null && button10.getVisibility() == 0) {
            this.acceptHeldButton.setVisibility(8);
        }
        if (!call3.isEmpty()) {
            this.heldLayout.setEnabled(false);
            Button button11 = this.mMicMuteBtn;
            if (button11 != null) {
                button11.setVisibility(8);
            }
            Button button12 = this.mCallAddBtn;
            if (button12 != null) {
                button12.setVisibility(8);
            }
            TextView textView3 = this.callingTimeView;
            if (textView3 != null) {
                textView3.setVisibility(8);
            }
            Button button13 = this.keypadButton;
            if (button13 != null) {
                button13.setVisibility(8);
            }
            Button button14 = this.voiceSourceCarButton;
            if (button14 != null) {
                button14.setVisibility(8);
            }
            TextView textView4 = this.callStatusView;
            if (textView4 != null) {
                textView4.setText(Skin2.getString(R.string.str_out_call_status));
            }
            Button button15 = this.swapButton;
            if (button15 != null && button15.getVisibility() == 0) {
                this.swapButton.setVisibility(8);
            }
        } else {
            this.heldLayout.setEnabled(true);
            Button button16 = this.mMicMuteBtn;
            if (button16 != null) {
                button16.setVisibility(0);
            }
            Button button17 = this.mCallAddBtn;
            if (button17 != null) {
                button17.setVisibility(0);
                this.mCallAddBtn.setEnabled(false);
            }
            TextView textView5 = this.callingTimeView;
            if (textView5 != null) {
                textView5.setVisibility(0);
            }
            Button button18 = this.keypadButton;
            if (button18 != null) {
                button18.setVisibility(0);
            }
            Button button19 = this.voiceSourceCarButton;
            if (button19 != null) {
                button19.setVisibility(0);
            }
            Button button20 = this.swapButton;
            if (button20 != null) {
                button20.setVisibility(0);
            }
        }
        BluetoothHfpclientService bluetoothHfpclientService = this.mHFPService;
        if (bluetoothHfpclientService != null) {
            updateAudioState(bluetoothHfpclientService.getAudioState());
        }
        if (!call2.isEmpty() || ((!call3.isEmpty() || !call.isEmpty()) && !call4.isEmpty())) {
            if (!call2.isEmpty()) {
                bluetoothHeadsetClientCall = call2.get(0);
            } else {
                bluetoothHeadsetClientCall = call4.get(0);
            }
            TextView textView6 = this.heldStateView;
            if (textView6 != null) {
                textView6.setText(Skin2.getString(R.string.str_bt_call_held));
            }
            String number2 = bluetoothHeadsetClientCall.getNumber();
            String callName2 = getCallName(number2, bluetoothHeadsetClientCall.getDevice().getAddress());
            if (this.heldNumView != null) {
                TextView textView7 = this.heldPhoneView;
                if (textView7 != null) {
                    textView7.setText(number2);
                }
                this.heldNumView.setText(callName2);
                return;
            }
            return;
        }
        Log.d(TAG, "isMultiParty");
        if ((!call4.isEmpty() && call4.get(0).isMultiParty()) || (!call2.isEmpty() && call2.get(0).isMultiParty())) {
            TextView textView8 = this.callStatusView;
            if (textView8 != null && textView8.getVisibility() == 8) {
                this.callStatusView.setText(Skin2.getString(R.string.str_bt_call_multiparty));
            }
        } else {
            TextView textView9 = this.callStatusView;
            if (textView9 != null && textView9.getVisibility() == 8) {
                this.callStatusView.setText(Skin2.getString(R.string.str_bt_call_abnormal));
            }
        }
        LinearLayout linearLayout4 = this.heldLayout;
        if (linearLayout4 != null) {
            linearLayout4.setVisibility(8);
        }
        Button button21 = this.swapButton;
        if (button21 == null || button21.getVisibility() != 0) {
            return;
        }
        this.swapButton.setVisibility(8);
    }

    private void onClickSoftkeypad() {
        LinearLayout linearLayout = this.softkeyPadLayout;
        if (linearLayout == null) {
            Log.d(TAG, "get calling_softkeypad fail!");
        } else if (linearLayout.getVisibility() == 0) {
            this.softkeyPadLayout.setVisibility(8);
        } else {
            this.softkeyPadLayout.setVisibility(0);
        }
    }

    private void onClickMicSwitch() {
        Log.i(TAG, "on click mic switch!");
        if (this.mAudioManager.isMicrophoneMute()) {
            this.mAudioManager.setMicrophoneMute(false);
            updateMicSource(false);
        } else {
            this.mAudioManager.setMicrophoneMute(true);
            updateMicSource(true);
        }
    }

    private void addSubPhoneCallInputString(CharSequence charSequence) {
        EditText editText = this.subcallNumber;
        if (editText == null) {
            Log.d(TAG, "get calling_input_et fail!");
            return;
        }
        int selectionStart = editText.getSelectionStart();
        Editable editableText = this.subcallNumber.getEditableText();
        if (editableText == null) {
            Log.d(TAG, "get subcallNumstr_Edt error!");
            return;
        }
        if (selectionStart < 0 || selectionStart > editableText.length()) {
            editableText.append(charSequence);
        } else {
            editableText.insert(selectionStart, charSequence);
        }
        this.subcallNumber.setText(editableText);
        this.subcallNumber.setSelection(selectionStart + 1);
    }

    private void updateMicSource(boolean z) {
        Button button = this.mMicMuteBtn;
        if (button == null) {
            return;
        }
        if (z) {
            button.setBackground(Skin2.getDrawable2(R.drawable.skpad_mic_disable));
        } else {
            button.setBackground(Skin2.getDrawable2(R.drawable.skpad_mic_enable));
        }
    }

    @Override // com.hcn.bluetooth.view.PhoneCallViewBase
    public void updateAudioState(int i) {
        Button button;
        Log.d(TAG, "updateAudioState state=" + i);
        List<BluetoothHeadsetClientCall> currentCalls = this.mHFPService.getCurrentCalls();
        if (currentCalls == null || currentCalls.size() == 0) {
            Log.e(TAG, "updateAudioState no call!");
            return;
        }
        if (!BluetoothHfpclientService.getCall(currentCalls, 4, 5).isEmpty()) {
            Log.d(TAG, "incoming or waiting not show audio state!!!");
            return;
        }
        if (currentCalls.get(0).getState() != 0 && currentCalls.get(0).getState() != 1) {
            Log.d(TAG, "507 only in active or held state can enter!!!");
            this.voiceSourceCarButton.setVisibility(8);
            this.voiceSourcePhoneButton.setVisibility(8);
            return;
        }
        if (currentCalls.size() == 1 && currentCalls.get(0).getState() == 1) {
            Log.d(TAG, "only one call and state is held not show audio state!!!");
            return;
        }
        Button button2 = this.answerButton;
        if (button2 != null && button2.getVisibility() == 0) {
            this.answerButton.setVisibility(8);
        }
        Button button3 = this.voiceSourceCarButton;
        if (button3 == null || (button = this.voiceSourcePhoneButton) == null) {
            Log.e(TAG, "updateAudioState:audio buttion is null!!!");
            return;
        }
        if (i == 2) {
            button.setVisibility(8);
            this.voiceSourceCarButton.setVisibility(0);
        } else if (i == 0) {
            button3.setVisibility(8);
            this.voiceSourcePhoneButton.setVisibility(0);
        }
    }

    private void addCall() {
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setComponent(new ComponentName("com.autochips.bluetooth", "com.autochips.bluetooth.MainBluetoothActivity"));
            intent.putExtra("extra_page_index", 2);
            intent.setFlags(268435456);
            this.mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
