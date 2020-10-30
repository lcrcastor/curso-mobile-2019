package org.bouncycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.CertificateList;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.TBSCertList;
import org.bouncycastle.asn1.x509.Time;
import org.bouncycastle.asn1.x509.V2TBSCertListGenerator;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.X509ExtensionsGenerator;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.X509CRLObject;

public class X509V2CRLGenerator {
    private V2TBSCertListGenerator a = new V2TBSCertListGenerator();
    private ASN1ObjectIdentifier b;
    private AlgorithmIdentifier c;
    private String d;
    private X509ExtensionsGenerator e = new X509ExtensionsGenerator();

    static class ExtCRLException extends CRLException {
        Throwable a;

        ExtCRLException(String str, Throwable th) {
            super(str);
            this.a = th;
        }

        public Throwable getCause() {
            return this.a;
        }
    }

    private X509CRL a(TBSCertList tBSCertList, byte[] bArr) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(tBSCertList);
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(new DERBitString(bArr));
        return new X509CRLObject(new CertificateList(new DERSequence(aSN1EncodableVector)));
    }

    private TBSCertList a() {
        if (!this.e.isEmpty()) {
            this.a.setExtensions(this.e.generate());
        }
        return this.a.generateTBSCertList();
    }

    public void addCRL(X509CRL x509crl) {
        Set<X509CRLEntry> revokedCertificates = x509crl.getRevokedCertificates();
        if (revokedCertificates != null) {
            for (X509CRLEntry encoded : revokedCertificates) {
                try {
                    this.a.addCRLEntry(ASN1Sequence.getInstance(new ASN1InputStream(encoded.getEncoded()).readObject()));
                } catch (IOException e2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("exception processing encoding of CRL: ");
                    sb.append(e2.toString());
                    throw new CRLException(sb.toString());
                }
            }
        }
    }

    public void addCRLEntry(BigInteger bigInteger, Date date, int i) {
        this.a.addCRLEntry(new ASN1Integer(bigInteger), new Time(date), i);
    }

    public void addCRLEntry(BigInteger bigInteger, Date date, int i, Date date2) {
        this.a.addCRLEntry(new ASN1Integer(bigInteger), new Time(date), i, new ASN1GeneralizedTime(date2));
    }

    public void addCRLEntry(BigInteger bigInteger, Date date, X509Extensions x509Extensions) {
        this.a.addCRLEntry(new ASN1Integer(bigInteger), new Time(date), Extensions.getInstance(x509Extensions));
    }

    public void addExtension(String str, boolean z, ASN1Encodable aSN1Encodable) {
        addExtension(new ASN1ObjectIdentifier(str), z, aSN1Encodable);
    }

    public void addExtension(String str, boolean z, byte[] bArr) {
        addExtension(new ASN1ObjectIdentifier(str), z, bArr);
    }

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, ASN1Encodable aSN1Encodable) {
        this.e.addExtension(new ASN1ObjectIdentifier(aSN1ObjectIdentifier.getId()), z, aSN1Encodable);
    }

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, byte[] bArr) {
        this.e.addExtension(new ASN1ObjectIdentifier(aSN1ObjectIdentifier.getId()), z, bArr);
    }

    public X509CRL generate(PrivateKey privateKey) {
        return generate(privateKey, (SecureRandom) null);
    }

    public X509CRL generate(PrivateKey privateKey, String str) {
        return generate(privateKey, str, null);
    }

    public X509CRL generate(PrivateKey privateKey, String str, SecureRandom secureRandom) {
        TBSCertList a2 = a();
        try {
            return a(a2, X509Util.a(this.b, this.d, str, privateKey, secureRandom, a2));
        } catch (IOException e2) {
            throw new ExtCRLException("cannot generate CRL encoding", e2);
        }
    }

    public X509CRL generate(PrivateKey privateKey, SecureRandom secureRandom) {
        TBSCertList a2 = a();
        try {
            return a(a2, X509Util.a(this.b, this.d, privateKey, secureRandom, a2));
        } catch (IOException e2) {
            throw new ExtCRLException("cannot generate CRL encoding", e2);
        }
    }

    public X509CRL generateX509CRL(PrivateKey privateKey) {
        try {
            return generateX509CRL(privateKey, BouncyCastleProvider.PROVIDER_NAME, null);
        } catch (NoSuchProviderException unused) {
            throw new SecurityException("BC provider not installed!");
        }
    }

    public X509CRL generateX509CRL(PrivateKey privateKey, String str) {
        return generateX509CRL(privateKey, str, null);
    }

    public X509CRL generateX509CRL(PrivateKey privateKey, String str, SecureRandom secureRandom) {
        try {
            return generate(privateKey, str, secureRandom);
        } catch (NoSuchProviderException e2) {
            throw e2;
        } catch (SignatureException e3) {
            throw e3;
        } catch (InvalidKeyException e4) {
            throw e4;
        } catch (GeneralSecurityException e5) {
            StringBuilder sb = new StringBuilder();
            sb.append("exception: ");
            sb.append(e5);
            throw new SecurityException(sb.toString());
        }
    }

    public X509CRL generateX509CRL(PrivateKey privateKey, SecureRandom secureRandom) {
        try {
            return generateX509CRL(privateKey, BouncyCastleProvider.PROVIDER_NAME, secureRandom);
        } catch (NoSuchProviderException unused) {
            throw new SecurityException("BC provider not installed!");
        }
    }

    public Iterator getSignatureAlgNames() {
        return X509Util.a();
    }

    public void reset() {
        this.a = new V2TBSCertListGenerator();
        this.e.reset();
    }

    public void setIssuerDN(X500Principal x500Principal) {
        try {
            this.a.setIssuer((X509Name) new X509Principal(x500Principal.getEncoded()));
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("can't process principal: ");
            sb.append(e2);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public void setIssuerDN(X509Name x509Name) {
        this.a.setIssuer(x509Name);
    }

    public void setNextUpdate(Date date) {
        this.a.setNextUpdate(new Time(date));
    }

    public void setSignatureAlgorithm(String str) {
        this.d = str;
        try {
            this.b = X509Util.a(str);
            this.c = X509Util.a(this.b, str);
            this.a.setSignature(this.c);
        } catch (Exception unused) {
            throw new IllegalArgumentException("Unknown signature type requested");
        }
    }

    public void setThisUpdate(Date date) {
        this.a.setThisUpdate(new Time(date));
    }
}
