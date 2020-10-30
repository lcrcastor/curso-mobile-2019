package org.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

public class HMacDSAKCalculator implements DSAKCalculator {
    private static final BigInteger a = BigInteger.valueOf(0);
    private final HMac b;
    private final byte[] c = new byte[this.b.getMacSize()];
    private final byte[] d = new byte[this.b.getMacSize()];
    private BigInteger e;

    public HMacDSAKCalculator(Digest digest) {
        this.b = new HMac(digest);
    }

    private BigInteger a(byte[] bArr) {
        BigInteger bigInteger = new BigInteger(1, bArr);
        return bArr.length * 8 > this.e.bitLength() ? bigInteger.shiftRight((bArr.length * 8) - this.e.bitLength()) : bigInteger;
    }

    public void init(BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        this.e = bigInteger;
        Arrays.fill(this.d, 1);
        Arrays.fill(this.c, 0);
        byte[] bArr2 = new byte[((bigInteger.bitLength() + 7) / 8)];
        byte[] asUnsignedByteArray = BigIntegers.asUnsignedByteArray(bigInteger2);
        System.arraycopy(asUnsignedByteArray, 0, bArr2, bArr2.length - asUnsignedByteArray.length, asUnsignedByteArray.length);
        byte[] bArr3 = new byte[((bigInteger.bitLength() + 7) / 8)];
        BigInteger a2 = a(bArr);
        if (a2.compareTo(bigInteger) > 0) {
            a2 = a2.subtract(bigInteger);
        }
        byte[] asUnsignedByteArray2 = BigIntegers.asUnsignedByteArray(a2);
        System.arraycopy(asUnsignedByteArray2, 0, bArr3, bArr3.length - asUnsignedByteArray2.length, asUnsignedByteArray2.length);
        this.b.init(new KeyParameter(this.c));
        this.b.update(this.d, 0, this.d.length);
        this.b.update(0);
        this.b.update(bArr2, 0, bArr2.length);
        this.b.update(bArr3, 0, bArr3.length);
        this.b.doFinal(this.c, 0);
        this.b.init(new KeyParameter(this.c));
        this.b.update(this.d, 0, this.d.length);
        this.b.doFinal(this.d, 0);
        this.b.update(this.d, 0, this.d.length);
        this.b.update(1);
        this.b.update(bArr2, 0, bArr2.length);
        this.b.update(bArr3, 0, bArr3.length);
        this.b.doFinal(this.c, 0);
        this.b.init(new KeyParameter(this.c));
        this.b.update(this.d, 0, this.d.length);
        this.b.doFinal(this.d, 0);
    }

    public void init(BigInteger bigInteger, SecureRandom secureRandom) {
        throw new IllegalStateException("Operation not supported");
    }

    public boolean isDeterministic() {
        return true;
    }

    public BigInteger nextK() {
        byte[] bArr = new byte[((this.e.bitLength() + 7) / 8)];
        while (true) {
            int i = 0;
            while (i < bArr.length) {
                this.b.update(this.d, 0, this.d.length);
                this.b.doFinal(this.d, 0);
                int min = Math.min(bArr.length - i, this.d.length);
                System.arraycopy(this.d, 0, bArr, i, min);
                i += min;
            }
            BigInteger a2 = a(bArr);
            if (a2.compareTo(a) > 0 && a2.compareTo(this.e) < 0) {
                return a2;
            }
            this.b.update(this.d, 0, this.d.length);
            this.b.update(0);
            this.b.doFinal(this.c, 0);
            this.b.init(new KeyParameter(this.c));
            this.b.update(this.d, 0, this.d.length);
            this.b.doFinal(this.d, 0);
        }
    }
}
