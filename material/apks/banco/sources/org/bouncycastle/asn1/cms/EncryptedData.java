package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.BERTaggedObject;

public class EncryptedData extends ASN1Object {
    private ASN1Integer a;
    private EncryptedContentInfo b;
    private ASN1Set c;

    private EncryptedData(ASN1Sequence aSN1Sequence) {
        this.a = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = EncryptedContentInfo.getInstance(aSN1Sequence.getObjectAt(1));
        if (aSN1Sequence.size() == 3) {
            this.c = ASN1Set.getInstance(aSN1Sequence.getObjectAt(2));
        }
    }

    public EncryptedData(EncryptedContentInfo encryptedContentInfo) {
        this(encryptedContentInfo, null);
    }

    public EncryptedData(EncryptedContentInfo encryptedContentInfo, ASN1Set aSN1Set) {
        this.a = new ASN1Integer(aSN1Set == null ? 0 : 2);
        this.b = encryptedContentInfo;
        this.c = aSN1Set;
    }

    public static EncryptedData getInstance(Object obj) {
        if (obj instanceof EncryptedData) {
            return (EncryptedData) obj;
        }
        if (obj != null) {
            return new EncryptedData(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public EncryptedContentInfo getEncryptedContentInfo() {
        return this.b;
    }

    public ASN1Set getUnprotectedAttrs() {
        return this.c;
    }

    public ASN1Integer getVersion() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        if (this.c != null) {
            aSN1EncodableVector.add(new BERTaggedObject(false, 1, this.c));
        }
        return new BERSequence(aSN1EncodableVector);
    }
}
