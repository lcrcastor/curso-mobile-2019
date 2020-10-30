package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.annotation.Nullable;

@Beta
public final class BloomFilter<T> implements Predicate<T>, Serializable {
    /* access modifiers changed from: private */
    public final BitArray a;
    /* access modifiers changed from: private */
    public final int b;
    /* access modifiers changed from: private */
    public final Funnel<? super T> c;
    /* access modifiers changed from: private */
    public final Strategy d;

    static class SerialForm<T> implements Serializable {
        private static final long serialVersionUID = 1;
        final long[] a;
        final int b;
        final Funnel<? super T> c;
        final Strategy d;

        SerialForm(BloomFilter<T> bloomFilter) {
            this.a = bloomFilter.a.a;
            this.b = bloomFilter.b;
            this.c = bloomFilter.c;
            this.d = bloomFilter.d;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            BloomFilter bloomFilter = new BloomFilter(new BitArray(this.a), this.b, this.c, this.d);
            return bloomFilter;
        }
    }

    interface Strategy extends Serializable {
        <T> boolean a(T t, Funnel<? super T> funnel, int i, BitArray bitArray);

        <T> boolean b(T t, Funnel<? super T> funnel, int i, BitArray bitArray);

        int ordinal();
    }

    private BloomFilter(BitArray bitArray, int i, Funnel<? super T> funnel, Strategy strategy) {
        boolean z = false;
        Preconditions.checkArgument(i > 0, "numHashFunctions (%s) must be > 0", i);
        if (i <= 255) {
            z = true;
        }
        Preconditions.checkArgument(z, "numHashFunctions (%s) must be <= 255", i);
        this.a = (BitArray) Preconditions.checkNotNull(bitArray);
        this.b = i;
        this.c = (Funnel) Preconditions.checkNotNull(funnel);
        this.d = (Strategy) Preconditions.checkNotNull(strategy);
    }

    public BloomFilter<T> copy() {
        return new BloomFilter<>(this.a.c(), this.b, this.c, this.d);
    }

    public boolean mightContain(T t) {
        return this.d.b(t, this.c, this.b, this.a);
    }

    @Deprecated
    public boolean apply(T t) {
        return mightContain(t);
    }

    @CanIgnoreReturnValue
    public boolean put(T t) {
        return this.d.a(t, this.c, this.b, this.a);
    }

    public double expectedFpp() {
        return Math.pow(((double) this.a.b()) / ((double) a()), (double) this.b);
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public long a() {
        return this.a.a();
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.common.hash.BloomFilter<T>, code=com.google.common.hash.BloomFilter, for r6v0, types: [com.google.common.hash.BloomFilter<T>, com.google.common.hash.BloomFilter, java.lang.Object] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isCompatible(com.google.common.hash.BloomFilter r6) {
        /*
            r5 = this;
            com.google.common.base.Preconditions.checkNotNull(r6)
            if (r5 == r6) goto L_0x002d
            int r0 = r5.b
            int r1 = r6.b
            if (r0 != r1) goto L_0x002d
            long r0 = r5.a()
            long r2 = r6.a()
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x002d
            com.google.common.hash.BloomFilter$Strategy r0 = r5.d
            com.google.common.hash.BloomFilter$Strategy r1 = r6.d
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x002d
            com.google.common.hash.Funnel<? super T> r0 = r5.c
            com.google.common.hash.Funnel<? super T> r6 = r6.c
            boolean r6 = r0.equals(r6)
            if (r6 == 0) goto L_0x002d
            r6 = 1
            goto L_0x002e
        L_0x002d:
            r6 = 0
        L_0x002e:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.hash.BloomFilter.isCompatible(com.google.common.hash.BloomFilter):boolean");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.common.hash.BloomFilter<T>, code=com.google.common.hash.BloomFilter, for r14v0, types: [com.google.common.hash.BloomFilter<T>, com.google.common.hash.BloomFilter, java.lang.Object] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void putAll(com.google.common.hash.BloomFilter r14) {
        /*
            r13 = this;
            com.google.common.base.Preconditions.checkNotNull(r14)
            r0 = 0
            r1 = 1
            if (r13 == r14) goto L_0x0009
            r2 = 1
            goto L_0x000a
        L_0x0009:
            r2 = 0
        L_0x000a:
            java.lang.String r3 = "Cannot combine a BloomFilter with itself."
            com.google.common.base.Preconditions.checkArgument(r2, r3)
            int r2 = r13.b
            int r3 = r14.b
            if (r2 != r3) goto L_0x0017
            r2 = 1
            goto L_0x0018
        L_0x0017:
            r2 = 0
        L_0x0018:
            java.lang.String r3 = "BloomFilters must have the same number of hash functions (%s != %s)"
            int r4 = r13.b
            int r5 = r14.b
            com.google.common.base.Preconditions.checkArgument(r2, r3, r4, r5)
            long r2 = r13.a()
            long r4 = r14.a()
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 != 0) goto L_0x002f
            r7 = 1
            goto L_0x0030
        L_0x002f:
            r7 = 0
        L_0x0030:
            java.lang.String r8 = "BloomFilters must have the same size underlying bit arrays (%s != %s)"
            long r9 = r13.a()
            long r11 = r14.a()
            com.google.common.base.Preconditions.checkArgument(r7, r8, r9, r11)
            com.google.common.hash.BloomFilter$Strategy r0 = r13.d
            com.google.common.hash.BloomFilter$Strategy r1 = r14.d
            boolean r0 = r0.equals(r1)
            java.lang.String r1 = "BloomFilters must have equal strategies (%s != %s)"
            com.google.common.hash.BloomFilter$Strategy r2 = r13.d
            com.google.common.hash.BloomFilter$Strategy r3 = r14.d
            com.google.common.base.Preconditions.checkArgument(r0, r1, r2, r3)
            com.google.common.hash.Funnel<? super T> r0 = r13.c
            com.google.common.hash.Funnel<? super T> r1 = r14.c
            boolean r0 = r0.equals(r1)
            java.lang.String r1 = "BloomFilters must have equal funnels (%s != %s)"
            com.google.common.hash.Funnel<? super T> r2 = r13.c
            com.google.common.hash.Funnel<? super T> r3 = r14.c
            com.google.common.base.Preconditions.checkArgument(r0, r1, r2, r3)
            com.google.common.hash.BloomFilterStrategies$BitArray r0 = r13.a
            com.google.common.hash.BloomFilterStrategies$BitArray r14 = r14.a
            r0.a(r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.hash.BloomFilter.putAll(com.google.common.hash.BloomFilter):void");
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BloomFilter)) {
            return false;
        }
        BloomFilter bloomFilter = (BloomFilter) obj;
        if (this.b != bloomFilter.b || !this.c.equals(bloomFilter.c) || !this.a.equals(bloomFilter.a) || !this.d.equals(bloomFilter.d)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.b), this.c, this.d, this.a);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, int i, double d2) {
        return create(funnel, (long) i, d2);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long j, double d2) {
        return a(funnel, j, d2, BloomFilterStrategies.MURMUR128_MITZ_64);
    }

    @VisibleForTesting
    static <T> BloomFilter<T> a(Funnel<? super T> funnel, long j, double d2, Strategy strategy) {
        Preconditions.checkNotNull(funnel);
        boolean z = false;
        Preconditions.checkArgument(j >= 0, "Expected insertions (%s) must be >= 0", j);
        Preconditions.checkArgument(d2 > 0.0d, "False positive probability (%s) must be > 0.0", (Object) Double.valueOf(d2));
        if (d2 < 1.0d) {
            z = true;
        }
        Preconditions.checkArgument(z, "False positive probability (%s) must be < 1.0", (Object) Double.valueOf(d2));
        Preconditions.checkNotNull(strategy);
        if (j == 0) {
            j = 1;
        }
        long a2 = a(j, d2);
        try {
            return new BloomFilter<>(new BitArray(a2), a(j, a2), funnel, strategy);
        } catch (IllegalArgumentException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Could not create BloomFilter of ");
            sb.append(a2);
            sb.append(" bits");
            throw new IllegalArgumentException(sb.toString(), e);
        }
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, int i) {
        return create(funnel, (long) i);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long j) {
        return create(funnel, j, 0.03d);
    }

    @VisibleForTesting
    static int a(long j, long j2) {
        return Math.max(1, (int) Math.round((((double) j2) / ((double) j)) * Math.log(2.0d)));
    }

    @VisibleForTesting
    static long a(long j, double d2) {
        if (d2 == 0.0d) {
            d2 = Double.MIN_VALUE;
        }
        return (long) ((((double) (-j)) * Math.log(d2)) / (Math.log(2.0d) * Math.log(2.0d)));
    }

    private Object writeReplace() {
        return new SerialForm(this);
    }

    public void writeTo(OutputStream outputStream) {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeByte(SignedBytes.checkedCast((long) this.d.ordinal()));
        dataOutputStream.writeByte(UnsignedBytes.checkedCast((long) this.b));
        dataOutputStream.writeInt(this.a.a.length);
        for (long writeLong : this.a.a) {
            dataOutputStream.writeLong(writeLong);
        }
    }

    public static <T> BloomFilter<T> readFrom(InputStream inputStream, Funnel<T> funnel) {
        byte b2;
        int i;
        int i2;
        Preconditions.checkNotNull(inputStream, "InputStream");
        Preconditions.checkNotNull(funnel, "Funnel");
        try {
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            b2 = dataInputStream.readByte();
            try {
                i2 = UnsignedBytes.toInt(dataInputStream.readByte());
                try {
                    i = dataInputStream.readInt();
                } catch (RuntimeException e) {
                    e = e;
                    i = -1;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unable to deserialize BloomFilter from InputStream. strategyOrdinal: ");
                    sb.append(b2);
                    sb.append(" numHashFunctions: ");
                    sb.append(i2);
                    sb.append(" dataLength: ");
                    sb.append(i);
                    throw new IOException(sb.toString(), e);
                }
            } catch (RuntimeException e2) {
                e = e2;
                i2 = -1;
                i = -1;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Unable to deserialize BloomFilter from InputStream. strategyOrdinal: ");
                sb2.append(b2);
                sb2.append(" numHashFunctions: ");
                sb2.append(i2);
                sb2.append(" dataLength: ");
                sb2.append(i);
                throw new IOException(sb2.toString(), e);
            }
            try {
                BloomFilterStrategies bloomFilterStrategies = BloomFilterStrategies.values()[b2];
                long[] jArr = new long[i];
                for (int i3 = 0; i3 < jArr.length; i3++) {
                    jArr[i3] = dataInputStream.readLong();
                }
                return new BloomFilter<>(new BitArray(jArr), i2, funnel, bloomFilterStrategies);
            } catch (RuntimeException e3) {
                e = e3;
                StringBuilder sb22 = new StringBuilder();
                sb22.append("Unable to deserialize BloomFilter from InputStream. strategyOrdinal: ");
                sb22.append(b2);
                sb22.append(" numHashFunctions: ");
                sb22.append(i2);
                sb22.append(" dataLength: ");
                sb22.append(i);
                throw new IOException(sb22.toString(), e);
            }
        } catch (RuntimeException e4) {
            e = e4;
            b2 = -1;
            i2 = -1;
            i = -1;
            StringBuilder sb222 = new StringBuilder();
            sb222.append("Unable to deserialize BloomFilter from InputStream. strategyOrdinal: ");
            sb222.append(b2);
            sb222.append(" numHashFunctions: ");
            sb222.append(i2);
            sb222.append(" dataLength: ");
            sb222.append(i);
            throw new IOException(sb222.toString(), e);
        }
    }
}
