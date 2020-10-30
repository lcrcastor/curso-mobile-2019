package org.bouncycastle.crypto.modes;

import com.google.common.primitives.UnsignedBytes;
import java.util.Vector;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.util.Arrays;

public class OCBBlockCipher implements AEADBlockCipher {
    private BlockCipher a;
    private BlockCipher b;
    private boolean c;
    private int d;
    private byte[] e;
    private Vector f;
    private byte[] g;
    private byte[] h;
    private byte[] i = null;
    private byte[] j = new byte[24];
    private byte[] k = new byte[16];
    private byte[] l;
    private byte[] m;
    private int n;
    private int o;
    private long p;
    private long q;
    private byte[] r;
    private byte[] s;
    private byte[] t = new byte[16];
    private byte[] u;
    private byte[] v;

    public OCBBlockCipher(BlockCipher blockCipher, BlockCipher blockCipher2) {
        if (blockCipher == null) {
            throw new IllegalArgumentException("'hashCipher' cannot be null");
        } else if (blockCipher.getBlockSize() != 16) {
            throw new IllegalArgumentException("'hashCipher' must have a block size of 16");
        } else if (blockCipher2 == null) {
            throw new IllegalArgumentException("'mainCipher' cannot be null");
        } else if (blockCipher2.getBlockSize() != 16) {
            throw new IllegalArgumentException("'mainCipher' must have a block size of 16");
        } else if (!blockCipher.getAlgorithmName().equals(blockCipher2.getAlgorithmName())) {
            throw new IllegalArgumentException("'hashCipher' and 'mainCipher' must be the same algorithm");
        } else {
            this.a = blockCipher;
            this.b = blockCipher2;
        }
    }

    protected static byte[] OCB_double(byte[] bArr) {
        byte[] bArr2 = new byte[16];
        bArr2[15] = (byte) ((CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA >>> ((1 - shiftLeft(bArr, bArr2)) << 3)) ^ bArr2[15]);
        return bArr2;
    }

    protected static void OCB_extend(byte[] bArr, int i2) {
        bArr[i2] = UnsignedBytes.MAX_POWER_OF_TWO;
        while (true) {
            i2++;
            if (i2 < 16) {
                bArr[i2] = 0;
            } else {
                return;
            }
        }
    }

    protected static int OCB_ntz(long j2) {
        if (j2 == 0) {
            return 64;
        }
        int i2 = 0;
        while ((j2 & 1) == 0) {
            i2++;
            j2 >>>= 1;
        }
        return i2;
    }

    protected static int shiftLeft(byte[] bArr, byte[] bArr2) {
        int i2 = 16;
        int i3 = 0;
        while (true) {
            i2--;
            if (i2 < 0) {
                return i3;
            }
            byte b2 = bArr[i2] & UnsignedBytes.MAX_VALUE;
            bArr2[i2] = (byte) (i3 | (b2 << 1));
            i3 = (b2 >>> 7) & 1;
        }
    }

    protected static void xor(byte[] bArr, byte[] bArr2) {
        for (int i2 = 15; i2 >= 0; i2--) {
            bArr[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
        }
    }

    /* access modifiers changed from: protected */
    public void clear(byte[] bArr) {
        if (bArr != null) {
            Arrays.fill(bArr, 0);
        }
    }

    public int doFinal(byte[] bArr, int i2) {
        byte[] bArr2;
        if (this.c) {
            bArr2 = null;
        } else if (this.o < this.d) {
            throw new InvalidCipherTextException("data too short");
        } else {
            this.o -= this.d;
            bArr2 = new byte[this.d];
            System.arraycopy(this.m, this.o, bArr2, 0, this.d);
        }
        if (this.n > 0) {
            OCB_extend(this.l, this.n);
            updateHASH(this.g);
        }
        if (this.o > 0) {
            if (this.c) {
                OCB_extend(this.m, this.o);
                xor(this.u, this.m);
            }
            xor(this.t, this.g);
            byte[] bArr3 = new byte[16];
            this.a.processBlock(this.t, 0, bArr3, 0);
            xor(this.m, bArr3);
            if (bArr.length < this.o + i2) {
                throw new OutputLengthException("Output buffer too short");
            }
            System.arraycopy(this.m, 0, bArr, i2, this.o);
            if (!this.c) {
                OCB_extend(this.m, this.o);
                xor(this.u, this.m);
            }
        }
        xor(this.u, this.t);
        xor(this.u, this.h);
        this.a.processBlock(this.u, 0, this.u, 0);
        xor(this.u, this.s);
        this.v = new byte[this.d];
        System.arraycopy(this.u, 0, this.v, 0, this.d);
        int i3 = this.o;
        if (this.c) {
            int i4 = i2 + i3;
            if (bArr.length < this.d + i4) {
                throw new OutputLengthException("Output buffer too short");
            }
            System.arraycopy(this.v, 0, bArr, i4, this.d);
            i3 += this.d;
        } else if (!Arrays.constantTimeAreEqual(this.v, bArr2)) {
            throw new InvalidCipherTextException("mac check in OCB failed");
        }
        reset(false);
        return i3;
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.getAlgorithmName());
        sb.append("/OCB");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public byte[] getLSub(int i2) {
        while (i2 >= this.f.size()) {
            this.f.addElement(OCB_double((byte[]) this.f.lastElement()));
        }
        return (byte[]) this.f.elementAt(i2);
    }

    public byte[] getMac() {
        return Arrays.clone(this.v);
    }

    public int getOutputSize(int i2) {
        int i3 = i2 + this.o;
        if (this.c) {
            return i3 + this.d;
        }
        if (i3 < this.d) {
            return 0;
        }
        return i3 - this.d;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.b;
    }

    public int getUpdateOutputSize(int i2) {
        int i3 = i2 + this.o;
        if (!this.c) {
            if (i3 < this.d) {
                return 0;
            }
            i3 -= this.d;
        }
        return i3 - (i3 % 16);
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        KeyParameter keyParameter;
        byte[] bArr;
        boolean z2 = this.c;
        this.c = z;
        this.v = null;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            bArr = aEADParameters.getNonce();
            this.e = aEADParameters.getAssociatedText();
            int macSize = aEADParameters.getMacSize();
            if (macSize < 64 || macSize > 128 || macSize % 8 != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid value for MAC size: ");
                sb.append(macSize);
                throw new IllegalArgumentException(sb.toString());
            }
            this.d = macSize / 8;
            keyParameter = aEADParameters.getKey();
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            bArr = parametersWithIV.getIV();
            this.e = null;
            this.d = 16;
            keyParameter = (KeyParameter) parametersWithIV.getParameters();
        } else {
            throw new IllegalArgumentException("invalid parameters passed to OCB");
        }
        this.l = new byte[16];
        this.m = new byte[(z ? 16 : this.d + 16)];
        if (bArr == null) {
            bArr = new byte[0];
        }
        if (bArr.length > 15) {
            throw new IllegalArgumentException("IV must be no more than 15 bytes");
        }
        if (keyParameter != null) {
            this.a.init(true, keyParameter);
            this.b.init(z, keyParameter);
            this.i = null;
        } else if (z2 != z) {
            throw new IllegalArgumentException("cannot change encrypting state without providing key.");
        }
        this.g = new byte[16];
        this.a.processBlock(this.g, 0, this.g, 0);
        this.h = OCB_double(this.g);
        this.f = new Vector();
        this.f.addElement(OCB_double(this.h));
        int processNonce = processNonce(bArr);
        int i2 = processNonce % 8;
        int i3 = processNonce / 8;
        if (i2 == 0) {
            System.arraycopy(this.j, i3, this.k, 0, 16);
        } else {
            int i4 = i3;
            for (int i5 = 0; i5 < 16; i5++) {
                byte b2 = this.j[i4] & UnsignedBytes.MAX_VALUE;
                i4++;
                this.k[i5] = (byte) ((b2 << i2) | ((this.j[i4] & UnsignedBytes.MAX_VALUE) >>> (8 - i2)));
            }
        }
        this.n = 0;
        this.o = 0;
        this.p = 0;
        this.q = 0;
        this.r = new byte[16];
        this.s = new byte[16];
        System.arraycopy(this.k, 0, this.t, 0, 16);
        this.u = new byte[16];
        if (this.e != null) {
            processAADBytes(this.e, 0, this.e.length);
        }
    }

    public void processAADByte(byte b2) {
        this.l[this.n] = b2;
        int i2 = this.n + 1;
        this.n = i2;
        if (i2 == this.l.length) {
            processHashBlock();
        }
    }

    public void processAADBytes(byte[] bArr, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            this.l[this.n] = bArr[i2 + i4];
            int i5 = this.n + 1;
            this.n = i5;
            if (i5 == this.l.length) {
                processHashBlock();
            }
        }
    }

    public int processByte(byte b2, byte[] bArr, int i2) {
        this.m[this.o] = b2;
        int i3 = this.o + 1;
        this.o = i3;
        if (i3 != this.m.length) {
            return 0;
        }
        processMainBlock(bArr, i2);
        return 16;
    }

    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (bArr.length < i2 + i3) {
            throw new DataLengthException("Input buffer too short");
        }
        int i5 = 0;
        for (int i6 = 0; i6 < i3; i6++) {
            this.m[this.o] = bArr[i2 + i6];
            int i7 = this.o + 1;
            this.o = i7;
            if (i7 == this.m.length) {
                processMainBlock(bArr2, i4 + i5);
                i5 += 16;
            }
        }
        return i5;
    }

    /* access modifiers changed from: protected */
    public void processHashBlock() {
        long j2 = this.p + 1;
        this.p = j2;
        updateHASH(getLSub(OCB_ntz(j2)));
        this.n = 0;
    }

    /* access modifiers changed from: protected */
    public void processMainBlock(byte[] bArr, int i2) {
        if (bArr.length < i2 + 16) {
            throw new OutputLengthException("Output buffer too short");
        }
        if (this.c) {
            xor(this.u, this.m);
            this.o = 0;
        }
        byte[] bArr2 = this.t;
        long j2 = this.q + 1;
        this.q = j2;
        xor(bArr2, getLSub(OCB_ntz(j2)));
        xor(this.m, this.t);
        this.b.processBlock(this.m, 0, this.m, 0);
        xor(this.m, this.t);
        System.arraycopy(this.m, 0, bArr, i2, 16);
        if (!this.c) {
            xor(this.u, this.m);
            System.arraycopy(this.m, 16, this.m, 0, this.d);
            this.o = this.d;
        }
    }

    /* access modifiers changed from: protected */
    public int processNonce(byte[] bArr) {
        byte[] bArr2 = new byte[16];
        int i2 = 0;
        System.arraycopy(bArr, 0, bArr2, bArr2.length - bArr.length, bArr.length);
        bArr2[0] = (byte) (this.d << 4);
        int length = 15 - bArr.length;
        bArr2[length] = (byte) (bArr2[length] | 1);
        byte b2 = bArr2[15] & 63;
        bArr2[15] = (byte) (bArr2[15] & 192);
        if (this.i == null || !Arrays.areEqual(bArr2, this.i)) {
            byte[] bArr3 = new byte[16];
            this.i = bArr2;
            this.a.processBlock(this.i, 0, bArr3, 0);
            System.arraycopy(bArr3, 0, this.j, 0, 16);
            while (i2 < 8) {
                byte[] bArr4 = this.j;
                int i3 = i2 + 16;
                byte b3 = bArr3[i2];
                i2++;
                bArr4[i3] = (byte) (b3 ^ bArr3[i2]);
            }
        }
        return b2;
    }

    public void reset() {
        reset(true);
    }

    /* access modifiers changed from: protected */
    public void reset(boolean z) {
        this.a.reset();
        this.b.reset();
        clear(this.l);
        clear(this.m);
        this.n = 0;
        this.o = 0;
        this.p = 0;
        this.q = 0;
        clear(this.r);
        clear(this.s);
        System.arraycopy(this.k, 0, this.t, 0, 16);
        clear(this.u);
        if (z) {
            this.v = null;
        }
        if (this.e != null) {
            processAADBytes(this.e, 0, this.e.length);
        }
    }

    /* access modifiers changed from: protected */
    public void updateHASH(byte[] bArr) {
        xor(this.r, bArr);
        xor(this.l, this.r);
        this.a.processBlock(this.l, 0, this.l, 0);
        xor(this.s, this.l);
    }
}
