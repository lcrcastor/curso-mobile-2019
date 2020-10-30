package org.bouncycastle.jcajce.provider.asymmetric.util;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
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
import org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public abstract class BaseKeyFactorySpi extends KeyFactorySpi implements AsymmetricKeyInfoConverter {
    public PrivateKey engineGeneratePrivate(KeySpec keySpec) {
        if (keySpec instanceof PKCS8EncodedKeySpec) {
            try {
                return generatePrivate(PrivateKeyInfo.getInstance(((PKCS8EncodedKeySpec) keySpec).getEncoded()));
            } catch (Exception unused) {
                throw new InvalidKeySpecException("encoded key spec not recognised");
            }
        } else {
            throw new InvalidKeySpecException("key spec not recognised");
        }
    }

    public PublicKey engineGeneratePublic(KeySpec keySpec) {
        if (keySpec instanceof X509EncodedKeySpec) {
            try {
                return generatePublic(SubjectPublicKeyInfo.getInstance(((X509EncodedKeySpec) keySpec).getEncoded()));
            } catch (Exception unused) {
                throw new InvalidKeySpecException("encoded key spec not recognised");
            }
        } else {
            throw new InvalidKeySpecException("key spec not recognised");
        }
    }

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
}
