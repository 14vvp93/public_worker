package com.autochips.bluetooth.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/* JADX INFO: loaded from: classes.dex */
public class HcnSeekBar extends SeekBar {
    @Override // android.widget.AbsSeekBar, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public HcnSeekBar(Context context) {
        super(context);
    }

    public HcnSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HcnSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
