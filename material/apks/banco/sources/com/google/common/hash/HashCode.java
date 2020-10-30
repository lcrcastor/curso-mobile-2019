package com.google.common.hash;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.common.annotations.Beta;
import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedBytes;
import com.google.common.primitives.UnsignedInts;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import javax.annotation.Nullable;

@Beta
public abstract class HashCode {
    private static final char[] a = "0123456789abcdef".toCharArray();

    static final class BytesHashCode extends HashCode implements Serializable {
        private static final long serialVersionUID = 0;
        final byte[] a;

        BytesHashCode(byte[] bArr) {
            this.a = (byte[]) Preconditions.checkNotNull(bArr);
        }

        public int bits() {
            return this.a.length * 8;
        }

        public byte[] asBytes() {
            return (byte[]) this.a.clone();
        }

        public int asInt() {
            Preconditions.checkState(this.a.length >= 4, "HashCode#asInt() requires >= 4 bytes (it only has %s bytes).", this.a.length);
            return (this.a[0] & UnsignedBytes.MAX_VALUE) | ((this.a[1] & UnsignedBytes.MAX_VALUE) << 8) | ((this.a[2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((this.a[3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN);
        }

        public long asLong() {
            Preconditions.checkState(this.a.length >= 8, "HashCode#asLong() requires >= 8 bytes (it only has %s bytes).", this.a.length);
            return padToLong();
        }

        public long padToLong() {
            long j = (long) (this.a[0] & UnsignedBytes.MAX_VALUE);
            int i = 1;
            while (i < Math.min(this.a.length, 8)) {
                i++;
                j |= (((long) this.a[i]) & 255) << (i * 8);
            }
            return j;
        }

        /* access modifiers changed from: 0000 */
        public void a(byte[] bArr, int i, int i2) {
            System.arraycopy(this.a, 0, bArr, i, i2);
        }

        /* access modifiers changed from: 0000 */
        public byte[] a() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(HashCode hashCode) {
            if (this.a.length != hashCode.a().length) {
                return false;
            }
            boolean z = true;
            for (int i = 0; i < this.a.length; i++) {
                z &= this.a[i] == hashCode.a()[i];
            }
            return z;
        }
    }

    static final class IntHashCode extends HashCode implements Serializable {
        private static final long serialVersionUID = 0;
        final int a;

        public int bits() {
            return 32;
        }

        IntHashCode(int i) {
            this.a = i;
        }

        public byte[] asBytes() {
            return new byte[]{(byte) this.a, (byte) (this.a >> 8), (byte) (this.a >> 16), (byte) (this.a >> 24)};
        }

        public int asInt() {
            return this.a;
        }

        public long asLong() {
            throw new IllegalStateException("this HashCode only has 32 bits; cannot create a long");
        }

        public long padToLong() {
            return UnsignedInts.toLong(this.a);
        }

        /* access modifiers changed from: 0000 */
        public void a(byte[] bArr, int i, int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                bArr[i + i3] = (byte) (this.a >> (i3 * 8));
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(HashCode hashCode) {
            return this.a == hashCode.asInt();
        }
    }

    static final class LongHashCode extends HashCode implements Serializable {
        private static final long serialVersionUID = 0;
        final long a;

        public int bits() {
            return 64;
        }

        LongHashCode(long j) {
            this.a = j;
        }

        public byte[] asBytes() {
            return new byte[]{(byte) ((int) this.a), (byte) ((int) (this.a >> 8)), (byte) ((int) (this.a >> 16)), (byte) ((int) (this.a >> 24)), (byte) ((int) (this.a >> 32)), (byte) ((int) (this.a >> 40)), (byte) ((int) (this.a >> 48)), (byte) ((int) (this.a >> 56))};
        }

        public int asInt() {
            return (int) this.a;
        }

        public long asLong() {
            return this.a;
        }

        public long padToLong() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public void a(byte[] bArr, int i, int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                bArr[i + i3] = (byte) ((int) (this.a >> (i3 * 8)));
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(HashCode hashCode) {
            return this.a == hashCode.asLong();
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void a(byte[] bArr, int i, int i2);

    /* access modifiers changed from: 0000 */
    public abstract boolean a(HashCode hashCode);

    public abstract byte[] asBytes();

    public abstract int asInt();

    public abstract long asLong();

    public abstract int bits();

    public abstract long padToLong();

    HashCode() {
    }

    @CanIgnoreReturnValue
    public int writeBytesTo(byte[] bArr, int i, int i2) {
        int min = Ints.min(i2, bits() / 8);
        Preconditions.checkPositionIndexes(i, i + min, bArr.length);
        a(bArr, i, min);
        return min;
    }

    /* access modifiers changed from: 0000 */
    public byte[] a() {
        return asBytes();
    }

    public static HashCode fromInt(int i) {
        return new IntHashCode(i);
    }

    public static HashCode fromLong(long j) {
        return new LongHashCode(j);
    }

    public static HashCode fromBytes(byte[] bArr) {
        boolean z = true;
        if (bArr.length < 1) {
            z = false;
        }
        Preconditions.checkArgument(z, "A HashCode must contain at least 1 byte.");
        return a((byte[]) bArr.clone());
    }

    static HashCode a(byte[] bArr) {
        return new BytesHashCode(bArr);
    }

    public static HashCode fromString(String str) {
        boolean z = true;
        Preconditions.checkArgument(str.length() >= 2, "input string (%s) must have at least 2 characters", (Object) str);
        if (str.length() % 2 != 0) {
            z = false;
        }
        Preconditions.checkArgument(z, "input string (%s) must have an even number of characters", (Object) str);
        byte[] bArr = new byte[(str.length() / 2)];
        for (int i = 0; i < str.length(); i += 2) {
            bArr[i / 2] = (byte) ((a(str.charAt(i)) << 4) + a(str.charAt(i + 1)));
        }
        return a(bArr);
    }

    private static int a(char c) {
        if (c >= '0' && c <= '9') {
            return c - TarjetasConstants.ULT_NUM_AMEX;
        }
        if (c >= 'a' && c <= 'f') {
            return (c - 'a') + 10;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Illegal hexadecimal character: ");
        sb.append(c);
        throw new IllegalArgumentException(sb.toString());
    }

    public final boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof HashCode)) {
            return false;
        }
        HashCode hashCode = (HashCode) obj;
        if (bits() == hashCode.bits() && a(hashCode)) {
            z = true;
        }
        return z;
    }

    public final int hashCode() {
        if (bits() >= 32) {
            return asInt();
        }
        byte[] a2 = a();
        byte b = a2[0] & UnsignedBytes.MAX_VALUE;
        for (int i = 1; i < a2.length; i++) {
            b |= (a2[i] & UnsignedBytes.MAX_VALUE) << (i * 8);
        }
        return b;
    }

    public final String toString() {
        byte[] a2 = a();
        StringBuilder sb = new StringBuilder(a2.length * 2);
        for (byte b : a2) {
            sb.append(a[(b >> 4) & 15]);
            sb.append(a[b & Ascii.SI]);
        }
        return sb.toString();
    }
}
