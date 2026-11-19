package com.android.systemui.statusbar.policy;

/* JADX INFO: loaded from: classes.dex */
public interface LocationController extends CallbackController<LocationChangeCallback> {
    boolean isLocationActive();

    boolean isLocationEnabled();

    boolean setLocationEnabled(boolean z);

    public interface LocationChangeCallback {
        default void onLocationActiveChanged(boolean active) {
        }

        default void onLocationSettingsChanged(boolean locationEnabled) {
        }
    }
}
