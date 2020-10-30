package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public class POPOSigningKeyInput extends ASN1Object {
    private GeneralName a;
    private PKMACValue b;
    private SubjectPublicKeyInfo c;

    private POPOSigningKeyInput(ASN1Sequence aSN1Sequence) {
        ASN1Encodable objectAt = aSN1Sequence.getObjectAt(0);
        if (objectAt instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) objectAt;
            if (aSN1TaggedObject.getTagNo() != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown authInfo tag: ");
                sb.append(aSN1TaggedObject.getTagNo());
                throw new IllegalArgumentException(sb.toString());
            }
            this.a = GeneralName.getInstance(aSN1TaggedObject.getObject());
        } else {
            this.b = PKMACValue.getInstance(objectAt);
        }
        this.c = SubjectPublicKeyInfo.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public POPOSigningKeyInput(PKMACValue pKMACValue, SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.b = pKMACValue;
        this.c = subjectPublicKeyInfo;
    }

    public POPOSigningKeyInput(GeneralName generalName, SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.a = generalName;
        this.c = subjectPublicKeyInfo;
    }

    public static POPOSigningKeyInput getInstance(Object obj) {
        if (obj instanceof POPOSigningKeyInput) {
            return (POPOSigningKeyInput) obj;
        }
        if (obj != null) {
            return new POPOSigningKeyInput(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public SubjectPublicKeyInfo getPublicKey() {
        return this.c;
    }

    public PKMACValue getPublicKeyMAC() {
        return this.b;
    }

    public GeneralName getSender() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a != null ? new DERTaggedObject(false, 0, this.a) : this.b);
        aSN1EncodableVector.add(this.c);
        return new DERSequence(aSN1EncodableVector);
    }
}
