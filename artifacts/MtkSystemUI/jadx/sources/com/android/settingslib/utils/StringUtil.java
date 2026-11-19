package com.android.settingslib.utils;

import android.content.Context;
import android.icu.text.MeasureFormat;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.text.SpannableStringBuilder;
import android.text.style.TtsSpan;
import java.util.ArrayList;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class StringUtil {
    public static CharSequence formatElapsedTime(Context context, double millis, boolean withSeconds) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        int seconds = (int) Math.floor(millis / 1000.0d);
        if (!withSeconds) {
            seconds += 30;
        }
        int days = 0;
        int hours = 0;
        int minutes = 0;
        if (seconds >= 86400) {
            days = seconds / 86400;
            seconds -= 86400 * days;
        }
        if (seconds >= 3600) {
            hours = seconds / 3600;
            seconds -= hours * 3600;
        }
        if (seconds >= 60) {
            minutes = seconds / 60;
            seconds -= minutes * 60;
        }
        ArrayList<Measure> measureList = new ArrayList<>(4);
        if (days > 0) {
            measureList.add(new Measure(Integer.valueOf(days), MeasureUnit.DAY));
        }
        if (hours > 0) {
            measureList.add(new Measure(Integer.valueOf(hours), MeasureUnit.HOUR));
        }
        if (minutes > 0) {
            measureList.add(new Measure(Integer.valueOf(minutes), MeasureUnit.MINUTE));
        }
        if (withSeconds && seconds > 0) {
            measureList.add(new Measure(Integer.valueOf(seconds), MeasureUnit.SECOND));
        }
        if (measureList.size() == 0) {
            measureList.add(new Measure(0, withSeconds ? MeasureUnit.SECOND : MeasureUnit.MINUTE));
        }
        Measure[] measureArray = (Measure[]) measureList.toArray(new Measure[measureList.size()]);
        Locale locale = context.getResources().getConfiguration().locale;
        MeasureFormat measureFormat = MeasureFormat.getInstance(locale, MeasureFormat.FormatWidth.SHORT);
        sb.append((CharSequence) measureFormat.formatMeasures(measureArray));
        if (measureArray.length == 1 && MeasureUnit.MINUTE.equals(measureArray[0].getUnit())) {
            TtsSpan ttsSpan = new TtsSpan.MeasureBuilder().setNumber(minutes).setUnit("minute").build();
            sb.setSpan(ttsSpan, 0, sb.length(), 33);
        }
        return sb;
    }
}
