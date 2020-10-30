package org.bouncycastle.pqc.jcajce.provider.mceliece;

import java.io.IOException;
import java.security.PrivateKey;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.pqc.asn1.McEliecePrivateKey;
import org.bouncycastle.pqc.crypto.mceliece.McElieceParameters;
import org.bouncycastle.pqc.crypto.mceliece.McEliecePrivateKeyParameters;
import org.bouncycastle.pqc.jcajce.spec.McEliecePrivateKeySpec;
import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class BCMcEliecePrivateKey implements PrivateKey, CipherParameters {
    private static final long serialVersionUID = 1;
    private String a;
    private int b;
    private int c;
    private GF2mField d;
    private PolynomialGF2mSmallM e;
    private GF2Matrix f;
    private Permutation g;
    private Permutation h;
    private GF2Matrix i;
    private PolynomialGF2mSmallM[] j;
    private McElieceParameters k;

    public BCMcEliecePrivateKey(String str, int i2, int i3, GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM, GF2Matrix gF2Matrix, Permutation permutation, Permutation permutation2, GF2Matrix gF2Matrix2, PolynomialGF2mSmallM[] polynomialGF2mSmallMArr) {
        this.a = str;
        this.b = i2;
        this.c = i3;
        this.d = gF2mField;
        this.e = polynomialGF2mSmallM;
        this.f = gF2Matrix;
        this.g = permutation;
        this.h = permutation2;
        this.i = gF2Matrix2;
        this.j = polynomialGF2mSmallMArr;
    }

    public BCMcEliecePrivateKey(McEliecePrivateKeyParameters mcEliecePrivateKeyParameters) {
        this(mcEliecePrivateKeyParameters.getOIDString(), mcEliecePrivateKeyParameters.getN(), mcEliecePrivateKeyParameters.getK(), mcEliecePrivateKeyParameters.getField(), mcEliecePrivateKeyParameters.getGoppaPoly(), mcEliecePrivateKeyParameters.getSInv(), mcEliecePrivateKeyParameters.getP1(), mcEliecePrivateKeyParameters.getP2(), mcEliecePrivateKeyParameters.getH(), mcEliecePrivateKeyParameters.getQInv());
        this.k = mcEliecePrivateKeyParameters.getParameters();
    }

    public BCMcEliecePrivateKey(McEliecePrivateKeySpec mcEliecePrivateKeySpec) {
        this(mcEliecePrivateKeySpec.getOIDString(), mcEliecePrivateKeySpec.getN(), mcEliecePrivateKeySpec.getK(), mcEliecePrivateKeySpec.getField(), mcEliecePrivateKeySpec.getGoppaPoly(), mcEliecePrivateKeySpec.getSInv(), mcEliecePrivateKeySpec.getP1(), mcEliecePrivateKeySpec.getP2(), mcEliecePrivateKeySpec.getH(), mcEliecePrivateKeySpec.getQInv());
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof BCMcEliecePrivateKey)) {
            return false;
        }
        BCMcEliecePrivateKey bCMcEliecePrivateKey = (BCMcEliecePrivateKey) obj;
        if (this.b == bCMcEliecePrivateKey.b && this.c == bCMcEliecePrivateKey.c && this.d.equals(bCMcEliecePrivateKey.d) && this.e.equals(bCMcEliecePrivateKey.e) && this.f.equals(bCMcEliecePrivateKey.f) && this.g.equals(bCMcEliecePrivateKey.g) && this.h.equals(bCMcEliecePrivateKey.h) && this.i.equals(bCMcEliecePrivateKey.i)) {
            z = true;
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
        McEliecePrivateKey mcEliecePrivateKey = new McEliecePrivateKey(new ASN1ObjectIdentifier(this.a), this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j);
        try {
            try {
                return new PrivateKeyInfo(new AlgorithmIdentifier(getOID(), DERNull.INSTANCE), mcEliecePrivateKey).getEncoded();
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
        return this.i;
    }

    public int getK() {
        return this.c;
    }

    public McElieceParameters getMcElieceParameters() {
        return this.k;
    }

    public int getN() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public ASN1ObjectIdentifier getOID() {
        return new ASN1ObjectIdentifier("1.3.6.1.4.1.8301.3.1.3.4.1");
    }

    public String getOIDString() {
        return this.a;
    }

    public Permutation getP1() {
        return this.g;
    }

    public Permutation getP2() {
        return this.h;
    }

    public PolynomialGF2mSmallM[] getQInv() {
        return this.j;
    }

    public GF2Matrix getSInv() {
        return this.f;
    }

    public int hashCode() {
        return this.c + this.b + this.d.hashCode() + this.e.hashCode() + this.f.hashCode() + this.g.hashCode() + this.h.hashCode() + this.i.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" length of the code          : ");
        sb.append(this.b);
        sb.append("\n");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(" dimension of the code       : ");
        sb3.append(this.c);
        sb3.append("\n");
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb4);
        sb5.append(" irreducible Goppa polynomial: ");
        sb5.append(this.e);
        sb5.append("\n");
        String sb6 = sb5.toString();
        StringBuilder sb7 = new StringBuilder();
        sb7.append(sb6);
        sb7.append(" (k x k)-matrix S^-1         : ");
        sb7.append(this.f);
        sb7.append("\n");
        String sb8 = sb7.toString();
        StringBuilder sb9 = new StringBuilder();
        sb9.append(sb8);
        sb9.append(" permutation P1              : ");
        sb9.append(this.g);
        sb9.append("\n");
        String sb10 = sb9.toString();
        StringBuilder sb11 = new StringBuilder();
        sb11.append(sb10);
        sb11.append(" permutation P2              : ");
        sb11.append(this.h);
        return sb11.toString();
    }
}
