package com.hcn.bluetooth.view;

import android.bluetooth.BluetoothHeadsetClientCall;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hcn.bluetooth.api.Utils;
import com.hcn.bluetooth.bean.CallInfo;
import com.hcn.bluetooth.config.ThemeConfig;
import com.hcn.bluetooth.service.BluetoothHfpclientService;
import com.hcn.bluetoothservice.R;
import com.hcn.common.utils.HUtilsEx;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.skin2.Skin2;
import com.hcn.skinx_night.NightX;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PhoneCallView extends PhoneCallViewBase implements View.OnClickListener {
    public static final int HALFOPACITY = 100;
    public static final int MAX_CALL_SIZE = 2;
    public static final int OPACITY = 255;
    private static final String TAG = "PhoneCallView";
    private static AnimationDrawable _animaition;
    private static boolean isRK01 = HUtilsEx.getSystemProperty(ThemeConfig.MODULE_SKIN_PROP, "").equals("rk01");
    private Button acceptHeldButton;
    private Button acceptTerminateButton;
    private Button answerButton;
    private ImageView bt_calling_image;
    TextView callStatusView;
    TextView callingNameView;
    TextView callingNumView;
    TextView callingStateView;
    TextView callingTimeView;
    private Button hangupButton;
    private Button heldButton;
    private LinearLayout heldLayout;
    private TextView heldNumView;
    private TextView heldStateView;
    private Button keypadButton;
    private Button mDeleteNum;
    private BluetoothHfpclientService mHFPService;
    private Map<String, String> mMapData;
    private Button mMicMuteBtn;
    private Button mergeButton;
    private LinearLayout softkeyPadLayout;
    private ImageView softkeyPadLine;
    private EditText subcallNumber;
    private Button swapButton;
    private Button voiceSourceCarButton;
    private Button voiceSourcePhoneButton;

    private void updateMicSource(boolean z) {
    }

    @Override // com.hcn.bluetooth.view.PhoneCallViewBase
    protected int getLayoutId() {
        return R.layout.bt_calling_status;
    }

    public PhoneCallView(Context context, WindowManager windowManager, AudioManager audioManager) {
        super(context, windowManager, audioManager);
        this.mMicMuteBtn = null;
        this.mMapData = new HashMap();
        this.bt_calling_image = null;
        this.mLayoutParams.type = Skin2.getInteger(R.integer.call_view_window_type);
        this.mLayoutParams.gravity = Skin2.getInteger(R.integer.call_view_window_gravity);
        this.mLayoutParams.width = Skin2.getInteger(R.integer.call_view_window_width);
        this.mLayoutParams.height = Skin2.getInteger(R.integer.call_view_window_height);
        this.mLayoutParams.y = Skin2.getInteger(R.integer.call_view_window_y);
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
        this.mLayoutParams.y = Skin2.getInteger(R.integer.call_view_window_y);
        LinearLayout linearLayout = this.softkeyPadLayout;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        ImageView imageView = this.softkeyPadLine;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
        EditText editText = this.subcallNumber;
        if (editText != null) {
            editText.setText("");
        }
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
    public void updateCallTime(Map<String, CallInfo> map) {
        if (map.isEmpty()) {
            this.callingTimeView.setVisibility(8);
            return;
        }
        if (this.callingTimeView.getVisibility() != 0) {
            this.callingTimeView.setVisibility(0);
        }
        Iterator<Map.Entry<String, CallInfo>> it = map.entrySet().iterator();
        int iMax = 0;
        while (it.hasNext()) {
            iMax = Math.max(iMax, it.next().getValue().getCallTime());
        }
        String str = String.format(Locale.ENGLISH, "%02d:%02d:%02d", Integer.valueOf((iMax / 3600) % 60), Integer.valueOf((iMax / 60) % 60), Integer.valueOf(iMax % 60));
        Log.d(TAG, "updateCallTime: time=" + str);
        TextView textView = this.callingTimeView;
        if (textView != null) {
            textView.setText(str);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (Skin2.getId(view)) {
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
            case R.id.btn_hangup /* 2131230817 */:
                Log.d(TAG, "btn_hangup");
                this.mHFPService.hangup();
                List<BluetoothHeadsetClientCall> currentCalls = this.mHFPService.getCurrentCalls();
                if (currentCalls == null || currentCalls.size() == 0) {
                    Log.e(TAG, "btn_hangup no call!");
                    hideView();
                }
                break;
            case R.id.btn_softkeypad /* 2131230820 */:
                onClickSoftkeypad();
                break;
            case R.id.btn_voiceswitch_car /* 2131230822 */:
            case R.id.btn_voiceswitch_phone /* 2131230823 */:
                this.mHFPService.switchAudio();
                break;
        }
    }

    @Override // com.hcn.bluetooth.view.PhoneCallViewBase
    public void showView() {
        super.showView();
    }

    @Override // com.hcn.bluetooth.view.PhoneCallViewBase
    protected void initView() {
        this.callStatusView = (TextView) findViewById(Skin2.xId(R.id.bt_calling_status_tv));
        this.callingNameView = (TextView) findViewById(Skin2.xId(R.id.bt_calling_phone_name));
        this.callingTimeView = (TextView) findViewById(Skin2.xId(R.id.bt_calling_time_text));
        this.callingNumView = (TextView) findViewById(Skin2.xId(R.id.bt_calling_phone_number));
        this.callingStateView = (TextView) findViewById(Skin2.xId(R.id.bt_calling_state_text));
        setTextMarquee(this.callingNameView);
        this.heldLayout = (LinearLayout) findViewById(Skin2.xId(R.id.call_held_layout));
        this.heldNumView = (TextView) findViewById(Skin2.xId(R.id.bt_call_held_number));
        this.heldStateView = (TextView) findViewById(Skin2.xId(R.id.bt_call_held_state_text));
        this.voiceSourceCarButton = (Button) findViewById(Skin2.xId(R.id.btn_voiceswitch_car));
        this.voiceSourcePhoneButton = (Button) findViewById(Skin2.xId(R.id.btn_voiceswitch_phone));
        this.answerButton = (Button) findViewById(Skin2.xId(R.id.btn_answer));
        this.hangupButton = (Button) findViewById(Skin2.xId(R.id.btn_hangup));
        this.heldButton = (Button) findViewById(Skin2.xId(R.id.btn_held));
        this.acceptTerminateButton = (Button) findViewById(Skin2.xId(R.id.btn_accept_terminate));
        this.acceptHeldButton = (Button) findViewById(Skin2.xId(R.id.btn_accept_held));
        this.swapButton = (Button) findViewById(Skin2.xId(R.id.btn_swap));
        this.mergeButton = (Button) findViewById(Skin2.xId(R.id.btn_merge));
        this.softkeyPadLayout = (LinearLayout) findViewById(Skin2.xId(R.id.calling_softkeypad));
        this.softkeyPadLine = (ImageView) findViewById(Skin2.xId(R.id.calling_softkeypad_line));
        this.keypadButton = (Button) findViewById(Skin2.xId(R.id.btn_softkeypad));
        this.subcallNumber = (EditText) findViewById(Skin2.xId(R.id.calling_input_et));
        try {
            this.answerButton.setOnClickListener(this);
            this.voiceSourceCarButton.setOnClickListener(this);
            this.voiceSourcePhoneButton.setOnClickListener(this);
            this.hangupButton.setOnClickListener(this);
            this.acceptTerminateButton.setOnClickListener(this);
            this.acceptHeldButton.setOnClickListener(this);
            this.heldButton.setOnClickListener(this);
            this.swapButton.setOnClickListener(this);
            this.mergeButton.setOnClickListener(this);
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
        Button button = (Button) findViewById(getSkinResourceID("btn_del_num", "id"));
        this.mDeleteNum = button;
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.hcn.bluetooth.view.PhoneCallView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m41lambda$initView$0$comhcnbluetoothviewPhoneCallView(view);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-hcn-bluetooth-view-PhoneCallView, reason: not valid java name */
    /* synthetic */ void m41lambda$initView$0$comhcnbluetoothviewPhoneCallView(View view) {
        deleteAllDialPadString();
    }

    private int getSkinResourceID(String str, String str2) {
        if (Skin2.useSkinPackage()) {
            try {
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    return SkinCompatResources.getInstance().getSkinResources().getIdentifier(str, str2, SkinCompatResources.getInstance().getSkinPkgName());
                }
            } catch (Exception unused) {
            }
        }
        return 0;
    }

    public static void setTextMarquee(TextView textView) {
        if (textView != null) {
            Log.d(TAG, "setTextMarquee: " + textView.getEllipsize());
            if (textView.getEllipsize() == TextUtils.TruncateAt.MARQUEE) {
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.setSingleLine(true);
                textView.setSelected(true);
                textView.setFocusable(true);
                textView.setFocusableInTouchMode(true);
            }
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
            String contactNameByNumber = Utils.getContactNameByNumber(this.mContext, str, str2);
            Log.d(TAG, "getCallName: num=" + str + " name=" + contactNameByNumber);
            if (TextUtils.isEmpty(contactNameByNumber)) {
                contactNameByNumber = Skin2.getString(R.string.unkown);
            }
            String str4 = contactNameByNumber;
            this.mMapData.put(str, str4);
            return str4;
        }
        return Skin2.getString(R.string.unkown);
    }

    private void onSingleCall(BluetoothHeadsetClientCall bluetoothHeadsetClientCall) {
        Log.d(TAG, "onSingleCall");
        if (bluetoothHeadsetClientCall == null) {
            return;
        }
        TextView textView = this.callingStateView;
        if (textView != null && textView.getVisibility() == 0) {
            this.callingStateView.setVisibility(8);
        }
        LinearLayout linearLayout = this.heldLayout;
        if (linearLayout != null && linearLayout.getVisibility() == 0) {
            this.heldLayout.setVisibility(8);
        }
        int state = bluetoothHeadsetClientCall.getState();
        Log.d(TAG, "callState: " + state);
        TextView textView2 = this.callStatusView;
        if (textView2 != null) {
            textView2.setVisibility(0);
            if (state == 0) {
                this.callStatusView.setText(Skin2.getString(R.string.str_bt_call_active));
            } else if (state == 1) {
                this.callStatusView.setText(Skin2.getString(R.string.str_bt_call_held));
            } else if (state == 2 || state == 3) {
                this.callStatusView.setText(Skin2.getString(R.string.str_out_call_status));
            } else if (state == 4) {
                this.callStatusView.setText(Skin2.getString(R.string.str_income_call_status));
            } else if (state == 7) {
                this.subcallNumber.setText("");
                return;
            }
        }
        String number = bluetoothHeadsetClientCall.getNumber();
        String callName = getCallName(number, bluetoothHeadsetClientCall.getDevice().getAddress());
        Log.d(TAG, "callNumber(" + number + "), callName(" + callName + ")");
        TextView textView3 = this.callingNumView;
        if (textView3 != null) {
            textView3.setVisibility(0);
            this.callingNumView.setText(number);
        }
        TextView textView4 = this.callingNameView;
        if (textView4 != null) {
            textView4.setVisibility(0);
            if (!callName.equals(this.callingNameView.getText().toString())) {
                this.callingNameView.setText(callName);
            }
        }
        updateMicSource(this.mAudioManager.isMicrophoneMute());
        Button button = this.acceptTerminateButton;
        if (button != null && button.getVisibility() == 0) {
            this.acceptTerminateButton.setVisibility(8);
        }
        Button button2 = this.acceptHeldButton;
        if (button2 != null && button2.getVisibility() == 0) {
            this.acceptHeldButton.setVisibility(8);
        }
        Button button3 = this.swapButton;
        if (button3 != null && button3.getVisibility() == 0) {
            this.swapButton.setVisibility(8);
        }
        Button button4 = this.mergeButton;
        if (button4 != null && button4.getVisibility() == 0) {
            this.mergeButton.setVisibility(8);
        }
        Button button5 = this.keypadButton;
        if (button5 != null) {
            button5.setVisibility(0);
        }
        if (state == 1) {
            Button button6 = this.heldButton;
            if (button6 != null) {
                button6.setVisibility(0);
            }
            Button button7 = this.voiceSourceCarButton;
            if (button7 != null) {
                button7.setVisibility(8);
            }
            Button button8 = this.voiceSourcePhoneButton;
            if (button8 != null) {
                button8.setVisibility(8);
            }
            Button button9 = this.answerButton;
            if (button9 != null) {
                button9.setVisibility(8);
                return;
            }
            return;
        }
        if (state == 4) {
            Button button10 = this.voiceSourceCarButton;
            if (button10 != null) {
                button10.setVisibility(8);
            }
            Button button11 = this.voiceSourcePhoneButton;
            if (button11 != null) {
                button11.setVisibility(8);
            }
            Button button12 = this.heldButton;
            if (button12 != null) {
                button12.setVisibility(8);
            }
            Button button13 = this.answerButton;
            if (button13 != null) {
                button13.setVisibility(0);
                return;
            }
            return;
        }
        Button button14 = this.answerButton;
        if (button14 != null && button14.getVisibility() == 0) {
            this.answerButton.setVisibility(8);
        }
        Button button15 = this.heldButton;
        if (button15 != null) {
            button15.setVisibility(8);
        }
        BluetoothHfpclientService bluetoothHfpclientService = this.mHFPService;
        if (bluetoothHfpclientService != null) {
            updateAudioState(bluetoothHfpclientService.getAudioState());
        }
    }

    public static synchronized String getCallNumber(List<BluetoothHeadsetClientCall> list) {
        String str;
        str = "";
        for (BluetoothHeadsetClientCall bluetoothHeadsetClientCall : list) {
            if (bluetoothHeadsetClientCall != null) {
                str = str + "/" + bluetoothHeadsetClientCall.getNumber();
                Log.d(TAG, "callNumber(" + str + "), callState(" + bluetoothHeadsetClientCall.getState() + ")");
            }
        }
        return str;
    }

    private void onMultiCall(List<BluetoothHeadsetClientCall> list) {
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
        List<BluetoothHeadsetClientCall> call = BluetoothHfpclientService.getCall(list, 5, 4);
        if (!call.isEmpty()) {
            TextView textView = this.callingStateView;
            if (textView != null && textView.getVisibility() == 0) {
                this.callingStateView.setVisibility(8);
            }
            LinearLayout linearLayout = this.heldLayout;
            if (linearLayout != null && linearLayout.getVisibility() == 0) {
                this.heldLayout.setVisibility(8);
            }
            TextView textView2 = this.callStatusView;
            if (textView2 != null) {
                textView2.setVisibility(0);
                this.callStatusView.setText(Skin2.getString(R.string.str_bt_call_waiting));
            }
            String number = call.get(0).getNumber();
            String callName = getCallName(number, call.get(0).getDevice().getAddress());
            TextView textView3 = this.callingNumView;
            if (textView3 != null) {
                textView3.setVisibility(0);
                this.callingNumView.setText(number);
            }
            TextView textView4 = this.callingNameView;
            if (textView4 != null) {
                textView4.setVisibility(0);
                this.callingNameView.setText(callName);
            }
            LinearLayout linearLayout2 = this.softkeyPadLayout;
            if (linearLayout2 != null && linearLayout2.getVisibility() == 0) {
                this.softkeyPadLayout.setVisibility(8);
                ImageView imageView = this.softkeyPadLine;
                if (imageView != null && imageView.getVisibility() == 0) {
                    this.softkeyPadLine.setVisibility(8);
                }
            }
            Button button3 = this.keypadButton;
            if (button3 != null) {
                button3.setVisibility(8);
            }
            Button button4 = this.voiceSourceCarButton;
            if (button4 != null) {
                button4.setVisibility(8);
            }
            Button button5 = this.voiceSourcePhoneButton;
            if (button5 != null) {
                button5.setVisibility(8);
            }
            Button button6 = this.acceptTerminateButton;
            if (button6 != null) {
                button6.getBackground().setAlpha(255);
                if (list.size() > 2) {
                    this.acceptTerminateButton.getBackground().setAlpha(100);
                }
                this.acceptTerminateButton.setVisibility(0);
            }
            Button button7 = this.acceptHeldButton;
            if (button7 != null) {
                button7.getBackground().setAlpha(255);
                if (list.size() > 2) {
                    this.acceptHeldButton.getBackground().setAlpha(100);
                }
                this.acceptHeldButton.setVisibility(0);
            }
            Button button8 = this.swapButton;
            if (button8 != null && button8.getVisibility() == 0) {
                this.swapButton.setVisibility(8);
            }
            Button button9 = this.mergeButton;
            if (button9 == null || button9.getVisibility() != 0) {
                return;
            }
            this.mergeButton.setVisibility(8);
            return;
        }
        Button button10 = this.keypadButton;
        if (button10 != null) {
            button10.setVisibility(0);
        }
        Button button11 = this.acceptTerminateButton;
        if (button11 != null && button11.getVisibility() == 0) {
            this.acceptTerminateButton.setVisibility(8);
        }
        Button button12 = this.acceptHeldButton;
        if (button12 != null && button12.getVisibility() == 0) {
            this.acceptHeldButton.setVisibility(8);
        }
        Button button13 = this.voiceSourceCarButton;
        if (button13 != null) {
            button13.setVisibility(0);
            BluetoothHfpclientService bluetoothHfpclientService = this.mHFPService;
            if (bluetoothHfpclientService != null) {
                updateAudioState(bluetoothHfpclientService.getAudioState());
            }
        }
        List<BluetoothHeadsetClientCall> call2 = BluetoothHfpclientService.getCall(list, 1);
        List<BluetoothHeadsetClientCall> call3 = BluetoothHfpclientService.getCall(list, 2, 3);
        List<BluetoothHeadsetClientCall> call4 = BluetoothHfpclientService.getCall(list, 0);
        if (!call2.isEmpty() || (!call3.isEmpty() && !call4.isEmpty())) {
            TextView textView5 = this.callStatusView;
            if (textView5 != null && textView5.getVisibility() == 0) {
                this.callStatusView.setVisibility(8);
            }
            LinearLayout linearLayout3 = this.heldLayout;
            if (linearLayout3 != null) {
                linearLayout3.setVisibility(0);
            }
            if (!call2.isEmpty()) {
                bluetoothHeadsetClientCall = call2.get(0);
                TextView textView6 = this.heldStateView;
                if (textView6 != null) {
                    textView6.setText(Skin2.getString(R.string.str_bt_call_held));
                }
            } else {
                bluetoothHeadsetClientCall = call4.get(0);
                TextView textView7 = this.heldStateView;
                if (textView7 != null) {
                    textView7.setText(Skin2.getString(R.string.str_bt_call_active));
                }
            }
            String number2 = bluetoothHeadsetClientCall.getNumber();
            String callName2 = getCallName(number2, bluetoothHeadsetClientCall.getDevice().getAddress());
            TextView textView8 = this.heldNumView;
            if (textView8 != null) {
                if (!callName2.equals(Skin2.getString(R.string.unkown))) {
                    number2 = callName2;
                }
                textView8.setText(number2);
            }
            if (!call3.isEmpty()) {
                String number3 = call3.get(0).getNumber();
                String callName3 = getCallName(number3, call3.get(0).getDevice().getAddress());
                TextView textView9 = this.callingNumView;
                if (textView9 != null) {
                    textView9.setVisibility(0);
                    this.callingNumView.setText(number3);
                }
                TextView textView10 = this.callingNameView;
                if (textView10 != null) {
                    textView10.setVisibility(0);
                    this.callingNameView.setText(callName3);
                }
                TextView textView11 = this.callingStateView;
                if (textView11 != null) {
                    textView11.setVisibility(0);
                    this.callingStateView.setText(Skin2.getString(R.string.str_out_call_status));
                }
                Button button14 = this.swapButton;
                if (button14 != null && button14.getVisibility() == 0) {
                    this.swapButton.setVisibility(8);
                }
                Button button15 = this.mergeButton;
                if (button15 == null || button15.getVisibility() != 0) {
                    return;
                }
                this.mergeButton.setVisibility(8);
                return;
            }
            if (!call4.isEmpty()) {
                String number4 = call4.get(0).getNumber();
                String callName4 = getCallName(number4, call4.get(0).getDevice().getAddress());
                TextView textView12 = this.callingNumView;
                if (textView12 != null) {
                    textView12.setVisibility(0);
                    this.callingNumView.setText(number4);
                }
                TextView textView13 = this.callingNameView;
                if (textView13 != null) {
                    textView13.setVisibility(0);
                    this.callingNameView.setText(callName4);
                }
                TextView textView14 = this.callingStateView;
                if (textView14 != null) {
                    textView14.setVisibility(0);
                    this.callingStateView.setText(Skin2.getString(R.string.str_bt_call_active));
                }
                Button button16 = this.swapButton;
                if (button16 != null) {
                    button16.setVisibility(0);
                }
                Button button17 = this.mergeButton;
                if (button17 != null) {
                    button17.setVisibility(0);
                    return;
                }
                return;
            }
            if (call2.size() > 1) {
                String number5 = call2.get(1).getNumber();
                String callName5 = getCallName(number5, call2.get(1).getDevice().getAddress());
                TextView textView15 = this.callingNumView;
                if (textView15 != null) {
                    textView15.setVisibility(0);
                    this.callingNumView.setText(number5);
                }
                TextView textView16 = this.callingNameView;
                if (textView16 != null) {
                    textView16.setVisibility(0);
                    this.callingNameView.setText(callName5);
                }
                TextView textView17 = this.callingStateView;
                if (textView17 != null) {
                    textView17.setVisibility(0);
                    this.callingStateView.setText(Skin2.getString(R.string.str_bt_call_held));
                }
                Button button18 = this.swapButton;
                if (button18 != null) {
                    button18.setVisibility(0);
                }
                Button button19 = this.mergeButton;
                if (button19 != null) {
                    button19.setVisibility(0);
                    return;
                }
                return;
            }
        }
        Log.d(TAG, "isMultiParty");
        if ((!call4.isEmpty() && call4.get(0).isMultiParty()) || (!call2.isEmpty() && call2.get(0).isMultiParty())) {
            TextView textView18 = this.callStatusView;
            if (textView18 != null && textView18.getVisibility() == 8) {
                this.callStatusView.setVisibility(0);
                this.callStatusView.setText(Skin2.getString(R.string.str_bt_call_multiparty));
            }
        } else {
            TextView textView19 = this.callStatusView;
            if (textView19 != null && textView19.getVisibility() == 8) {
                this.callStatusView.setVisibility(0);
                this.callStatusView.setText(Skin2.getString(R.string.str_bt_call_abnormal));
            }
        }
        TextView textView20 = this.callingStateView;
        if (textView20 != null && textView20.getVisibility() == 0) {
            this.callingStateView.setVisibility(8);
        }
        LinearLayout linearLayout4 = this.heldLayout;
        if (linearLayout4 != null) {
            linearLayout4.setVisibility(8);
        }
        Button button20 = this.swapButton;
        if (button20 != null && button20.getVisibility() == 0) {
            this.swapButton.setVisibility(8);
        }
        Button button21 = this.mergeButton;
        if (button21 != null && button21.getVisibility() == 0) {
            this.mergeButton.setVisibility(8);
        }
        String callNumber = getCallNumber(list);
        TextView textView21 = this.callingNumView;
        if (textView21 != null) {
            textView21.setVisibility(0);
            this.callingNumView.setText(callNumber);
        }
    }

    private void onClickSoftkeypad() {
        if (this.softkeyPadLayout == null) {
            Log.d(TAG, "get calling_softkeypad fail!");
            return;
        }
        Skin2.getDrawable2("calling_keyboard_bg");
        Skin2.getDrawable2("bg_calling_status");
        if (this.softkeyPadLayout.getVisibility() == 0) {
            this.softkeyPadLayout.setVisibility(8);
            ImageView imageView = this.softkeyPadLine;
            if (imageView != null) {
                imageView.setVisibility(8);
                return;
            }
            return;
        }
        this.softkeyPadLayout.setVisibility(0);
        ImageView imageView2 = this.softkeyPadLine;
        if (imageView2 != null) {
            imageView2.setVisibility(0);
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

    private void deleteAllDialPadString() {
        EditText editText = this.subcallNumber;
        if (editText == null) {
            return;
        }
        editText.setText("");
    }
}
