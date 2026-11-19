package com.android.systemui.recents.views.grid;

import android.view.View;
import com.android.systemui.shared.recents.view.AnimateableViewBounds;

/* JADX INFO: loaded from: classes.dex */
class AnimateableGridViewBounds extends AnimateableViewBounds {
    public AnimateableGridViewBounds(View source, int cornerRadius) {
        super(source, cornerRadius);
    }

    @Override // com.android.systemui.shared.recents.view.AnimateableViewBounds
    protected void updateClipBounds() {
    }
}
