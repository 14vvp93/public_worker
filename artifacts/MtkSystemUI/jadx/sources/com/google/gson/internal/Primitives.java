package com.google.gson.internal;

import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes.dex */
public final class Primitives {
    public static boolean isPrimitive(Type type) {
        return (type instanceof Class) && ((Class) type).isPrimitive();
    }
}
