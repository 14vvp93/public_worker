package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.os.LocaleList;
import com.android.systemui.ConfigurationChangedReceiver;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class ConfigurationControllerImpl implements ConfigurationChangedReceiver, ConfigurationController {
    private int mDensity;
    private float mFontScale;
    private boolean mInCarMode;
    private LocaleList mLocaleList;
    private int mUiMode;
    private final ArrayList<ConfigurationController.ConfigurationListener> mListeners = new ArrayList<>();
    private final Configuration mLastConfig = new Configuration();

    public ConfigurationControllerImpl(Context context) {
        Configuration currentConfig = context.getResources().getConfiguration();
        this.mFontScale = currentConfig.fontScale;
        this.mDensity = currentConfig.densityDpi;
        this.mInCarMode = (currentConfig.uiMode & 15) == 3;
        this.mUiMode = currentConfig.uiMode & 48;
        this.mLocaleList = currentConfig.getLocales();
    }

    @Override // com.android.systemui.ConfigurationChangedReceiver
    public void onConfigurationChanged(final Configuration newConfig) {
        ArrayList<ConfigurationController.ConfigurationListener> listeners = new ArrayList<>(this.mListeners);
        listeners.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$ConfigurationControllerImpl$q8toNxdmBM4_Z2SzGR-62P2UFpQ
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ConfigurationControllerImpl.lambda$onConfigurationChanged$0(this.f$0, newConfig, (ConfigurationController.ConfigurationListener) obj);
            }
        });
        float fontScale = newConfig.fontScale;
        int density = newConfig.densityDpi;
        int uiMode = newConfig.uiMode & 48;
        if (density != this.mDensity || fontScale != this.mFontScale || (this.mInCarMode && uiMode != this.mUiMode)) {
            listeners.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$ConfigurationControllerImpl$vqa1un3Hr9_5bDPhhhNK1qKD-2o
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ConfigurationControllerImpl.lambda$onConfigurationChanged$1(this.f$0, (ConfigurationController.ConfigurationListener) obj);
                }
            });
            this.mDensity = density;
            this.mFontScale = fontScale;
            this.mUiMode = uiMode;
        }
        LocaleList localeList = newConfig.getLocales();
        if (!localeList.equals(this.mLocaleList)) {
            this.mLocaleList = localeList;
            listeners.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$ConfigurationControllerImpl$MFXzl9-SIDbbTeRwTeJK0oQCn9Q
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ConfigurationControllerImpl.lambda$onConfigurationChanged$2(this.f$0, (ConfigurationController.ConfigurationListener) obj);
                }
            });
        }
        if ((this.mLastConfig.updateFrom(newConfig) & Integer.MIN_VALUE) != 0) {
            listeners.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.-$$Lambda$ConfigurationControllerImpl$SclF_d3UDXYaKsa0uhbxuxURXSI
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ConfigurationControllerImpl.lambda$onConfigurationChanged$3(this.f$0, (ConfigurationController.ConfigurationListener) obj);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$onConfigurationChanged$0(ConfigurationControllerImpl configurationControllerImpl, Configuration newConfig, ConfigurationController.ConfigurationListener l) {
        if (configurationControllerImpl.mListeners.contains(l)) {
            l.onConfigChanged(newConfig);
        }
    }

    public static /* synthetic */ void lambda$onConfigurationChanged$1(ConfigurationControllerImpl configurationControllerImpl, ConfigurationController.ConfigurationListener l) {
        if (configurationControllerImpl.mListeners.contains(l)) {
            l.onDensityOrFontScaleChanged();
        }
    }

    public static /* synthetic */ void lambda$onConfigurationChanged$2(ConfigurationControllerImpl configurationControllerImpl, ConfigurationController.ConfigurationListener l) {
        if (configurationControllerImpl.mListeners.contains(l)) {
            l.onLocaleListChanged();
        }
    }

    public static /* synthetic */ void lambda$onConfigurationChanged$3(ConfigurationControllerImpl configurationControllerImpl, ConfigurationController.ConfigurationListener l) {
        if (configurationControllerImpl.mListeners.contains(l)) {
            l.onOverlayChanged();
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(ConfigurationController.ConfigurationListener listener) {
        this.mListeners.add(listener);
        listener.onDensityOrFontScaleChanged();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(ConfigurationController.ConfigurationListener listener) {
        this.mListeners.remove(listener);
    }
}
