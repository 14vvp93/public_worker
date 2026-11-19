package com.mediatek.keyguard.ext;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class DefaultCarrierTextExt implements ICarrierTextExt {
    @Override // com.mediatek.keyguard.ext.ICarrierTextExt
    public CharSequence customizeCarrierTextCapital(CharSequence carrierText) {
        if (carrierText != null) {
            return carrierText.toString().toUpperCase();
        }
        return null;
    }

    @Override // com.mediatek.keyguard.ext.ICarrierTextExt
    public CharSequence customizeCarrierText(CharSequence carrierText, CharSequence simMessage, int simId) {
        return carrierText;
    }

    @Override // com.mediatek.keyguard.ext.ICarrierTextExt
    public boolean showCarrierTextWhenSimMissing(boolean isSimMissing, int simId) {
        return isSimMissing;
    }

    @Override // com.mediatek.keyguard.ext.ICarrierTextExt
    public CharSequence customizeCarrierTextWhenCardTypeLocked(CharSequence carrierText, Context context, int phoneId, boolean isCardLocked) {
        return carrierText;
    }

    @Override // com.mediatek.keyguard.ext.ICarrierTextExt
    public CharSequence customizeCarrierTextWhenSimMissing(CharSequence carrierText) {
        return carrierText;
    }

    @Override // com.mediatek.keyguard.ext.ICarrierTextExt
    public String customizeCarrierTextDivider(String divider) {
        return divider;
    }
}
