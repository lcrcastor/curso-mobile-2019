package org.bouncycastle.pqc.jcajce.provider.util;

import java.io.ByteArrayOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;

public abstract class AsymmetricBlockCipher extends CipherSpiExt {
    protected ByteArrayOutputStream buf = new ByteArrayOutputStream();
    protected int cipherTextSize;
    protected int maxPlainTextSize;
    protected AlgorithmParameterSpec paramSpec;

    /* access modifiers changed from: protected */
    public void checkLength(int i) {
        int size = i + this.buf.size();
        if (this.opMode == 1) {
            if (size > this.maxPlainTextSize) {
                StringBuilder sb = new StringBuilder();
                sb.append("The length of the plaintext (");
                sb.append(size);
                sb.append(" bytes) is not supported by ");
                sb.append("the cipher (max. ");
                sb.append(this.maxPlainTextSize);
                sb.append(" bytes).");
                throw new IllegalBlockSizeException(sb.toString());
            }
        } else if (this.opMode == 2 && size != this.cipherTextSize) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Illegal ciphertext length (expected ");
            sb2.append(this.cipherTextSize);
            sb2.append(" bytes, was ");
            sb2.append(size);
            sb2.append(" bytes).");
            throw new IllegalBlockSizeException(sb2.toString());
        }
    }

    public final int doFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (bArr2.length < getOutputSize(i2)) {
            throw new ShortBufferException("Output buffer too short.");
        }
        byte[] doFinal = doFinal(bArr, i, i2);
        System.arraycopy(doFinal, 0, bArr2, i3, doFinal.length);
        return doFinal.length;
    }

    public final byte[] doFinal(byte[] bArr, int i, int i2) {
        checkLength(i2);
        update(bArr, i, i2);
        byte[] byteArray = this.buf.toByteArray();
        this.buf.reset();
        switch (this.opMode) {
            case 1:
                return messageEncrypt(byteArray);
            case 2:
                return messageDecrypt(byteArray);
            default:
                return null;
        }
    }

    public final int getBlockSize() {
        return this.opMode == 1 ? this.maxPlainTextSize : this.cipherTextSize;
    }

    public final byte[] getIV() {
        return null;
    }

    public final int getOutputSize(int i) {
        int size = i + this.buf.size();
        int blockSize = getBlockSize();
        if (size > blockSize) {
            return 0;
        }
        return blockSize;
    }

    public final AlgorithmParameterSpec getParameters() {
        return this.paramSpec;
    }

    public abstract void initCipherDecrypt(Key key, AlgorithmParameterSpec algorithmParameterSpec);

    public abstract void initCipherEncrypt(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom);

    public final void initDecrypt(Key key) {
        try {
            initDecrypt(key, null);
        } catch (InvalidAlgorithmParameterException unused) {
            throw new InvalidParameterException("This cipher needs algorithm parameters for initialization (cannot be null).");
        }
    }

    public final void initDecrypt(Key key, AlgorithmParameterSpec algorithmParameterSpec) {
        this.opMode = 2;
        initCipherDecrypt(key, algorithmParameterSpec);
    }

    public final void initEncrypt(Key key) {
        try {
            initEncrypt(key, null, new SecureRandom());
        } catch (InvalidAlgorithmParameterException unused) {
            throw new InvalidParameterException("This cipher needs algorithm parameters for initialization (cannot be null).");
        }
    }

    public final void initEncrypt(Key key, SecureRandom secureRandom) {
        try {
            initEncrypt(key, null, secureRandom);
        } catch (InvalidAlgorithmParameterException unused) {
            throw new InvalidParameterException("This cipher needs algorithm parameters for initialization (cannot be null).");
        }
    }

    public final void initEncrypt(Key key, AlgorithmParameterSpec algorithmParameterSpec) {
        initEncrypt(key, algorithmParameterSpec, new SecureRandom());
    }

    public final void initEncrypt(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        this.opMode = 1;
        initCipherEncrypt(key, algorithmParameterSpec, secureRandom);
    }

    public abstract byte[] messageDecrypt(byte[] bArr);

    public abstract byte[] messageEncrypt(byte[] bArr);

    /* access modifiers changed from: protected */
    public final void setMode(String str) {
    }

    /* access modifiers changed from: protected */
    public final void setPadding(String str) {
    }

    public final int update(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        update(bArr, i, i2);
        return 0;
    }

    public final byte[] update(byte[] bArr, int i, int i2) {
        if (i2 != 0) {
            this.buf.write(bArr, i, i2);
        }
        return new byte[0];
    }
}
