package com.hcn.skin2.enhance;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface IOverlayCallback {
    boolean isSupportRROView(String str);

    View tryReplaceAndCreateView(String str, Context context, AttributeSet attributeSet);
}
