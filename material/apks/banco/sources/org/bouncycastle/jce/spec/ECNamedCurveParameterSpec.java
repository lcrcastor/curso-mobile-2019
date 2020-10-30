package org.bouncycastle.jce.spec;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

public class ECNamedCurveParameterSpec extends ECParameterSpec {
    private String a;

    public ECNamedCurveParameterSpec(String str, ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger) {
        super(eCCurve, eCPoint, bigInteger);
        this.a = str;
    }

    public ECNamedCurveParameterSpec(String str, ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        super(eCCurve, eCPoint, bigInteger, bigInteger2);
        this.a = str;
    }

    public ECNamedCurveParameterSpec(String str, ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        super(eCCurve, eCPoint, bigInteger, bigInteger2, bArr);
        this.a = str;
    }

    public String getName() {
        return this.a;
    }
}
