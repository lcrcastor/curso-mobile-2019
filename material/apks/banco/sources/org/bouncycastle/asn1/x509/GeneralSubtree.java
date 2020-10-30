package org.bouncycastle.asn1.x509;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class GeneralSubtree extends ASN1Object {
    private static final BigInteger a = BigInteger.valueOf(0);
    private GeneralName b;
    private ASN1Integer c;
    private ASN1Integer d;

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00b4, code lost:
        r4.d = org.bouncycastle.asn1.ASN1Integer.getInstance(r5, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00ba, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00c1, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private GeneralSubtree(org.bouncycastle.asn1.ASN1Sequence r5) {
        /*
            r4 = this;
            r4.<init>()
            r0 = 0
            org.bouncycastle.asn1.ASN1Encodable r1 = r5.getObjectAt(r0)
            org.bouncycastle.asn1.x509.GeneralName r1 = org.bouncycastle.asn1.x509.GeneralName.getInstance(r1)
            r4.b = r1
            int r1 = r5.size()
            r2 = 1
            switch(r1) {
                case 1: goto L_0x00c1;
                case 2: goto L_0x008a;
                case 3: goto L_0x0031;
                default: goto L_0x0016;
            }
        L_0x0016:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Bad sequence size: "
            r1.append(r2)
            int r5 = r5.size()
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            r0.<init>(r5)
            throw r0
        L_0x0031:
            org.bouncycastle.asn1.ASN1Encodable r1 = r5.getObjectAt(r2)
            org.bouncycastle.asn1.ASN1TaggedObject r1 = org.bouncycastle.asn1.ASN1TaggedObject.getInstance(r1)
            int r3 = r1.getTagNo()
            if (r3 == 0) goto L_0x005a
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "Bad tag number for 'minimum': "
            r0.append(r2)
            int r1 = r1.getTagNo()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L_0x005a:
            org.bouncycastle.asn1.ASN1Integer r1 = org.bouncycastle.asn1.ASN1Integer.getInstance(r1, r0)
            r4.c = r1
            r1 = 2
            org.bouncycastle.asn1.ASN1Encodable r5 = r5.getObjectAt(r1)
            org.bouncycastle.asn1.ASN1TaggedObject r5 = org.bouncycastle.asn1.ASN1TaggedObject.getInstance(r5)
            int r1 = r5.getTagNo()
            if (r1 == r2) goto L_0x00b4
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Bad tag number for 'maximum': "
            r1.append(r2)
            int r5 = r5.getTagNo()
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            r0.<init>(r5)
            throw r0
        L_0x008a:
            org.bouncycastle.asn1.ASN1Encodable r5 = r5.getObjectAt(r2)
            org.bouncycastle.asn1.ASN1TaggedObject r5 = org.bouncycastle.asn1.ASN1TaggedObject.getInstance(r5)
            int r1 = r5.getTagNo()
            switch(r1) {
                case 0: goto L_0x00bb;
                case 1: goto L_0x00b4;
                default: goto L_0x0099;
            }
        L_0x0099:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Bad tag number: "
            r1.append(r2)
            int r5 = r5.getTagNo()
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            r0.<init>(r5)
            throw r0
        L_0x00b4:
            org.bouncycastle.asn1.ASN1Integer r5 = org.bouncycastle.asn1.ASN1Integer.getInstance(r5, r0)
            r4.d = r5
            return
        L_0x00bb:
            org.bouncycastle.asn1.ASN1Integer r5 = org.bouncycastle.asn1.ASN1Integer.getInstance(r5, r0)
            r4.c = r5
        L_0x00c1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.x509.GeneralSubtree.<init>(org.bouncycastle.asn1.ASN1Sequence):void");
    }

    public GeneralSubtree(GeneralName generalName) {
        this(generalName, null, null);
    }

    public GeneralSubtree(GeneralName generalName, BigInteger bigInteger, BigInteger bigInteger2) {
        this.b = generalName;
        if (bigInteger2 != null) {
            this.d = new ASN1Integer(bigInteger2);
        }
        this.c = bigInteger == null ? null : new ASN1Integer(bigInteger);
    }

    public static GeneralSubtree getInstance(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj instanceof GeneralSubtree ? (GeneralSubtree) obj : new GeneralSubtree(ASN1Sequence.getInstance(obj));
    }

    public static GeneralSubtree getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return new GeneralSubtree(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public GeneralName getBase() {
        return this.b;
    }

    public BigInteger getMaximum() {
        if (this.d == null) {
            return null;
        }
        return this.d.getValue();
    }

    public BigInteger getMinimum() {
        return this.c == null ? a : this.c.getValue();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.b);
        if (this.c != null && !this.c.getValue().equals(a)) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.c));
        }
        if (this.d != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, this.d));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
