package com.hcn.skin2.compat;

import android.content.res.Configuration;
import android.os.Bundle;
import com.hcn.common.misc.LogUtils;
import com.hcn.skin.support.app.SkinCompatFragment;
import com.hcn.skinx_night.INightDecoration;

/* JADX INFO: loaded from: classes.dex */
public abstract class Skin2CompatFragment extends SkinCompatFragment implements INightDecoration {
    private static final String TAG = "Skin2CompatFragment";
    protected int mUiMode = 0;

    protected String TAG() {
        return TAG;
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mUiMode = getResources().getConfiguration().uiMode;
    }

    @Override // com.hcn.skinx_night.INightDecoration
    public void initNightDecoration() {
        LogUtils.vTag(TAG(), "initNightDecoration: " + this.mUiMode);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        tryOnUiModeChanged(configuration.uiMode);
    }

    @Override // com.hcn.skinx_night.INightDecoration
    public void tryOnUiModeChanged(int i) {
        if (this.mUiMode == i) {
            return;
        }
        this.mUiMode = i;
        LogUtils.vTag(TAG(), "tryOnUiModeChanged: " + i);
    }
}
