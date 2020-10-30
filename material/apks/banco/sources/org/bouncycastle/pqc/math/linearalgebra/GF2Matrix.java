package org.bouncycastle.pqc.math.linearalgebra;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.common.primitives.UnsignedBytes;
import cz.msebera.android.httpclient.message.TokenParser;
import java.lang.reflect.Array;
import java.security.SecureRandom;

public class GF2Matrix extends Matrix {
    private int[][] a;
    private int b;

    public GF2Matrix(int i, char c) {
        this(i, c, new SecureRandom());
    }

    public GF2Matrix(int i, char c, SecureRandom secureRandom) {
        if (i <= 0) {
            throw new ArithmeticException("Size of matrix is non-positive.");
        } else if (c == 'I') {
            a(i);
        } else if (c == 'L') {
            a(i, secureRandom);
        } else if (c == 'R') {
            c(i, secureRandom);
        } else if (c == 'U') {
            b(i, secureRandom);
        } else if (c != 'Z') {
            throw new ArithmeticException("Unknown matrix type.");
        } else {
            a(i, i);
        }
    }

    private GF2Matrix(int i, int i2) {
        if (i2 <= 0 || i <= 0) {
            throw new ArithmeticException("size of matrix is non-positive");
        }
        a(i, i2);
    }

    public GF2Matrix(int i, int[][] iArr) {
        if (iArr[0].length != ((i + 31) >> 5)) {
            throw new ArithmeticException("Int array does not match given number of columns.");
        }
        this.numColumns = i;
        this.numRows = iArr.length;
        this.b = iArr[0].length;
        int i2 = i & 31;
        int i3 = i2 == 0 ? -1 : (1 << i2) - 1;
        for (int i4 = 0; i4 < this.numRows; i4++) {
            int[] iArr2 = iArr[i4];
            int i5 = this.b - 1;
            iArr2[i5] = iArr2[i5] & i3;
        }
        this.a = iArr;
    }

    public GF2Matrix(GF2Matrix gF2Matrix) {
        this.numColumns = gF2Matrix.getNumColumns();
        this.numRows = gF2Matrix.getNumRows();
        this.b = gF2Matrix.b;
        this.a = new int[gF2Matrix.a.length][];
        for (int i = 0; i < this.a.length; i++) {
            this.a[i] = IntUtils.clone(gF2Matrix.a[i]);
        }
    }

    public GF2Matrix(byte[] bArr) {
        if (bArr.length < 9) {
            throw new ArithmeticException("given array is not an encoded matrix over GF(2)");
        }
        this.numRows = LittleEndianConversions.OS2IP(bArr, 0);
        this.numColumns = LittleEndianConversions.OS2IP(bArr, 4);
        int i = ((this.numColumns + 7) >>> 3) * this.numRows;
        if (this.numRows <= 0 || i != bArr.length - 8) {
            throw new ArithmeticException("given array is not an encoded matrix over GF(2)");
        }
        this.b = (this.numColumns + 31) >>> 5;
        this.a = (int[][]) Array.newInstance(int.class, new int[]{this.numRows, this.b});
        int i2 = this.numColumns >> 5;
        int i3 = this.numColumns & 31;
        int i4 = 0;
        int i5 = 8;
        while (i4 < this.numRows) {
            int i6 = i5;
            int i7 = 0;
            while (i7 < i2) {
                this.a[i4][i7] = LittleEndianConversions.OS2IP(bArr, i6);
                i7++;
                i6 += 4;
            }
            int i8 = 0;
            while (i8 < i3) {
                int[] iArr = this.a[i4];
                int i9 = i6 + 1;
                iArr[i2] = ((bArr[i6] & UnsignedBytes.MAX_VALUE) << i8) ^ iArr[i2];
                i8 += 8;
                i6 = i9;
            }
            i4++;
            i5 = i6;
        }
    }

    private void a(int i) {
        this.numRows = i;
        this.numColumns = i;
        this.b = (i + 31) >>> 5;
        this.a = (int[][]) Array.newInstance(int.class, new int[]{this.numRows, this.b});
        for (int i2 = 0; i2 < this.numRows; i2++) {
            for (int i3 = 0; i3 < this.b; i3++) {
                this.a[i2][i3] = 0;
            }
        }
        for (int i4 = 0; i4 < this.numRows; i4++) {
            this.a[i4][i4 >>> 5] = 1 << (i4 & 31);
        }
    }

    private void a(int i, int i2) {
        this.numRows = i;
        this.numColumns = i2;
        this.b = (i2 + 31) >>> 5;
        this.a = (int[][]) Array.newInstance(int.class, new int[]{this.numRows, this.b});
        for (int i3 = 0; i3 < this.numRows; i3++) {
            for (int i4 = 0; i4 < this.b; i4++) {
                this.a[i3][i4] = 0;
            }
        }
    }

    private void a(int i, SecureRandom secureRandom) {
        this.numRows = i;
        this.numColumns = i;
        this.b = (i + 31) >>> 5;
        this.a = (int[][]) Array.newInstance(int.class, new int[]{this.numRows, this.b});
        for (int i2 = 0; i2 < this.numRows; i2++) {
            int i3 = i2 >>> 5;
            int i4 = i2 & 31;
            int i5 = 31 - i4;
            int i6 = 1 << i4;
            for (int i7 = 0; i7 < i3; i7++) {
                this.a[i2][i7] = secureRandom.nextInt();
            }
            this.a[i2][i3] = i6 | (secureRandom.nextInt() >>> i5);
            while (true) {
                i3++;
                if (i3 >= this.b) {
                    break;
                }
                this.a[i2][i3] = 0;
            }
        }
    }

    private static void a(int[] iArr, int[] iArr2, int i) {
        for (int length = iArr2.length - 1; length >= i; length--) {
            iArr2[length] = iArr[length] ^ iArr2[length];
        }
    }

    private static void a(int[][] iArr, int i, int i2) {
        int[] iArr2 = iArr[i];
        iArr[i] = iArr[i2];
        iArr[i2] = iArr2;
    }

    private void b(int i, SecureRandom secureRandom) {
        this.numRows = i;
        this.numColumns = i;
        this.b = (i + 31) >>> 5;
        this.a = (int[][]) Array.newInstance(int.class, new int[]{this.numRows, this.b});
        int i2 = i & 31;
        int i3 = i2 == 0 ? -1 : (1 << i2) - 1;
        for (int i4 = 0; i4 < this.numRows; i4++) {
            int i5 = i4 >>> 5;
            int i6 = i4 & 31;
            int i7 = 1 << i6;
            for (int i8 = 0; i8 < i5; i8++) {
                this.a[i4][i8] = 0;
            }
            this.a[i4][i5] = (secureRandom.nextInt() << i6) | i7;
            while (true) {
                i5++;
                if (i5 >= this.b) {
                    break;
                }
                this.a[i4][i5] = secureRandom.nextInt();
            }
            int[] iArr = this.a[i4];
            int i9 = this.b - 1;
            iArr[i9] = iArr[i9] & i3;
        }
    }

    private void c(int i, SecureRandom secureRandom) {
        this.numRows = i;
        this.numColumns = i;
        this.b = (i + 31) >>> 5;
        this.a = (int[][]) Array.newInstance(int.class, new int[]{this.numRows, this.b});
        GF2Matrix gF2Matrix = (GF2Matrix) new GF2Matrix(i, Matrix.MATRIX_TYPE_RANDOM_LT, secureRandom).rightMultiply((Matrix) new GF2Matrix(i, Matrix.MATRIX_TYPE_RANDOM_UT, secureRandom));
        int[] vector = new Permutation(i, secureRandom).getVector();
        for (int i2 = 0; i2 < i; i2++) {
            System.arraycopy(gF2Matrix.a[i2], 0, this.a[vector[i2]], 0, this.b);
        }
    }

    public static GF2Matrix[] createRandomRegularMatrixAndItsInverse(int i, SecureRandom secureRandom) {
        int i2 = i;
        SecureRandom secureRandom2 = secureRandom;
        GF2Matrix[] gF2MatrixArr = new GF2Matrix[2];
        int i3 = (i2 + 31) >> 5;
        GF2Matrix gF2Matrix = new GF2Matrix(i2, Matrix.MATRIX_TYPE_RANDOM_LT, secureRandom2);
        GF2Matrix gF2Matrix2 = new GF2Matrix(i2, Matrix.MATRIX_TYPE_RANDOM_UT, secureRandom2);
        GF2Matrix gF2Matrix3 = (GF2Matrix) gF2Matrix.rightMultiply((Matrix) gF2Matrix2);
        Permutation permutation = new Permutation(i2, secureRandom2);
        int[] vector = permutation.getVector();
        int[][] iArr = (int[][]) Array.newInstance(int.class, new int[]{i2, i3});
        for (int i4 = 0; i4 < i2; i4++) {
            System.arraycopy(gF2Matrix3.a[vector[i4]], 0, iArr[i4], 0, i3);
        }
        gF2MatrixArr[0] = new GF2Matrix(i2, iArr);
        GF2Matrix gF2Matrix4 = new GF2Matrix(i2, 'I');
        int i5 = 0;
        while (i5 < i2) {
            int i6 = i5 >>> 5;
            int i7 = 1 << (i5 & 31);
            int i8 = i5 + 1;
            for (int i9 = i8; i9 < i2; i9++) {
                if ((gF2Matrix.a[i9][i6] & i7) != 0) {
                    for (int i10 = 0; i10 <= i6; i10++) {
                        int[] iArr2 = gF2Matrix4.a[i9];
                        iArr2[i10] = iArr2[i10] ^ gF2Matrix4.a[i5][i10];
                    }
                }
            }
            i5 = i8;
        }
        GF2Matrix gF2Matrix5 = new GF2Matrix(i2, 'I');
        for (int i11 = i2 - 1; i11 >= 0; i11--) {
            int i12 = i11 >>> 5;
            int i13 = 1 << (i11 & 31);
            for (int i14 = i11 - 1; i14 >= 0; i14--) {
                if ((gF2Matrix2.a[i14][i12] & i13) != 0) {
                    for (int i15 = i12; i15 < i3; i15++) {
                        int[] iArr3 = gF2Matrix5.a[i14];
                        iArr3[i15] = iArr3[i15] ^ gF2Matrix5.a[i11][i15];
                    }
                }
            }
        }
        gF2MatrixArr[1] = (GF2Matrix) gF2Matrix5.rightMultiply(gF2Matrix4.rightMultiply(permutation));
        return gF2MatrixArr;
    }

    public Matrix computeInverse() {
        if (this.numRows != this.numColumns) {
            throw new ArithmeticException("Matrix is not invertible.");
        }
        int[][] iArr = (int[][]) Array.newInstance(int.class, new int[]{this.numRows, this.b});
        for (int i = this.numRows - 1; i >= 0; i--) {
            iArr[i] = IntUtils.clone(this.a[i]);
        }
        int[][] iArr2 = (int[][]) Array.newInstance(int.class, new int[]{this.numRows, this.b});
        for (int i2 = this.numRows - 1; i2 >= 0; i2--) {
            iArr2[i2][i2 >> 5] = 1 << (i2 & 31);
        }
        for (int i3 = 0; i3 < this.numRows; i3++) {
            int i4 = i3 >> 5;
            int i5 = 1 << (i3 & 31);
            if ((iArr[i3][i4] & i5) == 0) {
                int i6 = i3 + 1;
                boolean z = false;
                while (i6 < this.numRows) {
                    if ((iArr[i6][i4] & i5) != 0) {
                        a(iArr, i3, i6);
                        a(iArr2, i3, i6);
                        i6 = this.numRows;
                        z = true;
                    }
                    i6++;
                }
                if (!z) {
                    throw new ArithmeticException("Matrix is not invertible.");
                }
            }
            for (int i7 = this.numRows - 1; i7 >= 0; i7--) {
                if (!(i7 == i3 || (iArr[i7][i4] & i5) == 0)) {
                    a(iArr[i3], iArr[i7], i4);
                    a(iArr2[i3], iArr2[i7], 0);
                }
            }
        }
        return new GF2Matrix(this.numColumns, iArr2);
    }

    public Matrix computeTranspose() {
        int[][] iArr = (int[][]) Array.newInstance(int.class, new int[]{this.numColumns, (this.numRows + 31) >>> 5});
        for (int i = 0; i < this.numRows; i++) {
            for (int i2 = 0; i2 < this.numColumns; i2++) {
                int i3 = i >>> 5;
                int i4 = i & 31;
                if (((this.a[i][i2 >>> 5] >>> (i2 & 31)) & 1) == 1) {
                    int[] iArr2 = iArr[i2];
                    iArr2[i3] = (1 << i4) | iArr2[i3];
                }
            }
        }
        return new GF2Matrix(this.numRows, iArr);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GF2Matrix)) {
            return false;
        }
        GF2Matrix gF2Matrix = (GF2Matrix) obj;
        if (this.numRows != gF2Matrix.numRows || this.numColumns != gF2Matrix.numColumns || this.b != gF2Matrix.b) {
            return false;
        }
        for (int i = 0; i < this.numRows; i++) {
            if (!IntUtils.equals(this.a[i], gF2Matrix.a[i])) {
                return false;
            }
        }
        return true;
    }

    public GF2Matrix extendLeftCompactForm() {
        GF2Matrix gF2Matrix = new GF2Matrix(this.numRows, this.numColumns + this.numRows);
        int i = (this.numRows - 1) + this.numColumns;
        int i2 = this.numRows - 1;
        while (i2 >= 0) {
            System.arraycopy(this.a[i2], 0, gF2Matrix.a[i2], 0, this.b);
            int[] iArr = gF2Matrix.a[i2];
            int i3 = i >> 5;
            iArr[i3] = iArr[i3] | (1 << (i & 31));
            i2--;
            i--;
        }
        return gF2Matrix;
    }

    public GF2Matrix extendRightCompactForm() {
        GF2Matrix gF2Matrix = new GF2Matrix(this.numRows, this.numRows + this.numColumns);
        int i = this.numRows >> 5;
        int i2 = this.numRows & 31;
        for (int i3 = this.numRows - 1; i3 >= 0; i3--) {
            int[] iArr = gF2Matrix.a[i3];
            int i4 = i3 >> 5;
            iArr[i4] = iArr[i4] | (1 << (i3 & 31));
            int i5 = 0;
            if (i2 != 0) {
                int i6 = i;
                while (i5 < this.b - 1) {
                    int i7 = this.a[i3][i5];
                    int[] iArr2 = gF2Matrix.a[i3];
                    int i8 = i6 + 1;
                    iArr2[i6] = iArr2[i6] | (i7 << i2);
                    int[] iArr3 = gF2Matrix.a[i3];
                    iArr3[i8] = (i7 >>> (32 - i2)) | iArr3[i8];
                    i5++;
                    i6 = i8;
                }
                int i9 = this.a[i3][this.b - 1];
                int[] iArr4 = gF2Matrix.a[i3];
                int i10 = i6 + 1;
                iArr4[i6] = iArr4[i6] | (i9 << i2);
                if (i10 < gF2Matrix.b) {
                    int[] iArr5 = gF2Matrix.a[i3];
                    iArr5[i10] = (i9 >>> (32 - i2)) | iArr5[i10];
                }
            } else {
                System.arraycopy(this.a[i3], 0, gF2Matrix.a[i3], i, this.b);
            }
        }
        return gF2Matrix;
    }

    public byte[] getEncoded() {
        byte[] bArr = new byte[((((this.numColumns + 7) >>> 3) * this.numRows) + 8)];
        LittleEndianConversions.I2OSP(this.numRows, bArr, 0);
        LittleEndianConversions.I2OSP(this.numColumns, bArr, 4);
        int i = this.numColumns >>> 5;
        int i2 = this.numColumns & 31;
        int i3 = 0;
        int i4 = 8;
        while (i3 < this.numRows) {
            int i5 = i4;
            int i6 = 0;
            while (i6 < i) {
                LittleEndianConversions.I2OSP(this.a[i3][i6], bArr, i5);
                i6++;
                i5 += 4;
            }
            int i7 = 0;
            while (i7 < i2) {
                int i8 = i5 + 1;
                bArr[i5] = (byte) ((this.a[i3][i] >>> i7) & 255);
                i7 += 8;
                i5 = i8;
            }
            i3++;
            i4 = i5;
        }
        return bArr;
    }

    public double getHammingWeight() {
        int i = this.numColumns & 31;
        int i2 = i == 0 ? this.b : this.b - 1;
        double d = 0.0d;
        double d2 = 0.0d;
        for (int i3 = 0; i3 < this.numRows; i3++) {
            int i4 = 0;
            while (i4 < i2) {
                int i5 = this.a[i3][i4];
                double d3 = d2;
                double d4 = d;
                for (int i6 = 0; i6 < 32; i6++) {
                    d4 += (double) ((i5 >>> i6) & 1);
                    d3 += 1.0d;
                }
                i4++;
                d = d4;
                d2 = d3;
            }
            int i7 = this.a[i3][this.b - 1];
            for (int i8 = 0; i8 < i; i8++) {
                d += (double) ((i7 >>> i8) & 1);
                d2 += 1.0d;
            }
        }
        return d / d2;
    }

    public int[][] getIntArray() {
        return this.a;
    }

    public GF2Matrix getLeftSubMatrix() {
        if (this.numColumns <= this.numRows) {
            throw new ArithmeticException("empty submatrix");
        }
        int i = (this.numRows + 31) >> 5;
        int[][] iArr = (int[][]) Array.newInstance(int.class, new int[]{this.numRows, i});
        int i2 = (1 << (this.numRows & 31)) - 1;
        if (i2 == 0) {
            i2 = -1;
        }
        for (int i3 = this.numRows - 1; i3 >= 0; i3--) {
            System.arraycopy(this.a[i3], 0, iArr[i3], 0, i);
            int[] iArr2 = iArr[i3];
            int i4 = i - 1;
            iArr2[i4] = iArr2[i4] & i2;
        }
        return new GF2Matrix(this.numRows, iArr);
    }

    public int getLength() {
        return this.b;
    }

    public GF2Matrix getRightSubMatrix() {
        if (this.numColumns <= this.numRows) {
            throw new ArithmeticException("empty submatrix");
        }
        int i = this.numRows >> 5;
        int i2 = this.numRows & 31;
        GF2Matrix gF2Matrix = new GF2Matrix(this.numRows, this.numColumns - this.numRows);
        for (int i3 = this.numRows - 1; i3 >= 0; i3--) {
            int i4 = 0;
            if (i2 != 0) {
                int i5 = i;
                while (i4 < gF2Matrix.b - 1) {
                    int i6 = i5 + 1;
                    gF2Matrix.a[i3][i4] = (this.a[i3][i5] >>> i2) | (this.a[i3][i6] << (32 - i2));
                    i4++;
                    i5 = i6;
                }
                int i7 = i5 + 1;
                gF2Matrix.a[i3][gF2Matrix.b - 1] = this.a[i3][i5] >>> i2;
                if (i7 < this.b) {
                    int[] iArr = gF2Matrix.a[i3];
                    int i8 = gF2Matrix.b - 1;
                    iArr[i8] = iArr[i8] | (this.a[i3][i7] << (32 - i2));
                }
            } else {
                System.arraycopy(this.a[i3], i, gF2Matrix.a[i3], 0, gF2Matrix.b);
            }
        }
        return gF2Matrix;
    }

    public int[] getRow(int i) {
        return this.a[i];
    }

    public int hashCode() {
        int i = (((this.numRows * 31) + this.numColumns) * 31) + this.b;
        for (int i2 = 0; i2 < this.numRows; i2++) {
            i = (i * 31) + this.a[i2].hashCode();
        }
        return i;
    }

    public boolean isZero() {
        for (int i = 0; i < this.numRows; i++) {
            for (int i2 = 0; i2 < this.b; i2++) {
                if (this.a[i][i2] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public Matrix leftMultiply(Permutation permutation) {
        int[] vector = permutation.getVector();
        if (vector.length != this.numRows) {
            throw new ArithmeticException("length mismatch");
        }
        int[][] iArr = new int[this.numRows][];
        for (int i = this.numRows - 1; i >= 0; i--) {
            iArr[i] = IntUtils.clone(this.a[vector[i]]);
        }
        return new GF2Matrix(this.numRows, iArr);
    }

    public Vector leftMultiply(Vector vector) {
        if (!(vector instanceof GF2Vector)) {
            throw new ArithmeticException("vector is not defined over GF(2)");
        } else if (vector.length != this.numRows) {
            throw new ArithmeticException("length mismatch");
        } else {
            int[] vecArray = ((GF2Vector) vector).getVecArray();
            int[] iArr = new int[this.b];
            int i = this.numRows >> 5;
            int i2 = 1 << (this.numRows & 31);
            int i3 = 0;
            int i4 = 0;
            while (i3 < i) {
                int i5 = i4;
                int i6 = 1;
                do {
                    if ((vecArray[i3] & i6) != 0) {
                        for (int i7 = 0; i7 < this.b; i7++) {
                            iArr[i7] = iArr[i7] ^ this.a[i5][i7];
                        }
                    }
                    i5++;
                    i6 <<= 1;
                } while (i6 != 0);
                i3++;
                i4 = i5;
            }
            for (int i8 = 1; i8 != i2; i8 <<= 1) {
                if ((vecArray[i] & i8) != 0) {
                    for (int i9 = 0; i9 < this.b; i9++) {
                        iArr[i9] = iArr[i9] ^ this.a[i4][i9];
                    }
                }
                i4++;
            }
            return new GF2Vector(iArr, this.numColumns);
        }
    }

    public Vector leftMultiplyLeftCompactForm(Vector vector) {
        if (!(vector instanceof GF2Vector)) {
            throw new ArithmeticException("vector is not defined over GF(2)");
        } else if (vector.length != this.numRows) {
            throw new ArithmeticException("length mismatch");
        } else {
            int[] vecArray = ((GF2Vector) vector).getVecArray();
            int[] iArr = new int[(((this.numRows + this.numColumns) + 31) >>> 5)];
            int i = this.numRows >>> 5;
            int i2 = 0;
            int i3 = 0;
            while (i2 < i) {
                int i4 = i3;
                int i5 = 1;
                do {
                    if ((vecArray[i2] & i5) != 0) {
                        for (int i6 = 0; i6 < this.b; i6++) {
                            iArr[i6] = iArr[i6] ^ this.a[i4][i6];
                        }
                        int i7 = (this.numColumns + i4) >>> 5;
                        iArr[i7] = (1 << ((this.numColumns + i4) & 31)) | iArr[i7];
                    }
                    i4++;
                    i5 <<= 1;
                } while (i5 != 0);
                i2++;
                i3 = i4;
            }
            int i8 = 1 << (this.numRows & 31);
            int i9 = i3;
            for (int i10 = 1; i10 != i8; i10 <<= 1) {
                if ((vecArray[i] & i10) != 0) {
                    for (int i11 = 0; i11 < this.b; i11++) {
                        iArr[i11] = iArr[i11] ^ this.a[i9][i11];
                    }
                    int i12 = (this.numColumns + i9) >>> 5;
                    iArr[i12] = (1 << ((this.numColumns + i9) & 31)) | iArr[i12];
                }
                i9++;
            }
            return new GF2Vector(iArr, this.numRows + this.numColumns);
        }
    }

    public Matrix rightMultiply(Matrix matrix) {
        if (!(matrix instanceof GF2Matrix)) {
            throw new ArithmeticException("matrix is not defined over GF(2)");
        } else if (matrix.numRows != this.numColumns) {
            throw new ArithmeticException("length mismatch");
        } else {
            GF2Matrix gF2Matrix = (GF2Matrix) matrix;
            GF2Matrix gF2Matrix2 = new GF2Matrix(this.numRows, matrix.numColumns);
            int i = this.numColumns & 31;
            int i2 = i == 0 ? this.b : this.b - 1;
            for (int i3 = 0; i3 < this.numRows; i3++) {
                int i4 = 0;
                int i5 = 0;
                while (i4 < i2) {
                    int i6 = this.a[i3][i4];
                    int i7 = i5;
                    for (int i8 = 0; i8 < 32; i8++) {
                        if (((1 << i8) & i6) != 0) {
                            for (int i9 = 0; i9 < gF2Matrix.b; i9++) {
                                int[] iArr = gF2Matrix2.a[i3];
                                iArr[i9] = iArr[i9] ^ gF2Matrix.a[i7][i9];
                            }
                        }
                        i7++;
                    }
                    i4++;
                    i5 = i7;
                }
                int i10 = this.a[i3][this.b - 1];
                int i11 = i5;
                for (int i12 = 0; i12 < i; i12++) {
                    if (((1 << i12) & i10) != 0) {
                        for (int i13 = 0; i13 < gF2Matrix.b; i13++) {
                            int[] iArr2 = gF2Matrix2.a[i3];
                            iArr2[i13] = iArr2[i13] ^ gF2Matrix.a[i11][i13];
                        }
                    }
                    i11++;
                }
            }
            return gF2Matrix2;
        }
    }

    public Matrix rightMultiply(Permutation permutation) {
        int[] vector = permutation.getVector();
        if (vector.length != this.numColumns) {
            throw new ArithmeticException("length mismatch");
        }
        GF2Matrix gF2Matrix = new GF2Matrix(this.numRows, this.numColumns);
        for (int i = this.numColumns - 1; i >= 0; i--) {
            int i2 = i >>> 5;
            int i3 = i & 31;
            int i4 = vector[i] >>> 5;
            int i5 = vector[i] & 31;
            for (int i6 = this.numRows - 1; i6 >= 0; i6--) {
                int[] iArr = gF2Matrix.a[i6];
                iArr[i2] = iArr[i2] | (((this.a[i6][i4] >>> i5) & 1) << i3);
            }
        }
        return gF2Matrix;
    }

    public Vector rightMultiply(Vector vector) {
        if (!(vector instanceof GF2Vector)) {
            throw new ArithmeticException("vector is not defined over GF(2)");
        } else if (vector.length != this.numColumns) {
            throw new ArithmeticException("length mismatch");
        } else {
            int[] vecArray = ((GF2Vector) vector).getVecArray();
            int[] iArr = new int[((this.numRows + 31) >>> 5)];
            for (int i = 0; i < this.numRows; i++) {
                int i2 = 0;
                for (int i3 = 0; i3 < this.b; i3++) {
                    i2 ^= this.a[i][i3] & vecArray[i3];
                }
                int i4 = 0;
                for (int i5 = 0; i5 < 32; i5++) {
                    i4 ^= (i2 >>> i5) & 1;
                }
                if (i4 == 1) {
                    int i6 = i >>> 5;
                    iArr[i6] = iArr[i6] | (1 << (i & 31));
                }
            }
            return new GF2Vector(iArr, this.numRows);
        }
    }

    public Vector rightMultiplyRightCompactForm(Vector vector) {
        int i;
        Vector vector2 = vector;
        if (!(vector2 instanceof GF2Vector)) {
            throw new ArithmeticException("vector is not defined over GF(2)");
        } else if (vector2.length != this.numColumns + this.numRows) {
            throw new ArithmeticException("length mismatch");
        } else {
            int[] vecArray = ((GF2Vector) vector2).getVecArray();
            int[] iArr = new int[((this.numRows + 31) >>> 5)];
            int i2 = this.numRows >> 5;
            int i3 = this.numRows & 31;
            for (int i4 = 0; i4 < this.numRows; i4++) {
                int i5 = i4 >> 5;
                int i6 = i4 & 31;
                int i7 = (vecArray[i5] >>> i6) & 1;
                if (i3 != 0) {
                    int i8 = i2;
                    int i9 = i7;
                    int i10 = 0;
                    while (i10 < this.b - 1) {
                        int i11 = i8 + 1;
                        i9 ^= ((vecArray[i8] >>> i3) | (vecArray[i11] << (32 - i3))) & this.a[i4][i10];
                        i10++;
                        i8 = i11;
                    }
                    int i12 = i8 + 1;
                    int i13 = vecArray[i8] >>> i3;
                    if (i12 < vecArray.length) {
                        i13 |= vecArray[i12] << (32 - i3);
                    }
                    i = (this.a[i4][this.b - 1] & i13) ^ i9;
                } else {
                    int i14 = i2;
                    i = i7;
                    int i15 = 0;
                    while (i15 < this.b) {
                        i ^= vecArray[i14] & this.a[i4][i15];
                        i15++;
                        i14++;
                    }
                }
                int i16 = i;
                int i17 = 0;
                for (int i18 = 0; i18 < 32; i18++) {
                    i17 ^= i16 & 1;
                    i16 >>>= 1;
                }
                if (i17 == 1) {
                    iArr[i5] = iArr[i5] | (1 << i6);
                }
            }
            return new GF2Vector(iArr, this.numRows);
        }
    }

    public String toString() {
        int i = this.numColumns & 31;
        int i2 = i == 0 ? this.b : this.b - 1;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = 0; i3 < this.numRows; i3++) {
            StringBuilder sb = new StringBuilder();
            sb.append(i3);
            sb.append(": ");
            stringBuffer.append(sb.toString());
            for (int i4 = 0; i4 < i2; i4++) {
                int i5 = this.a[i3][i4];
                for (int i6 = 0; i6 < 32; i6++) {
                    if (((i5 >>> i6) & 1) == 0) {
                        stringBuffer.append(TarjetasConstants.ULT_NUM_AMEX);
                    } else {
                        stringBuffer.append('1');
                    }
                }
                stringBuffer.append(TokenParser.SP);
            }
            int i7 = this.a[i3][this.b - 1];
            for (int i8 = 0; i8 < i; i8++) {
                if (((i7 >>> i8) & 1) == 0) {
                    stringBuffer.append(TarjetasConstants.ULT_NUM_AMEX);
                } else {
                    stringBuffer.append('1');
                }
            }
            stringBuffer.append(10);
        }
        return stringBuffer.toString();
    }
}
