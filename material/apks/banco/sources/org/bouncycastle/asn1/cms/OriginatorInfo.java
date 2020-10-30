package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class OriginatorInfo extends ASN1Object {
    private ASN1Set a;
    private ASN1Set b;

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x005b, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0026, code lost:
        r3.b = org.bouncycastle.asn1.ASN1Set.getInstance(r4, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x002c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private OriginatorInfo(org.bouncycastle.asn1.ASN1Sequence r4) {
        /*
            r3 = this;
            r3.<init>()
            int r0 = r4.size()
            r1 = 0
            switch(r0) {
                case 0: goto L_0x005b;
                case 1: goto L_0x002d;
                case 2: goto L_0x0013;
                default: goto L_0x000b;
            }
        L_0x000b:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "OriginatorInfo too big"
            r4.<init>(r0)
            throw r4
        L_0x0013:
            org.bouncycastle.asn1.ASN1Encodable r0 = r4.getObjectAt(r1)
            org.bouncycastle.asn1.ASN1TaggedObject r0 = (org.bouncycastle.asn1.ASN1TaggedObject) r0
            org.bouncycastle.asn1.ASN1Set r0 = org.bouncycastle.asn1.ASN1Set.getInstance(r0, r1)
            r3.a = r0
            r0 = 1
            org.bouncycastle.asn1.ASN1Encodable r4 = r4.getObjectAt(r0)
            org.bouncycastle.asn1.ASN1TaggedObject r4 = (org.bouncycastle.asn1.ASN1TaggedObject) r4
        L_0x0026:
            org.bouncycastle.asn1.ASN1Set r4 = org.bouncycastle.asn1.ASN1Set.getInstance(r4, r1)
            r3.b = r4
            return
        L_0x002d:
            org.bouncycastle.asn1.ASN1Encodable r4 = r4.getObjectAt(r1)
            org.bouncycastle.asn1.ASN1TaggedObject r4 = (org.bouncycastle.asn1.ASN1TaggedObject) r4
            int r0 = r4.getTagNo()
            switch(r0) {
                case 0: goto L_0x0055;
                case 1: goto L_0x0026;
                default: goto L_0x003a;
            }
        L_0x003a:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Bad tag in OriginatorInfo: "
            r1.append(r2)
            int r4 = r4.getTagNo()
            r1.append(r4)
            java.lang.String r4 = r1.toString()
            r0.<init>(r4)
            throw r0
        L_0x0055:
            org.bouncycastle.asn1.ASN1Set r4 = org.bouncycastle.asn1.ASN1Set.getInstance(r4, r1)
            r3.a = r4
        L_0x005b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.cms.OriginatorInfo.<init>(org.bouncycastle.asn1.ASN1Sequence):void");
    }

    public OriginatorInfo(ASN1Set aSN1Set, ASN1Set aSN1Set2) {
        this.a = aSN1Set;
        this.b = aSN1Set2;
    }

    public static OriginatorInfo getInstance(Object obj) {
        if (obj instanceof OriginatorInfo) {
            return (OriginatorInfo) obj;
        }
        if (obj != null) {
            return new OriginatorInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static OriginatorInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public ASN1Set getCRLs() {
        return this.b;
    }

    public ASN1Set getCertificates() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.a));
        }
        if (this.b != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, this.b));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
