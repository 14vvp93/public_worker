package com.mediatek.systemui.qs.tiles.ext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import com.android.systemui.plugins.qs.QSTile;
import com.mediatek.systemui.statusbar.extcb.IconIdWrapper;

/* JADX INFO: loaded from: classes.dex */
public class QsIconWrapper extends QSTile.Icon {
    private static SparseArray<QsIconWrapper> sQsIconWrapperMap = new SparseArray<>();
    private final IconIdWrapper mIconWrapper;

    public QsIconWrapper(IconIdWrapper iconWrapper) {
        this.mIconWrapper = iconWrapper;
    }

    public static QsIconWrapper get(int resId, IconIdWrapper iconId) {
        QsIconWrapper icon = sQsIconWrapperMap.get(resId);
        if (icon == null) {
            QsIconWrapper icon2 = new QsIconWrapper(iconId);
            sQsIconWrapperMap.put(resId, icon2);
            return icon2;
        }
        return icon;
    }

    @Override // com.android.systemui.plugins.qs.QSTile.Icon
    public Drawable getDrawable(Context context) {
        return this.mIconWrapper.getDrawable();
    }

    @Override // com.android.systemui.plugins.qs.QSTile.Icon
    public int hashCode() {
        return this.mIconWrapper.hashCode();
    }

    public boolean equals(Object o) {
        return this.mIconWrapper.equals(o);
    }
}
