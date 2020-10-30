package org.bouncycastle.asn1.pkcs;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.DLSequence;

public class AuthenticatedSafe extends ASN1Object {
    private ContentInfo[] a;
    private boolean b = true;

    private AuthenticatedSafe(ASN1Sequence aSN1Sequence) {
        this.a = new ContentInfo[aSN1Sequence.size()];
        for (int i = 0; i != this.a.length; i++) {
            this.a[i] = ContentInfo.getInstance(aSN1Sequence.getObjectAt(i));
        }
        this.b = aSN1Sequence instanceof BERSequence;
    }

    public AuthenticatedSafe(ContentInfo[] contentInfoArr) {
        this.a = contentInfoArr;
    }

    public static AuthenticatedSafe getInstance(Object obj) {
        if (obj instanceof AuthenticatedSafe) {
            return (AuthenticatedSafe) obj;
        }
        if (obj != null) {
            return new AuthenticatedSafe(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ContentInfo[] getContentInfo() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i = 0; i != this.a.length; i++) {
            aSN1EncodableVector.add(this.a[i]);
        }
        return this.b ? new BERSequence(aSN1EncodableVector) : new DLSequence(aSN1EncodableVector);
    }
}
