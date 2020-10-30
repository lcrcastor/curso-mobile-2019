package org.bouncycastle.asn1.x509;

import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class IssuingDistributionPoint extends ASN1Object {
    private DistributionPointName a;
    private boolean b;
    private boolean c;
    private ReasonFlags d;
    private boolean e;
    private boolean f;
    private ASN1Sequence g;

    private IssuingDistributionPoint(ASN1Sequence aSN1Sequence) {
        this.g = aSN1Sequence;
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            ASN1TaggedObject instance = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i));
            switch (instance.getTagNo()) {
                case 0:
                    this.a = DistributionPointName.getInstance(instance, true);
                    break;
                case 1:
                    this.b = ASN1Boolean.getInstance(instance, false).isTrue();
                    break;
                case 2:
                    this.c = ASN1Boolean.getInstance(instance, false).isTrue();
                    break;
                case 3:
                    this.d = new ReasonFlags(ReasonFlags.getInstance(instance, false));
                    break;
                case 4:
                    this.e = ASN1Boolean.getInstance(instance, false).isTrue();
                    break;
                case 5:
                    this.f = ASN1Boolean.getInstance(instance, false).isTrue();
                    break;
                default:
                    throw new IllegalArgumentException("unknown tag in IssuingDistributionPoint");
            }
        }
    }

    public IssuingDistributionPoint(DistributionPointName distributionPointName, boolean z, boolean z2) {
        this(distributionPointName, false, false, null, z, z2);
    }

    public IssuingDistributionPoint(DistributionPointName distributionPointName, boolean z, boolean z2, ReasonFlags reasonFlags, boolean z3, boolean z4) {
        this.a = distributionPointName;
        this.e = z3;
        this.f = z4;
        this.c = z2;
        this.b = z;
        this.d = reasonFlags;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (distributionPointName != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, distributionPointName));
        }
        if (z) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, ASN1Boolean.getInstance(true)));
        }
        if (z2) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 2, ASN1Boolean.getInstance(true)));
        }
        if (reasonFlags != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 3, reasonFlags));
        }
        if (z3) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 4, ASN1Boolean.getInstance(true)));
        }
        if (z4) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 5, ASN1Boolean.getInstance(true)));
        }
        this.g = new DERSequence(aSN1EncodableVector);
    }

    private String a(boolean z) {
        return z ? "true" : Reintento.Reintento_Falso;
    }

    private void a(StringBuffer stringBuffer, String str, String str2, String str3) {
        String str4 = "    ";
        stringBuffer.append(str4);
        stringBuffer.append(str2);
        stringBuffer.append(":");
        stringBuffer.append(str);
        stringBuffer.append(str4);
        stringBuffer.append(str4);
        stringBuffer.append(str3);
        stringBuffer.append(str);
    }

    public static IssuingDistributionPoint getInstance(Object obj) {
        if (obj instanceof IssuingDistributionPoint) {
            return (IssuingDistributionPoint) obj;
        }
        if (obj != null) {
            return new IssuingDistributionPoint(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static IssuingDistributionPoint getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public DistributionPointName getDistributionPoint() {
        return this.a;
    }

    public ReasonFlags getOnlySomeReasons() {
        return this.d;
    }

    public boolean isIndirectCRL() {
        return this.e;
    }

    public boolean onlyContainsAttributeCerts() {
        return this.f;
    }

    public boolean onlyContainsCACerts() {
        return this.c;
    }

    public boolean onlyContainsUserCerts() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.g;
    }

    public String toString() {
        String property = System.getProperty("line.separator");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("IssuingDistributionPoint: [");
        stringBuffer.append(property);
        if (this.a != null) {
            a(stringBuffer, property, "distributionPoint", this.a.toString());
        }
        if (this.b) {
            a(stringBuffer, property, "onlyContainsUserCerts", a(this.b));
        }
        if (this.c) {
            a(stringBuffer, property, "onlyContainsCACerts", a(this.c));
        }
        if (this.d != null) {
            a(stringBuffer, property, "onlySomeReasons", this.d.toString());
        }
        if (this.f) {
            a(stringBuffer, property, "onlyContainsAttributeCerts", a(this.f));
        }
        if (this.e) {
            a(stringBuffer, property, "indirectCRL", a(this.e));
        }
        stringBuffer.append("]");
        stringBuffer.append(property);
        return stringBuffer.toString();
    }
}
