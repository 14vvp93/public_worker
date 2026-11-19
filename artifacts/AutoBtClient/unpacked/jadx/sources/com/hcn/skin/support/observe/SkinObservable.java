package com.hcn.skin.support.observe;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class SkinObservable {
    private final ArrayList<SkinObserver> observers = new ArrayList<>();

    public synchronized void addObserver(SkinObserver skinObserver) {
        try {
            if (skinObserver == null) {
                throw new NullPointerException();
            }
            if (!this.observers.contains(skinObserver)) {
                this.observers.add(skinObserver);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void deleteObserver(SkinObserver skinObserver) {
        this.observers.remove(skinObserver);
    }

    public void notifySkinChanged() {
        notifySkinChanged(null);
    }

    public void notifySkinChanged(Object obj) {
        SkinObserver[] skinObserverArr;
        synchronized (this) {
            ArrayList<SkinObserver> arrayList = this.observers;
            skinObserverArr = (SkinObserver[]) arrayList.toArray(new SkinObserver[arrayList.size()]);
        }
        for (int length = skinObserverArr.length - 1; length >= 0; length--) {
            skinObserverArr[length].onSkinChanged(obj);
        }
    }

    public synchronized void deleteObservers() {
        this.observers.clear();
    }

    public synchronized int countObservers() {
        return this.observers.size();
    }
}
