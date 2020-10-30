package org.bouncycastle.x509;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Iterator;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.TBSCertificate;
import org.bouncycastle.asn1.x509.Time;
import org.bouncycastle.asn1.x509.V1TBSCertificateGenerator;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.X509CertificateObject;

public class X509V1CertificateGenerator {
    private V1TBSCertificateGenerator a = new V1TBSCertificateGenerator();
    private ASN1ObjectIdentifier b;
    private AlgorithmIdentifier c;
    private String d;

    private X509Certificate a(TBSCertificate tBSCertificate, byte[] bArr) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(tBSCertificate);
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(new DERBitString(bArr));
        try {
            return new X509CertificateObject(Certificate.getInstance(new DERSequence(aSN1EncodableVector)));
        } catch (CertificateParsingException e) {
            throw new ExtCertificateEncodingException("exception producing certificate object", e);
        }
    }

    public X509Certificate generate(PrivateKey privateKey) {
        return generate(privateKey, (SecureRandom) null);
    }

    public X509Certificate generate(PrivateKey privateKey, String str) {
        return generate(privateKey, str, null);
    }

    public X509Certificate generate(PrivateKey privateKey, String str, SecureRandom secureRandom) {
        TBSCertificate generateTBSCertificate = this.a.generateTBSCertificate();
        try {
            return a(generateTBSCertificate, X509Util.a(this.b, this.d, str, privateKey, secureRandom, generateTBSCertificate));
        } catch (IOException e) {
            throw new ExtCertificateEncodingException("exception encoding TBS cert", e);
        }
    }

    public X509Certificate generate(PrivateKey privateKey, SecureRandom secureRandom) {
        TBSCertificate generateTBSCertificate = this.a.generateTBSCertificate();
        try {
            return a(generateTBSCertificate, X509Util.a(this.b, this.d, privateKey, secureRandom, generateTBSCertificate));
        } catch (IOException e) {
            throw new ExtCertificateEncodingException("exception encoding TBS cert", e);
        }
    }

    public X509Certificate generateX509Certificate(PrivateKey privateKey) {
        try {
            return generateX509Certificate(privateKey, BouncyCastleProvider.PROVIDER_NAME, null);
        } catch (NoSuchProviderException unused) {
            throw new SecurityException("BC provider not installed!");
        }
    }

    public X509Certificate generateX509Certificate(PrivateKey privateKey, String str) {
        return generateX509Certificate(privateKey, str, null);
    }

    public X509Certificate generateX509Certificate(PrivateKey privateKey, String str, SecureRandom secureRandom) {
        try {
            return generate(privateKey, str, secureRandom);
        } catch (NoSuchProviderException e) {
            throw e;
        } catch (SignatureException e2) {
            throw e2;
        } catch (InvalidKeyException e3) {
            throw e3;
        } catch (GeneralSecurityException e4) {
            StringBuilder sb = new StringBuilder();
            sb.append("exception: ");
            sb.append(e4);
            throw new SecurityException(sb.toString());
        }
    }

    public X509Certificate generateX509Certificate(PrivateKey privateKey, SecureRandom secureRandom) {
        try {
            return generateX509Certificate(privateKey, BouncyCastleProvider.PROVIDER_NAME, secureRandom);
        } catch (NoSuchProviderException unused) {
            throw new SecurityException("BC provider not installed!");
        }
    }

    public Iterator getSignatureAlgNames() {
        return X509Util.a();
    }

    public void reset() {
        this.a = new V1TBSCertificateGenerator();
    }

    public void setIssuerDN(X500Principal x500Principal) {
        try {
            this.a.setIssuer((X509Name) new X509Principal(x500Principal.getEncoded()));
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("can't process principal: ");
            sb.append(e);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public void setIssuerDN(X509Name x509Name) {
        this.a.setIssuer(x509Name);
    }

    public void setNotAfter(Date date) {
        this.a.setEndDate(new Time(date));
    }

    public void setNotBefore(Date date) {
        this.a.setStartDate(new Time(date));
    }

    public void setPublicKey(PublicKey publicKey) {
        try {
            this.a.setSubjectPublicKeyInfo(new SubjectPublicKeyInfo((ASN1Sequence) new ASN1InputStream((InputStream) new ByteArrayInputStream(publicKey.getEncoded())).readObject()));
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("unable to process key - ");
            sb.append(e.toString());
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public void setSerialNumber(BigInteger bigInteger) {
        if (bigInteger.compareTo(BigInteger.ZERO) <= 0) {
            throw new IllegalArgumentException("serial number must be a positive integer");
        }
        this.a.setSerialNumber(new ASN1Integer(bigInteger));
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

    public void setSubjectDN(X500Principal x500Principal) {
        try {
            this.a.setSubject((X509Name) new X509Principal(x500Principal.getEncoded()));
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("can't process principal: ");
            sb.append(e);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public void setSubjectDN(X509Name x509Name) {
        this.a.setSubject(x509Name);
    }
}
