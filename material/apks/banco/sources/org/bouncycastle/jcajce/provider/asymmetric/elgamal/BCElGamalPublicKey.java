package org.bouncycastle.jcajce.provider.asymmetric.elgamal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.oiw.ElGamalParameter;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.ElGamalPublicKeyParameters;
import org.bouncycastle.jce.interfaces.ElGamalPublicKey;
import org.bouncycastle.jce.spec.ElGamalParameterSpec;
import org.bouncycastle.jce.spec.ElGamalPublicKeySpec;

public class BCElGamalPublicKey implements DHPublicKey, ElGamalPublicKey {
    static final long serialVersionUID = 8712728417091216948L;
    private BigInteger a;
    private transient ElGamalParameterSpec b;

    BCElGamalPublicKey(DHPublicKey dHPublicKey) {
        this.a = dHPublicKey.getY();
        this.b = new ElGamalParameterSpec(dHPublicKey.getParams().getP(), dHPublicKey.getParams().getG());
    }

    BCElGamalPublicKey(DHPublicKeySpec dHPublicKeySpec) {
        this.a = dHPublicKeySpec.getY();
        this.b = new ElGamalParameterSpec(dHPublicKeySpec.getP(), dHPublicKeySpec.getG());
    }

    BCElGamalPublicKey(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        ElGamalParameter instance = ElGamalParameter.getInstance(subjectPublicKeyInfo.getAlgorithm().getParameters());
        try {
            this.a = ((ASN1Integer) subjectPublicKeyInfo.parsePublicKey()).getValue();
            this.b = new ElGamalParameterSpec(instance.getP(), instance.getG());
        } catch (IOException unused) {
            throw new IllegalArgumentException("invalid info structure in DSA public key");
        }
    }

    BCElGamalPublicKey(ElGamalPublicKeyParameters elGamalPublicKeyParameters) {
        this.a = elGamalPublicKeyParameters.getY();
        this.b = new ElGamalParameterSpec(elGamalPublicKeyParameters.getParameters().getP(), elGamalPublicKeyParameters.getParameters().getG());
    }

    BCElGamalPublicKey(ElGamalPublicKey elGamalPublicKey) {
        this.a = elGamalPublicKey.getY();
        this.b = elGamalPublicKey.getParameters();
    }

    BCElGamalPublicKey(ElGamalPublicKeySpec elGamalPublicKeySpec) {
        this.a = elGamalPublicKeySpec.getY();
        this.b = new ElGamalParameterSpec(elGamalPublicKeySpec.getParams().getP(), elGamalPublicKeySpec.getParams().getG());
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        this.b = new ElGamalParameterSpec((BigInteger) objectInputStream.readObject(), (BigInteger) objectInputStream.readObject());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.b.getP());
        objectOutputStream.writeObject(this.b.getG());
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof DHPublicKey)) {
            return false;
        }
        DHPublicKey dHPublicKey = (DHPublicKey) obj;
        if (getY().equals(dHPublicKey.getY()) && getParams().getG().equals(dHPublicKey.getParams().getG()) && getParams().getP().equals(dHPublicKey.getParams().getP()) && getParams().getL() == dHPublicKey.getParams().getL()) {
            z = true;
        }
        return z;
    }

    public String getAlgorithm() {
        return "ElGamal";
    }

    public byte[] getEncoded() {
        try {
            return new SubjectPublicKeyInfo(new AlgorithmIdentifier(OIWObjectIdentifiers.elGamalAlgorithm, new ElGamalParameter(this.b.getP(), this.b.getG())), (ASN1Encodable) new ASN1Integer(this.a)).getEncoded(ASN1Encoding.DER);
        } catch (IOException unused) {
            return null;
        }
    }

    public String getFormat() {
        return "X.509";
    }

    public ElGamalParameterSpec getParameters() {
        return this.b;
    }

    public DHParameterSpec getParams() {
        return new DHParameterSpec(this.b.getP(), this.b.getG());
    }

    public BigInteger getY() {
        return this.a;
    }

    public int hashCode() {
        return ((getY().hashCode() ^ getParams().getG().hashCode()) ^ getParams().getP().hashCode()) ^ getParams().getL();
    }
}
