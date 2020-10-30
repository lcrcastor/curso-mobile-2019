package org.bouncycastle.asn1.x509;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1UTCTime;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.X500Name;

public class V2TBSCertListGenerator {
    private static final ASN1Sequence[] h = new ASN1Sequence[11];
    private ASN1Integer a = new ASN1Integer(1);
    private AlgorithmIdentifier b;
    private X500Name c;
    private Time d;
    private Time e = null;
    private Extensions f = null;
    private ASN1EncodableVector g = new ASN1EncodableVector();

    static {
        h[0] = a(0);
        h[1] = a(1);
        h[2] = a(2);
        h[3] = a(3);
        h[4] = a(4);
        h[5] = a(5);
        h[6] = a(6);
        h[7] = a(7);
        h[8] = a(8);
        h[9] = a(9);
        h[10] = a(10);
    }

    private static ASN1Sequence a(int i) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        CRLReason lookup = CRLReason.lookup(i);
        try {
            aSN1EncodableVector.add(Extension.reasonCode);
            aSN1EncodableVector.add(new DEROctetString(lookup.getEncoded()));
            return new DERSequence(aSN1EncodableVector);
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("error encoding reason: ");
            sb.append(e2);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private static ASN1Sequence a(ASN1GeneralizedTime aSN1GeneralizedTime) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        try {
            aSN1EncodableVector.add(Extension.invalidityDate);
            aSN1EncodableVector.add(new DEROctetString(aSN1GeneralizedTime.getEncoded()));
            return new DERSequence(aSN1EncodableVector);
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("error encoding reason: ");
            sb.append(e2);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private void a(ASN1Integer aSN1Integer, Time time, ASN1Sequence aSN1Sequence) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(aSN1Integer);
        aSN1EncodableVector.add(time);
        if (aSN1Sequence != null) {
            aSN1EncodableVector.add(aSN1Sequence);
        }
        addCRLEntry(new DERSequence(aSN1EncodableVector));
    }

    public void addCRLEntry(ASN1Integer aSN1Integer, ASN1UTCTime aSN1UTCTime, int i) {
        addCRLEntry(aSN1Integer, new Time((ASN1Primitive) aSN1UTCTime), i);
    }

    public void addCRLEntry(ASN1Integer aSN1Integer, Time time, int i) {
        addCRLEntry(aSN1Integer, time, i, null);
    }

    public void addCRLEntry(ASN1Integer aSN1Integer, Time time, int i, ASN1GeneralizedTime aSN1GeneralizedTime) {
        ASN1Sequence aSN1Sequence;
        if (i != 0) {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            if (i >= h.length) {
                aSN1Sequence = a(i);
            } else if (i < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("invalid reason value: ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
            } else {
                aSN1Sequence = h[i];
            }
            aSN1EncodableVector.add(aSN1Sequence);
            if (aSN1GeneralizedTime != null) {
                aSN1EncodableVector.add(a(aSN1GeneralizedTime));
            }
            a(aSN1Integer, time, new DERSequence(aSN1EncodableVector));
        } else if (aSN1GeneralizedTime != null) {
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            aSN1EncodableVector2.add(a(aSN1GeneralizedTime));
            a(aSN1Integer, time, new DERSequence(aSN1EncodableVector2));
        } else {
            addCRLEntry(aSN1Integer, time, (Extensions) null);
        }
    }

    public void addCRLEntry(ASN1Integer aSN1Integer, Time time, Extensions extensions) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(aSN1Integer);
        aSN1EncodableVector.add(time);
        if (extensions != null) {
            aSN1EncodableVector.add(extensions);
        }
        addCRLEntry(new DERSequence(aSN1EncodableVector));
    }

    public void addCRLEntry(ASN1Sequence aSN1Sequence) {
        this.g.add(aSN1Sequence);
    }

    public TBSCertList generateTBSCertList() {
        if (this.b == null || this.c == null || this.d == null) {
            throw new IllegalStateException("Not all mandatory fields set in V2 TBSCertList generator.");
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(this.d);
        if (this.e != null) {
            aSN1EncodableVector.add(this.e);
        }
        if (this.g.size() != 0) {
            aSN1EncodableVector.add(new DERSequence(this.g));
        }
        if (this.f != null) {
            aSN1EncodableVector.add(new DERTaggedObject(0, this.f));
        }
        return new TBSCertList(new DERSequence(aSN1EncodableVector));
    }

    public void setExtensions(Extensions extensions) {
        this.f = extensions;
    }

    public void setExtensions(X509Extensions x509Extensions) {
        setExtensions(Extensions.getInstance(x509Extensions));
    }

    public void setIssuer(X500Name x500Name) {
        this.c = x500Name;
    }

    public void setIssuer(X509Name x509Name) {
        this.c = X500Name.getInstance(x509Name.toASN1Primitive());
    }

    public void setNextUpdate(ASN1UTCTime aSN1UTCTime) {
        this.e = new Time((ASN1Primitive) aSN1UTCTime);
    }

    public void setNextUpdate(Time time) {
        this.e = time;
    }

    public void setSignature(AlgorithmIdentifier algorithmIdentifier) {
        this.b = algorithmIdentifier;
    }

    public void setThisUpdate(ASN1UTCTime aSN1UTCTime) {
        this.d = new Time((ASN1Primitive) aSN1UTCTime);
    }

    public void setThisUpdate(Time time) {
        this.d = time;
    }
}
