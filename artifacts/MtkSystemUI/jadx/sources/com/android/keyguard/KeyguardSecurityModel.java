package com.android.keyguard;

import android.content.Context;
import android.util.Log;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.widget.LockPatternUtils;
import com.mediatek.keyguard.AntiTheft.AntiTheftManager;
import com.mediatek.keyguard.PowerOffAlarm.PowerOffAlarmManager;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardSecurityModel {
    private final Context mContext;
    private final boolean mIsPukScreenAvailable;
    private LockPatternUtils mLockPatternUtils;

    public enum SecurityMode {
        Invalid,
        None,
        Pattern,
        Password,
        PIN,
        SimPinPukMe1,
        SimPinPukMe2,
        SimPinPukMe3,
        SimPinPukMe4,
        AntiTheft,
        AlarmBoot
    }

    public KeyguardSecurityModel(Context context) {
        this.mContext = context;
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mIsPukScreenAvailable = this.mContext.getResources().getBoolean(android.R.^attr-private.ignoreOffsetTopLimit);
    }

    void setLockPatternUtils(LockPatternUtils utils) {
        this.mLockPatternUtils = utils;
    }

    public SecurityMode getSecurityMode(int userId) {
        KeyguardUpdateMonitor.getInstance(this.mContext);
        SecurityMode mode = SecurityMode.None;
        if (PowerOffAlarmManager.isAlarmBoot()) {
            mode = SecurityMode.AlarmBoot;
        } else {
            for (int i = 0; i < KeyguardUtils.getNumOfPhone(); i++) {
                if (isPinPukOrMeRequiredOfPhoneId(i)) {
                    if (i == 0) {
                        mode = SecurityMode.SimPinPukMe1;
                        break;
                    }
                    if (1 == i) {
                        mode = SecurityMode.SimPinPukMe2;
                        break;
                    }
                    if (2 == i) {
                        mode = SecurityMode.SimPinPukMe3;
                        break;
                    }
                    if (3 != i) {
                        break;
                    }
                    mode = SecurityMode.SimPinPukMe4;
                    break;
                }
            }
        }
        if (AntiTheftManager.isAntiTheftPriorToSecMode(mode)) {
            Log.d("KeyguardSecurityModel", "should show AntiTheft!");
            mode = SecurityMode.AntiTheft;
        }
        if (mode == SecurityMode.None) {
            int security = this.mLockPatternUtils.getActivePasswordQuality(userId);
            if (security == 0) {
                return SecurityMode.None;
            }
            if (security == 65536) {
                return SecurityMode.Pattern;
            }
            if (security == 131072 || security == 196608) {
                return SecurityMode.PIN;
            }
            if (security == 262144 || security == 327680 || security == 393216 || security == 524288) {
                return SecurityMode.Password;
            }
            throw new IllegalStateException("Unknown security quality:" + security);
        }
        Log.d("KeyguardSecurityModel", "getSecurityMode() - mode = " + mode);
        return mode;
    }

    public boolean isPinPukOrMeRequiredOfPhoneId(int phoneId) {
        KeyguardUpdateMonitor updateMonitor = KeyguardUpdateMonitor.getInstance(this.mContext);
        if (updateMonitor == null) {
            return false;
        }
        IccCardConstants.State simState = updateMonitor.getSimStateOfPhoneId(phoneId);
        Log.d("KeyguardSecurityModel", "isPinPukOrMeRequiredOfSubId() - phoneId = " + phoneId + ", simState = " + simState);
        return (simState == IccCardConstants.State.PIN_REQUIRED && !updateMonitor.getPinPukMeDismissFlagOfPhoneId(phoneId)) || !(simState != IccCardConstants.State.PUK_REQUIRED || updateMonitor.getPinPukMeDismissFlagOfPhoneId(phoneId) || updateMonitor.getRetryPukCountOfPhoneId(phoneId) == 0) || (simState == IccCardConstants.State.NETWORK_LOCKED && !updateMonitor.getPinPukMeDismissFlagOfPhoneId(phoneId) && updateMonitor.getSimMeLeftRetryCountOfPhoneId(phoneId) != 0 && KeyguardUtils.isMediatekSimMeLockSupport() && !updateMonitor.getSimmeDismissFlagOfPhoneId(phoneId) && KeyguardUtils.isSimMeLockValid(phoneId));
    }

    int getPhoneIdUsingSecurityMode(SecurityMode mode) {
        if (!isSimPinPukSecurityMode(mode)) {
            return -1;
        }
        int phoneId = mode.ordinal() - SecurityMode.SimPinPukMe1.ordinal();
        return phoneId;
    }

    boolean isSimPinPukSecurityMode(SecurityMode mode) {
        switch (mode) {
            case SimPinPukMe1:
            case SimPinPukMe2:
            case SimPinPukMe3:
            case SimPinPukMe4:
                return true;
            default:
                return false;
        }
    }
}
