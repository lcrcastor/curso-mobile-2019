package org.bouncycastle.jcajce.provider.asymmetric.dsa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPublicKeySpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DSAParameter;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;

public class BCDSAPublicKey implements DSAPublicKey {
    private static final long serialVersionUID = 1752452449903495175L;
    private BigInteger a;
    private transient DSAParams b;

    BCDSAPublicKey(DSAPublicKey dSAPublicKey) {
        this.a = dSAPublicKey.getY();
        this.b = dSAPublicKey.getParams();
    }

    BCDSAPublicKey(DSAPublicKeySpec dSAPublicKeySpec) {
        this.a = dSAPublicKeySpec.getY();
        this.b = new DSAParameterSpec(dSAPublicKeySpec.getP(), dSAPublicKeySpec.getQ(), dSAPublicKeySpec.getG());
    }

    public BCDSAPublicKey(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        try {
            this.a = ((ASN1Integer) subjectPublicKeyInfo.parsePublicKey()).getValue();
            if (a(subjectPublicKeyInfo.getAlgorithm().getParameters())) {
                DSAParameter instance = DSAParameter.getInstance(subjectPublicKeyInfo.getAlgorithm().getParameters());
                this.b = new DSAParameterSpec(instance.getP(), instance.getQ(), instance.getG());
            }
        } catch (IOException unused) {
            throw new IllegalArgumentException("invalid info structure in DSA public key");
        }
    }

    BCDSAPublicKey(DSAPublicKeyParameters dSAPublicKeyParameters) {
        this.a = dSAPublicKeyParameters.getY();
        this.b = new DSAParameterSpec(dSAPublicKeyParameters.getParameters().getP(), dSAPublicKeyParameters.getParameters().getQ(), dSAPublicKeyParameters.getParameters().getG());
    }

    private boolean a(ASN1Encodable aSN1Encodable) {
        return aSN1Encodable != null && !DERNull.INSTANCE.equals(aSN1Encodable.toASN1Primitive());
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        this.b = new DSAParameterSpec((BigInteger) objectInputStream.readObject(), (BigInteger) objectInputStream.readObject(), (BigInteger) objectInputStream.readObject());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.b.getP());
        objectOutputStream.writeObject(this.b.getQ());
        objectOutputStream.writeObject(this.b.getG());
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof DSAPublicKey)) {
            return false;
        }
        DSAPublicKey dSAPublicKey = (DSAPublicKey) obj;
        if (getY().equals(dSAPublicKey.getY()) && getParams().getG().equals(dSAPublicKey.getParams().getG()) && getParams().getP().equals(dSAPublicKey.getParams().getP()) && getParams().getQ().equals(dSAPublicKey.getParams().getQ())) {
            z = true;
        }
        return z;
    }

    public String getAlgorithm() {
        return "DSA";
    }

    public byte[] getEncoded() {
        AlgorithmIdentifier algorithmIdentifier;
        ASN1Integer aSN1Integer;
        if (this.b == null) {
            algorithmIdentifier = new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa);
            aSN1Integer = new ASN1Integer(this.a);
        } else {
            algorithmIdentifier = new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa, new DSAParameter(this.b.getP(), this.b.getQ(), this.b.getG()).toASN1Primitive());
            aSN1Integer = new ASN1Integer(this.a);
        }
        return KeyUtil.getEncodedSubjectPublicKeyInfo(algorithmIdentifier, (ASN1Encodable) aSN1Integer);
    }

    public String getFormat() {
        return "X.509";
    }

    public DSAParams getParams() {
        return this.b;
    }

    public BigInteger getY() {
        return this.a;
    }

    public int hashCode() {
        return ((getY().hashCode() ^ getParams().getG().hashCode()) ^ getParams().getP().hashCode()) ^ getParams().getQ().hashCode();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String property = System.getProperty("line.separator");
        stringBuffer.append("DSA Public Key");
        stringBuffer.append(property);
        stringBuffer.append("            y: ");
        stringBuffer.append(getY().toString(16));
        stringBuffer.append(property);
        return stringBuffer.toString();
    }
}
