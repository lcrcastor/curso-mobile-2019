package org.bouncycastle.asn1.ess;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.asn1.x509.IssuerSerial;

public class OtherCertID extends ASN1Object {
    private ASN1Encodable a;
    private IssuerSerial b;

    private OtherCertID(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 1 || aSN1Sequence.size() > 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        this.a = aSN1Sequence.getObjectAt(0).toASN1Primitive() instanceof ASN1OctetString ? ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)) : DigestInfo.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() > 1) {
            this.b = IssuerSerial.getInstance(aSN1Sequence.getObjectAt(1));
        }
    }

    public OtherCertID(AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this.a = new DigestInfo(algorithmIdentifier, bArr);
    }

    public OtherCertID(AlgorithmIdentifier algorithmIdentifier, byte[] bArr, IssuerSerial issuerSerial) {
        this.a = new DigestInfo(algorithmIdentifier, bArr);
        this.b = issuerSerial;
    }

    public static OtherCertID getInstance(Object obj) {
        if (obj instanceof OtherCertID) {
            return (OtherCertID) obj;
        }
        if (obj != null) {
            return new OtherCertID(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public AlgorithmIdentifier getAlgorithmHash() {
        return this.a.toASN1Primitive() instanceof ASN1OctetString ? new AlgorithmIdentifier("1.3.14.3.2.26") : DigestInfo.getInstance(this.a).getAlgorithmId();
    }

    public byte[] getCertHash() {
        return this.a.toASN1Primitive() instanceof ASN1OctetString ? ((ASN1OctetString) this.a.toASN1Primitive()).getOctets() : DigestInfo.getInstance(this.a).getDigest();
    }

    public IssuerSerial getIssuerSerial() {
        return this.b;
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
