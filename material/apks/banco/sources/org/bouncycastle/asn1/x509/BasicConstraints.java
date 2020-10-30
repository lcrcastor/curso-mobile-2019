package org.bouncycastle.asn1.x509;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

public class BasicConstraints extends ASN1Object {
    ASN1Boolean a;
    ASN1Integer b;

    public BasicConstraints(int i) {
        this.a = ASN1Boolean.getInstance(false);
        this.b = null;
        this.a = ASN1Boolean.getInstance(true);
        this.b = new ASN1Integer((long) i);
    }

    private BasicConstraints(ASN1Sequence aSN1Sequence) {
        this.a = ASN1Boolean.getInstance(false);
        this.b = null;
        if (aSN1Sequence.size() == 0) {
            this.a = null;
            this.b = null;
            return;
        }
        if (aSN1Sequence.getObjectAt(0) instanceof ASN1Boolean) {
            this.a = ASN1Boolean.getInstance((Object) aSN1Sequence.getObjectAt(0));
        } else {
            this.a = null;
            this.b = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
        }
        if (aSN1Sequence.size() <= 1) {
            return;
        }
        if (this.a != null) {
            this.b = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1));
            return;
        }
        throw new IllegalArgumentException("wrong sequence in constructor");
    }

    public BasicConstraints(boolean z) {
        this.a = ASN1Boolean.getInstance(false);
        this.b = null;
        if (z) {
            this.a = ASN1Boolean.getInstance(true);
        } else {
            this.a = null;
        }
        this.b = null;
    }

    public static BasicConstraints fromExtensions(Extensions extensions) {
        return getInstance(extensions.getExtensionParsedValue(Extension.basicConstraints));
    }

    public static BasicConstraints getInstance(Object obj) {
        if (obj instanceof BasicConstraints) {
            return (BasicConstraints) obj;
        }
        if (obj instanceof X509Extension) {
            return getInstance(X509Extension.convertValueToObject((X509Extension) obj));
        }
        if (obj != null) {
            return new BasicConstraints(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static BasicConstraints getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public BigInteger getPathLenConstraint() {
        if (this.b != null) {
            return this.b.getValue();
        }
        return null;
    }

    public boolean isCA() {
        return this.a != null && this.a.isTrue();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(this.a);
        }
        if (this.b != null) {
            aSN1EncodableVector.add(this.b);
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        StringBuilder sb;
        if (this.b != null) {
            sb = new StringBuilder();
            sb.append("BasicConstraints: isCa(");
            sb.append(isCA());
            sb.append("), pathLenConstraint = ");
            sb.append(this.b.getValue());
        } else if (this.a == null) {
            return "BasicConstraints: isCa(false)";
        } else {
            sb = new StringBuilder();
            sb.append("BasicConstraints: isCa(");
            sb.append(isCA());
            sb.append(")");
        }
        return sb.toString();
    }
}
