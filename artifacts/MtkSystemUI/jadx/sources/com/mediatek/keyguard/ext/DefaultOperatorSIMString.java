package com.mediatek.keyguard.ext;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class DefaultOperatorSIMString implements IOperatorSIMString {
    @Override // com.mediatek.keyguard.ext.IOperatorSIMString
    public String getOperatorSIMString(String sourceStr, int slotId, IOperatorSIMString.SIMChangedTag simChangedTag, Context context) {
        return sourceStr;
    }
}
