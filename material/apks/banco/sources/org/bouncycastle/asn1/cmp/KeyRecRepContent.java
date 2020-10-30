package org.bouncycastle.asn1.cmp;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class KeyRecRepContent extends ASN1Object {
    private PKIStatusInfo a;
    private CMPCertificate b;
    private ASN1Sequence c;
    private ASN1Sequence d;

    private KeyRecRepContent(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = PKIStatusInfo.getInstance(objects.nextElement());
        while (objects.hasMoreElements()) {
            ASN1TaggedObject instance = ASN1TaggedObject.getInstance(objects.nextElement());
            switch (instance.getTagNo()) {
                case 0:
                    this.b = CMPCertificate.getInstance(instance.getObject());
                    break;
                case 1:
                    this.c = ASN1Sequence.getInstance(instance.getObject());
                    break;
                case 2:
                    this.d = ASN1Sequence.getInstance(instance.getObject());
                    break;
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("unknown tag number: ");
                    sb.append(instance.getTagNo());
                    throw new IllegalArgumentException(sb.toString());
            }
        }
    }

    private void a(ASN1EncodableVector aSN1EncodableVector, int i, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, i, aSN1Encodable));
        }
    }

    public static KeyRecRepContent getInstance(Object obj) {
        if (obj instanceof KeyRecRepContent) {
            return (KeyRecRepContent) obj;
        }
        if (obj != null) {
            return new KeyRecRepContent(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CMPCertificate[] getCaCerts() {
        if (this.c == null) {
            return null;
        }
        CMPCertificate[] cMPCertificateArr = new CMPCertificate[this.c.size()];
        for (int i = 0; i != cMPCertificateArr.length; i++) {
            cMPCertificateArr[i] = CMPCertificate.getInstance(this.c.getObjectAt(i));
        }
        return cMPCertificateArr;
    }

    public CertifiedKeyPair[] getKeyPairHist() {
        if (this.d == null) {
            return null;
        }
        CertifiedKeyPair[] certifiedKeyPairArr = new CertifiedKeyPair[this.d.size()];
        for (int i = 0; i != certifiedKeyPairArr.length; i++) {
            certifiedKeyPairArr[i] = CertifiedKeyPair.getInstance(this.d.getObjectAt(i));
        }
        return certifiedKeyPairArr;
    }

    public CMPCertificate getNewSigCert() {
        return this.b;
    }

    public PKIStatusInfo getStatus() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        a(aSN1EncodableVector, 0, this.b);
        a(aSN1EncodableVector, 1, this.c);
        a(aSN1EncodableVector, 2, this.d);
        return new DERSequence(aSN1EncodableVector);
    }
}
