package org.bouncycastle.asn1.x509;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class PolicyConstraints extends ASN1Object {
    private BigInteger a;
    private BigInteger b;

    public PolicyConstraints(BigInteger bigInteger, BigInteger bigInteger2) {
        this.a = bigInteger;
        this.b = bigInteger2;
    }

    private PolicyConstraints(ASN1Sequence aSN1Sequence) {
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            ASN1TaggedObject instance = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i));
            if (instance.getTagNo() == 0) {
                this.a = ASN1Integer.getInstance(instance, false).getValue();
            } else if (instance.getTagNo() == 1) {
                this.b = ASN1Integer.getInstance(instance, false).getValue();
            } else {
                throw new IllegalArgumentException("Unknown tag encountered.");
            }
        }
    }

    public static PolicyConstraints fromExtensions(Extensions extensions) {
        return getInstance(extensions.getExtensionParsedValue(Extension.policyConstraints));
    }

    public static PolicyConstraints getInstance(Object obj) {
        if (obj instanceof PolicyConstraints) {
            return (PolicyConstraints) obj;
        }
        if (obj != null) {
            return new PolicyConstraints(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public BigInteger getInhibitPolicyMapping() {
        return this.b;
    }

    public BigInteger getRequireExplicitPolicyMapping() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(new DERTaggedObject(0, new ASN1Integer(this.a)));
        }
        if (this.b != null) {
            aSN1EncodableVector.add(new DERTaggedObject(1, new ASN1Integer(this.b)));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
