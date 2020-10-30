package org.bouncycastle.asn1.pkcs;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.DigestInfo;

public class MacData extends ASN1Object {
    private static final BigInteger d = BigInteger.valueOf(1);
    DigestInfo a;
    byte[] b;
    BigInteger c;

    private MacData(ASN1Sequence aSN1Sequence) {
        this.a = DigestInfo.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = ((ASN1OctetString) aSN1Sequence.getObjectAt(1)).getOctets();
        this.c = aSN1Sequence.size() == 3 ? ((ASN1Integer) aSN1Sequence.getObjectAt(2)).getValue() : d;
    }

    public MacData(DigestInfo digestInfo, byte[] bArr, int i) {
        this.a = digestInfo;
        this.b = bArr;
        this.c = BigInteger.valueOf((long) i);
    }

    public static MacData getInstance(Object obj) {
        if (obj instanceof MacData) {
            return (MacData) obj;
        }
        if (obj != null) {
            return new MacData(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public BigInteger getIterationCount() {
        return this.c;
    }

    public DigestInfo getMac() {
        return this.a;
    }

    public byte[] getSalt() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(new DEROctetString(this.b));
        if (!this.c.equals(d)) {
            aSN1EncodableVector.add(new ASN1Integer(this.c));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
