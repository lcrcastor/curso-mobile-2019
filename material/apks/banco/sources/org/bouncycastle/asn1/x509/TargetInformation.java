package org.bouncycastle.asn1.x509;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class TargetInformation extends ASN1Object {
    private ASN1Sequence a;

    private TargetInformation(ASN1Sequence aSN1Sequence) {
        this.a = aSN1Sequence;
    }

    public TargetInformation(Targets targets) {
        this.a = new DERSequence((ASN1Encodable) targets);
    }

    public TargetInformation(Target[] targetArr) {
        this(new Targets(targetArr));
    }

    public static TargetInformation getInstance(Object obj) {
        if (obj instanceof TargetInformation) {
            return (TargetInformation) obj;
        }
        if (obj != null) {
            return new TargetInformation(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public Targets[] getTargetsObjects() {
        Targets[] targetsArr = new Targets[this.a.size()];
        Enumeration objects = this.a.getObjects();
        int i = 0;
        while (objects.hasMoreElements()) {
            int i2 = i + 1;
            targetsArr[i] = Targets.getInstance(objects.nextElement());
            i = i2;
        }
        return targetsArr;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }
}
