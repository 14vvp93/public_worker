package com.android.systemui.doze;

import android.os.Trace;
import android.util.Log;
import com.android.internal.hardware.AmbientDisplayConfiguration;
import com.android.internal.util.Preconditions;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.Assert;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.PrintWriter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class DozeMachine {
    static final boolean DEBUG = DozeService.DEBUG;
    private final AmbientDisplayConfiguration mConfig;
    private final Service mDozeService;
    private Part[] mParts;
    private int mPulseReason;
    private final WakeLock mWakeLock;
    private final ArrayList<State> mQueuedRequests = new ArrayList<>();
    private State mState = State.UNINITIALIZED;
    private boolean mWakeLockHeldForCurrentState = false;

    public enum State {
        UNINITIALIZED,
        INITIALIZED,
        DOZE,
        DOZE_AOD,
        DOZE_REQUEST_PULSE,
        DOZE_PULSING,
        DOZE_PULSE_DONE,
        FINISH,
        DOZE_AOD_PAUSED,
        DOZE_AOD_PAUSING;

        boolean canPulse() {
            switch (this) {
                case DOZE:
                case DOZE_AOD:
                case DOZE_AOD_PAUSED:
                case DOZE_AOD_PAUSING:
                    return true;
                default:
                    return false;
            }
        }

        boolean staysAwake() {
            switch (this) {
                case DOZE_REQUEST_PULSE:
                case DOZE_PULSING:
                    return true;
                default:
                    return false;
            }
        }

        int screenState(DozeParameters parameters) {
            switch (this) {
                case DOZE:
                case DOZE_AOD_PAUSED:
                    return 1;
                case DOZE_AOD:
                case DOZE_AOD_PAUSING:
                    return 4;
                case DOZE_REQUEST_PULSE:
                case UNINITIALIZED:
                case INITIALIZED:
                    return parameters.shouldControlScreenOff() ? 2 : 1;
                case DOZE_PULSING:
                    return 2;
                default:
                    return 0;
            }
        }
    }

    public DozeMachine(Service service, AmbientDisplayConfiguration config, WakeLock wakeLock) {
        this.mDozeService = service;
        this.mConfig = config;
        this.mWakeLock = wakeLock;
    }

    public void setParts(Part[] parts) {
        Preconditions.checkState(this.mParts == null);
        this.mParts = parts;
    }

    public void requestState(State requestedState) {
        Preconditions.checkArgument(requestedState != State.DOZE_REQUEST_PULSE);
        requestState(requestedState, -1);
    }

    public void requestPulse(int pulseReason) {
        Preconditions.checkState(!isExecutingTransition());
        requestState(State.DOZE_REQUEST_PULSE, pulseReason);
    }

    private void requestState(State requestedState, int pulseReason) {
        Assert.isMainThread();
        if (DEBUG) {
            Log.i("DozeMachine", "request: current=" + this.mState + " req=" + requestedState, new Throwable("here"));
        }
        boolean runNow = !isExecutingTransition();
        this.mQueuedRequests.add(requestedState);
        if (runNow) {
            this.mWakeLock.acquire();
            for (int i = 0; i < this.mQueuedRequests.size(); i++) {
                transitionTo(this.mQueuedRequests.get(i), pulseReason);
            }
            this.mQueuedRequests.clear();
            this.mWakeLock.release();
        }
    }

    public State getState() {
        Assert.isMainThread();
        Preconditions.checkState(!isExecutingTransition());
        return this.mState;
    }

    public int getPulseReason() {
        Assert.isMainThread();
        Preconditions.checkState(this.mState == State.DOZE_REQUEST_PULSE || this.mState == State.DOZE_PULSING || this.mState == State.DOZE_PULSE_DONE, "must be in pulsing state, but is " + this.mState);
        return this.mPulseReason;
    }

    public void wakeUp() {
        this.mDozeService.requestWakeUp();
    }

    private boolean isExecutingTransition() {
        return !this.mQueuedRequests.isEmpty();
    }

    private void transitionTo(State requestedState, int pulseReason) {
        State newState = transitionPolicy(requestedState);
        if (DEBUG) {
            Log.i("DozeMachine", "transition: old=" + this.mState + " req=" + requestedState + " new=" + newState);
        }
        if (newState == this.mState) {
            return;
        }
        validateTransition(newState);
        State oldState = this.mState;
        this.mState = newState;
        DozeLog.traceState(newState);
        Trace.traceCounter(4096L, "doze_machine_state", newState.ordinal());
        updatePulseReason(newState, oldState, pulseReason);
        performTransitionOnComponents(oldState, newState);
        updateWakeLockState(newState);
        resolveIntermediateState(newState);
    }

    private void updatePulseReason(State newState, State oldState, int pulseReason) {
        if (newState == State.DOZE_REQUEST_PULSE) {
            this.mPulseReason = pulseReason;
        } else if (oldState == State.DOZE_PULSE_DONE) {
            this.mPulseReason = -1;
        }
    }

    private void performTransitionOnComponents(State oldState, State newState) {
        for (Part p : this.mParts) {
            p.transitionTo(oldState, newState);
        }
        if (AnonymousClass1.$SwitchMap$com$android$systemui$doze$DozeMachine$State[newState.ordinal()] == 9) {
            this.mDozeService.finish();
        }
    }

    private void validateTransition(State newState) {
        try {
            int i = AnonymousClass1.$SwitchMap$com$android$systemui$doze$DozeMachine$State[this.mState.ordinal()];
            boolean z = false;
            if (i == 7) {
                Preconditions.checkState(newState == State.INITIALIZED);
            } else if (i == 9) {
                Preconditions.checkState(newState == State.FINISH);
            }
            switch (newState) {
                case DOZE_PULSING:
                    Preconditions.checkState(this.mState == State.DOZE_REQUEST_PULSE);
                    return;
                case UNINITIALIZED:
                    throw new IllegalArgumentException("can't transition to UNINITIALIZED");
                case INITIALIZED:
                    Preconditions.checkState(this.mState == State.UNINITIALIZED);
                    return;
                case FINISH:
                default:
                    return;
                case DOZE_PULSE_DONE:
                    if (this.mState == State.DOZE_REQUEST_PULSE || this.mState == State.DOZE_PULSING) {
                        z = true;
                    }
                    Preconditions.checkState(z);
                    return;
            }
        } catch (RuntimeException e) {
            throw new IllegalStateException("Illegal Transition: " + this.mState + " -> " + newState, e);
        }
    }

    private State transitionPolicy(State requestedState) {
        if (this.mState == State.FINISH) {
            return State.FINISH;
        }
        if ((this.mState == State.DOZE_AOD_PAUSED || this.mState == State.DOZE_AOD_PAUSING || this.mState == State.DOZE_AOD || this.mState == State.DOZE) && requestedState == State.DOZE_PULSE_DONE) {
            Log.i("DozeMachine", "Dropping pulse done because current state is already done: " + this.mState);
            return this.mState;
        }
        if (requestedState == State.DOZE_REQUEST_PULSE && !this.mState.canPulse()) {
            Log.i("DozeMachine", "Dropping pulse request because current state can't pulse: " + this.mState);
            return this.mState;
        }
        return requestedState;
    }

    private void updateWakeLockState(State newState) {
        boolean staysAwake = newState.staysAwake();
        if (this.mWakeLockHeldForCurrentState && !staysAwake) {
            this.mWakeLock.release();
            this.mWakeLockHeldForCurrentState = false;
        } else if (!this.mWakeLockHeldForCurrentState && staysAwake) {
            this.mWakeLock.acquire();
            this.mWakeLockHeldForCurrentState = true;
        }
    }

    private void resolveIntermediateState(State state) {
        int i = AnonymousClass1.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state.ordinal()];
        if (i == 8 || i == 10) {
            transitionTo(this.mConfig.alwaysOnEnabled(-2) ? State.DOZE_AOD : State.DOZE, -1);
        }
    }

    public void dump(PrintWriter pw) {
        pw.print(" state=");
        pw.println(this.mState);
        pw.print(" wakeLockHeldForCurrentState=");
        pw.println(this.mWakeLockHeldForCurrentState);
        pw.println("Parts:");
        for (Part p : this.mParts) {
            p.dump(pw);
        }
    }

    public interface Part {
        void transitionTo(State state, State state2);

        default void dump(PrintWriter pw) {
        }
    }

    public interface Service {
        void finish();

        void requestWakeUp();

        void setDozeScreenBrightness(int i);

        void setDozeScreenState(int i);

        public static class Delegate implements Service {
            private final Service mDelegate;

            public Delegate(Service delegate) {
                this.mDelegate = delegate;
            }

            @Override // com.android.systemui.doze.DozeMachine.Service
            public void finish() {
                this.mDelegate.finish();
            }

            @Override // com.android.systemui.doze.DozeMachine.Service
            public void setDozeScreenState(int state) {
                this.mDelegate.setDozeScreenState(state);
            }

            @Override // com.android.systemui.doze.DozeMachine.Service
            public void requestWakeUp() {
                this.mDelegate.requestWakeUp();
            }

            @Override // com.android.systemui.doze.DozeMachine.Service
            public void setDozeScreenBrightness(int brightness) {
                this.mDelegate.setDozeScreenBrightness(brightness);
            }
        }
    }
}
