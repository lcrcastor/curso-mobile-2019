package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;

public class ECDomainParameters implements ECConstants {
    private ECCurve a;
    private byte[] b;
    private ECPoint c;
    private BigInteger d;
    private BigInteger e;

    public ECDomainParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger) {
        this(eCCurve, eCPoint, bigInteger, ONE, null);
    }

    public ECDomainParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        this(eCCurve, eCPoint, bigInteger, bigInteger2, null);
    }

    public ECDomainParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        this.a = eCCurve;
        this.c = eCPoint.normalize();
        this.d = bigInteger;
        this.e = bigInteger2;
        this.b = bArr;
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
        return Arrays.clone(this.b);
    }
}
