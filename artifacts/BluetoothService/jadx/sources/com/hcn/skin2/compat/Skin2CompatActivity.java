package com.hcn.skin2.compat;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import com.hcn.common.misc.LogUtils;
import com.hcn.skin.support.app.SkinCompatActivity;
import com.hcn.skin2.Skin2;
import com.hcn.skin2.data.Skin2DataX;
import com.hcn.skinx_night.IUiModeDecoration;
import com.hcn.skinx_night.NightModeManager;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class Skin2CompatActivity extends SkinCompatActivity implements IUiModeDecoration {
    private static final String TAG = "Skin2CompatActivity";

    protected void tryExecuteRecreateTasK(String str) {
    }

    protected String TAG() {
        return TAG;
    }

    @Override // com.hcn.skin.support.app.SkinCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.hcn.skin.support.app.SkinCompatActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void setContentView(int i) {
        LogUtils.iTag(TAG, "setContentView: useSkinPackage = " + Skin2.useSkinPackage() + ".");
        Skin2DataX.init();
        NightModeManager.config(Skin2DataX.supportNightXDecorProtocol(), getResources().getConfiguration().uiMode);
        NightModeManager.register(this);
        super.setContentView(i);
    }

    @Override // com.hcn.skin.support.app.SkinCompatActivity
    protected void onContentViewCreated(View view) {
        if (Objects.isNull(getSkinContentView())) {
            return;
        }
        NightModeManager.initViewWithHelper(this, getSkinContentView());
    }

    @Override // com.hcn.skin.support.app.SkinCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (!Skin2DataX.supportNightXDecorProtocol()) {
            tryExecuteRecreateTasK("uimode");
        } else {
            NightModeManager.notifyNightModeChanged(configuration.uiMode);
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        NightModeManager.dispatchViewWithHelper(this);
    }

    @Override // com.hcn.skin.support.app.SkinCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        NightModeManager.unregister(this);
    }

    @Override // com.hcn.skinx_night.IUiModeDecoration
    public void onUpdateUiModeView(boolean z) {
        LogUtils.vTag(TAG(), z + " onUpdateUiModeView: " + getClass().getSimpleName());
    }
}
