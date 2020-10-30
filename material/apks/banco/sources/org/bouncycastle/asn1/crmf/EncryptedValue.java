package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedValue extends ASN1Object {
    private AlgorithmIdentifier a;
    private AlgorithmIdentifier b;
    private DERBitString c;
    private AlgorithmIdentifier d;
    private ASN1OctetString e;
    private DERBitString f;

    private EncryptedValue(ASN1Sequence aSN1Sequence) {
        int i = 0;
        while (aSN1Sequence.getObjectAt(i) instanceof ASN1TaggedObject) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Sequence.getObjectAt(i);
            switch (aSN1TaggedObject.getTagNo()) {
                case 0:
                    this.a = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
                    break;
                case 1:
                    this.b = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
                    break;
                case 2:
                    this.c = DERBitString.getInstance(aSN1TaggedObject, false);
                    break;
                case 3:
                    this.d = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
                    break;
                case 4:
                    this.e = ASN1OctetString.getInstance(aSN1TaggedObject, false);
                    break;
            }
            i++;
        }
        this.f = DERBitString.getInstance(aSN1Sequence.getObjectAt(i));
    }

    public EncryptedValue(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2, DERBitString dERBitString, AlgorithmIdentifier algorithmIdentifier3, ASN1OctetString aSN1OctetString, DERBitString dERBitString2) {
        if (dERBitString2 == null) {
            throw new IllegalArgumentException("'encValue' cannot be null");
        }
        this.a = algorithmIdentifier;
        this.b = algorithmIdentifier2;
        this.c = dERBitString;
        this.d = algorithmIdentifier3;
        this.e = aSN1OctetString;
        this.f = dERBitString2;
    }

    private void a(ASN1EncodableVector aSN1EncodableVector, int i, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, i, aSN1Encodable));
        }
    }

    public static EncryptedValue getInstance(Object obj) {
        if (obj instanceof EncryptedValue) {
            return (EncryptedValue) obj;
        }
        if (obj != null) {
            return new EncryptedValue(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public DERBitString getEncSymmKey() {
        return this.c;
    }

    public DERBitString getEncValue() {
        return this.f;
    }

    public AlgorithmIdentifier getIntendedAlg() {
        return this.a;
    }

    public AlgorithmIdentifier getKeyAlg() {
        return this.d;
    }

    public AlgorithmIdentifier getSymmAlg() {
        return this.b;
    }

    public ASN1OctetString getValueHint() {
        return this.e;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        a(aSN1EncodableVector, 0, this.a);
        a(aSN1EncodableVector, 1, this.b);
        a(aSN1EncodableVector, 2, this.c);
        a(aSN1EncodableVector, 3, this.d);
        a(aSN1EncodableVector, 4, this.e);
        aSN1EncodableVector.add(this.f);
        return new DERSequence(aSN1EncodableVector);
    }
}
