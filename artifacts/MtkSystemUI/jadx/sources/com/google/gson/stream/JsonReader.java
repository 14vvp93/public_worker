package com.google.gson.stream;

import com.android.systemui.plugins.R;
import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.bind.JsonTreeReader;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class JsonReader implements Closeable {
    private final Reader in;
    private int[] pathIndices;
    private String[] pathNames;
    private long peekedLong;
    private int peekedNumberLength;
    private String peekedString;
    private int stackSize;
    private boolean lenient = false;
    private final char[] buffer = new char[1024];
    private int pos = 0;
    private int limit = 0;
    private int lineNumber = 0;
    private int lineStart = 0;
    int peeked = 0;
    private int[] stack = new int[32];

    public JsonReader(Reader in) {
        this.stackSize = 0;
        int[] iArr = this.stack;
        int i = this.stackSize;
        this.stackSize = i + 1;
        iArr[i] = 6;
        this.pathNames = new String[32];
        this.pathIndices = new int[32];
        this.in = (Reader) Objects.requireNonNull(in, "in == null");
    }

    public final void setLenient(boolean lenient) {
        this.lenient = lenient;
    }

    public final boolean isLenient() {
        return this.lenient;
    }

    public void beginArray() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 3) {
            push(1);
            this.pathIndices[this.stackSize - 1] = 0;
            this.peeked = 0;
        } else {
            throw new IllegalStateException("Expected BEGIN_ARRAY but was " + peek() + locationString());
        }
    }

    public void endArray() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 4) {
            this.stackSize--;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected END_ARRAY but was " + peek() + locationString());
    }

    public void beginObject() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 1) {
            push(3);
            this.peeked = 0;
        } else {
            throw new IllegalStateException("Expected BEGIN_OBJECT but was " + peek() + locationString());
        }
    }

    public void endObject() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 2) {
            this.stackSize--;
            this.pathNames[this.stackSize] = null;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected END_OBJECT but was " + peek() + locationString());
    }

    public boolean hasNext() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        return (p == 2 || p == 4 || p == 17) ? false : true;
    }

    public JsonToken peek() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        switch (p) {
            case 1:
                return JsonToken.BEGIN_OBJECT;
            case 2:
                return JsonToken.END_OBJECT;
            case 3:
                return JsonToken.BEGIN_ARRAY;
            case 4:
                return JsonToken.END_ARRAY;
            case 5:
            case 6:
                return JsonToken.BOOLEAN;
            case 7:
                return JsonToken.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return JsonToken.STRING;
            case 12:
            case 13:
            case 14:
                return JsonToken.NAME;
            case 15:
            case 16:
                return JsonToken.NUMBER;
            case 17:
                return JsonToken.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    int doPeek() throws IOException {
        int c;
        int peekStack = this.stack[this.stackSize - 1];
        if (peekStack == 1) {
            this.stack[this.stackSize - 1] = 2;
        } else if (peekStack == 2) {
            int c2 = nextNonWhitespace(true);
            if (c2 != 44) {
                if (c2 != 59) {
                    if (c2 == 93) {
                        this.peeked = 4;
                        return 4;
                    }
                    throw syntaxError("Unterminated array");
                }
                checkLenient();
            }
        } else {
            if (peekStack == 3 || peekStack == 5) {
                this.stack[this.stackSize - 1] = 4;
                if (peekStack == 5 && (c = nextNonWhitespace(true)) != 44) {
                    if (c != 59) {
                        if (c == 125) {
                            this.peeked = 2;
                            return 2;
                        }
                        throw syntaxError("Unterminated object");
                    }
                    checkLenient();
                }
                int c3 = nextNonWhitespace(true);
                if (c3 != 34) {
                    if (c3 != 39) {
                        if (c3 == 125) {
                            if (peekStack != 5) {
                                this.peeked = 2;
                                return 2;
                            }
                            throw syntaxError("Expected name");
                        }
                        checkLenient();
                        this.pos--;
                        if (isLiteral((char) c3)) {
                            this.peeked = 14;
                            return 14;
                        }
                        throw syntaxError("Expected name");
                    }
                    checkLenient();
                    this.peeked = 12;
                    return 12;
                }
                this.peeked = 13;
                return 13;
            }
            if (peekStack == 4) {
                this.stack[this.stackSize - 1] = 5;
                int c4 = nextNonWhitespace(true);
                if (c4 != 58) {
                    if (c4 == 61) {
                        checkLenient();
                        if ((this.pos < this.limit || fillBuffer(1)) && this.buffer[this.pos] == '>') {
                            this.pos++;
                        }
                    } else {
                        throw syntaxError("Expected ':'");
                    }
                }
            } else if (peekStack == 6) {
                if (this.lenient) {
                    consumeNonExecutePrefix();
                }
                this.stack[this.stackSize - 1] = 7;
            } else if (peekStack == 7) {
                if (nextNonWhitespace(false) == -1) {
                    this.peeked = 17;
                    return 17;
                }
                checkLenient();
                this.pos--;
            } else if (peekStack == 8) {
                throw new IllegalStateException("JsonReader is closed");
            }
        }
        int c5 = nextNonWhitespace(true);
        if (c5 != 34) {
            if (c5 != 39) {
                if (c5 != 44 && c5 != 59) {
                    if (c5 == 91) {
                        this.peeked = 3;
                        return 3;
                    }
                    if (c5 != 93) {
                        if (c5 == 123) {
                            this.peeked = 1;
                            return 1;
                        }
                        this.pos--;
                        int result = peekKeyword();
                        if (result != 0) {
                            return result;
                        }
                        int result2 = peekNumber();
                        if (result2 != 0) {
                            return result2;
                        }
                        if (!isLiteral(this.buffer[this.pos])) {
                            throw syntaxError("Expected value");
                        }
                        checkLenient();
                        this.peeked = 10;
                        return 10;
                    }
                    if (peekStack == 1) {
                        this.peeked = 4;
                        return 4;
                    }
                }
                if (peekStack == 1 || peekStack == 2) {
                    checkLenient();
                    this.pos--;
                    this.peeked = 7;
                    return 7;
                }
                throw syntaxError("Unexpected value");
            }
            checkLenient();
            this.peeked = 8;
            return 8;
        }
        this.peeked = 9;
        return 9;
    }

    private int peekKeyword() throws IOException {
        String keyword;
        String keywordUpper;
        int peeking;
        char c = this.buffer[this.pos];
        if (c == 't' || c == 'T') {
            keyword = "true";
            keywordUpper = "TRUE";
            peeking = 5;
        } else if (c == 'f' || c == 'F') {
            keyword = "false";
            keywordUpper = "FALSE";
            peeking = 6;
        } else {
            if (c != 'n' && c != 'N') {
                return 0;
            }
            keyword = "null";
            keywordUpper = "NULL";
            peeking = 7;
        }
        int length = keyword.length();
        for (int i = 1; i < length; i++) {
            if (this.pos + i >= this.limit && !fillBuffer(i + 1)) {
                return 0;
            }
            char c2 = this.buffer[this.pos + i];
            if (c2 != keyword.charAt(i) && c2 != keywordUpper.charAt(i)) {
                return 0;
            }
        }
        int i2 = this.pos;
        if ((i2 + length < this.limit || fillBuffer(length + 1)) && isLiteral(this.buffer[this.pos + length])) {
            return 0;
        }
        this.pos += length;
        this.peeked = peeking;
        return peeking;
    }

    /* JADX WARN: Code duplicated, block: B:100:0x011b  */
    /* JADX WARN: Code duplicated, block: B:113:0x0127 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:14:0x0039  */
    /* JADX WARN: Code duplicated, block: B:91:0x0107  */
    /* JADX WARN: Code duplicated, block: B:93:0x010e  */
    /* JADX WARN: Code duplicated, block: B:98:0x0115  */
    private int peekNumber() throws IOException {
        char c;
        int last;
        int last2;
        char[] buffer = this.buffer;
        int p = this.pos;
        int l = this.limit;
        boolean negative = false;
        boolean fitsInLong = true;
        int last3 = 0;
        int i = 0;
        long value = 0;
        int l2 = l;
        int p2 = p;
        int p3 = 0;
        while (true) {
            if (p2 + p3 != l2) {
                c = buffer[p2 + p3];
                if (c == '+') {
                    p2 = p2;
                    l2 = l2;
                    if (last3 == 5) {
                        return 0;
                    }
                    last = 6;
                } else if (c == 'E' && c != 'e') {
                    switch (c) {
                        case '-':
                            p2 = p2;
                            l2 = l2;
                            if (last3 == 0) {
                                last2 = 1;
                                negative = true;
                                last3 = last2;
                                p3++;
                                p2 = p2;
                                l2 = l2;
                                i = 0;
                            } else {
                                if (last3 != 5) {
                                    return 0;
                                }
                                last = 6;
                            }
                            break;
                        case '.':
                            p2 = p2;
                            l2 = l2;
                            if (last3 != 2) {
                                return 0;
                            }
                            last2 = 3;
                            last3 = last2;
                            p3++;
                            p2 = p2;
                            l2 = l2;
                            i = 0;
                            break;
                            break;
                        default:
                            if (c >= '0' && c <= '9') {
                                boolean z = true;
                                if (last3 == 1 || last3 == 0) {
                                    int p4 = c - '0';
                                    long value2 = -p4;
                                    last3 = 2;
                                    value = value2;
                                } else if (last3 != 2) {
                                    p2 = p2;
                                    l2 = l2;
                                    if (last3 == 3) {
                                        last = 4;
                                        break;
                                    } else if (last3 == 5 || last3 == 6) {
                                        last = 7;
                                        break;
                                    }
                                } else {
                                    if (value == 0) {
                                        return 0;
                                    }
                                    p2 = p2;
                                    l2 = l2;
                                    long newValue = (10 * value) - ((long) (c - '0'));
                                    if (value <= -922337203685477580L && (value != -922337203685477580L || newValue >= value)) {
                                        z = false;
                                    }
                                    fitsInLong &= z;
                                    value = newValue;
                                }
                                p3++;
                                p2 = p2;
                                l2 = l2;
                                i = 0;
                            }
                            if (isLiteral(c)) {
                                return 0;
                            }
                            break;
                    }
                } else {
                    p2 = p2;
                    l2 = l2;
                    if (last3 == 2 && last3 != 4) {
                        return 0;
                    }
                    last = 5;
                }
                last3 = last;
                p3++;
                p2 = p2;
                l2 = l2;
                i = 0;
            } else {
                if (p3 == buffer.length) {
                    return i;
                }
                if (fillBuffer(p3 + 1)) {
                    p2 = this.pos;
                    l2 = this.limit;
                    c = buffer[p2 + p3];
                    if (c == '+') {
                        if (c == 'E') {
                            p2 = p2;
                            l2 = l2;
                            if (last3 == 2) {
                            }
                            last = 5;
                        } else {
                            p2 = p2;
                            l2 = l2;
                            if (last3 == 2) {
                            }
                            last = 5;
                        }
                        p3++;
                        p2 = p2;
                        l2 = l2;
                        i = 0;
                    } else {
                        p2 = p2;
                        l2 = l2;
                        if (last3 == 5) {
                            return 0;
                        }
                        last = 6;
                    }
                    last3 = last;
                    p3++;
                    p2 = p2;
                    l2 = l2;
                    i = 0;
                }
            }
        }
        if (last3 == 2 && fitsInLong && ((value != Long.MIN_VALUE || negative) && (value != 0 || !negative))) {
            this.peekedLong = negative ? value : -value;
            this.pos += p3;
            this.peeked = 15;
            return 15;
        }
        if (last3 != 2 && last3 != 4 && last3 != 7) {
            return 0;
        }
        this.peekedNumberLength = p3;
        this.peeked = 16;
        return 16;
    }

    private boolean isLiteral(char c) throws IOException {
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
            case ',':
            case ':':
            case R.styleable.AppCompatTheme_selectableItemBackground /* 91 */:
            case R.styleable.AppCompatTheme_spinnerDropDownItemStyle /* 93 */:
            case '{':
            case '}':
                return false;
            case '#':
            case '/':
            case ';':
            case '=':
            case R.styleable.AppCompatTheme_selectableItemBackgroundBorderless /* 92 */:
                checkLenient();
                return false;
            default:
                return true;
        }
    }

    public String nextName() throws IOException {
        String result;
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 14) {
            result = nextUnquotedValue();
        } else if (p == 12) {
            result = nextQuotedValue('\'');
        } else if (p == 13) {
            result = nextQuotedValue('\"');
        } else {
            throw new IllegalStateException("Expected a name but was " + peek() + locationString());
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = result;
        return result;
    }

    public String nextString() throws IOException {
        String result;
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 10) {
            result = nextUnquotedValue();
        } else if (p == 8) {
            result = nextQuotedValue('\'');
        } else if (p == 9) {
            result = nextQuotedValue('\"');
        } else if (p == 11) {
            result = this.peekedString;
            this.peekedString = null;
        } else if (p == 15) {
            result = Long.toString(this.peekedLong);
        } else if (p == 16) {
            result = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            throw new IllegalStateException("Expected a string but was " + peek() + locationString());
        }
        this.peeked = 0;
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return result;
    }

    public boolean nextBoolean() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 5) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return true;
        }
        if (p == 6) {
            this.peeked = 0;
            int[] iArr2 = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr2[i2] = iArr2[i2] + 1;
            return false;
        }
        throw new IllegalStateException("Expected a boolean but was " + peek() + locationString());
    }

    public void nextNull() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 7) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return;
        }
        throw new IllegalStateException("Expected null but was " + peek() + locationString());
    }

    public double nextDouble() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 15) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return this.peekedLong;
        }
        if (p == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else if (p == 8 || p == 9) {
            this.peekedString = nextQuotedValue(p == 8 ? '\'' : '\"');
        } else if (p == 10) {
            this.peekedString = nextUnquotedValue();
        } else if (p != 11) {
            throw new IllegalStateException("Expected a double but was " + peek() + locationString());
        }
        this.peeked = 11;
        double result = Double.parseDouble(this.peekedString);
        if (!this.lenient && (Double.isNaN(result) || Double.isInfinite(result))) {
            throw new MalformedJsonException("JSON forbids NaN and infinities: " + result + locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] iArr2 = this.pathIndices;
        int i2 = this.stackSize - 1;
        iArr2[i2] = iArr2[i2] + 1;
        return result;
    }

    public long nextLong() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 15) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return this.peekedLong;
        }
        if (p == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else if (p == 8 || p == 9 || p == 10) {
            if (p == 10) {
                this.peekedString = nextUnquotedValue();
            } else {
                this.peekedString = nextQuotedValue(p == 8 ? '\'' : '\"');
            }
            try {
                long result = Long.parseLong(this.peekedString);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i2 = this.stackSize - 1;
                iArr2[i2] = iArr2[i2] + 1;
                return result;
            } catch (NumberFormatException e) {
            }
        } else {
            throw new IllegalStateException("Expected a long but was " + peek() + locationString());
        }
        this.peeked = 11;
        double asDouble = Double.parseDouble(this.peekedString);
        long result2 = (long) asDouble;
        if (result2 != asDouble) {
            throw new NumberFormatException("Expected a long but was " + this.peekedString + locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] iArr3 = this.pathIndices;
        int i3 = this.stackSize - 1;
        iArr3[i3] = iArr3[i3] + 1;
        return result2;
    }

    private String nextQuotedValue(char quote) throws IOException {
        char[] buffer = this.buffer;
        StringBuilder builder = null;
        while (true) {
            int p = this.pos;
            int l = this.limit;
            StringBuilder builder2 = builder;
            int c = p;
            while (c < l) {
                int p2 = c + 1;
                char c2 = buffer[c];
                if (c2 == quote) {
                    this.pos = p2;
                    int len = (p2 - p) - 1;
                    if (builder2 == null) {
                        return new String(buffer, p, len);
                    }
                    builder2.append(buffer, p, len);
                    return builder2.toString();
                }
                if (c2 == '\\') {
                    this.pos = p2;
                    int len2 = (p2 - p) - 1;
                    if (builder2 == null) {
                        int estimatedLength = (len2 + 1) * 2;
                        builder2 = new StringBuilder(Math.max(estimatedLength, 16));
                    }
                    builder2.append(buffer, p, len2);
                    builder2.append(readEscapeCharacter());
                    int p3 = this.pos;
                    l = this.limit;
                    p = p3;
                    c = p3;
                } else {
                    if (c2 == '\n') {
                        this.lineNumber++;
                        this.lineStart = p2;
                    }
                    c = p2;
                }
            }
            if (builder2 == null) {
                int estimatedLength2 = (c - p) * 2;
                builder2 = new StringBuilder(Math.max(estimatedLength2, 16));
            }
            builder2.append(buffer, p, c - p);
            this.pos = c;
            if (fillBuffer(1)) {
                builder = builder2;
            } else {
                throw syntaxError("Unterminated string");
            }
        }
    }

    private String nextUnquotedValue() throws IOException {
        String result;
        StringBuilder builder = null;
        int i = 0;
        while (true) {
            if (this.pos + i < this.limit) {
                switch (this.buffer[this.pos + i]) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case R.styleable.AppCompatTheme_selectableItemBackground /* 91 */:
                    case R.styleable.AppCompatTheme_spinnerDropDownItemStyle /* 93 */:
                    case '{':
                    case '}':
                        break;
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case R.styleable.AppCompatTheme_selectableItemBackgroundBorderless /* 92 */:
                        checkLenient();
                        break;
                    default:
                        i++;
                        continue;
                }
            } else if (i < this.buffer.length) {
                if (fillBuffer(i + 1)) {
                }
            } else {
                if (builder == null) {
                    builder = new StringBuilder(Math.max(i, 16));
                }
                builder.append(this.buffer, this.pos, i);
                this.pos += i;
                i = 0;
                if (!fillBuffer(1)) {
                }
            }
        }
        if (builder == null) {
            result = new String(this.buffer, this.pos, i);
        } else {
            builder.append(this.buffer, this.pos, i);
            result = builder.toString();
        }
        this.pos += i;
        return result;
    }

    private void skipQuotedValue(char quote) throws IOException {
        char[] buffer = this.buffer;
        do {
            int c = this.pos;
            int l = this.limit;
            while (c < l) {
                int p = c + 1;
                char c2 = buffer[c];
                if (c2 == quote) {
                    this.pos = p;
                    return;
                }
                if (c2 == '\\') {
                    this.pos = p;
                    readEscapeCharacter();
                    int p2 = this.pos;
                    l = this.limit;
                    c = p2;
                } else {
                    if (c2 == '\n') {
                        this.lineNumber++;
                        this.lineStart = p;
                    }
                    c = p;
                }
            }
            this.pos = c;
        } while (fillBuffer(1));
        throw syntaxError("Unterminated string");
    }

    private void skipUnquotedValue() throws IOException {
        do {
            int i = 0;
            while (this.pos + i < this.limit) {
                switch (this.buffer[this.pos + i]) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case R.styleable.AppCompatTheme_selectableItemBackground /* 91 */:
                    case R.styleable.AppCompatTheme_spinnerDropDownItemStyle /* 93 */:
                    case '{':
                    case '}':
                        break;
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case R.styleable.AppCompatTheme_selectableItemBackgroundBorderless /* 92 */:
                        checkLenient();
                        break;
                    default:
                        i++;
                        break;
                }
                this.pos += i;
                return;
            }
            this.pos += i;
        } while (fillBuffer(1));
    }

    public int nextInt() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 15) {
            int result = (int) this.peekedLong;
            if (this.peekedLong != result) {
                throw new NumberFormatException("Expected an int but was " + this.peekedLong + locationString());
            }
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return result;
        }
        if (p == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else if (p == 8 || p == 9 || p == 10) {
            if (p == 10) {
                this.peekedString = nextUnquotedValue();
            } else {
                this.peekedString = nextQuotedValue(p == 8 ? '\'' : '\"');
            }
            try {
                int result2 = Integer.parseInt(this.peekedString);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i2 = this.stackSize - 1;
                iArr2[i2] = iArr2[i2] + 1;
                return result2;
            } catch (NumberFormatException e) {
            }
        } else {
            throw new IllegalStateException("Expected an int but was " + peek() + locationString());
        }
        this.peeked = 11;
        double asDouble = Double.parseDouble(this.peekedString);
        int result3 = (int) asDouble;
        if (result3 != asDouble) {
            throw new NumberFormatException("Expected an int but was " + this.peekedString + locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] iArr3 = this.pathIndices;
        int i3 = this.stackSize - 1;
        iArr3[i3] = iArr3[i3] + 1;
        return result3;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.peeked = 0;
        this.stack[0] = 8;
        this.stackSize = 1;
        this.in.close();
    }

    public void skipValue() throws IOException {
        int count = 0;
        do {
            int p = this.peeked;
            if (p == 0) {
                p = doPeek();
            }
            switch (p) {
                case 1:
                    push(3);
                    count++;
                    break;
                case 2:
                    if (count == 0) {
                        this.pathNames[this.stackSize - 1] = null;
                    }
                    this.stackSize--;
                    count--;
                    break;
                case 3:
                    push(1);
                    count++;
                    break;
                case 4:
                    this.stackSize--;
                    count--;
                    break;
                case 8:
                    skipQuotedValue('\'');
                    break;
                case 9:
                    skipQuotedValue('\"');
                    break;
                case 10:
                    skipUnquotedValue();
                    break;
                case 12:
                    skipQuotedValue('\'');
                    if (count == 0) {
                        this.pathNames[this.stackSize - 1] = "<skipped>";
                    }
                    break;
                case 13:
                    skipQuotedValue('\"');
                    if (count == 0) {
                        this.pathNames[this.stackSize - 1] = "<skipped>";
                    }
                    break;
                case 14:
                    skipUnquotedValue();
                    if (count == 0) {
                        this.pathNames[this.stackSize - 1] = "<skipped>";
                    }
                    break;
                case 16:
                    this.pos += this.peekedNumberLength;
                    break;
                case 17:
                    return;
            }
            this.peeked = 0;
        } while (count > 0);
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
    }

    private void push(int newTop) {
        if (this.stackSize == this.stack.length) {
            int newLength = this.stackSize * 2;
            this.stack = Arrays.copyOf(this.stack, newLength);
            this.pathIndices = Arrays.copyOf(this.pathIndices, newLength);
            this.pathNames = (String[]) Arrays.copyOf(this.pathNames, newLength);
        }
        int[] iArr = this.stack;
        int i = this.stackSize;
        this.stackSize = i + 1;
        iArr[i] = newTop;
    }

    private boolean fillBuffer(int minimum) throws IOException {
        char[] buffer = this.buffer;
        this.lineStart -= this.pos;
        if (this.limit != this.pos) {
            this.limit -= this.pos;
            System.arraycopy(buffer, this.pos, buffer, 0, this.limit);
        } else {
            this.limit = 0;
        }
        this.pos = 0;
        do {
            int total = this.in.read(buffer, this.limit, buffer.length - this.limit);
            if (total == -1) {
                return false;
            }
            this.limit += total;
            if (this.lineNumber == 0 && this.lineStart == 0 && this.limit > 0 && buffer[0] == 65279) {
                this.pos++;
                this.lineStart++;
                minimum++;
            }
        } while (this.limit < minimum);
        return true;
    }

    private int nextNonWhitespace(boolean throwOnEof) throws IOException {
        int p;
        char[] buffer = this.buffer;
        int p2 = this.pos;
        int l = this.limit;
        while (true) {
            if (p2 == l) {
                this.pos = p2;
                if (fillBuffer(1)) {
                    p2 = this.pos;
                    l = this.limit;
                } else {
                    if (throwOnEof) {
                        throw new EOFException("End of input" + locationString());
                    }
                    return -1;
                }
            }
            int p3 = p2 + 1;
            char c = buffer[p2];
            if (c == '\n') {
                this.lineNumber++;
                this.lineStart = p3;
            } else if (c != ' ' && c != '\r' && c != '\t') {
                if (c == '/') {
                    this.pos = p3;
                    if (p3 == l) {
                        this.pos--;
                        boolean charsLoaded = fillBuffer(2);
                        this.pos++;
                        if (!charsLoaded) {
                            return c;
                        }
                    }
                    checkLenient();
                    char peek = buffer[this.pos];
                    if (peek == '*') {
                        this.pos++;
                        if (skipTo("*/")) {
                            p = this.pos + 2;
                            l = this.limit;
                        } else {
                            throw syntaxError("Unterminated comment");
                        }
                    } else if (peek == '/') {
                        this.pos++;
                        skipToEndOfLine();
                        p = this.pos;
                        l = this.limit;
                    } else {
                        return c;
                    }
                } else if (c == '#') {
                    this.pos = p3;
                    checkLenient();
                    skipToEndOfLine();
                    p = this.pos;
                    l = this.limit;
                } else {
                    this.pos = p3;
                    return c;
                }
                p2 = p;
            }
            p2 = p3;
        }
    }

    private void checkLenient() throws IOException {
        if (!this.lenient) {
            throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private void skipToEndOfLine() throws IOException {
        char c;
        do {
            if (this.pos < this.limit || fillBuffer(1)) {
                char[] cArr = this.buffer;
                int i = this.pos;
                this.pos = i + 1;
                c = cArr[i];
                if (c == '\n') {
                    this.lineNumber++;
                    this.lineStart = this.pos;
                    return;
                }
            } else {
                return;
            }
        } while (c != '\r');
    }

    private boolean skipTo(String toFind) throws IOException {
        int length = toFind.length();
        while (true) {
            int c = 0;
            if (this.pos + length > this.limit && !fillBuffer(length)) {
                return false;
            }
            if (this.buffer[this.pos] == '\n') {
                this.lineNumber++;
                this.lineStart = this.pos + 1;
            } else {
                while (true) {
                    int c2 = c;
                    if (c2 >= length) {
                        return true;
                    }
                    if (this.buffer[this.pos + c2] != toFind.charAt(c2)) {
                        break;
                    }
                    c = c2 + 1;
                }
            }
            int c3 = this.pos;
            this.pos = c3 + 1;
        }
    }

    public String toString() {
        return getClass().getSimpleName() + locationString();
    }

    String locationString() {
        int line = this.lineNumber + 1;
        int column = (this.pos - this.lineStart) + 1;
        return " at line " + line + " column " + column + " path " + getPath();
    }

    private String getPath(boolean usePreviousPath) {
        StringBuilder result = new StringBuilder().append('$');
        for (int i = 0; i < this.stackSize; i++) {
            switch (this.stack[i]) {
                case 1:
                case 2:
                    int pathIndex = this.pathIndices[i];
                    if (usePreviousPath && pathIndex > 0 && i == this.stackSize - 1) {
                        pathIndex--;
                    }
                    result.append('[');
                    result.append(pathIndex);
                    result.append(']');
                    break;
                case 3:
                case 4:
                case 5:
                    result.append('.');
                    if (this.pathNames[i] != null) {
                        result.append(this.pathNames[i]);
                    }
                    break;
            }
        }
        return result.toString();
    }

    public String getPreviousPath() {
        return getPath(true);
    }

    public String getPath() {
        return getPath(false);
    }

    private char readEscapeCharacter() throws IOException {
        int i;
        if (this.pos == this.limit && !fillBuffer(1)) {
            throw syntaxError("Unterminated escape sequence");
        }
        char[] cArr = this.buffer;
        int i2 = this.pos;
        this.pos = i2 + 1;
        char escaped = cArr[i2];
        if (escaped == '\n') {
            this.lineNumber++;
            this.lineStart = this.pos;
        } else if (escaped != '\"' && escaped != '\'' && escaped != '/' && escaped != '\\') {
            if (escaped == 'b') {
                return '\b';
            }
            if (escaped == 'f') {
                return '\f';
            }
            if (escaped == 'n') {
                return '\n';
            }
            if (escaped != 'r') {
                switch (escaped) {
                    case R.styleable.AppCompatTheme_windowFixedWidthMajor /* 116 */:
                        return '\t';
                    case R.styleable.AppCompatTheme_windowFixedWidthMinor /* 117 */:
                        if (this.pos + 4 > this.limit && !fillBuffer(4)) {
                            throw syntaxError("Unterminated escape sequence");
                        }
                        char result = 0;
                        int i3 = this.pos;
                        int end = i3 + 4;
                        while (i3 < end) {
                            char c = this.buffer[i3];
                            char result2 = (char) (result << 4);
                            if (c >= '0' && c <= '9') {
                                i = c - '0';
                            } else if (c >= 'a' && c <= 'f') {
                                i = (c - 'a') + 10;
                            } else if (c >= 'A' && c <= 'F') {
                                i = (c - 'A') + 10;
                            } else {
                                throw new NumberFormatException("\\u" + new String(this.buffer, this.pos, 4));
                            }
                            result = (char) (i + result2);
                            i3++;
                        }
                        this.pos += 4;
                        return result;
                    default:
                        throw syntaxError("Invalid escape sequence");
                }
            }
            return '\r';
        }
        return escaped;
    }

    private IOException syntaxError(String message) throws IOException {
        throw new MalformedJsonException(message + locationString());
    }

    private void consumeNonExecutePrefix() throws IOException {
        nextNonWhitespace(true);
        this.pos--;
        if (this.pos + 5 > this.limit && !fillBuffer(5)) {
            return;
        }
        int p = this.pos;
        char[] buf = this.buffer;
        if (buf[p] != ')' || buf[p + 1] != ']' || buf[p + 2] != '}' || buf[p + 3] != '\'' || buf[p + 4] != '\n') {
            return;
        }
        this.pos += 5;
    }

    static {
        JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess() { // from class: com.google.gson.stream.JsonReader.1
            @Override // com.google.gson.internal.JsonReaderInternalAccess
            public void promoteNameToValue(JsonReader reader) throws IOException {
                if (reader instanceof JsonTreeReader) {
                    ((JsonTreeReader) reader).promoteNameToValue();
                    return;
                }
                int p = reader.peeked;
                if (p == 0) {
                    p = reader.doPeek();
                }
                if (p == 13) {
                    reader.peeked = 9;
                    return;
                }
                if (p == 12) {
                    reader.peeked = 8;
                    return;
                }
                if (p == 14) {
                    reader.peeked = 10;
                    return;
                }
                throw new IllegalStateException("Expected a name but was " + reader.peek() + reader.locationString());
            }
        };
    }
}
