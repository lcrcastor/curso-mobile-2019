package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;

public class RecipientKeyIdentifier extends ASN1Object {
    private ASN1OctetString a;
    private ASN1GeneralizedTime b;
    private OtherKeyAttribute c;

    public RecipientKeyIdentifier(ASN1OctetString aSN1OctetString, ASN1GeneralizedTime aSN1GeneralizedTime, OtherKeyAttribute otherKeyAttribute) {
        this.a = aSN1OctetString;
        this.b = aSN1GeneralizedTime;
        this.c = otherKeyAttribute;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0046, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0029, code lost:
        r3.c = org.bouncycastle.asn1.cms.OtherKeyAttribute.getInstance(r4.getObjectAt(2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0033, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public RecipientKeyIdentifier(org.bouncycastle.asn1.ASN1Sequence r4) {
        /*
            r3 = this;
            r3.<init>()
            r0 = 0
            org.bouncycastle.asn1.ASN1Encodable r0 = r4.getObjectAt(r0)
            org.bouncycastle.asn1.ASN1OctetString r0 = org.bouncycastle.asn1.ASN1OctetString.getInstance(r0)
            r3.a = r0
            int r0 = r4.size()
            r1 = 2
            r2 = 1
            switch(r0) {
                case 1: goto L_0x0046;
                case 2: goto L_0x0034;
                case 3: goto L_0x001f;
                default: goto L_0x0017;
            }
        L_0x0017:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Invalid RecipientKeyIdentifier"
            r4.<init>(r0)
            throw r4
        L_0x001f:
            org.bouncycastle.asn1.ASN1Encodable r0 = r4.getObjectAt(r2)
            org.bouncycastle.asn1.ASN1GeneralizedTime r0 = org.bouncycastle.asn1.ASN1GeneralizedTime.getInstance(r0)
            r3.b = r0
        L_0x0029:
            org.bouncycastle.asn1.ASN1Encodable r4 = r4.getObjectAt(r1)
            org.bouncycastle.asn1.cms.OtherKeyAttribute r4 = org.bouncycastle.asn1.cms.OtherKeyAttribute.getInstance(r4)
            r3.c = r4
            return
        L_0x0034:
            org.bouncycastle.asn1.ASN1Encodable r0 = r4.getObjectAt(r2)
            boolean r0 = r0 instanceof org.bouncycastle.asn1.ASN1GeneralizedTime
            if (r0 == 0) goto L_0x0029
            org.bouncycastle.asn1.ASN1Encodable r4 = r4.getObjectAt(r2)
            org.bouncycastle.asn1.ASN1GeneralizedTime r4 = org.bouncycastle.asn1.ASN1GeneralizedTime.getInstance(r4)
            r3.b = r4
        L_0x0046:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.cms.RecipientKeyIdentifier.<init>(org.bouncycastle.asn1.ASN1Sequence):void");
    }

    public RecipientKeyIdentifier(byte[] bArr) {
        this(bArr, (ASN1GeneralizedTime) null, (OtherKeyAttribute) null);
    }

    public RecipientKeyIdentifier(byte[] bArr, ASN1GeneralizedTime aSN1GeneralizedTime, OtherKeyAttribute otherKeyAttribute) {
        this.a = new DEROctetString(bArr);
        this.b = aSN1GeneralizedTime;
        this.c = otherKeyAttribute;
    }

    public static RecipientKeyIdentifier getInstance(Object obj) {
        if (obj instanceof RecipientKeyIdentifier) {
            return (RecipientKeyIdentifier) obj;
        }
        if (obj != null) {
            return new RecipientKeyIdentifier(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static RecipientKeyIdentifier getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public ASN1GeneralizedTime getDate() {
        return this.b;
    }

    public OtherKeyAttribute getOtherKeyAttribute() {
        return this.c;
    }

    public ASN1OctetString getSubjectKeyIdentifier() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.b != null) {
            aSN1EncodableVector.add(this.b);
        }
        if (this.c != null) {
            aSN1EncodableVector.add(this.c);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
