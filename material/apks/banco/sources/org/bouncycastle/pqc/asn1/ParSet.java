package org.bouncycastle.pqc.asn1;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;

public class ParSet extends ASN1Object {
    private static final BigInteger a = BigInteger.valueOf(0);
    private int b;
    private int[] c;
    private int[] d;
    private int[] e;

    public ParSet(int i, int[] iArr, int[] iArr2, int[] iArr3) {
        this.b = i;
        this.c = iArr;
        this.d = iArr2;
        this.e = iArr3;
    }

    private ParSet(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 4) {
            StringBuilder sb = new StringBuilder();
            sb.append("sie of seqOfParams = ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        this.b = a(((ASN1Integer) aSN1Sequence.getObjectAt(0)).getValue());
        ASN1Sequence aSN1Sequence2 = (ASN1Sequence) aSN1Sequence.getObjectAt(1);
        ASN1Sequence aSN1Sequence3 = (ASN1Sequence) aSN1Sequence.getObjectAt(2);
        ASN1Sequence aSN1Sequence4 = (ASN1Sequence) aSN1Sequence.getObjectAt(3);
        if (aSN1Sequence2.size() == this.b && aSN1Sequence3.size() == this.b && aSN1Sequence4.size() == this.b) {
            this.c = new int[aSN1Sequence2.size()];
            this.d = new int[aSN1Sequence3.size()];
            this.e = new int[aSN1Sequence4.size()];
            for (int i = 0; i < this.b; i++) {
                this.c[i] = a(((ASN1Integer) aSN1Sequence2.getObjectAt(i)).getValue());
                this.d[i] = a(((ASN1Integer) aSN1Sequence3.getObjectAt(i)).getValue());
                this.e[i] = a(((ASN1Integer) aSN1Sequence4.getObjectAt(i)).getValue());
            }
            return;
        }
        throw new IllegalArgumentException("invalid size of sequences");
    }

    private static int a(BigInteger bigInteger) {
        if (bigInteger.compareTo(BigInteger.valueOf(2147483647L)) <= 0 && bigInteger.compareTo(a) > 0) {
            return bigInteger.intValue();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("BigInteger not in Range: ");
        sb.append(bigInteger.toString());
        throw new IllegalArgumentException(sb.toString());
    }

    public static ParSet getInstance(Object obj) {
        if (obj instanceof ParSet) {
            return (ParSet) obj;
        }
        if (obj != null) {
            return new ParSet(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public int[] getH() {
        return Arrays.clone(this.c);
    }

    public int[] getK() {
        return Arrays.clone(this.e);
    }

    public int getT() {
        return this.b;
    }

    public int[] getW() {
        return Arrays.clone(this.d);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        ASN1EncodableVector aSN1EncodableVector3 = new ASN1EncodableVector();
        for (int i = 0; i < this.c.length; i++) {
            aSN1EncodableVector.add(new ASN1Integer((long) this.c[i]));
            aSN1EncodableVector2.add(new ASN1Integer((long) this.d[i]));
            aSN1EncodableVector3.add(new ASN1Integer((long) this.e[i]));
        }
        ASN1EncodableVector aSN1EncodableVector4 = new ASN1EncodableVector();
        aSN1EncodableVector4.add(new ASN1Integer((long) this.b));
        aSN1EncodableVector4.add(new DERSequence(aSN1EncodableVector));
        aSN1EncodableVector4.add(new DERSequence(aSN1EncodableVector2));
        aSN1EncodableVector4.add(new DERSequence(aSN1EncodableVector3));
        return new DERSequence(aSN1EncodableVector4);
    }
}
