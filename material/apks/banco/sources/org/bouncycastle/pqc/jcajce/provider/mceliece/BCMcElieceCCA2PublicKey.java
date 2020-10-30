package org.bouncycastle.pqc.jcajce.provider.mceliece;

import java.io.IOException;
import java.security.PublicKey;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.pqc.asn1.McElieceCCA2PublicKey;
import org.bouncycastle.pqc.crypto.mceliece.McElieceCCA2Parameters;
import org.bouncycastle.pqc.crypto.mceliece.McElieceCCA2PublicKeyParameters;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2PublicKeySpec;
import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;

public class BCMcElieceCCA2PublicKey implements PublicKey, CipherParameters {
    private static final long serialVersionUID = 1;
    private String a;
    private int b;
    private int c;
    private GF2Matrix d;
    private McElieceCCA2Parameters e;

    public BCMcElieceCCA2PublicKey(String str, int i, int i2, GF2Matrix gF2Matrix) {
        this.a = str;
        this.b = i;
        this.c = i2;
        this.d = gF2Matrix;
    }

    public BCMcElieceCCA2PublicKey(McElieceCCA2PublicKeyParameters mcElieceCCA2PublicKeyParameters) {
        this(mcElieceCCA2PublicKeyParameters.getOIDString(), mcElieceCCA2PublicKeyParameters.getN(), mcElieceCCA2PublicKeyParameters.getT(), mcElieceCCA2PublicKeyParameters.getMatrixG());
        this.e = mcElieceCCA2PublicKeyParameters.getParameters();
    }

    public BCMcElieceCCA2PublicKey(McElieceCCA2PublicKeySpec mcElieceCCA2PublicKeySpec) {
        this(mcElieceCCA2PublicKeySpec.getOIDString(), mcElieceCCA2PublicKeySpec.getN(), mcElieceCCA2PublicKeySpec.getT(), mcElieceCCA2PublicKeySpec.getMatrixG());
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj != null) {
            if (!(obj instanceof BCMcElieceCCA2PublicKey)) {
                return false;
            }
            BCMcElieceCCA2PublicKey bCMcElieceCCA2PublicKey = (BCMcElieceCCA2PublicKey) obj;
            if (this.b == bCMcElieceCCA2PublicKey.b && this.c == bCMcElieceCCA2PublicKey.c && this.d.equals(bCMcElieceCCA2PublicKey.d)) {
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
        try {
            return new SubjectPublicKeyInfo(new AlgorithmIdentifier(getOID(), DERNull.INSTANCE), (ASN1Encodable) new McElieceCCA2PublicKey(new ASN1ObjectIdentifier(this.a), this.b, this.c, this.d)).getEncoded();
        } catch (IOException unused) {
            return null;
        }
    }

    public String getFormat() {
        return null;
    }

    public GF2Matrix getG() {
        return this.d;
    }

    public int getK() {
        return this.d.getNumRows();
    }

    public McElieceCCA2Parameters getMcElieceCCA2Parameters() {
        return this.e;
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

    public int getT() {
        return this.c;
    }

    public int hashCode() {
        return this.b + this.c + this.d.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("McEliecePublicKey:\n");
        sb.append(" length of the code         : ");
        sb.append(this.b);
        sb.append("\n");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(" error correction capability: ");
        sb3.append(this.c);
        sb3.append("\n");
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb4);
        sb5.append(" generator matrix           : ");
        sb5.append(this.d.toString());
        return sb5.toString();
    }
}
