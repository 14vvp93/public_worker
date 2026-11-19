package com.android.settingslib.wrapper;

import android.location.LocationManager;
import android.os.UserHandle;

/* JADX INFO: loaded from: classes.dex */
public class LocationManagerWrapper {
    private LocationManager mLocationManager;

    public LocationManagerWrapper(LocationManager locationManager) {
        this.mLocationManager = locationManager;
    }

    public void setLocationEnabledForUser(boolean enabled, UserHandle userHandle) {
        this.mLocationManager.setLocationEnabledForUser(enabled, userHandle);
    }
}
