package org.bouncycastle.crypto.prng;

import org.bouncycastle.crypto.Digest;

public class DigestRandomGenerator implements RandomGenerator {
    private static long a = 10;
    private long b;
    private long c = 1;
    private Digest d;
    private byte[] e;
    private byte[] f;

    public DigestRandomGenerator(Digest digest) {
        this.d = digest;
        this.f = new byte[digest.getDigestSize()];
        this.e = new byte[digest.getDigestSize()];
        this.b = 1;
    }

    private void a() {
        a(this.f);
        long j = this.c;
        this.c = j + 1;
        a(j);
        b(this.f);
    }

    private void a(long j) {
        for (int i = 0; i != 8; i++) {
            this.d.update((byte) ((int) j));
            j >>>= 8;
        }
    }

    private void a(byte[] bArr) {
        this.d.update(bArr, 0, bArr.length);
    }

    private void b() {
        long j = this.b;
        this.b = j + 1;
        a(j);
        a(this.e);
        a(this.f);
        b(this.e);
        if (this.b % a == 0) {
            a();
        }
    }

    private void b(byte[] bArr) {
        this.d.doFinal(bArr, 0);
    }

    public void addSeedMaterial(long j) {
        synchronized (this) {
            a(j);
            a(this.f);
            b(this.f);
        }
    }

    public void addSeedMaterial(byte[] bArr) {
        synchronized (this) {
            a(bArr);
            a(this.f);
            b(this.f);
        }
    }

    public void nextBytes(byte[] bArr) {
        nextBytes(bArr, 0, bArr.length);
    }

    public void nextBytes(byte[] bArr, int i, int i2) {
        synchronized (this) {
            b();
            int i3 = i2 + i;
            int i4 = 0;
            while (i != i3) {
                if (i4 == this.e.length) {
                    b();
                    i4 = 0;
                }
                int i5 = i4 + 1;
                bArr[i] = this.e[i4];
                i++;
                i4 = i5;
            }
        }
    }
}
