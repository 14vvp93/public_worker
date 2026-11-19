package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.TimeUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.Interpolators;
import com.android.systemui.SysUiServiceProvider;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.KeyguardMonitor;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class LightBarTransitionsController implements Dumpable, CommandQueue.Callbacks {
    private final DarkIntensityApplier mApplier;
    private float mDarkIntensity;
    private float mNextDarkIntensity;
    private float mPendingDarkIntensity;
    private ValueAnimator mTintAnimator;
    private boolean mTintChangePending;
    private boolean mTransitionDeferring;
    private long mTransitionDeferringDuration;
    private long mTransitionDeferringStartTime;
    private boolean mTransitionPending;
    private final Runnable mTransitionDeferringDoneRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.LightBarTransitionsController.1
        @Override // java.lang.Runnable
        public void run() {
            LightBarTransitionsController.this.mTransitionDeferring = false;
        }
    };
    private final Handler mHandler = new Handler();
    private final KeyguardMonitor mKeyguardMonitor = (KeyguardMonitor) Dependency.get(KeyguardMonitor.class);

    public interface DarkIntensityApplier {
        void applyDarkIntensity(float f);
    }

    public LightBarTransitionsController(Context context, DarkIntensityApplier applier) {
        this.mApplier = applier;
        ((CommandQueue) SysUiServiceProvider.getComponent(context, CommandQueue.class)).addCallbacks(this);
    }

    public void destroy(Context context) {
        ((CommandQueue) SysUiServiceProvider.getComponent(context, CommandQueue.class)).removeCallbacks(this);
    }

    public void saveState(Bundle outState) {
        float intensity = (this.mTintAnimator == null || !this.mTintAnimator.isRunning()) ? this.mDarkIntensity : this.mNextDarkIntensity;
        outState.putFloat("dark_intensity", intensity);
    }

    public void restoreState(Bundle savedInstanceState) {
        setIconTintInternal(savedInstanceState.getFloat("dark_intensity", 0.0f));
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void appTransitionPending(boolean forced) {
        if (this.mKeyguardMonitor.isKeyguardGoingAway() && !forced) {
            return;
        }
        this.mTransitionPending = true;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void appTransitionCancelled() {
        if (this.mTransitionPending && this.mTintChangePending) {
            this.mTintChangePending = false;
            animateIconTint(this.mPendingDarkIntensity, 0L, 120L);
        }
        this.mTransitionPending = false;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public void appTransitionStarting(long startTime, long duration, boolean forced) {
        if (this.mKeyguardMonitor.isKeyguardGoingAway() && !forced) {
            return;
        }
        if (this.mTransitionPending && this.mTintChangePending) {
            this.mTintChangePending = false;
            animateIconTint(this.mPendingDarkIntensity, Math.max(0L, startTime - SystemClock.uptimeMillis()), duration);
        } else if (this.mTransitionPending) {
            this.mTransitionDeferring = true;
            this.mTransitionDeferringStartTime = startTime;
            this.mTransitionDeferringDuration = duration;
            this.mHandler.removeCallbacks(this.mTransitionDeferringDoneRunnable);
            this.mHandler.postAtTime(this.mTransitionDeferringDoneRunnable, startTime);
        }
        this.mTransitionPending = false;
    }

    public void setIconsDark(boolean dark, boolean animate) {
        if (!animate) {
            setIconTintInternal(dark ? 1.0f : 0.0f);
            this.mNextDarkIntensity = dark ? 1.0f : 0.0f;
        } else if (this.mTransitionPending) {
            deferIconTintChange(dark ? 1.0f : 0.0f);
        } else if (this.mTransitionDeferring) {
            animateIconTint(dark ? 1.0f : 0.0f, Math.max(0L, this.mTransitionDeferringStartTime - SystemClock.uptimeMillis()), this.mTransitionDeferringDuration);
        } else {
            animateIconTint(dark ? 1.0f : 0.0f, 0L, 120L);
        }
    }

    public float getCurrentDarkIntensity() {
        return this.mDarkIntensity;
    }

    private void deferIconTintChange(float darkIntensity) {
        if (this.mTintChangePending && darkIntensity == this.mPendingDarkIntensity) {
            return;
        }
        this.mTintChangePending = true;
        this.mPendingDarkIntensity = darkIntensity;
    }

    private void animateIconTint(float targetDarkIntensity, long delay, long duration) {
        if (this.mNextDarkIntensity == targetDarkIntensity) {
            return;
        }
        if (this.mTintAnimator != null) {
            this.mTintAnimator.cancel();
        }
        this.mNextDarkIntensity = targetDarkIntensity;
        this.mTintAnimator = ValueAnimator.ofFloat(this.mDarkIntensity, targetDarkIntensity);
        this.mTintAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$LightBarTransitionsController$PJRveQsGC7aANrqdSv3tRYb3x7c
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.setIconTintInternal(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        this.mTintAnimator.setDuration(duration);
        this.mTintAnimator.setStartDelay(delay);
        this.mTintAnimator.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        this.mTintAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIconTintInternal(float darkIntensity) {
        this.mDarkIntensity = darkIntensity;
        this.mApplier.applyDarkIntensity(darkIntensity);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.print("  mTransitionDeferring=");
        pw.print(this.mTransitionDeferring);
        if (this.mTransitionDeferring) {
            pw.println();
            pw.print("   mTransitionDeferringStartTime=");
            pw.println(TimeUtils.formatUptime(this.mTransitionDeferringStartTime));
            pw.print("   mTransitionDeferringDuration=");
            TimeUtils.formatDuration(this.mTransitionDeferringDuration, pw);
            pw.println();
        }
        pw.print("  mTransitionPending=");
        pw.print(this.mTransitionPending);
        pw.print(" mTintChangePending=");
        pw.println(this.mTintChangePending);
        pw.print("  mPendingDarkIntensity=");
        pw.print(this.mPendingDarkIntensity);
        pw.print(" mDarkIntensity=");
        pw.print(this.mDarkIntensity);
        pw.print(" mNextDarkIntensity=");
        pw.println(this.mNextDarkIntensity);
    }
}
