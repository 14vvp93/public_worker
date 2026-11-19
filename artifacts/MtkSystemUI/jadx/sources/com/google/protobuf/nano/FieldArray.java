package com.google.protobuf.nano;

/* JADX INFO: loaded from: classes.dex */
public final class FieldArray implements Cloneable {
    private static final FieldData DELETED = new FieldData();
    private FieldData[] mData;
    private int[] mFieldNumbers;
    private boolean mGarbage;
    private int mSize;

    FieldArray() {
        this(10);
    }

    FieldArray(int initialCapacity) {
        this.mGarbage = false;
        int initialCapacity2 = idealIntArraySize(initialCapacity);
        this.mFieldNumbers = new int[initialCapacity2];
        this.mData = new FieldData[initialCapacity2];
        this.mSize = 0;
    }

    private void gc() {
        int n = this.mSize;
        int[] keys = this.mFieldNumbers;
        FieldData[] values = this.mData;
        int o = 0;
        for (int o2 = 0; o2 < n; o2++) {
            FieldData val = values[o2];
            if (val != DELETED) {
                if (o2 != o) {
                    keys[o] = keys[o2];
                    values[o] = val;
                    values[o2] = null;
                }
                o++;
            }
        }
        this.mGarbage = false;
        this.mSize = o;
    }

    int size() {
        if (this.mGarbage) {
            gc();
        }
        return this.mSize;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FieldArray)) {
            return false;
        }
        FieldArray other = (FieldArray) o;
        if (size() != other.size()) {
            return false;
        }
        return arrayEquals(this.mFieldNumbers, other.mFieldNumbers, this.mSize) && arrayEquals(this.mData, other.mData, this.mSize);
    }

    public int hashCode() {
        if (this.mGarbage) {
            gc();
        }
        int result = 17;
        for (int i = 0; i < this.mSize; i++) {
            int result2 = (31 * result) + this.mFieldNumbers[i];
            result = this.mData[i].hashCode() + (31 * result2);
        }
        return result;
    }

    private int idealIntArraySize(int need) {
        return idealByteArraySize(need * 4) / 4;
    }

    private int idealByteArraySize(int need) {
        for (int i = 4; i < 32; i++) {
            if (need <= (1 << i) - 12) {
                return (1 << i) - 12;
            }
        }
        return need;
    }

    private boolean arrayEquals(int[] a, int[] b, int size) {
        for (int i = 0; i < size; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean arrayEquals(FieldData[] a, FieldData[] b, int size) {
        for (int i = 0; i < size; i++) {
            if (!a[i].equals(b[i])) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final FieldArray m32clone() {
        int size = size();
        FieldArray clone = new FieldArray(size);
        int i = 0;
        System.arraycopy(this.mFieldNumbers, 0, clone.mFieldNumbers, 0, size);
        while (true) {
            int i2 = i;
            if (i2 < size) {
                if (this.mData[i2] != null) {
                    clone.mData[i2] = this.mData[i2].m33clone();
                }
                i = i2 + 1;
            } else {
                clone.mSize = size;
                return clone;
            }
        }
    }
}
