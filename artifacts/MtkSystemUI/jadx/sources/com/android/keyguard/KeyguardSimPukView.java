package com.android.keyguard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
public class KeyguardSimPukView extends KeyguardPinBasedInputView {
    private static final boolean DEBUG = KeyguardConstants.DEBUG;
    private CheckSimPuk mCheckSimPukThread;
    KeyguardUtils mKeyguardUtils;
    private int mPhoneId;
    private String mPinText;
    private String mPukText;
    private int mRemainingAttempts;
    private AlertDialog mRemainingAttemptsDialog;
    private boolean mShowDefaultMessage;
    private ProgressDialog mSimUnlockProgressDialog;
    private StateMachine mStateMachine;
    private int mSubId;
    KeyguardUpdateMonitorCallback mUpdateMonitorCallback;

    /* JADX INFO: renamed from: com.android.keyguard.KeyguardSimPukView$3, reason: invalid class name */
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

    public KeyguardSimPukView(Context context) {
        this(context, null);
    }

    public KeyguardSimPukView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSimUnlockProgressDialog = null;
        this.mShowDefaultMessage = true;
        this.mRemainingAttempts = -1;
        this.mStateMachine = new StateMachine();
        this.mSubId = -1;
        this.mPhoneId = 0;
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSimPukView.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onSimStateChangedUsingPhoneId(int phoneId, IccCardConstants.State simState) {
                if (KeyguardSimPukView.DEBUG) {
                    Log.d("KeyguardSimPukView", "onSimStateChangedUsingPhoneId: " + simState + ", phoneId=" + phoneId);
                }
                switch (AnonymousClass3.$SwitchMap$com$android$internal$telephony$IccCardConstants$State[simState.ordinal()]) {
                    case 1:
                    case 2:
                        if (phoneId == KeyguardSimPukView.this.mPhoneId) {
                            KeyguardUpdateMonitor.getInstance(KeyguardSimPukView.this.getContext()).reportSimUnlocked(KeyguardSimPukView.this.mPhoneId);
                            if (KeyguardSimPukView.this.mCallback != null) {
                                KeyguardSimPukView.this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
                            }
                        }
                        break;
                    default:
                        KeyguardSimPukView.this.resetState();
                        break;
                }
            }
        };
    }

    private class StateMachine {
        final int CONFIRM_PIN;
        final int DONE;
        final int ENTER_PIN;
        final int ENTER_PUK;
        private int state;

        private StateMachine() {
            this.ENTER_PUK = 0;
            this.ENTER_PIN = 1;
            this.CONFIRM_PIN = 2;
            this.DONE = 3;
            this.state = 0;
        }

        public void next() {
            int msg = 0;
            if (this.state == 0) {
                if (KeyguardSimPukView.this.checkPuk()) {
                    this.state = 1;
                    msg = com.android.systemui.R.string.kg_puk_enter_pin_hint;
                } else {
                    msg = com.android.systemui.R.string.kg_invalid_sim_puk_hint;
                }
            } else if (this.state == 1) {
                if (KeyguardSimPukView.this.checkPin()) {
                    this.state = 2;
                    msg = com.android.systemui.R.string.kg_enter_confirm_pin_hint;
                } else {
                    msg = com.android.systemui.R.string.kg_invalid_sim_pin_hint;
                }
            } else if (this.state == 2) {
                if (KeyguardSimPukView.this.confirmPin()) {
                    this.state = 3;
                    msg = com.android.systemui.R.string.keyguard_sim_unlock_progress_dialog_message;
                    KeyguardSimPukView.this.updateSim();
                } else {
                    this.state = 1;
                    msg = com.android.systemui.R.string.kg_invalid_confirm_pin_hint;
                }
            }
            KeyguardSimPukView.this.resetPasswordText(true, true);
            if (msg != 0) {
                KeyguardSimPukView.this.mSecurityMessageDisplay.setMessage(msg);
            }
        }

        void reset() {
            KeyguardSimPukView.this.mPinText = "";
            KeyguardSimPukView.this.mPukText = "";
            this.state = 0;
            KeyguardSimPukView.this.mSecurityMessageDisplay.setMessage(com.android.systemui.R.string.kg_puk_enter_puk_hint);
            KeyguardSimPukView.this.mPasswordEntry.requestFocus();
        }
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView
    protected int getPromptReasonStringRes(int reason) {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getPukPasswordErrorMessage(int attemptsRemaining, boolean isDefault) {
        String displayMessage;
        if (attemptsRemaining == 0) {
            displayMessage = getContext().getString(com.android.systemui.R.string.kg_password_wrong_puk_code_dead);
        } else if (attemptsRemaining > 0) {
            int msgId = isDefault ? com.android.systemui.R.plurals.kg_password_default_puk_message : com.android.systemui.R.plurals.kg_password_wrong_puk_code;
            displayMessage = getContext().getResources().getQuantityString(msgId, attemptsRemaining, Integer.valueOf(attemptsRemaining));
        } else {
            int msgId2 = isDefault ? com.android.systemui.R.string.kg_puk_enter_puk_hint : com.android.systemui.R.string.kg_password_puk_failed;
            displayMessage = getContext().getString(msgId2);
        }
        if (KeyguardEsimArea.isEsimLocked(this.mContext, this.mSubId)) {
            displayMessage = getResources().getString(com.android.systemui.R.string.kg_sim_lock_esim_instructions, displayMessage);
        }
        if (DEBUG) {
            Log.d("KeyguardSimPukView", "getPukPasswordErrorMessage: attemptsRemaining=" + attemptsRemaining + " displayMessage=" + displayMessage);
        }
        return displayMessage;
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView
    public void resetState() {
        super.resetState();
        this.mStateMachine.reset();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected boolean shouldLockout(long deadline) {
        return false;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected int getPasswordTextViewId() {
        return com.android.systemui.R.id.pukEntry;
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPhoneId = KeyguardUpdateMonitor.getInstance(getContext()).getSimPukLockPhoneId();
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

    private abstract class CheckSimPuk extends Thread {
        private final String mPin;
        private final String mPuk;

        abstract void onSimLockChangedResponse(int i, int i2);

        protected CheckSimPuk(String puk, String pin) {
            this.mPuk = puk;
            this.mPin = pin;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                Log.v("KeyguardSimPukView", "call supplyPukReportResultForSubscriber() mPhoneId = " + KeyguardSimPukView.this.mPhoneId);
                int subId = KeyguardUtils.getSubIdUsingPhoneId(KeyguardSimPukView.this.mPhoneId);
                final int[] result = ITelephony.Stub.asInterface(ServiceManager.checkService("phone")).supplyPukReportResultForSubscriber(subId, this.mPuk, this.mPin);
                Log.v("KeyguardSimPukView", "supplyPukReportResultForSubscriber returned: " + result[0] + " " + result[1]);
                KeyguardSimPukView.this.post(new Runnable() { // from class: com.android.keyguard.KeyguardSimPukView.CheckSimPuk.1
                    @Override // java.lang.Runnable
                    public void run() {
                        CheckSimPuk.this.onSimLockChangedResponse(result[0], result[1]);
                    }
                });
            } catch (RemoteException e) {
                Log.e("KeyguardSimPukView", "RemoteException for supplyPukReportResult:", e);
                KeyguardSimPukView.this.post(new Runnable() { // from class: com.android.keyguard.KeyguardSimPukView.CheckSimPuk.2
                    @Override // java.lang.Runnable
                    public void run() {
                        CheckSimPuk.this.onSimLockChangedResponse(2, -1);
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
            if (!(this.mContext instanceof Activity)) {
                this.mSimUnlockProgressDialog.getWindow().setType(2009);
            }
        }
        return this.mSimUnlockProgressDialog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Dialog getPukRemainingAttemptsDialog(int remaining) {
        String msg = getPukPasswordErrorMessage(remaining, false);
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

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkPuk() {
        if (this.mPasswordEntry.getText().length() == 8) {
            this.mPukText = this.mPasswordEntry.getText();
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkPin() {
        int length = this.mPasswordEntry.getText().length();
        if (length >= 4 && length <= 8) {
            this.mPinText = this.mPasswordEntry.getText();
            return true;
        }
        return false;
    }

    public boolean confirmPin() {
        return this.mPinText.equals(this.mPasswordEntry.getText());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSim() {
        getSimUnlockProgressDialog().show();
        if (this.mCheckSimPukThread == null) {
            this.mCheckSimPukThread = new CheckSimPuk(this.mPukText, this.mPinText) { // from class: com.android.keyguard.KeyguardSimPukView.2
                @Override // com.android.keyguard.KeyguardSimPukView.CheckSimPuk
                void onSimLockChangedResponse(final int result, final int attemptsRemaining) {
                    KeyguardSimPukView.this.post(new Runnable() { // from class: com.android.keyguard.KeyguardSimPukView.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (KeyguardSimPukView.this.mSimUnlockProgressDialog != null) {
                                KeyguardSimPukView.this.mSimUnlockProgressDialog.hide();
                            }
                            if (result == 0) {
                                KeyguardUpdateMonitor.getInstance(KeyguardSimPukView.this.getContext()).reportSimUnlocked(KeyguardSimPukView.this.mPhoneId);
                                KeyguardSimPukView.this.mRemainingAttempts = -1;
                                KeyguardSimPukView.this.mShowDefaultMessage = true;
                                if (KeyguardSimPukView.this.mCallback != null) {
                                    KeyguardSimPukView.this.mCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
                                }
                            } else {
                                KeyguardSimPukView.this.mShowDefaultMessage = false;
                                if (result == 1) {
                                    KeyguardSimPukView.this.mSecurityMessageDisplay.setMessage(KeyguardSimPukView.this.getPukPasswordErrorMessage(attemptsRemaining, false));
                                    if (attemptsRemaining <= 2) {
                                        KeyguardSimPukView.this.getPukRemainingAttemptsDialog(attemptsRemaining).show();
                                    } else {
                                        KeyguardSimPukView.this.mSecurityMessageDisplay.setMessage(KeyguardSimPukView.this.getPukPasswordErrorMessage(attemptsRemaining, false));
                                    }
                                } else {
                                    KeyguardSimPukView.this.mSecurityMessageDisplay.setMessage(KeyguardSimPukView.this.getContext().getString(com.android.systemui.R.string.kg_password_puk_failed));
                                }
                                if (KeyguardSimPukView.DEBUG) {
                                    Log.d("KeyguardSimPukView", "verifyPasswordAndUnlock  UpdateSim.onSimCheckResponse:  attemptsRemaining=" + attemptsRemaining);
                                }
                                KeyguardSimPukView.this.mStateMachine.reset();
                            }
                            KeyguardSimPukView.this.mCheckSimPukThread = null;
                        }
                    });
                }
            };
            this.mCheckSimPukThread.start();
        }
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected void verifyPasswordAndUnlock() {
        this.mStateMachine.next();
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
        return getContext().getString(android.R.string.config_recentsComponentName);
    }

    private void dealwithSIMInfoChanged() {
        String string;
        String operName = null;
        try {
            operName = this.mKeyguardUtils.getOptrNameUsingPhoneId(this.mPhoneId, this.mContext);
        } catch (IndexOutOfBoundsException e) {
            Log.w("KeyguardSimPukView", "getOptrNameBySlot exception, mPhoneId=" + this.mPhoneId);
        }
        if (DEBUG) {
            Log.i("KeyguardSimPukView", "dealwithSIMInfoChanged, mPhoneId=" + this.mPhoneId + ", operName=" + operName);
        }
        TextView forText = (TextView) findViewById(com.android.systemui.R.id.for_text);
        ImageView subIcon = (ImageView) findViewById(com.android.systemui.R.id.sub_icon);
        TextView simCardName = (TextView) findViewById(com.android.systemui.R.id.sim_card_name);
        if (operName == null) {
            if (DEBUG) {
                Log.d("KeyguardSimPukView", "mPhoneId " + this.mPhoneId + " is new subInfo record");
            }
            setForTextNewCard(this.mPhoneId, forText);
            subIcon.setVisibility(8);
            simCardName.setVisibility(8);
            return;
        }
        if (DEBUG) {
            Log.d("KeyguardSimPukView", "dealwithSIMInfoChanged, show operName for mPhoneId=" + this.mPhoneId);
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
