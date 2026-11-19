package com.hcn.skin.support.app.attr;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.hcn.skin.support.app.attr.base.SkinAttr;
import com.hcn.skin.support.resources.SkinCompatResources;

/* JADX INFO: loaded from: classes.dex */
public class ImageViewSrcAttr extends SkinAttr {
    private static final String TAG = "ImageViewSrcAttr";

    @Override // com.hcn.skin.support.app.attr.base.SkinAttr
    protected void applySkin(View view) {
        Drawable drawable;
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            if (isDrawable()) {
                Drawable drawable2 = SkinCompatResources.getInstance().getDrawable(this.attrValueRefId);
                if (drawable2 != null) {
                    imageView.setImageDrawable(drawable2);
                    return;
                }
                return;
            }
            if (isColor()) {
                int color = SkinCompatResources.getInstance().getColor(this.attrValueRefId);
                if (color > 0) {
                    imageView.setImageDrawable(new ColorDrawable(color));
                    return;
                }
                return;
            }
            if (!isAnim() || (drawable = SkinCompatResources.getInstance().getDrawable(this.attrValueRefId)) == null) {
                return;
            }
            imageView.setImageDrawable(drawable);
        }
    }
}
