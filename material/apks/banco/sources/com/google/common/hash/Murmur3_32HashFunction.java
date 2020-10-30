package com.google.common.hash;

import com.google.common.hash.AbstractStreamingHashFunction.AbstractStreamingHasher;
import com.google.common.primitives.UnsignedBytes;
import java.io.Serializable;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

final class Murmur3_32HashFunction extends AbstractStreamingHashFunction implements Serializable {
    private static final long serialVersionUID = 0;
    private final int a;

    static final class Murmur3_32Hasher extends AbstractStreamingHasher {
        private int a;
        private int b = 0;

        Murmur3_32Hasher(int i) {
            super(4);
            this.a = i;
        }

        /* access modifiers changed from: protected */
        public void process(ByteBuffer byteBuffer) {
            this.a = Murmur3_32HashFunction.c(this.a, Murmur3_32HashFunction.b(byteBuffer.getInt()));
            this.b += 4;
        }

        /* access modifiers changed from: protected */
        public void processRemaining(ByteBuffer byteBuffer) {
            this.b += byteBuffer.remaining();
            int i = 0;
            int i2 = 0;
            while (byteBuffer.hasRemaining()) {
                i ^= UnsignedBytes.toInt(byteBuffer.get()) << i2;
                i2 += 8;
            }
            this.a ^= Murmur3_32HashFunction.b(i);
        }

        public HashCode a() {
            return Murmur3_32HashFunction.d(this.a, this.b);
        }
    }

    public int bits() {
        return 32;
    }

    Murmur3_32HashFunction(int i) {
        this.a = i;
    }

    public Hasher newHasher() {
        return new Murmur3_32Hasher(this.a);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hashing.murmur3_32(");
        sb.append(this.a);
        sb.append(")");
        return sb.toString();
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof Murmur3_32HashFunction)) {
            return false;
        }
        if (this.a == ((Murmur3_32HashFunction) obj).a) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return getClass().hashCode() ^ this.a;
    }

    public HashCode hashInt(int i) {
        return d(c(this.a, b(i)), 4);
    }

    public HashCode hashLong(long j) {
        int i = (int) j;
        int i2 = (int) (j >>> 32);
        return d(c(c(this.a, b(i)), b(i2)), 8);
    }

    public HashCode hashUnencodedChars(CharSequence charSequence) {
        int i = this.a;
        for (int i2 = 1; i2 < charSequence.length(); i2 += 2) {
            i = c(i, b(charSequence.charAt(i2 - 1) | (charSequence.charAt(i2) << 16)));
        }
        if ((charSequence.length() & 1) == 1) {
            i ^= b(charSequence.charAt(charSequence.length() - 1));
        }
        return d(i, charSequence.length() * 2);
    }

    /* access modifiers changed from: private */
    public static int b(int i) {
        return Integer.rotateLeft(i * -862048943, 15) * 461845907;
    }

    /* access modifiers changed from: private */
    public static int c(int i, int i2) {
        return (Integer.rotateLeft(i ^ i2, 13) * 5) - 430675100;
    }

    /* access modifiers changed from: private */
    public static HashCode d(int i, int i2) {
        int i3 = i ^ i2;
        int i4 = (i3 ^ (i3 >>> 16)) * -2048144789;
        int i5 = (i4 ^ (i4 >>> 13)) * -1028477387;
        return HashCode.fromInt(i5 ^ (i5 >>> 16));
    }
}
