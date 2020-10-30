package org.bouncycastle.asn1.crmf;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public class CertTemplate extends ASN1Object {
    private ASN1Sequence a;
    private ASN1Integer b;
    private ASN1Integer c;
    private AlgorithmIdentifier d;
    private X500Name e;
    private OptionalValidity f;
    private X500Name g;
    private SubjectPublicKeyInfo h;
    private DERBitString i;
    private DERBitString j;
    private Extensions k;

    private CertTemplate(ASN1Sequence aSN1Sequence) {
        this.a = aSN1Sequence;
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) objects.nextElement();
            switch (aSN1TaggedObject.getTagNo()) {
                case 0:
                    this.b = ASN1Integer.getInstance(aSN1TaggedObject, false);
                    break;
                case 1:
                    this.c = ASN1Integer.getInstance(aSN1TaggedObject, false);
                    break;
                case 2:
                    this.d = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
                    break;
                case 3:
                    this.e = X500Name.getInstance(aSN1TaggedObject, true);
                    break;
                case 4:
                    this.f = OptionalValidity.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, false));
                    break;
                case 5:
                    this.g = X500Name.getInstance(aSN1TaggedObject, true);
                    break;
                case 6:
                    this.h = SubjectPublicKeyInfo.getInstance(aSN1TaggedObject, false);
                    break;
                case 7:
                    this.i = DERBitString.getInstance(aSN1TaggedObject, false);
                    break;
                case 8:
                    this.j = DERBitString.getInstance(aSN1TaggedObject, false);
                    break;
                case 9:
                    this.k = Extensions.getInstance(aSN1TaggedObject, false);
                    break;
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("unknown tag: ");
                    sb.append(aSN1TaggedObject.getTagNo());
                    throw new IllegalArgumentException(sb.toString());
            }
        }
    }

    public static CertTemplate getInstance(Object obj) {
        if (obj instanceof CertTemplate) {
            return (CertTemplate) obj;
        }
        if (obj != null) {
            return new CertTemplate(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public Extensions getExtensions() {
        return this.k;
    }

    public X500Name getIssuer() {
        return this.e;
    }

    public DERBitString getIssuerUID() {
        return this.i;
    }

    public SubjectPublicKeyInfo getPublicKey() {
        return this.h;
    }

    public ASN1Integer getSerialNumber() {
        return this.c;
    }

    public AlgorithmIdentifier getSigningAlg() {
        return this.d;
    }

    public X500Name getSubject() {
        return this.g;
    }

    public DERBitString getSubjectUID() {
        return this.j;
    }

    public OptionalValidity getValidity() {
        return this.f;
    }

    public int getVersion() {
        return this.b.getValue().intValue();
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }
}
