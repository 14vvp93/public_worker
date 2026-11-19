package com.android.keyguard;

import android.os.SystemClock;
import com.android.internal.telephony.IccCardConstants;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardUpdateMonitorCallback {
    private boolean mShowing;
    private long mVisibilityChangedCalled;

    public void onRefreshBatteryInfo(KeyguardUpdateMonitor.BatteryStatus status) {
    }

    public void onTimeChanged() {
    }

    public void onRefreshCarrierInfo() {
    }

    public void onRingerModeChanged(int state) {
    }

    public void onPhoneStateChanged(int phoneState) {
    }

    public void onKeyguardVisibilityChanged(boolean showing) {
    }

    public void onKeyguardVisibilityChangedRaw(boolean showing) {
        long now = SystemClock.elapsedRealtime();
        if (showing == this.mShowing && now - this.mVisibilityChangedCalled < 1000) {
            return;
        }
        onKeyguardVisibilityChanged(showing);
        this.mVisibilityChangedCalled = now;
        this.mShowing = showing;
    }

    public void onKeyguardBouncerChanged(boolean bouncer) {
    }

    public void onClockVisibilityChanged() {
    }

    public void onDeviceProvisioned() {
    }

    public void onDevicePolicyManagerStateChanged() {
    }

    public void onUserSwitching(int userId) {
    }

    public void onUserSwitchComplete(int userId) {
    }

    public void onSimStateChangedUsingPhoneId(int phoneId, IccCardConstants.State simState) {
    }

    public void onUserInfoChanged(int userId) {
    }

    public void onUserUnlocked() {
    }

    public void onBootCompleted() {
    }

    public void onEmergencyCallAction() {
    }

    @Deprecated
    public void onStartedWakingUp() {
    }

    @Deprecated
    public void onStartedGoingToSleep(int why) {
    }

    @Deprecated
    public void onFinishedGoingToSleep(int why) {
    }

    @Deprecated
    public void onScreenTurnedOn() {
    }

    @Deprecated
    public void onScreenTurnedOff() {
    }

    public void onTrustChanged(int userId) {
    }

    public void onTrustManagedChanged(int userId) {
    }

    public void onTrustGrantedWithFlags(int flags, int userId) {
    }

    public void onFingerprintAcquired() {
    }

    public void onFingerprintAuthFailed() {
    }

    public void onFingerprintAuthenticated(int userId) {
    }

    public void onFingerprintHelp(int msgId, String helpString) {
    }

    public void onFingerprintError(int msgId, String errString) {
    }

    public void onFaceUnlockStateChanged(boolean running, int userId) {
    }

    public void onFingerprintRunningStateChanged(boolean running) {
    }

    public void onStrongAuthStateChanged(int userId) {
    }

    public void onHasLockscreenWallpaperChanged(boolean hasLockscreenWallpaper) {
    }

    public void onDreamingStateChanged(boolean dreaming) {
    }

    public void onTrustAgentErrorMessage(CharSequence message) {
    }

    public void onLogoutEnabledChanged() {
    }

    public void onAirPlaneModeChanged(boolean airPlaneModeEnabled) {
    }
}
