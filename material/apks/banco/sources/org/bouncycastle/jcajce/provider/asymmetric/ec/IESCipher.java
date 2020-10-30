package org.bouncycastle.jcajce.provider.asymmetric.ec;

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
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.KeyEncoder;
import org.bouncycastle.crypto.KeyParser;
import org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.engines.IESEngine;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.generators.EphemeralKeyPairGenerator;
import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.IESWithCipherParameters;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.parsers.ECIESPublicKeyParser;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jcajce.provider.asymmetric.util.IESUtil;
import org.bouncycastle.jce.interfaces.ECKey;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.interfaces.IESKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.IESParameterSpec;
import org.bouncycastle.util.Strings;

public class IESCipher extends CipherSpi {
    private int a;
    private IESEngine b;
    private int c = -1;
    private ByteArrayOutputStream d = new ByteArrayOutputStream();
    private AlgorithmParameters e = null;
    private IESParameterSpec f = null;
    private AsymmetricKeyParameter g;
    private SecureRandom h;
    private boolean i = false;
    private AsymmetricKeyParameter j = null;

    public static class ECIES extends IESCipher {
        public ECIES() {
            super(new IESEngine(new ECDHBasicAgreement(), new KDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest())));
        }
    }

    public static class ECIESwithAES extends IESCipher {
        public ECIESwithAES() {
            super(new IESEngine(new ECDHBasicAgreement(), new KDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest()), new PaddedBufferedBlockCipher(new AESEngine())));
        }
    }

    public static class ECIESwithAESCBC extends IESCipher {
        public ECIESwithAESCBC() {
            super(new IESEngine(new ECDHBasicAgreement(), new KDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest()), new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()))), 16);
        }
    }

    public static class ECIESwithDESede extends IESCipher {
        public ECIESwithDESede() {
            super(new IESEngine(new ECDHBasicAgreement(), new KDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest()), new PaddedBufferedBlockCipher(new DESedeEngine())));
        }
    }

    public static class ECIESwithDESedeCBC extends IESCipher {
        public ECIESwithDESedeCBC() {
            super(new IESEngine(new ECDHBasicAgreement(), new KDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest()), new PaddedBufferedBlockCipher(new CBCBlockCipher(new DESedeEngine()))), 8);
        }
    }

    public IESCipher(IESEngine iESEngine) {
        this.b = iESEngine;
        this.a = 0;
    }

    public IESCipher(IESEngine iESEngine, int i2) {
        this.b = iESEngine;
        this.a = i2;
    }

    public int engineDoFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        byte[] engineDoFinal = engineDoFinal(bArr, i2, i3);
        System.arraycopy(engineDoFinal, 0, bArr2, i4, engineDoFinal.length);
        return engineDoFinal.length;
    }

    public byte[] engineDoFinal(byte[] bArr, int i2, int i3) {
        if (i3 != 0) {
            this.d.write(bArr, i2, i3);
        }
        byte[] byteArray = this.d.toByteArray();
        this.d.reset();
        CipherParameters iESWithCipherParameters = new IESWithCipherParameters(this.f.getDerivationV(), this.f.getEncodingV(), this.f.getMacKeySize(), this.f.getCipherKeySize());
        if (this.f.getNonce() != null) {
            iESWithCipherParameters = new ParametersWithIV(iESWithCipherParameters, this.f.getNonce());
        }
        ECDomainParameters parameters = ((ECKeyParameters) this.g).getParameters();
        if (this.j != null) {
            try {
                if (this.c != 1) {
                    if (this.c != 3) {
                        this.b.init(false, this.g, this.j, iESWithCipherParameters);
                        return this.b.processBlock(byteArray, 0, byteArray.length);
                    }
                }
                this.b.init(true, this.j, this.g, iESWithCipherParameters);
                return this.b.processBlock(byteArray, 0, byteArray.length);
            } catch (Exception e2) {
                throw new BadPaddingException(e2.getMessage());
            }
        } else if (this.c == 1 || this.c == 3) {
            ECKeyPairGenerator eCKeyPairGenerator = new ECKeyPairGenerator();
            eCKeyPairGenerator.init(new ECKeyGenerationParameters(parameters, this.h));
            try {
                this.b.init(this.g, iESWithCipherParameters, new EphemeralKeyPairGenerator(eCKeyPairGenerator, new KeyEncoder() {
                    public byte[] getEncoded(AsymmetricKeyParameter asymmetricKeyParameter) {
                        return ((ECPublicKeyParameters) asymmetricKeyParameter).getQ().getEncoded();
                    }
                }));
                return this.b.processBlock(byteArray, 0, byteArray.length);
            } catch (Exception e3) {
                throw new BadPaddingException(e3.getMessage());
            }
        } else if (this.c == 2 || this.c == 4) {
            try {
                this.b.init(this.g, iESWithCipherParameters, (KeyParser) new ECIESPublicKeyParser(parameters));
                return this.b.processBlock(byteArray, 0, byteArray.length);
            } catch (InvalidCipherTextException e4) {
                throw new BadPaddingException(e4.getMessage());
            }
        } else {
            throw new IllegalStateException("cipher not initialised");
        }
    }

    public int engineGetBlockSize() {
        if (this.b.getCipher() != null) {
            return this.b.getCipher().getBlockSize();
        }
        return 0;
    }

    public byte[] engineGetIV() {
        return null;
    }

    public int engineGetKeySize(Key key) {
        if (key instanceof ECKey) {
            return ((ECKey) key).getParameters().getCurve().getFieldSize();
        }
        throw new IllegalArgumentException("not an EC key");
    }

    public int engineGetOutputSize(int i2) {
        int size;
        BufferedBlockCipher bufferedBlockCipher;
        int macSize = this.b.getMac().getMacSize();
        if (this.g != null) {
            int fieldSize = (((((ECKey) this.g).getParameters().getCurve().getFieldSize() + 7) * 2) / 8) + 1;
            if (this.b.getCipher() != null) {
                if (this.c == 1 || this.c == 3) {
                    bufferedBlockCipher = this.b.getCipher();
                } else if (this.c == 2 || this.c == 4) {
                    bufferedBlockCipher = this.b.getCipher();
                    i2 = (i2 - macSize) - fieldSize;
                } else {
                    throw new IllegalStateException("cipher not initialised");
                }
                i2 = bufferedBlockCipher.getOutputSize(i2);
            }
            if (this.c == 1 || this.c == 3) {
                size = this.d.size() + macSize + fieldSize;
            } else if (this.c == 2 || this.c == 4) {
                size = (this.d.size() - macSize) - fieldSize;
            } else {
                throw new IllegalStateException("cipher not initialised");
            }
            return size + i2;
        }
        throw new IllegalStateException("cipher not initialised");
    }

    public AlgorithmParameters engineGetParameters() {
        if (this.e == null && this.f != null) {
            try {
                this.e = AlgorithmParameters.getInstance("IES", BouncyCastleProvider.PROVIDER_NAME);
                this.e.init(this.f);
            } catch (Exception e2) {
                throw new RuntimeException(e2.toString());
            }
        }
        return this.e;
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
        this.e = algorithmParameters;
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
        this.j = null;
        if (algorithmParameterSpec == null) {
            iESParameterSpec = IESUtil.guessParameterSpec(this.b);
        } else if (algorithmParameterSpec instanceof IESParameterSpec) {
            iESParameterSpec = (IESParameterSpec) algorithmParameterSpec;
        } else {
            throw new InvalidAlgorithmParameterException("must be passed IES parameters");
        }
        this.f = iESParameterSpec;
        byte[] nonce = this.f.getNonce();
        if (nonce != null) {
            if (this.a == 0) {
                throw new InvalidAlgorithmParameterException("NONCE present in IES Parameters when none required");
            } else if (nonce.length != this.a) {
                StringBuilder sb = new StringBuilder();
                sb.append("NONCE in IES Parameters needs to be ");
                sb.append(this.a);
                sb.append(" bytes long");
                throw new InvalidAlgorithmParameterException(sb.toString());
            }
        }
        if (i2 == 1 || i2 == 3) {
            if (key instanceof ECPublicKey) {
                asymmetricKeyParameter = ECUtil.generatePublicKeyParameter((PublicKey) key);
            } else if (key instanceof IESKey) {
                IESKey iESKey = (IESKey) key;
                this.g = ECUtil.generatePublicKeyParameter(iESKey.getPublic());
                this.j = ECUtil.generatePrivateKeyParameter(iESKey.getPrivate());
                this.h = secureRandom;
                this.c = i2;
                this.d.reset();
            } else {
                throw new InvalidKeyException("must be passed recipient's public EC key for encryption");
            }
        } else if (i2 == 2 || i2 == 4) {
            if (key instanceof ECPrivateKey) {
                privateKey = (PrivateKey) key;
            } else if (key instanceof IESKey) {
                IESKey iESKey2 = (IESKey) key;
                this.j = ECUtil.generatePublicKeyParameter(iESKey2.getPublic());
                privateKey = iESKey2.getPrivate();
            } else {
                throw new InvalidKeyException("must be passed recipient's private EC key for decryption");
            }
            asymmetricKeyParameter = ECUtil.generatePrivateKeyParameter(privateKey);
        } else {
            throw new InvalidKeyException("must be passed EC key");
        }
        this.g = asymmetricKeyParameter;
        this.h = secureRandom;
        this.c = i2;
        this.d.reset();
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
        this.i = z;
    }

    public void engineSetPadding(String str) {
        String upperCase = Strings.toUpperCase(str);
        if (!upperCase.equals("NOPADDING") && !upperCase.equals("PKCS5PADDING") && !upperCase.equals("PKCS7PADDING")) {
            throw new NoSuchPaddingException("padding not available with IESCipher");
        }
    }

    public int engineUpdate(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        this.d.write(bArr, i2, i3);
        return 0;
    }

    public byte[] engineUpdate(byte[] bArr, int i2, int i3) {
        this.d.write(bArr, i2, i3);
        return null;
    }
}
