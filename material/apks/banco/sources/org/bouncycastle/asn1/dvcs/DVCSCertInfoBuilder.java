package org.bouncycastle.asn1.dvcs;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.cmp.PKIStatusInfo;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.PolicyInformation;

public class DVCSCertInfoBuilder {
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

    public DVCSCertInfoBuilder(DVCSRequestInformation dVCSRequestInformation, DigestInfo digestInfo, ASN1Integer aSN1Integer, DVCSTime dVCSTime) {
        this.b = dVCSRequestInformation;
        this.c = digestInfo;
        this.d = aSN1Integer;
        this.e = dVCSTime;
    }

    public DVCSCertInfo build() {
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
        return DVCSCertInfo.getInstance(new DERSequence(aSN1EncodableVector));
    }

    public void setCerts(TargetEtcChain[] targetEtcChainArr) {
        this.i = new DERSequence((ASN1Encodable[]) targetEtcChainArr);
    }

    public void setDvReqInfo(DVCSRequestInformation dVCSRequestInformation) {
        this.b = dVCSRequestInformation;
    }

    public void setDvStatus(PKIStatusInfo pKIStatusInfo) {
        this.f = pKIStatusInfo;
    }

    public void setExtensions(Extensions extensions) {
        this.j = extensions;
    }

    public void setMessageImprint(DigestInfo digestInfo) {
        this.c = digestInfo;
    }

    public void setPolicy(PolicyInformation policyInformation) {
        this.g = policyInformation;
    }

    public void setReqSignature(ASN1Set aSN1Set) {
        this.h = aSN1Set;
    }

    public void setResponseTime(DVCSTime dVCSTime) {
        this.e = dVCSTime;
    }

    public void setSerialNumber(ASN1Integer aSN1Integer) {
        this.d = aSN1Integer;
    }

    public void setVersion(int i2) {
        this.a = i2;
    }
}
