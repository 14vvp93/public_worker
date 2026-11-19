package kotlin.coroutines.jvm.internal;

import com.autochips.bluetooth.skin.SkinID;
import kotlin.Metadata;

/* JADX INFO: compiled from: boxing.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m61d1 = {"\u0000T\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0005\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\n\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u001a\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0006H\u0001\u001a\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0002\u001a\u00020\tH\u0001\u001a\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\fH\u0001\u001a\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000fH\u0001\u001a\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u0012H\u0001\u001a\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0002\u001a\u00020\u0015H\u0001\u001a\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0002\u001a\u00020\u0018H\u0001¨\u0006\u0019"}, m62d2 = {"boxBoolean", "Ljava/lang/Boolean;", "primitive", SkinID.SKIN_NONE, "boxByte", "Ljava/lang/Byte;", SkinID.SKIN_NONE, "boxChar", "Ljava/lang/Character;", SkinID.SKIN_NONE, "boxDouble", "Ljava/lang/Double;", SkinID.SKIN_NONE, "boxFloat", "Ljava/lang/Float;", SkinID.SKIN_NONE, "boxInt", "Ljava/lang/Integer;", SkinID.SKIN_NONE, "boxLong", "Ljava/lang/Long;", SkinID.SKIN_NONE, "boxShort", "Ljava/lang/Short;", SkinID.SKIN_NONE, "kotlin-stdlib"}, m63k = 2, m64mv = {1, 7, 1}, m66xi = 48)
public final class Boxing {
    public static final Boolean boxBoolean(boolean z) {
        return Boolean.valueOf(z);
    }

    public static final Byte boxByte(byte b) {
        return Byte.valueOf(b);
    }

    public static final Short boxShort(short s) {
        return new Short(s);
    }

    public static final Integer boxInt(int i) {
        return new Integer(i);
    }

    public static final Long boxLong(long j) {
        return new Long(j);
    }

    public static final Float boxFloat(float f) {
        return new Float(f);
    }

    public static final Double boxDouble(double d) {
        return new Double(d);
    }

    public static final Character boxChar(char c) {
        return new Character(c);
    }
}
