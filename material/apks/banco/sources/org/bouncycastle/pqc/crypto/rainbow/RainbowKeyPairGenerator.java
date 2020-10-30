package org.bouncycastle.pqc.crypto.rainbow;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.rainbow.util.ComputeInField;
import org.bouncycastle.pqc.crypto.rainbow.util.GF2Field;

public class RainbowKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private boolean a = false;
    private SecureRandom b;
    private RainbowKeyGenerationParameters c;
    private short[][] d;
    private short[][] e;
    private short[] f;
    private short[][] g;
    private short[][] h;
    private short[] i;
    private int j;
    private Layer[] k;
    private int[] l;
    private short[][] m;
    private short[][] n;
    private short[] o;

    private void a() {
        initialize(new RainbowKeyGenerationParameters(new SecureRandom(), new RainbowParameters()));
    }

    private void a(short[][][] sArr) {
        int length = sArr.length;
        int length2 = sArr[0].length;
        this.m = (short[][]) Array.newInstance(short.class, new int[]{length, ((length2 + 1) * length2) / 2});
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = 0;
            int i4 = 0;
            while (i3 < length2) {
                int i5 = i4;
                for (int i6 = i3; i6 < length2; i6++) {
                    if (i6 == i3) {
                        this.m[i2][i5] = sArr[i2][i3][i6];
                    } else {
                        this.m[i2][i5] = GF2Field.addElem(sArr[i2][i3][i6], sArr[i2][i6][i3]);
                    }
                    i5++;
                }
                i3++;
                i4 = i5;
            }
        }
    }

    private void b() {
        c();
        d();
        e();
        f();
    }

    private void c() {
        int i2 = this.l[this.l.length - 1] - this.l[0];
        this.d = (short[][]) Array.newInstance(short.class, new int[]{i2, i2});
        this.e = null;
        ComputeInField computeInField = new ComputeInField();
        while (this.e == null) {
            for (int i3 = 0; i3 < i2; i3++) {
                for (int i4 = 0; i4 < i2; i4++) {
                    this.d[i3][i4] = (short) (this.b.nextInt() & 255);
                }
            }
            this.e = computeInField.inverse(this.d);
        }
        this.f = new short[i2];
        for (int i5 = 0; i5 < i2; i5++) {
            this.f[i5] = (short) (this.b.nextInt() & 255);
        }
    }

    private void d() {
        int i2;
        int i3 = this.l[this.l.length - 1];
        this.g = (short[][]) Array.newInstance(short.class, new int[]{i3, i3});
        this.h = null;
        ComputeInField computeInField = new ComputeInField();
        while (true) {
            if (this.h != null) {
                break;
            }
            for (int i4 = 0; i4 < i3; i4++) {
                for (int i5 = 0; i5 < i3; i5++) {
                    this.g[i4][i5] = (short) (this.b.nextInt() & 255);
                }
            }
            this.h = computeInField.inverse(this.g);
        }
        this.i = new short[i3];
        for (i2 = 0; i2 < i3; i2++) {
            this.i[i2] = (short) (this.b.nextInt() & 255);
        }
    }

    private void e() {
        this.k = new Layer[this.j];
        int i2 = 0;
        while (i2 < this.j) {
            int i3 = i2 + 1;
            this.k[i2] = new Layer(this.l[i2], this.l[i3], this.b);
            i2 = i3;
        }
    }

    private void f() {
        ComputeInField computeInField = new ComputeInField();
        int i2 = 0;
        int i3 = this.l[this.l.length - 1] - this.l[0];
        int i4 = this.l[this.l.length - 1];
        short[][][] sArr = (short[][][]) Array.newInstance(short.class, new int[]{i3, i4, i4});
        this.n = (short[][]) Array.newInstance(short.class, new int[]{i3, i4});
        this.o = new short[i3];
        short[] sArr2 = new short[i4];
        int i5 = 0;
        int i6 = 0;
        while (i5 < this.k.length) {
            short[][][] coeffAlpha = this.k[i5].getCoeffAlpha();
            short[][][] coeffBeta = this.k[i5].getCoeffBeta();
            short[][] coeffGamma = this.k[i5].getCoeffGamma();
            short[] coeffEta = this.k[i5].getCoeffEta();
            int length = coeffAlpha[i2].length;
            int length2 = coeffBeta[i2].length;
            int i7 = 0;
            while (i7 < length) {
                int i8 = 0;
                while (i8 < length) {
                    while (i2 < length2) {
                        int i9 = i3;
                        int i10 = i4;
                        int i11 = i8 + length2;
                        short[] multVect = computeInField.multVect(coeffAlpha[i7][i8][i2], this.g[i11]);
                        int i12 = i6 + i7;
                        int i13 = i5;
                        short[] sArr3 = coeffEta;
                        sArr[i12] = computeInField.addSquareMatrix(sArr[i12], computeInField.multVects(multVect, this.g[i2]));
                        this.n[i12] = computeInField.addVect(computeInField.multVect(this.i[i2], multVect), this.n[i12]);
                        this.n[i12] = computeInField.addVect(computeInField.multVect(this.i[i11], computeInField.multVect(coeffAlpha[i7][i8][i2], this.g[i2])), this.n[i12]);
                        short[][][] sArr4 = coeffAlpha;
                        this.o[i12] = GF2Field.addElem(this.o[i12], GF2Field.multElem(GF2Field.multElem(coeffAlpha[i7][i8][i2], this.i[i11]), this.i[i2]));
                        i2++;
                        i3 = i9;
                        i4 = i10;
                        i5 = i13;
                        coeffEta = sArr3;
                        coeffAlpha = sArr4;
                    }
                    int i14 = i3;
                    int i15 = i4;
                    int i16 = i5;
                    short[][][] sArr5 = coeffAlpha;
                    short[] sArr6 = coeffEta;
                    i8++;
                    i2 = 0;
                }
                int i17 = i3;
                int i18 = i4;
                int i19 = i5;
                short[][][] sArr7 = coeffAlpha;
                short[] sArr8 = coeffEta;
                for (int i20 = 0; i20 < length2; i20++) {
                    for (int i21 = 0; i21 < length2; i21++) {
                        short[] multVect2 = computeInField.multVect(coeffBeta[i7][i20][i21], this.g[i20]);
                        int i22 = i6 + i7;
                        sArr[i22] = computeInField.addSquareMatrix(sArr[i22], computeInField.multVects(multVect2, this.g[i21]));
                        this.n[i22] = computeInField.addVect(computeInField.multVect(this.i[i21], multVect2), this.n[i22]);
                        this.n[i22] = computeInField.addVect(computeInField.multVect(this.i[i20], computeInField.multVect(coeffBeta[i7][i20][i21], this.g[i21])), this.n[i22]);
                        this.o[i22] = GF2Field.addElem(this.o[i22], GF2Field.multElem(GF2Field.multElem(coeffBeta[i7][i20][i21], this.i[i20]), this.i[i21]));
                    }
                }
                for (int i23 = 0; i23 < length2 + length; i23++) {
                    int i24 = i6 + i7;
                    this.n[i24] = computeInField.addVect(computeInField.multVect(coeffGamma[i7][i23], this.g[i23]), this.n[i24]);
                    this.o[i24] = GF2Field.addElem(this.o[i24], GF2Field.multElem(coeffGamma[i7][i23], this.i[i23]));
                }
                int i25 = i6 + i7;
                this.o[i25] = GF2Field.addElem(this.o[i25], sArr8[i7]);
                i7++;
                i3 = i17;
                i4 = i18;
                i5 = i19;
                coeffEta = sArr8;
                coeffAlpha = sArr7;
                i2 = 0;
            }
            int i26 = i3;
            int i27 = i4;
            i6 += length;
            i5++;
            i2 = 0;
        }
        short[][][] sArr9 = (short[][][]) Array.newInstance(short.class, new int[]{i3, i4, i4});
        short[][] sArr10 = (short[][]) Array.newInstance(short.class, new int[]{i3, i4});
        short[] sArr11 = new short[i3];
        for (int i28 = 0; i28 < i3; i28++) {
            for (int i29 = 0; i29 < this.d.length; i29++) {
                sArr9[i28] = computeInField.addSquareMatrix(sArr9[i28], computeInField.multMatrix(this.d[i28][i29], sArr[i29]));
                sArr10[i28] = computeInField.addVect(sArr10[i28], computeInField.multVect(this.d[i28][i29], this.n[i29]));
                sArr11[i28] = GF2Field.addElem(sArr11[i28], GF2Field.multElem(this.d[i28][i29], this.o[i29]));
            }
            sArr11[i28] = GF2Field.addElem(sArr11[i28], this.f[i28]);
        }
        this.n = sArr10;
        this.o = sArr11;
        a(sArr9);
    }

    public AsymmetricCipherKeyPair genKeyPair() {
        if (!this.a) {
            a();
        }
        b();
        RainbowPrivateKeyParameters rainbowPrivateKeyParameters = new RainbowPrivateKeyParameters(this.e, this.f, this.h, this.i, this.l, this.k);
        return new AsymmetricCipherKeyPair((AsymmetricKeyParameter) new RainbowPublicKeyParameters(this.l[this.l.length - 1] - this.l[0], this.m, this.n, this.o), (AsymmetricKeyParameter) rainbowPrivateKeyParameters);
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        return genKeyPair();
    }

    public void init(KeyGenerationParameters keyGenerationParameters) {
        initialize(keyGenerationParameters);
    }

    public void initialize(KeyGenerationParameters keyGenerationParameters) {
        this.c = (RainbowKeyGenerationParameters) keyGenerationParameters;
        this.b = new SecureRandom();
        this.l = this.c.getParameters().getVi();
        this.j = this.c.getParameters().getNumOfLayers();
        this.a = true;
    }
}
