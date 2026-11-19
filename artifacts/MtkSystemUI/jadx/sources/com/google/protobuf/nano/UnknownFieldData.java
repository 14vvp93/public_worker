package com.google.protobuf.nano;

import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
final class UnknownFieldData {
    final byte[] bytes;
    final int tag;

    int computeSerializedSize() {
        int size = 0 + CodedOutputByteBufferNano.computeRawVarint32Size(this.tag);
        return size + this.bytes.length;
    }

    void writeTo(CodedOutputByteBufferNano output) throws IOException {
        output.writeRawVarint32(this.tag);
        output.writeRawBytes(this.bytes);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UnknownFieldData)) {
            return false;
        }
        UnknownFieldData other = (UnknownFieldData) o;
        return this.tag == other.tag && Arrays.equals(this.bytes, other.bytes);
    }

    public int hashCode() {
        int result = (31 * 17) + this.tag;
        return (31 * result) + Arrays.hashCode(this.bytes);
    }
}
