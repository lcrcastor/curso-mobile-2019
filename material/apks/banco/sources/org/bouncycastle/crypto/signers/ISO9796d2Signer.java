package org.bouncycastle.crypto.signers;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.Hashtable;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.SignerWithRecovery;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;

public class ISO9796d2Signer implements SignerWithRecovery {
    public static final int TRAILER_IMPLICIT = 188;
    public static final int TRAILER_RIPEMD128 = 13004;
    public static final int TRAILER_RIPEMD160 = 12748;
    public static final int TRAILER_SHA1 = 13260;
    public static final int TRAILER_SHA256 = 13516;
    public static final int TRAILER_SHA384 = 14028;
    public static final int TRAILER_SHA512 = 13772;
    public static final int TRAILER_WHIRLPOOL = 14284;
    private static Hashtable a = new Hashtable();
    private Digest b;
    private AsymmetricBlockCipher c;
    private int d;
    private int e;
    private byte[] f;
    private byte[] g;
    private int h;
    private boolean i;
    private byte[] j;
    private byte[] k;
    private byte[] l;

    static {
        a.put("RIPEMD128", Integers.valueOf(13004));
        a.put("RIPEMD160", Integers.valueOf(12748));
        a.put(CommonUtils.SHA1_INSTANCE, Integers.valueOf(13260));
        a.put("SHA-256", Integers.valueOf(13516));
        a.put("SHA-384", Integers.valueOf(14028));
        a.put("SHA-512", Integers.valueOf(13772));
        a.put("Whirlpool", Integers.valueOf(14284));
    }

    public ISO9796d2Signer(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest) {
        this(asymmetricBlockCipher, digest, false);
    }

    public ISO9796d2Signer(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, boolean z) {
        int intValue;
        this.c = asymmetricBlockCipher;
        this.b = digest;
        if (z) {
            intValue = 188;
        } else {
            Integer num = (Integer) a.get(digest.getAlgorithmName());
            if (num != null) {
                intValue = num.intValue();
            } else {
                throw new IllegalArgumentException("no valid trailer for digest");
            }
        }
        this.d = intValue;
    }

    private void a(byte[] bArr) {
        for (int i2 = 0; i2 != bArr.length; i2++) {
            bArr[i2] = 0;
        }
    }

    private boolean a(byte[] bArr, byte[] bArr2) {
        boolean z = true;
        if (this.h > this.g.length) {
            if (this.g.length > bArr2.length) {
                z = false;
            }
            for (int i2 = 0; i2 != this.g.length; i2++) {
                if (bArr[i2] != bArr2[i2]) {
                    z = false;
                }
            }
        } else {
            if (this.h != bArr2.length) {
                z = false;
            }
            for (int i3 = 0; i3 != bArr2.length; i3++) {
                if (bArr[i3] != bArr2[i3]) {
                    z = false;
                }
            }
        }
        return z;
    }

    private boolean b(byte[] bArr) {
        a(this.g);
        a(bArr);
        return false;
    }

    public byte[] generateSignature() {
        int i2;
        int i3;
        int i4;
        byte b2;
        int digestSize = this.b.getDigestSize();
        if (this.d == 188) {
            int length = (this.f.length - digestSize) - 1;
            this.b.doFinal(this.f, length);
            this.f[this.f.length - 1] = PSSSigner.TRAILER_IMPLICIT;
            i2 = length;
            i3 = 8;
        } else {
            i3 = 16;
            i2 = (this.f.length - digestSize) - 2;
            this.b.doFinal(this.f, i2);
            this.f[this.f.length - 2] = (byte) (this.d >>> 8);
            this.f[this.f.length - 1] = (byte) this.d;
        }
        int i5 = ((((digestSize + this.h) * 8) + i3) + 4) - this.e;
        if (i5 > 0) {
            int i6 = this.h - ((i5 + 7) / 8);
            b2 = 96;
            i4 = i2 - i6;
            System.arraycopy(this.g, 0, this.f, i4, i6);
        } else {
            b2 = SignedBytes.MAX_POWER_OF_TWO;
            i4 = i2 - this.h;
            System.arraycopy(this.g, 0, this.f, i4, this.h);
        }
        int i7 = i4 - 1;
        if (i7 > 0) {
            for (int i8 = i7; i8 != 0; i8--) {
                this.f[i8] = -69;
            }
            byte[] bArr = this.f;
            bArr[i7] = (byte) (bArr[i7] ^ 1);
            this.f[0] = Ascii.VT;
            byte[] bArr2 = this.f;
            bArr2[0] = (byte) (b2 | bArr2[0]);
        } else {
            this.f[0] = 10;
            byte[] bArr3 = this.f;
            bArr3[0] = (byte) (b2 | bArr3[0]);
        }
        byte[] processBlock = this.c.processBlock(this.f, 0, this.f.length);
        a(this.g);
        a(this.f);
        return processBlock;
    }

    public byte[] getRecoveredMessage() {
        return this.j;
    }

    public boolean hasFullMessage() {
        return this.i;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        RSAKeyParameters rSAKeyParameters = (RSAKeyParameters) cipherParameters;
        this.c.init(z, rSAKeyParameters);
        this.e = rSAKeyParameters.getModulus().bitLength();
        this.f = new byte[((this.e + 7) / 8)];
        this.g = new byte[(this.d == 188 ? (this.f.length - this.b.getDigestSize()) - 2 : (this.f.length - this.b.getDigestSize()) - 3)];
        reset();
    }

    public void reset() {
        this.b.reset();
        this.h = 0;
        a(this.g);
        if (this.j != null) {
            a(this.j);
        }
        this.j = null;
        this.i = false;
        if (this.k != null) {
            this.k = null;
            a(this.l);
            this.l = null;
        }
    }

    public void update(byte b2) {
        this.b.update(b2);
        if (this.h < this.g.length) {
            this.g[this.h] = b2;
        }
        this.h++;
    }

    public void update(byte[] bArr, int i2, int i3) {
        while (i3 > 0 && this.h < this.g.length) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
        this.b.update(bArr, i2, i3);
        this.h += i3;
    }

    public void updateWithRecoveredMessage(byte[] bArr) {
        byte[] bArr2;
        int length;
        byte[] processBlock = this.c.processBlock(bArr, 0, bArr.length);
        if (((processBlock[0] & 192) ^ SignedBytes.MAX_POWER_OF_TWO) != 0) {
            throw new InvalidCipherTextException("malformed signature");
        } else if (((processBlock[processBlock.length - 1] & Ascii.SI) ^ Ascii.FF) != 0) {
            throw new InvalidCipherTextException("malformed signature");
        } else {
            int i2 = 2;
            if (((processBlock[processBlock.length - 1] & UnsignedBytes.MAX_VALUE) ^ PSSSigner.TRAILER_IMPLICIT) == 0) {
                i2 = 1;
            } else {
                byte b2 = ((processBlock[processBlock.length - 2] & UnsignedBytes.MAX_VALUE) << 8) | (processBlock[processBlock.length - 1] & UnsignedBytes.MAX_VALUE);
                Integer num = (Integer) a.get(this.b.getAlgorithmName());
                if (num == null) {
                    throw new IllegalArgumentException("unrecognised hash in signature");
                } else if (b2 != num.intValue()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("signer initialised with wrong digest for trailer ");
                    sb.append(b2);
                    throw new IllegalStateException(sb.toString());
                }
            }
            int i3 = 0;
            while (i3 != processBlock.length && ((processBlock[i3] & Ascii.SI) ^ 10) != 0) {
                i3++;
            }
            int i4 = i3 + 1;
            int length2 = ((processBlock.length - i2) - this.b.getDigestSize()) - i4;
            if (length2 <= 0) {
                throw new InvalidCipherTextException("malformed block");
            }
            if ((processBlock[0] & 32) == 0) {
                this.i = true;
                this.j = new byte[length2];
                bArr2 = this.j;
                length = this.j.length;
            } else {
                this.i = false;
                this.j = new byte[length2];
                bArr2 = this.j;
                length = this.j.length;
            }
            System.arraycopy(processBlock, i4, bArr2, 0, length);
            this.k = bArr;
            this.l = processBlock;
            this.b.update(this.j, 0, this.j.length);
            this.h = this.j.length;
            System.arraycopy(this.j, 0, this.g, 0, this.j.length);
        }
    }

    public boolean verifySignature(byte[] bArr) {
        byte[] bArr2;
        int i2;
        byte[] bArr3;
        if (this.k == null) {
            try {
                bArr2 = this.c.processBlock(bArr, 0, bArr.length);
            } catch (Exception unused) {
                return false;
            }
        } else if (!Arrays.areEqual(this.k, bArr)) {
            throw new IllegalStateException("updateWithRecoveredMessage called on different signature");
        } else {
            bArr2 = this.l;
            this.k = null;
            this.l = null;
        }
        if (((bArr2[0] & 192) ^ SignedBytes.MAX_POWER_OF_TWO) != 0) {
            return b(bArr2);
        }
        if (((bArr2[bArr2.length - 1] & Ascii.SI) ^ Ascii.FF) != 0) {
            return b(bArr2);
        }
        int i3 = 2;
        if (((bArr2[bArr2.length - 1] & UnsignedBytes.MAX_VALUE) ^ PSSSigner.TRAILER_IMPLICIT) == 0) {
            i3 = 1;
        } else {
            byte b2 = ((bArr2[bArr2.length - 2] & UnsignedBytes.MAX_VALUE) << 8) | (bArr2[bArr2.length - 1] & UnsignedBytes.MAX_VALUE);
            Integer num = (Integer) a.get(this.b.getAlgorithmName());
            if (num == null) {
                throw new IllegalArgumentException("unrecognised hash in signature");
            } else if (b2 != num.intValue()) {
                StringBuilder sb = new StringBuilder();
                sb.append("signer initialised with wrong digest for trailer ");
                sb.append(b2);
                throw new IllegalStateException(sb.toString());
            }
        }
        int i4 = 0;
        while (i4 != bArr2.length && ((bArr2[i4] & Ascii.SI) ^ 10) != 0) {
            i4++;
        }
        int i5 = i4 + 1;
        byte[] bArr4 = new byte[this.b.getDigestSize()];
        int length = (bArr2.length - i3) - bArr4.length;
        int i6 = length - i5;
        if (i6 <= 0) {
            return b(bArr2);
        }
        if ((bArr2[0] & 32) == 0) {
            this.i = true;
            if (this.h > i6) {
                return b(bArr2);
            }
            this.b.reset();
            this.b.update(bArr2, i5, i6);
            this.b.doFinal(bArr4, 0);
            boolean z = true;
            for (int i7 = 0; i7 != bArr4.length; i7++) {
                int i8 = length + i7;
                bArr2[i8] = (byte) (bArr2[i8] ^ bArr4[i7]);
                if (bArr2[i8] != 0) {
                    z = false;
                }
            }
            if (!z) {
                return b(bArr2);
            }
            this.j = new byte[i6];
            bArr3 = this.j;
            i2 = this.j.length;
        } else {
            this.i = false;
            this.b.doFinal(bArr4, 0);
            boolean z2 = true;
            for (int i9 = 0; i9 != bArr4.length; i9++) {
                int i10 = length + i9;
                bArr2[i10] = (byte) (bArr2[i10] ^ bArr4[i9]);
                if (bArr2[i10] != 0) {
                    z2 = false;
                }
            }
            if (!z2) {
                return b(bArr2);
            }
            this.j = new byte[i6];
            bArr3 = this.j;
            i2 = this.j.length;
        }
        System.arraycopy(bArr2, i5, bArr3, 0, i2);
        if (this.h != 0 && !a(this.g, this.j)) {
            return b(bArr2);
        }
        a(this.g);
        a(bArr2);
        return true;
    }
}
