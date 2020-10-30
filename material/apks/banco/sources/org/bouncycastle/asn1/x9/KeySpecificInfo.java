package org.bouncycastle.asn1.x9;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class KeySpecificInfo extends ASN1Object {
    private ASN1ObjectIdentifier a;
    private ASN1OctetString b;

    public KeySpecificInfo(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1OctetString aSN1OctetString) {
        this.a = aSN1ObjectIdentifier;
        this.b = aSN1OctetString;
    }

    public KeySpecificInfo(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = (ASN1ObjectIdentifier) objects.nextElement();
        this.b = (ASN1OctetString) objects.nextElement();
    }

    public ASN1ObjectIdentifier getAlgorithm() {
        return this.a;
    }

    public ASN1OctetString getCounter() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        return new DERSequence(aSN1EncodableVector);
    }
}
