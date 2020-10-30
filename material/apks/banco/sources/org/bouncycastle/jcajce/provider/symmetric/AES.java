package org.bouncycastle.jcajce.provider.symmetric;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import org.bouncycastle.asn1.cms.GCMParameters;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.engines.AESWrapEngine;
import org.bouncycastle.crypto.engines.RFC3211WrapEngine;
import org.bouncycastle.crypto.engines.RFC5649WrapEngine;
import org.bouncycastle.crypto.generators.Poly1305KeyGenerator;
import org.bouncycastle.crypto.macs.CMac;
import org.bouncycastle.crypto.macs.GMac;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Integers;

public final class AES {
    /* access modifiers changed from: private */
    public static final Class a = a("javax.crypto.spec.GCMParameterSpec");

    public static class AESCMAC extends BaseMac {
        public AESCMAC() {
            super(new CMac(new AESFastEngine()));
        }
    }

    public static class AESGMAC extends BaseMac {
        public AESGMAC() {
            super(new GMac(new GCMBlockCipher(new AESFastEngine())));
        }
    }

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] bArr = new byte[16];
            if (this.random == null) {
                this.random = new SecureRandom();
            }
            this.random.nextBytes(bArr);
            try {
                AlgorithmParameters instance = AlgorithmParameters.getInstance("AES", BouncyCastleProvider.PROVIDER_NAME);
                instance.init(new IvParameterSpec(bArr));
                return instance;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for AES parameter generation.");
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "AES IV";
        }
    }

    public static class AlgParamsGCM extends BaseAlgorithmParameters {
        private GCMParameters a;

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded() {
            return this.a.getEncoded();
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded(String str) {
            if (isASN1FormatString(str)) {
                return this.a.getEncoded();
            }
            throw new IOException("unknown format specified");
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
            if (AES.a != null) {
                try {
                    this.a = new GCMParameters((byte[]) AES.a.getDeclaredMethod("getIV", new Class[0]).invoke(algorithmParameterSpec, new Object[0]), ((Integer) AES.a.getDeclaredMethod("getTLen", new Class[0]).invoke(algorithmParameterSpec, new Object[0])).intValue());
                } catch (Exception unused) {
                    throw new InvalidParameterSpecException("Cannot process GCMParameterSpec.");
                }
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] bArr) {
            this.a = GCMParameters.getInstance(bArr);
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] bArr, String str) {
            if (!isASN1FormatString(str)) {
                throw new IOException("unknown format specified");
            }
            this.a = GCMParameters.getInstance(bArr);
        }

        /* access modifiers changed from: protected */
        public String engineToString() {
            return GoogleCloudMessaging.INSTANCE_ID_SCOPE;
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
            if (AES.a != null) {
                try {
                    return (AlgorithmParameterSpec) AES.a.getConstructor(new Class[]{Integer.TYPE, byte[].class}).newInstance(new Object[]{Integers.valueOf(this.a.getIcvLen()), this.a.getNonce()});
                } catch (NoSuchMethodException unused) {
                    throw new InvalidParameterSpecException("no constructor found!");
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("construction failed: ");
                    sb.append(e.getMessage());
                    throw new InvalidParameterSpecException(sb.toString());
                }
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("unknown parameter spec: ");
                sb2.append(cls.getName());
                throw new InvalidParameterSpecException(sb2.toString());
            }
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new AESFastEngine()), 128);
        }
    }

    public static class CFB extends BaseBlockCipher {
        public CFB() {
            super(new BufferedBlockCipher(new CFBBlockCipher(new AESFastEngine(), 128)), 128);
        }
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipherProvider) new BlockCipherProvider() {
                public BlockCipher get() {
                    return new AESFastEngine();
                }
            });
        }
    }

    public static class GCM extends BaseBlockCipher {
        public GCM() {
            super((AEADBlockCipher) new GCMBlockCipher(new AESFastEngine()));
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            this(192);
        }

        public KeyGen(int i) {
            super("AES", i, new CipherKeyGenerator());
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
        private static final String a = AES.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters.AES", sb.toString());
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.2", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.22", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.42", "AES");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.AlgorithmParameters.");
            sb2.append(NISTObjectIdentifiers.id_aes128_CBC);
            configurableProvider.addAlgorithm(sb2.toString(), "AES");
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Alg.Alias.AlgorithmParameters.");
            sb3.append(NISTObjectIdentifiers.id_aes192_CBC);
            configurableProvider.addAlgorithm(sb3.toString(), "AES");
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Alg.Alias.AlgorithmParameters.");
            sb4.append(NISTObjectIdentifiers.id_aes256_CBC);
            configurableProvider.addAlgorithm(sb4.toString(), "AES");
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("$AlgParamsGCM");
            configurableProvider.addAlgorithm("AlgorithmParameters.GCM", sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append("Alg.Alias.AlgorithmParameters.");
            sb6.append(NISTObjectIdentifiers.id_aes128_GCM);
            configurableProvider.addAlgorithm(sb6.toString(), GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            StringBuilder sb7 = new StringBuilder();
            sb7.append("Alg.Alias.AlgorithmParameters.");
            sb7.append(NISTObjectIdentifiers.id_aes192_GCM);
            configurableProvider.addAlgorithm(sb7.toString(), GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            StringBuilder sb8 = new StringBuilder();
            sb8.append("Alg.Alias.AlgorithmParameters.");
            sb8.append(NISTObjectIdentifiers.id_aes256_GCM);
            configurableProvider.addAlgorithm(sb8.toString(), GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            StringBuilder sb9 = new StringBuilder();
            sb9.append(a);
            sb9.append("$AlgParamGen");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.AES", sb9.toString());
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator.2.16.840.1.101.3.4.2", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator.2.16.840.1.101.3.4.22", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator.2.16.840.1.101.3.4.42", "AES");
            StringBuilder sb10 = new StringBuilder();
            sb10.append("Alg.Alias.AlgorithmParameterGenerator.");
            sb10.append(NISTObjectIdentifiers.id_aes128_CBC);
            configurableProvider.addAlgorithm(sb10.toString(), "AES");
            StringBuilder sb11 = new StringBuilder();
            sb11.append("Alg.Alias.AlgorithmParameterGenerator.");
            sb11.append(NISTObjectIdentifiers.id_aes192_CBC);
            configurableProvider.addAlgorithm(sb11.toString(), "AES");
            StringBuilder sb12 = new StringBuilder();
            sb12.append("Alg.Alias.AlgorithmParameterGenerator.");
            sb12.append(NISTObjectIdentifiers.id_aes256_CBC);
            configurableProvider.addAlgorithm(sb12.toString(), "AES");
            StringBuilder sb13 = new StringBuilder();
            sb13.append(a);
            sb13.append("$ECB");
            configurableProvider.addAlgorithm("Cipher.AES", sb13.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.2.16.840.1.101.3.4.2", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.2.16.840.1.101.3.4.22", "AES");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.2.16.840.1.101.3.4.42", "AES");
            StringBuilder sb14 = new StringBuilder();
            sb14.append("Cipher.");
            sb14.append(NISTObjectIdentifiers.id_aes128_ECB);
            String sb15 = sb14.toString();
            StringBuilder sb16 = new StringBuilder();
            sb16.append(a);
            sb16.append("$ECB");
            configurableProvider.addAlgorithm(sb15, sb16.toString());
            StringBuilder sb17 = new StringBuilder();
            sb17.append("Cipher.");
            sb17.append(NISTObjectIdentifiers.id_aes192_ECB);
            String sb18 = sb17.toString();
            StringBuilder sb19 = new StringBuilder();
            sb19.append(a);
            sb19.append("$ECB");
            configurableProvider.addAlgorithm(sb18, sb19.toString());
            StringBuilder sb20 = new StringBuilder();
            sb20.append("Cipher.");
            sb20.append(NISTObjectIdentifiers.id_aes256_ECB);
            String sb21 = sb20.toString();
            StringBuilder sb22 = new StringBuilder();
            sb22.append(a);
            sb22.append("$ECB");
            configurableProvider.addAlgorithm(sb21, sb22.toString());
            StringBuilder sb23 = new StringBuilder();
            sb23.append("Cipher.");
            sb23.append(NISTObjectIdentifiers.id_aes128_CBC);
            String sb24 = sb23.toString();
            StringBuilder sb25 = new StringBuilder();
            sb25.append(a);
            sb25.append("$CBC");
            configurableProvider.addAlgorithm(sb24, sb25.toString());
            StringBuilder sb26 = new StringBuilder();
            sb26.append("Cipher.");
            sb26.append(NISTObjectIdentifiers.id_aes192_CBC);
            String sb27 = sb26.toString();
            StringBuilder sb28 = new StringBuilder();
            sb28.append(a);
            sb28.append("$CBC");
            configurableProvider.addAlgorithm(sb27, sb28.toString());
            StringBuilder sb29 = new StringBuilder();
            sb29.append("Cipher.");
            sb29.append(NISTObjectIdentifiers.id_aes256_CBC);
            String sb30 = sb29.toString();
            StringBuilder sb31 = new StringBuilder();
            sb31.append(a);
            sb31.append("$CBC");
            configurableProvider.addAlgorithm(sb30, sb31.toString());
            StringBuilder sb32 = new StringBuilder();
            sb32.append("Cipher.");
            sb32.append(NISTObjectIdentifiers.id_aes128_OFB);
            String sb33 = sb32.toString();
            StringBuilder sb34 = new StringBuilder();
            sb34.append(a);
            sb34.append("$OFB");
            configurableProvider.addAlgorithm(sb33, sb34.toString());
            StringBuilder sb35 = new StringBuilder();
            sb35.append("Cipher.");
            sb35.append(NISTObjectIdentifiers.id_aes192_OFB);
            String sb36 = sb35.toString();
            StringBuilder sb37 = new StringBuilder();
            sb37.append(a);
            sb37.append("$OFB");
            configurableProvider.addAlgorithm(sb36, sb37.toString());
            StringBuilder sb38 = new StringBuilder();
            sb38.append("Cipher.");
            sb38.append(NISTObjectIdentifiers.id_aes256_OFB);
            String sb39 = sb38.toString();
            StringBuilder sb40 = new StringBuilder();
            sb40.append(a);
            sb40.append("$OFB");
            configurableProvider.addAlgorithm(sb39, sb40.toString());
            StringBuilder sb41 = new StringBuilder();
            sb41.append("Cipher.");
            sb41.append(NISTObjectIdentifiers.id_aes128_CFB);
            String sb42 = sb41.toString();
            StringBuilder sb43 = new StringBuilder();
            sb43.append(a);
            sb43.append("$CFB");
            configurableProvider.addAlgorithm(sb42, sb43.toString());
            StringBuilder sb44 = new StringBuilder();
            sb44.append("Cipher.");
            sb44.append(NISTObjectIdentifiers.id_aes192_CFB);
            String sb45 = sb44.toString();
            StringBuilder sb46 = new StringBuilder();
            sb46.append(a);
            sb46.append("$CFB");
            configurableProvider.addAlgorithm(sb45, sb46.toString());
            StringBuilder sb47 = new StringBuilder();
            sb47.append("Cipher.");
            sb47.append(NISTObjectIdentifiers.id_aes256_CFB);
            String sb48 = sb47.toString();
            StringBuilder sb49 = new StringBuilder();
            sb49.append(a);
            sb49.append("$CFB");
            configurableProvider.addAlgorithm(sb48, sb49.toString());
            StringBuilder sb50 = new StringBuilder();
            sb50.append(a);
            sb50.append("$Wrap");
            configurableProvider.addAlgorithm("Cipher.AESWRAP", sb50.toString());
            StringBuilder sb51 = new StringBuilder();
            sb51.append("Alg.Alias.Cipher.");
            sb51.append(NISTObjectIdentifiers.id_aes128_wrap);
            configurableProvider.addAlgorithm(sb51.toString(), "AESWRAP");
            StringBuilder sb52 = new StringBuilder();
            sb52.append("Alg.Alias.Cipher.");
            sb52.append(NISTObjectIdentifiers.id_aes192_wrap);
            configurableProvider.addAlgorithm(sb52.toString(), "AESWRAP");
            StringBuilder sb53 = new StringBuilder();
            sb53.append("Alg.Alias.Cipher.");
            sb53.append(NISTObjectIdentifiers.id_aes256_wrap);
            configurableProvider.addAlgorithm(sb53.toString(), "AESWRAP");
            StringBuilder sb54 = new StringBuilder();
            sb54.append(a);
            sb54.append("$RFC3211Wrap");
            configurableProvider.addAlgorithm("Cipher.AESRFC3211WRAP", sb54.toString());
            StringBuilder sb55 = new StringBuilder();
            sb55.append(a);
            sb55.append("$RFC5649Wrap");
            configurableProvider.addAlgorithm("Cipher.AESRFC5649WRAP", sb55.toString());
            StringBuilder sb56 = new StringBuilder();
            sb56.append(a);
            sb56.append("$GCM");
            configurableProvider.addAlgorithm("Cipher.GCM", sb56.toString());
            StringBuilder sb57 = new StringBuilder();
            sb57.append("Alg.Alias.Cipher.");
            sb57.append(NISTObjectIdentifiers.id_aes128_GCM);
            configurableProvider.addAlgorithm(sb57.toString(), GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            StringBuilder sb58 = new StringBuilder();
            sb58.append("Alg.Alias.Cipher.");
            sb58.append(NISTObjectIdentifiers.id_aes192_GCM);
            configurableProvider.addAlgorithm(sb58.toString(), GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            StringBuilder sb59 = new StringBuilder();
            sb59.append("Alg.Alias.Cipher.");
            sb59.append(NISTObjectIdentifiers.id_aes256_GCM);
            configurableProvider.addAlgorithm(sb59.toString(), GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            StringBuilder sb60 = new StringBuilder();
            sb60.append(a);
            sb60.append("$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator.AES", sb60.toString());
            StringBuilder sb61 = new StringBuilder();
            sb61.append(a);
            sb61.append("$KeyGen128");
            configurableProvider.addAlgorithm("KeyGenerator.2.16.840.1.101.3.4.2", sb61.toString());
            StringBuilder sb62 = new StringBuilder();
            sb62.append(a);
            sb62.append("$KeyGen192");
            configurableProvider.addAlgorithm("KeyGenerator.2.16.840.1.101.3.4.22", sb62.toString());
            StringBuilder sb63 = new StringBuilder();
            sb63.append(a);
            sb63.append("$KeyGen256");
            configurableProvider.addAlgorithm("KeyGenerator.2.16.840.1.101.3.4.42", sb63.toString());
            StringBuilder sb64 = new StringBuilder();
            sb64.append("KeyGenerator.");
            sb64.append(NISTObjectIdentifiers.id_aes128_ECB);
            String sb65 = sb64.toString();
            StringBuilder sb66 = new StringBuilder();
            sb66.append(a);
            sb66.append("$KeyGen128");
            configurableProvider.addAlgorithm(sb65, sb66.toString());
            StringBuilder sb67 = new StringBuilder();
            sb67.append("KeyGenerator.");
            sb67.append(NISTObjectIdentifiers.id_aes128_CBC);
            String sb68 = sb67.toString();
            StringBuilder sb69 = new StringBuilder();
            sb69.append(a);
            sb69.append("$KeyGen128");
            configurableProvider.addAlgorithm(sb68, sb69.toString());
            StringBuilder sb70 = new StringBuilder();
            sb70.append("KeyGenerator.");
            sb70.append(NISTObjectIdentifiers.id_aes128_OFB);
            String sb71 = sb70.toString();
            StringBuilder sb72 = new StringBuilder();
            sb72.append(a);
            sb72.append("$KeyGen128");
            configurableProvider.addAlgorithm(sb71, sb72.toString());
            StringBuilder sb73 = new StringBuilder();
            sb73.append("KeyGenerator.");
            sb73.append(NISTObjectIdentifiers.id_aes128_CFB);
            String sb74 = sb73.toString();
            StringBuilder sb75 = new StringBuilder();
            sb75.append(a);
            sb75.append("$KeyGen128");
            configurableProvider.addAlgorithm(sb74, sb75.toString());
            StringBuilder sb76 = new StringBuilder();
            sb76.append("KeyGenerator.");
            sb76.append(NISTObjectIdentifiers.id_aes192_ECB);
            String sb77 = sb76.toString();
            StringBuilder sb78 = new StringBuilder();
            sb78.append(a);
            sb78.append("$KeyGen192");
            configurableProvider.addAlgorithm(sb77, sb78.toString());
            StringBuilder sb79 = new StringBuilder();
            sb79.append("KeyGenerator.");
            sb79.append(NISTObjectIdentifiers.id_aes192_CBC);
            String sb80 = sb79.toString();
            StringBuilder sb81 = new StringBuilder();
            sb81.append(a);
            sb81.append("$KeyGen192");
            configurableProvider.addAlgorithm(sb80, sb81.toString());
            StringBuilder sb82 = new StringBuilder();
            sb82.append("KeyGenerator.");
            sb82.append(NISTObjectIdentifiers.id_aes192_OFB);
            String sb83 = sb82.toString();
            StringBuilder sb84 = new StringBuilder();
            sb84.append(a);
            sb84.append("$KeyGen192");
            configurableProvider.addAlgorithm(sb83, sb84.toString());
            StringBuilder sb85 = new StringBuilder();
            sb85.append("KeyGenerator.");
            sb85.append(NISTObjectIdentifiers.id_aes192_CFB);
            String sb86 = sb85.toString();
            StringBuilder sb87 = new StringBuilder();
            sb87.append(a);
            sb87.append("$KeyGen192");
            configurableProvider.addAlgorithm(sb86, sb87.toString());
            StringBuilder sb88 = new StringBuilder();
            sb88.append("KeyGenerator.");
            sb88.append(NISTObjectIdentifiers.id_aes256_ECB);
            String sb89 = sb88.toString();
            StringBuilder sb90 = new StringBuilder();
            sb90.append(a);
            sb90.append("$KeyGen256");
            configurableProvider.addAlgorithm(sb89, sb90.toString());
            StringBuilder sb91 = new StringBuilder();
            sb91.append("KeyGenerator.");
            sb91.append(NISTObjectIdentifiers.id_aes256_CBC);
            String sb92 = sb91.toString();
            StringBuilder sb93 = new StringBuilder();
            sb93.append(a);
            sb93.append("$KeyGen256");
            configurableProvider.addAlgorithm(sb92, sb93.toString());
            StringBuilder sb94 = new StringBuilder();
            sb94.append("KeyGenerator.");
            sb94.append(NISTObjectIdentifiers.id_aes256_OFB);
            String sb95 = sb94.toString();
            StringBuilder sb96 = new StringBuilder();
            sb96.append(a);
            sb96.append("$KeyGen256");
            configurableProvider.addAlgorithm(sb95, sb96.toString());
            StringBuilder sb97 = new StringBuilder();
            sb97.append("KeyGenerator.");
            sb97.append(NISTObjectIdentifiers.id_aes256_CFB);
            String sb98 = sb97.toString();
            StringBuilder sb99 = new StringBuilder();
            sb99.append(a);
            sb99.append("$KeyGen256");
            configurableProvider.addAlgorithm(sb98, sb99.toString());
            StringBuilder sb100 = new StringBuilder();
            sb100.append(a);
            sb100.append("$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator.AESWRAP", sb100.toString());
            StringBuilder sb101 = new StringBuilder();
            sb101.append("KeyGenerator.");
            sb101.append(NISTObjectIdentifiers.id_aes128_wrap);
            String sb102 = sb101.toString();
            StringBuilder sb103 = new StringBuilder();
            sb103.append(a);
            sb103.append("$KeyGen128");
            configurableProvider.addAlgorithm(sb102, sb103.toString());
            StringBuilder sb104 = new StringBuilder();
            sb104.append("KeyGenerator.");
            sb104.append(NISTObjectIdentifiers.id_aes192_wrap);
            String sb105 = sb104.toString();
            StringBuilder sb106 = new StringBuilder();
            sb106.append(a);
            sb106.append("$KeyGen192");
            configurableProvider.addAlgorithm(sb105, sb106.toString());
            StringBuilder sb107 = new StringBuilder();
            sb107.append("KeyGenerator.");
            sb107.append(NISTObjectIdentifiers.id_aes256_wrap);
            String sb108 = sb107.toString();
            StringBuilder sb109 = new StringBuilder();
            sb109.append(a);
            sb109.append("$KeyGen256");
            configurableProvider.addAlgorithm(sb108, sb109.toString());
            StringBuilder sb110 = new StringBuilder();
            sb110.append(a);
            sb110.append("$AESCMAC");
            configurableProvider.addAlgorithm("Mac.AESCMAC", sb110.toString());
            StringBuilder sb111 = new StringBuilder();
            sb111.append("Alg.Alias.Cipher.");
            sb111.append(BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes128_cbc.getId());
            configurableProvider.addAlgorithm(sb111.toString(), "PBEWITHSHAAND128BITAES-CBC-BC");
            StringBuilder sb112 = new StringBuilder();
            sb112.append("Alg.Alias.Cipher.");
            sb112.append(BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes192_cbc.getId());
            configurableProvider.addAlgorithm(sb112.toString(), "PBEWITHSHAAND192BITAES-CBC-BC");
            StringBuilder sb113 = new StringBuilder();
            sb113.append("Alg.Alias.Cipher.");
            sb113.append(BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes256_cbc.getId());
            configurableProvider.addAlgorithm(sb113.toString(), "PBEWITHSHAAND256BITAES-CBC-BC");
            StringBuilder sb114 = new StringBuilder();
            sb114.append("Alg.Alias.Cipher.");
            sb114.append(BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes128_cbc.getId());
            configurableProvider.addAlgorithm(sb114.toString(), "PBEWITHSHA256AND128BITAES-CBC-BC");
            StringBuilder sb115 = new StringBuilder();
            sb115.append("Alg.Alias.Cipher.");
            sb115.append(BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes192_cbc.getId());
            configurableProvider.addAlgorithm(sb115.toString(), "PBEWITHSHA256AND192BITAES-CBC-BC");
            StringBuilder sb116 = new StringBuilder();
            sb116.append("Alg.Alias.Cipher.");
            sb116.append(BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes256_cbc.getId());
            configurableProvider.addAlgorithm(sb116.toString(), "PBEWITHSHA256AND256BITAES-CBC-BC");
            StringBuilder sb117 = new StringBuilder();
            sb117.append(a);
            sb117.append("$PBEWithAESCBC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND128BITAES-CBC-BC", sb117.toString());
            StringBuilder sb118 = new StringBuilder();
            sb118.append(a);
            sb118.append("$PBEWithAESCBC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND192BITAES-CBC-BC", sb118.toString());
            StringBuilder sb119 = new StringBuilder();
            sb119.append(a);
            sb119.append("$PBEWithAESCBC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND256BITAES-CBC-BC", sb119.toString());
            StringBuilder sb120 = new StringBuilder();
            sb120.append(a);
            sb120.append("$PBEWithAESCBC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHA256AND128BITAES-CBC-BC", sb120.toString());
            StringBuilder sb121 = new StringBuilder();
            sb121.append(a);
            sb121.append("$PBEWithAESCBC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHA256AND192BITAES-CBC-BC", sb121.toString());
            StringBuilder sb122 = new StringBuilder();
            sb122.append(a);
            sb122.append("$PBEWithAESCBC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHA256AND256BITAES-CBC-BC", sb122.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND128BITAES-CBC-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND192BITAES-CBC-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA-256AND256BITAES-CBC-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
            StringBuilder sb123 = new StringBuilder();
            sb123.append(a);
            sb123.append("$PBEWithAESCBC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHMD5AND128BITAES-CBC-OPENSSL", sb123.toString());
            StringBuilder sb124 = new StringBuilder();
            sb124.append(a);
            sb124.append("$PBEWithAESCBC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHMD5AND192BITAES-CBC-OPENSSL", sb124.toString());
            StringBuilder sb125 = new StringBuilder();
            sb125.append(a);
            sb125.append("$PBEWithAESCBC");
            configurableProvider.addAlgorithm("Cipher.PBEWITHMD5AND256BITAES-CBC-OPENSSL", sb125.toString());
            StringBuilder sb126 = new StringBuilder();
            sb126.append(a);
            sb126.append("$PBEWithMD5And128BitAESCBCOpenSSL");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHMD5AND128BITAES-CBC-OPENSSL", sb126.toString());
            StringBuilder sb127 = new StringBuilder();
            sb127.append(a);
            sb127.append("$PBEWithMD5And192BitAESCBCOpenSSL");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHMD5AND192BITAES-CBC-OPENSSL", sb127.toString());
            StringBuilder sb128 = new StringBuilder();
            sb128.append(a);
            sb128.append("$PBEWithMD5And256BitAESCBCOpenSSL");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHMD5AND256BITAES-CBC-OPENSSL", sb128.toString());
            StringBuilder sb129 = new StringBuilder();
            sb129.append(a);
            sb129.append("$PBEWithSHAAnd128BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND128BITAES-CBC-BC", sb129.toString());
            StringBuilder sb130 = new StringBuilder();
            sb130.append(a);
            sb130.append("$PBEWithSHAAnd192BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND192BITAES-CBC-BC", sb130.toString());
            StringBuilder sb131 = new StringBuilder();
            sb131.append(a);
            sb131.append("$PBEWithSHAAnd256BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND256BITAES-CBC-BC", sb131.toString());
            StringBuilder sb132 = new StringBuilder();
            sb132.append(a);
            sb132.append("$PBEWithSHA256And128BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHA256AND128BITAES-CBC-BC", sb132.toString());
            StringBuilder sb133 = new StringBuilder();
            sb133.append(a);
            sb133.append("$PBEWithSHA256And192BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHA256AND192BITAES-CBC-BC", sb133.toString());
            StringBuilder sb134 = new StringBuilder();
            sb134.append(a);
            sb134.append("$PBEWithSHA256And256BitAESBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHA256AND256BITAES-CBC-BC", sb134.toString());
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND128BITAES-CBC-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND192BITAES-CBC-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND256BITAES-CBC-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
            StringBuilder sb135 = new StringBuilder();
            sb135.append("Alg.Alias.SecretKeyFactory.");
            sb135.append(BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes128_cbc.getId());
            configurableProvider.addAlgorithm(sb135.toString(), "PBEWITHSHAAND128BITAES-CBC-BC");
            StringBuilder sb136 = new StringBuilder();
            sb136.append("Alg.Alias.SecretKeyFactory.");
            sb136.append(BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes192_cbc.getId());
            configurableProvider.addAlgorithm(sb136.toString(), "PBEWITHSHAAND192BITAES-CBC-BC");
            StringBuilder sb137 = new StringBuilder();
            sb137.append("Alg.Alias.SecretKeyFactory.");
            sb137.append(BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes256_cbc.getId());
            configurableProvider.addAlgorithm(sb137.toString(), "PBEWITHSHAAND256BITAES-CBC-BC");
            StringBuilder sb138 = new StringBuilder();
            sb138.append("Alg.Alias.SecretKeyFactory.");
            sb138.append(BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes128_cbc.getId());
            configurableProvider.addAlgorithm(sb138.toString(), "PBEWITHSHA256AND128BITAES-CBC-BC");
            StringBuilder sb139 = new StringBuilder();
            sb139.append("Alg.Alias.SecretKeyFactory.");
            sb139.append(BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes192_cbc.getId());
            configurableProvider.addAlgorithm(sb139.toString(), "PBEWITHSHA256AND192BITAES-CBC-BC");
            StringBuilder sb140 = new StringBuilder();
            sb140.append("Alg.Alias.SecretKeyFactory.");
            sb140.append(BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes256_cbc.getId());
            configurableProvider.addAlgorithm(sb140.toString(), "PBEWITHSHA256AND256BITAES-CBC-BC");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND128BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND192BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND256BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND128BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND192BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND256BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND128BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND192BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND256BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND128BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND192BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND256BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND128BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND192BITAES-CBC-BC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND256BITAES-CBC-BC", "PKCS12PBE");
            StringBuilder sb141 = new StringBuilder();
            sb141.append("Alg.Alias.AlgorithmParameters.");
            sb141.append(BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes128_cbc.getId());
            configurableProvider.addAlgorithm(sb141.toString(), "PKCS12PBE");
            StringBuilder sb142 = new StringBuilder();
            sb142.append("Alg.Alias.AlgorithmParameters.");
            sb142.append(BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes192_cbc.getId());
            configurableProvider.addAlgorithm(sb142.toString(), "PKCS12PBE");
            StringBuilder sb143 = new StringBuilder();
            sb143.append("Alg.Alias.AlgorithmParameters.");
            sb143.append(BCObjectIdentifiers.bc_pbe_sha1_pkcs12_aes256_cbc.getId());
            configurableProvider.addAlgorithm(sb143.toString(), "PKCS12PBE");
            StringBuilder sb144 = new StringBuilder();
            sb144.append("Alg.Alias.AlgorithmParameters.");
            sb144.append(BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes128_cbc.getId());
            configurableProvider.addAlgorithm(sb144.toString(), "PKCS12PBE");
            StringBuilder sb145 = new StringBuilder();
            sb145.append("Alg.Alias.AlgorithmParameters.");
            sb145.append(BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes192_cbc.getId());
            configurableProvider.addAlgorithm(sb145.toString(), "PKCS12PBE");
            StringBuilder sb146 = new StringBuilder();
            sb146.append("Alg.Alias.AlgorithmParameters.");
            sb146.append(BCObjectIdentifiers.bc_pbe_sha256_pkcs12_aes256_cbc.getId());
            configurableProvider.addAlgorithm(sb146.toString(), "PKCS12PBE");
            StringBuilder sb147 = new StringBuilder();
            sb147.append(a);
            sb147.append("$AESGMAC");
            String sb148 = sb147.toString();
            StringBuilder sb149 = new StringBuilder();
            sb149.append(a);
            sb149.append("$KeyGen128");
            addGMacAlgorithm(configurableProvider, "AES", sb148, sb149.toString());
            StringBuilder sb150 = new StringBuilder();
            sb150.append(a);
            sb150.append("$Poly1305");
            String sb151 = sb150.toString();
            StringBuilder sb152 = new StringBuilder();
            sb152.append(a);
            sb152.append("$Poly1305KeyGen");
            addPoly1305Algorithm(configurableProvider, "AES", sb151, sb152.toString());
        }
    }

    public static class OFB extends BaseBlockCipher {
        public OFB() {
            super(new BufferedBlockCipher(new OFBBlockCipher(new AESFastEngine(), 128)), 128);
        }
    }

    public static class PBEWithAESCBC extends BaseBlockCipher {
        public PBEWithAESCBC() {
            super((BlockCipher) new CBCBlockCipher(new AESFastEngine()));
        }
    }

    public static class PBEWithMD5And128BitAESCBCOpenSSL extends PBESecretKeyFactory {
        public PBEWithMD5And128BitAESCBCOpenSSL() {
            super("PBEWithMD5And128BitAES-CBC-OpenSSL", null, true, 3, 0, 128, 128);
        }
    }

    public static class PBEWithMD5And192BitAESCBCOpenSSL extends PBESecretKeyFactory {
        public PBEWithMD5And192BitAESCBCOpenSSL() {
            super("PBEWithMD5And192BitAES-CBC-OpenSSL", null, true, 3, 0, 192, 128);
        }
    }

    public static class PBEWithMD5And256BitAESCBCOpenSSL extends PBESecretKeyFactory {
        public PBEWithMD5And256BitAESCBCOpenSSL() {
            super("PBEWithMD5And256BitAES-CBC-OpenSSL", null, true, 3, 0, 256, 128);
        }
    }

    public static class PBEWithSHA256And128BitAESBC extends PBESecretKeyFactory {
        public PBEWithSHA256And128BitAESBC() {
            super("PBEWithSHA256And128BitAES-CBC-BC", null, true, 2, 4, 128, 128);
        }
    }

    public static class PBEWithSHA256And192BitAESBC extends PBESecretKeyFactory {
        public PBEWithSHA256And192BitAESBC() {
            super("PBEWithSHA256And192BitAES-CBC-BC", null, true, 2, 4, 192, 128);
        }
    }

    public static class PBEWithSHA256And256BitAESBC extends PBESecretKeyFactory {
        public PBEWithSHA256And256BitAESBC() {
            super("PBEWithSHA256And256BitAES-CBC-BC", null, true, 2, 4, 256, 128);
        }
    }

    public static class PBEWithSHAAnd128BitAESBC extends PBESecretKeyFactory {
        public PBEWithSHAAnd128BitAESBC() {
            super("PBEWithSHA1And128BitAES-CBC-BC", null, true, 2, 1, 128, 128);
        }
    }

    public static class PBEWithSHAAnd192BitAESBC extends PBESecretKeyFactory {
        public PBEWithSHAAnd192BitAESBC() {
            super("PBEWithSHA1And192BitAES-CBC-BC", null, true, 2, 1, 192, 128);
        }
    }

    public static class PBEWithSHAAnd256BitAESBC extends PBESecretKeyFactory {
        public PBEWithSHAAnd256BitAESBC() {
            super("PBEWithSHA1And256BitAES-CBC-BC", null, true, 2, 1, 256, 128);
        }
    }

    public static class Poly1305 extends BaseMac {
        public Poly1305() {
            super(new org.bouncycastle.crypto.macs.Poly1305(new AESFastEngine()));
        }
    }

    public static class Poly1305KeyGen extends BaseKeyGenerator {
        public Poly1305KeyGen() {
            super("Poly1305-AES", 256, new Poly1305KeyGenerator());
        }
    }

    public static class RFC3211Wrap extends BaseWrapCipher {
        public RFC3211Wrap() {
            super(new RFC3211WrapEngine(new AESFastEngine()), 16);
        }
    }

    public static class RFC5649Wrap extends BaseWrapCipher {
        public RFC5649Wrap() {
            super(new RFC5649WrapEngine(new AESFastEngine()));
        }
    }

    public static class Wrap extends BaseWrapCipher {
        public Wrap() {
            super(new AESWrapEngine());
        }
    }

    private AES() {
    }

    private static Class a(String str) {
        try {
            return AES.class.getClassLoader().loadClass(str);
        } catch (Exception unused) {
            return null;
        }
    }
}
