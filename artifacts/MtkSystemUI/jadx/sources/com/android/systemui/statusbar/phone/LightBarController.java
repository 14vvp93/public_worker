package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class LightBarController implements Dumpable, BatteryController.BatteryStateChangeCallback {
    private final Color mDarkModeColor;
    private boolean mDockedLight;
    private int mDockedStackVisibility;
    private FingerprintUnlockController mFingerprintUnlockController;
    private boolean mForceDarkForScrim;
    private boolean mFullscreenLight;
    private int mFullscreenStackVisibility;
    private boolean mHasLightNavigationBar;
    private int mLastNavigationBarMode;
    private int mLastStatusBarMode;
    private LightBarTransitionsController mNavigationBarController;
    private boolean mNavigationLight;
    private boolean mQsCustomizing;
    private int mSystemUiVisibility;
    private final Rect mLastFullscreenBounds = new Rect();
    private final Rect mLastDockedBounds = new Rect();
    private final DarkIconDispatcher mStatusBarIconController = (DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class);
    private final BatteryController mBatteryController = (BatteryController) Dependency.get(BatteryController.class);

    public LightBarController(Context ctx) {
        this.mDarkModeColor = Color.valueOf(ctx.getColor(R.color.dark_mode_icon_color_single_tone));
        this.mBatteryController.addCallback(this);
    }

    public void setNavigationBar(LightBarTransitionsController navigationBar) {
        this.mNavigationBarController = navigationBar;
        updateNavigation();
    }

    public void setFingerprintUnlockController(FingerprintUnlockController fingerprintUnlockController) {
        this.mFingerprintUnlockController = fingerprintUnlockController;
    }

    public void onSystemUiVisibilityChanged(int fullscreenStackVis, int dockedStackVis, int mask, Rect fullscreenStackBounds, Rect dockedStackBounds, boolean sbModeChanged, int statusBarMode) {
        int oldFullscreen = this.mFullscreenStackVisibility;
        int newFullscreen = ((~mask) & oldFullscreen) | (fullscreenStackVis & mask);
        int diffFullscreen = newFullscreen ^ oldFullscreen;
        int oldDocked = this.mDockedStackVisibility;
        int newDocked = ((~mask) & oldDocked) | (dockedStackVis & mask);
        int diffDocked = newDocked ^ oldDocked;
        if ((diffFullscreen & 8192) != 0 || (diffDocked & 8192) != 0 || sbModeChanged || !this.mLastFullscreenBounds.equals(fullscreenStackBounds) || !this.mLastDockedBounds.equals(dockedStackBounds)) {
            this.mFullscreenLight = isLight(newFullscreen, statusBarMode, 8192);
            this.mDockedLight = isLight(newDocked, statusBarMode, 8192);
            updateStatus(fullscreenStackBounds, dockedStackBounds);
        }
        this.mFullscreenStackVisibility = newFullscreen;
        this.mDockedStackVisibility = newDocked;
        this.mLastStatusBarMode = statusBarMode;
        this.mLastFullscreenBounds.set(fullscreenStackBounds);
        this.mLastDockedBounds.set(dockedStackBounds);
    }

    public void onNavigationVisibilityChanged(int vis, int mask, boolean nbModeChanged, int navigationBarMode) {
        int oldVis = this.mSystemUiVisibility;
        int newVis = ((~mask) & oldVis) | (vis & mask);
        int diffVis = newVis ^ oldVis;
        if ((diffVis & 16) != 0 || nbModeChanged) {
            boolean last = this.mNavigationLight;
            this.mHasLightNavigationBar = isLight(vis, navigationBarMode, 16);
            this.mNavigationLight = (!this.mHasLightNavigationBar || this.mForceDarkForScrim || this.mQsCustomizing) ? false : true;
            if (this.mNavigationLight != last) {
                updateNavigation();
            }
        }
        this.mSystemUiVisibility = newVis;
        this.mLastNavigationBarMode = navigationBarMode;
    }

    private void reevaluate() {
        onSystemUiVisibilityChanged(this.mFullscreenStackVisibility, this.mDockedStackVisibility, 0, this.mLastFullscreenBounds, this.mLastDockedBounds, true, this.mLastStatusBarMode);
        onNavigationVisibilityChanged(this.mSystemUiVisibility, 0, true, this.mLastNavigationBarMode);
    }

    public void setQsCustomizing(boolean customizing) {
        if (this.mQsCustomizing == customizing) {
            return;
        }
        this.mQsCustomizing = customizing;
        reevaluate();
    }

    public void setScrimState(ScrimState scrimState, float scrimBehindAlpha, ColorExtractor.GradientColors scrimInFrontColor) {
        boolean forceDarkForScrimLast = this.mForceDarkForScrim;
        this.mForceDarkForScrim = (scrimState == ScrimState.BOUNCER || scrimState == ScrimState.BOUNCER_SCRIMMED || scrimBehindAlpha < 0.1f || scrimInFrontColor.supportsDarkText()) ? false : true;
        if (this.mHasLightNavigationBar && this.mForceDarkForScrim != forceDarkForScrimLast) {
            reevaluate();
        }
    }

    private boolean isLight(int vis, int barMode, int flag) {
        boolean isTransparentBar = barMode == 4 || barMode == 6;
        boolean light = (vis & flag) != 0;
        return isTransparentBar && light;
    }

    private boolean animateChange() {
        int unlockMode;
        return (this.mFingerprintUnlockController == null || (unlockMode = this.mFingerprintUnlockController.getMode()) == 2 || unlockMode == 1) ? false : true;
    }

    private void updateStatus(Rect fullscreenStackBounds, Rect dockedStackBounds) {
        boolean hasDockedStack = !dockedStackBounds.isEmpty();
        if ((this.mFullscreenLight && this.mDockedLight) || (this.mFullscreenLight && !hasDockedStack)) {
            this.mStatusBarIconController.setIconsDarkArea(null);
            this.mStatusBarIconController.getTransitionsController().setIconsDark(true, animateChange());
        } else {
            if ((!this.mFullscreenLight && !this.mDockedLight) || (!this.mFullscreenLight && !hasDockedStack)) {
                this.mStatusBarIconController.getTransitionsController().setIconsDark(false, animateChange());
                return;
            }
            if (hasDockedStack) {
                this.mStatusBarIconController.setIconsDarkArea(dockedStackBounds);
            }
            this.mStatusBarIconController.getTransitionsController().setIconsDark(this.mDockedLight, animateChange());
        }
    }

    private void updateNavigation() {
        if (this.mNavigationBarController != null) {
            this.mNavigationBarController.setIconsDark(this.mNavigationLight, animateChange());
        }
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public void onBatteryLevelChanged(int level, boolean pluggedIn, boolean charging) {
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public void onPowerSaveChanged(boolean isPowerSave) {
        reevaluate();
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("LightBarController: ");
        pw.print(" mSystemUiVisibility=0x");
        pw.print(Integer.toHexString(this.mSystemUiVisibility));
        pw.print(" mFullscreenStackVisibility=0x");
        pw.print(Integer.toHexString(this.mFullscreenStackVisibility));
        pw.print(" mDockedStackVisibility=0x");
        pw.println(Integer.toHexString(this.mDockedStackVisibility));
        pw.print(" mFullscreenLight=");
        pw.print(this.mFullscreenLight);
        pw.print(" mDockedLight=");
        pw.println(this.mDockedLight);
        pw.print(" mLastFullscreenBounds=");
        pw.print(this.mLastFullscreenBounds);
        pw.print(" mLastDockedBounds=");
        pw.println(this.mLastDockedBounds);
        pw.print(" mNavigationLight=");
        pw.print(this.mNavigationLight);
        pw.print(" mHasLightNavigationBar=");
        pw.println(this.mHasLightNavigationBar);
        pw.print(" mLastStatusBarMode=");
        pw.print(this.mLastStatusBarMode);
        pw.print(" mLastNavigationBarMode=");
        pw.println(this.mLastNavigationBarMode);
        pw.print(" mForceDarkForScrim=");
        pw.print(this.mForceDarkForScrim);
        pw.print(" mQsCustomizing=");
        pw.println(this.mQsCustomizing);
        pw.println();
        LightBarTransitionsController transitionsController = this.mStatusBarIconController.getTransitionsController();
        if (transitionsController != null) {
            pw.println(" StatusBarTransitionsController:");
            transitionsController.dump(fd, pw, args);
            pw.println();
        }
        if (this.mNavigationBarController != null) {
            pw.println(" NavigationBarTransitionsController:");
            this.mNavigationBarController.dump(fd, pw, args);
            pw.println();
        }
    }
}
