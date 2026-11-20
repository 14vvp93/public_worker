package com.hcn.skin.support.utils;

/* JADX INFO: loaded from: classes.dex */
public class SkinArrayUtils {
    private SkinArrayUtils() {
        throw new AssertionError();
    }

    public static <V> boolean isEmpty(V[] vArr) {
        return vArr == null || vArr.length == 0;
    }

    public static <V> V getLast(V[] vArr, V v, V v2, boolean z) {
        if (isEmpty(vArr)) {
            return v2;
        }
        int i = 0;
        while (true) {
            if (i >= vArr.length) {
                i = -1;
                break;
            }
            if (SkinObjectUtils.isEquals(v, vArr[i])) {
                break;
            }
            i++;
        }
        if (i == -1) {
            return v2;
        }
        if (i == 0) {
            return z ? vArr[vArr.length - 1] : v2;
        }
        return vArr[i - 1];
    }

    public static <V> V getNext(V[] vArr, V v, V v2, boolean z) {
        if (isEmpty(vArr)) {
            return v2;
        }
        int i = 0;
        while (true) {
            if (i >= vArr.length) {
                i = -1;
                break;
            }
            if (SkinObjectUtils.isEquals(v, vArr[i])) {
                break;
            }
            i++;
        }
        if (i == -1) {
            return v2;
        }
        if (i == vArr.length - 1) {
            return z ? vArr[0] : v2;
        }
        return vArr[i + 1];
    }

    public static <V> V getLast(V[] vArr, V v, boolean z) {
        return (V) getLast(vArr, v, (Object) null, z);
    }

    public static <V> V getNext(V[] vArr, V v, boolean z) {
        return (V) getNext(vArr, v, (Object) null, z);
    }

    public static long getLast(long[] jArr, long j, long j2, boolean z) {
        if (jArr.length == 0) {
            throw new IllegalArgumentException("The length of source array must be greater than 0.");
        }
        return ((Long) getLast(SkinObjectUtils.transformLongArray(jArr), Long.valueOf(j), Long.valueOf(j2), z)).longValue();
    }

    public static long getNext(long[] jArr, long j, long j2, boolean z) {
        if (jArr.length == 0) {
            throw new IllegalArgumentException("The length of source array must be greater than 0.");
        }
        return ((Long) getNext(SkinObjectUtils.transformLongArray(jArr), Long.valueOf(j), Long.valueOf(j2), z)).longValue();
    }

    public static int getLast(int[] iArr, int i, int i2, boolean z) {
        if (iArr.length == 0) {
            throw new IllegalArgumentException("The length of source array must be greater than 0.");
        }
        return ((Integer) getLast(SkinObjectUtils.transformIntArray(iArr), Integer.valueOf(i), Integer.valueOf(i2), z)).intValue();
    }

    public static int getNext(int[] iArr, int i, int i2, boolean z) {
        if (iArr.length == 0) {
            throw new IllegalArgumentException("The length of source array must be greater than 0.");
        }
        return ((Integer) getNext(SkinObjectUtils.transformIntArray(iArr), Integer.valueOf(i), Integer.valueOf(i2), z)).intValue();
    }
}
