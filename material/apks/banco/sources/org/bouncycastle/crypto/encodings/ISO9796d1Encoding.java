package org.bouncycastle.crypto.encodings;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.math.BigInteger;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSAKeyParameters;

public class ISO9796d1Encoding implements AsymmetricBlockCipher {
    private static final BigInteger a = BigInteger.valueOf(16);
    private static final BigInteger b = BigInteger.valueOf(6);
    private static byte[] c = {Ascii.SO, 3, 5, 8, 9, 4, 2, Ascii.SI, 0, Ascii.CR, Ascii.VT, 6, 7, 10, Ascii.FF, 1};
    private static byte[] d = {8, Ascii.SI, 6, 1, 5, 2, Ascii.VT, Ascii.FF, 3, 4, Ascii.CR, 10, Ascii.SO, 9, 0, 7};
    private AsymmetricBlockCipher e;
    private boolean f;
    private int g;
    private int h = 0;
    private BigInteger i;

    public ISO9796d1Encoding(AsymmetricBlockCipher asymmetricBlockCipher) {
        this.e = asymmetricBlockCipher;
    }

    private static byte[] a(BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray[0] != 0) {
            return byteArray;
        }
        byte[] bArr = new byte[(byteArray.length - 1)];
        System.arraycopy(byteArray, 1, bArr, 0, bArr.length);
        return bArr;
    }

    private byte[] a(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[((this.g + 7) / 8)];
        int i4 = 1;
        int i5 = this.h + 1;
        int i6 = (this.g + 13) / 16;
        int i7 = 0;
        while (i7 < i6) {
            if (i7 > i6 - i3) {
                int i8 = i6 - i7;
                System.arraycopy(bArr, (i2 + i3) - i8, bArr2, bArr2.length - i6, i8);
            } else {
                System.arraycopy(bArr, i2, bArr2, bArr2.length - (i7 + i3), i3);
            }
            i7 += i3;
        }
        for (int length = bArr2.length - (i6 * 2); length != bArr2.length; length += 2) {
            byte b2 = bArr2[(bArr2.length - i6) + (length / 2)];
            bArr2[length] = (byte) ((c[(b2 & UnsignedBytes.MAX_VALUE) >>> 4] << 4) | c[b2 & Ascii.SI]);
            bArr2[length + 1] = b2;
        }
        int length2 = bArr2.length - (i3 * 2);
        bArr2[length2] = (byte) (bArr2[length2] ^ i5);
        bArr2[bArr2.length - 1] = (byte) ((bArr2[bArr2.length - 1] << 4) | 6);
        int i9 = 8 - ((this.g - 1) % 8);
        if (i9 != 8) {
            bArr2[0] = (byte) (bArr2[0] & (255 >>> i9));
            bArr2[0] = (byte) ((128 >>> i9) | bArr2[0]);
            i4 = 0;
        } else {
            bArr2[0] = 0;
            bArr2[1] = (byte) (bArr2[1] | UnsignedBytes.MAX_POWER_OF_TWO);
        }
        return this.e.processBlock(bArr2, i4, bArr2.length - i4);
    }

    private byte[] b(byte[] bArr, int i2, int i3) {
        byte[] processBlock = this.e.processBlock(bArr, i2, i3);
        int i4 = (this.g + 13) / 16;
        BigInteger bigInteger = new BigInteger(1, processBlock);
        if (!bigInteger.mod(a).equals(b)) {
            if (this.i.subtract(bigInteger).mod(a).equals(b)) {
                bigInteger = this.i.subtract(bigInteger);
            } else {
                throw new InvalidCipherTextException("resulting integer iS or (modulus - iS) is not congruent to 6 mod 16");
            }
        }
        byte[] a2 = a(bigInteger);
        if ((a2[a2.length - 1] & Ascii.SI) != 6) {
            throw new InvalidCipherTextException("invalid forcing byte in block");
        }
        a2[a2.length - 1] = (byte) (((a2[a2.length - 1] & UnsignedBytes.MAX_VALUE) >>> 4) | (d[(a2[a2.length - 2] & UnsignedBytes.MAX_VALUE) >> 4] << 4));
        a2[0] = (byte) ((c[(a2[1] & UnsignedBytes.MAX_VALUE) >>> 4] << 4) | c[a2[1] & Ascii.SI]);
        int i5 = 0;
        boolean z = false;
        byte b2 = 1;
        for (int length = a2.length - 1; length >= a2.length - (i4 * 2); length -= 2) {
            byte b3 = (c[(a2[length] & UnsignedBytes.MAX_VALUE) >>> 4] << 4) | c[a2[length] & Ascii.SI];
            int i6 = length - 1;
            if (((a2[i6] ^ b3) & UnsignedBytes.MAX_VALUE) != 0) {
                if (!z) {
                    b2 = (a2[i6] ^ b3) & UnsignedBytes.MAX_VALUE;
                    i5 = i6;
                    z = true;
                } else {
                    throw new InvalidCipherTextException("invalid tsums in block");
                }
            }
        }
        a2[i5] = 0;
        byte[] bArr2 = new byte[((a2.length - i5) / 2)];
        for (int i7 = 0; i7 < bArr2.length; i7++) {
            bArr2[i7] = a2[(i7 * 2) + i5 + 1];
        }
        this.h = b2 - 1;
        return bArr2;
    }

    public int getInputBlockSize() {
        int inputBlockSize = this.e.getInputBlockSize();
        return this.f ? (inputBlockSize + 1) / 2 : inputBlockSize;
    }

    public int getOutputBlockSize() {
        int outputBlockSize = this.e.getOutputBlockSize();
        return this.f ? outputBlockSize : (outputBlockSize + 1) / 2;
    }

    public int getPadBits() {
        return this.h;
    }

    public AsymmetricBlockCipher getUnderlyingCipher() {
        return this.e;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        RSAKeyParameters rSAKeyParameters = cipherParameters instanceof ParametersWithRandom ? (RSAKeyParameters) ((ParametersWithRandom) cipherParameters).getParameters() : (RSAKeyParameters) cipherParameters;
        this.e.init(z, cipherParameters);
        this.i = rSAKeyParameters.getModulus();
        this.g = this.i.bitLength();
        this.f = z;
    }

    public byte[] processBlock(byte[] bArr, int i2, int i3) {
        return this.f ? a(bArr, i2, i3) : b(bArr, i2, i3);
    }

    public void setPadBits(int i2) {
        if (i2 > 7) {
            throw new IllegalArgumentException("padBits > 7");
        }
        this.h = i2;
    }
}
