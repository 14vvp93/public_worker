package com.android.keyguard;

import android.app.ActivityManager;
import android.app.Instrumentation;
import android.app.UserSwitchObserver;
import android.app.admin.DevicePolicyManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.hardware.fingerprint.FingerprintManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.recents.misc.SysUiTaskStackChangeListener;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.mediatek.internal.telephony.IMtkTelephonyEx;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardUpdateMonitor implements TrustManager.TrustListener {
    public static final boolean CORE_APPS_ONLY;
    private static final boolean DEBUG = KeyguardConstants.DEBUG;
    private static final boolean DEBUG_SIM_STATES = KeyguardConstants.DEBUG_SIM_STATES;
    private static final ComponentName FALLBACK_HOME_COMPONENT = new ComponentName("com.android.settings", "com.android.settings.FallbackHome");
    private static int sCurrentUser;
    private static boolean sDisableHandlerCheckForTesting;
    private static KeyguardUpdateMonitor sInstance;
    private boolean mAssistantVisible;
    private BatteryStatus mBatteryStatus;
    private boolean mBootCompleted;
    private boolean mBouncer;
    private final Context mContext;
    private boolean mDeviceInteractive;
    private final DevicePolicyManager mDevicePolicyManager;
    private ContentObserver mDeviceProvisionedObserver;
    private final IDreamManager mDreamManager;
    private CancellationSignal mFingerprintCancelSignal;
    private FingerprintManager mFpm;
    private boolean mGoingToSleep;
    private boolean mHasLockscreenWallpaper;
    private boolean mIsDreaming;
    private boolean mKeyguardGoingAway;
    private boolean mKeyguardIsVisible;
    private boolean mKeyguardOccluded;
    private LockPatternUtils mLockPatternUtils;
    private boolean mLogoutEnabled;
    private boolean mNeedsSlowUnlockTransition;
    private int mPhoneState;
    private int mRingMode;
    private boolean mScreenOn;
    private final StrongAuthTracker mStrongAuthTracker;
    private List<SubscriptionInfo> mSubscriptionInfo;
    private SubscriptionManager mSubscriptionManager;
    private boolean mSwitchingUser;
    private TrustManager mTrustManager;
    private UserManager mUserManager;
    private WifiManager mWifiManager;
    HashMap<Integer, SimData> mSimDatas = new HashMap<>();
    HashMap<Integer, ServiceState> mServiceStates = new HashMap<>();
    private SparseIntArray mFailedAttempts = new SparseIntArray();
    private final CopyOnWriteArrayList<WeakReference<KeyguardUpdateMonitorCallback>> mCallbacks = new CopyOnWriteArrayList<>();
    private int mFingerprintRunningState = 0;
    private int mHardwareUnavailableRetryCount = 0;
    private final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.keyguard.KeyguardUpdateMonitor.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i != 1015) {
                switch (i) {
                    case 301:
                        KeyguardUpdateMonitor.this.handleTimeUpdate();
                        break;
                    case 302:
                        KeyguardUpdateMonitor.this.handleBatteryUpdate((BatteryStatus) msg.obj);
                        break;
                    default:
                        switch (i) {
                            case 304:
                                KeyguardUpdateMonitor.this.handleSimStateChange((SimData) msg.obj);
                                break;
                            case 305:
                                KeyguardUpdateMonitor.this.handleRingerModeChange(msg.arg1);
                                break;
                            case 306:
                                KeyguardUpdateMonitor.this.handlePhoneStateChanged();
                                break;
                            default:
                                switch (i) {
                                    case 308:
                                        KeyguardUpdateMonitor.this.handleDeviceProvisioned();
                                        break;
                                    case 309:
                                        KeyguardUpdateMonitor.this.handleDevicePolicyManagerStateChanged();
                                        break;
                                    case 310:
                                        KeyguardUpdateMonitor.this.handleUserSwitching(msg.arg1, (IRemoteCallback) msg.obj);
                                        break;
                                    default:
                                        switch (i) {
                                            case 312:
                                                KeyguardUpdateMonitor.this.handleKeyguardReset();
                                                break;
                                            case 313:
                                                KeyguardUpdateMonitor.this.handleBootCompleted();
                                                break;
                                            case 314:
                                                KeyguardUpdateMonitor.this.handleUserSwitchComplete(msg.arg1);
                                                break;
                                            default:
                                                switch (i) {
                                                    case 317:
                                                        KeyguardUpdateMonitor.this.handleUserInfoChanged(msg.arg1);
                                                        break;
                                                    case 318:
                                                        KeyguardUpdateMonitor.this.handleReportEmergencyCallAction();
                                                        break;
                                                    case 319:
                                                        Trace.beginSection("KeyguardUpdateMonitor#handler MSG_STARTED_WAKING_UP");
                                                        KeyguardUpdateMonitor.this.handleStartedWakingUp();
                                                        Trace.endSection();
                                                        break;
                                                    case 320:
                                                        KeyguardUpdateMonitor.this.handleFinishedGoingToSleep(msg.arg1);
                                                        break;
                                                    case 321:
                                                        KeyguardUpdateMonitor.this.handleStartedGoingToSleep(msg.arg1);
                                                        break;
                                                    case 322:
                                                        KeyguardUpdateMonitor.this.handleKeyguardBouncerChanged(msg.arg1);
                                                        break;
                                                    default:
                                                        switch (i) {
                                                            case 327:
                                                                Trace.beginSection("KeyguardUpdateMonitor#handler MSG_FACE_UNLOCK_STATE_CHANGED");
                                                                KeyguardUpdateMonitor.this.handleFaceUnlockStateChanged(msg.arg1 != 0, msg.arg2);
                                                                Trace.endSection();
                                                                break;
                                                            case 328:
                                                                KeyguardUpdateMonitor.this.handleSimSubscriptionInfoChanged();
                                                                break;
                                                            case 329:
                                                                KeyguardUpdateMonitor.this.handleAirplaneModeChanged();
                                                                break;
                                                            case 330:
                                                                KeyguardUpdateMonitor.this.handleServiceStateChange(msg.arg1, (ServiceState) msg.obj);
                                                                break;
                                                            case 331:
                                                                KeyguardUpdateMonitor.this.handleScreenTurnedOn();
                                                                break;
                                                            case 332:
                                                                Trace.beginSection("KeyguardUpdateMonitor#handler MSG_SCREEN_TURNED_ON");
                                                                KeyguardUpdateMonitor.this.handleScreenTurnedOff();
                                                                Trace.endSection();
                                                                break;
                                                            case 333:
                                                                KeyguardUpdateMonitor.this.handleDreamingStateChanged(msg.arg1);
                                                                break;
                                                            case 334:
                                                                KeyguardUpdateMonitor.this.handleUserUnlocked();
                                                                break;
                                                            case 335:
                                                                KeyguardUpdateMonitor.this.mAssistantVisible = ((Boolean) msg.obj).booleanValue();
                                                                KeyguardUpdateMonitor.this.updateFingerprintListeningState();
                                                                break;
                                                            case 336:
                                                                KeyguardUpdateMonitor.this.updateFingerprintListeningState();
                                                                break;
                                                            case 337:
                                                                KeyguardUpdateMonitor.this.updateLogoutEnabled();
                                                                break;
                                                            default:
                                                                super.handleMessage(msg);
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                        }
                        break;
                }
            }
            KeyguardUpdateMonitor.this.handleAirPlaneModeUpdate(((Boolean) msg.obj).booleanValue());
        }
    };
    private SubscriptionManager.OnSubscriptionsChangedListener mSubscriptionListener = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.keyguard.KeyguardUpdateMonitor.2
        @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
        public void onSubscriptionsChanged() {
            KeyguardUpdateMonitor.this.mHandler.removeMessages(328);
            KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(328);
        }
    };
    private SparseBooleanArray mUserHasTrust = new SparseBooleanArray();
    private SparseBooleanArray mUserTrustIsManaged = new SparseBooleanArray();
    private SparseBooleanArray mUserFingerprintAuthenticated = new SparseBooleanArray();
    private SparseBooleanArray mUserFaceUnlockRunning = new SparseBooleanArray();
    private Runnable mUpdateFingerprintListeningState = new Runnable() { // from class: com.android.keyguard.-$$Lambda$KeyguardUpdateMonitor$3psLIyI5yDs2IBHa8rcm6IwKZfM
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.updateFingerprintListeningState();
        }
    };
    private Runnable mRetryFingerprintAuthentication = new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor.3
        @Override // java.lang.Runnable
        public void run() {
            Log.w("KeyguardUpdateMonitor", "Retrying fingerprint after HW unavailable, attempt " + KeyguardUpdateMonitor.this.mHardwareUnavailableRetryCount);
            KeyguardUpdateMonitor.this.updateFingerprintListeningState();
        }
    };
    private DisplayClientState mDisplayClientState = new DisplayClientState();

    @VisibleForTesting
    protected final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.keyguard.KeyguardUpdateMonitor.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (KeyguardUpdateMonitor.DEBUG) {
                Log.d("KeyguardUpdateMonitor", "received broadcast " + action);
            }
            if ("android.intent.action.TIME_SET".equals(action) || "android.intent.action.TIMEZONE_CHANGED".equals(action)) {
                KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(301);
                return;
            }
            if ("android.provider.Telephony.SPN_STRINGS_UPDATED".equals(action)) {
                int subId = intent.getIntExtra("subscription", -1);
                Log.d("KeyguardUpdateMonitor", "SPN_STRINGS_UPDATED_ACTION, sub Id = " + subId);
                int phoneId = KeyguardUtils.getPhoneIdUsingSubId(subId);
                if (KeyguardUtils.isValidPhoneId(phoneId)) {
                    KeyguardUpdateMonitor.this.mTelephonyPlmn.put(Integer.valueOf(phoneId), KeyguardUpdateMonitor.this.getTelephonyPlmnFrom(intent));
                    KeyguardUpdateMonitor.this.mTelephonySpn.put(Integer.valueOf(phoneId), KeyguardUpdateMonitor.this.getTelephonySpnFrom(intent));
                    KeyguardUpdateMonitor.this.mTelephonyCsgId.put(Integer.valueOf(phoneId), KeyguardUpdateMonitor.this.getTelephonyCsgIdFrom(intent));
                    KeyguardUpdateMonitor.this.mTelephonyHnbName.put(Integer.valueOf(phoneId), KeyguardUpdateMonitor.this.getTelephonyHnbNameFrom(intent));
                    if (KeyguardUpdateMonitor.DEBUG) {
                        Log.d("KeyguardUpdateMonitor", "SPN_STRINGS_UPDATED_ACTION, update phoneId=" + phoneId + ", plmn=" + KeyguardUpdateMonitor.this.mTelephonyPlmn.get(Integer.valueOf(phoneId)) + ", spn=" + KeyguardUpdateMonitor.this.mTelephonySpn.get(Integer.valueOf(phoneId)) + ", csgId=" + KeyguardUpdateMonitor.this.mTelephonyCsgId.get(Integer.valueOf(phoneId)) + ", hnbName=" + KeyguardUpdateMonitor.this.mTelephonyHnbName.get(Integer.valueOf(phoneId)));
                    }
                    KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(303, Integer.valueOf(phoneId)));
                    return;
                }
                Log.d("KeyguardUpdateMonitor", "SPN_STRINGS_UPDATED_ACTION, invalid phoneId = " + phoneId);
                return;
            }
            if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
                int status = intent.getIntExtra("status", 1);
                int plugged = intent.getIntExtra("plugged", 0);
                int level = intent.getIntExtra("level", 0);
                int health = intent.getIntExtra("health", 1);
                int maxChargingMicroAmp = intent.getIntExtra("max_charging_current", -1);
                int maxChargingMicroVolt = intent.getIntExtra("max_charging_voltage", -1);
                if (maxChargingMicroVolt <= 0) {
                    maxChargingMicroVolt = 5000000;
                }
                int maxChargingMicroWatt = maxChargingMicroAmp > 0 ? (maxChargingMicroAmp / 1000) * (maxChargingMicroVolt / 1000) : -1;
                KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(302, new BatteryStatus(status, level, plugged, health, maxChargingMicroWatt)));
                return;
            }
            if ("android.intent.action.SIM_STATE_CHANGED".equals(action) || "com.mediatek.phone.ACTION_UNLOCK_SIM_LOCK".equals(action)) {
                if (!intent.getBooleanExtra("rebroadcastOnUnlock", false)) {
                    String stateExtra = intent.getStringExtra("ss");
                    SimData simArgs = SimData.fromIntent(intent);
                    if (KeyguardUpdateMonitor.DEBUG_SIM_STATES) {
                        Log.v("KeyguardUpdateMonitor", "action=" + action + ", state=" + stateExtra + ", slotId=" + simArgs.phoneId + ", subId=" + simArgs.subId + ", simArgs.simState = " + simArgs.simState);
                    }
                    if ("com.mediatek.phone.ACTION_UNLOCK_SIM_LOCK".equals(action)) {
                        Log.d("KeyguardUpdateMonitor", "ACTION_UNLOCK_SIM_LOCK, set sim state as UNKNOWN");
                        KeyguardUpdateMonitor.this.mSimStateOfPhoneId.put(Integer.valueOf(simArgs.phoneId), IccCardConstants.State.UNKNOWN);
                    }
                    KeyguardUpdateMonitor.this.proceedToHandleSimStateChanged(simArgs);
                    return;
                }
                return;
            }
            if ("android.media.RINGER_MODE_CHANGED".equals(action)) {
                KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(305, intent.getIntExtra("android.media.EXTRA_RINGER_MODE", -1), 0));
                return;
            }
            if ("android.intent.action.PHONE_STATE".equals(action)) {
                KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(306, intent.getStringExtra("state")));
                return;
            }
            if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                KeyguardUpdateMonitor.this.dispatchBootCompleted();
                return;
            }
            if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                boolean state = intent.getBooleanExtra("state", false);
                Log.d("KeyguardUpdateMonitor", "Receive ACTION_AIRPLANE_MODE_CHANGED, state = " + state);
                Message msg = new Message();
                msg.what = 1015;
                msg.obj = new Boolean(state);
                KeyguardUpdateMonitor.this.mHandler.sendMessage(msg);
                return;
            }
            if (!"android.intent.action.SERVICE_STATE".equals(action)) {
                if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(337);
                    return;
                }
                return;
            }
            ServiceState serviceState = ServiceState.newFromBundle(intent.getExtras());
            int subId2 = intent.getIntExtra("subscription", -1);
            if (KeyguardUpdateMonitor.DEBUG) {
                Log.v("KeyguardUpdateMonitor", "action " + action + " serviceState=" + serviceState + " subId=" + subId2);
            }
            KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(330, subId2, 0, serviceState));
        }
    };
    private final BroadcastReceiver mBroadcastAllReceiver = new BroadcastReceiver() { // from class: com.android.keyguard.KeyguardUpdateMonitor.5
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.app.action.NEXT_ALARM_CLOCK_CHANGED".equals(action)) {
                KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(301);
                return;
            }
            if ("android.intent.action.USER_INFO_CHANGED".equals(action)) {
                KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(317, intent.getIntExtra("android.intent.extra.user_handle", getSendingUserId()), 0));
                return;
            }
            if ("com.android.facelock.FACE_UNLOCK_STARTED".equals(action)) {
                Trace.beginSection("KeyguardUpdateMonitor.mBroadcastAllReceiver#onReceive ACTION_FACE_UNLOCK_STARTED");
                KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(327, 1, getSendingUserId()));
                Trace.endSection();
            } else if ("com.android.facelock.FACE_UNLOCK_STOPPED".equals(action)) {
                KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(327, 0, getSendingUserId()));
            } else if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(action)) {
                KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(309);
            } else if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(334);
            }
        }
    };
    private final FingerprintManager.LockoutResetCallback mLockoutResetCallback = new FingerprintManager.LockoutResetCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.6
        public void onLockoutReset() {
            KeyguardUpdateMonitor.this.handleFingerprintLockoutReset();
        }
    };
    private FingerprintManager.AuthenticationCallback mAuthenticationCallback = new FingerprintManager.AuthenticationCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.7
        @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
        public void onAuthenticationFailed() {
            KeyguardUpdateMonitor.this.handleFingerprintAuthFailed();
        }

        @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            Trace.beginSection("KeyguardUpdateMonitor#onAuthenticationSucceeded");
            KeyguardUpdateMonitor.this.handleFingerprintAuthenticated(result.getUserId());
            Trace.endSection();
        }

        @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            KeyguardUpdateMonitor.this.handleFingerprintHelp(helpMsgId, helpString.toString());
        }

        @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            KeyguardUpdateMonitor.this.handleFingerprintError(errMsgId, errString.toString());
        }

        public void onAuthenticationAcquired(int acquireInfo) {
            KeyguardUpdateMonitor.this.handleFingerprintAcquired(acquireInfo);
        }
    };
    private final SysUiTaskStackChangeListener mTaskStackListener = new SysUiTaskStackChangeListener() { // from class: com.android.keyguard.KeyguardUpdateMonitor.10
        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskStackChangedBackground() {
            try {
                ActivityManager.StackInfo info = ActivityManager.getService().getStackInfo(0, 4);
                if (info == null) {
                    return;
                }
                KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(335, Boolean.valueOf(info.visible)));
            } catch (RemoteException e) {
                Log.e("KeyguardUpdateMonitor", "unable to check task stack", e);
            }
        }
    };
    private HashMap<Integer, Integer> mSimMeCategory = new HashMap<>();
    private HashMap<Integer, Integer> mSimMeLeftRetryCount = new HashMap<>();
    private int mPinPukMeDismissFlag = 0;
    private int mSimmeDismissFlag = 0;
    private HashMap<Integer, IccCardConstants.State> mSimStateOfPhoneId = new HashMap<>();
    private HashMap<Integer, CharSequence> mTelephonyPlmn = new HashMap<>();
    private HashMap<Integer, CharSequence> mTelephonySpn = new HashMap<>();
    private HashMap<Integer, CharSequence> mTelephonyHnbName = new HashMap<>();
    private HashMap<Integer, CharSequence> mTelephonyCsgId = new HashMap<>();
    private boolean mDeviceProvisioned = isDeviceProvisionedInSettingsDb();

    static {
        try {
            CORE_APPS_ONLY = IPackageManager.Stub.asInterface(ServiceManager.getService("package")).isOnlyCoreApps();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static synchronized void setCurrentUser(int currentUser) {
        sCurrentUser = currentUser;
    }

    public static synchronized int getCurrentUser() {
        return sCurrentUser;
    }

    public void onTrustChanged(boolean enabled, int userId, int flags) {
        checkIsHandlerThread();
        this.mUserHasTrust.put(userId, enabled);
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onTrustChanged(userId);
                if (enabled && flags != 0) {
                    cb.onTrustGrantedWithFlags(flags, userId);
                }
            }
        }
    }

    public void onTrustError(CharSequence message) {
        dispatchErrorMessage(message);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSimSubscriptionInfoChanged() {
        if (DEBUG_SIM_STATES) {
            Log.v("KeyguardUpdateMonitor", "onSubscriptionInfoChanged()");
            List<SubscriptionInfo> sil = this.mSubscriptionManager.getActiveSubscriptionInfoList();
            if (sil != null) {
                for (SubscriptionInfo subInfo : sil) {
                    Log.v("KeyguardUpdateMonitor", "SubInfo:" + subInfo);
                }
            } else {
                Log.v("KeyguardUpdateMonitor", "onSubscriptionInfoChanged: list is null");
            }
        }
        List<SubscriptionInfo> subscriptionInfos = getSubscriptionInfo(true);
        ArrayList<SubscriptionInfo> changedSubscriptions = new ArrayList<>();
        for (int i = 0; i < subscriptionInfos.size(); i++) {
            SubscriptionInfo info = subscriptionInfos.get(i);
            boolean changed = refreshSimState(info.getSubscriptionId(), info.getSimSlotIndex());
            if (changed) {
                changedSubscriptions.add(info);
            }
        }
        for (int i2 = 0; i2 < changedSubscriptions.size(); i2++) {
            int subId = changedSubscriptions.get(i2).getSubscriptionId();
            int phoneId = changedSubscriptions.get(i2).getSimSlotIndex();
            Log.d("KeyguardUpdateMonitor", "handleSimSubscriptionInfoChanged() - call callbacks for subId = " + subId + " & phoneId = " + phoneId);
            for (int j = 0; j < this.mCallbacks.size(); j++) {
                KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(j).get();
                if (cb != null) {
                    cb.onSimStateChangedUsingPhoneId(phoneId, this.mSimStateOfPhoneId.get(Integer.valueOf(phoneId)));
                }
            }
        }
        for (int j2 = 0; j2 < this.mCallbacks.size(); j2++) {
            KeyguardUpdateMonitorCallback cb2 = this.mCallbacks.get(j2).get();
            if (cb2 != null) {
                cb2.onRefreshCarrierInfo();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAirplaneModeChanged() {
        for (int j = 0; j < this.mCallbacks.size(); j++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(j).get();
            if (cb != null) {
                cb.onRefreshCarrierInfo();
            }
        }
    }

    public List<SubscriptionInfo> getSubscriptionInfo(boolean forceReload) {
        List<SubscriptionInfo> sil = this.mSubscriptionInfo;
        if (sil == null || forceReload || (sil != null && sil.size() == 0)) {
            sil = this.mSubscriptionManager.getActiveSubscriptionInfoList();
        }
        if (sil == null) {
            this.mSubscriptionInfo = new ArrayList();
        } else {
            this.mSubscriptionInfo = sil;
        }
        return this.mSubscriptionInfo;
    }

    public void onTrustManagedChanged(boolean managed, int userId) {
        checkIsHandlerThread();
        this.mUserTrustIsManaged.put(userId, managed);
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onTrustManagedChanged(userId);
            }
        }
    }

    public void setKeyguardGoingAway(boolean goingAway) {
        this.mKeyguardGoingAway = goingAway;
        updateFingerprintListeningState();
    }

    public void setKeyguardOccluded(boolean occluded) {
        this.mKeyguardOccluded = occluded;
        updateFingerprintListeningState();
    }

    public boolean isDreaming() {
        return this.mIsDreaming;
    }

    public void awakenFromDream() {
        if (this.mIsDreaming && this.mDreamManager != null) {
            try {
                this.mDreamManager.awaken();
            } catch (RemoteException e) {
                Log.e("KeyguardUpdateMonitor", "Unable to awaken from dream");
            }
        }
    }

    private void onFingerprintAuthenticated(int userId) {
        Trace.beginSection("KeyGuardUpdateMonitor#onFingerPrintAuthenticated");
        this.mUserFingerprintAuthenticated.put(userId, true);
        if (getUserCanSkipBouncer(userId)) {
            this.mTrustManager.unlockedByFingerprintForUser(userId);
        }
        this.mFingerprintCancelSignal = null;
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onFingerprintAuthenticated(userId);
            }
        }
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(336), 500L);
        this.mAssistantVisible = false;
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFingerprintAuthFailed() {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onFingerprintAuthFailed();
            }
        }
        handleFingerprintHelp(-1, this.mContext.getString(com.android.systemui.R.string.fingerprint_not_recognized));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFingerprintAcquired(int acquireInfo) {
        if (acquireInfo != 0) {
            return;
        }
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onFingerprintAcquired();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFingerprintAuthenticated(int authUserId) {
        Trace.beginSection("KeyGuardUpdateMonitor#handlerFingerPrintAuthenticated");
        try {
            try {
                int userId = ActivityManager.getService().getCurrentUser().id;
                if (userId != authUserId) {
                    Log.d("KeyguardUpdateMonitor", "Fingerprint authenticated for wrong user: " + authUserId);
                    setFingerprintRunningState(0);
                    return;
                }
                if (!isFingerprintDisabled(userId)) {
                    onFingerprintAuthenticated(userId);
                    setFingerprintRunningState(0);
                    Trace.endSection();
                } else {
                    Log.d("KeyguardUpdateMonitor", "Fingerprint disabled by DPM for userId: " + userId);
                    setFingerprintRunningState(0);
                }
            } catch (RemoteException e) {
                Log.e("KeyguardUpdateMonitor", "Failed to get current user id: ", e);
                setFingerprintRunningState(0);
            }
        } catch (Throwable th) {
            setFingerprintRunningState(0);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFingerprintHelp(int msgId, String helpString) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onFingerprintHelp(msgId, helpString);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFingerprintError(int msgId, String errString) {
        int i = 0;
        if (msgId == 5 && this.mFingerprintRunningState == 3) {
            setFingerprintRunningState(0);
            startListeningForFingerprint();
        } else {
            setFingerprintRunningState(0);
        }
        if (msgId == 1 && this.mHardwareUnavailableRetryCount < 3) {
            this.mHardwareUnavailableRetryCount++;
            this.mHandler.removeCallbacks(this.mRetryFingerprintAuthentication);
            this.mHandler.postDelayed(this.mRetryFingerprintAuthentication, 3000L);
        }
        if (msgId == 9) {
            this.mLockPatternUtils.requireStrongAuth(8, getCurrentUser());
        }
        while (true) {
            int i2 = i;
            if (i2 < this.mCallbacks.size()) {
                KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i2).get();
                if (cb != null) {
                    cb.onFingerprintError(msgId, errString);
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFingerprintLockoutReset() {
        updateFingerprintListeningState();
    }

    private void setFingerprintRunningState(int fingerprintRunningState) {
        boolean wasRunning = this.mFingerprintRunningState == 1;
        boolean isRunning = fingerprintRunningState == 1;
        this.mFingerprintRunningState = fingerprintRunningState;
        if (wasRunning != isRunning) {
            notifyFingerprintRunningStateChanged();
        }
    }

    private void notifyFingerprintRunningStateChanged() {
        checkIsHandlerThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onFingerprintRunningStateChanged(isFingerprintDetectionRunning());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFaceUnlockStateChanged(boolean running, int userId) {
        this.mUserFaceUnlockRunning.put(userId, running);
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onFaceUnlockStateChanged(running, userId);
            }
        }
    }

    public boolean isFaceUnlockRunning(int userId) {
        return this.mUserFaceUnlockRunning.get(userId);
    }

    public boolean isFingerprintDetectionRunning() {
        return this.mFingerprintRunningState == 1;
    }

    private boolean isTrustDisabled(int userId) {
        boolean disabledBySimPin = isSimPinSecure();
        return disabledBySimPin;
    }

    private boolean isFingerprintDisabled(int userId) {
        DevicePolicyManager dpm = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        return !(dpm == null || (dpm.getKeyguardDisabledFeatures(null, userId) & 32) == 0) || isSimPinSecure();
    }

    public boolean getUserCanSkipBouncer(int userId) {
        return getUserHasTrust(userId) || (this.mUserFingerprintAuthenticated.get(userId) && isUnlockingWithFingerprintAllowed());
    }

    public boolean getUserHasTrust(int userId) {
        return !isTrustDisabled(userId) && this.mUserHasTrust.get(userId);
    }

    public boolean getUserTrustIsManaged(int userId) {
        return this.mUserTrustIsManaged.get(userId) && !isTrustDisabled(userId);
    }

    public boolean isUnlockingWithFingerprintAllowed() {
        return this.mStrongAuthTracker.isUnlockingWithFingerprintAllowed();
    }

    public boolean isUserInLockdown(int userId) {
        return this.mStrongAuthTracker.getStrongAuthForUser(userId) == 32;
    }

    public boolean needsSlowUnlockTransition() {
        return this.mNeedsSlowUnlockTransition;
    }

    public StrongAuthTracker getStrongAuthTracker() {
        return this.mStrongAuthTracker;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyStrongAuthStateChanged(int userId) {
        checkIsHandlerThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onStrongAuthStateChanged(userId);
            }
        }
    }

    public boolean isScreenOn() {
        return this.mScreenOn;
    }

    private void dispatchErrorMessage(CharSequence message) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onTrustAgentErrorMessage(message);
            }
        }
    }

    static class DisplayClientState {
        DisplayClientState() {
        }
    }

    private static class SimData {
        public int phoneId;
        public int simMECategory;
        public final IccCardConstants.State simState;
        public int subId;

        SimData(IccCardConstants.State state, int phoneId, int subId) {
            this.phoneId = 0;
            this.simMECategory = 0;
            this.simState = state;
            this.phoneId = phoneId;
            this.subId = subId;
        }

        @VisibleForTesting
        SimData(IccCardConstants.State state, int phoneId, int subId, int meCategory) {
            this.phoneId = 0;
            this.simMECategory = 0;
            this.simState = state;
            this.phoneId = phoneId;
            this.subId = subId;
            this.simMECategory = meCategory;
        }

        static SimData fromIntent(Intent intent) {
            IccCardConstants.State state;
            IccCardConstants.State state2;
            IccCardConstants.State state3;
            if (!"android.intent.action.SIM_STATE_CHANGED".equals(intent.getAction()) && !"com.mediatek.phone.ACTION_UNLOCK_SIM_LOCK".equals(intent.getAction())) {
                throw new IllegalArgumentException("only handles intent ACTION_SIM_STATE_CHANGED");
            }
            int meCategory = 0;
            String stateExtra = intent.getStringExtra("ss");
            int phoneId = intent.getIntExtra("slot", 0);
            int subId = intent.getIntExtra("subscription", -1);
            if ("ABSENT".equals(stateExtra)) {
                String absentReason = intent.getStringExtra("reason");
                if ("PERM_DISABLED".equals(absentReason)) {
                    state3 = IccCardConstants.State.PERM_DISABLED;
                } else {
                    state3 = IccCardConstants.State.ABSENT;
                }
                state = state3;
            } else if ("READY".equals(stateExtra)) {
                state = IccCardConstants.State.READY;
            } else if ("LOCKED".equals(stateExtra)) {
                String lockedReason = intent.getStringExtra("reason");
                Log.d("KeyguardUpdateMonitor", "INTENT_VALUE_ICC_LOCKED, lockedReason=" + lockedReason);
                if ("PIN".equals(lockedReason)) {
                    state2 = IccCardConstants.State.PIN_REQUIRED;
                } else if ("PUK".equals(lockedReason)) {
                    state2 = IccCardConstants.State.PUK_REQUIRED;
                } else if ("NETWORK".equals(lockedReason)) {
                    meCategory = 0;
                    state2 = IccCardConstants.State.NETWORK_LOCKED;
                } else if ("NETWORK_SUBSET".equals(lockedReason)) {
                    meCategory = 1;
                    state2 = IccCardConstants.State.NETWORK_LOCKED;
                } else if ("SERVICE_PROVIDER".equals(lockedReason)) {
                    meCategory = 2;
                    state2 = IccCardConstants.State.NETWORK_LOCKED;
                } else if ("CORPORATE".equals(lockedReason)) {
                    meCategory = 3;
                    state2 = IccCardConstants.State.NETWORK_LOCKED;
                } else if ("SIM".equals(lockedReason)) {
                    meCategory = 4;
                    state2 = IccCardConstants.State.NETWORK_LOCKED;
                } else {
                    state2 = IccCardConstants.State.UNKNOWN;
                }
                state = state2;
            } else if ("NETWORK".equals(stateExtra)) {
                state = IccCardConstants.State.NETWORK_LOCKED;
            } else if ("CARD_IO_ERROR".equals(stateExtra)) {
                state = IccCardConstants.State.CARD_IO_ERROR;
            } else if ("LOADED".equals(stateExtra) || "IMSI".equals(stateExtra)) {
                state = IccCardConstants.State.READY;
            } else if ("NOT_READY".equals(stateExtra)) {
                state = IccCardConstants.State.NOT_READY;
            } else {
                state = IccCardConstants.State.UNKNOWN;
            }
            return new SimData(state, phoneId, subId, meCategory);
        }

        public String toString() {
            return this.simState.toString();
        }
    }

    public static class BatteryStatus {
        public final int health;
        public final int level;
        public final int maxChargingWattage;
        public final int plugged;
        public final int status;

        public BatteryStatus(int status, int level, int plugged, int health, int maxChargingWattage) {
            this.status = status;
            this.level = level;
            this.plugged = plugged;
            this.health = health;
            this.maxChargingWattage = maxChargingWattage;
        }

        public boolean isPluggedIn() {
            return this.plugged == 1 || this.plugged == 2 || this.plugged == 4;
        }

        public boolean isPluggedInWired() {
            return this.plugged == 1 || this.plugged == 2;
        }

        public boolean isCharged() {
            return this.status == 5 || this.level >= 100;
        }

        public final int getChargingSpeed(int slowThreshold, int fastThreshold) {
            if (this.maxChargingWattage <= 0) {
                return -1;
            }
            if (this.maxChargingWattage < slowThreshold) {
                return 0;
            }
            return this.maxChargingWattage > fastThreshold ? 2 : 1;
        }

        public String toString() {
            return "BatteryStatus{status=" + this.status + ",level=" + this.level + ",plugged=" + this.plugged + ",health=" + this.health + ",maxChargingWattage=" + this.maxChargingWattage + "}";
        }
    }

    public class StrongAuthTracker extends LockPatternUtils.StrongAuthTracker {
        public StrongAuthTracker(Context context) {
            super(context);
        }

        public boolean isUnlockingWithFingerprintAllowed() {
            int userId = KeyguardUpdateMonitor.getCurrentUser();
            return isFingerprintAllowedForUser(userId);
        }

        public boolean hasUserAuthenticatedSinceBoot() {
            int userId = KeyguardUpdateMonitor.getCurrentUser();
            return (getStrongAuthForUser(userId) & 1) == 0;
        }

        public void onStrongAuthRequiredChanged(int userId) {
            KeyguardUpdateMonitor.this.notifyStrongAuthStateChanged(userId);
        }
    }

    public static KeyguardUpdateMonitor getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new KeyguardUpdateMonitor(context);
        }
        return sInstance;
    }

    protected void handleStartedWakingUp() {
        Trace.beginSection("KeyguardUpdateMonitor#handleStartedWakingUp");
        updateFingerprintListeningState();
        int count = this.mCallbacks.size();
        for (int i = 0; i < count; i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onStartedWakingUp();
            }
        }
        Trace.endSection();
    }

    protected void handleStartedGoingToSleep(int arg1) {
        clearFingerprintRecognized();
        int count = this.mCallbacks.size();
        for (int i = 0; i < count; i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onStartedGoingToSleep(arg1);
            }
        }
        this.mGoingToSleep = true;
        updateFingerprintListeningState();
    }

    protected void handleFinishedGoingToSleep(int arg1) {
        this.mGoingToSleep = false;
        int count = this.mCallbacks.size();
        for (int i = 0; i < count; i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onFinishedGoingToSleep(arg1);
            }
        }
        updateFingerprintListeningState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScreenTurnedOn() {
        int count = this.mCallbacks.size();
        for (int i = 0; i < count; i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onScreenTurnedOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScreenTurnedOff() {
        this.mHardwareUnavailableRetryCount = 0;
        int count = this.mCallbacks.size();
        for (int i = 0; i < count; i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onScreenTurnedOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDreamingStateChanged(int dreamStart) {
        int count = this.mCallbacks.size();
        this.mIsDreaming = dreamStart == 1;
        for (int i = 0; i < count; i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onDreamingStateChanged(this.mIsDreaming);
            }
        }
        updateFingerprintListeningState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUserInfoChanged(int userId) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onUserInfoChanged(userId);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUserUnlocked() {
        this.mNeedsSlowUnlockTransition = resolveNeedsSlowUnlockTransition();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onUserUnlocked();
            }
        }
    }

    @VisibleForTesting
    protected KeyguardUpdateMonitor(Context context) {
        this.mContext = context;
        this.mSubscriptionManager = SubscriptionManager.from(context);
        this.mStrongAuthTracker = new StrongAuthTracker(context);
        if (DEBUG) {
            Log.d("KeyguardUpdateMonitor", "mDeviceProvisioned is:" + this.mDeviceProvisioned);
        }
        if (!this.mDeviceProvisioned) {
            watchForDeviceProvisioning();
        }
        this.mBatteryStatus = new BatteryStatus(1, 100, 0, 0, 0);
        this.mWifiManager = (WifiManager) context.getSystemService("wifi");
        initMembers();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.TIME_SET");
        filter.addAction("android.intent.action.BATTERY_CHANGED");
        filter.addAction("android.intent.action.TIMEZONE_CHANGED");
        filter.addAction("android.intent.action.AIRPLANE_MODE");
        filter.addAction("android.intent.action.SIM_STATE_CHANGED");
        filter.addAction("android.intent.action.SERVICE_STATE");
        filter.addAction("android.intent.action.PHONE_STATE");
        filter.addAction("android.media.RINGER_MODE_CHANGED");
        filter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        filter.addAction("com.mediatek.phone.ACTION_UNLOCK_SIM_LOCK");
        context.registerReceiver(this.mBroadcastReceiver, filter, null, this.mHandler);
        IntentFilter bootCompleteFilter = new IntentFilter();
        bootCompleteFilter.setPriority(1000);
        bootCompleteFilter.addAction("android.intent.action.BOOT_COMPLETED");
        context.registerReceiver(this.mBroadcastReceiver, bootCompleteFilter, null, this.mHandler);
        IntentFilter allUserFilter = new IntentFilter();
        allUserFilter.addAction("android.intent.action.USER_INFO_CHANGED");
        allUserFilter.addAction("android.app.action.NEXT_ALARM_CLOCK_CHANGED");
        allUserFilter.addAction("com.android.facelock.FACE_UNLOCK_STARTED");
        allUserFilter.addAction("com.android.facelock.FACE_UNLOCK_STOPPED");
        allUserFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        allUserFilter.addAction("android.intent.action.USER_UNLOCKED");
        context.registerReceiverAsUser(this.mBroadcastAllReceiver, UserHandle.ALL, allUserFilter, null, this.mHandler);
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(this.mSubscriptionListener);
        try {
            ActivityManager.getService().registerUserSwitchObserver(new UserSwitchObserver() { // from class: com.android.keyguard.KeyguardUpdateMonitor.8
                public void onUserSwitching(int newUserId, IRemoteCallback reply) {
                    KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(310, newUserId, 0, reply));
                }

                public void onUserSwitchComplete(int newUserId) throws RemoteException {
                    KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(314, newUserId, 0));
                }
            }, "KeyguardUpdateMonitor");
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
        this.mTrustManager = (TrustManager) context.getSystemService("trust");
        this.mTrustManager.registerTrustListener(this);
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mLockPatternUtils.registerStrongAuthTracker(this.mStrongAuthTracker);
        this.mDreamManager = IDreamManager.Stub.asInterface(ServiceManager.getService("dreams"));
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
            this.mFpm = (FingerprintManager) context.getSystemService("fingerprint");
        }
        updateFingerprintListeningState();
        if (this.mFpm != null) {
            this.mFpm.addLockoutResetCallback(this.mLockoutResetCallback);
        }
        ActivityManagerWrapper.getInstance().registerTaskStackListener(this.mTaskStackListener);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        this.mLogoutEnabled = this.mDevicePolicyManager.isLogoutEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFingerprintListeningState() {
        if (this.mHandler.hasMessages(336)) {
            return;
        }
        this.mHandler.removeCallbacks(this.mRetryFingerprintAuthentication);
        boolean shouldListenForFingerprint = shouldListenForFingerprint();
        if (this.mFingerprintRunningState == 1 && !shouldListenForFingerprint) {
            stopListeningForFingerprint();
        } else if (this.mFingerprintRunningState != 1 && shouldListenForFingerprint) {
            startListeningForFingerprint();
        }
    }

    private boolean shouldListenForFingerprintAssistant() {
        return this.mAssistantVisible && this.mKeyguardOccluded && !this.mUserFingerprintAuthenticated.get(getCurrentUser(), false) && !this.mUserHasTrust.get(getCurrentUser(), false);
    }

    private boolean shouldListenForFingerprint() {
        return ((!this.mKeyguardIsVisible && this.mDeviceInteractive && ((!this.mBouncer || this.mKeyguardGoingAway) && !this.mGoingToSleep && !shouldListenForFingerprintAssistant() && (!this.mKeyguardOccluded || !this.mIsDreaming))) || this.mSwitchingUser || isFingerprintDisabled(getCurrentUser()) || this.mKeyguardGoingAway) ? false : true;
    }

    private void startListeningForFingerprint() {
        if (this.mFingerprintRunningState == 2) {
            setFingerprintRunningState(3);
            return;
        }
        if (DEBUG) {
            Log.v("KeyguardUpdateMonitor", "startListeningForFingerprint()");
        }
        int userId = ActivityManager.getCurrentUser();
        if (isUnlockWithFingerprintPossible(userId)) {
            if (this.mFingerprintCancelSignal != null) {
                this.mFingerprintCancelSignal.cancel();
            }
            this.mFingerprintCancelSignal = new CancellationSignal();
            this.mFpm.authenticate(null, this.mFingerprintCancelSignal, 0, this.mAuthenticationCallback, null, userId);
            setFingerprintRunningState(1);
        }
    }

    public boolean isUnlockWithFingerprintPossible(int userId) {
        return this.mFpm != null && this.mFpm.isHardwareDetected() && !isFingerprintDisabled(userId) && this.mFpm.getEnrolledFingerprints(userId).size() > 0;
    }

    private void stopListeningForFingerprint() {
        if (DEBUG) {
            Log.v("KeyguardUpdateMonitor", "stopListeningForFingerprint()");
        }
        if (this.mFingerprintRunningState == 1) {
            if (this.mFingerprintCancelSignal != null) {
                this.mFingerprintCancelSignal.cancel();
                this.mFingerprintCancelSignal = null;
            }
            setFingerprintRunningState(2);
        }
        if (this.mFingerprintRunningState == 3) {
            setFingerprintRunningState(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDeviceProvisionedInSettingsDb() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0;
    }

    private void watchForDeviceProvisioning() {
        this.mDeviceProvisionedObserver = new ContentObserver(this.mHandler) { // from class: com.android.keyguard.KeyguardUpdateMonitor.9
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                KeyguardUpdateMonitor.this.mDeviceProvisioned = KeyguardUpdateMonitor.this.isDeviceProvisionedInSettingsDb();
                if (KeyguardUpdateMonitor.this.mDeviceProvisioned) {
                    KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(308);
                }
                if (KeyguardUpdateMonitor.DEBUG) {
                    Log.d("KeyguardUpdateMonitor", "DEVICE_PROVISIONED state = " + KeyguardUpdateMonitor.this.mDeviceProvisioned);
                }
            }
        };
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, this.mDeviceProvisionedObserver);
        boolean provisioned = isDeviceProvisionedInSettingsDb();
        if (provisioned != this.mDeviceProvisioned) {
            this.mDeviceProvisioned = provisioned;
            if (this.mDeviceProvisioned) {
                this.mHandler.sendEmptyMessage(308);
            }
        }
    }

    public void setHasLockscreenWallpaper(boolean hasLockscreenWallpaper) {
        checkIsHandlerThread();
        if (hasLockscreenWallpaper != this.mHasLockscreenWallpaper) {
            this.mHasLockscreenWallpaper = hasLockscreenWallpaper;
            for (int i = this.mCallbacks.size() - 1; i >= 0; i--) {
                KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
                if (cb != null) {
                    cb.onHasLockscreenWallpaperChanged(hasLockscreenWallpaper);
                }
            }
        }
    }

    public boolean hasLockscreenWallpaper() {
        return this.mHasLockscreenWallpaper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDevicePolicyManagerStateChanged() {
        updateFingerprintListeningState();
        for (int i = this.mCallbacks.size() - 1; i >= 0; i--) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onDevicePolicyManagerStateChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUserSwitching(int userId, IRemoteCallback reply) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onUserSwitching(userId);
            }
        }
        try {
            reply.sendResult((Bundle) null);
        } catch (RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUserSwitchComplete(int userId) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onUserSwitchComplete(userId);
            }
        }
    }

    public void dispatchBootCompleted() {
        this.mHandler.sendEmptyMessage(313);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBootCompleted() {
        if (this.mBootCompleted) {
            return;
        }
        this.mBootCompleted = true;
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onBootCompleted();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDeviceProvisioned() {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onDeviceProvisioned();
            }
        }
        if (this.mDeviceProvisionedObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mDeviceProvisionedObserver);
            this.mDeviceProvisionedObserver = null;
        }
    }

    protected void handlePhoneStateChanged() {
        if (DEBUG) {
            Log.d("KeyguardUpdateMonitor", "handlePhoneStateChanged");
        }
        this.mPhoneState = 0;
        for (int i = 0; i < KeyguardUtils.getNumOfPhone(); i++) {
            int subId = KeyguardUtils.getSubIdUsingPhoneId(i);
            int callState = TelephonyManager.getDefault().getCallState(subId);
            if (callState == 2) {
                this.mPhoneState = callState;
            } else if (callState == 1 && this.mPhoneState == 0) {
                this.mPhoneState = callState;
            }
        }
        Log.d("KeyguardUpdateMonitor", "handlePhoneStateChanged() - mPhoneState = " + this.mPhoneState);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i2).get();
            if (cb != null) {
                cb.onPhoneStateChanged(this.mPhoneState);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRingerModeChange(int mode) {
        if (DEBUG) {
            Log.d("KeyguardUpdateMonitor", "handleRingerModeChange(" + mode + ")");
        }
        this.mRingMode = mode;
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onRingerModeChanged(mode);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTimeUpdate() {
        if (DEBUG) {
            Log.d("KeyguardUpdateMonitor", "handleTimeUpdate");
        }
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onTimeChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBatteryUpdate(BatteryStatus status) {
        if (DEBUG) {
            Log.d("KeyguardUpdateMonitor", "handleBatteryUpdate");
        }
        boolean batteryUpdateInteresting = isBatteryUpdateInteresting(this.mBatteryStatus, status);
        this.mBatteryStatus = status;
        if (batteryUpdateInteresting) {
            for (int i = 0; i < this.mCallbacks.size(); i++) {
                KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
                if (cb != null) {
                    cb.onRefreshBatteryInfo(status);
                }
            }
        }
    }

    @VisibleForTesting
    void handleSimStateChange(int subId, int slotId, IccCardConstants.State state) {
        checkIsHandlerThread();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSimStateChange(SimData simArgs) {
        handleSimStateChange(simArgs.subId, simArgs.phoneId, simArgs.simState);
        IccCardConstants.State state = simArgs.simState;
        if (DEBUG) {
            Log.d("KeyguardUpdateMonitor", "handleSimStateChange: intentValue = " + simArgs + " state resolved to " + state.toString() + " phoneId=" + simArgs.phoneId);
        }
        if (state != this.mSimStateOfPhoneId.get(Integer.valueOf(simArgs.phoneId))) {
            setSimmeDismissFlagOfPhoneId(simArgs.phoneId, false);
        }
        if (state != IccCardConstants.State.UNKNOWN) {
            if (state == IccCardConstants.State.NETWORK_LOCKED || state != this.mSimStateOfPhoneId.get(Integer.valueOf(simArgs.phoneId))) {
                this.mSimStateOfPhoneId.put(Integer.valueOf(simArgs.phoneId), state);
                int phoneId = simArgs.phoneId;
                printState();
                for (int i = 0; i < this.mCallbacks.size(); i++) {
                    KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
                    if (cb != null) {
                        cb.onSimStateChangedUsingPhoneId(phoneId, state);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleServiceStateChange(int subId, ServiceState serviceState) {
        if (DEBUG) {
            Log.d("KeyguardUpdateMonitor", "handleServiceStateChange(subId=" + subId + ", serviceState=" + serviceState);
        }
        this.mServiceStates.put(Integer.valueOf(subId), serviceState);
        for (int j = 0; j < this.mCallbacks.size(); j++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(j).get();
            if (cb != null) {
                cb.onRefreshCarrierInfo();
            }
        }
    }

    public void onKeyguardVisibilityChanged(boolean showing) {
        checkIsHandlerThread();
        if (DEBUG) {
            Log.d("KeyguardUpdateMonitor", "onKeyguardVisibilityChanged(" + showing + ")");
        }
        this.mKeyguardIsVisible = showing;
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onKeyguardVisibilityChangedRaw(showing);
            }
        }
        updateFingerprintListeningState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleKeyguardReset() {
        if (DEBUG) {
            Log.d("KeyguardUpdateMonitor", "handleKeyguardReset");
        }
        updateFingerprintListeningState();
        this.mNeedsSlowUnlockTransition = resolveNeedsSlowUnlockTransition();
    }

    private boolean resolveNeedsSlowUnlockTransition() {
        if (this.mUserManager.isUserUnlocked(getCurrentUser())) {
            return false;
        }
        Intent homeIntent = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME");
        ResolveInfo resolveInfo = this.mContext.getPackageManager().resolveActivity(homeIntent, 0);
        return FALLBACK_HOME_COMPONENT.equals(resolveInfo.getComponentInfo().getComponentName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleKeyguardBouncerChanged(int bouncer) {
        if (DEBUG) {
            Log.d("KeyguardUpdateMonitor", "handleKeyguardBouncerChanged(" + bouncer + ")");
        }
        boolean isBouncer = bouncer == 1;
        this.mBouncer = isBouncer;
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onKeyguardBouncerChanged(isBouncer);
            }
        }
        updateFingerprintListeningState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReportEmergencyCallAction() {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
            if (cb != null) {
                cb.onEmergencyCallAction();
            }
        }
    }

    private boolean isBatteryUpdateInteresting(BatteryStatus old, BatteryStatus current) {
        boolean nowPluggedIn = current.isPluggedIn();
        boolean wasPluggedIn = old.isPluggedIn();
        boolean stateChangedWhilePluggedIn = wasPluggedIn && nowPluggedIn && old.status != current.status;
        if (wasPluggedIn == nowPluggedIn && !stateChangedWhilePluggedIn && old.level == current.level) {
            return nowPluggedIn && current.maxChargingWattage != old.maxChargingWattage;
        }
        return true;
    }

    public void removeCallback(KeyguardUpdateMonitorCallback callback) {
        checkIsHandlerThread();
        if (DEBUG) {
            Log.v("KeyguardUpdateMonitor", "*** unregister callback for " + callback);
        }
        for (int i = this.mCallbacks.size() - 1; i >= 0; i--) {
            if (this.mCallbacks.get(i).get() == callback) {
                this.mCallbacks.remove(i);
            }
        }
    }

    public void registerCallback(KeyguardUpdateMonitorCallback callback) {
        checkIsHandlerThread();
        if (DEBUG) {
            Log.v("KeyguardUpdateMonitor", "*** register callback for " + callback);
        }
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            if (this.mCallbacks.get(i).get() == callback) {
                if (DEBUG) {
                    Log.e("KeyguardUpdateMonitor", "Object tried to add another callback", new Exception("Called by"));
                    return;
                }
                return;
            }
        }
        this.mCallbacks.add(new WeakReference<>(callback));
        removeCallback(null);
        sendUpdates(callback);
    }

    public boolean isSwitchingUser() {
        return this.mSwitchingUser;
    }

    public void setSwitchingUser(boolean switching) {
        this.mSwitchingUser = switching;
        this.mHandler.post(this.mUpdateFingerprintListeningState);
    }

    private void sendUpdates(KeyguardUpdateMonitorCallback callback) {
        callback.onRefreshBatteryInfo(this.mBatteryStatus);
        callback.onTimeChanged();
        callback.onRingerModeChanged(this.mRingMode);
        callback.onPhoneStateChanged(this.mPhoneState);
        callback.onRefreshCarrierInfo();
        callback.onClockVisibilityChanged();
        callback.onKeyguardVisibilityChangedRaw(this.mKeyguardIsVisible);
        for (int phoneId = 0; phoneId < KeyguardUtils.getNumOfPhone(); phoneId++) {
            callback.onSimStateChangedUsingPhoneId(phoneId, this.mSimStateOfPhoneId.get(Integer.valueOf(phoneId)));
        }
    }

    public void sendKeyguardReset() {
        this.mHandler.obtainMessage(312).sendToTarget();
    }

    public void sendKeyguardBouncerChanged(boolean z) {
        if (DEBUG) {
            Log.d("KeyguardUpdateMonitor", "sendKeyguardBouncerChanged(" + z + ")");
        }
        Message messageObtainMessage = this.mHandler.obtainMessage(322);
        messageObtainMessage.arg1 = z ? 1 : 0;
        messageObtainMessage.sendToTarget();
    }

    public void reportSimUnlocked(int phoneId) {
        int subId = KeyguardUtils.getSubIdUsingPhoneId(phoneId);
        handleSimStateChange(new SimData(IccCardConstants.State.READY, phoneId, subId));
    }

    public void reportEmergencyCallAction(boolean bypassHandler) {
        if (!bypassHandler) {
            this.mHandler.obtainMessage(318).sendToTarget();
        } else {
            checkIsHandlerThread();
            handleReportEmergencyCallAction();
        }
    }

    public boolean isDeviceProvisioned() {
        if (!this.mDeviceProvisioned) {
            Log.d("KeyguardUpdateMonitor", "isDeviceProvisioned get DEVICE_PROVISIONED from db again !!");
            return Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0;
        }
        return this.mDeviceProvisioned;
    }

    public void clearFailedUnlockAttempts() {
        this.mFailedAttempts.delete(sCurrentUser);
    }

    public ServiceState getServiceState(int subId) {
        return this.mServiceStates.get(Integer.valueOf(subId));
    }

    public int getFailedUnlockAttempts(int userId) {
        return this.mFailedAttempts.get(userId, 0);
    }

    public void reportFailedStrongAuthUnlockAttempt(int userId) {
        this.mFailedAttempts.put(userId, getFailedUnlockAttempts(userId) + 1);
    }

    public void clearFingerprintRecognized() {
        this.mUserFingerprintAuthenticated.clear();
        this.mTrustManager.clearAllFingerprints();
    }

    public boolean isSimPinVoiceSecure() {
        return isSimPinSecure();
    }

    public boolean isSimPinSecure() {
        for (int phoneId = 0; phoneId < KeyguardUtils.getNumOfPhone(); phoneId++) {
            if (isSimPinSecure(phoneId)) {
                return true;
            }
        }
        return false;
    }

    public IccCardConstants.State getSimState(int subId) {
        if (this.mSimDatas.containsKey(Integer.valueOf(subId))) {
            return this.mSimDatas.get(Integer.valueOf(subId)).simState;
        }
        return IccCardConstants.State.UNKNOWN;
    }

    private boolean refreshSimState(int subId, int slotId) {
        IccCardConstants.State state;
        TelephonyManager tele = TelephonyManager.from(this.mContext);
        int simState = tele.getSimState(slotId);
        try {
            state = IccCardConstants.State.intToState(simState);
        } catch (IllegalArgumentException e) {
            Log.w("KeyguardUpdateMonitor", "Unknown sim state: " + simState);
            state = IccCardConstants.State.UNKNOWN;
        }
        IccCardConstants.State oriState = this.mSimStateOfPhoneId.get(Integer.valueOf(slotId));
        boolean changed = oriState != state;
        if (oriState == IccCardConstants.State.READY && state == IccCardConstants.State.PIN_REQUIRED) {
            changed = false;
        }
        if (changed) {
            this.mSimStateOfPhoneId.put(Integer.valueOf(slotId), state);
        }
        Log.d("KeyguardUpdateMonitor", "refreshSimState() - sub = " + subId + " phoneId = " + slotId + ", ori-state = " + oriState + ", new-state = " + state + ", changed = " + changed);
        return changed;
    }

    public boolean isSimPinSecure(int phoneId) {
        IccCardConstants.State state = this.mSimStateOfPhoneId.get(Integer.valueOf(phoneId));
        return (state == IccCardConstants.State.PIN_REQUIRED || state == IccCardConstants.State.PUK_REQUIRED || (state == IccCardConstants.State.NETWORK_LOCKED && KeyguardUtils.isMediatekSimMeLockSupport())) && !getPinPukMeDismissFlagOfPhoneId(phoneId);
    }

    public void dispatchStartedWakingUp() {
        synchronized (this) {
            this.mDeviceInteractive = true;
        }
        this.mHandler.sendEmptyMessage(319);
    }

    public void dispatchStartedGoingToSleep(int why) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(321, why, 0));
    }

    public void dispatchFinishedGoingToSleep(int why) {
        synchronized (this) {
            this.mDeviceInteractive = false;
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(320, why, 0));
    }

    public void dispatchScreenTurnedOn() {
        synchronized (this) {
            this.mScreenOn = true;
        }
        this.mHandler.sendEmptyMessage(331);
    }

    public void dispatchScreenTurnedOff() {
        synchronized (this) {
            this.mScreenOn = false;
        }
        this.mHandler.sendEmptyMessage(332);
    }

    public void dispatchDreamingStarted() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(333, 1, 0));
    }

    public void dispatchDreamingStopped() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(333, 0, 0));
    }

    public boolean isDeviceInteractive() {
        return this.mDeviceInteractive;
    }

    public boolean isGoingToSleep() {
        return this.mGoingToSleep;
    }

    public SubscriptionInfo getSubscriptionInfoForSubId(int subId) {
        return getSubscriptionInfoForSubId(subId, false);
    }

    public SubscriptionInfo getSubscriptionInfoForSubId(int subId, boolean forceReload) {
        List<SubscriptionInfo> list = getSubscriptionInfo(forceReload);
        for (int i = 0; i < list.size(); i++) {
            SubscriptionInfo info = list.get(i);
            if (subId == info.getSubscriptionId()) {
                return info;
            }
        }
        return null;
    }

    public boolean isLogoutEnabled() {
        return this.mLogoutEnabled;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLogoutEnabled() {
        checkIsHandlerThread();
        boolean logoutEnabled = this.mDevicePolicyManager.isLogoutEnabled();
        if (this.mLogoutEnabled != logoutEnabled) {
            this.mLogoutEnabled = logoutEnabled;
            for (int i = 0; i < this.mCallbacks.size(); i++) {
                KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i).get();
                if (cb != null) {
                    cb.onLogoutEnabledChanged();
                }
            }
        }
    }

    private void checkIsHandlerThread() {
        if (!sDisableHandlerCheckForTesting && !this.mHandler.getLooper().isCurrentThread()) {
            Log.wtf("KeyguardUpdateMonitor", "must call on mHandler's thread " + this.mHandler.getLooper().getThread() + ", not " + Thread.currentThread());
        }
    }

    @VisibleForTesting
    public static void disableHandlerCheckForTesting(Instrumentation instrumentation) {
        Preconditions.checkNotNull(instrumentation, "Must only call this method in tests!");
        sDisableHandlerCheckForTesting = true;
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("KeyguardUpdateMonitor state:");
        pw.println("  SIM States:");
        for (SimData data : this.mSimDatas.values()) {
            pw.println("    " + data.toString());
        }
        pw.println("  Subs:");
        if (this.mSubscriptionInfo != null) {
            for (int i = 0; i < this.mSubscriptionInfo.size(); i++) {
                pw.println("    " + this.mSubscriptionInfo.get(i));
            }
        }
        pw.println("  Service states:");
        Iterator<Integer> it = this.mServiceStates.keySet().iterator();
        while (it.hasNext()) {
            int subId = it.next().intValue();
            pw.println("    " + subId + "=" + this.mServiceStates.get(Integer.valueOf(subId)));
        }
        if (this.mFpm != null && this.mFpm.isHardwareDetected()) {
            int userId = ActivityManager.getCurrentUser();
            int strongAuthFlags = this.mStrongAuthTracker.getStrongAuthForUser(userId);
            pw.println("  Fingerprint state (user=" + userId + ")");
            StringBuilder sb = new StringBuilder();
            sb.append("    allowed=");
            sb.append(isUnlockingWithFingerprintAllowed());
            pw.println(sb.toString());
            pw.println("    auth'd=" + this.mUserFingerprintAuthenticated.get(userId));
            pw.println("    authSinceBoot=" + getStrongAuthTracker().hasUserAuthenticatedSinceBoot());
            pw.println("    disabled(DPM)=" + isFingerprintDisabled(userId));
            pw.println("    possible=" + isUnlockWithFingerprintPossible(userId));
            pw.println("    strongAuthFlags=" + Integer.toHexString(strongAuthFlags));
            pw.println("    trustManaged=" + getUserTrustIsManaged(userId));
        }
    }

    private void initMembers() {
        if (DEBUG) {
            Log.d("KeyguardUpdateMonitor", "initMembers() - NumOfPhone=" + KeyguardUtils.getNumOfPhone());
        }
        for (int i = 0; i < KeyguardUtils.getNumOfPhone(); i++) {
            this.mSimStateOfPhoneId.put(Integer.valueOf(i), IccCardConstants.State.UNKNOWN);
            this.mTelephonyPlmn.put(Integer.valueOf(i), getDefaultPlmn());
            this.mTelephonyCsgId.put(Integer.valueOf(i), "");
            this.mTelephonyHnbName.put(Integer.valueOf(i), "");
            this.mSimMeCategory.put(Integer.valueOf(i), 0);
            this.mSimMeLeftRetryCount.put(Integer.valueOf(i), 5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void proceedToHandleSimStateChanged(SimData simArgs) {
        if (IccCardConstants.State.NETWORK_LOCKED == simArgs.simState && KeyguardUtils.isMediatekSimMeLockSupport()) {
            new simMeStatusQueryThread(simArgs).start();
        } else {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(304, simArgs));
        }
    }

    public void setPinPukMeDismissFlagOfPhoneId(int phoneId, boolean dismiss) {
        Log.d("KeyguardUpdateMonitor", "setPinPukMeDismissFlagOfPhoneId() - phoneId = " + phoneId);
        if (!KeyguardUtils.isValidPhoneId(phoneId)) {
            return;
        }
        int flag2Dismiss = 1 << phoneId;
        if (dismiss) {
            this.mPinPukMeDismissFlag |= flag2Dismiss;
        } else {
            this.mPinPukMeDismissFlag &= ~flag2Dismiss;
        }
    }

    public boolean getPinPukMeDismissFlagOfPhoneId(int phoneId) {
        int flag2Check = 1 << phoneId;
        boolean result = (this.mPinPukMeDismissFlag & flag2Check) == flag2Check;
        return result;
    }

    public void setSimmeDismissFlagOfPhoneId(int phoneId, boolean dismiss) {
        Log.d("KeyguardUpdateMonitor", "setSimmeDismissFlagOfPhoneId() - phoneId = " + phoneId + ", dismiss=" + dismiss);
        if (!KeyguardUtils.isValidPhoneId(phoneId)) {
            return;
        }
        int flag2Dismiss = 16 << phoneId;
        if (dismiss) {
            this.mSimmeDismissFlag |= flag2Dismiss;
        } else {
            this.mSimmeDismissFlag &= ~flag2Dismiss;
        }
    }

    public boolean getSimmeDismissFlagOfPhoneId(int phoneId) {
        int flag2Check = 16 << phoneId;
        boolean result = (this.mSimmeDismissFlag & flag2Check) == flag2Check;
        return result;
    }

    public int getRetryPukCountOfPhoneId(int phoneId) {
        if (phoneId == 3) {
            return SystemProperties.getInt("vendor.gsm.sim.retry.puk1.4", -1);
        }
        if (phoneId == 2) {
            return SystemProperties.getInt("vendor.gsm.sim.retry.puk1.3", -1);
        }
        return phoneId == 1 ? SystemProperties.getInt("vendor.gsm.sim.retry.puk1.2", -1) : SystemProperties.getInt("vendor.gsm.sim.retry.puk1", -1);
    }

    private class simMeStatusQueryThread extends Thread {
        SimData simArgs;

        simMeStatusQueryThread(SimData simArgs) {
            this.simArgs = simArgs;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                KeyguardUpdateMonitor.this.mSimMeCategory.put(Integer.valueOf(this.simArgs.phoneId), Integer.valueOf(this.simArgs.simMECategory));
                Log.d("KeyguardUpdateMonitor", "queryNetworkLock, phoneId =" + this.simArgs.phoneId + ", simMECategory =" + this.simArgs.simMECategory);
                if (this.simArgs.simMECategory >= 0 && this.simArgs.simMECategory <= 5) {
                    int subId = KeyguardUtils.getSubIdUsingPhoneId(this.simArgs.phoneId);
                    Bundle bundle = IMtkTelephonyEx.Stub.asInterface(ServiceManager.getService("phoneEx")).queryNetworkLock(subId, this.simArgs.simMECategory);
                    boolean query_result = bundle.getBoolean("com.mediatek.phone.QUERY_SIMME_LOCK_RESULT", false);
                    Log.d("KeyguardUpdateMonitor", "queryNetworkLock, query_result =" + query_result);
                    if (query_result) {
                        KeyguardUpdateMonitor.this.mSimMeLeftRetryCount.put(Integer.valueOf(this.simArgs.phoneId), Integer.valueOf(bundle.getInt("com.mediatek.phone.SIMME_LOCK_LEFT_COUNT", 5)));
                    } else {
                        Log.e("KeyguardUpdateMonitor", "queryIccNetworkLock result fail");
                    }
                    KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(304, this.simArgs));
                }
            } catch (Exception e) {
                Log.e("KeyguardUpdateMonitor", "queryIccNetworkLock got exception: " + e.getMessage());
            }
        }
    }

    public int getSimMeCategoryOfPhoneId(int phoneId) {
        return this.mSimMeCategory.get(Integer.valueOf(phoneId)).intValue();
    }

    public int getSimMeLeftRetryCountOfPhoneId(int phoneId) {
        return this.mSimMeLeftRetryCount.get(Integer.valueOf(phoneId)).intValue();
    }

    public void minusSimMeLeftRetryCountOfPhoneId(int phoneId) {
        int simMeRetryCount = this.mSimMeLeftRetryCount.get(Integer.valueOf(phoneId)).intValue();
        if (simMeRetryCount > 0) {
            this.mSimMeLeftRetryCount.put(Integer.valueOf(phoneId), Integer.valueOf(simMeRetryCount - 1));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence getTelephonyHnbNameFrom(Intent intent) {
        String hnbName = intent.getStringExtra("hnbName");
        return hnbName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence getTelephonyCsgIdFrom(Intent intent) {
        String csgId = intent.getStringExtra("csgId");
        return csgId;
    }

    public IccCardConstants.State getSimStateOfPhoneId(int phoneId) {
        return this.mSimStateOfPhoneId.get(Integer.valueOf(phoneId));
    }

    public int getSimPinLockPhoneId() {
        for (int phoneId = 0; phoneId < KeyguardUtils.getNumOfPhone(); phoneId++) {
            if (DEBUG) {
                Log.d("KeyguardUpdateMonitor", "getSimPinLockSubId, phoneId=" + phoneId + " mSimStateOfPhoneId.get(phoneId)=" + this.mSimStateOfPhoneId.get(Integer.valueOf(phoneId)));
            }
            if (this.mSimStateOfPhoneId.get(Integer.valueOf(phoneId)) == IccCardConstants.State.PIN_REQUIRED && !getPinPukMeDismissFlagOfPhoneId(phoneId)) {
                int currentSimPinPhoneId = phoneId;
                return currentSimPinPhoneId;
            }
        }
        return -1;
    }

    public int getSimPukLockPhoneId() {
        for (int phoneId = 0; phoneId < KeyguardUtils.getNumOfPhone(); phoneId++) {
            if (DEBUG) {
                Log.d("KeyguardUpdateMonitor", "getSimPukLockSubId, phoneId=" + phoneId + " mSimStateOfSub.get(phoneId)=" + this.mSimStateOfPhoneId.get(Integer.valueOf(phoneId)));
            }
            if (this.mSimStateOfPhoneId.get(Integer.valueOf(phoneId)) == IccCardConstants.State.PUK_REQUIRED && !getPinPukMeDismissFlagOfPhoneId(phoneId) && getRetryPukCountOfPhoneId(phoneId) != 0) {
                int currentSimPukPhoneId = phoneId;
                return currentSimPukPhoneId;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence getTelephonyPlmnFrom(Intent intent) {
        if (intent.getBooleanExtra("showPlmn", false)) {
            String plmn = intent.getStringExtra("plmn");
            return plmn != null ? plmn : getDefaultPlmn();
        }
        return null;
    }

    public CharSequence getDefaultPlmn() {
        return this.mContext.getResources().getText(com.android.systemui.R.string.keyguard_carrier_default);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence getTelephonySpnFrom(Intent intent) {
        String spn;
        if (intent.getBooleanExtra("showSpn", false) && (spn = intent.getStringExtra("spn")) != null) {
            return spn;
        }
        return null;
    }

    private void printState() {
        for (int i = 0; i < KeyguardUtils.getNumOfPhone(); i++) {
            Log.d("KeyguardUpdateMonitor", "Phone# " + i + ", state = " + this.mSimStateOfPhoneId.get(Integer.valueOf(i)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAirPlaneModeUpdate(boolean airPlaneModeEnabled) {
        if (!airPlaneModeEnabled) {
            for (int i = 0; i < KeyguardUtils.getNumOfPhone(); i++) {
                setPinPukMeDismissFlagOfPhoneId(i, false);
                Log.d("KeyguardUpdateMonitor", "setPinPukMeDismissFlagOfPhoneId false: " + i);
            }
            for (int phoneId = 0; phoneId < KeyguardUtils.getNumOfPhone(); phoneId++) {
                if (DEBUG) {
                    Log.d("KeyguardUpdateMonitor", "phoneId = " + phoneId + " state=" + this.mSimStateOfPhoneId.get(Integer.valueOf(phoneId)));
                }
                if (this.mSimStateOfPhoneId.get(Integer.valueOf(phoneId)) != null && !this.mSimStateOfPhoneId.get(Integer.valueOf(phoneId)).equals("")) {
                    switch (AnonymousClass11.$SwitchMap$com$android$internal$telephony$IccCardConstants$State[this.mSimStateOfPhoneId.get(Integer.valueOf(phoneId)).ordinal()]) {
                        case 1:
                        case 2:
                        case 3:
                            IccCardConstants.State oriState = this.mSimStateOfPhoneId.get(Integer.valueOf(phoneId));
                            this.mSimStateOfPhoneId.put(Integer.valueOf(phoneId), IccCardConstants.State.UNKNOWN);
                            int meCategory = 0;
                            if (this.mSimMeCategory.get(Integer.valueOf(phoneId)) != null) {
                                meCategory = this.mSimMeCategory.get(Integer.valueOf(phoneId)).intValue();
                            }
                            SimData simData = new SimData(oriState, phoneId, KeyguardUtils.getSubIdUsingPhoneId(phoneId), meCategory);
                            if (DEBUG) {
                                Log.v("KeyguardUpdateMonitor", "SimData state=" + simData.simState + ", phoneId=" + simData.phoneId + ", subId=" + simData.subId + ", SimData.simMECategory = " + simData.simMECategory);
                            }
                            proceedToHandleSimStateChanged(simData);
                            break;
                    }
                }
            }
        } else if (airPlaneModeEnabled && KeyguardUtils.isFlightModePowerOffMd()) {
            Log.d("KeyguardUpdateMonitor", "Air mode is on, supress all SIM PIN/PUK/ME Lock views.");
            for (int i2 = 0; i2 < KeyguardUtils.getNumOfPhone(); i2++) {
                setPinPukMeDismissFlagOfPhoneId(i2, true);
                Log.d("KeyguardUpdateMonitor", "setPinPukMeDismissFlagOfPhoneId true: " + i2);
            }
        }
        for (int i3 = 0; i3 < this.mCallbacks.size(); i3++) {
            KeyguardUpdateMonitorCallback cb = this.mCallbacks.get(i3).get();
            if (cb != null) {
                cb.onAirPlaneModeChanged(airPlaneModeEnabled);
                cb.onRefreshCarrierInfo();
            }
        }
    }

    /* JADX INFO: renamed from: com.android.keyguard.KeyguardUpdateMonitor$11, reason: invalid class name */
    static /* synthetic */ class AnonymousClass11 {
        static final /* synthetic */ int[] $SwitchMap$com$android$internal$telephony$IccCardConstants$State = new int[IccCardConstants.State.values().length];

        static {
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.PIN_REQUIRED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.PUK_REQUIRED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$IccCardConstants$State[IccCardConstants.State.NETWORK_LOCKED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private boolean isAirplaneModeOn() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) == 1;
    }

    public void setDismissFlagWhenWfcOn(IccCardConstants.State simState) {
        if ((simState == IccCardConstants.State.PIN_REQUIRED || simState == IccCardConstants.State.PUK_REQUIRED || simState == IccCardConstants.State.NETWORK_LOCKED) && isAirplaneModeOn() && isWifiEnabled() && KeyguardUtils.isFlightModePowerOffMd()) {
            for (int i = 0; i < KeyguardUtils.getNumOfPhone(); i++) {
                setPinPukMeDismissFlagOfPhoneId(i, false);
                Log.d("KeyguardUpdateMonitor", "Wifi calling opened MD, setPinPukMeDismissFlagOfPhoneId false: " + i);
            }
        }
    }

    private boolean isWifiEnabled() {
        int wifiState = this.mWifiManager.getWifiState();
        Log.d("KeyguardUpdateMonitor", "wifi state:" + wifiState);
        return wifiState != 1;
    }
}
