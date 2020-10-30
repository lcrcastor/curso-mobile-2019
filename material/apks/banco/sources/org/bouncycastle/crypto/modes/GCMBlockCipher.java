package org.bouncycastle.crypto.modes;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.modes.gcm.GCMExponentiator;
import org.bouncycastle.crypto.modes.gcm.GCMMultiplier;
import org.bouncycastle.crypto.modes.gcm.Tables1kGCMExponentiator;
import org.bouncycastle.crypto.modes.gcm.Tables8kGCMMultiplier;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class GCMBlockCipher implements AEADBlockCipher {
    private BlockCipher a;
    private GCMMultiplier b;
    private GCMExponentiator c;
    private boolean d;
    private int e;
    private byte[] f;
    private byte[] g;
    private byte[] h;
    private byte[] i;
    private byte[] j;
    private byte[] k;
    private byte[] l;
    private byte[] m;
    private byte[] n;
    private byte[] o;
    private int p;
    private long q;
    private byte[] r;
    private int s;
    private long t;
    private long u;

    public GCMBlockCipher(BlockCipher blockCipher) {
        this(blockCipher, null);
    }

    public GCMBlockCipher(BlockCipher blockCipher, GCMMultiplier gCMMultiplier) {
        if (blockCipher.getBlockSize() != 16) {
            throw new IllegalArgumentException("cipher required with a block size of 16.");
        }
        if (gCMMultiplier == null) {
            gCMMultiplier = new Tables8kGCMMultiplier();
        }
        this.a = blockCipher;
        this.b = gCMMultiplier;
    }

    private void a() {
        if (this.t > 0) {
            System.arraycopy(this.m, 0, this.n, 0, 16);
            this.u = this.t;
        }
        if (this.s > 0) {
            a(this.n, this.r, 0, this.s);
            this.u += (long) this.s;
        }
        if (this.u > 0) {
            System.arraycopy(this.n, 0, this.l, 0, 16);
        }
    }

    private void a(boolean z) {
        this.a.reset();
        this.l = new byte[16];
        this.m = new byte[16];
        this.n = new byte[16];
        this.r = new byte[16];
        this.s = 0;
        this.t = 0;
        this.u = 0;
        this.o = Arrays.clone(this.i);
        this.p = 0;
        this.q = 0;
        if (this.j != null) {
            Arrays.fill(this.j, 0);
        }
        if (z) {
            this.k = null;
        }
        if (this.g != null) {
            processAADBytes(this.g, 0, this.g.length);
        }
    }

    private static void a(byte[] bArr) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            byte b2 = bArr[i2] & UnsignedBytes.MAX_VALUE;
            bArr[i2] = (byte) (i3 | (b2 >>> 1));
            i2++;
            if (i2 != 16) {
                i3 = (b2 & 1) << 7;
            } else {
                return;
            }
        }
    }

    private void a(byte[] bArr, int i2) {
        if (bArr.length < i2 + 16) {
            throw new OutputLengthException("Output buffer too short");
        }
        if (this.q == 0) {
            a();
        }
        a(this.j, bArr, i2);
        if (this.d) {
            this.p = 0;
            return;
        }
        System.arraycopy(this.j, 16, this.j, 0, this.e);
        this.p = this.e;
    }

    private void a(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        byte[] b2 = b();
        b(b2, bArr, i2, i3);
        System.arraycopy(b2, 0, bArr2, i4, i3);
        byte[] bArr3 = this.l;
        if (this.d) {
            bArr = b2;
        }
        a(bArr3, bArr, 0, i3);
        this.q += (long) i3;
    }

    private void a(byte[] bArr, byte[] bArr2) {
        c(bArr, bArr2);
        this.b.multiplyH(bArr);
    }

    private void a(byte[] bArr, byte[] bArr2, int i2) {
        byte[] b2 = b();
        c(b2, bArr);
        System.arraycopy(b2, 0, bArr2, i2, 16);
        byte[] bArr3 = this.l;
        if (this.d) {
            bArr = b2;
        }
        a(bArr3, bArr);
        this.q += 16;
    }

    private void a(byte[] bArr, byte[] bArr2, int i2, int i3) {
        b(bArr, bArr2, i2, i3);
        this.b.multiplyH(bArr);
    }

    private static void b(byte[] bArr, byte[] bArr2) {
        byte[] clone = Arrays.clone(bArr);
        byte[] bArr3 = new byte[16];
        for (int i2 = 0; i2 < 16; i2++) {
            byte b2 = bArr2[i2];
            for (int i3 = 7; i3 >= 0; i3--) {
                boolean z = true;
                if (((1 << i3) & b2) != 0) {
                    c(bArr3, clone);
                }
                if ((clone[15] & 1) == 0) {
                    z = false;
                }
                a(clone);
                if (z) {
                    clone[0] = (byte) (clone[0] ^ -31);
                }
            }
        }
        System.arraycopy(bArr3, 0, bArr, 0, 16);
    }

    private void b(byte[] bArr, byte[] bArr2, int i2) {
        for (int i3 = 0; i3 < i2; i3 += 16) {
            a(bArr, bArr2, i3, Math.min(i2 - i3, 16));
        }
    }

    private static void b(byte[] bArr, byte[] bArr2, int i2, int i3) {
        while (true) {
            int i4 = i3 - 1;
            if (i3 > 0) {
                bArr[i4] = (byte) (bArr[i4] ^ bArr2[i2 + i4]);
                i3 = i4;
            } else {
                return;
            }
        }
    }

    private byte[] b() {
        for (int i2 = 15; i2 >= 12; i2--) {
            byte b2 = (byte) ((this.o[i2] + 1) & 255);
            this.o[i2] = b2;
            if (b2 != 0) {
                break;
            }
        }
        byte[] bArr = new byte[16];
        this.a.processBlock(this.o, 0, bArr, 0);
        return bArr;
    }

    private static void c(byte[] bArr, byte[] bArr2) {
        for (int i2 = 15; i2 >= 0; i2--) {
            bArr[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
        }
    }

    public int doFinal(byte[] bArr, int i2) {
        if (this.q == 0) {
            a();
        }
        int i3 = this.p;
        if (!this.d) {
            if (i3 < this.e) {
                throw new InvalidCipherTextException("data too short");
            }
            i3 -= this.e;
        }
        if (i3 > 0) {
            if (bArr.length < i2 + i3) {
                throw new OutputLengthException("Output buffer too short");
            }
            a(this.j, 0, i3, bArr, i2);
        }
        this.t += (long) this.s;
        if (this.t > this.u) {
            if (this.s > 0) {
                a(this.m, this.r, 0, this.s);
            }
            if (this.u > 0) {
                c(this.m, this.n);
            }
            long j2 = ((this.q * 8) + 127) >>> 7;
            byte[] bArr2 = new byte[16];
            if (this.c == null) {
                this.c = new Tables1kGCMExponentiator();
                this.c.init(this.h);
            }
            this.c.exponentiateX(j2, bArr2);
            b(this.m, bArr2);
            c(this.l, this.m);
        }
        byte[] bArr3 = new byte[16];
        Pack.longToBigEndian(this.t * 8, bArr3, 0);
        Pack.longToBigEndian(this.q * 8, bArr3, 8);
        a(this.l, bArr3);
        byte[] bArr4 = new byte[16];
        this.a.processBlock(this.i, 0, bArr4, 0);
        c(bArr4, this.l);
        this.k = new byte[this.e];
        System.arraycopy(bArr4, 0, this.k, 0, this.e);
        if (!this.d) {
            byte[] bArr5 = new byte[this.e];
            System.arraycopy(this.j, i3, bArr5, 0, this.e);
            if (!Arrays.constantTimeAreEqual(this.k, bArr5)) {
                throw new InvalidCipherTextException("mac check in GCM failed");
            }
        } else if (bArr.length < i2 + i3 + this.e) {
            throw new OutputLengthException("Output buffer too short");
        } else {
            System.arraycopy(this.k, 0, bArr, i2 + this.p, this.e);
            i3 += this.e;
        }
        a(false);
        return i3;
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getAlgorithmName());
        sb.append("/GCM");
        return sb.toString();
    }

    public byte[] getMac() {
        return Arrays.clone(this.k);
    }

    public int getOutputSize(int i2) {
        int i3 = i2 + this.p;
        if (this.d) {
            return i3 + this.e;
        }
        if (i3 < this.e) {
            return 0;
        }
        return i3 - this.e;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.a;
    }

    public int getUpdateOutputSize(int i2) {
        int i3 = i2 + this.p;
        if (!this.d) {
            if (i3 < this.e) {
                return 0;
            }
            i3 -= this.e;
        }
        return i3 - (i3 % 16);
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        KeyParameter keyParameter;
        this.d = z;
        this.k = null;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            this.f = aEADParameters.getNonce();
            this.g = aEADParameters.getAssociatedText();
            int macSize = aEADParameters.getMacSize();
            if (macSize < 32 || macSize > 128 || macSize % 8 != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid value for MAC size: ");
                sb.append(macSize);
                throw new IllegalArgumentException(sb.toString());
            }
            this.e = macSize / 8;
            keyParameter = aEADParameters.getKey();
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.f = parametersWithIV.getIV();
            this.g = null;
            this.e = 16;
            keyParameter = (KeyParameter) parametersWithIV.getParameters();
        } else {
            throw new IllegalArgumentException("invalid parameters passed to GCM");
        }
        this.j = new byte[(z ? 16 : this.e + 16)];
        if (this.f == null || this.f.length < 1) {
            throw new IllegalArgumentException("IV must be at least 1 byte");
        }
        if (keyParameter != null) {
            this.a.init(true, keyParameter);
            this.h = new byte[16];
            this.a.processBlock(this.h, 0, this.h, 0);
            this.b.init(this.h);
            this.c = null;
        } else if (this.h == null) {
            throw new IllegalArgumentException("Key must be specified in initial init");
        }
        this.i = new byte[16];
        if (this.f.length == 12) {
            System.arraycopy(this.f, 0, this.i, 0, this.f.length);
            this.i[15] = 1;
        } else {
            b(this.i, this.f, this.f.length);
            byte[] bArr = new byte[16];
            Pack.longToBigEndian(((long) this.f.length) * 8, bArr, 8);
            a(this.i, bArr);
        }
        this.l = new byte[16];
        this.m = new byte[16];
        this.n = new byte[16];
        this.r = new byte[16];
        this.s = 0;
        this.t = 0;
        this.u = 0;
        this.o = Arrays.clone(this.i);
        this.p = 0;
        this.q = 0;
        if (this.g != null) {
            processAADBytes(this.g, 0, this.g.length);
        }
    }

    public void processAADByte(byte b2) {
        this.r[this.s] = b2;
        int i2 = this.s + 1;
        this.s = i2;
        if (i2 == 16) {
            a(this.m, this.r);
            this.s = 0;
            this.t += 16;
        }
    }

    public void processAADBytes(byte[] bArr, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            this.r[this.s] = bArr[i2 + i4];
            int i5 = this.s + 1;
            this.s = i5;
            if (i5 == 16) {
                a(this.m, this.r);
                this.s = 0;
                this.t += 16;
            }
        }
    }

    public int processByte(byte b2, byte[] bArr, int i2) {
        this.j[this.p] = b2;
        int i3 = this.p + 1;
        this.p = i3;
        if (i3 != this.j.length) {
            return 0;
        }
        a(bArr, i2);
        return 16;
    }

    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (bArr.length < i2 + i3) {
            throw new DataLengthException("Input buffer too short");
        }
        int i5 = 0;
        for (int i6 = 0; i6 < i3; i6++) {
            this.j[this.p] = bArr[i2 + i6];
            int i7 = this.p + 1;
            this.p = i7;
            if (i7 == this.j.length) {
                a(bArr2, i4 + i5);
                i5 += 16;
            }
        }
        return i5;
    }

    public void reset() {
        a(true);
    }
}
