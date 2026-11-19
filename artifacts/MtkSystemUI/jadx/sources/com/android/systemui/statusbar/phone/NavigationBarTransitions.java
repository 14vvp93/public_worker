package com.android.systemui.statusbar.phone;

import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.SparseArray;
import android.view.IWallpaperVisibilityListener;
import android.view.IWindowManager;
import android.view.MotionEvent;
import android.view.View;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.Dependency;
import com.android.systemui.R;

/* JADX INFO: loaded from: classes.dex */
public final class NavigationBarTransitions extends BarTransitions {
    private final boolean mAllowAutoDimWallpaperNotVisible;
    private boolean mAutoDim;
    private final IStatusBarService mBarService;
    private final LightBarTransitionsController mLightTransitionsController;
    private boolean mLightsOut;
    private final View.OnTouchListener mLightsOutListener;
    private View mNavButtons;
    private final NavigationBarView mView;
    private boolean mWallpaperVisible;

    public NavigationBarTransitions(NavigationBarView view) {
        super(view, R.drawable.nav_background);
        this.mLightsOutListener = new View.OnTouchListener() { // from class: com.android.systemui.statusbar.phone.NavigationBarTransitions.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent ev) {
                if (ev.getAction() == 0) {
                    NavigationBarTransitions.this.applyLightsOut(false, false, false);
                    try {
                        NavigationBarTransitions.this.mBarService.setSystemUiVisibility(0, 1, "LightsOutListener");
                    } catch (RemoteException e) {
                    }
                }
                return false;
            }
        };
        this.mView = view;
        this.mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        this.mLightTransitionsController = new LightBarTransitionsController(view.getContext(), new LightBarTransitionsController.DarkIntensityApplier() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$wMNbjUhGbyej8_fCHrQk9JvpMpc
            @Override // com.android.systemui.statusbar.phone.LightBarTransitionsController.DarkIntensityApplier
            public final void applyDarkIntensity(float f) {
                this.f$0.applyDarkIntensity(f);
            }
        });
        this.mAllowAutoDimWallpaperNotVisible = view.getContext().getResources().getBoolean(R.bool.config_navigation_bar_enable_auto_dim_no_visible_wallpaper);
        IWindowManager windowManagerService = (IWindowManager) Dependency.get(IWindowManager.class);
        Handler handler = Handler.getMain();
        try {
            this.mWallpaperVisible = windowManagerService.registerWallpaperVisibilityListener(new AnonymousClass1(handler), 0);
        } catch (RemoteException e) {
        }
        this.mView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarTransitions$XJcD0ZRW4UO2juvu7uZcSTj_ILk
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                NavigationBarTransitions.lambda$new$0(this.f$0, view2, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
        View currentView = this.mView.getCurrentView();
        if (currentView != null) {
            this.mNavButtons = currentView.findViewById(R.id.nav_buttons);
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.phone.NavigationBarTransitions$1, reason: invalid class name */
    class AnonymousClass1 extends IWallpaperVisibilityListener.Stub {
        final /* synthetic */ Handler val$handler;

        AnonymousClass1(Handler handler) {
            this.val$handler = handler;
        }

        public void onWallpaperVisibilityChanged(boolean newVisibility, int displayId) throws RemoteException {
            NavigationBarTransitions.this.mWallpaperVisible = newVisibility;
            this.val$handler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarTransitions$1$5foY_Yygo1gW25-mVBRpPSQRb_g
                @Override // java.lang.Runnable
                public final void run() {
                    NavigationBarTransitions.this.applyLightsOut(true, false);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$new$0(NavigationBarTransitions navigationBarTransitions, View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        View currentView = navigationBarTransitions.mView.getCurrentView();
        if (currentView != null) {
            navigationBarTransitions.mNavButtons = currentView.findViewById(R.id.nav_buttons);
            navigationBarTransitions.applyLightsOut(false, true);
        }
    }

    public void init() {
        applyModeBackground(-1, getMode(), false);
        applyLightsOut(false, true);
    }

    @Override // com.android.systemui.statusbar.phone.BarTransitions
    public void setAutoDim(boolean autoDim) {
        if (this.mAutoDim == autoDim) {
            return;
        }
        this.mAutoDim = autoDim;
        applyLightsOut(true, false);
    }

    @Override // com.android.systemui.statusbar.phone.BarTransitions
    protected boolean isLightsOut(int mode) {
        return super.isLightsOut(mode) || (this.mAllowAutoDimWallpaperNotVisible && this.mAutoDim && !this.mWallpaperVisible && mode != 5);
    }

    public LightBarTransitionsController getLightTransitionsController() {
        return this.mLightTransitionsController;
    }

    @Override // com.android.systemui.statusbar.phone.BarTransitions
    protected void onTransition(int oldMode, int newMode, boolean animate) {
        super.onTransition(oldMode, newMode, animate);
        applyLightsOut(animate, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyLightsOut(boolean animate, boolean force) {
        applyLightsOut(isLightsOut(getMode()), animate, force);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyLightsOut(boolean lightsOut, boolean animate, boolean force) {
        if (force || lightsOut != this.mLightsOut) {
            this.mLightsOut = lightsOut;
            if (this.mNavButtons == null) {
                return;
            }
            this.mNavButtons.animate().cancel();
            float darkBump = this.mLightTransitionsController.getCurrentDarkIntensity() / 10.0f;
            float navButtonsAlpha = lightsOut ? 0.6f + darkBump : 1.0f;
            if (!animate) {
                this.mNavButtons.setAlpha(navButtonsAlpha);
            } else {
                int duration = lightsOut ? 1500 : 250;
                this.mNavButtons.animate().alpha(navButtonsAlpha).setDuration(duration).start();
            }
        }
    }

    public void reapplyDarkIntensity() {
        applyDarkIntensity(this.mLightTransitionsController.getCurrentDarkIntensity());
    }

    public void applyDarkIntensity(float darkIntensity) {
        SparseArray<ButtonDispatcher> buttonDispatchers = this.mView.getButtonDispatchers();
        for (int i = buttonDispatchers.size() - 1; i >= 0; i--) {
            buttonDispatchers.valueAt(i).setDarkIntensity(darkIntensity);
        }
        if (this.mAutoDim) {
            applyLightsOut(false, true);
        }
        this.mView.onDarkIntensityChange(darkIntensity);
    }
}
