package com.hcn.skin.support.app.attr;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.SeekBar;
import com.hcn.skin.support.app.attr.base.SkinAttr;
import com.hcn.skin.support.resources.SkinCompatResources;

/* JADX INFO: loaded from: classes.dex */
public class ThumbAttr extends SkinAttr {
    @Override // com.hcn.skin.support.app.attr.base.SkinAttr
    protected void applySkin(View view) {
        int color;
        if (view instanceof SeekBar) {
            SeekBar seekBar = (SeekBar) view;
            if (isDrawable()) {
                Drawable drawable = SkinCompatResources.getInstance().getDrawable(this.attrValueRefId);
                if (drawable != null) {
                    seekBar.setThumb(drawable);
                    return;
                }
                return;
            }
            if (!isColor() || (color = SkinCompatResources.getInstance().getColor(this.attrValueRefId)) <= 0) {
                return;
            }
            seekBar.setThumb(new ColorDrawable(color));
        }
    }
}
