package org.bouncycastle.math.ec;

import java.math.BigInteger;
import java.util.Random;
import org.bouncycastle.math.raw.Mod;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

public abstract class ECFieldElement implements ECConstants {

    public static class F2m extends ECFieldElement {
        public static final int GNB = 1;
        public static final int PPB = 3;
        public static final int TPB = 2;
        private int a;
        private int b;
        private int[] c;
        private LongArray d;

        public F2m(int i, int i2, int i3, int i4, BigInteger bigInteger) {
            if (i3 == 0 && i4 == 0) {
                this.a = 2;
                this.c = new int[]{i2};
            } else if (i3 >= i4) {
                throw new IllegalArgumentException("k2 must be smaller than k3");
            } else if (i3 <= 0) {
                throw new IllegalArgumentException("k2 must be larger than 0");
            } else {
                this.a = 3;
                this.c = new int[]{i2, i3, i4};
            }
            this.b = i;
            this.d = new LongArray(bigInteger);
        }

        public F2m(int i, int i2, BigInteger bigInteger) {
            this(i, i2, 0, 0, bigInteger);
        }

        private F2m(int i, int[] iArr, LongArray longArray) {
            this.b = i;
            this.a = iArr.length == 1 ? 2 : 3;
            this.c = iArr;
            this.d = longArray;
        }

        public static void checkFieldElements(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            if (!(eCFieldElement instanceof F2m) || !(eCFieldElement2 instanceof F2m)) {
                throw new IllegalArgumentException("Field elements are not both instances of ECFieldElement.F2m");
            }
            F2m f2m = (F2m) eCFieldElement;
            F2m f2m2 = (F2m) eCFieldElement2;
            if (f2m.a != f2m2.a) {
                throw new IllegalArgumentException("One of the F2m field elements has incorrect representation");
            } else if (f2m.b != f2m2.b || !Arrays.areEqual(f2m.c, f2m2.c)) {
                throw new IllegalArgumentException("Field elements are not elements of the same field F2m");
            }
        }

        public ECFieldElement add(ECFieldElement eCFieldElement) {
            LongArray longArray = (LongArray) this.d.clone();
            longArray.a(((F2m) eCFieldElement).d, 0);
            return new F2m(this.b, this.c, longArray);
        }

        public ECFieldElement addOne() {
            return new F2m(this.b, this.c, this.d.f());
        }

        public int bitLength() {
            return this.d.d();
        }

        public ECFieldElement divide(ECFieldElement eCFieldElement) {
            return multiply(eCFieldElement.invert());
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof F2m)) {
                return false;
            }
            F2m f2m = (F2m) obj;
            return this.b == f2m.b && this.a == f2m.a && Arrays.areEqual(this.c, f2m.c) && this.d.equals(f2m.d);
        }

        public String getFieldName() {
            return "F2m";
        }

        public int getFieldSize() {
            return this.b;
        }

        public int getK1() {
            return this.c[0];
        }

        public int getK2() {
            if (this.c.length >= 2) {
                return this.c[1];
            }
            return 0;
        }

        public int getK3() {
            if (this.c.length >= 3) {
                return this.c[2];
            }
            return 0;
        }

        public int getM() {
            return this.b;
        }

        public int getRepresentation() {
            return this.a;
        }

        public int hashCode() {
            return (this.d.hashCode() ^ this.b) ^ Arrays.hashCode(this.c);
        }

        public ECFieldElement invert() {
            return new F2m(this.b, this.c, this.d.d(this.b, this.c));
        }

        public boolean isOne() {
            return this.d.a();
        }

        public boolean isZero() {
            return this.d.b();
        }

        public ECFieldElement multiply(ECFieldElement eCFieldElement) {
            return new F2m(this.b, this.c, this.d.a(((F2m) eCFieldElement).d, this.b, this.c));
        }

        public ECFieldElement multiplyMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
            return multiplyPlusProduct(eCFieldElement, eCFieldElement2, eCFieldElement3);
        }

        public ECFieldElement multiplyPlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
            LongArray longArray = this.d;
            LongArray longArray2 = ((F2m) eCFieldElement).d;
            LongArray longArray3 = ((F2m) eCFieldElement2).d;
            LongArray longArray4 = ((F2m) eCFieldElement3).d;
            LongArray b2 = longArray.b(longArray2, this.b, this.c);
            LongArray b3 = longArray3.b(longArray4, this.b, this.c);
            if (b2 == longArray || b2 == longArray2) {
                b2 = (LongArray) b2.clone();
            }
            b2.a(b3, 0);
            b2.a(this.b, this.c);
            return new F2m(this.b, this.c, b2);
        }

        public ECFieldElement negate() {
            return this;
        }

        public ECFieldElement sqrt() {
            LongArray longArray = this.d;
            if (longArray.a() || longArray.b()) {
                return this;
            }
            return new F2m(this.b, this.c, longArray.a(this.b - 1, this.b, this.c));
        }

        public ECFieldElement square() {
            return new F2m(this.b, this.c, this.d.b(this.b, this.c));
        }

        public ECFieldElement squareMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            return squarePlusProduct(eCFieldElement, eCFieldElement2);
        }

        public ECFieldElement squarePlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            LongArray longArray = this.d;
            LongArray longArray2 = ((F2m) eCFieldElement).d;
            LongArray longArray3 = ((F2m) eCFieldElement2).d;
            LongArray c2 = longArray.c(this.b, this.c);
            LongArray b2 = longArray2.b(longArray3, this.b, this.c);
            if (c2 == longArray) {
                c2 = (LongArray) c2.clone();
            }
            c2.a(b2, 0);
            c2.a(this.b, this.c);
            return new F2m(this.b, this.c, c2);
        }

        public ECFieldElement subtract(ECFieldElement eCFieldElement) {
            return add(eCFieldElement);
        }

        public boolean testBitZero() {
            return this.d.g();
        }

        public BigInteger toBigInteger() {
            return this.d.e();
        }
    }

    public static class Fp extends ECFieldElement {
        BigInteger a;
        BigInteger b;
        BigInteger c;

        public Fp(BigInteger bigInteger, BigInteger bigInteger2) {
            this(bigInteger, a(bigInteger), bigInteger2);
        }

        Fp(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
            if (bigInteger3 == null || bigInteger3.signum() < 0 || bigInteger3.compareTo(bigInteger) >= 0) {
                throw new IllegalArgumentException("x value invalid in Fp field element");
            }
            this.a = bigInteger;
            this.b = bigInteger2;
            this.c = bigInteger3;
        }

        static BigInteger a(BigInteger bigInteger) {
            int bitLength = bigInteger.bitLength();
            if (bitLength < 96 || bigInteger.shiftRight(bitLength - 64).longValue() != -1) {
                return null;
            }
            return ONE.shiftLeft(bitLength).subtract(bigInteger);
        }

        private ECFieldElement a(ECFieldElement eCFieldElement) {
            if (eCFieldElement.square().equals(this)) {
                return eCFieldElement;
            }
            return null;
        }

        private BigInteger[] a(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
            int bitLength = bigInteger3.bitLength();
            int lowestSetBit = bigInteger3.getLowestSetBit();
            BigInteger bigInteger4 = ECConstants.ONE;
            BigInteger bigInteger5 = ECConstants.TWO;
            BigInteger bigInteger6 = ECConstants.ONE;
            BigInteger bigInteger7 = ECConstants.ONE;
            BigInteger bigInteger8 = bigInteger;
            for (int i = bitLength - 1; i >= lowestSetBit + 1; i--) {
                bigInteger6 = modMult(bigInteger6, bigInteger7);
                if (bigInteger3.testBit(i)) {
                    bigInteger7 = modMult(bigInteger6, bigInteger2);
                    bigInteger4 = modMult(bigInteger4, bigInteger8);
                    bigInteger5 = modReduce(bigInteger8.multiply(bigInteger5).subtract(bigInteger.multiply(bigInteger6)));
                    bigInteger8 = modReduce(bigInteger8.multiply(bigInteger8).subtract(bigInteger7.shiftLeft(1)));
                } else {
                    bigInteger4 = modReduce(bigInteger4.multiply(bigInteger5).subtract(bigInteger6));
                    BigInteger modReduce = modReduce(bigInteger8.multiply(bigInteger5).subtract(bigInteger.multiply(bigInteger6)));
                    bigInteger5 = modReduce(bigInteger5.multiply(bigInteger5).subtract(bigInteger6.shiftLeft(1)));
                    bigInteger8 = modReduce;
                    bigInteger7 = bigInteger6;
                }
            }
            BigInteger modMult = modMult(bigInteger6, bigInteger7);
            BigInteger modMult2 = modMult(modMult, bigInteger2);
            BigInteger modReduce2 = modReduce(bigInteger4.multiply(bigInteger5).subtract(modMult));
            BigInteger modReduce3 = modReduce(bigInteger8.multiply(bigInteger5).subtract(bigInteger.multiply(modMult)));
            BigInteger modMult3 = modMult(modMult, modMult2);
            BigInteger bigInteger9 = modReduce3;
            for (int i2 = 1; i2 <= lowestSetBit; i2++) {
                modReduce2 = modMult(modReduce2, bigInteger9);
                bigInteger9 = modReduce(bigInteger9.multiply(bigInteger9).subtract(modMult3.shiftLeft(1)));
                modMult3 = modMult(modMult3, modMult3);
            }
            return new BigInteger[]{modReduce2, bigInteger9};
        }

        public ECFieldElement add(ECFieldElement eCFieldElement) {
            return new Fp(this.a, this.b, modAdd(this.c, eCFieldElement.toBigInteger()));
        }

        public ECFieldElement addOne() {
            BigInteger add = this.c.add(ECConstants.ONE);
            if (add.compareTo(this.a) == 0) {
                add = ECConstants.ZERO;
            }
            return new Fp(this.a, this.b, add);
        }

        public ECFieldElement divide(ECFieldElement eCFieldElement) {
            return new Fp(this.a, this.b, modMult(this.c, modInverse(eCFieldElement.toBigInteger())));
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Fp)) {
                return false;
            }
            Fp fp = (Fp) obj;
            return this.a.equals(fp.a) && this.c.equals(fp.c);
        }

        public String getFieldName() {
            return "Fp";
        }

        public int getFieldSize() {
            return this.a.bitLength();
        }

        public BigInteger getQ() {
            return this.a;
        }

        public int hashCode() {
            return this.a.hashCode() ^ this.c.hashCode();
        }

        public ECFieldElement invert() {
            return new Fp(this.a, this.b, modInverse(this.c));
        }

        /* access modifiers changed from: protected */
        public BigInteger modAdd(BigInteger bigInteger, BigInteger bigInteger2) {
            BigInteger add = bigInteger.add(bigInteger2);
            return add.compareTo(this.a) >= 0 ? add.subtract(this.a) : add;
        }

        /* access modifiers changed from: protected */
        public BigInteger modDouble(BigInteger bigInteger) {
            BigInteger shiftLeft = bigInteger.shiftLeft(1);
            return shiftLeft.compareTo(this.a) >= 0 ? shiftLeft.subtract(this.a) : shiftLeft;
        }

        /* access modifiers changed from: protected */
        public BigInteger modHalf(BigInteger bigInteger) {
            if (bigInteger.testBit(0)) {
                bigInteger = this.a.add(bigInteger);
            }
            return bigInteger.shiftRight(1);
        }

        /* access modifiers changed from: protected */
        public BigInteger modHalfAbs(BigInteger bigInteger) {
            if (bigInteger.testBit(0)) {
                bigInteger = this.a.subtract(bigInteger);
            }
            return bigInteger.shiftRight(1);
        }

        /* access modifiers changed from: protected */
        public BigInteger modInverse(BigInteger bigInteger) {
            int fieldSize = getFieldSize();
            int i = (fieldSize + 31) >> 5;
            int[] fromBigInteger = Nat.fromBigInteger(fieldSize, this.a);
            int[] fromBigInteger2 = Nat.fromBigInteger(fieldSize, bigInteger);
            int[] create = Nat.create(i);
            Mod.invert(fromBigInteger, fromBigInteger2, create);
            return Nat.toBigInteger(i, create);
        }

        /* access modifiers changed from: protected */
        public BigInteger modMult(BigInteger bigInteger, BigInteger bigInteger2) {
            return modReduce(bigInteger.multiply(bigInteger2));
        }

        /* access modifiers changed from: protected */
        public BigInteger modReduce(BigInteger bigInteger) {
            if (this.b != null) {
                boolean z = bigInteger.signum() < 0;
                if (z) {
                    bigInteger = bigInteger.abs();
                }
                int bitLength = this.a.bitLength();
                boolean equals = this.b.equals(ECConstants.ONE);
                while (bigInteger.bitLength() > bitLength + 1) {
                    BigInteger shiftRight = bigInteger.shiftRight(bitLength);
                    BigInteger subtract = bigInteger.subtract(shiftRight.shiftLeft(bitLength));
                    if (!equals) {
                        shiftRight = shiftRight.multiply(this.b);
                    }
                    bigInteger = shiftRight.add(subtract);
                }
                while (bigInteger.compareTo(this.a) >= 0) {
                    bigInteger = bigInteger.subtract(this.a);
                }
                if (z && bigInteger.signum() != 0) {
                    return this.a.subtract(bigInteger);
                }
            } else {
                bigInteger = bigInteger.mod(this.a);
            }
            return bigInteger;
        }

        /* access modifiers changed from: protected */
        public BigInteger modSubtract(BigInteger bigInteger, BigInteger bigInteger2) {
            BigInteger subtract = bigInteger.subtract(bigInteger2);
            return subtract.signum() < 0 ? subtract.add(this.a) : subtract;
        }

        public ECFieldElement multiply(ECFieldElement eCFieldElement) {
            return new Fp(this.a, this.b, modMult(this.c, eCFieldElement.toBigInteger()));
        }

        public ECFieldElement multiplyMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
            BigInteger bigInteger = this.c;
            BigInteger bigInteger2 = eCFieldElement.toBigInteger();
            BigInteger bigInteger3 = eCFieldElement2.toBigInteger();
            BigInteger bigInteger4 = eCFieldElement3.toBigInteger();
            return new Fp(this.a, this.b, modReduce(bigInteger.multiply(bigInteger2).subtract(bigInteger3.multiply(bigInteger4))));
        }

        public ECFieldElement multiplyPlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
            BigInteger bigInteger = this.c;
            BigInteger bigInteger2 = eCFieldElement.toBigInteger();
            BigInteger bigInteger3 = eCFieldElement2.toBigInteger();
            BigInteger bigInteger4 = eCFieldElement3.toBigInteger();
            return new Fp(this.a, this.b, modReduce(bigInteger.multiply(bigInteger2).add(bigInteger3.multiply(bigInteger4))));
        }

        public ECFieldElement negate() {
            return this.c.signum() == 0 ? this : new Fp(this.a, this.b, this.a.subtract(this.c));
        }

        public ECFieldElement sqrt() {
            if (isZero() || isOne()) {
                return this;
            }
            if (!this.a.testBit(0)) {
                throw new RuntimeException("not done yet");
            } else if (this.a.testBit(1)) {
                return a((ECFieldElement) new Fp(this.a, this.b, this.c.modPow(this.a.shiftRight(2).add(ECConstants.ONE), this.a)));
            } else if (this.a.testBit(2)) {
                BigInteger modPow = this.c.modPow(this.a.shiftRight(3), this.a);
                BigInteger modMult = modMult(modPow, this.c);
                if (modMult(modMult, modPow).equals(ECConstants.ONE)) {
                    return a((ECFieldElement) new Fp(this.a, this.b, modMult));
                }
                return a((ECFieldElement) new Fp(this.a, this.b, modMult(modMult, ECConstants.TWO.modPow(this.a.shiftRight(2), this.a))));
            } else {
                BigInteger shiftRight = this.a.shiftRight(1);
                if (!this.c.modPow(shiftRight, this.a).equals(ECConstants.ONE)) {
                    return null;
                }
                BigInteger bigInteger = this.c;
                BigInteger modDouble = modDouble(modDouble(bigInteger));
                BigInteger add = shiftRight.add(ECConstants.ONE);
                BigInteger subtract = this.a.subtract(ECConstants.ONE);
                Random random = new Random();
                while (true) {
                    BigInteger bigInteger2 = new BigInteger(this.a.bitLength(), random);
                    if (bigInteger2.compareTo(this.a) < 0 && modReduce(bigInteger2.multiply(bigInteger2).subtract(modDouble)).modPow(shiftRight, this.a).equals(subtract)) {
                        BigInteger[] a2 = a(bigInteger2, bigInteger, add);
                        BigInteger bigInteger3 = a2[0];
                        BigInteger bigInteger4 = a2[1];
                        if (modMult(bigInteger4, bigInteger4).equals(modDouble)) {
                            return new Fp(this.a, this.b, modHalfAbs(bigInteger4));
                        }
                        if (!bigInteger3.equals(ECConstants.ONE) && !bigInteger3.equals(subtract)) {
                            return null;
                        }
                    }
                }
            }
        }

        public ECFieldElement square() {
            return new Fp(this.a, this.b, modMult(this.c, this.c));
        }

        public ECFieldElement squareMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            BigInteger bigInteger = this.c;
            BigInteger bigInteger2 = eCFieldElement.toBigInteger();
            BigInteger bigInteger3 = eCFieldElement2.toBigInteger();
            return new Fp(this.a, this.b, modReduce(bigInteger.multiply(bigInteger).subtract(bigInteger2.multiply(bigInteger3))));
        }

        public ECFieldElement squarePlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            BigInteger bigInteger = this.c;
            BigInteger bigInteger2 = eCFieldElement.toBigInteger();
            BigInteger bigInteger3 = eCFieldElement2.toBigInteger();
            return new Fp(this.a, this.b, modReduce(bigInteger.multiply(bigInteger).add(bigInteger2.multiply(bigInteger3))));
        }

        public ECFieldElement subtract(ECFieldElement eCFieldElement) {
            return new Fp(this.a, this.b, modSubtract(this.c, eCFieldElement.toBigInteger()));
        }

        public BigInteger toBigInteger() {
            return this.c;
        }
    }

    public abstract ECFieldElement add(ECFieldElement eCFieldElement);

    public abstract ECFieldElement addOne();

    public int bitLength() {
        return toBigInteger().bitLength();
    }

    public abstract ECFieldElement divide(ECFieldElement eCFieldElement);

    public byte[] getEncoded() {
        return BigIntegers.asUnsignedByteArray((getFieldSize() + 7) / 8, toBigInteger());
    }

    public abstract String getFieldName();

    public abstract int getFieldSize();

    public abstract ECFieldElement invert();

    public boolean isOne() {
        return bitLength() == 1;
    }

    public boolean isZero() {
        return toBigInteger().signum() == 0;
    }

    public abstract ECFieldElement multiply(ECFieldElement eCFieldElement);

    public ECFieldElement multiplyMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        return multiply(eCFieldElement).subtract(eCFieldElement2.multiply(eCFieldElement3));
    }

    public ECFieldElement multiplyPlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        return multiply(eCFieldElement).add(eCFieldElement2.multiply(eCFieldElement3));
    }

    public abstract ECFieldElement negate();

    public abstract ECFieldElement sqrt();

    public abstract ECFieldElement square();

    public ECFieldElement squareMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return square().subtract(eCFieldElement.multiply(eCFieldElement2));
    }

    public ECFieldElement squarePlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return square().add(eCFieldElement.multiply(eCFieldElement2));
    }

    public abstract ECFieldElement subtract(ECFieldElement eCFieldElement);

    public boolean testBitZero() {
        return toBigInteger().testBit(0);
    }

    public abstract BigInteger toBigInteger();

    public String toString() {
        return toBigInteger().toString(16);
    }
}
