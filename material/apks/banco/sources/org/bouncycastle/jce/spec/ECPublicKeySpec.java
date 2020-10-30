package org.bouncycastle.jce.spec;

import org.bouncycastle.math.ec.ECPoint;

public class ECPublicKeySpec extends ECKeySpec {
    private ECPoint a;

    public ECPublicKeySpec(ECPoint eCPoint, ECParameterSpec eCParameterSpec) {
        super(eCParameterSpec);
        if (eCPoint.getCurve() != null) {
            eCPoint = eCPoint.normalize();
        }
        this.a = eCPoint;
    }

    public ECPoint getQ() {
        return this.a;
    }
}
