package com.android.systemui.settings;

import android.R;
import android.animation.ValueAnimator;
import android.carsource.McuManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.RestrictedLockUtils;
import com.android.systemui.Dependency;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class BrightnessController implements ToggleSlider.Listener {
    private volatile boolean mAutomatic;
    private final boolean mAutomaticAvailable;
    private final Handler mBackgroundHandler;
    private final BrightnessObserver mBrightnessObserver;
    private final TextView mBrightnessText;
    private ArrayList<BrightnessStateChangeCallback> mChangeCallbacks;
    private final Context mContext;
    private final ToggleSlider mControl;
    private boolean mControlValueInitialized;
    private final int mDefaultBacklight;
    private final int mDefaultBacklightForVr;
    private final DisplayManager mDisplayManager;
    private boolean mExternalChange;
    private final Handler mHandler;
    private final boolean mHasBrightnessText;
    private final ImageView mIcon;
    private volatile boolean mIsVrModeEnabled;
    private boolean mListening;
    private final int mMaximumBacklight;
    private final int mMaximumBacklightForVr;
    private final int mMinimumBacklight;
    private final int mMinimumBacklightForVr;
    private ValueAnimator mSliderAnimator;
    private final Runnable mStartListeningRunnable;
    private final Runnable mStopListeningRunnable;
    private final Runnable mUpdateModeRunnable;
    private final Runnable mUpdateSliderRunnable;
    private final CurrentUserTracker mUserTracker;
    private final IVrManager mVrManager;
    private final IVrStateCallbacks mVrStateCallbacks;

    public interface BrightnessStateChangeCallback {
        void onBrightnessLevelChanged();
    }

    private class BrightnessObserver extends ContentObserver {
        private final Uri BRIGHTNESS_FOR_VR_URI;
        private final Uri BRIGHTNESS_MODE_URI;
        private final Uri BRIGHTNESS_URI;

        public BrightnessObserver(Handler handler) {
            super(handler);
            this.BRIGHTNESS_MODE_URI = Settings.System.getUriFor("screen_brightness_mode");
            this.BRIGHTNESS_URI = Settings.System.getUriFor("screen_brightness");
            this.BRIGHTNESS_FOR_VR_URI = Settings.System.getUriFor("screen_brightness_for_vr");
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            if (selfChange) {
                return;
            }
            if (this.BRIGHTNESS_MODE_URI.equals(uri)) {
                BrightnessController.this.mBackgroundHandler.post(BrightnessController.this.mUpdateModeRunnable);
                BrightnessController.this.mBackgroundHandler.post(BrightnessController.this.mUpdateSliderRunnable);
            } else if (!this.BRIGHTNESS_URI.equals(uri) && !this.BRIGHTNESS_FOR_VR_URI.equals(uri)) {
                BrightnessController.this.mBackgroundHandler.post(BrightnessController.this.mUpdateModeRunnable);
                BrightnessController.this.mBackgroundHandler.post(BrightnessController.this.mUpdateSliderRunnable);
            } else {
                BrightnessController.this.mBackgroundHandler.post(BrightnessController.this.mUpdateSliderRunnable);
            }
            for (BrightnessStateChangeCallback cb : BrightnessController.this.mChangeCallbacks) {
                cb.onBrightnessLevelChanged();
            }
        }

        public void startObserving() {
            ContentResolver cr = BrightnessController.this.mContext.getContentResolver();
            cr.unregisterContentObserver(this);
            cr.registerContentObserver(this.BRIGHTNESS_MODE_URI, false, this, -1);
            cr.registerContentObserver(this.BRIGHTNESS_URI, false, this, -1);
            cr.registerContentObserver(this.BRIGHTNESS_FOR_VR_URI, false, this, -1);
        }

        public void stopObserving() {
            ContentResolver cr = BrightnessController.this.mContext.getContentResolver();
            cr.unregisterContentObserver(this);
        }
    }

    public BrightnessController(Context context, ImageView icon, ToggleSlider control) {
        this(context, icon, control, null, false);
    }

    public BrightnessController(Context context, ImageView icon, ToggleSlider control, TextView brightnessText, boolean hasBrightnessText) {
        this.mChangeCallbacks = new ArrayList<>();
        this.mStartListeningRunnable = new Runnable() { // from class: com.android.systemui.settings.BrightnessController.1
            @Override // java.lang.Runnable
            public void run() {
                BrightnessController.this.mBrightnessObserver.startObserving();
                BrightnessController.this.mUserTracker.startTracking();
                BrightnessController.this.mUpdateModeRunnable.run();
                BrightnessController.this.mUpdateSliderRunnable.run();
                BrightnessController.this.mHandler.sendEmptyMessage(3);
            }
        };
        this.mStopListeningRunnable = new Runnable() { // from class: com.android.systemui.settings.BrightnessController.2
            @Override // java.lang.Runnable
            public void run() {
                BrightnessController.this.mBrightnessObserver.stopObserving();
                BrightnessController.this.mUserTracker.stopTracking();
                BrightnessController.this.mHandler.sendEmptyMessage(4);
            }
        };
        this.mUpdateModeRunnable = new Runnable() { // from class: com.android.systemui.settings.BrightnessController.3
            @Override // java.lang.Runnable
            public void run() {
                if (BrightnessController.this.mAutomaticAvailable) {
                    int intForUser = Settings.System.getIntForUser(BrightnessController.this.mContext.getContentResolver(), "screen_brightness_mode", 0, -2);
                    BrightnessController.this.mAutomatic = intForUser != 0;
                    BrightnessController.this.mHandler.obtainMessage(0, Integer.valueOf(BrightnessController.this.mAutomatic ? 1 : 0)).sendToTarget();
                    return;
                }
                BrightnessController.this.mHandler.obtainMessage(2, 0).sendToTarget();
                BrightnessController.this.mHandler.obtainMessage(0, 0).sendToTarget();
            }
        };
        this.mUpdateSliderRunnable = new Runnable() { // from class: com.android.systemui.settings.BrightnessController.4
            @Override // java.lang.Runnable
            public void run() {
                int intForUser;
                boolean z = BrightnessController.this.mIsVrModeEnabled;
                if (z) {
                    intForUser = Settings.System.getIntForUser(BrightnessController.this.mContext.getContentResolver(), "screen_brightness_for_vr", BrightnessController.this.mDefaultBacklightForVr, -2);
                } else {
                    intForUser = Settings.System.getIntForUser(BrightnessController.this.mContext.getContentResolver(), "screen_brightness", BrightnessController.this.mDefaultBacklight, -2);
                }
                BrightnessController.this.mHandler.obtainMessage(1, intForUser - BrightnessController.this.mMinimumBacklight, z ? 1 : 0).sendToTarget();
            }
        };
        this.mVrStateCallbacks = new IVrStateCallbacks.Stub() { // from class: com.android.systemui.settings.BrightnessController.5
            public void onVrStateChanged(boolean z) {
                BrightnessController.this.mHandler.obtainMessage(5, z ? 1 : 0, 0).sendToTarget();
            }
        };
        this.mHandler = new Handler() { // from class: com.android.systemui.settings.BrightnessController.6
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                boolean z = true;
                BrightnessController.this.mExternalChange = true;
                try {
                    switch (msg.what) {
                        case 0:
                            BrightnessController brightnessController = BrightnessController.this;
                            if (msg.arg1 == 0) {
                                z = false;
                            }
                            brightnessController.updateIcon(z);
                            break;
                        case 1:
                            BrightnessController brightnessController2 = BrightnessController.this;
                            int i = msg.arg1;
                            if (msg.arg2 == 0) {
                                z = false;
                            }
                            brightnessController2.updateSlider(i, z);
                            break;
                        case 2:
                            ToggleSlider toggleSlider = BrightnessController.this.mControl;
                            if (msg.arg1 == 0) {
                                z = false;
                            }
                            toggleSlider.setChecked(z);
                            break;
                        case 3:
                            BrightnessController.this.mControl.setOnChangedListener(BrightnessController.this);
                            break;
                        case 4:
                            BrightnessController.this.mControl.setOnChangedListener(null);
                            break;
                        case 5:
                            BrightnessController brightnessController3 = BrightnessController.this;
                            if (msg.arg1 == 0) {
                                z = false;
                            }
                            brightnessController3.updateVrMode(z);
                            break;
                        default:
                            super.handleMessage(msg);
                            break;
                    }
                } finally {
                    BrightnessController.this.mExternalChange = false;
                }
            }
        };
        this.mContext = context;
        this.mIcon = icon;
        this.mControl = control;
        this.mBrightnessText = brightnessText;
        this.mHasBrightnessText = hasBrightnessText;
        this.mBackgroundHandler = new Handler((Looper) Dependency.get(Dependency.BG_LOOPER));
        this.mUserTracker = new CurrentUserTracker(this.mContext) { // from class: com.android.systemui.settings.BrightnessController.7
            @Override // com.android.systemui.settings.CurrentUserTracker
            public void onUserSwitched(int newUserId) {
                BrightnessController.this.mBackgroundHandler.post(BrightnessController.this.mUpdateModeRunnable);
                BrightnessController.this.mBackgroundHandler.post(BrightnessController.this.mUpdateSliderRunnable);
            }
        };
        this.mBrightnessObserver = new BrightnessObserver(this.mHandler);
        PowerManager pm = (PowerManager) context.getSystemService(PowerManager.class);
        this.mMinimumBacklight = pm.getMinimumScreenBrightnessSetting();
        this.mMaximumBacklight = pm.getMaximumScreenBrightnessSetting();
        this.mDefaultBacklight = pm.getDefaultScreenBrightnessSetting();
        this.mMinimumBacklightForVr = pm.getMinimumScreenBrightnessForVrSetting();
        this.mMaximumBacklightForVr = pm.getMaximumScreenBrightnessForVrSetting();
        this.mDefaultBacklightForVr = pm.getDefaultScreenBrightnessForVrSetting();
        this.mAutomaticAvailable = context.getResources().getBoolean(R.^attr-private.borderTop);
        this.mDisplayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        this.mVrManager = IVrManager.Stub.asInterface(ServiceManager.getService("vrmanager"));
        this.mControl.setMax(this.mMaximumBacklight - this.mMinimumBacklight);
        int value = Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_brightness", this.mMaximumBacklight, -2);
        this.mControl.setValue(value - this.mMinimumBacklight);
        if (this.mHasBrightnessText && this.mBrightnessText != null) {
            updateBrightnessText(value);
        }
        this.mIcon.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.settings.-$$Lambda$BrightnessController$daXJdO8QhMk8Ivkwgi6j5oHn7Mk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                McuManager.getsInstance().injectKeyEventTimeout(309, 50);
            }
        });
    }

    @Override // com.android.systemui.settings.ToggleSlider.Listener
    public void onInit(ToggleSlider control) {
    }

    public void registerCallbacks() {
        if (this.mListening) {
            return;
        }
        if (this.mVrManager != null) {
            try {
                this.mVrManager.registerListener(this.mVrStateCallbacks);
                this.mIsVrModeEnabled = this.mVrManager.getVrModeState();
            } catch (RemoteException e) {
                Log.e("StatusBar.BrightnessController", "Failed to register VR mode state listener: ", e);
            }
        }
        this.mBackgroundHandler.post(this.mStartListeningRunnable);
        this.mListening = true;
    }

    public void unregisterCallbacks() {
        if (!this.mListening) {
            return;
        }
        if (this.mVrManager != null) {
            try {
                this.mVrManager.unregisterListener(this.mVrStateCallbacks);
            } catch (RemoteException e) {
                Log.e("StatusBar.BrightnessController", "Failed to unregister VR mode state listener: ", e);
            }
        }
        this.mBackgroundHandler.post(this.mStopListeningRunnable);
        this.mListening = false;
        this.mControlValueInitialized = false;
    }

    @Override // com.android.systemui.settings.ToggleSlider.Listener
    public void onChanged(ToggleSlider toggleSlider, boolean tracking, boolean automatic, int value, boolean stopTracking) {
        int metric;
        final String setting;
        updateIcon(this.mAutomatic);
        if (this.mExternalChange) {
            return;
        }
        if (this.mSliderAnimator != null) {
            this.mSliderAnimator.cancel();
        }
        if (this.mIsVrModeEnabled) {
            metric = 498;
            int i = this.mMinimumBacklightForVr;
            int i2 = this.mMaximumBacklightForVr;
            setting = "screen_brightness_for_vr";
        } else {
            if (this.mAutomatic) {
                metric = 219;
            } else {
                metric = 218;
            }
            int i3 = this.mMinimumBacklight;
            int i4 = this.mMaximumBacklight;
            setting = "screen_brightness";
        }
        final int val = this.mMinimumBacklight + value;
        if (stopTracking) {
            MetricsLogger.action(this.mContext, metric, val);
        }
        setBrightness(val);
        if (this.mHasBrightnessText && this.mBrightnessText != null) {
            updateBrightnessText(val);
        }
        if (!tracking) {
            AsyncTask.execute(new Runnable() { // from class: com.android.systemui.settings.BrightnessController.8
                @Override // java.lang.Runnable
                public void run() {
                    Settings.System.putIntForUser(BrightnessController.this.mContext.getContentResolver(), setting, val, -2);
                }
            });
        }
        for (BrightnessStateChangeCallback cb : this.mChangeCallbacks) {
            cb.onBrightnessLevelChanged();
        }
    }

    public void checkRestrictionAndSetEnabled() {
        this.mBackgroundHandler.post(new Runnable() { // from class: com.android.systemui.settings.BrightnessController.9
            @Override // java.lang.Runnable
            public void run() {
                ((ToggleSliderView) BrightnessController.this.mControl).setEnforcedAdmin(RestrictedLockUtils.checkIfRestrictionEnforced(BrightnessController.this.mContext, "no_config_brightness", BrightnessController.this.mUserTracker.getCurrentUserId()));
            }
        });
    }

    private void setBrightness(int brightness) {
        this.mDisplayManager.setTemporaryBrightness(brightness);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateIcon(boolean automatic) {
        if (this.mIcon != null) {
            this.mIcon.setImageResource(com.android.systemui.R.drawable.ic_qs_brightness_auto_off);
        }
    }

    private void updateBrightnessText(int brightnessValue) {
        if (this.mBrightnessText != null) {
            this.mBrightnessText.setText(String.valueOf(brightnessValue));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVrMode(boolean isEnabled) {
        if (this.mIsVrModeEnabled != isEnabled) {
            this.mIsVrModeEnabled = isEnabled;
            this.mBackgroundHandler.post(this.mUpdateSliderRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSlider(int val, boolean inVrMode) {
        if (inVrMode) {
            int i = this.mMinimumBacklightForVr;
            int i2 = this.mMaximumBacklightForVr;
        } else {
            int min = this.mMinimumBacklight;
            int i3 = this.mMaximumBacklight;
        }
        if (val == this.mControl.getValue()) {
            return;
        }
        this.mControl.setValue(val);
        if (this.mHasBrightnessText && this.mBrightnessText != null) {
            updateBrightnessText(this.mMinimumBacklight + val);
        }
    }
}
