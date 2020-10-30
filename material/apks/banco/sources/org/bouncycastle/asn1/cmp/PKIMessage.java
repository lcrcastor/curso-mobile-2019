package org.bouncycastle.asn1.cmp;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class PKIMessage extends ASN1Object {
    private PKIHeader a;
    private PKIBody b;
    private DERBitString c;
    private ASN1Sequence d;

    private PKIMessage(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = PKIHeader.getInstance(objects.nextElement());
        this.b = PKIBody.getInstance(objects.nextElement());
        while (objects.hasMoreElements()) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) objects.nextElement();
            if (aSN1TaggedObject.getTagNo() == 0) {
                this.c = DERBitString.getInstance(aSN1TaggedObject, true);
            } else {
                this.d = ASN1Sequence.getInstance(aSN1TaggedObject, true);
            }
        }
    }

    public PKIMessage(PKIHeader pKIHeader, PKIBody pKIBody) {
        this(pKIHeader, pKIBody, null, null);
    }

    public PKIMessage(PKIHeader pKIHeader, PKIBody pKIBody, DERBitString dERBitString) {
        this(pKIHeader, pKIBody, dERBitString, null);
    }

    public PKIMessage(PKIHeader pKIHeader, PKIBody pKIBody, DERBitString dERBitString, CMPCertificate[] cMPCertificateArr) {
        this.a = pKIHeader;
        this.b = pKIBody;
        this.c = dERBitString;
        if (cMPCertificateArr != null) {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            for (CMPCertificate add : cMPCertificateArr) {
                aSN1EncodableVector.add(add);
            }
            this.d = new DERSequence(aSN1EncodableVector);
        }
    }

    private void a(ASN1EncodableVector aSN1EncodableVector, int i, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, i, aSN1Encodable));
        }
    }

    public static PKIMessage getInstance(Object obj) {
        if (obj instanceof PKIMessage) {
            return (PKIMessage) obj;
        }
        if (obj != null) {
            return new PKIMessage(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public PKIBody getBody() {
        return this.b;
    }

    public CMPCertificate[] getExtraCerts() {
        if (this.d == null) {
            return null;
        }
        CMPCertificate[] cMPCertificateArr = new CMPCertificate[this.d.size()];
        for (int i = 0; i < cMPCertificateArr.length; i++) {
            cMPCertificateArr[i] = CMPCertificate.getInstance(this.d.getObjectAt(i));
        }
        return cMPCertificateArr;
    }

    public PKIHeader getHeader() {
        return this.a;
    }

    public DERBitString getProtection() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        a(aSN1EncodableVector, 0, this.c);
        a(aSN1EncodableVector, 1, this.d);
        return new DERSequence(aSN1EncodableVector);
    }
}
