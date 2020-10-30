package org.bouncycastle.pqc.crypto.rainbow.util;

import java.lang.reflect.Array;

public class ComputeInField {
    short[] a;
    private short[][] b;

    private void a() {
        for (int length = this.b.length - 1; length > 0; length--) {
            for (int i = length - 1; i >= 0; i--) {
                short s = this.b[i][length];
                short invElem = GF2Field.invElem(this.b[length][length]);
                if (invElem == 0) {
                    throw new RuntimeException("The matrix is not invertible");
                }
                for (int i2 = length; i2 < this.b.length * 2; i2++) {
                    this.b[i][i2] = GF2Field.addElem(this.b[i][i2], GF2Field.multElem(s, GF2Field.multElem(this.b[length][i2], invElem)));
                }
            }
        }
    }

    private void a(boolean z) {
        int length = z ? this.b.length * 2 : this.b.length + 1;
        int i = 0;
        while (i < this.b.length - 1) {
            int i2 = i + 1;
            for (int i3 = i2; i3 < this.b.length; i3++) {
                short s = this.b[i3][i];
                short invElem = GF2Field.invElem(this.b[i][i]);
                if (invElem == 0) {
                    throw new RuntimeException("Matrix not invertible! We have to choose another one!");
                }
                for (int i4 = i; i4 < length; i4++) {
                    this.b[i3][i4] = GF2Field.addElem(this.b[i3][i4], GF2Field.multElem(s, GF2Field.multElem(this.b[i][i4], invElem)));
                }
            }
            i = i2;
        }
    }

    private void b() {
        short invElem = GF2Field.invElem(this.b[this.b.length - 1][this.b.length - 1]);
        if (invElem == 0) {
            throw new RuntimeException("The equation system is not solvable");
        }
        this.a[this.b.length - 1] = GF2Field.multElem(this.b[this.b.length - 1][this.b.length], invElem);
        for (int length = this.b.length - 2; length >= 0; length--) {
            short s = this.b[length][this.b.length];
            for (int length2 = this.b.length - 1; length2 > length; length2--) {
                s = GF2Field.addElem(s, GF2Field.multElem(this.b[length][length2], this.a[length2]));
            }
            short invElem2 = GF2Field.invElem(this.b[length][length]);
            if (invElem2 == 0) {
                throw new RuntimeException("Not solvable equation system");
            }
            this.a[length] = GF2Field.multElem(s, invElem2);
        }
    }

    public short[][] addSquareMatrix(short[][] sArr, short[][] sArr2) {
        if (sArr.length == sArr2.length && sArr[0].length == sArr2[0].length) {
            short[][] sArr3 = (short[][]) Array.newInstance(short.class, new int[]{sArr.length, sArr.length});
            for (int i = 0; i < sArr.length; i++) {
                for (int i2 = 0; i2 < sArr2.length; i2++) {
                    sArr3[i][i2] = GF2Field.addElem(sArr[i][i2], sArr2[i][i2]);
                }
            }
            return sArr3;
        }
        throw new RuntimeException("Addition is not possible!");
    }

    public short[] addVect(short[] sArr, short[] sArr2) {
        if (sArr.length != sArr2.length) {
            throw new RuntimeException("Multiplication is not possible!");
        }
        short[] sArr3 = new short[sArr.length];
        for (int i = 0; i < sArr3.length; i++) {
            sArr3[i] = GF2Field.addElem(sArr[i], sArr2[i]);
        }
        return sArr3;
    }

    public short[][] inverse(short[][] sArr) {
        try {
            this.b = (short[][]) Array.newInstance(short.class, new int[]{sArr.length, sArr.length * 2});
            if (sArr.length != sArr[0].length) {
                throw new RuntimeException("The matrix is not invertible. Please choose another one!");
            }
            for (int i = 0; i < sArr.length; i++) {
                for (int i2 = 0; i2 < sArr.length; i2++) {
                    this.b[i][i2] = sArr[i][i2];
                }
                for (int length = sArr.length; length < sArr.length * 2; length++) {
                    this.b[i][length] = 0;
                }
                this.b[i][this.b.length + i] = 1;
            }
            a(true);
            for (int i3 = 0; i3 < this.b.length; i3++) {
                short invElem = GF2Field.invElem(this.b[i3][i3]);
                for (int i4 = i3; i4 < this.b.length * 2; i4++) {
                    this.b[i3][i4] = GF2Field.multElem(this.b[i3][i4], invElem);
                }
            }
            a();
            short[][] sArr2 = (short[][]) Array.newInstance(short.class, new int[]{this.b.length, this.b.length});
            for (int i5 = 0; i5 < this.b.length; i5++) {
                for (int length2 = this.b.length; length2 < this.b.length * 2; length2++) {
                    sArr2[i5][length2 - this.b.length] = this.b[i5][length2];
                }
            }
            return sArr2;
        } catch (RuntimeException unused) {
            return null;
        }
    }

    public short[][] multMatrix(short s, short[][] sArr) {
        short[][] sArr2 = (short[][]) Array.newInstance(short.class, new int[]{sArr.length, sArr[0].length});
        for (int i = 0; i < sArr.length; i++) {
            for (int i2 = 0; i2 < sArr[0].length; i2++) {
                sArr2[i][i2] = GF2Field.multElem(s, sArr[i][i2]);
            }
        }
        return sArr2;
    }

    public short[] multVect(short s, short[] sArr) {
        short[] sArr2 = new short[sArr.length];
        for (int i = 0; i < sArr2.length; i++) {
            sArr2[i] = GF2Field.multElem(s, sArr[i]);
        }
        return sArr2;
    }

    public short[][] multVects(short[] sArr, short[] sArr2) {
        if (sArr.length != sArr2.length) {
            throw new RuntimeException("Multiplication is not possible!");
        }
        short[][] sArr3 = (short[][]) Array.newInstance(short.class, new int[]{sArr.length, sArr2.length});
        for (int i = 0; i < sArr.length; i++) {
            for (int i2 = 0; i2 < sArr2.length; i2++) {
                sArr3[i][i2] = GF2Field.multElem(sArr[i], sArr2[i2]);
            }
        }
        return sArr3;
    }

    public short[] multiplyMatrix(short[][] sArr, short[] sArr2) {
        if (sArr[0].length != sArr2.length) {
            throw new RuntimeException("Multiplication is not possible!");
        }
        short[] sArr3 = new short[sArr.length];
        for (int i = 0; i < sArr.length; i++) {
            for (int i2 = 0; i2 < sArr2.length; i2++) {
                sArr3[i] = GF2Field.addElem(sArr3[i], GF2Field.multElem(sArr[i][i2], sArr2[i2]));
            }
        }
        return sArr3;
    }

    public short[][] multiplyMatrix(short[][] sArr, short[][] sArr2) {
        if (sArr[0].length != sArr2.length) {
            throw new RuntimeException("Multiplication is not possible!");
        }
        this.b = (short[][]) Array.newInstance(short.class, new int[]{sArr.length, sArr2[0].length});
        for (int i = 0; i < sArr.length; i++) {
            for (int i2 = 0; i2 < sArr2.length; i2++) {
                for (int i3 = 0; i3 < sArr2[0].length; i3++) {
                    this.b[i][i3] = GF2Field.addElem(this.b[i][i3], GF2Field.multElem(sArr[i][i2], sArr2[i2][i3]));
                }
            }
        }
        return this.b;
    }

    public short[] solveEquation(short[][] sArr, short[] sArr2) {
        try {
            if (sArr.length != sArr2.length) {
                throw new RuntimeException("The equation system is not solvable");
            }
            this.b = (short[][]) Array.newInstance(short.class, new int[]{sArr.length, sArr.length + 1});
            this.a = new short[sArr.length];
            for (int i = 0; i < sArr.length; i++) {
                for (int i2 = 0; i2 < sArr[0].length; i2++) {
                    this.b[i][i2] = sArr[i][i2];
                }
            }
            for (int i3 = 0; i3 < sArr2.length; i3++) {
                this.b[i3][sArr2.length] = GF2Field.addElem(sArr2[i3], this.b[i3][sArr2.length]);
            }
            a(false);
            b();
            return this.a;
        } catch (RuntimeException unused) {
            return null;
        }
    }
}
