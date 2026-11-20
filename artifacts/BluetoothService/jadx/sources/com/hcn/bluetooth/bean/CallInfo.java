package com.hcn.bluetooth.bean;

/* JADX INFO: loaded from: classes.dex */
public class CallInfo {
    private String mCallNumber;
    private int mCallState;
    private int mCallTime;

    public CallInfo(String str, int i) {
        this(str, i, 0);
    }

    public CallInfo(String str, int i, int i2) {
        this.mCallNumber = str;
        this.mCallState = i;
        this.mCallTime = i2;
    }

    public String getCallNumber() {
        return this.mCallNumber;
    }

    public void setCallNumber(String str) {
        this.mCallNumber = str;
    }

    public int getCallState() {
        return this.mCallState;
    }

    public void setCallState(int i) {
        this.mCallState = i;
    }

    public int getCallTime() {
        return this.mCallTime;
    }

    public void setCallTime(int i) {
        this.mCallTime = i;
    }

    public void CallTimeAdd() {
        this.mCallTime++;
    }
}
