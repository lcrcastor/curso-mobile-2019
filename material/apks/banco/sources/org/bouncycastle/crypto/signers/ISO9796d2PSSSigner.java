package org.bouncycastle.crypto.signers;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.security.SecureRandom;
import java.util.Hashtable;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.SignerWithRecovery;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;

public class ISO9796d2PSSSigner implements SignerWithRecovery {
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
    private SecureRandom d;
    private byte[] e;
    private int f;
    private int g;
    private int h;
    private byte[] i;
    private byte[] j;
    private int k;
    private int l;
    private boolean m;
    private byte[] n;
    private byte[] o;
    private byte[] p;
    private int q;
    private int r;

    static {
        a.put("RIPEMD128", Integers.valueOf(13004));
        a.put("RIPEMD160", Integers.valueOf(12748));
        a.put(CommonUtils.SHA1_INSTANCE, Integers.valueOf(13260));
        a.put("SHA-256", Integers.valueOf(13516));
        a.put("SHA-384", Integers.valueOf(14028));
        a.put("SHA-512", Integers.valueOf(13772));
        a.put("Whirlpool", Integers.valueOf(14284));
    }

    public ISO9796d2PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, int i2) {
        this(asymmetricBlockCipher, digest, i2, false);
    }

    public ISO9796d2PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, int i2, boolean z) {
        int intValue;
        this.c = asymmetricBlockCipher;
        this.b = digest;
        this.f = digest.getDigestSize();
        this.l = i2;
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
        this.g = intValue;
    }

    private void a(int i2, byte[] bArr) {
        bArr[0] = (byte) (i2 >>> 24);
        bArr[1] = (byte) (i2 >>> 16);
        bArr[2] = (byte) (i2 >>> 8);
        bArr[3] = (byte) (i2 >>> 0);
    }

    private void a(long j2, byte[] bArr) {
        bArr[0] = (byte) ((int) (j2 >>> 56));
        bArr[1] = (byte) ((int) (j2 >>> 48));
        bArr[2] = (byte) ((int) (j2 >>> 40));
        bArr[3] = (byte) ((int) (j2 >>> 32));
        bArr[4] = (byte) ((int) (j2 >>> 24));
        bArr[5] = (byte) ((int) (j2 >>> 16));
        bArr[6] = (byte) ((int) (j2 >>> 8));
        bArr[7] = (byte) ((int) (j2 >>> 0));
    }

    private void a(byte[] bArr) {
        for (int i2 = 0; i2 != bArr.length; i2++) {
            bArr[i2] = 0;
        }
    }

    private boolean a(byte[] bArr, byte[] bArr2) {
        boolean z = this.k == bArr2.length;
        for (int i2 = 0; i2 != bArr2.length; i2++) {
            if (bArr[i2] != bArr2[i2]) {
                z = false;
            }
        }
        return z;
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
        byte[] bArr;
        byte[] bArr2 = new byte[this.b.getDigestSize()];
        this.b.doFinal(bArr2, 0);
        byte[] bArr3 = new byte[8];
        a((long) (this.k * 8), bArr3);
        this.b.update(bArr3, 0, bArr3.length);
        this.b.update(this.j, 0, this.k);
        this.b.update(bArr2, 0, bArr2.length);
        if (this.e != null) {
            bArr = this.e;
        } else {
            bArr = new byte[this.l];
            this.d.nextBytes(bArr);
        }
        this.b.update(bArr, 0, bArr.length);
        byte[] bArr4 = new byte[this.b.getDigestSize()];
        this.b.doFinal(bArr4, 0);
        int i2 = this.g == 188 ? 1 : 2;
        int length = ((((this.i.length - this.k) - bArr.length) - this.f) - i2) - 1;
        this.i[length] = 1;
        int i3 = length + 1;
        System.arraycopy(this.j, 0, this.i, i3, this.k);
        System.arraycopy(bArr, 0, this.i, i3 + this.k, bArr.length);
        byte[] a2 = a(bArr4, 0, bArr4.length, (this.i.length - this.f) - i2);
        for (int i4 = 0; i4 != a2.length; i4++) {
            byte[] bArr5 = this.i;
            bArr5[i4] = (byte) (bArr5[i4] ^ a2[i4]);
        }
        System.arraycopy(bArr4, 0, this.i, (this.i.length - this.f) - i2, this.f);
        if (this.g == 188) {
            this.i[this.i.length - 1] = PSSSigner.TRAILER_IMPLICIT;
        } else {
            this.i[this.i.length - 2] = (byte) (this.g >>> 8);
            this.i[this.i.length - 1] = (byte) this.g;
        }
        byte[] bArr6 = this.i;
        bArr6[0] = (byte) (bArr6[0] & Ascii.DEL);
        byte[] processBlock = this.c.processBlock(this.i, 0, this.i.length);
        a(this.j);
        a(this.i);
        this.k = 0;
        return processBlock;
    }

    public byte[] getRecoveredMessage() {
        return this.n;
    }

    public boolean hasFullMessage() {
        return this.m;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x007a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void init(boolean r4, org.bouncycastle.crypto.CipherParameters r5) {
        /*
            r3 = this;
            int r0 = r3.l
            boolean r1 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithRandom
            if (r1 == 0) goto L_0x0017
            org.bouncycastle.crypto.params.ParametersWithRandom r5 = (org.bouncycastle.crypto.params.ParametersWithRandom) r5
            org.bouncycastle.crypto.CipherParameters r1 = r5.getParameters()
            org.bouncycastle.crypto.params.RSAKeyParameters r1 = (org.bouncycastle.crypto.params.RSAKeyParameters) r1
            if (r4 == 0) goto L_0x0047
            java.security.SecureRandom r5 = r5.getRandom()
        L_0x0014:
            r3.d = r5
            goto L_0x0047
        L_0x0017:
            boolean r1 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithSalt
            if (r1 == 0) goto L_0x003c
            org.bouncycastle.crypto.params.ParametersWithSalt r5 = (org.bouncycastle.crypto.params.ParametersWithSalt) r5
            org.bouncycastle.crypto.CipherParameters r0 = r5.getParameters()
            r1 = r0
            org.bouncycastle.crypto.params.RSAKeyParameters r1 = (org.bouncycastle.crypto.params.RSAKeyParameters) r1
            byte[] r5 = r5.getSalt()
            r3.e = r5
            byte[] r5 = r3.e
            int r0 = r5.length
            byte[] r5 = r3.e
            int r5 = r5.length
            int r2 = r3.l
            if (r5 == r2) goto L_0x0047
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r5 = "Fixed salt is of wrong length"
            r4.<init>(r5)
            throw r4
        L_0x003c:
            r1 = r5
            org.bouncycastle.crypto.params.RSAKeyParameters r1 = (org.bouncycastle.crypto.params.RSAKeyParameters) r1
            if (r4 == 0) goto L_0x0047
            java.security.SecureRandom r5 = new java.security.SecureRandom
            r5.<init>()
            goto L_0x0014
        L_0x0047:
            org.bouncycastle.crypto.AsymmetricBlockCipher r5 = r3.c
            r5.init(r4, r1)
            java.math.BigInteger r4 = r1.getModulus()
            int r4 = r4.bitLength()
            r3.h = r4
            int r4 = r3.h
            int r4 = r4 + 7
            int r4 = r4 / 8
            byte[] r4 = new byte[r4]
            r3.i = r4
            int r4 = r3.g
            r5 = 188(0xbc, float:2.63E-43)
            if (r4 != r5) goto L_0x007a
            byte[] r4 = r3.i
            int r4 = r4.length
            org.bouncycastle.crypto.Digest r5 = r3.b
            int r5 = r5.getDigestSize()
            int r4 = r4 - r5
            int r4 = r4 - r0
            int r4 = r4 + -1
            int r4 = r4 + -1
        L_0x0075:
            byte[] r4 = new byte[r4]
            r3.j = r4
            goto L_0x008a
        L_0x007a:
            byte[] r4 = r3.i
            int r4 = r4.length
            org.bouncycastle.crypto.Digest r5 = r3.b
            int r5 = r5.getDigestSize()
            int r4 = r4 - r5
            int r4 = r4 - r0
            int r4 = r4 + -1
            int r4 = r4 + -2
            goto L_0x0075
        L_0x008a:
            r3.reset()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.signers.ISO9796d2PSSSigner.init(boolean, org.bouncycastle.crypto.CipherParameters):void");
    }

    public void reset() {
        this.b.reset();
        this.k = 0;
        if (this.j != null) {
            a(this.j);
        }
        if (this.n != null) {
            a(this.n);
            this.n = null;
        }
        this.m = false;
        if (this.o != null) {
            this.o = null;
            a(this.p);
            this.p = null;
        }
    }

    public void update(byte b2) {
        if (this.o != null || this.k >= this.j.length) {
            this.b.update(b2);
            return;
        }
        byte[] bArr = this.j;
        int i2 = this.k;
        this.k = i2 + 1;
        bArr[i2] = b2;
    }

    public void update(byte[] bArr, int i2, int i3) {
        if (this.o == null) {
            while (i3 > 0 && this.k < this.j.length) {
                update(bArr[i2]);
                i2++;
                i3--;
            }
        }
        if (i3 > 0) {
            this.b.update(bArr, i2, i3);
        }
    }

    public void updateWithRecoveredMessage(byte[] bArr) {
        byte[] processBlock = this.c.processBlock(bArr, 0, bArr.length);
        if (processBlock.length < (this.h + 7) / 8) {
            byte[] bArr2 = new byte[((this.h + 7) / 8)];
            System.arraycopy(processBlock, 0, bArr2, bArr2.length - processBlock.length, processBlock.length);
            a(processBlock);
            processBlock = bArr2;
        }
        boolean z = true;
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
        this.b.doFinal(new byte[this.f], 0);
        byte[] a2 = a(processBlock, (processBlock.length - this.f) - i2, this.f, (processBlock.length - this.f) - i2);
        for (int i3 = 0; i3 != a2.length; i3++) {
            processBlock[i3] = (byte) (processBlock[i3] ^ a2[i3]);
        }
        processBlock[0] = (byte) (processBlock[0] & Ascii.DEL);
        int i4 = 0;
        while (i4 != processBlock.length && processBlock[i4] != 1) {
            i4++;
        }
        int i5 = i4 + 1;
        if (i5 >= processBlock.length) {
            a(processBlock);
        }
        if (i5 <= 1) {
            z = false;
        }
        this.m = z;
        this.n = new byte[((a2.length - i5) - this.l)];
        System.arraycopy(processBlock, i5, this.n, 0, this.n.length);
        System.arraycopy(this.n, 0, this.j, 0, this.n.length);
        this.o = bArr;
        this.p = processBlock;
        this.q = i5;
        this.r = i2;
    }

    public boolean verifySignature(byte[] bArr) {
        byte[] bArr2;
        byte[] bArr3 = new byte[this.f];
        this.b.doFinal(bArr3, 0);
        if (this.o == null) {
            try {
                updateWithRecoveredMessage(bArr);
            } catch (Exception unused) {
                return false;
            }
        } else if (!Arrays.areEqual(this.o, bArr)) {
            throw new IllegalStateException("updateWithRecoveredMessage called on different signature");
        }
        byte[] bArr4 = this.p;
        int i2 = this.q;
        int i3 = this.r;
        this.o = null;
        this.p = null;
        byte[] bArr5 = new byte[8];
        a((long) (this.n.length * 8), bArr5);
        this.b.update(bArr5, 0, bArr5.length);
        if (this.n.length != 0) {
            this.b.update(this.n, 0, this.n.length);
        }
        this.b.update(bArr3, 0, bArr3.length);
        this.b.update(bArr4, i2 + this.n.length, this.l);
        byte[] bArr6 = new byte[this.b.getDigestSize()];
        this.b.doFinal(bArr6, 0);
        int length = (bArr4.length - i3) - bArr6.length;
        boolean z = true;
        for (int i4 = 0; i4 != bArr6.length; i4++) {
            if (bArr6[i4] != bArr4[length + i4]) {
                z = false;
            }
        }
        a(bArr4);
        a(bArr6);
        if (!z) {
            this.m = false;
            bArr2 = this.n;
        } else {
            if (this.k != 0) {
                if (!a(this.j, this.n)) {
                    bArr2 = this.j;
                } else {
                    this.k = 0;
                }
            }
            a(this.j);
            return true;
        }
        a(bArr2);
        return false;
    }
}
