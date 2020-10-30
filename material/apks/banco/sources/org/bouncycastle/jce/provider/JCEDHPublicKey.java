package org.bouncycastle.jce.provider;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.pkcs.DHParameter;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;

public class JCEDHPublicKey implements DHPublicKey {
    static final long serialVersionUID = -216691575254424324L;
    private BigInteger a;
    private DHParameterSpec b;
    private SubjectPublicKeyInfo c;

    private void readObject(ObjectInputStream objectInputStream) {
        this.a = (BigInteger) objectInputStream.readObject();
        this.b = new DHParameterSpec((BigInteger) objectInputStream.readObject(), (BigInteger) objectInputStream.readObject(), objectInputStream.readInt());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.writeObject(getY());
        objectOutputStream.writeObject(this.b.getP());
        objectOutputStream.writeObject(this.b.getG());
        objectOutputStream.writeInt(this.b.getL());
    }

    public String getAlgorithm() {
        return "DH";
    }

    public byte[] getEncoded() {
        return this.c != null ? KeyUtil.getEncodedSubjectPublicKeyInfo(this.c) : KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.dhKeyAgreement, new DHParameter(this.b.getP(), this.b.getG(), this.b.getL())), (ASN1Encodable) new ASN1Integer(this.a));
    }

    public String getFormat() {
        return "X.509";
    }

    public DHParameterSpec getParams() {
        return this.b;
    }

    public BigInteger getY() {
        return this.a;
    }
}
