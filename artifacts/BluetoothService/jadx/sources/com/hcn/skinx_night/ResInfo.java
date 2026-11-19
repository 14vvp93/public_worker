package com.hcn.skinx_night;

/* JADX INFO: loaded from: classes.dex */
class ResInfo {

    /* JADX INFO: renamed from: id */
    public int f90id;
    public String name;
    public String type;

    public ResInfo() {
        reset();
    }

    public void reset() {
        this.f90id = 0;
        this.type = "none";
        this.name = "null";
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("{");
        sb.append("id=");
        sb.append(this.f90id);
        sb.append(", type=");
        sb.append(this.type);
        sb.append(", name=");
        sb.append(this.name);
        sb.append("}");
        return sb.toString();
    }
}
