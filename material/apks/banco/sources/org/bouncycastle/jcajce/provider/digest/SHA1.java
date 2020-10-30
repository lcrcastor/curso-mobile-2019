package org.bouncycastle.jcajce.provider.digest;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import org.bouncycastle.asn1.iana.IANAObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util;
import org.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;

public class SHA1 {

    public static class BasePBKDF2WithHmacSHA1 extends BaseSecretKeyFactory {
        private int a;

        public BasePBKDF2WithHmacSHA1(String str, int i) {
            super(str, PKCSObjectIdentifiers.id_PBKDF2);
            this.a = i;
        }

        /* access modifiers changed from: protected */
        public SecretKey engineGenerateSecret(KeySpec keySpec) {
            if (keySpec instanceof PBEKeySpec) {
                PBEKeySpec pBEKeySpec = (PBEKeySpec) keySpec;
                if (pBEKeySpec.getSalt() == null) {
                    throw new InvalidKeySpecException("missing required salt");
                } else if (pBEKeySpec.getIterationCount() <= 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("positive iteration count required: ");
                    sb.append(pBEKeySpec.getIterationCount());
                    throw new InvalidKeySpecException(sb.toString());
                } else if (pBEKeySpec.getKeyLength() <= 0) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("positive key length required: ");
                    sb2.append(pBEKeySpec.getKeyLength());
                    throw new InvalidKeySpecException(sb2.toString());
                } else if (pBEKeySpec.getPassword().length == 0) {
                    throw new IllegalArgumentException("password empty");
                } else {
                    int keyLength = pBEKeySpec.getKeyLength();
                    BCPBEKey bCPBEKey = new BCPBEKey(this.algName, this.algOid, this.a, 1, keyLength, -1, pBEKeySpec, Util.makePBEMacParameters(pBEKeySpec, this.a, 1, keyLength));
                    return bCPBEKey;
                }
            } else {
                throw new InvalidKeySpecException("Invalid KeySpec");
            }
        }
    }

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new SHA1Digest());
        }

        public Object clone() {
            Digest digest = (Digest) super.clone();
            digest.digest = new SHA1Digest((SHA1Digest) this.digest);
            return digest;
        }
    }

    public static class HashMac extends BaseMac {
        public HashMac() {
            super(new HMac(new SHA1Digest()));
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACSHA1", CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = SHA1.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$Digest");
            configurableProvider.addAlgorithm("MessageDigest.SHA-1", sb.toString());
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHA1", CommonUtils.SHA1_INSTANCE);
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHA", CommonUtils.SHA1_INSTANCE);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.MessageDigest.");
            sb2.append(OIWObjectIdentifiers.idSHA1);
            configurableProvider.addAlgorithm(sb2.toString(), CommonUtils.SHA1_INSTANCE);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a);
            sb3.append("$HashMac");
            String sb4 = sb3.toString();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("$KeyGenerator");
            addHMACAlgorithm(configurableProvider, "SHA1", sb4, sb5.toString());
            addHMACAlias(configurableProvider, "SHA1", PKCSObjectIdentifiers.id_hmacWithSHA1);
            addHMACAlias(configurableProvider, "SHA1", IANAObjectIdentifiers.hmacSHA1);
            StringBuilder sb6 = new StringBuilder();
            sb6.append(a);
            sb6.append("$SHA1Mac");
            configurableProvider.addAlgorithm("Mac.PBEWITHHMACSHA", sb6.toString());
            StringBuilder sb7 = new StringBuilder();
            sb7.append(a);
            sb7.append("$SHA1Mac");
            configurableProvider.addAlgorithm("Mac.PBEWITHHMACSHA1", sb7.toString());
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHHMACSHA", "PBEWITHHMACSHA1");
            StringBuilder sb8 = new StringBuilder();
            sb8.append("Alg.Alias.SecretKeyFactory.");
            sb8.append(OIWObjectIdentifiers.idSHA1);
            configurableProvider.addAlgorithm(sb8.toString(), "PBEWITHHMACSHA1");
            StringBuilder sb9 = new StringBuilder();
            sb9.append("Alg.Alias.Mac.");
            sb9.append(OIWObjectIdentifiers.idSHA1);
            configurableProvider.addAlgorithm(sb9.toString(), "PBEWITHHMACSHA");
            StringBuilder sb10 = new StringBuilder();
            sb10.append(a);
            sb10.append("$PBEWithMacKeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHHMACSHA1", sb10.toString());
            StringBuilder sb11 = new StringBuilder();
            sb11.append(a);
            sb11.append("$PBKDF2WithHmacSHA1UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA1", sb11.toString());
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WithHmacSHA1AndUTF8", "PBKDF2WithHmacSHA1");
            StringBuilder sb12 = new StringBuilder();
            sb12.append(a);
            sb12.append("$PBKDF2WithHmacSHA18BIT");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA1And8BIT", sb12.toString());
        }
    }

    public static class PBEWithMacKeyFactory extends PBESecretKeyFactory {
        public PBEWithMacKeyFactory() {
            super("PBEwithHmacSHA", null, false, 2, 1, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, 0);
        }
    }

    public static class PBKDF2WithHmacSHA18BIT extends BasePBKDF2WithHmacSHA1 {
        public PBKDF2WithHmacSHA18BIT() {
            super("PBKDF2WithHmacSHA1And8bit", 1);
        }
    }

    public static class PBKDF2WithHmacSHA1UTF8 extends BasePBKDF2WithHmacSHA1 {
        public PBKDF2WithHmacSHA1UTF8() {
            super("PBKDF2WithHmacSHA1", 5);
        }
    }

    public static class SHA1Mac extends BaseMac {
        public SHA1Mac() {
            super(new HMac(new SHA1Digest()));
        }
    }

    private SHA1() {
    }
}
