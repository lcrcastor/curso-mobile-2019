package org.bouncycastle.asn1.dvcs;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.cmp.PKIStatusInfo;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.ess.ESSCertID;
import org.bouncycastle.asn1.ocsp.CertID;
import org.bouncycastle.asn1.ocsp.CertStatus;
import org.bouncycastle.asn1.ocsp.OCSPResponse;
import org.bouncycastle.asn1.smime.SMIMECapabilities;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.CertificateList;
import org.bouncycastle.asn1.x509.Extension;

public class CertEtcToken extends ASN1Object implements ASN1Choice {
    public static final int TAG_ASSERTION = 3;
    public static final int TAG_CAPABILITIES = 8;
    public static final int TAG_CERTIFICATE = 0;
    public static final int TAG_CRL = 4;
    public static final int TAG_ESSCERTID = 1;
    public static final int TAG_OCSPCERTID = 6;
    public static final int TAG_OCSPCERTSTATUS = 5;
    public static final int TAG_OCSPRESPONSE = 7;
    public static final int TAG_PKISTATUS = 2;
    private static final boolean[] a = {false, true, false, true, false, true, false, false, true};
    private int b;
    private ASN1Encodable c;
    private Extension d;

    public CertEtcToken(int i, ASN1Encodable aSN1Encodable) {
        this.b = i;
        this.c = aSN1Encodable;
    }

    private CertEtcToken(ASN1TaggedObject aSN1TaggedObject) {
        ASN1Encodable aSN1Encodable;
        this.b = aSN1TaggedObject.getTagNo();
        switch (this.b) {
            case 0:
                aSN1Encodable = Certificate.getInstance(aSN1TaggedObject, false);
                break;
            case 1:
                aSN1Encodable = ESSCertID.getInstance(aSN1TaggedObject.getObject());
                break;
            case 2:
                aSN1Encodable = PKIStatusInfo.getInstance(aSN1TaggedObject, false);
                break;
            case 3:
                aSN1Encodable = ContentInfo.getInstance(aSN1TaggedObject.getObject());
                break;
            case 4:
                aSN1Encodable = CertificateList.getInstance(aSN1TaggedObject, false);
                break;
            case 5:
                aSN1Encodable = CertStatus.getInstance(aSN1TaggedObject.getObject());
                break;
            case 6:
                aSN1Encodable = CertID.getInstance(aSN1TaggedObject, false);
                break;
            case 7:
                aSN1Encodable = OCSPResponse.getInstance(aSN1TaggedObject, false);
                break;
            case 8:
                aSN1Encodable = SMIMECapabilities.getInstance(aSN1TaggedObject.getObject());
                break;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown tag: ");
                sb.append(this.b);
                throw new IllegalArgumentException(sb.toString());
        }
        this.c = aSN1Encodable;
    }

    public CertEtcToken(Extension extension) {
        this.b = -1;
        this.d = extension;
    }

    public static CertEtcToken[] arrayFromSequence(ASN1Sequence aSN1Sequence) {
        CertEtcToken[] certEtcTokenArr = new CertEtcToken[aSN1Sequence.size()];
        for (int i = 0; i != certEtcTokenArr.length; i++) {
            certEtcTokenArr[i] = getInstance(aSN1Sequence.getObjectAt(i));
        }
        return certEtcTokenArr;
    }

    public static CertEtcToken getInstance(Object obj) {
        if (obj instanceof CertEtcToken) {
            return (CertEtcToken) obj;
        }
        if (obj instanceof ASN1TaggedObject) {
            return new CertEtcToken((ASN1TaggedObject) obj);
        }
        if (obj != null) {
            return new CertEtcToken(Extension.getInstance(obj));
        }
        return null;
    }

    public Extension getExtension() {
        return this.d;
    }

    public int getTagNo() {
        return this.b;
    }

    public ASN1Encodable getValue() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.d == null ? new DERTaggedObject(a[this.b], this.b, this.c) : this.d.toASN1Primitive();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CertEtcToken {\n");
        sb.append(this.c);
        sb.append("}\n");
        return sb.toString();
    }
}
