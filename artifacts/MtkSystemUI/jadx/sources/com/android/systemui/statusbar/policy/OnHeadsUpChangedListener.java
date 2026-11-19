package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.ExpandableNotificationRow;
import com.android.systemui.statusbar.NotificationData;

/* JADX INFO: loaded from: classes.dex */
public interface OnHeadsUpChangedListener {
    default void onHeadsUpPinnedModeChanged(boolean inPinnedMode) {
    }

    default void onHeadsUpPinned(ExpandableNotificationRow headsUp) {
    }

    default void onHeadsUpUnPinned(ExpandableNotificationRow headsUp) {
    }

    default void onHeadsUpStateChanged(NotificationData.Entry entry, boolean isHeadsUp) {
    }
}
