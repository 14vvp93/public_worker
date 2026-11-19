package com.android.systemui.qs.car;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import com.android.settingslib.Utils;
import com.android.systemui.statusbar.policy.DarkIconDispatcher;

/* JADX INFO: loaded from: classes.dex */
public class CarStatusBarHeader extends LinearLayout {
    public CarStatusBarHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        int colorForeground = Utils.getColorAttr(getContext(), R.attr.colorForeground);
        float intensity = colorForeground == -1 ? 0.0f : 1.0f;
        Rect tintArea = new Rect(0, 0, 0, 0);
        applyDarkness(com.android.systemui.R.id.clock, tintArea, intensity, colorForeground);
    }

    private void applyDarkness(int id, Rect tintArea, float intensity, int color) {
        KeyEvent.Callback callbackFindViewById = findViewById(id);
        if (callbackFindViewById instanceof DarkIconDispatcher.DarkReceiver) {
            ((DarkIconDispatcher.DarkReceiver) callbackFindViewById).onDarkChanged(tintArea, intensity, color);
        }
    }
}
