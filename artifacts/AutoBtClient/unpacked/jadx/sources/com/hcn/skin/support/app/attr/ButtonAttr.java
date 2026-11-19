package com.hcn.skin.support.app.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CheckBox;
import com.hcn.skin.support.app.attr.base.SkinAttr;
import com.hcn.skin.support.resources.SkinCompatResources;

/* JADX INFO: loaded from: classes.dex */
public class ButtonAttr extends SkinAttr {
    private static final String TAG = "ButtonAttr";

    @Override // com.hcn.skin.support.app.attr.base.SkinAttr
    @Deprecated
    protected void applySkin(View view) {
        Drawable drawable;
        if (view instanceof CheckBox) {
            CheckBox checkBox = (CheckBox) view;
            if (isColor()) {
                ColorStateList colorStateList = SkinCompatResources.getInstance().getColorStateList(this.attrValueRefId);
                if (colorStateList != null) {
                    checkBox.setButtonTintList(colorStateList);
                    return;
                }
                return;
            }
            if (isDrawable()) {
                Drawable drawable2 = SkinCompatResources.getInstance().getDrawable(this.attrValueRefId);
                if (drawable2 != null) {
                    checkBox.setButtonDrawable(drawable2);
                    return;
                }
                return;
            }
            if (!isAnim() || (drawable = SkinCompatResources.getInstance().getDrawable(this.attrValueRefId)) == null) {
                return;
            }
            checkBox.setButtonDrawable(drawable);
        }
    }
}
