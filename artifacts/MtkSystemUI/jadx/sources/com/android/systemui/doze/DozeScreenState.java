package com.android.systemui.doze;

import android.os.Handler;
import android.util.Log;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;

/* JADX INFO: loaded from: classes.dex */
public class DozeScreenState implements DozeMachine.Part {
    private static final boolean DEBUG = DozeService.DEBUG;
    private final DozeMachine.Service mDozeService;
    private final Handler mHandler;
    private final DozeParameters mParameters;
    private SettableWakeLock mWakeLock;
    private final Runnable mApplyPendingScreenState = new Runnable() { // from class: com.android.systemui.doze.-$$Lambda$DozeScreenState$eRrLSFQgxPfG2I_jJDfdCLwKzVE
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.applyPendingScreenState();
        }
    };
    private int mPendingScreenState = 0;

    public DozeScreenState(DozeMachine.Service service, Handler handler, DozeParameters parameters, WakeLock wakeLock) {
        this.mDozeService = service;
        this.mHandler = handler;
        this.mParameters = parameters;
        this.mWakeLock = new SettableWakeLock(wakeLock);
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State oldState, DozeMachine.State newState) {
        int screenState = newState.screenState(this.mParameters);
        boolean shouldDelayTransition = false;
        if (newState == DozeMachine.State.FINISH) {
            this.mPendingScreenState = 0;
            this.mHandler.removeCallbacks(this.mApplyPendingScreenState);
            applyScreenState(screenState);
            this.mWakeLock.setAcquired(false);
            return;
        }
        if (screenState == 0) {
            return;
        }
        boolean messagePending = this.mHandler.hasCallbacks(this.mApplyPendingScreenState);
        boolean pulseEnding = oldState == DozeMachine.State.DOZE_PULSE_DONE && newState == DozeMachine.State.DOZE_AOD;
        if (messagePending || oldState == DozeMachine.State.INITIALIZED || pulseEnding) {
            this.mPendingScreenState = screenState;
            if (newState == DozeMachine.State.DOZE_AOD && this.mParameters.shouldControlScreenOff()) {
                shouldDelayTransition = true;
            }
            if (shouldDelayTransition) {
                this.mWakeLock.setAcquired(true);
            }
            if (!messagePending) {
                if (DEBUG) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Display state changed to ");
                    sb.append(screenState);
                    sb.append(" delayed by ");
                    sb.append(shouldDelayTransition ? 6000 : 1);
                    Log.d("DozeScreenState", sb.toString());
                }
                if (shouldDelayTransition) {
                    this.mHandler.postDelayed(this.mApplyPendingScreenState, 6000L);
                    return;
                } else {
                    this.mHandler.post(this.mApplyPendingScreenState);
                    return;
                }
            }
            if (DEBUG) {
                Log.d("DozeScreenState", "Pending display state change to " + screenState);
                return;
            }
            return;
        }
        applyScreenState(screenState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyPendingScreenState() {
        applyScreenState(this.mPendingScreenState);
        this.mPendingScreenState = 0;
    }

    private void applyScreenState(int screenState) {
        if (screenState != 0) {
            if (DEBUG) {
                Log.d("DozeScreenState", "setDozeScreenState(" + screenState + ")");
            }
            this.mDozeService.setDozeScreenState(screenState);
            this.mPendingScreenState = 0;
            this.mWakeLock.setAcquired(false);
        }
    }
}
