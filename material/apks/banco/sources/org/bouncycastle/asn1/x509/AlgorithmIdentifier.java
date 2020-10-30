package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERSequence;

public class AlgorithmIdentifier extends ASN1Object {
    private ASN1ObjectIdentifier a;
    private ASN1Encodable b;
    private boolean c;

    public AlgorithmIdentifier(String str) {
        this.c = false;
        this.a = new ASN1ObjectIdentifier(str);
    }

    public AlgorithmIdentifier(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.c = false;
        this.a = aSN1ObjectIdentifier;
    }

    public AlgorithmIdentifier(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.c = false;
        this.c = true;
        this.a = aSN1ObjectIdentifier;
        this.b = aSN1Encodable;
    }

    public AlgorithmIdentifier(ASN1Sequence aSN1Sequence) {
        ASN1Encodable aSN1Encodable;
        this.c = false;
        if (aSN1Sequence.size() < 1 || aSN1Sequence.size() > 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        this.a = ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() == 2) {
            this.c = true;
            aSN1Encodable = aSN1Sequence.getObjectAt(1);
        } else {
            aSN1Encodable = null;
        }
        this.b = aSN1Encodable;
    }

    public static AlgorithmIdentifier getInstance(Object obj) {
        return (obj == null || (obj instanceof AlgorithmIdentifier)) ? (AlgorithmIdentifier) obj : obj instanceof ASN1ObjectIdentifier ? new AlgorithmIdentifier((ASN1ObjectIdentifier) obj) : obj instanceof String ? new AlgorithmIdentifier((String) obj) : new AlgorithmIdentifier(ASN1Sequence.getInstance(obj));
    }

    public static AlgorithmIdentifier getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public ASN1ObjectIdentifier getAlgorithm() {
        return new ASN1ObjectIdentifier(this.a.getId());
    }

    public ASN1ObjectIdentifier getObjectId() {
        return this.a;
    }

    public ASN1Encodable getParameters() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.c) {
            aSN1EncodableVector.add(this.b != null ? this.b : DERNull.INSTANCE);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
