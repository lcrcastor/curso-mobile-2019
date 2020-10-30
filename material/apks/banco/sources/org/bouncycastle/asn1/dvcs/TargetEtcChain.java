package org.bouncycastle.asn1.dvcs;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class TargetEtcChain extends ASN1Object {
    private CertEtcToken a;
    private ASN1Sequence b;
    private PathProcInput c;

    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001c */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002a A[Catch:{ IllegalArgumentException | IndexOutOfBoundsException -> 0x0031 }, RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x002b A[Catch:{ IllegalArgumentException | IndexOutOfBoundsException -> 0x0031 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private TargetEtcChain(org.bouncycastle.asn1.ASN1Sequence r4) {
        /*
            r3 = this;
            r3.<init>()
            r0 = 0
            org.bouncycastle.asn1.ASN1Encodable r1 = r4.getObjectAt(r0)
            org.bouncycastle.asn1.dvcs.CertEtcToken r1 = org.bouncycastle.asn1.dvcs.CertEtcToken.getInstance(r1)
            r3.a = r1
            r1 = 2
            r2 = 1
            org.bouncycastle.asn1.ASN1Encodable r2 = r4.getObjectAt(r2)     // Catch:{ IllegalArgumentException -> 0x001c, IndexOutOfBoundsException -> 0x001b }
            org.bouncycastle.asn1.ASN1Sequence r2 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r2)     // Catch:{ IllegalArgumentException -> 0x001c, IndexOutOfBoundsException -> 0x001b }
            r3.b = r2     // Catch:{ IllegalArgumentException -> 0x001c, IndexOutOfBoundsException -> 0x001b }
            goto L_0x001c
        L_0x001b:
            return
        L_0x001c:
            org.bouncycastle.asn1.ASN1Encodable r4 = r4.getObjectAt(r1)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException -> 0x0031 }
            org.bouncycastle.asn1.ASN1TaggedObject r4 = org.bouncycastle.asn1.ASN1TaggedObject.getInstance(r4)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException -> 0x0031 }
            int r1 = r4.getTagNo()     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException -> 0x0031 }
            if (r1 == 0) goto L_0x002b
            return
        L_0x002b:
            org.bouncycastle.asn1.dvcs.PathProcInput r4 = org.bouncycastle.asn1.dvcs.PathProcInput.getInstance(r4, r0)     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException -> 0x0031 }
            r3.c = r4     // Catch:{ IllegalArgumentException | IndexOutOfBoundsException -> 0x0031 }
        L_0x0031:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.dvcs.TargetEtcChain.<init>(org.bouncycastle.asn1.ASN1Sequence):void");
    }

    public TargetEtcChain(CertEtcToken certEtcToken) {
        this(certEtcToken, null, null);
    }

    public TargetEtcChain(CertEtcToken certEtcToken, PathProcInput pathProcInput) {
        this(certEtcToken, null, pathProcInput);
    }

    public TargetEtcChain(CertEtcToken certEtcToken, CertEtcToken[] certEtcTokenArr) {
        this(certEtcToken, certEtcTokenArr, null);
    }

    public TargetEtcChain(CertEtcToken certEtcToken, CertEtcToken[] certEtcTokenArr, PathProcInput pathProcInput) {
        this.a = certEtcToken;
        if (certEtcTokenArr != null) {
            this.b = new DERSequence((ASN1Encodable[]) certEtcTokenArr);
        }
        this.c = pathProcInput;
    }

    public static TargetEtcChain[] arrayFromSequence(ASN1Sequence aSN1Sequence) {
        TargetEtcChain[] targetEtcChainArr = new TargetEtcChain[aSN1Sequence.size()];
        for (int i = 0; i != targetEtcChainArr.length; i++) {
            targetEtcChainArr[i] = getInstance(aSN1Sequence.getObjectAt(i));
        }
        return targetEtcChainArr;
    }

    public static TargetEtcChain getInstance(Object obj) {
        if (obj instanceof TargetEtcChain) {
            return (TargetEtcChain) obj;
        }
        if (obj != null) {
            return new TargetEtcChain(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static TargetEtcChain getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public CertEtcToken[] getChain() {
        if (this.b != null) {
            return CertEtcToken.arrayFromSequence(this.b);
        }
        return null;
    }

    public PathProcInput getPathProcInput() {
        return this.c;
    }

    public CertEtcToken getTarget() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.b != null) {
            aSN1EncodableVector.add(this.b);
        }
        if (this.c != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.c));
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("TargetEtcChain {\n");
        StringBuilder sb = new StringBuilder();
        sb.append("target: ");
        sb.append(this.a);
        sb.append("\n");
        stringBuffer.append(sb.toString());
        if (this.b != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("chain: ");
            sb2.append(this.b);
            sb2.append("\n");
            stringBuffer.append(sb2.toString());
        }
        if (this.c != null) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("pathProcInput: ");
            sb3.append(this.c);
            sb3.append("\n");
            stringBuffer.append(sb3.toString());
        }
        stringBuffer.append("}\n");
        return stringBuffer.toString();
    }
}
