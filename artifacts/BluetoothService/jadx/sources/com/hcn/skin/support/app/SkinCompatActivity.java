package com.hcn.skin.support.app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.LayoutInflaterCompat;
import androidx.fragment.app.FragmentActivity;
import com.hcn.common.misc.LogUtils;
import com.hcn.skin.support.SkinCompatManager;
import com.hcn.skin.support.observe.SkinObserver;
import com.hcn.skin.support.resources.AsyncLayoutInflaterEx;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.skin.support.utils.SkinLog;

/* JADX INFO: loaded from: classes.dex */
public abstract class SkinCompatActivity extends FragmentActivity implements SkinObserver {
    private static final String TAG = "SkinCompatActivity";
    private SkinInflaterFactory mSkinInflaterFactory;
    private View mSkinContentView = null;
    private int mLayoutId = -1;
    private volatile boolean mAsyncCompleted = !supportAsyncInflater();

    protected boolean configAsyncInflater() {
        return false;
    }

    protected void contentViewAsyncCreated() {
    }

    protected void onContentViewCreated(View view) {
    }

    protected void onPreContentViewCreated() {
    }

    protected boolean supportSkinInflaterFactory() {
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        SkinCompatManager.create(getApplicationContext());
        SkinCompatManager.getInstance().addObserver(this);
        SkinCompatManager.getInstance().updateConfig(getResources().getConfiguration());
        if (supportSkinInflaterFactory()) {
            SkinCompatManager.getInstance().configSkinInflaterFactory(true);
            LayoutInflaterCompat.setFactory2(getLayoutInflater(), getSkinInflaterFactory());
        }
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        SkinLog.i(TAG, "onConfigurationChanged " + configuration);
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
        onPreContentViewCreated();
        if (supportAsyncInflater()) {
            asyncInflateContent(i);
            return;
        }
        LogUtils.vTag(TAG, "don't use async inflater view. ");
        LogUtils.vTag(TAG, "setContentView " + Integer.toHexString(i));
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

    private void asyncInflateContent(final int i) {
        SkinCompatResources.getInstance().skinInflaterFactorySet(SkinCompatManager.skinLayoutFactory2());
        if (SkinCompatResources.getInstance().getSkinResources() != null) {
            this.mLayoutId = i;
            int skinResId = SkinCompatResources.getInstance().getSkinResId(i, "layout");
            if (skinResId > 0) {
                LogUtils.vTag(TAG, "start async load skin content view. ");
                new AsyncLayoutInflaterEx(SkinCompatResources.getInstance().getSkinContext()).inflate(skinResId, null, new AsyncLayoutInflaterEx.OnInflateFinishedListener() { // from class: com.hcn.skin.support.app.SkinCompatActivity$$ExternalSyntheticLambda0
                    @Override // com.hcn.skin.support.resources.AsyncLayoutInflaterEx.OnInflateFinishedListener
                    public final void onInflateFinished(View view, int i2, ViewGroup viewGroup) {
                        this.f$0.m143x651bc67b(i, view, i2, viewGroup);
                    }
                });
                return;
            }
        }
        asyncInflateAppContent(i);
    }

    /* JADX INFO: renamed from: lambda$asyncInflateContent$0$com-hcn-skin-support-app-SkinCompatActivity, reason: not valid java name */
    /* synthetic */ void m143x651bc67b(int i, View view, int i2, ViewGroup viewGroup) {
        if (view == null) {
            LogUtils.eTag(TAG, "skinContentView async inflate failed. ");
            asyncInflateAppContent(i);
            return;
        }
        LogUtils.vTag(TAG, "skinContentView async inflate success. ");
        this.mAsyncCompleted = true;
        this.mSkinContentView = view;
        setContentView(view);
        onContentViewCreated(this.mSkinContentView);
        contentViewAsyncCreated();
    }

    private void asyncInflateAppContent(int i) {
        LogUtils.vTag(TAG, "start async load app content view. ");
        new AsyncLayoutInflaterEx(getBaseContext()).inflate(i, null, new AsyncLayoutInflaterEx.OnInflateFinishedListener() { // from class: com.hcn.skin.support.app.SkinCompatActivity$$ExternalSyntheticLambda1
            @Override // com.hcn.skin.support.resources.AsyncLayoutInflaterEx.OnInflateFinishedListener
            public final void onInflateFinished(View view, int i2, ViewGroup viewGroup) {
                this.f$0.m142x73ea1feb(view, i2, viewGroup);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$asyncInflateAppContent$1$com-hcn-skin-support-app-SkinCompatActivity, reason: not valid java name */
    /* synthetic */ void m142x73ea1feb(View view, int i, ViewGroup viewGroup) {
        LogUtils.vTag(TAG, "appContentView async inflate success. ");
        this.mAsyncCompleted = true;
        super.setContentView(view);
        onContentViewCreated(null);
        contentViewAsyncCreated();
    }

    private boolean supportAsyncInflater() {
        return configAsyncInflater();
    }

    protected boolean isAsyncCompleted() {
        return this.mAsyncCompleted;
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
            SkinLog.v(TAG, "skin ContentView findViewByName " + str);
            return this.mSkinContentView.findViewById(SkinCompatResources.getInstance().getSkinResId(str, "id"));
        }
        SkinLog.v(TAG, "findViewByName " + str);
        Context applicationContext = getApplicationContext();
        int identifier = applicationContext.getResources().getIdentifier(str, "id", applicationContext.getPackageName());
        if (identifier == 0) {
            return null;
        }
        int integer = applicationContext.getResources().getInteger(identifier);
        SkinLog.v(TAG, " targetId " + Integer.toHexString(identifier) + " Integer " + Integer.toHexString(integer));
        return findViewById(integer);
    }

    @Override // com.hcn.skin.support.observe.SkinObserver
    public void onSkinChanged(Object obj) {
        boolean zIsSameContentView = isSameContentView(SkinCompatResources.getInstance().getLayout(this, this.mLayoutId, SkinCompatManager.skinLayoutFactory2()));
        SkinLog.i(TAG, "onSkinChanged isSameContentView:" + zIsSameContentView);
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
