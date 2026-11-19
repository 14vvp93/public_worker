package com.hcn.skin.support.app.attr;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AbsListView;
import com.hcn.skin.support.app.attr.base.SkinAttr;
import com.hcn.skin.support.resources.SkinCompatResources;

/* JADX INFO: loaded from: classes.dex */
public class ListSelectorAttr extends SkinAttr {
    @Override // com.hcn.skin.support.app.attr.base.SkinAttr
    protected void applySkin(View view) {
        int color;
        if (view instanceof AbsListView) {
            AbsListView absListView = (AbsListView) view;
            if (isDrawable()) {
                Drawable drawable = SkinCompatResources.getInstance().getDrawable(this.attrValueRefId);
                if (drawable != null) {
                    absListView.setSelector(drawable);
                    return;
                }
                return;
            }
            if (!isColor() || (color = SkinCompatResources.getInstance().getColor(this.attrValueRefId)) <= 0) {
                return;
            }
            absListView.setSelector(new ColorDrawable(color));
        }
    }
}
