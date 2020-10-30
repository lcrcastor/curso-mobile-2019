package org.bouncycastle.jcajce.provider.symmetric;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import org.bouncycastle.jcajce.spec.PBKDF2KeySpec;

public class PBEPBKDF2 {

    public static class AlgParams extends BaseAlgorithmParameters {
        PBKDF2Params a;

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded() {
            try {
                return this.a.getEncoded(ASN1Encoding.DER);
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Oooops! ");
                sb.append(e.toString());
                throw new RuntimeException(sb.toString());
            }
        }

        /* access modifiers changed from: protected */
        public byte[] engineGetEncoded(String str) {
            if (isASN1FormatString(str)) {
                return engineGetEncoded();
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
            if (!(algorithmParameterSpec instanceof PBEParameterSpec)) {
                throw new InvalidParameterSpecException("PBEParameterSpec required to initialise a PBKDF2 PBE parameters algorithm parameters object");
            }
            PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParameterSpec;
            this.a = new PBKDF2Params(pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] bArr) {
            this.a = PBKDF2Params.getInstance(ASN1Primitive.fromByteArray(bArr));
        }

        /* access modifiers changed from: protected */
        public void engineInit(byte[] bArr, String str) {
            if (isASN1FormatString(str)) {
                engineInit(bArr);
                return;
            }
            throw new IOException("Unknown parameters format in PBKDF2 parameters object");
        }

        /* access modifiers changed from: protected */
        public String engineToString() {
            return "PBKDF2 Parameters";
        }

        /* access modifiers changed from: protected */
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
            if (cls == PBEParameterSpec.class) {
                return new PBEParameterSpec(this.a.getSalt(), this.a.getIterationCount().intValue());
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to PBKDF2 PBE parameters object.");
        }
    }

    public static class BasePBKDF2 extends BaseSecretKeyFactory {
        private int a;

        public BasePBKDF2(String str, int i) {
            super(str, PKCSObjectIdentifiers.id_PBKDF2);
            this.a = i;
        }

        private int a(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
            if (aSN1ObjectIdentifier.equals(CryptoProObjectIdentifiers.gostR3411Hmac)) {
                return 6;
            }
            if (aSN1ObjectIdentifier.equals(PKCSObjectIdentifiers.id_hmacWithSHA1)) {
                return 1;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid KeySpec: unknown PRF algorithm ");
            sb.append(aSN1ObjectIdentifier);
            throw new InvalidKeySpecException(sb.toString());
        }

        /* access modifiers changed from: protected */
        public SecretKey engineGenerateSecret(KeySpec keySpec) {
            if (keySpec instanceof PBEKeySpec) {
                PBEKeySpec pBEKeySpec = (PBEKeySpec) keySpec;
                if (pBEKeySpec.getSalt() == null) {
                    throw new InvalidKeySpecException("missing required salt");
                } else if (pBEKeySpec.getIterationCount() <= 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("positive iteration count required: ");
                    sb.append(pBEKeySpec.getIterationCount());
                    throw new InvalidKeySpecException(sb.toString());
                } else if (pBEKeySpec.getKeyLength() <= 0) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("positive key length required: ");
                    sb2.append(pBEKeySpec.getKeyLength());
                    throw new InvalidKeySpecException(sb2.toString());
                } else if (pBEKeySpec.getPassword().length == 0) {
                    throw new IllegalArgumentException("password empty");
                } else if (pBEKeySpec instanceof PBKDF2KeySpec) {
                    int a2 = a(((PBKDF2KeySpec) pBEKeySpec).getPrf().getAlgorithm());
                    int keyLength = pBEKeySpec.getKeyLength();
                    BCPBEKey bCPBEKey = new BCPBEKey(this.algName, this.algOid, this.a, a2, keyLength, -1, pBEKeySpec, Util.makePBEMacParameters(pBEKeySpec, this.a, a2, keyLength));
                    return bCPBEKey;
                } else {
                    int keyLength2 = pBEKeySpec.getKeyLength();
                    BCPBEKey bCPBEKey2 = new BCPBEKey(this.algName, this.algOid, this.a, 1, keyLength2, -1, pBEKeySpec, Util.makePBEMacParameters(pBEKeySpec, this.a, 1, keyLength2));
                    return bCPBEKey2;
                }
            } else {
                throw new InvalidKeySpecException("Invalid KeySpec");
            }
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = PBEPBKDF2.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters.PBKDF2", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Alg.Alias.AlgorithmParameters.");
            sb2.append(PKCSObjectIdentifiers.id_PBKDF2);
            configurableProvider.addAlgorithm(sb2.toString(), "PBKDF2");
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a);
            sb3.append("$PBKDF2withUTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2", sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Alg.Alias.SecretKeyFactory.");
            sb4.append(PKCSObjectIdentifiers.id_PBKDF2);
            configurableProvider.addAlgorithm(sb4.toString(), "PBKDF2");
        }
    }

    public static class PBKDF2withUTF8 extends BasePBKDF2 {
        public PBKDF2withUTF8() {
            super("PBKDF2", 5);
        }
    }

    private PBEPBKDF2() {
    }
}
