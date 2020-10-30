package org.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.spec.DHParameterSpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.pkcs.DHParameter;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;

public class JCEDHPrivateKey implements DHPrivateKey, PKCS12BagAttributeCarrier {
    static final long serialVersionUID = 311058815616901812L;
    BigInteger a;
    private DHParameterSpec b;
    private PrivateKeyInfo c;
    private PKCS12BagAttributeCarrier d = new PKCS12BagAttributeCarrierImpl();

    protected JCEDHPrivateKey() {
    }

    private void readObject(ObjectInputStream objectInputStream) {
        this.a = (BigInteger) objectInputStream.readObject();
        this.b = new DHParameterSpec((BigInteger) objectInputStream.readObject(), (BigInteger) objectInputStream.readObject(), objectInputStream.readInt());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.writeObject(getX());
        objectOutputStream.writeObject(this.b.getP());
        objectOutputStream.writeObject(this.b.getG());
        objectOutputStream.writeInt(this.b.getL());
    }

    public String getAlgorithm() {
        return "DH";
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.d.getBagAttribute(aSN1ObjectIdentifier);
    }

    public Enumeration getBagAttributeKeys() {
        return this.d.getBagAttributeKeys();
    }

    public byte[] getEncoded() {
        try {
            return this.c != null ? this.c.getEncoded(ASN1Encoding.DER) : new PrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.dhKeyAgreement, new DHParameter(this.b.getP(), this.b.getG(), this.b.getL())), new ASN1Integer(getX())).getEncoded(ASN1Encoding.DER);
        } catch (IOException unused) {
            return null;
        }
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public DHParameterSpec getParams() {
        return this.b;
    }

    public BigInteger getX() {
        return this.a;
    }

    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.d.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }
}
