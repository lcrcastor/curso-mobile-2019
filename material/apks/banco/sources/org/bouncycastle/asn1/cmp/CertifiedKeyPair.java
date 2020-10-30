package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.crmf.EncryptedValue;
import org.bouncycastle.asn1.crmf.PKIPublicationInfo;

public class CertifiedKeyPair extends ASN1Object {
    private CertOrEncCert a;
    private EncryptedValue b;
    private PKIPublicationInfo c;

    private CertifiedKeyPair(ASN1Sequence aSN1Sequence) {
        Object obj;
        this.a = CertOrEncCert.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() >= 2) {
            if (aSN1Sequence.size() == 2) {
                ASN1TaggedObject instance = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(1));
                if (instance.getTagNo() == 0) {
                    this.b = EncryptedValue.getInstance(instance.getObject());
                    return;
                }
                obj = instance.getObject();
            } else {
                this.b = EncryptedValue.getInstance(ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(1)));
                obj = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(2));
            }
            this.c = PKIPublicationInfo.getInstance(obj);
        }
    }

    public CertifiedKeyPair(CertOrEncCert certOrEncCert) {
        this(certOrEncCert, null, null);
    }

    public CertifiedKeyPair(CertOrEncCert certOrEncCert, EncryptedValue encryptedValue, PKIPublicationInfo pKIPublicationInfo) {
        if (certOrEncCert == null) {
            throw new IllegalArgumentException("'certOrEncCert' cannot be null");
        }
        this.a = certOrEncCert;
        this.b = encryptedValue;
        this.c = pKIPublicationInfo;
    }

    public static CertifiedKeyPair getInstance(Object obj) {
        if (obj instanceof CertifiedKeyPair) {
            return (CertifiedKeyPair) obj;
        }
        if (obj != null) {
            return new CertifiedKeyPair(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CertOrEncCert getCertOrEncCert() {
        return this.a;
    }

    public EncryptedValue getPrivateKey() {
        return this.b;
    }

    public PKIPublicationInfo getPublicationInfo() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.b != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.b));
        }
        if (this.c != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, this.c));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
