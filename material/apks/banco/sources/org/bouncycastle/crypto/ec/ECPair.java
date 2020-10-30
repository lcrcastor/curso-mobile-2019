package org.bouncycastle.crypto.ec;

import org.bouncycastle.math.ec.ECPoint;

public class ECPair {
    private final ECPoint a;
    private final ECPoint b;

    public ECPair(ECPoint eCPoint, ECPoint eCPoint2) {
        this.a = eCPoint;
        this.b = eCPoint2;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ECPair) {
            return equals((ECPair) obj);
        }
        return false;
    }

    public boolean equals(ECPair eCPair) {
        return eCPair.getX().equals(getX()) && eCPair.getY().equals(getY());
    }

    public ECPoint getX() {
        return this.a;
    }

    public ECPoint getY() {
        return this.b;
    }

    public int hashCode() {
        return this.a.hashCode() + (this.b.hashCode() * 37);
    }
}
