package org.bouncycastle.pqc.jcajce.provider.mceliece;

import java.io.IOException;
import java.security.PrivateKey;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.pqc.asn1.McElieceCCA2PrivateKey;
import org.bouncycastle.pqc.crypto.mceliece.McElieceCCA2Parameters;
import org.bouncycastle.pqc.crypto.mceliece.McElieceCCA2PrivateKeyParameters;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2PrivateKeySpec;
import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class BCMcElieceCCA2PrivateKey implements PrivateKey, CipherParameters {
    private static final long serialVersionUID = 1;
    private String a;
    private int b;
    private int c;
    private GF2mField d;
    private PolynomialGF2mSmallM e;
    private Permutation f;
    private GF2Matrix g;
    private PolynomialGF2mSmallM[] h;
    private McElieceCCA2Parameters i;

    public BCMcElieceCCA2PrivateKey(String str, int i2, int i3, GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM, Permutation permutation, GF2Matrix gF2Matrix, PolynomialGF2mSmallM[] polynomialGF2mSmallMArr) {
        this.a = str;
        this.b = i2;
        this.c = i3;
        this.d = gF2mField;
        this.e = polynomialGF2mSmallM;
        this.f = permutation;
        this.g = gF2Matrix;
        this.h = polynomialGF2mSmallMArr;
    }

    public BCMcElieceCCA2PrivateKey(McElieceCCA2PrivateKeyParameters mcElieceCCA2PrivateKeyParameters) {
        this(mcElieceCCA2PrivateKeyParameters.getOIDString(), mcElieceCCA2PrivateKeyParameters.getN(), mcElieceCCA2PrivateKeyParameters.getK(), mcElieceCCA2PrivateKeyParameters.getField(), mcElieceCCA2PrivateKeyParameters.getGoppaPoly(), mcElieceCCA2PrivateKeyParameters.getP(), mcElieceCCA2PrivateKeyParameters.getH(), mcElieceCCA2PrivateKeyParameters.getQInv());
        this.i = mcElieceCCA2PrivateKeyParameters.getParameters();
    }

    public BCMcElieceCCA2PrivateKey(McElieceCCA2PrivateKeySpec mcElieceCCA2PrivateKeySpec) {
        this(mcElieceCCA2PrivateKeySpec.getOIDString(), mcElieceCCA2PrivateKeySpec.getN(), mcElieceCCA2PrivateKeySpec.getK(), mcElieceCCA2PrivateKeySpec.getField(), mcElieceCCA2PrivateKeySpec.getGoppaPoly(), mcElieceCCA2PrivateKeySpec.getP(), mcElieceCCA2PrivateKeySpec.getH(), mcElieceCCA2PrivateKeySpec.getQInv());
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj != null) {
            if (!(obj instanceof BCMcElieceCCA2PrivateKey)) {
                return false;
            }
            BCMcElieceCCA2PrivateKey bCMcElieceCCA2PrivateKey = (BCMcElieceCCA2PrivateKey) obj;
            if (this.b == bCMcElieceCCA2PrivateKey.b && this.c == bCMcElieceCCA2PrivateKey.c && this.d.equals(bCMcElieceCCA2PrivateKey.d) && this.e.equals(bCMcElieceCCA2PrivateKey.e) && this.f.equals(bCMcElieceCCA2PrivateKey.f) && this.g.equals(bCMcElieceCCA2PrivateKey.g)) {
                z = true;
            }
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public ASN1Primitive getAlgParams() {
        return null;
    }

    public String getAlgorithm() {
        return "McEliece";
    }

    public byte[] getEncoded() {
        McElieceCCA2PrivateKey mcElieceCCA2PrivateKey = new McElieceCCA2PrivateKey(new ASN1ObjectIdentifier(this.a), this.b, this.c, this.d, this.e, this.f, this.g, this.h);
        try {
            try {
                return new PrivateKeyInfo(new AlgorithmIdentifier(getOID(), DERNull.INSTANCE), mcElieceCCA2PrivateKey).getEncoded();
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            }
        } catch (IOException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public GF2mField getField() {
        return this.d;
    }

    public String getFormat() {
        return null;
    }

    public PolynomialGF2mSmallM getGoppaPoly() {
        return this.e;
    }

    public GF2Matrix getH() {
        return this.g;
    }

    public int getK() {
        return this.c;
    }

    public McElieceCCA2Parameters getMcElieceCCA2Parameters() {
        return this.i;
    }

    public int getN() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public ASN1ObjectIdentifier getOID() {
        return new ASN1ObjectIdentifier("1.3.6.1.4.1.8301.3.1.3.4.2");
    }

    public String getOIDString() {
        return this.a;
    }

    public Permutation getP() {
        return this.f;
    }

    public PolynomialGF2mSmallM[] getQInv() {
        return this.h;
    }

    public int getT() {
        return this.e.getDegree();
    }

    public int hashCode() {
        return this.c + this.b + this.d.hashCode() + this.e.hashCode() + this.f.hashCode() + this.g.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(" extension degree of the field      : ");
        sb.append(this.b);
        sb.append("\n");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(" dimension of the code              : ");
        sb3.append(this.c);
        sb3.append("\n");
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb4);
        sb5.append(" irreducible Goppa polynomial       : ");
        sb5.append(this.e);
        sb5.append("\n");
        return sb5.toString();
    }
}
