package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;

public class AttributeCertificateInfo extends ASN1Object {
    private ASN1Integer a;
    private Holder b;
    private AttCertIssuer c;
    private AlgorithmIdentifier d;
    private ASN1Integer e;
    private AttCertValidityPeriod f;
    private ASN1Sequence g;
    private DERBitString h;
    private Extensions i;

    private AttributeCertificateInfo(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 6 || aSN1Sequence.size() > 9) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        int i2 = 0;
        if (aSN1Sequence.getObjectAt(0) instanceof ASN1Integer) {
            this.a = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
            i2 = 1;
        } else {
            this.a = new ASN1Integer(0);
        }
        this.b = Holder.getInstance(aSN1Sequence.getObjectAt(i2));
        this.c = AttCertIssuer.getInstance(aSN1Sequence.getObjectAt(i2 + 1));
        this.d = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i2 + 2));
        this.e = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(i2 + 3));
        this.f = AttCertValidityPeriod.getInstance(aSN1Sequence.getObjectAt(i2 + 4));
        this.g = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(i2 + 5));
        for (int i3 = i2 + 6; i3 < aSN1Sequence.size(); i3++) {
            ASN1Encodable objectAt = aSN1Sequence.getObjectAt(i3);
            if (objectAt instanceof DERBitString) {
                this.h = DERBitString.getInstance(aSN1Sequence.getObjectAt(i3));
            } else if ((objectAt instanceof ASN1Sequence) || (objectAt instanceof Extensions)) {
                this.i = Extensions.getInstance(aSN1Sequence.getObjectAt(i3));
            }
        }
    }

    public static AttributeCertificateInfo getInstance(Object obj) {
        if (obj instanceof AttributeCertificateInfo) {
            return (AttributeCertificateInfo) obj;
        }
        if (obj != null) {
            return new AttributeCertificateInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static AttributeCertificateInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public AttCertValidityPeriod getAttrCertValidityPeriod() {
        return this.f;
    }

    public ASN1Sequence getAttributes() {
        return this.g;
    }

    public Extensions getExtensions() {
        return this.i;
    }

    public Holder getHolder() {
        return this.b;
    }

    public AttCertIssuer getIssuer() {
        return this.c;
    }

    public DERBitString getIssuerUniqueID() {
        return this.h;
    }

    public ASN1Integer getSerialNumber() {
        return this.e;
    }

    public AlgorithmIdentifier getSignature() {
        return this.d;
    }

    public ASN1Integer getVersion() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a.getValue().intValue() != 0) {
            aSN1EncodableVector.add(this.a);
        }
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(this.d);
        aSN1EncodableVector.add(this.e);
        aSN1EncodableVector.add(this.f);
        aSN1EncodableVector.add(this.g);
        if (this.h != null) {
            aSN1EncodableVector.add(this.h);
        }
        if (this.i != null) {
            aSN1EncodableVector.add(this.i);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
