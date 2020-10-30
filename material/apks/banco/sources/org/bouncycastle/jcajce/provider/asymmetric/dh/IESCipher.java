package org.bouncycastle.jcajce.provider.asymmetric.dh;

import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.interfaces.DHKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.KeyEncoder;
import org.bouncycastle.crypto.KeyParser;
import org.bouncycastle.crypto.agreement.DHBasicAgreement;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.engines.IESEngine;
import org.bouncycastle.crypto.generators.DHKeyPairGenerator;
import org.bouncycastle.crypto.generators.EphemeralKeyPairGenerator;
import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DHKeyGenerationParameters;
import org.bouncycastle.crypto.params.DHKeyParameters;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.bouncycastle.crypto.params.IESWithCipherParameters;
import org.bouncycastle.crypto.parsers.DHIESPublicKeyParser;
import org.bouncycastle.jcajce.provider.asymmetric.util.DHUtil;
import org.bouncycastle.jcajce.provider.asymmetric.util.IESUtil;
import org.bouncycastle.jce.interfaces.IESKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.IESParameterSpec;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Strings;

public class IESCipher extends CipherSpi {
    private IESEngine a;
    private int b = -1;
    private ByteArrayOutputStream c = new ByteArrayOutputStream();
    private AlgorithmParameters d = null;
    private IESParameterSpec e = null;
    private AsymmetricKeyParameter f;
    private SecureRandom g;
    private boolean h = false;
    private AsymmetricKeyParameter i = null;

    public static class IES extends IESCipher {
        public IES() {
            super(new IESEngine(new DHBasicAgreement(), new KDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest())));
        }
    }

    public static class IESwithAES extends IESCipher {
        public IESwithAES() {
            super(new IESEngine(new DHBasicAgreement(), new KDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest()), new PaddedBufferedBlockCipher(new AESEngine())));
        }
    }

    public static class IESwithDESede extends IESCipher {
        public IESwithDESede() {
            super(new IESEngine(new DHBasicAgreement(), new KDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest()), new PaddedBufferedBlockCipher(new DESedeEngine())));
        }
    }

    public IESCipher(IESEngine iESEngine) {
        this.a = iESEngine;
    }

    public int engineDoFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        byte[] engineDoFinal = engineDoFinal(bArr, i2, i3);
        System.arraycopy(engineDoFinal, 0, bArr2, i4, engineDoFinal.length);
        return engineDoFinal.length;
    }

    public byte[] engineDoFinal(byte[] bArr, int i2, int i3) {
        if (i3 != 0) {
            this.c.write(bArr, i2, i3);
        }
        byte[] byteArray = this.c.toByteArray();
        this.c.reset();
        IESWithCipherParameters iESWithCipherParameters = new IESWithCipherParameters(this.e.getDerivationV(), this.e.getEncodingV(), this.e.getMacKeySize(), this.e.getCipherKeySize());
        DHParameters parameters = ((DHKeyParameters) this.f).getParameters();
        if (this.i != null) {
            try {
                if (this.b != 1) {
                    if (this.b != 3) {
                        this.a.init(false, this.f, this.i, iESWithCipherParameters);
                        return this.a.processBlock(byteArray, 0, byteArray.length);
                    }
                }
                this.a.init(true, this.i, this.f, iESWithCipherParameters);
                return this.a.processBlock(byteArray, 0, byteArray.length);
            } catch (Exception e2) {
                throw new BadPaddingException(e2.getMessage());
            }
        } else if (this.b == 1 || this.b == 3) {
            DHKeyPairGenerator dHKeyPairGenerator = new DHKeyPairGenerator();
            dHKeyPairGenerator.init(new DHKeyGenerationParameters(this.g, parameters));
            try {
                this.a.init(this.f, (CipherParameters) iESWithCipherParameters, new EphemeralKeyPairGenerator(dHKeyPairGenerator, new KeyEncoder() {
                    public byte[] getEncoded(AsymmetricKeyParameter asymmetricKeyParameter) {
                        byte[] bArr = new byte[((((DHKeyParameters) asymmetricKeyParameter).getParameters().getP().bitLength() + 7) / 8)];
                        byte[] asUnsignedByteArray = BigIntegers.asUnsignedByteArray(((DHPublicKeyParameters) asymmetricKeyParameter).getY());
                        if (asUnsignedByteArray.length > bArr.length) {
                            throw new IllegalArgumentException("Senders's public key longer than expected.");
                        }
                        System.arraycopy(asUnsignedByteArray, 0, bArr, bArr.length - asUnsignedByteArray.length, asUnsignedByteArray.length);
                        return bArr;
                    }
                }));
                return this.a.processBlock(byteArray, 0, byteArray.length);
            } catch (Exception e3) {
                throw new BadPaddingException(e3.getMessage());
            }
        } else if (this.b == 2 || this.b == 4) {
            try {
                this.a.init(this.f, (CipherParameters) iESWithCipherParameters, (KeyParser) new DHIESPublicKeyParser(((DHKeyParameters) this.f).getParameters()));
                return this.a.processBlock(byteArray, 0, byteArray.length);
            } catch (InvalidCipherTextException e4) {
                throw new BadPaddingException(e4.getMessage());
            }
        } else {
            throw new IllegalStateException("IESCipher not initialised");
        }
    }

    public int engineGetBlockSize() {
        if (this.a.getCipher() != null) {
            return this.a.getCipher().getBlockSize();
        }
        return 0;
    }

    public byte[] engineGetIV() {
        return null;
    }

    public int engineGetKeySize(Key key) {
        if (key instanceof DHKey) {
            return ((DHKey) key).getParams().getP().bitLength();
        }
        throw new IllegalArgumentException("not a DH key");
    }

    public int engineGetOutputSize(int i2) {
        int size;
        BufferedBlockCipher bufferedBlockCipher;
        int macSize = this.a.getMac().getMacSize();
        if (this.f != null) {
            int bitLength = (((DHKey) this.f).getParams().getP().bitLength() / 8) + 1;
            if (this.a.getCipher() != null) {
                if (this.b == 1 || this.b == 3) {
                    bufferedBlockCipher = this.a.getCipher();
                } else if (this.b == 2 || this.b == 4) {
                    bufferedBlockCipher = this.a.getCipher();
                    i2 = (i2 - macSize) - bitLength;
                } else {
                    throw new IllegalStateException("cipher not initialised");
                }
                i2 = bufferedBlockCipher.getOutputSize(i2);
            }
            if (this.b == 1 || this.b == 3) {
                size = this.c.size() + macSize + bitLength;
            } else if (this.b == 2 || this.b == 4) {
                size = (this.c.size() - macSize) - bitLength;
            } else {
                throw new IllegalStateException("IESCipher not initialised");
            }
            return size + i2;
        }
        throw new IllegalStateException("cipher not initialised");
    }

    public AlgorithmParameters engineGetParameters() {
        if (this.d == null && this.e != null) {
            try {
                this.d = AlgorithmParameters.getInstance("IES", BouncyCastleProvider.PROVIDER_NAME);
                this.d.init(this.e);
            } catch (Exception e2) {
                throw new RuntimeException(e2.toString());
            }
        }
        return this.d;
    }

    public void engineInit(int i2, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) {
        AlgorithmParameterSpec algorithmParameterSpec;
        if (algorithmParameters != null) {
            try {
                algorithmParameterSpec = algorithmParameters.getParameterSpec(IESParameterSpec.class);
            } catch (Exception e2) {
                StringBuilder sb = new StringBuilder();
                sb.append("cannot recognise parameters: ");
                sb.append(e2.toString());
                throw new InvalidAlgorithmParameterException(sb.toString());
            }
        } else {
            algorithmParameterSpec = null;
        }
        this.d = algorithmParameters;
        engineInit(i2, key, algorithmParameterSpec, secureRandom);
    }

    public void engineInit(int i2, Key key, SecureRandom secureRandom) {
        try {
            engineInit(i2, key, (AlgorithmParameterSpec) null, secureRandom);
        } catch (InvalidAlgorithmParameterException unused) {
            throw new IllegalArgumentException("can't handle supplied parameter spec");
        }
    }

    public void engineInit(int i2, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        IESParameterSpec iESParameterSpec;
        AsymmetricKeyParameter asymmetricKeyParameter;
        PrivateKey privateKey;
        if (algorithmParameterSpec == null) {
            iESParameterSpec = IESUtil.guessParameterSpec(this.a);
        } else if (algorithmParameterSpec instanceof IESParameterSpec) {
            iESParameterSpec = (IESParameterSpec) algorithmParameterSpec;
        } else {
            throw new InvalidAlgorithmParameterException("must be passed IES parameters");
        }
        this.e = iESParameterSpec;
        if (i2 == 1 || i2 == 3) {
            if (key instanceof DHPublicKey) {
                asymmetricKeyParameter = DHUtil.generatePublicKeyParameter((PublicKey) key);
            } else if (key instanceof IESKey) {
                IESKey iESKey = (IESKey) key;
                this.f = DHUtil.generatePublicKeyParameter(iESKey.getPublic());
                this.i = DHUtil.generatePrivateKeyParameter(iESKey.getPrivate());
                this.g = secureRandom;
                this.b = i2;
                this.c.reset();
            } else {
                throw new InvalidKeyException("must be passed recipient's public DH key for encryption");
            }
        } else if (i2 == 2 || i2 == 4) {
            if (key instanceof DHPrivateKey) {
                privateKey = (PrivateKey) key;
            } else if (key instanceof IESKey) {
                IESKey iESKey2 = (IESKey) key;
                this.i = DHUtil.generatePublicKeyParameter(iESKey2.getPublic());
                privateKey = iESKey2.getPrivate();
            } else {
                throw new InvalidKeyException("must be passed recipient's private DH key for decryption");
            }
            asymmetricKeyParameter = DHUtil.generatePrivateKeyParameter(privateKey);
        } else {
            throw new InvalidKeyException("must be passed EC key");
        }
        this.f = asymmetricKeyParameter;
        this.g = secureRandom;
        this.b = i2;
        this.c.reset();
    }

    public void engineSetMode(String str) {
        boolean z;
        String upperCase = Strings.toUpperCase(str);
        if (upperCase.equals("NONE")) {
            z = false;
        } else if (upperCase.equals("DHAES")) {
            z = true;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("can't support mode ");
            sb.append(str);
            throw new IllegalArgumentException(sb.toString());
        }
        this.h = z;
    }

    public void engineSetPadding(String str) {
        String upperCase = Strings.toUpperCase(str);
        if (!upperCase.equals("NOPADDING") && !upperCase.equals("PKCS5PADDING") && !upperCase.equals("PKCS7PADDING")) {
            throw new NoSuchPaddingException("padding not available with IESCipher");
        }
    }

    public int engineUpdate(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        this.c.write(bArr, i2, i3);
        return 0;
    }

    public byte[] engineUpdate(byte[] bArr, int i2, int i3) {
        this.c.write(bArr, i2, i3);
        return null;
    }
}
