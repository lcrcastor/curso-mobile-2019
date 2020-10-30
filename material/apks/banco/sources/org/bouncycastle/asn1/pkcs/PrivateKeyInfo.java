package org.bouncycastle.asn1.pkcs;

import java.io.IOException;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class PrivateKeyInfo extends ASN1Object {
    private ASN1OctetString a;
    private AlgorithmIdentifier b;
    private ASN1Set c;

    public PrivateKeyInfo(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        if (((ASN1Integer) objects.nextElement()).getValue().intValue() != 0) {
            throw new IllegalArgumentException("wrong version for private key info");
        }
        this.b = AlgorithmIdentifier.getInstance(objects.nextElement());
        this.a = ASN1OctetString.getInstance(objects.nextElement());
        if (objects.hasMoreElements()) {
            this.c = ASN1Set.getInstance((ASN1TaggedObject) objects.nextElement(), false);
        }
    }

    public PrivateKeyInfo(AlgorithmIdentifier algorithmIdentifier, ASN1Encodable aSN1Encodable) {
        this(algorithmIdentifier, aSN1Encodable, null);
    }

    public PrivateKeyInfo(AlgorithmIdentifier algorithmIdentifier, ASN1Encodable aSN1Encodable, ASN1Set aSN1Set) {
        this.a = new DEROctetString(aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER));
        this.b = algorithmIdentifier;
        this.c = aSN1Set;
    }

    public static PrivateKeyInfo getInstance(Object obj) {
        if (obj instanceof PrivateKeyInfo) {
            return (PrivateKeyInfo) obj;
        }
        if (obj != null) {
            return new PrivateKeyInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static PrivateKeyInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public AlgorithmIdentifier getAlgorithmId() {
        return this.b;
    }

    public ASN1Set getAttributes() {
        return this.c;
    }

    public ASN1Primitive getPrivateKey() {
        try {
            return parsePrivateKey().toASN1Primitive();
        } catch (IOException unused) {
            throw new IllegalStateException("unable to parse private key");
        }
    }

    public AlgorithmIdentifier getPrivateKeyAlgorithm() {
        return this.b;
    }

    public ASN1Encodable parsePrivateKey() {
        return ASN1Primitive.fromByteArray(this.a.getOctets());
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(0));
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.a);
        if (this.c != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.c));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
