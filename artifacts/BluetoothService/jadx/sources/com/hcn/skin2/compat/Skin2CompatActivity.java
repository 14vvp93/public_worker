package com.hcn.skin2.compat;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import com.hcn.common.misc.LogUtils;
import com.hcn.skin.support.app.SkinCompatActivity;
import com.hcn.skinx_night.INightDecoration;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class Skin2CompatActivity extends SkinCompatActivity implements INightDecoration {
    private static final String TAG = "Skin2CompatActivity";
    protected int mUiMode = 0;

    protected String TAG() {
        return TAG;
    }

    @Override // com.hcn.skin.support.app.SkinCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mUiMode = getResources().getConfiguration().uiMode;
    }

    @Override // com.hcn.skin.support.app.SkinCompatActivity
    protected void onContentViewCreated(View view) {
        Objects.isNull(getSkinContentView());
    }

    @Override // com.hcn.skinx_night.INightDecoration
    public void initNightDecoration() {
        LogUtils.vTag(TAG(), "initNightDecoration: " + this.mUiMode);
    }

    @Override // com.hcn.skin.support.app.SkinCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
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
