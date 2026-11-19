package com.hcn.skin.support.observe;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class SkinObservable {
    private final ArrayList<SkinObserver> observers = new ArrayList<>();

    public void notifySkinChanged() {
        notifySkinChanged(null);
    }

    public void notifySkinChanged(Object arg) {
        SkinObserver[] arrLocal;
        synchronized (this) {
            arrLocal = (SkinObserver[]) this.observers.toArray(new SkinObserver[this.observers.size()]);
        }
        for (int i = arrLocal.length - 1; i >= 0; i--) {
            arrLocal[i].onSkinChanged(arg);
        }
    }
}
