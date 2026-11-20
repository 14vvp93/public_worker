package com.hcn.skin.support.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import com.hcn.common.misc.LogUtils;
import com.hcn.skin.support.SkinCompatManager;
import com.hcn.skin.support.observe.SkinLayoutObserver;
import com.hcn.skin.support.observe.SkinObserver;
import com.hcn.skin.support.resources.AsyncLayoutInflaterEx;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.skin.support.utils.SkinLog;

/* JADX INFO: loaded from: classes.dex */
public abstract class SkinCompatFragment extends Fragment implements SkinObserver, SkinLayoutObserver {
    private static final String TAG = "SkinCompatFragment";
    private View mSkinContentView = null;
    private int mLayoutId = -1;
    private volatile boolean mAsyncCompleted = !supportAsyncInflater();

    protected boolean configAsyncInflater() {
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        SkinCompatManager.create(requireActivity().getApplicationContext());
        SkinCompatManager.getInstance().addObserver(this);
        super.onCreate(bundle);
    }

    private void contentViewAsyncCreated(View view) {
        onCreateViewLocked(view, "async");
        onViewCreatedLocked(view, "async");
        if (isResumed()) {
            view.post(new Runnable() { // from class: com.hcn.skin.support.app.SkinCompatFragment$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m146x2a5497d8();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$contentViewAsyncCreated$0$com-hcn-skin-support-app-SkinCompatFragment, reason: not valid java name */
    /* synthetic */ void m146x2a5497d8() {
        onResumeLocked("async");
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mLayoutId = getLayoutRes();
        if (supportAsyncInflater()) {
            FrameLayout frameLayout = new FrameLayout(requireContext());
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            asyncInflateContent(frameLayout);
            return frameLayout;
        }
        LogUtils.vTag(TAG, "don't use async inflater view. ");
        Object[] objArr = new Object[1];
        objArr[0] = "onCreateView layout: " + this.mLayoutId + " id: " + getId() + " " + (getActivity() instanceof SkinObserver ? "SkinCompatActivity" : "NormalActivity");
        LogUtils.iTag(TAG, objArr);
        View layout = SkinCompatResources.getInstance().getLayout(requireActivity().getApplicationContext(), this.mLayoutId, SkinCompatManager.skinLayoutFactory2());
        this.mSkinContentView = layout;
        if (layout != null) {
            onCreateViewLocked(layout, "sync");
            return this.mSkinContentView;
        }
        View viewInflate = layoutInflater.inflate(this.mLayoutId, viewGroup, false);
        onCreateViewLocked(viewInflate, "sync");
        return viewInflate;
    }

    private void onCreateViewLocked(View view, String str) {
        onCreateViewImpl(view, str);
    }

    protected void onCreateViewImpl(View view, String str) {
        LogUtils.vTag(TAG, "onCreateViewImpl is executed reason: " + str);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        LogUtils.iTag(TAG, "onCreateView " + view);
        if (!isAsyncCompleted()) {
            LogUtils.vTag(TAG, "lazy async onViewCreatedLocked. ");
        } else {
            onViewCreatedLocked(view, "sync");
        }
    }

    private void onViewCreatedLocked(View view, String str) {
        onViewCreatedImpl(view, str);
    }

    protected void onViewCreatedImpl(View view, String str) {
        LogUtils.vTag(TAG, "onViewCreatedImpl is executed reason: " + str);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        SkinLog.i(TAG, "onResume");
        super.onResume();
        if (!isAsyncCompleted()) {
            LogUtils.vTag(TAG, "lazy async onResumeLocked. ");
        } else {
            onResumeLocked("sync");
        }
    }

    private void onResumeLocked(String str) {
        onBindViewData();
        onResumeImpl(str);
    }

    protected void onResumeImpl(String str) {
        LogUtils.vTag(TAG, "onResumeImpl is executed reason: " + str);
    }

    public View findViewByName(String str) {
        if (this.mSkinContentView != null) {
            SkinLog.v(TAG, "skin ContentView findViewByName " + str);
            return this.mSkinContentView.findViewById(SkinCompatResources.getInstance().getSkinResId(str, "id"));
        }
        if (getView() == null) {
            return null;
        }
        SkinLog.v(TAG, getView() + " findViewByName " + str);
        Context context = getView().getContext();
        int identifier = context.getResources().getIdentifier(str, "id", context.getPackageName());
        if (identifier == 0) {
            return null;
        }
        int integer = context.getResources().getInteger(identifier);
        SkinLog.v(TAG, " targetId " + Integer.toHexString(identifier) + " Integer " + Integer.toHexString(integer));
        return getView().findViewById(integer);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        SkinCompatManager.getInstance().deleteObserver(this);
    }

    @Override // com.hcn.skin.support.observe.SkinObserver
    public void onSkinChanged(Object obj) {
        boolean zIsSameContentView = isSameContentView(SkinCompatResources.getInstance().getLayout(requireActivity().getApplicationContext(), this.mLayoutId, SkinCompatManager.skinLayoutFactory2()));
        SkinLog.i(TAG, "onSkinChanged isSameContentView: " + zIsSameContentView + " " + (getActivity() instanceof SkinObserver ? "SkinCompatActivity" : "NormalActivity"));
        if (zIsSameContentView) {
            return;
        }
        requireActivity().recreate();
    }

    private void asyncInflateContent(final FrameLayout frameLayout) {
        int skinResId;
        SkinCompatResources.getInstance().skinInflaterFactorySet(SkinCompatManager.skinLayoutFactory2());
        if (SkinCompatResources.getInstance().getSkinResources() != null && (skinResId = SkinCompatResources.getInstance().getSkinResId(this.mLayoutId, "layout")) > 0) {
            LogUtils.vTag(TAG, "start async load skin content view. ");
            new AsyncLayoutInflaterEx(SkinCompatResources.getInstance().getContext()).inflate(skinResId, null, new AsyncLayoutInflaterEx.OnInflateFinishedListener() { // from class: com.hcn.skin.support.app.SkinCompatFragment$$ExternalSyntheticLambda2
                @Override // com.hcn.skin.support.resources.AsyncLayoutInflaterEx.OnInflateFinishedListener
                public final void onInflateFinished(View view, int i, ViewGroup viewGroup) {
                    this.f$0.m145x13783d(frameLayout, view, i, viewGroup);
                }
            });
        } else {
            asyncInflateAppContent(frameLayout);
        }
    }

    /* JADX INFO: renamed from: lambda$asyncInflateContent$1$com-hcn-skin-support-app-SkinCompatFragment, reason: not valid java name */
    /* synthetic */ void m145x13783d(FrameLayout frameLayout, View view, int i, ViewGroup viewGroup) {
        if (view == null) {
            LogUtils.eTag(TAG, "skinContentView async inflate failed. ");
            asyncInflateAppContent(frameLayout);
            return;
        }
        LogUtils.vTag(TAG, "skinContentView async inflate success. ");
        this.mAsyncCompleted = true;
        this.mSkinContentView = view;
        frameLayout.addView(view);
        contentViewAsyncCreated(this.mSkinContentView);
    }

    private void asyncInflateAppContent(final FrameLayout frameLayout) {
        LogUtils.vTag(TAG, "start async load app content view. ");
        new AsyncLayoutInflaterEx(requireContext()).inflate(this.mLayoutId, null, new AsyncLayoutInflaterEx.OnInflateFinishedListener() { // from class: com.hcn.skin.support.app.SkinCompatFragment$$ExternalSyntheticLambda1
            @Override // com.hcn.skin.support.resources.AsyncLayoutInflaterEx.OnInflateFinishedListener
            public final void onInflateFinished(View view, int i, ViewGroup viewGroup) {
                this.f$0.m144xee1d1ad(frameLayout, view, i, viewGroup);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$asyncInflateAppContent$2$com-hcn-skin-support-app-SkinCompatFragment, reason: not valid java name */
    /* synthetic */ void m144xee1d1ad(FrameLayout frameLayout, View view, int i, ViewGroup viewGroup) {
        LogUtils.vTag(TAG, "appContentView async inflate success. ");
        this.mAsyncCompleted = true;
        frameLayout.addView(view);
        contentViewAsyncCreated(view);
    }

    private boolean supportAsyncInflater() {
        return configAsyncInflater();
    }

    protected final boolean isAsyncCompleted() {
        return this.mAsyncCompleted;
    }

    protected final View appSyncInflater(LayoutInflater layoutInflater, int i, ViewGroup viewGroup) {
        LogUtils.vTag(TAG, "appSyncInflater resource: " + i);
        this.mAsyncCompleted = true;
        return layoutInflater.inflate(i, viewGroup, false);
    }

    public View getSkinContentView() {
        return this.mSkinContentView;
    }

    private boolean isSameContentView(View view) {
        View view2;
        if (view == null || (view2 = this.mSkinContentView) == null) {
            return (view != null || this.mSkinContentView == null) && view == null;
        }
        return view.equals(view2);
    }
}
