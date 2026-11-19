package com.mediatek.keyguard.ext;

import android.content.Intent;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class DefaultEmergencyButtonExt implements IEmergencyButtonExt {
    private static final String TAG = "DefaultEmergencyButtonExt";

    @Override // com.mediatek.keyguard.ext.IEmergencyButtonExt
    public boolean showEccByServiceState(boolean[] isServiceSupportEcc, int slotId) {
        for (boolean z : isServiceSupportEcc) {
            if (z) {
                return true;
            }
        }
        return false;
    }

    @Override // com.mediatek.keyguard.ext.IEmergencyButtonExt
    public void customizeEmergencyIntent(Intent intent, int slotId) {
    }

    @Override // com.mediatek.keyguard.ext.IEmergencyButtonExt
    public boolean showEccInNonSecureUnlock() {
        return false;
    }

    @Override // com.mediatek.keyguard.ext.IEmergencyButtonExt
    public void setEmergencyButtonVisibility(View eccButtonView, float alpha) {
    }
}
