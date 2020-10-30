package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;

public class ObjectDigestInfo extends ASN1Object {
    public static final int otherObjectDigest = 2;
    public static final int publicKey = 0;
    public static final int publicKeyCert = 1;
    ASN1Enumerated a;
    ASN1ObjectIdentifier b;
    AlgorithmIdentifier c;
    DERBitString d;

    public ObjectDigestInfo(int i, ASN1ObjectIdentifier aSN1ObjectIdentifier, AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this.a = new ASN1Enumerated(i);
        if (i == 2) {
            this.b = aSN1ObjectIdentifier;
        }
        this.c = algorithmIdentifier;
        this.d = new DERBitString(bArr);
    }

    private ObjectDigestInfo(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() > 4 || aSN1Sequence.size() < 3) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        int i = 0;
        this.a = ASN1Enumerated.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() == 4) {
            this.b = ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
            i = 1;
        }
        this.c = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i + 1));
        this.d = DERBitString.getInstance(aSN1Sequence.getObjectAt(i + 2));
    }

    public static ObjectDigestInfo getInstance(Object obj) {
        if (obj instanceof ObjectDigestInfo) {
            return (ObjectDigestInfo) obj;
        }
        if (obj != null) {
            return new ObjectDigestInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static ObjectDigestInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public AlgorithmIdentifier getDigestAlgorithm() {
        return this.c;
    }

    public ASN1Enumerated getDigestedObjectType() {
        return this.a;
    }

    public DERBitString getObjectDigest() {
        return this.d;
    }

    public ASN1ObjectIdentifier getOtherObjectTypeID() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.b != null) {
            aSN1EncodableVector.add(this.b);
        }
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(this.d);
        return new DERSequence(aSN1EncodableVector);
    }
}
