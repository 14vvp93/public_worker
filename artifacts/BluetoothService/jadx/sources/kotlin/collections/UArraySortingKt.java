package kotlin.collections;

import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UArraySorting.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0010\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001a\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u0014\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010\u0016\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b \u0010\u0018\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b!\u0010\u001a\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\""}, d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "fromIndex", "toIndex", "sortArray-4UcCI2c", "sortArray-oBK06Vg", "sortArray--nroSd4", "sortArray-Aa5vz7o", "kotlin-stdlib"}, k = 2, mv = {1, 7, 1}, xi = 48)
public final class UArraySortingKt {
    /* JADX INFO: renamed from: partition-4UcCI2c, reason: not valid java name */
    private static final int m606partition4UcCI2c(byte[] bArr, int i, int i2) {
        int i3;
        byte bM229getw2LRezQ = UByteArray.m229getw2LRezQ(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM229getw2LRezQ = UByteArray.m229getw2LRezQ(bArr, i) & UByte.MAX_VALUE;
                i3 = bM229getw2LRezQ & UByte.MAX_VALUE;
                if (Intrinsics.compare(iM229getw2LRezQ, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UByteArray.m229getw2LRezQ(bArr, i2) & UByte.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte bM229getw2LRezQ2 = UByteArray.m229getw2LRezQ(bArr, i);
                UByteArray.m234setVurrAj0(bArr, i, UByteArray.m229getw2LRezQ(bArr, i2));
                UByteArray.m234setVurrAj0(bArr, i2, bM229getw2LRezQ2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort-4UcCI2c, reason: not valid java name */
    private static final void m610quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int iM606partition4UcCI2c = m606partition4UcCI2c(bArr, i, i2);
        int i3 = iM606partition4UcCI2c - 1;
        if (i < i3) {
            m610quickSort4UcCI2c(bArr, i, i3);
        }
        if (iM606partition4UcCI2c < i2) {
            m610quickSort4UcCI2c(bArr, iM606partition4UcCI2c, i2);
        }
    }

    /* JADX INFO: renamed from: partition-Aa5vz7o, reason: not valid java name */
    private static final int m607partitionAa5vz7o(short[] sArr, int i, int i2) {
        int i3;
        short sM489getMh2AYeg = UShortArray.m489getMh2AYeg(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM489getMh2AYeg = UShortArray.m489getMh2AYeg(sArr, i) & UShort.MAX_VALUE;
                i3 = sM489getMh2AYeg & UShort.MAX_VALUE;
                if (Intrinsics.compare(iM489getMh2AYeg, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UShortArray.m489getMh2AYeg(sArr, i2) & UShort.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                short sM489getMh2AYeg2 = UShortArray.m489getMh2AYeg(sArr, i);
                UShortArray.m494set01HTLdE(sArr, i, UShortArray.m489getMh2AYeg(sArr, i2));
                UShortArray.m494set01HTLdE(sArr, i2, sM489getMh2AYeg2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort-Aa5vz7o, reason: not valid java name */
    private static final void m611quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int iM607partitionAa5vz7o = m607partitionAa5vz7o(sArr, i, i2);
        int i3 = iM607partitionAa5vz7o - 1;
        if (i < i3) {
            m611quickSortAa5vz7o(sArr, i, i3);
        }
        if (iM607partitionAa5vz7o < i2) {
            m611quickSortAa5vz7o(sArr, iM607partitionAa5vz7o, i2);
        }
    }

    /* JADX INFO: renamed from: partition-oBK06Vg, reason: not valid java name */
    private static final int m608partitionoBK06Vg(int[] iArr, int i, int i2) {
        int iM307getpVg5ArA = UIntArray.m307getpVg5ArA(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.uintCompare(UIntArray.m307getpVg5ArA(iArr, i), iM307getpVg5ArA) < 0) {
                i++;
            }
            while (UnsignedKt.uintCompare(UIntArray.m307getpVg5ArA(iArr, i2), iM307getpVg5ArA) > 0) {
                i2--;
            }
            if (i <= i2) {
                int iM307getpVg5ArA2 = UIntArray.m307getpVg5ArA(iArr, i);
                UIntArray.m312setVXSXFK8(iArr, i, UIntArray.m307getpVg5ArA(iArr, i2));
                UIntArray.m312setVXSXFK8(iArr, i2, iM307getpVg5ArA2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort-oBK06Vg, reason: not valid java name */
    private static final void m612quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int iM608partitionoBK06Vg = m608partitionoBK06Vg(iArr, i, i2);
        int i3 = iM608partitionoBK06Vg - 1;
        if (i < i3) {
            m612quickSortoBK06Vg(iArr, i, i3);
        }
        if (iM608partitionoBK06Vg < i2) {
            m612quickSortoBK06Vg(iArr, iM608partitionoBK06Vg, i2);
        }
    }

    /* JADX INFO: renamed from: partition--nroSd4, reason: not valid java name */
    private static final int m605partitionnroSd4(long[] jArr, int i, int i2) {
        long jM385getsVKNKU = ULongArray.m385getsVKNKU(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.ulongCompare(ULongArray.m385getsVKNKU(jArr, i), jM385getsVKNKU) < 0) {
                i++;
            }
            while (UnsignedKt.ulongCompare(ULongArray.m385getsVKNKU(jArr, i2), jM385getsVKNKU) > 0) {
                i2--;
            }
            if (i <= i2) {
                long jM385getsVKNKU2 = ULongArray.m385getsVKNKU(jArr, i);
                ULongArray.m390setk8EXiF4(jArr, i, ULongArray.m385getsVKNKU(jArr, i2));
                ULongArray.m390setk8EXiF4(jArr, i2, jM385getsVKNKU2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort--nroSd4, reason: not valid java name */
    private static final void m609quickSortnroSd4(long[] jArr, int i, int i2) {
        int iM605partitionnroSd4 = m605partitionnroSd4(jArr, i, i2);
        int i3 = iM605partitionnroSd4 - 1;
        if (i < i3) {
            m609quickSortnroSd4(jArr, i, i3);
        }
        if (iM605partitionnroSd4 < i2) {
            m609quickSortnroSd4(jArr, iM605partitionnroSd4, i2);
        }
    }

    /* JADX INFO: renamed from: sortArray-4UcCI2c, reason: not valid java name */
    public static final void m614sortArray4UcCI2c(byte[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m610quickSort4UcCI2c(array, i, i2 - 1);
    }

    /* JADX INFO: renamed from: sortArray-Aa5vz7o, reason: not valid java name */
    public static final void m615sortArrayAa5vz7o(short[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m611quickSortAa5vz7o(array, i, i2 - 1);
    }

    /* JADX INFO: renamed from: sortArray-oBK06Vg, reason: not valid java name */
    public static final void m616sortArrayoBK06Vg(int[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m612quickSortoBK06Vg(array, i, i2 - 1);
    }

    /* JADX INFO: renamed from: sortArray--nroSd4, reason: not valid java name */
    public static final void m613sortArraynroSd4(long[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m609quickSortnroSd4(array, i, i2 - 1);
    }
}
