package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class Holder extends ASN1Object {
    public static final int V1_CERTIFICATE_HOLDER = 0;
    public static final int V2_CERTIFICATE_HOLDER = 1;
    IssuerSerial a;
    GeneralNames b;
    ObjectDigestInfo c;
    private int d;

    private Holder(ASN1Sequence aSN1Sequence) {
        this.d = 1;
        if (aSN1Sequence.size() > 3) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            ASN1TaggedObject instance = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i));
            switch (instance.getTagNo()) {
                case 0:
                    this.a = IssuerSerial.getInstance(instance, false);
                    break;
                case 1:
                    this.b = GeneralNames.getInstance(instance, false);
                    break;
                case 2:
                    this.c = ObjectDigestInfo.getInstance(instance, false);
                    break;
                default:
                    throw new IllegalArgumentException("unknown tag in Holder");
            }
        }
        this.d = 1;
    }

    private Holder(ASN1TaggedObject aSN1TaggedObject) {
        this.d = 1;
        switch (aSN1TaggedObject.getTagNo()) {
            case 0:
                this.a = IssuerSerial.getInstance(aSN1TaggedObject, true);
                break;
            case 1:
                this.b = GeneralNames.getInstance(aSN1TaggedObject, true);
                break;
            default:
                throw new IllegalArgumentException("unknown tag in Holder");
        }
        this.d = 0;
    }

    public Holder(GeneralNames generalNames) {
        this(generalNames, 1);
    }

    public Holder(GeneralNames generalNames, int i) {
        this.d = 1;
        this.b = generalNames;
        this.d = i;
    }

    public Holder(IssuerSerial issuerSerial) {
        this(issuerSerial, 1);
    }

    public Holder(IssuerSerial issuerSerial, int i) {
        this.d = 1;
        this.a = issuerSerial;
        this.d = i;
    }

    public Holder(ObjectDigestInfo objectDigestInfo) {
        this.d = 1;
        this.c = objectDigestInfo;
    }

    public static Holder getInstance(Object obj) {
        if (obj instanceof Holder) {
            return (Holder) obj;
        }
        if (obj instanceof ASN1TaggedObject) {
            return new Holder(ASN1TaggedObject.getInstance(obj));
        }
        if (obj != null) {
            return new Holder(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public IssuerSerial getBaseCertificateID() {
        return this.a;
    }

    public GeneralNames getEntityName() {
        return this.b;
    }

    public ObjectDigestInfo getObjectDigestInfo() {
        return this.c;
    }

    public int getVersion() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        if (this.d != 1) {
            return this.b != null ? new DERTaggedObject(true, 1, this.b) : new DERTaggedObject(true, 0, this.a);
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.a));
        }
        if (this.b != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, this.b));
        }
        if (this.c != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 2, this.c));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
