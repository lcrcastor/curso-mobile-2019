package org.bouncycastle.jce.provider;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.SignedData;
import org.bouncycastle.x509.X509AttributeCertificate;
import org.bouncycastle.x509.X509StreamParserSpi;
import org.bouncycastle.x509.X509V2AttributeCertificate;
import org.bouncycastle.x509.util.StreamParsingException;

public class X509AttrCertParser extends X509StreamParserSpi {
    private static final PEMUtil a = new PEMUtil("ATTRIBUTE CERTIFICATE");
    private ASN1Set b = null;
    private int c = 0;
    private InputStream d = null;

    private X509AttributeCertificate a() {
        if (this.b != null) {
            while (this.c < this.b.size()) {
                ASN1Set aSN1Set = this.b;
                int i = this.c;
                this.c = i + 1;
                ASN1Encodable objectAt = aSN1Set.getObjectAt(i);
                if (objectAt instanceof ASN1TaggedObject) {
                    ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) objectAt;
                    if (aSN1TaggedObject.getTagNo() == 2) {
                        return new X509V2AttributeCertificate(ASN1Sequence.getInstance(aSN1TaggedObject, false).getEncoded());
                    }
                }
            }
        }
        return null;
    }

    private X509AttributeCertificate a(InputStream inputStream) {
        ASN1Sequence aSN1Sequence = (ASN1Sequence) new ASN1InputStream(inputStream).readObject();
        if (aSN1Sequence.size() <= 1 || !(aSN1Sequence.getObjectAt(0) instanceof ASN1ObjectIdentifier) || !aSN1Sequence.getObjectAt(0).equals(PKCSObjectIdentifiers.signedData)) {
            return new X509V2AttributeCertificate(aSN1Sequence.getEncoded());
        }
        this.b = new SignedData(ASN1Sequence.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(1), true)).getCertificates();
        return a();
    }

    private X509AttributeCertificate b(InputStream inputStream) {
        ASN1Sequence a2 = a.a(inputStream);
        if (a2 != null) {
            return new X509V2AttributeCertificate(a2.getEncoded());
        }
        return null;
    }

    public void engineInit(InputStream inputStream) {
        this.d = inputStream;
        this.b = null;
        this.c = 0;
        if (!this.d.markSupported()) {
            this.d = new BufferedInputStream(this.d);
        }
    }

    public Object engineRead() {
        try {
            if (this.b == null) {
                this.d.mark(10);
                int read = this.d.read();
                if (read == -1) {
                    return null;
                }
                if (read != 48) {
                    this.d.reset();
                    return b(this.d);
                }
                this.d.reset();
                return a(this.d);
            } else if (this.c != this.b.size()) {
                return a();
            } else {
                this.b = null;
                this.c = 0;
                return null;
            }
        } catch (Exception e) {
            throw new StreamParsingException(e.toString(), e);
        }
    }

    public Collection engineReadAll() {
        ArrayList arrayList = new ArrayList();
        while (true) {
            X509AttributeCertificate x509AttributeCertificate = (X509AttributeCertificate) engineRead();
            if (x509AttributeCertificate == null) {
                return arrayList;
            }
            arrayList.add(x509AttributeCertificate);
        }
    }
}
