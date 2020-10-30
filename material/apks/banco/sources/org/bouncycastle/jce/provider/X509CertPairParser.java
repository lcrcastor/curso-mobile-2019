package org.bouncycastle.jce.provider;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.CertificatePair;
import org.bouncycastle.x509.X509CertificatePair;
import org.bouncycastle.x509.X509StreamParserSpi;
import org.bouncycastle.x509.util.StreamParsingException;

public class X509CertPairParser extends X509StreamParserSpi {
    private InputStream a = null;

    private X509CertificatePair a(InputStream inputStream) {
        return new X509CertificatePair(CertificatePair.getInstance((ASN1Sequence) new ASN1InputStream(inputStream).readObject()));
    }

    public void engineInit(InputStream inputStream) {
        this.a = inputStream;
        if (!this.a.markSupported()) {
            this.a = new BufferedInputStream(this.a);
        }
    }

    public Object engineRead() {
        try {
            this.a.mark(10);
            if (this.a.read() == -1) {
                return null;
            }
            this.a.reset();
            return a(this.a);
        } catch (Exception e) {
            throw new StreamParsingException(e.toString(), e);
        }
    }

    public Collection engineReadAll() {
        ArrayList arrayList = new ArrayList();
        while (true) {
            X509CertificatePair x509CertificatePair = (X509CertificatePair) engineRead();
            if (x509CertificatePair == null) {
                return arrayList;
            }
            arrayList.add(x509CertificatePair);
        }
    }
}
