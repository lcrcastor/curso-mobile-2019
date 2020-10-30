package org.bouncycastle.jcajce.provider.digest;

import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.SHA512tDigest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.macs.OldHMac;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;

public class SHA512 {

    public static class Digest extends BCMessageDigest implements Cloneable {
        public Digest() {
            super(new SHA512Digest());
        }

        public Object clone() {
            Digest digest = (Digest) super.clone();
            digest.digest = new SHA512Digest((SHA512Digest) this.digest);
            return digest;
        }
    }

    public static class DigestT extends BCMessageDigest implements Cloneable {
        public DigestT(int i) {
            super(new SHA512tDigest(i));
        }

        public Object clone() {
            DigestT digestT = (DigestT) super.clone();
            digestT.digest = new SHA512tDigest((SHA512tDigest) this.digest);
            return digestT;
        }
    }

    public static class DigestT224 extends DigestT {
        public DigestT224() {
            super(224);
        }
    }

    public static class DigestT256 extends DigestT {
        public DigestT256() {
            super(256);
        }
    }

    public static class HashMac extends BaseMac {
        public HashMac() {
            super(new HMac(new SHA512Digest()));
        }
    }

    public static class HashMacT224 extends BaseMac {
        public HashMacT224() {
            super(new HMac(new SHA512tDigest(224)));
        }
    }

    public static class HashMacT256 extends BaseMac {
        public HashMacT256() {
            super(new HMac(new SHA512tDigest(256)));
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACSHA512", 512, new CipherKeyGenerator());
        }
    }

    public static class KeyGeneratorT224 extends BaseKeyGenerator {
        public KeyGeneratorT224() {
            super("HMACSHA512/224", 224, new CipherKeyGenerator());
        }
    }

    public static class KeyGeneratorT256 extends BaseKeyGenerator {
        public KeyGeneratorT256() {
            super("HMACSHA512/256", 256, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = SHA512.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$Digest");
            configurableProvider.addAlgorithm("MessageDigest.SHA-512", sb.toString());
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHA512", "SHA-512");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.MessageDigest.");
            sb2.append(NISTObjectIdentifiers.id_sha512);
            configurableProvider.addAlgorithm(sb2.toString(), "SHA-512");
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a);
            sb3.append("$DigestT224");
            configurableProvider.addAlgorithm("MessageDigest.SHA-512/224", sb3.toString());
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHA512/224", "SHA-512/224");
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Alg.Alias.MessageDigest.");
            sb4.append(NISTObjectIdentifiers.id_sha512_224);
            configurableProvider.addAlgorithm(sb4.toString(), "SHA-512/224");
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("$DigestT256");
            configurableProvider.addAlgorithm("MessageDigest.SHA-512/256", sb5.toString());
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHA512256", "SHA-512/256");
            StringBuilder sb6 = new StringBuilder();
            sb6.append("Alg.Alias.MessageDigest.");
            sb6.append(NISTObjectIdentifiers.id_sha512_256);
            configurableProvider.addAlgorithm(sb6.toString(), "SHA-512/256");
            StringBuilder sb7 = new StringBuilder();
            sb7.append(a);
            sb7.append("$OldSHA512");
            configurableProvider.addAlgorithm("Mac.OLDHMACSHA512", sb7.toString());
            StringBuilder sb8 = new StringBuilder();
            sb8.append(a);
            sb8.append("$HashMac");
            String sb9 = sb8.toString();
            StringBuilder sb10 = new StringBuilder();
            sb10.append(a);
            sb10.append("$KeyGenerator");
            addHMACAlgorithm(configurableProvider, "SHA512", sb9, sb10.toString());
            addHMACAlias(configurableProvider, "SHA512", PKCSObjectIdentifiers.id_hmacWithSHA512);
            StringBuilder sb11 = new StringBuilder();
            sb11.append(a);
            sb11.append("$HashMacT224");
            String sb12 = sb11.toString();
            StringBuilder sb13 = new StringBuilder();
            sb13.append(a);
            sb13.append("$KeyGeneratorT224");
            addHMACAlgorithm(configurableProvider, "SHA512/224", sb12, sb13.toString());
            StringBuilder sb14 = new StringBuilder();
            sb14.append(a);
            sb14.append("$HashMacT256");
            String sb15 = sb14.toString();
            StringBuilder sb16 = new StringBuilder();
            sb16.append(a);
            sb16.append("$KeyGeneratorT256");
            addHMACAlgorithm(configurableProvider, "SHA512/256", sb15, sb16.toString());
        }
    }

    public static class OldSHA512 extends BaseMac {
        public OldSHA512() {
            super(new OldHMac(new SHA512Digest()));
        }
    }

    private SHA512() {
    }
}
