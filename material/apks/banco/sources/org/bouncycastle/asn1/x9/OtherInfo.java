package org.bouncycastle.asn1.x9;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class OtherInfo extends ASN1Object {
    private KeySpecificInfo a;
    private ASN1OctetString b;
    private ASN1OctetString c;

    public OtherInfo(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = new KeySpecificInfo((ASN1Sequence) objects.nextElement());
        while (objects.hasMoreElements()) {
            DERTaggedObject dERTaggedObject = (DERTaggedObject) objects.nextElement();
            if (dERTaggedObject.getTagNo() == 0) {
                this.b = (ASN1OctetString) dERTaggedObject.getObject();
            } else if (dERTaggedObject.getTagNo() == 2) {
                this.c = (ASN1OctetString) dERTaggedObject.getObject();
            }
        }
    }

    public OtherInfo(KeySpecificInfo keySpecificInfo, ASN1OctetString aSN1OctetString, ASN1OctetString aSN1OctetString2) {
        this.a = keySpecificInfo;
        this.b = aSN1OctetString;
        this.c = aSN1OctetString2;
    }

    public KeySpecificInfo getKeyInfo() {
        return this.a;
    }

    public ASN1OctetString getPartyAInfo() {
        return this.b;
    }

    public ASN1OctetString getSuppPubInfo() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.b != null) {
            aSN1EncodableVector.add(new DERTaggedObject(0, this.b));
        }
        aSN1EncodableVector.add(new DERTaggedObject(2, this.c));
        return new DERSequence(aSN1EncodableVector);
    }
}
