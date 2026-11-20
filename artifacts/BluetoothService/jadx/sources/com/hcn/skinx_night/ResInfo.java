package com.hcn.skinx_night;

/* JADX INFO: loaded from: classes.dex */
class ResInfo {
    public int id;
    public String name;
    public String type;

    public ResInfo() {
        reset();
    }

    public void reset() {
        this.id = 0;
        this.type = "none";
        this.name = "null";
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("{");
        sb.append("id=");
        sb.append(this.id);
        sb.append(", type=");
        sb.append(this.type);
        sb.append(", name=");
        sb.append(this.name);
        sb.append("}");
        return sb.toString();
    }

    public boolean isValid() {
        return (this.id == 0 || this.type == null) ? false : true;
    }
}
