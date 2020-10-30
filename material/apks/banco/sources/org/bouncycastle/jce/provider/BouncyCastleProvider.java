package org.bouncycastle.jce.provider;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.security.AccessController;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2ParameterSpec;

public final class BouncyCastleProvider extends Provider implements ConfigurableProvider {
    public static final ProviderConfiguration CONFIGURATION = new BouncyCastleProviderConfiguration();
    public static final String PROVIDER_NAME = "BC";
    private static String a = "BouncyCastle Security Provider v1.51";
    private static final Map b = new HashMap();
    private static final String[] c = {"PBEPBKDF2", "PBEPKCS12"};
    private static final String[] d = {"SipHash"};
    private static final String[] e = {"AES", "ARC4", "Blowfish", "Camellia", "CAST5", "CAST6", "ChaCha", "DES", "DESede", "GOST28147", "Grainv1", "Grain128", "HC128", "HC256", "IDEA", "Noekeon", "RC2", "RC5", "RC6", "Rijndael", "Salsa20", "SEED", "Serpent", "Shacal2", "Skipjack", "TEA", "Twofish", "Threefish", "VMPC", "VMPCKSA3", "XTEA", "XSalsa20"};
    private static final String[] f = {"X509", "IES"};
    private static final String[] g = {"DSA", "DH", "EC", "RSA", "GOST", "ECGOST", "ElGamal", "DSTU4145"};
    private static final String[] h = {"GOST3411", "MD2", "MD4", CommonUtils.MD5_INSTANCE, "SHA1", "RIPEMD128", "RIPEMD160", "RIPEMD256", "RIPEMD320", "SHA224", McElieceCCA2ParameterSpec.DEFAULT_MD, "SHA384", "SHA512", "SHA3", "Skein", "SM3", "Tiger", "Whirlpool"};
    private static final String[] i = {PROVIDER_NAME, "PKCS12"};

    public BouncyCastleProvider() {
        super(PROVIDER_NAME, 1.51d, a);
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                BouncyCastleProvider.this.a();
                return null;
            }
        });
    }

    /* access modifiers changed from: private */
    public void a() {
        a("org.bouncycastle.jcajce.provider.digest.", h);
        a("org.bouncycastle.jcajce.provider.symmetric.", c);
        a("org.bouncycastle.jcajce.provider.symmetric.", d);
        a("org.bouncycastle.jcajce.provider.symmetric.", e);
        a("org.bouncycastle.jcajce.provider.asymmetric.", f);
        a("org.bouncycastle.jcajce.provider.asymmetric.", g);
        a("org.bouncycastle.jcajce.provider.keystore.", i);
        put("X509Store.CERTIFICATE/COLLECTION", "org.bouncycastle.jce.provider.X509StoreCertCollection");
        put("X509Store.ATTRIBUTECERTIFICATE/COLLECTION", "org.bouncycastle.jce.provider.X509StoreAttrCertCollection");
        put("X509Store.CRL/COLLECTION", "org.bouncycastle.jce.provider.X509StoreCRLCollection");
        put("X509Store.CERTIFICATEPAIR/COLLECTION", "org.bouncycastle.jce.provider.X509StoreCertPairCollection");
        put("X509Store.CERTIFICATE/LDAP", "org.bouncycastle.jce.provider.X509StoreLDAPCerts");
        put("X509Store.CRL/LDAP", "org.bouncycastle.jce.provider.X509StoreLDAPCRLs");
        put("X509Store.ATTRIBUTECERTIFICATE/LDAP", "org.bouncycastle.jce.provider.X509StoreLDAPAttrCerts");
        put("X509Store.CERTIFICATEPAIR/LDAP", "org.bouncycastle.jce.provider.X509StoreLDAPCertPairs");
        put("X509StreamParser.CERTIFICATE", "org.bouncycastle.jce.provider.X509CertParser");
        put("X509StreamParser.ATTRIBUTECERTIFICATE", "org.bouncycastle.jce.provider.X509AttrCertParser");
        put("X509StreamParser.CRL", "org.bouncycastle.jce.provider.X509CRLParser");
        put("X509StreamParser.CERTIFICATEPAIR", "org.bouncycastle.jce.provider.X509CertPairParser");
        put("Cipher.BROKENPBEWITHMD5ANDDES", "org.bouncycastle.jce.provider.BrokenJCEBlockCipher$BrokePBEWithMD5AndDES");
        put("Cipher.BROKENPBEWITHSHA1ANDDES", "org.bouncycastle.jce.provider.BrokenJCEBlockCipher$BrokePBEWithSHA1AndDES");
        put("Cipher.OLDPBEWITHSHAANDTWOFISH-CBC", "org.bouncycastle.jce.provider.BrokenJCEBlockCipher$OldPBEWithSHAAndTwofish");
        put("CertPathValidator.RFC3281", "org.bouncycastle.jce.provider.PKIXAttrCertPathValidatorSpi");
        put("CertPathBuilder.RFC3281", "org.bouncycastle.jce.provider.PKIXAttrCertPathBuilderSpi");
        put("CertPathValidator.RFC3280", "org.bouncycastle.jce.provider.PKIXCertPathValidatorSpi");
        put("CertPathBuilder.RFC3280", "org.bouncycastle.jce.provider.PKIXCertPathBuilderSpi");
        put("CertPathValidator.PKIX", "org.bouncycastle.jce.provider.PKIXCertPathValidatorSpi");
        put("CertPathBuilder.PKIX", "org.bouncycastle.jce.provider.PKIXCertPathBuilderSpi");
        put("CertStore.Collection", "org.bouncycastle.jce.provider.CertStoreCollectionSpi");
        put("CertStore.LDAP", "org.bouncycastle.jce.provider.X509LDAPCertStoreSpi");
        put("CertStore.Multi", "org.bouncycastle.jce.provider.MultiCertStoreSpi");
        put("Alg.Alias.CertStore.X509LDAP", "LDAP");
    }

    private void a(String str, String[] strArr) {
        Class cls;
        for (int i2 = 0; i2 != strArr.length; i2++) {
            Class cls2 = null;
            try {
                ClassLoader classLoader = getClass().getClassLoader();
                if (classLoader != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(strArr[i2]);
                    sb.append("$Mappings");
                    cls = classLoader.loadClass(sb.toString());
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(strArr[i2]);
                    sb2.append("$Mappings");
                    cls = Class.forName(sb2.toString());
                }
                cls2 = cls;
            } catch (ClassNotFoundException unused) {
            }
            if (cls2 != null) {
                try {
                    ((AlgorithmProvider) cls2.newInstance()).configure(this);
                } catch (Exception e2) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("cannot create instance of ");
                    sb3.append(str);
                    sb3.append(strArr[i2]);
                    sb3.append("$Mappings : ");
                    sb3.append(e2);
                    throw new InternalError(sb3.toString());
                }
            }
        }
    }

    public static PrivateKey getPrivateKey(PrivateKeyInfo privateKeyInfo) {
        AsymmetricKeyInfoConverter asymmetricKeyInfoConverter = (AsymmetricKeyInfoConverter) b.get(privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm());
        if (asymmetricKeyInfoConverter == null) {
            return null;
        }
        return asymmetricKeyInfoConverter.generatePrivate(privateKeyInfo);
    }

    public static PublicKey getPublicKey(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        AsymmetricKeyInfoConverter asymmetricKeyInfoConverter = (AsymmetricKeyInfoConverter) b.get(subjectPublicKeyInfo.getAlgorithm().getAlgorithm());
        if (asymmetricKeyInfoConverter == null) {
            return null;
        }
        return asymmetricKeyInfoConverter.generatePublic(subjectPublicKeyInfo);
    }

    public void addAlgorithm(String str, String str2) {
        if (containsKey(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append("duplicate provider key (");
            sb.append(str);
            sb.append(") found");
            throw new IllegalStateException(sb.toString());
        }
        put(str, str2);
    }

    public void addKeyInfoConverter(ASN1ObjectIdentifier aSN1ObjectIdentifier, AsymmetricKeyInfoConverter asymmetricKeyInfoConverter) {
        b.put(aSN1ObjectIdentifier, asymmetricKeyInfoConverter);
    }

    public boolean hasAlgorithm(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(".");
        sb.append(str2);
        if (!containsKey(sb.toString())) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.");
            sb2.append(str);
            sb2.append(".");
            sb2.append(str2);
            if (!containsKey(sb2.toString())) {
                return false;
            }
        }
        return true;
    }

    public void setParameter(String str, Object obj) {
        synchronized (CONFIGURATION) {
            ((BouncyCastleProviderConfiguration) CONFIGURATION).a(str, obj);
        }
    }
}
