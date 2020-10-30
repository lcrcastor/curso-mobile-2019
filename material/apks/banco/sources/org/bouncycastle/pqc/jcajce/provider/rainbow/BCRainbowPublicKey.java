package org.bouncycastle.pqc.jcajce.provider.rainbow;

import java.security.PublicKey;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.bouncycastle.pqc.asn1.RainbowPublicKey;
import org.bouncycastle.pqc.crypto.rainbow.RainbowPublicKeyParameters;
import org.bouncycastle.pqc.crypto.rainbow.util.RainbowUtil;
import org.bouncycastle.pqc.jcajce.provider.util.KeyUtil;
import org.bouncycastle.pqc.jcajce.spec.RainbowPublicKeySpec;
import org.bouncycastle.util.Arrays;

public class BCRainbowPublicKey implements PublicKey {
    private static final long serialVersionUID = 1;
    private short[][] a;
    private short[][] b;
    private short[] c;
    private int d;

    public BCRainbowPublicKey(int i, short[][] sArr, short[][] sArr2, short[] sArr3) {
        this.d = i;
        this.a = sArr;
        this.b = sArr2;
        this.c = sArr3;
    }

    public BCRainbowPublicKey(RainbowPublicKeyParameters rainbowPublicKeyParameters) {
        this(rainbowPublicKeyParameters.getDocLength(), rainbowPublicKeyParameters.getCoeffQuadratic(), rainbowPublicKeyParameters.getCoeffSingular(), rainbowPublicKeyParameters.getCoeffScalar());
    }

    public BCRainbowPublicKey(RainbowPublicKeySpec rainbowPublicKeySpec) {
        this(rainbowPublicKeySpec.getDocLength(), rainbowPublicKeySpec.getCoeffQuadratic(), rainbowPublicKeySpec.getCoeffSingular(), rainbowPublicKeySpec.getCoeffScalar());
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj != null) {
            if (!(obj instanceof BCRainbowPublicKey)) {
                return false;
            }
            BCRainbowPublicKey bCRainbowPublicKey = (BCRainbowPublicKey) obj;
            if (this.d == bCRainbowPublicKey.getDocLength() && RainbowUtil.equals(this.a, bCRainbowPublicKey.getCoeffQuadratic()) && RainbowUtil.equals(this.b, bCRainbowPublicKey.getCoeffSingular()) && RainbowUtil.equals(this.c, bCRainbowPublicKey.getCoeffScalar())) {
                z = true;
            }
        }
        return z;
    }

    public final String getAlgorithm() {
        return "Rainbow";
    }

    public short[][] getCoeffQuadratic() {
        return this.a;
    }

    public short[] getCoeffScalar() {
        return Arrays.clone(this.c);
    }

    public short[][] getCoeffSingular() {
        short[][] sArr = new short[this.b.length][];
        for (int i = 0; i != this.b.length; i++) {
            sArr[i] = Arrays.clone(this.b[i]);
        }
        return sArr;
    }

    public int getDocLength() {
        return this.d;
    }

    public byte[] getEncoded() {
        return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.rainbow, DERNull.INSTANCE), (ASN1Encodable) new RainbowPublicKey(this.d, this.a, this.b, this.c));
    }

    public String getFormat() {
        return "X.509";
    }

    public int hashCode() {
        return (((((this.d * 37) + Arrays.hashCode(this.a)) * 37) + Arrays.hashCode(this.b)) * 37) + Arrays.hashCode(this.c);
    }
}
