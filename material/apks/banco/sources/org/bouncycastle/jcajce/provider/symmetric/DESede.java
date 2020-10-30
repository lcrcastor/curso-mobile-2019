package org.bouncycastle.jcajce.provider.symmetric;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.engines.DESedeWrapEngine;
import org.bouncycastle.crypto.engines.RFC3211WrapEngine;
import org.bouncycastle.crypto.generators.DESedeKeyGenerator;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.macs.CFBBlockCipherMac;
import org.bouncycastle.crypto.macs.CMac;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.ISO7816d4Padding;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.DES.DESPBEKeyFactory;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public final class DESede {

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] bArr = new byte[8];
            if (this.random == null) {
                this.random = new SecureRandom();
            }
            this.random.nextBytes(bArr);
            try {
                AlgorithmParameters instance = AlgorithmParameters.getInstance("DES", BouncyCastleProvider.PROVIDER_NAME);
                instance.init(new IvParameterSpec(bArr));
                return instance;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for DES parameter generation.");
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new DESedeEngine()), 64);
        }
    }

    public static class CBCMAC extends BaseMac {
        public CBCMAC() {
            super(new CBCBlockCipherMac(new DESedeEngine()));
        }
    }

    public static class CMAC extends BaseMac {
        public CMAC() {
            super(new CMac(new DESedeEngine()));
        }
    }

    public static class DESede64 extends BaseMac {
        public DESede64() {
            super(new CBCBlockCipherMac((BlockCipher) new DESedeEngine(), 64));
        }
    }

    public static class DESede64with7816d4 extends BaseMac {
        public DESede64with7816d4() {
            super(new CBCBlockCipherMac(new DESedeEngine(), 64, new ISO7816d4Padding()));
        }
    }

    public static class DESedeCFB8 extends BaseMac {
        public DESedeCFB8() {
            super(new CFBBlockCipherMac(new DESedeEngine()));
        }
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipher) new DESedeEngine());
        }
    }

    public static class KeyFactory extends BaseSecretKeyFactory {
        public KeyFactory() {
            super("DESede", null);
        }

        /* access modifiers changed from: protected */
        public SecretKey engineGenerateSecret(KeySpec keySpec) {
            return keySpec instanceof DESedeKeySpec ? new SecretKeySpec(((DESedeKeySpec) keySpec).getKey(), "DESede") : super.engineGenerateSecret(keySpec);
        }

        /* access modifiers changed from: protected */
        public KeySpec engineGetKeySpec(SecretKey secretKey, Class cls) {
            if (cls == null) {
                throw new InvalidKeySpecException("keySpec parameter is null");
            } else if (secretKey == null) {
                throw new InvalidKeySpecException("key parameter is null");
            } else if (SecretKeySpec.class.isAssignableFrom(cls)) {
                return new SecretKeySpec(secretKey.getEncoded(), this.algName);
            } else {
                if (DESedeKeySpec.class.isAssignableFrom(cls)) {
                    byte[] encoded = secretKey.getEncoded();
                    try {
                        if (encoded.length != 16) {
                            return new DESedeKeySpec(encoded);
                        }
                        byte[] bArr = new byte[24];
                        System.arraycopy(encoded, 0, bArr, 0, 16);
                        System.arraycopy(encoded, 0, bArr, 16, 8);
                        return new DESedeKeySpec(bArr);
                    } catch (Exception e) {
                        throw new InvalidKeySpecException(e.toString());
                    }
                } else {
                    throw new InvalidKeySpecException("Invalid KeySpec");
                }
            }
        }
    }

    public static class KeyGenerator extends BaseKeyGenerator {
        private boolean a = false;

        public KeyGenerator() {
            super("DESede", 192, new DESedeKeyGenerator());
        }

        /* access modifiers changed from: protected */
        public SecretKey engineGenerateKey() {
            if (this.uninitialised) {
                this.engine.init(new KeyGenerationParameters(new SecureRandom(), this.defaultKeySize));
                this.uninitialised = false;
            }
            if (this.a) {
                return new SecretKeySpec(this.engine.generateKey(), this.algName);
            }
            byte[] generateKey = this.engine.generateKey();
            System.arraycopy(generateKey, 0, generateKey, 16, 8);
            return new SecretKeySpec(generateKey, this.algName);
        }

        /* access modifiers changed from: protected */
        public void engineInit(int i, SecureRandom secureRandom) {
            super.engineInit(i, secureRandom);
            this.a = true;
        }
    }

    public static class KeyGenerator3 extends BaseKeyGenerator {
        public KeyGenerator3() {
            super("DESede3", 192, new DESedeKeyGenerator());
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = DESede.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$ECB");
            configurableProvider.addAlgorithm("Cipher.DESEDE", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Cipher.");
            sb2.append(PKCSObjectIdentifiers.des_EDE3_CBC);
            String sb3 = sb2.toString();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(a);
            sb4.append("$CBC");
            configurableProvider.addAlgorithm(sb3, sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("$Wrap");
            configurableProvider.addAlgorithm("Cipher.DESEDEWRAP", sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append("Cipher.");
            sb6.append(PKCSObjectIdentifiers.id_alg_CMS3DESwrap);
            String sb7 = sb6.toString();
            StringBuilder sb8 = new StringBuilder();
            sb8.append(a);
            sb8.append("$Wrap");
            configurableProvider.addAlgorithm(sb7, sb8.toString());
            StringBuilder sb9 = new StringBuilder();
            sb9.append(a);
            sb9.append("$RFC3211");
            configurableProvider.addAlgorithm("Cipher.DESEDERFC3211WRAP", sb9.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.TDEA", "DESEDE");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.TDEAWRAP", "DESEDEWRAP");
            configurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.TDEA", "DESEDE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.TDEA", "DESEDE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator.TDEA", "DESEDE");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.TDEA", "DESEDE");
            if (configurableProvider.hasAlgorithm("MessageDigest", CommonUtils.SHA1_INSTANCE)) {
                StringBuilder sb10 = new StringBuilder();
                sb10.append(a);
                sb10.append("$PBEWithSHAAndDES3Key");
                configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", sb10.toString());
                StringBuilder sb11 = new StringBuilder();
                sb11.append(a);
                sb11.append("$BrokePBEWithSHAAndDES3Key");
                configurableProvider.addAlgorithm("Cipher.BROKENPBEWITHSHAAND3-KEYTRIPLEDES-CBC", sb11.toString());
                StringBuilder sb12 = new StringBuilder();
                sb12.append(a);
                sb12.append("$OldPBEWithSHAAndDES3Key");
                configurableProvider.addAlgorithm("Cipher.OLDPBEWITHSHAAND3-KEYTRIPLEDES-CBC", sb12.toString());
                StringBuilder sb13 = new StringBuilder();
                sb13.append(a);
                sb13.append("$PBEWithSHAAndDES2Key");
                configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", sb13.toString());
                StringBuilder sb14 = new StringBuilder();
                sb14.append(a);
                sb14.append("$BrokePBEWithSHAAndDES2Key");
                configurableProvider.addAlgorithm("Cipher.BROKENPBEWITHSHAAND2-KEYTRIPLEDES-CBC", sb14.toString());
                StringBuilder sb15 = new StringBuilder();
                sb15.append("Alg.Alias.Cipher.");
                sb15.append(PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC);
                configurableProvider.addAlgorithm(sb15.toString(), "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
                StringBuilder sb16 = new StringBuilder();
                sb16.append("Alg.Alias.Cipher.");
                sb16.append(PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC);
                configurableProvider.addAlgorithm(sb16.toString(), "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
                configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1ANDDESEDE", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
                configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND3-KEYTRIPLEDES-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
                configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND2-KEYTRIPLEDES-CBC", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
            }
            StringBuilder sb17 = new StringBuilder();
            sb17.append(a);
            sb17.append("$KeyGenerator");
            configurableProvider.addAlgorithm("KeyGenerator.DESEDE", sb17.toString());
            StringBuilder sb18 = new StringBuilder();
            sb18.append("KeyGenerator.");
            sb18.append(PKCSObjectIdentifiers.des_EDE3_CBC);
            String sb19 = sb18.toString();
            StringBuilder sb20 = new StringBuilder();
            sb20.append(a);
            sb20.append("$KeyGenerator3");
            configurableProvider.addAlgorithm(sb19, sb20.toString());
            StringBuilder sb21 = new StringBuilder();
            sb21.append(a);
            sb21.append("$KeyGenerator");
            configurableProvider.addAlgorithm("KeyGenerator.DESEDEWRAP", sb21.toString());
            StringBuilder sb22 = new StringBuilder();
            sb22.append(a);
            sb22.append("$KeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.DESEDE", sb22.toString());
            StringBuilder sb23 = new StringBuilder();
            sb23.append(a);
            sb23.append("$CMAC");
            configurableProvider.addAlgorithm("Mac.DESEDECMAC", sb23.toString());
            StringBuilder sb24 = new StringBuilder();
            sb24.append(a);
            sb24.append("$CBCMAC");
            configurableProvider.addAlgorithm("Mac.DESEDEMAC", sb24.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Mac.DESEDE", "DESEDEMAC");
            StringBuilder sb25 = new StringBuilder();
            sb25.append(a);
            sb25.append("$DESedeCFB8");
            configurableProvider.addAlgorithm("Mac.DESEDEMAC/CFB8", sb25.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Mac.DESEDE/CFB8", "DESEDEMAC/CFB8");
            StringBuilder sb26 = new StringBuilder();
            sb26.append(a);
            sb26.append("$DESede64");
            configurableProvider.addAlgorithm("Mac.DESEDEMAC64", sb26.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Mac.DESEDE64", "DESEDEMAC64");
            StringBuilder sb27 = new StringBuilder();
            sb27.append(a);
            sb27.append("$DESede64with7816d4");
            configurableProvider.addAlgorithm("Mac.DESEDEMAC64WITHISO7816-4PADDING", sb27.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Mac.DESEDE64WITHISO7816-4PADDING", "DESEDEMAC64WITHISO7816-4PADDING");
            configurableProvider.addAlgorithm("Alg.Alias.Mac.DESEDEISO9797ALG1MACWITHISO7816-4PADDING", "DESEDEMAC64WITHISO7816-4PADDING");
            configurableProvider.addAlgorithm("Alg.Alias.Mac.DESEDEISO9797ALG1WITHISO7816-4PADDING", "DESEDEMAC64WITHISO7816-4PADDING");
            configurableProvider.addAlgorithm("AlgorithmParameters.DESEDE", "org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters");
            StringBuilder sb28 = new StringBuilder();
            sb28.append("Alg.Alias.AlgorithmParameters.");
            sb28.append(PKCSObjectIdentifiers.des_EDE3_CBC);
            configurableProvider.addAlgorithm(sb28.toString(), "DESEDE");
            StringBuilder sb29 = new StringBuilder();
            sb29.append(a);
            sb29.append("$AlgParamGen");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.DESEDE", sb29.toString());
            StringBuilder sb30 = new StringBuilder();
            sb30.append("Alg.Alias.AlgorithmParameterGenerator.");
            sb30.append(PKCSObjectIdentifiers.des_EDE3_CBC);
            configurableProvider.addAlgorithm(sb30.toString(), "DESEDE");
            StringBuilder sb31 = new StringBuilder();
            sb31.append(a);
            sb31.append("$PBEWithSHAAndDES3KeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", sb31.toString());
            StringBuilder sb32 = new StringBuilder();
            sb32.append(a);
            sb32.append("$PBEWithSHAAndDES2KeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", sb32.toString());
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND3-KEYTRIPLEDES", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND2-KEYTRIPLEDES", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDDES3KEY-CBC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDDES2KEY-CBC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.3", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.4", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWithSHAAnd3KeyTripleDES", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.3", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.4", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWithSHAAnd3KeyTripleDES", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        }
    }

    public static class PBEWithSHAAndDES2Key extends BaseBlockCipher {
        public PBEWithSHAAndDES2Key() {
            super((BlockCipher) new CBCBlockCipher(new DESedeEngine()));
        }
    }

    public static class PBEWithSHAAndDES2KeyFactory extends DESPBEKeyFactory {
        public PBEWithSHAAndDES2KeyFactory() {
            super("PBEwithSHAandDES2Key-CBC", PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC, true, 2, 1, 128, 64);
        }
    }

    public static class PBEWithSHAAndDES3Key extends BaseBlockCipher {
        public PBEWithSHAAndDES3Key() {
            super((BlockCipher) new CBCBlockCipher(new DESedeEngine()));
        }
    }

    public static class PBEWithSHAAndDES3KeyFactory extends DESPBEKeyFactory {
        public PBEWithSHAAndDES3KeyFactory() {
            super("PBEwithSHAandDES3Key-CBC", PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, true, 2, 1, 192, 64);
        }
    }

    public static class RFC3211 extends BaseWrapCipher {
        public RFC3211() {
            super(new RFC3211WrapEngine(new DESedeEngine()), 8);
        }
    }

    public static class Wrap extends BaseWrapCipher {
        public Wrap() {
            super(new DESedeWrapEngine());
        }
    }

    private DESede() {
    }
}
