package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Handler;
import android.os.UserManager;
import android.util.Log;
import android.util.MathUtils;
import android.util.Slog;
import android.util.StatsLog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardHostView;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.DejankUtils;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingManager;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.mediatek.keyguard.PowerOffAlarm.PowerOffAlarmManager;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardBouncer {
    private int mBouncerPromptReason;
    protected final ViewMediatorCallback mCallback;
    protected final ViewGroup mContainer;
    protected final Context mContext;
    private final DismissCallbackRegistry mDismissCallbackRegistry;
    private final BouncerExpansionCallback mExpansionCallback;
    private final FalsingManager mFalsingManager;
    private final Handler mHandler;
    private boolean mIsAnimatingAway;
    private boolean mIsScrimmed;
    protected KeyguardHostView mKeyguardView;
    protected final LockPatternUtils mLockPatternUtils;
    protected ViewGroup mRoot;
    public KeyguardSecurityModel mSecurityModel;
    private boolean mShowingSoon;
    private int mStatusBarHeight;
    private final boolean DEBUG = true;
    private final KeyguardUpdateMonitorCallback mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardBouncer.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onStrongAuthStateChanged(int userId) {
            KeyguardBouncer.this.mBouncerPromptReason = KeyguardBouncer.this.mCallback.getBouncerPromptReason();
        }
    };
    private final Runnable mRemoveViewRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$iQsniWdIxLGqyYwRi09kQ-Ah02M
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.removeView();
        }
    };
    private final Runnable mResetRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$KeyguardBouncer$Y9Hvfk0n3yPK2FQ39O1Z5j49gj0
        @Override // java.lang.Runnable
        public final void run() {
            KeyguardBouncer.lambda$new$0(this.f$0);
        }
    };
    private float mExpansion = 1.0f;
    private final Runnable mShowRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardBouncer.2
        @Override // java.lang.Runnable
        public void run() {
            KeyguardBouncer.this.mRoot.setVisibility(0);
            KeyguardBouncer.this.showPromptReason(KeyguardBouncer.this.mBouncerPromptReason);
            CharSequence customMessage = KeyguardBouncer.this.mCallback.consumeCustomMessage();
            if (customMessage != null) {
                KeyguardBouncer.this.mKeyguardView.showErrorMessage(customMessage);
            }
            if (KeyguardBouncer.this.mKeyguardView.getHeight() != 0 && KeyguardBouncer.this.mKeyguardView.getHeight() != KeyguardBouncer.this.mStatusBarHeight) {
                KeyguardBouncer.this.mKeyguardView.startAppearAnimation();
            } else {
                KeyguardBouncer.this.mKeyguardView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.phone.KeyguardBouncer.2.1
                    @Override // android.view.ViewTreeObserver.OnPreDrawListener
                    public boolean onPreDraw() {
                        KeyguardBouncer.this.mKeyguardView.getViewTreeObserver().removeOnPreDrawListener(this);
                        KeyguardBouncer.this.mKeyguardView.startAppearAnimation();
                        return true;
                    }
                });
                KeyguardBouncer.this.mKeyguardView.requestLayout();
            }
            KeyguardBouncer.this.mShowingSoon = false;
            if (KeyguardBouncer.this.mExpansion == 0.0f) {
                KeyguardBouncer.this.mKeyguardView.onResume();
            }
            StatsLog.write(63, 2);
        }
    };

    public interface BouncerExpansionCallback {
        void onFullyHidden();

        void onFullyShown();
    }

    public static /* synthetic */ void lambda$new$0(KeyguardBouncer keyguardBouncer) {
        if (keyguardBouncer.mKeyguardView != null) {
            keyguardBouncer.mKeyguardView.resetSecurityContainer();
        }
    }

    public KeyguardBouncer(Context context, ViewMediatorCallback callback, LockPatternUtils lockPatternUtils, ViewGroup container, DismissCallbackRegistry dismissCallbackRegistry, FalsingManager falsingManager, BouncerExpansionCallback expansionCallback) {
        this.mContext = context;
        this.mCallback = callback;
        this.mLockPatternUtils = lockPatternUtils;
        this.mContainer = container;
        KeyguardUpdateMonitor.getInstance(this.mContext).registerCallback(this.mUpdateMonitorCallback);
        this.mFalsingManager = falsingManager;
        this.mDismissCallbackRegistry = dismissCallbackRegistry;
        this.mExpansionCallback = expansionCallback;
        this.mHandler = new Handler();
        this.mSecurityModel = new KeyguardSecurityModel(this.mContext);
    }

    public void show(boolean resetSecuritySelection) {
        show(resetSecuritySelection, false, true);
    }

    public void show(boolean resetSecuritySelection, boolean authenticated, boolean isScrimmed) {
        int keyguardUserId = KeyguardUpdateMonitor.getCurrentUser();
        if (keyguardUserId == 0 && UserManager.isSplitSystemUser()) {
            return;
        }
        if (PowerOffAlarmManager.isAlarmBoot()) {
            Slog.d("KeyguardBouncer", "show() - this is alarm boot, just re-inflate.");
            if (this.mKeyguardView != null && this.mRoot != null) {
                Slog.d("KeyguardBouncer", "show() - before re-inflate, we should pause current view.");
                this.mKeyguardView.onPause();
            }
            inflateView();
        } else {
            ensureView();
        }
        if (isScrimmed) {
            setExpansion(0.0f);
        }
        this.mIsScrimmed = isScrimmed;
        if (resetSecuritySelection) {
            this.mKeyguardView.showPrimarySecurityScreen();
        }
        if (this.mRoot.getVisibility() == 0 || this.mShowingSoon) {
            return;
        }
        int activeUserId = KeyguardUpdateMonitor.getCurrentUser();
        boolean allowDismissKeyguard = false;
        boolean isSystemUser = UserManager.isSplitSystemUser() && activeUserId == 0;
        if (!isSystemUser && activeUserId == keyguardUserId) {
            allowDismissKeyguard = true;
        }
        if (allowDismissKeyguard && this.mKeyguardView.dismiss(activeUserId)) {
            return;
        }
        if (!allowDismissKeyguard) {
            Slog.w("KeyguardBouncer", "User can't dismiss keyguard: " + activeUserId + " != " + keyguardUserId);
        }
        if (!this.mKeyguardView.dismiss(authenticated, activeUserId)) {
            Slog.d("KeyguardBouncer", "show() - try to dismiss \"Bouncer\" directly.");
            this.mShowingSoon = true;
            DejankUtils.removeCallbacks(this.mResetRunnable);
            DejankUtils.postAfterTraversal(this.mShowRunnable);
            this.mCallback.onBouncerVisiblityChanged(true);
        }
    }

    public boolean isShowingScrimmed() {
        return isShowing() && this.mIsScrimmed;
    }

    private void onFullyShown() {
        this.mFalsingManager.onBouncerShown();
        if (this.mKeyguardView == null) {
            Log.e("KeyguardBouncer", "onFullyShown when view was null");
        } else {
            this.mKeyguardView.onResume();
        }
    }

    private void onFullyHidden() {
        if (!this.mShowingSoon) {
            cancelShowRunnable();
            if (this.mRoot != null) {
                this.mRoot.setVisibility(4);
            }
            this.mFalsingManager.onBouncerHidden();
            DejankUtils.postAfterTraversal(this.mResetRunnable);
        }
    }

    public void showPromptReason(int reason) {
        if (this.mKeyguardView != null) {
            this.mKeyguardView.showPromptReason(reason);
        } else {
            Log.w("KeyguardBouncer", "Trying to show prompt reason on empty bouncer");
        }
    }

    public void showMessage(String message, int color) {
        if (this.mKeyguardView != null) {
            this.mKeyguardView.showMessage(message, color);
        } else {
            Log.w("KeyguardBouncer", "Trying to show message on empty bouncer");
        }
    }

    private void cancelShowRunnable() {
        DejankUtils.removeCallbacks(this.mShowRunnable);
        this.mShowingSoon = false;
    }

    public void showWithDismissAction(KeyguardHostView.OnDismissAction r, Runnable cancelAction) {
        ensureView();
        this.mKeyguardView.setOnDismissAction(r, cancelAction);
        show(false);
    }

    public void hide(boolean destroyView) {
        if (isShowing()) {
            StatsLog.write(63, 1);
            this.mDismissCallbackRegistry.notifyDismissCancelled();
        }
        this.mFalsingManager.onBouncerHidden();
        this.mCallback.onBouncerVisiblityChanged(false);
        cancelShowRunnable();
        if (this.mKeyguardView != null) {
            this.mKeyguardView.cancelDismissAction();
            this.mKeyguardView.cleanUp();
        }
        this.mIsAnimatingAway = false;
        if (this.mRoot != null) {
            this.mRoot.setVisibility(4);
            if (destroyView) {
                this.mHandler.postDelayed(this.mRemoveViewRunnable, 50L);
            }
        }
    }

    public void startPreHideAnimation(Runnable runnable) {
        this.mIsAnimatingAway = true;
        if (this.mKeyguardView != null) {
            this.mKeyguardView.startDisappearAnimation(runnable);
        } else if (runnable != null) {
            runnable.run();
        }
    }

    public void onScreenTurnedOff() {
        if (this.mKeyguardView != null && this.mRoot != null && this.mRoot.getVisibility() == 0) {
            this.mKeyguardView.onPause();
        }
    }

    public boolean isShowing() {
        return (this.mShowingSoon || (this.mRoot != null && this.mRoot.getVisibility() == 0)) && this.mExpansion == 0.0f && !isAnimatingAway();
    }

    public boolean isAnimatingAway() {
        return this.mIsAnimatingAway;
    }

    public void prepare() {
        boolean wasInitialized = this.mRoot != null;
        ensureView();
        if (wasInitialized) {
            this.mKeyguardView.showPrimarySecurityScreen();
        }
        this.mBouncerPromptReason = this.mCallback.getBouncerPromptReason();
    }

    public void setExpansion(float fraction) {
        float oldExpansion = this.mExpansion;
        this.mExpansion = fraction;
        if (this.mKeyguardView != null && !this.mIsAnimatingAway) {
            float alpha = MathUtils.map(0.95f, 1.0f, 1.0f, 0.0f, fraction);
            this.mKeyguardView.setAlpha(MathUtils.constrain(alpha, 0.0f, 1.0f));
            this.mKeyguardView.setTranslationY(this.mKeyguardView.getHeight() * fraction);
        }
        if (fraction == 0.0f && oldExpansion != 0.0f) {
            onFullyShown();
            this.mExpansionCallback.onFullyShown();
        } else if (fraction == 1.0f && oldExpansion != 1.0f) {
            onFullyHidden();
            this.mExpansionCallback.onFullyHidden();
        }
    }

    public boolean willDismissWithAction() {
        return this.mKeyguardView != null && this.mKeyguardView.hasDismissActions();
    }

    public int getTop() {
        if (this.mKeyguardView == null) {
            return 0;
        }
        int top = this.mKeyguardView.getTop();
        if (this.mKeyguardView.getCurrentSecurityMode() == KeyguardSecurityModel.SecurityMode.Password) {
            View messageArea = this.mKeyguardView.findViewById(R.id.keyguard_message_area);
            return top + messageArea.getTop();
        }
        return top;
    }

    protected void ensureView() {
        boolean forceRemoval = this.mHandler.hasCallbacks(this.mRemoveViewRunnable);
        if (this.mRoot == null || forceRemoval) {
            inflateView();
        }
    }

    protected void inflateView() {
        removeView();
        this.mHandler.removeCallbacks(this.mRemoveViewRunnable);
        this.mRoot = (ViewGroup) LayoutInflater.from(this.mContext).inflate(R.layout.keyguard_bouncer, (ViewGroup) null);
        this.mKeyguardView = (KeyguardHostView) this.mRoot.findViewById(R.id.keyguard_host_view);
        this.mKeyguardView.setLockPatternUtils(this.mLockPatternUtils);
        this.mKeyguardView.setViewMediatorCallback(this.mCallback);
        this.mContainer.addView(this.mRoot, this.mContainer.getChildCount());
        this.mStatusBarHeight = this.mRoot.getResources().getDimensionPixelOffset(R.dimen.status_bar_height);
        this.mRoot.setVisibility(4);
        this.mRoot.setAccessibilityPaneTitle(this.mKeyguardView.getAccessibilityTitleForCurrentMode());
        WindowInsets rootInsets = this.mRoot.getRootWindowInsets();
        if (rootInsets != null) {
            this.mRoot.dispatchApplyWindowInsets(rootInsets);
        }
    }

    protected void removeView() {
        if (this.mRoot != null && this.mRoot.getParent() == this.mContainer) {
            this.mContainer.removeView(this.mRoot);
            this.mRoot = null;
        }
    }

    public boolean needsFullscreenBouncer() {
        ensureView();
        KeyguardSecurityModel.SecurityMode mode = this.mSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser());
        return mode == KeyguardSecurityModel.SecurityMode.SimPinPukMe1 || mode == KeyguardSecurityModel.SecurityMode.SimPinPukMe2 || mode == KeyguardSecurityModel.SecurityMode.SimPinPukMe3 || mode == KeyguardSecurityModel.SecurityMode.SimPinPukMe4 || mode == KeyguardSecurityModel.SecurityMode.AntiTheft || mode == KeyguardSecurityModel.SecurityMode.AlarmBoot;
    }

    public boolean isFullscreenBouncer() {
        if (this.mKeyguardView == null) {
            return false;
        }
        KeyguardSecurityModel.SecurityMode mode = this.mKeyguardView.getCurrentSecurityMode();
        return mode == KeyguardSecurityModel.SecurityMode.SimPinPukMe1 || mode == KeyguardSecurityModel.SecurityMode.SimPinPukMe2 || mode == KeyguardSecurityModel.SecurityMode.SimPinPukMe3 || mode == KeyguardSecurityModel.SecurityMode.SimPinPukMe4 || mode == KeyguardSecurityModel.SecurityMode.AntiTheft || mode == KeyguardSecurityModel.SecurityMode.AlarmBoot;
    }

    public boolean isSecure() {
        return this.mKeyguardView == null || this.mKeyguardView.getSecurityMode() != KeyguardSecurityModel.SecurityMode.None;
    }

    public boolean shouldDismissOnMenuPressed() {
        return this.mKeyguardView.shouldEnableMenuKey();
    }

    public boolean interceptMediaKey(KeyEvent event) {
        ensureView();
        return this.mKeyguardView.interceptMediaKey(event);
    }

    public void notifyKeyguardAuthenticated(boolean strongAuth) {
        ensureView();
        this.mKeyguardView.finish(strongAuth, KeyguardUpdateMonitor.getCurrentUser());
    }

    public void dump(PrintWriter pw) {
        pw.println("KeyguardBouncer");
        pw.println("  isShowing(): " + isShowing());
        pw.println("  mStatusBarHeight: " + this.mStatusBarHeight);
        pw.println("  mExpansion: " + this.mExpansion);
        pw.println("  mKeyguardView; " + this.mKeyguardView);
        pw.println("  mShowingSoon: " + this.mKeyguardView);
        pw.println("  mBouncerPromptReason: " + this.mBouncerPromptReason);
        pw.println("  mIsAnimatingAway: " + this.mIsAnimatingAway);
    }
}
