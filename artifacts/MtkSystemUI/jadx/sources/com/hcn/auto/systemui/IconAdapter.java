package com.hcn.auto.systemui;

import com.android.systemui.R;
import com.hcn.skin.support.resources.SkinCompatResources;
import com.hcn.widgets.dynamicrecyclerview.adapter.AppIconAdapter;
import com.hcn.widgets.dynamicrecyclerview.adapter.BaseViewHolder;
import com.hcn.widgets.dynamicrecyclerview.model.AppIconData;

/* JADX INFO: loaded from: classes.dex */
public class IconAdapter extends AppIconAdapter {
    public IconAdapter(int layoutId) {
        super(layoutId);
    }

    @Override // com.hcn.widgets.dynamicrecyclerview.adapter.BaseRecyclerViewAdapter
    public void bindView(BaseViewHolder<AppIconData> baseViewHolder, AppIconData appIconData, int i) {
        int appIcon = SkinCompatResources.getInstance().getSkinResId("skin_app_icon", "id");
        if (appIcon != 0) {
            baseViewHolder.setImageDrawable(appIcon, appIconData.getAppIcon());
        } else {
            baseViewHolder.setImageDrawable(R.id.app_icon, appIconData.getAppIcon());
        }
    }
}
