package kotlin.time;

import com.autochips.bluetooth.skin.SkinID;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DurationJvm.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m61d1 = {"\u0000.\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\u001a\u0010\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0000\u001a\u0018\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u001c\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\b¨\u0006\u0011"}, m62d2 = {"durationAssertionsEnabled", SkinID.SKIN_NONE, "getDurationAssertionsEnabled", "()Z", "precisionFormats", SkinID.SKIN_NONE, "Ljava/lang/ThreadLocal;", "Ljava/text/DecimalFormat;", "[Ljava/lang/ThreadLocal;", "createFormatForDecimals", "decimals", SkinID.SKIN_NONE, "formatToExactDecimals", SkinID.SKIN_NONE, "value", SkinID.SKIN_NONE, "formatUpToDecimals", "kotlin-stdlib"}, m63k = 2, m64mv = {1, 7, 1}, m66xi = 48)
public final class DurationJvmKt {
    private static final boolean durationAssertionsEnabled = false;
    private static final ThreadLocal<DecimalFormat>[] precisionFormats;

    static {
        ThreadLocal<DecimalFormat>[] threadLocalArr = new ThreadLocal[4];
        for (int i = 0; i < 4; i++) {
            threadLocalArr[i] = new ThreadLocal<>();
        }
        precisionFormats = threadLocalArr;
    }

    public static final boolean getDurationAssertionsEnabled() {
        return durationAssertionsEnabled;
    }

    private static final DecimalFormat createFormatForDecimals(int i) {
        DecimalFormat decimalFormat = new DecimalFormat("0");
        if (i > 0) {
            decimalFormat.setMinimumFractionDigits(i);
        }
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat;
    }

    public static final String formatToExactDecimals(double d, int i) {
        DecimalFormat decimalFormatCreateFormatForDecimals;
        ThreadLocal<DecimalFormat>[] threadLocalArr = precisionFormats;
        if (i < threadLocalArr.length) {
            ThreadLocal<DecimalFormat> threadLocal = threadLocalArr[i];
            DecimalFormat decimalFormatCreateFormatForDecimals2 = threadLocal.get();
            if (decimalFormatCreateFormatForDecimals2 == null) {
                decimalFormatCreateFormatForDecimals2 = createFormatForDecimals(i);
                threadLocal.set(decimalFormatCreateFormatForDecimals2);
            } else {
                Intrinsics.checkNotNullExpressionValue(decimalFormatCreateFormatForDecimals2, "get() ?: default().also(this::set)");
            }
            decimalFormatCreateFormatForDecimals = decimalFormatCreateFormatForDecimals2;
        } else {
            decimalFormatCreateFormatForDecimals = createFormatForDecimals(i);
        }
        String str = decimalFormatCreateFormatForDecimals.format(d);
        Intrinsics.checkNotNullExpressionValue(str, "format.format(value)");
        return str;
    }

    public static final String formatUpToDecimals(double d, int i) {
        DecimalFormat decimalFormatCreateFormatForDecimals = createFormatForDecimals(0);
        decimalFormatCreateFormatForDecimals.setMaximumFractionDigits(i);
        String str = decimalFormatCreateFormatForDecimals.format(d);
        Intrinsics.checkNotNullExpressionValue(str, "createFormatForDecimals(… }\n        .format(value)");
        return str;
    }
}
