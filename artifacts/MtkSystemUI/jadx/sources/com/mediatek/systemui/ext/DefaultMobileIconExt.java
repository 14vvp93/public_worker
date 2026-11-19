package com.mediatek.systemui.ext;

/* JADX INFO: loaded from: classes.dex */
public class DefaultMobileIconExt implements IMobileIconExt {
    @Override // com.mediatek.systemui.ext.IMobileIconExt
    public int customizeWifiNetCondition(int netCondition) {
        return netCondition;
    }

    @Override // com.mediatek.systemui.ext.IMobileIconExt
    public int customizeMobileNetCondition(int netCondition) {
        return netCondition;
    }
}
