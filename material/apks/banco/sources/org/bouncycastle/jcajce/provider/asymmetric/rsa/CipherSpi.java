package org.bouncycastle.jcajce.provider.asymmetric.rsa;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource.PSpecified;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.encodings.ISO9796d1Encoding;
import org.bouncycastle.crypto.encodings.OAEPEncoding;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSABlindedEngine;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi;
import org.bouncycastle.jcajce.provider.util.DigestFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Strings;

public class CipherSpi extends BaseCipherSpi {
    private AsymmetricBlockCipher a;
    private AlgorithmParameterSpec b;
    private AlgorithmParameters c;
    private boolean d = false;
    private boolean e = false;
    private ByteArrayOutputStream f = new ByteArrayOutputStream();

    public static class ISO9796d1Padding extends CipherSpi {
        public ISO9796d1Padding() {
            super((AsymmetricBlockCipher) new ISO9796d1Encoding(new RSABlindedEngine()));
        }
    }

    public static class NoPadding extends CipherSpi {
        public NoPadding() {
            super((AsymmetricBlockCipher) new RSABlindedEngine());
        }
    }

    public static class OAEPPadding extends CipherSpi {
        public OAEPPadding() {
            super(OAEPParameterSpec.DEFAULT);
        }
    }

    public static class PKCS1v1_5Padding extends CipherSpi {
        public PKCS1v1_5Padding() {
            super((AsymmetricBlockCipher) new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class PKCS1v1_5Padding_PrivateOnly extends CipherSpi {
        public PKCS1v1_5Padding_PrivateOnly() {
            super(false, true, new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class PKCS1v1_5Padding_PublicOnly extends CipherSpi {
        public PKCS1v1_5Padding_PublicOnly() {
            super(true, false, new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public CipherSpi(OAEPParameterSpec oAEPParameterSpec) {
        try {
            a(oAEPParameterSpec);
        } catch (NoSuchPaddingException e2) {
            throw new IllegalArgumentException(e2.getMessage());
        }
    }

    public CipherSpi(AsymmetricBlockCipher asymmetricBlockCipher) {
        this.a = asymmetricBlockCipher;
    }

    public CipherSpi(boolean z, boolean z2, AsymmetricBlockCipher asymmetricBlockCipher) {
        this.d = z;
        this.e = z2;
        this.a = asymmetricBlockCipher;
    }

    private void a(OAEPParameterSpec oAEPParameterSpec) {
        MGF1ParameterSpec mGF1ParameterSpec = (MGF1ParameterSpec) oAEPParameterSpec.getMGFParameters();
        Digest digest = DigestFactory.getDigest(mGF1ParameterSpec.getDigestAlgorithm());
        if (digest == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("no match on OAEP constructor for digest algorithm: ");
            sb.append(mGF1ParameterSpec.getDigestAlgorithm());
            throw new NoSuchPaddingException(sb.toString());
        }
        this.a = new OAEPEncoding(new RSABlindedEngine(), digest, ((PSpecified) oAEPParameterSpec.getPSource()).getValue());
        this.b = oAEPParameterSpec;
    }

    /* access modifiers changed from: protected */
    public int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (bArr != null) {
            this.f.write(bArr, i, i2);
        }
        if (this.a instanceof RSABlindedEngine) {
            if (this.f.size() > this.a.getInputBlockSize() + 1) {
                throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
            }
        } else if (this.f.size() > this.a.getInputBlockSize()) {
            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
        }
        try {
            byte[] byteArray = this.f.toByteArray();
            byte[] processBlock = this.a.processBlock(byteArray, 0, byteArray.length);
            this.f.reset();
            for (int i4 = 0; i4 != processBlock.length; i4++) {
                bArr2[i3 + i4] = processBlock[i4];
            }
            return processBlock.length;
        } catch (InvalidCipherTextException e2) {
            throw new BadPaddingException(e2.getMessage());
        } catch (Throwable th) {
            this.f.reset();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineDoFinal(byte[] bArr, int i, int i2) {
        if (bArr != null) {
            this.f.write(bArr, i, i2);
        }
        if (this.a instanceof RSABlindedEngine) {
            if (this.f.size() > this.a.getInputBlockSize() + 1) {
                throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
            }
        } else if (this.f.size() > this.a.getInputBlockSize()) {
            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
        }
        try {
            byte[] byteArray = this.f.toByteArray();
            this.f.reset();
            return this.a.processBlock(byteArray, 0, byteArray.length);
        } catch (InvalidCipherTextException e2) {
            throw new BadPaddingException(e2.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public int engineGetBlockSize() {
        try {
            return this.a.getInputBlockSize();
        } catch (NullPointerException unused) {
            throw new IllegalStateException("RSA Cipher not initialised");
        }
    }

    /* access modifiers changed from: protected */
    public int engineGetKeySize(Key key) {
        BigInteger modulus;
        if (key instanceof RSAPrivateKey) {
            modulus = ((RSAPrivateKey) key).getModulus();
        } else if (key instanceof RSAPublicKey) {
            modulus = ((RSAPublicKey) key).getModulus();
        } else {
            throw new IllegalArgumentException("not an RSA key!");
        }
        return modulus.bitLength();
    }

    /* access modifiers changed from: protected */
    public int engineGetOutputSize(int i) {
        try {
            return this.a.getOutputBlockSize();
        } catch (NullPointerException unused) {
            throw new IllegalStateException("RSA Cipher not initialised");
        }
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGetParameters() {
        if (this.c == null && this.b != null) {
            try {
                this.c = AlgorithmParameters.getInstance("OAEP", BouncyCastleProvider.PROVIDER_NAME);
                this.c.init(this.b);
            } catch (Exception e2) {
                throw new RuntimeException(e2.toString());
            }
        }
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) {
        AlgorithmParameterSpec algorithmParameterSpec;
        if (algorithmParameters != null) {
            try {
                algorithmParameterSpec = algorithmParameters.getParameterSpec(OAEPParameterSpec.class);
            } catch (InvalidParameterSpecException e2) {
                StringBuilder sb = new StringBuilder();
                sb.append("cannot recognise parameters: ");
                sb.append(e2.toString());
                throw new InvalidAlgorithmParameterException(sb.toString(), e2);
            }
        } else {
            algorithmParameterSpec = null;
        }
        this.c = algorithmParameters;
        engineInit(i, key, algorithmParameterSpec, secureRandom);
    }

    /* access modifiers changed from: protected */
    public void engineInit(int i, Key key, SecureRandom secureRandom) {
        try {
            engineInit(i, key, (AlgorithmParameterSpec) null, secureRandom);
        } catch (InvalidAlgorithmParameterException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Eeeek! ");
            sb.append(e2.toString());
            throw new InvalidKeyException(sb.toString(), e2);
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        CipherParameters cipherParameters;
        if (algorithmParameterSpec == null || (algorithmParameterSpec instanceof OAEPParameterSpec)) {
            if (key instanceof RSAPublicKey) {
                if (!this.e || i != 1) {
                    cipherParameters = RSAUtil.a((RSAPublicKey) key);
                } else {
                    throw new InvalidKeyException("mode 1 requires RSAPrivateKey");
                }
            } else if (!(key instanceof RSAPrivateKey)) {
                throw new InvalidKeyException("unknown key type passed to RSA");
            } else if (!this.d || i != 1) {
                cipherParameters = RSAUtil.a((RSAPrivateKey) key);
            } else {
                throw new InvalidKeyException("mode 2 requires RSAPublicKey");
            }
            if (algorithmParameterSpec != null) {
                OAEPParameterSpec oAEPParameterSpec = (OAEPParameterSpec) algorithmParameterSpec;
                this.b = algorithmParameterSpec;
                if (!oAEPParameterSpec.getMGFAlgorithm().equalsIgnoreCase("MGF1") && !oAEPParameterSpec.getMGFAlgorithm().equals(PKCSObjectIdentifiers.id_mgf1.getId())) {
                    throw new InvalidAlgorithmParameterException("unknown mask generation function specified");
                } else if (!(oAEPParameterSpec.getMGFParameters() instanceof MGF1ParameterSpec)) {
                    throw new InvalidAlgorithmParameterException("unkown MGF parameters");
                } else {
                    Digest digest = DigestFactory.getDigest(oAEPParameterSpec.getDigestAlgorithm());
                    if (digest == null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("no match on digest algorithm: ");
                        sb.append(oAEPParameterSpec.getDigestAlgorithm());
                        throw new InvalidAlgorithmParameterException(sb.toString());
                    }
                    MGF1ParameterSpec mGF1ParameterSpec = (MGF1ParameterSpec) oAEPParameterSpec.getMGFParameters();
                    Digest digest2 = DigestFactory.getDigest(mGF1ParameterSpec.getDigestAlgorithm());
                    if (digest2 == null) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("no match on MGF digest algorithm: ");
                        sb2.append(mGF1ParameterSpec.getDigestAlgorithm());
                        throw new InvalidAlgorithmParameterException(sb2.toString());
                    }
                    this.a = new OAEPEncoding(new RSABlindedEngine(), digest, digest2, ((PSpecified) oAEPParameterSpec.getPSource()).getValue());
                }
            }
            CipherParameters cipherParameters2 = !(this.a instanceof RSABlindedEngine) ? secureRandom != null ? new ParametersWithRandom(cipherParameters, secureRandom) : new ParametersWithRandom(cipherParameters, new SecureRandom()) : cipherParameters;
            this.f.reset();
            switch (i) {
                case 1:
                case 3:
                    this.a.init(true, cipherParameters2);
                    return;
                case 2:
                case 4:
                    this.a.init(false, cipherParameters2);
                    return;
                default:
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("unknown opmode ");
                    sb3.append(i);
                    sb3.append(" passed to RSA");
                    throw new InvalidParameterException(sb3.toString());
            }
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("unknown parameter type: ");
            sb4.append(algorithmParameterSpec.getClass().getName());
            throw new InvalidAlgorithmParameterException(sb4.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void engineSetMode(String str) {
        String upperCase = Strings.toUpperCase(str);
        if (!upperCase.equals("NONE") && !upperCase.equals("ECB")) {
            if (upperCase.equals("1")) {
                this.e = true;
                this.d = false;
            } else if (upperCase.equals("2")) {
                this.e = false;
                this.d = true;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("can't support mode ");
                sb.append(str);
                throw new NoSuchAlgorithmException(sb.toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void engineSetPadding(String str) {
        OAEPParameterSpec oAEPParameterSpec;
        AsymmetricBlockCipher iSO9796d1Encoding;
        String upperCase = Strings.toUpperCase(str);
        if (upperCase.equals("NOPADDING")) {
            iSO9796d1Encoding = new RSABlindedEngine();
        } else if (upperCase.equals("PKCS1PADDING")) {
            iSO9796d1Encoding = new PKCS1Encoding(new RSABlindedEngine());
        } else if (upperCase.equals("ISO9796-1PADDING")) {
            iSO9796d1Encoding = new ISO9796d1Encoding(new RSABlindedEngine());
        } else {
            if (upperCase.equals("OAEPWITHMD5ANDMGF1PADDING")) {
                oAEPParameterSpec = new OAEPParameterSpec(CommonUtils.MD5_INSTANCE, "MGF1", new MGF1ParameterSpec(CommonUtils.MD5_INSTANCE), PSpecified.DEFAULT);
            } else if (upperCase.equals("OAEPPADDING") || upperCase.equals("OAEPWITHSHA1ANDMGF1PADDING") || upperCase.equals("OAEPWITHSHA-1ANDMGF1PADDING")) {
                oAEPParameterSpec = OAEPParameterSpec.DEFAULT;
            } else if (upperCase.equals("OAEPWITHSHA224ANDMGF1PADDING") || upperCase.equals("OAEPWITHSHA-224ANDMGF1PADDING")) {
                oAEPParameterSpec = new OAEPParameterSpec("SHA-224", "MGF1", new MGF1ParameterSpec("SHA-224"), PSpecified.DEFAULT);
            } else if (upperCase.equals("OAEPWITHSHA256ANDMGF1PADDING") || upperCase.equals("OAEPWITHSHA-256ANDMGF1PADDING")) {
                oAEPParameterSpec = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSpecified.DEFAULT);
            } else if (upperCase.equals("OAEPWITHSHA384ANDMGF1PADDING") || upperCase.equals("OAEPWITHSHA-384ANDMGF1PADDING")) {
                oAEPParameterSpec = new OAEPParameterSpec("SHA-384", "MGF1", MGF1ParameterSpec.SHA384, PSpecified.DEFAULT);
            } else if (upperCase.equals("OAEPWITHSHA512ANDMGF1PADDING") || upperCase.equals("OAEPWITHSHA-512ANDMGF1PADDING")) {
                oAEPParameterSpec = new OAEPParameterSpec("SHA-512", "MGF1", MGF1ParameterSpec.SHA512, PSpecified.DEFAULT);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(" unavailable with RSA.");
                throw new NoSuchPaddingException(sb.toString());
            }
            a(oAEPParameterSpec);
            return;
        }
        this.a = iSO9796d1Encoding;
    }

    /* access modifiers changed from: protected */
    public int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        this.f.write(bArr, i, i2);
        if (this.a instanceof RSABlindedEngine) {
            if (this.f.size() > this.a.getInputBlockSize() + 1) {
                throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
            }
        } else if (this.f.size() > this.a.getInputBlockSize()) {
            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public byte[] engineUpdate(byte[] bArr, int i, int i2) {
        this.f.write(bArr, i, i2);
        if (this.a instanceof RSABlindedEngine) {
            if (this.f.size() > this.a.getInputBlockSize() + 1) {
                throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
            }
        } else if (this.f.size() > this.a.getInputBlockSize()) {
            throw new ArrayIndexOutOfBoundsException("too much data for RSA block");
        }
        return null;
    }
}
