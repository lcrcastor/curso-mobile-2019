package org.bouncycastle.asn1.pkcs;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.BERTaggedObject;
import org.bouncycastle.asn1.DLSequence;

public class ContentInfo extends ASN1Object implements PKCSObjectIdentifiers {
    private ASN1ObjectIdentifier a;
    private ASN1Encodable b;
    private boolean c = true;

    public ContentInfo(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.a = aSN1ObjectIdentifier;
        this.b = aSN1Encodable;
    }

    private ContentInfo(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = (ASN1ObjectIdentifier) objects.nextElement();
        if (objects.hasMoreElements()) {
            this.b = ((ASN1TaggedObject) objects.nextElement()).getObject();
        }
        this.c = aSN1Sequence instanceof BERSequence;
    }

    public static ContentInfo getInstance(Object obj) {
        if (obj instanceof ContentInfo) {
            return (ContentInfo) obj;
        }
        if (obj != null) {
            return new ContentInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Encodable getContent() {
        return this.b;
    }

    public ASN1ObjectIdentifier getContentType() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.b != null) {
            aSN1EncodableVector.add(new BERTaggedObject(true, 0, this.b));
        }
        return this.c ? new BERSequence(aSN1EncodableVector) : new DLSequence(aSN1EncodableVector);
    }
}
