package kotlin;

import com.autochips.bluetooth.skin.SkinID;

/* JADX INFO: compiled from: ULong.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m61d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0003\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u0004H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0005\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\bH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\t\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\nH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\fH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\r\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, m62d2 = {"toULong", "Lkotlin/ULong;", SkinID.SKIN_NONE, "(B)J", SkinID.SKIN_NONE, "(D)J", SkinID.SKIN_NONE, "(F)J", SkinID.SKIN_NONE, "(I)J", SkinID.SKIN_NONE, "(J)J", SkinID.SKIN_NONE, "(S)J", "kotlin-stdlib"}, m63k = 2, m64mv = {1, 7, 1}, m66xi = 48)
public final class ULongKt {
    private static final long toULong(byte b) {
        return ULong.m379constructorimpl(b);
    }

    private static final long toULong(short s) {
        return ULong.m379constructorimpl(s);
    }

    private static final long toULong(int i) {
        return ULong.m379constructorimpl(i);
    }

    private static final long toULong(long j) {
        return ULong.m379constructorimpl(j);
    }

    private static final long toULong(float f) {
        return UnsignedKt.doubleToULong(f);
    }

    private static final long toULong(double d) {
        return UnsignedKt.doubleToULong(d);
    }
}
