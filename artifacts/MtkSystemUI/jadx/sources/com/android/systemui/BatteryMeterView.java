package com.android.systemui;

import android.animation.ArgbEvaluator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.settingslib.graph.BatteryMeterDrawableBase;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;
import com.android.systemui.statusbar.policy.IconLogger;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Utils;
import java.text.NumberFormat;

/* JADX INFO: loaded from: classes.dex */
public class BatteryMeterView extends LinearLayout implements BatteryController.BatteryStateChangeCallback, ConfigurationController.ConfigurationListener, DarkIconDispatcher.DarkReceiver, TunerService.Tunable {
    private BatteryController mBatteryController;
    private final ImageView mBatteryIconView;
    private TextView mBatteryPercentView;
    private float mDarkIntensity;
    private int mDarkModeBackgroundColor;
    private int mDarkModeFillColor;
    private final BatteryMeterDrawableBase mDrawable;
    private boolean mForceShowPercent;
    private int mLevel;
    private int mLightModeBackgroundColor;
    private int mLightModeFillColor;
    private int mNonAdaptedBackgroundColor;
    private int mNonAdaptedForegroundColor;
    private SettingObserver mSettingObserver;
    private final String mSlotBattery;
    private int mTextColor;
    private boolean mUseWallpaperTextColors;
    private int mUser;
    private final CurrentUserTracker mUserTracker;

    public BatteryMeterView(Context context) {
        this(context, null, 0);
    }

    public BatteryMeterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatteryMeterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(0);
        setGravity(8388627);
        TypedArray atts = context.obtainStyledAttributes(attrs, R.styleable.BatteryMeterView, defStyle, 0);
        int frameColor = atts.getColor(0, context.getColor(R.color.meter_background_color));
        this.mDrawable = new BatteryMeterDrawableBase(context, frameColor);
        atts.recycle();
        this.mSettingObserver = new SettingObserver(new Handler(context.getMainLooper()));
        addOnAttachStateChangeListener(new Utils.DisableStateTracker(0, 2));
        this.mSlotBattery = context.getString(android.R.string.mediasize_iso_b5);
        this.mBatteryIconView = new ImageView(context);
        this.mBatteryIconView.setImageDrawable(this.mDrawable);
        ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(getResources().getDimensionPixelSize(R.dimen.status_bar_battery_icon_width), getResources().getDimensionPixelSize(R.dimen.status_bar_battery_icon_height));
        mlp.setMargins(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.battery_margin_bottom));
        addView(this.mBatteryIconView, mlp);
        updateShowPercent();
        setColorsFromContext(context);
        onDarkChanged(new Rect(), 0.0f, -1);
        this.mUserTracker = new CurrentUserTracker(this.mContext) { // from class: com.android.systemui.BatteryMeterView.1
            @Override // com.android.systemui.settings.CurrentUserTracker
            public void onUserSwitched(int newUserId) {
                BatteryMeterView.this.mUser = newUserId;
                BatteryMeterView.this.getContext().getContentResolver().unregisterContentObserver(BatteryMeterView.this.mSettingObserver);
                BatteryMeterView.this.getContext().getContentResolver().registerContentObserver(Settings.System.getUriFor("status_bar_show_battery_percent"), false, BatteryMeterView.this.mSettingObserver, newUserId);
            }
        };
        setClipChildren(false);
        setClipToPadding(false);
    }

    public void setColorsFromContext(Context context) {
        if (context == null) {
            return;
        }
        Context dualToneDarkTheme = new ContextThemeWrapper(context, com.android.settingslib.Utils.getThemeAttr(context, R.attr.darkIconTheme));
        Context dualToneLightTheme = new ContextThemeWrapper(context, com.android.settingslib.Utils.getThemeAttr(context, R.attr.lightIconTheme));
        this.mDarkModeBackgroundColor = com.android.settingslib.Utils.getColorAttr(dualToneDarkTheme, R.attr.backgroundColor);
        this.mDarkModeFillColor = com.android.settingslib.Utils.getColorAttr(dualToneDarkTheme, R.attr.fillColor);
        this.mLightModeBackgroundColor = com.android.settingslib.Utils.getColorAttr(dualToneLightTheme, R.attr.backgroundColor);
        this.mLightModeFillColor = com.android.settingslib.Utils.getColorAttr(dualToneLightTheme, R.attr.fillColor);
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        if ("icon_blacklist".equals(key)) {
            ArraySet<String> icons = StatusBarIconController.getIconBlacklist(newValue);
            boolean hidden = icons.contains(this.mSlotBattery);
            ((IconLogger) Dependency.get(IconLogger.class)).onIconVisibility(this.mSlotBattery, !hidden);
            setVisibility(hidden ? 8 : 0);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mBatteryController = (BatteryController) Dependency.get(BatteryController.class);
        this.mBatteryController.addCallback(this);
        this.mUser = ActivityManager.getCurrentUser();
        getContext().getContentResolver().registerContentObserver(Settings.System.getUriFor("status_bar_show_battery_percent"), false, this.mSettingObserver, this.mUser);
        updateShowPercent();
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "icon_blacklist");
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this);
        this.mUserTracker.startTracking();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mUserTracker.stopTracking();
        this.mBatteryController.removeCallback(this);
        getContext().getContentResolver().unregisterContentObserver(this.mSettingObserver);
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).removeCallback(this);
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public void onBatteryLevelChanged(int level, boolean pluggedIn, boolean charging) {
        this.mDrawable.setBatteryLevel(level);
        this.mDrawable.setCharging(pluggedIn && charging);
        this.mLevel = level;
        updatePercentText();
        setContentDescription(getContext().getString(charging ? R.string.accessibility_battery_level_charging : R.string.accessibility_battery_level, Integer.valueOf(level)));
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public void onPowerSaveChanged(boolean isPowerSave) {
        this.mDrawable.setPowerSave(isPowerSave);
    }

    private TextView loadPercentView() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.battery_percentage_view, (ViewGroup) null);
    }

    private void updatePercentText() {
        if (this.mBatteryPercentView != null) {
            this.mBatteryPercentView.setText(NumberFormat.getPercentInstance().format(this.mLevel / 100.0f));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateShowPercent() {
        boolean showing = this.mBatteryPercentView != null;
        if (Settings.System.getIntForUser(getContext().getContentResolver(), "status_bar_show_battery_percent", 0, this.mUser) != 0 || this.mForceShowPercent) {
            if (!showing) {
                this.mBatteryPercentView = loadPercentView();
                if (this.mTextColor != 0) {
                    this.mBatteryPercentView.setTextColor(this.mTextColor);
                }
                updatePercentText();
                addView(this.mBatteryPercentView, new ViewGroup.LayoutParams(-2, -1));
                return;
            }
            return;
        }
        if (showing) {
            removeView(this.mBatteryPercentView);
            this.mBatteryPercentView = null;
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onDensityOrFontScaleChanged() {
        scaleBatteryMeterViews();
    }

    private void scaleBatteryMeterViews() {
        Resources res = getContext().getResources();
        TypedValue typedValue = new TypedValue();
        res.getValue(R.dimen.status_bar_icon_scale_factor, typedValue, true);
        float iconScaleFactor = typedValue.getFloat();
        int batteryHeight = res.getDimensionPixelSize(R.dimen.status_bar_battery_icon_height);
        int batteryWidth = res.getDimensionPixelSize(R.dimen.status_bar_battery_icon_width);
        int marginBottom = res.getDimensionPixelSize(R.dimen.battery_margin_bottom);
        LinearLayout.LayoutParams scaledLayoutParams = new LinearLayout.LayoutParams((int) (batteryWidth * iconScaleFactor), (int) (batteryHeight * iconScaleFactor));
        scaledLayoutParams.setMargins(0, 0, 0, marginBottom);
        this.mBatteryIconView.setLayoutParams(scaledLayoutParams);
        FontSizeUtils.updateFontSize(this.mBatteryPercentView, R.dimen.qs_time_expanded_size);
    }

    @Override // com.android.systemui.statusbar.policy.DarkIconDispatcher.DarkReceiver
    public void onDarkChanged(Rect area, float darkIntensity, int tint) {
        this.mDarkIntensity = darkIntensity;
        float intensity = DarkIconDispatcher.isInArea(area, this) ? darkIntensity : 0.0f;
        this.mNonAdaptedForegroundColor = getColorForDarkIntensity(intensity, this.mLightModeFillColor, this.mDarkModeFillColor);
        this.mNonAdaptedBackgroundColor = getColorForDarkIntensity(intensity, this.mLightModeBackgroundColor, this.mDarkModeBackgroundColor);
        if (!this.mUseWallpaperTextColors) {
            updateColors(this.mNonAdaptedForegroundColor, this.mNonAdaptedBackgroundColor);
        }
    }

    private void updateColors(int foregroundColor, int backgroundColor) {
        this.mDrawable.setColors(foregroundColor, backgroundColor);
        this.mTextColor = foregroundColor;
        if (this.mBatteryPercentView != null) {
            this.mBatteryPercentView.setTextColor(foregroundColor);
        }
    }

    private int getColorForDarkIntensity(float darkIntensity, int lightColor, int darkColor) {
        return ((Integer) ArgbEvaluator.getInstance().evaluate(darkIntensity, Integer.valueOf(lightColor), Integer.valueOf(darkColor))).intValue();
    }

    private final class SettingObserver extends ContentObserver {
        public SettingObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            BatteryMeterView.this.updateShowPercent();
        }
    }
}
