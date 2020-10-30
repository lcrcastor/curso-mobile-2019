package org.bouncycastle.jcajce.provider.symmetric.util;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class BaseStreamCipher extends BaseWrapCipher implements PBE {
    private Class[] a = {RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
    private StreamCipher b;
    private ParametersWithIV c;
    private int d = 0;
    private PBEParameterSpec e = null;
    private String f = null;

    protected BaseStreamCipher(StreamCipher streamCipher, int i) {
        this.b = streamCipher;
        this.d = i;
    }

    /* access modifiers changed from: protected */
    public int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (i2 != 0) {
            this.b.processBytes(bArr, i, i2, bArr2, i3);
        }
        this.b.reset();
        return i2;
    }

    /* access modifiers changed from: protected */
    public byte[] engineDoFinal(byte[] bArr, int i, int i2) {
        if (i2 != 0) {
            byte[] engineUpdate = engineUpdate(bArr, i, i2);
            this.b.reset();
            return engineUpdate;
        }
        this.b.reset();
        return new byte[0];
    }

    /* access modifiers changed from: protected */
    public int engineGetBlockSize() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetIV() {
        if (this.c != null) {
            return this.c.getIV();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public int engineGetKeySize(Key key) {
        return key.getEncoded().length * 8;
    }

    /* access modifiers changed from: protected */
    public int engineGetOutputSize(int i) {
        return i;
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGetParameters() {
        if (this.engineParams != null || this.e == null) {
            return this.engineParams;
        }
        try {
            AlgorithmParameters instance = AlgorithmParameters.getInstance(this.f, BouncyCastleProvider.PROVIDER_NAME);
            instance.init(this.e);
            return instance;
        } catch (Exception unused) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) {
        AlgorithmParameterSpec algorithmParameterSpec = null;
        if (algorithmParameters != null) {
            int i2 = 0;
            while (true) {
                if (i2 == this.a.length) {
                    break;
                }
                try {
                    algorithmParameterSpec = algorithmParameters.getParameterSpec(this.a[i2]);
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
        engineInit(i, key, algorithmParameterSpec, secureRandom);
        this.engineParams = algorithmParameters;
    }

    /* access modifiers changed from: protected */
    public void engineInit(int i, Key key, SecureRandom secureRandom) {
        try {
            engineInit(i, key, (AlgorithmParameterSpec) null, secureRandom);
        } catch (InvalidAlgorithmParameterException e2) {
            throw new InvalidKeyException(e2.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        CipherParameters cipherParameters;
        this.e = null;
        this.f = null;
        this.engineParams = null;
        if (!(key instanceof SecretKey)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Key for algorithm ");
            sb.append(key.getAlgorithm());
            sb.append(" not suitable for symmetric enryption.");
            throw new InvalidKeyException(sb.toString());
        }
        if (key instanceof BCPBEKey) {
            BCPBEKey bCPBEKey = (BCPBEKey) key;
            this.f = bCPBEKey.getOID() != null ? bCPBEKey.getOID().getId() : bCPBEKey.getAlgorithm();
            if (bCPBEKey.getParam() != null) {
                cipherParameters = bCPBEKey.getParam();
                this.e = new PBEParameterSpec(bCPBEKey.getSalt(), bCPBEKey.getIterationCount());
            } else if (algorithmParameterSpec instanceof PBEParameterSpec) {
                CipherParameters makePBEParameters = Util.makePBEParameters(bCPBEKey, algorithmParameterSpec, this.b.getAlgorithmName());
                this.e = (PBEParameterSpec) algorithmParameterSpec;
                cipherParameters = makePBEParameters;
            } else {
                throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
            }
            if (bCPBEKey.getIvSize() != 0) {
                this.c = (ParametersWithIV) cipherParameters;
            }
        } else if (algorithmParameterSpec == null) {
            cipherParameters = new KeyParameter(key.getEncoded());
        } else if (algorithmParameterSpec instanceof IvParameterSpec) {
            CipherParameters parametersWithIV = new ParametersWithIV(new KeyParameter(key.getEncoded()), ((IvParameterSpec) algorithmParameterSpec).getIV());
            this.c = (ParametersWithIV) parametersWithIV;
            cipherParameters = parametersWithIV;
        } else {
            throw new InvalidAlgorithmParameterException("unknown parameter type.");
        }
        if (this.d != 0 && !(cipherParameters instanceof ParametersWithIV)) {
            if (secureRandom == null) {
                secureRandom = new SecureRandom();
            }
            if (i == 1 || i == 3) {
                byte[] bArr = new byte[this.d];
                secureRandom.nextBytes(bArr);
                ParametersWithIV parametersWithIV2 = new ParametersWithIV(cipherParameters, bArr);
                this.c = parametersWithIV2;
                cipherParameters = parametersWithIV2;
            } else {
                throw new InvalidAlgorithmParameterException("no IV set when one expected");
            }
        }
        switch (i) {
            case 1:
            case 3:
                this.b.init(true, cipherParameters);
                return;
            case 2:
            case 4:
                this.b.init(false, cipherParameters);
                return;
            default:
                try {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("unknown opmode ");
                    sb2.append(i);
                    sb2.append(" passed");
                    throw new InvalidParameterException(sb2.toString());
                } catch (Exception e2) {
                    throw new InvalidKeyException(e2.getMessage());
                }
        }
    }

    /* access modifiers changed from: protected */
    public void engineSetMode(String str) {
        if (!str.equalsIgnoreCase("ECB")) {
            StringBuilder sb = new StringBuilder();
            sb.append("can't support mode ");
            sb.append(str);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void engineSetPadding(String str) {
        if (!str.equalsIgnoreCase("NoPadding")) {
            StringBuilder sb = new StringBuilder();
            sb.append("Padding ");
            sb.append(str);
            sb.append(" unknown.");
            throw new NoSuchPaddingException(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        try {
            this.b.processBytes(bArr, i, i2, bArr2, i3);
            return i2;
        } catch (DataLengthException e2) {
            throw new ShortBufferException(e2.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineUpdate(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        this.b.processBytes(bArr, i, i2, bArr2, 0);
        return bArr2;
    }
}
