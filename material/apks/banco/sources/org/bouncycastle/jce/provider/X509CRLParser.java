package org.bouncycastle.jce.provider;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.cert.CRL;
import java.util.ArrayList;
import java.util.Collection;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.SignedData;
import org.bouncycastle.asn1.x509.CertificateList;
import org.bouncycastle.x509.X509StreamParserSpi;
import org.bouncycastle.x509.util.StreamParsingException;

public class X509CRLParser extends X509StreamParserSpi {
    private static final PEMUtil a = new PEMUtil("CRL");
    private ASN1Set b = null;
    private int c = 0;
    private InputStream d = null;

    private CRL a() {
        if (this.b == null || this.c >= this.b.size()) {
            return null;
        }
        ASN1Set aSN1Set = this.b;
        int i = this.c;
        this.c = i + 1;
        return new X509CRLObject(CertificateList.getInstance(aSN1Set.getObjectAt(i)));
    }

    private CRL a(InputStream inputStream) {
        ASN1Sequence aSN1Sequence = (ASN1Sequence) new ASN1InputStream(inputStream).readObject();
        if (aSN1Sequence.size() <= 1 || !(aSN1Sequence.getObjectAt(0) instanceof ASN1ObjectIdentifier) || !aSN1Sequence.getObjectAt(0).equals(PKCSObjectIdentifiers.signedData)) {
            return new X509CRLObject(CertificateList.getInstance(aSN1Sequence));
        }
        this.b = new SignedData(ASN1Sequence.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(1), true)).getCRLs();
        return a();
    }

    private CRL b(InputStream inputStream) {
        ASN1Sequence a2 = a.a(inputStream);
        if (a2 != null) {
            return new X509CRLObject(CertificateList.getInstance(a2));
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
            CRL crl = (CRL) engineRead();
            if (crl == null) {
                return arrayList;
            }
            arrayList.add(crl);
        }
    }
}
