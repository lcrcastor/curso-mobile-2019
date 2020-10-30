package org.bouncycastle.crypto.digests;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;

public abstract class GeneralDigest implements ExtendedDigest, Memoable {
    private final byte[] a;
    private int b;
    private long c;

    protected GeneralDigest() {
        this.a = new byte[4];
        this.b = 0;
    }

    protected GeneralDigest(GeneralDigest generalDigest) {
        this.a = new byte[4];
        copyIn(generalDigest);
    }

    protected GeneralDigest(byte[] bArr) {
        this.a = new byte[4];
        System.arraycopy(bArr, 0, this.a, 0, this.a.length);
        this.b = Pack.bigEndianToInt(bArr, 4);
        this.c = Pack.bigEndianToLong(bArr, 8);
    }

    /* access modifiers changed from: protected */
    public void copyIn(GeneralDigest generalDigest) {
        System.arraycopy(generalDigest.a, 0, this.a, 0, generalDigest.a.length);
        this.b = generalDigest.b;
        this.c = generalDigest.c;
    }

    public void finish() {
        long j = this.c << 3;
        byte b2 = UnsignedBytes.MAX_POWER_OF_TWO;
        while (true) {
            update(b2);
            if (this.b != 0) {
                b2 = 0;
            } else {
                processLength(j);
                processBlock();
                return;
            }
        }
    }

    public int getByteLength() {
        return 64;
    }

    /* access modifiers changed from: protected */
    public void populateState(byte[] bArr) {
        System.arraycopy(this.a, 0, bArr, 0, this.b);
        Pack.intToBigEndian(this.b, bArr, 4);
        Pack.longToBigEndian(this.c, bArr, 8);
    }

    /* access modifiers changed from: protected */
    public abstract void processBlock();

    /* access modifiers changed from: protected */
    public abstract void processLength(long j);

    /* access modifiers changed from: protected */
    public abstract void processWord(byte[] bArr, int i);

    public void reset() {
        this.c = 0;
        this.b = 0;
        for (int i = 0; i < this.a.length; i++) {
            this.a[i] = 0;
        }
    }

    public void update(byte b2) {
        byte[] bArr = this.a;
        int i = this.b;
        this.b = i + 1;
        bArr[i] = b2;
        if (this.b == this.a.length) {
            processWord(this.a, 0);
            this.b = 0;
        }
        this.c++;
    }

    public void update(byte[] bArr, int i, int i2) {
        while (this.b != 0 && i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
        while (i2 > this.a.length) {
            processWord(bArr, i);
            i += this.a.length;
            i2 -= this.a.length;
            this.c += (long) this.a.length;
        }
        while (i2 > 0) {
            update(bArr[i]);
            i++;
            i2--;
        }
    }
}
