package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.io.FilterOutputStream;
import java.io.OutputStream;

@Beta
public final class HashingOutputStream extends FilterOutputStream {
    private final Hasher a;

    public HashingOutputStream(HashFunction hashFunction, OutputStream outputStream) {
        super((OutputStream) Preconditions.checkNotNull(outputStream));
        this.a = (Hasher) Preconditions.checkNotNull(hashFunction.newHasher());
    }

    public void write(int i) {
        this.a.putByte((byte) i);
        this.out.write(i);
    }

    public void write(byte[] bArr, int i, int i2) {
        this.a.putBytes(bArr, i, i2);
        this.out.write(bArr, i, i2);
    }

    public HashCode hash() {
        return this.a.hash();
    }

    public void close() {
        this.out.close();
    }
}
