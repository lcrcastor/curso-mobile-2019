package org.bouncycastle.pqc.jcajce.provider.gmss;

import java.security.PublicKey;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.pqc.asn1.GMSSPublicKey;
import org.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.bouncycastle.pqc.asn1.ParSet;
import org.bouncycastle.pqc.crypto.gmss.GMSSParameters;
import org.bouncycastle.pqc.crypto.gmss.GMSSPublicKeyParameters;
import org.bouncycastle.pqc.jcajce.provider.util.KeyUtil;
import org.bouncycastle.pqc.jcajce.spec.GMSSPublicKeySpec;
import org.bouncycastle.util.encoders.Hex;

public class BCGMSSPublicKey implements PublicKey, CipherParameters {
    private static final long serialVersionUID = 1;
    private byte[] a;
    private GMSSParameters b;

    public BCGMSSPublicKey(GMSSPublicKeyParameters gMSSPublicKeyParameters) {
        this(gMSSPublicKeyParameters.getPublicKey(), gMSSPublicKeyParameters.getParameters());
    }

    protected BCGMSSPublicKey(GMSSPublicKeySpec gMSSPublicKeySpec) {
        this(gMSSPublicKeySpec.getPublicKey(), gMSSPublicKeySpec.getParameters());
    }

    public BCGMSSPublicKey(byte[] bArr, GMSSParameters gMSSParameters) {
        this.b = gMSSParameters;
        this.a = bArr;
    }

    public String getAlgorithm() {
        return "GMSS";
    }

    public byte[] getEncoded() {
        return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.gmss, new ParSet(this.b.getNumOfLayers(), this.b.getHeightOfTrees(), this.b.getWinternitzParameter(), this.b.getK()).toASN1Primitive()), (ASN1Encodable) new GMSSPublicKey(this.a));
    }

    public String getFormat() {
        return "X.509";
    }

    public GMSSParameters getParameterSet() {
        return this.b;
    }

    public byte[] getPublicKeyBytes() {
        return this.a;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GMSS public key : ");
        sb.append(new String(Hex.encode(this.a)));
        sb.append("\n");
        sb.append("Height of Trees: \n");
        String sb2 = sb.toString();
        for (int i = 0; i < this.b.getHeightOfTrees().length; i++) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append("Layer ");
            sb3.append(i);
            sb3.append(" : ");
            sb3.append(this.b.getHeightOfTrees()[i]);
            sb3.append(" WinternitzParameter: ");
            sb3.append(this.b.getWinternitzParameter()[i]);
            sb3.append(" K: ");
            sb3.append(this.b.getK()[i]);
            sb3.append("\n");
            sb2 = sb3.toString();
        }
        return sb2;
    }
}
