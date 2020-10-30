package org.bouncycastle.crypto.prng.drbg;

import org.bouncycastle.math.ec.ECPoint;

public class DualECPoints {
    private final ECPoint a;
    private final ECPoint b;
    private final int c;
    private final int d;

    public DualECPoints(int i, ECPoint eCPoint, ECPoint eCPoint2, int i2) {
        if (!eCPoint.getCurve().equals(eCPoint2.getCurve())) {
            throw new IllegalArgumentException("points need to be on the same curve");
        }
        this.c = i;
        this.a = eCPoint;
        this.b = eCPoint2;
        this.d = i2;
    }

    private static int a(int i) {
        int i2 = 0;
        while (true) {
            i >>= 1;
            if (i == 0) {
                return i2;
            }
            i2++;
        }
    }

    public int getCofactor() {
        return this.d;
    }

    public int getMaxOutlen() {
        return ((this.a.getCurve().getFieldSize() - (a(this.d) + 13)) / 8) * 8;
    }

    public ECPoint getP() {
        return this.a;
    }

    public ECPoint getQ() {
        return this.b;
    }

    public int getSecurityStrength() {
        return this.c;
    }

    public int getSeedLen() {
        return this.a.getCurve().getFieldSize();
    }
}
