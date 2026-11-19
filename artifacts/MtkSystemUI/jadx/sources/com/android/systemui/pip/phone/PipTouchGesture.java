package com.android.systemui.pip.phone;

/* JADX INFO: loaded from: classes.dex */
public abstract class PipTouchGesture {
    void onDown(PipTouchState touchState) {
    }

    boolean onMove(PipTouchState touchState) {
        return false;
    }

    boolean onUp(PipTouchState touchState) {
        return false;
    }
}
