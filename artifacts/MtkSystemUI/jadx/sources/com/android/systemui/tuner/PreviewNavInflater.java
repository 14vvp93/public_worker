package com.android.systemui.tuner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.phone.NavigationBarInflaterView;

/* JADX INFO: loaded from: classes.dex */
public class PreviewNavInflater extends NavigationBarInflaterView {
    public PreviewNavInflater(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override // com.android.systemui.statusbar.phone.NavigationBarInflaterView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override // com.android.systemui.statusbar.phone.NavigationBarInflaterView, com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        if ("sysui_nav_bar".equals(key)) {
            if (isValidLayout(newValue)) {
                super.onTuningChanged(key, newValue);
                return;
            }
            return;
        }
        super.onTuningChanged(key, newValue);
    }

    private boolean isValidLayout(String newValue) {
        if (newValue == null) {
            return true;
        }
        int lastGravitySeparator = 0;
        int separatorCount = 0;
        for (int separatorCount2 = 0; separatorCount2 < newValue.length(); separatorCount2++) {
            if (newValue.charAt(separatorCount2) == ";".charAt(0)) {
                if (separatorCount2 == 0 || separatorCount2 - lastGravitySeparator == 1) {
                    return false;
                }
                lastGravitySeparator = separatorCount2;
                separatorCount++;
            }
        }
        if (separatorCount == 2 && newValue.length() - lastGravitySeparator != 1) {
            return true;
        }
        return false;
    }
}
