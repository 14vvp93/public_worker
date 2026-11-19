package com.mediatek.keyguard.Telephony;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.INotificationManager;
import android.app.ITransientNotification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.SubscriptionInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManagerImpl;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.IccCardConstants;
import com.android.keyguard.EmergencyCarrierArea;
import com.android.keyguard.KeyguardPinBasedInputView;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardUtils;
import com.android.systemui.R;
import com.mediatek.internal.telephony.IMtkTelephonyEx;
import com.mediatek.keyguard.ext.IKeyguardUtilExt;
import com.mediatek.keyguard.ext.IOperatorSIMString;
import com.mediatek.keyguard.ext.OpKeyguardCustomizationFactoryBase;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardSimPinPukMeView extends KeyguardPinBasedInputView {
    private Button mDismissButton;
    private Runnable mDismissSimPinPukRunnable;
    private Handler mHandler;
    private IOperatorSIMString mIOperatorSIMString;
    private IKeyguardUtilExt mKeyguardUtilExt;
    private KeyguardUtils mKeyguardUtils;
    private IccCardConstants.State mLastSimState;
    private String mNewPinText;
    private int mNextRepollStatePhoneId;
    private int mPhoneId;
    private String mPukText;
    private AlertDialog mRemainingAttemptsDialog;
    private StringBuffer mSb;
    private KeyguardSecurityModel mSecurityModel;
    private AlertDialog mSimCardDialog;
    private volatile boolean mSimCheckInProgress;
    private ImageView mSimImageView;
    private ProgressDialog mSimUnlockProgressDialog;
    private int mUnlockEnterState;
    KeyguardUpdateMonitor mUpdateMonitor;
    KeyguardUpdateMonitorCallback mUpdateMonitorCallback;
    private String[] strLockName;

    public KeyguardSimPinPukMeView(Context context) {
        this(context, null);
    }

    public KeyguardSimPinPukMeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSimUnlockProgressDialog = null;
        this.mUpdateMonitor = null;
        this.mSb = null;
        this.mNextRepollStatePhoneId = -1;
        this.mLastSimState = IccCardConstants.State.UNKNOWN;
        this.strLockName = new String[]{" [NP]", " [NSP]", " [SP]", " [CP]", " [SIMP]"};
        this.mHandler = new Handler(Looper.myLooper(), null, true);
        this.mDismissSimPinPukRunnable = new Runnable() { // from class: com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.1
            @Override // java.lang.Runnable
            public void run() {
                KeyguardSimPinPukMeView.this.mUpdateMonitor.reportSimUnlocked(KeyguardSimPinPukMeView.this.mPhoneId);
            }
        };
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onSimStateChangedUsingPhoneId(int phoneId, IccCardConstants.State simState) {
                Log.d("KeyguardSimPinPukMeView", "onSimStateChangedUsingPhoneId: " + simState + ", phoneId = " + phoneId + ", mPhoneId = " + KeyguardSimPinPukMeView.this.mPhoneId);
                StringBuilder sb = new StringBuilder();
                sb.append("onSimStateChangedUsingPhoneId: mCallback = ");
                sb.append(KeyguardSimPinPukMeView.this.mCallback);
                Log.d("KeyguardSimPinPukMeView", sb.toString());
                if (phoneId != KeyguardSimPinPukMeView.this.mPhoneId) {
                    if (phoneId == KeyguardSimPinPukMeView.this.mNextRepollStatePhoneId) {
                        Log.d("KeyguardSimPinPukMeView", "onSimStateChanged: mNextRepollStatePhoneId = " + KeyguardSimPinPukMeView.this.mNextRepollStatePhoneId);
                        if (KeyguardSimPinPukMeView.this.mSimUnlockProgressDialog != null) {
                            KeyguardSimPinPukMeView.this.mSimUnlockProgressDialog.hide();
                        }
                        if (IccCardConstants.State.READY != simState) {
                            KeyguardSimPinPukMeView.this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
                            KeyguardSimPinPukMeView.this.mLastSimState = simState;
                            return;
                        } else {
                            KeyguardSimPinPukMeView.this.mLastSimState = IccCardConstants.State.NETWORK_LOCKED;
                            KeyguardSimPinPukMeView.this.simStateReadyProcess();
                            return;
                        }
                    }
                    return;
                }
                KeyguardSimPinPukMeView.this.resetState(true);
                if (KeyguardSimPinPukMeView.this.mSimUnlockProgressDialog != null) {
                    KeyguardSimPinPukMeView.this.mSimUnlockProgressDialog.hide();
                }
                KeyguardSimPinPukMeView.this.mHandler.removeCallbacks(KeyguardSimPinPukMeView.this.mDismissSimPinPukRunnable);
                if (IccCardConstants.State.READY == simState) {
                    KeyguardSimPinPukMeView.this.simStateReadyProcess();
                } else if (IccCardConstants.State.NOT_READY == simState || IccCardConstants.State.ABSENT == simState) {
                    Log.d("KeyguardSimPinPukMeView", "onSimStateChangedUsingPhoneId: not ready, phoneId = " + phoneId);
                    KeyguardSimPinPukMeView.this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
                    KeyguardSimPinPukMeView.this.mSimCheckInProgress = false;
                    Log.d("KeyguardSimPinPukMeView", "set mSimCheckInProgress false");
                } else if (IccCardConstants.State.NETWORK_LOCKED == simState) {
                    if (!KeyguardUtils.isMediatekSimMeLockSupport()) {
                        KeyguardSimPinPukMeView.this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
                    } else if (KeyguardSimPinPukMeView.this.getRetryMeCount(KeyguardSimPinPukMeView.this.mPhoneId) == 0) {
                        Log.d("KeyguardSimPinPukMeView", "onSimStateChanged: ME retrycount is 0, dismiss it");
                        KeyguardSimPinPukMeView.this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
                    }
                }
                KeyguardSimPinPukMeView.this.mLastSimState = simState;
                Log.d("KeyguardSimPinPukMeView", "assign mLastSimState=" + KeyguardSimPinPukMeView.this.mLastSimState);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onAirPlaneModeChanged(boolean airPlaneModeEnabled) {
                Log.d("KeyguardSimPinPukMeView", "onAirPlaneModeChanged(airPlaneModeEnabled = " + airPlaneModeEnabled + ")");
                if (airPlaneModeEnabled) {
                    Log.d("KeyguardSimPinPukMeView", "Flight-Mode turns on & keyguard is showing, dismiss keyguard.");
                    KeyguardSimPinPukMeView.this.mPasswordEntry.reset(true, true);
                    KeyguardSimPinPukMeView.this.mCallback.userActivity();
                    KeyguardSimPinPukMeView.this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
                }
            }
        };
        this.mKeyguardUtils = new KeyguardUtils(context);
        this.mSb = new StringBuffer();
        this.mUpdateMonitor = KeyguardUpdateMonitor.getInstance(getContext());
        this.mSecurityModel = new KeyguardSecurityModel(getContext());
        try {
            this.mKeyguardUtilExt = OpKeyguardCustomizationFactoryBase.getOpFactory(context).makeKeyguardUtil();
            this.mIOperatorSIMString = OpKeyguardCustomizationFactoryBase.getOpFactory(context).makeOperatorSIMString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPhoneId(int phoneId) {
        this.mPhoneId = phoneId;
        Log.i("KeyguardSimPinPukMeView", "setPhoneId=" + phoneId);
        resetState();
        if (this.mSimCardDialog != null) {
            if (this.mSimCardDialog.isShowing()) {
                this.mSimCardDialog.dismiss();
            }
            this.mSimCardDialog = null;
        }
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView
    public void resetState() {
        resetState(false);
    }

    public void resetState(boolean forceReload) {
        Log.v("KeyguardSimPinPukMeView", "Resetting state");
        super.resetState();
        TextView forText = (TextView) findViewById(R.id.slot_num_text);
        forText.setText(this.mContext.getString(R.string.kg_slot_id, Integer.valueOf(this.mPhoneId + 1)) + " ");
        Resources rez = getResources();
        String msg = "";
        int count = KeyguardUtils.getNumOfPhone();
        int color = -1;
        IccCardConstants.State simState = this.mUpdateMonitor.getSimStateOfPhoneId(this.mPhoneId);
        showDismissIfNeed(simState);
        if (count < 2) {
            if (simState == IccCardConstants.State.PIN_REQUIRED) {
                msg = rez.getString(R.string.kg_sim_pin_instructions);
                this.mUnlockEnterState = 0;
            } else if (simState == IccCardConstants.State.PUK_REQUIRED) {
                msg = rez.getString(R.string.kg_puk_enter_puk_hint);
                this.mUnlockEnterState = 1;
            } else if (IccCardConstants.State.NETWORK_LOCKED == simState && KeyguardUtils.isMediatekSimMeLockSupport()) {
                int category = this.mUpdateMonitor.getSimMeCategoryOfPhoneId(this.mPhoneId);
                msg = rez.getString(R.string.simlock_entersimmelock) + this.strLockName[category] + getRetryMeString(this.mPhoneId);
                this.mUnlockEnterState = 5;
            }
        } else {
            int subId = KeyguardUtils.getSubIdUsingPhoneId(this.mPhoneId);
            SubscriptionInfo info = this.mUpdateMonitor.getSubscriptionInfoForSubId(subId, forceReload);
            CharSequence displayName = info != null ? info.getDisplayName() : "";
            if (info == null) {
                displayName = "CARD " + Integer.toString(this.mPhoneId + 1);
                Log.d("KeyguardSimPinPukMeView", "we set a displayname");
            }
            Log.d("KeyguardSimPinPukMeView", "resetState() - subId = " + subId + ", displayName = " + ((Object) displayName));
            if (simState == IccCardConstants.State.PIN_REQUIRED) {
                msg = rez.getString(R.string.kg_sim_pin_instructions_multi, displayName);
                this.mUnlockEnterState = 0;
            } else if (simState == IccCardConstants.State.PUK_REQUIRED) {
                msg = rez.getString(R.string.kg_puk_enter_puk_hint_multi, displayName);
                this.mUnlockEnterState = 1;
            } else if (IccCardConstants.State.NETWORK_LOCKED == simState && KeyguardUtils.isMediatekSimMeLockSupport()) {
                int category2 = this.mUpdateMonitor.getSimMeCategoryOfPhoneId(this.mPhoneId);
                msg = rez.getString(R.string.simlock_entersimmelock) + this.strLockName[category2] + getRetryMeString(this.mPhoneId);
                this.mUnlockEnterState = 5;
            }
            if (info != null) {
                color = info.getIconTint();
            }
        }
        this.mKeyguardUtilExt.customizePinPukLockView(this.mPhoneId, this.mSimImageView, forText);
        this.mSimImageView.setImageTintList(ColorStateList.valueOf(color));
        String msg2 = this.mIOperatorSIMString.getOperatorSIMString(msg, this.mPhoneId, IOperatorSIMString.SIMChangedTag.DELSIM, this.mContext);
        Log.d("KeyguardSimPinPukMeView", "resetState() - mSecurityMessageDisplay.setMessage = " + msg2);
        this.mSecurityMessageDisplay.setMessage(msg2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getPinPasswordErrorMessage(int attemptsRemaining) {
        String displayMessage;
        if (attemptsRemaining == 0) {
            displayMessage = getContext().getString(R.string.kg_password_wrong_pin_code_pukked);
        } else if (attemptsRemaining > 0) {
            displayMessage = getContext().getResources().getQuantityString(R.plurals.kg_password_wrong_pin_code, attemptsRemaining, Integer.valueOf(attemptsRemaining));
        } else {
            displayMessage = getContext().getString(R.string.kg_password_pin_failed);
        }
        Log.d("KeyguardSimPinPukMeView", "getPinPasswordErrorMessage: attemptsRemaining=" + attemptsRemaining + " displayMessage=" + displayMessage);
        return displayMessage;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getPukPasswordErrorMessage(int attemptsRemaining) {
        String displayMessage;
        if (attemptsRemaining == 0) {
            displayMessage = getContext().getString(R.string.kg_password_wrong_puk_code_dead);
        } else if (attemptsRemaining > 0) {
            displayMessage = getContext().getResources().getQuantityString(R.plurals.kg_password_wrong_puk_code, attemptsRemaining, Integer.valueOf(attemptsRemaining));
        } else {
            displayMessage = getContext().getString(R.string.kg_password_puk_failed);
        }
        Log.d("KeyguardSimPinPukMeView", "getPukPasswordErrorMessage: attemptsRemaining=" + attemptsRemaining + " displayMessage=" + displayMessage);
        return displayMessage;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected boolean shouldLockout(long deadline) {
        return false;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected int getPasswordTextViewId() {
        return R.id.simPinPukMeEntry;
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPhoneId = -1;
        if (this.mEcaView instanceof EmergencyCarrierArea) {
            ((EmergencyCarrierArea) this.mEcaView).setCarrierTextVisible(true);
            View ctView = this.mEcaView.findViewById(R.id.carrier_text);
            this.mKeyguardUtilExt.customizeCarrierTextGravity((TextView) ctView);
        }
        this.mSimImageView = (ImageView) findViewById(R.id.keyguard_sim);
        this.mDismissButton = (Button) findViewById(R.id.key_dismiss);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("KeyguardSimPinPukMeView", "onAttachedToWindow");
        this.mUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("KeyguardSimPinPukMeView", "onDetachedFromWindow");
        this.mHandler.removeCallbacks(this.mDismissSimPinPukRunnable);
        this.mUpdateMonitor.removeCallback(this.mUpdateMonitorCallback);
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardSecurityView
    public void onResume(int reason) {
        if (this.mSimUnlockProgressDialog != null) {
            this.mSimUnlockProgressDialog.dismiss();
            this.mSimUnlockProgressDialog = null;
        }
        InputMethodManager imm = (InputMethodManager) this.mContext.getSystemService("input_method");
        if (imm.isActive()) {
            Log.i("KeyguardSimPinPukMeView", "IME is showing, we should hide it");
            imm.hideSoftInputFromWindow(getWindowToken(), 2);
        }
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardSecurityView
    public void onPause() {
        if (this.mSimUnlockProgressDialog != null) {
            this.mSimUnlockProgressDialog.dismiss();
            this.mSimUnlockProgressDialog = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setInputInvalidAlertDialog(CharSequence message, boolean shouldDisplay) {
        StringBuilder sb = new StringBuilder(message);
        if (shouldDisplay) {
            AlertDialog newDialog = new AlertDialog.Builder(this.mContext).setMessage(sb).setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null).setCancelable(true).create();
            newDialog.getWindow().setType(2009);
            newDialog.getWindow().addFlags(2);
            newDialog.show();
            return;
        }
        Toast.makeText(this.mContext, sb).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getRetryMeString(int phoneId) {
        int meRetryCount = getRetryMeCount(phoneId);
        return "(" + this.mContext.getString(R.string.retries_left, Integer.valueOf(meRetryCount)) + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRetryMeCount(int phoneId) {
        return this.mUpdateMonitor.getSimMeLeftRetryCountOfPhoneId(phoneId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void minusRetryMeCount(int phoneId) {
        this.mUpdateMonitor.minusSimMeLeftRetryCountOfPhoneId(phoneId);
    }

    private boolean validatePin(String pin, boolean isPUK) {
        int pinMinimum = isPUK ? 8 : 4;
        if (pin == null || pin.length() < pinMinimum || pin.length() > 8) {
            return false;
        }
        return true;
    }

    private void updatePinEnterScreen() {
        switch (this.mUnlockEnterState) {
            case 1:
                this.mPukText = this.mPasswordEntry.getText().toString();
                if (validatePin(this.mPukText, true)) {
                    this.mUnlockEnterState = 2;
                    this.mSb.delete(0, this.mSb.length());
                    this.mSb.append(this.mContext.getText(R.string.keyguard_password_enter_new_pin_code));
                    Log.d("KeyguardSimPinPukMeView", "updatePinEnterScreen() - STATE_ENTER_UK, validatePin = true ,mSecurityMessageDisplay.setMessage = " + this.mSb.toString());
                    this.mSecurityMessageDisplay.setMessage(this.mSb.toString());
                } else {
                    Log.d("KeyguardSimPinPukMeView", "updatePinEnterScreen() - STATE_ENTER_UK, validatePin = false ,mSecurityMessageDisplay.setMessage = R.string.invalidPuk");
                    this.mSecurityMessageDisplay.setMessage(R.string.invalidPuk);
                }
                break;
            case 2:
                this.mNewPinText = this.mPasswordEntry.getText().toString();
                if (validatePin(this.mNewPinText, false)) {
                    this.mUnlockEnterState = 3;
                    this.mSb.delete(0, this.mSb.length());
                    this.mSb.append(this.mContext.getText(R.string.keyguard_password_Confirm_pin_code));
                    Log.d("KeyguardSimPinPukMeView", "updatePinEnterScreen() - STATE_ENTER_NEW, validatePin = true ,mSecurityMessageDisplay.setMessage = " + this.mSb.toString());
                    this.mSecurityMessageDisplay.setMessage(this.mSb.toString());
                } else {
                    Log.d("KeyguardSimPinPukMeView", "updatePinEnterScreen() - STATE_ENTER_NEW, validatePin = false ,mSecurityMessageDisplay.setMessage = R.string.keyguard_code_length_prompt");
                    this.mSecurityMessageDisplay.setMessage(R.string.keyguard_code_length_prompt);
                }
                break;
            case 3:
                if (!this.mNewPinText.equals(this.mPasswordEntry.getText().toString())) {
                    this.mUnlockEnterState = 2;
                    this.mSb.delete(0, this.mSb.length());
                    this.mSb.append(this.mContext.getText(R.string.keyguard_code_donnot_mismatch));
                    this.mSb.append(this.mContext.getText(R.string.keyguard_password_enter_new_pin_code));
                    Log.d("KeyguardSimPinPukMeView", "updatePinEnterScreen() - STATE_REENTER_NEW, true ,mSecurityMessageDisplay.setMessage = " + this.mSb.toString());
                    this.mSecurityMessageDisplay.setMessage(this.mSb.toString());
                } else {
                    Log.d("KeyguardSimPinPukMeView", "updatePinEnterScreen() - STATE_REENTER_NEW, false ,mSecurityMessageDisplay.setMessage = empty string.");
                    this.mUnlockEnterState = 4;
                    this.mSecurityMessageDisplay.setMessage("");
                }
                break;
        }
        this.mPasswordEntry.reset(true, true);
        this.mCallback.userActivity();
    }

    private abstract class CheckSimPinPuk extends Thread {
        private final String mPin;
        private final String mPuk;
        private int[] mResult;

        abstract void onSimCheckResponse(int i, int i2);

        protected CheckSimPinPuk(String pin, int phoneId) {
            this.mPin = pin;
            this.mPuk = null;
        }

        protected CheckSimPinPuk(String puk, String pin, int phoneId) {
            this.mPin = pin;
            this.mPuk = puk;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                Log.d("KeyguardSimPinPukMeView", "CheckSimPinPuk, mPhoneId =" + KeyguardSimPinPukMeView.this.mPhoneId);
                if (KeyguardSimPinPukMeView.this.mUpdateMonitor.getSimStateOfPhoneId(KeyguardSimPinPukMeView.this.mPhoneId) != IccCardConstants.State.PIN_REQUIRED) {
                    if (KeyguardSimPinPukMeView.this.mUpdateMonitor.getSimStateOfPhoneId(KeyguardSimPinPukMeView.this.mPhoneId) == IccCardConstants.State.PUK_REQUIRED) {
                        ITelephony phoneService = ITelephony.Stub.asInterface(ServiceManager.checkService("phone"));
                        if (phoneService != null) {
                            int subId = KeyguardUtils.getSubIdUsingPhoneId(KeyguardSimPinPukMeView.this.mPhoneId);
                            this.mResult = phoneService.supplyPukReportResultForSubscriber(subId, this.mPuk, this.mPin);
                        } else {
                            Log.d("KeyguardSimPinPukMeView", "phoneService is gone, skip supplyPukForSubscriber().");
                        }
                    }
                } else {
                    ITelephony phoneService2 = ITelephony.Stub.asInterface(ServiceManager.checkService("phone"));
                    if (phoneService2 != null) {
                        int subId2 = KeyguardUtils.getSubIdUsingPhoneId(KeyguardSimPinPukMeView.this.mPhoneId);
                        this.mResult = phoneService2.supplyPinReportResultForSubscriber(subId2, this.mPin);
                    } else {
                        Log.d("KeyguardSimPinPukMeView", "phoneService is gone, skip supplyPinForSubscriber().");
                    }
                }
                if (this.mResult == null) {
                    KeyguardSimPinPukMeView.this.mHandler.post(new Runnable() { // from class: com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.CheckSimPinPuk.2
                        @Override // java.lang.Runnable
                        public void run() {
                            CheckSimPinPuk.this.onSimCheckResponse(2, -1);
                        }
                    });
                    Log.d("KeyguardSimPinPukMeView", "there is an error with sim fw");
                    return;
                }
                Log.v("KeyguardSimPinPukMeView", "supplyPinReportResultForSubscriber returned: " + this.mResult[0] + " " + this.mResult[1]);
                Log.d("KeyguardSimPinPukMeView", "CheckSimPinPuk.run(),mResult is true(success), so we postDelayed a timeout runnable object");
                KeyguardSimPinPukMeView.this.mHandler.post(new Runnable() { // from class: com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.CheckSimPinPuk.1
                    @Override // java.lang.Runnable
                    public void run() {
                        CheckSimPinPuk.this.onSimCheckResponse(CheckSimPinPuk.this.mResult[0], CheckSimPinPuk.this.mResult[1]);
                    }
                });
            } catch (RemoteException e) {
                KeyguardSimPinPukMeView.this.mHandler.post(new Runnable() { // from class: com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.CheckSimPinPuk.3
                    @Override // java.lang.Runnable
                    public void run() {
                        CheckSimPinPuk.this.onSimCheckResponse(2, -1);
                    }
                });
            }
        }
    }

    private abstract class CheckSimMe extends Thread {
        private final String mPasswd;
        private int mResult;

        abstract void onSimMeCheckResponse(int i);

        protected CheckSimMe(String passwd, int phoneId) {
            this.mPasswd = passwd;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                Log.d("KeyguardSimPinPukMeView", "CheckMe, mPhoneId =" + KeyguardSimPinPukMeView.this.mPhoneId);
                int subId = KeyguardUtils.getSubIdUsingPhoneId(KeyguardSimPinPukMeView.this.mPhoneId);
                this.mResult = IMtkTelephonyEx.Stub.asInterface(ServiceManager.getService("phoneEx")).supplyNetworkDepersonalization(subId, this.mPasswd);
                Log.d("KeyguardSimPinPukMeView", "CheckMe, mPhoneId =" + KeyguardSimPinPukMeView.this.mPhoneId + " mResult=" + this.mResult);
                if (this.mResult == 0) {
                    Log.d("KeyguardSimPinPukMeView", "CheckSimMe.run(), VERIFY_RESULT_PASS == ret, so we postDelayed a timeout runnable object");
                }
                KeyguardSimPinPukMeView.this.mHandler.post(new Runnable() { // from class: com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.CheckSimMe.1
                    @Override // java.lang.Runnable
                    public void run() {
                        CheckSimMe.this.onSimMeCheckResponse(CheckSimMe.this.mResult);
                    }
                });
            } catch (RemoteException e) {
                KeyguardSimPinPukMeView.this.mHandler.post(new Runnable() { // from class: com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.CheckSimMe.2
                    @Override // java.lang.Runnable
                    public void run() {
                        CheckSimMe.this.onSimMeCheckResponse(2);
                    }
                });
            }
        }
    }

    private Dialog getSimUnlockProgressDialog() {
        if (this.mSimUnlockProgressDialog == null) {
            this.mSimUnlockProgressDialog = new ProgressDialog(this.mContext);
            String msg = this.mContext.getString(R.string.kg_sim_unlock_progress_dialog_message);
            this.mSimUnlockProgressDialog.setMessage(this.mIOperatorSIMString.getOperatorSIMString(msg, this.mPhoneId, IOperatorSIMString.SIMChangedTag.DELSIM, this.mContext));
            this.mSimUnlockProgressDialog.setIndeterminate(true);
            this.mSimUnlockProgressDialog.setCancelable(false);
            if (!(this.mContext instanceof Activity)) {
                this.mSimUnlockProgressDialog.getWindow().setType(2009);
            }
        }
        return this.mSimUnlockProgressDialog;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected void verifyPasswordAndUnlock() {
        String entry = this.mPasswordEntry.getText().toString();
        if (!validatePin(entry, false) && (this.mUpdateMonitor.getSimStateOfPhoneId(this.mPhoneId) == IccCardConstants.State.PIN_REQUIRED || (this.mUpdateMonitor.getSimStateOfPhoneId(this.mPhoneId) == IccCardConstants.State.NETWORK_LOCKED && KeyguardUtils.isMediatekSimMeLockSupport()))) {
            if (this.mUpdateMonitor.getSimStateOfPhoneId(this.mPhoneId) == IccCardConstants.State.PIN_REQUIRED) {
                this.mSecurityMessageDisplay.setMessage(R.string.kg_invalid_sim_pin_hint);
            } else {
                this.mSecurityMessageDisplay.setMessage(R.string.keyguard_code_length_prompt);
            }
            this.mPasswordEntry.reset(true, true);
            this.mPasswordEntry.setEnabled(true);
            this.mCallback.userActivity();
            return;
        }
        this.mPasswordEntry.setEnabled(true);
        dealWithPinOrPukUnlock();
    }

    private void dealWithPinOrPukUnlock() {
        if (this.mUpdateMonitor.getSimStateOfPhoneId(this.mPhoneId) == IccCardConstants.State.PIN_REQUIRED) {
            Log.d("KeyguardSimPinPukMeView", "onClick, checkPin, mPhoneId=" + this.mPhoneId);
            checkPin(this.mPhoneId);
            return;
        }
        if (this.mUpdateMonitor.getSimStateOfPhoneId(this.mPhoneId) == IccCardConstants.State.PUK_REQUIRED) {
            Log.d("KeyguardSimPinPukMeView", "onClick, checkPuk, mPhoneId=" + this.mPhoneId);
            checkPuk(this.mPhoneId);
            return;
        }
        if (this.mUpdateMonitor.getSimStateOfPhoneId(this.mPhoneId) == IccCardConstants.State.NETWORK_LOCKED && KeyguardUtils.isMediatekSimMeLockSupport()) {
            Log.d("KeyguardSimPinPukMeView", "onClick, checkMe, mPhoneId=" + this.mPhoneId);
            checkMe(this.mPhoneId);
            return;
        }
        Log.d("KeyguardSimPinPukMeView", "wrong status, mPhoneId=" + this.mPhoneId);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView$3] */
    private void checkPin(int phoneId) {
        getSimUnlockProgressDialog().show();
        Log.d("KeyguardSimPinPukMeView", "mSimCheckInProgress: " + this.mSimCheckInProgress);
        if (!this.mSimCheckInProgress) {
            this.mSimCheckInProgress = true;
            new CheckSimPinPuk(this.mPasswordEntry.getText().toString(), phoneId) { // from class: com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.3
                @Override // com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.CheckSimPinPuk
                void onSimCheckResponse(int result, int attemptsRemaining) {
                    KeyguardSimPinPukMeView.this.resetPasswordText(true, result != 0);
                    if (result == 0) {
                        KeyguardSimPinPukMeView.this.mKeyguardUtilExt.showToastWhenUnlockPinPuk(KeyguardSimPinPukMeView.this.mContext, 501);
                        Log.d("KeyguardSimPinPukMeView", "checkPin() success");
                        KeyguardSimPinPukMeView.this.mUpdateMonitor.reportSimUnlocked(KeyguardSimPinPukMeView.this.mPhoneId);
                        KeyguardSimPinPukMeView.this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
                    } else {
                        if (KeyguardSimPinPukMeView.this.mSimUnlockProgressDialog != null) {
                            KeyguardSimPinPukMeView.this.mSimUnlockProgressDialog.hide();
                        }
                        if (result != 1) {
                            KeyguardSimPinPukMeView.this.mSecurityMessageDisplay.setMessage(KeyguardSimPinPukMeView.this.getContext().getString(R.string.kg_password_pin_failed));
                        } else if (attemptsRemaining <= 2) {
                            KeyguardSimPinPukMeView.this.getSimRemainingAttemptsDialog(attemptsRemaining).show();
                            KeyguardSimPinPukMeView.this.mSecurityMessageDisplay.setMessage(KeyguardSimPinPukMeView.this.getPinPasswordErrorMessage(attemptsRemaining));
                        } else {
                            KeyguardSimPinPukMeView.this.mSecurityMessageDisplay.setMessage(KeyguardSimPinPukMeView.this.getPinPasswordErrorMessage(attemptsRemaining));
                        }
                        Log.d("KeyguardSimPinPukMeView", "verifyPasswordAndUnlock  CheckSimPin.onSimCheckResponse: " + result + " attemptsRemaining=" + attemptsRemaining);
                        KeyguardSimPinPukMeView.this.resetPasswordText(true, result != 0);
                    }
                    KeyguardSimPinPukMeView.this.mCallback.userActivity();
                    KeyguardSimPinPukMeView.this.mSimCheckInProgress = false;
                }
            }.start();
        }
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView$4] */
    private void checkPuk(int phoneId) {
        updatePinEnterScreen();
        if (this.mUnlockEnterState != 4) {
            return;
        }
        getSimUnlockProgressDialog().show();
        Log.d("KeyguardSimPinPukMeView", "mSimCheckInProgress: " + this.mSimCheckInProgress);
        if (!this.mSimCheckInProgress) {
            this.mSimCheckInProgress = true;
            new CheckSimPinPuk(this.mPukText, this.mNewPinText, phoneId) { // from class: com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.4
                @Override // com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.CheckSimPinPuk
                void onSimCheckResponse(int result, int attemptsRemaining) {
                    KeyguardSimPinPukMeView.this.resetPasswordText(true, result != 0);
                    if (result != 0) {
                        if (KeyguardSimPinPukMeView.this.mSimUnlockProgressDialog != null) {
                            KeyguardSimPinPukMeView.this.mSimUnlockProgressDialog.hide();
                        }
                        KeyguardSimPinPukMeView.this.mUnlockEnterState = 1;
                        if (result != 1) {
                            KeyguardSimPinPukMeView.this.mSecurityMessageDisplay.setMessage(KeyguardSimPinPukMeView.this.getContext().getString(R.string.kg_password_puk_failed));
                        } else if (attemptsRemaining <= 2) {
                            KeyguardSimPinPukMeView.this.getPukRemainingAttemptsDialog(attemptsRemaining).show();
                            KeyguardSimPinPukMeView.this.mSecurityMessageDisplay.setMessage(KeyguardSimPinPukMeView.this.getPukPasswordErrorMessage(attemptsRemaining));
                        } else {
                            KeyguardSimPinPukMeView.this.mSecurityMessageDisplay.setMessage(KeyguardSimPinPukMeView.this.getPukPasswordErrorMessage(attemptsRemaining));
                        }
                        Log.d("KeyguardSimPinPukMeView", "verifyPasswordAndUnlock  UpdateSim.onSimCheckResponse:  attemptsRemaining=" + attemptsRemaining);
                    } else {
                        Log.d("KeyguardSimPinPukMeView", "checkPuk onSimCheckResponse, success!");
                        KeyguardSimPinPukMeView.this.mKeyguardUtilExt.showToastWhenUnlockPinPuk(KeyguardSimPinPukMeView.this.mContext, 502);
                        KeyguardSimPinPukMeView.this.mUpdateMonitor.reportSimUnlocked(KeyguardSimPinPukMeView.this.mPhoneId);
                        KeyguardSimPinPukMeView.this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
                    }
                    KeyguardSimPinPukMeView.this.mCallback.userActivity();
                    KeyguardSimPinPukMeView.this.mSimCheckInProgress = false;
                }
            }.start();
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView$5] */
    private void checkMe(int phoneId) {
        getSimUnlockProgressDialog().show();
        if (!this.mSimCheckInProgress) {
            this.mSimCheckInProgress = true;
            new CheckSimMe(this.mPasswordEntry.getText().toString(), phoneId) { // from class: com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.5
                @Override // com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.CheckSimMe
                void onSimMeCheckResponse(int ret) {
                    Log.d("KeyguardSimPinPukMeView", "checkMe onSimChangedResponse, ret = " + ret);
                    if (ret == 0) {
                        Log.d("KeyguardSimPinPukMeView", "checkMe VERIFY_RESULT_PASS == ret(we had sent runnable before");
                        KeyguardSimPinPukMeView.this.mUpdateMonitor.reportSimUnlocked(KeyguardSimPinPukMeView.this.mPhoneId);
                        KeyguardSimPinPukMeView.this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
                    } else if (1 == ret) {
                        KeyguardSimPinPukMeView.this.mSb.delete(0, KeyguardSimPinPukMeView.this.mSb.length());
                        KeyguardSimPinPukMeView.this.minusRetryMeCount(KeyguardSimPinPukMeView.this.mPhoneId);
                        if (KeyguardSimPinPukMeView.this.mSimUnlockProgressDialog != null) {
                            KeyguardSimPinPukMeView.this.mSimUnlockProgressDialog.hide();
                        }
                        if (KeyguardSimPinPukMeView.this.mUnlockEnterState == 5) {
                            if (KeyguardSimPinPukMeView.this.getRetryMeCount(KeyguardSimPinPukMeView.this.mPhoneId) == 0) {
                                KeyguardSimPinPukMeView.this.setInputInvalidAlertDialog(KeyguardSimPinPukMeView.this.mContext.getText(R.string.simlock_slot_locked_message), true);
                                KeyguardSimPinPukMeView.this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
                            } else {
                                int category = KeyguardSimPinPukMeView.this.mUpdateMonitor.getSimMeCategoryOfPhoneId(KeyguardSimPinPukMeView.this.mPhoneId);
                                KeyguardSimPinPukMeView.this.mSb.append(KeyguardSimPinPukMeView.this.mContext.getText(R.string.keyguard_wrong_code_input));
                                KeyguardSimPinPukMeView.this.mSb.append(KeyguardSimPinPukMeView.this.mContext.getText(R.string.simlock_entersimmelock));
                                KeyguardSimPinPukMeView.this.mSb.append(KeyguardSimPinPukMeView.this.strLockName[category] + KeyguardSimPinPukMeView.this.getRetryMeString(KeyguardSimPinPukMeView.this.mPhoneId));
                            }
                            Log.d("KeyguardSimPinPukMeView", "checkMe() - VERIFY_INCORRECT_PASSWORD == ret, mSecurityMessageDisplay.setMessage = " + KeyguardSimPinPukMeView.this.mSb.toString());
                            KeyguardSimPinPukMeView.this.mSecurityMessageDisplay.setMessage(KeyguardSimPinPukMeView.this.mSb.toString());
                            KeyguardSimPinPukMeView.this.mPasswordEntry.reset(true, true);
                        }
                    } else if (2 == ret) {
                        if (KeyguardSimPinPukMeView.this.mSimUnlockProgressDialog != null) {
                            KeyguardSimPinPukMeView.this.mSimUnlockProgressDialog.hide();
                        }
                        KeyguardSimPinPukMeView.this.setInputInvalidAlertDialog("Exception happen, fail to unlock", true);
                        KeyguardSimPinPukMeView.this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
                    }
                    KeyguardSimPinPukMeView.this.mCallback.userActivity();
                    KeyguardSimPinPukMeView.this.mSimCheckInProgress = false;
                }
            }.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Dialog getSimRemainingAttemptsDialog(int remaining) {
        String msg = getPinPasswordErrorMessage(remaining);
        if (this.mRemainingAttemptsDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setMessage(msg);
            builder.setCancelable(false);
            builder.setNeutralButton(R.string.ok, (DialogInterface.OnClickListener) null);
            this.mRemainingAttemptsDialog = builder.create();
            this.mRemainingAttemptsDialog.getWindow().setType(2009);
        } else {
            this.mRemainingAttemptsDialog.setMessage(msg);
        }
        return this.mRemainingAttemptsDialog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Dialog getPukRemainingAttemptsDialog(int remaining) {
        String msg = getPukPasswordErrorMessage(remaining);
        if (this.mRemainingAttemptsDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setMessage(msg);
            builder.setCancelable(false);
            builder.setNeutralButton(R.string.ok, (DialogInterface.OnClickListener) null);
            this.mRemainingAttemptsDialog = builder.create();
            this.mRemainingAttemptsDialog.getWindow().setType(2009);
        } else {
            this.mRemainingAttemptsDialog.setMessage(msg);
        }
        return this.mRemainingAttemptsDialog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void simStateReadyProcess() {
        this.mNextRepollStatePhoneId = getNextRepollStatePhoneId();
        Log.d("KeyguardSimPinPukMeView", "simStateReadyProcess mNextRepollStatePhoneId =" + this.mNextRepollStatePhoneId);
        if (this.mNextRepollStatePhoneId != -1) {
            try {
                getSimUnlockProgressDialog().show();
                Log.d("KeyguardSimPinPukMeView", "repollIccStateForNetworkLock phoneId =" + this.mNextRepollStatePhoneId);
                int subId = KeyguardUtils.getSubIdUsingPhoneId(this.mNextRepollStatePhoneId);
                IMtkTelephonyEx.Stub.asInterface(ServiceManager.getService("phoneEx")).repollIccStateForNetworkLock(subId, true);
                return;
            } catch (RemoteException e) {
                Log.d("KeyguardSimPinPukMeView", "repollIccStateForNetworkLock exception caught");
                return;
            }
        }
        this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
    }

    private int getNextRepollStatePhoneId() {
        if (IccCardConstants.State.NETWORK_LOCKED == this.mLastSimState && KeyguardUtils.isMediatekSimMeLockSupport()) {
            for (int i = 0; i < KeyguardUtils.getNumOfPhone(); i++) {
                if (this.mSecurityModel.isPinPukOrMeRequiredOfPhoneId(i)) {
                    IccCardConstants.State simState = this.mUpdateMonitor.getSimStateOfPhoneId(i);
                    if (simState == IccCardConstants.State.NETWORK_LOCKED) {
                        return i;
                    }
                    return -1;
                }
            }
            return -1;
        }
        return -1;
    }

    public static class Toast {
        final Context mContext;
        private INotificationManager mService;
        View mView;
        int mY;
        final Handler mHandler = new Handler();
        int mGravity = 81;
        final TN mTN = new TN();

        public Toast(Context context) {
            this.mContext = context;
            this.mY = context.getResources().getDimensionPixelSize(android.R.dimen.indeterminate_progress_alpha_31);
        }

        public static Toast makeText(Context context, CharSequence text) {
            Toast result = new Toast(context);
            LayoutInflater inflate = (LayoutInflater) context.getSystemService("layout_inflater");
            View v = inflate.inflate(android.R.layout.preference_information_material, (ViewGroup) null);
            TextView tv = (TextView) v.findViewById(android.R.id.message);
            tv.setText(text);
            result.mView = v;
            return result;
        }

        public void show() {
            if (this.mView == null) {
                throw new RuntimeException("setView must have been called");
            }
            INotificationManager service = getService();
            String pkg = this.mContext.getPackageName();
            TN tn = this.mTN;
            try {
                service.enqueueToast(pkg, tn, 0);
            } catch (RemoteException e) {
            }
        }

        private INotificationManager getService() {
            if (this.mService != null) {
                return this.mService;
            }
            this.mService = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
            return this.mService;
        }

        private class TN extends ITransientNotification.Stub {
            WindowManagerImpl mWM;
            final Runnable mShow = new Runnable() { // from class: com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.Toast.TN.1
                @Override // java.lang.Runnable
                public void run() {
                    TN.this.handleShow();
                }
            };
            final Runnable mHide = new Runnable() { // from class: com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.Toast.TN.2
                @Override // java.lang.Runnable
                public void run() {
                    TN.this.handleHide();
                }
            };
            private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

            TN() {
                WindowManager.LayoutParams params = this.mParams;
                params.height = -2;
                params.width = -2;
                params.flags = 152;
                params.format = -3;
                params.windowAnimations = android.R.style.Animation.Toast;
                params.type = 2009;
                params.setTitle("Toast");
            }

            public void show(IBinder windowToken) {
                Toast.this.mHandler.post(this.mShow);
            }

            public void hide() {
                Toast.this.mHandler.post(this.mHide);
            }

            public void handleShow() {
                this.mWM = (WindowManagerImpl) Toast.this.mContext.getSystemService("window");
                int gravity = Toast.this.mGravity;
                this.mParams.gravity = gravity;
                if ((gravity & 7) == 7) {
                    this.mParams.horizontalWeight = 1.0f;
                }
                if ((gravity & com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBarOverlay) == 112) {
                    this.mParams.verticalWeight = 1.0f;
                }
                this.mParams.y = Toast.this.mY;
                if (Toast.this.mView != null) {
                    if (Toast.this.mView.getParent() != null) {
                        this.mWM.removeView(Toast.this.mView);
                    }
                    this.mWM.addView(Toast.this.mView, this.mParams);
                }
            }

            public void handleHide() {
                if (Toast.this.mView != null) {
                    if (Toast.this.mView.getParent() != null) {
                        this.mWM.removeView(Toast.this.mView);
                    }
                    Toast.this.mView = null;
                }
            }
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        Log.d("KeyguardSimPinPukMeView", "onWindowFocusChanged(hasWindowFocus = " + hasWindowFocus + ")");
        if (hasWindowFocus) {
            resetPasswordText(true, false);
            KeyguardUtils.requestImeStatusRefresh(this.mContext);
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void startAppearAnimation() {
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardSecurityView
    public boolean startDisappearAnimation(Runnable finishRunnable) {
        return false;
    }

    private void showDismissIfNeed(IccCardConstants.State simState) {
        if (simState == IccCardConstants.State.NETWORK_LOCKED && KeyguardUtils.isMediatekSimMeLockSupport() && KeyguardUtils.isDismissSimMeLockSupport()) {
            this.mDismissButton.setVisibility(0);
            this.mDismissButton.setOnClickListener(new View.OnClickListener() { // from class: com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView.6
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    KeyguardSimPinPukMeView.this.mUpdateMonitor.setSimmeDismissFlagOfPhoneId(KeyguardSimPinPukMeView.this.mPhoneId, true);
                    KeyguardSimPinPukMeView.this.resetPasswordText(true, false);
                    KeyguardSimPinPukMeView.this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
                }
            });
        } else {
            this.mDismissButton.setVisibility(4);
            this.mDismissButton.setOnClickListener(null);
        }
    }
}
