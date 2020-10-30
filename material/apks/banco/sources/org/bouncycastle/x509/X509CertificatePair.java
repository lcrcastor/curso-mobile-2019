package org.bouncycastle.x509;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.CertificatePair;
import org.bouncycastle.jce.provider.X509CertificateObject;

public class X509CertificatePair {
    private X509Certificate a;
    private X509Certificate b;

    public X509CertificatePair(X509Certificate x509Certificate, X509Certificate x509Certificate2) {
        this.a = x509Certificate;
        this.b = x509Certificate2;
    }

    public X509CertificatePair(CertificatePair certificatePair) {
        if (certificatePair.getForward() != null) {
            this.a = new X509CertificateObject(certificatePair.getForward());
        }
        if (certificatePair.getReverse() != null) {
            this.b = new X509CertificateObject(certificatePair.getReverse());
        }
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == null || !(obj instanceof X509CertificatePair)) {
            return false;
        }
        X509CertificatePair x509CertificatePair = (X509CertificatePair) obj;
        boolean z2 = this.a != null ? this.a.equals(x509CertificatePair.a) : x509CertificatePair.a == null;
        boolean z3 = this.b != null ? this.b.equals(x509CertificatePair.b) : x509CertificatePair.b == null;
        if (z2 && z3) {
            z = true;
        }
        return z;
    }

    public byte[] getEncoded() {
        Certificate certificate;
        try {
            Certificate certificate2 = null;
            if (this.a != null) {
                certificate = Certificate.getInstance(new ASN1InputStream(this.a.getEncoded()).readObject());
                if (certificate == null) {
                    throw new CertificateEncodingException("unable to get encoding for forward");
                }
            } else {
                certificate = null;
            }
            if (this.b != null) {
                certificate2 = Certificate.getInstance(new ASN1InputStream(this.b.getEncoded()).readObject());
                if (certificate2 == null) {
                    throw new CertificateEncodingException("unable to get encoding for reverse");
                }
            }
            return new CertificatePair(certificate, certificate2).getEncoded(ASN1Encoding.DER);
        } catch (IllegalArgumentException e) {
            throw new ExtCertificateEncodingException(e.toString(), e);
        } catch (IOException e2) {
            throw new ExtCertificateEncodingException(e2.toString(), e2);
        }
    }

    public X509Certificate getForward() {
        return this.a;
    }

    public X509Certificate getReverse() {
        return this.b;
    }

    public int hashCode() {
        int i = -1;
        if (this.a != null) {
            i = -1 ^ this.a.hashCode();
        }
        return this.b != null ? (i * 17) ^ this.b.hashCode() : i;
    }
}
