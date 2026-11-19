package com.hcn.auto.systemui;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class IconFactory extends BaseIconFactory {
    private static IconFactory sPool;
    private final int mPoolId;
    private IconFactory next;
    private static final Object sPoolSync = new Object();
    private static int sPoolId = 0;

    public static IconFactory obtain(Context context, int iconBitmapSize) {
        synchronized (sPoolSync) {
            if (sPool != null) {
                IconFactory m = sPool;
                sPool = m.next;
                m.next = null;
                return m;
            }
            int poolId = sPoolId;
            return new IconFactory(context, context.getResources().getConfiguration().densityDpi, iconBitmapSize, poolId);
        }
    }

    private IconFactory(Context context, int fillResIconDpi, int iconBitmapSize, int poolId) {
        super(context, fillResIconDpi, iconBitmapSize);
        this.mPoolId = poolId;
    }

    public void recycle() {
        synchronized (sPoolSync) {
            if (sPoolId == this.mPoolId) {
                clear();
                this.next = sPool;
                sPool = this;
            }
        }
    }

    @Override // com.hcn.auto.systemui.BaseIconFactory, java.lang.AutoCloseable
    public void close() {
        recycle();
    }
}
