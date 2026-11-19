package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.StatsLog;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowManagerGlobal;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardHostView;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.SystemUIFactory;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.statusbar.RemoteInputController;
import com.mediatek.keyguard.VoiceWakeup.VoiceWakeupManagerProxy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarKeyguardViewManager implements RemoteInputController.Callback {
    private static String TAG = "StatusBarKeyguardViewManager";
    private KeyguardHostView.OnDismissAction mAfterKeyguardGoneAction;
    protected KeyguardBouncer mBouncer;
    private ViewGroup mContainer;
    protected final Context mContext;
    private boolean mDozing;
    private FingerprintUnlockController mFingerprintUnlockController;
    private boolean mGoingToSleepVisibleNotOccluded;
    private boolean mLastBouncerDismissible;
    private boolean mLastBouncerShowing;
    private boolean mLastDozing;
    private int mLastFpMode;
    protected boolean mLastOccluded;
    protected boolean mLastRemoteInputActive;
    protected boolean mLastShowing;
    protected LockPatternUtils mLockPatternUtils;
    private NotificationPanelView mNotificationPanelView;
    protected boolean mOccluded;
    private DismissWithActionRequest mPendingWakeupAction;
    protected boolean mRemoteInputActive;
    protected boolean mShowing;
    protected StatusBar mStatusBar;
    protected ViewMediatorCallback mViewMediatorCallback;
    private final boolean DEBUG = true;
    private final KeyguardBouncer.BouncerExpansionCallback mExpansionCallback = new KeyguardBouncer.BouncerExpansionCallback() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.1
        @Override // com.android.systemui.statusbar.phone.KeyguardBouncer.BouncerExpansionCallback
        public void onFullyShown() {
            StatusBarKeyguardViewManager.this.updateStates();
        }

        @Override // com.android.systemui.statusbar.phone.KeyguardBouncer.BouncerExpansionCallback
        public void onFullyHidden() {
            StatusBarKeyguardViewManager.this.updateStates();
        }
    };
    protected boolean mFirstUpdate = true;
    private final ArrayList<Runnable> mAfterKeyguardGoneRunnables = new ArrayList<>();
    private final KeyguardUpdateMonitorCallback mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.2
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onEmergencyCallAction() {
            if (StatusBarKeyguardViewManager.this.mOccluded) {
                StatusBarKeyguardViewManager.this.reset(true);
            }
        }
    };
    private Runnable mMakeNavigationBarVisibleRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.6
        @Override // java.lang.Runnable
        public void run() {
            Log.d(StatusBarKeyguardViewManager.TAG, "mMakeNavigationBarVisibleRunnable - set nav bar VISIBLE.");
            StatusBarKeyguardViewManager.this.mStatusBar.getNavigationBarView().getRootView().setVisibility(0);
        }
    };
    private final StatusBarWindowManager mStatusBarWindowManager = (StatusBarWindowManager) Dependency.get(StatusBarWindowManager.class);

    public StatusBarKeyguardViewManager(Context context, ViewMediatorCallback callback, LockPatternUtils lockPatternUtils) {
        this.mContext = context;
        this.mViewMediatorCallback = callback;
        this.mLockPatternUtils = lockPatternUtils;
        KeyguardUpdateMonitor.getInstance(context).registerCallback(this.mUpdateMonitorCallback);
    }

    public void registerStatusBar(StatusBar statusBar, ViewGroup container, NotificationPanelView notificationPanelView, FingerprintUnlockController fingerprintUnlockController, DismissCallbackRegistry dismissCallbackRegistry) {
        this.mStatusBar = statusBar;
        this.mContainer = container;
        this.mFingerprintUnlockController = fingerprintUnlockController;
        this.mBouncer = SystemUIFactory.getInstance().createKeyguardBouncer(this.mContext, this.mViewMediatorCallback, this.mLockPatternUtils, container, dismissCallbackRegistry, this.mExpansionCallback);
        this.mContainer.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBarKeyguardViewManager$la5jBJIHWW-wWro_RwMU-0UMDCw
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                this.f$0.onContainerLayout(view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
        this.mNotificationPanelView = notificationPanelView;
        notificationPanelView.setExpansionListener(new BiConsumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$RwixcXxEpB1TMXbyMOl_aGzysd0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.onPanelExpansionChanged(((Float) obj).floatValue(), ((Boolean) obj2).booleanValue());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onContainerLayout(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        this.mNotificationPanelView.setBouncerTop(this.mBouncer.getTop());
    }

    @VisibleForTesting
    void onPanelExpansionChanged(float expansion, boolean tracking) {
        if (this.mNotificationPanelView.isUnlockHintRunning()) {
            this.mBouncer.setExpansion(1.0f);
            return;
        }
        if (this.mOccluded || this.mBouncer.willDismissWithAction() || this.mBouncer.isShowingScrimmed() || this.mStatusBar.isFullScreenUserSwitcherState()) {
            this.mBouncer.setExpansion(0.0f);
            return;
        }
        if (this.mShowing && !this.mDozing) {
            if (!isWakeAndUnlocking()) {
                this.mBouncer.setExpansion(expansion);
            }
            if (expansion != 1.0f && tracking && this.mStatusBar.isKeyguardCurrentlySecure() && !this.mBouncer.isShowing() && !this.mBouncer.isAnimatingAway()) {
                this.mBouncer.show(false, false, false);
            }
        }
    }

    public void show(Bundle options) {
        Log.d(TAG, "show() is called.");
        this.mShowing = true;
        this.mStatusBarWindowManager.setKeyguardShowing(true);
        reset(true);
        StatsLog.write(62, 2);
    }

    protected void showBouncerOrKeyguard(boolean hideBouncerWhenShowing) {
        Log.d(TAG, "showBouncerOrKeyguard() is called.");
        if (this.mBouncer.needsFullscreenBouncer() && !this.mDozing) {
            Log.d(TAG, "needsFullscreenBouncer() is true, show \"Bouncer\" view directly.");
            this.mStatusBar.hideKeyguard();
            this.mBouncer.show(true);
        } else {
            Log.d(TAG, "needsFullscreenBouncer() is false,show \"Notification Keyguard\" view.");
            this.mStatusBar.showKeyguard();
            if (hideBouncerWhenShowing) {
                hideBouncer(shouldDestroyViewOnReset());
                this.mBouncer.prepare();
            }
        }
        updateStates();
    }

    protected boolean shouldDestroyViewOnReset() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideBouncer(boolean destroyView) {
        this.mBouncer.hide(destroyView);
        cancelPendingWakeupAction();
    }

    private void showBouncer() {
        showBouncer(false, false);
    }

    public void showBouncer(boolean scrimmed) {
        showBouncer(false, scrimmed);
    }

    private void showBouncer(boolean authenticated, boolean scrimmed) {
        if (this.mShowing && !this.mBouncer.isShowing()) {
            this.mBouncer.show(false, authenticated, scrimmed);
        }
        updateStates();
    }

    public void dismissWithAction(KeyguardHostView.OnDismissAction r, Runnable cancelAction, boolean afterKeyguardGone) {
        dismissWithAction(r, cancelAction, afterKeyguardGone, null);
    }

    public void dismissWithAction(KeyguardHostView.OnDismissAction r, Runnable cancelAction, boolean afterKeyguardGone, String message) {
        if (this.mShowing) {
            cancelPendingWakeupAction();
            if (this.mDozing && !isWakeAndUnlocking()) {
                this.mPendingWakeupAction = new DismissWithActionRequest(r, cancelAction, afterKeyguardGone, message);
                return;
            } else if (!afterKeyguardGone) {
                Log.d(TAG, "dismissWithAction() - afterKeyguardGone = false,call showWithDismissAction");
                this.mBouncer.showWithDismissAction(r, cancelAction);
            } else {
                Log.d(TAG, "dismissWithAction() - afterKeyguardGone = true, call bouncer.show()");
                this.mAfterKeyguardGoneAction = r;
                this.mBouncer.show(false);
            }
        }
        updateStates();
    }

    private boolean isWakeAndUnlocking() {
        int mode = this.mFingerprintUnlockController.getMode();
        return mode == 1 || mode == 2;
    }

    public void addAfterKeyguardGoneRunnable(Runnable runnable) {
        this.mAfterKeyguardGoneRunnables.add(runnable);
    }

    public void reset(boolean hideBouncerWhenShowing) {
        Log.d(TAG, "reset() is called, mShowing = " + this.mShowing + " ,mOccluded = " + this.mOccluded + "hideBouncerWhenShowing = " + hideBouncerWhenShowing);
        if (this.mShowing) {
            if (this.mOccluded && !this.mDozing) {
                this.mStatusBar.hideKeyguard();
                if (hideBouncerWhenShowing || this.mBouncer.needsFullscreenBouncer()) {
                    hideBouncer(false);
                }
            } else {
                showBouncerOrKeyguard(hideBouncerWhenShowing);
            }
            KeyguardUpdateMonitor.getInstance(this.mContext).sendKeyguardReset();
            updateStates();
        }
    }

    public boolean isGoingToSleepVisibleNotOccluded() {
        return this.mGoingToSleepVisibleNotOccluded;
    }

    public void onStartedGoingToSleep() {
        this.mGoingToSleepVisibleNotOccluded = isShowing() && !isOccluded();
    }

    public void onFinishedGoingToSleep() {
        this.mGoingToSleepVisibleNotOccluded = false;
        this.mBouncer.onScreenTurnedOff();
    }

    public void onStartedWakingUp() {
    }

    public void onScreenTurningOn() {
    }

    public void onScreenTurnedOn() {
    }

    @Override // com.android.systemui.statusbar.RemoteInputController.Callback
    public void onRemoteInputActive(boolean active) {
        this.mRemoteInputActive = active;
        updateStates();
    }

    public void setDozing(boolean dozing) {
        if (this.mDozing != dozing) {
            this.mDozing = dozing;
            if (dozing || this.mBouncer.needsFullscreenBouncer() || this.mOccluded) {
                reset(dozing);
            }
            updateStates();
            if (!dozing) {
                launchPendingWakeupAction();
            }
        }
    }

    public void onScreenTurnedOff() {
    }

    public void notifyDeviceWakeUpRequested() {
    }

    public void setNeedsInput(boolean needsInput) {
        Log.d(TAG, "setNeedsInput() - needsInput = " + needsInput);
        this.mStatusBarWindowManager.setKeyguardNeedsInput(needsInput);
    }

    public boolean isUnlockWithWallpaper() {
        return this.mStatusBarWindowManager.isShowingWallpaper();
    }

    public void setOccluded(boolean occluded, boolean animate) {
        this.mStatusBar.setOccluded(occluded);
        if (occluded && !this.mOccluded && this.mShowing) {
            StatsLog.write(62, 3);
            if (this.mStatusBar.isInLaunchTransition()) {
                this.mOccluded = true;
                this.mStatusBar.fadeKeyguardAfterLaunchTransition(null, new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.3
                    @Override // java.lang.Runnable
                    public void run() {
                        if (StatusBarKeyguardViewManager.this.mOccluded) {
                            StatusBarKeyguardViewManager.this.mStatusBarWindowManager.setKeyguardOccluded(StatusBarKeyguardViewManager.this.mOccluded);
                            StatusBarKeyguardViewManager.this.reset(true);
                        } else {
                            Log.d(StatusBarKeyguardViewManager.TAG, "setOccluded.run() - mOccluded was set to false");
                        }
                    }
                });
                return;
            }
        } else if (!occluded && this.mOccluded && this.mShowing) {
            StatsLog.write(62, 2);
        }
        boolean isOccluding = !this.mOccluded && occluded;
        this.mOccluded = occluded;
        Log.d(TAG, "setOccluded() - setKeyguardOccluded(" + occluded + ")");
        if (this.mShowing) {
            this.mStatusBar.updateMediaMetaData(false, animate && !occluded);
        }
        this.mStatusBarWindowManager.setKeyguardOccluded(occluded);
        if (!this.mDozing) {
            reset(isOccluding);
        }
        if (animate && !occluded && this.mShowing) {
            this.mStatusBar.animateKeyguardUnoccluding();
        }
    }

    public boolean isOccluded() {
        return this.mOccluded;
    }

    public void startPreHideAnimation(Runnable finishRunnable) {
        if (this.mBouncer.isShowing()) {
            this.mBouncer.startPreHideAnimation(finishRunnable);
            this.mNotificationPanelView.onBouncerPreHideAnimation();
        } else if (finishRunnable != null) {
            finishRunnable.run();
        }
    }

    public void hide(long startTime, long fadeoutDuration) {
        long fadeoutDuration2;
        this.mShowing = false;
        launchPendingWakeupAction();
        if (KeyguardUpdateMonitor.getInstance(this.mContext).needsSlowUnlockTransition()) {
            fadeoutDuration2 = 2000;
        } else {
            fadeoutDuration2 = fadeoutDuration;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        long delay = Math.max(0L, (startTime - 48) - uptimeMillis);
        if (this.mStatusBar.isInLaunchTransition()) {
            this.mStatusBar.fadeKeyguardAfterLaunchTransition(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.4
                @Override // java.lang.Runnable
                public void run() {
                    StatusBarKeyguardViewManager.this.mStatusBarWindowManager.setKeyguardShowing(false);
                    StatusBarKeyguardViewManager.this.mStatusBarWindowManager.setKeyguardFadingAway(true);
                    StatusBarKeyguardViewManager.this.hideBouncer(true);
                    StatusBarKeyguardViewManager.this.updateStates();
                }
            }, new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager.5
                @Override // java.lang.Runnable
                public void run() {
                    StatusBarKeyguardViewManager.this.mStatusBar.hideKeyguard();
                    StatusBarKeyguardViewManager.this.mStatusBarWindowManager.setKeyguardFadingAway(false);
                    StatusBarKeyguardViewManager.this.mViewMediatorCallback.keyguardGone();
                    StatusBarKeyguardViewManager.this.executeAfterKeyguardGoneAction();
                }
            });
        } else {
            executeAfterKeyguardGoneAction();
            boolean wakeUnlockPulsing = this.mFingerprintUnlockController.getMode() == 2;
            if (wakeUnlockPulsing) {
                delay = 0;
                fadeoutDuration2 = 240;
            }
            this.mStatusBar.setKeyguardFadingAway(startTime, delay, fadeoutDuration2);
            this.mFingerprintUnlockController.startKeyguardFadingAway();
            hideBouncer(true);
            if (wakeUnlockPulsing) {
                this.mStatusBar.fadeKeyguardWhilePulsing();
                wakeAndUnlockDejank();
            } else {
                boolean staying = this.mStatusBar.hideKeyguard();
                if (!staying) {
                    this.mStatusBarWindowManager.setKeyguardFadingAway(true);
                    wakeAndUnlockDejank();
                } else {
                    this.mStatusBar.finishKeyguardFadingAway();
                    this.mFingerprintUnlockController.finishKeyguardFadingAway();
                }
            }
            updateStates();
            this.mStatusBarWindowManager.setKeyguardShowing(false);
            this.mViewMediatorCallback.keyguardGone();
        }
        StatsLog.write(62, 1);
    }

    public void onDensityOrFontScaleChanged() {
        hideBouncer(true);
    }

    public void onThemeChanged() {
        hideBouncer(true);
        this.mBouncer.prepare();
    }

    public void onKeyguardFadedAway() {
        this.mContainer.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBarKeyguardViewManager$Qxwhwo1uUdyEeH0KZ8eQm9-dLJ8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mStatusBarWindowManager.setKeyguardFadingAway(false);
            }
        }, 100L);
        this.mStatusBar.finishKeyguardFadingAway();
        this.mFingerprintUnlockController.finishKeyguardFadingAway();
        WindowManagerGlobal.getInstance().trimMemory(20);
    }

    private void wakeAndUnlockDejank() {
        if (this.mFingerprintUnlockController.getMode() == 1 && LatencyTracker.isEnabled(this.mContext)) {
            DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$StatusBarKeyguardViewManager$MwYtqufbgyJNJ1l_l2mNmQjtUTg
                @Override // java.lang.Runnable
                public final void run() {
                    LatencyTracker.getInstance(this.f$0.mContext).onActionEnd(2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeAfterKeyguardGoneAction() {
        if (this.mAfterKeyguardGoneAction != null) {
            Log.d(TAG, "executeAfterKeyguardGoneAction() is called");
            this.mAfterKeyguardGoneAction.onDismiss();
            this.mAfterKeyguardGoneAction = null;
        }
        for (int i = 0; i < this.mAfterKeyguardGoneRunnables.size(); i++) {
            this.mAfterKeyguardGoneRunnables.get(i).run();
        }
        this.mAfterKeyguardGoneRunnables.clear();
    }

    public void dismissAndCollapse() {
        this.mStatusBar.executeRunnableDismissingKeyguard(null, null, true, false, true);
    }

    public void dismiss(boolean authenticated) {
        Log.d(TAG, "dismiss(authenticated = " + authenticated + ") is called.");
        if (VoiceWakeupManagerProxy.getInstance().isDismissAndLaunchApp()) {
            showBouncer(authenticated, false);
        } else {
            showBouncer();
        }
    }

    public boolean isSecure() {
        return this.mBouncer.isSecure();
    }

    public boolean isShowing() {
        return this.mShowing;
    }

    public boolean onBackPressed(boolean hideImmediately) {
        Log.d(TAG, "onBackPressed()");
        if (this.mBouncer.isShowing()) {
            this.mStatusBar.endAffordanceLaunch();
            reset(hideImmediately);
            this.mAfterKeyguardGoneAction = null;
            return true;
        }
        Log.d(TAG, "onBackPressed() - reset & return false");
        return false;
    }

    public boolean isBouncerShowing() {
        return this.mBouncer.isShowing();
    }

    public boolean isFullscreenBouncer() {
        return this.mBouncer.isFullscreenBouncer();
    }

    private long getNavBarShowDelay() {
        if (this.mStatusBar.isKeyguardFadingAway()) {
            return this.mStatusBar.getKeyguardFadingAwayDelay();
        }
        if (this.mBouncer.isShowing()) {
            return 320L;
        }
        return 0L;
    }

    public void updateStates() {
        int vis = this.mContainer.getSystemUiVisibility();
        boolean showing = this.mShowing;
        boolean occluded = this.mOccluded;
        boolean bouncerShowing = this.mBouncer.isShowing();
        boolean bouncerDismissible = !this.mBouncer.isFullscreenBouncer();
        boolean remoteInputActive = this.mRemoteInputActive;
        if ((bouncerDismissible || !showing || remoteInputActive) != (this.mLastBouncerDismissible || !this.mLastShowing || this.mLastRemoteInputActive) || this.mFirstUpdate) {
            if (bouncerDismissible || !showing || remoteInputActive) {
                this.mContainer.setSystemUiVisibility((-4194305) & vis);
            } else {
                this.mContainer.setSystemUiVisibility(4194304 | vis);
            }
        }
        boolean navBarVisible = isNavBarVisible();
        boolean lastNavBarVisible = getLastNavBarVisible();
        if (navBarVisible != lastNavBarVisible || this.mFirstUpdate) {
            Log.d(TAG, "updateStates() - showing = " + showing + ", mLastShowing = " + this.mLastShowing + "\nupdateStates() - occluded = " + occluded + "mLastOccluded = " + this.mLastOccluded + "\nupdateStates() - bouncerShowing = " + bouncerShowing + ", mLastBouncerShowing = " + this.mLastBouncerShowing + "\nupdateStates() - mFirstUpdate = " + this.mFirstUpdate);
            updateNavigationBarVisibility(navBarVisible);
        }
        if (bouncerShowing != this.mLastBouncerShowing || this.mFirstUpdate) {
            Log.d(TAG, "updateStates() - setBouncerShowing(" + bouncerShowing + ")");
            this.mStatusBarWindowManager.setBouncerShowing(bouncerShowing);
            this.mStatusBar.setBouncerShowing(bouncerShowing);
        }
        KeyguardUpdateMonitor updateMonitor = KeyguardUpdateMonitor.getInstance(this.mContext);
        if ((showing && !occluded) != (this.mLastShowing && !this.mLastOccluded) || this.mFirstUpdate) {
            updateMonitor.onKeyguardVisibilityChanged(showing && !occluded);
        }
        if (bouncerShowing != this.mLastBouncerShowing || this.mFirstUpdate) {
            updateMonitor.sendKeyguardBouncerChanged(bouncerShowing);
        }
        this.mFirstUpdate = false;
        this.mLastShowing = showing;
        this.mLastOccluded = occluded;
        this.mLastBouncerShowing = bouncerShowing;
        this.mLastBouncerDismissible = bouncerDismissible;
        this.mLastRemoteInputActive = remoteInputActive;
        this.mLastDozing = this.mDozing;
        this.mLastFpMode = this.mFingerprintUnlockController.getMode();
        this.mStatusBar.onKeyguardViewManagerStatesUpdated();
    }

    protected void updateNavigationBarVisibility(boolean navBarVisible) {
        if (this.mStatusBar.getNavigationBarView() != null) {
            if (navBarVisible) {
                long delay = getNavBarShowDelay();
                if (delay == 0) {
                    this.mMakeNavigationBarVisibleRunnable.run();
                    return;
                } else {
                    this.mContainer.postOnAnimationDelayed(this.mMakeNavigationBarVisibleRunnable, delay);
                    return;
                }
            }
            Log.d(TAG, "updateNavigationBarVisibility() - set nav bar GONE for showing notification keyguard.");
            this.mContainer.removeCallbacks(this.mMakeNavigationBarVisibleRunnable);
            this.mStatusBar.getNavigationBarView().getRootView().setVisibility(8);
        }
    }

    protected boolean isNavBarVisible() {
        int fpMode = this.mFingerprintUnlockController.getMode();
        boolean keyguardShowing = this.mShowing && !this.mOccluded;
        boolean hideWhileDozing = this.mDozing && fpMode != 2;
        return !(keyguardShowing || hideWhileDozing) || this.mBouncer.isShowing() || this.mRemoteInputActive;
    }

    protected boolean getLastNavBarVisible() {
        boolean keyguardShowing = this.mLastShowing && !this.mLastOccluded;
        boolean hideWhileDozing = this.mLastDozing && this.mLastFpMode != 2;
        return !(keyguardShowing || hideWhileDozing) || this.mLastBouncerShowing || this.mLastRemoteInputActive;
    }

    public boolean shouldDismissOnMenuPressed() {
        return this.mBouncer.shouldDismissOnMenuPressed();
    }

    public boolean interceptMediaKey(KeyEvent event) {
        return this.mBouncer.interceptMediaKey(event);
    }

    public void readyForKeyguardDone() {
        this.mViewMediatorCallback.readyForKeyguardDone();
    }

    public boolean shouldDisableWindowAnimationsForUnlock() {
        return this.mStatusBar.isInLaunchTransition();
    }

    public boolean isGoingToNotificationShade() {
        return this.mStatusBar.isGoingToNotificationShade();
    }

    public boolean isSecure(int userId) {
        return this.mBouncer.isSecure() || this.mLockPatternUtils.isSecure(userId);
    }

    public void keyguardGoingAway() {
        this.mStatusBar.keyguardGoingAway();
    }

    public void animateCollapsePanels(float speedUpFactor) {
        this.mStatusBar.animateCollapsePanels(0, true, false, speedUpFactor);
    }

    public void notifyKeyguardAuthenticated(boolean strongAuth) {
        this.mBouncer.notifyKeyguardAuthenticated(strongAuth);
    }

    public void showBouncerMessage(String message, int color) {
        this.mBouncer.showMessage(message, color);
    }

    public ViewRootImpl getViewRootImpl() {
        return this.mStatusBar.getStatusBarView().getViewRootImpl();
    }

    public void launchPendingWakeupAction() {
        DismissWithActionRequest request = this.mPendingWakeupAction;
        this.mPendingWakeupAction = null;
        if (request != null) {
            if (this.mShowing) {
                dismissWithAction(request.dismissAction, request.cancelAction, request.afterKeyguardGone, request.message);
            } else if (request.dismissAction != null) {
                request.dismissAction.onDismiss();
            }
        }
    }

    public void cancelPendingWakeupAction() {
        DismissWithActionRequest request = this.mPendingWakeupAction;
        this.mPendingWakeupAction = null;
        if (request != null && request.cancelAction != null) {
            request.cancelAction.run();
        }
    }

    public boolean willDismissWithAction() {
        return this.mBouncer.willDismissWithAction();
    }

    public boolean bouncerNeedsScrimming() {
        return this.mBouncer.isShowingScrimmed();
    }

    public void dump(PrintWriter pw) {
        pw.println("StatusBarKeyguardViewManager:");
        pw.println("  mShowing: " + this.mShowing);
        pw.println("  mOccluded: " + this.mOccluded);
        pw.println("  mRemoteInputActive: " + this.mRemoteInputActive);
        pw.println("  mDozing: " + this.mDozing);
        pw.println("  mGoingToSleepVisibleNotOccluded: " + this.mGoingToSleepVisibleNotOccluded);
        pw.println("  mAfterKeyguardGoneAction: " + this.mAfterKeyguardGoneAction);
        pw.println("  mAfterKeyguardGoneRunnables: " + this.mAfterKeyguardGoneRunnables);
        pw.println("  mPendingWakeupAction: " + this.mPendingWakeupAction);
        if (this.mBouncer != null) {
            this.mBouncer.dump(pw);
        }
    }

    private static class DismissWithActionRequest {
        final boolean afterKeyguardGone;
        final Runnable cancelAction;
        final KeyguardHostView.OnDismissAction dismissAction;
        final String message;

        DismissWithActionRequest(KeyguardHostView.OnDismissAction dismissAction, Runnable cancelAction, boolean afterKeyguardGone, String message) {
            this.dismissAction = dismissAction;
            this.cancelAction = cancelAction;
            this.afterKeyguardGone = afterKeyguardGone;
            this.message = message;
        }
    }
}
