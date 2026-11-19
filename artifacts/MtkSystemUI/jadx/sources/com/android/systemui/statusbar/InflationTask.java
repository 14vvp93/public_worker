package com.android.systemui.statusbar;

/* JADX INFO: loaded from: classes.dex */
public interface InflationTask {
    void abort();

    default void supersedeTask(InflationTask task) {
    }
}
