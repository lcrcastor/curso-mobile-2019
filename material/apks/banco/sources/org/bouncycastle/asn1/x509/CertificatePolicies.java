package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

public class CertificatePolicies extends ASN1Object {
    private final PolicyInformation[] a;

    private CertificatePolicies(ASN1Sequence aSN1Sequence) {
        this.a = new PolicyInformation[aSN1Sequence.size()];
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            this.a[i] = PolicyInformation.getInstance(aSN1Sequence.getObjectAt(i));
        }
    }

    public CertificatePolicies(PolicyInformation policyInformation) {
        this.a = new PolicyInformation[]{policyInformation};
    }

    public CertificatePolicies(PolicyInformation[] policyInformationArr) {
        this.a = policyInformationArr;
    }

    public static CertificatePolicies fromExtensions(Extensions extensions) {
        return getInstance(extensions.getExtensionParsedValue(Extension.certificatePolicies));
    }

    public static CertificatePolicies getInstance(Object obj) {
        if (obj instanceof CertificatePolicies) {
            return (CertificatePolicies) obj;
        }
        if (obj != null) {
            return new CertificatePolicies(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static CertificatePolicies getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public PolicyInformation getPolicyInformation(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        for (int i = 0; i != this.a.length; i++) {
            if (aSN1ObjectIdentifier.equals(this.a[i].getPolicyIdentifier())) {
                return this.a[i];
            }
        }
        return null;
    }

    public PolicyInformation[] getPolicyInformation() {
        PolicyInformation[] policyInformationArr = new PolicyInformation[this.a.length];
        System.arraycopy(this.a, 0, policyInformationArr, 0, this.a.length);
        return policyInformationArr;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence((ASN1Encodable[]) this.a);
    }

    public String toString() {
        String str = null;
        for (PolicyInformation append : this.a) {
            if (str != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(", ");
                str = sb.toString();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(append);
            str = sb2.toString();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("CertificatePolicies: ");
        sb3.append(str);
        return sb3.toString();
    }
}
