package com.android.systemui.statusbar.car.hvac;

import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.hvac.CarHvacManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class HvacController {
    private Car mCar;
    private Context mContext;
    private Handler mHandler;
    private CarHvacManager mHvacManager;
    private HashMap<HvacKey, List<TemperatureView>> mTempComponents = new HashMap<>();
    private ServiceConnection mServiceConnection = new ServiceConnection() { // from class: com.android.systemui.statusbar.car.hvac.HvacController.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                service.linkToDeath(HvacController.this.mRestart, 0);
                HvacController.this.mHvacManager = (CarHvacManager) HvacController.this.mCar.getCarManager("hvac");
                HvacController.this.mHvacManager.registerCallback(HvacController.this.mHardwareCallback);
                HvacController.this.initComponents();
            } catch (Exception e) {
                Log.e("HvacController", "Failed to correctly connect to HVAC", e);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            HvacController.this.destroyHvacManager();
        }
    };
    private final IBinder.DeathRecipient mRestart = new AnonymousClass2();
    private final CarHvacManager.CarHvacEventCallback mHardwareCallback = new CarHvacManager.CarHvacEventCallback() { // from class: com.android.systemui.statusbar.car.hvac.HvacController.3
        public void onChangeEvent(CarPropertyValue val) {
            try {
                int areaId = val.getAreaId();
                int propertyId = val.getPropertyId();
                List<TemperatureView> temperatureViews = (List) HvacController.this.mTempComponents.get(new HvacKey(propertyId, areaId));
                if (temperatureViews != null && !temperatureViews.isEmpty()) {
                    float value = ((Float) val.getValue()).floatValue();
                    for (TemperatureView tempView : temperatureViews) {
                        tempView.setTemp(value);
                    }
                }
            } catch (Exception e) {
                Log.e("HvacController", "Failed handling hvac change event", e);
            }
        }

        public void onErrorEvent(int propertyId, int zone) {
            Log.d("HvacController", "HVAC error event, propertyId: " + propertyId + " zone: " + zone);
        }
    };

    public HvacController(Context context) {
        this.mContext = context;
    }

    public void connectToCarService() {
        this.mHandler = new Handler();
        this.mCar = Car.createCar(this.mContext, this.mServiceConnection, this.mHandler);
        if (this.mCar != null) {
            this.mCar.connect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroyHvacManager() {
        if (this.mHvacManager != null) {
            this.mHvacManager.unregisterCallback(this.mHardwareCallback);
            this.mHvacManager = null;
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.car.hvac.HvacController$2, reason: invalid class name */
    class AnonymousClass2 implements IBinder.DeathRecipient {
        AnonymousClass2() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.d("HvacController", "Death of HVAC triggering a restart");
            if (HvacController.this.mCar != null) {
                HvacController.this.mCar.disconnect();
            }
            HvacController.this.destroyHvacManager();
            HvacController.this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.car.hvac.-$$Lambda$HvacController$2$iHT8Tpg8uVwfmNfI8JJMJYOK4tk
                @Override // java.lang.Runnable
                public final void run() {
                    HvacController.this.mCar.connect();
                }
            }, 5000L);
        }
    }

    public void addHvacTextView(TemperatureView temperatureView) {
        HvacKey hvacKey = new HvacKey(temperatureView.getPropertyId(), temperatureView.getAreaId());
        if (!this.mTempComponents.containsKey(hvacKey)) {
            this.mTempComponents.put(hvacKey, new ArrayList());
        }
        this.mTempComponents.get(hvacKey).add(temperatureView);
        initComponent(temperatureView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initComponents() {
        for (Map.Entry<HvacKey, List<TemperatureView>> next : this.mTempComponents.entrySet()) {
            List<TemperatureView> temperatureViews = next.getValue();
            for (TemperatureView view : temperatureViews) {
                initComponent(view);
            }
        }
    }

    private void initComponent(TemperatureView view) {
        int id = view.getPropertyId();
        int zone = view.getAreaId();
        try {
            if (this.mHvacManager != null && this.mHvacManager.isPropertyAvailable(id, zone)) {
                view.setTemp(this.mHvacManager.getFloatProperty(id, zone));
                return;
            }
            view.setTemp(Float.NaN);
        } catch (CarNotConnectedException e) {
            view.setTemp(Float.NaN);
            Log.e("HvacController", "Failed to get value from hvac service", e);
        }
    }

    public void removeAllComponents() {
        this.mTempComponents.clear();
    }

    private static class HvacKey {
        int mAreaId;
        int mPropertyId;

        public HvacKey(int propertyId, int areaId) {
            this.mPropertyId = propertyId;
            this.mAreaId = areaId;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            HvacKey hvacKey = (HvacKey) o;
            if (this.mPropertyId == hvacKey.mPropertyId && this.mAreaId == hvacKey.mAreaId) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mPropertyId), Integer.valueOf(this.mAreaId));
        }
    }
}
