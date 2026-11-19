package com.android.systemui.statusbar.policy;

/* JADX INFO: loaded from: classes.dex */
public interface IconLogger {
    void onIconHidden(String str);

    void onIconShown(String str);

    default void onIconVisibility(String tag, boolean visible) {
        if (visible) {
            onIconShown(tag);
        } else {
            onIconHidden(tag);
        }
    }
}
