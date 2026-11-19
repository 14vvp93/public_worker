package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.R;

/* JADX INFO: loaded from: classes.dex */
public class TopWindow extends TextView implements DarkIconDispatcher.DarkReceiver {
    private boolean mAttached;
    private final boolean mShowDark;

    public TopWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Clock, 0, 0);
        try {
            this.mShowDark = a.getBoolean(1, true);
        } finally {
            a.recycle();
        }
    }

    public TopWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopWindow(Context context) {
        this(context, null);
    }

    public void onDarkChanged(Rect area, float darkIntensity, int tint) {
        setTextColor(DarkIconDispatcher.getTint(area, this, tint));
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mAttached) {
            this.mAttached = true;
            if (this.mShowDark) {
                ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).addDarkReceiver(this);
            }
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mAttached) {
            this.mAttached = false;
        }
        if (this.mShowDark) {
            ((DarkIconDispatcher) Dependency.get(DarkIconDispatcher.class)).removeDarkReceiver(this);
        }
    }
}
