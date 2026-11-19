package com.android.systemui.stackdivider;

import android.content.res.Configuration;
import android.os.Handler;
import android.os.RemoteException;
import android.view.IDockedStackListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.SystemUI;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.events.ui.RecentsDrawnEvent;
import com.android.systemui.recents.misc.SystemServicesProxy;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class Divider extends SystemUI {
    private DockDividerVisibilityListener mDockDividerVisibilityListener;
    private ForcedResizableInfoActivityController mForcedResizableController;
    private DividerView mView;
    private DividerWindowManager mWindowManager;
    private final DividerState mDividerState = new DividerState();
    private boolean mVisible = false;
    private boolean mMinimized = false;
    private boolean mAdjustedForIme = false;
    private boolean mHomeStackResizable = false;
    private final Handler mHandler = new Handler();

    @Override // com.android.systemui.SystemUI
    public void start() {
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.stackdivider.-$$Lambda$Divider$WYg5EhyQXJtV0PKYsV4XTYJih-M
            @Override // java.lang.Runnable
            public final void run() {
                Divider.lambda$start$0(this.f$0);
            }
        }, 5000L);
        putComponent(Divider.class, this);
    }

    public static /* synthetic */ void lambda$start$0(Divider divider) {
        divider.mWindowManager = new DividerWindowManager(divider.mContext);
        divider.update(divider.mContext.getResources().getConfiguration());
        divider.mDockDividerVisibilityListener = divider.new DockDividerVisibilityListener();
        SystemServicesProxy ssp = Recents.getSystemServices();
        ssp.registerDockedStackListener(divider.mDockDividerVisibilityListener);
        divider.mForcedResizableController = new ForcedResizableInfoActivityController(divider.mContext);
        EventBus.getDefault().register(divider);
    }

    @Override // com.android.systemui.SystemUI
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        update(newConfig);
    }

    public DividerView getView() {
        return this.mView;
    }

    public boolean isMinimized() {
        return this.mMinimized;
    }

    public boolean isHomeStackResizable() {
        return this.mHomeStackResizable;
    }

    private void addDivider(Configuration configuration) {
        this.mView = (DividerView) LayoutInflater.from(this.mContext).inflate(R.layout.docked_stack_divider, (ViewGroup) null);
        this.mView.injectDependencies(this.mWindowManager, this.mDividerState);
        this.mView.setVisibility(this.mVisible ? 0 : 4);
        this.mView.setMinimizedDockStack(this.mMinimized, this.mHomeStackResizable);
        int size = this.mContext.getResources().getDimensionPixelSize(android.R.dimen.car_button_height);
        boolean landscape = configuration.orientation == 2;
        int width = landscape ? size : -1;
        int height = landscape ? -1 : size;
        if (this.mWindowManager != null) {
            this.mWindowManager.add(this.mView, width, height);
        }
    }

    private void removeDivider() {
        if (this.mView != null) {
            this.mView.onDividerRemoved();
        }
        if (this.mWindowManager != null) {
            this.mWindowManager.remove();
        }
    }

    private void update(Configuration configuration) {
        removeDivider();
        addDivider(configuration);
        if (this.mMinimized && this.mView != null) {
            this.mView.setMinimizedDockStack(true, this.mHomeStackResizable);
            updateTouchable();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVisibility(final boolean visible) {
        if (this.mView == null) {
            return;
        }
        this.mView.post(new Runnable() { // from class: com.android.systemui.stackdivider.Divider.1
            @Override // java.lang.Runnable
            public void run() {
                if (Divider.this.mVisible != visible) {
                    Divider.this.mVisible = visible;
                    if (Divider.this.mView != null) {
                        Divider.this.mView.setVisibility(visible ? 0 : 4);
                        Divider.this.mView.setMinimizedDockStack(Divider.this.mMinimized, Divider.this.mHomeStackResizable);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMinimizedDockedStack(final boolean minimized, final long animDuration, final boolean isHomeStackResizable) {
        if (this.mView == null) {
            return;
        }
        this.mView.post(new Runnable() { // from class: com.android.systemui.stackdivider.Divider.2
            @Override // java.lang.Runnable
            public void run() {
                Divider.this.mHomeStackResizable = isHomeStackResizable;
                if (Divider.this.mMinimized != minimized) {
                    Divider.this.mMinimized = minimized;
                    Divider.this.updateTouchable();
                    if (animDuration > 0) {
                        Divider.this.mView.setMinimizedDockStack(minimized, animDuration, isHomeStackResizable);
                    } else {
                        Divider.this.mView.setMinimizedDockStack(minimized, isHomeStackResizable);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDockedStackExistsChanged(final boolean exists) {
        if (this.mView == null) {
            return;
        }
        this.mView.post(new Runnable() { // from class: com.android.systemui.stackdivider.Divider.3
            @Override // java.lang.Runnable
            public void run() {
                if (Divider.this.mForcedResizableController != null) {
                    Divider.this.mForcedResizableController.notifyDockedStackExistsChanged(exists);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTouchable() {
        if (this.mWindowManager != null) {
            this.mWindowManager.setTouchable((this.mHomeStackResizable || !this.mMinimized) && !this.mAdjustedForIme);
        }
    }

    public final void onBusEvent(RecentsDrawnEvent drawnEvent) {
        if (this.mView != null) {
            this.mView.onRecentsDrawn();
        }
    }

    @Override // com.android.systemui.SystemUI
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.print("  mVisible=");
        pw.println(this.mVisible);
        pw.print("  mMinimized=");
        pw.println(this.mMinimized);
        pw.print("  mAdjustedForIme=");
        pw.println(this.mAdjustedForIme);
    }

    class DockDividerVisibilityListener extends IDockedStackListener.Stub {
        DockDividerVisibilityListener() {
        }

        public void onDividerVisibilityChanged(boolean visible) throws RemoteException {
            Divider.this.updateVisibility(visible);
        }

        public void onDockedStackExistsChanged(boolean exists) throws RemoteException {
            Divider.this.notifyDockedStackExistsChanged(exists);
        }

        public void onDockedStackMinimizedChanged(boolean minimized, long animDuration, boolean isHomeStackResizable) throws RemoteException {
            Divider.this.mHomeStackResizable = isHomeStackResizable;
            Divider.this.updateMinimizedDockedStack(minimized, animDuration, isHomeStackResizable);
        }

        public void onAdjustedForImeChanged(final boolean adjustedForIme, final long animDuration) throws RemoteException {
            if (Divider.this.mView != null) {
                Divider.this.mView.post(new Runnable() { // from class: com.android.systemui.stackdivider.-$$Lambda$Divider$DockDividerVisibilityListener$fZDE4rhC5s3QEgR-7YXeKi_feiY
                    @Override // java.lang.Runnable
                    public final void run() {
                        Divider.DockDividerVisibilityListener.lambda$onAdjustedForImeChanged$0(this.f$0, adjustedForIme, animDuration);
                    }
                });
            }
        }

        public static /* synthetic */ void lambda$onAdjustedForImeChanged$0(DockDividerVisibilityListener dockDividerVisibilityListener, boolean adjustedForIme, long animDuration) {
            if (Divider.this.mAdjustedForIme != adjustedForIme) {
                Divider.this.mAdjustedForIme = adjustedForIme;
                Divider.this.updateTouchable();
                if (Divider.this.mMinimized || Divider.this.mView == null) {
                    return;
                }
                if (animDuration > 0) {
                    Divider.this.mView.setAdjustedForIme(adjustedForIme, animDuration);
                } else {
                    Divider.this.mView.setAdjustedForIme(adjustedForIme);
                }
            }
        }

        public void onDockSideChanged(final int newDockSide) throws RemoteException {
            if (Divider.this.mView != null) {
                Divider.this.mView.post(new Runnable() { // from class: com.android.systemui.stackdivider.-$$Lambda$Divider$DockDividerVisibilityListener$cPiHgQdgCDQeKAQTEdGGnGaaM_c
                    @Override // java.lang.Runnable
                    public final void run() {
                        Divider.this.mView.notifyDockSideChanged(newDockSide);
                    }
                });
            }
        }
    }
}
