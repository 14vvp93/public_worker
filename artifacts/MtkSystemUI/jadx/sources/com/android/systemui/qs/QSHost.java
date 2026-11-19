package com.android.systemui.qs;

import android.content.Context;
import com.android.systemui.qs.external.TileServices;

/* JADX INFO: loaded from: classes.dex */
public interface QSHost {

    public interface Callback {
        void onTilesChanged();
    }

    void collapsePanels();

    Context getContext();

    TileServices getTileServices();

    int indexOf(String str);

    void openPanels();

    void warn(String str, Throwable th);
}
