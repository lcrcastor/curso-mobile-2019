package org.bouncycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.asn1.kisa.KISAObjectIdentifiers;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.engines.SEEDEngine;
import org.bouncycastle.crypto.engines.SEEDWrapEngine;
import org.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.bouncycastle.crypto.macs.GMac;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public final class SEED {

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] bArr = new byte[16];
            if (this.random == null) {
                this.random = new SecureRandom();
            }
            this.random.nextBytes(bArr);
            try {
                AlgorithmParameters instance = AlgorithmParameters.getInstance("SEED", BouncyCastleProvider.PROVIDER_NAME);
                instance.init(new IvParameterSpec(bArr));
                return instance;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for SEED parameter generation.");
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "SEED IV";
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new SEEDEngine()), 128);
        }
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipherProvider) new BlockCipherProvider() {
                public BlockCipher get() {
                    return new SEEDEngine();
                }
            });
        }
    }

    public static class GMAC extends BaseMac {
        public GMAC() {
            super(new GMac(new GCMBlockCipher(new SEEDEngine())));
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("SEED", 128, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends SymmetricAlgorithmProvider {
        private static final String a = SEED.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters.SEED", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.AlgorithmParameters.");
            sb2.append(KISAObjectIdentifiers.id_seedCBC);
            configurableProvider.addAlgorithm(sb2.toString(), "SEED");
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a);
            sb3.append("$AlgParamGen");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.SEED", sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Alg.Alias.AlgorithmParameterGenerator.");
            sb4.append(KISAObjectIdentifiers.id_seedCBC);
            configurableProvider.addAlgorithm(sb4.toString(), "SEED");
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("$ECB");
            configurableProvider.addAlgorithm("Cipher.SEED", sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append("Cipher.");
            sb6.append(KISAObjectIdentifiers.id_seedCBC);
            String sb7 = sb6.toString();
            StringBuilder sb8 = new StringBuilder();
            sb8.append(a);
            sb8.append("$CBC");
            configurableProvider.addAlgorithm(sb7, sb8.toString());
            StringBuilder sb9 = new StringBuilder();
            sb9.append(a);
            sb9.append("$Wrap");
            configurableProvider.addAlgorithm("Cipher.SEEDWRAP", sb9.toString());
            StringBuilder sb10 = new StringBuilder();
            sb10.append("Alg.Alias.Cipher.");
            sb10.append(KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap);
            configurableProvider.addAlgorithm(sb10.toString(), "SEEDWRAP");
            StringBuilder sb11 = new StringBuilder();
            sb11.append(a);
            sb11.append("$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator.SEED", sb11.toString());
            StringBuilder sb12 = new StringBuilder();
            sb12.append("KeyGenerator.");
            sb12.append(KISAObjectIdentifiers.id_seedCBC);
            String sb13 = sb12.toString();
            StringBuilder sb14 = new StringBuilder();
            sb14.append(a);
            sb14.append("$KeyGen");
            configurableProvider.addAlgorithm(sb13, sb14.toString());
            StringBuilder sb15 = new StringBuilder();
            sb15.append("KeyGenerator.");
            sb15.append(KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap);
            String sb16 = sb15.toString();
            StringBuilder sb17 = new StringBuilder();
            sb17.append(a);
            sb17.append("$KeyGen");
            configurableProvider.addAlgorithm(sb16, sb17.toString());
            StringBuilder sb18 = new StringBuilder();
            sb18.append(a);
            sb18.append("$GMAC");
            String sb19 = sb18.toString();
            StringBuilder sb20 = new StringBuilder();
            sb20.append(a);
            sb20.append("$KeyGen");
            addGMacAlgorithm(configurableProvider, "SEED", sb19, sb20.toString());
            StringBuilder sb21 = new StringBuilder();
            sb21.append(a);
            sb21.append("$Poly1305");
            String sb22 = sb21.toString();
            StringBuilder sb23 = new StringBuilder();
            sb23.append(a);
            sb23.append("$Poly1305KeyGen");
            addPoly1305Algorithm(configurableProvider, "SEED", sb22, sb23.toString());
        }
    }

    public static class Poly1305 extends BaseMac {
        public Poly1305() {
            super(new org.bouncycastle.crypto.macs.Poly1305(new SEEDEngine()));
        }
    }

    public static class Poly1305KeyGen extends BaseKeyGenerator {
        public Poly1305KeyGen() {
            super("Poly1305-SEED", 256, new Poly1305KeyGenerator());
        }
    }

    public static class Wrap extends BaseWrapCipher {
        public Wrap() {
            super(new SEEDWrapEngine());
        }
    }

    private SEED() {
    }
}
