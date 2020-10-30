package org.bouncycastle.asn1.cmp;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;

public class PKIStatusInfo extends ASN1Object {
    ASN1Integer a;
    PKIFreeText b;
    DERBitString c;

    private PKIStatusInfo(ASN1Sequence aSN1Sequence) {
        ASN1Encodable objectAt;
        this.a = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = null;
        this.c = null;
        if (aSN1Sequence.size() > 2) {
            this.b = PKIFreeText.getInstance(aSN1Sequence.getObjectAt(1));
            objectAt = aSN1Sequence.getObjectAt(2);
        } else {
            if (aSN1Sequence.size() > 1) {
                objectAt = aSN1Sequence.getObjectAt(1);
                if (!(objectAt instanceof DERBitString)) {
                    this.b = PKIFreeText.getInstance(objectAt);
                }
            }
            return;
        }
        this.c = DERBitString.getInstance(objectAt);
    }

    public PKIStatusInfo(PKIStatus pKIStatus) {
        this.a = ASN1Integer.getInstance(pKIStatus.toASN1Primitive());
    }

    public PKIStatusInfo(PKIStatus pKIStatus, PKIFreeText pKIFreeText) {
        this.a = ASN1Integer.getInstance(pKIStatus.toASN1Primitive());
        this.b = pKIFreeText;
    }

    public PKIStatusInfo(PKIStatus pKIStatus, PKIFreeText pKIFreeText, PKIFailureInfo pKIFailureInfo) {
        this.a = ASN1Integer.getInstance(pKIStatus.toASN1Primitive());
        this.b = pKIFreeText;
        this.c = pKIFailureInfo;
    }

    public static PKIStatusInfo getInstance(Object obj) {
        if (obj instanceof PKIStatusInfo) {
            return (PKIStatusInfo) obj;
        }
        if (obj != null) {
            return new PKIStatusInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static PKIStatusInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public DERBitString getFailInfo() {
        return this.c;
    }

    public BigInteger getStatus() {
        return this.a.getValue();
    }

    public PKIFreeText getStatusString() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.b != null) {
            aSN1EncodableVector.add(this.b);
        }
        if (this.c != null) {
            aSN1EncodableVector.add(this.c);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
