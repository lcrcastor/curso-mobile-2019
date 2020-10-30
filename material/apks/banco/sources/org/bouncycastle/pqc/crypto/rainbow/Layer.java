package org.bouncycastle.pqc.crypto.rainbow;

import com.google.common.primitives.UnsignedBytes;
import java.lang.reflect.Array;
import java.security.SecureRandom;
import org.bouncycastle.pqc.crypto.rainbow.util.GF2Field;
import org.bouncycastle.pqc.crypto.rainbow.util.RainbowUtil;
import org.bouncycastle.util.Arrays;

public class Layer {
    private int a;
    private int b;
    private int c;
    private short[][][] d;
    private short[][][] e;
    private short[][] f;
    private short[] g;

    public Layer(byte b2, byte b3, short[][][] sArr, short[][][] sArr2, short[][] sArr3, short[] sArr4) {
        this.a = b2 & UnsignedBytes.MAX_VALUE;
        this.b = b3 & UnsignedBytes.MAX_VALUE;
        this.c = this.b - this.a;
        this.d = sArr;
        this.e = sArr2;
        this.f = sArr3;
        this.g = sArr4;
    }

    public Layer(int i, int i2, SecureRandom secureRandom) {
        this.a = i;
        this.b = i2;
        this.c = i2 - i;
        this.d = (short[][][]) Array.newInstance(short.class, new int[]{this.c, this.c, this.a});
        this.e = (short[][][]) Array.newInstance(short.class, new int[]{this.c, this.a, this.a});
        this.f = (short[][]) Array.newInstance(short.class, new int[]{this.c, this.b});
        this.g = new short[this.c];
        int i3 = this.c;
        for (int i4 = 0; i4 < i3; i4++) {
            for (int i5 = 0; i5 < this.c; i5++) {
                for (int i6 = 0; i6 < this.a; i6++) {
                    this.d[i4][i5][i6] = (short) (secureRandom.nextInt() & 255);
                }
            }
        }
        for (int i7 = 0; i7 < i3; i7++) {
            for (int i8 = 0; i8 < this.a; i8++) {
                for (int i9 = 0; i9 < this.a; i9++) {
                    this.e[i7][i8][i9] = (short) (secureRandom.nextInt() & 255);
                }
            }
        }
        for (int i10 = 0; i10 < i3; i10++) {
            for (int i11 = 0; i11 < this.b; i11++) {
                this.f[i10][i11] = (short) (secureRandom.nextInt() & 255);
            }
        }
        for (int i12 = 0; i12 < i3; i12++) {
            this.g[i12] = (short) (secureRandom.nextInt() & 255);
        }
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj != null) {
            if (!(obj instanceof Layer)) {
                return false;
            }
            Layer layer = (Layer) obj;
            if (this.a == layer.getVi() && this.b == layer.getViNext() && this.c == layer.getOi() && RainbowUtil.equals(this.d, layer.getCoeffAlpha()) && RainbowUtil.equals(this.e, layer.getCoeffBeta()) && RainbowUtil.equals(this.f, layer.getCoeffGamma()) && RainbowUtil.equals(this.g, layer.getCoeffEta())) {
                z = true;
            }
        }
        return z;
    }

    public short[][][] getCoeffAlpha() {
        return this.d;
    }

    public short[][][] getCoeffBeta() {
        return this.e;
    }

    public short[] getCoeffEta() {
        return this.g;
    }

    public short[][] getCoeffGamma() {
        return this.f;
    }

    public int getOi() {
        return this.c;
    }

    public int getVi() {
        return this.a;
    }

    public int getViNext() {
        return this.b;
    }

    public int hashCode() {
        return (((((((((((this.a * 37) + this.b) * 37) + this.c) * 37) + Arrays.hashCode(this.d)) * 37) + Arrays.hashCode(this.e)) * 37) + Arrays.hashCode(this.f)) * 37) + Arrays.hashCode(this.g);
    }

    public short[][] plugInVinegars(short[] sArr) {
        short[][] sArr2 = (short[][]) Array.newInstance(short.class, new int[]{this.c, this.c + 1});
        short[] sArr3 = new short[this.c];
        for (int i = 0; i < this.c; i++) {
            for (int i2 = 0; i2 < this.a; i2++) {
                for (int i3 = 0; i3 < this.a; i3++) {
                    sArr3[i] = GF2Field.addElem(sArr3[i], GF2Field.multElem(GF2Field.multElem(this.e[i][i2][i3], sArr[i2]), sArr[i3]));
                }
            }
        }
        for (int i4 = 0; i4 < this.c; i4++) {
            for (int i5 = 0; i5 < this.c; i5++) {
                for (int i6 = 0; i6 < this.a; i6++) {
                    sArr2[i4][i5] = GF2Field.addElem(sArr2[i4][i5], GF2Field.multElem(this.d[i4][i5][i6], sArr[i6]));
                }
            }
        }
        for (int i7 = 0; i7 < this.c; i7++) {
            for (int i8 = 0; i8 < this.a; i8++) {
                sArr3[i7] = GF2Field.addElem(sArr3[i7], GF2Field.multElem(this.f[i7][i8], sArr[i8]));
            }
        }
        for (int i9 = 0; i9 < this.c; i9++) {
            for (int i10 = this.a; i10 < this.b; i10++) {
                sArr2[i9][i10 - this.a] = GF2Field.addElem(this.f[i9][i10], sArr2[i9][i10 - this.a]);
            }
        }
        for (int i11 = 0; i11 < this.c; i11++) {
            sArr3[i11] = GF2Field.addElem(sArr3[i11], this.g[i11]);
        }
        for (int i12 = 0; i12 < this.c; i12++) {
            sArr2[i12][this.c] = sArr3[i12];
        }
        return sArr2;
    }
}
