package com.hcn.auto.systemui;

import android.content.Context;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class RecyclerViewDialog extends com.hcn.widgets.dynamicrecyclerview.RecyclerViewDialog {
    private int mWindowHeight;
    private int mWindowWidth;

    public RecyclerViewDialog(Context context) {
        super(context);
    }

    @Override // com.hcn.widgets.dynamicrecyclerview.RecyclerViewDialog
    public com.hcn.widgets.dynamicrecyclerview.RecyclerViewDialog show() {
        getDialog().getWindow().setLayout(this.mWindowWidth, this.mWindowHeight);
        Log.d("RecyclerViewDialog", "show: mWindowWidth = " + this.mWindowWidth + " mWindowHeight = " + this.mWindowHeight);
        getDialog().show();
        return this;
    }

    @Override // com.hcn.widgets.dynamicrecyclerview.RecyclerViewDialog
    public com.hcn.widgets.dynamicrecyclerview.RecyclerViewDialog setDialogWidth(int width) {
        this.mWindowWidth = width;
        return this;
    }

    @Override // com.hcn.widgets.dynamicrecyclerview.RecyclerViewDialog
    public com.hcn.widgets.dynamicrecyclerview.RecyclerViewDialog setDialogHeight(int height) {
        this.mWindowHeight = height;
        return this;
    }
}
