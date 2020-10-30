package org.bouncycastle.asn1.pkcs;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;

public class RC2CBCParameter extends ASN1Object {
    ASN1Integer a;
    ASN1OctetString b;

    public RC2CBCParameter(int i, byte[] bArr) {
        this.a = new ASN1Integer((long) i);
        this.b = new DEROctetString(bArr);
    }

    private RC2CBCParameter(ASN1Sequence aSN1Sequence) {
        ASN1Encodable objectAt;
        if (aSN1Sequence.size() == 1) {
            this.a = null;
            objectAt = aSN1Sequence.getObjectAt(0);
        } else {
            this.a = (ASN1Integer) aSN1Sequence.getObjectAt(0);
            objectAt = aSN1Sequence.getObjectAt(1);
        }
        this.b = (ASN1OctetString) objectAt;
    }

    public RC2CBCParameter(byte[] bArr) {
        this.a = null;
        this.b = new DEROctetString(bArr);
    }

    public static RC2CBCParameter getInstance(Object obj) {
        if (obj instanceof RC2CBCParameter) {
            return (RC2CBCParameter) obj;
        }
        if (obj != null) {
            return new RC2CBCParameter(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public byte[] getIV() {
        return this.b.getOctets();
    }

    public BigInteger getRC2ParameterVersion() {
        if (this.a == null) {
            return null;
        }
        return this.a.getValue();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(this.a);
        }
        aSN1EncodableVector.add(this.b);
        return new DERSequence(aSN1EncodableVector);
    }
}
