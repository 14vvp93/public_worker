package com.hcn.skin.support.app.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import com.hcn.skin.support.app.attr.base.SkinAttr;
import com.hcn.skin.support.resources.SkinCompatResources;

/* JADX INFO: loaded from: classes.dex */
public class DrawableAttr extends SkinAttr {
    private static final String TAG = "DrawableAttr";
    private String mDrawablePos;

    public DrawableAttr(String str) {
        this.mDrawablePos = str;
    }

    @Override // com.hcn.skin.support.app.attr.base.SkinAttr
    protected void applySkin(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            Drawable drawable = SkinCompatResources.getInstance().getDrawable(this.attrValueRefId);
            if (drawable != null) {
                String str = this.mDrawablePos;
                str.hashCode();
                switch (str) {
                    case "drawableRight":
                        textView.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, drawable, (Drawable) null);
                        break;
                    case "drawableStart":
                        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
                        break;
                    case "drawableBottom":
                        textView.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, drawable);
                        break;
                    case "drawableEnd":
                        textView.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, drawable, (Drawable) null);
                        break;
                    case "drawableTop":
                        textView.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, drawable, (Drawable) null, (Drawable) null);
                        break;
                    case "drawableLeft":
                        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
                        break;
                }
            }
        }
    }
}
