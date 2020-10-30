package org.bouncycastle.asn1.isismtt.ocsp;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class CertHash extends ASN1Object {
    private AlgorithmIdentifier a;
    private byte[] b;

    private CertHash(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        this.a = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = DEROctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets();
    }

    public CertHash(AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this.a = algorithmIdentifier;
        this.b = new byte[bArr.length];
        System.arraycopy(bArr, 0, this.b, 0, bArr.length);
    }

    public static CertHash getInstance(Object obj) {
        if (obj == null || (obj instanceof CertHash)) {
            return (CertHash) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new CertHash((ASN1Sequence) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("illegal object in getInstance: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public byte[] getCertificateHash() {
        return this.b;
    }

    public AlgorithmIdentifier getHashAlgorithm() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(new DEROctetString(this.b));
        return new DERSequence(aSN1EncodableVector);
    }
}
