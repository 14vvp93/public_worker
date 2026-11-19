package com.android.systemui.statusbar;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class DZ23CanStatusBarController {
    private SparseArray<String> CanMap;
    private final String[] gear = {"N", "R", "D"};
    private WeakReference<Context> mContext;
    private View mFragmentView;
    private WeakReference<Handler> mHandler;

    public DZ23CanStatusBarController(Context ctx) {
        this.mContext = new WeakReference<>(ctx);
    }

    public void setNavigationBarFragment(View fragmentView, Handler handler) {
        this.mFragmentView = fragmentView;
        this.mHandler = new WeakReference<>(handler);
        this.CanMap = new SparseArray<>();
        this.CanMap.put(R.id.iv_light_left, "car_left_signal");
        this.CanMap.put(R.id.iv_light_far, "car_high_beam");
        this.CanMap.put(R.id.iv_light_fog_front, "car_front_fog_light");
        this.CanMap.put(R.id.iv_light_fog_back, "car_rear_fog_light");
        this.CanMap.put(R.id.iv_light_right, "car_right_signal");
        this.CanMap.put(R.id.iv_light_normal, "car_head_light");
        this.CanMap.put(R.id.iv_light_dipped, "car_low_beam");
        this.CanMap.put(R.id.iv_door_lf, "persist.vendor.cem.cardoor1");
        this.CanMap.put(R.id.iv_door_lb, "persist.vendor.cem.cardoor3");
        this.CanMap.put(R.id.iv_door_rf, "persist.vendor.cem.cardoor2");
        this.CanMap.put(R.id.iv_door_rb, "persist.vendor.cem.cardoor4");
        this.CanMap.put(R.id.iv_brake_failure, "car_brake_failure");
        this.CanMap.put(R.id.iv_parking_brake, "car_parking_brake");
        this.CanMap.put(R.id.iv_esp, "car_eps_alarm");
        this.CanMap.put(R.id.iv_wheel_lock, "car_steering_fault");
        this.CanMap.put(R.id.iv_abs, "car_abs_indication");
        this.CanMap.put(R.id.iv_dcdc_error, "car_dcdc_fault_indication");
        this.CanMap.put(R.id.iv_power_battery_error, "car_power_battery_fault");
        this.CanMap.put(R.id.iv_power_battery_low, "car_low_battery_alarm");
        this.CanMap.put(R.id.iv_power_battery_charging, "car_charging_status_indication");
        this.CanMap.put(R.id.iv_battery_disconnect, "car_battery_disconnect");
        this.CanMap.put(R.id.iv_battery_temp, "car_battery_temp_abnormal_indication");
        this.CanMap.put(R.id.iv_battery_insulation, "car_battery_insulation_fault_indication");
        this.CanMap.put(R.id.iv_belt, "car_seat_belt");
        this.CanMap.put(R.id.iv_turtle, "car_power_derate");
        this.CanMap.put(R.id.iv_refresh, "car_braking_energy_recovery_indication");
        this.CanMap.put(R.id.iv_plug, "car_charging_connection");
        this.CanMap.put(R.id.iv_engine_warning, "car_motor_fault_indication");
        this.CanMap.put(R.id.iv_engine_heat, "car_motor_overheat_indication");
    }

    public void setUp() {
        int i = 0;
        while (true) {
            if (i >= this.CanMap.size()) {
                ((TextView) this.mFragmentView.findViewById(R.id.tv_gear)).setText(this.gear[Settings.System.getInt(this.mContext.get().getContentResolver(), "car_manual", 0)]);
                this.mContext.get().getContentResolver().registerContentObserver(Settings.System.getUriFor("car_manual"), true, (ContentObserver) Objects.requireNonNull(buildGearObserver((TextView) this.mFragmentView.findViewById(R.id.tv_gear))), -1);
                this.mContext.get().getContentResolver().registerContentObserver(Settings.System.getUriFor("car_speed_value"), true, (ContentObserver) Objects.requireNonNull(buildSpeedObserver((TextView) this.mFragmentView.findViewById(R.id.tv_speed))), -1);
                return;
            } else {
                ImageView ivTemp = (ImageView) this.mFragmentView.findViewById(this.CanMap.keyAt(i));
                if (ivTemp != null) {
                    boolean isShow = Settings.System.getInt(this.mContext.get().getContentResolver(), this.CanMap.valueAt(i), 0) == 1;
                    ivTemp.setVisibility(isShow ? 0 : 4);
                    regist(ivTemp, this.CanMap.valueAt(i));
                }
                i++;
            }
        }
    }

    private void regist(ImageView imageView, String target) {
        this.mContext.get().getContentResolver().registerContentObserver(Settings.System.getUriFor(target), true, (ContentObserver) Objects.requireNonNull(buildNormalObserver(imageView, target)), -1);
    }

    private ContentObserver buildNormalObserver(final ImageView imageView, final String target) {
        if (this.mHandler.get() == null || this.mContext.get() == null || imageView == null) {
            return null;
        }
        return new ContentObserver(this.mHandler.get()) { // from class: com.android.systemui.statusbar.DZ23CanStatusBarController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                if (DZ23CanStatusBarController.this.mContext.get() != null && imageView != null) {
                    boolean isShow = Settings.System.getInt(((Context) DZ23CanStatusBarController.this.mContext.get()).getContentResolver(), target, 0) == 1;
                    imageView.setVisibility(isShow ? 0 : 4);
                }
            }
        };
    }

    private ContentObserver buildGearObserver(final TextView tvGear) {
        if (this.mHandler.get() == null || this.mContext.get() == null || tvGear == null) {
            return null;
        }
        return new ContentObserver(this.mHandler.get()) { // from class: com.android.systemui.statusbar.DZ23CanStatusBarController.2
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                int gearValue;
                if (DZ23CanStatusBarController.this.mContext.get() != null && tvGear != null && (gearValue = Settings.System.getInt(((Context) DZ23CanStatusBarController.this.mContext.get()).getContentResolver(), "car_manual", 0)) <= 2 && gearValue >= 0) {
                    tvGear.setText(DZ23CanStatusBarController.this.gear[gearValue]);
                }
            }
        };
    }

    private ContentObserver buildSpeedObserver(final TextView tvSpeed) {
        if (this.mHandler.get() == null || this.mContext.get() == null || tvSpeed == null) {
            return null;
        }
        return new ContentObserver(this.mHandler.get()) { // from class: com.android.systemui.statusbar.DZ23CanStatusBarController.3
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                int speed;
                if (DZ23CanStatusBarController.this.mContext.get() == null || tvSpeed == null || (speed = Settings.System.getInt(((Context) DZ23CanStatusBarController.this.mContext.get()).getContentResolver(), "car_speed_value", 0)) > 255 || speed < 0) {
                    return;
                }
                tvSpeed.setText(String.valueOf(speed));
            }
        };
    }
}
