package com.hcn.skin.support.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.hcn.skin.support.SkinCompatManager;
import com.hcn.skin.support.observe.SkinLayoutObserver;
import com.hcn.skin.support.observe.SkinObserver;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.skin.support.utils.SkinLog;

/* JADX INFO: loaded from: classes.dex */
public abstract class SkinCompatFragment extends Fragment implements SkinObserver, SkinLayoutObserver {
    private static final String TAG = "SkinCompatFragment";
    private View mSkinContentView = null;
    private int mLayoutId = -1;

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        SkinCompatManager.create(requireActivity().getApplicationContext());
        SkinCompatManager.getInstance().addObserver(this);
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mLayoutId = getLayoutRes();
        SkinLog.m55i(TAG, "onCreateView layout: " + this.mLayoutId + " id: " + getId() + " " + (getActivity() instanceof SkinObserver ? "SkinCompatActivity" : "NormalActivity"));
        View layout = SkinCompatResources.getInstance().getLayout(requireActivity().getApplicationContext(), this.mLayoutId, SkinCompatManager.skinLayoutFactory2());
        this.mSkinContentView = layout;
        return layout != null ? layout : layoutInflater.inflate(this.mLayoutId, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        SkinLog.m55i(TAG, "onCreateView " + view);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        SkinLog.m55i(TAG, "onResume");
        super.onResume();
        onBindViewData();
    }

    public View findViewByName(String str) {
        if (this.mSkinContentView != null) {
            SkinLog.m57v(TAG, "skin ContentView findViewByName " + str);
            return this.mSkinContentView.findViewById(SkinCompatResources.getInstance().getSkinResId(str, "id"));
        }
        if (getView() == null) {
            return null;
        }
        SkinLog.m57v(TAG, getView() + " findViewByName " + str);
        Context context = getView().getContext();
        int identifier = context.getResources().getIdentifier(str, "id", context.getPackageName());
        if (identifier == 0) {
            return null;
        }
        int integer = context.getResources().getInteger(identifier);
        SkinLog.m57v(TAG, " targetId " + Integer.toHexString(identifier) + " Integer " + Integer.toHexString(integer));
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
        SkinLog.m55i(TAG, "onSkinChanged isSameContentView: " + zIsSameContentView + " " + (getActivity() instanceof SkinObserver ? "SkinCompatActivity" : "NormalActivity"));
        if (zIsSameContentView) {
            return;
        }
        requireActivity().recreate();
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
