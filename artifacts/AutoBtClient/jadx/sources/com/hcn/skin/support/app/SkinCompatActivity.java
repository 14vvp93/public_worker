package com.hcn.skin.support.app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import com.hcn.skin.support.SkinCompatManager;
import com.hcn.skin.support.observe.SkinObserver;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.skin.support.utils.SkinLog;

/* JADX INFO: loaded from: classes.dex */
public abstract class SkinCompatActivity extends FragmentActivity implements SkinObserver {
    private static final String TAG = "SkinCompatActivity";
    private SkinInflaterFactory mSkinInflaterFactory;
    private View mSkinContentView = null;
    private int mLayoutId = -1;

    protected void onContentViewCreated(View view) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        SkinCompatManager.create(getApplicationContext());
        SkinCompatManager.getInstance().addObserver(this);
        SkinCompatManager.getInstance().updateConfig(getResources().getConfiguration());
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        SkinLog.m55i(TAG, "onConfigurationChanged " + configuration);
        super.onConfigurationChanged(configuration);
    }

    private SkinInflaterFactory getSkinInflaterFactory() {
        if (this.mSkinInflaterFactory == null) {
            this.mSkinInflaterFactory = SkinInflaterFactory.create(this);
        }
        return this.mSkinInflaterFactory;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void setContentView(int i) {
        SkinLog.m55i(TAG, "setContentView " + Integer.toHexString(i));
        this.mLayoutId = i;
        View layout = SkinCompatResources.getInstance().getLayout(this, i, SkinCompatManager.skinLayoutFactory2());
        this.mSkinContentView = layout;
        if (layout != null) {
            setContentView(layout);
        } else {
            super.setContentView(i);
        }
        onContentViewCreated(this.mSkinContentView);
    }

    public View getSkinContentView() {
        return this.mSkinContentView;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        SkinCompatManager.getInstance().deleteObserver(this);
    }

    public View findViewByName(String str) {
        if (this.mSkinContentView != null) {
            SkinLog.m57v(TAG, "skin ContentView findViewByName " + str);
            return this.mSkinContentView.findViewById(SkinCompatResources.getInstance().getSkinResId(str, "id"));
        }
        SkinLog.m57v(TAG, "findViewByName " + str);
        Context applicationContext = getApplicationContext();
        int identifier = applicationContext.getResources().getIdentifier(str, "id", applicationContext.getPackageName());
        if (identifier == 0) {
            return null;
        }
        int integer = applicationContext.getResources().getInteger(identifier);
        SkinLog.m57v(TAG, " targetId " + Integer.toHexString(identifier) + " Integer " + Integer.toHexString(integer));
        return findViewById(integer);
    }

    @Override // com.hcn.skin.support.observe.SkinObserver
    public void onSkinChanged(Object obj) {
        boolean zIsSameContentView = isSameContentView(SkinCompatResources.getInstance().getLayout(this, this.mLayoutId, SkinCompatManager.skinLayoutFactory2()));
        SkinLog.m55i(TAG, "onSkinChanged isSameContentView:" + zIsSameContentView);
        if (zIsSameContentView) {
            getSkinInflaterFactory().applySkin();
        } else {
            recreate();
        }
    }

    public void applySkin() {
        getSkinInflaterFactory().applySkin();
    }

    private boolean isSameContentView(View view) {
        View view2;
        if (view == null || (view2 = this.mSkinContentView) == null) {
            return (view != null || this.mSkinContentView == null) && view == null;
        }
        return view.equals(view2);
    }
}
