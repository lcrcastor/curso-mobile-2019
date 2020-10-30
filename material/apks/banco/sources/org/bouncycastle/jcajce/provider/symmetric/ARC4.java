package org.bouncycastle.jcajce.provider.symmetric;

import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class ARC4 {

    public static class Base extends BaseStreamCipher {
        public Base() {
            super(new RC4Engine(), 0);
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("RC4", 128, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = ARC4.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$Base");
            configurableProvider.addAlgorithm("Cipher.ARC4", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.Cipher.");
            sb2.append(PKCSObjectIdentifiers.rc4);
            configurableProvider.addAlgorithm(sb2.toString(), "ARC4");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.ARCFOUR", "ARC4");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RC4", "ARC4");
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a);
            sb3.append("$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator.ARC4", sb3.toString());
            configurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.RC4", "ARC4");
            configurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.1.2.840.113549.3.4", "ARC4");
            StringBuilder sb4 = new StringBuilder();
            sb4.append(a);
            sb4.append("$PBEWithSHAAnd128BitKeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND128BITRC4", sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("$PBEWithSHAAnd40BitKeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND40BITRC4", sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append("Alg.Alias.AlgorithmParameters.");
            sb6.append(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4);
            configurableProvider.addAlgorithm(sb6.toString(), "PKCS12PBE");
            StringBuilder sb7 = new StringBuilder();
            sb7.append("Alg.Alias.AlgorithmParameters.");
            sb7.append(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4);
            configurableProvider.addAlgorithm(sb7.toString(), "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND40BITRC4", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND128BITRC4", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDRC4", "PKCS12PBE");
            StringBuilder sb8 = new StringBuilder();
            sb8.append(a);
            sb8.append("$PBEWithSHAAnd128Bit");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND128BITRC4", sb8.toString());
            StringBuilder sb9 = new StringBuilder();
            sb9.append(a);
            sb9.append("$PBEWithSHAAnd40Bit");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND40BITRC4", sb9.toString());
            StringBuilder sb10 = new StringBuilder();
            sb10.append("Alg.Alias.SecretKeyFactory.");
            sb10.append(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4);
            configurableProvider.addAlgorithm(sb10.toString(), "PBEWITHSHAAND128BITRC4");
            StringBuilder sb11 = new StringBuilder();
            sb11.append("Alg.Alias.SecretKeyFactory.");
            sb11.append(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4);
            configurableProvider.addAlgorithm(sb11.toString(), "PBEWITHSHAAND40BITRC4");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND128BITRC4", "PBEWITHSHAAND128BITRC4");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND40BITRC4", "PBEWITHSHAAND40BITRC4");
            StringBuilder sb12 = new StringBuilder();
            sb12.append("Alg.Alias.Cipher.");
            sb12.append(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4);
            configurableProvider.addAlgorithm(sb12.toString(), "PBEWITHSHAAND128BITRC4");
            StringBuilder sb13 = new StringBuilder();
            sb13.append("Alg.Alias.Cipher.");
            sb13.append(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4);
            configurableProvider.addAlgorithm(sb13.toString(), "PBEWITHSHAAND40BITRC4");
        }
    }

    public static class PBEWithSHAAnd128Bit extends BaseStreamCipher {
        public PBEWithSHAAnd128Bit() {
            super(new RC4Engine(), 0);
        }
    }

    public static class PBEWithSHAAnd128BitKeyFactory extends PBESecretKeyFactory {
        public PBEWithSHAAnd128BitKeyFactory() {
            super("PBEWithSHAAnd128BitRC4", PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4, true, 2, 1, 128, 0);
        }
    }

    public static class PBEWithSHAAnd40Bit extends BaseStreamCipher {
        public PBEWithSHAAnd40Bit() {
            super(new RC4Engine(), 0);
        }
    }

    public static class PBEWithSHAAnd40BitKeyFactory extends PBESecretKeyFactory {
        public PBEWithSHAAnd40BitKeyFactory() {
            super("PBEWithSHAAnd128BitRC4", PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4, true, 2, 1, 40, 0);
        }
    }

    private ARC4() {
    }
}
