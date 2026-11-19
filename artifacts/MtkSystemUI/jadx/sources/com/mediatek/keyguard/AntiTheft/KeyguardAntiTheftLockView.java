package com.mediatek.keyguard.AntiTheft;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import com.android.keyguard.EmergencyCarrierArea;
import com.android.keyguard.KeyguardMessageArea;
import com.android.keyguard.KeyguardPinBasedInputView;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecurityMessageDisplay;
import com.android.systemui.R;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardAntiTheftLockView extends KeyguardPinBasedInputView {
    private static int mReportUnlockAttemptTimeout = 30000;
    private AntiTheftManager mAntiTheftManager;
    private ViewGroup mBouncerFrameView;
    private Context mContext;
    private SecurityMessageDisplay mSecurityMessageDisplay;

    public KeyguardAntiTheftLockView(Context context) {
        this(context, null);
    }

    public KeyguardAntiTheftLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mAntiTheftManager = AntiTheftManager.getInstance(null, null, null);
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView
    protected void resetState() {
        super.resetState();
        updateKeypadVisibility();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected int getPasswordTextViewId() {
        return R.id.antiTheftPinEntry;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    protected void verifyPasswordAndUnlock() {
        String entry = getPasswordText();
        boolean isLockOut = false;
        int userId = KeyguardUpdateMonitor.getCurrentUser();
        Log.d("KeyguardAntiTheftLockView", "verifyPasswordAndUnlock is called.");
        if (AntiTheftManager.getInstance(null, null, null).checkPassword(entry)) {
            this.mCallback.reportUnlockAttempt(userId, true, mReportUnlockAttemptTimeout);
            this.mCallback.dismiss(true, userId);
            AntiTheftManager.getInstance(null, null, null).adjustStatusBarLocked();
        } else if (entry.length() > 3) {
            Log.d("KeyguardAntiTheftLockView", "verifyPasswordAndUnlock fail");
            this.mCallback.reportUnlockAttempt(userId, false, mReportUnlockAttemptTimeout);
            if (KeyguardUpdateMonitor.getInstance(this.mContext).getFailedUnlockAttempts(userId) % 5 == 0) {
                long deadline = this.mLockPatternUtils.setLockoutAttemptDeadline(KeyguardUpdateMonitor.getCurrentUser(), mReportUnlockAttemptTimeout);
                handleAttemptLockout(deadline);
                isLockOut = true;
            }
            this.mSecurityMessageDisplay.setMessage(getWrongPasswordStringId());
        }
        setPasswordEntryEnabled(true);
        resetPasswordText(true, false);
        if (isLockOut) {
            setPasswordEntryEnabled(false);
        }
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView, android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d("KeyguardAntiTheftLockView", "onFinishInflate() is called");
        this.mBouncerFrameView = (ViewGroup) findViewById(R.id.keyguard_bouncer_frame);
        this.mSecurityMessageDisplay = KeyguardMessageArea.findSecurityMessageDisplay(this);
        if (!AntiTheftManager.isKeypadNeeded()) {
            Log.d("KeyguardAntiTheftLockView", "onFinishInflate, not need keypad");
            this.mBouncerFrameView.setVisibility(4);
        }
        AntiTheftManager.getInstance(null, null, null).doBindAntiThftLockServices();
        if (this.mEcaView instanceof EmergencyCarrierArea) {
            ((EmergencyCarrierArea) this.mEcaView).setCarrierTextVisible(true);
        }
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public int getWrongPasswordStringId() {
        return R.string.kg_wrong_pin;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        AntiTheftManager.getInstance(null, null, null).doBindAntiThftLockServices();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        Log.d("KeyguardAntiTheftLockView", "onDetachedFromWindow() is called.");
        super.onDetachedFromWindow();
        this.mAntiTheftManager.setSecurityViewCallback(null);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardSecurityView
    public void onPause() {
        Log.d("KeyguardAntiTheftLockView", "onPause");
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardSecurityView
    public void onResume(int reason) {
        super.onResume(reason);
        Log.d("KeyguardAntiTheftLockView", "onResume");
        this.mSecurityMessageDisplay.setMessage(AntiTheftManager.ANTITHEFT_NONEED_PRINT_TEXT);
        AntiTheftManager.getInstance(null, null, null).doBindAntiThftLockServices();
        this.mAntiTheftManager.setSecurityViewCallback(this.mCallback);
        updateKeypadVisibility();
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void startAppearAnimation() {
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardSecurityView
    public boolean startDisappearAnimation(Runnable finishRunnable) {
        return false;
    }

    private void updateKeypadVisibility() {
        if (AntiTheftManager.isKeypadNeeded()) {
            this.mBouncerFrameView.setVisibility(0);
        } else {
            this.mBouncerFrameView.setVisibility(4);
        }
    }
}
