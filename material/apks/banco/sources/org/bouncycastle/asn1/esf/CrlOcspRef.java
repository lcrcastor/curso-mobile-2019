package org.bouncycastle.asn1.esf;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class CrlOcspRef extends ASN1Object {
    private CrlListID a;
    private OcspListID b;
    private OtherRevRefs c;

    private CrlOcspRef(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            DERTaggedObject dERTaggedObject = (DERTaggedObject) objects.nextElement();
            switch (dERTaggedObject.getTagNo()) {
                case 0:
                    this.a = CrlListID.getInstance(dERTaggedObject.getObject());
                    break;
                case 1:
                    this.b = OcspListID.getInstance(dERTaggedObject.getObject());
                    break;
                case 2:
                    this.c = OtherRevRefs.getInstance(dERTaggedObject.getObject());
                    break;
                default:
                    throw new IllegalArgumentException("illegal tag");
            }
        }
    }

    public CrlOcspRef(CrlListID crlListID, OcspListID ocspListID, OtherRevRefs otherRevRefs) {
        this.a = crlListID;
        this.b = ocspListID;
        this.c = otherRevRefs;
    }

    public static CrlOcspRef getInstance(Object obj) {
        if (obj instanceof CrlOcspRef) {
            return (CrlOcspRef) obj;
        }
        if (obj != null) {
            return new CrlOcspRef(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CrlListID getCrlids() {
        return this.a;
    }

    public OcspListID getOcspids() {
        return this.b;
    }

    public OtherRevRefs getOtherRev() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.a.toASN1Primitive()));
        }
        if (this.b != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, this.b.toASN1Primitive()));
        }
        if (this.c != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 2, this.c.toASN1Primitive()));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
