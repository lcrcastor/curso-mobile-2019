package org.bouncycastle.asn1.dvcs;

import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.PolicyInformation;

public class PathProcInput extends ASN1Object {
    private PolicyInformation[] a;
    private boolean b = false;
    private boolean c = false;
    private boolean d = false;

    public PathProcInput(PolicyInformation[] policyInformationArr) {
        this.a = policyInformationArr;
    }

    public PathProcInput(PolicyInformation[] policyInformationArr, boolean z, boolean z2, boolean z3) {
        this.a = policyInformationArr;
        this.b = z;
        this.c = z2;
        this.d = z3;
    }

    private void a(boolean z) {
        this.b = z;
    }

    private static PolicyInformation[] a(ASN1Sequence aSN1Sequence) {
        PolicyInformation[] policyInformationArr = new PolicyInformation[aSN1Sequence.size()];
        for (int i = 0; i != policyInformationArr.length; i++) {
            policyInformationArr[i] = PolicyInformation.getInstance(aSN1Sequence.getObjectAt(i));
        }
        return policyInformationArr;
    }

    private void b(boolean z) {
        this.c = z;
    }

    private void c(boolean z) {
        this.d = z;
    }

    public static PathProcInput getInstance(Object obj) {
        if (obj instanceof PathProcInput) {
            return (PathProcInput) obj;
        }
        if (obj == null) {
            return null;
        }
        ASN1Sequence instance = ASN1Sequence.getInstance(obj);
        PathProcInput pathProcInput = new PathProcInput(a(ASN1Sequence.getInstance(instance.getObjectAt(0))));
        for (int i = 1; i < instance.size(); i++) {
            ASN1Encodable objectAt = instance.getObjectAt(i);
            if (objectAt instanceof ASN1Boolean) {
                pathProcInput.a(ASN1Boolean.getInstance((Object) objectAt).isTrue());
            } else if (objectAt instanceof ASN1TaggedObject) {
                ASN1TaggedObject instance2 = ASN1TaggedObject.getInstance(objectAt);
                switch (instance2.getTagNo()) {
                    case 0:
                        pathProcInput.b(ASN1Boolean.getInstance(instance2, false).isTrue());
                        break;
                    case 1:
                        pathProcInput.c(ASN1Boolean.getInstance(instance2, false).isTrue());
                        break;
                }
            }
        }
        return pathProcInput;
    }

    public static PathProcInput getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public PolicyInformation[] getAcceptablePolicySet() {
        return this.a;
    }

    public boolean isExplicitPolicyReqd() {
        return this.c;
    }

    public boolean isInhibitAnyPolicy() {
        return this.d;
    }

    public boolean isInhibitPolicyMapping() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        for (int i = 0; i != this.a.length; i++) {
            aSN1EncodableVector2.add(this.a[i]);
        }
        aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        if (this.b) {
            aSN1EncodableVector.add(new ASN1Boolean(this.b));
        }
        if (this.c) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, new ASN1Boolean(this.c)));
        }
        if (this.d) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, new ASN1Boolean(this.d)));
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PathProcInput: {\nacceptablePolicySet: ");
        sb.append(this.a);
        sb.append("\n");
        sb.append("inhibitPolicyMapping: ");
        sb.append(this.b);
        sb.append("\n");
        sb.append("explicitPolicyReqd: ");
        sb.append(this.c);
        sb.append("\n");
        sb.append("inhibitAnyPolicy: ");
        sb.append(this.d);
        sb.append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
