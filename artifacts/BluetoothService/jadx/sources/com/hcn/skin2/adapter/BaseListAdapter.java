package com.hcn.skin2.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.hcn.common.misc.LogUtils;
import com.hcn.skin2.Skin2;
import com.hcn.skinx_night.IUiModeDecoration;
import com.hcn.skinx_night.NightModeManager;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseListAdapter extends BaseAdapter implements IUiModeDecoration {
    private static final String TAG = "BaseListAdapter";
    protected final Context mContext;
    protected SparseBooleanArray mItemViewTypeSkinFlags = new SparseBooleanArray();

    public interface ItemViewType {
        public static final int GALLERY_LIST_ITEM = 2;
        public static final int MEDIA_LIST_ITEM = 1;
    }

    public int getLayoutRes(int i) {
        return 0;
    }

    public BaseListAdapter(Context context) {
        this.mContext = context;
        NightModeManager.register(this);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        NightModeManager.unregister(this);
    }

    protected String TAG() {
        return TAG;
    }

    @Override // com.hcn.skinx_night.IUiModeDecoration
    public void onUpdateUiModeView(boolean z) {
        LogUtils.vTag(TAG(), z + " onUpdateUiModeView: " + getClass().getSimpleName());
    }

    public View inflateItemView(int i, ViewGroup viewGroup, boolean z) {
        int layoutRes = getLayoutRes(i);
        View viewInflate = Skin2.inflate(layoutRes, viewGroup, z);
        Object tag = viewInflate.getTag(layoutRes);
        if ((tag instanceof Boolean) && ((Boolean) tag).booleanValue()) {
            this.mItemViewTypeSkinFlags.put(i, true);
        }
        NightModeManager.initViewWithHelper(this, viewInflate);
        return viewInflate;
    }

    public int xId(int i) {
        return Skin2.xId(i);
    }

    public int xId(int i, int i2) {
        return !this.mItemViewTypeSkinFlags.get(i) ? i2 : Skin2.xId(i2);
    }

    public static int xInteger(int i) {
        return Skin2.getInteger(i);
    }

    public static int xInteger(String str) {
        return Skin2.getInteger(str);
    }
}
