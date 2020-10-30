package org.bouncycastle.asn1.ess;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERUTF8String;

public class ContentHints extends ASN1Object {
    private DERUTF8String a;
    private ASN1ObjectIdentifier b;

    public ContentHints(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.b = aSN1ObjectIdentifier;
        this.a = null;
    }

    public ContentHints(ASN1ObjectIdentifier aSN1ObjectIdentifier, DERUTF8String dERUTF8String) {
        this.b = aSN1ObjectIdentifier;
        this.a = dERUTF8String;
    }

    private ContentHints(ASN1Sequence aSN1Sequence) {
        int i = 0;
        ASN1Encodable objectAt = aSN1Sequence.getObjectAt(0);
        if (objectAt.toASN1Primitive() instanceof DERUTF8String) {
            this.a = DERUTF8String.getInstance(objectAt);
            i = 1;
        }
        this.b = ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(i));
    }

    public static ContentHints getInstance(Object obj) {
        if (obj instanceof ContentHints) {
            return (ContentHints) obj;
        }
        if (obj != null) {
            return new ContentHints(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public DERUTF8String getContentDescription() {
        return this.a;
    }

    public ASN1ObjectIdentifier getContentType() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(this.a);
        }
        aSN1EncodableVector.add(this.b);
        return new DERSequence(aSN1EncodableVector);
    }
}
