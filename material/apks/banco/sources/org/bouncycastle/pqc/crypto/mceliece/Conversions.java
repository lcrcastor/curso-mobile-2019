package org.bouncycastle.pqc.crypto.mceliece;

import java.math.BigInteger;
import org.bouncycastle.pqc.math.linearalgebra.BigIntUtils;
import org.bouncycastle.pqc.math.linearalgebra.GF2Vector;
import org.bouncycastle.pqc.math.linearalgebra.IntegerFunctions;

final class Conversions {
    private static final BigInteger a = BigInteger.valueOf(0);
    private static final BigInteger b = BigInteger.valueOf(1);

    private Conversions() {
    }

    public static GF2Vector a(int i, int i2, byte[] bArr) {
        if (i < i2) {
            throw new IllegalArgumentException("n < t");
        }
        BigInteger binomial = IntegerFunctions.binomial(i, i2);
        BigInteger bigInteger = new BigInteger(1, bArr);
        if (bigInteger.compareTo(binomial) >= 0) {
            throw new IllegalArgumentException("Encoded number too large.");
        }
        GF2Vector gF2Vector = new GF2Vector(i);
        BigInteger bigInteger2 = bigInteger;
        int i3 = i2;
        int i4 = i;
        for (int i5 = 0; i5 < i; i5++) {
            binomial = binomial.multiply(BigInteger.valueOf((long) (i4 - i3))).divide(BigInteger.valueOf((long) i4));
            i4--;
            if (binomial.compareTo(bigInteger2) <= 0) {
                gF2Vector.setBit(i5);
                bigInteger2 = bigInteger2.subtract(binomial);
                i3--;
                binomial = i4 == i3 ? b : binomial.multiply(BigInteger.valueOf((long) (i3 + 1))).divide(BigInteger.valueOf((long) (i4 - i3)));
            }
        }
        return gF2Vector;
    }

    public static byte[] a(int i, int i2, GF2Vector gF2Vector) {
        if (gF2Vector.getLength() == i && gF2Vector.getHammingWeight() == i2) {
            int[] vecArray = gF2Vector.getVecArray();
            BigInteger binomial = IntegerFunctions.binomial(i, i2);
            BigInteger bigInteger = a;
            int i3 = i2;
            int i4 = i;
            for (int i5 = 0; i5 < i; i5++) {
                binomial = binomial.multiply(BigInteger.valueOf((long) (i4 - i3))).divide(BigInteger.valueOf((long) i4));
                i4--;
                if ((vecArray[i5 >> 5] & (1 << (i5 & 31))) != 0) {
                    bigInteger = bigInteger.add(binomial);
                    i3--;
                    binomial = i4 == i3 ? b : binomial.multiply(BigInteger.valueOf((long) (i3 + 1))).divide(BigInteger.valueOf((long) (i4 - i3)));
                }
            }
            return BigIntUtils.toMinimalByteArray(bigInteger);
        }
        throw new IllegalArgumentException("vector has wrong length or hamming weight");
    }
}
