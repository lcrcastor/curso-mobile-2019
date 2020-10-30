package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.GeneralName;

public class EncKeyWithID extends ASN1Object {
    private final PrivateKeyInfo a;
    private final ASN1Encodable b;

    private EncKeyWithID(ASN1Sequence aSN1Sequence) {
        this.a = PrivateKeyInfo.getInstance(aSN1Sequence.getObjectAt(0));
        ASN1Encodable aSN1Encodable = aSN1Sequence.size() > 1 ? !(aSN1Sequence.getObjectAt(1) instanceof DERUTF8String) ? GeneralName.getInstance(aSN1Sequence.getObjectAt(1)) : aSN1Sequence.getObjectAt(1) : null;
        this.b = aSN1Encodable;
    }

    public EncKeyWithID(PrivateKeyInfo privateKeyInfo) {
        this.a = privateKeyInfo;
        this.b = null;
    }

    public EncKeyWithID(PrivateKeyInfo privateKeyInfo, DERUTF8String dERUTF8String) {
        this.a = privateKeyInfo;
        this.b = dERUTF8String;
    }

    public EncKeyWithID(PrivateKeyInfo privateKeyInfo, GeneralName generalName) {
        this.a = privateKeyInfo;
        this.b = generalName;
    }

    public static EncKeyWithID getInstance(Object obj) {
        if (obj instanceof EncKeyWithID) {
            return (EncKeyWithID) obj;
        }
        if (obj != null) {
            return new EncKeyWithID(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Encodable getIdentifier() {
        return this.b;
    }

    public PrivateKeyInfo getPrivateKey() {
        return this.a;
    }

    public boolean hasIdentifier() {
        return this.b != null;
    }

    public boolean isIdentifierUTF8String() {
        return this.b instanceof DERUTF8String;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.b != null) {
            aSN1EncodableVector.add(this.b);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
