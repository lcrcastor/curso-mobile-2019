package org.bouncycastle.asn1.isismtt.ocsp;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.Certificate;

public class RequestedCertificate extends ASN1Object implements ASN1Choice {
    public static final int attributeCertificate = 1;
    public static final int certificate = -1;
    public static final int publicKeyCertificate = 0;
    private Certificate a;
    private byte[] b;
    private byte[] c;

    public RequestedCertificate(int i, byte[] bArr) {
        this((ASN1TaggedObject) new DERTaggedObject(i, new DEROctetString(bArr)));
    }

    private RequestedCertificate(ASN1TaggedObject aSN1TaggedObject) {
        if (aSN1TaggedObject.getTagNo() == 0) {
            this.b = ASN1OctetString.getInstance(aSN1TaggedObject, true).getOctets();
        } else if (aSN1TaggedObject.getTagNo() == 1) {
            this.c = ASN1OctetString.getInstance(aSN1TaggedObject, true).getOctets();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("unknown tag number: ");
            sb.append(aSN1TaggedObject.getTagNo());
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public RequestedCertificate(Certificate certificate2) {
        this.a = certificate2;
    }

    public static RequestedCertificate getInstance(Object obj) {
        if (obj == null || (obj instanceof RequestedCertificate)) {
            return (RequestedCertificate) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new RequestedCertificate(Certificate.getInstance(obj));
        }
        if (obj instanceof ASN1TaggedObject) {
            return new RequestedCertificate((ASN1TaggedObject) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("illegal object in getInstance: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static RequestedCertificate getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (z) {
            return getInstance(aSN1TaggedObject.getObject());
        }
        throw new IllegalArgumentException("choice item must be explicitly tagged");
    }

    public byte[] getCertificateBytes() {
        if (this.a == null) {
            return this.b != null ? this.b : this.c;
        }
        try {
            return this.a.getEncoded();
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("can't decode certificate: ");
            sb.append(e);
            throw new IllegalStateException(sb.toString());
        }
    }

    public int getType() {
        if (this.a != null) {
            return -1;
        }
        return this.b != null ? 0 : 1;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.b != null ? new DERTaggedObject(0, new DEROctetString(this.b)) : this.c != null ? new DERTaggedObject(1, new DEROctetString(this.c)) : this.a.toASN1Primitive();
    }
}
