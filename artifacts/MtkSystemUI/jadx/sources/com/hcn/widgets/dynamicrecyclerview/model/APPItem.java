package com.hcn.widgets.dynamicrecyclerview.model;

/* JADX INFO: loaded from: classes.dex */
public class APPItem {
    public String className;
    public String packageName;
    public String shortName;

    public APPItem(String shortName, String packageName, String className) {
        this.shortName = shortName;
        this.packageName = packageName;
        this.className = className;
    }
}
