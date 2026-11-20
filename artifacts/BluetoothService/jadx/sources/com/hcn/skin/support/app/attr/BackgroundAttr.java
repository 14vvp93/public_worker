package com.hcn.skin.support.app.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.hcn.skin.support.app.attr.base.SkinAttr;
import com.hcn.skin.support.resources.SkinCompatResources;

/* JADX INFO: loaded from: classes.dex */
public class BackgroundAttr extends SkinAttr {
    private static final String TAG = "BackgroundAttr";

    @Override // com.hcn.skin.support.app.attr.base.SkinAttr
    @Deprecated
    protected void applySkin(View view) {
        Drawable drawable;
        if (isColor()) {
            int color = SkinCompatResources.getInstance().getColor(this.attrValueRefId);
            if (color > 0) {
                view.setBackgroundColor(color);
                return;
            }
            return;
        }
        if (isDrawable()) {
            Drawable drawable2 = SkinCompatResources.getInstance().getDrawable(this.attrValueRefId);
            if (drawable2 != null) {
                view.setBackgroundDrawable(drawable2);
                return;
            }
            return;
        }
        if (!isAnim() || (drawable = SkinCompatResources.getInstance().getDrawable(this.attrValueRefId)) == null) {
            return;
        }
        view.setBackgroundDrawable(drawable);
    }
}
