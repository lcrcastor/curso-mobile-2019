package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class POPOSigningKey extends ASN1Object {
    private POPOSigningKeyInput a;
    private AlgorithmIdentifier b;
    private DERBitString c;

    private POPOSigningKey(ASN1Sequence aSN1Sequence) {
        int i = 0;
        if (aSN1Sequence.getObjectAt(0) instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Sequence.getObjectAt(0);
            if (aSN1TaggedObject.getTagNo() != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown POPOSigningKeyInput tag: ");
                sb.append(aSN1TaggedObject.getTagNo());
                throw new IllegalArgumentException(sb.toString());
            }
            this.a = POPOSigningKeyInput.getInstance(aSN1TaggedObject.getObject());
            i = 1;
        }
        int i2 = i + 1;
        this.b = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i));
        this.c = DERBitString.getInstance(aSN1Sequence.getObjectAt(i2));
    }

    public POPOSigningKey(POPOSigningKeyInput pOPOSigningKeyInput, AlgorithmIdentifier algorithmIdentifier, DERBitString dERBitString) {
        this.a = pOPOSigningKeyInput;
        this.b = algorithmIdentifier;
        this.c = dERBitString;
    }

    public static POPOSigningKey getInstance(Object obj) {
        if (obj instanceof POPOSigningKey) {
            return (POPOSigningKey) obj;
        }
        if (obj != null) {
            return new POPOSigningKey(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static POPOSigningKey getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public AlgorithmIdentifier getAlgorithmIdentifier() {
        return this.b;
    }

    public POPOSigningKeyInput getPoposkInput() {
        return this.a;
    }

    public DERBitString getSignature() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.a));
        }
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        return new DERSequence(aSN1EncodableVector);
    }
}
