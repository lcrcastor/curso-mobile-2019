package org.bouncycastle.jcajce.provider.asymmetric.x509;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class KeyFactory extends KeyFactorySpi {
    /* access modifiers changed from: protected */
    public PrivateKey engineGeneratePrivate(KeySpec keySpec) {
        if (keySpec instanceof PKCS8EncodedKeySpec) {
            try {
                PrivateKeyInfo instance = PrivateKeyInfo.getInstance(((PKCS8EncodedKeySpec) keySpec).getEncoded());
                PrivateKey privateKey = BouncyCastleProvider.getPrivateKey(instance);
                if (privateKey != null) {
                    return privateKey;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("no factory found for OID: ");
                sb.append(instance.getPrivateKeyAlgorithm().getAlgorithm());
                throw new InvalidKeySpecException(sb.toString());
            } catch (Exception e) {
                throw new InvalidKeySpecException(e.toString());
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unknown KeySpec type: ");
            sb2.append(keySpec.getClass().getName());
            throw new InvalidKeySpecException(sb2.toString());
        }
    }

    /* access modifiers changed from: protected */
    public PublicKey engineGeneratePublic(KeySpec keySpec) {
        if (keySpec instanceof X509EncodedKeySpec) {
            try {
                SubjectPublicKeyInfo instance = SubjectPublicKeyInfo.getInstance(((X509EncodedKeySpec) keySpec).getEncoded());
                PublicKey publicKey = BouncyCastleProvider.getPublicKey(instance);
                if (publicKey != null) {
                    return publicKey;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("no factory found for OID: ");
                sb.append(instance.getAlgorithm().getAlgorithm());
                throw new InvalidKeySpecException(sb.toString());
            } catch (Exception e) {
                throw new InvalidKeySpecException(e.toString());
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unknown KeySpec type: ");
            sb2.append(keySpec.getClass().getName());
            throw new InvalidKeySpecException(sb2.toString());
        }
    }

    /* access modifiers changed from: protected */
    public KeySpec engineGetKeySpec(Key key, Class cls) {
        if (cls.isAssignableFrom(PKCS8EncodedKeySpec.class) && key.getFormat().equals("PKCS#8")) {
            return new PKCS8EncodedKeySpec(key.getEncoded());
        }
        if (cls.isAssignableFrom(X509EncodedKeySpec.class) && key.getFormat().equals("X.509")) {
            return new X509EncodedKeySpec(key.getEncoded());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("not implemented yet ");
        sb.append(key);
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(cls);
        throw new InvalidKeySpecException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public Key engineTranslateKey(Key key) {
        StringBuilder sb = new StringBuilder();
        sb.append("not implemented yet ");
        sb.append(key);
        throw new InvalidKeyException(sb.toString());
    }
}
