package org.bouncycastle.pqc.crypto.gmss;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Vector;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.bouncycastle.pqc.crypto.gmss.util.WinternitzOTSignature;
import org.bouncycastle.util.Arrays;

public class GMSSPrivateKeyParameters extends GMSSKeyParameters {
    private int[] A;
    private int B;
    private Digest C;
    private int D;
    private GMSSRandom E;
    private int[] F;
    private int[] b;
    private byte[][] c;
    private byte[][] d;
    private byte[][][] e;
    private byte[][][] f;
    private Treehash[][] g;
    private Treehash[][] h;
    private Vector[] i;
    private Vector[] j;
    private Vector[][] k;
    private Vector[][] l;
    private byte[][][] m;
    private GMSSLeaf[] n;
    private GMSSLeaf[] o;
    private GMSSLeaf[] p;
    private int[] q;
    private GMSSParameters r;
    private byte[][] s;
    private GMSSRootCalc[] t;
    private byte[][] u;
    private GMSSRootSig[] v;
    private GMSSDigestProvider w;
    private boolean x;
    private int[] y;
    private int[] z;

    private GMSSPrivateKeyParameters(GMSSPrivateKeyParameters gMSSPrivateKeyParameters) {
        super(true, gMSSPrivateKeyParameters.getParameters());
        this.x = false;
        this.b = Arrays.clone(gMSSPrivateKeyParameters.b);
        this.c = Arrays.clone(gMSSPrivateKeyParameters.c);
        this.d = Arrays.clone(gMSSPrivateKeyParameters.d);
        this.e = Arrays.clone(gMSSPrivateKeyParameters.e);
        this.f = Arrays.clone(gMSSPrivateKeyParameters.f);
        this.g = gMSSPrivateKeyParameters.g;
        this.h = gMSSPrivateKeyParameters.h;
        this.i = gMSSPrivateKeyParameters.i;
        this.j = gMSSPrivateKeyParameters.j;
        this.k = gMSSPrivateKeyParameters.k;
        this.l = gMSSPrivateKeyParameters.l;
        this.m = Arrays.clone(gMSSPrivateKeyParameters.m);
        this.n = gMSSPrivateKeyParameters.n;
        this.o = gMSSPrivateKeyParameters.o;
        this.p = gMSSPrivateKeyParameters.p;
        this.q = gMSSPrivateKeyParameters.q;
        this.r = gMSSPrivateKeyParameters.r;
        this.s = Arrays.clone(gMSSPrivateKeyParameters.s);
        this.t = gMSSPrivateKeyParameters.t;
        this.u = gMSSPrivateKeyParameters.u;
        this.v = gMSSPrivateKeyParameters.v;
        this.w = gMSSPrivateKeyParameters.w;
        this.y = gMSSPrivateKeyParameters.y;
        this.z = gMSSPrivateKeyParameters.z;
        this.A = gMSSPrivateKeyParameters.A;
        this.B = gMSSPrivateKeyParameters.B;
        this.C = gMSSPrivateKeyParameters.C;
        this.D = gMSSPrivateKeyParameters.D;
        this.E = gMSSPrivateKeyParameters.E;
        this.F = gMSSPrivateKeyParameters.F;
    }

    public GMSSPrivateKeyParameters(int[] iArr, byte[][] bArr, byte[][] bArr2, byte[][][] bArr3, byte[][][] bArr4, byte[][][] bArr5, Treehash[][] treehashArr, Treehash[][] treehashArr2, Vector[] vectorArr, Vector[] vectorArr2, Vector[][] vectorArr3, Vector[][] vectorArr4, GMSSLeaf[] gMSSLeafArr, GMSSLeaf[] gMSSLeafArr2, GMSSLeaf[] gMSSLeafArr3, int[] iArr2, byte[][] bArr6, GMSSRootCalc[] gMSSRootCalcArr, byte[][] bArr7, GMSSRootSig[] gMSSRootSigArr, GMSSParameters gMSSParameters, GMSSDigestProvider gMSSDigestProvider) {
        int[] iArr3 = iArr;
        byte[][] bArr8 = bArr;
        byte[][][] bArr9 = bArr5;
        Vector[] vectorArr5 = vectorArr;
        Vector[] vectorArr6 = vectorArr2;
        GMSSLeaf[] gMSSLeafArr4 = gMSSLeafArr;
        GMSSLeaf[] gMSSLeafArr5 = gMSSLeafArr2;
        GMSSLeaf[] gMSSLeafArr6 = gMSSLeafArr3;
        int[] iArr4 = iArr2;
        byte[][] bArr10 = bArr6;
        GMSSRootCalc[] gMSSRootCalcArr2 = gMSSRootCalcArr;
        GMSSRootSig[] gMSSRootSigArr2 = gMSSRootSigArr;
        GMSSParameters gMSSParameters2 = gMSSParameters;
        super(true, gMSSParameters2);
        this.x = false;
        this.C = gMSSDigestProvider.get();
        this.D = this.C.getDigestSize();
        this.r = gMSSParameters2;
        this.z = gMSSParameters.getWinternitzParameter();
        this.A = gMSSParameters.getK();
        this.y = gMSSParameters.getHeightOfTrees();
        this.B = this.r.getNumOfLayers();
        if (iArr3 == null) {
            this.b = new int[this.B];
            for (int i2 = 0; i2 < this.B; i2++) {
                this.b[i2] = 0;
            }
        } else {
            this.b = iArr3;
        }
        this.c = bArr8;
        this.d = bArr2;
        this.e = bArr3;
        this.f = bArr4;
        if (bArr9 == null) {
            this.m = new byte[this.B][][];
            for (int i3 = 0; i3 < this.B; i3++) {
                this.m[i3] = (byte[][]) Array.newInstance(byte.class, new int[]{(int) Math.floor((double) (this.y[i3] / 2)), this.D});
            }
        } else {
            this.m = bArr9;
        }
        if (vectorArr5 == null) {
            this.i = new Vector[this.B];
            for (int i4 = 0; i4 < this.B; i4++) {
                this.i[i4] = new Vector();
            }
        } else {
            this.i = vectorArr5;
        }
        if (vectorArr6 == null) {
            this.j = new Vector[(this.B - 1)];
            int i5 = 0;
            for (int i6 = 1; i5 < this.B - i6; i6 = 1) {
                this.j[i5] = new Vector();
                i5++;
            }
        } else {
            this.j = vectorArr6;
        }
        this.g = treehashArr;
        this.h = treehashArr2;
        this.k = vectorArr3;
        this.l = vectorArr4;
        this.s = bArr10;
        this.w = gMSSDigestProvider;
        if (gMSSRootCalcArr2 == null) {
            this.t = new GMSSRootCalc[(this.B - 1)];
            int i7 = 0;
            for (int i8 = 1; i7 < this.B - i8; i8 = 1) {
                int i9 = i7 + 1;
                this.t[i7] = new GMSSRootCalc(this.y[i9], this.A[i9], this.w);
                i7 = i9;
            }
        } else {
            this.t = gMSSRootCalcArr2;
        }
        this.u = bArr7;
        this.F = new int[this.B];
        for (int i10 = 0; i10 < this.B; i10++) {
            this.F[i10] = 1 << this.y[i10];
        }
        this.E = new GMSSRandom(this.C);
        if (this.B <= 1) {
            this.n = new GMSSLeaf[0];
        } else if (gMSSLeafArr4 == null) {
            this.n = new GMSSLeaf[(this.B - 2)];
            int i11 = 0;
            while (i11 < this.B - 2) {
                int i12 = i11 + 1;
                this.n[i11] = new GMSSLeaf(gMSSDigestProvider.get(), this.z[i12], this.F[i11 + 2], this.d[i11]);
                i11 = i12;
            }
        } else {
            this.n = gMSSLeafArr4;
        }
        if (gMSSLeafArr5 == null) {
            this.o = new GMSSLeaf[(this.B - 1)];
            int i13 = 0;
            for (int i14 = 1; i13 < this.B - i14; i14 = 1) {
                int i15 = i13 + 1;
                this.o[i13] = new GMSSLeaf(gMSSDigestProvider.get(), this.z[i13], this.F[i15], this.c[i13]);
                i13 = i15;
            }
        } else {
            this.o = gMSSLeafArr5;
        }
        if (gMSSLeafArr6 == null) {
            this.p = new GMSSLeaf[(this.B - 1)];
            int i16 = 0;
            for (int i17 = 1; i16 < this.B - i17; i17 = 1) {
                int i18 = i16 + 1;
                this.p[i16] = new GMSSLeaf(gMSSDigestProvider.get(), this.z[i16], this.F[i18]);
                i16 = i18;
            }
        } else {
            this.p = gMSSLeafArr6;
        }
        if (iArr4 == null) {
            this.q = new int[(this.B - 1)];
            int i19 = 0;
            for (int i20 = 1; i19 < this.B - i20; i20 = 1) {
                this.q[i19] = -1;
                i19++;
            }
        } else {
            this.q = iArr4;
        }
        byte[] bArr11 = new byte[this.D];
        byte[] bArr12 = new byte[this.D];
        if (gMSSRootSigArr2 == null) {
            this.v = new GMSSRootSig[(this.B - 1)];
            int i21 = 0;
            while (i21 < this.B - 1) {
                System.arraycopy(bArr8[i21], 0, bArr11, 0, this.D);
                this.E.nextSeed(bArr11);
                byte[] nextSeed = this.E.nextSeed(bArr11);
                int i22 = i21 + 1;
                this.v[i21] = new GMSSRootSig(gMSSDigestProvider.get(), this.z[i21], this.y[i22]);
                this.v[i21].initSign(nextSeed, bArr10[i21]);
                i21 = i22;
            }
            return;
        }
        this.v = gMSSRootSigArr2;
    }

    public GMSSPrivateKeyParameters(byte[][] bArr, byte[][] bArr2, byte[][][] bArr3, byte[][][] bArr4, Treehash[][] treehashArr, Treehash[][] treehashArr2, Vector[] vectorArr, Vector[] vectorArr2, Vector[][] vectorArr3, Vector[][] vectorArr4, byte[][] bArr5, byte[][] bArr6, GMSSParameters gMSSParameters, GMSSDigestProvider gMSSDigestProvider) {
        this(null, bArr, bArr2, bArr3, bArr4, null, treehashArr, treehashArr2, vectorArr, vectorArr2, vectorArr3, vectorArr4, null, null, null, null, bArr5, null, bArr6, null, gMSSParameters, gMSSDigestProvider);
    }

    private void a(int i2) {
        if (i2 == this.B - 1) {
            int[] iArr = this.b;
            iArr[i2] = iArr[i2] + 1;
        }
        if (this.b[i2] != this.F[i2]) {
            c(i2);
        } else if (this.B != 1) {
            b(i2);
            this.b[i2] = 0;
        }
    }

    private void b(int i2) {
        if (i2 > 0) {
            int[] iArr = this.b;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
            int i4 = i2;
            boolean z2 = true;
            do {
                i4--;
                if (this.b[i4] < this.F[i4]) {
                    z2 = false;
                }
                if (!z2) {
                    break;
                }
            } while (i4 > 0);
            if (!z2) {
                this.E.nextSeed(this.c[i2]);
                this.v[i3].updateSign();
                if (i2 > 1) {
                    int i5 = i3 - 1;
                    this.n[i5] = this.n[i5].a();
                }
                this.o[i3] = this.o[i3].a();
                if (this.q[i3] >= 0) {
                    this.p[i3] = this.p[i3].a();
                    try {
                        this.g[i3][this.q[i3]].update(this.E, this.p[i3].getLeaf());
                        this.g[i3][this.q[i3]].wasFinished();
                    } catch (Exception e2) {
                        System.out.println(e2);
                    }
                }
                g(i2);
                this.u[i3] = this.v[i3].getSig();
                for (int i6 = 0; i6 < this.y[i2] - this.A[i2]; i6++) {
                    this.g[i2][i6] = this.h[i3][i6];
                    this.h[i3][i6] = this.t[i3].getTreehash()[i6];
                }
                for (int i7 = 0; i7 < this.y[i2]; i7++) {
                    System.arraycopy(this.f[i3][i7], 0, this.e[i2][i7], 0, this.D);
                    System.arraycopy(this.t[i3].getAuthPath()[i7], 0, this.f[i3][i7], 0, this.D);
                }
                for (int i8 = 0; i8 < this.A[i2] - 1; i8++) {
                    this.k[i2][i8] = this.l[i3][i8];
                    this.l[i3][i8] = this.t[i3].getRetain()[i8];
                }
                this.i[i2] = this.j[i3];
                this.j[i3] = this.t[i3].getStack();
                this.s[i3] = this.t[i3].getRoot();
                byte[] bArr = new byte[this.D];
                byte[] bArr2 = new byte[this.D];
                System.arraycopy(this.c[i3], 0, bArr2, 0, this.D);
                this.E.nextSeed(bArr2);
                this.E.nextSeed(bArr2);
                this.v[i3].initSign(this.E.nextSeed(bArr2), this.s[i3]);
                a(i3);
            }
        }
    }

    private void c(int i2) {
        e(i2);
        if (i2 > 0) {
            if (i2 > 1) {
                int i3 = (i2 - 1) - 1;
                this.n[i3] = this.n[i3].a();
            }
            int i4 = i2 - 1;
            this.o[i4] = this.o[i4].a();
            int floor = (int) Math.floor(((double) (getNumLeafs(i2) * 2)) / ((double) (this.y[i4] - this.A[i4])));
            if (this.b[i2] % floor == 1) {
                if (this.b[i2] > 1 && this.q[i4] >= 0) {
                    try {
                        this.g[i4][this.q[i4]].update(this.E, this.p[i4].getLeaf());
                        this.g[i4][this.q[i4]].wasFinished();
                    } catch (Exception e2) {
                        System.out.println(e2);
                    }
                }
                this.q[i4] = d(i4);
                if (this.q[i4] >= 0) {
                    this.p[i4] = new GMSSLeaf(this.w.get(), this.z[i4], floor, this.g[i4][this.q[i4]].getSeedActive());
                    this.p[i4] = this.p[i4].a();
                }
            } else if (this.q[i4] >= 0) {
                this.p[i4] = this.p[i4].a();
            }
            this.v[i4].updateSign();
            if (this.b[i2] == 1) {
                this.t[i4].initialize(new Vector());
            }
            g(i2);
        }
    }

    private int d(int i2) {
        int i3 = -1;
        for (int i4 = 0; i4 < this.y[i2] - this.A[i2]; i4++) {
            if (this.g[i2][i4].wasInitialized() && !this.g[i2][i4].wasFinished() && (i3 == -1 || this.g[i2][i4].getLowestNodeHeight() < this.g[i2][i3].getLowestNodeHeight())) {
                i3 = i4;
            }
        }
        return i3;
    }

    private void e(int i2) {
        int i3;
        byte[] bArr;
        int i4 = this.b[i2];
        int i5 = this.y[i2];
        int i6 = this.A[i2];
        int i7 = 0;
        while (true) {
            i3 = i5 - i6;
            if (i7 >= i3) {
                break;
            }
            this.g[i2][i7].updateNextSeed(this.E);
            i7++;
        }
        int f2 = f(i4);
        byte[] bArr2 = new byte[this.D];
        byte[] nextSeed = this.E.nextSeed(this.c[i2]);
        int i8 = (i4 >>> (f2 + 1)) & 1;
        byte[] bArr3 = new byte[this.D];
        int i9 = i5 - 1;
        if (f2 < i9 && i8 == 0) {
            System.arraycopy(this.e[i2][f2], 0, bArr3, 0, this.D);
        }
        byte[] bArr4 = new byte[this.D];
        if (f2 == 0) {
            if (i2 == this.B - 1) {
                bArr = new WinternitzOTSignature(nextSeed, this.w.get(), this.z[i2]).getPublicKey();
            } else {
                byte[] bArr5 = new byte[this.D];
                System.arraycopy(this.c[i2], 0, bArr5, 0, this.D);
                this.E.nextSeed(bArr5);
                byte[] leaf = this.o[i2].getLeaf();
                this.o[i2].a(bArr5);
                bArr = leaf;
            }
            System.arraycopy(bArr, 0, this.e[i2][0], 0, this.D);
        } else {
            byte[] bArr6 = new byte[(this.D << 1)];
            int i10 = f2 - 1;
            System.arraycopy(this.e[i2][i10], 0, bArr6, 0, this.D);
            System.arraycopy(this.m[i2][(int) Math.floor((double) (i10 / 2))], 0, bArr6, this.D, this.D);
            this.C.update(bArr6, 0, bArr6.length);
            this.e[i2][f2] = new byte[this.C.getDigestSize()];
            this.C.doFinal(this.e[i2][f2], 0);
            for (int i11 = 0; i11 < f2; i11++) {
                if (i11 < i3) {
                    if (this.g[i2][i11].wasFinished()) {
                        System.arraycopy(this.g[i2][i11].getFirstNode(), 0, this.e[i2][i11], 0, this.D);
                        this.g[i2][i11].destroy();
                    } else {
                        PrintStream printStream = System.err;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Treehash (");
                        sb.append(i2);
                        sb.append(",");
                        sb.append(i11);
                        sb.append(") not finished when needed in AuthPathComputation");
                        printStream.println(sb.toString());
                    }
                }
                if (i11 < i9 && i11 >= i3) {
                    int i12 = i11 - i3;
                    if (this.k[i2][i12].size() > 0) {
                        System.arraycopy(this.k[i2][i12].lastElement(), 0, this.e[i2][i11], 0, this.D);
                        this.k[i2][i12].removeElementAt(this.k[i2][i12].size() - 1);
                    }
                }
                if (i11 < i3 && ((1 << i11) * 3) + i4 < this.F[i2]) {
                    this.g[i2][i11].initialize();
                }
            }
        }
        if (f2 < i9 && i8 == 0) {
            System.arraycopy(bArr3, 0, this.m[i2][(int) Math.floor((double) (f2 / 2))], 0, this.D);
        }
        if (i2 == this.B - 1) {
            for (int i13 = 1; i13 <= i3 / 2; i13++) {
                int d2 = d(i2);
                if (d2 >= 0) {
                    try {
                        byte[] bArr7 = new byte[this.D];
                        System.arraycopy(this.g[i2][d2].getSeedActive(), 0, bArr7, 0, this.D);
                        this.g[i2][d2].update(this.E, new WinternitzOTSignature(this.E.nextSeed(bArr7), this.w.get(), this.z[i2]).getPublicKey());
                    } catch (Exception e2) {
                        System.out.println(e2);
                    }
                }
            }
            return;
        }
        this.q[i2] = d(i2);
    }

    private int f(int i2) {
        if (i2 == 0) {
            return -1;
        }
        int i3 = 1;
        int i4 = 0;
        while (i2 % i3 == 0) {
            i3 *= 2;
            i4++;
        }
        return i4 - 1;
    }

    private void g(int i2) {
        byte[] bArr = new byte[this.D];
        int i3 = i2 - 1;
        byte[] nextSeed = this.E.nextSeed(this.d[i3]);
        if (i2 == this.B - 1) {
            this.t[i3].update(this.d[i3], new WinternitzOTSignature(nextSeed, this.w.get(), this.z[i2]).getPublicKey());
            return;
        }
        this.t[i3].update(this.d[i3], this.n[i3].getLeaf());
        this.n[i3].a(this.d[i3]);
    }

    public byte[][][] getCurrentAuthPaths() {
        return Arrays.clone(this.e);
    }

    public byte[][] getCurrentSeeds() {
        return Arrays.clone(this.c);
    }

    public int getIndex(int i2) {
        return this.b[i2];
    }

    public int[] getIndex() {
        return this.b;
    }

    public GMSSDigestProvider getName() {
        return this.w;
    }

    public int getNumLeafs(int i2) {
        return this.F[i2];
    }

    public byte[] getSubtreeRootSig(int i2) {
        return this.u[i2];
    }

    public boolean isUsed() {
        return this.x;
    }

    public void markUsed() {
        this.x = true;
    }

    public GMSSPrivateKeyParameters nextKey() {
        GMSSPrivateKeyParameters gMSSPrivateKeyParameters = new GMSSPrivateKeyParameters(this);
        gMSSPrivateKeyParameters.a(this.r.getNumOfLayers() - 1);
        return gMSSPrivateKeyParameters;
    }
}
