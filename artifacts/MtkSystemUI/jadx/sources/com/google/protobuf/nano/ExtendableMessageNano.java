package com.google.protobuf.nano;

import com.google.protobuf.nano.ExtendableMessageNano;

/* JADX INFO: loaded from: classes.dex */
public abstract class ExtendableMessageNano<M extends ExtendableMessageNano<M>> extends MessageNano {
    protected FieldArray unknownFieldData;

    @Override // com.google.protobuf.nano.MessageNano
    /* JADX INFO: renamed from: clone */
    public M mo31clone() throws CloneNotSupportedException {
        M m = (M) super.mo31clone();
        InternalNano.cloneUnknownFieldData(this, m);
        return m;
    }
}
