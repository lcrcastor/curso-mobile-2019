package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class V2Form extends ASN1Object {
    GeneralNames a;
    IssuerSerial b;
    ObjectDigestInfo c;

    public V2Form(ASN1Sequence aSN1Sequence) {
        int i;
        if (aSN1Sequence.size() > 3) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        if (!(aSN1Sequence.getObjectAt(0) instanceof ASN1TaggedObject)) {
            this.a = GeneralNames.getInstance(aSN1Sequence.getObjectAt(0));
            i = 1;
        } else {
            i = 0;
        }
        while (i != aSN1Sequence.size()) {
            ASN1TaggedObject instance = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i));
            if (instance.getTagNo() == 0) {
                this.b = IssuerSerial.getInstance(instance, false);
            } else if (instance.getTagNo() == 1) {
                this.c = ObjectDigestInfo.getInstance(instance, false);
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Bad tag number: ");
                sb2.append(instance.getTagNo());
                throw new IllegalArgumentException(sb2.toString());
            }
            i++;
        }
    }

    public V2Form(GeneralNames generalNames) {
        this(generalNames, null, null);
    }

    public V2Form(GeneralNames generalNames, IssuerSerial issuerSerial) {
        this(generalNames, issuerSerial, null);
    }

    public V2Form(GeneralNames generalNames, IssuerSerial issuerSerial, ObjectDigestInfo objectDigestInfo) {
        this.a = generalNames;
        this.b = issuerSerial;
        this.c = objectDigestInfo;
    }

    public V2Form(GeneralNames generalNames, ObjectDigestInfo objectDigestInfo) {
        this(generalNames, null, objectDigestInfo);
    }

    public static V2Form getInstance(Object obj) {
        if (obj instanceof V2Form) {
            return (V2Form) obj;
        }
        if (obj != null) {
            return new V2Form(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static V2Form getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public IssuerSerial getBaseCertificateID() {
        return this.b;
    }

    public GeneralNames getIssuerName() {
        return this.a;
    }

    public ObjectDigestInfo getObjectDigestInfo() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(this.a);
        }
        if (this.b != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.b));
        }
        if (this.c != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, this.c));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
