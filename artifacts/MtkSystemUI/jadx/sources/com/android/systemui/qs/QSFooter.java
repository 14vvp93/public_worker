package com.android.systemui.qs;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface QSFooter {
    int getHeight();

    void setExpandClickListener(View.OnClickListener onClickListener);

    void setExpanded(boolean z);

    void setExpansion(float f);

    void setKeyguardShowing(boolean z);

    void setListening(boolean z);

    void setQSPanel(QSPanel qSPanel);

    void setVisibility(int i);

    default void disable(int state1, int state2, boolean animate) {
    }
}
