package com.google.gson.internal.bind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class ISO8601Utils {
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");

    /* JADX WARN: Code duplicated, block: B:24:0x0068 A[Catch: IllegalArgumentException -> 0x0053, NumberFormatException -> 0x0058, IndexOutOfBoundsException -> 0x005d, TryCatch #7 {NumberFormatException -> 0x0058, IllegalArgumentException -> 0x0053, IndexOutOfBoundsException -> 0x005d, blocks: (B:12:0x003b, B:14:0x0041, B:24:0x0068, B:26:0x0079, B:27:0x007b, B:29:0x0088, B:30:0x008a, B:32:0x0091, B:37:0x009d, B:42:0x00ad, B:44:0x00b6, B:57:0x00e7), top: B:120:0x003b }] */
    /* JADX WARN: Code duplicated, block: B:26:0x0079 A[Catch: IllegalArgumentException -> 0x0053, NumberFormatException -> 0x0058, IndexOutOfBoundsException -> 0x005d, TryCatch #7 {NumberFormatException -> 0x0058, IllegalArgumentException -> 0x0053, IndexOutOfBoundsException -> 0x005d, blocks: (B:12:0x003b, B:14:0x0041, B:24:0x0068, B:26:0x0079, B:27:0x007b, B:29:0x0088, B:30:0x008a, B:32:0x0091, B:37:0x009d, B:42:0x00ad, B:44:0x00b6, B:57:0x00e7), top: B:120:0x003b }] */
    /* JADX WARN: Code duplicated, block: B:29:0x0088 A[Catch: IllegalArgumentException -> 0x0053, NumberFormatException -> 0x0058, IndexOutOfBoundsException -> 0x005d, TryCatch #7 {NumberFormatException -> 0x0058, IllegalArgumentException -> 0x0053, IndexOutOfBoundsException -> 0x005d, blocks: (B:12:0x003b, B:14:0x0041, B:24:0x0068, B:26:0x0079, B:27:0x007b, B:29:0x0088, B:30:0x008a, B:32:0x0091, B:37:0x009d, B:42:0x00ad, B:44:0x00b6, B:57:0x00e7), top: B:120:0x003b }] */
    /* JADX WARN: Code duplicated, block: B:44:0x00b6 A[Catch: IllegalArgumentException -> 0x0053, NumberFormatException -> 0x0058, IndexOutOfBoundsException -> 0x005d, TRY_LEAVE, TryCatch #7 {NumberFormatException -> 0x0058, IllegalArgumentException -> 0x0053, IndexOutOfBoundsException -> 0x005d, blocks: (B:12:0x003b, B:14:0x0041, B:24:0x0068, B:26:0x0079, B:27:0x007b, B:29:0x0088, B:30:0x008a, B:32:0x0091, B:37:0x009d, B:42:0x00ad, B:44:0x00b6, B:57:0x00e7), top: B:120:0x003b }] */
    /* JADX WARN: Code duplicated, block: B:47:0x00cd  */
    /* JADX WARN: Code duplicated, block: B:48:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:49:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:51:0x00d8  */
    /* JADX WARN: Code duplicated, block: B:54:0x00df A[Catch: IllegalArgumentException -> 0x0208, NumberFormatException -> 0x020d, IndexOutOfBoundsException -> 0x0212, TRY_LEAVE, TryCatch #5 {IndexOutOfBoundsException -> 0x0212, NumberFormatException -> 0x020d, IllegalArgumentException -> 0x0208, blocks: (B:3:0x0006, B:5:0x0018, B:6:0x001a, B:8:0x0026, B:9:0x0028, B:52:0x00d9, B:54:0x00df, B:64:0x00fb), top: B:121:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:57:0x00e7 A[Catch: IllegalArgumentException -> 0x0053, NumberFormatException -> 0x0058, IndexOutOfBoundsException -> 0x005d, TRY_ENTER, TRY_LEAVE, TryCatch #7 {NumberFormatException -> 0x0058, IllegalArgumentException -> 0x0053, IndexOutOfBoundsException -> 0x005d, blocks: (B:12:0x003b, B:14:0x0041, B:24:0x0068, B:26:0x0079, B:27:0x007b, B:29:0x0088, B:30:0x008a, B:32:0x0091, B:37:0x009d, B:42:0x00ad, B:44:0x00b6, B:57:0x00e7), top: B:120:0x003b }] */
    /* JADX WARN: Code duplicated, block: B:59:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:71:0x0126  */
    /* JADX WARN: Code duplicated, block: B:72:0x0128 A[Catch: IllegalArgumentException -> 0x0202, NumberFormatException -> 0x0204, IndexOutOfBoundsException -> 0x0206, TryCatch #4 {NumberFormatException -> 0x0204, IllegalArgumentException -> 0x0202, IndexOutOfBoundsException -> 0x0206, blocks: (B:90:0x01c3, B:69:0x011b, B:73:0x0139, B:75:0x0147, B:88:0x01be, B:78:0x0156, B:80:0x0176, B:83:0x018b, B:84:0x01b1, B:72:0x0128, B:66:0x0104, B:67:0x0118, B:92:0x01f6, B:93:0x0201), top: B:123:0x00dd }] */
    /* JADX WARN: Code duplicated, block: B:92:0x01f6 A[Catch: IllegalArgumentException -> 0x0202, NumberFormatException -> 0x0204, IndexOutOfBoundsException -> 0x0206, TryCatch #4 {NumberFormatException -> 0x0204, IllegalArgumentException -> 0x0202, IndexOutOfBoundsException -> 0x0206, blocks: (B:90:0x01c3, B:69:0x011b, B:73:0x0139, B:75:0x0147, B:88:0x01be, B:78:0x0156, B:80:0x0176, B:83:0x018b, B:84:0x01b1, B:72:0x0128, B:66:0x0104, B:67:0x0118, B:92:0x01f6, B:93:0x0201), top: B:123:0x00dd }] */
    /* JADX WARN: Instruction removed from duplicated block: B:72:0x0128, please report this as an issue */
    public static Date parse(String date, ParsePosition pos) throws ParseException {
        String str;
        char timezoneIndicator;
        String timezoneOffset;
        String str2;
        String timezoneOffset2;
        TimeZone timezone;
        int offset;
        int offset2;
        int offset3;
        char c;
        int offset4;
        int seconds;
        int offset5;
        int parseEndOffset;
        int fraction;
        try {
            int offset6 = pos.getIndex();
            int offset7 = offset6 + 4;
            int offset8 = parseInt(date, offset6, offset7);
            if (checkOffset(date, offset7, '-')) {
                offset7++;
            }
            int offset9 = offset7 + 2;
            int offset10 = parseInt(date, offset7, offset9);
            if (checkOffset(date, offset9, '-')) {
                offset9++;
            }
            int endOffset = offset9 + 2;
            int offset11 = parseInt(date, offset9, endOffset);
            int hour = 0;
            int minutes = 0;
            int seconds2 = 0;
            int milliseconds = 0;
            boolean hasT = checkOffset(date, endOffset, 'T');
            if (hasT) {
                if (hasT) {
                    int offset12 = endOffset + 1;
                    offset2 = offset12 + 2;
                    hour = parseInt(date, offset12, offset2);
                    if (checkOffset(date, offset2, ':')) {
                        offset2++;
                    }
                    offset3 = offset2 + 2;
                    minutes = parseInt(date, offset2, offset3);
                    if (checkOffset(date, offset3, ':')) {
                        offset3++;
                    }
                    endOffset = offset3;
                    if (date.length() > endOffset) {
                        offset4 = endOffset + 2;
                        seconds = parseInt(date, endOffset, offset4);
                        if (seconds > 59) {
                            seconds = 59;
                        }
                        seconds2 = seconds;
                        if (checkOffset(date, offset4, '.')) {
                            offset5 = offset4 + 1;
                            endOffset = indexOfNonDigit(date, offset5 + 1);
                            parseEndOffset = Math.min(endOffset, offset5 + 3);
                            fraction = parseInt(date, offset5, parseEndOffset);
                            switch (parseEndOffset - offset5) {
                                case 1:
                                    milliseconds = fraction * 100;
                                    break;
                                case 2:
                                    milliseconds = fraction * 10;
                                    break;
                                default:
                                    milliseconds = fraction;
                                    break;
                            }
                        } else {
                            endOffset = offset4;
                        }
                    }
                }
                if (date.length() > endOffset) {
                    throw new IllegalArgumentException("No time zone indicator");
                }
                timezoneIndicator = date.charAt(endOffset);
                if (timezoneIndicator == 'Z') {
                    timezone = TIMEZONE_UTC;
                    offset = endOffset + 1;
                } else {
                    if (timezoneIndicator != '+') {
                    }
                    timezoneOffset = date.substring(endOffset);
                    if (timezoneOffset.length() >= 5) {
                        str2 = timezoneOffset;
                    } else {
                        str2 = timezoneOffset + "00";
                    }
                    timezoneOffset2 = str2;
                    int length = endOffset + timezoneOffset2.length();
                    if ("+0000".equals(timezoneOffset2)) {
                    }
                    timezone = TIMEZONE_UTC;
                    offset = length;
                }
                Calendar calendar = new GregorianCalendar(timezone);
                calendar.setLenient(false);
                calendar.set(1, offset8);
                calendar.set(2, offset10 - 1);
                calendar.set(5, offset11);
                calendar.set(11, hour);
                calendar.set(12, minutes);
                calendar.set(13, seconds2);
                calendar.set(14, milliseconds);
                pos.setIndex(offset);
                return calendar.getTime();
            }
            try {
                if (date.length() <= endOffset) {
                    Calendar calendar2 = new GregorianCalendar(offset8, offset10 - 1, offset11);
                    calendar2.setLenient(false);
                    pos.setIndex(endOffset);
                    return calendar2.getTime();
                }
                if (hasT) {
                    int offset13 = endOffset + 1;
                    offset2 = offset13 + 2;
                    hour = parseInt(date, offset13, offset2);
                    if (checkOffset(date, offset2, ':')) {
                        offset2++;
                    }
                    offset3 = offset2 + 2;
                    minutes = parseInt(date, offset2, offset3);
                    if (checkOffset(date, offset3, ':')) {
                        offset3++;
                    }
                    endOffset = offset3;
                    if (date.length() > endOffset && (c = date.charAt(endOffset)) != 'Z' && c != '+' && c != '-') {
                        offset4 = endOffset + 2;
                        seconds = parseInt(date, endOffset, offset4);
                        if (seconds > 59 && seconds < 63) {
                            seconds = 59;
                        }
                        seconds2 = seconds;
                        if (checkOffset(date, offset4, '.')) {
                            offset5 = offset4 + 1;
                            endOffset = indexOfNonDigit(date, offset5 + 1);
                            parseEndOffset = Math.min(endOffset, offset5 + 3);
                            fraction = parseInt(date, offset5, parseEndOffset);
                            switch (parseEndOffset - offset5) {
                                case 1:
                                    milliseconds = fraction * 100;
                                    break;
                                case 2:
                                    milliseconds = fraction * 10;
                                    break;
                                default:
                                    milliseconds = fraction;
                                    break;
                            }
                        } else {
                            endOffset = offset4;
                        }
                    }
                }
                try {
                    if (date.length() > endOffset) {
                        throw new IllegalArgumentException("No time zone indicator");
                    }
                    timezoneIndicator = date.charAt(endOffset);
                    if (timezoneIndicator == 'Z') {
                        timezone = TIMEZONE_UTC;
                        offset = endOffset + 1;
                    } else if (timezoneIndicator != '+' || timezoneIndicator == '-') {
                        timezoneOffset = date.substring(endOffset);
                        if (timezoneOffset.length() >= 5) {
                            str2 = timezoneOffset;
                        } else {
                            str2 = timezoneOffset + "00";
                        }
                        timezoneOffset2 = str2;
                        int length2 = endOffset + timezoneOffset2.length();
                        if (!"+0000".equals(timezoneOffset2) || "+00:00".equals(timezoneOffset2)) {
                            timezone = TIMEZONE_UTC;
                        } else {
                            String timezoneId = "GMT" + timezoneOffset2;
                            TimeZone timezone2 = TimeZone.getTimeZone(timezoneId);
                            String act = timezone2.getID();
                            if (!act.equals(timezoneId)) {
                                String cleaned = act.replace(":", "");
                                if (!cleaned.equals(timezoneId)) {
                                    throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + timezoneId + " given, resolves to " + timezone2.getID());
                                }
                            }
                            timezone = timezone2;
                        }
                        offset = length2;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Invalid time zone indicator '");
                        sb.append(timezoneIndicator);
                        sb.append("'");
                        throw new IndexOutOfBoundsException(sb.toString());
                    }
                    Calendar calendar3 = new GregorianCalendar(timezone);
                    calendar3.setLenient(false);
                    calendar3.set(1, offset8);
                    calendar3.set(2, offset10 - 1);
                    calendar3.set(5, offset11);
                    calendar3.set(11, hour);
                    calendar3.set(12, minutes);
                    calendar3.set(13, seconds2);
                    calendar3.set(14, milliseconds);
                    pos.setIndex(offset);
                    return calendar3.getTime();
                } catch (NumberFormatException e) {
                    fail = e;
                } catch (IllegalArgumentException e2) {
                    fail = e2;
                } catch (IndexOutOfBoundsException e3) {
                    fail = e3;
                }
            } catch (NumberFormatException e4) {
                fail = e4;
            } catch (IllegalArgumentException e5) {
                fail = e5;
            } catch (IndexOutOfBoundsException e6) {
                fail = e6;
            }
        } catch (IndexOutOfBoundsException e7) {
            fail = e7;
        } catch (NumberFormatException e8) {
            fail = e8;
        } catch (IllegalArgumentException e9) {
            fail = e9;
        }
        if (date == null) {
            str = null;
        } else {
            str = '\"' + date + '\"';
        }
        String input = str;
        String msg = fail.getMessage();
        if (msg == null || msg.isEmpty()) {
            msg = "(" + fail.getClass().getName() + ")";
        }
        ParseException ex = new ParseException("Failed to parse date [" + input + "]: " + msg, pos.getIndex());
        ex.initCause(fail);
        throw ex;
    }

    private static boolean checkOffset(String value, int offset, char expected) {
        return offset < value.length() && value.charAt(offset) == expected;
    }

    private static int parseInt(String value, int beginIndex, int endIndex) throws NumberFormatException {
        if (beginIndex < 0 || endIndex > value.length() || beginIndex > endIndex) {
            throw new NumberFormatException(value);
        }
        int i = beginIndex;
        int result = 0;
        if (i < endIndex) {
            int i2 = i + 1;
            int digit = Character.digit(value.charAt(i), 10);
            if (digit < 0) {
                throw new NumberFormatException("Invalid number: " + value.substring(beginIndex, endIndex));
            }
            result = -digit;
            i = i2;
        }
        while (i < endIndex) {
            int i3 = i + 1;
            int digit2 = Character.digit(value.charAt(i), 10);
            if (digit2 < 0) {
                throw new NumberFormatException("Invalid number: " + value.substring(beginIndex, endIndex));
            }
            result = (result * 10) - digit2;
            i = i3;
        }
        return -result;
    }

    private static int indexOfNonDigit(String string, int offset) {
        for (int i = offset; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c < '0' || c > '9') {
                return i;
            }
        }
        int i2 = string.length();
        return i2;
    }
}
