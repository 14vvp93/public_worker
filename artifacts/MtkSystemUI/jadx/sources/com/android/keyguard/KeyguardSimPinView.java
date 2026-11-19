package com.android.keyguard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.IccCardConstants;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardSimPinView extends KeyguardPinBasedInputView {
    private static final boolean DEBUG = KeyguardConstants.DEBUG_SIM_STATES;
    private CheckSimPin mCheckSimPinThread;
    KeyguardUtils mKeyguardUtils;
    private int mPhoneId;
    private int mRemainingAttempts;
    private AlertDialog mRemainingAttemptsDialog;
    private boolean mShowDefaultMessage;
    private ProgressDialog mSimUnlockProgressDialog;
    private int mSubId;
    KeyguardUpdateMonitorCallback mUpdateMonitorCallback;

    /* JADX INFO: renamed from: com.android.keyguard.KeyguardSimPinView$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$android$internal$telephony$IccCardConstants$State = new int[IccCardConstants.State.values().length];

        static {
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.ABSENT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.READY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public KeyguardSimPinView(Context context) {
        this(context, null);
    }

    public KeyguardSimPinView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSimUnlockProgressDialog = null;
        this.mShowDefaultMessage = true;
        this.mRemainingAttempts = -1;
        this.mSubId = -1;
        this.mPhoneId = 0;
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSimPinView.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onSimStateChangedUsingPhoneId(int phoneId, IccCardConstants.State simState) {
                if (KeyguardSimPinView.DEBUG) {
                    Log.d("KeyguardSimPinView", "onSimStateChangedUsingSubId: " + simState + ", phoneId=" + phoneId);
                }
                switch (AnonymousClass3.$SwitchMap$com$android$internal$telephony$IccCardConstants$State[simState.ordinal()]) {
                    case 1:
                        if (phoneId == KeyguardSimPinView.this.mPhoneId) {
                            KeyguardUpdateMonitor.getInstance(KeyguardSimPinView.this.getContext()).reportSimUnlocked(KeyguardSimPinView.this.mPhoneId);
                            if (KeyguardSimPinView.this.mCallback != null) {
                                KeyguardSimPinView.this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
                            }
                        }
                        break;
                    case 2:
                        KeyguardSimPinView.this.mRemainingAttempts = -1;
                        KeyguardSimPinView.this.resetState();
                        break;
                    default:
                        KeyguardSimPinView.this.resetState();
                        break;
                }
            }
        };
        this.mKeyguardUtils = new KeyguardUtils(context);
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView
    public void resetState() {
        super.resetState();
        if (DEBUG) {
            Log.v("KeyguardSimPinView", "Resetting state");
        }
        this.mSecurityMessageDisplay.setMessage(com.android.systemui.R.string.kg_sim_pin_instructions);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetState();
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView
    protected int getPromptReasonStringRes(int reason) {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getPinPasswordErrorMessage(int attemptsRemaining, boolean isDefault) {
        String displayMessage;
        if (attemptsRemaining == 0) {
            displayMessage = getContext().getString(com.android.systemui.R.string.kg_password_wrong_pin_code_pukked);
        } else if (attemptsRemaining > 0) {
            int msgId = isDefault ? com.android.systemui.R.plurals.kg_password_default_pin_message : com.android.systemui.R.plurals.kg_password_wrong_pin_code;
            String displayMessage2 = getContext().getResources().getQuantityString(msgId, attemptsRemaining, Integer.valueOf(attemptsRemaining));
            displayMessage = displayMessage2;
        } else {
            int msgId2 = isDefault ? com.android.systemui.R.string.kg_sim_pin_instructions : com.android.systemui.R.string.kg_password_pin_failed;
            displayMessage = getContext().getString(msgId2);
        }
        if (KeyguardEsimArea.isEsimLocked(this.mContext, this.mSubId)) {
            displayMessage = getResources().getString(com.android.systemui.R.string.kg_sim_lock_esim_instructions, displayMessage);
        }
        if (DEBUG) {
            Log.d("KeyguardSimPinView", "getPinPasswordErrorMessage: attemptsRemaining=" + attemptsRemaining + " displayMessage=" + displayMessage);
        }
        return displayMessage;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected boolean shouldLockout(long deadline) {
        return false;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected int getPasswordTextViewId() {
        return com.android.systemui.R.id.simPinEntry;
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPhoneId = KeyguardUpdateMonitor.getInstance(getContext()).getSimPinLockPhoneId();
        if (KeyguardUtils.getNumOfPhone() > 1) {
            View simIcon = findViewById(com.android.systemui.R.id.keyguard_sim);
            if (simIcon != null) {
                simIcon.setVisibility(8);
            }
            View simInfoMsg = findViewById(com.android.systemui.R.id.sim_info_message);
            if (simInfoMsg != null) {
                simInfoMsg.setVisibility(0);
            }
            dealwithSIMInfoChanged();
        }
        if (this.mEcaView instanceof EmergencyCarrierArea) {
            ((EmergencyCarrierArea) this.mEcaView).setCarrierTextVisible(true);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        KeyguardUpdateMonitor.getInstance(this.mContext).registerCallback(this.mUpdateMonitorCallback);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        KeyguardUpdateMonitor.getInstance(this.mContext).removeCallback(this.mUpdateMonitorCallback);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardSecurityView
    public void onPause() {
        if (this.mSimUnlockProgressDialog != null) {
            this.mSimUnlockProgressDialog.dismiss();
            this.mSimUnlockProgressDialog = null;
        }
    }

    private abstract class CheckSimPin extends Thread {
        private final String mPin;

        abstract void onSimCheckResponse(int i, int i2);

        protected CheckSimPin(String pin) {
            this.mPin = pin;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                if (KeyguardSimPinView.DEBUG) {
                    Log.v("KeyguardSimPinView", "call supplyPinReportResultForSubscriber(subid=" + KeyguardSimPinView.this.mSubId + ")");
                }
                Log.d("KeyguardSimPinView", "call supplyPinReportResultForSubscriber() mPhoneId = " + KeyguardSimPinView.this.mPhoneId);
                KeyguardUtils.getSubIdUsingPhoneId(KeyguardSimPinView.this.mPhoneId);
                final int[] result = ITelephony.Stub.asInterface(ServiceManager.checkService("phone")).supplyPinReportResultForSubscriber(KeyguardSimPinView.this.mSubId, this.mPin);
                if (KeyguardSimPinView.DEBUG) {
                    Log.v("KeyguardSimPinView", "supplyPinReportResult returned: " + result[0] + " " + result[1]);
                }
                KeyguardSimPinView.this.post(new Runnable() { // from class: com.android.keyguard.KeyguardSimPinView.CheckSimPin.1
                    @Override // java.lang.Runnable
                    public void run() {
                        CheckSimPin.this.onSimCheckResponse(result[0], result[1]);
                    }
                });
            } catch (RemoteException e) {
                Log.e("KeyguardSimPinView", "RemoteException for supplyPinReportResult:", e);
                KeyguardSimPinView.this.post(new Runnable() { // from class: com.android.keyguard.KeyguardSimPinView.CheckSimPin.2
                    @Override // java.lang.Runnable
                    public void run() {
                        CheckSimPin.this.onSimCheckResponse(2, -1);
                    }
                });
            }
        }
    }

    private Dialog getSimUnlockProgressDialog() {
        if (this.mSimUnlockProgressDialog == null) {
            this.mSimUnlockProgressDialog = new ProgressDialog(this.mContext);
            this.mSimUnlockProgressDialog.setMessage(this.mContext.getString(com.android.systemui.R.string.kg_sim_unlock_progress_dialog_message));
            this.mSimUnlockProgressDialog.setIndeterminate(true);
            this.mSimUnlockProgressDialog.setCancelable(false);
            this.mSimUnlockProgressDialog.getWindow().setType(2009);
        }
        return this.mSimUnlockProgressDialog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Dialog getSimRemainingAttemptsDialog(int remaining) {
        String msg = getPinPasswordErrorMessage(remaining, false);
        if (this.mRemainingAttemptsDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setMessage(msg);
            builder.setCancelable(false);
            builder.setNeutralButton(com.android.systemui.R.string.ok, (DialogInterface.OnClickListener) null);
            this.mRemainingAttemptsDialog = builder.create();
            this.mRemainingAttemptsDialog.getWindow().setType(2009);
        } else {
            this.mRemainingAttemptsDialog.setMessage(msg);
        }
        return this.mRemainingAttemptsDialog;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected void verifyPasswordAndUnlock() {
        String entry = this.mPasswordEntry.getText();
        if (entry.length() < 4) {
            this.mSecurityMessageDisplay.setMessage(com.android.systemui.R.string.kg_invalid_sim_pin_hint);
            resetPasswordText(true, true);
            this.mCallback.userActivity();
        } else {
            getSimUnlockProgressDialog().show();
            if (this.mCheckSimPinThread == null) {
                this.mCheckSimPinThread = new CheckSimPin(this.mPasswordEntry.getText()) { // from class: com.android.keyguard.KeyguardSimPinView.2
                    @Override // com.android.keyguard.KeyguardSimPinView.CheckSimPin
                    void onSimCheckResponse(final int result, final int attemptsRemaining) {
                        KeyguardSimPinView.this.post(new Runnable() { // from class: com.android.keyguard.KeyguardSimPinView.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                KeyguardSimPinView.this.mRemainingAttempts = attemptsRemaining;
                                if (KeyguardSimPinView.this.mSimUnlockProgressDialog != null) {
                                    KeyguardSimPinView.this.mSimUnlockProgressDialog.hide();
                                }
                                if (result == 0) {
                                    KeyguardUpdateMonitor.getInstance(KeyguardSimPinView.this.getContext()).reportSimUnlocked(KeyguardSimPinView.this.mPhoneId);
                                    KeyguardSimPinView.this.mRemainingAttempts = -1;
                                    KeyguardSimPinView.this.mShowDefaultMessage = true;
                                    if (KeyguardSimPinView.this.mCallback != null) {
                                        KeyguardSimPinView.this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
                                    }
                                } else {
                                    KeyguardSimPinView.this.mShowDefaultMessage = false;
                                    if (result == 1) {
                                        if (attemptsRemaining <= 2) {
                                            KeyguardSimPinView.this.getSimRemainingAttemptsDialog(attemptsRemaining).show();
                                        } else {
                                            KeyguardSimPinView.this.mSecurityMessageDisplay.setMessage(KeyguardSimPinView.this.getPinPasswordErrorMessage(attemptsRemaining, false));
                                        }
                                    } else {
                                        KeyguardSimPinView.this.mSecurityMessageDisplay.setMessage(KeyguardSimPinView.this.getContext().getString(com.android.systemui.R.string.kg_password_pin_failed));
                                    }
                                    if (KeyguardSimPinView.DEBUG) {
                                        Log.d("KeyguardSimPinView", "verifyPasswordAndUnlock  CheckSimPin.onSimCheckResponse: " + result + " attemptsRemaining=" + attemptsRemaining);
                                    }
                                    KeyguardSimPinView.this.resetPasswordText(true, result != 0);
                                }
                                KeyguardSimPinView.this.mCallback.userActivity();
                                KeyguardSimPinView.this.mCheckSimPinThread = null;
                            }
                        });
                    }
                };
                this.mCheckSimPinThread.start();
            }
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void startAppearAnimation() {
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardSecurityView
    public boolean startDisappearAnimation(Runnable finishRunnable) {
        return false;
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardSecurityView
    public CharSequence getTitle() {
        return getContext().getString(android.R.string.config_rearDisplayPhysicalAddress);
    }

    private void dealwithSIMInfoChanged() {
        String string;
        String operName = null;
        try {
            operName = this.mKeyguardUtils.getOptrNameUsingPhoneId(this.mPhoneId, this.mContext);
        } catch (IndexOutOfBoundsException e) {
            Log.w("KeyguardSimPinView", "getOptrNameBySlot exception, mPhoneId=" + this.mPhoneId);
        }
        if (DEBUG) {
            Log.i("KeyguardSimPinView", "dealwithSIMInfoChanged, mPhoneId=" + this.mPhoneId + ", operName=" + operName);
        }
        TextView forText = (TextView) findViewById(com.android.systemui.R.id.for_text);
        ImageView subIcon = (ImageView) findViewById(com.android.systemui.R.id.sub_icon);
        TextView simCardName = (TextView) findViewById(com.android.systemui.R.id.sim_card_name);
        if (operName == null) {
            if (DEBUG) {
                Log.d("KeyguardSimPinView", "mPhoneId " + this.mPhoneId + " is new subInfo record");
            }
            setForTextNewCard(this.mPhoneId, forText);
            subIcon.setVisibility(8);
            simCardName.setVisibility(8);
            return;
        }
        if (DEBUG) {
            Log.d("KeyguardSimPinView", "dealwithSIMInfoChanged, show operName for mPhoneId=" + this.mPhoneId);
        }
        forText.setText(this.mContext.getString(com.android.systemui.R.string.kg_slot_id, Integer.valueOf(this.mPhoneId + 1)) + " ");
        if (operName != null) {
            string = operName;
        } else {
            string = this.mContext.getString(com.android.systemui.R.string.kg_detecting_simcard);
        }
        simCardName.setText(string);
        Bitmap iconBitmap = this.mKeyguardUtils.getOptrBitmapUsingPhoneId(this.mPhoneId, this.mContext);
        subIcon.setImageBitmap(iconBitmap);
        subIcon.setVisibility(0);
        simCardName.setVisibility(0);
    }

    private void setForTextNewCard(int phoneId, TextView forText) {
        StringBuffer forSb = new StringBuffer();
        forSb.append(this.mContext.getString(com.android.systemui.R.string.kg_slot_id, Integer.valueOf(phoneId + 1)));
        forSb.append(" ");
        forSb.append(this.mContext.getText(com.android.systemui.R.string.kg_new_simcard));
        forText.setText(forSb.toString());
    }
}
