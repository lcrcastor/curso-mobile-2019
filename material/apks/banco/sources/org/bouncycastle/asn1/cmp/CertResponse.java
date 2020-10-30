package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class CertResponse extends ASN1Object {
    private ASN1Integer a;
    private PKIStatusInfo b;
    private CertifiedKeyPair c;
    private ASN1OctetString d;

    public CertResponse(ASN1Integer aSN1Integer, PKIStatusInfo pKIStatusInfo) {
        this(aSN1Integer, pKIStatusInfo, null, null);
    }

    public CertResponse(ASN1Integer aSN1Integer, PKIStatusInfo pKIStatusInfo, CertifiedKeyPair certifiedKeyPair, ASN1OctetString aSN1OctetString) {
        if (aSN1Integer == null) {
            throw new IllegalArgumentException("'certReqId' cannot be null");
        } else if (pKIStatusInfo == null) {
            throw new IllegalArgumentException("'status' cannot be null");
        } else {
            this.a = aSN1Integer;
            this.b = pKIStatusInfo;
            this.c = certifiedKeyPair;
            this.d = aSN1OctetString;
        }
    }

    private CertResponse(ASN1Sequence aSN1Sequence) {
        ASN1Encodable aSN1Encodable;
        this.a = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = PKIStatusInfo.getInstance(aSN1Sequence.getObjectAt(1));
        if (aSN1Sequence.size() >= 3) {
            if (aSN1Sequence.size() == 3) {
                aSN1Encodable = aSN1Sequence.getObjectAt(2);
                if (!(aSN1Encodable instanceof ASN1OctetString)) {
                    this.c = CertifiedKeyPair.getInstance(aSN1Encodable);
                    return;
                }
            } else {
                this.c = CertifiedKeyPair.getInstance(aSN1Sequence.getObjectAt(2));
                aSN1Encodable = aSN1Sequence.getObjectAt(3);
            }
            this.d = ASN1OctetString.getInstance(aSN1Encodable);
        }
    }

    public static CertResponse getInstance(Object obj) {
        if (obj instanceof CertResponse) {
            return (CertResponse) obj;
        }
        if (obj != null) {
            return new CertResponse(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Integer getCertReqId() {
        return this.a;
    }

    public CertifiedKeyPair getCertifiedKeyPair() {
        return this.c;
    }

    public PKIStatusInfo getStatus() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        if (this.c != null) {
            aSN1EncodableVector.add(this.c);
        }
        if (this.d != null) {
            aSN1EncodableVector.add(this.d);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
