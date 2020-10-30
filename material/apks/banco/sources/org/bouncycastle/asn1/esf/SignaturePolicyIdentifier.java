package org.bouncycastle.asn1.esf;

import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;

public class SignaturePolicyIdentifier extends ASN1Object {
    private SignaturePolicyId a;
    private boolean b = true;

    public SignaturePolicyIdentifier() {
    }

    public SignaturePolicyIdentifier(SignaturePolicyId signaturePolicyId) {
        this.a = signaturePolicyId;
    }

    public static SignaturePolicyIdentifier getInstance(Object obj) {
        if (obj instanceof SignaturePolicyIdentifier) {
            return (SignaturePolicyIdentifier) obj;
        }
        if ((obj instanceof ASN1Null) || hasEncodedTagValue(obj, 5)) {
            return new SignaturePolicyIdentifier();
        }
        if (obj != null) {
            return new SignaturePolicyIdentifier(SignaturePolicyId.getInstance(obj));
        }
        return null;
    }

    public SignaturePolicyId getSignaturePolicyId() {
        return this.a;
    }

    public boolean isSignaturePolicyImplied() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.b ? DERNull.INSTANCE : this.a.toASN1Primitive();
    }
}
