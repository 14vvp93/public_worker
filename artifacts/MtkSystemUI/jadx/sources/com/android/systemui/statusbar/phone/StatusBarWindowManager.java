package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Binder;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.RemoteInputController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarWindowManager implements Dumpable, RemoteInputController.Callback {
    private int mBarHeight;
    private final Context mContext;
    private final DozeParameters mDozeParameters;
    private boolean mHasTopUi;
    private boolean mHasTopUiChanged;
    private OtherwisedCollapsedListener mListener;
    private WindowManager.LayoutParams mLp;
    private WindowManager.LayoutParams mLpChanged;
    private float mScreenBrightnessDoze;
    private View mStatusBarView;
    private final WindowManager mWindowManager;
    private final State mCurrentState = new State();
    private final IActivityManager mActivityManager = ActivityManager.getService();
    private final boolean mKeyguardScreenRotation = shouldEnableKeyguardScreenRotation();

    public interface OtherwisedCollapsedListener {
        void setWouldOtherwiseCollapse(boolean z);
    }

    public StatusBarWindowManager(Context context) {
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mDozeParameters = DozeParameters.getInstance(this.mContext);
        this.mScreenBrightnessDoze = this.mDozeParameters.getScreenBrightnessDoze();
    }

    private boolean shouldEnableKeyguardScreenRotation() {
        Resources res = this.mContext.getResources();
        return SystemProperties.getBoolean("lockscreen.rot_override", false) || res.getBoolean(R.bool.config_enableLockScreenRotation);
    }

    public void add(View statusBarView, int barHeight) {
        this.mLp = new WindowManager.LayoutParams(-1, barHeight, 2000, -2138832824, -3);
        this.mLp.token = new Binder();
        this.mLp.gravity = 48;
        this.mLp.softInputMode = 16;
        this.mLp.setTitle("StatusBar");
        this.mLp.packageName = this.mContext.getPackageName();
        this.mLp.layoutInDisplayCutoutMode = 1;
        this.mStatusBarView = statusBarView;
        this.mBarHeight = barHeight;
        this.mWindowManager.addView(this.mStatusBarView, this.mLp);
        this.mLpChanged = new WindowManager.LayoutParams();
        this.mLpChanged.copyFrom(this.mLp);
    }

    public void setDozeScreenBrightness(int value) {
        this.mScreenBrightnessDoze = value / 255.0f;
    }

    public void setKeyguardDark(boolean dark) {
        int vis;
        int vis2 = this.mStatusBarView.getSystemUiVisibility();
        if (dark) {
            vis = vis2 | 16 | 8192;
        } else {
            vis = vis2 & (-17) & (-8193);
        }
        this.mStatusBarView.setSystemUiVisibility(vis);
    }

    private void applyKeyguardFlags(State state) {
        if (state.keyguardShowing) {
            this.mLpChanged.privateFlags |= 1024;
        } else {
            this.mLpChanged.privateFlags &= -1025;
        }
        boolean z = false;
        boolean scrimsOccludingWallpaper = state.scrimsVisibility == 2;
        if (state.keyguardShowing || (state.dozing && this.mDozeParameters.getAlwaysOn())) {
            z = true;
        }
        boolean keyguardOrAod = z;
        if (keyguardOrAod && !state.backdropShowing && !scrimsOccludingWallpaper) {
            this.mLpChanged.flags |= 1048576;
        } else {
            this.mLpChanged.flags &= -1048577;
        }
    }

    private void adjustScreenOrientation(State state) {
        if (state.isKeyguardShowingAndNotOccluded() || state.dozing) {
            if (this.mKeyguardScreenRotation) {
                this.mLpChanged.screenOrientation = 2;
                return;
            } else {
                this.mLpChanged.screenOrientation = 5;
                return;
            }
        }
        this.mLpChanged.screenOrientation = -1;
    }

    private void applyFocusableFlag(State state) {
        boolean panelFocusable = state.statusBarFocusable && state.panelExpanded;
        if ((state.bouncerShowing && (state.keyguardOccluded || state.keyguardNeedsInput)) || (NotificationRemoteInputManager.ENABLE_REMOTE_INPUT && state.remoteInputActive)) {
            this.mLpChanged.flags &= -9;
            WindowManager.LayoutParams layoutParams = this.mLpChanged;
            layoutParams.flags = (-131073) & layoutParams.flags;
        } else if (state.isKeyguardShowingAndNotOccluded() || panelFocusable) {
            this.mLpChanged.flags &= -9;
            this.mLpChanged.flags |= 131072;
        } else {
            this.mLpChanged.flags |= 8;
            WindowManager.LayoutParams layoutParams2 = this.mLpChanged;
            layoutParams2.flags = (-131073) & layoutParams2.flags;
        }
        this.mLpChanged.softInputMode = 16;
    }

    private void applyHeight(State state) {
        boolean expanded = isExpanded(state);
        if (state.forcePluginOpen) {
            this.mListener.setWouldOtherwiseCollapse(expanded);
            expanded = true;
        }
        if (expanded) {
            this.mLpChanged.height = -1;
        } else {
            this.mLpChanged.height = this.mBarHeight;
        }
    }

    private boolean isExpanded(State state) {
        return !state.forceCollapsed && (state.isKeyguardShowingAndNotOccluded() || state.panelVisible || state.keyguardFadingAway || state.bouncerShowing || state.headsUpShowing || state.scrimsVisibility != 0);
    }

    private void applyFitsSystemWindows(State state) {
        boolean fitsSystemWindows = !state.isKeyguardShowingAndNotOccluded();
        if (this.mStatusBarView.getFitsSystemWindows() != fitsSystemWindows) {
            this.mStatusBarView.setFitsSystemWindows(fitsSystemWindows);
            this.mStatusBarView.requestApplyInsets();
        }
    }

    private void applyUserActivityTimeout(State state) {
        if (state.isKeyguardShowingAndNotOccluded() && state.statusBarState == 1 && !state.qsExpanded) {
            this.mLpChanged.userActivityTimeout = 10000L;
        } else {
            this.mLpChanged.userActivityTimeout = -1L;
        }
    }

    private void applyInputFeatures(State state) {
        if (state.isKeyguardShowingAndNotOccluded() && state.statusBarState == 1 && !state.qsExpanded && !state.forceUserActivity) {
            this.mLpChanged.inputFeatures |= 4;
        } else {
            this.mLpChanged.inputFeatures &= -5;
        }
    }

    private void apply(State state) {
        applyKeyguardFlags(state);
        applyForceStatusBarVisibleFlag(state);
        applyFocusableFlag(state);
        adjustScreenOrientation(state);
        applyHeight(state);
        applyUserActivityTimeout(state);
        applyInputFeatures(state);
        applyFitsSystemWindows(state);
        applyModalFlag(state);
        applyBrightness(state);
        applyHasTopUi(state);
        applySleepToken(state);
        if (this.mLp.copyFrom(this.mLpChanged) != 0) {
            this.mWindowManager.updateViewLayout(this.mStatusBarView, this.mLp);
        }
        if (this.mHasTopUi != this.mHasTopUiChanged) {
            try {
                this.mActivityManager.setHasTopUi(this.mHasTopUiChanged);
            } catch (RemoteException e) {
                Log.e("StatusBarWindowManager", "Failed to call setHasTopUi", e);
            }
            this.mHasTopUi = this.mHasTopUiChanged;
        }
    }

    private void applyForceStatusBarVisibleFlag(State state) {
        if (state.forceStatusBarVisible) {
            this.mLpChanged.privateFlags |= 4096;
        } else {
            this.mLpChanged.privateFlags &= -4097;
        }
    }

    private void applyModalFlag(State state) {
        if (state.headsUpShowing) {
            this.mLpChanged.flags |= 32;
        } else {
            this.mLpChanged.flags &= -33;
        }
    }

    private void applyBrightness(State state) {
        if (state.forceDozeBrightness) {
            this.mLpChanged.screenBrightness = this.mScreenBrightnessDoze;
        } else {
            this.mLpChanged.screenBrightness = -1.0f;
        }
    }

    private void applyHasTopUi(State state) {
        this.mHasTopUiChanged = isExpanded(state);
    }

    private void applySleepToken(State state) {
        if (state.dozing) {
            this.mLpChanged.privateFlags |= 2097152;
        } else {
            this.mLpChanged.privateFlags &= -2097153;
        }
    }

    public void setKeyguardShowing(boolean showing) {
        this.mCurrentState.keyguardShowing = showing;
        apply(this.mCurrentState);
    }

    public void setKeyguardOccluded(boolean occluded) {
        this.mCurrentState.keyguardOccluded = occluded;
        apply(this.mCurrentState);
    }

    public void setKeyguardNeedsInput(boolean needsInput) {
        this.mCurrentState.keyguardNeedsInput = needsInput;
        apply(this.mCurrentState);
    }

    public void setPanelVisible(boolean visible) {
        this.mCurrentState.panelVisible = visible;
        this.mCurrentState.statusBarFocusable = visible;
        apply(this.mCurrentState);
    }

    public void setStatusBarFocusable(boolean focusable) {
        this.mCurrentState.statusBarFocusable = focusable;
        apply(this.mCurrentState);
    }

    public void setBouncerShowing(boolean showing) {
        this.mCurrentState.bouncerShowing = showing;
        apply(this.mCurrentState);
    }

    public void setBackdropShowing(boolean showing) {
        this.mCurrentState.backdropShowing = showing;
        apply(this.mCurrentState);
    }

    public void setKeyguardFadingAway(boolean keyguardFadingAway) {
        this.mCurrentState.keyguardFadingAway = keyguardFadingAway;
        apply(this.mCurrentState);
    }

    public void setQsExpanded(boolean expanded) {
        this.mCurrentState.qsExpanded = expanded;
        apply(this.mCurrentState);
    }

    public void setScrimsVisibility(int scrimsVisibility) {
        this.mCurrentState.scrimsVisibility = scrimsVisibility;
        apply(this.mCurrentState);
    }

    public void setHeadsUpShowing(boolean showing) {
        this.mCurrentState.headsUpShowing = showing;
        apply(this.mCurrentState);
    }

    public void setWallpaperSupportsAmbientMode(boolean supportsAmbientMode) {
        this.mCurrentState.wallpaperSupportsAmbientMode = supportsAmbientMode;
        apply(this.mCurrentState);
    }

    public void setStatusBarState(int state) {
        this.mCurrentState.statusBarState = state;
        apply(this.mCurrentState);
    }

    public void setForceStatusBarVisible(boolean forceStatusBarVisible) {
        this.mCurrentState.forceStatusBarVisible = forceStatusBarVisible;
        apply(this.mCurrentState);
    }

    public void setForceWindowCollapsed(boolean force) {
        this.mCurrentState.forceCollapsed = force;
        apply(this.mCurrentState);
    }

    public void setPanelExpanded(boolean isExpanded) {
        this.mCurrentState.panelExpanded = isExpanded;
        apply(this.mCurrentState);
    }

    @Override // com.android.systemui.statusbar.RemoteInputController.Callback
    public void onRemoteInputActive(boolean remoteInputActive) {
        this.mCurrentState.remoteInputActive = remoteInputActive;
        apply(this.mCurrentState);
    }

    public void setForceDozeBrightness(boolean forceDozeBrightness) {
        this.mCurrentState.forceDozeBrightness = forceDozeBrightness;
        apply(this.mCurrentState);
    }

    public void setDozing(boolean dozing) {
        this.mCurrentState.dozing = dozing;
        apply(this.mCurrentState);
    }

    public void setBarHeight(int barHeight) {
        this.mBarHeight = barHeight;
        apply(this.mCurrentState);
    }

    public void setForcePluginOpen(boolean forcePluginOpen) {
        this.mCurrentState.forcePluginOpen = forcePluginOpen;
        apply(this.mCurrentState);
    }

    public void setStateListener(OtherwisedCollapsedListener listener) {
        this.mListener = listener;
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("StatusBarWindowManager state:");
        pw.println(this.mCurrentState);
    }

    public boolean isShowingWallpaper() {
        return !this.mCurrentState.backdropShowing;
    }

    private static class State {
        boolean backdropShowing;
        boolean bouncerShowing;
        boolean dozing;
        boolean forceCollapsed;
        boolean forceDozeBrightness;
        boolean forcePluginOpen;
        boolean forceStatusBarVisible;
        boolean forceUserActivity;
        boolean headsUpShowing;
        boolean keyguardFadingAway;
        boolean keyguardNeedsInput;
        boolean keyguardOccluded;
        boolean keyguardShowing;
        boolean panelExpanded;
        boolean panelVisible;
        boolean qsExpanded;
        boolean remoteInputActive;
        int scrimsVisibility;
        boolean statusBarFocusable;
        int statusBarState;
        boolean wallpaperSupportsAmbientMode;

        private State() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isKeyguardShowingAndNotOccluded() {
            return this.keyguardShowing && !this.keyguardOccluded;
        }

        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append("Window State {");
            result.append("\n");
            Field[] fields = getClass().getDeclaredFields();
            for (Field field : fields) {
                result.append("  ");
                try {
                    result.append(field.getName());
                    result.append(": ");
                    result.append(field.get(this));
                } catch (IllegalAccessException e) {
                }
                result.append("\n");
            }
            result.append("}");
            return result.toString();
        }
    }
}
