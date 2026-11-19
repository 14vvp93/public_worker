package com.mediatek.keyguard.AntiTheft;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardSecurityCallback;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUtils;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.R;
import com.mediatek.common.ppl.IPplManager;
import vendor.mediatek.hardware.pplagent.V1_0.IPplAgent;

/* JADX INFO: loaded from: classes.dex */
public class AntiTheftManager {
    public static final String ANTITHEFT_NONEED_PRINT_TEXT = "AntiTheft Noneed Print Text";
    private static final int MSG_ANTITHEFT_KEYGUARD_UPDATE = 1001;
    public static final String PPL_LOCK = "com.mediatek.ppl.NOTIFY_LOCK";
    public static final String PPL_UNLOCK = "com.mediatek.ppl.NOTIFY_UNLOCK";
    public static final String RESET_FOR_ANTITHEFT_LOCK = "antitheftlock_reset";
    private static final String TAG = "AntiTheftManager";
    private static Context mContext;
    private static IPplManager mIPplManager;
    private static AntiTheftManager sInstance;
    protected KeyguardSecurityCallback mKeyguardSecurityCallback;
    private LockPatternUtils mLockPatternUtils;
    private KeyguardSecurityModel mSecurityModel;
    private ViewMediatorCallback mViewMediatorCallback;
    private static int mAntiTheftLockEnabled = 0;
    private static int mKeypadNeeded = 0;
    private static int mDismissable = 0;
    private static final boolean DEBUG = false;
    private static boolean mAntiTheftAutoTestNotShowUI = DEBUG;
    private final int MSG_ARG_LOCK = 0;
    private final int MSG_ARG_UNLOCK = 1;

    @VisibleForTesting
    protected final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.mediatek.keyguard.AntiTheft.AntiTheftManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(AntiTheftManager.TAG, "handleAntiTheftViewUpdate() - action = " + action);
            if (AntiTheftManager.PPL_LOCK.equals(action)) {
                Log.d(AntiTheftManager.TAG, "receive PPL_LOCK");
                if (!KeyguardUtils.isSystemEncrypted()) {
                    AntiTheftManager.this.sendAntiTheftUpdateMsg(2, 0);
                    return;
                } else {
                    Log.d(AntiTheftManager.TAG, "Currently system needs to be decrypted. Not show PPL.");
                    return;
                }
            }
            if (AntiTheftManager.PPL_UNLOCK.equals(action)) {
                Log.d(AntiTheftManager.TAG, "receive PPL_UNLOCK");
                AntiTheftManager.this.sendAntiTheftUpdateMsg(2, 1);
            }
        }
    };
    private Handler mHandler = new Handler(Looper.myLooper(), null, true) { // from class: com.mediatek.keyguard.AntiTheft.AntiTheftManager.2
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == AntiTheftManager.MSG_ANTITHEFT_KEYGUARD_UPDATE) {
                AntiTheftManager.this.handleAntiTheftViewUpdate(msg.arg1, msg.arg2 == 0 ? true : AntiTheftManager.DEBUG);
            }
        }
    };

    @VisibleForTesting
    protected ServiceConnection mPplServiceConnection = new ServiceConnection() { // from class: com.mediatek.keyguard.AntiTheft.AntiTheftManager.3
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(AntiTheftManager.TAG, "onServiceConnected() -- PPL");
            IPplManager unused = AntiTheftManager.mIPplManager = IPplManager.Stub.asInterface(service);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            Log.i(AntiTheftManager.TAG, "onServiceDisconnected()");
            IPplManager unused = AntiTheftManager.mIPplManager = null;
        }
    };

    public AntiTheftManager(Context context, ViewMediatorCallback viewMediatorCallback, LockPatternUtils lockPatternUtils) {
        Log.d(TAG, "AntiTheftManager() is called.");
        mContext = context;
        this.mViewMediatorCallback = viewMediatorCallback;
        this.mLockPatternUtils = lockPatternUtils;
        this.mSecurityModel = new KeyguardSecurityModel(mContext);
        IntentFilter filter = new IntentFilter();
        if (KeyguardUtils.isPrivacyProtectionLockSupport()) {
            Log.d(TAG, "MTK_PRIVACY_PROTECTION_LOCK is enabled.");
            setKeypadNeeded(2, true);
            setDismissable(2, true);
            filter.addAction(PPL_LOCK);
            filter.addAction(PPL_UNLOCK);
        }
        mContext.registerReceiver(this.mBroadcastReceiver, filter);
    }

    public static AntiTheftManager getInstance(Context context, ViewMediatorCallback viewMediatorCallback, LockPatternUtils lockPatternUtils) {
        if (sInstance == null) {
            Log.d(TAG, "getInstance(...) create one.");
            sInstance = new AntiTheftManager(context, viewMediatorCallback, lockPatternUtils);
        }
        return sInstance;
    }

    public static String getAntiTheftModeName(int mode) {
        if (mode != 0 && mode == 2) {
            return "AntiTheftMode.PplLock";
        }
        return "AntiTheftMode.None";
    }

    public static int getCurrentAntiTheftMode() {
        if (!isAntiTheftLocked()) {
            return 0;
        }
        for (int shift = 0; shift < 32; shift++) {
            int mode = mAntiTheftLockEnabled & (1 << shift);
            if (mode != 0) {
                return mode;
            }
        }
        return 0;
    }

    public static boolean isKeypadNeeded() {
        int mode = getCurrentAntiTheftMode();
        Log.d(TAG, "getCurrentAntiTheftMode() = " + getAntiTheftModeName(mode));
        boolean needKeypad = (mKeypadNeeded & mode) != 0 ? true : DEBUG;
        Log.d(TAG, "isKeypadNeeded() = " + needKeypad);
        return needKeypad;
    }

    public static void setKeypadNeeded(int lockMode, boolean need) {
        if (need) {
            mKeypadNeeded |= lockMode;
        } else {
            mKeypadNeeded &= ~lockMode;
        }
    }

    public static boolean isAntiTheftLocked() {
        if (mAntiTheftLockEnabled != 0) {
            return true;
        }
        return DEBUG;
    }

    private static boolean isNeedUpdate(int lockMode, boolean enable) {
        if (enable && (mAntiTheftLockEnabled & lockMode) != 0) {
            Log.d(TAG, "isNeedUpdate() - lockMode( " + lockMode + " ) is already enabled, no need update");
            return DEBUG;
        }
        if (enable || (mAntiTheftLockEnabled & lockMode) != 0) {
            return true;
        }
        Log.d(TAG, "isNeedUpdate() - lockMode( " + lockMode + " ) is already disabled, no need update");
        return DEBUG;
    }

    private void setAntiTheftLocked(int lockMode, boolean enable) {
        if (enable) {
            mAntiTheftLockEnabled |= lockMode;
        } else {
            mAntiTheftLockEnabled &= ~lockMode;
        }
    }

    public static boolean isDismissable() {
        int mode = getCurrentAntiTheftMode();
        if (mode != 0 && (mDismissable & mode) == 0) {
            return DEBUG;
        }
        return true;
    }

    public static void setDismissable(int lockMode, boolean canBeDismissed) {
        Log.d(TAG, "mDismissable is " + mDismissable + " before");
        if (canBeDismissed) {
            mDismissable |= lockMode;
        } else {
            mDismissable &= ~lockMode;
        }
        Log.d(TAG, "mDismissable is " + mDismissable + " after");
    }

    public static boolean isAntiTheftPriorToSecMode(KeyguardSecurityModel.SecurityMode mode) {
        getCurrentAntiTheftMode();
        if (isAntiTheftLocked()) {
            switch (mode) {
                case SimPinPukMe1:
                case SimPinPukMe2:
                case SimPinPukMe3:
                case SimPinPukMe4:
                case AlarmBoot:
                    return DEBUG;
                default:
                    return true;
            }
        }
        return DEBUG;
    }

    public static int getAntiTheftViewId() {
        return R.id.keyguard_antitheft_lock_view;
    }

    public static int getAntiTheftLayoutId() {
        return R.layout.mtk_keyguard_anti_theft_lock_view;
    }

    public static int getPrompt() {
        return R.string.ppl_prompt;
    }

    public static String getAntiTheftMessageAreaText(CharSequence text, CharSequence seperator) {
        StringBuilder b = new StringBuilder();
        if (text != null && text.length() > 0 && !text.toString().equals(ANTITHEFT_NONEED_PRINT_TEXT)) {
            b.append(text);
            b.append(seperator);
        }
        b.append(mContext.getText(getPrompt()));
        return b.toString();
    }

    public static boolean isAntiTheftAutoTestNotShowUI() {
        return mAntiTheftAutoTestNotShowUI;
    }

    public boolean checkPassword(String pw) {
        boolean unlockSuccess = DEBUG;
        int mode = getCurrentAntiTheftMode();
        Log.d(TAG, "checkPassword, mode is " + getAntiTheftModeName(mode));
        if (mode == 2) {
            unlockSuccess = doPplCheckPassword(pw);
        }
        Log.d(TAG, "checkPassword, unlockSuccess is " + unlockSuccess);
        return unlockSuccess;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAntiTheftUpdateMsg(int antiTheftLockType, int lock) {
        Message msg = this.mHandler.obtainMessage(MSG_ANTITHEFT_KEYGUARD_UPDATE);
        msg.arg1 = antiTheftLockType;
        msg.arg2 = lock;
        msg.sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAntiTheftViewUpdate(int antiTheftLockType, boolean lock) {
        if (isNeedUpdate(antiTheftLockType, lock)) {
            setAntiTheftLocked(antiTheftLockType, lock);
            if (lock) {
                Log.d(TAG, "handleAntiTheftViewUpdate() - locked, !isShowing = " + (true ^ this.mViewMediatorCallback.isShowing()) + " isKeyguardDoneOnGoing = " + this.mViewMediatorCallback.isKeyguardDoneOnGoing());
                if (!this.mViewMediatorCallback.isShowing() || this.mViewMediatorCallback.isKeyguardDoneOnGoing()) {
                    this.mViewMediatorCallback.showLocked(null);
                } else {
                    boolean needToRest = isAntiTheftPriorToSecMode(this.mSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser()));
                    if (needToRest) {
                        Log.d(TAG, "handleAntiTheftViewUpdate() - call resetStateLocked().");
                        this.mViewMediatorCallback.resetStateLocked();
                    } else {
                        Log.d(TAG, "No need to reset the security view to show AntiTheft,since current view should show above antitheft view.");
                    }
                }
            } else if (this.mKeyguardSecurityCallback != null) {
                this.mKeyguardSecurityCallback.dismiss(true, KeyguardUpdateMonitor.getCurrentUser());
            } else {
                Log.d(TAG, "mKeyguardSecurityCallback is null !");
            }
            adjustStatusBarLocked();
        }
    }

    public void doBindAntiThftLockServices() {
        Log.d(TAG, "doBindAntiThftLockServices() is called.");
        if (KeyguardUtils.isPrivacyProtectionLockSupport()) {
            bindPplService();
        }
    }

    public void doAntiTheftLockCheck() {
        String status = SystemProperties.get("ro.crypto.state", "unsupported");
        if ("unencrypted".equalsIgnoreCase(status)) {
            doPplLockCheck();
        }
    }

    private void doPplLockCheck() {
        if (mAntiTheftLockEnabled == 2) {
            setAntiTheftLocked(2, true);
        }
    }

    public static void checkPplStatus() {
        boolean z = true;
        boolean isUnEncrypted = !KeyguardUtils.isSystemEncrypted();
        try {
            IPplAgent agent = IPplAgent.getService();
            if (agent != null) {
                boolean flag = agent.needLock() == 1;
                StringBuilder sb = new StringBuilder();
                sb.append("PplCheckLocked, the lock flag is:");
                if (!flag || !isUnEncrypted) {
                    z = false;
                }
                sb.append(z);
                Log.i(TAG, sb.toString());
                if (flag && isUnEncrypted) {
                    mAntiTheftLockEnabled |= 2;
                }
                return;
            }
            Log.i(TAG, "PplCheckLocked, PPLAgent doesn't exit");
        } catch (Exception e) {
            Log.e(TAG, "doPplLockCheck() - error in get PPLAgent service.");
        }
    }

    private void bindPplService() {
        Log.e(TAG, "binPplService() is called.");
        if (mIPplManager == null) {
            try {
                Intent intent = new Intent("com.mediatek.ppl.service");
                intent.setClassName("com.mediatek.ppl", "com.mediatek.ppl.PplService");
                mContext.bindService(intent, this.mPplServiceConnection, 1);
                return;
            } catch (SecurityException e) {
                Log.e(TAG, "bindPplService() - error in bind ppl service.");
                return;
            }
        }
        Log.d(TAG, "bindPplService() -- the ppl service is already bound.");
    }

    private boolean doPplCheckPassword(String pw) {
        boolean unlockSuccess = DEBUG;
        if (mIPplManager != null) {
            try {
                unlockSuccess = mIPplManager.unlock(pw);
                Log.i(TAG, "doPplCheckPassword, unlockSuccess is " + unlockSuccess);
                if (unlockSuccess) {
                    setAntiTheftLocked(2, DEBUG);
                }
            } catch (RemoteException e) {
            }
        } else {
            Log.i(TAG, "doPplCheckPassword() mIPplManager == null !!??");
        }
        return unlockSuccess;
    }

    public void adjustStatusBarLocked() {
        this.mViewMediatorCallback.adjustStatusBarLocked();
    }

    public void setSecurityViewCallback(KeyguardSecurityCallback callback) {
        Log.d(TAG, "setSecurityViewCallback(" + callback + ")");
        this.mKeyguardSecurityCallback = callback;
    }

    @VisibleForTesting
    public IPplManager getPPLManagerInstance() {
        return mIPplManager;
    }

    @VisibleForTesting
    public BroadcastReceiver getPPLBroadcastReceiverInstance() {
        return this.mBroadcastReceiver;
    }

    @VisibleForTesting
    public Handler getHandlerInstance() {
        return this.mHandler;
    }
}
