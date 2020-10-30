package org.bouncycastle.jce;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PSSParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.CertificationRequest;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2ParameterSpec;

public class PKCS10CertificationRequest extends CertificationRequest {
    private static Hashtable a = new Hashtable();
    private static Hashtable b = new Hashtable();
    private static Hashtable c = new Hashtable();
    private static Hashtable d = new Hashtable();
    private static Set e = new HashSet();

    static {
        a.put("MD2WITHRSAENCRYPTION", new ASN1ObjectIdentifier("1.2.840.113549.1.1.2"));
        a.put("MD2WITHRSA", new ASN1ObjectIdentifier("1.2.840.113549.1.1.2"));
        a.put("MD5WITHRSAENCRYPTION", new ASN1ObjectIdentifier("1.2.840.113549.1.1.4"));
        a.put("MD5WITHRSA", new ASN1ObjectIdentifier("1.2.840.113549.1.1.4"));
        a.put("RSAWITHMD5", new ASN1ObjectIdentifier("1.2.840.113549.1.1.4"));
        a.put("SHA1WITHRSAENCRYPTION", new ASN1ObjectIdentifier("1.2.840.113549.1.1.5"));
        a.put("SHA1WITHRSA", new ASN1ObjectIdentifier("1.2.840.113549.1.1.5"));
        a.put("SHA224WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha224WithRSAEncryption);
        a.put("SHA224WITHRSA", PKCSObjectIdentifiers.sha224WithRSAEncryption);
        a.put("SHA256WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha256WithRSAEncryption);
        a.put("SHA256WITHRSA", PKCSObjectIdentifiers.sha256WithRSAEncryption);
        a.put("SHA384WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha384WithRSAEncryption);
        a.put("SHA384WITHRSA", PKCSObjectIdentifiers.sha384WithRSAEncryption);
        a.put("SHA512WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha512WithRSAEncryption);
        a.put("SHA512WITHRSA", PKCSObjectIdentifiers.sha512WithRSAEncryption);
        a.put("SHA1WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        a.put("SHA224WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        a.put("SHA256WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        a.put("SHA384WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        a.put("SHA512WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        a.put("RSAWITHSHA1", new ASN1ObjectIdentifier("1.2.840.113549.1.1.5"));
        a.put("RIPEMD128WITHRSAENCRYPTION", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
        a.put("RIPEMD128WITHRSA", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
        a.put("RIPEMD160WITHRSAENCRYPTION", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
        a.put("RIPEMD160WITHRSA", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
        a.put("RIPEMD256WITHRSAENCRYPTION", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
        a.put("RIPEMD256WITHRSA", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
        a.put("SHA1WITHDSA", new ASN1ObjectIdentifier("1.2.840.10040.4.3"));
        a.put("DSAWITHSHA1", new ASN1ObjectIdentifier("1.2.840.10040.4.3"));
        a.put("SHA224WITHDSA", NISTObjectIdentifiers.dsa_with_sha224);
        a.put("SHA256WITHDSA", NISTObjectIdentifiers.dsa_with_sha256);
        a.put("SHA384WITHDSA", NISTObjectIdentifiers.dsa_with_sha384);
        a.put("SHA512WITHDSA", NISTObjectIdentifiers.dsa_with_sha512);
        a.put("SHA1WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA1);
        a.put("SHA224WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA224);
        a.put("SHA256WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA256);
        a.put("SHA384WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA384);
        a.put("SHA512WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA512);
        a.put("ECDSAWITHSHA1", X9ObjectIdentifiers.ecdsa_with_SHA1);
        a.put("GOST3411WITHGOST3410", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
        a.put("GOST3410WITHGOST3411", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
        a.put("GOST3411WITHECGOST3410", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
        a.put("GOST3411WITHECGOST3410-2001", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
        a.put("GOST3411WITHGOST3410-2001", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
        d.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.5"), "SHA1WITHRSA");
        d.put(PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224WITHRSA");
        d.put(PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256WITHRSA");
        d.put(PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384WITHRSA");
        d.put(PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512WITHRSA");
        d.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94, "GOST3411WITHGOST3410");
        d.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001, "GOST3411WITHECGOST3410");
        d.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.4"), "MD5WITHRSA");
        d.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.2"), "MD2WITHRSA");
        d.put(new ASN1ObjectIdentifier("1.2.840.10040.4.3"), "SHA1WITHDSA");
        d.put(X9ObjectIdentifiers.ecdsa_with_SHA1, "SHA1WITHECDSA");
        d.put(X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224WITHECDSA");
        d.put(X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256WITHECDSA");
        d.put(X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384WITHECDSA");
        d.put(X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512WITHECDSA");
        d.put(OIWObjectIdentifiers.sha1WithRSA, "SHA1WITHRSA");
        d.put(OIWObjectIdentifiers.dsaWithSHA1, "SHA1WITHDSA");
        d.put(NISTObjectIdentifiers.dsa_with_sha224, "SHA224WITHDSA");
        d.put(NISTObjectIdentifiers.dsa_with_sha256, "SHA256WITHDSA");
        c.put(PKCSObjectIdentifiers.rsaEncryption, "RSA");
        c.put(X9ObjectIdentifiers.id_dsa, "DSA");
        e.add(X9ObjectIdentifiers.ecdsa_with_SHA1);
        e.add(X9ObjectIdentifiers.ecdsa_with_SHA224);
        e.add(X9ObjectIdentifiers.ecdsa_with_SHA256);
        e.add(X9ObjectIdentifiers.ecdsa_with_SHA384);
        e.add(X9ObjectIdentifiers.ecdsa_with_SHA512);
        e.add(X9ObjectIdentifiers.id_dsa_with_sha1);
        e.add(NISTObjectIdentifiers.dsa_with_sha224);
        e.add(NISTObjectIdentifiers.dsa_with_sha256);
        e.add(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
        e.add(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
        b.put("SHA1WITHRSAANDMGF1", a(new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE), 20));
        b.put("SHA224WITHRSAANDMGF1", a(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha224, DERNull.INSTANCE), 28));
        b.put("SHA256WITHRSAANDMGF1", a(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE), 32));
        b.put("SHA384WITHRSAANDMGF1", a(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384, DERNull.INSTANCE), 48));
        b.put("SHA512WITHRSAANDMGF1", a(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512, DERNull.INSTANCE), 64));
    }

    public PKCS10CertificationRequest(String str, X500Principal x500Principal, PublicKey publicKey, ASN1Set aSN1Set, PrivateKey privateKey) {
        this(str, a(x500Principal), publicKey, aSN1Set, privateKey, BouncyCastleProvider.PROVIDER_NAME);
    }

    public PKCS10CertificationRequest(String str, X500Principal x500Principal, PublicKey publicKey, ASN1Set aSN1Set, PrivateKey privateKey, String str2) {
        this(str, a(x500Principal), publicKey, aSN1Set, privateKey, str2);
    }

    public PKCS10CertificationRequest(String str, X509Name x509Name, PublicKey publicKey, ASN1Set aSN1Set, PrivateKey privateKey) {
        this(str, x509Name, publicKey, aSN1Set, privateKey, BouncyCastleProvider.PROVIDER_NAME);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0080  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public PKCS10CertificationRequest(java.lang.String r5, org.bouncycastle.asn1.x509.X509Name r6, java.security.PublicKey r7, org.bouncycastle.asn1.ASN1Set r8, java.security.PrivateKey r9, java.lang.String r10) {
        /*
            r4 = this;
            r4.<init>()
            java.lang.String r0 = org.bouncycastle.util.Strings.toUpperCase(r5)
            java.util.Hashtable r1 = a
            java.lang.Object r1 = r1.get(r0)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r1
            if (r1 != 0) goto L_0x001f
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = new org.bouncycastle.asn1.ASN1ObjectIdentifier     // Catch:{ Exception -> 0x0017 }
            r1.<init>(r0)     // Catch:{ Exception -> 0x0017 }
            goto L_0x001f
        L_0x0017:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "Unknown signature type requested"
            r5.<init>(r6)
            throw r5
        L_0x001f:
            if (r6 != 0) goto L_0x0029
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "subject must not be null"
            r5.<init>(r6)
            throw r5
        L_0x0029:
            if (r7 != 0) goto L_0x0033
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "public key must not be null"
            r5.<init>(r6)
            throw r5
        L_0x0033:
            java.util.Set r2 = e
            boolean r2 = r2.contains(r1)
            if (r2 == 0) goto L_0x0043
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r0 = new org.bouncycastle.asn1.x509.AlgorithmIdentifier
            r0.<init>(r1)
        L_0x0040:
            r4.sigAlgId = r0
            goto L_0x0063
        L_0x0043:
            java.util.Hashtable r2 = b
            boolean r2 = r2.containsKey(r0)
            if (r2 == 0) goto L_0x005b
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r2 = new org.bouncycastle.asn1.x509.AlgorithmIdentifier
            java.util.Hashtable r3 = b
            java.lang.Object r0 = r3.get(r0)
            org.bouncycastle.asn1.ASN1Encodable r0 = (org.bouncycastle.asn1.ASN1Encodable) r0
            r2.<init>(r1, r0)
            r4.sigAlgId = r2
            goto L_0x0063
        L_0x005b:
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r0 = new org.bouncycastle.asn1.x509.AlgorithmIdentifier
            org.bouncycastle.asn1.DERNull r2 = org.bouncycastle.asn1.DERNull.INSTANCE
            r0.<init>(r1, r2)
            goto L_0x0040
        L_0x0063:
            byte[] r7 = r7.getEncoded()     // Catch:{ IOException -> 0x00b6 }
            org.bouncycastle.asn1.ASN1Primitive r7 = org.bouncycastle.asn1.ASN1Primitive.fromByteArray(r7)     // Catch:{ IOException -> 0x00b6 }
            org.bouncycastle.asn1.ASN1Sequence r7 = (org.bouncycastle.asn1.ASN1Sequence) r7     // Catch:{ IOException -> 0x00b6 }
            org.bouncycastle.asn1.pkcs.CertificationRequestInfo r0 = new org.bouncycastle.asn1.pkcs.CertificationRequestInfo     // Catch:{ IOException -> 0x00b6 }
            org.bouncycastle.asn1.x509.SubjectPublicKeyInfo r1 = new org.bouncycastle.asn1.x509.SubjectPublicKeyInfo     // Catch:{ IOException -> 0x00b6 }
            r1.<init>(r7)     // Catch:{ IOException -> 0x00b6 }
            r0.<init>(r6, r1, r8)     // Catch:{ IOException -> 0x00b6 }
            r4.reqInfo = r0     // Catch:{ IOException -> 0x00b6 }
            if (r10 != 0) goto L_0x0080
            java.security.Signature r5 = java.security.Signature.getInstance(r5)
            goto L_0x0084
        L_0x0080:
            java.security.Signature r5 = java.security.Signature.getInstance(r5, r10)
        L_0x0084:
            r5.initSign(r9)
            org.bouncycastle.asn1.pkcs.CertificationRequestInfo r6 = r4.reqInfo     // Catch:{ Exception -> 0x009e }
            java.lang.String r7 = "DER"
            byte[] r6 = r6.getEncoded(r7)     // Catch:{ Exception -> 0x009e }
            r5.update(r6)     // Catch:{ Exception -> 0x009e }
            org.bouncycastle.asn1.DERBitString r6 = new org.bouncycastle.asn1.DERBitString
            byte[] r5 = r5.sign()
            r6.<init>(r5)
            r4.sigBits = r6
            return
        L_0x009e:
            r5 = move-exception
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "exception encoding TBS cert request - "
            r7.append(r8)
            r7.append(r5)
            java.lang.String r5 = r7.toString()
            r6.<init>(r5)
            throw r6
        L_0x00b6:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "can't encode public key"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.PKCS10CertificationRequest.<init>(java.lang.String, org.bouncycastle.asn1.x509.X509Name, java.security.PublicKey, org.bouncycastle.asn1.ASN1Set, java.security.PrivateKey, java.lang.String):void");
    }

    public PKCS10CertificationRequest(ASN1Sequence aSN1Sequence) {
        super(aSN1Sequence);
    }

    public PKCS10CertificationRequest(byte[] bArr) {
        super(a(bArr));
    }

    private static String a(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return PKCSObjectIdentifiers.md5.equals(aSN1ObjectIdentifier) ? CommonUtils.MD5_INSTANCE : OIWObjectIdentifiers.idSHA1.equals(aSN1ObjectIdentifier) ? "SHA1" : NISTObjectIdentifiers.id_sha224.equals(aSN1ObjectIdentifier) ? "SHA224" : NISTObjectIdentifiers.id_sha256.equals(aSN1ObjectIdentifier) ? McElieceCCA2ParameterSpec.DEFAULT_MD : NISTObjectIdentifiers.id_sha384.equals(aSN1ObjectIdentifier) ? "SHA384" : NISTObjectIdentifiers.id_sha512.equals(aSN1ObjectIdentifier) ? "SHA512" : TeleTrusTObjectIdentifiers.ripemd128.equals(aSN1ObjectIdentifier) ? "RIPEMD128" : TeleTrusTObjectIdentifiers.ripemd160.equals(aSN1ObjectIdentifier) ? "RIPEMD160" : TeleTrusTObjectIdentifiers.ripemd256.equals(aSN1ObjectIdentifier) ? "RIPEMD256" : CryptoProObjectIdentifiers.gostR3411.equals(aSN1ObjectIdentifier) ? "GOST3411" : aSN1ObjectIdentifier.getId();
    }

    static String a(AlgorithmIdentifier algorithmIdentifier) {
        ASN1Encodable parameters = algorithmIdentifier.getParameters();
        if (parameters == null || DERNull.INSTANCE.equals(parameters) || !algorithmIdentifier.getObjectId().equals(PKCSObjectIdentifiers.id_RSASSA_PSS)) {
            return algorithmIdentifier.getObjectId().getId();
        }
        RSASSAPSSparams instance = RSASSAPSSparams.getInstance(parameters);
        StringBuilder sb = new StringBuilder();
        sb.append(a(instance.getHashAlgorithm().getObjectId()));
        sb.append("withRSAandMGF1");
        return sb.toString();
    }

    private static ASN1Sequence a(byte[] bArr) {
        try {
            return (ASN1Sequence) new ASN1InputStream(bArr).readObject();
        } catch (Exception unused) {
            throw new IllegalArgumentException("badly encoded request");
        }
    }

    private static RSASSAPSSparams a(AlgorithmIdentifier algorithmIdentifier, int i) {
        return new RSASSAPSSparams(algorithmIdentifier, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, algorithmIdentifier), new ASN1Integer((long) i), new ASN1Integer(1));
    }

    private static X509Name a(X500Principal x500Principal) {
        try {
            return new X509Principal(x500Principal.getEncoded());
        } catch (IOException unused) {
            throw new IllegalArgumentException("can't convert name");
        }
    }

    private void a(Signature signature, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null && !DERNull.INSTANCE.equals(aSN1Encodable)) {
            AlgorithmParameters instance = AlgorithmParameters.getInstance(signature.getAlgorithm(), signature.getProvider());
            try {
                instance.init(aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER));
                if (signature.getAlgorithm().endsWith("MGF1")) {
                    try {
                        signature.setParameter(instance.getParameterSpec(PSSParameterSpec.class));
                    } catch (GeneralSecurityException e2) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Exception extracting parameters: ");
                        sb.append(e2.getMessage());
                        throw new SignatureException(sb.toString());
                    }
                }
            } catch (IOException e3) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("IOException decoding parameters: ");
                sb2.append(e3.getMessage());
                throw new SignatureException(sb2.toString());
            }
        }
    }

    public byte[] getEncoded() {
        try {
            return getEncoded(ASN1Encoding.DER);
        } catch (IOException e2) {
            throw new RuntimeException(e2.toString());
        }
    }

    public PublicKey getPublicKey() {
        return getPublicKey(BouncyCastleProvider.PROVIDER_NAME);
    }

    public PublicKey getPublicKey(String str) {
        X509EncodedKeySpec x509EncodedKeySpec;
        AlgorithmIdentifier algorithm;
        SubjectPublicKeyInfo subjectPublicKeyInfo = this.reqInfo.getSubjectPublicKeyInfo();
        try {
            x509EncodedKeySpec = new X509EncodedKeySpec(new DERBitString((ASN1Encodable) subjectPublicKeyInfo).getBytes());
            algorithm = subjectPublicKeyInfo.getAlgorithm();
            return str == null ? KeyFactory.getInstance(algorithm.getAlgorithm().getId()).generatePublic(x509EncodedKeySpec) : KeyFactory.getInstance(algorithm.getAlgorithm().getId(), str).generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException e2) {
            if (c.get(algorithm.getObjectId()) != null) {
                String str2 = (String) c.get(algorithm.getObjectId());
                return str == null ? KeyFactory.getInstance(str2).generatePublic(x509EncodedKeySpec) : KeyFactory.getInstance(str2, str).generatePublic(x509EncodedKeySpec);
            }
            throw e2;
        } catch (InvalidKeySpecException unused) {
            throw new InvalidKeyException("error decoding public key");
        } catch (IOException unused2) {
            throw new InvalidKeyException("error decoding public key");
        }
    }

    public boolean verify() {
        return verify(BouncyCastleProvider.PROVIDER_NAME);
    }

    public boolean verify(String str) {
        return verify(getPublicKey(str), str);
    }

    public boolean verify(PublicKey publicKey, String str) {
        Signature signature;
        if (str == null) {
            try {
                signature = Signature.getInstance(a(this.sigAlgId));
            } catch (NoSuchAlgorithmException e2) {
                if (d.get(this.sigAlgId.getObjectId()) != null) {
                    String str2 = (String) d.get(this.sigAlgId.getObjectId());
                    signature = str == null ? Signature.getInstance(str2) : Signature.getInstance(str2, str);
                } else {
                    throw e2;
                }
            }
        } else {
            signature = Signature.getInstance(a(this.sigAlgId), str);
        }
        a(signature, this.sigAlgId.getParameters());
        signature.initVerify(publicKey);
        try {
            signature.update(this.reqInfo.getEncoded(ASN1Encoding.DER));
            return signature.verify(this.sigBits.getBytes());
        } catch (Exception e3) {
            StringBuilder sb = new StringBuilder();
            sb.append("exception encoding TBS cert request - ");
            sb.append(e3);
            throw new SignatureException(sb.toString());
        }
    }
}
