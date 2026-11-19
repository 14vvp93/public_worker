package com.android.keyguard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Slog;
import android.util.StatsLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.widget.LockPatternUtils;
import com.mediatek.keyguard.AntiTheft.AntiTheftManager;
import com.mediatek.keyguard.Telephony.KeyguardSimPinPukMeView;
import com.mediatek.keyguard.VoiceWakeup.VoiceWakeupManagerProxy;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardSecurityContainer extends FrameLayout implements KeyguardSecurityView {
    private static final boolean DEBUG = KeyguardConstants.DEBUG;
    private AlertDialog mAlertDialog;
    private KeyguardSecurityCallback mCallback;
    private KeyguardSecurityModel.SecurityMode mCurrentSecuritySelection;
    private LockPatternUtils mLockPatternUtils;
    private KeyguardSecurityCallback mNullCallback;
    private SecurityCallback mSecurityCallback;
    private KeyguardSecurityModel mSecurityModel;
    private KeyguardSecurityViewFlipper mSecurityViewFlipper;
    private final KeyguardUpdateMonitor mUpdateMonitor;

    public interface SecurityCallback {
        boolean dismiss(boolean z, int i);

        void finish(boolean z, int i);

        void onSecurityModeChanged(KeyguardSecurityModel.SecurityMode securityMode, boolean z);

        void reset();

        void updateNavbarStatus();

        void userActivity();
    }

    public KeyguardSecurityContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyguardSecurityContainer(Context context) {
        this(context, null, 0);
    }

    public KeyguardSecurityContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mCurrentSecuritySelection = KeyguardSecurityModel.SecurityMode.Invalid;
        this.mCallback = new KeyguardSecurityCallback() { // from class: com.android.keyguard.KeyguardSecurityContainer.1
            @Override // com.android.keyguard.KeyguardSecurityCallback
            public void userActivity() {
                if (KeyguardSecurityContainer.this.mSecurityCallback != null) {
                    KeyguardSecurityContainer.this.mSecurityCallback.userActivity();
                }
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public void dismiss(boolean authenticated, int targetId) {
                KeyguardSecurityContainer.this.mSecurityCallback.dismiss(authenticated, targetId);
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public void reportUnlockAttempt(int userId, boolean success, int timeoutMs) {
                KeyguardUpdateMonitor monitor = KeyguardUpdateMonitor.getInstance(KeyguardSecurityContainer.this.mContext);
                if (success) {
                    StatsLog.write(64, 2);
                    monitor.clearFailedUnlockAttempts();
                    KeyguardSecurityContainer.this.mLockPatternUtils.reportSuccessfulPasswordAttempt(userId);
                } else {
                    StatsLog.write(64, 1);
                    KeyguardSecurityContainer.this.reportFailedUnlockAttempt(userId, timeoutMs);
                }
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public void reset() {
                KeyguardSecurityContainer.this.mSecurityCallback.reset();
            }
        };
        this.mNullCallback = new KeyguardSecurityCallback() { // from class: com.android.keyguard.KeyguardSecurityContainer.2
            @Override // com.android.keyguard.KeyguardSecurityCallback
            public void userActivity() {
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public void reportUnlockAttempt(int userId, boolean success, int timeoutMs) {
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public void dismiss(boolean securityVerified, int targetUserId) {
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public void reset() {
            }
        };
        this.mSecurityModel = new KeyguardSecurityModel(context);
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mUpdateMonitor = KeyguardUpdateMonitor.getInstance(this.mContext);
    }

    public void setSecurityCallback(SecurityCallback callback) {
        this.mSecurityCallback = callback;
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void onResume(int reason) {
        if (this.mCurrentSecuritySelection != KeyguardSecurityModel.SecurityMode.None) {
            getSecurityView(this.mCurrentSecuritySelection).onResume(reason);
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void onPause() {
        if (this.mAlertDialog != null) {
            this.mAlertDialog.dismiss();
            this.mAlertDialog = null;
        }
        if (this.mCurrentSecuritySelection != KeyguardSecurityModel.SecurityMode.None) {
            getSecurityView(this.mCurrentSecuritySelection).onPause();
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void startAppearAnimation() {
        if (this.mCurrentSecuritySelection != KeyguardSecurityModel.SecurityMode.None) {
            getSecurityView(this.mCurrentSecuritySelection).startAppearAnimation();
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public boolean startDisappearAnimation(Runnable onFinishRunnable) {
        if (this.mCurrentSecuritySelection != KeyguardSecurityModel.SecurityMode.None) {
            return getSecurityView(this.mCurrentSecuritySelection).startDisappearAnimation(onFinishRunnable);
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public CharSequence getTitle() {
        return this.mSecurityViewFlipper.getTitle();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private KeyguardSecurityView getSecurityView(KeyguardSecurityModel.SecurityMode securityMode) {
        int securityViewIdForMode = getSecurityViewIdForMode(securityMode);
        KeyguardSecurityView view = null;
        int children = this.mSecurityViewFlipper.getChildCount();
        for (int child = 0; child < children; child++) {
            if (this.mSecurityViewFlipper.getChildAt(child).getId() == securityViewIdForMode) {
                view = (KeyguardSecurityView) this.mSecurityViewFlipper.getChildAt(child);
                break;
            }
        }
        int layoutId = getLayoutIdFor(securityMode);
        if (view == null && layoutId != 0) {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            if (DEBUG) {
                Log.v("KeyguardSecurityView", "inflating id = " + layoutId);
            }
            View viewInflate = inflater.inflate(layoutId, (ViewGroup) this.mSecurityViewFlipper, false);
            view = (KeyguardSecurityView) viewInflate;
            if (view instanceof KeyguardSimPinPukMeView) {
                KeyguardSimPinPukMeView pinPukView = (KeyguardSimPinPukMeView) view;
                int phoneId = this.mSecurityModel.getPhoneIdUsingSecurityMode(securityMode);
                pinPukView.setPhoneId(phoneId);
            }
            this.mSecurityViewFlipper.addView(viewInflate);
            updateSecurityView(viewInflate);
        } else if (view != null && (view instanceof KeyguardSimPinPukMeView) && securityMode != this.mCurrentSecuritySelection) {
            Log.i("KeyguardSecurityView", "getSecurityView, here, we will refresh the layout");
            KeyguardSimPinPukMeView pinPukView2 = (KeyguardSimPinPukMeView) view;
            int phoneId2 = this.mSecurityModel.getPhoneIdUsingSecurityMode(securityMode);
            pinPukView2.setPhoneId(phoneId2);
        }
        return view;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateSecurityView(View view) {
        if (view instanceof KeyguardSecurityView) {
            KeyguardSecurityView ksv = (KeyguardSecurityView) view;
            ksv.setKeyguardCallback(this.mCallback);
            ksv.setLockPatternUtils(this.mLockPatternUtils);
        } else {
            Log.w("KeyguardSecurityView", "View " + view + " is not a KeyguardSecurityView");
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        this.mSecurityViewFlipper = (KeyguardSecurityViewFlipper) findViewById(com.android.systemui.R.id.view_flipper);
        this.mSecurityViewFlipper.setLockPatternUtils(this.mLockPatternUtils);
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void setLockPatternUtils(LockPatternUtils utils) {
        this.mLockPatternUtils = utils;
        this.mSecurityModel.setLockPatternUtils(utils);
        this.mSecurityViewFlipper.setLockPatternUtils(this.mLockPatternUtils);
    }

    private void showDialog(String title, String message) {
        if (this.mAlertDialog != null) {
            this.mAlertDialog.dismiss();
        }
        this.mAlertDialog = new AlertDialog.Builder(this.mContext).setTitle(title).setMessage(message).setCancelable(false).setNeutralButton(com.android.systemui.R.string.ok, (DialogInterface.OnClickListener) null).create();
        if (!(this.mContext instanceof Activity)) {
            this.mAlertDialog.getWindow().setType(2009);
        }
        this.mAlertDialog.show();
    }

    private void showAlmostAtWipeDialog(int attempts, int remaining, int userType) {
        String message = null;
        switch (userType) {
            case 1:
                message = this.mContext.getString(com.android.systemui.R.string.kg_failed_attempts_almost_at_wipe, Integer.valueOf(attempts), Integer.valueOf(remaining));
                break;
            case 2:
                message = this.mContext.getString(com.android.systemui.R.string.kg_failed_attempts_almost_at_erase_profile, Integer.valueOf(attempts), Integer.valueOf(remaining));
                break;
            case 3:
                message = this.mContext.getString(com.android.systemui.R.string.kg_failed_attempts_almost_at_erase_user, Integer.valueOf(attempts), Integer.valueOf(remaining));
                break;
        }
        showDialog(null, message);
    }

    private void showWipeDialog(int attempts, int userType) {
        String message = null;
        switch (userType) {
            case 1:
                message = this.mContext.getString(com.android.systemui.R.string.kg_failed_attempts_now_wiping, Integer.valueOf(attempts));
                break;
            case 2:
                message = this.mContext.getString(com.android.systemui.R.string.kg_failed_attempts_now_erasing_profile, Integer.valueOf(attempts));
                break;
            case 3:
                message = this.mContext.getString(com.android.systemui.R.string.kg_failed_attempts_now_erasing_user, Integer.valueOf(attempts));
                break;
        }
        showDialog(null, message);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportFailedUnlockAttempt(int userId, int timeoutMs) {
        int remainingBeforeWipe;
        KeyguardUpdateMonitor monitor = KeyguardUpdateMonitor.getInstance(this.mContext);
        int failedAttempts = monitor.getFailedUnlockAttempts(userId) + 1;
        if (DEBUG) {
            Log.d("KeyguardSecurityView", "reportFailedPatternAttempt: #" + failedAttempts);
        }
        DevicePolicyManager dpm = this.mLockPatternUtils.getDevicePolicyManager();
        int failedAttemptsBeforeWipe = dpm.getMaximumFailedPasswordsForWipe(null, userId);
        if (failedAttemptsBeforeWipe > 0) {
            remainingBeforeWipe = failedAttemptsBeforeWipe - failedAttempts;
        } else {
            remainingBeforeWipe = Integer.MAX_VALUE;
        }
        if (remainingBeforeWipe < 5) {
            int expiringUser = dpm.getProfileWithMinimumFailedPasswordsForWipe(userId);
            int userType = 1;
            if (expiringUser == userId) {
                if (expiringUser != 0) {
                    userType = 3;
                }
            } else if (expiringUser != -10000) {
                userType = 2;
            }
            if (remainingBeforeWipe > 0) {
                showAlmostAtWipeDialog(failedAttempts, remainingBeforeWipe, userType);
            } else {
                Slog.i("KeyguardSecurityView", "Too many unlock attempts; user " + expiringUser + " will be wiped!");
                showWipeDialog(failedAttempts, userType);
            }
        }
        monitor.reportFailedStrongAuthUnlockAttempt(userId);
        this.mLockPatternUtils.reportFailedPasswordAttempt(userId);
        if (timeoutMs > 0) {
            this.mLockPatternUtils.reportPasswordLockout(timeoutMs, userId);
        }
    }

    void showPrimarySecurityScreen(boolean turningOff) {
        KeyguardSecurityModel.SecurityMode securityMode = this.mSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser());
        if (DEBUG) {
            Log.v("KeyguardSecurityView", "showPrimarySecurityScreen(turningOff=" + turningOff + ")");
        }
        Log.v("KeyguardSecurityView", "showPrimarySecurityScreen(securityMode=" + securityMode + ")");
        if (this.mSecurityModel.isSimPinPukSecurityMode(this.mCurrentSecuritySelection)) {
            Log.d("KeyguardSecurityView", "showPrimarySecurityScreen() - current is " + this.mCurrentSecuritySelection);
            int phoneId = this.mSecurityModel.getPhoneIdUsingSecurityMode(this.mCurrentSecuritySelection);
            Log.d("KeyguardSecurityView", "showPrimarySecurityScreen() - phoneId of currentView is " + phoneId);
            boolean isCurrentModeSimPinSecure = this.mUpdateMonitor.isSimPinSecure(phoneId);
            Log.d("KeyguardSecurityView", "showPrimarySecurityScreen() - isCurrentModeSimPinSecure = " + isCurrentModeSimPinSecure);
            if (isCurrentModeSimPinSecure) {
                Log.d("KeyguardSecurityView", "Skip show security because it already shows SimPinPukMeView");
                return;
            }
            Log.d("KeyguardSecurityView", "showPrimarySecurityScreen() - since current simpinview not secured, we should call showSecurityScreen() to set correct PhoneId for next view.");
        }
        showSecurityScreen(securityMode);
    }

    boolean showNextSecurityScreenOrFinish(boolean authenticated, int targetUserId) {
        if (DEBUG) {
            Log.d("KeyguardSecurityView", "showNextSecurityScreenOrFinish(" + authenticated + ")");
        }
        Log.d("KeyguardSecurityView", "showNext.. mCurrentSecuritySelection = " + this.mCurrentSecuritySelection);
        boolean finish = false;
        boolean strongAuth = false;
        if (this.mUpdateMonitor.getUserCanSkipBouncer(targetUserId)) {
            finish = true;
        } else if (KeyguardSecurityModel.SecurityMode.None == this.mCurrentSecuritySelection) {
            KeyguardSecurityModel.SecurityMode securityMode = this.mSecurityModel.getSecurityMode(targetUserId);
            if (KeyguardSecurityModel.SecurityMode.None == securityMode) {
                if (DEBUG) {
                    Log.d("KeyguardSecurityView", "showNextSecurityScreenOrFinish() - securityMode is None, just finish.");
                }
                finish = true;
            } else {
                if (DEBUG) {
                    Log.d("KeyguardSecurityView", "showNextSecurityScreenOrFinish()- switch to the alternate security view for None mode.");
                }
                showSecurityScreen(securityMode);
            }
        } else if (authenticated) {
            if (DEBUG) {
                Log.d("KeyguardSecurityView", "showNextSecurityScreenOrFinish() - authenticated is True, and mCurrentSecuritySelection = " + this.mCurrentSecuritySelection);
            }
            KeyguardSecurityModel.SecurityMode securityMode2 = this.mSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser());
            if (DEBUG) {
                Log.v("KeyguardSecurityView", "securityMode = " + securityMode2);
            }
            Log.d("KeyguardSecurityView", "mCurrentSecuritySelection: " + this.mCurrentSecuritySelection);
            switch (this.mCurrentSecuritySelection) {
                case Pattern:
                case PIN:
                case Password:
                    strongAuth = true;
                    finish = true;
                    break;
                case Invalid:
                case None:
                default:
                    Log.v("KeyguardSecurityView", "Bad security screen " + this.mCurrentSecuritySelection + ", fail safe");
                    showPrimarySecurityScreen(false);
                    break;
                case SimPinPukMe1:
                case SimPinPukMe2:
                case SimPinPukMe3:
                case SimPinPukMe4:
                    if (securityMode2 != KeyguardSecurityModel.SecurityMode.None) {
                        showSecurityScreen(securityMode2);
                    } else {
                        finish = true;
                    }
                    break;
                case AntiTheft:
                    KeyguardSecurityModel.SecurityMode nextMode = this.mSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser());
                    if (DEBUG) {
                        Log.v("KeyguardSecurityView", "now is Antitheft, next securityMode = " + nextMode);
                    }
                    if (nextMode != KeyguardSecurityModel.SecurityMode.None) {
                        showSecurityScreen(nextMode);
                    } else {
                        finish = true;
                    }
                    break;
            }
        }
        this.mSecurityCallback.updateNavbarStatus();
        if (finish) {
            this.mSecurityCallback.finish(strongAuth, targetUserId);
            Log.d("KeyguardSecurityView", "finish ");
        }
        if (DEBUG) {
            Log.d("KeyguardSecurityView", "showNextSecurityScreenOrFinish() - return finish = " + finish);
        }
        return finish;
    }

    private void showSecurityScreen(KeyguardSecurityModel.SecurityMode securityMode) {
        if (DEBUG) {
            Log.d("KeyguardSecurityView", "showSecurityScreen(" + securityMode + ")");
        }
        if (securityMode == this.mCurrentSecuritySelection && securityMode != KeyguardSecurityModel.SecurityMode.AntiTheft) {
            return;
        }
        VoiceWakeupManagerProxy.getInstance().notifySecurityModeChange(this.mCurrentSecuritySelection, securityMode);
        Log.d("KeyguardSecurityView", "showSecurityScreen() - get oldview for" + this.mCurrentSecuritySelection + ", get newview for" + securityMode);
        KeyguardSecurityView oldView = getSecurityView(this.mCurrentSecuritySelection);
        KeyguardSecurityView newView = getSecurityView(securityMode);
        if (oldView != null) {
            oldView.onPause();
            oldView.setKeyguardCallback(this.mNullCallback);
        }
        if (securityMode != KeyguardSecurityModel.SecurityMode.None) {
            newView.setKeyguardCallback(this.mCallback);
            Log.d("KeyguardSecurityView", "showSecurityScreen() - newview.setKeyguardCallback(mCallback)");
            newView.onResume(2);
        }
        int childCount = this.mSecurityViewFlipper.getChildCount();
        int securityViewIdForMode = getSecurityViewIdForMode(securityMode);
        boolean z = false;
        for (int i = 0; i < childCount; i++) {
            if (this.mSecurityViewFlipper.getChildAt(i).getId() == securityViewIdForMode) {
                this.mSecurityViewFlipper.setDisplayedChild(i);
                break;
            }
        }
        this.mCurrentSecuritySelection = securityMode;
        Log.d("KeyguardSecurityView", "Before update, mCurrentSecuritySelection = " + securityMode + "After update, mCurrentSecuritySelection = " + this.mCurrentSecuritySelection);
        SecurityCallback securityCallback = this.mSecurityCallback;
        if (securityMode != KeyguardSecurityModel.SecurityMode.None && newView.needsInput()) {
            z = true;
        }
        securityCallback.onSecurityModeChanged(securityMode, z);
    }

    private int getSecurityViewIdForMode(KeyguardSecurityModel.SecurityMode securityMode) {
        switch (securityMode) {
            case Pattern:
                return com.android.systemui.R.id.keyguard_pattern_view;
            case PIN:
                return com.android.systemui.R.id.keyguard_pin_view;
            case Password:
                return com.android.systemui.R.id.keyguard_password_view;
            case Invalid:
            case None:
            default:
                return 0;
            case SimPinPukMe1:
            case SimPinPukMe2:
            case SimPinPukMe3:
            case SimPinPukMe4:
                return com.android.systemui.R.id.keyguard_sim_pin_puk_me_view;
            case AntiTheft:
                return AntiTheftManager.getAntiTheftViewId();
            case AlarmBoot:
                return com.android.systemui.R.id.power_off_alarm_view;
        }
    }

    public int getLayoutIdFor(KeyguardSecurityModel.SecurityMode securityMode) {
        switch (securityMode) {
            case Pattern:
                return com.android.systemui.R.layout.keyguard_pattern_view;
            case PIN:
                return com.android.systemui.R.layout.keyguard_pin_view;
            case Password:
                return com.android.systemui.R.layout.keyguard_password_view;
            case Invalid:
            case None:
            default:
                return 0;
            case SimPinPukMe1:
            case SimPinPukMe2:
            case SimPinPukMe3:
            case SimPinPukMe4:
                return com.android.systemui.R.layout.mtk_keyguard_sim_pin_puk_me_view;
            case AntiTheft:
                return AntiTheftManager.getAntiTheftLayoutId();
            case AlarmBoot:
                return com.android.systemui.R.layout.mtk_power_off_alarm_view;
        }
    }

    public KeyguardSecurityModel.SecurityMode getSecurityMode() {
        return this.mSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser());
    }

    public KeyguardSecurityModel.SecurityMode getCurrentSecurityMode() {
        return this.mCurrentSecuritySelection;
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public boolean needsInput() {
        return this.mSecurityViewFlipper.needsInput();
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void setKeyguardCallback(KeyguardSecurityCallback callback) {
        this.mSecurityViewFlipper.setKeyguardCallback(callback);
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void reset() {
        this.mSecurityViewFlipper.reset();
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void showPromptReason(int reason) {
        if (this.mCurrentSecuritySelection != KeyguardSecurityModel.SecurityMode.None) {
            if (reason != 0) {
                Log.i("KeyguardSecurityView", "Strong auth required, reason: " + reason);
            }
            getSecurityView(this.mCurrentSecuritySelection).showPromptReason(reason);
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void showMessage(CharSequence message, int color) {
        if (this.mCurrentSecuritySelection != KeyguardSecurityModel.SecurityMode.None) {
            getSecurityView(this.mCurrentSecuritySelection).showMessage(message, color);
        }
    }
}
