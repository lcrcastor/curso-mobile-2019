package org.bouncycastle.asn1.eac;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1ParsingException;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERApplicationSpecific;
import org.bouncycastle.asn1.DEROctetString;

public class CVCertificate extends ASN1Object {
    public static String ReferenceEncoding = "ISO-8859-1";
    private static int d = 1;
    private static int e = 2;
    public static final byte version_1 = 0;
    private CertificateBody a;
    private byte[] b;
    private int c;

    public CVCertificate(ASN1InputStream aSN1InputStream) {
        a(aSN1InputStream);
    }

    private CVCertificate(DERApplicationSpecific dERApplicationSpecific) {
        a(dERApplicationSpecific);
    }

    public CVCertificate(CertificateBody certificateBody, byte[] bArr) {
        this.a = certificateBody;
        this.b = bArr;
        this.c |= d;
        this.c |= e;
    }

    private void a(ASN1InputStream aSN1InputStream) {
        while (true) {
            ASN1Primitive readObject = aSN1InputStream.readObject();
            if (readObject == null) {
                return;
            }
            if (readObject instanceof DERApplicationSpecific) {
                a((DERApplicationSpecific) readObject);
            } else {
                throw new IOException("Invalid Input Stream for creating an Iso7816CertificateStructure");
            }
        }
    }

    private void a(DERApplicationSpecific dERApplicationSpecific) {
        int i;
        int i2;
        this.c = 0;
        if (dERApplicationSpecific.getApplicationTag() == 33) {
            ASN1InputStream aSN1InputStream = new ASN1InputStream(dERApplicationSpecific.getContents());
            while (true) {
                ASN1Primitive readObject = aSN1InputStream.readObject();
                if (readObject == null) {
                    return;
                }
                if (readObject instanceof DERApplicationSpecific) {
                    DERApplicationSpecific dERApplicationSpecific2 = (DERApplicationSpecific) readObject;
                    int applicationTag = dERApplicationSpecific2.getApplicationTag();
                    if (applicationTag == 55) {
                        this.b = dERApplicationSpecific2.getContents();
                        i = this.c;
                        i2 = e;
                    } else if (applicationTag != 78) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Invalid tag, not an Iso7816CertificateStructure :");
                        sb.append(dERApplicationSpecific2.getApplicationTag());
                        throw new IOException(sb.toString());
                    } else {
                        this.a = CertificateBody.getInstance(dERApplicationSpecific2);
                        i = this.c;
                        i2 = d;
                    }
                    this.c = i | i2;
                } else {
                    throw new IOException("Invalid Object, not an Iso7816CertificateStructure");
                }
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("not a CARDHOLDER_CERTIFICATE :");
            sb2.append(dERApplicationSpecific.getApplicationTag());
            throw new IOException(sb2.toString());
        }
    }

    public static CVCertificate getInstance(Object obj) {
        if (obj instanceof CVCertificate) {
            return (CVCertificate) obj;
        }
        if (obj == null) {
            return null;
        }
        try {
            return new CVCertificate(DERApplicationSpecific.getInstance(obj));
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("unable to parse data: ");
            sb.append(e2.getMessage());
            throw new ASN1ParsingException(sb.toString(), e2);
        }
    }

    public CertificationAuthorityReference getAuthorityReference() {
        return this.a.getCertificationAuthorityReference();
    }

    public CertificateBody getBody() {
        return this.a;
    }

    public int getCertificateType() {
        return this.a.getCertificateType();
    }

    public PackedDate getEffectiveDate() {
        return this.a.getCertificateEffectiveDate();
    }

    public PackedDate getExpirationDate() {
        return this.a.getCertificateExpirationDate();
    }

    public ASN1ObjectIdentifier getHolderAuthorization() {
        return this.a.getCertificateHolderAuthorization().getOid();
    }

    public Flags getHolderAuthorizationRights() {
        return new Flags(this.a.getCertificateHolderAuthorization().getAccessRights() & 31);
    }

    public int getHolderAuthorizationRole() {
        return this.a.getCertificateHolderAuthorization().getAccessRights() & 192;
    }

    public CertificateHolderReference getHolderReference() {
        return this.a.getCertificateHolderReference();
    }

    public int getRole() {
        return this.a.getCertificateHolderAuthorization().getAccessRights();
    }

    public byte[] getSignature() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.c != (e | d)) {
            return null;
        }
        aSN1EncodableVector.add(this.a);
        try {
            aSN1EncodableVector.add(new DERApplicationSpecific(false, 55, (ASN1Encodable) new DEROctetString(this.b)));
            return new DERApplicationSpecific(33, aSN1EncodableVector);
        } catch (IOException unused) {
            throw new IllegalStateException("unable to convert signature!");
        }
    }
}
