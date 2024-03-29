package org.bouncycastle.jcajce.provider.digest;

import io.fabric.sdk.android.services.events.EventsFilesManager;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.digests.SkeinDigest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.macs.SkeinMac;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;

public class Skein {

    public static class DigestSkein1024 extends BCMessageDigest implements Cloneable {
        public DigestSkein1024(int i) {
            super(new SkeinDigest(1024, i));
        }

        public Object clone() {
            BCMessageDigest bCMessageDigest = (BCMessageDigest) super.clone();
            bCMessageDigest.digest = new SkeinDigest((SkeinDigest) this.digest);
            return bCMessageDigest;
        }
    }

    public static class DigestSkein256 extends BCMessageDigest implements Cloneable {
        public DigestSkein256(int i) {
            super(new SkeinDigest(256, i));
        }

        public Object clone() {
            BCMessageDigest bCMessageDigest = (BCMessageDigest) super.clone();
            bCMessageDigest.digest = new SkeinDigest((SkeinDigest) this.digest);
            return bCMessageDigest;
        }
    }

    public static class DigestSkein512 extends BCMessageDigest implements Cloneable {
        public DigestSkein512(int i) {
            super(new SkeinDigest(512, i));
        }

        public Object clone() {
            BCMessageDigest bCMessageDigest = (BCMessageDigest) super.clone();
            bCMessageDigest.digest = new SkeinDigest((SkeinDigest) this.digest);
            return bCMessageDigest;
        }
    }

    public static class Digest_1024_1024 extends DigestSkein1024 {
        public Digest_1024_1024() {
            super(1024);
        }
    }

    public static class Digest_1024_384 extends DigestSkein1024 {
        public Digest_1024_384() {
            super(384);
        }
    }

    public static class Digest_1024_512 extends DigestSkein1024 {
        public Digest_1024_512() {
            super(512);
        }
    }

    public static class Digest_256_128 extends DigestSkein256 {
        public Digest_256_128() {
            super(128);
        }
    }

    public static class Digest_256_160 extends DigestSkein256 {
        public Digest_256_160() {
            super(CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256);
        }
    }

    public static class Digest_256_224 extends DigestSkein256 {
        public Digest_256_224() {
            super(224);
        }
    }

    public static class Digest_256_256 extends DigestSkein256 {
        public Digest_256_256() {
            super(256);
        }
    }

    public static class Digest_512_128 extends DigestSkein512 {
        public Digest_512_128() {
            super(128);
        }
    }

    public static class Digest_512_160 extends DigestSkein512 {
        public Digest_512_160() {
            super(CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256);
        }
    }

    public static class Digest_512_224 extends DigestSkein512 {
        public Digest_512_224() {
            super(224);
        }
    }

    public static class Digest_512_256 extends DigestSkein512 {
        public Digest_512_256() {
            super(256);
        }
    }

    public static class Digest_512_384 extends DigestSkein512 {
        public Digest_512_384() {
            super(384);
        }
    }

    public static class Digest_512_512 extends DigestSkein512 {
        public Digest_512_512() {
            super(512);
        }
    }

    public static class HMacKeyGenerator_1024_1024 extends BaseKeyGenerator {
        public HMacKeyGenerator_1024_1024() {
            super("HMACSkein-1024-1024", 1024, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_1024_384 extends BaseKeyGenerator {
        public HMacKeyGenerator_1024_384() {
            super("HMACSkein-1024-384", 384, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_1024_512 extends BaseKeyGenerator {
        public HMacKeyGenerator_1024_512() {
            super("HMACSkein-1024-512", 512, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_256_128 extends BaseKeyGenerator {
        public HMacKeyGenerator_256_128() {
            super("HMACSkein-256-128", 128, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_256_160 extends BaseKeyGenerator {
        public HMacKeyGenerator_256_160() {
            super("HMACSkein-256-160", CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_256_224 extends BaseKeyGenerator {
        public HMacKeyGenerator_256_224() {
            super("HMACSkein-256-224", 224, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_256_256 extends BaseKeyGenerator {
        public HMacKeyGenerator_256_256() {
            super("HMACSkein-256-256", 256, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_512_128 extends BaseKeyGenerator {
        public HMacKeyGenerator_512_128() {
            super("HMACSkein-512-128", 128, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_512_160 extends BaseKeyGenerator {
        public HMacKeyGenerator_512_160() {
            super("HMACSkein-512-160", CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_512_224 extends BaseKeyGenerator {
        public HMacKeyGenerator_512_224() {
            super("HMACSkein-512-224", 224, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_512_256 extends BaseKeyGenerator {
        public HMacKeyGenerator_512_256() {
            super("HMACSkein-512-256", 256, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_512_384 extends BaseKeyGenerator {
        public HMacKeyGenerator_512_384() {
            super("HMACSkein-512-384", 384, new CipherKeyGenerator());
        }
    }

    public static class HMacKeyGenerator_512_512 extends BaseKeyGenerator {
        public HMacKeyGenerator_512_512() {
            super("HMACSkein-512-512", 512, new CipherKeyGenerator());
        }
    }

    public static class HashMac_1024_1024 extends BaseMac {
        public HashMac_1024_1024() {
            super(new HMac(new SkeinDigest(1024, 1024)));
        }
    }

    public static class HashMac_1024_384 extends BaseMac {
        public HashMac_1024_384() {
            super(new HMac(new SkeinDigest(1024, 384)));
        }
    }

    public static class HashMac_1024_512 extends BaseMac {
        public HashMac_1024_512() {
            super(new HMac(new SkeinDigest(1024, 512)));
        }
    }

    public static class HashMac_256_128 extends BaseMac {
        public HashMac_256_128() {
            super(new HMac(new SkeinDigest(256, 128)));
        }
    }

    public static class HashMac_256_160 extends BaseMac {
        public HashMac_256_160() {
            super(new HMac(new SkeinDigest(256, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256)));
        }
    }

    public static class HashMac_256_224 extends BaseMac {
        public HashMac_256_224() {
            super(new HMac(new SkeinDigest(256, 224)));
        }
    }

    public static class HashMac_256_256 extends BaseMac {
        public HashMac_256_256() {
            super(new HMac(new SkeinDigest(256, 256)));
        }
    }

    public static class HashMac_512_128 extends BaseMac {
        public HashMac_512_128() {
            super(new HMac(new SkeinDigest(512, 128)));
        }
    }

    public static class HashMac_512_160 extends BaseMac {
        public HashMac_512_160() {
            super(new HMac(new SkeinDigest(512, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256)));
        }
    }

    public static class HashMac_512_224 extends BaseMac {
        public HashMac_512_224() {
            super(new HMac(new SkeinDigest(512, 224)));
        }
    }

    public static class HashMac_512_256 extends BaseMac {
        public HashMac_512_256() {
            super(new HMac(new SkeinDigest(512, 256)));
        }
    }

    public static class HashMac_512_384 extends BaseMac {
        public HashMac_512_384() {
            super(new HMac(new SkeinDigest(512, 384)));
        }
    }

    public static class HashMac_512_512 extends BaseMac {
        public HashMac_512_512() {
            super(new HMac(new SkeinDigest(512, 512)));
        }
    }

    public static class Mappings extends DigestAlgorithmProvider {
        private static final String a = Skein.class.getName();

        private void a(ConfigurableProvider configurableProvider, int i, int i2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Skein-MAC-");
            sb.append(i);
            sb.append("-");
            sb.append(i2);
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a);
            sb3.append("$SkeinMac_");
            sb3.append(i);
            sb3.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            sb3.append(i2);
            String sb4 = sb3.toString();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("$SkeinMacKeyGenerator_");
            sb5.append(i);
            sb5.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            sb5.append(i2);
            String sb6 = sb5.toString();
            StringBuilder sb7 = new StringBuilder();
            sb7.append("Mac.");
            sb7.append(sb2);
            configurableProvider.addAlgorithm(sb7.toString(), sb4);
            StringBuilder sb8 = new StringBuilder();
            sb8.append("Alg.Alias.Mac.Skein-MAC");
            sb8.append(i);
            sb8.append("/");
            sb8.append(i2);
            configurableProvider.addAlgorithm(sb8.toString(), sb2);
            StringBuilder sb9 = new StringBuilder();
            sb9.append("KeyGenerator.");
            sb9.append(sb2);
            configurableProvider.addAlgorithm(sb9.toString(), sb6);
            StringBuilder sb10 = new StringBuilder();
            sb10.append("Alg.Alias.KeyGenerator.Skein-MAC");
            sb10.append(i);
            sb10.append("/");
            sb10.append(i2);
            configurableProvider.addAlgorithm(sb10.toString(), sb2);
        }

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$Digest_256_128");
            configurableProvider.addAlgorithm("MessageDigest.Skein-256-128", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a);
            sb2.append("$Digest_256_160");
            configurableProvider.addAlgorithm("MessageDigest.Skein-256-160", sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a);
            sb3.append("$Digest_256_224");
            configurableProvider.addAlgorithm("MessageDigest.Skein-256-224", sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(a);
            sb4.append("$Digest_256_256");
            configurableProvider.addAlgorithm("MessageDigest.Skein-256-256", sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("$Digest_512_128");
            configurableProvider.addAlgorithm("MessageDigest.Skein-512-128", sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append(a);
            sb6.append("$Digest_512_160");
            configurableProvider.addAlgorithm("MessageDigest.Skein-512-160", sb6.toString());
            StringBuilder sb7 = new StringBuilder();
            sb7.append(a);
            sb7.append("$Digest_512_224");
            configurableProvider.addAlgorithm("MessageDigest.Skein-512-224", sb7.toString());
            StringBuilder sb8 = new StringBuilder();
            sb8.append(a);
            sb8.append("$Digest_512_256");
            configurableProvider.addAlgorithm("MessageDigest.Skein-512-256", sb8.toString());
            StringBuilder sb9 = new StringBuilder();
            sb9.append(a);
            sb9.append("$Digest_512_384");
            configurableProvider.addAlgorithm("MessageDigest.Skein-512-384", sb9.toString());
            StringBuilder sb10 = new StringBuilder();
            sb10.append(a);
            sb10.append("$Digest_512_512");
            configurableProvider.addAlgorithm("MessageDigest.Skein-512-512", sb10.toString());
            StringBuilder sb11 = new StringBuilder();
            sb11.append(a);
            sb11.append("$Digest_1024_384");
            configurableProvider.addAlgorithm("MessageDigest.Skein-1024-384", sb11.toString());
            StringBuilder sb12 = new StringBuilder();
            sb12.append(a);
            sb12.append("$Digest_1024_512");
            configurableProvider.addAlgorithm("MessageDigest.Skein-1024-512", sb12.toString());
            StringBuilder sb13 = new StringBuilder();
            sb13.append(a);
            sb13.append("$Digest_1024_1024");
            configurableProvider.addAlgorithm("MessageDigest.Skein-1024-1024", sb13.toString());
            StringBuilder sb14 = new StringBuilder();
            sb14.append(a);
            sb14.append("$HashMac_256_128");
            String sb15 = sb14.toString();
            StringBuilder sb16 = new StringBuilder();
            sb16.append(a);
            sb16.append("$HMacKeyGenerator_256_128");
            addHMACAlgorithm(configurableProvider, "Skein-256-128", sb15, sb16.toString());
            StringBuilder sb17 = new StringBuilder();
            sb17.append(a);
            sb17.append("$HashMac_256_160");
            String sb18 = sb17.toString();
            StringBuilder sb19 = new StringBuilder();
            sb19.append(a);
            sb19.append("$HMacKeyGenerator_256_160");
            addHMACAlgorithm(configurableProvider, "Skein-256-160", sb18, sb19.toString());
            StringBuilder sb20 = new StringBuilder();
            sb20.append(a);
            sb20.append("$HashMac_256_224");
            String sb21 = sb20.toString();
            StringBuilder sb22 = new StringBuilder();
            sb22.append(a);
            sb22.append("$HMacKeyGenerator_256_224");
            addHMACAlgorithm(configurableProvider, "Skein-256-224", sb21, sb22.toString());
            StringBuilder sb23 = new StringBuilder();
            sb23.append(a);
            sb23.append("$HashMac_256_256");
            String sb24 = sb23.toString();
            StringBuilder sb25 = new StringBuilder();
            sb25.append(a);
            sb25.append("$HMacKeyGenerator_256_256");
            addHMACAlgorithm(configurableProvider, "Skein-256-256", sb24, sb25.toString());
            StringBuilder sb26 = new StringBuilder();
            sb26.append(a);
            sb26.append("$HashMac_512_128");
            String sb27 = sb26.toString();
            StringBuilder sb28 = new StringBuilder();
            sb28.append(a);
            sb28.append("$HMacKeyGenerator_512_128");
            addHMACAlgorithm(configurableProvider, "Skein-512-128", sb27, sb28.toString());
            StringBuilder sb29 = new StringBuilder();
            sb29.append(a);
            sb29.append("$HashMac_512_160");
            String sb30 = sb29.toString();
            StringBuilder sb31 = new StringBuilder();
            sb31.append(a);
            sb31.append("$HMacKeyGenerator_512_160");
            addHMACAlgorithm(configurableProvider, "Skein-512-160", sb30, sb31.toString());
            StringBuilder sb32 = new StringBuilder();
            sb32.append(a);
            sb32.append("$HashMac_512_224");
            String sb33 = sb32.toString();
            StringBuilder sb34 = new StringBuilder();
            sb34.append(a);
            sb34.append("$HMacKeyGenerator_512_224");
            addHMACAlgorithm(configurableProvider, "Skein-512-224", sb33, sb34.toString());
            StringBuilder sb35 = new StringBuilder();
            sb35.append(a);
            sb35.append("$HashMac_512_256");
            String sb36 = sb35.toString();
            StringBuilder sb37 = new StringBuilder();
            sb37.append(a);
            sb37.append("$HMacKeyGenerator_512_256");
            addHMACAlgorithm(configurableProvider, "Skein-512-256", sb36, sb37.toString());
            StringBuilder sb38 = new StringBuilder();
            sb38.append(a);
            sb38.append("$HashMac_512_384");
            String sb39 = sb38.toString();
            StringBuilder sb40 = new StringBuilder();
            sb40.append(a);
            sb40.append("$HMacKeyGenerator_512_384");
            addHMACAlgorithm(configurableProvider, "Skein-512-384", sb39, sb40.toString());
            StringBuilder sb41 = new StringBuilder();
            sb41.append(a);
            sb41.append("$HashMac_512_512");
            String sb42 = sb41.toString();
            StringBuilder sb43 = new StringBuilder();
            sb43.append(a);
            sb43.append("$HMacKeyGenerator_512_512");
            addHMACAlgorithm(configurableProvider, "Skein-512-512", sb42, sb43.toString());
            StringBuilder sb44 = new StringBuilder();
            sb44.append(a);
            sb44.append("$HashMac_1024_384");
            String sb45 = sb44.toString();
            StringBuilder sb46 = new StringBuilder();
            sb46.append(a);
            sb46.append("$HMacKeyGenerator_1024_384");
            addHMACAlgorithm(configurableProvider, "Skein-1024-384", sb45, sb46.toString());
            StringBuilder sb47 = new StringBuilder();
            sb47.append(a);
            sb47.append("$HashMac_1024_512");
            String sb48 = sb47.toString();
            StringBuilder sb49 = new StringBuilder();
            sb49.append(a);
            sb49.append("$HMacKeyGenerator_1024_512");
            addHMACAlgorithm(configurableProvider, "Skein-1024-512", sb48, sb49.toString());
            StringBuilder sb50 = new StringBuilder();
            sb50.append(a);
            sb50.append("$HashMac_1024_1024");
            String sb51 = sb50.toString();
            StringBuilder sb52 = new StringBuilder();
            sb52.append(a);
            sb52.append("$HMacKeyGenerator_1024_1024");
            addHMACAlgorithm(configurableProvider, "Skein-1024-1024", sb51, sb52.toString());
            a(configurableProvider, 256, 128);
            a(configurableProvider, 256, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256);
            a(configurableProvider, 256, 224);
            a(configurableProvider, 256, 256);
            a(configurableProvider, 512, 128);
            a(configurableProvider, 512, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256);
            a(configurableProvider, 512, 224);
            a(configurableProvider, 512, 256);
            a(configurableProvider, 512, 384);
            a(configurableProvider, 512, 512);
            a(configurableProvider, 1024, 384);
            a(configurableProvider, 1024, 512);
            a(configurableProvider, 1024, 1024);
        }
    }

    public static class SkeinMacKeyGenerator_1024_1024 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_1024_1024() {
            super("Skein-MAC-1024-1024", 1024, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_1024_384 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_1024_384() {
            super("Skein-MAC-1024-384", 384, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_1024_512 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_1024_512() {
            super("Skein-MAC-1024-512", 512, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_256_128 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_256_128() {
            super("Skein-MAC-256-128", 128, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_256_160 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_256_160() {
            super("Skein-MAC-256-160", CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_256_224 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_256_224() {
            super("Skein-MAC-256-224", 224, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_256_256 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_256_256() {
            super("Skein-MAC-256-256", 256, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_512_128 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_512_128() {
            super("Skein-MAC-512-128", 128, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_512_160 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_512_160() {
            super("Skein-MAC-512-160", CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_512_224 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_512_224() {
            super("Skein-MAC-512-224", 224, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_512_256 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_512_256() {
            super("Skein-MAC-512-256", 256, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_512_384 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_512_384() {
            super("Skein-MAC-512-384", 384, new CipherKeyGenerator());
        }
    }

    public static class SkeinMacKeyGenerator_512_512 extends BaseKeyGenerator {
        public SkeinMacKeyGenerator_512_512() {
            super("Skein-MAC-512-512", 512, new CipherKeyGenerator());
        }
    }

    public static class SkeinMac_1024_1024 extends BaseMac {
        public SkeinMac_1024_1024() {
            super(new SkeinMac(1024, 1024));
        }
    }

    public static class SkeinMac_1024_384 extends BaseMac {
        public SkeinMac_1024_384() {
            super(new SkeinMac(1024, 384));
        }
    }

    public static class SkeinMac_1024_512 extends BaseMac {
        public SkeinMac_1024_512() {
            super(new SkeinMac(1024, 512));
        }
    }

    public static class SkeinMac_256_128 extends BaseMac {
        public SkeinMac_256_128() {
            super(new SkeinMac(256, 128));
        }
    }

    public static class SkeinMac_256_160 extends BaseMac {
        public SkeinMac_256_160() {
            super(new SkeinMac(256, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256));
        }
    }

    public static class SkeinMac_256_224 extends BaseMac {
        public SkeinMac_256_224() {
            super(new SkeinMac(256, 224));
        }
    }

    public static class SkeinMac_256_256 extends BaseMac {
        public SkeinMac_256_256() {
            super(new SkeinMac(256, 256));
        }
    }

    public static class SkeinMac_512_128 extends BaseMac {
        public SkeinMac_512_128() {
            super(new SkeinMac(512, 128));
        }
    }

    public static class SkeinMac_512_160 extends BaseMac {
        public SkeinMac_512_160() {
            super(new SkeinMac(512, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256));
        }
    }

    public static class SkeinMac_512_224 extends BaseMac {
        public SkeinMac_512_224() {
            super(new SkeinMac(512, 224));
        }
    }

    public static class SkeinMac_512_256 extends BaseMac {
        public SkeinMac_512_256() {
            super(new SkeinMac(512, 256));
        }
    }

    public static class SkeinMac_512_384 extends BaseMac {
        public SkeinMac_512_384() {
            super(new SkeinMac(512, 384));
        }
    }

    public static class SkeinMac_512_512 extends BaseMac {
        public SkeinMac_512_512() {
            super(new SkeinMac(512, 512));
        }
    }

    private Skein() {
    }
}
