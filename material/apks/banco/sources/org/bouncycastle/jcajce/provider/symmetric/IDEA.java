package org.bouncycastle.jcajce.provider.symmetric;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.misc.IDEACBCPar;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.engines.IDEAEngine;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.macs.CFBBlockCipherMac;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public final class IDEA {

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] bArr = new byte[8];
            if (this.random == null) {
                this.random = new SecureRandom();
            }
            this.random.nextBytes(bArr);
            try {
                AlgorithmParameters instance = AlgorithmParameters.getInstance("IDEA", BouncyCastleProvider.PROVIDER_NAME);
                instance.init(new IvParameterSpec(bArr));
                return instance;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for IDEA parameter generation.");
        }
    }

    public static class AlgParams extends BaseAlgorithmParameters {
        private byte[] a;

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded() {
            return engineGetEncoded("ASN.1");
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded(String str) {
            if (isASN1FormatString(str)) {
                return new IDEACBCPar(engineGetEncoded("RAW")).getEncoded();
            }
            if (!str.equals("RAW")) {
                return null;
            }
            byte[] bArr = new byte[this.a.length];
            System.arraycopy(this.a, 0, bArr, 0, this.a.length);
            return bArr;
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
            if (!(algorithmParameterSpec instanceof IvParameterSpec)) {
                throw new InvalidParameterSpecException("IvParameterSpec required to initialise a IV parameters algorithm parameters object");
            }
            this.a = ((IvParameterSpec) algorithmParameterSpec).getIV();
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] bArr) {
            this.a = new byte[bArr.length];
            System.arraycopy(bArr, 0, this.a, 0, this.a.length);
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] bArr, String str) {
            if (str.equals("RAW")) {
                engineInit(bArr);
            } else if (str.equals("ASN.1")) {
                engineInit(new IDEACBCPar((ASN1Sequence) new ASN1InputStream(bArr).readObject()).getIV());
            } else {
                throw new IOException("Unknown parameters format in IV parameters object");
            }
        }

        /* access modifiers changed from: protected */
        public String engineToString() {
            return "IDEA Parameters";
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
            if (cls == IvParameterSpec.class) {
                return new IvParameterSpec(this.a);
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to IV parameters object.");
        }
    }

    public static class CBC extends BaseBlockCipher {
        public CBC() {
            super((BlockCipher) new CBCBlockCipher(new IDEAEngine()), 64);
        }
    }

    public static class CFB8Mac extends BaseMac {
        public CFB8Mac() {
            super(new CFBBlockCipherMac(new IDEAEngine()));
        }
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipher) new IDEAEngine());
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("IDEA", 128, new CipherKeyGenerator());
        }
    }

    public static class Mac extends BaseMac {
        public Mac() {
            super(new CBCBlockCipherMac(new IDEAEngine()));
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = IDEA.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$AlgParamGen");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.IDEA", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a);
            sb2.append("$AlgParamGen");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.1.3.6.1.4.1.188.7.1.1.2", sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a);
            sb3.append("$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters.IDEA", sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(a);
            sb4.append("$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters.1.3.6.1.4.1.188.7.1.1.2", sb4.toString());
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDIDEA", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDIDEA-CBC", "PKCS12PBE");
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("$ECB");
            configurableProvider.addAlgorithm("Cipher.IDEA", sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append(a);
            sb6.append("$CBC");
            configurableProvider.addAlgorithm("Cipher.1.3.6.1.4.1.188.7.1.1.2", sb6.toString());
            StringBuilder sb7 = new StringBuilder();
            sb7.append(a);
            sb7.append("$PBEWithSHAAndIDEA");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAANDIDEA-CBC", sb7.toString());
            StringBuilder sb8 = new StringBuilder();
            sb8.append(a);
            sb8.append("$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator.IDEA", sb8.toString());
            StringBuilder sb9 = new StringBuilder();
            sb9.append(a);
            sb9.append("$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator.1.3.6.1.4.1.188.7.1.1.2", sb9.toString());
            StringBuilder sb10 = new StringBuilder();
            sb10.append(a);
            sb10.append("$PBEWithSHAAndIDEAKeyGen");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAANDIDEA-CBC", sb10.toString());
            StringBuilder sb11 = new StringBuilder();
            sb11.append(a);
            sb11.append("$Mac");
            configurableProvider.addAlgorithm("Mac.IDEAMAC", sb11.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Mac.IDEA", "IDEAMAC");
            StringBuilder sb12 = new StringBuilder();
            sb12.append(a);
            sb12.append("$CFB8Mac");
            configurableProvider.addAlgorithm("Mac.IDEAMAC/CFB8", sb12.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Mac.IDEA/CFB8", "IDEAMAC/CFB8");
        }
    }

    public static class PBEWithSHAAndIDEA extends BaseBlockCipher {
        public PBEWithSHAAndIDEA() {
            super((BlockCipher) new CBCBlockCipher(new IDEAEngine()));
        }
    }

    public static class PBEWithSHAAndIDEAKeyGen extends PBESecretKeyFactory {
        public PBEWithSHAAndIDEAKeyGen() {
            super("PBEwithSHAandIDEA-CBC", null, true, 2, 1, 128, 64);
        }
    }

    private IDEA() {
    }
}
