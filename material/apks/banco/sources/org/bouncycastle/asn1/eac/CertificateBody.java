package org.bouncycastle.asn1.eac;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERApplicationSpecific;
import org.bouncycastle.asn1.DEROctetString;

public class CertificateBody extends ASN1Object {
    public static final int profileType = 127;
    public static final int requestType = 13;
    private DERApplicationSpecific a;
    private DERApplicationSpecific b;
    private PublicKeyDataObject c;
    private DERApplicationSpecific d;
    private CertificateHolderAuthorization e;
    private DERApplicationSpecific f;
    private DERApplicationSpecific g;
    private int h = 0;

    private CertificateBody(DERApplicationSpecific dERApplicationSpecific) {
        a(dERApplicationSpecific);
    }

    public CertificateBody(DERApplicationSpecific dERApplicationSpecific, CertificationAuthorityReference certificationAuthorityReference, PublicKeyDataObject publicKeyDataObject, CertificateHolderReference certificateHolderReference, CertificateHolderAuthorization certificateHolderAuthorization, PackedDate packedDate, PackedDate packedDate2) {
        b(dERApplicationSpecific);
        d(new DERApplicationSpecific(2, certificationAuthorityReference.getEncoded()));
        a(publicKeyDataObject);
        c(new DERApplicationSpecific(32, certificateHolderReference.getEncoded()));
        a(certificateHolderAuthorization);
        try {
            e(new DERApplicationSpecific(false, 37, (ASN1Encodable) new DEROctetString(packedDate.getEncoding())));
            f(new DERApplicationSpecific(false, 36, (ASN1Encodable) new DEROctetString(packedDate2.getEncoding())));
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("unable to encode dates: ");
            sb.append(e2.getMessage());
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private ASN1Primitive a() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(new DERApplicationSpecific(false, 73, (ASN1Encodable) this.c));
        aSN1EncodableVector.add(this.d);
        aSN1EncodableVector.add(this.e);
        aSN1EncodableVector.add(this.f);
        aSN1EncodableVector.add(this.g);
        return new DERApplicationSpecific(78, aSN1EncodableVector);
    }

    private void a(DERApplicationSpecific dERApplicationSpecific) {
        if (dERApplicationSpecific.getApplicationTag() == 78) {
            ASN1InputStream aSN1InputStream = new ASN1InputStream(dERApplicationSpecific.getContents());
            while (true) {
                ASN1Primitive readObject = aSN1InputStream.readObject();
                if (readObject == null) {
                    return;
                }
                if (readObject instanceof DERApplicationSpecific) {
                    DERApplicationSpecific dERApplicationSpecific2 = (DERApplicationSpecific) readObject;
                    int applicationTag = dERApplicationSpecific2.getApplicationTag();
                    if (applicationTag == 2) {
                        d(dERApplicationSpecific2);
                    } else if (applicationTag == 32) {
                        c(dERApplicationSpecific2);
                    } else if (applicationTag == 41) {
                        b(dERApplicationSpecific2);
                    } else if (applicationTag == 73) {
                        a(PublicKeyDataObject.getInstance(dERApplicationSpecific2.getObject(16)));
                    } else if (applicationTag != 76) {
                        switch (applicationTag) {
                            case 36:
                                f(dERApplicationSpecific2);
                                break;
                            case 37:
                                e(dERApplicationSpecific2);
                                break;
                            default:
                                this.h = 0;
                                StringBuilder sb = new StringBuilder();
                                sb.append("Not a valid iso7816 DERApplicationSpecific tag ");
                                sb.append(dERApplicationSpecific2.getApplicationTag());
                                throw new IOException(sb.toString());
                        }
                    } else {
                        a(new CertificateHolderAuthorization(dERApplicationSpecific2));
                    }
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Not a valid iso7816 content : not a DERApplicationSpecific Object :");
                    sb2.append(EACTags.encodeTag(dERApplicationSpecific));
                    sb2.append(readObject.getClass());
                    throw new IOException(sb2.toString());
                }
            }
        } else {
            throw new IOException("Bad tag : not an iso7816 CERTIFICATE_CONTENT_TEMPLATE");
        }
    }

    private void a(CertificateHolderAuthorization certificateHolderAuthorization) {
        this.e = certificateHolderAuthorization;
        this.h |= 16;
    }

    private void a(PublicKeyDataObject publicKeyDataObject) {
        this.c = PublicKeyDataObject.getInstance(publicKeyDataObject);
        this.h |= 4;
    }

    private ASN1Primitive b() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(new DERApplicationSpecific(false, 73, (ASN1Encodable) this.c));
        aSN1EncodableVector.add(this.d);
        return new DERApplicationSpecific(78, aSN1EncodableVector);
    }

    private void b(DERApplicationSpecific dERApplicationSpecific) {
        if (dERApplicationSpecific.getApplicationTag() == 41) {
            this.a = dERApplicationSpecific;
            this.h |= 1;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Not an Iso7816Tags.INTERCHANGE_PROFILE tag :");
        sb.append(EACTags.encodeTag(dERApplicationSpecific));
        throw new IllegalArgumentException(sb.toString());
    }

    private void c(DERApplicationSpecific dERApplicationSpecific) {
        if (dERApplicationSpecific.getApplicationTag() == 32) {
            this.d = dERApplicationSpecific;
            this.h |= 8;
            return;
        }
        throw new IllegalArgumentException("Not an Iso7816Tags.CARDHOLDER_NAME tag");
    }

    private void d(DERApplicationSpecific dERApplicationSpecific) {
        if (dERApplicationSpecific.getApplicationTag() == 2) {
            this.b = dERApplicationSpecific;
            this.h |= 2;
            return;
        }
        throw new IllegalArgumentException("Not an Iso7816Tags.ISSUER_IDENTIFICATION_NUMBER tag");
    }

    private void e(DERApplicationSpecific dERApplicationSpecific) {
        if (dERApplicationSpecific.getApplicationTag() == 37) {
            this.f = dERApplicationSpecific;
            this.h |= 32;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Not an Iso7816Tags.APPLICATION_EFFECTIVE_DATE tag :");
        sb.append(EACTags.encodeTag(dERApplicationSpecific));
        throw new IllegalArgumentException(sb.toString());
    }

    private void f(DERApplicationSpecific dERApplicationSpecific) {
        if (dERApplicationSpecific.getApplicationTag() == 36) {
            this.g = dERApplicationSpecific;
            this.h |= 64;
            return;
        }
        throw new IllegalArgumentException("Not an Iso7816Tags.APPLICATION_EXPIRATION_DATE tag");
    }

    public static CertificateBody getInstance(Object obj) {
        if (obj instanceof CertificateBody) {
            return (CertificateBody) obj;
        }
        if (obj != null) {
            return new CertificateBody(DERApplicationSpecific.getInstance(obj));
        }
        return null;
    }

    public PackedDate getCertificateEffectiveDate() {
        if ((this.h & 32) == 32) {
            return new PackedDate(this.f.getContents());
        }
        return null;
    }

    public PackedDate getCertificateExpirationDate() {
        if ((this.h & 64) == 64) {
            return new PackedDate(this.g.getContents());
        }
        throw new IOException("certificate Expiration Date not set");
    }

    public CertificateHolderAuthorization getCertificateHolderAuthorization() {
        if ((this.h & 16) == 16) {
            return this.e;
        }
        throw new IOException("Certificate Holder Authorisation not set");
    }

    public CertificateHolderReference getCertificateHolderReference() {
        return new CertificateHolderReference(this.d.getContents());
    }

    public DERApplicationSpecific getCertificateProfileIdentifier() {
        return this.a;
    }

    public int getCertificateType() {
        return this.h;
    }

    public CertificationAuthorityReference getCertificationAuthorityReference() {
        if ((this.h & 2) == 2) {
            return new CertificationAuthorityReference(this.b.getContents());
        }
        throw new IOException("Certification authority reference not set");
    }

    public PublicKeyDataObject getPublicKey() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        try {
            if (this.h == 127) {
                return a();
            }
            if (this.h == 13) {
                return b();
            }
            return null;
        } catch (IOException unused) {
        }
    }
}
