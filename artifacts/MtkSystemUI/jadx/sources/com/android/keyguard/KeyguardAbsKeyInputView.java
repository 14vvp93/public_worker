package com.android.keyguard;

import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public abstract class KeyguardAbsKeyInputView extends LinearLayout implements EmergencyButton.EmergencyButtonCallback, KeyguardSecurityView {
    protected KeyguardSecurityCallback mCallback;
    private CountDownTimer mCountdownTimer;
    private boolean mDismissing;
    protected View mEcaView;
    protected boolean mEnableHaptics;
    protected LockPatternUtils mLockPatternUtils;
    protected AsyncTask<?, ?, ?> mPendingLockCheck;
    protected SecurityMessageDisplay mSecurityMessageDisplay;

    protected abstract String getPasswordText();

    protected abstract int getPasswordTextViewId();

    protected abstract int getPromptReasonStringRes(int i);

    protected abstract void resetPasswordText(boolean z, boolean z2);

    protected abstract void resetState();

    protected abstract void setPasswordEntryEnabled(boolean z);

    protected abstract void setPasswordEntryInputEnabled(boolean z);

    public KeyguardAbsKeyInputView(Context context) {
        this(context, null);
    }

    public KeyguardAbsKeyInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCountdownTimer = null;
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void setKeyguardCallback(KeyguardSecurityCallback callback) {
        this.mCallback = callback;
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void setLockPatternUtils(LockPatternUtils utils) {
        this.mLockPatternUtils = utils;
        this.mEnableHaptics = this.mLockPatternUtils.isTactileFeedbackEnabled();
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void reset() {
        this.mDismissing = false;
        resetPasswordText(false, false);
        long deadline = this.mLockPatternUtils.getLockoutAttemptDeadline(KeyguardUpdateMonitor.getCurrentUser());
        if (shouldLockout(deadline)) {
            handleAttemptLockout(deadline);
        } else {
            resetState();
        }
    }

    protected boolean shouldLockout(long deadline) {
        return deadline != 0;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        this.mSecurityMessageDisplay = KeyguardMessageArea.findSecurityMessageDisplay(this);
        this.mEcaView = findViewById(com.android.systemui.R.id.keyguard_selector_fade_container);
        EmergencyButton button = (EmergencyButton) findViewById(com.android.systemui.R.id.emergency_call_button);
        if (button != null) {
            button.setCallback(this);
        }
    }

    @Override // com.android.keyguard.EmergencyButton.EmergencyButtonCallback
    public void onEmergencyButtonClickedWhenInCall() {
        this.mCallback.reset();
    }

    protected int getWrongPasswordStringId() {
        return com.android.systemui.R.string.kg_wrong_password;
    }

    protected void verifyPasswordAndUnlock() {
        if (this.mDismissing) {
            return;
        }
        String entry = getPasswordText();
        setPasswordEntryInputEnabled(false);
        if (this.mPendingLockCheck != null) {
            this.mPendingLockCheck.cancel(false);
        }
        final int userId = KeyguardUpdateMonitor.getCurrentUser();
        if (entry.length() <= 3) {
            setPasswordEntryInputEnabled(true);
            onPasswordChecked(userId, false, 0, false);
            return;
        }
        ContentResolver cr = this.mContext.getContentResolver();
        String pin = Settings.System.getString(cr, "lockscreen_disabled_pin");
        if (pin != null && pin.equals(entry)) {
            File file = new File("/apd/lockscreen_disabled");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.getFD().sync();
                    fos.close();
                } catch (IOException e) {
                    Log.e("KeyguardAbsKeyInputView", "create file fail", e);
                }
            }
            this.mLockPatternUtils.clearLock(pin, userId);
            this.mLockPatternUtils.setLockScreenDisabled(true, userId);
            this.mSecurityMessageDisplay.setMessage("Screen lock is disabled");
            setPasswordEntryInputEnabled(true);
            this.mPendingLockCheck = null;
            resetPasswordText(true, false);
            return;
        }
        if (LatencyTracker.isEnabled(this.mContext)) {
            LatencyTracker.getInstance(this.mContext).onActionStart(3);
            LatencyTracker.getInstance(this.mContext).onActionStart(4);
        }
        this.mPendingLockCheck = LockPatternChecker.checkPassword(this.mLockPatternUtils, entry, userId, new LockPatternChecker.OnCheckCallback() { // from class: com.android.keyguard.KeyguardAbsKeyInputView.1
            public void onEarlyMatched() {
                if (LatencyTracker.isEnabled(KeyguardAbsKeyInputView.this.mContext)) {
                    LatencyTracker.getInstance(KeyguardAbsKeyInputView.this.mContext).onActionEnd(3);
                }
                KeyguardAbsKeyInputView.this.onPasswordChecked(userId, true, 0, true);
            }

            public void onChecked(boolean matched, int timeoutMs) {
                if (LatencyTracker.isEnabled(KeyguardAbsKeyInputView.this.mContext)) {
                    LatencyTracker.getInstance(KeyguardAbsKeyInputView.this.mContext).onActionEnd(4);
                }
                KeyguardAbsKeyInputView.this.setPasswordEntryInputEnabled(true);
                KeyguardAbsKeyInputView.this.mPendingLockCheck = null;
                if (!matched) {
                    KeyguardAbsKeyInputView.this.onPasswordChecked(userId, false, timeoutMs, true);
                }
            }

            public void onCancelled() {
                if (LatencyTracker.isEnabled(KeyguardAbsKeyInputView.this.mContext)) {
                    LatencyTracker.getInstance(KeyguardAbsKeyInputView.this.mContext).onActionEnd(4);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPasswordChecked(int userId, boolean matched, int timeoutMs, boolean isValidPassword) {
        boolean dismissKeyguard = KeyguardUpdateMonitor.getCurrentUser() == userId;
        if (matched) {
            this.mCallback.reportUnlockAttempt(userId, true, 0);
            if (dismissKeyguard) {
                this.mDismissing = true;
                this.mCallback.dismiss(true, userId);
            }
        } else {
            if (isValidPassword) {
                this.mCallback.reportUnlockAttempt(userId, false, timeoutMs);
                if (timeoutMs > 0) {
                    long deadline = this.mLockPatternUtils.setLockoutAttemptDeadline(userId, timeoutMs);
                    handleAttemptLockout(deadline);
                }
            }
            if (timeoutMs == 0) {
                this.mSecurityMessageDisplay.setMessage(getWrongPasswordStringId());
            }
        }
        resetPasswordText(true, !matched);
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.keyguard.KeyguardAbsKeyInputView$2] */
    protected void handleAttemptLockout(long elapsedRealtimeDeadline) {
        setPasswordEntryEnabled(false);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long secondsInFuture = (long) Math.ceil((elapsedRealtimeDeadline - elapsedRealtime) / 1000.0d);
        if (this.mCountdownTimer != null) {
            this.mCountdownTimer.cancel();
        }
        this.mCountdownTimer = new CountDownTimer(secondsInFuture * 1000, 1000L) { // from class: com.android.keyguard.KeyguardAbsKeyInputView.2
            @Override // android.os.CountDownTimer
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) Math.round(millisUntilFinished / 1000.0d);
                KeyguardAbsKeyInputView.this.mSecurityMessageDisplay.setMessage(KeyguardAbsKeyInputView.this.mContext.getResources().getQuantityString(com.android.systemui.R.plurals.kg_too_many_failed_attempts_countdown, secondsRemaining, Integer.valueOf(secondsRemaining)));
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                KeyguardAbsKeyInputView.this.mSecurityMessageDisplay.setMessage("");
                KeyguardAbsKeyInputView.this.resetState();
            }
        }.start();
    }

    protected void onUserInput() {
        if (this.mCallback != null) {
            this.mCallback.userActivity();
        }
        this.mSecurityMessageDisplay.setMessage("");
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 0) {
            onUserInput();
            return false;
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public boolean needsInput() {
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void onPause() {
        if (this.mCountdownTimer != null) {
            this.mCountdownTimer.cancel();
            this.mCountdownTimer = null;
        }
        if (this.mPendingLockCheck != null) {
            this.mPendingLockCheck.cancel(false);
            this.mPendingLockCheck = null;
        }
        reset();
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void onResume(int reason) {
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void showPromptReason(int reason) {
        int promtReasonStringRes;
        if (reason != 0 && (promtReasonStringRes = getPromptReasonStringRes(reason)) != 0) {
            this.mSecurityMessageDisplay.setMessage(promtReasonStringRes);
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void showMessage(CharSequence message, int color) {
        this.mSecurityMessageDisplay.setNextMessageColor(color);
        this.mSecurityMessageDisplay.setMessage(message);
    }

    public void doHapticKeyClick() {
        if (this.mEnableHaptics) {
            performHapticFeedback(1, 3);
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public boolean startDisappearAnimation(Runnable finishRunnable) {
        return false;
    }
}
