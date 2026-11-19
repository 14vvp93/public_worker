package com.android.systemui.statusbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.ViewGroup;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.IBatteryStats;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.Utils;
import com.android.systemui.Interpolators;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.statusbar.phone.LockIcon;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.IllegalFormatConversionException;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardIndicationController {
    private final IBatteryStats mBatteryInfo;
    private int mBatteryLevel;
    private int mChargingSpeed;
    private int mChargingWattage;
    private final Context mContext;
    private final DevicePolicyManager mDevicePolicyManager;
    private KeyguardIndicationTextView mDisclosure;
    private boolean mDozing;
    private final int mFastThreshold;
    private final Handler mHandler;
    private ViewGroup mIndicationArea;
    private int mInitialTextColor;
    private LockIcon mLockIcon;
    private String mMessageToShowOnScreenOn;
    private boolean mPowerCharged;
    private boolean mPowerPluggedIn;
    private boolean mPowerPluggedInWired;
    private String mRestingIndication;
    private final int mSlowThreshold;
    private StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    private KeyguardIndicationTextView mTextView;
    private final BroadcastReceiver mTickReceiver;
    private CharSequence mTransientIndication;
    private int mTransientTextColor;
    private KeyguardUpdateMonitorCallback mUpdateMonitorCallback;
    private final UserManager mUserManager;
    private boolean mVisible;
    private final SettableWakeLock mWakeLock;

    public KeyguardIndicationController(Context context, ViewGroup indicationArea, LockIcon lockIcon) {
        this(context, indicationArea, lockIcon, WakeLock.createPartial(context, "Doze:KeyguardIndication"));
        registerCallbacks(KeyguardUpdateMonitor.getInstance(context));
    }

    @VisibleForTesting
    KeyguardIndicationController(Context context, ViewGroup indicationArea, LockIcon lockIcon, WakeLock wakeLock) {
        this.mTickReceiver = new AnonymousClass2();
        this.mHandler = new Handler() { // from class: com.android.systemui.statusbar.KeyguardIndicationController.3
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    KeyguardIndicationController.this.hideTransientIndication();
                } else if (msg.what == 2) {
                    KeyguardIndicationController.this.mLockIcon.setTransientFpError(false);
                }
            }
        };
        this.mContext = context;
        this.mIndicationArea = indicationArea;
        this.mTextView = (KeyguardIndicationTextView) indicationArea.findViewById(R.id.keyguard_indication_text);
        this.mInitialTextColor = this.mTextView != null ? this.mTextView.getCurrentTextColor() : -1;
        this.mDisclosure = (KeyguardIndicationTextView) indicationArea.findViewById(R.id.keyguard_indication_enterprise_disclosure);
        this.mLockIcon = lockIcon;
        this.mWakeLock = new SettableWakeLock(wakeLock);
        Resources res = context.getResources();
        this.mSlowThreshold = res.getInteger(R.integer.config_chargingSlowlyThreshold);
        this.mFastThreshold = res.getInteger(R.integer.config_chargingFastThreshold);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mBatteryInfo = IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats"));
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        updateDisclosure();
    }

    private void registerCallbacks(KeyguardUpdateMonitor monitor) {
        monitor.registerCallback(getKeyguardCallback());
    }

    protected KeyguardUpdateMonitorCallback getKeyguardCallback() {
        if (this.mUpdateMonitorCallback == null) {
            this.mUpdateMonitorCallback = new BaseKeyguardCallback();
        }
        return this.mUpdateMonitorCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDisclosure() {
        if (this.mDevicePolicyManager == null) {
            return;
        }
        if (!this.mDozing && this.mDevicePolicyManager.isDeviceManaged()) {
            CharSequence organizationName = this.mDevicePolicyManager.getDeviceOwnerOrganizationName();
            if (organizationName != null) {
                this.mDisclosure.switchIndication(this.mContext.getResources().getString(R.string.do_disclosure_with_name, organizationName));
            } else {
                this.mDisclosure.switchIndication(R.string.do_disclosure_generic);
            }
            this.mDisclosure.setVisibility(0);
            return;
        }
        this.mDisclosure.setVisibility(8);
    }

    public void setVisible(boolean visible) {
        this.mVisible = visible;
        this.mIndicationArea.setVisibility(visible ? 0 : 8);
        if (visible) {
            if (!this.mHandler.hasMessages(1)) {
                hideTransientIndication();
            }
            updateIndication(false);
        } else if (!visible) {
            hideTransientIndication();
        }
    }

    protected String getTrustGrantedIndication() {
        return null;
    }

    protected String getTrustManagedIndication() {
        return null;
    }

    public void hideTransientIndicationDelayed(long delayMs) {
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), delayMs);
    }

    public void showTransientIndication(int transientIndication) {
        showTransientIndication(this.mContext.getResources().getString(transientIndication));
    }

    public void showTransientIndication(CharSequence transientIndication) {
        showTransientIndication(transientIndication, this.mInitialTextColor);
    }

    public void showTransientIndication(CharSequence transientIndication, int textColor) {
        this.mTransientIndication = transientIndication;
        this.mTransientTextColor = textColor;
        this.mHandler.removeMessages(1);
        if (this.mDozing && !TextUtils.isEmpty(this.mTransientIndication)) {
            this.mWakeLock.setAcquired(true);
            hideTransientIndicationDelayed(5000L);
        }
        updateIndication(false);
    }

    public void hideTransientIndication() {
        if (this.mTransientIndication != null) {
            this.mTransientIndication = null;
            this.mHandler.removeMessages(1);
            updateIndication(false);
        }
    }

    protected final void updateIndication(boolean animate) {
        if (TextUtils.isEmpty(this.mTransientIndication)) {
            this.mWakeLock.setAcquired(false);
        }
        if (this.mVisible) {
            if (this.mDozing) {
                this.mTextView.setTextColor(-1);
                if (!TextUtils.isEmpty(this.mTransientIndication)) {
                    this.mTextView.switchIndication(this.mTransientIndication);
                    return;
                }
                if (this.mPowerPluggedIn) {
                    String indication = computePowerIndication();
                    if (animate) {
                        animateText(this.mTextView, indication);
                        return;
                    } else {
                        this.mTextView.switchIndication(indication);
                        return;
                    }
                }
                String percentage = NumberFormat.getPercentInstance().format(this.mBatteryLevel / 100.0f);
                this.mTextView.switchIndication(percentage);
                return;
            }
            KeyguardUpdateMonitor updateMonitor = KeyguardUpdateMonitor.getInstance(this.mContext);
            int userId = KeyguardUpdateMonitor.getCurrentUser();
            String trustGrantedIndication = getTrustGrantedIndication();
            String trustManagedIndication = getTrustManagedIndication();
            if (!this.mUserManager.isUserUnlocked(userId)) {
                this.mTextView.switchIndication(android.R.string.date_picker_decrement_year_button);
                this.mTextView.setTextColor(this.mInitialTextColor);
                return;
            }
            if (!TextUtils.isEmpty(this.mTransientIndication)) {
                this.mTextView.switchIndication(this.mTransientIndication);
                this.mTextView.setTextColor(this.mTransientTextColor);
                return;
            }
            if (!TextUtils.isEmpty(trustGrantedIndication) && updateMonitor.getUserHasTrust(userId)) {
                this.mTextView.switchIndication(trustGrantedIndication);
                this.mTextView.setTextColor(this.mInitialTextColor);
                return;
            }
            if (this.mPowerPluggedIn) {
                String indication2 = computePowerIndication();
                this.mTextView.setTextColor(this.mInitialTextColor);
                if (animate) {
                    animateText(this.mTextView, indication2);
                    return;
                } else {
                    this.mTextView.switchIndication(indication2);
                    return;
                }
            }
            if (!TextUtils.isEmpty(trustManagedIndication) && updateMonitor.getUserTrustIsManaged(userId) && !updateMonitor.getUserHasTrust(userId)) {
                this.mTextView.switchIndication(trustManagedIndication);
                this.mTextView.setTextColor(this.mInitialTextColor);
            } else {
                this.mTextView.switchIndication(this.mRestingIndication);
                this.mTextView.setTextColor(this.mInitialTextColor);
            }
        }
    }

    private void animateText(final KeyguardIndicationTextView textView, final String indication) {
        final int yTranslation = this.mContext.getResources().getInteger(R.integer.wired_charging_keyguard_text_animation_distance);
        int animateUpDuration = this.mContext.getResources().getInteger(R.integer.wired_charging_keyguard_text_animation_duration_up);
        final int animateDownDuration = this.mContext.getResources().getInteger(R.integer.wired_charging_keyguard_text_animation_duration_down);
        textView.animate().translationYBy(yTranslation).setInterpolator(Interpolators.LINEAR).setDuration(animateUpDuration).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.KeyguardIndicationController.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                textView.switchIndication(indication);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                textView.animate().setDuration(animateDownDuration).setInterpolator(Interpolators.BOUNCE).translationYBy((-1) * yTranslation).setListener(null);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String computePowerIndication() {
        int chargingId;
        if (this.mPowerCharged) {
            return this.mContext.getResources().getString(R.string.keyguard_charged);
        }
        long chargingTimeRemaining = 0;
        try {
            chargingTimeRemaining = this.mBatteryInfo.computeChargeTimeRemaining();
        } catch (RemoteException e) {
            Log.e("KeyguardIndication", "Error calling IBatteryStats: ", e);
        }
        boolean hasChargingTime = chargingTimeRemaining > 0;
        int i = this.mChargingSpeed;
        if (i != 0) {
            if (i == 2) {
                if (hasChargingTime) {
                    chargingId = R.string.keyguard_indication_charging_time_fast;
                } else {
                    chargingId = R.string.keyguard_plugged_in_charging_fast;
                }
            } else if (hasChargingTime) {
                chargingId = R.string.keyguard_indication_charging_time;
            } else {
                chargingId = R.string.keyguard_plugged_in;
            }
        } else if (hasChargingTime) {
            chargingId = R.string.keyguard_indication_charging_time_slowly;
        } else {
            chargingId = R.string.keyguard_plugged_in_charging_slowly;
        }
        String percentage = NumberFormat.getPercentInstance().format(this.mBatteryLevel / 100.0f);
        if (hasChargingTime) {
            String chargingTimeFormatted = Formatter.formatShortElapsedTimeRoundingUpToMinutes(this.mContext, chargingTimeRemaining);
            try {
                return this.mContext.getResources().getString(chargingId, chargingTimeFormatted, percentage);
            } catch (IllegalFormatConversionException e2) {
                return this.mContext.getResources().getString(chargingId, chargingTimeFormatted);
            }
        }
        try {
            return this.mContext.getResources().getString(chargingId, percentage);
        } catch (IllegalFormatConversionException e3) {
            return this.mContext.getResources().getString(chargingId);
        }
    }

    public void setStatusBarKeyguardViewManager(StatusBarKeyguardViewManager statusBarKeyguardViewManager) {
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.KeyguardIndicationController$2, reason: invalid class name */
    class AnonymousClass2 extends BroadcastReceiver {
        AnonymousClass2() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            KeyguardIndicationController.this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.-$$Lambda$KeyguardIndicationController$2$zESedvrtrUKx0fqXwwlgga5A3WM
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardIndicationController.AnonymousClass2.lambda$onReceive$0(this.f$0);
                }
            });
        }

        public static /* synthetic */ void lambda$onReceive$0(AnonymousClass2 anonymousClass2) {
            if (KeyguardIndicationController.this.mVisible) {
                KeyguardIndicationController.this.updateIndication(false);
            }
        }
    }

    public void setDozing(boolean dozing) {
        if (this.mDozing == dozing) {
            return;
        }
        this.mDozing = dozing;
        updateIndication(false);
        updateDisclosure();
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("KeyguardIndicationController:");
        pw.println("  mTransientTextColor: " + Integer.toHexString(this.mTransientTextColor));
        pw.println("  mInitialTextColor: " + Integer.toHexString(this.mInitialTextColor));
        pw.println("  mPowerPluggedInWired: " + this.mPowerPluggedInWired);
        pw.println("  mPowerPluggedIn: " + this.mPowerPluggedIn);
        pw.println("  mPowerCharged: " + this.mPowerCharged);
        pw.println("  mChargingSpeed: " + this.mChargingSpeed);
        pw.println("  mChargingWattage: " + this.mChargingWattage);
        pw.println("  mMessageToShowOnScreenOn: " + this.mMessageToShowOnScreenOn);
        pw.println("  mDozing: " + this.mDozing);
        pw.println("  mBatteryLevel: " + this.mBatteryLevel);
        StringBuilder sb = new StringBuilder();
        sb.append("  mTextView.getText(): ");
        sb.append((Object) (this.mTextView == null ? null : this.mTextView.getText()));
        pw.println(sb.toString());
        pw.println("  computePowerIndication(): " + computePowerIndication());
    }

    protected class BaseKeyguardCallback extends KeyguardUpdateMonitorCallback {
        private int mLastSuccessiveErrorMessage = -1;

        protected BaseKeyguardCallback() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onRefreshBatteryInfo(KeyguardUpdateMonitor.BatteryStatus status) {
            boolean isChargingOrFull = status.status == 2 || status.status == 5;
            boolean wasPluggedIn = KeyguardIndicationController.this.mPowerPluggedIn;
            KeyguardIndicationController.this.mPowerPluggedInWired = status.isPluggedInWired() && isChargingOrFull;
            KeyguardIndicationController.this.mPowerPluggedIn = status.isPluggedIn() && isChargingOrFull;
            KeyguardIndicationController.this.mPowerCharged = status.isCharged();
            KeyguardIndicationController.this.mChargingWattage = status.maxChargingWattage;
            KeyguardIndicationController.this.mChargingSpeed = status.getChargingSpeed(KeyguardIndicationController.this.mSlowThreshold, KeyguardIndicationController.this.mFastThreshold);
            KeyguardIndicationController.this.mBatteryLevel = status.level;
            KeyguardIndicationController.this.updateIndication(!wasPluggedIn && KeyguardIndicationController.this.mPowerPluggedInWired);
            if (KeyguardIndicationController.this.mDozing) {
                if (!wasPluggedIn && KeyguardIndicationController.this.mPowerPluggedIn) {
                    KeyguardIndicationController.this.showTransientIndication(KeyguardIndicationController.this.computePowerIndication());
                    KeyguardIndicationController.this.hideTransientIndicationDelayed(5000L);
                } else if (wasPluggedIn && !KeyguardIndicationController.this.mPowerPluggedIn) {
                    KeyguardIndicationController.this.hideTransientIndication();
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onKeyguardVisibilityChanged(boolean showing) {
            if (showing) {
                KeyguardIndicationController.this.updateDisclosure();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onFingerprintHelp(int msgId, String helpString) {
            KeyguardUpdateMonitor updateMonitor = KeyguardUpdateMonitor.getInstance(KeyguardIndicationController.this.mContext);
            if (updateMonitor.isUnlockingWithFingerprintAllowed()) {
                int errorColor = Utils.getColorError(KeyguardIndicationController.this.mContext);
                if (KeyguardIndicationController.this.mStatusBarKeyguardViewManager.isBouncerShowing()) {
                    KeyguardIndicationController.this.mStatusBarKeyguardViewManager.showBouncerMessage(helpString, errorColor);
                } else if (updateMonitor.isScreenOn()) {
                    KeyguardIndicationController.this.mLockIcon.setTransientFpError(true);
                    KeyguardIndicationController.this.showTransientIndication(helpString, errorColor);
                    KeyguardIndicationController.this.hideTransientIndicationDelayed(1300L);
                    KeyguardIndicationController.this.mHandler.removeMessages(2);
                    KeyguardIndicationController.this.mHandler.sendMessageDelayed(KeyguardIndicationController.this.mHandler.obtainMessage(2), 1300L);
                }
                this.mLastSuccessiveErrorMessage = -1;
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onFingerprintError(int msgId, String errString) {
            KeyguardUpdateMonitor updateMonitor = KeyguardUpdateMonitor.getInstance(KeyguardIndicationController.this.mContext);
            if ((updateMonitor.isUnlockingWithFingerprintAllowed() || msgId == 9) && msgId != 5) {
                int errorColor = Utils.getColorError(KeyguardIndicationController.this.mContext);
                if (KeyguardIndicationController.this.mStatusBarKeyguardViewManager.isBouncerShowing()) {
                    if (this.mLastSuccessiveErrorMessage != msgId) {
                        KeyguardIndicationController.this.mStatusBarKeyguardViewManager.showBouncerMessage(errString, errorColor);
                    }
                } else if (!updateMonitor.isScreenOn()) {
                    KeyguardIndicationController.this.mMessageToShowOnScreenOn = errString;
                } else {
                    KeyguardIndicationController.this.showTransientIndication(errString, errorColor);
                    KeyguardIndicationController.this.hideTransientIndicationDelayed(5000L);
                }
                this.mLastSuccessiveErrorMessage = msgId;
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onTrustAgentErrorMessage(CharSequence message) {
            int errorColor = Utils.getColorError(KeyguardIndicationController.this.mContext);
            KeyguardIndicationController.this.showTransientIndication(message, errorColor);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onScreenTurnedOn() {
            if (KeyguardIndicationController.this.mMessageToShowOnScreenOn != null) {
                int errorColor = Utils.getColorError(KeyguardIndicationController.this.mContext);
                KeyguardIndicationController.this.showTransientIndication(KeyguardIndicationController.this.mMessageToShowOnScreenOn, errorColor);
                KeyguardIndicationController.this.hideTransientIndicationDelayed(5000L);
                KeyguardIndicationController.this.mMessageToShowOnScreenOn = null;
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onFingerprintRunningStateChanged(boolean running) {
            if (running) {
                KeyguardIndicationController.this.mMessageToShowOnScreenOn = null;
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onFingerprintAuthenticated(int userId) {
            super.onFingerprintAuthenticated(userId);
            this.mLastSuccessiveErrorMessage = -1;
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onFingerprintAuthFailed() {
            super.onFingerprintAuthFailed();
            this.mLastSuccessiveErrorMessage = -1;
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onUserUnlocked() {
            if (KeyguardIndicationController.this.mVisible) {
                KeyguardIndicationController.this.updateIndication(false);
            }
        }
    }
}
