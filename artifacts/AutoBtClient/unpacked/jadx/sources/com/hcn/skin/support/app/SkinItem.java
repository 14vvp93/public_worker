package com.hcn.skin.support.app;

import android.view.View;
import com.hcn.skin.support.app.attr.base.SkinAttr;
import com.hcn.skin.support.utils.SkinListUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SkinItem {
    public List<SkinAttr> attrs = new ArrayList();
    public View view;

    public void apply() {
        if (SkinListUtils.isEmpty(this.attrs)) {
            return;
        }
        Iterator<SkinAttr> it = this.attrs.iterator();
        while (it.hasNext()) {
            it.next().apply(this.view);
        }
    }

    public void clean() {
        if (SkinListUtils.isEmpty(this.attrs)) {
            return;
        }
        this.attrs.clear();
    }

    public String toString() {
        return "SkinItem [view=" + getClass().getSimpleName() + ", attrs=" + this.attrs + "]";
    }
}
