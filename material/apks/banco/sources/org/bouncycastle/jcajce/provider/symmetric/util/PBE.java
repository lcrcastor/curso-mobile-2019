package org.bouncycastle.jcajce.provider.symmetric.util;

import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.GOST3411Digest;
import org.bouncycastle.crypto.digests.MD2Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.TigerDigest;
import org.bouncycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS12ParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS5S1ParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.DESParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public interface PBE {
    public static final int GOST3411 = 6;
    public static final int MD2 = 5;
    public static final int MD5 = 0;
    public static final int OPENSSL = 3;
    public static final int PKCS12 = 2;
    public static final int PKCS5S1 = 0;
    public static final int PKCS5S1_UTF8 = 4;
    public static final int PKCS5S2 = 1;
    public static final int PKCS5S2_UTF8 = 5;
    public static final int RIPEMD160 = 2;
    public static final int SHA1 = 1;
    public static final int SHA256 = 4;
    public static final int TIGER = 3;

    public static class Util {
        private static PBEParametersGenerator a(int i, int i2) {
            if (i == 0 || i == 4) {
                if (i2 == 5) {
                    return new PKCS5S1ParametersGenerator(new MD2Digest());
                }
                switch (i2) {
                    case 0:
                        return new PKCS5S1ParametersGenerator(new MD5Digest());
                    case 1:
                        return new PKCS5S1ParametersGenerator(new SHA1Digest());
                    default:
                        throw new IllegalStateException("PKCS5 scheme 1 only supports MD2, MD5 and SHA1.");
                }
            } else if (i == 1 || i == 5) {
                switch (i2) {
                    case 0:
                        return new PKCS5S2ParametersGenerator(new MD5Digest());
                    case 1:
                        return new PKCS5S2ParametersGenerator(new SHA1Digest());
                    case 2:
                        return new PKCS5S2ParametersGenerator(new RIPEMD160Digest());
                    case 3:
                        return new PKCS5S2ParametersGenerator(new TigerDigest());
                    case 4:
                        return new PKCS5S2ParametersGenerator(new SHA256Digest());
                    case 5:
                        return new PKCS5S2ParametersGenerator(new MD2Digest());
                    case 6:
                        return new PKCS5S2ParametersGenerator(new GOST3411Digest());
                    default:
                        throw new IllegalStateException("unknown digest scheme for PBE PKCS5S2 encryption.");
                }
            } else if (i != 2) {
                return new OpenSSLPBEParametersGenerator();
            } else {
                switch (i2) {
                    case 0:
                        return new PKCS12ParametersGenerator(new MD5Digest());
                    case 1:
                        return new PKCS12ParametersGenerator(new SHA1Digest());
                    case 2:
                        return new PKCS12ParametersGenerator(new RIPEMD160Digest());
                    case 3:
                        return new PKCS12ParametersGenerator(new TigerDigest());
                    case 4:
                        return new PKCS12ParametersGenerator(new SHA256Digest());
                    case 5:
                        return new PKCS12ParametersGenerator(new MD2Digest());
                    case 6:
                        return new PKCS12ParametersGenerator(new GOST3411Digest());
                    default:
                        throw new IllegalStateException("unknown digest scheme for PBE encryption.");
                }
            }
        }

        private static byte[] a(int i, PBEKeySpec pBEKeySpec) {
            return i == 2 ? PBEParametersGenerator.PKCS12PasswordToBytes(pBEKeySpec.getPassword()) : (i == 5 || i == 4) ? PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(pBEKeySpec.getPassword()) : PBEParametersGenerator.PKCS5PasswordToBytes(pBEKeySpec.getPassword());
        }

        public static CipherParameters makePBEMacParameters(PBEKeySpec pBEKeySpec, int i, int i2, int i3) {
            PBEParametersGenerator a = a(i, i2);
            byte[] a2 = a(i, pBEKeySpec);
            a.init(a2, pBEKeySpec.getSalt(), pBEKeySpec.getIterationCount());
            CipherParameters generateDerivedMacParameters = a.generateDerivedMacParameters(i3);
            for (int i4 = 0; i4 != a2.length; i4++) {
                a2[i4] = 0;
            }
            return generateDerivedMacParameters;
        }

        public static CipherParameters makePBEMacParameters(BCPBEKey bCPBEKey, AlgorithmParameterSpec algorithmParameterSpec) {
            if (algorithmParameterSpec == null || !(algorithmParameterSpec instanceof PBEParameterSpec)) {
                throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
            }
            PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParameterSpec;
            PBEParametersGenerator a = a(bCPBEKey.a(), bCPBEKey.b());
            byte[] encoded = bCPBEKey.getEncoded();
            if (bCPBEKey.d()) {
                encoded = new byte[2];
            }
            a.init(encoded, pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
            CipherParameters generateDerivedMacParameters = a.generateDerivedMacParameters(bCPBEKey.c());
            for (int i = 0; i != encoded.length; i++) {
                encoded[i] = 0;
            }
            return generateDerivedMacParameters;
        }

        public static CipherParameters makePBEParameters(PBEKeySpec pBEKeySpec, int i, int i2, int i3, int i4) {
            PBEParametersGenerator a = a(i, i2);
            byte[] a2 = a(i, pBEKeySpec);
            a.init(a2, pBEKeySpec.getSalt(), pBEKeySpec.getIterationCount());
            CipherParameters generateDerivedParameters = i4 != 0 ? a.generateDerivedParameters(i3, i4) : a.generateDerivedParameters(i3);
            for (int i5 = 0; i5 != a2.length; i5++) {
                a2[i5] = 0;
            }
            return generateDerivedParameters;
        }

        public static CipherParameters makePBEParameters(BCPBEKey bCPBEKey, AlgorithmParameterSpec algorithmParameterSpec, String str) {
            if (algorithmParameterSpec == null || !(algorithmParameterSpec instanceof PBEParameterSpec)) {
                throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
            }
            PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParameterSpec;
            PBEParametersGenerator a = a(bCPBEKey.a(), bCPBEKey.b());
            byte[] encoded = bCPBEKey.getEncoded();
            if (bCPBEKey.d()) {
                encoded = new byte[2];
            }
            a.init(encoded, pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
            CipherParameters generateDerivedParameters = bCPBEKey.getIvSize() != 0 ? a.generateDerivedParameters(bCPBEKey.c(), bCPBEKey.getIvSize()) : a.generateDerivedParameters(bCPBEKey.c());
            if (str.startsWith("DES")) {
                DESParameters.setOddParity((generateDerivedParameters instanceof ParametersWithIV ? (KeyParameter) ((ParametersWithIV) generateDerivedParameters).getParameters() : (KeyParameter) generateDerivedParameters).getKey());
            }
            for (int i = 0; i != encoded.length; i++) {
                encoded[i] = 0;
            }
            return generateDerivedParameters;
        }
    }
}
