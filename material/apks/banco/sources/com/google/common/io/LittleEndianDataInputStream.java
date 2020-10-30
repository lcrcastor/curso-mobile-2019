package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.InputStream;

@GwtIncompatible
@Beta
public final class LittleEndianDataInputStream extends FilterInputStream implements DataInput {
    public LittleEndianDataInputStream(InputStream inputStream) {
        super((InputStream) Preconditions.checkNotNull(inputStream));
    }

    @CanIgnoreReturnValue
    public String readLine() {
        throw new UnsupportedOperationException("readLine is not supported");
    }

    public void readFully(byte[] bArr) {
        ByteStreams.readFully(this, bArr);
    }

    public void readFully(byte[] bArr, int i, int i2) {
        ByteStreams.readFully(this, bArr, i, i2);
    }

    public int skipBytes(int i) {
        return (int) this.in.skip((long) i);
    }

    @CanIgnoreReturnValue
    public int readUnsignedByte() {
        int read = this.in.read();
        if (read >= 0) {
            return read;
        }
        throw new EOFException();
    }

    @CanIgnoreReturnValue
    public int readUnsignedShort() {
        return Ints.fromBytes(0, 0, a(), a());
    }

    @CanIgnoreReturnValue
    public int readInt() {
        byte a = a();
        byte a2 = a();
        return Ints.fromBytes(a(), a(), a2, a);
    }

    @CanIgnoreReturnValue
    public long readLong() {
        byte a = a();
        byte a2 = a();
        byte a3 = a();
        byte a4 = a();
        byte a5 = a();
        byte a6 = a();
        return Longs.fromBytes(a(), a(), a6, a5, a4, a3, a2, a);
    }

    @CanIgnoreReturnValue
    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }

    @CanIgnoreReturnValue
    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    @CanIgnoreReturnValue
    public String readUTF() {
        return new DataInputStream(this.in).readUTF();
    }

    @CanIgnoreReturnValue
    public short readShort() {
        return (short) readUnsignedShort();
    }

    @CanIgnoreReturnValue
    public char readChar() {
        return (char) readUnsignedShort();
    }

    @CanIgnoreReturnValue
    public byte readByte() {
        return (byte) readUnsignedByte();
    }

    @CanIgnoreReturnValue
    public boolean readBoolean() {
        return readUnsignedByte() != 0;
    }

    private byte a() {
        int read = this.in.read();
        if (-1 != read) {
            return (byte) read;
        }
        throw new EOFException();
    }
}
