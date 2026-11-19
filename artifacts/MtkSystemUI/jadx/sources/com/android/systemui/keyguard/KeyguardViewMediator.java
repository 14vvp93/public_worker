package com.android.systemui.keyguard;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.EventLog;
import android.util.Log;
import android.util.Slog;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardDrawnCallback;
import com.android.internal.policy.IKeyguardExitCallback;
import com.android.internal.policy.IKeyguardStateCallback;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardConstants;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardUtils;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.SystemUI;
import com.android.systemui.SystemUIFactory;
import com.android.systemui.UiOffloadThread;
import com.android.systemui.classifier.FalsingManager;
import com.android.systemui.statusbar.phone.FingerprintUnlockController;
import com.android.systemui.statusbar.phone.NotificationPanelView;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.mediatek.keyguard.AntiTheft.AntiTheftManager;
import com.mediatek.keyguard.PowerOffAlarm.PowerOffAlarmManager;
import com.mediatek.keyguard.Telephony.KeyguardDialogManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardViewMediator extends SystemUI {
    private static final boolean DEBUG = KeyguardConstants.DEBUG;
    private static final boolean DEBUG_SIM_STATES = KeyguardConstants.DEBUG_SIM_STATES;
    private static final Intent USER_PRESENT_INTENT = new Intent("android.intent.action.USER_PRESENT").addFlags(606076928);
    private static boolean mKeyguardDoneOnGoing = false;
    private AlarmManager mAlarmManager;
    private AntiTheftManager mAntiTheftManager;
    private boolean mAodShowing;
    private AudioManager mAudioManager;
    private boolean mBootCompleted;
    private boolean mBootSendUserPresent;
    private CharSequence mCustomMessage;
    private int mDelayedProfileShowingSequence;
    private int mDelayedShowingSequence;
    private boolean mDeviceInteractive;
    private KeyguardDialogManager mDialogManager;
    private IKeyguardDrawnCallback mDrawnCallback;
    private IKeyguardExitCallback mExitSecureCallback;
    private boolean mGoingToSleep;
    private Animation mHideAnimation;
    private boolean mHiding;
    private boolean mInputRestricted;
    private KeyguardDisplayManager mKeyguardDisplayManager;
    private boolean mLockLater;
    private LockPatternUtils mLockPatternUtils;
    private int mLockSoundId;
    private int mLockSoundStreamId;
    private float mLockSoundVolume;
    private SoundPool mLockSounds;
    private boolean mLockWhenSimRemoved;
    private PowerManager mPM;
    private boolean mPendingLock;
    private boolean mPendingReset;
    private PowerOffAlarmManager mPowerOffAlarmManager;
    private PowerManager.WakeLock mShowKeyguardWakeLock;
    private boolean mShowing;
    private boolean mShuttingDown;
    private StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    private StatusBarManager mStatusBarManager;
    private boolean mSystemReady;
    private TrustManager mTrustManager;
    private int mTrustedSoundId;
    private int mUiSoundsStreamType;
    private int mUnlockSoundId;
    private KeyguardUpdateMonitor mUpdateMonitor;
    private boolean mWakeAndUnlocking;
    private WorkLockActivityController mWorkLockController;
    private final UiOffloadThread mUiOffloadThread = (UiOffloadThread) Dependency.get(UiOffloadThread.class);
    private boolean mExternallyEnabled = true;
    private boolean mNeedToReshowWhenReenabled = false;
    private int mSecondaryDisplayShowing = -1;
    private boolean mOccluded = false;
    private final DismissCallbackRegistry mDismissCallbackRegistry = new DismissCallbackRegistry();
    private String mPhoneState = TelephonyManager.EXTRA_STATE_IDLE;
    private boolean mWaitingUntilKeyguardVisible = false;
    private boolean mKeyguardDonePending = false;
    private boolean mHideAnimationRun = false;
    private boolean mHideAnimationRunning = false;
    private final ArrayList<IKeyguardStateCallback> mKeyguardStateCallbacks = new ArrayList<>();
    KeyguardUpdateMonitorCallback mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onUserSwitching(int userId) {
            synchronized (KeyguardViewMediator.this) {
                KeyguardViewMediator.this.resetKeyguardDonePendingLocked();
                if (!KeyguardViewMediator.this.mLockPatternUtils.isLockScreenDisabled(userId)) {
                    KeyguardViewMediator.this.resetStateLocked();
                } else {
                    KeyguardViewMediator.this.dismiss(null, null);
                }
                KeyguardViewMediator.this.adjustStatusBarLocked();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onUserSwitchComplete(int userId) {
            UserInfo info;
            if (userId == 0 || (info = UserManager.get(KeyguardViewMediator.this.mContext).getUserInfo(userId)) == null || KeyguardViewMediator.this.mLockPatternUtils.isSecure(userId)) {
                return;
            }
            if (info.isGuest() || info.isDemo()) {
                KeyguardViewMediator.this.dismiss(null, null);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onUserInfoChanged(int userId) {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onPhoneStateChanged(int phoneState) {
            synchronized (KeyguardViewMediator.this) {
                if (phoneState == 0) {
                    try {
                        if (!KeyguardViewMediator.this.mDeviceInteractive && KeyguardViewMediator.this.mExternallyEnabled && KeyguardViewMediator.DEBUG) {
                            Log.d("KeyguardViewMediator", "screen is off and call ended, let's make sure the keyguard is showing");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onClockVisibilityChanged() {
            KeyguardViewMediator.this.adjustStatusBarLocked();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onDeviceProvisioned() {
            KeyguardViewMediator.this.sendUserPresentBroadcast();
            synchronized (KeyguardViewMediator.this) {
                if (KeyguardViewMediator.this.mustNotUnlockCurrentUser()) {
                    KeyguardViewMediator.this.doKeyguardLocked(null);
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onSimStateChangedUsingPhoneId(int phoneId, IccCardConstants.State simState) {
            if (KeyguardViewMediator.DEBUG) {
                Log.d("KeyguardViewMediator", "onSimStateChangedUsingSubId: " + simState + ", phoneId=" + phoneId);
            }
            switch (AnonymousClass9.$SwitchMap$com$android$internal$telephony$IccCardConstants$State[simState.ordinal()]) {
                case 1:
                case 2:
                    synchronized (KeyguardViewMediator.this) {
                        if (KeyguardViewMediator.this.shouldWaitForProvisioning()) {
                            if (!KeyguardViewMediator.this.mShowing) {
                                if (KeyguardViewMediator.DEBUG_SIM_STATES) {
                                    Log.d("KeyguardViewMediator", "ICC_ABSENT isn't showing, we need to show the keyguard since the device isn't provisioned yet.");
                                }
                                KeyguardViewMediator.this.doKeyguardLocked(null);
                            } else {
                                KeyguardViewMediator.this.resetStateLocked();
                            }
                        }
                        if (simState == IccCardConstants.State.ABSENT) {
                            onSimAbsentLocked();
                        }
                        break;
                    }
                    break;
                case 3:
                case 4:
                case 5:
                    synchronized (this) {
                        if (simState == IccCardConstants.State.NETWORK_LOCKED && !KeyguardUtils.isMediatekSimMeLockSupport()) {
                            Log.d("KeyguardViewMediator", "Get NETWORK_LOCKED but not support ME lock. Not show.");
                        } else if (simState == IccCardConstants.State.NETWORK_LOCKED && !KeyguardUtils.isSimMeLockValid(phoneId)) {
                            Log.d("KeyguardViewMediator", "Get NETWORK_LOCKED but not to show with specific policy!");
                        } else if (KeyguardUtils.isSystemEncrypted()) {
                            if (KeyguardViewMediator.DEBUG) {
                                Log.d("KeyguardViewMediator", "Currently system needs to be decrypted. Not show.");
                            }
                        } else {
                            KeyguardViewMediator.this.mUpdateMonitor.setDismissFlagWhenWfcOn(simState);
                            if (KeyguardViewMediator.this.mUpdateMonitor.getRetryPukCountOfPhoneId(phoneId) == 0 && KeyguardViewMediator.this.mDialogManager != null) {
                                KeyguardViewMediator.this.mDialogManager.requestShowDialog(new InvalidDialogCallback());
                            } else if (IccCardConstants.State.NETWORK_LOCKED == simState && KeyguardViewMediator.this.mUpdateMonitor.getSimMeLeftRetryCountOfPhoneId(phoneId) == 0 && KeyguardViewMediator.this.mDialogManager != null) {
                                Log.d("KeyguardViewMediator", "SIM ME lock retrycount is 0, only to show dialog");
                                KeyguardViewMediator.this.mDialogManager.requestShowDialog(new MeLockedDialogCallback());
                            } else if (!KeyguardViewMediator.this.isShowing()) {
                                if (KeyguardViewMediator.DEBUG) {
                                    Log.d("KeyguardViewMediator", "INTENT_VALUE_ICC_LOCKED and keygaurd isn't showing; need to show keyguard so user can enter sim pin");
                                }
                                KeyguardViewMediator.this.doKeyguardLocked(null);
                            } else if (!KeyguardViewMediator.mKeyguardDoneOnGoing) {
                                KeyguardViewMediator.this.removeKeyguardDoneMsg();
                                KeyguardViewMediator.this.resetStateLocked();
                            } else {
                                Log.d("KeyguardViewMediator", "mKeyguardDoneOnGoing is true");
                                KeyguardViewMediator.this.doKeyguardLaterLocked();
                            }
                        }
                        break;
                    }
                    break;
                case 6:
                    synchronized (KeyguardViewMediator.this) {
                        if (!KeyguardViewMediator.this.mShowing) {
                            if (KeyguardViewMediator.DEBUG_SIM_STATES) {
                                Log.d("KeyguardViewMediator", "PERM_DISABLED and keygaurd isn't showing.");
                            }
                            KeyguardViewMediator.this.doKeyguardLocked(null);
                        } else {
                            if (KeyguardViewMediator.DEBUG_SIM_STATES) {
                                Log.d("KeyguardViewMediator", "PERM_DISABLED, resetStateLocked toshow permanently disabled message in lockscreen.");
                            }
                            KeyguardViewMediator.this.resetStateLocked();
                        }
                        onSimAbsentLocked();
                        break;
                    }
                    break;
                case 7:
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator.this.mLockWhenSimRemoved = true;
                        break;
                    }
                    break;
                default:
                    if (KeyguardViewMediator.DEBUG_SIM_STATES) {
                        Log.v("KeyguardViewMediator", "Unspecific state: " + simState);
                    }
                    break;
            }
            try {
                int size = KeyguardViewMediator.this.mKeyguardStateCallbacks.size();
                boolean simPinSecure = KeyguardViewMediator.this.mUpdateMonitor.isSimPinSecure();
                for (int i = 0; i < size; i++) {
                    ((IKeyguardStateCallback) KeyguardViewMediator.this.mKeyguardStateCallbacks.get(i)).onSimSecureStateChanged(simPinSecure);
                }
            } catch (RemoteException e) {
                Slog.w("KeyguardViewMediator", "Failed to call onSimSecureStateChanged", e);
            }
        }

        private void onSimAbsentLocked() {
            if (KeyguardViewMediator.this.isSecure() && KeyguardViewMediator.this.mLockWhenSimRemoved && !KeyguardViewMediator.this.mShuttingDown) {
                KeyguardViewMediator.this.mLockWhenSimRemoved = false;
                MetricsLogger.action(KeyguardViewMediator.this.mContext, 496, KeyguardViewMediator.this.mShowing);
                if (!KeyguardViewMediator.this.mShowing) {
                    Log.i("KeyguardViewMediator", "SIM removed, showing keyguard");
                    KeyguardViewMediator.this.doKeyguardLocked(null);
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onFingerprintAuthFailed() {
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            if (KeyguardViewMediator.this.mLockPatternUtils.isSecure(currentUser)) {
                KeyguardViewMediator.this.mLockPatternUtils.getDevicePolicyManager().reportFailedFingerprintAttempt(currentUser);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onFingerprintAuthenticated(int userId) {
            if (KeyguardViewMediator.this.mLockPatternUtils.isSecure(userId)) {
                KeyguardViewMediator.this.mLockPatternUtils.getDevicePolicyManager().reportSuccessfulFingerprintAttempt(userId);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onTrustChanged(int userId) {
            if (userId == KeyguardUpdateMonitor.getCurrentUser()) {
                synchronized (KeyguardViewMediator.this) {
                    KeyguardViewMediator.this.notifyTrustedChangedLocked(KeyguardViewMediator.this.mUpdateMonitor.getUserHasTrust(userId));
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onHasLockscreenWallpaperChanged(boolean hasLockscreenWallpaper) {
            synchronized (KeyguardViewMediator.this) {
                KeyguardViewMediator.this.notifyHasLockscreenWallpaperChanged(hasLockscreenWallpaper);
            }
        }
    };
    ViewMediatorCallback mViewMediatorCallback = new ViewMediatorCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.2
        @Override // com.android.keyguard.ViewMediatorCallback
        public void userActivity() {
            KeyguardViewMediator.this.userActivity();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void keyguardDone(boolean strongAuth, int targetUserId) {
            if (targetUserId == ActivityManager.getCurrentUser()) {
                KeyguardViewMediator.this.tryKeyguardDone();
            }
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void keyguardDoneDrawing() {
            Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#keyguardDoneDrawing");
            KeyguardViewMediator.this.mHandler.sendEmptyMessage(8);
            Trace.endSection();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void setNeedsInput(boolean needsInput) {
            KeyguardViewMediator.this.mStatusBarKeyguardViewManager.setNeedsInput(needsInput);
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void keyguardDonePending(boolean strongAuth, int targetUserId) {
            Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#keyguardDonePending");
            if (targetUserId == ActivityManager.getCurrentUser()) {
                KeyguardViewMediator.this.mKeyguardDonePending = true;
                KeyguardViewMediator.this.mHideAnimationRun = true;
                KeyguardViewMediator.this.mHideAnimationRunning = true;
                KeyguardViewMediator.this.mStatusBarKeyguardViewManager.startPreHideAnimation(KeyguardViewMediator.this.mHideAnimationFinishedRunnable);
                KeyguardViewMediator.this.mHandler.sendEmptyMessageDelayed(13, 3000L);
                Trace.endSection();
                return;
            }
            Trace.endSection();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void keyguardGone() {
            Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#keyguardGone");
            KeyguardViewMediator.this.mKeyguardDisplayManager.hide();
            Trace.endSection();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void readyForKeyguardDone() {
            Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#readyForKeyguardDone");
            if (KeyguardViewMediator.this.mKeyguardDonePending) {
                KeyguardViewMediator.this.mKeyguardDonePending = false;
                KeyguardViewMediator.this.tryKeyguardDone();
            }
            Trace.endSection();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void resetKeyguard() {
            resetStateLocked();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void onBouncerVisiblityChanged(boolean shown) {
            synchronized (KeyguardViewMediator.this) {
                KeyguardViewMediator.this.adjustStatusBarLocked(shown);
            }
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void playTrustedSound() {
            KeyguardViewMediator.this.playTrustedSound();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public boolean isScreenOn() {
            return KeyguardViewMediator.this.mDeviceInteractive;
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public int getBouncerPromptReason() {
            int currentUser = ActivityManager.getCurrentUser();
            boolean trust = KeyguardViewMediator.this.mTrustManager.isTrustUsuallyManaged(currentUser);
            boolean fingerprint = KeyguardViewMediator.this.mUpdateMonitor.isUnlockWithFingerprintPossible(currentUser);
            boolean any = trust || fingerprint;
            KeyguardUpdateMonitor.StrongAuthTracker strongAuthTracker = KeyguardViewMediator.this.mUpdateMonitor.getStrongAuthTracker();
            int strongAuth = strongAuthTracker.getStrongAuthForUser(currentUser);
            if (any && !strongAuthTracker.hasUserAuthenticatedSinceBoot()) {
                return 1;
            }
            if (any && (strongAuth & 16) != 0) {
                return 2;
            }
            if (any && (strongAuth & 2) != 0) {
                return 3;
            }
            if (!trust || (strongAuth & 4) == 0) {
                return (!any || (strongAuth & 8) == 0) ? 0 : 5;
            }
            return 4;
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public CharSequence consumeCustomMessage() {
            CharSequence message = KeyguardViewMediator.this.mCustomMessage;
            KeyguardViewMediator.this.mCustomMessage = null;
            return message;
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void onSecondaryDisplayShowingChanged(int displayId) {
            synchronized (KeyguardViewMediator.this) {
                KeyguardViewMediator.this.setShowingLocked(KeyguardViewMediator.this.mShowing, KeyguardViewMediator.this.mAodShowing, displayId, false);
            }
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public boolean isShowing() {
            return KeyguardViewMediator.this.isShowing();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void showLocked(Bundle options) {
            KeyguardViewMediator.this.showLocked(options);
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void resetStateLocked() {
            KeyguardViewMediator.this.resetStateLocked();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void adjustStatusBarLocked() {
            KeyguardViewMediator.this.adjustStatusBarLocked();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public boolean isKeyguardDoneOnGoing() {
            return KeyguardViewMediator.this.isKeyguardDoneOnGoing();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void hideLocked() {
            KeyguardViewMediator.this.hideLocked();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public boolean isSecure() {
            return KeyguardViewMediator.this.isSecure();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void setSuppressPlaySoundFlag() {
            KeyguardViewMediator.this.setSuppressPlaySoundFlag();
        }

        @Override // com.android.keyguard.ViewMediatorCallback
        public void updateNavbarStatus() {
            KeyguardViewMediator.this.updateNavbarStatus();
        }
    };
    private final BroadcastReceiver mDelayedLockBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD".equals(intent.getAction())) {
                int sequence = intent.getIntExtra("seq", 0);
                if (KeyguardViewMediator.DEBUG) {
                    Log.d("KeyguardViewMediator", "received DELAYED_KEYGUARD_ACTION with seq = " + sequence + ", mDelayedShowingSequence = " + KeyguardViewMediator.this.mDelayedShowingSequence);
                }
                synchronized (KeyguardViewMediator.this) {
                    if (KeyguardViewMediator.this.mDelayedShowingSequence == sequence) {
                        KeyguardViewMediator.this.mSuppressNextLockSound = true;
                        KeyguardViewMediator.this.doKeyguardLocked(null);
                    }
                }
                return;
            }
            if ("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK".equals(intent.getAction())) {
                int sequence2 = intent.getIntExtra("seq", 0);
                int userId = intent.getIntExtra("android.intent.extra.USER_ID", 0);
                if (userId != 0) {
                    synchronized (KeyguardViewMediator.this) {
                        if (KeyguardViewMediator.this.mDelayedProfileShowingSequence == sequence2) {
                            KeyguardViewMediator.this.lockProfile(userId);
                        }
                    }
                }
            }
        }
    };
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                synchronized (KeyguardViewMediator.this) {
                    KeyguardViewMediator.this.mShuttingDown = true;
                }
            }
        }
    };
    private Handler mHandler = new Handler(Looper.myLooper(), null, true) { // from class: com.android.systemui.keyguard.KeyguardViewMediator.5
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    KeyguardViewMediator.this.handleShow((Bundle) msg.obj);
                    return;
                case 2:
                    KeyguardViewMediator.this.handleHide();
                    return;
                case 3:
                    KeyguardViewMediator.this.handleReset();
                    return;
                case 4:
                    Trace.beginSection("KeyguardViewMediator#handleMessage VERIFY_UNLOCK");
                    KeyguardViewMediator.this.handleVerifyUnlock();
                    Trace.endSection();
                    return;
                case 5:
                    KeyguardViewMediator.this.handleNotifyFinishedGoingToSleep();
                    return;
                case 6:
                    Trace.beginSection("KeyguardViewMediator#handleMessage NOTIFY_SCREEN_TURNING_ON");
                    KeyguardViewMediator.this.handleNotifyScreenTurningOn((IKeyguardDrawnCallback) msg.obj);
                    Trace.endSection();
                    return;
                case 7:
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE");
                    KeyguardViewMediator.this.handleKeyguardDone();
                    Trace.endSection();
                    return;
                case 8:
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE_DRAWING");
                    KeyguardViewMediator.this.handleKeyguardDoneDrawing();
                    Trace.endSection();
                    return;
                case 9:
                    Trace.beginSection("KeyguardViewMediator#handleMessage SET_OCCLUDED");
                    KeyguardViewMediator.this.handleSetOccluded(msg.arg1 != 0, msg.arg2 != 0);
                    Trace.endSection();
                    return;
                case 10:
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator.this.doKeyguardLocked((Bundle) msg.obj);
                        break;
                    }
                    return;
                case 11:
                    DismissMessage message = (DismissMessage) msg.obj;
                    KeyguardViewMediator.this.handleDismiss(message.getCallback(), message.getMessage());
                    return;
                case 12:
                    Trace.beginSection("KeyguardViewMediator#handleMessage START_KEYGUARD_EXIT_ANIM");
                    StartKeyguardExitAnimParams params = (StartKeyguardExitAnimParams) msg.obj;
                    KeyguardViewMediator.this.handleStartKeyguardExitAnimation(params.startTime, params.fadeoutDuration);
                    FalsingManager.getInstance(KeyguardViewMediator.this.mContext).onSucccessfulUnlock();
                    Trace.endSection();
                    return;
                case 13:
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE_PENDING_TIMEOUT");
                    Log.w("KeyguardViewMediator", "Timeout while waiting for activity drawn!");
                    Trace.endSection();
                    return;
                case 14:
                    Trace.beginSection("KeyguardViewMediator#handleMessage NOTIFY_STARTED_WAKING_UP");
                    KeyguardViewMediator.this.handleNotifyStartedWakingUp();
                    Trace.endSection();
                    return;
                case 15:
                    Trace.beginSection("KeyguardViewMediator#handleMessage NOTIFY_SCREEN_TURNED_ON");
                    KeyguardViewMediator.this.handleNotifyScreenTurnedOn();
                    Trace.endSection();
                    return;
                case 16:
                    KeyguardViewMediator.this.handleNotifyScreenTurnedOff();
                    return;
                case 17:
                    KeyguardViewMediator.this.handleNotifyStartedGoingToSleep();
                    return;
                case 18:
                    KeyguardViewMediator.this.handleSystemReady();
                    return;
                case 19:
                    KeyguardViewMediator.this.handleDismissInternal(msg.arg1 == 1);
                    return;
                default:
                    return;
            }
        }
    };
    private final Runnable mKeyguardGoingAwayRunnable = new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.7
        @Override // java.lang.Runnable
        public void run() {
            Trace.beginSection("KeyguardViewMediator.mKeyGuardGoingAwayRunnable");
            if (KeyguardViewMediator.DEBUG) {
                Log.d("KeyguardViewMediator", "keyguardGoingAway");
            }
            try {
                KeyguardViewMediator.this.mStatusBarKeyguardViewManager.keyguardGoingAway();
                int flags = 0;
                if (KeyguardViewMediator.this.mStatusBarKeyguardViewManager.shouldDisableWindowAnimationsForUnlock() || KeyguardViewMediator.this.mWakeAndUnlocking) {
                    flags = 0 | 2;
                }
                if (KeyguardViewMediator.this.mStatusBarKeyguardViewManager.isGoingToNotificationShade()) {
                    flags |= 1;
                }
                if (KeyguardViewMediator.this.mStatusBarKeyguardViewManager.isUnlockWithWallpaper()) {
                    flags |= 4;
                }
                KeyguardViewMediator.this.mUpdateMonitor.setKeyguardGoingAway(true);
                ActivityManager.getService().keyguardGoingAway(flags);
            } catch (RemoteException e) {
                Log.e("KeyguardViewMediator", "Error while calling WindowManager", e);
            }
            Trace.endSection();
        }
    };
    private final Runnable mHideAnimationFinishedRunnable = new Runnable() { // from class: com.android.systemui.keyguard.-$$Lambda$KeyguardViewMediator$cwsnZe582iHRLRSJWQeXdqmun1k
        @Override // java.lang.Runnable
        public final void run() {
            KeyguardViewMediator.lambda$new$4(this.f$0);
        }
    };
    private boolean mSuppressNextLockSound = true;

    /* JADX INFO: renamed from: com.android.systemui.keyguard.KeyguardViewMediator$9, reason: invalid class name */
    static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$com$android$internal$telephony$IccCardConstants$State = new int[IccCardConstants.State.values().length];

        static {
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.NOT_READY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.ABSENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.PIN_REQUIRED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.PUK_REQUIRED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.NETWORK_LOCKED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.PERM_DISABLED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.READY.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public void userActivity() {
        this.mPM.userActivity(SystemClock.uptimeMillis(), false);
    }

    boolean mustNotUnlockCurrentUser() {
        return UserManager.isSplitSystemUser() && KeyguardUpdateMonitor.getCurrentUser() == 0;
    }

    private void setupLocked() {
        this.mPM = (PowerManager) this.mContext.getSystemService("power");
        this.mTrustManager = (TrustManager) this.mContext.getSystemService("trust");
        this.mShowKeyguardWakeLock = this.mPM.newWakeLock(1, "show keyguard");
        this.mShowKeyguardWakeLock.setReferenceCounted(false);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.ACTION_SHUTDOWN");
        this.mContext.registerReceiver(this.mBroadcastReceiver, filter);
        IntentFilter delayedActionFilter = new IntentFilter();
        delayedActionFilter.addAction("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        delayedActionFilter.addAction("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK");
        this.mContext.registerReceiver(this.mDelayedLockBroadcastReceiver, delayedActionFilter, "com.android.systemui.permission.SELF", null);
        this.mKeyguardDisplayManager = new KeyguardDisplayManager(this.mContext, this.mViewMediatorCallback);
        this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        this.mUpdateMonitor = KeyguardUpdateMonitor.getInstance(this.mContext);
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        KeyguardUpdateMonitor.setCurrentUser(ActivityManager.getCurrentUser());
        if (this.mContext.getResources().getBoolean(R.bool.config_enableKeyguardService)) {
            setShowingLocked((shouldWaitForProvisioning() || this.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser())) ? false : true, this.mAodShowing, this.mSecondaryDisplayShowing, true);
        } else {
            setShowingLocked(false, this.mAodShowing, this.mSecondaryDisplayShowing, true);
        }
        this.mStatusBarKeyguardViewManager = SystemUIFactory.getInstance().createStatusBarKeyguardViewManager(this.mContext, this.mViewMediatorCallback, this.mLockPatternUtils);
        ContentResolver cr = this.mContext.getContentResolver();
        this.mDeviceInteractive = this.mPM.isInteractive();
        this.mLockSounds = new SoundPool(1, 1, 0);
        String soundPath = Settings.Global.getString(cr, "lock_sound");
        if (soundPath != null) {
            this.mLockSoundId = this.mLockSounds.load(soundPath, 1);
        }
        if (soundPath == null || this.mLockSoundId == 0) {
            Log.w("KeyguardViewMediator", "failed to load lock sound from " + soundPath);
        }
        String soundPath2 = Settings.Global.getString(cr, "unlock_sound");
        if (soundPath2 != null) {
            this.mUnlockSoundId = this.mLockSounds.load(soundPath2, 1);
        }
        if (soundPath2 == null || this.mUnlockSoundId == 0) {
            Log.w("KeyguardViewMediator", "failed to load unlock sound from " + soundPath2);
        }
        String soundPath3 = Settings.Global.getString(cr, "trusted_sound");
        if (soundPath3 != null) {
            this.mTrustedSoundId = this.mLockSounds.load(soundPath3, 1);
        }
        if (soundPath3 == null || this.mTrustedSoundId == 0) {
            Log.w("KeyguardViewMediator", "failed to load trusted sound from " + soundPath3);
        }
        int lockSoundDefaultAttenuation = this.mContext.getResources().getInteger(android.R.integer.config_datause_throttle_kbitsps);
        this.mLockSoundVolume = (float) Math.pow(10.0d, lockSoundDefaultAttenuation / 20.0f);
        this.mHideAnimation = AnimationUtils.loadAnimation(this.mContext, android.R.anim.ic_signal_wifi_transient_animation_7);
        this.mWorkLockController = new WorkLockActivityController(this.mContext);
        this.mDialogManager = KeyguardDialogManager.getInstance(this.mContext);
        AntiTheftManager.checkPplStatus();
        this.mAntiTheftManager = AntiTheftManager.getInstance(this.mContext, this.mViewMediatorCallback, this.mLockPatternUtils);
        this.mAntiTheftManager.doAntiTheftLockCheck();
        this.mPowerOffAlarmManager = PowerOffAlarmManager.getInstance(this.mContext, this.mViewMediatorCallback, this.mLockPatternUtils);
    }

    @Override // com.android.systemui.SystemUI
    public void start() {
        KeyguardUtils.getDefault().initSimmePolicy(this.mContext);
        synchronized (this) {
            setupLocked();
        }
        putComponent(KeyguardViewMediator.class, this);
    }

    public void onSystemReady() {
        this.mHandler.obtainMessage(18).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSystemReady() {
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "onSystemReady");
            }
            this.mSystemReady = true;
            doKeyguardLocked(null);
            this.mUpdateMonitor.registerCallback(this.mUpdateCallback);
            this.mPowerOffAlarmManager.onSystemReady();
        }
        maybeSendUserPresentBroadcast();
    }

    public void onStartedGoingToSleep(int why) {
        boolean lockImmediately;
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "onStartedGoingToSleep(" + why + ")");
        }
        synchronized (this) {
            this.mDeviceInteractive = false;
            this.mGoingToSleep = true;
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            if (!this.mLockPatternUtils.getPowerButtonInstantlyLocks(currentUser) && this.mLockPatternUtils.isSecure(currentUser)) {
                lockImmediately = false;
            } else {
                lockImmediately = true;
            }
            long timeout = getLockTimeout(KeyguardUpdateMonitor.getCurrentUser());
            this.mLockLater = false;
            if (this.mExitSecureCallback != null) {
                if (DEBUG) {
                    Log.d("KeyguardViewMediator", "pending exit secure callback cancelled");
                }
                try {
                    this.mExitSecureCallback.onKeyguardExitResult(false);
                } catch (RemoteException e) {
                    Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e);
                }
                this.mExitSecureCallback = null;
                if (!this.mExternallyEnabled) {
                    hideLocked();
                }
            } else if (this.mShowing) {
                this.mPendingReset = true;
            } else if ((why == 3 && timeout > 0) || (why == 2 && !lockImmediately)) {
                doKeyguardLaterLocked(timeout);
                this.mLockLater = true;
            } else if (!this.mLockPatternUtils.isLockScreenDisabled(currentUser)) {
                this.mPendingLock = true;
            }
            if (this.mPendingLock) {
                playSounds(true);
            }
        }
        KeyguardUpdateMonitor.getInstance(this.mContext).dispatchStartedGoingToSleep(why);
        notifyStartedGoingToSleep();
    }

    public void onFinishedGoingToSleep(int why, boolean cameraGestureTriggered) {
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "onFinishedGoingToSleep(" + why + ")");
        }
        synchronized (this) {
            this.mDeviceInteractive = false;
            this.mGoingToSleep = false;
            this.mWakeAndUnlocking = false;
            resetKeyguardDonePendingLocked();
            this.mHideAnimationRun = false;
            notifyFinishedGoingToSleep();
            if (cameraGestureTriggered) {
                Log.i("KeyguardViewMediator", "Camera gesture was triggered, preventing Keyguard locking.");
                ((PowerManager) this.mContext.getSystemService(PowerManager.class)).wakeUp(SystemClock.uptimeMillis(), "com.android.systemui:CAMERA_GESTURE_PREVENT_LOCK");
                this.mPendingLock = false;
                this.mPendingReset = false;
            }
            if (this.mPendingReset) {
                resetStateLocked();
                this.mPendingReset = false;
            }
            if (this.mPendingLock) {
                doKeyguardLocked(null);
                this.mPendingLock = false;
            }
            if (!this.mLockLater && !cameraGestureTriggered) {
                doKeyguardForChildProfilesLocked();
            }
        }
        KeyguardUpdateMonitor.getInstance(this.mContext).dispatchFinishedGoingToSleep(why);
    }

    private long getLockTimeout(int userId) {
        ContentResolver cr = this.mContext.getContentResolver();
        long lockAfterTimeout = Settings.Secure.getInt(cr, "lock_screen_lock_after_timeout", 5000);
        long policyTimeout = this.mLockPatternUtils.getDevicePolicyManager().getMaximumTimeToLock(null, userId);
        if (policyTimeout <= 0) {
            return lockAfterTimeout;
        }
        long displayTimeout = Settings.System.getInt(cr, "screen_off_timeout", 30000);
        long timeout = Math.min(policyTimeout - Math.max(displayTimeout, 0L), lockAfterTimeout);
        return Math.max(timeout, 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doKeyguardLaterLocked() {
        long timeout = getLockTimeout(KeyguardUpdateMonitor.getCurrentUser());
        if (timeout == 0) {
            doKeyguardLocked(null);
        } else {
            doKeyguardLaterLocked(timeout);
        }
    }

    private void doKeyguardLaterLocked(long timeout) {
        long when = SystemClock.elapsedRealtime() + timeout;
        Intent intent = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        intent.putExtra("seq", this.mDelayedShowingSequence);
        intent.addFlags(268435456);
        PendingIntent sender = PendingIntent.getBroadcast(this.mContext, 0, intent, 268435456);
        this.mAlarmManager.setExactAndAllowWhileIdle(2, when, sender);
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "setting alarm to turn off keyguard, seq = " + this.mDelayedShowingSequence);
        }
        doKeyguardLaterForChildProfilesLocked();
    }

    private void doKeyguardLaterForChildProfilesLocked() {
        UserManager um = UserManager.get(this.mContext);
        for (int profileId : um.getEnabledProfileIds(UserHandle.myUserId())) {
            if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(profileId)) {
                long userTimeout = getLockTimeout(profileId);
                if (userTimeout == 0) {
                    doKeyguardForChildProfilesLocked();
                } else {
                    long userWhen = SystemClock.elapsedRealtime() + userTimeout;
                    Intent lockIntent = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK");
                    lockIntent.putExtra("seq", this.mDelayedProfileShowingSequence);
                    lockIntent.putExtra("android.intent.extra.USER_ID", profileId);
                    lockIntent.addFlags(268435456);
                    PendingIntent lockSender = PendingIntent.getBroadcast(this.mContext, 0, lockIntent, 268435456);
                    this.mAlarmManager.setExactAndAllowWhileIdle(2, userWhen, lockSender);
                }
            }
        }
    }

    private void doKeyguardForChildProfilesLocked() {
        UserManager um = UserManager.get(this.mContext);
        for (int profileId : um.getEnabledProfileIds(UserHandle.myUserId())) {
            if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(profileId)) {
                lockProfile(profileId);
            }
        }
    }

    private void cancelDoKeyguardLaterLocked() {
        this.mDelayedShowingSequence++;
    }

    private void cancelDoKeyguardForChildProfilesLocked() {
        this.mDelayedProfileShowingSequence++;
    }

    public void onStartedWakingUp() {
        Trace.beginSection("KeyguardViewMediator#onStartedWakingUp");
        synchronized (this) {
            this.mDeviceInteractive = true;
            cancelDoKeyguardLaterLocked();
            cancelDoKeyguardForChildProfilesLocked();
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "onStartedWakingUp, seq = " + this.mDelayedShowingSequence);
            }
            notifyStartedWakingUp();
        }
        KeyguardUpdateMonitor.getInstance(this.mContext).dispatchStartedWakingUp();
        maybeSendUserPresentBroadcast();
        Trace.endSection();
    }

    public void onScreenTurningOn(IKeyguardDrawnCallback callback) {
        Trace.beginSection("KeyguardViewMediator#onScreenTurningOn");
        notifyScreenOn(callback);
        Trace.endSection();
    }

    public void onScreenTurnedOn() {
        Trace.beginSection("KeyguardViewMediator#onScreenTurnedOn");
        notifyScreenTurnedOn();
        this.mUpdateMonitor.dispatchScreenTurnedOn();
        Trace.endSection();
    }

    public void onScreenTurnedOff() {
        notifyScreenTurnedOff();
        this.mUpdateMonitor.dispatchScreenTurnedOff();
    }

    private void maybeSendUserPresentBroadcast() {
        if (this.mSystemReady && this.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser())) {
            sendUserPresentBroadcast();
        } else if (this.mSystemReady && shouldWaitForProvisioning()) {
            getLockPatternUtils().userPresent(KeyguardUpdateMonitor.getCurrentUser());
        }
    }

    public void onDreamingStarted() {
        KeyguardUpdateMonitor.getInstance(this.mContext).dispatchDreamingStarted();
        synchronized (this) {
            if (this.mDeviceInteractive && this.mLockPatternUtils.isSecure(KeyguardUpdateMonitor.getCurrentUser())) {
                doKeyguardLaterLocked();
            }
        }
    }

    public void onDreamingStopped() {
        KeyguardUpdateMonitor.getInstance(this.mContext).dispatchDreamingStopped();
        synchronized (this) {
            if (this.mDeviceInteractive) {
                cancelDoKeyguardLaterLocked();
            }
        }
    }

    public void setKeyguardEnabled(boolean enabled) {
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "setKeyguardEnabled(" + enabled + ")");
            }
            this.mExternallyEnabled = enabled;
            if (!enabled && this.mShowing) {
                if (this.mExitSecureCallback != null) {
                    if (DEBUG) {
                        Log.d("KeyguardViewMediator", "in process of verifyUnlock request, ignoring");
                    }
                } else {
                    if (DEBUG) {
                        Log.d("KeyguardViewMediator", "remembering to reshow, hiding keyguard, disabling status bar expansion");
                    }
                    this.mNeedToReshowWhenReenabled = true;
                    updateInputRestrictedLocked();
                    hideLocked();
                }
            } else if (enabled && this.mNeedToReshowWhenReenabled) {
                if (DEBUG) {
                    Log.d("KeyguardViewMediator", "previously hidden, reshowing, reenabling status bar expansion");
                }
                this.mNeedToReshowWhenReenabled = false;
                updateInputRestrictedLocked();
                if (this.mExitSecureCallback != null) {
                    if (DEBUG) {
                        Log.d("KeyguardViewMediator", "onKeyguardExitResult(false), resetting");
                    }
                    try {
                        this.mExitSecureCallback.onKeyguardExitResult(false);
                    } catch (RemoteException e) {
                        Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e);
                    }
                    this.mExitSecureCallback = null;
                    resetStateLocked();
                } else {
                    showLocked(null);
                    this.mWaitingUntilKeyguardVisible = true;
                    this.mHandler.sendEmptyMessageDelayed(8, 2000L);
                    if (DEBUG) {
                        Log.d("KeyguardViewMediator", "waiting until mWaitingUntilKeyguardVisible is false");
                    }
                    while (this.mWaitingUntilKeyguardVisible) {
                        try {
                            wait();
                        } catch (InterruptedException e2) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    if (DEBUG) {
                        Log.d("KeyguardViewMediator", "done waiting for mWaitingUntilKeyguardVisible");
                    }
                }
            }
        }
    }

    public void verifyUnlock(IKeyguardExitCallback callback) {
        Trace.beginSection("KeyguardViewMediator#verifyUnlock");
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "verifyUnlock");
            }
            if (shouldWaitForProvisioning()) {
                if (DEBUG) {
                    Log.d("KeyguardViewMediator", "ignoring because device isn't provisioned");
                }
                try {
                    callback.onKeyguardExitResult(false);
                } catch (RemoteException e) {
                    Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e);
                }
            } else if (this.mExternallyEnabled) {
                Log.w("KeyguardViewMediator", "verifyUnlock called when not externally disabled");
                try {
                    callback.onKeyguardExitResult(false);
                } catch (RemoteException e2) {
                    Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e2);
                }
            } else if (this.mExitSecureCallback != null) {
                try {
                    callback.onKeyguardExitResult(false);
                } catch (RemoteException e3) {
                    Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e3);
                }
            } else if (!isSecure()) {
                this.mExternallyEnabled = true;
                this.mNeedToReshowWhenReenabled = false;
                updateInputRestricted();
                try {
                    callback.onKeyguardExitResult(true);
                } catch (RemoteException e4) {
                    Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e4);
                }
            } else {
                try {
                    callback.onKeyguardExitResult(false);
                } catch (RemoteException e5) {
                    Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e5);
                }
            }
            throw th;
        }
        Trace.endSection();
    }

    public boolean isShowingAndNotOccluded() {
        return this.mShowing && !this.mOccluded;
    }

    public void setOccluded(boolean z, boolean z2) {
        Trace.beginSection("KeyguardViewMediator#setOccluded");
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "setOccluded " + z);
        }
        this.mHandler.removeMessages(9);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(9, z ? 1 : 0, z2 ? 1 : 0));
        Trace.endSection();
    }

    public boolean isHiding() {
        return this.mHiding;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetOccluded(boolean isOccluded, boolean animate) {
        Trace.beginSection("KeyguardViewMediator#handleSetOccluded");
        synchronized (this) {
            if (this.mHiding && isOccluded) {
                startKeyguardExitAnimation(0L, 0L);
            }
            this.mOccluded = isOccluded;
            this.mUpdateMonitor.setKeyguardOccluded(isOccluded);
            this.mStatusBarKeyguardViewManager.setOccluded(isOccluded, animate && this.mDeviceInteractive);
            adjustStatusBarLocked();
        }
        Trace.endSection();
    }

    public void doKeyguardTimeout(Bundle options) {
        this.mHandler.removeMessages(10);
        Message msg = this.mHandler.obtainMessage(10, options);
        this.mHandler.sendMessage(msg);
    }

    public boolean isInputRestricted() {
        return this.mShowing || this.mNeedToReshowWhenReenabled;
    }

    private void updateInputRestricted() {
        synchronized (this) {
            updateInputRestrictedLocked();
        }
    }

    private void updateInputRestrictedLocked() {
        boolean inputRestricted = isInputRestricted();
        if (this.mInputRestricted != inputRestricted) {
            this.mInputRestricted = inputRestricted;
            int size = this.mKeyguardStateCallbacks.size();
            for (int i = size - 1; i >= 0; i--) {
                IKeyguardStateCallback callback = this.mKeyguardStateCallbacks.get(i);
                try {
                    callback.onInputRestrictedStateChanged(inputRestricted);
                } catch (RemoteException e) {
                    Slog.w("KeyguardViewMediator", "Failed to call onDeviceProvisioned", e);
                    if (e instanceof DeadObjectException) {
                        this.mKeyguardStateCallbacks.remove(callback);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doKeyguardLocked(Bundle options) {
        if (KeyguardUpdateMonitor.CORE_APPS_ONLY) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "doKeyguard: not showing because booting to cryptkeeper");
                return;
            }
            return;
        }
        if (!this.mExternallyEnabled || PowerOffAlarmManager.isAlarmBoot()) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "doKeyguard: not showing because externally disabled");
                return;
            }
            return;
        }
        if (this.mStatusBarKeyguardViewManager.isShowing()) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "doKeyguard: not showing because it is already showing");
            }
            resetStateLocked();
            return;
        }
        if (!mustNotUnlockCurrentUser() || !this.mUpdateMonitor.isDeviceProvisioned()) {
            boolean requireSim = !SystemProperties.getBoolean("keyguard.no_require_sim", true);
            boolean lockedOrMissing = false;
            for (int i = 0; i < KeyguardUtils.getNumOfPhone(); i++) {
                if (isSimLockedOrMissing(i, requireSim)) {
                    lockedOrMissing = true;
                    break;
                }
            }
            boolean antiTheftLocked = AntiTheftManager.isAntiTheftLocked();
            Log.d("KeyguardViewMediator", "lockedOrMissing is " + lockedOrMissing + ", requireSim=" + requireSim + ", antiTheftLocked=" + antiTheftLocked);
            if (!lockedOrMissing && shouldWaitForProvisioning() && !antiTheftLocked) {
                if (DEBUG) {
                    Log.d("KeyguardViewMediator", "doKeyguard: not showing because device isn't provisioned and the sim is not locked or missing");
                    return;
                }
                return;
            }
            boolean forceShow = options != null && options.getBoolean("force_show", false);
            if (this.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser()) && !lockedOrMissing && !forceShow && !antiTheftLocked) {
                if (DEBUG) {
                    Log.d("KeyguardViewMediator", "doKeyguard: not showing because lockscreen is off");
                    return;
                }
                return;
            } else if (this.mLockPatternUtils.checkVoldPassword(KeyguardUpdateMonitor.getCurrentUser()) && KeyguardUtils.isSystemEncrypted()) {
                if (DEBUG) {
                    Log.d("KeyguardViewMediator", "Not showing lock screen since just decrypted");
                }
                setShowingLocked(false, this.mAodShowing);
                hideLocked();
                return;
            }
        }
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "doKeyguard: showing the lock screen");
        }
        showLocked(options);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lockProfile(int userId) {
        this.mTrustManager.setDeviceLockedForUser(userId, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldWaitForProvisioning() {
        return (this.mUpdateMonitor.isDeviceProvisioned() || isSecure()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDismiss(IKeyguardDismissCallback callback, CharSequence message) {
        if (this.mShowing) {
            if (callback != null) {
                this.mDismissCallbackRegistry.addCallback(callback);
            }
            this.mCustomMessage = message;
            this.mStatusBarKeyguardViewManager.dismissAndCollapse();
            return;
        }
        if (callback != null) {
            new DismissCallbackWrapper(callback).notifyDismissError();
        }
    }

    public void dismiss(IKeyguardDismissCallback callback, CharSequence message) {
        this.mHandler.obtainMessage(11, new DismissMessage(callback, message)).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetStateLocked() {
        if (DEBUG) {
            Log.e("KeyguardViewMediator", "resetStateLocked");
        }
        Message msg = this.mHandler.obtainMessage(3);
        this.mHandler.sendMessage(msg);
    }

    private void notifyStartedGoingToSleep() {
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "notifyStartedGoingToSleep");
        }
        this.mHandler.sendEmptyMessage(17);
    }

    private void notifyFinishedGoingToSleep() {
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "notifyFinishedGoingToSleep");
        }
        this.mHandler.sendEmptyMessage(5);
    }

    private void notifyStartedWakingUp() {
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "notifyStartedWakingUp");
        }
        this.mHandler.sendEmptyMessage(14);
    }

    private void notifyScreenOn(IKeyguardDrawnCallback callback) {
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "notifyScreenOn");
        }
        Message msg = this.mHandler.obtainMessage(6, callback);
        this.mHandler.sendMessage(msg);
    }

    private void notifyScreenTurnedOn() {
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "notifyScreenTurnedOn");
        }
        Message msg = this.mHandler.obtainMessage(15);
        this.mHandler.sendMessage(msg);
    }

    private void notifyScreenTurnedOff() {
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "notifyScreenTurnedOff");
        }
        Message msg = this.mHandler.obtainMessage(16);
        this.mHandler.sendMessage(msg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLocked(Bundle options) {
        Trace.beginSection("KeyguardViewMediator#showLocked aqcuiring mShowKeyguardWakeLock");
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "showLocked");
        }
        this.mShowKeyguardWakeLock.acquire();
        Message msg = this.mHandler.obtainMessage(1, options);
        this.mHandler.sendMessage(msg);
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideLocked() {
        Trace.beginSection("KeyguardViewMediator#hideLocked");
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "hideLocked");
        }
        Message msg = this.mHandler.obtainMessage(2);
        this.mHandler.sendMessage(msg);
        Trace.endSection();
    }

    public boolean isSecure() {
        return isSecure(KeyguardUpdateMonitor.getCurrentUser());
    }

    public boolean isSecure(int userId) {
        return this.mLockPatternUtils.isSecure(userId) || KeyguardUpdateMonitor.getInstance(this.mContext).isSimPinSecure() || AntiTheftManager.isAntiTheftLocked();
    }

    public void setSwitchingUser(boolean switching) {
        KeyguardUpdateMonitor.getInstance(this.mContext).setSwitchingUser(switching);
    }

    public void setCurrentUser(int newUserId) {
        KeyguardUpdateMonitor.setCurrentUser(newUserId);
        synchronized (this) {
            notifyTrustedChangedLocked(this.mUpdateMonitor.getUserHasTrust(newUserId));
        }
    }

    public void keyguardDone() {
        Trace.beginSection("KeyguardViewMediator#keyguardDone");
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "keyguardDone()");
        }
        userActivity();
        EventLog.writeEvent(70000, 2);
        Message msg = this.mHandler.obtainMessage(7);
        this.mHandler.sendMessage(msg);
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryKeyguardDone() {
        if (!this.mKeyguardDonePending && this.mHideAnimationRun && !this.mHideAnimationRunning) {
            handleKeyguardDone();
        } else if (!this.mHideAnimationRun) {
            this.mHideAnimationRun = true;
            this.mHideAnimationRunning = true;
            this.mStatusBarKeyguardViewManager.startPreHideAnimation(this.mHideAnimationFinishedRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleKeyguardDone() {
        Trace.beginSection("KeyguardViewMediator#handleKeyguardDone");
        final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.keyguard.-$$Lambda$KeyguardViewMediator$_5R5BVmx-ThRHQbevLLkSqkvnz0
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardViewMediator.lambda$handleKeyguardDone$0(this.f$0, currentUser);
            }
        });
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "handleKeyguardDone");
        }
        synchronized (this) {
            resetKeyguardDonePendingLocked();
        }
        if (AntiTheftManager.isAntiTheftLocked()) {
            Log.d("KeyguardViewMediator", "handleKeyguardDone() - Skip keyguard done! antitheft = true or sim = " + this.mUpdateMonitor.isSimPinSecure());
            return;
        }
        mKeyguardDoneOnGoing = true;
        this.mUpdateMonitor.clearFailedUnlockAttempts();
        this.mUpdateMonitor.clearFingerprintRecognized();
        if (this.mGoingToSleep) {
            Log.i("KeyguardViewMediator", "Device is going to sleep, aborting keyguardDone");
            return;
        }
        if (this.mExitSecureCallback != null) {
            try {
                this.mExitSecureCallback.onKeyguardExitResult(true);
            } catch (RemoteException e) {
                Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult()", e);
            }
            this.mExitSecureCallback = null;
            this.mExternallyEnabled = true;
            this.mNeedToReshowWhenReenabled = false;
            updateInputRestricted();
        }
        this.mSuppressNextLockSound = false;
        handleHide();
        Trace.endSection();
    }

    public static /* synthetic */ void lambda$handleKeyguardDone$0(KeyguardViewMediator keyguardViewMediator, int currentUser) {
        if (keyguardViewMediator.mLockPatternUtils.isSecure(currentUser)) {
            keyguardViewMediator.mLockPatternUtils.getDevicePolicyManager().reportKeyguardDismissed(currentUser);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUserPresentBroadcast() {
        synchronized (this) {
            if (this.mBootCompleted) {
                final int currentUserId = KeyguardUpdateMonitor.getCurrentUser();
                final UserHandle currentUser = new UserHandle(currentUserId);
                final UserManager um = (UserManager) this.mContext.getSystemService("user");
                this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.keyguard.-$$Lambda$KeyguardViewMediator$Zo4yApohhKZDVLRFEZ5fXw6KLNI
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardViewMediator.lambda$sendUserPresentBroadcast$1(this.f$0, um, currentUser, currentUserId);
                    }
                });
            } else {
                this.mBootSendUserPresent = true;
            }
        }
    }

    public static /* synthetic */ void lambda$sendUserPresentBroadcast$1(KeyguardViewMediator keyguardViewMediator, UserManager um, UserHandle currentUser, int currentUserId) {
        for (int profileId : um.getProfileIdsWithDisabled(currentUser.getIdentifier())) {
            keyguardViewMediator.mContext.sendBroadcastAsUser(USER_PRESENT_INTENT, UserHandle.of(profileId));
        }
        keyguardViewMediator.getLockPatternUtils().userPresent(currentUserId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleKeyguardDoneDrawing() {
        Trace.beginSection("KeyguardViewMediator#handleKeyguardDoneDrawing");
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleKeyguardDoneDrawing");
            }
            if (this.mWaitingUntilKeyguardVisible) {
                if (DEBUG) {
                    Log.d("KeyguardViewMediator", "handleKeyguardDoneDrawing: notifying mWaitingUntilKeyguardVisible");
                }
                this.mWaitingUntilKeyguardVisible = false;
                notifyAll();
                this.mHandler.removeMessages(8);
            }
        }
        Trace.endSection();
    }

    private void playSounds(boolean locked) {
        Log.d("KeyguardViewMediator", "playSounds(locked = " + locked + "), mSuppressNextLockSound =" + this.mSuppressNextLockSound);
        if (this.mSuppressNextLockSound) {
            this.mSuppressNextLockSound = false;
        } else {
            playSound(locked ? this.mLockSoundId : this.mUnlockSoundId);
        }
    }

    private void playSound(final int soundId) {
        if (soundId == 0) {
            return;
        }
        ContentResolver cr = this.mContext.getContentResolver();
        if (Settings.System.getInt(cr, "lockscreen_sounds_enabled", 1) == 1) {
            this.mLockSounds.stop(this.mLockSoundStreamId);
            if (this.mAudioManager == null) {
                this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
                if (this.mAudioManager == null) {
                    return;
                } else {
                    this.mUiSoundsStreamType = this.mAudioManager.getUiSoundsStreamType();
                }
            }
            this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.keyguard.-$$Lambda$KeyguardViewMediator$gkamSmMNqxOX1FiRuNJrEyzupq0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardViewMediator.lambda$playSound$2(this.f$0, soundId);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$playSound$2(KeyguardViewMediator keyguardViewMediator, int soundId) {
        if (keyguardViewMediator.mAudioManager.isStreamMute(keyguardViewMediator.mUiSoundsStreamType)) {
            return;
        }
        int id = keyguardViewMediator.mLockSounds.play(soundId, keyguardViewMediator.mLockSoundVolume, keyguardViewMediator.mLockSoundVolume, 1, 0, 1.0f);
        synchronized (keyguardViewMediator) {
            keyguardViewMediator.mLockSoundStreamId = id;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playTrustedSound() {
        if (this.mSuppressNextLockSound) {
            return;
        }
        playSound(this.mTrustedSoundId);
    }

    private void updateActivityLockScreenState(final boolean showing, final boolean aodShowing, final int secondaryDisplayShowing) {
        this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.keyguard.-$$Lambda$KeyguardViewMediator$VfeT6HygUNYTqb-KGjnpPirW-Z4
            @Override // java.lang.Runnable
            public final void run() {
                ActivityManager.getService().setLockScreenShown(showing, aodShowing, secondaryDisplayShowing);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleShow(Bundle options) {
        Trace.beginSection("KeyguardViewMediator#handleShow");
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        if (this.mLockPatternUtils.isSecure(currentUser)) {
            this.mLockPatternUtils.getDevicePolicyManager().reportKeyguardSecured(currentUser);
        }
        synchronized (this) {
            if (!this.mSystemReady) {
                if (DEBUG) {
                    Log.d("KeyguardViewMediator", "ignoring handleShow because system is not ready.");
                }
                return;
            }
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleShow");
            }
            setShowingLocked(true, this.mAodShowing);
            this.mStatusBarKeyguardViewManager.show(options);
            this.mHiding = false;
            this.mWakeAndUnlocking = false;
            resetKeyguardDonePendingLocked();
            this.mHideAnimationRun = false;
            adjustStatusBarLocked();
            userActivity();
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.6
                @Override // java.lang.Runnable
                public void run() {
                    if (ActivityManager.isSystemReady()) {
                        try {
                            ActivityManager.getService().closeSystemDialogs("lock");
                        } catch (RemoteException e) {
                        }
                    }
                }
            }, 500L);
            if (PowerOffAlarmManager.isAlarmBoot()) {
                this.mPowerOffAlarmManager.startAlarm();
            }
            this.mShowKeyguardWakeLock.release();
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleShow exit");
            }
            if (this.mKeyguardDisplayManager != null) {
                Log.d("KeyguardViewMediator", "handle show call mKeyguardDisplayManager.show()");
                this.mKeyguardDisplayManager.show();
            } else {
                Log.d("KeyguardViewMediator", "handle show mKeyguardDisplayManager is null");
            }
            Trace.endSection();
        }
    }

    public static /* synthetic */ void lambda$new$4(KeyguardViewMediator keyguardViewMediator) {
        keyguardViewMediator.mHideAnimationRunning = false;
        keyguardViewMediator.tryKeyguardDone();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHide() {
        Trace.beginSection("KeyguardViewMediator#handleHide");
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleHide");
            }
            if (mustNotUnlockCurrentUser()) {
                if (DEBUG) {
                    Log.d("KeyguardViewMediator", "Split system user, quit unlocking.");
                }
                return;
            }
            this.mHiding = true;
            if (this.mShowing && !this.mOccluded) {
                this.mKeyguardGoingAwayRunnable.run();
            } else {
                handleStartKeyguardExitAnimation(SystemClock.uptimeMillis() + this.mHideAnimation.getStartOffset(), this.mHideAnimation.getDuration());
            }
            Trace.endSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStartKeyguardExitAnimation(long startTime, long fadeoutDuration) {
        Trace.beginSection("KeyguardViewMediator#handleStartKeyguardExitAnimation");
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "handleStartKeyguardExitAnimation startTime=" + startTime + " fadeoutDuration=" + fadeoutDuration);
        }
        synchronized (this) {
            if (!this.mHiding) {
                setShowingLocked(this.mShowing, this.mAodShowing, this.mSecondaryDisplayShowing, true);
                return;
            }
            this.mHiding = false;
            if (this.mWakeAndUnlocking && this.mDrawnCallback != null) {
                this.mStatusBarKeyguardViewManager.getViewRootImpl().setReportNextDraw();
                notifyDrawn(this.mDrawnCallback);
                this.mDrawnCallback = null;
            }
            if (TelephonyManager.EXTRA_STATE_IDLE.equals(this.mPhoneState) && this.mShowing) {
                playSounds(false);
            }
            this.mWakeAndUnlocking = false;
            setShowingLocked(false, this.mAodShowing);
            this.mDismissCallbackRegistry.notifyDismissSucceeded();
            this.mStatusBarKeyguardViewManager.hide(startTime, fadeoutDuration);
            resetKeyguardDonePendingLocked();
            this.mHideAnimationRun = false;
            adjustStatusBarLocked();
            sendUserPresentBroadcast();
            this.mUpdateMonitor.setKeyguardGoingAway(false);
            Log.d("KeyguardViewMediator", "set mKeyguardDoneOnGoing = false");
            mKeyguardDoneOnGoing = false;
            Trace.endSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustStatusBarLocked() {
        adjustStatusBarLocked(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustStatusBarLocked(boolean forceHideHomeRecentsButtons) {
        if (this.mStatusBarManager == null) {
            this.mStatusBarManager = (StatusBarManager) this.mContext.getSystemService("statusbar");
        }
        if (this.mStatusBarManager == null) {
            Log.w("KeyguardViewMediator", "Could not get status bar manager");
            return;
        }
        int flags = 0;
        if (this.mShowing && PowerOffAlarmManager.isAlarmBoot()) {
            flags = 0 | 33554432;
        }
        if (forceHideHomeRecentsButtons || isShowingAndNotOccluded()) {
            flags |= 18874368;
        }
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "adjustStatusBarLocked: mShowing=" + this.mShowing + " mOccluded=" + this.mOccluded + " isSecure=" + isSecure() + " force=" + forceHideHomeRecentsButtons + " --> flags=0x" + Integer.toHexString(flags));
        }
        this.mStatusBarManager.disable(flags);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReset() {
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleReset");
            }
            this.mStatusBarKeyguardViewManager.reset(true);
            adjustStatusBarLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleVerifyUnlock() {
        Trace.beginSection("KeyguardViewMediator#handleVerifyUnlock");
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleVerifyUnlock");
            }
            setShowingLocked(true, this.mAodShowing);
            this.mStatusBarKeyguardViewManager.dismissAndCollapse();
        }
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNotifyStartedGoingToSleep() {
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleNotifyStartedGoingToSleep");
            }
            this.mStatusBarKeyguardViewManager.onStartedGoingToSleep();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNotifyFinishedGoingToSleep() {
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleNotifyFinishedGoingToSleep");
            }
            this.mStatusBarKeyguardViewManager.onFinishedGoingToSleep();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNotifyStartedWakingUp() {
        Trace.beginSection("KeyguardViewMediator#handleMotifyStartedWakingUp");
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleNotifyWakingUp");
            }
            this.mStatusBarKeyguardViewManager.onStartedWakingUp();
        }
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNotifyScreenTurningOn(IKeyguardDrawnCallback callback) {
        Trace.beginSection("KeyguardViewMediator#handleNotifyScreenTurningOn");
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleNotifyScreenTurningOn");
            }
            this.mStatusBarKeyguardViewManager.onScreenTurningOn();
            if (callback != null) {
                if (this.mWakeAndUnlocking) {
                    this.mDrawnCallback = callback;
                } else {
                    notifyDrawn(callback);
                }
            }
        }
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNotifyScreenTurnedOn() {
        Trace.beginSection("KeyguardViewMediator#handleNotifyScreenTurnedOn");
        if (LatencyTracker.isEnabled(this.mContext)) {
            LatencyTracker.getInstance(this.mContext).onActionEnd(5);
        }
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleNotifyScreenTurnedOn");
            }
            this.mStatusBarKeyguardViewManager.onScreenTurnedOn();
        }
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNotifyScreenTurnedOff() {
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleNotifyScreenTurnedOff");
            }
            this.mStatusBarKeyguardViewManager.onScreenTurnedOff();
            this.mDrawnCallback = null;
        }
    }

    private void notifyDrawn(IKeyguardDrawnCallback callback) {
        Trace.beginSection("KeyguardViewMediator#notifyDrawn");
        try {
            callback.onDrawn();
        } catch (RemoteException e) {
            Slog.w("KeyguardViewMediator", "Exception calling onDrawn():", e);
        }
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetKeyguardDonePendingLocked() {
        this.mKeyguardDonePending = false;
        this.mHandler.removeMessages(13);
    }

    @Override // com.android.systemui.SystemUI
    public void onBootCompleted() {
        Log.d("KeyguardViewMediator", "onBootCompleted() is called");
        this.mUpdateMonitor.dispatchBootCompleted();
        synchronized (this) {
            this.mBootCompleted = true;
            if (this.mBootSendUserPresent) {
                sendUserPresentBroadcast();
            }
        }
    }

    public void onWakeAndUnlocking() {
        Trace.beginSection("KeyguardViewMediator#onWakeAndUnlocking");
        this.mWakeAndUnlocking = true;
        keyguardDone();
        Trace.endSection();
    }

    public StatusBarKeyguardViewManager registerStatusBar(StatusBar statusBar, ViewGroup container, NotificationPanelView panelView, FingerprintUnlockController fingerprintUnlockController) {
        this.mStatusBarKeyguardViewManager.registerStatusBar(statusBar, container, panelView, fingerprintUnlockController, this.mDismissCallbackRegistry);
        return this.mStatusBarKeyguardViewManager;
    }

    public void startKeyguardExitAnimation(long startTime, long fadeoutDuration) {
        Trace.beginSection("KeyguardViewMediator#startKeyguardExitAnimation");
        Message msg = this.mHandler.obtainMessage(12, new StartKeyguardExitAnimParams(startTime, fadeoutDuration));
        this.mHandler.sendMessage(msg);
        Trace.endSection();
    }

    public void onShortPowerPressedGoHome() {
    }

    public ViewMediatorCallback getViewMediatorCallback() {
        return this.mViewMediatorCallback;
    }

    public LockPatternUtils getLockPatternUtils() {
        return this.mLockPatternUtils;
    }

    @Override // com.android.systemui.SystemUI
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.print("  mSystemReady: ");
        pw.println(this.mSystemReady);
        pw.print("  mBootCompleted: ");
        pw.println(this.mBootCompleted);
        pw.print("  mBootSendUserPresent: ");
        pw.println(this.mBootSendUserPresent);
        pw.print("  mExternallyEnabled: ");
        pw.println(this.mExternallyEnabled);
        pw.print("  mShuttingDown: ");
        pw.println(this.mShuttingDown);
        pw.print("  mNeedToReshowWhenReenabled: ");
        pw.println(this.mNeedToReshowWhenReenabled);
        pw.print("  mShowing: ");
        pw.println(this.mShowing);
        pw.print("  mInputRestricted: ");
        pw.println(this.mInputRestricted);
        pw.print("  mOccluded: ");
        pw.println(this.mOccluded);
        pw.print("  mDelayedShowingSequence: ");
        pw.println(this.mDelayedShowingSequence);
        pw.print("  mExitSecureCallback: ");
        pw.println(this.mExitSecureCallback);
        pw.print("  mDeviceInteractive: ");
        pw.println(this.mDeviceInteractive);
        pw.print("  mGoingToSleep: ");
        pw.println(this.mGoingToSleep);
        pw.print("  mHiding: ");
        pw.println(this.mHiding);
        pw.print("  mWaitingUntilKeyguardVisible: ");
        pw.println(this.mWaitingUntilKeyguardVisible);
        pw.print("  mKeyguardDonePending: ");
        pw.println(this.mKeyguardDonePending);
        pw.print("  mHideAnimationRun: ");
        pw.println(this.mHideAnimationRun);
        pw.print("  mPendingReset: ");
        pw.println(this.mPendingReset);
        pw.print("  mPendingLock: ");
        pw.println(this.mPendingLock);
        pw.print("  mWakeAndUnlocking: ");
        pw.println(this.mWakeAndUnlocking);
        pw.print("  mDrawnCallback: ");
        pw.println(this.mDrawnCallback);
    }

    public void setAodShowing(boolean aodShowing) {
        setShowingLocked(this.mShowing, aodShowing);
    }

    private static class StartKeyguardExitAnimParams {
        long fadeoutDuration;
        long startTime;

        private StartKeyguardExitAnimParams(long startTime, long fadeoutDuration) {
            this.startTime = startTime;
            this.fadeoutDuration = fadeoutDuration;
        }
    }

    private void setShowingLocked(boolean showing, boolean aodShowing) {
        setShowingLocked(showing, aodShowing, this.mSecondaryDisplayShowing, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setShowingLocked(boolean showing, boolean aodShowing, int secondaryDisplayShowing, boolean forceCallbacks) {
        Log.d("KeyguardViewMediator", "setShowingLocked: " + showing + ", " + aodShowing + ", " + secondaryDisplayShowing + ", " + forceCallbacks);
        boolean notifyDefaultDisplayCallbacks = (showing == this.mShowing && aodShowing == this.mAodShowing && !forceCallbacks) ? false : true;
        if (notifyDefaultDisplayCallbacks || secondaryDisplayShowing != this.mSecondaryDisplayShowing) {
            this.mShowing = showing;
            this.mAodShowing = aodShowing;
            this.mSecondaryDisplayShowing = secondaryDisplayShowing;
            if (notifyDefaultDisplayCallbacks) {
                notifyDefaultDisplayCallbacks(showing);
            }
            updateActivityLockScreenState(showing, aodShowing, secondaryDisplayShowing);
        }
    }

    private void notifyDefaultDisplayCallbacks(boolean showing) {
        int size = this.mKeyguardStateCallbacks.size();
        for (int i = size - 1; i >= 0; i--) {
            IKeyguardStateCallback callback = this.mKeyguardStateCallbacks.get(i);
            try {
                callback.onShowingStateChanged(showing);
            } catch (RemoteException e) {
                Slog.w("KeyguardViewMediator", "Failed to call onShowingStateChanged", e);
                if (e instanceof DeadObjectException) {
                    this.mKeyguardStateCallbacks.remove(callback);
                }
            }
        }
        updateInputRestrictedLocked();
        this.mUiOffloadThread.submit(new Runnable() { // from class: com.android.systemui.keyguard.-$$Lambda$KeyguardViewMediator$tj2ooDljMkGvvuUoztwrUid_YnI
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mTrustManager.reportKeyguardShowingChanged();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTrustedChangedLocked(boolean trusted) {
        int size = this.mKeyguardStateCallbacks.size();
        for (int i = size - 1; i >= 0; i--) {
            try {
                this.mKeyguardStateCallbacks.get(i).onTrustedChanged(trusted);
            } catch (RemoteException e) {
                Slog.w("KeyguardViewMediator", "Failed to call notifyTrustedChangedLocked", e);
                if (e instanceof DeadObjectException) {
                    this.mKeyguardStateCallbacks.remove(i);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyHasLockscreenWallpaperChanged(boolean hasLockscreenWallpaper) {
        int size = this.mKeyguardStateCallbacks.size();
        for (int i = size - 1; i >= 0; i--) {
            try {
                this.mKeyguardStateCallbacks.get(i).onHasLockscreenWallpaperChanged(hasLockscreenWallpaper);
            } catch (RemoteException e) {
                Slog.w("KeyguardViewMediator", "Failed to call onHasLockscreenWallpaperChanged", e);
                if (e instanceof DeadObjectException) {
                    this.mKeyguardStateCallbacks.remove(i);
                }
            }
        }
    }

    public void addStateMonitorCallback(IKeyguardStateCallback callback) {
        synchronized (this) {
            this.mKeyguardStateCallbacks.add(callback);
            try {
                callback.onSimSecureStateChanged(this.mUpdateMonitor.isSimPinSecure());
                callback.onShowingStateChanged(this.mShowing);
                callback.onInputRestrictedStateChanged(this.mInputRestricted);
                callback.onTrustedChanged(this.mUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser()));
                callback.onHasLockscreenWallpaperChanged(this.mUpdateMonitor.hasLockscreenWallpaper());
            } catch (RemoteException e) {
                Slog.w("KeyguardViewMediator", "Failed to call to IKeyguardStateCallback", e);
            }
        }
    }

    private static class DismissMessage {
        private final IKeyguardDismissCallback mCallback;
        private final CharSequence mMessage;

        DismissMessage(IKeyguardDismissCallback callback, CharSequence message) {
            this.mCallback = callback;
            this.mMessage = message;
        }

        public IKeyguardDismissCallback getCallback() {
            return this.mCallback;
        }

        public CharSequence getMessage() {
            return this.mMessage;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeKeyguardDoneMsg() {
        this.mHandler.removeMessages(7);
    }

    private class InvalidDialogCallback implements KeyguardDialogManager.DialogShowCallBack {
        private InvalidDialogCallback() {
        }

        @Override // com.mediatek.keyguard.Telephony.KeyguardDialogManager.DialogShowCallBack
        public void show() {
            String title = KeyguardViewMediator.this.mContext.getString(R.string.invalid_sim_title);
            String message = KeyguardViewMediator.this.mContext.getString(R.string.invalid_sim_message);
            AlertDialog dialog = KeyguardViewMediator.this.createDialog(title, message);
            dialog.show();
        }
    }

    private class MeLockedDialogCallback implements KeyguardDialogManager.DialogShowCallBack {
        private MeLockedDialogCallback() {
        }

        @Override // com.mediatek.keyguard.Telephony.KeyguardDialogManager.DialogShowCallBack
        public void show() {
            String message = KeyguardViewMediator.this.mContext.getString(R.string.simlock_slot_locked_message);
            AlertDialog dialog = KeyguardViewMediator.this.createDialog(null, message);
            dialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AlertDialog createDialog(String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(this.mContext).setTitle(title).setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).setMessage(message).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog2, int which) {
                if (KeyguardViewMediator.this.mDialogManager != null) {
                    KeyguardViewMediator.this.mDialogManager.reportDialogClose();
                }
                Log.d("KeyguardViewMediator", "invalid sim card ,reportCloseDialog");
            }
        }).create();
        dialog.getWindow().setType(2003);
        return dialog;
    }

    public boolean isKeyguardDoneOnGoing() {
        return mKeyguardDoneOnGoing;
    }

    public boolean isShowing() {
        return this.mShowing;
    }

    private boolean isSimLockedOrMissing(int phoneId, boolean requireSim) {
        IccCardConstants.State state = this.mUpdateMonitor.getSimStateOfPhoneId(phoneId);
        return this.mUpdateMonitor.isSimPinSecure(phoneId) || ((state == IccCardConstants.State.ABSENT || state == IccCardConstants.State.PERM_DISABLED) && requireSim);
    }

    public void handleDismissInternal(boolean allowWhileOccluded) {
        if (this.mShowing) {
            if (allowWhileOccluded || !this.mOccluded) {
                this.mStatusBarKeyguardViewManager.dismiss(allowWhileOccluded);
            }
        }
    }

    void setSuppressPlaySoundFlag() {
        this.mSuppressNextLockSound = true;
    }

    void updateNavbarStatus() {
        Log.d("KeyguardViewMediator", "updateNavbarStatus() is called.");
        this.mStatusBarKeyguardViewManager.updateStates();
    }
}
