package com.android.systemui.tuner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.widget.ScrollView;

/* JADX INFO: loaded from: classes.dex */
public class AutoScrollView extends ScrollView {
    public AutoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override // android.view.View
    public boolean onDragEvent(DragEvent event) {
        if (event.getAction() == 2) {
            int y = (int) event.getY();
            int height = getHeight();
            int scrollPadding = (int) (height * 0.1f);
            if (y < scrollPadding) {
                scrollBy(0, y - scrollPadding);
            } else if (y > height - scrollPadding) {
                scrollBy(0, (y - height) + scrollPadding);
            }
        }
        return false;
    }
}
