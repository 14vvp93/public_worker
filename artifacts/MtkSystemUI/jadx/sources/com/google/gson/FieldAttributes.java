package com.google.gson;

import java.lang.reflect.Field;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class FieldAttributes {
    private final Field field;

    public FieldAttributes(Field f) {
        this.field = (Field) Objects.requireNonNull(f);
    }

    public String toString() {
        return this.field.toString();
    }
}
