package org.bouncycastle.pqc.jcajce.provider;

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

public class BouncyCastlePQCProvider extends Provider implements ConfigurableProvider {
    public static final ProviderConfiguration CONFIGURATION = null;
    public static String PROVIDER_NAME = "BCPQC";
    private static String a = "BouncyCastle Post-Quantum Security Provider v1.50";
    private static final Map b = new HashMap();
    private static final String[] c = {"Rainbow", "McEliece"};

    public BouncyCastlePQCProvider() {
        super(PROVIDER_NAME, 1.5d, a);
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                BouncyCastlePQCProvider.this.a();
                return null;
            }
        });
    }

    /* access modifiers changed from: private */
    public void a() {
        a("org.bouncycastle.pqc.jcajce.provider.", c);
    }

    private void a(String str, String[] strArr) {
        Class cls;
        for (int i = 0; i != strArr.length; i++) {
            Class cls2 = null;
            try {
                ClassLoader classLoader = getClass().getClassLoader();
                if (classLoader != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(strArr[i]);
                    sb.append("$Mappings");
                    cls = classLoader.loadClass(sb.toString());
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(strArr[i]);
                    sb2.append("$Mappings");
                    cls = Class.forName(sb2.toString());
                }
                cls2 = cls;
            } catch (ClassNotFoundException unused) {
            }
            if (cls2 != null) {
                try {
                    ((AlgorithmProvider) cls2.newInstance()).configure(this);
                } catch (Exception e) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("cannot create instance of ");
                    sb3.append(str);
                    sb3.append(strArr[i]);
                    sb3.append("$Mappings : ");
                    sb3.append(e);
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
        }
    }
}
