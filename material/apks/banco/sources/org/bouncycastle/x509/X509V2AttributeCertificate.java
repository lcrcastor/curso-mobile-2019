package org.bouncycastle.x509;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.x509.AttributeCertificate;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.util.Arrays;

public class X509V2AttributeCertificate implements X509AttributeCertificate {
    private AttributeCertificate a;
    private Date b;
    private Date c;

    public X509V2AttributeCertificate(InputStream inputStream) {
        this(a(inputStream));
    }

    X509V2AttributeCertificate(AttributeCertificate attributeCertificate) {
        this.a = attributeCertificate;
        try {
            this.c = attributeCertificate.getAcinfo().getAttrCertValidityPeriod().getNotAfterTime().getDate();
            this.b = attributeCertificate.getAcinfo().getAttrCertValidityPeriod().getNotBeforeTime().getDate();
        } catch (ParseException unused) {
            throw new IOException("invalid data structure in certificate!");
        }
    }

    public X509V2AttributeCertificate(byte[] bArr) {
        this((InputStream) new ByteArrayInputStream(bArr));
    }

    private Set a(boolean z) {
        Extensions extensions = this.a.getAcinfo().getExtensions();
        if (extensions == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        Enumeration oids = extensions.oids();
        while (oids.hasMoreElements()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
            if (extensions.getExtension(aSN1ObjectIdentifier).isCritical() == z) {
                hashSet.add(aSN1ObjectIdentifier.getId());
            }
        }
        return hashSet;
    }

    private static AttributeCertificate a(InputStream inputStream) {
        try {
            return AttributeCertificate.getInstance(new ASN1InputStream(inputStream).readObject());
        } catch (IOException e) {
            throw e;
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("exception decoding certificate structure: ");
            sb.append(e2.toString());
            throw new IOException(sb.toString());
        }
    }

    public void checkValidity() {
        checkValidity(new Date());
    }

    public void checkValidity(Date date) {
        if (date.after(getNotAfter())) {
            StringBuilder sb = new StringBuilder();
            sb.append("certificate expired on ");
            sb.append(getNotAfter());
            throw new CertificateExpiredException(sb.toString());
        } else if (date.before(getNotBefore())) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("certificate not valid till ");
            sb2.append(getNotBefore());
            throw new CertificateNotYetValidException(sb2.toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X509AttributeCertificate)) {
            return false;
        }
        try {
            return Arrays.areEqual(getEncoded(), ((X509AttributeCertificate) obj).getEncoded());
        } catch (IOException unused) {
            return false;
        }
    }

    public X509Attribute[] getAttributes() {
        ASN1Sequence attributes = this.a.getAcinfo().getAttributes();
        X509Attribute[] x509AttributeArr = new X509Attribute[attributes.size()];
        for (int i = 0; i != attributes.size(); i++) {
            x509AttributeArr[i] = new X509Attribute(attributes.getObjectAt(i));
        }
        return x509AttributeArr;
    }

    public X509Attribute[] getAttributes(String str) {
        ASN1Sequence attributes = this.a.getAcinfo().getAttributes();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i != attributes.size(); i++) {
            X509Attribute x509Attribute = new X509Attribute(attributes.getObjectAt(i));
            if (x509Attribute.getOID().equals(str)) {
                arrayList.add(x509Attribute);
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return (X509Attribute[]) arrayList.toArray(new X509Attribute[arrayList.size()]);
    }

    public Set getCriticalExtensionOIDs() {
        return a(true);
    }

    public byte[] getEncoded() {
        return this.a.getEncoded();
    }

    public byte[] getExtensionValue(String str) {
        Extensions extensions = this.a.getAcinfo().getExtensions();
        if (extensions != null) {
            Extension extension = extensions.getExtension(new ASN1ObjectIdentifier(str));
            if (extension != null) {
                try {
                    return extension.getExtnValue().getEncoded(ASN1Encoding.DER);
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("error encoding ");
                    sb.append(e.toString());
                    throw new RuntimeException(sb.toString());
                }
            }
        }
        return null;
    }

    public AttributeCertificateHolder getHolder() {
        return new AttributeCertificateHolder((ASN1Sequence) this.a.getAcinfo().getHolder().toASN1Object());
    }

    public AttributeCertificateIssuer getIssuer() {
        return new AttributeCertificateIssuer(this.a.getAcinfo().getIssuer());
    }

    public boolean[] getIssuerUniqueID() {
        DERBitString issuerUniqueID = this.a.getAcinfo().getIssuerUniqueID();
        if (issuerUniqueID == null) {
            return null;
        }
        byte[] bytes = issuerUniqueID.getBytes();
        boolean[] zArr = new boolean[((bytes.length * 8) - issuerUniqueID.getPadBits())];
        for (int i = 0; i != zArr.length; i++) {
            zArr[i] = (bytes[i / 8] & (128 >>> (i % 8))) != 0;
        }
        return zArr;
    }

    public Set getNonCriticalExtensionOIDs() {
        return a(false);
    }

    public Date getNotAfter() {
        return this.c;
    }

    public Date getNotBefore() {
        return this.b;
    }

    public BigInteger getSerialNumber() {
        return this.a.getAcinfo().getSerialNumber().getValue();
    }

    public byte[] getSignature() {
        return this.a.getSignatureValue().getBytes();
    }

    public int getVersion() {
        return this.a.getAcinfo().getVersion().getValue().intValue() + 1;
    }

    public boolean hasUnsupportedCriticalExtension() {
        Set criticalExtensionOIDs = getCriticalExtensionOIDs();
        return criticalExtensionOIDs != null && !criticalExtensionOIDs.isEmpty();
    }

    public int hashCode() {
        try {
            return Arrays.hashCode(getEncoded());
        } catch (IOException unused) {
            return 0;
        }
    }

    public final void verify(PublicKey publicKey, String str) {
        if (!this.a.getSignatureAlgorithm().equals(this.a.getAcinfo().getSignature())) {
            throw new CertificateException("Signature algorithm in certificate info not same as outer certificate");
        }
        Signature instance = Signature.getInstance(this.a.getSignatureAlgorithm().getObjectId().getId(), str);
        instance.initVerify(publicKey);
        try {
            instance.update(this.a.getAcinfo().getEncoded());
            if (!instance.verify(getSignature())) {
                throw new InvalidKeyException("Public key presented not for certificate signature");
            }
        } catch (IOException unused) {
            throw new SignatureException("Exception encoding certificate info object");
        }
    }
}
