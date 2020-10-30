package com.google.common.hash;

import com.google.common.hash.AbstractStreamingHashFunction.AbstractStreamingHasher;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.annotation.Nullable;

final class Murmur3_128HashFunction extends AbstractStreamingHashFunction implements Serializable {
    private static final long serialVersionUID = 0;
    private final int a;

    static final class Murmur3_128Hasher extends AbstractStreamingHasher {
        private long a;
        private long b;
        private int c = 0;

        private static long a(long j) {
            long j2 = (j ^ (j >>> 33)) * -49064778989728563L;
            long j3 = (j2 ^ (j2 >>> 33)) * -4265267296055464877L;
            return j3 ^ (j3 >>> 33);
        }

        Murmur3_128Hasher(int i) {
            super(16);
            long j = (long) i;
            this.a = j;
            this.b = j;
        }

        /* access modifiers changed from: protected */
        public void process(ByteBuffer byteBuffer) {
            a(byteBuffer.getLong(), byteBuffer.getLong());
            this.c += 16;
        }

        private void a(long j, long j2) {
            this.a ^= b(j);
            this.a = Long.rotateLeft(this.a, 27);
            this.a += this.b;
            this.a = (this.a * 5) + 1390208809;
            this.b ^= c(j2);
            this.b = Long.rotateLeft(this.b, 31);
            this.b += this.a;
            this.b = (this.b * 5) + 944331445;
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0058, code lost:
            r4 = r11 ^ (((long) com.google.common.primitives.UnsignedBytes.toInt(r14.get(11))) << 24);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0069, code lost:
            r2 = r4 ^ (((long) com.google.common.primitives.UnsignedBytes.toInt(r14.get(10))) << 16);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x007a, code lost:
            r4 = r2 ^ (((long) com.google.common.primitives.UnsignedBytes.toInt(r14.get(9))) << 8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x008a, code lost:
            r2 = r4 ^ ((long) com.google.common.primitives.UnsignedBytes.toInt(r14.get(8)));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0097, code lost:
            r6 = r2;
            r2 = 0 ^ r14.getLong();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x00b0, code lost:
            r9 = r11 ^ (((long) com.google.common.primitives.UnsignedBytes.toInt(r14.get(5))) << 40);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00c0, code lost:
            r11 = r9 ^ (((long) com.google.common.primitives.UnsignedBytes.toInt(r14.get(4))) << 32);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x00d0, code lost:
            r4 = r11 ^ (((long) com.google.common.primitives.UnsignedBytes.toInt(r14.get(3))) << 24);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x00e0, code lost:
            r2 = r4 ^ (((long) com.google.common.primitives.UnsignedBytes.toInt(r14.get(2))) << 16);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x00f0, code lost:
            r4 = r2 ^ (((long) com.google.common.primitives.UnsignedBytes.toInt(r14.get(1))) << 8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ff, code lost:
            r2 = r4 ^ ((long) com.google.common.primitives.UnsignedBytes.toInt(r14.get(0)));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x010b, code lost:
            r13.a ^= b(r2);
            r13.b ^= c(r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x011f, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0036, code lost:
            r9 = r11 ^ (((long) com.google.common.primitives.UnsignedBytes.toInt(r14.get(13))) << 40);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0047, code lost:
            r11 = r9 ^ (((long) com.google.common.primitives.UnsignedBytes.toInt(r14.get(12))) << 32);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void processRemaining(java.nio.ByteBuffer r14) {
            /*
                r13 = this;
                int r0 = r13.c
                int r1 = r14.remaining()
                int r0 = r0 + r1
                r13.c = r0
                int r0 = r14.remaining()
                r1 = 16
                r2 = 24
                r3 = 32
                r4 = 40
                r5 = 48
                r6 = 0
                r8 = 8
                switch(r0) {
                    case 1: goto L_0x00fe;
                    case 2: goto L_0x00ef;
                    case 3: goto L_0x00df;
                    case 4: goto L_0x00cf;
                    case 5: goto L_0x00bf;
                    case 6: goto L_0x00af;
                    case 7: goto L_0x00a1;
                    case 8: goto L_0x0096;
                    case 9: goto L_0x0089;
                    case 10: goto L_0x0079;
                    case 11: goto L_0x0068;
                    case 12: goto L_0x0057;
                    case 13: goto L_0x0046;
                    case 14: goto L_0x0035;
                    case 15: goto L_0x0026;
                    default: goto L_0x001e;
                }
            L_0x001e:
                java.lang.AssertionError r14 = new java.lang.AssertionError
                java.lang.String r0 = "Should never get here."
                r14.<init>(r0)
                throw r14
            L_0x0026:
                r0 = 14
                byte r0 = r14.get(r0)
                int r0 = com.google.common.primitives.UnsignedBytes.toInt(r0)
                long r9 = (long) r0
                long r9 = r9 << r5
                long r11 = r9 ^ r6
                goto L_0x0036
            L_0x0035:
                r11 = r6
            L_0x0036:
                r0 = 13
                byte r0 = r14.get(r0)
                int r0 = com.google.common.primitives.UnsignedBytes.toInt(r0)
                long r9 = (long) r0
                long r4 = r9 << r4
                long r9 = r11 ^ r4
                goto L_0x0047
            L_0x0046:
                r9 = r6
            L_0x0047:
                r0 = 12
                byte r0 = r14.get(r0)
                int r0 = com.google.common.primitives.UnsignedBytes.toInt(r0)
                long r4 = (long) r0
                long r3 = r4 << r3
                long r11 = r9 ^ r3
                goto L_0x0058
            L_0x0057:
                r11 = r6
            L_0x0058:
                r0 = 11
                byte r0 = r14.get(r0)
                int r0 = com.google.common.primitives.UnsignedBytes.toInt(r0)
                long r3 = (long) r0
                long r2 = r3 << r2
                long r4 = r11 ^ r2
                goto L_0x0069
            L_0x0068:
                r4 = r6
            L_0x0069:
                r0 = 10
                byte r0 = r14.get(r0)
                int r0 = com.google.common.primitives.UnsignedBytes.toInt(r0)
                long r2 = (long) r0
                long r0 = r2 << r1
                long r2 = r4 ^ r0
                goto L_0x007a
            L_0x0079:
                r2 = r6
            L_0x007a:
                r0 = 9
                byte r0 = r14.get(r0)
                int r0 = com.google.common.primitives.UnsignedBytes.toInt(r0)
                long r0 = (long) r0
                long r0 = r0 << r8
                long r4 = r2 ^ r0
                goto L_0x008a
            L_0x0089:
                r4 = r6
            L_0x008a:
                byte r0 = r14.get(r8)
                int r0 = com.google.common.primitives.UnsignedBytes.toInt(r0)
                long r0 = (long) r0
                long r2 = r4 ^ r0
                goto L_0x0097
            L_0x0096:
                r2 = r6
            L_0x0097:
                long r0 = r14.getLong()
                long r4 = r6 ^ r0
                r6 = r2
                r2 = r4
                goto L_0x010b
            L_0x00a1:
                r0 = 6
                byte r0 = r14.get(r0)
                int r0 = com.google.common.primitives.UnsignedBytes.toInt(r0)
                long r9 = (long) r0
                long r9 = r9 << r5
                long r11 = r9 ^ r6
                goto L_0x00b0
            L_0x00af:
                r11 = r6
            L_0x00b0:
                r0 = 5
                byte r0 = r14.get(r0)
                int r0 = com.google.common.primitives.UnsignedBytes.toInt(r0)
                long r9 = (long) r0
                long r4 = r9 << r4
                long r9 = r11 ^ r4
                goto L_0x00c0
            L_0x00bf:
                r9 = r6
            L_0x00c0:
                r0 = 4
                byte r0 = r14.get(r0)
                int r0 = com.google.common.primitives.UnsignedBytes.toInt(r0)
                long r4 = (long) r0
                long r3 = r4 << r3
                long r11 = r9 ^ r3
                goto L_0x00d0
            L_0x00cf:
                r11 = r6
            L_0x00d0:
                r0 = 3
                byte r0 = r14.get(r0)
                int r0 = com.google.common.primitives.UnsignedBytes.toInt(r0)
                long r3 = (long) r0
                long r2 = r3 << r2
                long r4 = r11 ^ r2
                goto L_0x00e0
            L_0x00df:
                r4 = r6
            L_0x00e0:
                r0 = 2
                byte r0 = r14.get(r0)
                int r0 = com.google.common.primitives.UnsignedBytes.toInt(r0)
                long r2 = (long) r0
                long r0 = r2 << r1
                long r2 = r4 ^ r0
                goto L_0x00f0
            L_0x00ef:
                r2 = r6
            L_0x00f0:
                r0 = 1
                byte r0 = r14.get(r0)
                int r0 = com.google.common.primitives.UnsignedBytes.toInt(r0)
                long r0 = (long) r0
                long r0 = r0 << r8
                long r4 = r2 ^ r0
                goto L_0x00ff
            L_0x00fe:
                r4 = r6
            L_0x00ff:
                r0 = 0
                byte r14 = r14.get(r0)
                int r14 = com.google.common.primitives.UnsignedBytes.toInt(r14)
                long r0 = (long) r14
                long r2 = r4 ^ r0
            L_0x010b:
                long r0 = r13.a
                long r2 = b(r2)
                long r4 = r0 ^ r2
                r13.a = r4
                long r0 = r13.b
                long r2 = c(r6)
                long r4 = r0 ^ r2
                r13.b = r4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.hash.Murmur3_128HashFunction.Murmur3_128Hasher.processRemaining(java.nio.ByteBuffer):void");
        }

        public HashCode a() {
            this.a ^= (long) this.c;
            this.b ^= (long) this.c;
            this.a += this.b;
            this.b += this.a;
            this.a = a(this.a);
            this.b = a(this.b);
            this.a += this.b;
            this.b += this.a;
            return HashCode.a(ByteBuffer.wrap(new byte[16]).order(ByteOrder.LITTLE_ENDIAN).putLong(this.a).putLong(this.b).array());
        }

        private static long b(long j) {
            return Long.rotateLeft(j * -8663945395140668459L, 31) * 5545529020109919103L;
        }

        private static long c(long j) {
            return Long.rotateLeft(j * 5545529020109919103L, 33) * -8663945395140668459L;
        }
    }

    public int bits() {
        return 128;
    }

    Murmur3_128HashFunction(int i) {
        this.a = i;
    }

    public Hasher newHasher() {
        return new Murmur3_128Hasher(this.a);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hashing.murmur3_128(");
        sb.append(this.a);
        sb.append(")");
        return sb.toString();
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof Murmur3_128HashFunction)) {
            return false;
        }
        if (this.a == ((Murmur3_128HashFunction) obj).a) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return getClass().hashCode() ^ this.a;
    }
}
