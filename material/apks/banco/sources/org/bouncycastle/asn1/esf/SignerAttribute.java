package org.bouncycastle.asn1.esf;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.Attribute;
import org.bouncycastle.asn1.x509.AttributeCertificate;

public class SignerAttribute extends ASN1Object {
    private Object[] a;

    private SignerAttribute(ASN1Sequence aSN1Sequence) {
        this.a = new Object[aSN1Sequence.size()];
        Enumeration objects = aSN1Sequence.getObjects();
        int i = 0;
        while (objects.hasMoreElements()) {
            ASN1TaggedObject instance = ASN1TaggedObject.getInstance(objects.nextElement());
            if (instance.getTagNo() == 0) {
                ASN1Sequence instance2 = ASN1Sequence.getInstance(instance, true);
                Attribute[] attributeArr = new Attribute[instance2.size()];
                for (int i2 = 0; i2 != attributeArr.length; i2++) {
                    attributeArr[i2] = Attribute.getInstance(instance2.getObjectAt(i2));
                }
                this.a[i] = attributeArr;
            } else if (instance.getTagNo() == 1) {
                this.a[i] = AttributeCertificate.getInstance(ASN1Sequence.getInstance(instance, true));
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("illegal tag: ");
                sb.append(instance.getTagNo());
                throw new IllegalArgumentException(sb.toString());
            }
            i++;
        }
    }

    public SignerAttribute(AttributeCertificate attributeCertificate) {
        this.a = new Object[1];
        this.a[0] = attributeCertificate;
    }

    public SignerAttribute(Attribute[] attributeArr) {
        this.a = new Object[1];
        this.a[0] = attributeArr;
    }

    public static SignerAttribute getInstance(Object obj) {
        if (obj instanceof SignerAttribute) {
            return (SignerAttribute) obj;
        }
        if (obj != null) {
            return new SignerAttribute(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public Object[] getValues() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i = 0; i != this.a.length; i++) {
            aSN1EncodableVector.add(this.a[i] instanceof Attribute[] ? new DERTaggedObject(0, new DERSequence((ASN1Encodable[]) (Attribute[]) this.a[i])) : new DERTaggedObject(1, (AttributeCertificate) this.a[i]));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
