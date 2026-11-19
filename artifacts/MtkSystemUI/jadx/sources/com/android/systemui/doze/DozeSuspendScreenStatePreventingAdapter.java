package com.android.systemui.doze;

import com.android.systemui.statusbar.phone.DozeParameters;

/* JADX INFO: loaded from: classes.dex */
public class DozeSuspendScreenStatePreventingAdapter extends DozeMachine.Service.Delegate {
    DozeSuspendScreenStatePreventingAdapter(DozeMachine.Service inner) {
        super(inner);
    }

    @Override // com.android.systemui.doze.DozeMachine.Service.Delegate, com.android.systemui.doze.DozeMachine.Service
    public void setDozeScreenState(int state) {
        if (state == 4) {
            state = 3;
        }
        super.setDozeScreenState(state);
    }

    public static DozeMachine.Service wrapIfNeeded(DozeMachine.Service inner, DozeParameters params) {
        return isNeeded(params) ? new DozeSuspendScreenStatePreventingAdapter(inner) : inner;
    }

    private static boolean isNeeded(DozeParameters params) {
        return !params.getDozeSuspendDisplayStateSupported();
    }
}
