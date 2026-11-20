package com.hcn.skin.support.app.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Spinner;
import com.hcn.skin.support.app.attr.base.SkinAttr;
import com.hcn.skin.support.resources.SkinCompatResources;

/* JADX INFO: loaded from: classes.dex */
public class PopupBackgroundAttr extends SkinAttr {
    private static final String TAG = "PopupBackgroundAttr";

    @Override // com.hcn.skin.support.app.attr.base.SkinAttr
    protected void applySkin(View view) {
        Drawable drawable;
        if (view instanceof Spinner) {
            Spinner spinner = (Spinner) view;
            if (isDrawable()) {
                Drawable drawable2 = SkinCompatResources.getInstance().getDrawable(this.attrValueRefId);
                if (drawable2 != null) {
                    spinner.setPopupBackgroundDrawable(drawable2);
                    return;
                }
                return;
            }
            if (!isAnim() || (drawable = SkinCompatResources.getInstance().getDrawable(this.attrValueRefId)) == null) {
                return;
            }
            spinner.setPopupBackgroundDrawable(drawable);
        }
    }
}
