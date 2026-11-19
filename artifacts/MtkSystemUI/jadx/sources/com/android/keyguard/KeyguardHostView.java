package com.android.keyguard;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import com.android.internal.widget.LockPatternUtils;
import com.android.settingslib.Utils;
import com.mediatek.keyguard.AntiTheft.AntiTheftManager;
import com.mediatek.keyguard.VoiceWakeup.VoiceWakeupManagerProxy;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardHostView extends FrameLayout implements KeyguardSecurityContainer.SecurityCallback {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    private AudioManager mAudioManager;
    private Runnable mCancelAction;
    private OnDismissAction mDismissAction;
    protected LockPatternUtils mLockPatternUtils;
    private KeyguardSecurityContainer mSecurityContainer;
    private TelephonyManager mTelephonyManager;
    private final KeyguardUpdateMonitorCallback mUpdateCallback;
    protected ViewMediatorCallback mViewMediatorCallback;

    public interface OnDismissAction {
        boolean onDismiss();
    }

    public KeyguardHostView(Context context) {
        this(context, null);
    }

    public KeyguardHostView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTelephonyManager = null;
        this.mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardHostView.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onUserSwitchComplete(int userId) {
                KeyguardHostView.this.getSecurityContainer().showPrimarySecurityScreen(false);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onTrustGrantedWithFlags(int flags, int userId) {
                if (userId == KeyguardUpdateMonitor.getCurrentUser() && KeyguardHostView.this.isAttachedToWindow()) {
                    boolean bouncerVisible = KeyguardHostView.this.isVisibleToUser();
                    boolean initiatedByUser = (flags & 1) != 0;
                    boolean dismissKeyguard = (flags & 2) != 0;
                    if (initiatedByUser || dismissKeyguard) {
                        if (KeyguardHostView.this.mViewMediatorCallback.isScreenOn() && (bouncerVisible || dismissKeyguard)) {
                            if (!bouncerVisible) {
                                Log.i("KeyguardViewBase", "TrustAgent dismissed Keyguard.");
                            }
                            KeyguardHostView.this.dismiss(false, userId);
                            return;
                        }
                        KeyguardHostView.this.mViewMediatorCallback.playTrustedSound();
                    }
                }
            }
        };
        KeyguardUpdateMonitor.getInstance(context).registerCallback(this.mUpdateCallback);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mViewMediatorCallback != null) {
            this.mViewMediatorCallback.keyguardDoneDrawing();
        }
    }

    public void setOnDismissAction(OnDismissAction action, Runnable cancelAction) {
        if (this.mCancelAction != null) {
            this.mCancelAction.run();
            this.mCancelAction = null;
        }
        this.mDismissAction = action;
        this.mCancelAction = cancelAction;
    }

    public boolean hasDismissActions() {
        return (this.mDismissAction == null && this.mCancelAction == null) ? false : true;
    }

    public void cancelDismissAction() {
        setOnDismissAction(null, null);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        this.mSecurityContainer = (KeyguardSecurityContainer) findViewById(com.android.systemui.R.id.keyguard_security_container);
        this.mLockPatternUtils = new LockPatternUtils(this.mContext);
        this.mSecurityContainer.setLockPatternUtils(this.mLockPatternUtils);
        this.mSecurityContainer.setSecurityCallback(this);
        this.mSecurityContainer.showPrimarySecurityScreen(false);
    }

    public void showPrimarySecurityScreen() {
        if (DEBUG) {
            Log.d("KeyguardViewBase", "show()");
        }
        this.mSecurityContainer.showPrimarySecurityScreen(false);
    }

    public void showPromptReason(int reason) {
        this.mSecurityContainer.showPromptReason(reason);
    }

    public void showMessage(CharSequence message, int color) {
        this.mSecurityContainer.showMessage(message, color);
    }

    public void showErrorMessage(CharSequence message) {
        showMessage(message, Utils.getColorError(this.mContext));
    }

    public boolean dismiss(int targetUserId) {
        if (AntiTheftManager.isDismissable()) {
            return dismiss(false, targetUserId);
        }
        return false;
    }

    protected KeyguardSecurityContainer getSecurityContainer() {
        return this.mSecurityContainer;
    }

    @Override // com.android.keyguard.KeyguardSecurityContainer.SecurityCallback
    public boolean dismiss(boolean authenticated, int targetUserId) {
        return this.mSecurityContainer.showNextSecurityScreenOrFinish(authenticated, targetUserId);
    }

    @Override // com.android.keyguard.KeyguardSecurityContainer.SecurityCallback
    public void finish(boolean strongAuth, int targetUserId) {
        boolean deferKeyguardDone = false;
        if (this.mDismissAction != null) {
            deferKeyguardDone = this.mDismissAction.onDismiss();
            this.mDismissAction = null;
            this.mCancelAction = null;
        } else if (VoiceWakeupManagerProxy.getInstance().isDismissAndLaunchApp()) {
            if (DEBUG) {
                Log.d("KeyguardViewBase", "finish() - call VoiceWakeupManager.getInstance().onDismiss().");
            }
            VoiceWakeupManagerProxy.getInstance().onDismiss();
            deferKeyguardDone = false;
        }
        if (this.mViewMediatorCallback != null) {
            if (deferKeyguardDone) {
                this.mViewMediatorCallback.keyguardDonePending(strongAuth, targetUserId);
            } else {
                this.mViewMediatorCallback.keyguardDone(strongAuth, targetUserId);
            }
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityContainer.SecurityCallback
    public void reset() {
        this.mViewMediatorCallback.resetKeyguard();
    }

    public void resetSecurityContainer() {
        this.mSecurityContainer.reset();
    }

    @Override // com.android.keyguard.KeyguardSecurityContainer.SecurityCallback
    public void onSecurityModeChanged(KeyguardSecurityModel.SecurityMode securityMode, boolean needsInput) {
        if (this.mViewMediatorCallback != null) {
            this.mViewMediatorCallback.setNeedsInput(needsInput);
        }
    }

    public CharSequence getAccessibilityTitleForCurrentMode() {
        return this.mSecurityContainer.getTitle();
    }

    @Override // com.android.keyguard.KeyguardSecurityContainer.SecurityCallback
    public void userActivity() {
        if (this.mViewMediatorCallback != null) {
            this.mViewMediatorCallback.userActivity();
        }
    }

    public void onPause() {
        if (DEBUG) {
            Log.d("KeyguardViewBase", String.format("screen off, instance %s at %s", Integer.toHexString(hashCode()), Long.valueOf(SystemClock.uptimeMillis())));
        }
        this.mSecurityContainer.showPrimarySecurityScreen(true);
        this.mSecurityContainer.onPause();
        clearFocus();
    }

    public void onResume() {
        if (DEBUG) {
            Log.d("KeyguardViewBase", "screen on, instance " + Integer.toHexString(hashCode()));
        }
        this.mSecurityContainer.onResume(1);
        requestFocus();
    }

    public void startAppearAnimation() {
        this.mSecurityContainer.startAppearAnimation();
    }

    public void startDisappearAnimation(Runnable finishRunnable) {
        if (!this.mSecurityContainer.startDisappearAnimation(finishRunnable) && finishRunnable != null) {
            finishRunnable.run();
        }
    }

    public void cleanUp() {
        getSecurityContainer().onPause();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (interceptMediaKey(event)) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:13:0x0026  */
    /* JADX WARN: Code duplicated, block: B:15:0x002a  */
    /*  JADX ERROR: UnsupportedOperationException in pass: RegionMakerVisitor
        java.lang.UnsupportedOperationException
        	at java.base/java.util.Collections$UnmodifiableCollection.add(Collections.java:1068)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker$1.leaveRegion(SwitchRegionMaker.java:419)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:91)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:31)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.insertBreaksForCase(SwitchRegionMaker.java:399)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.insertBreaks(SwitchRegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.PostProcessRegions.leaveRegion(PostProcessRegions.java:31)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:91)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.PostProcessRegions.process(PostProcessRegions.java:21)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:31)
        */
    public boolean interceptMediaKey(android.view.KeyEvent r8) {
        /*
            r7 = this;
            int r0 = r8.getKeyCode()
            int r1 = r8.getAction()
            r2 = 0
            r3 = 222(0xde, float:3.11E-43)
            r4 = 130(0x82, float:1.82E-43)
            r5 = 79
            r6 = 1
            if (r1 != 0) goto L4a
            if (r0 == r5) goto L46
            if (r0 == r4) goto L46
            r1 = 164(0xa4, float:2.3E-43)
            if (r0 == r1) goto L45
            if (r0 == r3) goto L46
            switch(r0) {
                case 24: goto L45;
                case 25: goto L45;
                default: goto L1f;
            }
        L1f:
            switch(r0) {
                case 85: goto L26;
                case 86: goto L46;
                case 87: goto L46;
                case 88: goto L46;
                case 89: goto L46;
                case 90: goto L46;
                case 91: goto L46;
                default: goto L22;
            }
        L22:
            switch(r0) {
                case 126: goto L26;
                case 127: goto L26;
                default: goto L25;
            }
        L25:
            goto L61
        L26:
            android.telephony.TelephonyManager r1 = r7.mTelephonyManager
            if (r1 != 0) goto L38
            android.content.Context r1 = r7.getContext()
            java.lang.String r2 = "phone"
            java.lang.Object r1 = r1.getSystemService(r2)
            android.telephony.TelephonyManager r1 = (android.telephony.TelephonyManager) r1
            r7.mTelephonyManager = r1
        L38:
            android.telephony.TelephonyManager r1 = r7.mTelephonyManager
            if (r1 == 0) goto L46
            android.telephony.TelephonyManager r1 = r7.mTelephonyManager
            int r1 = r1.getCallState()
            if (r1 == 0) goto L46
            return r6
        L45:
            return r2
        L46:
            r7.handleMediaKeyEvent(r8)
            return r6
        L4a:
            int r1 = r8.getAction()
            if (r1 != r6) goto L61
            if (r0 == r5) goto L5d
            if (r0 == r4) goto L5d
            if (r0 == r3) goto L5d
            switch(r0) {
                case 85: goto L5d;
                case 86: goto L5d;
                case 87: goto L5d;
                case 88: goto L5d;
                case 89: goto L5d;
                case 90: goto L5d;
                case 91: goto L5d;
                default: goto L59;
            }
        L59:
            switch(r0) {
                case 126: goto L5d;
                case 127: goto L5d;
                default: goto L5c;
            }
        L5c:
            goto L61
        L5d:
            r7.handleMediaKeyEvent(r8)
            return r6
        L61:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardHostView.interceptMediaKey(android.view.KeyEvent):boolean");
    }

    private void handleMediaKeyEvent(KeyEvent keyEvent) {
        synchronized (this) {
            if (this.mAudioManager == null) {
                this.mAudioManager = (AudioManager) getContext().getSystemService("audio");
            }
        }
        this.mAudioManager.dispatchMediaKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchSystemUiVisibilityChanged(int visibility) {
        super.dispatchSystemUiVisibilityChanged(visibility);
        if (!(this.mContext instanceof Activity)) {
            setSystemUiVisibility(4194304);
        }
    }

    public boolean shouldEnableMenuKey() {
        Resources res = getResources();
        boolean configDisabled = res.getBoolean(com.android.systemui.R.bool.config_disableMenuKeyInLockScreen);
        boolean isTestHarness = ActivityManager.isRunningInTestHarness();
        boolean fileOverride = new File("/data/local/enable_menu_key").exists();
        return !configDisabled || isTestHarness || fileOverride;
    }

    public void setViewMediatorCallback(ViewMediatorCallback viewMediatorCallback) {
        this.mViewMediatorCallback = viewMediatorCallback;
        this.mViewMediatorCallback.setNeedsInput(this.mSecurityContainer.needsInput());
    }

    public void setLockPatternUtils(LockPatternUtils utils) {
        this.mLockPatternUtils = utils;
        this.mSecurityContainer.setLockPatternUtils(utils);
    }

    public KeyguardSecurityModel.SecurityMode getSecurityMode() {
        return this.mSecurityContainer.getSecurityMode();
    }

    public KeyguardSecurityModel.SecurityMode getCurrentSecurityMode() {
        return this.mSecurityContainer.getCurrentSecurityMode();
    }

    @Override // com.android.keyguard.KeyguardSecurityContainer.SecurityCallback
    public void updateNavbarStatus() {
        this.mViewMediatorCallback.updateNavbarStatus();
    }
}
