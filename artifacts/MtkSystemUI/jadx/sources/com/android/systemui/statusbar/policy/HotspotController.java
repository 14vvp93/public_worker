package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;

/* JADX INFO: loaded from: classes.dex */
public interface HotspotController extends Dumpable, CallbackController<Callback> {

    public interface Callback {
        void onHotspotChanged(boolean z, int i);
    }

    boolean isHotspotEnabled();
}
