package com.hcn.skinx_night;

import android.view.View;
import android.view.ViewGroup;
import com.hcn.common.lang.HThreadUtils;
import com.hcn.common.misc.LogUtils;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
class NightModeHelper {
    private static final String TAG = "NightModeHelper";
    private final WeakReference<IUiModeDecoration> mUIModeDecoration;
    private WeakReference<View> mViewRef;

    protected NightModeHelper(IUiModeDecoration iUiModeDecoration) {
        this.mUIModeDecoration = new WeakReference<>(iUiModeDecoration);
    }

    protected void init(View view) {
        this.mViewRef = new WeakReference<>(view);
        if (!NightModeManager.supportNightDecorProtocol()) {
            LogUtils.vTag(TAG, "this skin don't support");
        } else {
            initDecoration();
        }
    }

    protected void setBindView(View view) {
        WeakReference<View> weakReference = this.mViewRef;
        if (weakReference != null && weakReference.get() != null) {
            LogUtils.wTag(TAG, "view already bind, set operation ignored");
            return;
        }
        this.mViewRef = new WeakReference<>(view);
        if (!NightModeManager.supportNightDecorProtocol()) {
            LogUtils.vTag(TAG, "this skin don't support");
        } else {
            initDecoration();
        }
    }

    protected void tryOnUiModeChanged(int i) {
        if (this.mViewRef == null) {
            LogUtils.wTag(TAG, "this view is null, don't changed");
            return;
        }
        int i2 = i & 48;
        if (i2 == 32) {
            onNightDecoration();
        } else {
            onDayDecoration();
        }
        dispatchUpdateView(i2 == 32);
    }

    protected void dispatchUpdateView(boolean z) {
        if (!NightModeManager.supportNightDecorProtocol()) {
            LogUtils.vTag(TAG, "this skin don't support");
            return;
        }
        IUiModeDecoration iUiModeDecoration = this.mUIModeDecoration.get();
        if (iUiModeDecoration != null) {
            iUiModeDecoration.onUpdateUiModeView(z);
        } else {
            LogUtils.wTag(TAG, "IUiModeDecoration is null");
        }
    }

    protected void updateDecorationForView(View view, boolean z) {
        LogUtils.vTag(TAG, "updateDecorationForView " + z);
        updateDecoration(view, z);
    }

    private void initDecoration() {
        if (NightX.isNightMode()) {
            onNightDecoration();
        } else {
            onDayDecoration();
        }
    }

    private void onNightDecoration() {
        final View view = this.mViewRef.get();
        if (view == null) {
            LogUtils.wTag(TAG, "contentView is null");
        } else {
            HThreadUtils.runOnUiThread(new Runnable() { // from class: com.hcn.skinx_night.NightModeHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m149lambda$onNightDecoration$0$comhcnskinx_nightNightModeHelper(view);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$onNightDecoration$0$com-hcn-skinx_night-NightModeHelper, reason: not valid java name */
    /* synthetic */ void m149lambda$onNightDecoration$0$comhcnskinx_nightNightModeHelper(View view) {
        updateDecoration(view, true);
    }

    private void onDayDecoration() {
        final View view = this.mViewRef.get();
        if (view == null) {
            LogUtils.wTag(TAG, "contentView is null");
        } else {
            HThreadUtils.runOnUiThread(new Runnable() { // from class: com.hcn.skinx_night.NightModeHelper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m148lambda$onDayDecoration$1$comhcnskinx_nightNightModeHelper(view);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$onDayDecoration$1$com-hcn-skinx_night-NightModeHelper, reason: not valid java name */
    /* synthetic */ void m148lambda$onDayDecoration$1$comhcnskinx_nightNightModeHelper(View view) {
        updateDecoration(view, false);
    }

    private void updateDecoration(View view, boolean z) {
        if (view == null) {
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                updateDecoration(viewGroup.getChildAt(i), z);
            }
        }
        NightAttrs viewNightAttrs = NightX.getViewNightAttrs(view);
        if (viewNightAttrs == null) {
            return;
        }
        if (z) {
            viewNightAttrs.resolveNightResIds();
        }
        NightSkinX.updateDecoration(view, viewNightAttrs, z);
    }
}
