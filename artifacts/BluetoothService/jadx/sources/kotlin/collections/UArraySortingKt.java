package kotlin.collections;

import com.autochips.bluetooth.skin.SkinID;
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
@Metadata(m61d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0010\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001a\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u0014\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010\u0016\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b \u0010\u0018\u001a*\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b!\u0010\u001a\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\""}, m62d2 = {"partition", SkinID.SKIN_NONE, "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", SkinID.SKIN_NONE, "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "fromIndex", "toIndex", "sortArray-4UcCI2c", "sortArray-oBK06Vg", "sortArray--nroSd4", "sortArray-Aa5vz7o", "kotlin-stdlib"}, m63k = 2, m64mv = {1, 7, 1}, m66xi = 48)
public final class UArraySortingKt {
    /* JADX INFO: renamed from: partition-4UcCI2c, reason: not valid java name */
    private static final int m659partition4UcCI2c(byte[] bArr, int i, int i2) {
        int i3;
        byte bM282getw2LRezQ = UByteArray.m282getw2LRezQ(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM282getw2LRezQ = UByteArray.m282getw2LRezQ(bArr, i) & UByte.MAX_VALUE;
                i3 = bM282getw2LRezQ & UByte.MAX_VALUE;
                if (Intrinsics.compare(iM282getw2LRezQ, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UByteArray.m282getw2LRezQ(bArr, i2) & UByte.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte bM282getw2LRezQ2 = UByteArray.m282getw2LRezQ(bArr, i);
                UByteArray.m287setVurrAj0(bArr, i, UByteArray.m282getw2LRezQ(bArr, i2));
                UByteArray.m287setVurrAj0(bArr, i2, bM282getw2LRezQ2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort-4UcCI2c, reason: not valid java name */
    private static final void m663quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int iM659partition4UcCI2c = m659partition4UcCI2c(bArr, i, i2);
        int i3 = iM659partition4UcCI2c - 1;
        if (i < i3) {
            m663quickSort4UcCI2c(bArr, i, i3);
        }
        if (iM659partition4UcCI2c < i2) {
            m663quickSort4UcCI2c(bArr, iM659partition4UcCI2c, i2);
        }
    }

    /* JADX INFO: renamed from: partition-Aa5vz7o, reason: not valid java name */
    private static final int m660partitionAa5vz7o(short[] sArr, int i, int i2) {
        int i3;
        short sM542getMh2AYeg = UShortArray.m542getMh2AYeg(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM542getMh2AYeg = UShortArray.m542getMh2AYeg(sArr, i) & UShort.MAX_VALUE;
                i3 = sM542getMh2AYeg & UShort.MAX_VALUE;
                if (Intrinsics.compare(iM542getMh2AYeg, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UShortArray.m542getMh2AYeg(sArr, i2) & UShort.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                short sM542getMh2AYeg2 = UShortArray.m542getMh2AYeg(sArr, i);
                UShortArray.m547set01HTLdE(sArr, i, UShortArray.m542getMh2AYeg(sArr, i2));
                UShortArray.m547set01HTLdE(sArr, i2, sM542getMh2AYeg2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort-Aa5vz7o, reason: not valid java name */
    private static final void m664quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int iM660partitionAa5vz7o = m660partitionAa5vz7o(sArr, i, i2);
        int i3 = iM660partitionAa5vz7o - 1;
        if (i < i3) {
            m664quickSortAa5vz7o(sArr, i, i3);
        }
        if (iM660partitionAa5vz7o < i2) {
            m664quickSortAa5vz7o(sArr, iM660partitionAa5vz7o, i2);
        }
    }

    /* JADX INFO: renamed from: partition-oBK06Vg, reason: not valid java name */
    private static final int m661partitionoBK06Vg(int[] iArr, int i, int i2) {
        int iM360getpVg5ArA = UIntArray.m360getpVg5ArA(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.uintCompare(UIntArray.m360getpVg5ArA(iArr, i), iM360getpVg5ArA) < 0) {
                i++;
            }
            while (UnsignedKt.uintCompare(UIntArray.m360getpVg5ArA(iArr, i2), iM360getpVg5ArA) > 0) {
                i2--;
            }
            if (i <= i2) {
                int iM360getpVg5ArA2 = UIntArray.m360getpVg5ArA(iArr, i);
                UIntArray.m365setVXSXFK8(iArr, i, UIntArray.m360getpVg5ArA(iArr, i2));
                UIntArray.m365setVXSXFK8(iArr, i2, iM360getpVg5ArA2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort-oBK06Vg, reason: not valid java name */
    private static final void m665quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int iM661partitionoBK06Vg = m661partitionoBK06Vg(iArr, i, i2);
        int i3 = iM661partitionoBK06Vg - 1;
        if (i < i3) {
            m665quickSortoBK06Vg(iArr, i, i3);
        }
        if (iM661partitionoBK06Vg < i2) {
            m665quickSortoBK06Vg(iArr, iM661partitionoBK06Vg, i2);
        }
    }

    /* JADX INFO: renamed from: partition--nroSd4, reason: not valid java name */
    private static final int m658partitionnroSd4(long[] jArr, int i, int i2) {
        long jM438getsVKNKU = ULongArray.m438getsVKNKU(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.ulongCompare(ULongArray.m438getsVKNKU(jArr, i), jM438getsVKNKU) < 0) {
                i++;
            }
            while (UnsignedKt.ulongCompare(ULongArray.m438getsVKNKU(jArr, i2), jM438getsVKNKU) > 0) {
                i2--;
            }
            if (i <= i2) {
                long jM438getsVKNKU2 = ULongArray.m438getsVKNKU(jArr, i);
                ULongArray.m443setk8EXiF4(jArr, i, ULongArray.m438getsVKNKU(jArr, i2));
                ULongArray.m443setk8EXiF4(jArr, i2, jM438getsVKNKU2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort--nroSd4, reason: not valid java name */
    private static final void m662quickSortnroSd4(long[] jArr, int i, int i2) {
        int iM658partitionnroSd4 = m658partitionnroSd4(jArr, i, i2);
        int i3 = iM658partitionnroSd4 - 1;
        if (i < i3) {
            m662quickSortnroSd4(jArr, i, i3);
        }
        if (iM658partitionnroSd4 < i2) {
            m662quickSortnroSd4(jArr, iM658partitionnroSd4, i2);
        }
    }

    /* JADX INFO: renamed from: sortArray-4UcCI2c, reason: not valid java name */
    public static final void m667sortArray4UcCI2c(byte[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m663quickSort4UcCI2c(array, i, i2 - 1);
    }

    /* JADX INFO: renamed from: sortArray-Aa5vz7o, reason: not valid java name */
    public static final void m668sortArrayAa5vz7o(short[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m664quickSortAa5vz7o(array, i, i2 - 1);
    }

    /* JADX INFO: renamed from: sortArray-oBK06Vg, reason: not valid java name */
    public static final void m669sortArrayoBK06Vg(int[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m665quickSortoBK06Vg(array, i, i2 - 1);
    }

    /* JADX INFO: renamed from: sortArray--nroSd4, reason: not valid java name */
    public static final void m666sortArraynroSd4(long[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m662quickSortnroSd4(array, i, i2 - 1);
    }
}
