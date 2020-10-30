package org.bouncycastle.x509;

import java.io.IOException;
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
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.TBSCertificate;
import org.bouncycastle.asn1.x509.Time;
import org.bouncycastle.asn1.x509.V3TBSCertificateGenerator;
import org.bouncycastle.asn1.x509.X509ExtensionsGenerator;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.X509CertificateObject;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

public class X509V3CertificateGenerator {
    private V3TBSCertificateGenerator a = new V3TBSCertificateGenerator();
    private ASN1ObjectIdentifier b;
    private AlgorithmIdentifier c;
    private String d;
    private X509ExtensionsGenerator e = new X509ExtensionsGenerator();

    private X509Certificate a(TBSCertificate tBSCertificate, byte[] bArr) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(tBSCertificate);
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(new DERBitString(bArr));
        return new X509CertificateObject(Certificate.getInstance(new DERSequence(aSN1EncodableVector)));
    }

    private DERBitString a(boolean[] zArr) {
        byte[] bArr = new byte[((zArr.length + 7) / 8)];
        for (int i = 0; i != zArr.length; i++) {
            int i2 = i / 8;
            bArr[i2] = (byte) (bArr[i2] | (zArr[i] ? 1 << (7 - (i % 8)) : 0));
        }
        int length = zArr.length % 8;
        return length == 0 ? new DERBitString(bArr) : new DERBitString(bArr, 8 - length);
    }

    private TBSCertificate a() {
        if (!this.e.isEmpty()) {
            this.a.setExtensions(this.e.generate());
        }
        return this.a.generateTBSCertificate();
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

    public void copyAndAddExtension(String str, boolean z, X509Certificate x509Certificate) {
        byte[] extensionValue = x509Certificate.getExtensionValue(str);
        if (extensionValue == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("extension ");
            sb.append(str);
            sb.append(" not present");
            throw new CertificateParsingException(sb.toString());
        }
        try {
            addExtension(str, z, (ASN1Encodable) X509ExtensionUtil.fromExtensionValue(extensionValue));
        } catch (IOException e2) {
            throw new CertificateParsingException(e2.toString());
        }
    }

    public void copyAndAddExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, X509Certificate x509Certificate) {
        copyAndAddExtension(aSN1ObjectIdentifier.getId(), z, x509Certificate);
    }

    public X509Certificate generate(PrivateKey privateKey) {
        return generate(privateKey, (SecureRandom) null);
    }

    public X509Certificate generate(PrivateKey privateKey, String str) {
        return generate(privateKey, str, null);
    }

    public X509Certificate generate(PrivateKey privateKey, String str, SecureRandom secureRandom) {
        TBSCertificate a2 = a();
        try {
            try {
                return a(a2, X509Util.a(this.b, this.d, str, privateKey, secureRandom, a2));
            } catch (CertificateParsingException e2) {
                throw new ExtCertificateEncodingException("exception producing certificate object", e2);
            }
        } catch (IOException e3) {
            throw new ExtCertificateEncodingException("exception encoding TBS cert", e3);
        }
    }

    public X509Certificate generate(PrivateKey privateKey, SecureRandom secureRandom) {
        TBSCertificate a2 = a();
        try {
            try {
                return a(a2, X509Util.a(this.b, this.d, privateKey, secureRandom, a2));
            } catch (CertificateParsingException e2) {
                throw new ExtCertificateEncodingException("exception producing certificate object", e2);
            }
        } catch (IOException e3) {
            throw new ExtCertificateEncodingException("exception encoding TBS cert", e3);
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
        this.a = new V3TBSCertificateGenerator();
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

    public void setIssuerUniqueID(boolean[] zArr) {
        this.a.setIssuerUniqueID(a(zArr));
    }

    public void setNotAfter(Date date) {
        this.a.setEndDate(new Time(date));
    }

    public void setNotBefore(Date date) {
        this.a.setStartDate(new Time(date));
    }

    public void setPublicKey(PublicKey publicKey) {
        try {
            this.a.setSubjectPublicKeyInfo(SubjectPublicKeyInfo.getInstance(new ASN1InputStream(publicKey.getEncoded()).readObject()));
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("unable to process key - ");
            sb.append(e2.toString());
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
            StringBuilder sb = new StringBuilder();
            sb.append("Unknown signature type requested: ");
            sb.append(str);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public void setSubjectDN(X500Principal x500Principal) {
        try {
            this.a.setSubject((X509Name) new X509Principal(x500Principal.getEncoded()));
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("can't process principal: ");
            sb.append(e2);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public void setSubjectDN(X509Name x509Name) {
        this.a.setSubject(x509Name);
    }

    public void setSubjectUniqueID(boolean[] zArr) {
        this.a.setSubjectUniqueID(a(zArr));
    }
}
