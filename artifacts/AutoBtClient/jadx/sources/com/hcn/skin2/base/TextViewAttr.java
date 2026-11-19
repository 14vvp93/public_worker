package com.hcn.skin2.base;

import android.widget.TextView;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class TextViewAttr {
    public final int rro_string_id;
    public final WeakReference<TextView> view;

    private TextViewAttr() {
        this.view = new WeakReference<>(null);
        this.rro_string_id = 0;
    }

    public TextViewAttr(TextView textView, int i) {
        this.view = new WeakReference<>(textView);
        this.rro_string_id = i;
    }
}
