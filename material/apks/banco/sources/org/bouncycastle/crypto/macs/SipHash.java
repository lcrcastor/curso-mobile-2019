package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Pack;

public class SipHash implements Mac {
    protected final int c;
    protected final int d;
    protected long k0;
    protected long k1;
    protected long m;
    protected long v0;
    protected long v1;
    protected long v2;
    protected long v3;
    protected int wordCount;
    protected int wordPos;

    public SipHash() {
        this.m = 0;
        this.wordPos = 0;
        this.wordCount = 0;
        this.c = 2;
        this.d = 4;
    }

    public SipHash(int i, int i2) {
        this.m = 0;
        this.wordPos = 0;
        this.wordCount = 0;
        this.c = i;
        this.d = i2;
    }

    protected static long rotateLeft(long j, int i) {
        return (j << i) | (j >>> (-i));
    }

    /* access modifiers changed from: protected */
    public void applySipRounds(int i) {
        long j = this.v0;
        long j2 = this.v1;
        long j3 = this.v2;
        long j4 = this.v3;
        int i2 = 0;
        while (i2 < i) {
            long j5 = j + j2;
            long j6 = j3 + j4;
            long rotateLeft = rotateLeft(j2, 13);
            long rotateLeft2 = rotateLeft(j4, 16);
            long j7 = rotateLeft ^ j5;
            long j8 = rotateLeft2 ^ j6;
            long j9 = j6 + j7;
            j = rotateLeft(j5, 32) + j8;
            long rotateLeft3 = rotateLeft(j7, 17) ^ j9;
            j4 = rotateLeft(j8, 21) ^ j;
            j3 = rotateLeft(j9, 32);
            i2++;
            j2 = rotateLeft3;
        }
        this.v0 = j;
        this.v1 = j2;
        this.v2 = j3;
        this.v3 = j4;
    }

    public int doFinal(byte[] bArr, int i) {
        Pack.longToLittleEndian(doFinal(), bArr, i);
        return 8;
    }

    public long doFinal() {
        this.m >>>= (7 - this.wordPos) << 3;
        this.m >>>= 8;
        this.m |= (((long) ((this.wordCount << 3) + this.wordPos)) & 255) << 56;
        processMessageWord();
        this.v2 ^= 255;
        applySipRounds(this.d);
        long j = ((this.v0 ^ this.v1) ^ this.v2) ^ this.v3;
        reset();
        return j;
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append("SipHash-");
        sb.append(this.c);
        sb.append("-");
        sb.append(this.d);
        return sb.toString();
    }

    public int getMacSize() {
        return 8;
    }

    public void init(CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("'params' must be an instance of KeyParameter");
        }
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        if (key.length != 16) {
            throw new IllegalArgumentException("'params' must be a 128-bit key");
        }
        this.k0 = Pack.littleEndianToLong(key, 0);
        this.k1 = Pack.littleEndianToLong(key, 8);
        reset();
    }

    /* access modifiers changed from: protected */
    public void processMessageWord() {
        this.wordCount++;
        this.v3 ^= this.m;
        applySipRounds(this.c);
        this.v0 ^= this.m;
    }

    public void reset() {
        this.v0 = this.k0 ^ 8317987319222330741L;
        this.v1 = this.k1 ^ 7237128888997146477L;
        this.v2 = this.k0 ^ 7816392313619706465L;
        this.v3 = this.k1 ^ 8387220255154660723L;
        this.m = 0;
        this.wordPos = 0;
        this.wordCount = 0;
    }

    public void update(byte b) {
        this.m >>>= 8;
        this.m |= (((long) b) & 255) << 56;
        int i = this.wordPos + 1;
        this.wordPos = i;
        if (i == 8) {
            processMessageWord();
            this.wordPos = 0;
        }
    }

    public void update(byte[] bArr, int i, int i2) {
        byte[] bArr2 = bArr;
        int i3 = i2;
        int i4 = i3 & -8;
        char c2 = '8';
        long j = 255;
        if (this.wordPos == 0) {
            int i5 = 0;
            while (i5 < i4) {
                this.m = Pack.littleEndianToLong(bArr2, i + i5);
                processMessageWord();
                i5 += 8;
            }
            while (i5 < i3) {
                this.m >>>= 8;
                this.m |= (((long) bArr2[i + i5]) & j) << c2;
                i5++;
                c2 = '8';
                j = 255;
            }
            this.wordPos = i3 - i4;
            return;
        }
        int i6 = this.wordPos << 3;
        int i7 = 0;
        while (i7 < i4) {
            long littleEndianToLong = Pack.littleEndianToLong(bArr2, i + i7);
            this.m = (littleEndianToLong << i6) | (this.m >>> (-i6));
            processMessageWord();
            this.m = littleEndianToLong;
            i7 += 8;
        }
        while (i7 < i3) {
            this.m >>>= 8;
            this.m |= (((long) bArr2[i + i7]) & 255) << 56;
            int i8 = this.wordPos + 1;
            this.wordPos = i8;
            if (i8 == 8) {
                processMessageWord();
                this.wordPos = 0;
            }
            i7++;
        }
    }
}
