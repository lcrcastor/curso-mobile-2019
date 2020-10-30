package org.bouncycastle.jcajce.provider.digest;

import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;

public class SHA3 {

    public static class Digest224 extends DigestSHA3 {
        public Digest224() {
            super(224);
        }
    }

    public static class Digest256 extends DigestSHA3 {
        public Digest256() {
            super(256);
        }
    }

    public static class Digest384 extends DigestSHA3 {
        public Digest384() {
            super(384);
        }
    }

    public static class Digest512 extends DigestSHA3 {
        public Digest512() {
            super(512);
        }
    }

    public static class DigestSHA3 extends BCMessageDigest implements Cloneable {
        public DigestSHA3(int i) {
            super(new SHA3Digest(i));
        }

        public Object clone() {
            BCMessageDigest bCMessageDigest = (BCMessageDigest) super.clone();
            bCMessageDigest.digest = new SHA3Digest((SHA3Digest) this.digest);
            return bCMessageDigest;
        }
    }

    public static class HashMac224 extends BaseMac {
        public HashMac224() {
            super(new HMac(new SHA3Digest(224)));
        }
    }

    public static class HashMac256 extends BaseMac {
        public HashMac256() {
            super(new HMac(new SHA3Digest(256)));
        }
    }

    public static class HashMac384 extends BaseMac {
        public HashMac384() {
            super(new HMac(new SHA3Digest(384)));
        }
    }

    public static class HashMac512 extends BaseMac {
        public HashMac512() {
            super(new HMac(new SHA3Digest(512)));
        }
    }

    public static class KeyGenerator224 extends BaseKeyGenerator {
        public KeyGenerator224() {
            super("HMACSHA3-224", 224, new CipherKeyGenerator());
        }
    }

    public static class KeyGenerator256 extends BaseKeyGenerator {
        public KeyGenerator256() {
            super("HMACSHA3-256", 256, new CipherKeyGenerator());
        }
    }

    public static class KeyGenerator384 extends BaseKeyGenerator {
        public KeyGenerator384() {
            super("HMACSHA3-384", 384, new CipherKeyGenerator());
        }
    }

    public static class KeyGenerator512 extends BaseKeyGenerator {
        public KeyGenerator512() {
            super("HMACSHA3-512", 512, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = SHA3.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$Digest224");
            configurableProvider.addAlgorithm("MessageDigest.SHA3-224", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a);
            sb2.append("$Digest256");
            configurableProvider.addAlgorithm("MessageDigest.SHA3-256", sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a);
            sb3.append("$Digest384");
            configurableProvider.addAlgorithm("MessageDigest.SHA3-384", sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(a);
            sb4.append("$Digest512");
            configurableProvider.addAlgorithm("MessageDigest.SHA3-512", sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("$HashMac224");
            String sb6 = sb5.toString();
            StringBuilder sb7 = new StringBuilder();
            sb7.append(a);
            sb7.append("$KeyGenerator224");
            addHMACAlgorithm(configurableProvider, "SHA3-224", sb6, sb7.toString());
            StringBuilder sb8 = new StringBuilder();
            sb8.append(a);
            sb8.append("$HashMac256");
            String sb9 = sb8.toString();
            StringBuilder sb10 = new StringBuilder();
            sb10.append(a);
            sb10.append("$KeyGenerator256");
            addHMACAlgorithm(configurableProvider, "SHA3-256", sb9, sb10.toString());
            StringBuilder sb11 = new StringBuilder();
            sb11.append(a);
            sb11.append("$HashMac384");
            String sb12 = sb11.toString();
            StringBuilder sb13 = new StringBuilder();
            sb13.append(a);
            sb13.append("$KeyGenerator384");
            addHMACAlgorithm(configurableProvider, "SHA3-384", sb12, sb13.toString());
            StringBuilder sb14 = new StringBuilder();
            sb14.append(a);
            sb14.append("$HashMac512");
            String sb15 = sb14.toString();
            StringBuilder sb16 = new StringBuilder();
            sb16.append(a);
            sb16.append("$KeyGenerator512");
            addHMACAlgorithm(configurableProvider, "SHA3-512", sb15, sb16.toString());
        }
    }

    private SHA3() {
    }
}
