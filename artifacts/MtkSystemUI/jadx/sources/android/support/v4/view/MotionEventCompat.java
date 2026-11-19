package android.support.v4.view;

import android.view.MotionEvent;

/* JADX INFO: loaded from: classes.dex */
public final class MotionEventCompat {
    public static boolean isFromSource(MotionEvent event, int source) {
        return (event.getSource() & source) == source;
    }
}
