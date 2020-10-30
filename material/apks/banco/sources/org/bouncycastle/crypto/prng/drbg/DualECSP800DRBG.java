package org.bouncycastle.crypto.prng.drbg;

import com.google.common.primitives.UnsignedBytes;
import java.math.BigInteger;
import org.bouncycastle.asn1.nist.NISTNamedCurves;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.prng.EntropySource;
import org.bouncycastle.math.ec.ECCurve.Fp;
import org.bouncycastle.math.ec.ECMultiplier;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

public class DualECSP800DRBG implements SP80090DRBG {
    private static final BigInteger a = new BigInteger("6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296", 16);
    private static final BigInteger b = new BigInteger("4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5", 16);
    private static final BigInteger c = new BigInteger("c97445f45cdef9f0d3e05e1e585fc297235b82b5be8ff3efca67c59852018192", 16);
    private static final BigInteger d = new BigInteger("b28ef557ba31dfcbdd21ac46e2a91e3c304f44cb87058ada2cb815151e610046", 16);
    private static final BigInteger e = new BigInteger("aa87ca22be8b05378eb1c71ef320ad746e1d3b628ba79b9859f741e082542a385502f25dbf55296c3a545e3872760ab7", 16);
    private static final BigInteger f = new BigInteger("3617de4a96262c6f5d9e98bf9292dc29f8f41dbd289a147ce9da3113b5f0b8c00a60b1ce1d7e819d7a431d7c90ea0e5f", 16);
    private static final BigInteger g = new BigInteger("8e722de3125bddb05580164bfe20b8b432216a62926c57502ceede31c47816edd1e89769124179d0b695106428815065", 16);
    private static final BigInteger h = new BigInteger("023b1660dd701d0839fd45eec36f9ee7b32e13b315dc02610aa1b636e346df671f790f84c5e09b05674dbb7e45c803dd", 16);
    private static final BigInteger i = new BigInteger("c6858e06b70404e9cd9e3ecb662395b4429c648139053fb521f828af606b4d3dbaa14b5e77efe75928fe1dc127a2ffa8de3348b3c1856a429bf97e7e31c2e5bd66", 16);
    private static final BigInteger j = new BigInteger("11839296a789a3bc0045c8a5fb42c7d1bd998f54449579b446817afbd17273e662c97ee72995ef42640c550b9013fad0761353c7086a272c24088be94769fd16650", 16);
    private static final BigInteger k = new BigInteger("1b9fa3e518d683c6b65763694ac8efbaec6fab44f2276171a42726507dd08add4c3b3f4c1ebc5b1222ddba077f722943b24c3edfa0f85fe24d0c8c01591f0be6f63", 16);
    private static final BigInteger l = new BigInteger("1f3bdba585295d9a1110d1df1f9430ef8442c5018976ff3437ef91b81dc0b8132c8d5c39c32d0e004a3092b7d327c0e7a4d26d2c7b69b58f9066652911e457779de", 16);
    private static final DualECPoints[] m = new DualECPoints[3];
    private Digest n;
    private long o;
    private EntropySource p;
    private int q;
    private int r;
    private int s;
    private ECPoint t;
    private ECPoint u;
    private byte[] v;
    private int w;
    private ECMultiplier x;

    static {
        Fp fp = (Fp) NISTNamedCurves.getByName("P-256").getCurve();
        m[0] = new DualECPoints(128, fp.createPoint(a, b), fp.createPoint(c, d), 1);
        Fp fp2 = (Fp) NISTNamedCurves.getByName("P-384").getCurve();
        m[1] = new DualECPoints(192, fp2.createPoint(e, f), fp2.createPoint(g, h), 1);
        Fp fp3 = (Fp) NISTNamedCurves.getByName("P-521").getCurve();
        m[2] = new DualECPoints(256, fp3.createPoint(i, j), fp3.createPoint(k, l), 1);
    }

    public DualECSP800DRBG(Digest digest, int i2, EntropySource entropySource, byte[] bArr, byte[] bArr2) {
        this(m, digest, i2, entropySource, bArr, bArr2);
    }

    public DualECSP800DRBG(DualECPoints[] dualECPointsArr, Digest digest, int i2, EntropySource entropySource, byte[] bArr, byte[] bArr2) {
        this.x = new FixedPointCombMultiplier();
        this.n = digest;
        this.p = entropySource;
        this.q = i2;
        if (Utils.a(bArr, 512)) {
            throw new IllegalArgumentException("Personalization string too large");
        } else if (entropySource.entropySize() < i2 || entropySource.entropySize() > 4096) {
            StringBuilder sb = new StringBuilder();
            sb.append("EntropySource must provide between ");
            sb.append(i2);
            sb.append(" and ");
            sb.append(4096);
            sb.append(" bits");
            throw new IllegalArgumentException(sb.toString());
        } else {
            byte[] concatenate = Arrays.concatenate(entropySource.getEntropy(), bArr2, bArr);
            int i3 = 0;
            while (true) {
                if (i3 == dualECPointsArr.length) {
                    break;
                } else if (i2 > dualECPointsArr[i3].getSecurityStrength()) {
                    i3++;
                } else if (Utils.a(digest) < dualECPointsArr[i3].getSecurityStrength()) {
                    throw new IllegalArgumentException("Requested security strength is not supported by digest");
                } else {
                    this.r = dualECPointsArr[i3].getSeedLen();
                    this.s = dualECPointsArr[i3].getMaxOutlen() / 8;
                    this.t = dualECPointsArr[i3].getP();
                    this.u = dualECPointsArr[i3].getQ();
                }
            }
            if (this.t == null) {
                throw new IllegalArgumentException("security strength cannot be greater than 256 bits");
            }
            this.v = Utils.a(this.n, concatenate, this.r);
            this.w = this.v.length;
            this.o = 0;
        }
    }

    private BigInteger a(ECPoint eCPoint, BigInteger bigInteger) {
        return this.x.multiply(eCPoint, bigInteger).normalize().getAffineXCoord().toBigInteger();
    }

    private byte[] a(byte[] bArr, int i2) {
        int i3 = i2 % 8;
        if (i3 == 0) {
            return bArr;
        }
        int i4 = 8 - i3;
        byte b2 = 0;
        int length = bArr.length - 1;
        while (length >= 0) {
            byte b3 = bArr[length] & UnsignedBytes.MAX_VALUE;
            bArr[length] = (byte) ((b2 >> (8 - i4)) | (b3 << i4));
            length--;
            b2 = b3;
        }
        return bArr;
    }

    private byte[] a(byte[] bArr, byte[] bArr2) {
        if (bArr2 == null) {
            return bArr;
        }
        byte[] bArr3 = new byte[bArr.length];
        for (int i2 = 0; i2 != bArr3.length; i2++) {
            bArr3[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
        }
        return bArr3;
    }

    public int generate(byte[] bArr, byte[] bArr2, boolean z) {
        BigInteger bigInteger;
        int length = bArr.length * 8;
        int length2 = bArr.length / this.s;
        if (Utils.a(bArr2, 512)) {
            throw new IllegalArgumentException("Additional input too large");
        } else if (this.o + ((long) length2) > 2147483648L) {
            return -1;
        } else {
            if (z) {
                reseed(bArr2);
                bArr2 = null;
            }
            if (bArr2 != null) {
                bigInteger = new BigInteger(1, a(this.v, Utils.a(this.n, bArr2, this.r)));
            } else {
                bigInteger = new BigInteger(1, this.v);
            }
            int i2 = 0;
            Arrays.fill(bArr, 0);
            int i3 = 0;
            for (int i4 = 0; i4 < length2; i4++) {
                bigInteger = a(this.t, bigInteger);
                byte[] byteArray = a(this.u, bigInteger).toByteArray();
                if (byteArray.length > this.s) {
                    System.arraycopy(byteArray, byteArray.length - this.s, bArr, i3, this.s);
                } else {
                    System.arraycopy(byteArray, 0, bArr, (this.s - byteArray.length) + i3, byteArray.length);
                }
                i3 += this.s;
                this.o++;
            }
            if (i3 < bArr.length) {
                bigInteger = a(this.t, bigInteger);
                byte[] byteArray2 = a(this.u, bigInteger).toByteArray();
                int length3 = bArr.length - i3;
                if (byteArray2.length > this.s) {
                    i2 = byteArray2.length - this.s;
                } else {
                    i3 += this.s - byteArray2.length;
                }
                System.arraycopy(byteArray2, i2, bArr, i3, length3);
                this.o++;
            }
            this.v = BigIntegers.asUnsignedByteArray(this.w, a(this.t, bigInteger));
            return length;
        }
    }

    public int getBlockSize() {
        return this.s * 8;
    }

    public void reseed(byte[] bArr) {
        if (Utils.a(bArr, 512)) {
            throw new IllegalArgumentException("Additional input string too large");
        }
        this.v = Utils.a(this.n, Arrays.concatenate(a(this.v, this.r), this.p.getEntropy(), bArr), this.r);
        this.o = 0;
    }
}
