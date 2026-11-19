package com.android.systemui.statusbar.notification;

import android.util.Pools;

/* JADX INFO: loaded from: classes.dex */
public class ProgressTransformState extends TransformState {
    private static Pools.SimplePool<ProgressTransformState> sInstancePool = new Pools.SimplePool<>(40);

    @Override // com.android.systemui.statusbar.notification.TransformState
    protected boolean sameAs(TransformState otherState) {
        if (otherState instanceof ProgressTransformState) {
            return true;
        }
        return super.sameAs(otherState);
    }

    public static ProgressTransformState obtain() {
        ProgressTransformState instance = (ProgressTransformState) sInstancePool.acquire();
        if (instance != null) {
            return instance;
        }
        return new ProgressTransformState();
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public void recycle() {
        super.recycle();
        sInstancePool.release(this);
    }
}
