package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;

/* JADX INFO: loaded from: classes.dex */
public class BatteryVoltageTopWindow extends TopWindow {
    public BatteryVoltageTopWindow(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public BatteryVoltageTopWindow(Context context) {
        super(context, null);
    }

    public BatteryVoltageTopWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override // com.android.systemui.statusbar.policy.TopWindow, com.android.systemui.statusbar.policy.DarkIconDispatcher.DarkReceiver
    public void onDarkChanged(Rect area, float darkIntensity, int tint) {
        int color = DarkIconDispatcher.getTint(area, this, tint);
        setTextColor(color);
        Drawable[] bg = getCompoundDrawables();
        Drawable drawableLeft = bg[0];
        if (drawableLeft instanceof VectorDrawable) {
            VectorDrawable vd = (VectorDrawable) drawableLeft;
            vd.setTint(color);
        }
    }
}
