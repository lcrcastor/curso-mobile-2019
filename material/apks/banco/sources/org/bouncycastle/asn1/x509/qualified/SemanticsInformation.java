package org.bouncycastle.asn1.x509.qualified;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.GeneralName;

public class SemanticsInformation extends ASN1Object {
    private ASN1ObjectIdentifier a;
    private GeneralName[] b;

    public SemanticsInformation(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.a = aSN1ObjectIdentifier;
        this.b = null;
    }

    public SemanticsInformation(ASN1ObjectIdentifier aSN1ObjectIdentifier, GeneralName[] generalNameArr) {
        this.a = aSN1ObjectIdentifier;
        this.b = generalNameArr;
    }

    private SemanticsInformation(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        if (aSN1Sequence.size() < 1) {
            throw new IllegalArgumentException("no objects in SemanticsInformation");
        }
        Object nextElement = objects.nextElement();
        if (nextElement instanceof ASN1ObjectIdentifier) {
            this.a = ASN1ObjectIdentifier.getInstance(nextElement);
            nextElement = objects.hasMoreElements() ? objects.nextElement() : null;
        }
        if (nextElement != null) {
            ASN1Sequence instance = ASN1Sequence.getInstance(nextElement);
            this.b = new GeneralName[instance.size()];
            for (int i = 0; i < instance.size(); i++) {
                this.b[i] = GeneralName.getInstance(instance.getObjectAt(i));
            }
        }
    }

    public SemanticsInformation(GeneralName[] generalNameArr) {
        this.a = null;
        this.b = generalNameArr;
    }

    public static SemanticsInformation getInstance(Object obj) {
        if (obj instanceof SemanticsInformation) {
            return (SemanticsInformation) obj;
        }
        if (obj != null) {
            return new SemanticsInformation(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public GeneralName[] getNameRegistrationAuthorities() {
        return this.b;
    }

    public ASN1ObjectIdentifier getSemanticsIdentifier() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(this.a);
        }
        if (this.b != null) {
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            for (GeneralName add : this.b) {
                aSN1EncodableVector2.add(add);
            }
            aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
