package org.bouncycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

public class ECParameterSpec implements AlgorithmParameterSpec {
    private ECCurve a;
    private byte[] b;
    private ECPoint c;
    private BigInteger d;
    private BigInteger e;

    public ECParameterSpec(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger) {
        this.a = eCCurve;
        this.c = eCPoint.normalize();
        this.d = bigInteger;
        this.e = BigInteger.valueOf(1);
        this.b = null;
    }

    public ECParameterSpec(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        this.a = eCCurve;
        this.c = eCPoint.normalize();
        this.d = bigInteger;
        this.e = bigInteger2;
        this.b = null;
    }

    public ECParameterSpec(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        this.a = eCCurve;
        this.c = eCPoint.normalize();
        this.d = bigInteger;
        this.e = bigInteger2;
        this.b = bArr;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof ECParameterSpec)) {
            return false;
        }
        ECParameterSpec eCParameterSpec = (ECParameterSpec) obj;
        if (getCurve().equals(eCParameterSpec.getCurve()) && getG().equals(eCParameterSpec.getG())) {
            z = true;
        }
        return z;
    }

    public ECCurve getCurve() {
        return this.a;
    }

    public ECPoint getG() {
        return this.c;
    }

    public BigInteger getH() {
        return this.e;
    }

    public BigInteger getN() {
        return this.d;
    }

    public byte[] getSeed() {
        return this.b;
    }

    public int hashCode() {
        return getCurve().hashCode() ^ getG().hashCode();
    }
}
