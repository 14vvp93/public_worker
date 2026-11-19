package com.hcn.common.lang;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/* JADX INFO: loaded from: classes.dex */
public class HNumberUtils {
    private static final ThreadLocal<DecimalFormat> DF_THREAD_LOCAL = new ThreadLocal<DecimalFormat>() { // from class: com.hcn.common.lang.HNumberUtils.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public DecimalFormat initialValue() {
            return (DecimalFormat) NumberFormat.getInstance();
        }
    };

    public static DecimalFormat getSafeDecimalFormat() {
        return DF_THREAD_LOCAL.get();
    }

    private HNumberUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String format(float f, int i) {
        return format(f, false, 1, i, true);
    }

    public static String format(float f, int i, boolean z) {
        return format(f, false, 1, i, z);
    }

    public static String format(float f, int i, int i2, boolean z) {
        return format(f, false, i, i2, z);
    }

    public static String format(float f, boolean z, int i) {
        return format(f, z, 1, i, true);
    }

    public static String format(float f, boolean z, int i, boolean z2) {
        return format(f, z, 1, i, z2);
    }

    public static String format(float f, boolean z, int i, int i2, boolean z2) {
        return format(float2Double(f), z, i, i2, z2);
    }

    public static String format(double d, int i) {
        return format(d, false, 1, i, true);
    }

    public static String format(double d, int i, boolean z) {
        return format(d, false, 1, i, z);
    }

    public static String format(double d, int i, int i2, boolean z) {
        return format(d, false, i, i2, z);
    }

    public static String format(double d, boolean z, int i) {
        return format(d, z, 1, i, true);
    }

    public static String format(double d, boolean z, int i, boolean z2) {
        return format(d, z, 1, i, z2);
    }

    public static String format(double d, boolean z, int i, int i2, boolean z2) {
        DecimalFormat safeDecimalFormat = getSafeDecimalFormat();
        safeDecimalFormat.setGroupingUsed(z);
        safeDecimalFormat.setRoundingMode(z2 ? RoundingMode.HALF_UP : RoundingMode.DOWN);
        safeDecimalFormat.setMinimumIntegerDigits(i);
        safeDecimalFormat.setMinimumFractionDigits(i2);
        safeDecimalFormat.setMaximumFractionDigits(i2);
        return safeDecimalFormat.format(d);
    }

    public static double float2Double(float f) {
        return new BigDecimal(String.valueOf(f)).doubleValue();
    }
}
