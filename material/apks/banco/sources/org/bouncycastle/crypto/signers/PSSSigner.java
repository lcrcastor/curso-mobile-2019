package org.bouncycastle.crypto.signers;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSABlindingParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;

public class PSSSigner implements Signer {
    public static final byte TRAILER_IMPLICIT = -68;
    private Digest a;
    private Digest b;
    private AsymmetricBlockCipher c;
    private SecureRandom d;
    private int e;
    private int f;
    private int g;
    private int h;
    private byte[] i;
    private byte[] j;
    private byte[] k;
    private byte l;

    public PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, int i2) {
        this(asymmetricBlockCipher, digest, i2, (byte) TRAILER_IMPLICIT);
    }

    public PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, int i2, byte b2) {
        this(asymmetricBlockCipher, digest, digest, i2, b2);
    }

    public PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, Digest digest2, int i2) {
        this(asymmetricBlockCipher, digest, digest2, i2, TRAILER_IMPLICIT);
    }

    public PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, Digest digest2, int i2, byte b2) {
        this.c = asymmetricBlockCipher;
        this.a = digest;
        this.b = digest2;
        this.e = digest.getDigestSize();
        this.f = digest2.getDigestSize();
        this.g = i2;
        this.i = new byte[i2];
        this.j = new byte[(i2 + 8 + this.e)];
        this.l = b2;
    }

    private void a(int i2, byte[] bArr) {
        bArr[0] = (byte) (i2 >>> 24);
        bArr[1] = (byte) (i2 >>> 16);
        bArr[2] = (byte) (i2 >>> 8);
        bArr[3] = (byte) (i2 >>> 0);
    }

    private void a(byte[] bArr) {
        for (int i2 = 0; i2 != bArr.length; i2++) {
            bArr[i2] = 0;
        }
    }

    private byte[] a(byte[] bArr, int i2, int i3, int i4) {
        byte[] bArr2 = new byte[i4];
        byte[] bArr3 = new byte[this.f];
        byte[] bArr4 = new byte[4];
        this.b.reset();
        int i5 = 0;
        while (i5 < i4 / this.f) {
            a(i5, bArr4);
            this.b.update(bArr, i2, i3);
            this.b.update(bArr4, 0, bArr4.length);
            this.b.doFinal(bArr3, 0);
            System.arraycopy(bArr3, 0, bArr2, this.f * i5, this.f);
            i5++;
        }
        if (this.f * i5 < i4) {
            a(i5, bArr4);
            this.b.update(bArr, i2, i3);
            this.b.update(bArr4, 0, bArr4.length);
            this.b.doFinal(bArr3, 0);
            System.arraycopy(bArr3, 0, bArr2, this.f * i5, bArr2.length - (i5 * this.f));
        }
        return bArr2;
    }

    public byte[] generateSignature() {
        this.a.doFinal(this.j, (this.j.length - this.e) - this.g);
        if (this.g != 0) {
            this.d.nextBytes(this.i);
            System.arraycopy(this.i, 0, this.j, this.j.length - this.g, this.g);
        }
        byte[] bArr = new byte[this.e];
        this.a.update(this.j, 0, this.j.length);
        this.a.doFinal(bArr, 0);
        this.k[(((this.k.length - this.g) - 1) - this.e) - 1] = 1;
        System.arraycopy(this.i, 0, this.k, ((this.k.length - this.g) - this.e) - 1, this.g);
        byte[] a2 = a(bArr, 0, bArr.length, (this.k.length - this.e) - 1);
        for (int i2 = 0; i2 != a2.length; i2++) {
            byte[] bArr2 = this.k;
            bArr2[i2] = (byte) (bArr2[i2] ^ a2[i2]);
        }
        byte[] bArr3 = this.k;
        bArr3[0] = (byte) (bArr3[0] & (255 >> ((this.k.length * 8) - this.h)));
        System.arraycopy(bArr, 0, this.k, (this.k.length - this.e) - 1, this.e);
        this.k[this.k.length - 1] = this.l;
        byte[] processBlock = this.c.processBlock(this.k, 0, this.k.length);
        a(this.k);
        return processBlock;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            CipherParameters parameters = parametersWithRandom.getParameters();
            this.d = parametersWithRandom.getRandom();
            cipherParameters = parameters;
        } else if (z) {
            this.d = new SecureRandom();
        }
        this.c.init(z, cipherParameters);
        this.h = (cipherParameters instanceof RSABlindingParameters ? ((RSABlindingParameters) cipherParameters).getPublicKey() : (RSAKeyParameters) cipherParameters).getModulus().bitLength() - 1;
        if (this.h < (this.e * 8) + (this.g * 8) + 9) {
            throw new IllegalArgumentException("key too small for specified hash and salt lengths");
        }
        this.k = new byte[((this.h + 7) / 8)];
        reset();
    }

    public void reset() {
        this.a.reset();
    }

    public void update(byte b2) {
        this.a.update(b2);
    }

    public void update(byte[] bArr, int i2, int i3) {
        this.a.update(bArr, i2, i3);
    }

    public boolean verifySignature(byte[] bArr) {
        this.a.doFinal(this.j, (this.j.length - this.e) - this.g);
        try {
            byte[] processBlock = this.c.processBlock(bArr, 0, bArr.length);
            System.arraycopy(processBlock, 0, this.k, this.k.length - processBlock.length, processBlock.length);
            if (this.k[this.k.length - 1] == this.l) {
                byte[] a2 = a(this.k, (this.k.length - this.e) - 1, this.e, (this.k.length - this.e) - 1);
                for (int i2 = 0; i2 != a2.length; i2++) {
                    byte[] bArr2 = this.k;
                    bArr2[i2] = (byte) (bArr2[i2] ^ a2[i2]);
                }
                byte[] bArr3 = this.k;
                bArr3[0] = (byte) (bArr3[0] & (255 >> ((this.k.length * 8) - this.h)));
                int i3 = 0;
                while (true) {
                    if (i3 != ((this.k.length - this.e) - this.g) - 2) {
                        if (this.k[i3] != 0) {
                            break;
                        }
                        i3++;
                    } else if (this.k[((this.k.length - this.e) - this.g) - 2] == 1) {
                        System.arraycopy(this.k, ((this.k.length - this.g) - this.e) - 1, this.j, this.j.length - this.g, this.g);
                        this.a.update(this.j, 0, this.j.length);
                        this.a.doFinal(this.j, this.j.length - this.e);
                        int length = (this.k.length - this.e) - 1;
                        int length2 = this.j.length - this.e;
                        while (length2 != this.j.length) {
                            if ((this.k[length] ^ this.j[length2]) != 0) {
                                a(this.j);
                            } else {
                                length++;
                                length2++;
                            }
                        }
                        a(this.j);
                        a(this.k);
                        return true;
                    }
                }
            }
            a(this.k);
            return false;
        } catch (Exception unused) {
            return false;
        }
    }
}
