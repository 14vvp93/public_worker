package com.mediatek.systemui.ext;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/* JADX INFO: loaded from: classes.dex */
public class DefaultStatusBarPlmnPlugin extends ContextWrapper implements IStatusBarPlmnPlugin {
    public DefaultStatusBarPlmnPlugin(Context context) {
        super(context);
    }

    @Override // com.mediatek.systemui.ext.IStatusBarPlmnPlugin
    public boolean supportCustomizeCarrierLabel() {
        return false;
    }

    @Override // com.mediatek.systemui.ext.IStatusBarPlmnPlugin
    public View customizeCarrierLabel(ViewGroup parentView, View orgCarrierLabel) {
        return null;
    }

    @Override // com.mediatek.systemui.ext.IStatusBarPlmnPlugin
    public void updateCarrierLabelVisibility(boolean force, boolean makeVisible) {
    }

    @Override // com.mediatek.systemui.ext.IStatusBarPlmnPlugin
    public void updateCarrierLabel(int slotId, boolean isSimInserted, boolean isHasSimService, String[] networkNames) {
    }

    @Override // com.mediatek.systemui.ext.IStatusBarPlmnPlugin
    public void addPlmn(LinearLayout statusBarContents, Context contx) {
    }

    @Override // com.mediatek.systemui.ext.IStatusBarPlmnPlugin
    public void setPlmnVisibility(int visibility) {
    }
}
