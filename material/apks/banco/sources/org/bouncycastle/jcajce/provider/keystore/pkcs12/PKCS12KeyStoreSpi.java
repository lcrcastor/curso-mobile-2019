package org.bouncycastle.jcajce.provider.keystore.pkcs12;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore.LoadStoreParameter;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.cryptopro.GOST28147Parameters;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PBES2Parameters;
import org.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.jcajce.provider.config.PKCS12StoreParameter;
import org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.bouncycastle.jcajce.spec.GOST28147ParameterSpec;
import org.bouncycastle.jcajce.spec.PBKDF2KeySpec;
import org.bouncycastle.jce.interfaces.BCKeyStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.JDKPKCS12StoreParameter;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Strings;

public class PKCS12KeyStoreSpi extends KeyStoreSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers, BCKeyStore {
    /* access modifiers changed from: private */
    public static final Provider a = new BouncyCastleProvider();
    private static final DefaultSecretKeyProvider b = new DefaultSecretKeyProvider();
    private IgnoresCaseHashtable c = new IgnoresCaseHashtable();
    private Hashtable d = new Hashtable();
    private IgnoresCaseHashtable e = new IgnoresCaseHashtable();
    private Hashtable f = new Hashtable();
    private Hashtable g = new Hashtable();
    private CertificateFactory h;
    private ASN1ObjectIdentifier i;
    private ASN1ObjectIdentifier j;
    protected SecureRandom random = new SecureRandom();

    public static class BCPKCS12KeyStore extends PKCS12KeyStoreSpi {
        public BCPKCS12KeyStore() {
            super(PKCS12KeyStoreSpi.a, pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd40BitRC2_CBC);
        }
    }

    public static class BCPKCS12KeyStore3DES extends PKCS12KeyStoreSpi {
        public BCPKCS12KeyStore3DES() {
            super(PKCS12KeyStoreSpi.a, pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd3_KeyTripleDES_CBC);
        }
    }

    class CertId {
        byte[] a;

        CertId(PublicKey publicKey) {
            this.a = PKCS12KeyStoreSpi.this.a(publicKey).getKeyIdentifier();
        }

        CertId(byte[] bArr) {
            this.a = bArr;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CertId)) {
                return false;
            }
            return Arrays.areEqual(this.a, ((CertId) obj).a);
        }

        public int hashCode() {
            return Arrays.hashCode(this.a);
        }
    }

    public static class DefPKCS12KeyStore extends PKCS12KeyStoreSpi {
        public DefPKCS12KeyStore() {
            super(null, pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd40BitRC2_CBC);
        }
    }

    public static class DefPKCS12KeyStore3DES extends PKCS12KeyStoreSpi {
        public DefPKCS12KeyStore3DES() {
            super(null, pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd3_KeyTripleDES_CBC);
        }
    }

    static class DefaultSecretKeyProvider {
        private final Map a;

        DefaultSecretKeyProvider() {
            HashMap hashMap = new HashMap();
            hashMap.put(new ASN1ObjectIdentifier("1.2.840.113533.7.66.10"), Integers.valueOf(128));
            hashMap.put(PKCSObjectIdentifiers.des_EDE3_CBC.getId(), Integers.valueOf(192));
            hashMap.put(NISTObjectIdentifiers.id_aes128_CBC, Integers.valueOf(128));
            hashMap.put(NISTObjectIdentifiers.id_aes192_CBC, Integers.valueOf(192));
            hashMap.put(NISTObjectIdentifiers.id_aes256_CBC, Integers.valueOf(256));
            hashMap.put(NTTObjectIdentifiers.id_camellia128_cbc, Integers.valueOf(128));
            hashMap.put(NTTObjectIdentifiers.id_camellia192_cbc, Integers.valueOf(192));
            hashMap.put(NTTObjectIdentifiers.id_camellia256_cbc, Integers.valueOf(256));
            hashMap.put(CryptoProObjectIdentifiers.gostR28147_gcfb, Integers.valueOf(256));
            this.a = Collections.unmodifiableMap(hashMap);
        }

        public int a(AlgorithmIdentifier algorithmIdentifier) {
            Integer num = (Integer) this.a.get(algorithmIdentifier.getAlgorithm());
            if (num != null) {
                return num.intValue();
            }
            return -1;
        }
    }

    static class IgnoresCaseHashtable {
        private Hashtable a;
        private Hashtable b;

        private IgnoresCaseHashtable() {
            this.a = new Hashtable();
            this.b = new Hashtable();
        }

        public Object a(String str) {
            String str2 = (String) this.b.remove(str == null ? null : Strings.toLowerCase(str));
            if (str2 == null) {
                return null;
            }
            return this.a.remove(str2);
        }

        public Enumeration a() {
            return this.a.keys();
        }

        public void a(String str, Object obj) {
            String lowerCase = str == null ? null : Strings.toLowerCase(str);
            String str2 = (String) this.b.get(lowerCase);
            if (str2 != null) {
                this.a.remove(str2);
            }
            this.b.put(lowerCase, str);
            this.a.put(str, obj);
        }

        public Object b(String str) {
            String str2 = (String) this.b.get(str == null ? null : Strings.toLowerCase(str));
            if (str2 == null) {
                return null;
            }
            return this.a.get(str2);
        }

        public Enumeration b() {
            return this.a.elements();
        }
    }

    public PKCS12KeyStoreSpi(Provider provider, ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1ObjectIdentifier aSN1ObjectIdentifier2) {
        CertificateFactory instance;
        this.i = aSN1ObjectIdentifier;
        this.j = aSN1ObjectIdentifier2;
        if (provider != null) {
            try {
                instance = CertificateFactory.getInstance("X.509", provider);
            } catch (Exception e2) {
                StringBuilder sb = new StringBuilder();
                sb.append("can't create cert factory - ");
                sb.append(e2.toString());
                throw new IllegalArgumentException(sb.toString());
            }
        } else {
            instance = CertificateFactory.getInstance("X.509");
        }
        this.h = instance;
    }

    private Cipher a(int i2, char[] cArr, AlgorithmIdentifier algorithmIdentifier) {
        SecretKey secretKey;
        AlgorithmParameterSpec gOST28147ParameterSpec;
        PBES2Parameters instance = PBES2Parameters.getInstance(algorithmIdentifier.getParameters());
        PBKDF2Params instance2 = PBKDF2Params.getInstance(instance.getKeyDerivationFunc().getParameters());
        AlgorithmIdentifier instance3 = AlgorithmIdentifier.getInstance(instance.getEncryptionScheme());
        SecretKeyFactory instance4 = SecretKeyFactory.getInstance(instance.getKeyDerivationFunc().getAlgorithm().getId(), a);
        if (instance2.isDefaultPrf()) {
            secretKey = instance4.generateSecret(new PBEKeySpec(cArr, instance2.getSalt(), instance2.getIterationCount().intValue(), b.a(instance3)));
        } else {
            PBKDF2KeySpec pBKDF2KeySpec = new PBKDF2KeySpec(cArr, instance2.getSalt(), instance2.getIterationCount().intValue(), b.a(instance3), instance2.getPrf());
            secretKey = instance4.generateSecret(pBKDF2KeySpec);
        }
        Cipher instance5 = Cipher.getInstance(instance.getEncryptionScheme().getAlgorithm().getId());
        AlgorithmIdentifier.getInstance(instance.getEncryptionScheme());
        ASN1Encodable parameters = instance.getEncryptionScheme().getParameters();
        if (parameters instanceof ASN1OctetString) {
            gOST28147ParameterSpec = new IvParameterSpec(ASN1OctetString.getInstance(parameters).getOctets());
        } else {
            GOST28147Parameters instance6 = GOST28147Parameters.getInstance(parameters);
            gOST28147ParameterSpec = new GOST28147ParameterSpec(instance6.getEncryptionParamSet(), instance6.getIV());
        }
        instance5.init(i2, secretKey, gOST28147ParameterSpec);
        return instance5;
    }

    /* access modifiers changed from: private */
    public SubjectKeyIdentifier a(PublicKey publicKey) {
        try {
            return new SubjectKeyIdentifier(a(new SubjectPublicKeyInfo((ASN1Sequence) ASN1Primitive.fromByteArray(publicKey.getEncoded()))));
        } catch (Exception unused) {
            throw new RuntimeException("error creating key");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x01b4 A[Catch:{ CertificateEncodingException -> 0x025a }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x01cc A[Catch:{ CertificateEncodingException -> 0x025a }, LOOP:3: B:42:0x01c6->B:44:0x01cc, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.io.OutputStream r21, char[] r22, boolean r23) {
        /*
            r20 = this;
            r7 = r20
            r8 = r21
            r9 = r22
            if (r9 != 0) goto L_0x0010
            java.lang.NullPointerException r1 = new java.lang.NullPointerException
            java.lang.String r2 = "No password supplied for PKCS#12 KeyStore."
            r1.<init>(r2)
            throw r1
        L_0x0010:
            org.bouncycastle.asn1.ASN1EncodableVector r1 = new org.bouncycastle.asn1.ASN1EncodableVector
            r1.<init>()
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r2 = r7.c
            java.util.Enumeration r2 = r2.a()
        L_0x001b:
            boolean r3 = r2.hasMoreElements()
            r10 = 1024(0x400, float:1.435E-42)
            r11 = 20
            if (r3 == 0) goto L_0x0127
            byte[] r3 = new byte[r11]
            java.security.SecureRandom r4 = r7.random
            r4.nextBytes(r3)
            java.lang.Object r4 = r2.nextElement()
            java.lang.String r4 = (java.lang.String) r4
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r5 = r7.c
            java.lang.Object r5 = r5.b(r4)
            java.security.PrivateKey r5 = (java.security.PrivateKey) r5
            org.bouncycastle.asn1.pkcs.PKCS12PBEParams r6 = new org.bouncycastle.asn1.pkcs.PKCS12PBEParams
            r6.<init>(r3, r10)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = r7.i
            java.lang.String r3 = r3.getId()
            byte[] r3 = r7.wrapKey(r3, r5, r6, r9)
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r10 = new org.bouncycastle.asn1.x509.AlgorithmIdentifier
            org.bouncycastle.asn1.ASN1ObjectIdentifier r11 = r7.i
            org.bouncycastle.asn1.ASN1Primitive r6 = r6.toASN1Primitive()
            r10.<init>(r11, r6)
            org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo r6 = new org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo
            r6.<init>(r10, r3)
            org.bouncycastle.asn1.ASN1EncodableVector r3 = new org.bouncycastle.asn1.ASN1EncodableVector
            r3.<init>()
            boolean r10 = r5 instanceof org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
            if (r10 == 0) goto L_0x00ca
            org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier r5 = (org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) r5
            org.bouncycastle.asn1.ASN1ObjectIdentifier r10 = pkcs_9_at_friendlyName
            org.bouncycastle.asn1.ASN1Encodable r10 = r5.getBagAttribute(r10)
            org.bouncycastle.asn1.DERBMPString r10 = (org.bouncycastle.asn1.DERBMPString) r10
            if (r10 == 0) goto L_0x0078
            java.lang.String r10 = r10.getString()
            boolean r10 = r10.equals(r4)
            if (r10 != 0) goto L_0x0082
        L_0x0078:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r10 = pkcs_9_at_friendlyName
            org.bouncycastle.asn1.DERBMPString r11 = new org.bouncycastle.asn1.DERBMPString
            r11.<init>(r4)
            r5.setBagAttribute(r10, r11)
        L_0x0082:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r10 = pkcs_9_at_localKeyId
            org.bouncycastle.asn1.ASN1Encodable r10 = r5.getBagAttribute(r10)
            if (r10 != 0) goto L_0x009b
            java.security.cert.Certificate r10 = r7.engineGetCertificate(r4)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r11 = pkcs_9_at_localKeyId
            java.security.PublicKey r10 = r10.getPublicKey()
            org.bouncycastle.asn1.x509.SubjectKeyIdentifier r10 = r7.a(r10)
            r5.setBagAttribute(r11, r10)
        L_0x009b:
            java.util.Enumeration r10 = r5.getBagAttributeKeys()
            r13 = 0
        L_0x00a0:
            boolean r11 = r10.hasMoreElements()
            if (r11 == 0) goto L_0x00cb
            java.lang.Object r11 = r10.nextElement()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r11 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r11
            org.bouncycastle.asn1.ASN1EncodableVector r13 = new org.bouncycastle.asn1.ASN1EncodableVector
            r13.<init>()
            r13.add(r11)
            org.bouncycastle.asn1.DERSet r14 = new org.bouncycastle.asn1.DERSet
            org.bouncycastle.asn1.ASN1Encodable r11 = r5.getBagAttribute(r11)
            r14.<init>(r11)
            r13.add(r14)
            org.bouncycastle.asn1.DERSequence r11 = new org.bouncycastle.asn1.DERSequence
            r11.<init>(r13)
            r3.add(r11)
            r13 = 1
            goto L_0x00a0
        L_0x00ca:
            r13 = 0
        L_0x00cb:
            if (r13 != 0) goto L_0x0112
            org.bouncycastle.asn1.ASN1EncodableVector r5 = new org.bouncycastle.asn1.ASN1EncodableVector
            r5.<init>()
            java.security.cert.Certificate r10 = r7.engineGetCertificate(r4)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r11 = pkcs_9_at_localKeyId
            r5.add(r11)
            org.bouncycastle.asn1.DERSet r11 = new org.bouncycastle.asn1.DERSet
            java.security.PublicKey r10 = r10.getPublicKey()
            org.bouncycastle.asn1.x509.SubjectKeyIdentifier r10 = r7.a(r10)
            r11.<init>(r10)
            r5.add(r11)
            org.bouncycastle.asn1.DERSequence r10 = new org.bouncycastle.asn1.DERSequence
            r10.<init>(r5)
            r3.add(r10)
            org.bouncycastle.asn1.ASN1EncodableVector r5 = new org.bouncycastle.asn1.ASN1EncodableVector
            r5.<init>()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r10 = pkcs_9_at_friendlyName
            r5.add(r10)
            org.bouncycastle.asn1.DERSet r10 = new org.bouncycastle.asn1.DERSet
            org.bouncycastle.asn1.DERBMPString r11 = new org.bouncycastle.asn1.DERBMPString
            r11.<init>(r4)
            r10.<init>(r11)
            r5.add(r10)
            org.bouncycastle.asn1.DERSequence r4 = new org.bouncycastle.asn1.DERSequence
            r4.<init>(r5)
            r3.add(r4)
        L_0x0112:
            org.bouncycastle.asn1.pkcs.SafeBag r4 = new org.bouncycastle.asn1.pkcs.SafeBag
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = pkcs8ShroudedKeyBag
            org.bouncycastle.asn1.ASN1Primitive r6 = r6.toASN1Primitive()
            org.bouncycastle.asn1.DERSet r10 = new org.bouncycastle.asn1.DERSet
            r10.<init>(r3)
            r4.<init>(r5, r6, r10)
            r1.add(r4)
            goto L_0x001b
        L_0x0127:
            org.bouncycastle.asn1.DERSequence r2 = new org.bouncycastle.asn1.DERSequence
            r2.<init>(r1)
            java.lang.String r1 = "DER"
            byte[] r1 = r2.getEncoded(r1)
            org.bouncycastle.asn1.BEROctetString r14 = new org.bouncycastle.asn1.BEROctetString
            r14.<init>(r1)
            byte[] r1 = new byte[r11]
            java.security.SecureRandom r2 = r7.random
            r2.nextBytes(r1)
            org.bouncycastle.asn1.ASN1EncodableVector r2 = new org.bouncycastle.asn1.ASN1EncodableVector
            r2.<init>()
            org.bouncycastle.asn1.pkcs.PKCS12PBEParams r3 = new org.bouncycastle.asn1.pkcs.PKCS12PBEParams
            r3.<init>(r1, r10)
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r6 = new org.bouncycastle.asn1.x509.AlgorithmIdentifier
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r7.j
            org.bouncycastle.asn1.ASN1Primitive r3 = r3.toASN1Primitive()
            r6.<init>(r1, r3)
            java.util.Hashtable r1 = new java.util.Hashtable
            r1.<init>()
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r3 = r7.c
            java.util.Enumeration r3 = r3.a()
        L_0x015e:
            boolean r4 = r3.hasMoreElements()
            if (r4 == 0) goto L_0x0277
            java.lang.Object r4 = r3.nextElement()     // Catch:{ CertificateEncodingException -> 0x025a }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ CertificateEncodingException -> 0x025a }
            java.security.cert.Certificate r5 = r7.engineGetCertificate(r4)     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.pkcs.CertBag r10 = new org.bouncycastle.asn1.pkcs.CertBag     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r11 = x509Certificate     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.DEROctetString r12 = new org.bouncycastle.asn1.DEROctetString     // Catch:{ CertificateEncodingException -> 0x025a }
            byte[] r13 = r5.getEncoded()     // Catch:{ CertificateEncodingException -> 0x025a }
            r12.<init>(r13)     // Catch:{ CertificateEncodingException -> 0x025a }
            r10.<init>(r11, r12)     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.ASN1EncodableVector r11 = new org.bouncycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x025a }
            r11.<init>()     // Catch:{ CertificateEncodingException -> 0x025a }
            boolean r12 = r5 instanceof org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier     // Catch:{ CertificateEncodingException -> 0x025a }
            if (r12 == 0) goto L_0x01f6
            r12 = r5
            org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier r12 = (org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) r12     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r13 = pkcs_9_at_friendlyName     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.ASN1Encodable r13 = r12.getBagAttribute(r13)     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.DERBMPString r13 = (org.bouncycastle.asn1.DERBMPString) r13     // Catch:{ CertificateEncodingException -> 0x025a }
            if (r13 == 0) goto L_0x01a1
            java.lang.String r13 = r13.getString()     // Catch:{ CertificateEncodingException -> 0x025a }
            boolean r13 = r13.equals(r4)     // Catch:{ CertificateEncodingException -> 0x025a }
            if (r13 != 0) goto L_0x019f
            goto L_0x01a1
        L_0x019f:
            r15 = r3
            goto L_0x01ac
        L_0x01a1:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r13 = pkcs_9_at_friendlyName     // Catch:{ CertificateEncodingException -> 0x025a }
            r15 = r3
            org.bouncycastle.asn1.DERBMPString r3 = new org.bouncycastle.asn1.DERBMPString     // Catch:{ CertificateEncodingException -> 0x025a }
            r3.<init>(r4)     // Catch:{ CertificateEncodingException -> 0x025a }
            r12.setBagAttribute(r13, r3)     // Catch:{ CertificateEncodingException -> 0x025a }
        L_0x01ac:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = pkcs_9_at_localKeyId     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.ASN1Encodable r3 = r12.getBagAttribute(r3)     // Catch:{ CertificateEncodingException -> 0x025a }
            if (r3 != 0) goto L_0x01c1
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = pkcs_9_at_localKeyId     // Catch:{ CertificateEncodingException -> 0x025a }
            java.security.PublicKey r13 = r5.getPublicKey()     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.x509.SubjectKeyIdentifier r13 = r7.a(r13)     // Catch:{ CertificateEncodingException -> 0x025a }
            r12.setBagAttribute(r3, r13)     // Catch:{ CertificateEncodingException -> 0x025a }
        L_0x01c1:
            java.util.Enumeration r3 = r12.getBagAttributeKeys()     // Catch:{ CertificateEncodingException -> 0x025a }
            r13 = 0
        L_0x01c6:
            boolean r16 = r3.hasMoreElements()     // Catch:{ CertificateEncodingException -> 0x025a }
            if (r16 == 0) goto L_0x01f8
            java.lang.Object r13 = r3.nextElement()     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r13 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r13     // Catch:{ CertificateEncodingException -> 0x025a }
            r17 = r3
            org.bouncycastle.asn1.ASN1EncodableVector r3 = new org.bouncycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x025a }
            r3.<init>()     // Catch:{ CertificateEncodingException -> 0x025a }
            r3.add(r13)     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.DERSet r8 = new org.bouncycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.ASN1Encodable r13 = r12.getBagAttribute(r13)     // Catch:{ CertificateEncodingException -> 0x025a }
            r8.<init>(r13)     // Catch:{ CertificateEncodingException -> 0x025a }
            r3.add(r8)     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.DERSequence r8 = new org.bouncycastle.asn1.DERSequence     // Catch:{ CertificateEncodingException -> 0x025a }
            r8.<init>(r3)     // Catch:{ CertificateEncodingException -> 0x025a }
            r11.add(r8)     // Catch:{ CertificateEncodingException -> 0x025a }
            r3 = r17
            r8 = r21
            r13 = 1
            goto L_0x01c6
        L_0x01f6:
            r15 = r3
            r13 = 0
        L_0x01f8:
            if (r13 != 0) goto L_0x023b
            org.bouncycastle.asn1.ASN1EncodableVector r3 = new org.bouncycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x025a }
            r3.<init>()     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r8 = pkcs_9_at_localKeyId     // Catch:{ CertificateEncodingException -> 0x025a }
            r3.add(r8)     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.DERSet r8 = new org.bouncycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x025a }
            java.security.PublicKey r12 = r5.getPublicKey()     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.x509.SubjectKeyIdentifier r12 = r7.a(r12)     // Catch:{ CertificateEncodingException -> 0x025a }
            r8.<init>(r12)     // Catch:{ CertificateEncodingException -> 0x025a }
            r3.add(r8)     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.DERSequence r8 = new org.bouncycastle.asn1.DERSequence     // Catch:{ CertificateEncodingException -> 0x025a }
            r8.<init>(r3)     // Catch:{ CertificateEncodingException -> 0x025a }
            r11.add(r8)     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.ASN1EncodableVector r3 = new org.bouncycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x025a }
            r3.<init>()     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r8 = pkcs_9_at_friendlyName     // Catch:{ CertificateEncodingException -> 0x025a }
            r3.add(r8)     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.DERSet r8 = new org.bouncycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.DERBMPString r12 = new org.bouncycastle.asn1.DERBMPString     // Catch:{ CertificateEncodingException -> 0x025a }
            r12.<init>(r4)     // Catch:{ CertificateEncodingException -> 0x025a }
            r8.<init>(r12)     // Catch:{ CertificateEncodingException -> 0x025a }
            r3.add(r8)     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.DERSequence r4 = new org.bouncycastle.asn1.DERSequence     // Catch:{ CertificateEncodingException -> 0x025a }
            r4.<init>(r3)     // Catch:{ CertificateEncodingException -> 0x025a }
            r11.add(r4)     // Catch:{ CertificateEncodingException -> 0x025a }
        L_0x023b:
            org.bouncycastle.asn1.pkcs.SafeBag r3 = new org.bouncycastle.asn1.pkcs.SafeBag     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = certBag     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.ASN1Primitive r8 = r10.toASN1Primitive()     // Catch:{ CertificateEncodingException -> 0x025a }
            org.bouncycastle.asn1.DERSet r10 = new org.bouncycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x025a }
            r10.<init>(r11)     // Catch:{ CertificateEncodingException -> 0x025a }
            r3.<init>(r4, r8, r10)     // Catch:{ CertificateEncodingException -> 0x025a }
            r2.add(r3)     // Catch:{ CertificateEncodingException -> 0x025a }
            r1.put(r5, r5)     // Catch:{ CertificateEncodingException -> 0x025a }
            r3 = r15
            r8 = r21
            r10 = 1024(0x400, float:1.435E-42)
            r11 = 20
            goto L_0x015e
        L_0x025a:
            r0 = move-exception
            r1 = r0
            java.io.IOException r2 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Error encoding certificate: "
            r3.append(r4)
            java.lang.String r1 = r1.toString()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            r2.<init>(r1)
            throw r2
        L_0x0277:
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r3 = r7.e
            java.util.Enumeration r3 = r3.a()
        L_0x027d:
            boolean r4 = r3.hasMoreElements()
            if (r4 == 0) goto L_0x0377
            java.lang.Object r4 = r3.nextElement()     // Catch:{ CertificateEncodingException -> 0x035a }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r5 = r7.e     // Catch:{ CertificateEncodingException -> 0x035a }
            java.lang.Object r5 = r5.b(r4)     // Catch:{ CertificateEncodingException -> 0x035a }
            java.security.cert.Certificate r5 = (java.security.cert.Certificate) r5     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r8 = r7.c     // Catch:{ CertificateEncodingException -> 0x035a }
            java.lang.Object r8 = r8.b(r4)     // Catch:{ CertificateEncodingException -> 0x035a }
            if (r8 == 0) goto L_0x029a
            goto L_0x027d
        L_0x029a:
            org.bouncycastle.asn1.pkcs.CertBag r8 = new org.bouncycastle.asn1.pkcs.CertBag     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r10 = x509Certificate     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.DEROctetString r11 = new org.bouncycastle.asn1.DEROctetString     // Catch:{ CertificateEncodingException -> 0x035a }
            byte[] r12 = r5.getEncoded()     // Catch:{ CertificateEncodingException -> 0x035a }
            r11.<init>(r12)     // Catch:{ CertificateEncodingException -> 0x035a }
            r8.<init>(r10, r11)     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.ASN1EncodableVector r10 = new org.bouncycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x035a }
            r10.<init>()     // Catch:{ CertificateEncodingException -> 0x035a }
            boolean r11 = r5 instanceof org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier     // Catch:{ CertificateEncodingException -> 0x035a }
            if (r11 == 0) goto L_0x031c
            r11 = r5
            org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier r11 = (org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) r11     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r12 = pkcs_9_at_friendlyName     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.ASN1Encodable r12 = r11.getBagAttribute(r12)     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.DERBMPString r12 = (org.bouncycastle.asn1.DERBMPString) r12     // Catch:{ CertificateEncodingException -> 0x035a }
            if (r12 == 0) goto L_0x02ca
            java.lang.String r12 = r12.getString()     // Catch:{ CertificateEncodingException -> 0x035a }
            boolean r12 = r12.equals(r4)     // Catch:{ CertificateEncodingException -> 0x035a }
            if (r12 != 0) goto L_0x02d4
        L_0x02ca:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r12 = pkcs_9_at_friendlyName     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.DERBMPString r13 = new org.bouncycastle.asn1.DERBMPString     // Catch:{ CertificateEncodingException -> 0x035a }
            r13.<init>(r4)     // Catch:{ CertificateEncodingException -> 0x035a }
            r11.setBagAttribute(r12, r13)     // Catch:{ CertificateEncodingException -> 0x035a }
        L_0x02d4:
            java.util.Enumeration r12 = r11.getBagAttributeKeys()     // Catch:{ CertificateEncodingException -> 0x035a }
            r13 = 0
        L_0x02d9:
            boolean r15 = r12.hasMoreElements()     // Catch:{ CertificateEncodingException -> 0x035a }
            if (r15 == 0) goto L_0x0319
            java.lang.Object r15 = r12.nextElement()     // Catch:{ CertificateEncodingException -> 0x035a }
            r18 = r3
            r3 = r15
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r3     // Catch:{ CertificateEncodingException -> 0x035a }
            r19 = r12
            org.bouncycastle.asn1.ASN1ObjectIdentifier r12 = org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pkcs_9_at_localKeyId     // Catch:{ CertificateEncodingException -> 0x035a }
            boolean r12 = r3.equals(r12)     // Catch:{ CertificateEncodingException -> 0x035a }
            if (r12 == 0) goto L_0x02f7
            r3 = r18
            r12 = r19
            goto L_0x02d9
        L_0x02f7:
            org.bouncycastle.asn1.ASN1EncodableVector r12 = new org.bouncycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x035a }
            r12.<init>()     // Catch:{ CertificateEncodingException -> 0x035a }
            r12.add(r3)     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.DERSet r13 = new org.bouncycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.ASN1Encodable r3 = r11.getBagAttribute(r3)     // Catch:{ CertificateEncodingException -> 0x035a }
            r13.<init>(r3)     // Catch:{ CertificateEncodingException -> 0x035a }
            r12.add(r13)     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.DERSequence r3 = new org.bouncycastle.asn1.DERSequence     // Catch:{ CertificateEncodingException -> 0x035a }
            r3.<init>(r12)     // Catch:{ CertificateEncodingException -> 0x035a }
            r10.add(r3)     // Catch:{ CertificateEncodingException -> 0x035a }
            r3 = r18
            r12 = r19
            r13 = 1
            goto L_0x02d9
        L_0x0319:
            r18 = r3
            goto L_0x031f
        L_0x031c:
            r18 = r3
            r13 = 0
        L_0x031f:
            if (r13 != 0) goto L_0x0340
            org.bouncycastle.asn1.ASN1EncodableVector r3 = new org.bouncycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x035a }
            r3.<init>()     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r11 = pkcs_9_at_friendlyName     // Catch:{ CertificateEncodingException -> 0x035a }
            r3.add(r11)     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.DERSet r11 = new org.bouncycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.DERBMPString r12 = new org.bouncycastle.asn1.DERBMPString     // Catch:{ CertificateEncodingException -> 0x035a }
            r12.<init>(r4)     // Catch:{ CertificateEncodingException -> 0x035a }
            r11.<init>(r12)     // Catch:{ CertificateEncodingException -> 0x035a }
            r3.add(r11)     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.DERSequence r4 = new org.bouncycastle.asn1.DERSequence     // Catch:{ CertificateEncodingException -> 0x035a }
            r4.<init>(r3)     // Catch:{ CertificateEncodingException -> 0x035a }
            r10.add(r4)     // Catch:{ CertificateEncodingException -> 0x035a }
        L_0x0340:
            org.bouncycastle.asn1.pkcs.SafeBag r3 = new org.bouncycastle.asn1.pkcs.SafeBag     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = certBag     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.ASN1Primitive r8 = r8.toASN1Primitive()     // Catch:{ CertificateEncodingException -> 0x035a }
            org.bouncycastle.asn1.DERSet r11 = new org.bouncycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x035a }
            r11.<init>(r10)     // Catch:{ CertificateEncodingException -> 0x035a }
            r3.<init>(r4, r8, r11)     // Catch:{ CertificateEncodingException -> 0x035a }
            r2.add(r3)     // Catch:{ CertificateEncodingException -> 0x035a }
            r1.put(r5, r5)     // Catch:{ CertificateEncodingException -> 0x035a }
            r3 = r18
            goto L_0x027d
        L_0x035a:
            r0 = move-exception
            r1 = r0
            java.io.IOException r2 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Error encoding certificate: "
            r3.append(r4)
            java.lang.String r1 = r1.toString()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            r2.<init>(r1)
            throw r2
        L_0x0377:
            java.util.Hashtable r3 = r7.f
            java.util.Enumeration r3 = r3.keys()
        L_0x037d:
            boolean r4 = r3.hasMoreElements()
            if (r4 == 0) goto L_0x041a
            java.lang.Object r4 = r3.nextElement()     // Catch:{ CertificateEncodingException -> 0x03fd }
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId r4 = (org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.CertId) r4     // Catch:{ CertificateEncodingException -> 0x03fd }
            java.util.Hashtable r5 = r7.f     // Catch:{ CertificateEncodingException -> 0x03fd }
            java.lang.Object r4 = r5.get(r4)     // Catch:{ CertificateEncodingException -> 0x03fd }
            java.security.cert.Certificate r4 = (java.security.cert.Certificate) r4     // Catch:{ CertificateEncodingException -> 0x03fd }
            java.lang.Object r5 = r1.get(r4)     // Catch:{ CertificateEncodingException -> 0x03fd }
            if (r5 == 0) goto L_0x0398
            goto L_0x037d
        L_0x0398:
            org.bouncycastle.asn1.pkcs.CertBag r5 = new org.bouncycastle.asn1.pkcs.CertBag     // Catch:{ CertificateEncodingException -> 0x03fd }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r8 = x509Certificate     // Catch:{ CertificateEncodingException -> 0x03fd }
            org.bouncycastle.asn1.DEROctetString r10 = new org.bouncycastle.asn1.DEROctetString     // Catch:{ CertificateEncodingException -> 0x03fd }
            byte[] r11 = r4.getEncoded()     // Catch:{ CertificateEncodingException -> 0x03fd }
            r10.<init>(r11)     // Catch:{ CertificateEncodingException -> 0x03fd }
            r5.<init>(r8, r10)     // Catch:{ CertificateEncodingException -> 0x03fd }
            org.bouncycastle.asn1.ASN1EncodableVector r8 = new org.bouncycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x03fd }
            r8.<init>()     // Catch:{ CertificateEncodingException -> 0x03fd }
            boolean r10 = r4 instanceof org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier     // Catch:{ CertificateEncodingException -> 0x03fd }
            if (r10 == 0) goto L_0x03e9
            org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier r4 = (org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) r4     // Catch:{ CertificateEncodingException -> 0x03fd }
            java.util.Enumeration r10 = r4.getBagAttributeKeys()     // Catch:{ CertificateEncodingException -> 0x03fd }
        L_0x03b7:
            boolean r11 = r10.hasMoreElements()     // Catch:{ CertificateEncodingException -> 0x03fd }
            if (r11 == 0) goto L_0x03e9
            java.lang.Object r11 = r10.nextElement()     // Catch:{ CertificateEncodingException -> 0x03fd }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r11 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r11     // Catch:{ CertificateEncodingException -> 0x03fd }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r12 = org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pkcs_9_at_localKeyId     // Catch:{ CertificateEncodingException -> 0x03fd }
            boolean r12 = r11.equals(r12)     // Catch:{ CertificateEncodingException -> 0x03fd }
            if (r12 == 0) goto L_0x03cc
            goto L_0x03b7
        L_0x03cc:
            org.bouncycastle.asn1.ASN1EncodableVector r12 = new org.bouncycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x03fd }
            r12.<init>()     // Catch:{ CertificateEncodingException -> 0x03fd }
            r12.add(r11)     // Catch:{ CertificateEncodingException -> 0x03fd }
            org.bouncycastle.asn1.DERSet r13 = new org.bouncycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x03fd }
            org.bouncycastle.asn1.ASN1Encodable r11 = r4.getBagAttribute(r11)     // Catch:{ CertificateEncodingException -> 0x03fd }
            r13.<init>(r11)     // Catch:{ CertificateEncodingException -> 0x03fd }
            r12.add(r13)     // Catch:{ CertificateEncodingException -> 0x03fd }
            org.bouncycastle.asn1.DERSequence r11 = new org.bouncycastle.asn1.DERSequence     // Catch:{ CertificateEncodingException -> 0x03fd }
            r11.<init>(r12)     // Catch:{ CertificateEncodingException -> 0x03fd }
            r8.add(r11)     // Catch:{ CertificateEncodingException -> 0x03fd }
            goto L_0x03b7
        L_0x03e9:
            org.bouncycastle.asn1.pkcs.SafeBag r4 = new org.bouncycastle.asn1.pkcs.SafeBag     // Catch:{ CertificateEncodingException -> 0x03fd }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r10 = certBag     // Catch:{ CertificateEncodingException -> 0x03fd }
            org.bouncycastle.asn1.ASN1Primitive r5 = r5.toASN1Primitive()     // Catch:{ CertificateEncodingException -> 0x03fd }
            org.bouncycastle.asn1.DERSet r11 = new org.bouncycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x03fd }
            r11.<init>(r8)     // Catch:{ CertificateEncodingException -> 0x03fd }
            r4.<init>(r10, r5, r11)     // Catch:{ CertificateEncodingException -> 0x03fd }
            r2.add(r4)     // Catch:{ CertificateEncodingException -> 0x03fd }
            goto L_0x037d
        L_0x03fd:
            r0 = move-exception
            r1 = r0
            java.io.IOException r2 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Error encoding certificate: "
            r3.append(r4)
            java.lang.String r1 = r1.toString()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            r2.<init>(r1)
            throw r2
        L_0x041a:
            org.bouncycastle.asn1.DERSequence r1 = new org.bouncycastle.asn1.DERSequence
            r1.<init>(r2)
            java.lang.String r2 = "DER"
            byte[] r8 = r1.getEncoded(r2)
            r2 = 1
            r5 = 0
            r1 = r7
            r3 = r6
            r4 = r9
            r10 = r6
            r6 = r8
            byte[] r1 = r1.cryptData(r2, r3, r4, r5, r6)
            org.bouncycastle.asn1.pkcs.EncryptedData r2 = new org.bouncycastle.asn1.pkcs.EncryptedData
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = data
            org.bouncycastle.asn1.BEROctetString r4 = new org.bouncycastle.asn1.BEROctetString
            r4.<init>(r1)
            r2.<init>(r3, r10, r4)
            r1 = 2
            org.bouncycastle.asn1.pkcs.ContentInfo[] r1 = new org.bouncycastle.asn1.pkcs.ContentInfo[r1]
            org.bouncycastle.asn1.pkcs.ContentInfo r3 = new org.bouncycastle.asn1.pkcs.ContentInfo
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = data
            r3.<init>(r4, r14)
            r4 = 0
            r1[r4] = r3
            org.bouncycastle.asn1.pkcs.ContentInfo r3 = new org.bouncycastle.asn1.pkcs.ContentInfo
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = encryptedData
            org.bouncycastle.asn1.ASN1Primitive r2 = r2.toASN1Primitive()
            r3.<init>(r4, r2)
            r2 = 1
            r1[r2] = r3
            org.bouncycastle.asn1.pkcs.AuthenticatedSafe r2 = new org.bouncycastle.asn1.pkcs.AuthenticatedSafe
            r2.<init>(r1)
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream
            r1.<init>()
            if (r23 == 0) goto L_0x0469
            org.bouncycastle.asn1.DEROutputStream r3 = new org.bouncycastle.asn1.DEROutputStream
            r3.<init>(r1)
            goto L_0x046e
        L_0x0469:
            org.bouncycastle.asn1.BEROutputStream r3 = new org.bouncycastle.asn1.BEROutputStream
            r3.<init>(r1)
        L_0x046e:
            r3.writeObject(r2)
            byte[] r1 = r1.toByteArray()
            org.bouncycastle.asn1.pkcs.ContentInfo r8 = new org.bouncycastle.asn1.pkcs.ContentInfo
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = data
            org.bouncycastle.asn1.BEROctetString r3 = new org.bouncycastle.asn1.BEROctetString
            r3.<init>(r1)
            r8.<init>(r2, r3)
            r1 = 20
            byte[] r10 = new byte[r1]
            java.security.SecureRandom r1 = r7.random
            r1.nextBytes(r10)
            org.bouncycastle.asn1.ASN1Encodable r1 = r8.getContent()
            org.bouncycastle.asn1.ASN1OctetString r1 = (org.bouncycastle.asn1.ASN1OctetString) r1
            byte[] r6 = r1.getOctets()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = id_SHA1     // Catch:{ Exception -> 0x04ce }
            r5 = 0
            r3 = 1024(0x400, float:1.435E-42)
            r2 = r10
            r4 = r9
            byte[] r1 = a(r1, r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x04ce }
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r2 = new org.bouncycastle.asn1.x509.AlgorithmIdentifier     // Catch:{ Exception -> 0x04ce }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = id_SHA1     // Catch:{ Exception -> 0x04ce }
            org.bouncycastle.asn1.DERNull r4 = org.bouncycastle.asn1.DERNull.INSTANCE     // Catch:{ Exception -> 0x04ce }
            r2.<init>(r3, r4)     // Catch:{ Exception -> 0x04ce }
            org.bouncycastle.asn1.x509.DigestInfo r3 = new org.bouncycastle.asn1.x509.DigestInfo     // Catch:{ Exception -> 0x04ce }
            r3.<init>(r2, r1)     // Catch:{ Exception -> 0x04ce }
            org.bouncycastle.asn1.pkcs.MacData r1 = new org.bouncycastle.asn1.pkcs.MacData     // Catch:{ Exception -> 0x04ce }
            r2 = 1024(0x400, float:1.435E-42)
            r1.<init>(r3, r10, r2)     // Catch:{ Exception -> 0x04ce }
            org.bouncycastle.asn1.pkcs.Pfx r2 = new org.bouncycastle.asn1.pkcs.Pfx
            r2.<init>(r8, r1)
            if (r23 == 0) goto L_0x04c3
            org.bouncycastle.asn1.DEROutputStream r1 = new org.bouncycastle.asn1.DEROutputStream
            r3 = r21
            r1.<init>(r3)
            goto L_0x04ca
        L_0x04c3:
            r3 = r21
            org.bouncycastle.asn1.BEROutputStream r1 = new org.bouncycastle.asn1.BEROutputStream
            r1.<init>(r3)
        L_0x04ca:
            r1.writeObject(r2)
            return
        L_0x04ce:
            r0 = move-exception
            r1 = r0
            java.io.IOException r2 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "error constructing MAC: "
            r3.append(r4)
            java.lang.String r1 = r1.toString()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            r2.<init>(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.a(java.io.OutputStream, char[], boolean):void");
    }

    private static byte[] a(ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] bArr, int i2, char[] cArr, boolean z, byte[] bArr2) {
        SecretKeyFactory instance = SecretKeyFactory.getInstance(aSN1ObjectIdentifier.getId(), a);
        PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(bArr, i2);
        BCPBEKey bCPBEKey = (BCPBEKey) instance.generateSecret(new PBEKeySpec(cArr));
        bCPBEKey.setTryWrongPKCS12Zero(z);
        Mac instance2 = Mac.getInstance(aSN1ObjectIdentifier.getId(), a);
        instance2.init(bCPBEKey, pBEParameterSpec);
        instance2.update(bArr2);
        return instance2.doFinal();
    }

    private static byte[] a(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        SHA1Digest sHA1Digest = new SHA1Digest();
        byte[] bArr = new byte[sHA1Digest.getDigestSize()];
        byte[] bytes = subjectPublicKeyInfo.getPublicKeyData().getBytes();
        sHA1Digest.update(bytes, 0, bytes.length);
        sHA1Digest.doFinal(bArr, 0);
        return bArr;
    }

    /* access modifiers changed from: protected */
    public byte[] cryptData(boolean z, AlgorithmIdentifier algorithmIdentifier, char[] cArr, boolean z2, byte[] bArr) {
        ASN1ObjectIdentifier algorithm = algorithmIdentifier.getAlgorithm();
        int i2 = z ? 1 : 2;
        if (algorithm.on(PKCSObjectIdentifiers.pkcs_12PbeIds)) {
            PKCS12PBEParams instance = PKCS12PBEParams.getInstance(algorithmIdentifier.getParameters());
            PBEKeySpec pBEKeySpec = new PBEKeySpec(cArr);
            try {
                SecretKeyFactory instance2 = SecretKeyFactory.getInstance(algorithm.getId(), a);
                PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(instance.getIV(), instance.getIterations().intValue());
                BCPBEKey bCPBEKey = (BCPBEKey) instance2.generateSecret(pBEKeySpec);
                bCPBEKey.setTryWrongPKCS12Zero(z2);
                Cipher instance3 = Cipher.getInstance(algorithm.getId(), a);
                instance3.init(i2, bCPBEKey, pBEParameterSpec);
                return instance3.doFinal(bArr);
            } catch (Exception e2) {
                StringBuilder sb = new StringBuilder();
                sb.append("exception decrypting data - ");
                sb.append(e2.toString());
                throw new IOException(sb.toString());
            }
        } else if (algorithm.equals(PKCSObjectIdentifiers.id_PBES2)) {
            try {
                return a(i2, cArr, algorithmIdentifier).doFinal(bArr);
            } catch (Exception e3) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("exception decrypting data - ");
                sb2.append(e3.toString());
                throw new IOException(sb2.toString());
            }
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("unknown PBE algorithm: ");
            sb3.append(algorithm);
            throw new IOException(sb3.toString());
        }
    }

    public Enumeration engineAliases() {
        Hashtable hashtable = new Hashtable();
        Enumeration a2 = this.e.a();
        while (a2.hasMoreElements()) {
            hashtable.put(a2.nextElement(), "cert");
        }
        Enumeration a3 = this.c.a();
        while (a3.hasMoreElements()) {
            String str = (String) a3.nextElement();
            if (hashtable.get(str) == null) {
                hashtable.put(str, "key");
            }
        }
        return hashtable.keys();
    }

    public boolean engineContainsAlias(String str) {
        return (this.e.b(str) == null && this.c.b(str) == null) ? false : true;
    }

    public void engineDeleteEntry(String str) {
        Key key = (Key) this.c.a(str);
        Certificate certificate = (Certificate) this.e.a(str);
        if (certificate != null) {
            this.f.remove(new CertId(certificate.getPublicKey()));
        }
        if (key != null) {
            String str2 = (String) this.d.remove(str);
            if (str2 != null) {
                certificate = (Certificate) this.g.remove(str2);
            }
            if (certificate != null) {
                this.f.remove(new CertId(certificate.getPublicKey()));
            }
        }
    }

    public Certificate engineGetCertificate(String str) {
        if (str == null) {
            throw new IllegalArgumentException("null alias passed to getCertificate.");
        }
        Certificate certificate = (Certificate) this.e.b(str);
        if (certificate != null) {
            return certificate;
        }
        String str2 = (String) this.d.get(str);
        return (Certificate) (str2 != null ? this.g.get(str2) : this.g.get(str));
    }

    public String engineGetCertificateAlias(Certificate certificate) {
        Enumeration b2 = this.e.b();
        Enumeration a2 = this.e.a();
        while (b2.hasMoreElements()) {
            String str = (String) a2.nextElement();
            if (((Certificate) b2.nextElement()).equals(certificate)) {
                return str;
            }
        }
        Enumeration elements = this.g.elements();
        Enumeration keys = this.g.keys();
        while (elements.hasMoreElements()) {
            String str2 = (String) keys.nextElement();
            if (((Certificate) elements.nextElement()).equals(certificate)) {
                return str2;
            }
        }
        return null;
    }

    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00b0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.security.cert.Certificate[] engineGetCertificateChain(java.lang.String r9) {
        /*
            r8 = this;
            if (r9 != 0) goto L_0x000a
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "null alias passed to getCertificateChain."
            r9.<init>(r0)
            throw r9
        L_0x000a:
            boolean r0 = r8.engineIsKeyEntry(r9)
            r1 = 0
            if (r0 != 0) goto L_0x0012
            return r1
        L_0x0012:
            java.security.cert.Certificate r9 = r8.engineGetCertificate(r9)
            if (r9 == 0) goto L_0x00c9
            java.util.Vector r0 = new java.util.Vector
            r0.<init>()
        L_0x001d:
            if (r9 == 0) goto L_0x00b3
            r2 = r9
            java.security.cert.X509Certificate r2 = (java.security.cert.X509Certificate) r2
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = org.bouncycastle.asn1.x509.Extension.authorityKeyIdentifier
            java.lang.String r3 = r3.getId()
            byte[] r3 = r2.getExtensionValue(r3)
            if (r3 == 0) goto L_0x006d
            org.bouncycastle.asn1.ASN1InputStream r4 = new org.bouncycastle.asn1.ASN1InputStream     // Catch:{ IOException -> 0x0062 }
            r4.<init>(r3)     // Catch:{ IOException -> 0x0062 }
            org.bouncycastle.asn1.ASN1Primitive r3 = r4.readObject()     // Catch:{ IOException -> 0x0062 }
            org.bouncycastle.asn1.ASN1OctetString r3 = (org.bouncycastle.asn1.ASN1OctetString) r3     // Catch:{ IOException -> 0x0062 }
            byte[] r3 = r3.getOctets()     // Catch:{ IOException -> 0x0062 }
            org.bouncycastle.asn1.ASN1InputStream r4 = new org.bouncycastle.asn1.ASN1InputStream     // Catch:{ IOException -> 0x0062 }
            r4.<init>(r3)     // Catch:{ IOException -> 0x0062 }
            org.bouncycastle.asn1.ASN1Primitive r3 = r4.readObject()     // Catch:{ IOException -> 0x0062 }
            org.bouncycastle.asn1.x509.AuthorityKeyIdentifier r3 = org.bouncycastle.asn1.x509.AuthorityKeyIdentifier.getInstance(r3)     // Catch:{ IOException -> 0x0062 }
            byte[] r4 = r3.getKeyIdentifier()     // Catch:{ IOException -> 0x0062 }
            if (r4 == 0) goto L_0x006d
            java.util.Hashtable r4 = r8.f     // Catch:{ IOException -> 0x0062 }
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId r5 = new org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId     // Catch:{ IOException -> 0x0062 }
            byte[] r3 = r3.getKeyIdentifier()     // Catch:{ IOException -> 0x0062 }
            r5.<init>(r3)     // Catch:{ IOException -> 0x0062 }
            java.lang.Object r3 = r4.get(r5)     // Catch:{ IOException -> 0x0062 }
            java.security.cert.Certificate r3 = (java.security.cert.Certificate) r3     // Catch:{ IOException -> 0x0062 }
            goto L_0x006e
        L_0x0062:
            r9 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r9 = r9.toString()
            r0.<init>(r9)
            throw r0
        L_0x006d:
            r3 = r1
        L_0x006e:
            if (r3 != 0) goto L_0x00a8
            java.security.Principal r4 = r2.getIssuerDN()
            java.security.Principal r5 = r2.getSubjectDN()
            boolean r5 = r4.equals(r5)
            if (r5 != 0) goto L_0x00a8
            java.util.Hashtable r5 = r8.f
            java.util.Enumeration r5 = r5.keys()
        L_0x0084:
            boolean r6 = r5.hasMoreElements()
            if (r6 == 0) goto L_0x00a8
            java.util.Hashtable r6 = r8.f
            java.lang.Object r7 = r5.nextElement()
            java.lang.Object r6 = r6.get(r7)
            java.security.cert.X509Certificate r6 = (java.security.cert.X509Certificate) r6
            java.security.Principal r7 = r6.getSubjectDN()
            boolean r7 = r7.equals(r4)
            if (r7 == 0) goto L_0x0084
            java.security.PublicKey r7 = r6.getPublicKey()     // Catch:{ Exception -> 0x0084 }
            r2.verify(r7)     // Catch:{ Exception -> 0x0084 }
            r3 = r6
        L_0x00a8:
            r0.addElement(r9)
            if (r3 == r9) goto L_0x00b0
            r9 = r3
            goto L_0x001d
        L_0x00b0:
            r9 = r1
            goto L_0x001d
        L_0x00b3:
            int r9 = r0.size()
            java.security.cert.Certificate[] r9 = new java.security.cert.Certificate[r9]
            r1 = 0
        L_0x00ba:
            int r2 = r9.length
            if (r1 == r2) goto L_0x00c8
            java.lang.Object r2 = r0.elementAt(r1)
            java.security.cert.Certificate r2 = (java.security.cert.Certificate) r2
            r9[r1] = r2
            int r1 = r1 + 1
            goto L_0x00ba
        L_0x00c8:
            return r9
        L_0x00c9:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.engineGetCertificateChain(java.lang.String):java.security.cert.Certificate[]");
    }

    public Date engineGetCreationDate(String str) {
        if (str == null) {
            throw new NullPointerException("alias == null");
        } else if (this.c.b(str) == null && this.e.b(str) == null) {
            return null;
        } else {
            return new Date();
        }
    }

    public Key engineGetKey(String str, char[] cArr) {
        if (str != null) {
            return (Key) this.c.b(str);
        }
        throw new IllegalArgumentException("null alias passed to getKey.");
    }

    public boolean engineIsCertificateEntry(String str) {
        return this.e.b(str) != null && this.c.b(str) == null;
    }

    public boolean engineIsKeyEntry(String str) {
        return this.c.b(str) != null;
    }

    /* JADX WARNING: type inference failed for: r14v0 */
    /* JADX WARNING: type inference failed for: r14v8 */
    /* JADX WARNING: type inference failed for: r14v9 */
    /* JADX WARNING: type inference failed for: r14v15 */
    /* JADX WARNING: type inference failed for: r14v16 */
    /* JADX WARNING: type inference failed for: r6v26, types: [org.bouncycastle.asn1.ASN1OctetString] */
    /* JADX WARNING: type inference failed for: r3v40, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r3v43 */
    /* JADX WARNING: type inference failed for: r6v29 */
    /* JADX WARNING: type inference failed for: r6v30 */
    /* JADX WARNING: type inference failed for: r17v1 */
    /* JADX WARNING: type inference failed for: r17v2 */
    /* JADX WARNING: type inference failed for: r6v31 */
    /* JADX WARNING: type inference failed for: r3v46 */
    /* JADX WARNING: type inference failed for: r17v3 */
    /* JADX WARNING: type inference failed for: r6v32 */
    /* JADX WARNING: type inference failed for: r6v34, types: [org.bouncycastle.asn1.ASN1OctetString] */
    /* JADX WARNING: type inference failed for: r11v29, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r17v4 */
    /* JADX WARNING: type inference failed for: r14v28 */
    /* JADX WARNING: type inference failed for: r14v29 */
    /* JADX WARNING: type inference failed for: r14v30 */
    /* JADX WARNING: type inference failed for: r14v31 */
    /* JADX WARNING: type inference failed for: r3v50 */
    /* JADX WARNING: type inference failed for: r6v37 */
    /* JADX WARNING: type inference failed for: r6v38 */
    /* JADX WARNING: type inference failed for: r17v6 */
    /* JADX WARNING: type inference failed for: r17v7 */
    /* JADX WARNING: type inference failed for: r6v39 */
    /* JADX WARNING: type inference failed for: r6v40 */
    /* JADX WARNING: type inference failed for: r6v41 */
    /* JADX WARNING: type inference failed for: r17v8 */
    /* JADX WARNING: type inference failed for: r6v42 */
    /* JADX WARNING: type inference failed for: r6v43 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r14v8
      assigns: []
      uses: []
      mth insns count: 517
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 16 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void engineLoad(java.io.InputStream r22, char[] r23) {
        /*
            r21 = this;
            r7 = r21
            r1 = r22
            r8 = r23
            if (r1 != 0) goto L_0x0009
            return
        L_0x0009:
            if (r8 != 0) goto L_0x0013
            java.lang.NullPointerException r1 = new java.lang.NullPointerException
            java.lang.String r2 = "No password supplied for PKCS#12 KeyStore."
            r1.<init>(r2)
            throw r1
        L_0x0013:
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream
            r2.<init>(r1)
            r1 = 10
            r2.mark(r1)
            int r1 = r2.read()
            r3 = 48
            if (r1 == r3) goto L_0x002d
            java.io.IOException r1 = new java.io.IOException
            java.lang.String r2 = "stream does not represent a PKCS12 key store"
            r1.<init>(r2)
            throw r1
        L_0x002d:
            r2.reset()
            org.bouncycastle.asn1.ASN1InputStream r1 = new org.bouncycastle.asn1.ASN1InputStream
            r1.<init>(r2)
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.readObject()
            org.bouncycastle.asn1.ASN1Sequence r1 = (org.bouncycastle.asn1.ASN1Sequence) r1
            org.bouncycastle.asn1.pkcs.Pfx r1 = org.bouncycastle.asn1.pkcs.Pfx.getInstance(r1)
            org.bouncycastle.asn1.pkcs.ContentInfo r9 = r1.getAuthSafe()
            java.util.Vector r10 = new java.util.Vector
            r10.<init>()
            org.bouncycastle.asn1.pkcs.MacData r2 = r1.getMacData()
            r11 = 1
            r12 = 0
            if (r2 == 0) goto L_0x00d8
            org.bouncycastle.asn1.pkcs.MacData r1 = r1.getMacData()
            org.bouncycastle.asn1.x509.DigestInfo r13 = r1.getMac()
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r14 = r13.getAlgorithmId()
            byte[] r15 = r1.getSalt()
            java.math.BigInteger r1 = r1.getIterationCount()
            int r16 = r1.intValue()
            org.bouncycastle.asn1.ASN1Encodable r1 = r9.getContent()
            org.bouncycastle.asn1.ASN1OctetString r1 = (org.bouncycastle.asn1.ASN1OctetString) r1
            byte[] r17 = r1.getOctets()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r14.getAlgorithm()     // Catch:{ IOException -> 0x00d5, Exception -> 0x00b8 }
            r5 = 0
            r2 = r15
            r3 = r16
            r4 = r8
            r6 = r17
            byte[] r1 = a(r1, r2, r3, r4, r5, r6)     // Catch:{ IOException -> 0x00d5, Exception -> 0x00b8 }
            byte[] r13 = r13.getDigest()     // Catch:{ IOException -> 0x00d5, Exception -> 0x00b8 }
            boolean r1 = org.bouncycastle.util.Arrays.constantTimeAreEqual(r1, r13)     // Catch:{ IOException -> 0x00d5, Exception -> 0x00b8 }
            if (r1 != 0) goto L_0x00b5
            int r1 = r8.length     // Catch:{ IOException -> 0x00d5, Exception -> 0x00b8 }
            if (r1 <= 0) goto L_0x0096
            java.io.IOException r1 = new java.io.IOException     // Catch:{ IOException -> 0x00d5, Exception -> 0x00b8 }
            java.lang.String r2 = "PKCS12 key store mac invalid - wrong password or corrupted file."
            r1.<init>(r2)     // Catch:{ IOException -> 0x00d5, Exception -> 0x00b8 }
            throw r1     // Catch:{ IOException -> 0x00d5, Exception -> 0x00b8 }
        L_0x0096:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r14.getAlgorithm()     // Catch:{ IOException -> 0x00d5, Exception -> 0x00b8 }
            r5 = 1
            r2 = r15
            r3 = r16
            r4 = r8
            r6 = r17
            byte[] r1 = a(r1, r2, r3, r4, r5, r6)     // Catch:{ IOException -> 0x00d5, Exception -> 0x00b8 }
            boolean r1 = org.bouncycastle.util.Arrays.constantTimeAreEqual(r1, r13)     // Catch:{ IOException -> 0x00d5, Exception -> 0x00b8 }
            if (r1 != 0) goto L_0x00b3
            java.io.IOException r1 = new java.io.IOException     // Catch:{ IOException -> 0x00d5, Exception -> 0x00b8 }
            java.lang.String r2 = "PKCS12 key store mac invalid - wrong password or corrupted file."
            r1.<init>(r2)     // Catch:{ IOException -> 0x00d5, Exception -> 0x00b8 }
            throw r1     // Catch:{ IOException -> 0x00d5, Exception -> 0x00b8 }
        L_0x00b3:
            r1 = 1
            goto L_0x00b6
        L_0x00b5:
            r1 = 0
        L_0x00b6:
            r13 = r1
            goto L_0x00d9
        L_0x00b8:
            r0 = move-exception
            r1 = r0
            java.io.IOException r2 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "error constructing MAC: "
            r3.append(r4)
            java.lang.String r1 = r1.toString()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            r2.<init>(r1)
            throw r2
        L_0x00d5:
            r0 = move-exception
            r1 = r0
            throw r1
        L_0x00d8:
            r13 = 0
        L_0x00d9:
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r1 = new org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable
            r14 = 0
            r1.<init>()
            r7.c = r1
            java.util.Hashtable r1 = new java.util.Hashtable
            r1.<init>()
            r7.d = r1
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r9.getContentType()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = data
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0474
            org.bouncycastle.asn1.ASN1InputStream r1 = new org.bouncycastle.asn1.ASN1InputStream
            org.bouncycastle.asn1.ASN1Encodable r2 = r9.getContent()
            org.bouncycastle.asn1.ASN1OctetString r2 = (org.bouncycastle.asn1.ASN1OctetString) r2
            byte[] r2 = r2.getOctets()
            r1.<init>(r2)
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.readObject()
            org.bouncycastle.asn1.pkcs.AuthenticatedSafe r1 = org.bouncycastle.asn1.pkcs.AuthenticatedSafe.getInstance(r1)
            org.bouncycastle.asn1.pkcs.ContentInfo[] r9 = r1.getContentInfo()
            r15 = 0
            r16 = 0
        L_0x0112:
            int r1 = r9.length
            if (r15 == r1) goto L_0x0476
            r1 = r9[r15]
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r1.getContentType()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = data
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x024e
            org.bouncycastle.asn1.ASN1InputStream r1 = new org.bouncycastle.asn1.ASN1InputStream
            r2 = r9[r15]
            org.bouncycastle.asn1.ASN1Encodable r2 = r2.getContent()
            org.bouncycastle.asn1.ASN1OctetString r2 = (org.bouncycastle.asn1.ASN1OctetString) r2
            byte[] r2 = r2.getOctets()
            r1.<init>(r2)
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.readObject()
            org.bouncycastle.asn1.ASN1Sequence r1 = (org.bouncycastle.asn1.ASN1Sequence) r1
            r2 = 0
        L_0x013b:
            int r3 = r1.size()
            if (r2 == r3) goto L_0x046d
            org.bouncycastle.asn1.ASN1Encodable r3 = r1.getObjectAt(r2)
            org.bouncycastle.asn1.pkcs.SafeBag r3 = org.bouncycastle.asn1.pkcs.SafeBag.getInstance(r3)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = r3.getBagId()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = pkcs8ShroudedKeyBag
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0214
            org.bouncycastle.asn1.ASN1Encodable r4 = r3.getBagValue()
            org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo r4 = org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo.getInstance(r4)
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r5 = r4.getEncryptionAlgorithm()
            byte[] r4 = r4.getEncryptedData()
            java.security.PrivateKey r4 = r7.unwrapKey(r5, r4, r8, r13)
            r5 = r4
            org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier r5 = (org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) r5
            org.bouncycastle.asn1.ASN1Set r6 = r3.getBagAttributes()
            if (r6 == 0) goto L_0x01eb
            org.bouncycastle.asn1.ASN1Set r3 = r3.getBagAttributes()
            java.util.Enumeration r3 = r3.getObjects()
            r6 = r14
            r17 = r6
        L_0x017d:
            boolean r18 = r3.hasMoreElements()
            if (r18 == 0) goto L_0x01e8
            java.lang.Object r18 = r3.nextElement()
            r14 = r18
            org.bouncycastle.asn1.ASN1Sequence r14 = (org.bouncycastle.asn1.ASN1Sequence) r14
            org.bouncycastle.asn1.ASN1Encodable r18 = r14.getObjectAt(r12)
            r12 = r18
            org.bouncycastle.asn1.ASN1ObjectIdentifier r12 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r12
            org.bouncycastle.asn1.ASN1Encodable r14 = r14.getObjectAt(r11)
            org.bouncycastle.asn1.ASN1Set r14 = (org.bouncycastle.asn1.ASN1Set) r14
            int r18 = r14.size()
            if (r18 <= 0) goto L_0x01c2
            r11 = 0
            org.bouncycastle.asn1.ASN1Encodable r14 = r14.getObjectAt(r11)
            org.bouncycastle.asn1.ASN1Primitive r14 = (org.bouncycastle.asn1.ASN1Primitive) r14
            org.bouncycastle.asn1.ASN1Encodable r11 = r5.getBagAttribute(r12)
            if (r11 == 0) goto L_0x01be
            org.bouncycastle.asn1.ASN1Primitive r11 = r11.toASN1Primitive()
            boolean r11 = r11.equals(r14)
            if (r11 != 0) goto L_0x01c3
            java.io.IOException r1 = new java.io.IOException
            java.lang.String r2 = "attempt to add existing attribute with different value"
            r1.<init>(r2)
            throw r1
        L_0x01be:
            r5.setBagAttribute(r12, r14)
            goto L_0x01c3
        L_0x01c2:
            r14 = 0
        L_0x01c3:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r11 = pkcs_9_at_friendlyName
            boolean r11 = r12.equals(r11)
            if (r11 == 0) goto L_0x01d9
            org.bouncycastle.asn1.DERBMPString r14 = (org.bouncycastle.asn1.DERBMPString) r14
            java.lang.String r11 = r14.getString()
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r12 = r7.c
            r12.a(r11, r4)
            r17 = r11
            goto L_0x01e4
        L_0x01d9:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r11 = pkcs_9_at_localKeyId
            boolean r11 = r12.equals(r11)
            if (r11 == 0) goto L_0x01e4
            r6 = r14
            org.bouncycastle.asn1.ASN1OctetString r6 = (org.bouncycastle.asn1.ASN1OctetString) r6
        L_0x01e4:
            r11 = 1
            r12 = 0
            r14 = 0
            goto L_0x017d
        L_0x01e8:
            r3 = r17
            goto L_0x01ed
        L_0x01eb:
            r3 = 0
            r6 = 0
        L_0x01ed:
            if (r6 == 0) goto L_0x020a
            java.lang.String r5 = new java.lang.String
            byte[] r6 = r6.getOctets()
            byte[] r6 = org.bouncycastle.util.encoders.Hex.encode(r6)
            r5.<init>(r6)
            if (r3 != 0) goto L_0x0204
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r3 = r7.c
            r3.a(r5, r4)
            goto L_0x0247
        L_0x0204:
            java.util.Hashtable r4 = r7.d
            r4.put(r3, r5)
            goto L_0x0247
        L_0x020a:
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r3 = r7.c
            java.lang.String r5 = "unmarked"
            r3.a(r5, r4)
            r16 = 1
            goto L_0x0247
        L_0x0214:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = r3.getBagId()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = certBag
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0224
            r10.addElement(r3)
            goto L_0x0247
        L_0x0224:
            java.io.PrintStream r4 = java.lang.System.out
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "extra in data "
            r5.append(r6)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r6 = r3.getBagId()
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.println(r5)
            java.io.PrintStream r4 = java.lang.System.out
            java.lang.String r3 = org.bouncycastle.asn1.util.ASN1Dump.dumpAsString(r3)
            r4.println(r3)
        L_0x0247:
            int r2 = r2 + 1
            r11 = 1
            r12 = 0
            r14 = 0
            goto L_0x013b
        L_0x024e:
            r1 = r9[r15]
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r1.getContentType()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = encryptedData
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x042d
            r1 = r9[r15]
            org.bouncycastle.asn1.ASN1Encodable r1 = r1.getContent()
            org.bouncycastle.asn1.pkcs.EncryptedData r1 = org.bouncycastle.asn1.pkcs.EncryptedData.getInstance(r1)
            r2 = 0
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r3 = r1.getEncryptionAlgorithm()
            org.bouncycastle.asn1.ASN1OctetString r1 = r1.getContent()
            byte[] r6 = r1.getOctets()
            r1 = r7
            r4 = r8
            r5 = r13
            byte[] r1 = r1.cryptData(r2, r3, r4, r5, r6)
            org.bouncycastle.asn1.ASN1Primitive r1 = org.bouncycastle.asn1.ASN1Primitive.fromByteArray(r1)
            org.bouncycastle.asn1.ASN1Sequence r1 = (org.bouncycastle.asn1.ASN1Sequence) r1
            r2 = 0
        L_0x0281:
            int r3 = r1.size()
            if (r2 == r3) goto L_0x046d
            org.bouncycastle.asn1.ASN1Encodable r3 = r1.getObjectAt(r2)
            org.bouncycastle.asn1.pkcs.SafeBag r3 = org.bouncycastle.asn1.pkcs.SafeBag.getInstance(r3)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = r3.getBagId()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = certBag
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x02a2
            r10.addElement(r3)
            r19 = r1
            goto L_0x0427
        L_0x02a2:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = r3.getBagId()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = pkcs8ShroudedKeyBag
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x035a
            org.bouncycastle.asn1.ASN1Encodable r4 = r3.getBagValue()
            org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo r4 = org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo.getInstance(r4)
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r5 = r4.getEncryptionAlgorithm()
            byte[] r4 = r4.getEncryptedData()
            java.security.PrivateKey r4 = r7.unwrapKey(r5, r4, r8, r13)
            r5 = r4
            org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier r5 = (org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) r5
            org.bouncycastle.asn1.ASN1Set r3 = r3.getBagAttributes()
            java.util.Enumeration r3 = r3.getObjects()
            r6 = 0
            r11 = 0
        L_0x02cf:
            boolean r12 = r3.hasMoreElements()
            if (r12 == 0) goto L_0x033b
            java.lang.Object r12 = r3.nextElement()
            org.bouncycastle.asn1.ASN1Sequence r12 = (org.bouncycastle.asn1.ASN1Sequence) r12
            r14 = 0
            org.bouncycastle.asn1.ASN1Encodable r17 = r12.getObjectAt(r14)
            r14 = r17
            org.bouncycastle.asn1.ASN1ObjectIdentifier r14 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r14
            r19 = r1
            r1 = 1
            org.bouncycastle.asn1.ASN1Encodable r12 = r12.getObjectAt(r1)
            org.bouncycastle.asn1.ASN1Set r12 = (org.bouncycastle.asn1.ASN1Set) r12
            int r1 = r12.size()
            if (r1 <= 0) goto L_0x0317
            r1 = 0
            org.bouncycastle.asn1.ASN1Encodable r12 = r12.getObjectAt(r1)
            r1 = r12
            org.bouncycastle.asn1.ASN1Primitive r1 = (org.bouncycastle.asn1.ASN1Primitive) r1
            org.bouncycastle.asn1.ASN1Encodable r12 = r5.getBagAttribute(r14)
            if (r12 == 0) goto L_0x0313
            org.bouncycastle.asn1.ASN1Primitive r12 = r12.toASN1Primitive()
            boolean r12 = r12.equals(r1)
            if (r12 != 0) goto L_0x0318
            java.io.IOException r1 = new java.io.IOException
            java.lang.String r2 = "attempt to add existing attribute with different value"
            r1.<init>(r2)
            throw r1
        L_0x0313:
            r5.setBagAttribute(r14, r1)
            goto L_0x0318
        L_0x0317:
            r1 = 0
        L_0x0318:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r12 = pkcs_9_at_friendlyName
            boolean r12 = r14.equals(r12)
            if (r12 == 0) goto L_0x032d
            org.bouncycastle.asn1.DERBMPString r1 = (org.bouncycastle.asn1.DERBMPString) r1
            java.lang.String r1 = r1.getString()
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r11 = r7.c
            r11.a(r1, r4)
            r11 = r1
            goto L_0x0338
        L_0x032d:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r12 = pkcs_9_at_localKeyId
            boolean r12 = r14.equals(r12)
            if (r12 == 0) goto L_0x0338
            org.bouncycastle.asn1.ASN1OctetString r1 = (org.bouncycastle.asn1.ASN1OctetString) r1
            r6 = r1
        L_0x0338:
            r1 = r19
            goto L_0x02cf
        L_0x033b:
            r19 = r1
            java.lang.String r1 = new java.lang.String
            byte[] r3 = r6.getOctets()
            byte[] r3 = org.bouncycastle.util.encoders.Hex.encode(r3)
            r1.<init>(r3)
            if (r11 != 0) goto L_0x0353
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r3 = r7.c
            r3.a(r1, r4)
            goto L_0x0427
        L_0x0353:
            java.util.Hashtable r3 = r7.d
            r3.put(r11, r1)
            goto L_0x0427
        L_0x035a:
            r19 = r1
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r3.getBagId()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = keyBag
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0404
            org.bouncycastle.asn1.ASN1Encodable r1 = r3.getBagValue()
            org.bouncycastle.asn1.pkcs.PrivateKeyInfo r1 = org.bouncycastle.asn1.pkcs.PrivateKeyInfo.getInstance(r1)
            java.security.PrivateKey r1 = org.bouncycastle.jce.provider.BouncyCastleProvider.getPrivateKey(r1)
            r4 = r1
            org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier r4 = (org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) r4
            org.bouncycastle.asn1.ASN1Set r3 = r3.getBagAttributes()
            java.util.Enumeration r3 = r3.getObjects()
            r5 = 0
            r6 = 0
        L_0x0381:
            boolean r11 = r3.hasMoreElements()
            if (r11 == 0) goto L_0x03e9
            java.lang.Object r11 = r3.nextElement()
            org.bouncycastle.asn1.ASN1Sequence r11 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r11)
            r12 = 0
            org.bouncycastle.asn1.ASN1Encodable r14 = r11.getObjectAt(r12)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r14 = org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(r14)
            r12 = 1
            org.bouncycastle.asn1.ASN1Encodable r11 = r11.getObjectAt(r12)
            org.bouncycastle.asn1.ASN1Set r11 = org.bouncycastle.asn1.ASN1Set.getInstance(r11)
            int r12 = r11.size()
            if (r12 <= 0) goto L_0x0381
            r12 = 0
            org.bouncycastle.asn1.ASN1Encodable r11 = r11.getObjectAt(r12)
            org.bouncycastle.asn1.ASN1Primitive r11 = (org.bouncycastle.asn1.ASN1Primitive) r11
            org.bouncycastle.asn1.ASN1Encodable r12 = r4.getBagAttribute(r14)
            if (r12 == 0) goto L_0x03c6
            org.bouncycastle.asn1.ASN1Primitive r12 = r12.toASN1Primitive()
            boolean r12 = r12.equals(r11)
            if (r12 != 0) goto L_0x03c9
            java.io.IOException r1 = new java.io.IOException
            java.lang.String r2 = "attempt to add existing attribute with different value"
            r1.<init>(r2)
            throw r1
        L_0x03c6:
            r4.setBagAttribute(r14, r11)
        L_0x03c9:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r12 = pkcs_9_at_friendlyName
            boolean r12 = r14.equals(r12)
            if (r12 == 0) goto L_0x03dd
            org.bouncycastle.asn1.DERBMPString r11 = (org.bouncycastle.asn1.DERBMPString) r11
            java.lang.String r6 = r11.getString()
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r11 = r7.c
            r11.a(r6, r1)
            goto L_0x0381
        L_0x03dd:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r12 = pkcs_9_at_localKeyId
            boolean r12 = r14.equals(r12)
            if (r12 == 0) goto L_0x0381
            org.bouncycastle.asn1.ASN1OctetString r11 = (org.bouncycastle.asn1.ASN1OctetString) r11
            r5 = r11
            goto L_0x0381
        L_0x03e9:
            java.lang.String r3 = new java.lang.String
            byte[] r4 = r5.getOctets()
            byte[] r4 = org.bouncycastle.util.encoders.Hex.encode(r4)
            r3.<init>(r4)
            if (r6 != 0) goto L_0x03fe
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r4 = r7.c
            r4.a(r3, r1)
            goto L_0x0427
        L_0x03fe:
            java.util.Hashtable r1 = r7.d
            r1.put(r6, r3)
            goto L_0x0427
        L_0x0404:
            java.io.PrintStream r1 = java.lang.System.out
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "extra in encryptedData "
            r4.append(r5)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = r3.getBagId()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r1.println(r4)
            java.io.PrintStream r1 = java.lang.System.out
            java.lang.String r3 = org.bouncycastle.asn1.util.ASN1Dump.dumpAsString(r3)
            r1.println(r3)
        L_0x0427:
            int r2 = r2 + 1
            r1 = r19
            goto L_0x0281
        L_0x042d:
            java.io.PrintStream r1 = java.lang.System.out
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "extra "
            r2.append(r3)
            r3 = r9[r15]
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = r3.getContentType()
            java.lang.String r3 = r3.getId()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.println(r2)
            java.io.PrintStream r1 = java.lang.System.out
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "extra "
            r2.append(r3)
            r3 = r9[r15]
            org.bouncycastle.asn1.ASN1Encodable r3 = r3.getContent()
            java.lang.String r3 = org.bouncycastle.asn1.util.ASN1Dump.dumpAsString(r3)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.println(r2)
        L_0x046d:
            int r15 = r15 + 1
            r11 = 1
            r12 = 0
            r14 = 0
            goto L_0x0112
        L_0x0474:
            r16 = 0
        L_0x0476:
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r1 = new org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable
            r2 = 0
            r1.<init>()
            r7.e = r1
            java.util.Hashtable r1 = new java.util.Hashtable
            r1.<init>()
            r7.f = r1
            java.util.Hashtable r1 = new java.util.Hashtable
            r1.<init>()
            r7.g = r1
            r1 = 0
        L_0x048d:
            int r3 = r10.size()
            if (r1 == r3) goto L_0x05c8
            java.lang.Object r3 = r10.elementAt(r1)
            org.bouncycastle.asn1.pkcs.SafeBag r3 = (org.bouncycastle.asn1.pkcs.SafeBag) r3
            org.bouncycastle.asn1.ASN1Encodable r4 = r3.getBagValue()
            org.bouncycastle.asn1.pkcs.CertBag r4 = org.bouncycastle.asn1.pkcs.CertBag.getInstance(r4)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = r4.getCertId()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r6 = x509Certificate
            boolean r5 = r5.equals(r6)
            if (r5 != 0) goto L_0x04c8
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Unsupported certificate type: "
            r2.append(r3)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = r4.getCertId()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x04c8:
            java.io.ByteArrayInputStream r5 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x05bc }
            org.bouncycastle.asn1.ASN1Encodable r4 = r4.getCertValue()     // Catch:{ Exception -> 0x05bc }
            org.bouncycastle.asn1.ASN1OctetString r4 = (org.bouncycastle.asn1.ASN1OctetString) r4     // Catch:{ Exception -> 0x05bc }
            byte[] r4 = r4.getOctets()     // Catch:{ Exception -> 0x05bc }
            r5.<init>(r4)     // Catch:{ Exception -> 0x05bc }
            java.security.cert.CertificateFactory r4 = r7.h     // Catch:{ Exception -> 0x05bc }
            java.security.cert.Certificate r4 = r4.generateCertificate(r5)     // Catch:{ Exception -> 0x05bc }
            org.bouncycastle.asn1.ASN1Set r5 = r3.getBagAttributes()
            if (r5 == 0) goto L_0x0559
            org.bouncycastle.asn1.ASN1Set r3 = r3.getBagAttributes()
            java.util.Enumeration r3 = r3.getObjects()
            r5 = r2
            r14 = r5
        L_0x04ed:
            boolean r6 = r3.hasMoreElements()
            if (r6 == 0) goto L_0x0556
            java.lang.Object r6 = r3.nextElement()
            org.bouncycastle.asn1.ASN1Sequence r6 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r6)
            r8 = 0
            org.bouncycastle.asn1.ASN1Encodable r9 = r6.getObjectAt(r8)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r9 = org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(r9)
            r11 = 1
            org.bouncycastle.asn1.ASN1Encodable r6 = r6.getObjectAt(r11)
            org.bouncycastle.asn1.ASN1Set r6 = org.bouncycastle.asn1.ASN1Set.getInstance(r6)
            int r12 = r6.size()
            if (r12 <= 0) goto L_0x04ed
            org.bouncycastle.asn1.ASN1Encodable r6 = r6.getObjectAt(r8)
            org.bouncycastle.asn1.ASN1Primitive r6 = (org.bouncycastle.asn1.ASN1Primitive) r6
            boolean r12 = r4 instanceof org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
            if (r12 == 0) goto L_0x053b
            r12 = r4
            org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier r12 = (org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier) r12
            org.bouncycastle.asn1.ASN1Encodable r13 = r12.getBagAttribute(r9)
            if (r13 == 0) goto L_0x0538
            org.bouncycastle.asn1.ASN1Primitive r12 = r13.toASN1Primitive()
            boolean r12 = r12.equals(r6)
            if (r12 != 0) goto L_0x053b
            java.io.IOException r1 = new java.io.IOException
            java.lang.String r2 = "attempt to add existing attribute with different value"
            r1.<init>(r2)
            throw r1
        L_0x0538:
            r12.setBagAttribute(r9, r6)
        L_0x053b:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r12 = pkcs_9_at_friendlyName
            boolean r12 = r9.equals(r12)
            if (r12 == 0) goto L_0x054a
            org.bouncycastle.asn1.DERBMPString r6 = (org.bouncycastle.asn1.DERBMPString) r6
            java.lang.String r5 = r6.getString()
            goto L_0x04ed
        L_0x054a:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r12 = pkcs_9_at_localKeyId
            boolean r9 = r9.equals(r12)
            if (r9 == 0) goto L_0x04ed
            r14 = r6
            org.bouncycastle.asn1.ASN1OctetString r14 = (org.bouncycastle.asn1.ASN1OctetString) r14
            goto L_0x04ed
        L_0x0556:
            r8 = 0
            r11 = 1
            goto L_0x055d
        L_0x0559:
            r8 = 0
            r11 = 1
            r5 = r2
            r14 = r5
        L_0x055d:
            java.util.Hashtable r3 = r7.f
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId r6 = new org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId
            java.security.PublicKey r9 = r4.getPublicKey()
            r6.<init>(r9)
            r3.put(r6, r4)
            if (r16 == 0) goto L_0x059d
            java.util.Hashtable r3 = r7.g
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L_0x05b8
            java.lang.String r3 = new java.lang.String
            java.security.PublicKey r5 = r4.getPublicKey()
            org.bouncycastle.asn1.x509.SubjectKeyIdentifier r5 = r7.a(r5)
            byte[] r5 = r5.getKeyIdentifier()
            byte[] r5 = org.bouncycastle.util.encoders.Hex.encode(r5)
            r3.<init>(r5)
            java.util.Hashtable r5 = r7.g
            r5.put(r3, r4)
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r4 = r7.c
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r5 = r7.c
            java.lang.String r6 = "unmarked"
            java.lang.Object r5 = r5.a(r6)
            r4.a(r3, r5)
            goto L_0x05b8
        L_0x059d:
            if (r14 == 0) goto L_0x05b1
            java.lang.String r3 = new java.lang.String
            byte[] r6 = r14.getOctets()
            byte[] r6 = org.bouncycastle.util.encoders.Hex.encode(r6)
            r3.<init>(r6)
            java.util.Hashtable r6 = r7.g
            r6.put(r3, r4)
        L_0x05b1:
            if (r5 == 0) goto L_0x05b8
            org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r3 = r7.e
            r3.a(r5, r4)
        L_0x05b8:
            int r1 = r1 + 1
            goto L_0x048d
        L_0x05bc:
            r0 = move-exception
            r1 = r0
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.String r1 = r1.toString()
            r2.<init>(r1)
            throw r2
        L_0x05c8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.engineLoad(java.io.InputStream, char[]):void");
    }

    public void engineSetCertificateEntry(String str, Certificate certificate) {
        if (this.c.b(str) != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("There is a key entry with the name ");
            sb.append(str);
            sb.append(".");
            throw new KeyStoreException(sb.toString());
        }
        this.e.a(str, certificate);
        this.f.put(new CertId(certificate.getPublicKey()), certificate);
    }

    public void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) {
        boolean z = key instanceof PrivateKey;
        if (!z) {
            throw new KeyStoreException("PKCS12 does not support non-PrivateKeys");
        } else if (!z || certificateArr != null) {
            if (this.c.b(str) != null) {
                engineDeleteEntry(str);
            }
            this.c.a(str, key);
            if (certificateArr != null) {
                this.e.a(str, certificateArr[0]);
                for (int i2 = 0; i2 != certificateArr.length; i2++) {
                    this.f.put(new CertId(certificateArr[i2].getPublicKey()), certificateArr[i2]);
                }
            }
        } else {
            throw new KeyStoreException("no certificate chain for private key");
        }
    }

    public void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) {
        throw new RuntimeException("operation not supported");
    }

    public int engineSize() {
        Hashtable hashtable = new Hashtable();
        Enumeration a2 = this.e.a();
        while (a2.hasMoreElements()) {
            hashtable.put(a2.nextElement(), "cert");
        }
        Enumeration a3 = this.c.a();
        while (a3.hasMoreElements()) {
            String str = (String) a3.nextElement();
            if (hashtable.get(str) == null) {
                hashtable.put(str, "key");
            }
        }
        return hashtable.size();
    }

    public void engineStore(OutputStream outputStream, char[] cArr) {
        a(outputStream, cArr, false);
    }

    public void engineStore(LoadStoreParameter loadStoreParameter) {
        PKCS12StoreParameter pKCS12StoreParameter;
        char[] cArr;
        if (loadStoreParameter == null) {
            throw new IllegalArgumentException("'param' arg cannot be null");
        }
        boolean z = loadStoreParameter instanceof PKCS12StoreParameter;
        if (z || (loadStoreParameter instanceof JDKPKCS12StoreParameter)) {
            if (z) {
                pKCS12StoreParameter = (PKCS12StoreParameter) loadStoreParameter;
            } else {
                JDKPKCS12StoreParameter jDKPKCS12StoreParameter = (JDKPKCS12StoreParameter) loadStoreParameter;
                pKCS12StoreParameter = new PKCS12StoreParameter(jDKPKCS12StoreParameter.getOutputStream(), loadStoreParameter.getProtectionParameter(), jDKPKCS12StoreParameter.isUseDEREncoding());
            }
            ProtectionParameter protectionParameter = loadStoreParameter.getProtectionParameter();
            if (protectionParameter == null) {
                cArr = null;
            } else if (protectionParameter instanceof PasswordProtection) {
                cArr = ((PasswordProtection) protectionParameter).getPassword();
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("No support for protection parameter of type ");
                sb.append(protectionParameter.getClass().getName());
                throw new IllegalArgumentException(sb.toString());
            }
            a(pKCS12StoreParameter.getOutputStream(), cArr, pKCS12StoreParameter.isForDEREncoding());
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("No support for 'param' of type ");
        sb2.append(loadStoreParameter.getClass().getName());
        throw new IllegalArgumentException(sb2.toString());
    }

    public void setRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
    }

    /* access modifiers changed from: protected */
    public PrivateKey unwrapKey(AlgorithmIdentifier algorithmIdentifier, byte[] bArr, char[] cArr, boolean z) {
        ASN1ObjectIdentifier algorithm = algorithmIdentifier.getAlgorithm();
        try {
            if (algorithm.on(PKCSObjectIdentifiers.pkcs_12PbeIds)) {
                PKCS12PBEParams instance = PKCS12PBEParams.getInstance(algorithmIdentifier.getParameters());
                PBEKeySpec pBEKeySpec = new PBEKeySpec(cArr);
                SecretKeyFactory instance2 = SecretKeyFactory.getInstance(algorithm.getId(), a);
                PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(instance.getIV(), instance.getIterations().intValue());
                SecretKey generateSecret = instance2.generateSecret(pBEKeySpec);
                ((BCPBEKey) generateSecret).setTryWrongPKCS12Zero(z);
                Cipher instance3 = Cipher.getInstance(algorithm.getId(), a);
                instance3.init(4, generateSecret, pBEParameterSpec);
                return (PrivateKey) instance3.unwrap(bArr, "", 2);
            } else if (algorithm.equals(PKCSObjectIdentifiers.id_PBES2)) {
                return (PrivateKey) a(4, cArr, algorithmIdentifier).unwrap(bArr, "", 2);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("exception unwrapping private key - cannot recognise: ");
                sb.append(algorithm);
                throw new IOException(sb.toString());
            }
        } catch (Exception e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("exception unwrapping private key - ");
            sb2.append(e2.toString());
            throw new IOException(sb2.toString());
        }
    }

    /* access modifiers changed from: protected */
    public byte[] wrapKey(String str, Key key, PKCS12PBEParams pKCS12PBEParams, char[] cArr) {
        PBEKeySpec pBEKeySpec = new PBEKeySpec(cArr);
        try {
            SecretKeyFactory instance = SecretKeyFactory.getInstance(str, a);
            PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(pKCS12PBEParams.getIV(), pKCS12PBEParams.getIterations().intValue());
            Cipher instance2 = Cipher.getInstance(str, a);
            instance2.init(3, instance.generateSecret(pBEKeySpec), pBEParameterSpec);
            return instance2.wrap(key);
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("exception encrypting data - ");
            sb.append(e2.toString());
            throw new IOException(sb.toString());
        }
    }
}
