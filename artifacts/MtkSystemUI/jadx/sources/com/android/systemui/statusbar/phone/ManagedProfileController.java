package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.policy.CallbackController;

/* JADX INFO: loaded from: classes.dex */
public interface ManagedProfileController extends CallbackController<Callback> {

    public interface Callback {
        void onManagedProfileChanged();

        void onManagedProfileRemoved();
    }

    boolean hasActiveProfile();
}
