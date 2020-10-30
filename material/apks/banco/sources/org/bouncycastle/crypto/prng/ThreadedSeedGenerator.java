package org.bouncycastle.crypto.prng;

public class ThreadedSeedGenerator {

    class SeedGenerator implements Runnable {
        private volatile int b;
        private volatile boolean c;

        private SeedGenerator() {
            this.b = 0;
            this.c = false;
        }

        public byte[] a(int i, boolean z) {
            Thread thread = new Thread(this);
            byte[] bArr = new byte[i];
            this.b = 0;
            this.c = false;
            thread.start();
            if (!z) {
                i *= 8;
            }
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                while (this.b == i2) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException unused) {
                    }
                }
                i2 = this.b;
                if (z) {
                    bArr[i3] = (byte) (i2 & 255);
                } else {
                    int i4 = i3 / 8;
                    bArr[i4] = (byte) ((bArr[i4] << 1) | (i2 & 1));
                }
            }
            this.c = true;
            return bArr;
        }

        public void run() {
            while (!this.c) {
                this.b++;
            }
        }
    }

    public byte[] generateSeed(int i, boolean z) {
        return new SeedGenerator().a(i, z);
    }
}
