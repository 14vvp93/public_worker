package com.android.systemui.statusbar.policy;

import android.view.View;
import com.android.systemui.R;

/* JADX INFO: loaded from: classes.dex */
public final class HeadsUpUtil {
    public static void setIsClickedHeadsUpNotification(View view, boolean clicked) {
        view.setTag(R.id.is_clicked_heads_up_tag, clicked ? true : null);
    }

    public static boolean isClickedHeadsUpNotification(View view) {
        Boolean clicked = (Boolean) view.getTag(R.id.is_clicked_heads_up_tag);
        return clicked != null && clicked.booleanValue();
    }
}
