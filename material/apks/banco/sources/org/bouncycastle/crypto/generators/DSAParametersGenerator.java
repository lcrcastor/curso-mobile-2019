package org.bouncycastle.crypto.generators;

import com.google.common.primitives.UnsignedBytes;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.DSAParameterGenerationParameters;
import org.bouncycastle.crypto.params.DSAParameters;
import org.bouncycastle.crypto.params.DSAValidationParameters;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.encoders.Hex;

public class DSAParametersGenerator {
    private static final BigInteger f = BigInteger.valueOf(0);
    private static final BigInteger g = BigInteger.valueOf(1);
    private static final BigInteger h = BigInteger.valueOf(2);
    private Digest a;
    private int b;
    private int c;
    private int d;
    private SecureRandom e;
    private boolean i;
    private int j;

    public DSAParametersGenerator() {
        this(new SHA1Digest());
    }

    public DSAParametersGenerator(Digest digest) {
        this.a = digest;
    }

    private static int a(int i2) {
        if (i2 > 1024) {
            return 256;
        }
        return CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256;
    }

    private static BigInteger a(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        BigInteger modPow;
        BigInteger divide = bigInteger.subtract(g).divide(bigInteger2);
        BigInteger subtract = bigInteger.subtract(h);
        do {
            modPow = BigIntegers.createRandomInRange(h, subtract, secureRandom).modPow(divide, bigInteger);
        } while (modPow.bitLength() <= 1);
        return modPow;
    }

    private static BigInteger a(Digest digest, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr, int i2) {
        BigInteger divide = bigInteger.subtract(g).divide(bigInteger2);
        byte[] decode = Hex.decode("6767656E");
        byte[] bArr2 = new byte[(bArr.length + decode.length + 1 + 2)];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        System.arraycopy(decode, 0, bArr2, bArr.length, decode.length);
        bArr2[bArr2.length - 3] = (byte) i2;
        byte[] bArr3 = new byte[digest.getDigestSize()];
        for (int i3 = 1; i3 < 65536; i3++) {
            a(bArr2);
            a(digest, bArr2, bArr3);
            BigInteger modPow = new BigInteger(1, bArr3).modPow(divide, bigInteger);
            if (modPow.compareTo(h) >= 0) {
                return modPow;
            }
        }
        return null;
    }

    private DSAParameters a() {
        byte[] bArr = new byte[20];
        byte[] bArr2 = new byte[20];
        byte[] bArr3 = new byte[20];
        byte[] bArr4 = new byte[20];
        int i2 = (this.b - 1) / CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256;
        byte[] bArr5 = new byte[(this.b / 8)];
        if (!(this.a instanceof SHA1Digest)) {
            throw new IllegalStateException("can only use SHA-1 for generating FIPS 186-2 parameters");
        }
        while (true) {
            this.e.nextBytes(bArr);
            a(this.a, bArr, bArr2);
            System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
            a(bArr3);
            a(this.a, bArr3, bArr3);
            for (int i3 = 0; i3 != bArr4.length; i3++) {
                bArr4[i3] = (byte) (bArr2[i3] ^ bArr3[i3]);
            }
            bArr4[0] = (byte) (bArr4[0] | UnsignedBytes.MAX_POWER_OF_TWO);
            bArr4[19] = (byte) (bArr4[19] | 1);
            BigInteger bigInteger = new BigInteger(1, bArr4);
            if (bigInteger.isProbablePrime(this.d)) {
                byte[] clone = Arrays.clone(bArr);
                a(clone);
                for (int i4 = 0; i4 < 4096; i4++) {
                    int i5 = 0;
                    while (i5 < i2) {
                        a(clone);
                        a(this.a, clone, bArr2);
                        i5++;
                        System.arraycopy(bArr2, 0, bArr5, bArr5.length - (bArr2.length * i5), bArr2.length);
                    }
                    a(clone);
                    a(this.a, clone, bArr2);
                    System.arraycopy(bArr2, bArr2.length - (bArr5.length - (bArr2.length * i2)), bArr5, 0, bArr5.length - (bArr2.length * i2));
                    bArr5[0] = (byte) (bArr5[0] | UnsignedBytes.MAX_POWER_OF_TWO);
                    BigInteger bigInteger2 = new BigInteger(1, bArr5);
                    BigInteger subtract = bigInteger2.subtract(bigInteger2.mod(bigInteger.shiftLeft(1)).subtract(g));
                    if (subtract.bitLength() == this.b && subtract.isProbablePrime(this.d)) {
                        return new DSAParameters(subtract, bigInteger, a(subtract, bigInteger, this.e), new DSAValidationParameters(bArr, i4));
                    }
                }
                continue;
            }
        }
    }

    private static void a(Digest digest, byte[] bArr, byte[] bArr2) {
        digest.update(bArr, 0, bArr.length);
        digest.doFinal(bArr2, 0);
    }

    private static void a(byte[] bArr) {
        int length = bArr.length - 1;
        while (length >= 0) {
            byte b2 = (byte) ((bArr[length] + 1) & 255);
            bArr[length] = b2;
            if (b2 == 0) {
                length--;
            } else {
                return;
            }
        }
    }

    private static BigInteger b(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        return a(bigInteger, bigInteger2, secureRandom);
    }

    private DSAParameters b() {
        BigInteger subtract;
        int i2;
        BigInteger subtract2;
        Digest digest = this.a;
        int digestSize = digest.getDigestSize() * 8;
        byte[] bArr = new byte[(this.c / 8)];
        int i3 = 1;
        int i4 = (this.b - 1) / digestSize;
        int i5 = (this.b - 1) % digestSize;
        byte[] bArr2 = new byte[digest.getDigestSize()];
        loop0:
        while (true) {
            this.e.nextBytes(bArr);
            a(digest, bArr, bArr2);
            BigInteger mod = new BigInteger(i3, bArr2).mod(g.shiftLeft(this.c - i3));
            subtract = g.shiftLeft(this.c - i3).add(mod).add(g).subtract(mod.mod(h));
            if (subtract.isProbablePrime(this.d)) {
                byte[] clone = Arrays.clone(bArr);
                int i6 = this.b * 4;
                i2 = 0;
                while (i2 < i6) {
                    BigInteger bigInteger = f;
                    int i7 = 0;
                    int i8 = 0;
                    while (i7 <= i4) {
                        a(clone);
                        a(digest, clone, bArr2);
                        BigInteger bigInteger2 = new BigInteger(i3, bArr2);
                        if (i7 == i4) {
                            bigInteger2 = bigInteger2.mod(g.shiftLeft(i5));
                        }
                        bigInteger = bigInteger.add(bigInteger2.shiftLeft(i8));
                        i7++;
                        i8 += digestSize;
                        i3 = 1;
                    }
                    BigInteger add = bigInteger.add(g.shiftLeft(this.b - 1));
                    subtract2 = add.subtract(add.mod(subtract.shiftLeft(1)).subtract(g));
                    if (subtract2.bitLength() == this.b && subtract2.isProbablePrime(this.d)) {
                        break loop0;
                    }
                    i2++;
                    i3 = 1;
                }
                continue;
            }
        }
        if (this.j >= 0) {
            BigInteger a2 = a(digest, subtract2, subtract, bArr, this.j);
            if (a2 != null) {
                return new DSAParameters(subtract2, subtract, a2, new DSAValidationParameters(bArr, i2, this.j));
            }
        }
        return new DSAParameters(subtract2, subtract, b(subtract2, subtract, this.e), new DSAValidationParameters(bArr, i2));
    }

    public DSAParameters generateParameters() {
        return this.i ? b() : a();
    }

    public void init(int i2, int i3, SecureRandom secureRandom) {
        this.i = false;
        this.b = i2;
        this.c = a(i2);
        this.d = i3;
        this.e = secureRandom;
    }

    public void init(DSAParameterGenerationParameters dSAParameterGenerationParameters) {
        this.i = true;
        this.b = dSAParameterGenerationParameters.getL();
        this.c = dSAParameterGenerationParameters.getN();
        this.d = dSAParameterGenerationParameters.getCertainty();
        this.e = dSAParameterGenerationParameters.getRandom();
        this.j = dSAParameterGenerationParameters.getUsageIndex();
        if (this.b < 1024 || this.b > 3072 || this.b % 1024 != 0) {
            throw new IllegalArgumentException("L values must be between 1024 and 3072 and a multiple of 1024");
        } else if (this.b == 1024 && this.c != 160) {
            throw new IllegalArgumentException("N must be 160 for L = 1024");
        } else if (this.b == 2048 && this.c != 224 && this.c != 256) {
            throw new IllegalArgumentException("N must be 224 or 256 for L = 2048");
        } else if (this.b == 3072 && this.c != 256) {
            throw new IllegalArgumentException("N must be 256 for L = 3072");
        } else if (this.a.getDigestSize() * 8 < this.c) {
            throw new IllegalStateException("Digest output size too small for value of N");
        }
    }
}
