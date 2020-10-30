package org.bouncycastle.jcajce.provider.asymmetric.rsa;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;

public class BCRSAPublicKey implements RSAPublicKey {
    private static final AlgorithmIdentifier a = new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE);
    static final long serialVersionUID = 2675817738516720772L;
    private BigInteger b;
    private BigInteger c;
    private transient AlgorithmIdentifier d;

    BCRSAPublicKey(RSAPublicKey rSAPublicKey) {
        this.d = a;
        this.b = rSAPublicKey.getModulus();
        this.c = rSAPublicKey.getPublicExponent();
    }

    BCRSAPublicKey(RSAPublicKeySpec rSAPublicKeySpec) {
        this.d = a;
        this.b = rSAPublicKeySpec.getModulus();
        this.c = rSAPublicKeySpec.getPublicExponent();
    }

    BCRSAPublicKey(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        a(subjectPublicKeyInfo);
    }

    BCRSAPublicKey(RSAKeyParameters rSAKeyParameters) {
        this.d = a;
        this.b = rSAKeyParameters.getModulus();
        this.c = rSAKeyParameters.getExponent();
    }

    private void a(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        try {
            org.bouncycastle.asn1.pkcs.RSAPublicKey instance = org.bouncycastle.asn1.pkcs.RSAPublicKey.getInstance(subjectPublicKeyInfo.parsePublicKey());
            this.d = subjectPublicKeyInfo.getAlgorithm();
            this.b = instance.getModulus();
            this.c = instance.getPublicExponent();
        } catch (IOException unused) {
            throw new IllegalArgumentException("invalid info structure in RSA public key");
        }
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        try {
            this.d = AlgorithmIdentifier.getInstance(objectInputStream.readObject());
        } catch (EOFException | OptionalDataException unused) {
            this.d = a;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        if (!this.d.equals(a)) {
            objectOutputStream.writeObject(this.d.getEncoded());
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RSAPublicKey)) {
            return false;
        }
        RSAPublicKey rSAPublicKey = (RSAPublicKey) obj;
        return getModulus().equals(rSAPublicKey.getModulus()) && getPublicExponent().equals(rSAPublicKey.getPublicExponent());
    }

    public String getAlgorithm() {
        return "RSA";
    }

    public byte[] getEncoded() {
        return KeyUtil.getEncodedSubjectPublicKeyInfo(this.d, (ASN1Encodable) new org.bouncycastle.asn1.pkcs.RSAPublicKey(getModulus(), getPublicExponent()));
    }

    public String getFormat() {
        return "X.509";
    }

    public BigInteger getModulus() {
        return this.b;
    }

    public BigInteger getPublicExponent() {
        return this.c;
    }

    public int hashCode() {
        return getModulus().hashCode() ^ getPublicExponent().hashCode();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String property = System.getProperty("line.separator");
        stringBuffer.append("RSA Public Key");
        stringBuffer.append(property);
        stringBuffer.append("            modulus: ");
        stringBuffer.append(getModulus().toString(16));
        stringBuffer.append(property);
        stringBuffer.append("    public exponent: ");
        stringBuffer.append(getPublicExponent().toString(16));
        stringBuffer.append(property);
        return stringBuffer.toString();
    }
}
