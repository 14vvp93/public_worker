package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.MathUtils;
import android.util.SparseBooleanArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.hardware.AmbientDisplayConfiguration;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.doze.AlwaysOnDisplayPolicy;
import com.android.systemui.tuner.TunerService;

/* JADX INFO: loaded from: classes.dex */
public class DozeParameters implements TunerService.Tunable {
    public static final boolean FORCE_NO_BLANKING = SystemProperties.getBoolean("debug.force_no_blanking", false);
    private static DozeParameters sInstance;
    private static IntInOutMatcher sPickupSubtypePerformsProxMatcher;
    private final AlwaysOnDisplayPolicy mAlwaysOnPolicy;
    private final AmbientDisplayConfiguration mAmbientDisplayConfiguration;
    private final Context mContext;
    private boolean mControlScreenOffAnimation = !getDisplayNeedsBlanking();
    private boolean mDozeAlwaysOn;
    private final PowerManager mPowerManager;

    public static DozeParameters getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DozeParameters(context);
        }
        return sInstance;
    }

    @VisibleForTesting
    protected DozeParameters(Context context) {
        this.mContext = context.getApplicationContext();
        this.mAmbientDisplayConfiguration = new AmbientDisplayConfiguration(this.mContext);
        this.mAlwaysOnPolicy = new AlwaysOnDisplayPolicy(this.mContext);
        this.mPowerManager = (PowerManager) this.mContext.getSystemService(PowerManager.class);
        this.mPowerManager.setDozeAfterScreenOff(!this.mControlScreenOffAnimation);
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "doze_always_on", "accessibility_display_inversion_enabled");
    }

    public boolean getDisplayStateSupported() {
        return getBoolean("doze.display.supported", R.bool.doze_display_state_supported);
    }

    public boolean getDozeSuspendDisplayStateSupported() {
        return this.mContext.getResources().getBoolean(R.bool.doze_suspend_display_state_supported);
    }

    public float getScreenBrightnessDoze() {
        return this.mContext.getResources().getInteger(android.R.integer.config_doubleTapPowerGestureMode) / 255.0f;
    }

    public int getPulseVisibleDuration() {
        return getInt("doze.pulse.duration.visible", R.integer.doze_pulse_duration_visible);
    }

    public boolean getPulseOnSigMotion() {
        return getBoolean("doze.pulse.sigmotion", R.bool.doze_pulse_on_significant_motion);
    }

    public boolean getProxCheckBeforePulse() {
        return getBoolean("doze.pulse.proxcheck", R.bool.doze_proximity_check_before_pulse);
    }

    public int getPickupVibrationThreshold() {
        return getInt("doze.pickup.vibration.threshold", R.integer.doze_pickup_vibration_threshold);
    }

    public long getWallpaperAodDuration() {
        if (shouldControlScreenOff()) {
            return 4500L;
        }
        return this.mAlwaysOnPolicy.wallpaperVisibilityDuration;
    }

    public long getWallpaperFadeOutDuration() {
        return this.mAlwaysOnPolicy.wallpaperFadeOutDuration;
    }

    public boolean getAlwaysOn() {
        return this.mDozeAlwaysOn;
    }

    public boolean getDisplayNeedsBlanking() {
        return !FORCE_NO_BLANKING && this.mContext.getResources().getBoolean(android.R.^attr-private.dropdownListPreferredItemHeight);
    }

    public boolean shouldControlScreenOff() {
        return this.mControlScreenOffAnimation;
    }

    public void setControlScreenOffAnimation(boolean controlScreenOffAnimation) {
        if (this.mControlScreenOffAnimation == controlScreenOffAnimation) {
            return;
        }
        this.mControlScreenOffAnimation = controlScreenOffAnimation;
        getPowerManager().setDozeAfterScreenOff(!controlScreenOffAnimation);
    }

    @VisibleForTesting
    protected PowerManager getPowerManager() {
        return this.mPowerManager;
    }

    private boolean getBoolean(String propName, int resId) {
        return SystemProperties.getBoolean(propName, this.mContext.getResources().getBoolean(resId));
    }

    private int getInt(String propName, int resId) {
        int value = SystemProperties.getInt(propName, this.mContext.getResources().getInteger(resId));
        return MathUtils.constrain(value, 0, 60000);
    }

    private String getString(String propName, int resId) {
        return SystemProperties.get(propName, this.mContext.getString(resId));
    }

    public boolean getPickupSubtypePerformsProxCheck(int subType) {
        String spec = getString("doze.pickup.proxcheck", R.string.doze_pickup_subtype_performs_proximity_check);
        if (TextUtils.isEmpty(spec)) {
            return this.mContext.getResources().getBoolean(R.bool.doze_pickup_performs_proximity_check);
        }
        if (sPickupSubtypePerformsProxMatcher == null || !TextUtils.equals(spec, sPickupSubtypePerformsProxMatcher.mSpec)) {
            sPickupSubtypePerformsProxMatcher = new IntInOutMatcher(spec);
        }
        return sPickupSubtypePerformsProxMatcher.isIn(subType);
    }

    public int getPulseVisibleDurationExtended() {
        return 2 * getPulseVisibleDuration();
    }

    public boolean doubleTapReportsTouchCoordinates() {
        return this.mContext.getResources().getBoolean(R.bool.doze_double_tap_reports_touch_coordinates);
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        this.mDozeAlwaysOn = this.mAmbientDisplayConfiguration.alwaysOnEnabled(-2);
    }

    public AlwaysOnDisplayPolicy getPolicy() {
        return this.mAlwaysOnPolicy;
    }

    public static class IntInOutMatcher {
        private final boolean mDefaultIsIn;
        private final SparseBooleanArray mIsIn;
        final String mSpec;

        public IntInOutMatcher(String spec) {
            if (TextUtils.isEmpty(spec)) {
                throw new IllegalArgumentException("Spec must not be empty");
            }
            boolean foundWildcard = false;
            this.mSpec = spec;
            this.mIsIn = new SparseBooleanArray();
            boolean defaultIsIn = false;
            for (String itemPrefixed : spec.split(",", -1)) {
                if (itemPrefixed.length() == 0) {
                    throw new IllegalArgumentException("Illegal spec, must not have zero-length items: `" + spec + "`");
                }
                boolean isIn = itemPrefixed.charAt(0) != '!';
                String item = isIn ? itemPrefixed : itemPrefixed.substring(1);
                if (itemPrefixed.length() == 0) {
                    throw new IllegalArgumentException("Illegal spec, must not have zero-length items: `" + spec + "`");
                }
                if ("*".equals(item)) {
                    if (foundWildcard) {
                        throw new IllegalArgumentException("Illegal spec, `*` must not appear multiple times in `" + spec + "`");
                    }
                    defaultIsIn = isIn;
                    foundWildcard = true;
                } else {
                    int key = Integer.parseInt(item);
                    if (this.mIsIn.indexOfKey(key) >= 0) {
                        throw new IllegalArgumentException("Illegal spec, `" + key + "` must not appear multiple times in `" + spec + "`");
                    }
                    this.mIsIn.put(key, isIn);
                }
            }
            if (!foundWildcard) {
                throw new IllegalArgumentException("Illegal spec, must specify either * or !*");
            }
            this.mDefaultIsIn = defaultIsIn;
        }

        public boolean isIn(int value) {
            return this.mIsIn.get(value, this.mDefaultIsIn);
        }
    }
}
