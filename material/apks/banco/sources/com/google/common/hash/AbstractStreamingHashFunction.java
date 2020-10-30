package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

abstract class AbstractStreamingHashFunction implements HashFunction {

    @CanIgnoreReturnValue
    public static abstract class AbstractStreamingHasher extends AbstractHasher {
        private final ByteBuffer a;
        private final int b;
        private final int c;

        /* access modifiers changed from: 0000 */
        public abstract HashCode a();

        /* access modifiers changed from: protected */
        public abstract void process(ByteBuffer byteBuffer);

        protected AbstractStreamingHasher(int i) {
            this(i, i);
        }

        protected AbstractStreamingHasher(int i, int i2) {
            Preconditions.checkArgument(i2 % i == 0);
            this.a = ByteBuffer.allocate(i2 + 7).order(ByteOrder.LITTLE_ENDIAN);
            this.b = i2;
            this.c = i;
        }

        /* access modifiers changed from: protected */
        public void processRemaining(ByteBuffer byteBuffer) {
            byteBuffer.position(byteBuffer.limit());
            byteBuffer.limit(this.c + 7);
            while (byteBuffer.position() < this.c) {
                byteBuffer.putLong(0);
            }
            byteBuffer.limit(this.c);
            byteBuffer.flip();
            process(byteBuffer);
        }

        public final Hasher putBytes(byte[] bArr) {
            return putBytes(bArr, 0, bArr.length);
        }

        public final Hasher putBytes(byte[] bArr, int i, int i2) {
            return a(ByteBuffer.wrap(bArr, i, i2).order(ByteOrder.LITTLE_ENDIAN));
        }

        private Hasher a(ByteBuffer byteBuffer) {
            if (byteBuffer.remaining() <= this.a.remaining()) {
                this.a.put(byteBuffer);
                b();
                return this;
            }
            int position = this.b - this.a.position();
            for (int i = 0; i < position; i++) {
                this.a.put(byteBuffer.get());
            }
            c();
            while (byteBuffer.remaining() >= this.c) {
                process(byteBuffer);
            }
            this.a.put(byteBuffer);
            return this;
        }

        public final Hasher putUnencodedChars(CharSequence charSequence) {
            for (int i = 0; i < charSequence.length(); i++) {
                putChar(charSequence.charAt(i));
            }
            return this;
        }

        public final Hasher putByte(byte b2) {
            this.a.put(b2);
            b();
            return this;
        }

        public final Hasher putShort(short s) {
            this.a.putShort(s);
            b();
            return this;
        }

        public final Hasher putChar(char c2) {
            this.a.putChar(c2);
            b();
            return this;
        }

        public final Hasher putInt(int i) {
            this.a.putInt(i);
            b();
            return this;
        }

        public final Hasher putLong(long j) {
            this.a.putLong(j);
            b();
            return this;
        }

        public final <T> Hasher putObject(T t, Funnel<? super T> funnel) {
            funnel.funnel(t, this);
            return this;
        }

        public final HashCode hash() {
            c();
            this.a.flip();
            if (this.a.remaining() > 0) {
                processRemaining(this.a);
            }
            return a();
        }

        private void b() {
            if (this.a.remaining() < 8) {
                c();
            }
        }

        private void c() {
            this.a.flip();
            while (this.a.remaining() >= this.c) {
                process(this.a);
            }
            this.a.compact();
        }
    }

    AbstractStreamingHashFunction() {
    }

    public <T> HashCode hashObject(T t, Funnel<? super T> funnel) {
        return newHasher().putObject(t, funnel).hash();
    }

    public HashCode hashUnencodedChars(CharSequence charSequence) {
        return newHasher().putUnencodedChars(charSequence).hash();
    }

    public HashCode hashString(CharSequence charSequence, Charset charset) {
        return newHasher().putString(charSequence, charset).hash();
    }

    public HashCode hashInt(int i) {
        return newHasher().putInt(i).hash();
    }

    public HashCode hashLong(long j) {
        return newHasher().putLong(j).hash();
    }

    public HashCode hashBytes(byte[] bArr) {
        return newHasher().putBytes(bArr).hash();
    }

    public HashCode hashBytes(byte[] bArr, int i, int i2) {
        return newHasher().putBytes(bArr, i, i2).hash();
    }

    public Hasher newHasher(int i) {
        Preconditions.checkArgument(i >= 0);
        return newHasher();
    }
}
