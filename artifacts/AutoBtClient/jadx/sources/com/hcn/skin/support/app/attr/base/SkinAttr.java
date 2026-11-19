package com.hcn.skin.support.app.attr.base;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public abstract class SkinAttr implements Cloneable {
    protected static final String RES_TYPE_NAME_ANIM = "anim";
    protected static final String RES_TYPE_NAME_COLOR = "color";
    protected static final String RES_TYPE_NAME_DRAWABLE = "drawable";
    protected static final String RES_TYPE_NAME_MIPMAP = "mipmap";
    protected String attrName;
    protected int attrValueRefId;
    protected String attrValueRefName;
    protected String attrValueTypeName;

    protected abstract void applySkin(View view);

    public void apply(View view) {
        applySkin(view);
    }

    protected boolean isDrawable() {
        return "drawable".equals(this.attrValueTypeName) || "mipmap".equals(this.attrValueTypeName);
    }

    protected boolean isColor() {
        return "color".equals(this.attrValueTypeName);
    }

    protected boolean isAnim() {
        return RES_TYPE_NAME_ANIM.equals(this.attrValueTypeName);
    }

    public String toString() {
        return "SkinAttr{attrName='" + this.attrName + "', attrValueRefId=" + this.attrValueRefId + ", attrValueRefName='" + this.attrValueRefName + "', attrValueTypeName='" + this.attrValueTypeName + "'}";
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SkinAttr m202clone() {
        try {
            return (SkinAttr) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
