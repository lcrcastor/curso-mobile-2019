package org.bouncycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

public class Permutation {
    private int[] a;

    public Permutation(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("invalid length");
        }
        this.a = new int[i];
        for (int i2 = i - 1; i2 >= 0; i2--) {
            this.a[i2] = i2;
        }
    }

    public Permutation(int i, SecureRandom secureRandom) {
        if (i <= 0) {
            throw new IllegalArgumentException("invalid length");
        }
        this.a = new int[i];
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = i2;
        }
        int i3 = i;
        for (int i4 = 0; i4 < i; i4++) {
            int a2 = RandUtils.a(secureRandom, i3);
            i3--;
            this.a[i4] = iArr[a2];
            iArr[a2] = iArr[i3];
        }
    }

    public Permutation(byte[] bArr) {
        if (bArr.length <= 4) {
            throw new IllegalArgumentException("invalid encoding");
        }
        int OS2IP = LittleEndianConversions.OS2IP(bArr, 0);
        int ceilLog256 = IntegerFunctions.ceilLog256(OS2IP - 1);
        if (bArr.length != (OS2IP * ceilLog256) + 4) {
            throw new IllegalArgumentException("invalid encoding");
        }
        this.a = new int[OS2IP];
        for (int i = 0; i < OS2IP; i++) {
            this.a[i] = LittleEndianConversions.OS2IP(bArr, (i * ceilLog256) + 4, ceilLog256);
        }
        if (!a(this.a)) {
            throw new IllegalArgumentException("invalid encoding");
        }
    }

    public Permutation(int[] iArr) {
        if (!a(iArr)) {
            throw new IllegalArgumentException("array is not a permutation vector");
        }
        this.a = IntUtils.clone(iArr);
    }

    private boolean a(int[] iArr) {
        int length = iArr.length;
        boolean[] zArr = new boolean[length];
        for (int i = 0; i < length; i++) {
            if (iArr[i] < 0 || iArr[i] >= length || zArr[iArr[i]]) {
                return false;
            }
            zArr[iArr[i]] = true;
        }
        return true;
    }

    public Permutation computeInverse() {
        Permutation permutation = new Permutation(this.a.length);
        for (int length = this.a.length - 1; length >= 0; length--) {
            permutation.a[this.a[length]] = length;
        }
        return permutation;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Permutation)) {
            return false;
        }
        return IntUtils.equals(this.a, ((Permutation) obj).a);
    }

    public byte[] getEncoded() {
        int length = this.a.length;
        int ceilLog256 = IntegerFunctions.ceilLog256(length - 1);
        byte[] bArr = new byte[((length * ceilLog256) + 4)];
        LittleEndianConversions.I2OSP(length, bArr, 0);
        for (int i = 0; i < length; i++) {
            LittleEndianConversions.I2OSP(this.a[i], bArr, (i * ceilLog256) + 4, ceilLog256);
        }
        return bArr;
    }

    public int[] getVector() {
        return IntUtils.clone(this.a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public Permutation rightMultiply(Permutation permutation) {
        if (permutation.a.length != this.a.length) {
            throw new IllegalArgumentException("length mismatch");
        }
        Permutation permutation2 = new Permutation(this.a.length);
        for (int length = this.a.length - 1; length >= 0; length--) {
            permutation2.a[length] = this.a[permutation.a[length]];
        }
        return permutation2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.a[0]);
        String sb2 = sb.toString();
        for (int i = 1; i < this.a.length; i++) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(", ");
            sb3.append(this.a[i]);
            sb2 = sb3.toString();
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(sb2);
        sb4.append("]");
        return sb4.toString();
    }
}
