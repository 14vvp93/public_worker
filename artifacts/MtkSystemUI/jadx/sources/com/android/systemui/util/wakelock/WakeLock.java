package com.android.systemui.util.wakelock;

import android.content.Context;
import android.os.PowerManager;

/* JADX INFO: loaded from: classes.dex */
public interface WakeLock {
    void acquire();

    void release();

    Runnable wrap(Runnable runnable);

    static WakeLock createPartial(Context context, String tag) {
        return wrap(createPartialInner(context, tag));
    }

    static PowerManager.WakeLock createPartialInner(Context context, String tag) {
        return ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(1, tag);
    }

    static Runnable wrapImpl(final WakeLock w, final Runnable r) {
        w.acquire();
        return new Runnable() { // from class: com.android.systemui.util.wakelock.-$$Lambda$WakeLock$Rdut1DSGlHtP-OM8Y87P7galvtM
            @Override // java.lang.Runnable
            public final void run() {
                WakeLock.lambda$wrapImpl$0(r, w);
            }
        };
    }

    static /* synthetic */ void lambda$wrapImpl$0(Runnable r, WakeLock w) {
        try {
            r.run();
        } finally {
            w.release();
        }
    }

    static WakeLock wrap(final PowerManager.WakeLock inner) {
        return new WakeLock() { // from class: com.android.systemui.util.wakelock.WakeLock.1
            @Override // com.android.systemui.util.wakelock.WakeLock
            public void acquire() {
                inner.acquire();
            }

            @Override // com.android.systemui.util.wakelock.WakeLock
            public void release() {
                inner.release();
            }

            @Override // com.android.systemui.util.wakelock.WakeLock
            public Runnable wrap(Runnable runnable) {
                return WakeLock.wrapImpl(this, runnable);
            }
        };
    }
}
