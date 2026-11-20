package com.hcn.skin2.compat;

import android.content.res.Configuration;
import androidx.lifecycle.LifecycleService;
import com.hcn.common.misc.LogUtils;
import com.hcn.skinx_night.IUiModeDecoration;
import com.hcn.skinx_night.NightModeManager;

/* JADX INFO: loaded from: classes.dex */
public class Skin2CompatService extends LifecycleService implements IUiModeDecoration {
    private static final String TAG = "Skin2CompatService";

    protected String TAG() {
        return TAG;
    }

    public void onCreate() {
        super.onCreate();
        NightModeManager.register(this);
    }

    public void onDestroy() {
        super.onDestroy();
        NightModeManager.unregister(this);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        NightModeManager.notifyNightModeChanged(configuration.uiMode);
    }

    @Override // com.hcn.skinx_night.IUiModeDecoration
    public void onUpdateUiModeView(boolean z) {
        LogUtils.vTag(TAG(), z + " onUpdateUiModeView: " + getClass().getSimpleName());
    }
}
