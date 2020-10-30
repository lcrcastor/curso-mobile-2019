package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class PBMParameter extends ASN1Object {
    private ASN1OctetString a;
    private AlgorithmIdentifier b;
    private ASN1Integer c;
    private AlgorithmIdentifier d;

    public PBMParameter(ASN1OctetString aSN1OctetString, AlgorithmIdentifier algorithmIdentifier, ASN1Integer aSN1Integer, AlgorithmIdentifier algorithmIdentifier2) {
        this.a = aSN1OctetString;
        this.b = algorithmIdentifier;
        this.c = aSN1Integer;
        this.d = algorithmIdentifier2;
    }

    private PBMParameter(ASN1Sequence aSN1Sequence) {
        this.a = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
        this.c = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(2));
        this.d = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(3));
    }

    public PBMParameter(byte[] bArr, AlgorithmIdentifier algorithmIdentifier, int i, AlgorithmIdentifier algorithmIdentifier2) {
        this((ASN1OctetString) new DEROctetString(bArr), algorithmIdentifier, new ASN1Integer((long) i), algorithmIdentifier2);
    }

    public static PBMParameter getInstance(Object obj) {
        if (obj instanceof PBMParameter) {
            return (PBMParameter) obj;
        }
        if (obj != null) {
            return new PBMParameter(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Integer getIterationCount() {
        return this.c;
    }

    public AlgorithmIdentifier getMac() {
        return this.d;
    }

    public AlgorithmIdentifier getOwf() {
        return this.b;
    }

    public ASN1OctetString getSalt() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(this.d);
        return new DERSequence(aSN1EncodableVector);
    }
}
