package org.bouncycastle.crypto.prng;

public class ReversedWindowGenerator implements RandomGenerator {
    private final RandomGenerator a;
    private byte[] b;
    private int c;

    public ReversedWindowGenerator(RandomGenerator randomGenerator, int i) {
        if (randomGenerator == null) {
            throw new IllegalArgumentException("generator cannot be null");
        } else if (i < 2) {
            throw new IllegalArgumentException("windowSize must be at least 2");
        } else {
            this.a = randomGenerator;
            this.b = new byte[i];
        }
    }

    private void a(byte[] bArr, int i, int i2) {
        synchronized (this) {
            int i3 = 0;
            while (i3 < i2) {
                try {
                    if (this.c < 1) {
                        this.a.nextBytes(this.b, 0, this.b.length);
                        this.c = this.b.length;
                    }
                    int i4 = i3 + 1;
                    int i5 = i3 + i;
                    byte[] bArr2 = this.b;
                    int i6 = this.c - 1;
                    this.c = i6;
                    bArr[i5] = bArr2[i6];
                    i3 = i4;
                } finally {
                }
            }
        }
    }

    public void addSeedMaterial(long j) {
        synchronized (this) {
            this.c = 0;
            this.a.addSeedMaterial(j);
        }
    }

    public void addSeedMaterial(byte[] bArr) {
        synchronized (this) {
            this.c = 0;
            this.a.addSeedMaterial(bArr);
        }
    }

    public void nextBytes(byte[] bArr) {
        a(bArr, 0, bArr.length);
    }

    public void nextBytes(byte[] bArr, int i, int i2) {
        a(bArr, i, i2);
    }
}
