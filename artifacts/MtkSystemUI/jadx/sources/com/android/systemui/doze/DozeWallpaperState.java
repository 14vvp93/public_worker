package com.android.systemui.doze;

import android.app.IWallpaperManager;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.statusbar.phone.DozeParameters;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class DozeWallpaperState implements DozeMachine.Part {
    private static final boolean DEBUG = Log.isLoggable("DozeWallpaperState", 3);
    private final DozeParameters mDozeParameters;
    private boolean mIsAmbientMode;
    private final IWallpaperManager mWallpaperManagerService;

    public DozeWallpaperState(Context context) {
        this(IWallpaperManager.Stub.asInterface(ServiceManager.getService("wallpaper")), DozeParameters.getInstance(context));
    }

    @VisibleForTesting
    DozeWallpaperState(IWallpaperManager wallpaperManagerService, DozeParameters parameters) {
        this.mWallpaperManagerService = wallpaperManagerService;
        this.mDozeParameters = parameters;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State oldState, DozeMachine.State newState) {
        boolean isAmbientMode;
        boolean animated = false;
        switch (newState) {
            case DOZE:
            case DOZE_AOD:
            case DOZE_AOD_PAUSING:
            case DOZE_AOD_PAUSED:
            case DOZE_REQUEST_PULSE:
            case DOZE_PULSING:
            case DOZE_PULSE_DONE:
                isAmbientMode = true;
                break;
            default:
                isAmbientMode = false;
                break;
        }
        if (isAmbientMode) {
            animated = this.mDozeParameters.shouldControlScreenOff();
        } else {
            boolean wakingUpFromPulse = oldState == DozeMachine.State.DOZE_PULSING && newState == DozeMachine.State.FINISH;
            if (!this.mDozeParameters.getDisplayNeedsBlanking() || wakingUpFromPulse) {
                animated = true;
            }
        }
        boolean wakingUpFromPulse2 = this.mIsAmbientMode;
        if (isAmbientMode != wakingUpFromPulse2) {
            this.mIsAmbientMode = isAmbientMode;
            try {
                if (DEBUG) {
                    Log.i("DozeWallpaperState", "AOD wallpaper state changed to: " + this.mIsAmbientMode + ", animated: " + animated);
                }
                this.mWallpaperManagerService.setInAmbientMode(this.mIsAmbientMode, animated);
            } catch (RemoteException e) {
                Log.w("DozeWallpaperState", "Cannot notify state to WallpaperManagerService: " + this.mIsAmbientMode);
            }
        }
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void dump(PrintWriter pw) {
        pw.println("DozeWallpaperState:");
        pw.println(" isAmbientMode: " + this.mIsAmbientMode);
    }
}
