package org.bouncycastle.asn1.x9;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECCurve;

public class X9Curve extends ASN1Object implements X9ObjectIdentifiers {
    private ECCurve a;
    private byte[] b;
    private ASN1ObjectIdentifier c = null;

    /* JADX WARNING: type inference failed for: r2v6, types: [org.bouncycastle.math.ec.ECCurve$Fp] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public X9Curve(org.bouncycastle.asn1.x9.X9FieldID r14, org.bouncycastle.asn1.ASN1Sequence r15) {
        /*
            r13 = this;
            r13.<init>()
            r0 = 0
            r13.c = r0
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r14.getIdentifier()
            r13.c = r0
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r13.c
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = prime_field
            boolean r0 = r0.equals(r1)
            r1 = 2
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L_0x0052
            org.bouncycastle.asn1.ASN1Primitive r14 = r14.getParameters()
            org.bouncycastle.asn1.ASN1Integer r14 = (org.bouncycastle.asn1.ASN1Integer) r14
            java.math.BigInteger r14 = r14.getValue()
            org.bouncycastle.asn1.x9.X9FieldElement r0 = new org.bouncycastle.asn1.x9.X9FieldElement
            org.bouncycastle.asn1.ASN1Encodable r3 = r15.getObjectAt(r3)
            org.bouncycastle.asn1.ASN1OctetString r3 = (org.bouncycastle.asn1.ASN1OctetString) r3
            r0.<init>(r14, r3)
            org.bouncycastle.asn1.x9.X9FieldElement r3 = new org.bouncycastle.asn1.x9.X9FieldElement
            org.bouncycastle.asn1.ASN1Encodable r2 = r15.getObjectAt(r2)
            org.bouncycastle.asn1.ASN1OctetString r2 = (org.bouncycastle.asn1.ASN1OctetString) r2
            r3.<init>(r14, r2)
            org.bouncycastle.math.ec.ECCurve$Fp r2 = new org.bouncycastle.math.ec.ECCurve$Fp
            org.bouncycastle.math.ec.ECFieldElement r0 = r0.getValue()
            java.math.BigInteger r0 = r0.toBigInteger()
            org.bouncycastle.math.ec.ECFieldElement r3 = r3.getValue()
            java.math.BigInteger r3 = r3.toBigInteger()
            r2.<init>(r14, r0, r3)
        L_0x004e:
            r13.a = r2
            goto L_0x010d
        L_0x0052:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r13.c
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = characteristic_two_field
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0129
            org.bouncycastle.asn1.ASN1Primitive r14 = r14.getParameters()
            org.bouncycastle.asn1.ASN1Sequence r14 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r14)
            org.bouncycastle.asn1.ASN1Encodable r0 = r14.getObjectAt(r3)
            org.bouncycastle.asn1.ASN1Integer r0 = (org.bouncycastle.asn1.ASN1Integer) r0
            java.math.BigInteger r0 = r0.getValue()
            int r0 = r0.intValue()
            org.bouncycastle.asn1.ASN1Encodable r4 = r14.getObjectAt(r2)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r4
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = tpBasis
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x0093
            org.bouncycastle.asn1.ASN1Encodable r14 = r14.getObjectAt(r1)
            org.bouncycastle.asn1.ASN1Integer r14 = org.bouncycastle.asn1.ASN1Integer.getInstance(r14)
            java.math.BigInteger r14 = r14.getValue()
            int r14 = r14.intValue()
            r10 = 0
            r11 = 0
            goto L_0x00d6
        L_0x0093:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = ppBasis
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0121
            org.bouncycastle.asn1.ASN1Encodable r14 = r14.getObjectAt(r1)
            org.bouncycastle.asn1.ASN1Sequence r14 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r14)
            org.bouncycastle.asn1.ASN1Encodable r4 = r14.getObjectAt(r3)
            org.bouncycastle.asn1.ASN1Integer r4 = org.bouncycastle.asn1.ASN1Integer.getInstance(r4)
            java.math.BigInteger r4 = r4.getValue()
            int r4 = r4.intValue()
            org.bouncycastle.asn1.ASN1Encodable r5 = r14.getObjectAt(r2)
            org.bouncycastle.asn1.ASN1Integer r5 = org.bouncycastle.asn1.ASN1Integer.getInstance(r5)
            java.math.BigInteger r5 = r5.getValue()
            int r5 = r5.intValue()
            org.bouncycastle.asn1.ASN1Encodable r14 = r14.getObjectAt(r1)
            org.bouncycastle.asn1.ASN1Integer r14 = org.bouncycastle.asn1.ASN1Integer.getInstance(r14)
            java.math.BigInteger r14 = r14.getValue()
            int r14 = r14.intValue()
            r11 = r14
            r14 = r4
            r10 = r5
        L_0x00d6:
            org.bouncycastle.asn1.x9.X9FieldElement r12 = new org.bouncycastle.asn1.x9.X9FieldElement
            org.bouncycastle.asn1.ASN1Encodable r3 = r15.getObjectAt(r3)
            r9 = r3
            org.bouncycastle.asn1.ASN1OctetString r9 = (org.bouncycastle.asn1.ASN1OctetString) r9
            r4 = r12
            r5 = r0
            r6 = r14
            r7 = r10
            r8 = r11
            r4.<init>(r5, r6, r7, r8, r9)
            org.bouncycastle.asn1.x9.X9FieldElement r3 = new org.bouncycastle.asn1.x9.X9FieldElement
            org.bouncycastle.asn1.ASN1Encodable r2 = r15.getObjectAt(r2)
            r9 = r2
            org.bouncycastle.asn1.ASN1OctetString r9 = (org.bouncycastle.asn1.ASN1OctetString) r9
            r4 = r3
            r4.<init>(r5, r6, r7, r8, r9)
            org.bouncycastle.math.ec.ECCurve$F2m r2 = new org.bouncycastle.math.ec.ECCurve$F2m
            org.bouncycastle.math.ec.ECFieldElement r4 = r12.getValue()
            java.math.BigInteger r9 = r4.toBigInteger()
            org.bouncycastle.math.ec.ECFieldElement r3 = r3.getValue()
            java.math.BigInteger r3 = r3.toBigInteger()
            r4 = r2
            r10 = r3
            r4.<init>(r5, r6, r7, r8, r9, r10)
            goto L_0x004e
        L_0x010d:
            int r14 = r15.size()
            r0 = 3
            if (r14 != r0) goto L_0x0120
            org.bouncycastle.asn1.ASN1Encodable r14 = r15.getObjectAt(r1)
            org.bouncycastle.asn1.DERBitString r14 = (org.bouncycastle.asn1.DERBitString) r14
            byte[] r14 = r14.getBytes()
            r13.b = r14
        L_0x0120:
            return
        L_0x0121:
            java.lang.IllegalArgumentException r14 = new java.lang.IllegalArgumentException
            java.lang.String r15 = "This type of EC basis is not implemented"
            r14.<init>(r15)
            throw r14
        L_0x0129:
            java.lang.IllegalArgumentException r14 = new java.lang.IllegalArgumentException
            java.lang.String r15 = "This type of ECCurve is not implemented"
            r14.<init>(r15)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.x9.X9Curve.<init>(org.bouncycastle.asn1.x9.X9FieldID, org.bouncycastle.asn1.ASN1Sequence):void");
    }

    public X9Curve(ECCurve eCCurve) {
        this.a = eCCurve;
        this.b = null;
        a();
    }

    public X9Curve(ECCurve eCCurve, byte[] bArr) {
        this.a = eCCurve;
        this.b = bArr;
        a();
    }

    private void a() {
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        if (ECAlgorithms.isFpCurve(this.a)) {
            aSN1ObjectIdentifier = prime_field;
        } else if (ECAlgorithms.isF2mCurve(this.a)) {
            aSN1ObjectIdentifier = characteristic_two_field;
        } else {
            throw new IllegalArgumentException("This type of ECCurve is not implemented");
        }
        this.c = aSN1ObjectIdentifier;
    }

    public ECCurve getCurve() {
        return this.a;
    }

    public byte[] getSeed() {
        return this.b;
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0060  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        /*
            r3 = this;
            org.bouncycastle.asn1.ASN1EncodableVector r0 = new org.bouncycastle.asn1.ASN1EncodableVector
            r0.<init>()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r3.c
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = prime_field
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0034
            org.bouncycastle.asn1.x9.X9FieldElement r1 = new org.bouncycastle.asn1.x9.X9FieldElement
            org.bouncycastle.math.ec.ECCurve r2 = r3.a
            org.bouncycastle.math.ec.ECFieldElement r2 = r2.getA()
            r1.<init>(r2)
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.toASN1Primitive()
            r0.add(r1)
            org.bouncycastle.asn1.x9.X9FieldElement r1 = new org.bouncycastle.asn1.x9.X9FieldElement
            org.bouncycastle.math.ec.ECCurve r2 = r3.a
            org.bouncycastle.math.ec.ECFieldElement r2 = r2.getB()
            r1.<init>(r2)
        L_0x002c:
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.toASN1Primitive()
            r0.add(r1)
            goto L_0x005c
        L_0x0034:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r3.c
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = characteristic_two_field
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x005c
            org.bouncycastle.asn1.x9.X9FieldElement r1 = new org.bouncycastle.asn1.x9.X9FieldElement
            org.bouncycastle.math.ec.ECCurve r2 = r3.a
            org.bouncycastle.math.ec.ECFieldElement r2 = r2.getA()
            r1.<init>(r2)
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.toASN1Primitive()
            r0.add(r1)
            org.bouncycastle.asn1.x9.X9FieldElement r1 = new org.bouncycastle.asn1.x9.X9FieldElement
            org.bouncycastle.math.ec.ECCurve r2 = r3.a
            org.bouncycastle.math.ec.ECFieldElement r2 = r2.getB()
            r1.<init>(r2)
            goto L_0x002c
        L_0x005c:
            byte[] r1 = r3.b
            if (r1 == 0) goto L_0x006a
            org.bouncycastle.asn1.DERBitString r1 = new org.bouncycastle.asn1.DERBitString
            byte[] r2 = r3.b
            r1.<init>(r2)
            r0.add(r1)
        L_0x006a:
            org.bouncycastle.asn1.DERSequence r1 = new org.bouncycastle.asn1.DERSequence
            r1.<init>(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.x9.X9Curve.toASN1Primitive():org.bouncycastle.asn1.ASN1Primitive");
    }
}
