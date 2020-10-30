package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractStreamingHashFunction.AbstractStreamingHasher;
import java.io.Serializable;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

final class SipHashFunction extends AbstractStreamingHashFunction implements Serializable {
    private static final long serialVersionUID = 0;
    private final int a;
    private final int b;
    private final long c;
    private final long d;

    static final class SipHasher extends AbstractStreamingHasher {
        private final int a;
        private final int b;
        private long c = 8317987319222330741L;
        private long d = 7237128888997146477L;
        private long e = 7816392313619706465L;
        private long f = 8387220255154660723L;
        private long g = 0;
        private long h = 0;

        SipHasher(int i, int i2, long j, long j2) {
            super(8);
            this.a = i;
            this.b = i2;
            this.c ^= j;
            this.d ^= j2;
            this.e ^= j;
            this.f ^= j2;
        }

        /* access modifiers changed from: protected */
        public void process(ByteBuffer byteBuffer) {
            this.g += 8;
            a(byteBuffer.getLong());
        }

        /* access modifiers changed from: protected */
        public void processRemaining(ByteBuffer byteBuffer) {
            this.g += (long) byteBuffer.remaining();
            int i = 0;
            while (byteBuffer.hasRemaining()) {
                this.h ^= (((long) byteBuffer.get()) & 255) << i;
                i += 8;
            }
        }

        public HashCode a() {
            this.h ^= this.g << 56;
            a(this.h);
            this.e ^= 255;
            a(this.b);
            return HashCode.fromLong(((this.c ^ this.d) ^ this.e) ^ this.f);
        }

        private void a(long j) {
            this.f ^= j;
            a(this.a);
            this.c ^= j;
        }

        private void a(int i) {
            for (int i2 = 0; i2 < i; i2++) {
                this.c += this.d;
                this.e += this.f;
                this.d = Long.rotateLeft(this.d, 13);
                this.f = Long.rotateLeft(this.f, 16);
                this.d ^= this.c;
                this.f ^= this.e;
                this.c = Long.rotateLeft(this.c, 32);
                this.e += this.d;
                this.c += this.f;
                this.d = Long.rotateLeft(this.d, 17);
                this.f = Long.rotateLeft(this.f, 21);
                this.d ^= this.e;
                this.f ^= this.c;
                this.e = Long.rotateLeft(this.e, 32);
            }
        }
    }

    public int bits() {
        return 64;
    }

    SipHashFunction(int i, int i2, long j, long j2) {
        boolean z = false;
        Preconditions.checkArgument(i > 0, "The number of SipRound iterations (c=%s) during Compression must be positive.", i);
        if (i2 > 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "The number of SipRound iterations (d=%s) during Finalization must be positive.", i2);
        this.a = i;
        this.b = i2;
        this.c = j;
        this.d = j2;
    }

    public Hasher newHasher() {
        SipHasher sipHasher = new SipHasher(this.a, this.b, this.c, this.d);
        return sipHasher;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hashing.sipHash");
        sb.append(this.a);
        sb.append("");
        sb.append(this.b);
        sb.append("(");
        sb.append(this.c);
        sb.append(", ");
        sb.append(this.d);
        sb.append(")");
        return sb.toString();
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof SipHashFunction)) {
            return false;
        }
        SipHashFunction sipHashFunction = (SipHashFunction) obj;
        if (this.a == sipHashFunction.a && this.b == sipHashFunction.b && this.c == sipHashFunction.c && this.d == sipHashFunction.d) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return (int) ((((long) ((getClass().hashCode() ^ this.a) ^ this.b)) ^ this.c) ^ this.d);
    }
}
