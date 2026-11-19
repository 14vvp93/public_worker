package com.android.keyguard;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.telecom.TelecomManager;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.Slog;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.util.EmergencyAffordanceManager;
import com.android.internal.widget.LockPatternUtils;
import com.mediatek.keyguard.AntiTheft.AntiTheftManager;
import com.mediatek.keyguard.ext.IEmergencyButtonExt;
import com.mediatek.keyguard.ext.OpKeyguardCustomizationFactoryBase;

/* JADX INFO: loaded from: classes.dex */
public class EmergencyButton extends Button {
    private static final boolean DEBUG = KeyguardConstants.DEBUG;
    private static final Intent INTENT_EMERGENCY_DIAL = new Intent().setAction("com.android.phone.EmergencyDialer.DIAL").setPackage("com.android.phone").setFlags(343932928);
    private int mDownX;
    private int mDownY;
    private int mEccPhoneIdForNoneSecurityMode;
    private final EmergencyAffordanceManager mEmergencyAffordanceManager;
    private EmergencyButtonCallback mEmergencyButtonCallback;
    private IEmergencyButtonExt mEmergencyButtonExt;
    private final boolean mEnableEmergencyCallWhileSimLocked;
    KeyguardUpdateMonitorCallback mInfoCallback;
    private boolean mIsSecure;
    private final boolean mIsVoiceCapable;
    private boolean mLocateAtNonSecureView;
    private LockPatternUtils mLockPatternUtils;
    private boolean mLongPressWasDragged;
    private PowerManager mPowerManager;
    private TelephonyManager mTelephonyManager;

    public interface EmergencyButtonCallback {
        void onEmergencyButtonClickedWhenInCall();
    }

    public EmergencyButton(Context context) {
        this(context, null);
    }

    public EmergencyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInfoCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.EmergencyButton.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onPhoneStateChanged(int phoneState) {
                EmergencyButton.this.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onSimStateChangedUsingPhoneId(int phoneId, IccCardConstants.State simState) {
                if (EmergencyButton.DEBUG) {
                    Slog.d("EmergencyButton", "onSimStateChangedUsingSubId: " + simState + ", phoneId=" + phoneId);
                }
                EmergencyButton.this.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onRefreshCarrierInfo() {
                EmergencyButton.this.updateEmergencyCallButton();
            }
        };
        this.mEccPhoneIdForNoneSecurityMode = -1;
        this.mLocateAtNonSecureView = false;
        this.mIsVoiceCapable = context.getResources().getBoolean(android.R.^attr-private.popupPromptView);
        this.mEnableEmergencyCallWhileSimLocked = this.mContext.getResources().getBoolean(android.R.^attr-private.iconfactoryIconSize);
        this.mEmergencyAffordanceManager = new EmergencyAffordanceManager(context);
        try {
            this.mEmergencyButtonExt = OpKeyguardCustomizationFactoryBase.getOpFactory(this.mContext).makeEmergencyButton();
        } catch (Exception e) {
            Slog.d("EmergencyButton", "EmergencyButton() - error in calling getEmergencyButtonExt().");
            e.printStackTrace();
        }
        TypedArray localAtNonSecureValue = context.obtainStyledAttributes(attrs, R.styleable.ECCButtonAttr);
        this.mLocateAtNonSecureView = localAtNonSecureValue.getBoolean(0, this.mLocateAtNonSecureView);
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        KeyguardUpdateMonitor.getInstance(this.mContext).registerCallback(this.mInfoCallback);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        KeyguardUpdateMonitor.getInstance(this.mContext).removeCallback(this.mInfoCallback);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        this.mPowerManager = (PowerManager) this.mContext.getSystemService("power");
        setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.EmergencyButton.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                EmergencyButton.this.takeEmergencyCallAction();
            }
        });
        this.mIsSecure = this.mLockPatternUtils.isSecure(KeyguardUpdateMonitor.getCurrentUser());
        setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.keyguard.EmergencyButton.3
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View v) {
                if (!EmergencyButton.this.mLongPressWasDragged && EmergencyButton.this.mEmergencyAffordanceManager.needsEmergencyAffordance()) {
                    EmergencyButton.this.mEmergencyAffordanceManager.performEmergencyCall();
                    return true;
                }
                return false;
            }
        });
        updateEmergencyCallButton();
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (event.getActionMasked() == 0) {
            this.mDownX = x;
            this.mDownY = y;
            this.mLongPressWasDragged = false;
        } else {
            int xDiff = Math.abs(x - this.mDownX);
            int yDiff = Math.abs(y - this.mDownY);
            int touchSlop = ViewConfiguration.get(this.mContext).getScaledTouchSlop();
            if (Math.abs(yDiff) > touchSlop || Math.abs(xDiff) > touchSlop) {
                this.mLongPressWasDragged = true;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean performLongClick() {
        return super.performLongClick();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setText(com.android.systemui.R.string.kg_emergency_call_label);
        updateEmergencyCallButton();
    }

    public void takeEmergencyCallAction() {
        MetricsLogger.action(this.mContext, 200);
        this.mPowerManager.userActivity(SystemClock.uptimeMillis(), true);
        try {
            ActivityManager.getService().stopSystemLockTaskMode();
        } catch (RemoteException e) {
            Slog.w("EmergencyButton", "Failed to stop app pinning");
        }
        if (isInCall()) {
            resumeCall();
            if (this.mEmergencyButtonCallback != null) {
                this.mEmergencyButtonCallback.onEmergencyButtonClickedWhenInCall();
                return;
            }
            return;
        }
        KeyguardUpdateMonitor.getInstance(this.mContext).reportEmergencyCallAction(true);
        int phoneId = getCurPhoneId();
        if (phoneId == -1) {
            phoneId = this.mEccPhoneIdForNoneSecurityMode;
        }
        this.mEmergencyButtonExt.customizeEmergencyIntent(INTENT_EMERGENCY_DIAL, phoneId);
        getContext().startActivityAsUser(INTENT_EMERGENCY_DIAL, ActivityOptions.makeCustomAnimation(getContext(), 0, 0).toBundle(), new UserHandle(KeyguardUpdateMonitor.getCurrentUser()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEmergencyCallButton() {
        boolean show;
        boolean visible = false;
        if (isInCall()) {
            visible = true;
        } else if (this.mIsVoiceCapable) {
            boolean simLocked = KeyguardUpdateMonitor.getInstance(this.mContext).isSimPinVoiceSecure();
            if (simLocked) {
                visible = this.mEnableEmergencyCallWhileSimLocked;
            } else {
                visible = this.mLockPatternUtils.isSecure(KeyguardUpdateMonitor.getCurrentUser());
            }
        }
        boolean antiTheftLocked = AntiTheftManager.isAntiTheftLocked();
        boolean eccShouldShow = eccButtonShouldShow();
        if (DEBUG) {
            Slog.i("EmergencyButton", "mLocateAtNonSecureView = " + this.mLocateAtNonSecureView);
        }
        if (this.mLocateAtNonSecureView && !this.mEmergencyButtonExt.showEccInNonSecureUnlock()) {
            if (DEBUG) {
                Slog.i("EmergencyButton", "ECC Button is located on Notification Keygaurd and OP do not ask to show. So this is a normal case ,we never show it.");
            }
            show = false;
        } else {
            show = (visible || antiTheftLocked || this.mEmergencyButtonExt.showEccInNonSecureUnlock()) && eccShouldShow;
            Slog.i("EmergencyButton", "show = " + show);
            if (DEBUG) {
                Slog.i("EmergencyButton", "visible= " + visible + ", antiTheftLocked=" + antiTheftLocked + ", mEmergencyButtonExt.showEccInNonSecureUnlock() =" + this.mEmergencyButtonExt.showEccInNonSecureUnlock() + ", eccShouldShow=" + eccShouldShow);
            }
        }
        if (this.mLocateAtNonSecureView && !show) {
            setVisibility(8);
        } else {
            updateEmergencyCallButtonState(show, false);
        }
    }

    public void setCallback(EmergencyButtonCallback callback) {
        this.mEmergencyButtonCallback = callback;
    }

    private void resumeCall() {
        getTelecommManager().showInCallScreen(false);
    }

    private boolean isInCall() {
        return getTelecommManager().isInCall();
    }

    private TelecomManager getTelecommManager() {
        return (TelecomManager) this.mContext.getSystemService("telecom");
    }

    private boolean eccButtonShouldShow() {
        int phoneCount = KeyguardUtils.getNumOfPhone();
        boolean[] isServiceSupportEcc = new boolean[phoneCount];
        TelephonyManager tm = TelephonyManager.getDefault();
        this.mEccPhoneIdForNoneSecurityMode = -1;
        if (tm != null) {
            for (int i = 0; i < phoneCount; i++) {
                int subId = KeyguardUtils.getSubIdUsingPhoneId(i);
                ServiceState ss = this.mTelephonyManager.getServiceStateForSubscriber(subId);
                if (ss != null) {
                    Slog.i("EmergencyButton", "ss.getState()=" + ss.getState() + " ss.isEmergencyOnly()=" + ss.isEmergencyOnly() + " for simId=" + i);
                    if (ss.getState() == 0 || ss.isEmergencyOnly()) {
                        isServiceSupportEcc[i] = true;
                        if (this.mEccPhoneIdForNoneSecurityMode == -1) {
                            this.mEccPhoneIdForNoneSecurityMode = i;
                        }
                    } else {
                        isServiceSupportEcc[i] = false;
                    }
                } else {
                    Slog.e("EmergencyButton", "Service state is null");
                }
            }
        }
        return this.mEmergencyButtonExt.showEccByServiceState(isServiceSupportEcc, getCurPhoneId());
    }

    private int getCurPhoneId() {
        KeyguardSecurityModel securityModel = new KeyguardSecurityModel(this.mContext);
        return securityModel.getPhoneIdUsingSecurityMode(securityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser()));
    }

    public void updateEmergencyCallButtonState(boolean shown, boolean showIcon) {
        int textId;
        if (this.mIsVoiceCapable && shown) {
            setVisibility(0);
            if (isInCall()) {
                textId = android.R.string.data_usage_warning_title;
                int phoneCallIcon = showIcon ? android.R.drawable.stat_sys_phone_call : 0;
                setCompoundDrawablesWithIntrinsicBounds(phoneCallIcon, 0, 0, 0);
            } else {
                textId = android.R.string.content_description_collapsed;
                int emergencyIcon = showIcon ? android.R.drawable.fastscroll_thumb_default_holo : 0;
                setCompoundDrawablesWithIntrinsicBounds(emergencyIcon, 0, 0, 0);
            }
            setText(textId);
            return;
        }
        setVisibility(4);
    }
}
