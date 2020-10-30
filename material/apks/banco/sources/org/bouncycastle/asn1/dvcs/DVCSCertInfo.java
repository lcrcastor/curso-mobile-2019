package org.bouncycastle.asn1.dvcs;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.cmp.PKIStatusInfo;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.PolicyInformation;

public class DVCSCertInfo extends ASN1Object {
    private int a = 1;
    private DVCSRequestInformation b;
    private DigestInfo c;
    private ASN1Integer d;
    private DVCSTime e;
    private PKIStatusInfo f;
    private PolicyInformation g;
    private ASN1Set h;
    private ASN1Sequence i;
    private Extensions j;

    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r5.j = org.bouncycastle.asn1.x509.Extensions.getInstance(r0);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x0083 */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0051  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private DVCSCertInfo(org.bouncycastle.asn1.ASN1Sequence r6) {
        /*
            r5 = this;
            r5.<init>()
            r0 = 1
            r5.a = r0
            r1 = 0
            org.bouncycastle.asn1.ASN1Encodable r2 = r6.getObjectAt(r1)
            org.bouncycastle.asn1.ASN1Integer r3 = org.bouncycastle.asn1.ASN1Integer.getInstance(r2)     // Catch:{ IllegalArgumentException -> 0x001f }
            java.math.BigInteger r3 = r3.getValue()     // Catch:{ IllegalArgumentException -> 0x001f }
            int r3 = r3.intValue()     // Catch:{ IllegalArgumentException -> 0x001f }
            r5.a = r3     // Catch:{ IllegalArgumentException -> 0x001f }
            r3 = 2
            org.bouncycastle.asn1.ASN1Encodable r0 = r6.getObjectAt(r0)     // Catch:{ IllegalArgumentException -> 0x0020 }
            goto L_0x0021
        L_0x001f:
            r3 = 1
        L_0x0020:
            r0 = r2
        L_0x0021:
            org.bouncycastle.asn1.dvcs.DVCSRequestInformation r0 = org.bouncycastle.asn1.dvcs.DVCSRequestInformation.getInstance(r0)
            r5.b = r0
            int r0 = r3 + 1
            org.bouncycastle.asn1.ASN1Encodable r2 = r6.getObjectAt(r3)
            org.bouncycastle.asn1.x509.DigestInfo r2 = org.bouncycastle.asn1.x509.DigestInfo.getInstance(r2)
            r5.c = r2
            int r2 = r0 + 1
            org.bouncycastle.asn1.ASN1Encodable r0 = r6.getObjectAt(r0)
            org.bouncycastle.asn1.ASN1Integer r0 = org.bouncycastle.asn1.ASN1Integer.getInstance(r0)
            r5.d = r0
            int r0 = r2 + 1
            org.bouncycastle.asn1.ASN1Encodable r2 = r6.getObjectAt(r2)
            org.bouncycastle.asn1.dvcs.DVCSTime r2 = org.bouncycastle.asn1.dvcs.DVCSTime.getInstance(r2)
            r5.e = r2
        L_0x004b:
            int r2 = r6.size()
            if (r0 >= r2) goto L_0x008b
            int r2 = r0 + 1
            org.bouncycastle.asn1.ASN1Encodable r0 = r6.getObjectAt(r0)
            org.bouncycastle.asn1.ASN1TaggedObject r3 = org.bouncycastle.asn1.ASN1TaggedObject.getInstance(r0)     // Catch:{ IllegalArgumentException -> 0x0083 }
            int r4 = r3.getTagNo()     // Catch:{ IllegalArgumentException -> 0x0083 }
            switch(r4) {
                case 0: goto L_0x007c;
                case 1: goto L_0x0071;
                case 2: goto L_0x006a;
                case 3: goto L_0x0063;
                default: goto L_0x0062;
            }     // Catch:{ IllegalArgumentException -> 0x0083 }
        L_0x0062:
            goto L_0x0089
        L_0x0063:
            org.bouncycastle.asn1.ASN1Sequence r3 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r3, r1)     // Catch:{ IllegalArgumentException -> 0x0083 }
            r5.i = r3     // Catch:{ IllegalArgumentException -> 0x0083 }
            goto L_0x0089
        L_0x006a:
            org.bouncycastle.asn1.ASN1Set r3 = org.bouncycastle.asn1.ASN1Set.getInstance(r3, r1)     // Catch:{ IllegalArgumentException -> 0x0083 }
            r5.h = r3     // Catch:{ IllegalArgumentException -> 0x0083 }
            goto L_0x0089
        L_0x0071:
            org.bouncycastle.asn1.ASN1Sequence r3 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r3, r1)     // Catch:{ IllegalArgumentException -> 0x0083 }
            org.bouncycastle.asn1.x509.PolicyInformation r3 = org.bouncycastle.asn1.x509.PolicyInformation.getInstance(r3)     // Catch:{ IllegalArgumentException -> 0x0083 }
            r5.g = r3     // Catch:{ IllegalArgumentException -> 0x0083 }
            goto L_0x0089
        L_0x007c:
            org.bouncycastle.asn1.cmp.PKIStatusInfo r3 = org.bouncycastle.asn1.cmp.PKIStatusInfo.getInstance(r3, r1)     // Catch:{ IllegalArgumentException -> 0x0083 }
            r5.f = r3     // Catch:{ IllegalArgumentException -> 0x0083 }
            goto L_0x0089
        L_0x0083:
            org.bouncycastle.asn1.x509.Extensions r0 = org.bouncycastle.asn1.x509.Extensions.getInstance(r0)     // Catch:{ IllegalArgumentException -> 0x0089 }
            r5.j = r0     // Catch:{ IllegalArgumentException -> 0x0089 }
        L_0x0089:
            r0 = r2
            goto L_0x004b
        L_0x008b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.dvcs.DVCSCertInfo.<init>(org.bouncycastle.asn1.ASN1Sequence):void");
    }

    public DVCSCertInfo(DVCSRequestInformation dVCSRequestInformation, DigestInfo digestInfo, ASN1Integer aSN1Integer, DVCSTime dVCSTime) {
        this.b = dVCSRequestInformation;
        this.c = digestInfo;
        this.d = aSN1Integer;
        this.e = dVCSTime;
    }

    public static DVCSCertInfo getInstance(Object obj) {
        if (obj instanceof DVCSCertInfo) {
            return (DVCSCertInfo) obj;
        }
        if (obj != null) {
            return new DVCSCertInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static DVCSCertInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public TargetEtcChain[] getCerts() {
        if (this.i != null) {
            return TargetEtcChain.arrayFromSequence(this.i);
        }
        return null;
    }

    public DVCSRequestInformation getDvReqInfo() {
        return this.b;
    }

    public PKIStatusInfo getDvStatus() {
        return this.f;
    }

    public Extensions getExtensions() {
        return this.j;
    }

    public DigestInfo getMessageImprint() {
        return this.c;
    }

    public PolicyInformation getPolicy() {
        return this.g;
    }

    public ASN1Set getReqSignature() {
        return this.h;
    }

    public DVCSTime getResponseTime() {
        return this.e;
    }

    public ASN1Integer getSerialNumber() {
        return this.d;
    }

    public int getVersion() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != 1) {
            aSN1EncodableVector.add(new ASN1Integer((long) this.a));
        }
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(this.d);
        aSN1EncodableVector.add(this.e);
        if (this.f != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.f));
        }
        if (this.g != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, this.g));
        }
        if (this.h != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 2, this.h));
        }
        if (this.i != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 3, this.i));
        }
        if (this.j != null) {
            aSN1EncodableVector.add(this.j);
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("DVCSCertInfo {\n");
        if (this.a != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("version: ");
            sb.append(this.a);
            sb.append("\n");
            stringBuffer.append(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("dvReqInfo: ");
        sb2.append(this.b);
        sb2.append("\n");
        stringBuffer.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("messageImprint: ");
        sb3.append(this.c);
        sb3.append("\n");
        stringBuffer.append(sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append("serialNumber: ");
        sb4.append(this.d);
        sb4.append("\n");
        stringBuffer.append(sb4.toString());
        StringBuilder sb5 = new StringBuilder();
        sb5.append("responseTime: ");
        sb5.append(this.e);
        sb5.append("\n");
        stringBuffer.append(sb5.toString());
        if (this.f != null) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append("dvStatus: ");
            sb6.append(this.f);
            sb6.append("\n");
            stringBuffer.append(sb6.toString());
        }
        if (this.g != null) {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("policy: ");
            sb7.append(this.g);
            sb7.append("\n");
            stringBuffer.append(sb7.toString());
        }
        if (this.h != null) {
            StringBuilder sb8 = new StringBuilder();
            sb8.append("reqSignature: ");
            sb8.append(this.h);
            sb8.append("\n");
            stringBuffer.append(sb8.toString());
        }
        if (this.i != null) {
            StringBuilder sb9 = new StringBuilder();
            sb9.append("certs: ");
            sb9.append(this.i);
            sb9.append("\n");
            stringBuffer.append(sb9.toString());
        }
        if (this.j != null) {
            StringBuilder sb10 = new StringBuilder();
            sb10.append("extensions: ");
            sb10.append(this.j);
            sb10.append("\n");
            stringBuffer.append(sb10.toString());
        }
        stringBuffer.append("}\n");
        return stringBuffer.toString();
    }
}
