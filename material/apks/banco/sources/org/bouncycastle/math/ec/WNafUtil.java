package org.bouncycastle.math.ec;

import java.math.BigInteger;
import org.bouncycastle.asn1.eac.EACTags;

public abstract class WNafUtil {
    public static final String PRECOMP_NAME = "bc_wnaf";
    private static final int[] a = {13, 41, EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY, 337, 897, 2305};
    private static final byte[] b = new byte[0];
    private static final int[] c = new int[0];

    private static byte[] a(byte[] bArr, int i) {
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
        return bArr2;
    }

    private static int[] a(int[] iArr, int i) {
        int[] iArr2 = new int[i];
        System.arraycopy(iArr, 0, iArr2, 0, iArr2.length);
        return iArr2;
    }

    private static ECPoint[] a(ECPoint[] eCPointArr, int i) {
        ECPoint[] eCPointArr2 = new ECPoint[i];
        System.arraycopy(eCPointArr, 0, eCPointArr2, 0, eCPointArr.length);
        return eCPointArr2;
    }

    public static int[] generateCompactNaf(BigInteger bigInteger) {
        if ((bigInteger.bitLength() >>> 16) != 0) {
            throw new IllegalArgumentException("'k' must have bitlength < 2^16");
        } else if (bigInteger.signum() == 0) {
            return c;
        } else {
            BigInteger add = bigInteger.shiftLeft(1).add(bigInteger);
            int bitLength = add.bitLength();
            int[] iArr = new int[(bitLength >> 1)];
            BigInteger xor = add.xor(bigInteger);
            int i = bitLength - 1;
            int i2 = 1;
            int i3 = 0;
            int i4 = 0;
            while (i2 < i) {
                if (!xor.testBit(i2)) {
                    i4++;
                } else {
                    int i5 = i3 + 1;
                    iArr[i3] = i4 | ((bigInteger.testBit(i2) ? -1 : 1) << 16);
                    i2++;
                    i3 = i5;
                    i4 = 1;
                }
                i2++;
            }
            int i6 = i3 + 1;
            iArr[i3] = 65536 | i4;
            if (iArr.length > i6) {
                iArr = a(iArr, i6);
            }
            return iArr;
        }
    }

    public static int[] generateCompactWindowNaf(int i, BigInteger bigInteger) {
        if (i == 2) {
            return generateCompactNaf(bigInteger);
        }
        if (i < 2 || i > 16) {
            throw new IllegalArgumentException("'width' must be in the range [2, 16]");
        } else if ((bigInteger.bitLength() >>> 16) != 0) {
            throw new IllegalArgumentException("'k' must have bitlength < 2^16");
        } else if (bigInteger.signum() == 0) {
            return c;
        } else {
            int[] iArr = new int[((bigInteger.bitLength() / i) + 1)];
            int i2 = 1 << i;
            int i3 = i2 - 1;
            int i4 = i2 >>> 1;
            BigInteger bigInteger2 = bigInteger;
            int i5 = 0;
            boolean z = false;
            int i6 = 0;
            while (i5 <= bigInteger2.bitLength()) {
                if (bigInteger2.testBit(i5) == z) {
                    i5++;
                } else {
                    bigInteger2 = bigInteger2.shiftRight(i5);
                    int intValue = bigInteger2.intValue() & i3;
                    if (z) {
                        intValue++;
                    }
                    z = (intValue & i4) != 0;
                    if (z) {
                        intValue -= i2;
                    }
                    if (i6 > 0) {
                        i5--;
                    }
                    int i7 = i6 + 1;
                    iArr[i6] = i5 | (intValue << 16);
                    i5 = i;
                    i6 = i7;
                }
            }
            if (iArr.length > i6) {
                iArr = a(iArr, i6);
            }
            return iArr;
        }
    }

    public static byte[] generateJSF(BigInteger bigInteger, BigInteger bigInteger2) {
        byte[] bArr = new byte[(Math.max(bigInteger.bitLength(), bigInteger2.bitLength()) + 1)];
        BigInteger bigInteger3 = bigInteger;
        BigInteger bigInteger4 = bigInteger2;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if ((i | i2) == 0 && bigInteger3.bitLength() <= i3 && bigInteger4.bitLength() <= i3) {
                break;
            }
            int intValue = ((bigInteger3.intValue() >>> i3) + i) & 7;
            int intValue2 = ((bigInteger4.intValue() >>> i3) + i2) & 7;
            int i5 = intValue & 1;
            if (i5 != 0) {
                i5 -= intValue & 2;
                if (intValue + i5 == 4 && (intValue2 & 3) == 2) {
                    i5 = -i5;
                }
            }
            int i6 = intValue2 & 1;
            if (i6 != 0) {
                i6 -= intValue2 & 2;
                if (intValue2 + i6 == 4 && (intValue & 3) == 2) {
                    i6 = -i6;
                }
            }
            if ((i << 1) == i5 + 1) {
                i ^= 1;
            }
            if ((i2 << 1) == i6 + 1) {
                i2 ^= 1;
            }
            i3++;
            if (i3 == 30) {
                bigInteger3 = bigInteger3.shiftRight(30);
                bigInteger4 = bigInteger4.shiftRight(30);
                i3 = 0;
            }
            int i7 = i4 + 1;
            bArr[i4] = (byte) ((i5 << 4) | (i6 & 15));
            i4 = i7;
        }
        return bArr.length > i4 ? a(bArr, i4) : bArr;
    }

    public static byte[] generateNaf(BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return b;
        }
        BigInteger add = bigInteger.shiftLeft(1).add(bigInteger);
        int bitLength = add.bitLength() - 1;
        byte[] bArr = new byte[bitLength];
        BigInteger xor = add.xor(bigInteger);
        int i = 1;
        while (i < bitLength) {
            if (xor.testBit(i)) {
                bArr[i - 1] = (byte) (bigInteger.testBit(i) ? -1 : 1);
                i++;
            }
            i++;
        }
        bArr[bitLength - 1] = 1;
        return bArr;
    }

    public static byte[] generateWindowNaf(int i, BigInteger bigInteger) {
        if (i == 2) {
            return generateNaf(bigInteger);
        }
        if (i < 2 || i > 8) {
            throw new IllegalArgumentException("'width' must be in the range [2, 8]");
        } else if (bigInteger.signum() == 0) {
            return b;
        } else {
            byte[] bArr = new byte[(bigInteger.bitLength() + 1)];
            int i2 = 1 << i;
            int i3 = i2 - 1;
            int i4 = i2 >>> 1;
            BigInteger bigInteger2 = bigInteger;
            int i5 = 0;
            boolean z = false;
            int i6 = 0;
            while (i5 <= bigInteger2.bitLength()) {
                if (bigInteger2.testBit(i5) == z) {
                    i5++;
                } else {
                    bigInteger2 = bigInteger2.shiftRight(i5);
                    int intValue = bigInteger2.intValue() & i3;
                    if (z) {
                        intValue++;
                    }
                    z = (intValue & i4) != 0;
                    if (z) {
                        intValue -= i2;
                    }
                    if (i6 > 0) {
                        i5--;
                    }
                    int i7 = i6 + i5;
                    int i8 = i7 + 1;
                    bArr[i7] = (byte) intValue;
                    i6 = i8;
                    i5 = i;
                }
            }
            if (bArr.length > i6) {
                bArr = a(bArr, i6);
            }
            return bArr;
        }
    }

    public static int getNafWeight(BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return 0;
        }
        return bigInteger.shiftLeft(1).add(bigInteger).xor(bigInteger).bitCount();
    }

    public static WNafPreCompInfo getWNafPreCompInfo(ECPoint eCPoint) {
        return getWNafPreCompInfo(eCPoint.getCurve().getPreCompInfo(eCPoint, PRECOMP_NAME));
    }

    public static WNafPreCompInfo getWNafPreCompInfo(PreCompInfo preCompInfo) {
        return (preCompInfo == null || !(preCompInfo instanceof WNafPreCompInfo)) ? new WNafPreCompInfo() : (WNafPreCompInfo) preCompInfo;
    }

    public static int getWindowSize(int i) {
        return getWindowSize(i, a);
    }

    public static int getWindowSize(int i, int[] iArr) {
        int i2 = 0;
        while (i2 < iArr.length && i >= iArr[i2]) {
            i2++;
        }
        return i2 + 2;
    }

    public static ECPoint mapPointWithPrecomp(ECPoint eCPoint, int i, boolean z, ECPointMap eCPointMap) {
        ECCurve curve = eCPoint.getCurve();
        WNafPreCompInfo precompute = precompute(eCPoint, i, z);
        ECPoint map = eCPointMap.map(eCPoint);
        WNafPreCompInfo wNafPreCompInfo = getWNafPreCompInfo(curve.getPreCompInfo(map, PRECOMP_NAME));
        ECPoint twice = precompute.getTwice();
        if (twice != null) {
            wNafPreCompInfo.setTwice(eCPointMap.map(twice));
        }
        ECPoint[] preComp = precompute.getPreComp();
        ECPoint[] eCPointArr = new ECPoint[preComp.length];
        for (int i2 = 0; i2 < preComp.length; i2++) {
            eCPointArr[i2] = eCPointMap.map(preComp[i2]);
        }
        wNafPreCompInfo.setPreComp(eCPointArr);
        if (z) {
            ECPoint[] eCPointArr2 = new ECPoint[eCPointArr.length];
            for (int i3 = 0; i3 < eCPointArr2.length; i3++) {
                eCPointArr2[i3] = eCPointArr[i3].negate();
            }
            wNafPreCompInfo.setPreCompNeg(eCPointArr2);
        }
        curve.setPreCompInfo(map, PRECOMP_NAME, wNafPreCompInfo);
        return map;
    }

    public static WNafPreCompInfo precompute(ECPoint eCPoint, int i, boolean z) {
        ECCurve curve = eCPoint.getCurve();
        WNafPreCompInfo wNafPreCompInfo = getWNafPreCompInfo(curve.getPreCompInfo(eCPoint, PRECOMP_NAME));
        ECPoint[] preComp = wNafPreCompInfo.getPreComp();
        int i2 = 0;
        if (preComp == null) {
            preComp = new ECPoint[]{eCPoint};
        }
        int length = preComp.length;
        int max = 1 << Math.max(0, i - 2);
        if (length < max) {
            preComp = a(preComp, max);
            if (max == 2) {
                preComp[1] = preComp[0].threeTimes();
            } else {
                ECPoint twice = wNafPreCompInfo.getTwice();
                if (twice == null) {
                    twice = preComp[0].twice();
                    wNafPreCompInfo.setTwice(twice);
                }
                while (length < max) {
                    preComp[length] = twice.add(preComp[length - 1]);
                    length++;
                }
            }
            curve.normalizeAll(preComp);
        }
        wNafPreCompInfo.setPreComp(preComp);
        if (z) {
            ECPoint[] preCompNeg = wNafPreCompInfo.getPreCompNeg();
            if (preCompNeg == null) {
                preCompNeg = new ECPoint[max];
            } else {
                i2 = preCompNeg.length;
                if (i2 < max) {
                    preCompNeg = a(preCompNeg, max);
                }
            }
            while (i2 < max) {
                preCompNeg[i2] = preComp[i2].negate();
                i2++;
            }
            wNafPreCompInfo.setPreCompNeg(preCompNeg);
        }
        curve.setPreCompInfo(eCPoint, PRECOMP_NAME, wNafPreCompInfo);
        return wNafPreCompInfo;
    }
}
