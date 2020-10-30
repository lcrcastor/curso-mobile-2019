package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

abstract class AbstractNonStreamingHashFunction implements HashFunction {

    final class BufferingHasher extends AbstractHasher {
        final ExposedByteArrayOutputStream a;

        BufferingHasher(int i) {
            this.a = new ExposedByteArrayOutputStream(i);
        }

        public Hasher putByte(byte b2) {
            this.a.write(b2);
            return this;
        }

        public Hasher putBytes(byte[] bArr) {
            try {
                this.a.write(bArr);
                return this;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public Hasher putBytes(byte[] bArr, int i, int i2) {
            this.a.write(bArr, i, i2);
            return this;
        }

        public Hasher putShort(short s) {
            this.a.write(s & 255);
            this.a.write((s >>> 8) & 255);
            return this;
        }

        public Hasher putInt(int i) {
            this.a.write(i & 255);
            this.a.write((i >>> 8) & 255);
            this.a.write((i >>> 16) & 255);
            this.a.write((i >>> 24) & 255);
            return this;
        }

        public Hasher putLong(long j) {
            for (int i = 0; i < 64; i += 8) {
                this.a.write((byte) ((int) ((j >>> i) & 255)));
            }
            return this;
        }

        public Hasher putChar(char c) {
            this.a.write(c & 255);
            this.a.write((c >>> 8) & 255);
            return this;
        }

        public <T> Hasher putObject(T t, Funnel<? super T> funnel) {
            funnel.funnel(t, this);
            return this;
        }

        public HashCode hash() {
            return AbstractNonStreamingHashFunction.this.hashBytes(this.a.a(), 0, this.a.b());
        }
    }

    static final class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
        ExposedByteArrayOutputStream(int i) {
            super(i);
        }

        /* access modifiers changed from: 0000 */
        public byte[] a() {
            return this.buf;
        }

        /* access modifiers changed from: 0000 */
        public int b() {
            return this.count;
        }
    }

    AbstractNonStreamingHashFunction() {
    }

    public Hasher newHasher() {
        return new BufferingHasher(32);
    }

    public Hasher newHasher(int i) {
        Preconditions.checkArgument(i >= 0);
        return new BufferingHasher(i);
    }

    public <T> HashCode hashObject(T t, Funnel<? super T> funnel) {
        return newHasher().putObject(t, funnel).hash();
    }

    public HashCode hashUnencodedChars(CharSequence charSequence) {
        int length = charSequence.length();
        Hasher newHasher = newHasher(length * 2);
        for (int i = 0; i < length; i++) {
            newHasher.putChar(charSequence.charAt(i));
        }
        return newHasher.hash();
    }

    public HashCode hashString(CharSequence charSequence, Charset charset) {
        return hashBytes(charSequence.toString().getBytes(charset));
    }

    public HashCode hashInt(int i) {
        return newHasher(4).putInt(i).hash();
    }

    public HashCode hashLong(long j) {
        return newHasher(8).putLong(j).hash();
    }

    public HashCode hashBytes(byte[] bArr) {
        return hashBytes(bArr, 0, bArr.length);
    }
}
