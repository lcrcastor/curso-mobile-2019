package org.bouncycastle.pqc.asn1;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;

public class GMSSPublicKey extends ASN1Object {
    private ASN1Integer a;
    private byte[] b;

    private GMSSPublicKey(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("size of seq = ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        this.a = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets();
    }

    public GMSSPublicKey(byte[] bArr) {
        this.a = new ASN1Integer(0);
        this.b = bArr;
    }

    public static GMSSPublicKey getInstance(Object obj) {
        if (obj instanceof GMSSPublicKey) {
            return (GMSSPublicKey) obj;
        }
        if (obj != null) {
            return new GMSSPublicKey(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public byte[] getPublicKey() {
        return Arrays.clone(this.b);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(new DEROctetString(this.b));
        return new DERSequence(aSN1EncodableVector);
    }
}
