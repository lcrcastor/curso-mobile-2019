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
import org.bouncycastle.asn1.crmf.CertId;
import org.bouncycastle.asn1.x509.CertificateList;

public class RevRepContent extends ASN1Object {
    private ASN1Sequence a;
    private ASN1Sequence b;
    private ASN1Sequence c;

    private RevRepContent(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = ASN1Sequence.getInstance(objects.nextElement());
        while (objects.hasMoreElements()) {
            ASN1TaggedObject instance = ASN1TaggedObject.getInstance(objects.nextElement());
            if (instance.getTagNo() == 0) {
                this.b = ASN1Sequence.getInstance(instance, true);
            } else {
                this.c = ASN1Sequence.getInstance(instance, true);
            }
        }
    }

    private void a(ASN1EncodableVector aSN1EncodableVector, int i, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, i, aSN1Encodable));
        }
    }

    public static RevRepContent getInstance(Object obj) {
        if (obj instanceof RevRepContent) {
            return (RevRepContent) obj;
        }
        if (obj != null) {
            return new RevRepContent(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CertificateList[] getCrls() {
        if (this.c == null) {
            return null;
        }
        CertificateList[] certificateListArr = new CertificateList[this.c.size()];
        for (int i = 0; i != certificateListArr.length; i++) {
            certificateListArr[i] = CertificateList.getInstance(this.c.getObjectAt(i));
        }
        return certificateListArr;
    }

    public CertId[] getRevCerts() {
        if (this.b == null) {
            return null;
        }
        CertId[] certIdArr = new CertId[this.b.size()];
        for (int i = 0; i != certIdArr.length; i++) {
            certIdArr[i] = CertId.getInstance(this.b.getObjectAt(i));
        }
        return certIdArr;
    }

    public PKIStatusInfo[] getStatus() {
        PKIStatusInfo[] pKIStatusInfoArr = new PKIStatusInfo[this.a.size()];
        for (int i = 0; i != pKIStatusInfoArr.length; i++) {
            pKIStatusInfoArr[i] = PKIStatusInfo.getInstance(this.a.getObjectAt(i));
        }
        return pKIStatusInfoArr;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        a(aSN1EncodableVector, 0, this.b);
        a(aSN1EncodableVector, 1, this.c);
        return new DERSequence(aSN1EncodableVector);
    }
}
