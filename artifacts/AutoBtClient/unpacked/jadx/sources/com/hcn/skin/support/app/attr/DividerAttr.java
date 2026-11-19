package com.hcn.skin.support.app.attr;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ListView;
import com.hcn.skin.support.app.attr.base.SkinAttr;
import com.hcn.skin.support.resources.SkinCompatResources;

/* JADX INFO: loaded from: classes.dex */
public class DividerAttr extends SkinAttr {
    public int dividerHeight = 1;

    @Override // com.hcn.skin.support.app.attr.base.SkinAttr
    protected void applySkin(View view) {
        int color;
        if (view instanceof ListView) {
            ListView listView = (ListView) view;
            if (isDrawable()) {
                Drawable drawable = SkinCompatResources.getInstance().getDrawable(this.attrValueRefId);
                if (drawable != null) {
                    listView.setDivider(drawable);
                    return;
                }
                return;
            }
            if (!isColor() || (color = SkinCompatResources.getInstance().getColor(this.attrValueRefId)) <= 0) {
                return;
            }
            listView.setDivider(new ColorDrawable(color));
            listView.setDividerHeight(this.dividerHeight);
        }
    }
}
