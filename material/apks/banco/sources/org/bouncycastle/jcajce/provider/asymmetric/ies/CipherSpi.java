package org.bouncycastle.jcajce.provider.asymmetric.ies;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.interfaces.DHPrivateKey;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.agreement.DHBasicAgreement;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.engines.IESEngine;
import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.IESParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.DHUtil;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.interfaces.IESKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.IESParameterSpec;

public class CipherSpi extends javax.crypto.CipherSpi {
    private IESEngine a;
    private int b = -1;
    private ByteArrayOutputStream c = new ByteArrayOutputStream();
    private AlgorithmParameters d = null;
    private IESParameterSpec e = null;
    private Class[] f = {IESParameterSpec.class};

    public static class IES extends CipherSpi {
        public IES() {
            super(new IESEngine(new DHBasicAgreement(), new KDF2BytesGenerator(new SHA1Digest()), new HMac(new SHA1Digest())));
        }
    }

    public CipherSpi(IESEngine iESEngine) {
        this.a = iESEngine;
    }

    /* access modifiers changed from: protected */
    public int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (i2 != 0) {
            this.c.write(bArr, i, i2);
        }
        try {
            byte[] byteArray = this.c.toByteArray();
            this.c.reset();
            byte[] processBlock = this.a.processBlock(byteArray, 0, byteArray.length);
            System.arraycopy(processBlock, 0, bArr2, i3, processBlock.length);
            return processBlock.length;
        } catch (InvalidCipherTextException e2) {
            throw new BadPaddingException(e2.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineDoFinal(byte[] bArr, int i, int i2) {
        if (i2 != 0) {
            this.c.write(bArr, i, i2);
        }
        try {
            byte[] byteArray = this.c.toByteArray();
            this.c.reset();
            return this.a.processBlock(byteArray, 0, byteArray.length);
        } catch (InvalidCipherTextException e2) {
            throw new BadPaddingException(e2.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public int engineGetBlockSize() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetIV() {
        return null;
    }

    /* access modifiers changed from: protected */
    public int engineGetKeySize(Key key) {
        BigInteger d2;
        if (!(key instanceof IESKey)) {
            throw new IllegalArgumentException("must be passed IE key");
        }
        IESKey iESKey = (IESKey) key;
        if (iESKey.getPrivate() instanceof DHPrivateKey) {
            d2 = ((DHPrivateKey) iESKey.getPrivate()).getX();
        } else if (iESKey.getPrivate() instanceof ECPrivateKey) {
            d2 = ((ECPrivateKey) iESKey.getPrivate()).getD();
        } else {
            throw new IllegalArgumentException("not an IE key!");
        }
        return d2.bitLength();
    }

    /* access modifiers changed from: protected */
    public int engineGetOutputSize(int i) {
        if (this.b == 1 || this.b == 3) {
            return this.c.size() + i + 20;
        }
        if (this.b == 2 || this.b == 4) {
            return (this.c.size() + i) - 20;
        }
        throw new IllegalStateException("cipher not initialised");
    }

    /* access modifiers changed from: protected */
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

    /* access modifiers changed from: protected */
    public void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) {
        AlgorithmParameterSpec algorithmParameterSpec = null;
        if (algorithmParameters != null) {
            int i2 = 0;
            while (true) {
                if (i2 == this.f.length) {
                    break;
                }
                try {
                    algorithmParameterSpec = algorithmParameters.getParameterSpec(this.f[i2]);
                    break;
                } catch (Exception unused) {
                    i2++;
                }
            }
            if (algorithmParameterSpec == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("can't handle parameter ");
                sb.append(algorithmParameters.toString());
                throw new InvalidAlgorithmParameterException(sb.toString());
            }
        }
        this.d = algorithmParameters;
        engineInit(i, key, algorithmParameterSpec, secureRandom);
    }

    /* access modifiers changed from: protected */
    public void engineInit(int i, Key key, SecureRandom secureRandom) {
        if (i == 1 || i == 3) {
            try {
                engineInit(i, key, (AlgorithmParameterSpec) null, secureRandom);
                return;
            } catch (InvalidAlgorithmParameterException unused) {
            }
        }
        throw new IllegalArgumentException("can't handle null parameter spec in IES");
    }

    /* access modifiers changed from: protected */
    public void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        AsymmetricKeyParameter asymmetricKeyParameter;
        AsymmetricKeyParameter asymmetricKeyParameter2;
        IESEngine iESEngine;
        if (!(key instanceof IESKey)) {
            throw new InvalidKeyException("must be passed IES key");
        }
        boolean z = true;
        if (algorithmParameterSpec == null && (i == 1 || i == 3)) {
            byte[] bArr = new byte[16];
            byte[] bArr2 = new byte[16];
            if (secureRandom == null) {
                secureRandom = new SecureRandom();
            }
            secureRandom.nextBytes(bArr);
            secureRandom.nextBytes(bArr2);
            algorithmParameterSpec = new IESParameterSpec(bArr, bArr2, 128);
        } else if (!(algorithmParameterSpec instanceof IESParameterSpec)) {
            throw new InvalidAlgorithmParameterException("must be passed IES parameters");
        }
        IESKey iESKey = (IESKey) key;
        if (iESKey.getPublic() instanceof ECPublicKey) {
            asymmetricKeyParameter = ECUtil.generatePublicKeyParameter(iESKey.getPublic());
            asymmetricKeyParameter2 = ECUtil.generatePrivateKeyParameter(iESKey.getPrivate());
        } else {
            asymmetricKeyParameter = DHUtil.generatePublicKeyParameter(iESKey.getPublic());
            asymmetricKeyParameter2 = DHUtil.generatePrivateKeyParameter(iESKey.getPrivate());
        }
        this.e = (IESParameterSpec) algorithmParameterSpec;
        IESParameters iESParameters = new IESParameters(this.e.getDerivationV(), this.e.getEncodingV(), this.e.getMacKeySize());
        this.b = i;
        this.c.reset();
        switch (i) {
            case 1:
            case 3:
                iESEngine = this.a;
                break;
            case 2:
            case 4:
                iESEngine = this.a;
                z = false;
                break;
            default:
                System.out.println("eeek!");
                return;
        }
        iESEngine.init(z, asymmetricKeyParameter2, asymmetricKeyParameter, iESParameters);
    }

    /* access modifiers changed from: protected */
    public void engineSetMode(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("can't support mode ");
        sb.append(str);
        throw new IllegalArgumentException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public void engineSetPadding(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" unavailable with RSA.");
        throw new NoSuchPaddingException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        this.c.write(bArr, i, i2);
        return 0;
    }

    /* access modifiers changed from: protected */
    public byte[] engineUpdate(byte[] bArr, int i, int i2) {
        this.c.write(bArr, i, i2);
        return null;
    }
}
