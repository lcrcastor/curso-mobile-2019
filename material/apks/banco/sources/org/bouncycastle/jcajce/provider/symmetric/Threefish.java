package org.bouncycastle.jcajce.provider.symmetric;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.engines.ThreefishEngine;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class Threefish {

    public static class AlgParams_1024 extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "Threefish-1024 IV";
        }
    }

    public static class AlgParams_256 extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "Threefish-256 IV";
        }
    }

    public static class AlgParams_512 extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "Threefish-512 IV";
        }
    }

    public static class ECB_1024 extends BaseBlockCipher {
        public ECB_1024() {
            super((BlockCipher) new ThreefishEngine(1024));
        }
    }

    public static class ECB_256 extends BaseBlockCipher {
        public ECB_256() {
            super((BlockCipher) new ThreefishEngine(256));
        }
    }

    public static class ECB_512 extends BaseBlockCipher {
        public ECB_512() {
            super((BlockCipher) new ThreefishEngine(512));
        }
    }

    public static class KeyGen_1024 extends BaseKeyGenerator {
        public KeyGen_1024() {
            super("Threefish-1024", 1024, new CipherKeyGenerator());
        }
    }

    public static class KeyGen_256 extends BaseKeyGenerator {
        public KeyGen_256() {
            super("Threefish-256", 256, new CipherKeyGenerator());
        }
    }

    public static class KeyGen_512 extends BaseKeyGenerator {
        public KeyGen_512() {
            super("Threefish-512", 512, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = Threefish.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$ECB_256");
            configurableProvider.addAlgorithm("Cipher.Threefish-256", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a);
            sb2.append("$ECB_512");
            configurableProvider.addAlgorithm("Cipher.Threefish-512", sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a);
            sb3.append("$ECB_1024");
            configurableProvider.addAlgorithm("Cipher.Threefish-1024", sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(a);
            sb4.append("$KeyGen_256");
            configurableProvider.addAlgorithm("KeyGenerator.Threefish-256", sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("$KeyGen_512");
            configurableProvider.addAlgorithm("KeyGenerator.Threefish-512", sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append(a);
            sb6.append("$KeyGen_1024");
            configurableProvider.addAlgorithm("KeyGenerator.Threefish-1024", sb6.toString());
            StringBuilder sb7 = new StringBuilder();
            sb7.append(a);
            sb7.append("$AlgParams_256");
            configurableProvider.addAlgorithm("AlgorithmParameters.Threefish-256", sb7.toString());
            StringBuilder sb8 = new StringBuilder();
            sb8.append(a);
            sb8.append("$AlgParams_512");
            configurableProvider.addAlgorithm("AlgorithmParameters.Threefish-512", sb8.toString());
            StringBuilder sb9 = new StringBuilder();
            sb9.append(a);
            sb9.append("$AlgParams_1024");
            configurableProvider.addAlgorithm("AlgorithmParameters.Threefish-1024", sb9.toString());
        }
    }

    private Threefish() {
    }
}
