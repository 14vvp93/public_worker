package com.hcn.skin.support.app.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import com.hcn.skin.support.app.attr.base.SkinAttr;
import com.hcn.skin.support.resources.SkinCompatResources;

/* JADX INFO: loaded from: classes.dex */
public class DrawableStartAttr extends SkinAttr {
    private static final String TAG = "DrawableStartAttr";

    @Override // com.hcn.skin.support.app.attr.base.SkinAttr
    protected void applySkin(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            Drawable drawable = SkinCompatResources.getInstance().getDrawable(this.attrValueRefId);
            if (drawable != null) {
                textView.setCompoundDrawablesWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
            }
        }
    }
}
