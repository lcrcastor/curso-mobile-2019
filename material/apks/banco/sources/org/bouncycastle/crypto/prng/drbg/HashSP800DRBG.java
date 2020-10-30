package org.bouncycastle.crypto.prng.drbg;

import com.google.common.primitives.UnsignedBytes;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.Hashtable;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.prng.EntropySource;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;

public class HashSP800DRBG implements SP80090DRBG {
    private static final byte[] a = {1};
    private static final Hashtable b = new Hashtable();
    private Digest c;
    private byte[] d;
    private byte[] e;
    private long f;
    private EntropySource g;
    private int h;
    private int i;

    static {
        b.put(CommonUtils.SHA1_INSTANCE, Integers.valueOf(440));
        b.put("SHA-224", Integers.valueOf(440));
        b.put("SHA-256", Integers.valueOf(440));
        b.put("SHA-512/256", Integers.valueOf(440));
        b.put("SHA-512/224", Integers.valueOf(440));
        b.put("SHA-384", Integers.valueOf(888));
        b.put("SHA-512", Integers.valueOf(888));
    }

    public HashSP800DRBG(Digest digest, int i2, EntropySource entropySource, byte[] bArr, byte[] bArr2) {
        if (i2 > Utils.a(digest)) {
            throw new IllegalArgumentException("Requested security strength is not supported by the derivation function");
        } else if (entropySource.entropySize() < i2) {
            throw new IllegalArgumentException("Not enough entropy for security strength required");
        } else {
            this.c = digest;
            this.g = entropySource;
            this.h = i2;
            this.i = ((Integer) b.get(digest.getAlgorithmName())).intValue();
            this.d = Utils.a(this.c, Arrays.concatenate(entropySource.getEntropy(), bArr2, bArr), this.i);
            byte[] bArr3 = new byte[(this.d.length + 1)];
            System.arraycopy(this.d, 0, bArr3, 1, this.d.length);
            this.e = Utils.a(this.c, bArr3, this.i);
            this.f = 1;
        }
    }

    private void a(byte[] bArr, byte[] bArr2) {
        byte b2 = 0;
        for (int i2 = 1; i2 <= bArr2.length; i2++) {
            int i3 = (bArr[bArr.length - i2] & UnsignedBytes.MAX_VALUE) + (bArr2[bArr2.length - i2] & UnsignedBytes.MAX_VALUE) + b2;
            b2 = i3 > 255 ? (byte) 1 : 0;
            bArr[bArr.length - i2] = (byte) i3;
        }
        for (int length = bArr2.length + 1; length <= bArr.length; length++) {
            int i4 = (bArr[bArr.length - length] & UnsignedBytes.MAX_VALUE) + b2;
            b2 = i4 > 255 ? (byte) 1 : 0;
            bArr[bArr.length - length] = (byte) i4;
        }
    }

    private byte[] a(byte[] bArr) {
        byte[] bArr2 = new byte[this.c.getDigestSize()];
        b(bArr, bArr2);
        return bArr2;
    }

    private byte[] a(byte[] bArr, int i2) {
        int i3 = i2 / 8;
        int digestSize = i3 / this.c.getDigestSize();
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        byte[] bArr3 = new byte[i3];
        byte[] bArr4 = new byte[this.c.getDigestSize()];
        for (int i4 = 0; i4 <= digestSize; i4++) {
            b(bArr2, bArr4);
            System.arraycopy(bArr4, 0, bArr3, bArr4.length * i4, bArr3.length - (bArr4.length * i4) > bArr4.length ? bArr4.length : bArr3.length - (bArr4.length * i4));
            a(bArr2, a);
        }
        return bArr3;
    }

    private void b(byte[] bArr, byte[] bArr2) {
        this.c.update(bArr, 0, bArr.length);
        this.c.doFinal(bArr2, 0);
    }

    public int generate(byte[] bArr, byte[] bArr2, boolean z) {
        int length = bArr.length * 8;
        if (length > 262144) {
            throw new IllegalArgumentException("Number of bits per request limited to 262144");
        } else if (this.f > 140737488355328L) {
            return -1;
        } else {
            if (z) {
                reseed(bArr2);
                bArr2 = null;
            }
            if (bArr2 != null) {
                byte[] bArr3 = new byte[(this.d.length + 1 + bArr2.length)];
                bArr3[0] = 2;
                System.arraycopy(this.d, 0, bArr3, 1, this.d.length);
                System.arraycopy(bArr2, 0, bArr3, this.d.length + 1, bArr2.length);
                a(this.d, a(bArr3));
            }
            byte[] a2 = a(this.d, length);
            byte[] bArr4 = new byte[(this.d.length + 1)];
            System.arraycopy(this.d, 0, bArr4, 1, this.d.length);
            bArr4[0] = 3;
            a(this.d, a(bArr4));
            a(this.d, this.e);
            a(this.d, new byte[]{(byte) ((int) (this.f >> 24)), (byte) ((int) (this.f >> 16)), (byte) ((int) (this.f >> 8)), (byte) ((int) this.f)});
            this.f++;
            System.arraycopy(a2, 0, bArr, 0, bArr.length);
            return length;
        }
    }

    public int getBlockSize() {
        return this.c.getDigestSize() * 8;
    }

    public void reseed(byte[] bArr) {
        this.d = Utils.a(this.c, Arrays.concatenate(a, this.d, this.g.getEntropy(), bArr), this.i);
        byte[] bArr2 = new byte[(this.d.length + 1)];
        bArr2[0] = 0;
        System.arraycopy(this.d, 0, bArr2, 1, this.d.length);
        this.e = Utils.a(this.c, bArr2, this.i);
        this.f = 1;
    }
}
