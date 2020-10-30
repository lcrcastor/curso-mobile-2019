package org.bouncycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.engines.CamelliaEngine;
import org.bouncycastle.crypto.engines.CamelliaWrapEngine;
import org.bouncycastle.crypto.engines.RFC3211WrapEngine;
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

public final class Camellia {

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] bArr = new byte[16];
            if (this.random == null) {
                this.random = new SecureRandom();
            }
            this.random.nextBytes(bArr);
            try {
                AlgorithmParameters instance = AlgorithmParameters.getInstance("Camellia", BouncyCastleProvider.PROVIDER_NAME);
                instance.init(new IvParameterSpec(bArr));
                return instance;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for Camellia parameter generation.");
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "Camellia IV";
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new CamelliaEngine()), 128);
        }
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipherProvider) new BlockCipherProvider() {
                public BlockCipher get() {
                    return new CamelliaEngine();
                }
            });
        }
    }

    public static class GMAC extends BaseMac {
        public GMAC() {
            super(new GMac(new GCMBlockCipher(new CamelliaEngine())));
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            this(256);
        }

        public KeyGen(int i) {
            super("Camellia", i, new CipherKeyGenerator());
        }
    }

    public static class KeyGen128 extends KeyGen {
        public KeyGen128() {
            super(128);
        }
    }

    public static class KeyGen192 extends KeyGen {
        public KeyGen192() {
            super(192);
        }
    }

    public static class KeyGen256 extends KeyGen {
        public KeyGen256() {
            super(256);
        }
    }

    public static class Mappings extends SymmetricAlgorithmProvider {
        private static final String a = Camellia.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters.CAMELLIA", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.AlgorithmParameters.");
            sb2.append(NTTObjectIdentifiers.id_camellia128_cbc);
            configurableProvider.addAlgorithm(sb2.toString(), "CAMELLIA");
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Alg.Alias.AlgorithmParameters.");
            sb3.append(NTTObjectIdentifiers.id_camellia192_cbc);
            configurableProvider.addAlgorithm(sb3.toString(), "CAMELLIA");
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Alg.Alias.AlgorithmParameters.");
            sb4.append(NTTObjectIdentifiers.id_camellia256_cbc);
            configurableProvider.addAlgorithm(sb4.toString(), "CAMELLIA");
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("$AlgParamGen");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.CAMELLIA", sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append("Alg.Alias.AlgorithmParameterGenerator.");
            sb6.append(NTTObjectIdentifiers.id_camellia128_cbc);
            configurableProvider.addAlgorithm(sb6.toString(), "CAMELLIA");
            StringBuilder sb7 = new StringBuilder();
            sb7.append("Alg.Alias.AlgorithmParameterGenerator.");
            sb7.append(NTTObjectIdentifiers.id_camellia192_cbc);
            configurableProvider.addAlgorithm(sb7.toString(), "CAMELLIA");
            StringBuilder sb8 = new StringBuilder();
            sb8.append("Alg.Alias.AlgorithmParameterGenerator.");
            sb8.append(NTTObjectIdentifiers.id_camellia256_cbc);
            configurableProvider.addAlgorithm(sb8.toString(), "CAMELLIA");
            StringBuilder sb9 = new StringBuilder();
            sb9.append(a);
            sb9.append("$ECB");
            configurableProvider.addAlgorithm("Cipher.CAMELLIA", sb9.toString());
            StringBuilder sb10 = new StringBuilder();
            sb10.append("Cipher.");
            sb10.append(NTTObjectIdentifiers.id_camellia128_cbc);
            String sb11 = sb10.toString();
            StringBuilder sb12 = new StringBuilder();
            sb12.append(a);
            sb12.append("$CBC");
            configurableProvider.addAlgorithm(sb11, sb12.toString());
            StringBuilder sb13 = new StringBuilder();
            sb13.append("Cipher.");
            sb13.append(NTTObjectIdentifiers.id_camellia192_cbc);
            String sb14 = sb13.toString();
            StringBuilder sb15 = new StringBuilder();
            sb15.append(a);
            sb15.append("$CBC");
            configurableProvider.addAlgorithm(sb14, sb15.toString());
            StringBuilder sb16 = new StringBuilder();
            sb16.append("Cipher.");
            sb16.append(NTTObjectIdentifiers.id_camellia256_cbc);
            String sb17 = sb16.toString();
            StringBuilder sb18 = new StringBuilder();
            sb18.append(a);
            sb18.append("$CBC");
            configurableProvider.addAlgorithm(sb17, sb18.toString());
            StringBuilder sb19 = new StringBuilder();
            sb19.append(a);
            sb19.append("$RFC3211Wrap");
            configurableProvider.addAlgorithm("Cipher.CAMELLIARFC3211WRAP", sb19.toString());
            StringBuilder sb20 = new StringBuilder();
            sb20.append(a);
            sb20.append("$Wrap");
            configurableProvider.addAlgorithm("Cipher.CAMELLIAWRAP", sb20.toString());
            StringBuilder sb21 = new StringBuilder();
            sb21.append("Alg.Alias.Cipher.");
            sb21.append(NTTObjectIdentifiers.id_camellia128_wrap);
            configurableProvider.addAlgorithm(sb21.toString(), "CAMELLIAWRAP");
            StringBuilder sb22 = new StringBuilder();
            sb22.append("Alg.Alias.Cipher.");
            sb22.append(NTTObjectIdentifiers.id_camellia192_wrap);
            configurableProvider.addAlgorithm(sb22.toString(), "CAMELLIAWRAP");
            StringBuilder sb23 = new StringBuilder();
            sb23.append("Alg.Alias.Cipher.");
            sb23.append(NTTObjectIdentifiers.id_camellia256_wrap);
            configurableProvider.addAlgorithm(sb23.toString(), "CAMELLIAWRAP");
            StringBuilder sb24 = new StringBuilder();
            sb24.append(a);
            sb24.append("$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator.CAMELLIA", sb24.toString());
            StringBuilder sb25 = new StringBuilder();
            sb25.append("KeyGenerator.");
            sb25.append(NTTObjectIdentifiers.id_camellia128_wrap);
            String sb26 = sb25.toString();
            StringBuilder sb27 = new StringBuilder();
            sb27.append(a);
            sb27.append("$KeyGen128");
            configurableProvider.addAlgorithm(sb26, sb27.toString());
            StringBuilder sb28 = new StringBuilder();
            sb28.append("KeyGenerator.");
            sb28.append(NTTObjectIdentifiers.id_camellia192_wrap);
            String sb29 = sb28.toString();
            StringBuilder sb30 = new StringBuilder();
            sb30.append(a);
            sb30.append("$KeyGen192");
            configurableProvider.addAlgorithm(sb29, sb30.toString());
            StringBuilder sb31 = new StringBuilder();
            sb31.append("KeyGenerator.");
            sb31.append(NTTObjectIdentifiers.id_camellia256_wrap);
            String sb32 = sb31.toString();
            StringBuilder sb33 = new StringBuilder();
            sb33.append(a);
            sb33.append("$KeyGen256");
            configurableProvider.addAlgorithm(sb32, sb33.toString());
            StringBuilder sb34 = new StringBuilder();
            sb34.append("KeyGenerator.");
            sb34.append(NTTObjectIdentifiers.id_camellia128_cbc);
            String sb35 = sb34.toString();
            StringBuilder sb36 = new StringBuilder();
            sb36.append(a);
            sb36.append("$KeyGen128");
            configurableProvider.addAlgorithm(sb35, sb36.toString());
            StringBuilder sb37 = new StringBuilder();
            sb37.append("KeyGenerator.");
            sb37.append(NTTObjectIdentifiers.id_camellia192_cbc);
            String sb38 = sb37.toString();
            StringBuilder sb39 = new StringBuilder();
            sb39.append(a);
            sb39.append("$KeyGen192");
            configurableProvider.addAlgorithm(sb38, sb39.toString());
            StringBuilder sb40 = new StringBuilder();
            sb40.append("KeyGenerator.");
            sb40.append(NTTObjectIdentifiers.id_camellia256_cbc);
            String sb41 = sb40.toString();
            StringBuilder sb42 = new StringBuilder();
            sb42.append(a);
            sb42.append("$KeyGen256");
            configurableProvider.addAlgorithm(sb41, sb42.toString());
            StringBuilder sb43 = new StringBuilder();
            sb43.append(a);
            sb43.append("$GMAC");
            String sb44 = sb43.toString();
            StringBuilder sb45 = new StringBuilder();
            sb45.append(a);
            sb45.append("$KeyGen");
            addGMacAlgorithm(configurableProvider, "CAMELLIA", sb44, sb45.toString());
            StringBuilder sb46 = new StringBuilder();
            sb46.append(a);
            sb46.append("$Poly1305");
            String sb47 = sb46.toString();
            StringBuilder sb48 = new StringBuilder();
            sb48.append(a);
            sb48.append("$Poly1305KeyGen");
            addPoly1305Algorithm(configurableProvider, "CAMELLIA", sb47, sb48.toString());
        }
    }

    public static class Poly1305 extends BaseMac {
        public Poly1305() {
            super(new org.bouncycastle.crypto.macs.Poly1305(new CamelliaEngine()));
        }
    }

    public static class Poly1305KeyGen extends BaseKeyGenerator {
        public Poly1305KeyGen() {
            super("Poly1305-Camellia", 256, new Poly1305KeyGenerator());
        }
    }

    public static class RFC3211Wrap extends BaseWrapCipher {
        public RFC3211Wrap() {
            super(new RFC3211WrapEngine(new CamelliaEngine()), 16);
        }
    }

    public static class Wrap extends BaseWrapCipher {
        public Wrap() {
            super(new CamelliaWrapEngine());
        }
    }

    private Camellia() {
    }
}
