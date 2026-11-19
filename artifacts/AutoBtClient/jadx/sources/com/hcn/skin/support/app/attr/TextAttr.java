package com.hcn.skin.support.app.attr;

import android.view.View;
import android.widget.TextView;
import com.hcn.skin.support.app.attr.base.SkinAttr;
import com.hcn.skin.support.resources.SkinCompatResources;

/* JADX INFO: loaded from: classes.dex */
public class TextAttr extends SkinAttr {
    @Override // com.hcn.skin.support.app.attr.base.SkinAttr
    protected void applySkin(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            String string = SkinCompatResources.getInstance().getString(this.attrValueRefId, true);
            if (string != null) {
                textView.setText(string);
            }
        }
    }
}
