package com.android.systemui.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.AlarmTimeout;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class DozeUi implements DozeMachine.Part {
    private final boolean mCanAnimateTransition;
    private final Context mContext;
    private final DozeParameters mDozeParameters;
    private final Handler mHandler;
    private final DozeHost mHost;
    private boolean mKeyguardShowing;
    private final KeyguardUpdateMonitorCallback mKeyguardVisibilityCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.doze.DozeUi.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onKeyguardVisibilityChanged(boolean showing) {
            DozeUi.this.mKeyguardShowing = showing;
            DozeUi.this.updateAnimateScreenOff();
        }
    };
    private long mLastTimeTickElapsed = 0;
    private final DozeMachine mMachine;
    private final AlarmTimeout mTimeTicker;
    private final WakeLock mWakeLock;

    public DozeUi(Context context, AlarmManager alarmManager, DozeMachine machine, WakeLock wakeLock, DozeHost host, Handler handler, DozeParameters params, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mContext = context;
        this.mMachine = machine;
        this.mWakeLock = wakeLock;
        this.mHost = host;
        this.mHandler = handler;
        this.mCanAnimateTransition = !params.getDisplayNeedsBlanking();
        this.mDozeParameters = params;
        this.mTimeTicker = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.android.systemui.doze.-$$Lambda$DozeUi$FO90hbI6xqXYUh2DtwuwM-uzJzs
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                this.f$0.onTimeTick();
            }
        }, "doze_time_tick", handler);
        keyguardUpdateMonitor.registerCallback(this.mKeyguardVisibilityCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAnimateScreenOff() {
        if (this.mCanAnimateTransition) {
            boolean controlScreenOff = this.mDozeParameters.getAlwaysOn() && this.mKeyguardShowing;
            this.mDozeParameters.setControlScreenOffAnimation(controlScreenOff);
            this.mHost.setAnimateScreenOff(controlScreenOff);
        }
    }

    private void pulseWhileDozing(int reason) {
        this.mHost.pulseWhileDozing(new DozeHost.PulseCallback() { // from class: com.android.systemui.doze.DozeUi.2
            @Override // com.android.systemui.doze.DozeHost.PulseCallback
            public void onPulseStarted() {
                DozeUi.this.mMachine.requestState(DozeMachine.State.DOZE_PULSING);
            }

            @Override // com.android.systemui.doze.DozeHost.PulseCallback
            public void onPulseFinished() {
                DozeUi.this.mMachine.requestState(DozeMachine.State.DOZE_PULSE_DONE);
            }
        }, reason);
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State oldState, DozeMachine.State newState) {
        switch (newState) {
            case DOZE_AOD:
                if (oldState == DozeMachine.State.DOZE_AOD_PAUSED) {
                    this.mHost.dozeTimeTick();
                    Handler handler = this.mHandler;
                    WakeLock wakeLock = this.mWakeLock;
                    final DozeHost dozeHost = this.mHost;
                    Objects.requireNonNull(dozeHost);
                    handler.postDelayed(wakeLock.wrap(new Runnable() { // from class: com.android.systemui.doze.-$$Lambda$TvDuFxrq6WnRSNRP7k8oBY4uOBc
                        @Override // java.lang.Runnable
                        public final void run() {
                            dozeHost.dozeTimeTick();
                        }
                    }), 100L);
                    Handler handler2 = this.mHandler;
                    WakeLock wakeLock2 = this.mWakeLock;
                    final DozeHost dozeHost2 = this.mHost;
                    Objects.requireNonNull(dozeHost2);
                    handler2.postDelayed(wakeLock2.wrap(new Runnable() { // from class: com.android.systemui.doze.-$$Lambda$TvDuFxrq6WnRSNRP7k8oBY4uOBc
                        @Override // java.lang.Runnable
                        public final void run() {
                            dozeHost2.dozeTimeTick();
                        }
                    }), 1000L);
                }
                scheduleTimeTick();
                break;
            case DOZE_AOD_PAUSING:
                scheduleTimeTick();
                break;
            case DOZE:
            case DOZE_AOD_PAUSED:
                unscheduleTimeTick();
                break;
            case DOZE_REQUEST_PULSE:
                pulseWhileDozing(this.mMachine.getPulseReason());
                break;
            case INITIALIZED:
                this.mHost.startDozing();
                break;
            case FINISH:
                this.mHost.stopDozing();
                unscheduleTimeTick();
                break;
        }
        updateAnimateWakeup(newState);
    }

    private void updateAnimateWakeup(DozeMachine.State state) {
        int i = AnonymousClass3.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state.ordinal()];
        if (i != 5) {
            switch (i) {
                case 7:
                    break;
                case 8:
                case 9:
                    break;
                default:
                    this.mHost.setAnimateWakeup(this.mCanAnimateTransition && this.mDozeParameters.getAlwaysOn());
                    break;
            }
        }
        this.mHost.setAnimateWakeup(true);
    }

    private void scheduleTimeTick() {
        if (this.mTimeTicker.isScheduled()) {
            return;
        }
        long delta = roundToNextMinute(System.currentTimeMillis()) - System.currentTimeMillis();
        this.mTimeTicker.schedule(delta, 1);
        this.mLastTimeTickElapsed = SystemClock.elapsedRealtime();
    }

    private void unscheduleTimeTick() {
        if (!this.mTimeTicker.isScheduled()) {
            return;
        }
        verifyLastTimeTick();
        this.mTimeTicker.cancel();
    }

    private void verifyLastTimeTick() {
        long millisSinceLastTick = SystemClock.elapsedRealtime() - this.mLastTimeTickElapsed;
        if (millisSinceLastTick > 90000) {
            String delay = Formatter.formatShortElapsedTime(this.mContext, millisSinceLastTick);
            DozeLog.traceMissedTick(delay);
            Log.e("DozeMachine", "Missed AOD time tick by " + delay);
        }
    }

    private long roundToNextMinute(long timeInMillis) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.add(12, 1);
        return calendar.getTimeInMillis();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTimeTick() {
        verifyLastTimeTick();
        this.mHost.dozeTimeTick();
        this.mHandler.post(this.mWakeLock.wrap(new Runnable() { // from class: com.android.systemui.doze.-$$Lambda$DozeUi$lHTcknku1GKi6pFF17CHlz1K3H8
            @Override // java.lang.Runnable
            public final void run() {
                DozeUi.lambda$onTimeTick$0();
            }
        }));
        scheduleTimeTick();
    }

    static /* synthetic */ void lambda$onTimeTick$0() {
    }

    @VisibleForTesting
    KeyguardUpdateMonitorCallback getKeyguardCallback() {
        return this.mKeyguardVisibilityCallback;
    }
}
