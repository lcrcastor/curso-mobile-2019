package org.bouncycastle.asn1.x9;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

public class DHDomainParameters extends ASN1Object {
    private ASN1Integer a;
    private ASN1Integer b;
    private ASN1Integer c;
    private ASN1Integer d;
    private DHValidationParms e;

    public DHDomainParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, DHValidationParms dHValidationParms) {
        if (bigInteger == null) {
            throw new IllegalArgumentException("'p' cannot be null");
        } else if (bigInteger2 == null) {
            throw new IllegalArgumentException("'g' cannot be null");
        } else if (bigInteger3 == null) {
            throw new IllegalArgumentException("'q' cannot be null");
        } else {
            this.a = new ASN1Integer(bigInteger);
            this.b = new ASN1Integer(bigInteger2);
            this.c = new ASN1Integer(bigInteger3);
            this.d = new ASN1Integer(bigInteger4);
            this.e = dHValidationParms;
        }
    }

    public DHDomainParameters(ASN1Integer aSN1Integer, ASN1Integer aSN1Integer2, ASN1Integer aSN1Integer3, ASN1Integer aSN1Integer4, DHValidationParms dHValidationParms) {
        if (aSN1Integer == null) {
            throw new IllegalArgumentException("'p' cannot be null");
        } else if (aSN1Integer2 == null) {
            throw new IllegalArgumentException("'g' cannot be null");
        } else if (aSN1Integer3 == null) {
            throw new IllegalArgumentException("'q' cannot be null");
        } else {
            this.a = aSN1Integer;
            this.b = aSN1Integer2;
            this.c = aSN1Integer3;
            this.d = aSN1Integer4;
            this.e = dHValidationParms;
        }
    }

    private DHDomainParameters(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 3 || aSN1Sequence.size() > 5) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = ASN1Integer.getInstance(objects.nextElement());
        this.b = ASN1Integer.getInstance(objects.nextElement());
        this.c = ASN1Integer.getInstance(objects.nextElement());
        ASN1Encodable a2 = a(objects);
        if (a2 != null && (a2 instanceof ASN1Integer)) {
            this.d = ASN1Integer.getInstance(a2);
            a2 = a(objects);
        }
        if (a2 != null) {
            this.e = DHValidationParms.getInstance(a2.toASN1Primitive());
        }
    }

    private static ASN1Encodable a(Enumeration enumeration) {
        if (enumeration.hasMoreElements()) {
            return (ASN1Encodable) enumeration.nextElement();
        }
        return null;
    }

    public static DHDomainParameters getInstance(Object obj) {
        if (obj == null || (obj instanceof DHDomainParameters)) {
            return (DHDomainParameters) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new DHDomainParameters((ASN1Sequence) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid DHDomainParameters: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static DHDomainParameters getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public ASN1Integer getG() {
        return this.b;
    }

    public ASN1Integer getJ() {
        return this.d;
    }

    public ASN1Integer getP() {
        return this.a;
    }

    public ASN1Integer getQ() {
        return this.c;
    }

    public DHValidationParms getValidationParms() {
        return this.e;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        if (this.d != null) {
            aSN1EncodableVector.add(this.d);
        }
        if (this.e != null) {
            aSN1EncodableVector.add(this.e);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
