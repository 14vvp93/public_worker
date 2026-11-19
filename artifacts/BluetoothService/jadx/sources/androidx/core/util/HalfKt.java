package androidx.core.util;

import android.util.Half;
import com.autochips.bluetooth.skin.SkinID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Half.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m61d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\n\n\u0002\u0010\u000e\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0003H\u0087\b\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0004H\u0087\b\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0005H\u0087\b¨\u0006\u0006"}, m62d2 = {"toHalf", "Landroid/util/Half;", SkinID.SKIN_NONE, SkinID.SKIN_NONE, SkinID.SKIN_NONE, SkinID.SKIN_NONE, "core-ktx_release"}, m63k = 2, m64mv = {1, 7, 1}, m66xi = 48)
public final class HalfKt {
    public static final Half toHalf(short s) {
        Half halfValueOf = Half.valueOf(s);
        Intrinsics.checkNotNullExpressionValue(halfValueOf, "valueOf(this)");
        return halfValueOf;
    }

    public static final Half toHalf(float f) {
        Half halfValueOf = Half.valueOf(f);
        Intrinsics.checkNotNullExpressionValue(halfValueOf, "valueOf(this)");
        return halfValueOf;
    }

    public static final Half toHalf(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Half halfValueOf = Half.valueOf(str);
        Intrinsics.checkNotNullExpressionValue(halfValueOf, "valueOf(this)");
        return halfValueOf;
    }

    public static final Half toHalf(double d) {
        Half halfValueOf = Half.valueOf((float) d);
        Intrinsics.checkNotNullExpressionValue(halfValueOf, "valueOf(this)");
        return halfValueOf;
    }
}
