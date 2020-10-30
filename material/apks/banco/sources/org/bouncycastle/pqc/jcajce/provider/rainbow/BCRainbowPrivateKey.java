package org.bouncycastle.pqc.jcajce.provider.rainbow;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.Arrays;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.bouncycastle.pqc.asn1.RainbowPrivateKey;
import org.bouncycastle.pqc.crypto.rainbow.Layer;
import org.bouncycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.rainbow.util.RainbowUtil;
import org.bouncycastle.pqc.jcajce.spec.RainbowPrivateKeySpec;

public class BCRainbowPrivateKey implements PrivateKey {
    private static final long serialVersionUID = 1;
    private short[][] a;
    private short[] b;
    private short[][] c;
    private short[] d;
    private Layer[] e;
    private int[] f;

    public BCRainbowPrivateKey(RainbowPrivateKeyParameters rainbowPrivateKeyParameters) {
        this(rainbowPrivateKeyParameters.getInvA1(), rainbowPrivateKeyParameters.getB1(), rainbowPrivateKeyParameters.getInvA2(), rainbowPrivateKeyParameters.getB2(), rainbowPrivateKeyParameters.getVi(), rainbowPrivateKeyParameters.getLayers());
    }

    public BCRainbowPrivateKey(RainbowPrivateKeySpec rainbowPrivateKeySpec) {
        this(rainbowPrivateKeySpec.getInvA1(), rainbowPrivateKeySpec.getB1(), rainbowPrivateKeySpec.getInvA2(), rainbowPrivateKeySpec.getB2(), rainbowPrivateKeySpec.getVi(), rainbowPrivateKeySpec.getLayers());
    }

    public BCRainbowPrivateKey(short[][] sArr, short[] sArr2, short[][] sArr3, short[] sArr4, int[] iArr, Layer[] layerArr) {
        this.a = sArr;
        this.b = sArr2;
        this.c = sArr3;
        this.d = sArr4;
        this.f = iArr;
        this.e = layerArr;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BCRainbowPrivateKey)) {
            return false;
        }
        BCRainbowPrivateKey bCRainbowPrivateKey = (BCRainbowPrivateKey) obj;
        boolean z = ((((RainbowUtil.equals(this.a, bCRainbowPrivateKey.getInvA1())) && RainbowUtil.equals(this.c, bCRainbowPrivateKey.getInvA2())) && RainbowUtil.equals(this.b, bCRainbowPrivateKey.getB1())) && RainbowUtil.equals(this.d, bCRainbowPrivateKey.getB2())) && Arrays.equals(this.f, bCRainbowPrivateKey.getVi());
        if (this.e.length != bCRainbowPrivateKey.getLayers().length) {
            return false;
        }
        for (int length = this.e.length - 1; length >= 0; length--) {
            z &= this.e[length].equals(bCRainbowPrivateKey.getLayers()[length]);
        }
        return z;
    }

    public final String getAlgorithm() {
        return "Rainbow";
    }

    public short[] getB1() {
        return this.b;
    }

    public short[] getB2() {
        return this.d;
    }

    public byte[] getEncoded() {
        RainbowPrivateKey rainbowPrivateKey = new RainbowPrivateKey(this.a, this.b, this.c, this.d, this.f, this.e);
        try {
            try {
                return new PrivateKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.rainbow, DERNull.INSTANCE), rainbowPrivateKey).getEncoded();
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            }
        } catch (IOException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public short[][] getInvA1() {
        return this.a;
    }

    public short[][] getInvA2() {
        return this.c;
    }

    public Layer[] getLayers() {
        return this.e;
    }

    public int[] getVi() {
        return this.f;
    }

    public int hashCode() {
        int length = (((((((((this.e.length * 37) + org.bouncycastle.util.Arrays.hashCode(this.a)) * 37) + org.bouncycastle.util.Arrays.hashCode(this.b)) * 37) + org.bouncycastle.util.Arrays.hashCode(this.c)) * 37) + org.bouncycastle.util.Arrays.hashCode(this.d)) * 37) + org.bouncycastle.util.Arrays.hashCode(this.f);
        for (int length2 = this.e.length - 1; length2 >= 0; length2--) {
            length = (length * 37) + this.e[length2].hashCode();
        }
        return length;
    }
}
