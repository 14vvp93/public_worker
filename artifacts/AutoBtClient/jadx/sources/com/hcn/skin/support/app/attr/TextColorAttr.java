package com.hcn.skin.support.app.attr;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;
import com.hcn.skin.support.app.attr.base.SkinAttr;
import com.hcn.skin.support.resources.SkinCompatResources;

/* JADX INFO: loaded from: classes.dex */
public class TextColorAttr extends SkinAttr {
    @Override // com.hcn.skin.support.app.attr.base.SkinAttr
    protected void applySkin(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            ColorStateList colorStateList = SkinCompatResources.getInstance().getColorStateList(this.attrValueRefId);
            if (colorStateList != null) {
                textView.setTextColor(colorStateList);
            }
        }
    }
}
