package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERUTF8String;

public class MetaData extends ASN1Object {
    private ASN1Boolean a;
    private DERUTF8String b;
    private DERIA5String c;
    private Attributes d;

    public MetaData(ASN1Boolean aSN1Boolean, DERUTF8String dERUTF8String, DERIA5String dERIA5String, Attributes attributes) {
        this.a = aSN1Boolean;
        this.b = dERUTF8String;
        this.c = dERIA5String;
        this.d = attributes;
    }

    private MetaData(ASN1Sequence aSN1Sequence) {
        int i;
        this.a = ASN1Boolean.getInstance((Object) aSN1Sequence.getObjectAt(0));
        if (1 >= aSN1Sequence.size() || !(aSN1Sequence.getObjectAt(1) instanceof DERUTF8String)) {
            i = 1;
        } else {
            i = 2;
            this.b = DERUTF8String.getInstance(aSN1Sequence.getObjectAt(1));
        }
        if (i < aSN1Sequence.size() && (aSN1Sequence.getObjectAt(i) instanceof DERIA5String)) {
            int i2 = i + 1;
            this.c = DERIA5String.getInstance(aSN1Sequence.getObjectAt(i));
            i = i2;
        }
        if (i < aSN1Sequence.size()) {
            this.d = Attributes.getInstance(aSN1Sequence.getObjectAt(i));
        }
    }

    public static MetaData getInstance(Object obj) {
        if (obj instanceof MetaData) {
            return (MetaData) obj;
        }
        if (obj != null) {
            return new MetaData(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public DERUTF8String getFileName() {
        return this.b;
    }

    public DERIA5String getMediaType() {
        return this.c;
    }

    public Attributes getOtherMetaData() {
        return this.d;
    }

    public boolean isHashProtected() {
        return this.a.isTrue();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.b != null) {
            aSN1EncodableVector.add(this.b);
        }
        if (this.c != null) {
            aSN1EncodableVector.add(this.c);
        }
        if (this.d != null) {
            aSN1EncodableVector.add(this.d);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
