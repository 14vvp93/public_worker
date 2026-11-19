package com.hcn.common.lang;

/* JADX INFO: loaded from: classes.dex */
public abstract class RunnableEx implements Runnable {
    private Object mObject;

    public void callback(Object obj) {
    }

    @Override // java.lang.Runnable
    public void run() {
    }

    public Object getObject() {
        return this.mObject;
    }

    public void setObject(Object obj) {
        this.mObject = obj;
    }
}
