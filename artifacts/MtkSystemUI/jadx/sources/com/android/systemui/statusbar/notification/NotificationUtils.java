package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import com.android.internal.util.NotificationColorUtil;
import com.android.systemui.R;

/* JADX INFO: loaded from: classes.dex */
public class NotificationUtils {
    private static final int[] sLocationBase = new int[2];
    private static final int[] sLocationOffset = new int[2];

    public static boolean isGrayscale(ImageView v, NotificationColorUtil colorUtil) {
        Object isGrayscale = v.getTag(R.id.icon_is_grayscale);
        if (isGrayscale != null) {
            return Boolean.TRUE.equals(isGrayscale);
        }
        boolean grayscale = colorUtil.isGrayscaleIcon(v.getDrawable());
        v.setTag(R.id.icon_is_grayscale, Boolean.valueOf(grayscale));
        return grayscale;
    }

    public static float interpolate(float start, float end, float amount) {
        return ((1.0f - amount) * start) + (end * amount);
    }

    public static int interpolateColors(int startColor, int endColor, float amount) {
        return Color.argb((int) interpolate(Color.alpha(startColor), Color.alpha(endColor), amount), (int) interpolate(Color.red(startColor), Color.red(endColor), amount), (int) interpolate(Color.green(startColor), Color.green(endColor), amount), (int) interpolate(Color.blue(startColor), Color.blue(endColor), amount));
    }

    public static float getRelativeYOffset(View offsetView, View baseView) {
        baseView.getLocationOnScreen(sLocationBase);
        offsetView.getLocationOnScreen(sLocationOffset);
        return sLocationOffset[1] - sLocationBase[1];
    }

    public static int getFontScaledHeight(Context context, int dimenId) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(dimenId);
        float factor = Math.max(1.0f, context.getResources().getDisplayMetrics().scaledDensity / context.getResources().getDisplayMetrics().density);
        return (int) (dimensionPixelSize * factor);
    }
}
