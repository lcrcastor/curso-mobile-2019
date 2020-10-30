package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.params.GOST3410Parameters;
import org.bouncycastle.crypto.params.GOST3410ValidationParameters;

public class GOST3410ParametersGenerator {
    private static final BigInteger d = BigInteger.valueOf(1);
    private static final BigInteger e = BigInteger.valueOf(2);
    private int a;
    private int b;
    private SecureRandom c;

    private int a(int i, int i2, BigInteger[] bigIntegerArr, int i3) {
        BigInteger bigInteger;
        BigInteger[] bigIntegerArr2;
        BigInteger bigInteger2;
        int i4;
        BigInteger bigInteger3;
        int i5 = i;
        while (true) {
            if (i5 >= 0 && i5 <= 65536) {
                break;
            }
            i5 = this.c.nextInt() / 32768;
        }
        int i6 = i2;
        while (true) {
            if (i6 >= 0 && i6 <= 65536 && i6 / 2 != 0) {
                break;
            }
            i6 = (this.c.nextInt() / 32768) + 1;
        }
        BigInteger bigInteger4 = new BigInteger(Integer.toString(i6));
        BigInteger bigInteger5 = new BigInteger("19381");
        BigInteger bigInteger6 = new BigInteger(Integer.toString(i5));
        int i7 = 0;
        BigInteger[] bigIntegerArr3 = {bigInteger6};
        int[] iArr = {i3};
        int i8 = 0;
        int i9 = 0;
        while (iArr[i8] >= 17) {
            int[] iArr2 = new int[(iArr.length + 1)];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            iArr = new int[iArr2.length];
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            i9 = i8 + 1;
            iArr[i9] = iArr[i8] / 2;
            i8 = i9;
        }
        BigInteger[] bigIntegerArr4 = new BigInteger[(i9 + 1)];
        int i10 = 16;
        bigIntegerArr4[i9] = new BigInteger("8003", 16);
        int i11 = i9 - 1;
        BigInteger[] bigIntegerArr5 = bigIntegerArr3;
        int i12 = 0;
        while (true) {
            if (i12 >= i9) {
                bigInteger = bigIntegerArr5[0];
                break;
            }
            int i13 = iArr[i11] / i10;
            while (true) {
                BigInteger[] bigIntegerArr6 = new BigInteger[bigIntegerArr5.length];
                System.arraycopy(bigIntegerArr5, i7, bigIntegerArr6, i7, bigIntegerArr5.length);
                bigIntegerArr2 = new BigInteger[(i13 + 1)];
                System.arraycopy(bigIntegerArr6, i7, bigIntegerArr2, i7, bigIntegerArr6.length);
                int i14 = 0;
                while (i14 < i13) {
                    int i15 = i14 + 1;
                    bigIntegerArr2[i15] = bigIntegerArr2[i14].multiply(bigInteger5).add(bigInteger4).mod(e.pow(i10));
                    i14 = i15;
                }
                BigInteger bigInteger7 = new BigInteger("0");
                for (int i16 = 0; i16 < i13; i16++) {
                    bigInteger7 = bigInteger7.add(bigIntegerArr2[i16].multiply(e.pow(i16 * 16)));
                }
                bigIntegerArr2[0] = bigIntegerArr2[i13];
                int i17 = i11 + 1;
                bigInteger2 = bigInteger4;
                BigInteger add = e.pow(iArr[i11] - 1).divide(bigIntegerArr4[i17]).add(e.pow(iArr[i11] - 1).multiply(bigInteger7).divide(bigIntegerArr4[i17].multiply(e.pow(i13 * 16))));
                if (add.mod(e).compareTo(d) == 0) {
                    add = add.add(d);
                }
                int i18 = 0;
                while (true) {
                    i4 = i13;
                    long j = (long) i18;
                    bigIntegerArr4[i11] = bigIntegerArr4[i17].multiply(add.add(BigInteger.valueOf(j))).add(d);
                    bigInteger3 = bigInteger5;
                    if (bigIntegerArr4[i11].compareTo(e.pow(iArr[i11])) != 1) {
                        if (e.modPow(bigIntegerArr4[i17].multiply(add.add(BigInteger.valueOf(j))), bigIntegerArr4[i11]).compareTo(d) == 0 && e.modPow(add.add(BigInteger.valueOf(j)), bigIntegerArr4[i11]).compareTo(d) != 0) {
                            break;
                        }
                        i18 += 2;
                        i13 = i4;
                        bigInteger5 = bigInteger3;
                    } else {
                        break;
                    }
                }
                bigIntegerArr5 = bigIntegerArr2;
                bigInteger4 = bigInteger2;
                i13 = i4;
                bigInteger5 = bigInteger3;
                i7 = 0;
                i10 = 16;
            }
            i11--;
            if (i11 < 0) {
                bigIntegerArr[0] = bigIntegerArr4[0];
                bigIntegerArr[1] = bigIntegerArr4[1];
                bigInteger = bigIntegerArr2[0];
                break;
            }
            i12++;
            bigIntegerArr5 = bigIntegerArr2;
            bigInteger4 = bigInteger2;
            bigInteger5 = bigInteger3;
            i7 = 0;
            i10 = 16;
        }
        return bigInteger.intValue();
    }

    private long a(long j, long j2, BigInteger[] bigIntegerArr, int i) {
        BigInteger bigInteger;
        BigInteger[] bigIntegerArr2;
        BigInteger bigInteger2;
        int i2;
        BigInteger bigInteger3;
        long j3 = j;
        while (true) {
            if (j3 >= 0 && j3 <= 4294967296L) {
                break;
            }
            j3 = (long) (this.c.nextInt() * 2);
        }
        long j4 = j2;
        while (true) {
            if (j4 >= 0 && j4 <= 4294967296L && j4 / 2 != 0) {
                break;
            }
            j4 = (long) ((this.c.nextInt() * 2) + 1);
        }
        BigInteger bigInteger4 = new BigInteger(Long.toString(j4));
        BigInteger bigInteger5 = new BigInteger("97781173");
        BigInteger bigInteger6 = new BigInteger(Long.toString(j3));
        int i3 = 0;
        BigInteger[] bigIntegerArr3 = {bigInteger6};
        int[] iArr = {i};
        int i4 = 0;
        int i5 = 0;
        while (iArr[i4] >= 33) {
            int[] iArr2 = new int[(iArr.length + 1)];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            iArr = new int[iArr2.length];
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            i5 = i4 + 1;
            iArr[i5] = iArr[i4] / 2;
            i4 = i5;
        }
        BigInteger[] bigIntegerArr4 = new BigInteger[(i5 + 1)];
        bigIntegerArr4[i5] = new BigInteger("8000000B", 16);
        int i6 = i5 - 1;
        BigInteger[] bigIntegerArr5 = bigIntegerArr3;
        int i7 = 0;
        while (true) {
            if (i7 >= i5) {
                bigInteger = bigIntegerArr5[0];
                break;
            }
            int i8 = 32;
            int i9 = iArr[i6] / 32;
            while (true) {
                BigInteger[] bigIntegerArr6 = new BigInteger[bigIntegerArr5.length];
                System.arraycopy(bigIntegerArr5, i3, bigIntegerArr6, i3, bigIntegerArr5.length);
                bigIntegerArr2 = new BigInteger[(i9 + 1)];
                System.arraycopy(bigIntegerArr6, i3, bigIntegerArr2, i3, bigIntegerArr6.length);
                int i10 = 0;
                while (i10 < i9) {
                    int i11 = i10 + 1;
                    bigIntegerArr2[i11] = bigIntegerArr2[i10].multiply(bigInteger5).add(bigInteger4).mod(e.pow(i8));
                    i10 = i11;
                }
                BigInteger bigInteger7 = new BigInteger("0");
                for (int i12 = 0; i12 < i9; i12++) {
                    bigInteger7 = bigInteger7.add(bigIntegerArr2[i12].multiply(e.pow(i12 * 32)));
                }
                bigIntegerArr2[0] = bigIntegerArr2[i9];
                int i13 = i6 + 1;
                bigInteger2 = bigInteger4;
                BigInteger add = e.pow(iArr[i6] - 1).divide(bigIntegerArr4[i13]).add(e.pow(iArr[i6] - 1).multiply(bigInteger7).divide(bigIntegerArr4[i13].multiply(e.pow(i9 * 32))));
                if (add.mod(e).compareTo(d) == 0) {
                    add = add.add(d);
                }
                int i14 = 0;
                while (true) {
                    i2 = i9;
                    long j5 = (long) i14;
                    bigIntegerArr4[i6] = bigIntegerArr4[i13].multiply(add.add(BigInteger.valueOf(j5))).add(d);
                    bigInteger3 = bigInteger5;
                    if (bigIntegerArr4[i6].compareTo(e.pow(iArr[i6])) != 1) {
                        if (e.modPow(bigIntegerArr4[i13].multiply(add.add(BigInteger.valueOf(j5))), bigIntegerArr4[i6]).compareTo(d) == 0 && e.modPow(add.add(BigInteger.valueOf(j5)), bigIntegerArr4[i6]).compareTo(d) != 0) {
                            break;
                        }
                        i14 += 2;
                        i9 = i2;
                        bigInteger5 = bigInteger3;
                    } else {
                        break;
                    }
                }
                bigIntegerArr5 = bigIntegerArr2;
                bigInteger4 = bigInteger2;
                i9 = i2;
                bigInteger5 = bigInteger3;
                i3 = 0;
                i8 = 32;
            }
            i6--;
            if (i6 < 0) {
                bigIntegerArr[0] = bigIntegerArr4[0];
                bigIntegerArr[1] = bigIntegerArr4[1];
                bigInteger = bigIntegerArr2[0];
                break;
            }
            i7++;
            bigIntegerArr5 = bigIntegerArr2;
            bigInteger4 = bigInteger2;
            bigInteger5 = bigInteger3;
            i3 = 0;
        }
        return bigInteger.longValue();
    }

    private BigInteger a(BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger subtract = bigInteger.subtract(d);
        BigInteger divide = subtract.divide(bigInteger2);
        int bitLength = bigInteger.bitLength();
        while (true) {
            BigInteger bigInteger3 = new BigInteger(bitLength, this.c);
            if (bigInteger3.compareTo(d) > 0 && bigInteger3.compareTo(subtract) < 0) {
                BigInteger modPow = bigInteger3.modPow(divide, bigInteger);
                if (modPow.compareTo(d) != 0) {
                    return modPow;
                }
            }
        }
    }

    private void a(int i, int i2, BigInteger[] bigIntegerArr) {
        int i3;
        int i4 = i;
        while (true) {
            if (i4 >= 0 && i4 <= 65536) {
                break;
            }
            i4 = this.c.nextInt() / 32768;
        }
        int i5 = i2;
        while (true) {
            i3 = 1;
            if (i5 >= 0 && i5 <= 65536 && i5 / 2 != 0) {
                break;
            }
            i5 = (this.c.nextInt() / 32768) + 1;
        }
        BigInteger[] bigIntegerArr2 = new BigInteger[2];
        BigInteger bigInteger = new BigInteger(Integer.toString(i5));
        BigInteger bigInteger2 = new BigInteger("19381");
        int a2 = a(i4, i5, bigIntegerArr2, 256);
        BigInteger bigInteger3 = bigIntegerArr2[0];
        int a3 = a(a2, i5, bigIntegerArr2, 512);
        BigInteger bigInteger4 = bigIntegerArr2[0];
        BigInteger[] bigIntegerArr3 = new BigInteger[65];
        bigIntegerArr3[0] = new BigInteger(Integer.toString(a3));
        while (true) {
            int i6 = 0;
            while (i6 < 64) {
                int i7 = i6 + 1;
                bigIntegerArr3[i7] = bigIntegerArr3[i6].multiply(bigInteger2).add(bigInteger).mod(e.pow(16));
                i6 = i7;
            }
            BigInteger bigInteger5 = new BigInteger("0");
            for (int i8 = 0; i8 < 64; i8++) {
                bigInteger5 = bigInteger5.add(bigIntegerArr3[i8].multiply(e.pow(i8 * 16)));
            }
            bigIntegerArr3[0] = bigIntegerArr3[64];
            int i9 = 1024;
            BigInteger add = e.pow(1023).divide(bigInteger3.multiply(bigInteger4)).add(e.pow(1023).multiply(bigInteger5).divide(bigInteger3.multiply(bigInteger4).multiply(e.pow(1024))));
            if (add.mod(e).compareTo(d) == 0) {
                add = add.add(d);
            }
            BigInteger bigInteger6 = add;
            int i10 = 0;
            while (true) {
                long j = (long) i10;
                BigInteger add2 = bigInteger3.multiply(bigInteger4).multiply(bigInteger6.add(BigInteger.valueOf(j))).add(d);
                if (add2.compareTo(e.pow(i9)) != i3) {
                    if (e.modPow(bigInteger3.multiply(bigInteger4).multiply(bigInteger6.add(BigInteger.valueOf(j))), add2).compareTo(d) != 0 || e.modPow(bigInteger3.multiply(bigInteger6.add(BigInteger.valueOf(j))), add2).compareTo(d) == 0) {
                        i10 += 2;
                        i3 = 1;
                        i9 = 1024;
                    } else {
                        bigIntegerArr[0] = add2;
                        bigIntegerArr[1] = bigInteger3;
                        return;
                    }
                }
            }
        }
    }

    private void a(long j, long j2, BigInteger[] bigIntegerArr) {
        long j3 = j;
        while (true) {
            if (j3 >= 0 && j3 <= 4294967296L) {
                break;
            }
            j3 = (long) (this.c.nextInt() * 2);
        }
        long j4 = j2;
        while (true) {
            if (j4 >= 0 && j4 <= 4294967296L && j4 / 2 != 0) {
                break;
            }
            j4 = (long) ((this.c.nextInt() * 2) + 1);
        }
        BigInteger[] bigIntegerArr2 = new BigInteger[2];
        BigInteger bigInteger = new BigInteger(Long.toString(j4));
        BigInteger bigInteger2 = new BigInteger("97781173");
        long j5 = j4;
        BigInteger[] bigIntegerArr3 = bigIntegerArr2;
        long a2 = a(j3, j5, bigIntegerArr3, 256);
        BigInteger bigInteger3 = bigIntegerArr2[0];
        long a3 = a(a2, j5, bigIntegerArr3, 512);
        BigInteger bigInteger4 = bigIntegerArr2[0];
        BigInteger[] bigIntegerArr4 = new BigInteger[33];
        bigIntegerArr4[0] = new BigInteger(Long.toString(a3));
        while (true) {
            int i = 0;
            while (i < 32) {
                int i2 = i + 1;
                bigIntegerArr4[i2] = bigIntegerArr4[i].multiply(bigInteger2).add(bigInteger).mod(e.pow(32));
                i = i2;
            }
            BigInteger bigInteger5 = new BigInteger("0");
            for (int i3 = 0; i3 < 32; i3++) {
                bigInteger5 = bigInteger5.add(bigIntegerArr4[i3].multiply(e.pow(i3 * 32)));
            }
            bigIntegerArr4[0] = bigIntegerArr4[32];
            int i4 = 1024;
            BigInteger add = e.pow(1023).divide(bigInteger3.multiply(bigInteger4)).add(e.pow(1023).multiply(bigInteger5).divide(bigInteger3.multiply(bigInteger4).multiply(e.pow(1024))));
            if (add.mod(e).compareTo(d) == 0) {
                add = add.add(d);
            }
            int i5 = 0;
            while (true) {
                long j6 = (long) i5;
                BigInteger add2 = bigInteger3.multiply(bigInteger4).multiply(add.add(BigInteger.valueOf(j6))).add(d);
                if (add2.compareTo(e.pow(i4)) != 1) {
                    if (e.modPow(bigInteger3.multiply(bigInteger4).multiply(add.add(BigInteger.valueOf(j6))), add2).compareTo(d) != 0 || e.modPow(bigInteger3.multiply(add.add(BigInteger.valueOf(j6))), add2).compareTo(d) == 0) {
                        i5 += 2;
                        i4 = 1024;
                    } else {
                        bigIntegerArr[0] = add2;
                        bigIntegerArr[1] = bigInteger3;
                        return;
                    }
                }
            }
        }
    }

    public GOST3410Parameters generateParameters() {
        BigInteger[] bigIntegerArr = new BigInteger[2];
        if (this.b == 1) {
            int nextInt = this.c.nextInt();
            int nextInt2 = this.c.nextInt();
            int i = this.a;
            if (i == 512) {
                a(nextInt, nextInt2, bigIntegerArr, 512);
            } else if (i != 1024) {
                throw new IllegalArgumentException("Ooops! key size 512 or 1024 bit.");
            } else {
                a(nextInt, nextInt2, bigIntegerArr);
            }
            BigInteger bigInteger = bigIntegerArr[0];
            BigInteger bigInteger2 = bigIntegerArr[1];
            return new GOST3410Parameters(bigInteger, bigInteger2, a(bigInteger, bigInteger2), new GOST3410ValidationParameters(nextInt, nextInt2));
        }
        long nextLong = this.c.nextLong();
        long nextLong2 = this.c.nextLong();
        int i2 = this.a;
        if (i2 == 512) {
            a(nextLong, nextLong2, bigIntegerArr, 512);
        } else if (i2 != 1024) {
            throw new IllegalStateException("Ooops! key size 512 or 1024 bit.");
        } else {
            a(nextLong, nextLong2, bigIntegerArr);
        }
        BigInteger bigInteger3 = bigIntegerArr[0];
        BigInteger bigInteger4 = bigIntegerArr[1];
        return new GOST3410Parameters(bigInteger3, bigInteger4, a(bigInteger3, bigInteger4), new GOST3410ValidationParameters(nextLong, nextLong2));
    }

    public void init(int i, int i2, SecureRandom secureRandom) {
        this.a = i;
        this.b = i2;
        this.c = secureRandom;
    }
}
