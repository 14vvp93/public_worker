package com.hcn.bluetooth.view;

import android.app.ActivityManager;
import android.bluetooth.BluetoothHeadsetClientCall;
import android.content.Context;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.hcn.bluetooth.api.Utils;
import com.hcn.bluetooth.bean.CallInfo;
import com.hcn.bluetooth.service.BluetoothHfpclientService;
import com.hcn.bluetoothservice.R;
import com.hcn.skin2.Skin2;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PhoneCallViewMiniEx extends PhoneCallViewBase implements View.OnClickListener {
    public static final int HALFOPACITY = 100;
    public static final int MAX_CALL_SIZE = 2;
    public static final int OPACITY = 255;
    private static final String TAG = "PhoneCallViewMiniEx";
    private Button acceptHeldButton;
    private Button answerButton;
    TextView callStatusView;
    TextView callingNameView;
    TextView callingTimeView;
    private Button hangupButton;
    private View mCallBg;
    private BluetoothHfpclientService mHFPService;
    private Map<String, String> mMapData;
    private String mPhone;
    private Button voiceSourceCarButton;
    private Button voiceSourcePhoneButton;

    @Override // com.hcn.bluetooth.view.PhoneCallViewBase
    protected int getLayoutId() {
        return R.layout.bt_calling_status_mini;
    }

    public PhoneCallViewMiniEx(Context context, WindowManager windowManager, AudioManager audioManager) {
        super(context, windowManager, audioManager);
        this.mMapData = new HashMap();
        this.mLayoutParams.type = Skin2.getInteger(R.integer.call_view_mini_window_type);
        this.mLayoutParams.gravity = Skin2.getInteger(R.integer.call_view_mini_window_gravity);
        this.mLayoutParams.width = Skin2.getInteger(R.integer.call_view_mini_window_width);
        this.mLayoutParams.height = Skin2.getInteger(R.integer.call_view_mini_window_height);
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
        this.mLayoutParams.x = 0;
        this.mLayoutParams.y = 0;
        TextView textView = this.callingTimeView;
        if (textView != null) {
            textView.setVisibility(8);
        }
        Map<String, String> map = this.mMapData;
        if (map != null) {
            map.clear();
        }
    }

    @Override // com.hcn.bluetooth.view.PhoneCallViewBase
    public void showView() {
        try {
            if ("com.autochips.bluetooth.MainBluetoothActivity".equals(((ActivityManager) this.mContext.getSystemService("activity")).getRunningTasks(1).get(0).topActivity.getClassName())) {
                this.mLayoutParams.height = -2;
            } else {
                this.mLayoutParams.height = Skin2.getInteger(R.integer.call_view_mini_window_height);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.showView();
    }

    @Override // com.hcn.bluetooth.view.PhoneCallViewBase
    public void updateCallTime(Map<String, CallInfo> map) {
        String str;
        if (map.isEmpty()) {
            this.callingTimeView.setVisibility(8);
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
        switch (Skin2.getId(view)) {
            case R.id.bt_calling_root /* 2131230794 */:
                Utils.startApp(this.mContext, "com.autochips.bluetooth", "com.autochips.bluetooth.MainBluetoothActivity");
                BluetoothHfpclientService bluetoothHfpclientService = this.mHFPService;
                if (bluetoothHfpclientService != null) {
                    bluetoothHfpclientService.switchCallViewByManual(0);
                }
                break;
            case R.id.btn_accept_held /* 2131230798 */:
            case R.id.btn_held /* 2131230818 */:
            case R.id.btn_swap /* 2131230821 */:
                this.mHFPService.acceptCall(1);
                break;
            case R.id.btn_accept_terminate /* 2131230799 */:
                this.mHFPService.acceptCall(2);
                break;
            case R.id.btn_answer /* 2131230801 */:
            case R.id.btn_merge /* 2131230819 */:
                this.mHFPService.acceptCall(0);
                break;
            case R.id.btn_hangup /* 2131230817 */:
                Log.d(TAG, "btn_hangup");
                this.mHFPService.hangup();
                List<BluetoothHeadsetClientCall> currentCalls = this.mHFPService.getCurrentCalls();
                if (currentCalls == null || currentCalls.size() == 0) {
                    Log.e(TAG, "btn_hangup no call!");
                    hideView();
                }
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
        this.voiceSourceCarButton = (Button) findViewById(Skin2.xId(R.id.btn_voiceswitch_car));
        this.voiceSourcePhoneButton = (Button) findViewById(Skin2.xId(R.id.btn_voiceswitch_phone));
        this.answerButton = (Button) findViewById(Skin2.xId(R.id.btn_answer));
        this.hangupButton = (Button) findViewById(Skin2.xId(R.id.btn_hangup));
        this.acceptHeldButton = (Button) findViewById(Skin2.xId(R.id.btn_accept_held));
        this.mCallBg = findViewById(Skin2.xId(R.id.bt_calling_mini_bg));
        try {
            this.answerButton.setOnClickListener(this);
            this.voiceSourceCarButton.setOnClickListener(this);
            this.voiceSourcePhoneButton.setOnClickListener(this);
            this.hangupButton.setOnClickListener(this);
            this.acceptHeldButton.setOnClickListener(this);
            if (findViewById(Skin2.xId(R.id.bt_calling_root)) != null) {
                findViewById(Skin2.xId(R.id.bt_calling_root)).setOnClickListener(this);
            }
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
        Log.d(TAG, "onSingleCall");
        if (bluetoothHeadsetClientCall == null) {
            return;
        }
        int state = bluetoothHeadsetClientCall.getState();
        Log.d(TAG, "callState: " + state);
        String number = bluetoothHeadsetClientCall.getNumber();
        this.mPhone = number;
        String callName = getCallName(number, bluetoothHeadsetClientCall.getDevice().getAddress());
        Log.d(TAG, "callNumber(" + number + "), callName(" + callName + ")");
        TextView textView = this.callingNameView;
        if (textView != null) {
            textView.setVisibility(0);
            this.callingNameView.setText(callName);
        }
        Button button = this.acceptHeldButton;
        if (button != null && button.getVisibility() == 0) {
            this.acceptHeldButton.setVisibility(8);
        }
        View view = this.mCallBg;
        if (view != null) {
            if (state == 4) {
                view.setBackground(Skin2.getDrawable2(R.drawable.mini_dialog_bg2));
            } else {
                view.setBackground(Skin2.getDrawable2(R.drawable.mini_dialog_bg));
            }
        }
        if (state == 0) {
            TextView textView2 = this.callingTimeView;
            if (textView2 != null) {
                textView2.setVisibility(0);
            }
            TextView textView3 = this.callStatusView;
            if (textView3 != null) {
                textView3.setText(Skin2.getString(R.string.str_bt_call_active));
            }
            Button button2 = this.answerButton;
            if (button2 != null) {
                button2.setVisibility(8);
            }
        } else {
            Button button3 = this.voiceSourceCarButton;
            if (button3 != null) {
                button3.setVisibility(8);
            }
            Button button4 = this.voiceSourcePhoneButton;
            if (button4 != null) {
                button4.setVisibility(8);
            }
            if (state == 1) {
                Button button5 = this.answerButton;
                if (button5 != null) {
                    button5.setVisibility(8);
                }
                TextView textView4 = this.callStatusView;
                if (textView4 != null) {
                    textView4.setText(Skin2.getString(R.string.str_bt_call_held));
                }
            } else if (state == 4) {
                Button button6 = this.answerButton;
                if (button6 != null) {
                    button6.setVisibility(0);
                }
                TextView textView5 = this.callStatusView;
                if (textView5 != null) {
                    textView5.setText(Skin2.getString(R.string.str_income_call_status));
                }
            } else if (state == 2 || state == 3) {
                TextView textView6 = this.callStatusView;
                if (textView6 != null) {
                    textView6.setText(Skin2.getString(R.string.str_out_call_status));
                }
                Button button7 = this.answerButton;
                if (button7 != null && button7.getVisibility() == 0) {
                    this.answerButton.setVisibility(8);
                }
            } else {
                Button button8 = this.answerButton;
                if (button8 != null && button8.getVisibility() == 0) {
                    this.answerButton.setVisibility(8);
                }
            }
        }
        BluetoothHfpclientService bluetoothHfpclientService = this.mHFPService;
        if (bluetoothHfpclientService != null) {
            updateAudioState(bluetoothHfpclientService.getAudioState());
        }
    }

    private void onMultiCall(List<BluetoothHeadsetClientCall> list) {
        String address;
        if (list == null) {
            return;
        }
        Log.d(TAG, "onMultiCall");
        for (int i = 0; i < list.size(); i++) {
            BluetoothHeadsetClientCall bluetoothHeadsetClientCall = list.get(i);
            Log.d(TAG, "onMultiCall: num=" + bluetoothHeadsetClientCall.getNumber() + " state=" + this.mHFPService.callStateToString(bluetoothHeadsetClientCall.getState()));
        }
        Log.d(TAG, "*************************************************************");
        Button button = this.answerButton;
        if (button != null && button.getVisibility() == 0) {
            this.answerButton.setVisibility(8);
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
            TextView textView2 = this.callStatusView;
            if (textView2 != null) {
                textView2.setVisibility(0);
                TextView textView3 = this.callingTimeView;
                if (textView3 != null) {
                    textView3.setVisibility(8);
                }
                this.callStatusView.setText(Skin2.getString(R.string.str_bt_call_waiting));
            }
            Button button2 = this.voiceSourceCarButton;
            if (button2 != null) {
                button2.setVisibility(8);
            }
            Button button3 = this.voiceSourcePhoneButton;
            if (button3 != null) {
                button3.setVisibility(8);
            }
            Button button4 = this.acceptHeldButton;
            if (button4 != null) {
                button4.getBackground().setAlpha(255);
                if (list.size() > 2) {
                    this.acceptHeldButton.getBackground().setAlpha(100);
                }
                this.acceptHeldButton.setVisibility(0);
            }
            View view = this.mCallBg;
            if (view != null) {
                view.setBackground(Skin2.getDrawable2(R.drawable.mini_dialog_bg2));
            }
        } else {
            Button button5 = this.acceptHeldButton;
            if (button5 != null && button5.getVisibility() == 0) {
                this.acceptHeldButton.setVisibility(8);
            }
            View view2 = this.mCallBg;
            if (view2 != null) {
                view2.setBackground(Skin2.getDrawable2(R.drawable.mini_dialog_bg));
            }
            if (!call3.isEmpty()) {
                TextView textView4 = this.callStatusView;
                if (textView4 != null) {
                    textView4.setVisibility(0);
                    this.callStatusView.setText(Skin2.getString(R.string.str_out_call_status));
                }
                TextView textView5 = this.callingTimeView;
                if (textView5 != null) {
                    textView5.setVisibility(8);
                }
                Button button6 = this.voiceSourceCarButton;
                if (button6 != null) {
                    button6.setVisibility(8);
                }
                Button button7 = this.voiceSourcePhoneButton;
                if (button7 != null) {
                    button7.setVisibility(8);
                }
            } else {
                TextView textView6 = this.callingTimeView;
                if (textView6 != null) {
                    textView6.setVisibility(0);
                }
            }
        }
        BluetoothHfpclientService bluetoothHfpclientService = this.mHFPService;
        if (bluetoothHfpclientService != null) {
            updateAudioState(bluetoothHfpclientService.getAudioState());
        }
        if (call2.isEmpty()) {
            if ((call3.isEmpty() && call.isEmpty()) || call4.isEmpty()) {
                Log.d(TAG, "isMultiParty");
                if ((!call4.isEmpty() && call4.get(0).isMultiParty()) || (!call2.isEmpty() && call2.get(0).isMultiParty())) {
                    TextView textView7 = this.callStatusView;
                    if (textView7 == null || textView7.getVisibility() != 8) {
                        return;
                    }
                    this.callStatusView.setVisibility(0);
                    this.callStatusView.setText(Skin2.getString(R.string.str_bt_call_multiparty));
                    return;
                }
                TextView textView8 = this.callStatusView;
                if (textView8 == null || textView8.getVisibility() != 8) {
                    return;
                }
                this.callStatusView.setVisibility(0);
                this.callStatusView.setText(Skin2.getString(R.string.str_bt_call_abnormal));
            }
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
}
