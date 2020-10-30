package org.bouncycastle.crypto.encodings;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;

public class PKCS1Encoding implements AsymmetricBlockCipher {
    public static final String STRICT_LENGTH_ENABLED_PROPERTY = "org.bouncycastle.pkcs1.strict";
    private SecureRandom a;
    private AsymmetricBlockCipher b;
    private boolean c;
    private boolean d;
    private boolean e;
    private int f = -1;
    private byte[] g = null;

    public PKCS1Encoding(AsymmetricBlockCipher asymmetricBlockCipher) {
        this.b = asymmetricBlockCipher;
        this.e = a();
    }

    public PKCS1Encoding(AsymmetricBlockCipher asymmetricBlockCipher, int i) {
        this.b = asymmetricBlockCipher;
        this.e = a();
        this.f = i;
    }

    public PKCS1Encoding(AsymmetricBlockCipher asymmetricBlockCipher, byte[] bArr) {
        this.b = asymmetricBlockCipher;
        this.e = a();
        this.g = bArr;
        this.f = bArr.length;
    }

    private static int a(byte[] bArr, int i) {
        int i2 = i + 1;
        int length = bArr.length - i2;
        byte b2 = 0 | (bArr[0] ^ 2);
        for (int i3 = 1; i3 < length; i3++) {
            byte b3 = bArr[i3];
            byte b4 = b3 | (b3 >> 1);
            byte b5 = b4 | (b4 >> 2);
            b2 |= ((b5 | (b5 >> 4)) & 1) - 1;
        }
        byte b6 = bArr[bArr.length - i2] | b2;
        byte b7 = b6 | (b6 >> 1);
        byte b8 = b7 | (b7 >> 2);
        return (((b8 | (b8 >> 4)) & 1) - 1) ^ -1;
    }

    private boolean a() {
        String str = (String) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                return System.getProperty(PKCS1Encoding.STRICT_LENGTH_ENABLED_PROPERTY);
            }
        });
        return str == null || str.equals("true");
    }

    private byte[] a(byte[] bArr, int i, int i2) {
        if (i2 > getInputBlockSize()) {
            throw new IllegalArgumentException("input data too large");
        }
        byte[] bArr2 = new byte[this.b.getInputBlockSize()];
        if (this.d) {
            bArr2[0] = 1;
            for (int i3 = 1; i3 != (bArr2.length - i2) - 1; i3++) {
                bArr2[i3] = -1;
            }
        } else {
            this.a.nextBytes(bArr2);
            bArr2[0] = 2;
            for (int i4 = 1; i4 != (bArr2.length - i2) - 1; i4++) {
                while (bArr2[i4] == 0) {
                    bArr2[i4] = (byte) this.a.nextInt();
                }
            }
        }
        bArr2[(bArr2.length - i2) - 1] = 0;
        System.arraycopy(bArr, i, bArr2, bArr2.length - i2, i2);
        return this.b.processBlock(bArr2, 0, bArr2.length);
    }

    private byte[] b(byte[] bArr, int i, int i2) {
        byte[] bArr2;
        if (!this.d) {
            throw new InvalidCipherTextException("sorry, this method is only for decryption, not for signing");
        }
        byte[] processBlock = this.b.processBlock(bArr, i, i2);
        if (this.g == null) {
            bArr2 = new byte[this.f];
            this.a.nextBytes(bArr2);
        } else {
            bArr2 = this.g;
        }
        if (processBlock.length < getOutputBlockSize()) {
            throw new InvalidCipherTextException("block truncated");
        } else if (!this.e || processBlock.length == this.b.getOutputBlockSize()) {
            int a2 = a(processBlock, this.f);
            byte[] bArr3 = new byte[this.f];
            for (int i3 = 0; i3 < this.f; i3++) {
                bArr3[i3] = (byte) ((processBlock[(processBlock.length - this.f) + i3] & (a2 ^ -1)) | (bArr2[i3] & a2));
            }
            return bArr3;
        } else {
            throw new InvalidCipherTextException("block incorrect size");
        }
    }

    private byte[] c(byte[] bArr, int i, int i2) {
        if (this.f != -1) {
            return b(bArr, i, i2);
        }
        byte[] processBlock = this.b.processBlock(bArr, i, i2);
        if (processBlock.length < getOutputBlockSize()) {
            throw new InvalidCipherTextException("block truncated");
        }
        byte b2 = processBlock[0];
        if (this.d) {
            if (b2 != 2) {
                throw new InvalidCipherTextException("unknown block type");
            }
        } else if (b2 != 1) {
            throw new InvalidCipherTextException("unknown block type");
        }
        if (!this.e || processBlock.length == this.b.getOutputBlockSize()) {
            int i3 = 1;
            while (i3 != processBlock.length) {
                byte b3 = processBlock[i3];
                if (b3 == 0) {
                    break;
                } else if (b2 != 1 || b3 == -1) {
                    i3++;
                } else {
                    throw new InvalidCipherTextException("block padding incorrect");
                }
            }
            int i4 = i3 + 1;
            if (i4 > processBlock.length || i4 < 10) {
                throw new InvalidCipherTextException("no data in block");
            }
            byte[] bArr2 = new byte[(processBlock.length - i4)];
            System.arraycopy(processBlock, i4, bArr2, 0, bArr2.length);
            return bArr2;
        }
        throw new InvalidCipherTextException("block incorrect size");
    }

    public int getInputBlockSize() {
        int inputBlockSize = this.b.getInputBlockSize();
        return this.c ? inputBlockSize - 10 : inputBlockSize;
    }

    public int getOutputBlockSize() {
        int outputBlockSize = this.b.getOutputBlockSize();
        return this.c ? outputBlockSize : outputBlockSize - 10;
    }

    public AsymmetricBlockCipher getUnderlyingCipher() {
        return this.b;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        AsymmetricKeyParameter asymmetricKeyParameter;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.a = parametersWithRandom.getRandom();
            asymmetricKeyParameter = (AsymmetricKeyParameter) parametersWithRandom.getParameters();
        } else {
            this.a = new SecureRandom();
            asymmetricKeyParameter = (AsymmetricKeyParameter) cipherParameters;
        }
        this.b.init(z, cipherParameters);
        this.d = asymmetricKeyParameter.isPrivate();
        this.c = z;
    }

    public byte[] processBlock(byte[] bArr, int i, int i2) {
        return this.c ? a(bArr, i, i2) : c(bArr, i, i2);
    }
}
