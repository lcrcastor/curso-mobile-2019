package org.bouncycastle.asn1.x509;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;

public class SubjectPublicKeyInfo extends ASN1Object {
    private AlgorithmIdentifier a;
    private DERBitString b;

    public SubjectPublicKeyInfo(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = AlgorithmIdentifier.getInstance(objects.nextElement());
        this.b = DERBitString.getInstance(objects.nextElement());
    }

    public SubjectPublicKeyInfo(AlgorithmIdentifier algorithmIdentifier, ASN1Encodable aSN1Encodable) {
        this.b = new DERBitString(aSN1Encodable);
        this.a = algorithmIdentifier;
    }

    public SubjectPublicKeyInfo(AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this.b = new DERBitString(bArr);
        this.a = algorithmIdentifier;
    }

    public static SubjectPublicKeyInfo getInstance(Object obj) {
        if (obj instanceof SubjectPublicKeyInfo) {
            return (SubjectPublicKeyInfo) obj;
        }
        if (obj != null) {
            return new SubjectPublicKeyInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static SubjectPublicKeyInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public AlgorithmIdentifier getAlgorithm() {
        return this.a;
    }

    public AlgorithmIdentifier getAlgorithmId() {
        return this.a;
    }

    public ASN1Primitive getPublicKey() {
        return new ASN1InputStream(this.b.getBytes()).readObject();
    }

    public DERBitString getPublicKeyData() {
        return this.b;
    }

    public ASN1Primitive parsePublicKey() {
        return new ASN1InputStream(this.b.getBytes()).readObject();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        return new DERSequence(aSN1EncodableVector);
    }
}
