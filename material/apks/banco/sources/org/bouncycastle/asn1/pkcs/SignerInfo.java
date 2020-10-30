package org.bouncycastle.asn1.pkcs;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
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

public class SignerInfo extends ASN1Object {
    private ASN1Integer a;
    private IssuerAndSerialNumber b;
    private AlgorithmIdentifier c;
    private ASN1Set d;
    private AlgorithmIdentifier e;
    private ASN1OctetString f;
    private ASN1Set g;

    public SignerInfo(ASN1Integer aSN1Integer, IssuerAndSerialNumber issuerAndSerialNumber, AlgorithmIdentifier algorithmIdentifier, ASN1Set aSN1Set, AlgorithmIdentifier algorithmIdentifier2, ASN1OctetString aSN1OctetString, ASN1Set aSN1Set2) {
        this.a = aSN1Integer;
        this.b = issuerAndSerialNumber;
        this.c = algorithmIdentifier;
        this.d = aSN1Set;
        this.e = algorithmIdentifier2;
        this.f = aSN1OctetString;
        this.g = aSN1Set2;
    }

    public SignerInfo(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = (ASN1Integer) objects.nextElement();
        this.b = IssuerAndSerialNumber.getInstance(objects.nextElement());
        this.c = AlgorithmIdentifier.getInstance(objects.nextElement());
        Object nextElement = objects.nextElement();
        if (nextElement instanceof ASN1TaggedObject) {
            this.d = ASN1Set.getInstance((ASN1TaggedObject) nextElement, false);
            nextElement = objects.nextElement();
        } else {
            this.d = null;
        }
        this.e = AlgorithmIdentifier.getInstance(nextElement);
        this.f = DEROctetString.getInstance(objects.nextElement());
        if (objects.hasMoreElements()) {
            this.g = ASN1Set.getInstance((ASN1TaggedObject) objects.nextElement(), false);
        } else {
            this.g = null;
        }
    }

    public static SignerInfo getInstance(Object obj) {
        if (obj instanceof SignerInfo) {
            return (SignerInfo) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new SignerInfo((ASN1Sequence) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unknown object in factory: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public ASN1Set getAuthenticatedAttributes() {
        return this.d;
    }

    public AlgorithmIdentifier getDigestAlgorithm() {
        return this.c;
    }

    public AlgorithmIdentifier getDigestEncryptionAlgorithm() {
        return this.e;
    }

    public ASN1OctetString getEncryptedDigest() {
        return this.f;
    }

    public IssuerAndSerialNumber getIssuerAndSerialNumber() {
        return this.b;
    }

    public ASN1Set getUnauthenticatedAttributes() {
        return this.g;
    }

    public ASN1Integer getVersion() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        if (this.d != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.d));
        }
        aSN1EncodableVector.add(this.e);
        aSN1EncodableVector.add(this.f);
        if (this.g != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, this.g));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
