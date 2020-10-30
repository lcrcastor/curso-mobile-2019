package org.bouncycastle.asn1.cmp;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;

public class CertStatus extends ASN1Object {
    private ASN1OctetString a;
    private ASN1Integer b;
    private PKIStatusInfo c;

    private CertStatus(ASN1Sequence aSN1Sequence) {
        this.a = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1));
        if (aSN1Sequence.size() > 2) {
            this.c = PKIStatusInfo.getInstance(aSN1Sequence.getObjectAt(2));
        }
    }

    public CertStatus(byte[] bArr, BigInteger bigInteger) {
        this.a = new DEROctetString(bArr);
        this.b = new ASN1Integer(bigInteger);
    }

    public CertStatus(byte[] bArr, BigInteger bigInteger, PKIStatusInfo pKIStatusInfo) {
        this.a = new DEROctetString(bArr);
        this.b = new ASN1Integer(bigInteger);
        this.c = pKIStatusInfo;
    }

    public static CertStatus getInstance(Object obj) {
        if (obj instanceof CertStatus) {
            return (CertStatus) obj;
        }
        if (obj != null) {
            return new CertStatus(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1OctetString getCertHash() {
        return this.a;
    }

    public ASN1Integer getCertReqId() {
        return this.b;
    }

    public PKIStatusInfo getStatusInfo() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        if (this.c != null) {
            aSN1EncodableVector.add(this.c);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
