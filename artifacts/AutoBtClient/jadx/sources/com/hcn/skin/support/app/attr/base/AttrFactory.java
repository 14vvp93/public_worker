package com.hcn.skin.support.app.attr.base;

import com.hcn.skin.support.app.attr.BackgroundAttr;
import com.hcn.skin.support.app.attr.ButtonAttr;
import com.hcn.skin.support.app.attr.DividerAttr;
import com.hcn.skin.support.app.attr.DrawableAttr;
import com.hcn.skin.support.app.attr.ImageViewSrcAttr;
import com.hcn.skin.support.app.attr.ListSelectorAttr;
import com.hcn.skin.support.app.attr.PopupBackgroundAttr;
import com.hcn.skin.support.app.attr.ProgressDrawableAttr;
import com.hcn.skin.support.app.attr.TextAttr;
import com.hcn.skin.support.app.attr.TextColorAttr;
import com.hcn.skin.support.app.attr.ThumbAttr;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class AttrFactory {
    private static HashMap<String, SkinAttr> sSupportAttr;

    static {
        HashMap<String, SkinAttr> map = new HashMap<>();
        sSupportAttr = map;
        map.put("background", new BackgroundAttr());
        sSupportAttr.put("textColor", new TextColorAttr());
        sSupportAttr.put("text", new TextAttr());
        sSupportAttr.put("listSelector", new ListSelectorAttr());
        sSupportAttr.put("divider", new DividerAttr());
        sSupportAttr.put("src", new ImageViewSrcAttr());
        sSupportAttr.put("thumb", new ThumbAttr());
        sSupportAttr.put("progressDrawable", new ProgressDrawableAttr());
        sSupportAttr.put("button", new ButtonAttr());
        sSupportAttr.put("popupBackground", new PopupBackgroundAttr());
        makeDrawableAttr();
    }

    private static void makeDrawableAttr() {
        String[] strArr = {"drawableTop", "drawableBottom", "drawableLeft", "drawableRight", "drawableStart", "drawableEnd"};
        for (int i = 0; i < 6; i++) {
            sSupportAttr.put(strArr[i], new DrawableAttr(strArr[i]));
        }
    }

    public static SkinAttr get(String str, int i, String str2, String str3) {
        SkinAttr skinAttrM202clone = sSupportAttr.get(str).m202clone();
        if (skinAttrM202clone == null) {
            return null;
        }
        skinAttrM202clone.attrName = str;
        skinAttrM202clone.attrValueRefId = i;
        skinAttrM202clone.attrValueRefName = str2;
        skinAttrM202clone.attrValueTypeName = str3;
        return skinAttrM202clone;
    }

    public static boolean isSupportedAttr(String str) {
        return sSupportAttr.containsKey(str);
    }

    public static void addSupportAttr(String str, SkinAttr skinAttr) {
        sSupportAttr.put(str, skinAttr);
    }
}
