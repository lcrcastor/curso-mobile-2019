package org.bouncycastle.jcajce.provider.digest;

import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2ParameterSpec;

public class SHA256 {

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new SHA256Digest());
        }

        public Object clone() {
            Digest digest = (Digest) super.clone();
            digest.digest = new SHA256Digest((SHA256Digest) this.digest);
            return digest;
        }
    }

    public static class HashMac extends BaseMac {
        public HashMac() {
            super(new HMac(new SHA256Digest()));
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACSHA256", 256, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = SHA256.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$Digest");
            configurableProvider.addAlgorithm("MessageDigest.SHA-256", sb.toString());
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHA256", "SHA-256");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.MessageDigest.");
            sb2.append(NISTObjectIdentifiers.id_sha256);
            configurableProvider.addAlgorithm(sb2.toString(), "SHA-256");
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a);
            sb3.append("$PBEWithMacKeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHHMACSHA256", sb3.toString());
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHHMACSHA-256", "PBEWITHHMACSHA256");
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Alg.Alias.SecretKeyFactory.");
            sb4.append(NISTObjectIdentifiers.id_sha256);
            configurableProvider.addAlgorithm(sb4.toString(), "PBEWITHHMACSHA256");
            String str = McElieceCCA2ParameterSpec.DEFAULT_MD;
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("$HashMac");
            String sb6 = sb5.toString();
            StringBuilder sb7 = new StringBuilder();
            sb7.append(a);
            sb7.append("$KeyGenerator");
            addHMACAlgorithm(configurableProvider, str, sb6, sb7.toString());
            addHMACAlias(configurableProvider, McElieceCCA2ParameterSpec.DEFAULT_MD, PKCSObjectIdentifiers.id_hmacWithSHA256);
            addHMACAlias(configurableProvider, McElieceCCA2ParameterSpec.DEFAULT_MD, NISTObjectIdentifiers.id_sha256);
        }
    }

    public static class PBEWithMacKeyFactory extends PBESecretKeyFactory {
        public PBEWithMacKeyFactory() {
            super("PBEwithHmacSHA256", null, false, 2, 4, 256, 0);
        }
    }

    private SHA256() {
    }
}
