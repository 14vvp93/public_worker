package com.android.systemui.statusbar.policy;

import android.net.Uri;
import android.service.notification.ZenModeConfig;

/* JADX INFO: loaded from: classes.dex */
public interface ZenModeController extends CallbackController<Callback> {
    boolean areNotificationsHiddenInShade();

    ZenModeConfig getConfig();

    ZenModeConfig.ZenRule getManualRule();

    long getNextAlarm();

    int getZen();

    boolean isVolumeRestricted();

    void setZen(int i, Uri uri, String str);

    public interface Callback {
        default void onZenChanged(int zen) {
        }

        default void onNextAlarmChanged() {
        }

        default void onZenAvailableChanged(boolean available) {
        }

        default void onEffectsSupressorChanged() {
        }

        default void onManualRuleChanged(ZenModeConfig.ZenRule rule) {
        }

        default void onConfigChanged(ZenModeConfig config) {
        }
    }
}
