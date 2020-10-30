package org.bouncycastle.math.ec;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECCurve.F2m;

class Tnaf {
    public static final ZTauElement[] a = {null, new ZTauElement(ECConstants.ONE, ECConstants.ZERO), null, new ZTauElement(g, e), null, new ZTauElement(e, e), null, new ZTauElement(ECConstants.ONE, e), null};
    public static final byte[][] b = {null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, 1}};
    public static final ZTauElement[] c = {null, new ZTauElement(ECConstants.ONE, ECConstants.ZERO), null, new ZTauElement(g, ECConstants.ONE), null, new ZTauElement(e, ECConstants.ONE), null, new ZTauElement(ECConstants.ONE, ECConstants.ONE), null};
    public static final byte[][] d = {null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, -1}};
    private static final BigInteger e = ECConstants.ONE.negate();
    private static final BigInteger f = ECConstants.TWO.negate();
    private static final BigInteger g = ECConstants.THREE.negate();

    Tnaf() {
    }

    public static byte a(F2m f2m) {
        if (f2m.isKoblitz()) {
            return f2m.getA().isZero() ? (byte) -1 : 1;
        }
        throw new IllegalArgumentException("No Koblitz curve (ABC), TNAF multiplication not possible");
    }

    protected static int a(BigInteger bigInteger) {
        if (bigInteger != null) {
            if (bigInteger.equals(ECConstants.TWO)) {
                return 1;
            }
            if (bigInteger.equals(ECConstants.FOUR)) {
                return 2;
            }
        }
        throw new IllegalArgumentException("h (Cofactor) must be 2 or 4");
    }

    public static BigInteger a(byte b2, int i) {
        if (i == 4) {
            return b2 == 1 ? BigInteger.valueOf(6) : BigInteger.valueOf(10);
        }
        BigInteger[] a2 = a(b2, i, false);
        BigInteger bit = ECConstants.ZERO.setBit(i);
        return ECConstants.TWO.multiply(a2[0]).multiply(a2[1].modInverse(bit)).mod(bit);
    }

    public static BigInteger a(byte b2, ZTauElement zTauElement) {
        BigInteger subtract;
        BigInteger multiply = zTauElement.a.multiply(zTauElement.a);
        BigInteger multiply2 = zTauElement.a.multiply(zTauElement.b);
        BigInteger shiftLeft = zTauElement.b.multiply(zTauElement.b).shiftLeft(1);
        if (b2 == 1) {
            subtract = multiply.add(multiply2);
        } else if (b2 == -1) {
            subtract = multiply.subtract(multiply2);
        } else {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        return subtract.add(shiftLeft);
    }

    public static ECPoint.F2m a(ECPoint.F2m f2m) {
        return f2m.tau();
    }

    public static ECPoint.F2m a(ECPoint.F2m f2m, byte[] bArr) {
        ECPoint.F2m f2m2 = (ECPoint.F2m) ((F2m) f2m.getCurve()).getInfinity();
        for (int length = bArr.length - 1; length >= 0; length--) {
            f2m2 = a(f2m2);
            if (bArr[length] == 1) {
                f2m2 = f2m2.addSimple(f2m);
            } else if (bArr[length] == -1) {
                f2m2 = f2m2.subtractSimple(f2m);
            }
        }
        return f2m2;
    }

    public static SimpleBigDecimal a(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, byte b2, int i, int i2) {
        int i3 = ((i + 5) / 2) + i2;
        BigInteger multiply = bigInteger2.multiply(bigInteger.shiftRight(((i - i3) - 2) + b2));
        BigInteger add = multiply.add(bigInteger3.multiply(multiply.shiftRight(i)));
        int i4 = i3 - i2;
        BigInteger shiftRight = add.shiftRight(i4);
        if (add.testBit(i4 - 1)) {
            shiftRight = shiftRight.add(ECConstants.ONE);
        }
        return new SimpleBigDecimal(shiftRight, i2);
    }

    public static ZTauElement a(BigInteger bigInteger, int i, byte b2, BigInteger[] bigIntegerArr, byte b3, byte b4) {
        byte b5 = b3;
        BigInteger add = b5 == 1 ? bigIntegerArr[0].add(bigIntegerArr[1]) : bigIntegerArr[0].subtract(bigIntegerArr[1]);
        int i2 = i;
        BigInteger bigInteger2 = bigInteger;
        BigInteger bigInteger3 = a(b5, i2, true)[1];
        byte b6 = b2;
        int i3 = i2;
        byte b7 = b4;
        ZTauElement a2 = a(a(bigInteger2, bigIntegerArr[0], bigInteger3, b6, i3, (int) b7), a(bigInteger2, bigIntegerArr[1], bigInteger3, b6, i3, (int) b7), b5);
        return new ZTauElement(bigInteger2.subtract(add.multiply(a2.a)).subtract(BigInteger.valueOf(2).multiply(bigIntegerArr[1]).multiply(a2.b)), bigIntegerArr[1].multiply(a2.a).subtract(bigIntegerArr[0].multiply(a2.b)));
    }

    /* JADX INFO: used method not loaded: org.bouncycastle.math.ec.SimpleBigDecimal.b(java.math.BigInteger):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0099, code lost:
        if (r7.b(f) < 0) goto L_0x008e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0093  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.bouncycastle.math.ec.ZTauElement a(org.bouncycastle.math.ec.SimpleBigDecimal r7, org.bouncycastle.math.ec.SimpleBigDecimal r8, byte r9) {
        /*
            int r0 = r7.d()
            int r1 = r8.d()
            if (r1 == r0) goto L_0x0012
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.String r8 = "lambda0 and lambda1 do not have same scale"
            r7.<init>(r8)
            throw r7
        L_0x0012:
            r0 = -1
            r1 = 1
            if (r9 == r1) goto L_0x0020
            if (r9 == r0) goto L_0x0020
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.String r8 = "mu must be 1 or -1"
            r7.<init>(r8)
            throw r7
        L_0x0020:
            java.math.BigInteger r2 = r7.c()
            java.math.BigInteger r3 = r8.c()
            org.bouncycastle.math.ec.SimpleBigDecimal r7 = r7.a(r2)
            org.bouncycastle.math.ec.SimpleBigDecimal r8 = r8.a(r3)
            org.bouncycastle.math.ec.SimpleBigDecimal r4 = r7.a(r7)
            if (r9 != r1) goto L_0x003b
            org.bouncycastle.math.ec.SimpleBigDecimal r4 = r4.a(r8)
            goto L_0x003f
        L_0x003b:
            org.bouncycastle.math.ec.SimpleBigDecimal r4 = r4.b(r8)
        L_0x003f:
            org.bouncycastle.math.ec.SimpleBigDecimal r5 = r8.a(r8)
            org.bouncycastle.math.ec.SimpleBigDecimal r5 = r5.a(r8)
            org.bouncycastle.math.ec.SimpleBigDecimal r8 = r5.a(r8)
            if (r9 != r1) goto L_0x0056
            org.bouncycastle.math.ec.SimpleBigDecimal r5 = r7.b(r5)
            org.bouncycastle.math.ec.SimpleBigDecimal r7 = r7.a(r8)
            goto L_0x005e
        L_0x0056:
            org.bouncycastle.math.ec.SimpleBigDecimal r5 = r7.a(r5)
            org.bouncycastle.math.ec.SimpleBigDecimal r7 = r7.b(r8)
        L_0x005e:
            java.math.BigInteger r8 = org.bouncycastle.math.ec.ECConstants.ONE
            int r8 = r4.b(r8)
            r6 = 0
            if (r8 < 0) goto L_0x0073
            java.math.BigInteger r8 = e
            int r8 = r5.b(r8)
            if (r8 >= 0) goto L_0x0070
            goto L_0x007b
        L_0x0070:
            r8 = 0
            r6 = 1
            goto L_0x007e
        L_0x0073:
            java.math.BigInteger r8 = org.bouncycastle.math.ec.ECConstants.TWO
            int r8 = r7.b(r8)
            if (r8 < 0) goto L_0x007d
        L_0x007b:
            r8 = r9
            goto L_0x007e
        L_0x007d:
            r8 = 0
        L_0x007e:
            java.math.BigInteger r1 = e
            int r1 = r4.b(r1)
            if (r1 >= 0) goto L_0x0093
            java.math.BigInteger r7 = org.bouncycastle.math.ec.ECConstants.ONE
            int r7 = r5.b(r7)
            if (r7 < 0) goto L_0x0091
        L_0x008e:
            int r7 = -r9
            byte r8 = (byte) r7
            goto L_0x009c
        L_0x0091:
            r6 = -1
            goto L_0x009c
        L_0x0093:
            java.math.BigInteger r0 = f
            int r7 = r7.b(r0)
            if (r7 >= 0) goto L_0x009c
            goto L_0x008e
        L_0x009c:
            long r0 = (long) r6
            java.math.BigInteger r7 = java.math.BigInteger.valueOf(r0)
            java.math.BigInteger r7 = r2.add(r7)
            long r8 = (long) r8
            java.math.BigInteger r8 = java.math.BigInteger.valueOf(r8)
            java.math.BigInteger r8 = r3.add(r8)
            org.bouncycastle.math.ec.ZTauElement r9 = new org.bouncycastle.math.ec.ZTauElement
            r9.<init>(r7, r8)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.math.ec.Tnaf.a(org.bouncycastle.math.ec.SimpleBigDecimal, org.bouncycastle.math.ec.SimpleBigDecimal, byte):org.bouncycastle.math.ec.ZTauElement");
    }

    public static byte[] a(byte b2, ZTauElement zTauElement, byte b3, BigInteger bigInteger, BigInteger bigInteger2, ZTauElement[] zTauElementArr) {
        byte b4;
        boolean z;
        if (b2 == 1 || b2 == -1) {
            int bitLength = a(b2, zTauElement).bitLength();
            byte[] bArr = new byte[(bitLength > 30 ? bitLength + 4 + b3 : b3 + 34)];
            BigInteger shiftRight = bigInteger.shiftRight(1);
            BigInteger bigInteger3 = zTauElement.a;
            BigInteger bigInteger4 = zTauElement.b;
            int i = 0;
            while (true) {
                if (bigInteger3.equals(ECConstants.ZERO) && bigInteger4.equals(ECConstants.ZERO)) {
                    return bArr;
                }
                if (bigInteger3.testBit(0)) {
                    BigInteger mod = bigInteger3.add(bigInteger4.multiply(bigInteger2)).mod(bigInteger);
                    if (mod.compareTo(shiftRight) >= 0) {
                        mod = mod.subtract(bigInteger);
                    }
                    byte intValue = (byte) mod.intValue();
                    bArr[i] = intValue;
                    if (intValue < 0) {
                        b4 = (byte) (-intValue);
                        z = false;
                    } else {
                        b4 = intValue;
                        z = true;
                    }
                    if (z) {
                        bigInteger3 = bigInteger3.subtract(zTauElementArr[b4].a);
                        bigInteger4 = bigInteger4.subtract(zTauElementArr[b4].b);
                    } else {
                        bigInteger3 = bigInteger3.add(zTauElementArr[b4].a);
                        bigInteger4 = bigInteger4.add(zTauElementArr[b4].b);
                    }
                } else {
                    bArr[i] = 0;
                }
                BigInteger add = b2 == 1 ? bigInteger4.add(bigInteger3.shiftRight(1)) : bigInteger4.subtract(bigInteger3.shiftRight(1));
                i++;
                BigInteger negate = bigInteger3.shiftRight(1).negate();
                bigInteger3 = add;
                bigInteger4 = negate;
            }
        } else {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
    }

    public static BigInteger[] a(byte b2, int i, boolean z) {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        if (b2 == 1 || b2 == -1) {
            if (z) {
                bigInteger = ECConstants.TWO;
                bigInteger2 = BigInteger.valueOf((long) b2);
            } else {
                bigInteger = ECConstants.ZERO;
                bigInteger2 = ECConstants.ONE;
            }
            BigInteger bigInteger3 = bigInteger2;
            BigInteger bigInteger4 = bigInteger;
            int i2 = 1;
            while (i2 < i) {
                i2++;
                BigInteger bigInteger5 = bigInteger3;
                bigInteger3 = (b2 == 1 ? bigInteger3 : bigInteger3.negate()).subtract(bigInteger4.shiftLeft(1));
                bigInteger4 = bigInteger5;
            }
            return new BigInteger[]{bigInteger4, bigInteger3};
        }
        throw new IllegalArgumentException("mu must be 1 or -1");
    }

    public static ECPoint.F2m[] a(ECPoint.F2m f2m, byte b2) {
        ECPoint.F2m[] f2mArr = new ECPoint.F2m[16];
        f2mArr[1] = f2m;
        byte[][] bArr = b2 == 0 ? b : d;
        int length = bArr.length;
        for (int i = 3; i < length; i += 2) {
            f2mArr[i] = a(f2m, bArr[i]);
        }
        f2m.getCurve().normalizeAll(f2mArr);
        return f2mArr;
    }

    public static BigInteger[] b(F2m f2m) {
        if (!f2m.isKoblitz()) {
            throw new IllegalArgumentException("si is defined for Koblitz curves only");
        }
        int m = f2m.getM();
        int intValue = f2m.getA().toBigInteger().intValue();
        byte a2 = f2m.a();
        int a3 = a(f2m.getCofactor());
        BigInteger[] a4 = a(a2, (m + 3) - intValue, false);
        if (a2 == 1) {
            a4[0] = a4[0].negate();
            a4[1] = a4[1].negate();
        }
        return new BigInteger[]{ECConstants.ONE.add(a4[1]).shiftRight(a3), ECConstants.ONE.add(a4[0]).shiftRight(a3).negate()};
    }
}
