package org.bouncycastle.jcajce.provider.asymmetric.x509;

import java.io.InputStream;
import java.io.PushbackInputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactorySpi;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.SignedData;
import org.bouncycastle.asn1.x509.CertificateList;

public class CertificateFactory extends CertificateFactorySpi {
    private static final PEMUtil a = new PEMUtil("CERTIFICATE");
    private static final PEMUtil b = new PEMUtil("CRL");
    private ASN1Set c = null;
    private int d = 0;
    private InputStream e = null;
    private ASN1Set f = null;
    private int g = 0;
    private InputStream h = null;

    class ExCertificateException extends CertificateException {
        private Throwable b;

        public ExCertificateException(Throwable th) {
            this.b = th;
        }

        public Throwable getCause() {
            return this.b;
        }
    }

    private Certificate a() {
        if (this.c != null) {
            while (this.d < this.c.size()) {
                ASN1Set aSN1Set = this.c;
                int i = this.d;
                this.d = i + 1;
                ASN1Encodable objectAt = aSN1Set.getObjectAt(i);
                if (objectAt instanceof ASN1Sequence) {
                    return new X509CertificateObject(org.bouncycastle.asn1.x509.Certificate.getInstance(objectAt));
                }
            }
        }
        return null;
    }

    private Certificate a(InputStream inputStream) {
        ASN1Sequence a2 = a.a(inputStream);
        if (a2 != null) {
            return new X509CertificateObject(org.bouncycastle.asn1.x509.Certificate.getInstance(a2));
        }
        return null;
    }

    private Certificate a(ASN1InputStream aSN1InputStream) {
        ASN1Sequence aSN1Sequence = (ASN1Sequence) aSN1InputStream.readObject();
        if (aSN1Sequence.size() <= 1 || !(aSN1Sequence.getObjectAt(0) instanceof ASN1ObjectIdentifier) || !aSN1Sequence.getObjectAt(0).equals(PKCSObjectIdentifiers.signedData)) {
            return new X509CertificateObject(org.bouncycastle.asn1.x509.Certificate.getInstance(aSN1Sequence));
        }
        this.c = SignedData.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(1), true)).getCertificates();
        return a();
    }

    private CRL b() {
        if (this.f == null || this.g >= this.f.size()) {
            return null;
        }
        ASN1Set aSN1Set = this.f;
        int i = this.g;
        this.g = i + 1;
        return createCRL(CertificateList.getInstance(aSN1Set.getObjectAt(i)));
    }

    private CRL b(InputStream inputStream) {
        ASN1Sequence a2 = b.a(inputStream);
        if (a2 != null) {
            return createCRL(CertificateList.getInstance(a2));
        }
        return null;
    }

    private CRL b(ASN1InputStream aSN1InputStream) {
        ASN1Sequence aSN1Sequence = (ASN1Sequence) aSN1InputStream.readObject();
        if (aSN1Sequence.size() <= 1 || !(aSN1Sequence.getObjectAt(0) instanceof ASN1ObjectIdentifier) || !aSN1Sequence.getObjectAt(0).equals(PKCSObjectIdentifiers.signedData)) {
            return createCRL(CertificateList.getInstance(aSN1Sequence));
        }
        this.f = SignedData.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(1), true)).getCRLs();
        return b();
    }

    /* access modifiers changed from: protected */
    public CRL createCRL(CertificateList certificateList) {
        return new X509CRLObject(certificateList);
    }

    public CRL engineGenerateCRL(InputStream inputStream) {
        if (this.h == null || this.h != inputStream) {
            this.h = inputStream;
            this.f = null;
            this.g = 0;
        }
        try {
            if (this.f == null) {
                PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream);
                int read = pushbackInputStream.read();
                if (read == -1) {
                    return null;
                }
                pushbackInputStream.unread(read);
                return read != 48 ? b((InputStream) pushbackInputStream) : b(new ASN1InputStream((InputStream) pushbackInputStream, true));
            } else if (this.g != this.f.size()) {
                return b();
            } else {
                this.f = null;
                this.g = 0;
                return null;
            }
        } catch (CRLException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new CRLException(e3.toString());
        }
    }

    public Collection engineGenerateCRLs(InputStream inputStream) {
        ArrayList arrayList = new ArrayList();
        while (true) {
            CRL engineGenerateCRL = engineGenerateCRL(inputStream);
            if (engineGenerateCRL == null) {
                return arrayList;
            }
            arrayList.add(engineGenerateCRL);
        }
    }

    public CertPath engineGenerateCertPath(InputStream inputStream) {
        return engineGenerateCertPath(inputStream, "PkiPath");
    }

    public CertPath engineGenerateCertPath(InputStream inputStream, String str) {
        return new PKIXCertPath(inputStream, str);
    }

    public CertPath engineGenerateCertPath(List list) {
        for (Object next : list) {
            if (next != null && !(next instanceof X509Certificate)) {
                StringBuilder sb = new StringBuilder();
                sb.append("list contains non X509Certificate object while creating CertPath\n");
                sb.append(next.toString());
                throw new CertificateException(sb.toString());
            }
        }
        return new PKIXCertPath(list);
    }

    public Certificate engineGenerateCertificate(InputStream inputStream) {
        if (this.e == null || this.e != inputStream) {
            this.e = inputStream;
            this.c = null;
            this.d = 0;
        }
        try {
            if (this.c == null) {
                PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream);
                int read = pushbackInputStream.read();
                if (read == -1) {
                    return null;
                }
                pushbackInputStream.unread(read);
                return read != 48 ? a((InputStream) pushbackInputStream) : a(new ASN1InputStream((InputStream) pushbackInputStream));
            } else if (this.d != this.c.size()) {
                return a();
            } else {
                this.c = null;
                this.d = 0;
                return null;
            }
        } catch (Exception e2) {
            throw new ExCertificateException(e2);
        }
    }

    public Collection engineGenerateCertificates(InputStream inputStream) {
        ArrayList arrayList = new ArrayList();
        while (true) {
            Certificate engineGenerateCertificate = engineGenerateCertificate(inputStream);
            if (engineGenerateCertificate == null) {
                return arrayList;
            }
            arrayList.add(engineGenerateCertificate);
        }
    }

    public Iterator engineGetCertPathEncodings() {
        return PKIXCertPath.a.iterator();
    }
}
