package com.android.systemui.statusbar.policy;

/* JADX INFO: loaded from: classes.dex */
public interface DataSaverController extends CallbackController<Listener> {

    public interface Listener {
        void onDataSaverChanged(boolean z);
    }
}
