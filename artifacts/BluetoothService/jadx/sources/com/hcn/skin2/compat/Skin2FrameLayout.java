package com.hcn.skin2.compat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.hcn.common.misc.LogUtils;
import com.hcn.skin2.Skin2;
import com.hcn.skinx_night.IUiModeDecoration;
import com.hcn.skinx_night.NightModeManager;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class Skin2FrameLayout extends FrameLayout implements IUiModeDecoration {
    private static final String TAG = "Skin2FrameLayout";
    protected View mContentView;
    protected final Context mContext;
    protected View mSkinContentView;

    protected int getLayoutRes() {
        return 0;
    }

    public Skin2FrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mContentView = null;
        this.mSkinContentView = null;
        this.mContext = context;
    }

    protected String TAG() {
        return TAG;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        NightModeManager.register(this);
        NightModeManager.initViewWithHelper(this, getContentView());
        NightModeManager.dispatchViewWithHelper(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        NightModeManager.unregister(this);
    }

    protected View initContentView(int i, ViewGroup viewGroup) {
        if (this.mContentView != null) {
            throw new RuntimeException("SkinExFrameLayout/The init function is called repeatedly!");
        }
        View viewInflate = Skin2.inflate(i, viewGroup, true);
        this.mContentView = viewInflate;
        Object tag = viewInflate.getTag(i);
        if ((tag instanceof Boolean) && ((Boolean) tag).booleanValue()) {
            this.mSkinContentView = this.mContentView;
        }
        return this.mContentView;
    }

    public View getContentView() {
        return this.mContentView;
    }

    public View getSkinContentView() {
        return this.mSkinContentView;
    }

    public View xInflate(int i, ViewGroup viewGroup) {
        return Skin2.inflate(i, viewGroup, viewGroup != null);
    }

    protected int xId(int i) {
        return Objects.isNull(this.mSkinContentView) ? i : Skin2.xId(i);
    }

    public int xDrawableId2(int i) {
        return Objects.isNull(getSkinContentView()) ? i : Skin2.xDrawableId2(i);
    }

    public int xDrawableId2(String str) {
        return Skin2.xDrawableId2(str);
    }

    protected int getId(View view) {
        if (Objects.isNull(this.mSkinContentView)) {
            return view.getId();
        }
        return Skin2.getId(view);
    }

    public View findViewByName(String str) {
        if (Objects.isNull(this.mSkinContentView)) {
            int identifier = getResources().getIdentifier(str, "id", this.mContext.getPackageName());
            if (identifier > 0) {
                return this.mContentView.findViewById(identifier);
            }
            return null;
        }
        int iXId = Skin2.xId(str);
        if (iXId > 0) {
            return this.mSkinContentView.findViewById(iXId);
        }
        return null;
    }

    public static int xColor(int i) {
        return Skin2.getColor(i);
    }

    public static int xColor(String str) {
        return Skin2.getColor(str);
    }

    public int xColorId2(int i) {
        return Objects.isNull(getSkinContentView()) ? i : Skin2.xColorId2(i);
    }

    public int xColorId2(String str) {
        return Skin2.xColorId2(str);
    }

    public static String xString(int i) {
        return Skin2.getString(i);
    }

    public static String xString(String str) {
        return Skin2.getString(str);
    }

    @Override // com.hcn.skinx_night.IUiModeDecoration
    public void onUpdateUiModeView(boolean z) {
        LogUtils.vTag(TAG(), z + " onUpdateUiModeView: " + getClass().getSimpleName());
    }
}
