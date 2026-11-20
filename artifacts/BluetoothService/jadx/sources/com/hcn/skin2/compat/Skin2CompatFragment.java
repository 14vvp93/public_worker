package com.hcn.skin2.compat;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hcn.common.misc.LogUtils;
import com.hcn.skin.support.app.SkinCompatFragment;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.skin2.Skin2;
import com.hcn.skinx_night.IUiModeDecoration;
import com.hcn.skinx_night.NightModeManager;
import com.hcn.skinx_night.NightSkinX;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class Skin2CompatFragment extends SkinCompatFragment implements IUiModeDecoration {
    private static final String TAG = "Skin2CompatFragment";

    protected String TAG() {
        return TAG;
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        NightModeManager.register(this);
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (!Skin2.useSkinPackage()) {
            View viewAppSyncInflater = appSyncInflater(layoutInflater, getLayoutRes(), viewGroup);
            onCreateViewImpl(viewAppSyncInflater, "sync");
            return viewAppSyncInflater;
        }
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment
    protected void onCreateViewImpl(View view, String str) {
        super.onCreateViewImpl(view, str);
        LogUtils.vTag(TAG(), "onCreateViewImpl " + str);
        NightModeManager.initViewWithHelper(this, view);
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment
    protected void onViewCreatedImpl(View view, String str) {
        super.onViewCreatedImpl(view, str);
        LogUtils.vTag(TAG(), "onViewCreatedImpl " + str);
        NightModeManager.dispatchViewWithHelper(this);
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment
    protected void onResumeImpl(String str) {
        super.onResumeImpl(str);
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        NightModeManager.unregister(this);
    }

    @Override // com.hcn.skinx_night.IUiModeDecoration
    public void onUpdateUiModeView(boolean z) {
        LogUtils.vTag(TAG(), z + " onUpdateUiModeView: " + getClass().getSimpleName());
    }

    public int xId(int i) {
        return Objects.isNull(getSkinContentView()) ? i : Skin2.xId(i);
    }

    @Deprecated(since = "2025-04-26")
    public int xDrawableId2(int i) {
        return Objects.isNull(getSkinContentView()) ? i : Skin2.xDrawableId2(i);
    }

    @Deprecated(since = "2025-04-26")
    public int xDrawableId2(String str) {
        return Skin2.xDrawableId2(str);
    }

    public Drawable getDrawable2(String str) {
        if (Objects.isNull(getSkinContentView())) {
            return Skin2.instance().context().getResources().getDrawable(SkinCompatResources.getInstance().getAppResId(str, NightSkinX.ResTypeName.DRAWABLE));
        }
        return Skin2.getDrawable2(str);
    }

    public Drawable getDrawable2(int i) {
        if (Objects.isNull(getSkinContentView())) {
            return Skin2.instance().context().getResources().getDrawable(i);
        }
        return Skin2.getDrawable2(i);
    }

    public int xColorId2(int i) {
        return Objects.isNull(getSkinContentView()) ? i : Skin2.xColorId2(i);
    }

    public int xColorId2(String str) {
        return Skin2.xColorId2(str);
    }

    public int getColor2(int i) {
        if (Objects.isNull(getSkinContentView())) {
            return Skin2.instance().context().getResources().getColor(i);
        }
        return Skin2.getColor2(i);
    }

    public int getColor2(String str) {
        if (Objects.isNull(getSkinContentView())) {
            return Skin2.instance().context().getResources().getColor(SkinCompatResources.getInstance().getAppResId(str, NightSkinX.ResTypeName.COLOR));
        }
        return Skin2.getColor2(str);
    }

    public int getId(View view) {
        if (Objects.isNull(getSkinContentView())) {
            return view.getId();
        }
        return Skin2.getId(view);
    }

    public final Resources getResourcesEx() {
        if (Objects.isNull(getSkinContentView())) {
            return getResources();
        }
        return Skin2.skinContext().getResources();
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment
    public View findViewByName(String str) {
        View skinContentView = getSkinContentView();
        if (Objects.isNull(skinContentView)) {
            int identifier = getResources().getIdentifier(str, "id", requireContext().getPackageName());
            if (identifier > 0) {
                return requireView().findViewById(identifier);
            }
            return null;
        }
        int iXId = Skin2.xId(str);
        if (iXId > 0) {
            return skinContentView.findViewById(iXId);
        }
        return null;
    }

    public static Drawable xDrawable(int i) {
        return Skin2.getDrawable(i);
    }

    public static Drawable xDrawable(String str) {
        return Skin2.getDrawable(str);
    }

    public static boolean xBoolean(int i) {
        return Skin2.getBoolean(i);
    }

    public static boolean xBoolean(String str) {
        return Skin2.getBoolean(str);
    }

    public static boolean xBoolean(int i, boolean z) {
        return Skin2.getBoolean(i, z);
    }

    public static boolean xBoolean(String str, boolean z) {
        return Skin2.getBoolean(str, z);
    }

    public static String xString(int i) {
        return Skin2.getString(i);
    }

    public static String xString(String str) {
        return Skin2.getString(str);
    }
}
