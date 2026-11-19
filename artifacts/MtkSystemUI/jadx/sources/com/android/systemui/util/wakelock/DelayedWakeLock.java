package com.android.systemui.util.wakelock;

import android.os.Handler;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class DelayedWakeLock implements WakeLock {
    private final Handler mHandler;
    private final WakeLock mInner;
    private final Runnable mRelease;

    public DelayedWakeLock(Handler h, WakeLock inner) {
        this.mHandler = h;
        this.mInner = inner;
        final WakeLock wakeLock = this.mInner;
        Objects.requireNonNull(wakeLock);
        this.mRelease = new Runnable() { // from class: com.android.systemui.util.wakelock.-$$Lambda$CFIjGRHyMGVbSujKFcwVsXltENg
            @Override // java.lang.Runnable
            public final void run() {
                wakeLock.release();
            }
        };
    }

    @Override // com.android.systemui.util.wakelock.WakeLock
    public void acquire() {
        this.mInner.acquire();
    }

    @Override // com.android.systemui.util.wakelock.WakeLock
    public void release() {
        this.mHandler.postDelayed(this.mRelease, 140L);
    }

    @Override // com.android.systemui.util.wakelock.WakeLock
    public Runnable wrap(Runnable r) {
        return WakeLock.wrapImpl(this, r);
    }
}
