package org.bouncycastle.jcajce.provider.asymmetric.dh;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.DHParameter;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.DHDomainParameters;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;

public class BCDHPublicKey implements DHPublicKey {
    static final long serialVersionUID = -216691575254424324L;
    private BigInteger a;
    private transient DHParameterSpec b;
    private transient SubjectPublicKeyInfo c;

    BCDHPublicKey(BigInteger bigInteger, DHParameterSpec dHParameterSpec) {
        this.a = bigInteger;
        this.b = dHParameterSpec;
    }

    BCDHPublicKey(DHPublicKey dHPublicKey) {
        this.a = dHPublicKey.getY();
        this.b = dHPublicKey.getParams();
    }

    BCDHPublicKey(DHPublicKeySpec dHPublicKeySpec) {
        this.a = dHPublicKeySpec.getY();
        this.b = new DHParameterSpec(dHPublicKeySpec.getP(), dHPublicKeySpec.getG());
    }

    public BCDHPublicKey(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        DHParameterSpec dHParameterSpec;
        this.c = subjectPublicKeyInfo;
        try {
            this.a = ((ASN1Integer) subjectPublicKeyInfo.parsePublicKey()).getValue();
            ASN1Sequence instance = ASN1Sequence.getInstance(subjectPublicKeyInfo.getAlgorithm().getParameters());
            ASN1ObjectIdentifier algorithm = subjectPublicKeyInfo.getAlgorithm().getAlgorithm();
            if (algorithm.equals(PKCSObjectIdentifiers.dhKeyAgreement) || a(instance)) {
                DHParameter instance2 = DHParameter.getInstance(instance);
                dHParameterSpec = instance2.getL() != null ? new DHParameterSpec(instance2.getP(), instance2.getG(), instance2.getL().intValue()) : new DHParameterSpec(instance2.getP(), instance2.getG());
            } else if (algorithm.equals(X9ObjectIdentifiers.dhpublicnumber)) {
                DHDomainParameters instance3 = DHDomainParameters.getInstance(instance);
                dHParameterSpec = new DHParameterSpec(instance3.getP().getValue(), instance3.getG().getValue());
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("unknown algorithm type: ");
                sb.append(algorithm);
                throw new IllegalArgumentException(sb.toString());
            }
            this.b = dHParameterSpec;
        } catch (IOException unused) {
            throw new IllegalArgumentException("invalid info structure in DH public key");
        }
    }

    BCDHPublicKey(DHPublicKeyParameters dHPublicKeyParameters) {
        this.a = dHPublicKeyParameters.getY();
        this.b = new DHParameterSpec(dHPublicKeyParameters.getParameters().getP(), dHPublicKeyParameters.getParameters().getG(), dHPublicKeyParameters.getParameters().getL());
    }

    private boolean a(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() == 2) {
            return true;
        }
        if (aSN1Sequence.size() > 3) {
            return false;
        }
        return ASN1Integer.getInstance(aSN1Sequence.getObjectAt(2)).getValue().compareTo(BigInteger.valueOf((long) ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).getValue().bitLength())) <= 0;
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        this.b = new DHParameterSpec((BigInteger) objectInputStream.readObject(), (BigInteger) objectInputStream.readObject(), objectInputStream.readInt());
        this.c = null;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.b.getP());
        objectOutputStream.writeObject(this.b.getG());
        objectOutputStream.writeInt(this.b.getL());
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
        return "DH";
    }

    public byte[] getEncoded() {
        return this.c != null ? KeyUtil.getEncodedSubjectPublicKeyInfo(this.c) : KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.dhKeyAgreement, new DHParameter(this.b.getP(), this.b.getG(), this.b.getL()).toASN1Primitive()), (ASN1Encodable) new ASN1Integer(this.a));
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

    public int hashCode() {
        return ((getY().hashCode() ^ getParams().getG().hashCode()) ^ getParams().getP().hashCode()) ^ getParams().getL();
    }
}
