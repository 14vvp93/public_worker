package com.android.systemui.statusbar.policy;

import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;

/* JADX INFO: loaded from: classes.dex */
public interface DarkIconDispatcher {
    public static final Rect sTmpRect = new Rect();
    public static final int[] sTmpInt2 = new int[2];

    public interface DarkReceiver {
        void onDarkChanged(Rect rect, float f, int i);
    }

    void addDarkReceiver(ImageView imageView);

    void addDarkReceiver(DarkReceiver darkReceiver);

    void applyDark(DarkReceiver darkReceiver);

    LightBarTransitionsController getTransitionsController();

    void removeDarkReceiver(ImageView imageView);

    void removeDarkReceiver(DarkReceiver darkReceiver);

    void setIconsDarkArea(Rect rect);

    static int getTint(Rect tintArea, View view, int color) {
        if (isInArea(tintArea, view)) {
            return color;
        }
        return -1;
    }

    static float getDarkIntensity(Rect tintArea, View view, float intensity) {
        if (isInArea(tintArea, view)) {
            return intensity;
        }
        return 0.0f;
    }

    static boolean isInArea(Rect area, View view) {
        if (area.isEmpty()) {
            return true;
        }
        sTmpRect.set(area);
        view.getLocationOnScreen(sTmpInt2);
        int left = sTmpInt2[0];
        int intersectStart = Math.max(left, area.left);
        int intersectEnd = Math.min(view.getWidth() + left, area.right);
        int intersectAmount = Math.max(0, intersectEnd - intersectStart);
        boolean coversFullStatusBar = area.top <= 0;
        boolean majorityOfWidth = 2 * intersectAmount > view.getWidth();
        return majorityOfWidth && coversFullStatusBar;
    }
}
