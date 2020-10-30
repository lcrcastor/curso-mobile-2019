package org.bouncycastle.crypto.prng.drbg;

import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.prng.EntropySource;
import org.bouncycastle.util.Arrays;

public class HMacSP800DRBG implements SP80090DRBG {
    private byte[] a;
    private byte[] b;
    private long c;
    private EntropySource d;
    private Mac e;

    public HMacSP800DRBG(Mac mac, int i, EntropySource entropySource, byte[] bArr, byte[] bArr2) {
        if (i > Utils.a(mac)) {
            throw new IllegalArgumentException("Requested security strength is not supported by the derivation function");
        } else if (entropySource.entropySize() < i) {
            throw new IllegalArgumentException("Not enough entropy for security strength required");
        } else {
            this.d = entropySource;
            this.e = mac;
            byte[] concatenate = Arrays.concatenate(entropySource.getEntropy(), bArr2, bArr);
            this.a = new byte[mac.getMacSize()];
            this.b = new byte[this.a.length];
            Arrays.fill(this.b, 1);
            a(concatenate);
            this.c = 1;
        }
    }

    private void a(byte[] bArr) {
        a(bArr, 0);
        if (bArr != null) {
            a(bArr, 1);
        }
    }

    private void a(byte[] bArr, byte b2) {
        this.e.init(new KeyParameter(this.a));
        this.e.update(this.b, 0, this.b.length);
        this.e.update(b2);
        if (bArr != null) {
            this.e.update(bArr, 0, bArr.length);
        }
        this.e.doFinal(this.a, 0);
        this.e.init(new KeyParameter(this.a));
        this.e.update(this.b, 0, this.b.length);
        this.e.doFinal(this.b, 0);
    }

    public int generate(byte[] bArr, byte[] bArr2, boolean z) {
        int length = bArr.length * 8;
        if (length > 262144) {
            throw new IllegalArgumentException("Number of bits per request limited to 262144");
        } else if (this.c > 140737488355328L) {
            return -1;
        } else {
            if (z) {
                reseed(bArr2);
                bArr2 = null;
            }
            if (bArr2 != null) {
                a(bArr2);
            }
            byte[] bArr3 = new byte[bArr.length];
            int length2 = bArr.length / this.b.length;
            this.e.init(new KeyParameter(this.a));
            for (int i = 0; i < length2; i++) {
                this.e.update(this.b, 0, this.b.length);
                this.e.doFinal(this.b, 0);
                System.arraycopy(this.b, 0, bArr3, this.b.length * i, this.b.length);
            }
            if (this.b.length * length2 < bArr3.length) {
                this.e.update(this.b, 0, this.b.length);
                this.e.doFinal(this.b, 0);
                System.arraycopy(this.b, 0, bArr3, this.b.length * length2, bArr3.length - (length2 * this.b.length));
            }
            a(bArr2);
            this.c++;
            System.arraycopy(bArr3, 0, bArr, 0, bArr.length);
            return length;
        }
    }

    public int getBlockSize() {
        return this.b.length * 8;
    }

    public void reseed(byte[] bArr) {
        a(Arrays.concatenate(this.d.getEntropy(), bArr));
        this.c = 1;
    }
}
