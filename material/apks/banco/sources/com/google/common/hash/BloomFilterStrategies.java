package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.math.RoundingMode;
import java.util.Arrays;
import javax.annotation.Nullable;

enum BloomFilterStrategies implements Strategy {
    MURMUR128_MITZ_32 {
        public <T> boolean a(T t, Funnel<? super T> funnel, int i, BitArray bitArray) {
            long a = bitArray.a();
            long asLong = Hashing.murmur3_128().hashObject(t, funnel).asLong();
            int i2 = (int) asLong;
            int i3 = (int) (asLong >>> 32);
            boolean z = false;
            for (int i4 = 1; i4 <= i; i4++) {
                int i5 = (i4 * i3) + i2;
                if (i5 < 0) {
                    i5 ^= -1;
                }
                z |= bitArray.a(((long) i5) % a);
            }
            return z;
        }

        public <T> boolean b(T t, Funnel<? super T> funnel, int i, BitArray bitArray) {
            long a = bitArray.a();
            long asLong = Hashing.murmur3_128().hashObject(t, funnel).asLong();
            int i2 = (int) asLong;
            int i3 = (int) (asLong >>> 32);
            for (int i4 = 1; i4 <= i; i4++) {
                int i5 = (i4 * i3) + i2;
                if (i5 < 0) {
                    i5 ^= -1;
                }
                if (!bitArray.b(((long) i5) % a)) {
                    return false;
                }
            }
            return true;
        }
    },
    MURMUR128_MITZ_64 {
        public <T> boolean a(T t, Funnel<? super T> funnel, int i, BitArray bitArray) {
            long a = bitArray.a();
            byte[] a2 = Hashing.murmur3_128().hashObject(t, funnel).a();
            long a3 = a(a2);
            long b = b(a2);
            int i2 = 0;
            long j = a3;
            boolean z = false;
            while (i2 < i) {
                z |= bitArray.a((j & Long.MAX_VALUE) % a);
                i2++;
                j += b;
            }
            return z;
        }

        public <T> boolean b(T t, Funnel<? super T> funnel, int i, BitArray bitArray) {
            long a = bitArray.a();
            byte[] a2 = Hashing.murmur3_128().hashObject(t, funnel).a();
            long a3 = a(a2);
            long b = b(a2);
            long j = a3;
            int i2 = 0;
            while (i2 < i) {
                if (!bitArray.b((j & Long.MAX_VALUE) % a)) {
                    return false;
                }
                i2++;
                j += b;
            }
            return true;
        }

        private long a(byte[] bArr) {
            return Longs.fromBytes(bArr[7], bArr[6], bArr[5], bArr[4], bArr[3], bArr[2], bArr[1], bArr[0]);
        }

        private long b(byte[] bArr) {
            return Longs.fromBytes(bArr[15], bArr[14], bArr[13], bArr[12], bArr[11], bArr[10], bArr[9], bArr[8]);
        }
    };

    static final class BitArray {
        final long[] a;
        long b;

        BitArray(long j) {
            this(new long[Ints.checkedCast(LongMath.divide(j, 64, RoundingMode.CEILING))]);
        }

        BitArray(long[] jArr) {
            int i = 0;
            Preconditions.checkArgument(jArr.length > 0, "data length is zero!");
            this.a = jArr;
            long j = 0;
            while (i < jArr.length) {
                i++;
                j += (long) Long.bitCount(jArr[i]);
            }
            this.b = j;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(long j) {
            if (b(j)) {
                return false;
            }
            long[] jArr = this.a;
            int i = (int) (j >>> 6);
            jArr[i] = jArr[i] | (1 << ((int) j));
            this.b++;
            return true;
        }

        /* access modifiers changed from: 0000 */
        public boolean b(long j) {
            return (this.a[(int) (j >>> 6)] & (1 << ((int) j))) != 0;
        }

        /* access modifiers changed from: 0000 */
        public long a() {
            return ((long) this.a.length) * 64;
        }

        /* access modifiers changed from: 0000 */
        public long b() {
            return this.b;
        }

        /* access modifiers changed from: 0000 */
        public BitArray c() {
            return new BitArray((long[]) this.a.clone());
        }

        /* access modifiers changed from: 0000 */
        public void a(BitArray bitArray) {
            Preconditions.checkArgument(this.a.length == bitArray.a.length, "BitArrays must be of equal length (%s != %s)", this.a.length, bitArray.a.length);
            this.b = 0;
            for (int i = 0; i < this.a.length; i++) {
                long[] jArr = this.a;
                jArr[i] = jArr[i] | bitArray.a[i];
                this.b += (long) Long.bitCount(this.a[i]);
            }
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof BitArray)) {
                return false;
            }
            return Arrays.equals(this.a, ((BitArray) obj).a);
        }

        public int hashCode() {
            return Arrays.hashCode(this.a);
        }
    }
}
