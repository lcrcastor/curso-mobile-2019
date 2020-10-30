package org.bouncycastle.asn1.x509;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1UTCTime;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.X500Name;

public class TBSCertList extends ASN1Object {
    ASN1Integer a;
    AlgorithmIdentifier b;
    X500Name c;
    Time d;
    Time e;
    ASN1Sequence f;
    Extensions g;

    public static class CRLEntry extends ASN1Object {
        ASN1Sequence a;
        Extensions b;

        private CRLEntry(ASN1Sequence aSN1Sequence) {
            if (aSN1Sequence.size() < 2 || aSN1Sequence.size() > 3) {
                StringBuilder sb = new StringBuilder();
                sb.append("Bad sequence size: ");
                sb.append(aSN1Sequence.size());
                throw new IllegalArgumentException(sb.toString());
            }
            this.a = aSN1Sequence;
        }

        public static CRLEntry getInstance(Object obj) {
            if (obj instanceof CRLEntry) {
                return (CRLEntry) obj;
            }
            if (obj != null) {
                return new CRLEntry(ASN1Sequence.getInstance(obj));
            }
            return null;
        }

        public Extensions getExtensions() {
            if (this.b == null && this.a.size() == 3) {
                this.b = Extensions.getInstance(this.a.getObjectAt(2));
            }
            return this.b;
        }

        public Time getRevocationDate() {
            return Time.getInstance(this.a.getObjectAt(1));
        }

        public ASN1Integer getUserCertificate() {
            return ASN1Integer.getInstance(this.a.getObjectAt(0));
        }

        public boolean hasExtensions() {
            return this.a.size() == 3;
        }

        public ASN1Primitive toASN1Primitive() {
            return this.a;
        }
    }

    class EmptyEnumeration implements Enumeration {
        private EmptyEnumeration() {
        }

        public boolean hasMoreElements() {
            return false;
        }

        public Object nextElement() {
            return null;
        }
    }

    class RevokedCertificatesEnumeration implements Enumeration {
        private final Enumeration b;

        RevokedCertificatesEnumeration(Enumeration enumeration) {
            this.b = enumeration;
        }

        public boolean hasMoreElements() {
            return this.b.hasMoreElements();
        }

        public Object nextElement() {
            return CRLEntry.getInstance(this.b.nextElement());
        }
    }

    public TBSCertList(ASN1Sequence aSN1Sequence) {
        int i;
        if (aSN1Sequence.size() < 3 || aSN1Sequence.size() > 7) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        int i2 = 0;
        if (aSN1Sequence.getObjectAt(0) instanceof ASN1Integer) {
            this.a = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
            i2 = 1;
        } else {
            this.a = null;
        }
        int i3 = i2 + 1;
        this.b = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i2));
        int i4 = i3 + 1;
        this.c = X500Name.getInstance(aSN1Sequence.getObjectAt(i3));
        int i5 = i4 + 1;
        this.d = Time.getInstance(aSN1Sequence.getObjectAt(i4));
        if (i5 >= aSN1Sequence.size() || (!(aSN1Sequence.getObjectAt(i5) instanceof ASN1UTCTime) && !(aSN1Sequence.getObjectAt(i5) instanceof ASN1GeneralizedTime) && !(aSN1Sequence.getObjectAt(i5) instanceof Time))) {
            i = i5;
        } else {
            i = i5 + 1;
            this.e = Time.getInstance(aSN1Sequence.getObjectAt(i5));
        }
        if (i < aSN1Sequence.size() && !(aSN1Sequence.getObjectAt(i) instanceof DERTaggedObject)) {
            int i6 = i + 1;
            this.f = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(i));
            i = i6;
        }
        if (i < aSN1Sequence.size() && (aSN1Sequence.getObjectAt(i) instanceof DERTaggedObject)) {
            this.g = Extensions.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(i), true));
        }
    }

    public static TBSCertList getInstance(Object obj) {
        if (obj instanceof TBSCertList) {
            return (TBSCertList) obj;
        }
        if (obj != null) {
            return new TBSCertList(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static TBSCertList getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public Extensions getExtensions() {
        return this.g;
    }

    public X500Name getIssuer() {
        return this.c;
    }

    public Time getNextUpdate() {
        return this.e;
    }

    public Enumeration getRevokedCertificateEnumeration() {
        return this.f == null ? new EmptyEnumeration() : new RevokedCertificatesEnumeration(this.f.getObjects());
    }

    public CRLEntry[] getRevokedCertificates() {
        if (this.f == null) {
            return new CRLEntry[0];
        }
        CRLEntry[] cRLEntryArr = new CRLEntry[this.f.size()];
        for (int i = 0; i < cRLEntryArr.length; i++) {
            cRLEntryArr[i] = CRLEntry.getInstance(this.f.getObjectAt(i));
        }
        return cRLEntryArr;
    }

    public AlgorithmIdentifier getSignature() {
        return this.b;
    }

    public Time getThisUpdate() {
        return this.d;
    }

    public ASN1Integer getVersion() {
        return this.a;
    }

    public int getVersionNumber() {
        if (this.a == null) {
            return 1;
        }
        return this.a.getValue().intValue() + 1;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(this.a);
        }
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(this.d);
        if (this.e != null) {
            aSN1EncodableVector.add(this.e);
        }
        if (this.f != null) {
            aSN1EncodableVector.add(this.f);
        }
        if (this.g != null) {
            aSN1EncodableVector.add(new DERTaggedObject(0, this.g));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
