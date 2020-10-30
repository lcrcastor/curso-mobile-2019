package org.bouncycastle.jcajce.provider.digest;

import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.digests.GOST3411Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;

public class GOST3411 {

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new GOST3411Digest());
        }

        public Object clone() {
            Digest digest = (Digest) super.clone();
            digest.digest = new GOST3411Digest((GOST3411Digest) this.digest);
            return digest;
        }
    }

    public static class HashMac extends BaseMac {
        public HashMac() {
            super(new HMac(new GOST3411Digest()));
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACGOST3411", 256, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = GOST3411.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$Digest");
            configurableProvider.addAlgorithm("MessageDigest.GOST3411", sb.toString());
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.GOST", "GOST3411");
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.GOST-3411", "GOST3411");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.MessageDigest.");
            sb2.append(CryptoProObjectIdentifiers.gostR3411);
            configurableProvider.addAlgorithm(sb2.toString(), "GOST3411");
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a);
            sb3.append("$PBEWithMacKeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHHMACGOST3411", sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Alg.Alias.SecretKeyFactory.");
            sb4.append(CryptoProObjectIdentifiers.gostR3411);
            configurableProvider.addAlgorithm(sb4.toString(), "PBEWITHHMACGOST3411");
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("$HashMac");
            String sb6 = sb5.toString();
            StringBuilder sb7 = new StringBuilder();
            sb7.append(a);
            sb7.append("$KeyGenerator");
            addHMACAlgorithm(configurableProvider, "GOST3411", sb6, sb7.toString());
            addHMACAlias(configurableProvider, "GOST3411", CryptoProObjectIdentifiers.gostR3411);
        }
    }

    public static class PBEWithMacKeyFactory extends PBESecretKeyFactory {
        public PBEWithMacKeyFactory() {
            super("PBEwithHmacGOST3411", null, false, 2, 6, 256, 0);
        }
    }

    private GOST3411() {
    }
}
