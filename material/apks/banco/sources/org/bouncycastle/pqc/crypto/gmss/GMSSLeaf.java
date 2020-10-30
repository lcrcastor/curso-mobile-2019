package org.bouncycastle.pqc.crypto.gmss;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

public class GMSSLeaf {
    byte[] a;
    private Digest b;
    private int c;
    private int d;
    private GMSSRandom e;
    private byte[] f;
    private byte[] g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private byte[] m;

    GMSSLeaf(Digest digest, int i2, int i3) {
        this.k = i2;
        this.b = digest;
        this.e = new GMSSRandom(this.b);
        this.c = this.b.getDigestSize();
        double d2 = (double) i2;
        int ceil = (int) Math.ceil(((double) (this.c << 3)) / d2);
        this.d = ceil + ((int) Math.ceil(((double) a((ceil << i2) + 1)) / d2));
        int i4 = 1 << i2;
        this.j = i4;
        this.l = (int) Math.ceil(((double) ((((i4 - 1) * this.d) + 1) + this.d)) / ((double) i3));
        this.m = new byte[this.c];
        this.f = new byte[this.c];
        this.a = new byte[this.c];
        this.g = new byte[(this.c * this.d)];
    }

    public GMSSLeaf(Digest digest, int i2, int i3, byte[] bArr) {
        this.k = i2;
        this.b = digest;
        this.e = new GMSSRandom(this.b);
        this.c = this.b.getDigestSize();
        double d2 = (double) i2;
        int ceil = (int) Math.ceil(((double) (this.c << 3)) / d2);
        this.d = ceil + ((int) Math.ceil(((double) a((ceil << i2) + 1)) / d2));
        int i4 = 1 << i2;
        this.j = i4;
        this.l = (int) Math.ceil(((double) ((((i4 - 1) * this.d) + 1) + this.d)) / ((double) i3));
        this.m = new byte[this.c];
        this.f = new byte[this.c];
        this.a = new byte[this.c];
        this.g = new byte[(this.c * this.d)];
        a(bArr);
    }

    public GMSSLeaf(Digest digest, byte[][] bArr, int[] iArr) {
        this.h = iArr[0];
        this.i = iArr[1];
        this.l = iArr[2];
        this.k = iArr[3];
        this.b = digest;
        this.e = new GMSSRandom(this.b);
        this.c = this.b.getDigestSize();
        int ceil = (int) Math.ceil(((double) (this.c << 3)) / ((double) this.k));
        this.d = ceil + ((int) Math.ceil(((double) a((ceil << this.k) + 1)) / ((double) this.k)));
        this.j = 1 << this.k;
        this.a = bArr[0];
        this.m = bArr[1];
        this.g = bArr[2];
        this.f = bArr[3];
    }

    private GMSSLeaf(GMSSLeaf gMSSLeaf) {
        this.b = gMSSLeaf.b;
        this.c = gMSSLeaf.c;
        this.d = gMSSLeaf.d;
        this.e = gMSSLeaf.e;
        this.f = Arrays.clone(gMSSLeaf.f);
        this.g = Arrays.clone(gMSSLeaf.g);
        this.h = gMSSLeaf.h;
        this.i = gMSSLeaf.i;
        this.j = gMSSLeaf.j;
        this.k = gMSSLeaf.k;
        this.l = gMSSLeaf.l;
        this.m = Arrays.clone(gMSSLeaf.m);
        this.a = Arrays.clone(gMSSLeaf.a);
    }

    private int a(int i2) {
        int i3 = 1;
        int i4 = 2;
        while (i4 < i2) {
            i4 <<= 1;
            i3++;
        }
        return i3;
    }

    private void b() {
        byte[] bArr = new byte[this.b.getDigestSize()];
        for (int i2 = 0; i2 < this.l + 10000; i2++) {
            if (this.h == this.d && this.i == this.j - 1) {
                this.b.update(this.g, 0, this.g.length);
                this.f = new byte[this.b.getDigestSize()];
                this.b.doFinal(this.f, 0);
                return;
            }
            if (this.h == 0 || this.i == this.j - 1) {
                this.h++;
                this.i = 0;
                this.a = this.e.nextSeed(this.m);
            } else {
                this.b.update(this.a, 0, this.a.length);
                this.a = bArr;
                this.b.doFinal(this.a, 0);
                this.i++;
                if (this.i == this.j - 1) {
                    System.arraycopy(this.a, 0, this.g, this.c * (this.h - 1), this.c);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unable to updateLeaf in steps: ");
        sb.append(this.l);
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.h);
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(this.i);
        throw new IllegalStateException(sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public GMSSLeaf a() {
        GMSSLeaf gMSSLeaf = new GMSSLeaf(this);
        gMSSLeaf.b();
        return gMSSLeaf;
    }

    /* access modifiers changed from: 0000 */
    public void a(byte[] bArr) {
        this.h = 0;
        this.i = 0;
        byte[] bArr2 = new byte[this.c];
        System.arraycopy(bArr, 0, bArr2, 0, this.m.length);
        this.m = this.e.nextSeed(bArr2);
    }

    public byte[] getLeaf() {
        return Arrays.clone(this.f);
    }

    public byte[][] getStatByte() {
        byte[][] bArr = {new byte[this.c], new byte[this.c], new byte[(this.c * this.d)], new byte[this.c]};
        bArr[0] = this.a;
        bArr[1] = this.m;
        bArr[2] = this.g;
        bArr[3] = this.f;
        return bArr;
    }

    public int[] getStatInt() {
        return new int[]{this.h, this.i, this.l, this.k};
    }

    public String toString() {
        StringBuilder sb;
        String str;
        String str2 = "";
        for (int i2 = 0; i2 < 4; i2++) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(getStatInt()[i2]);
            sb2.append(UtilsCuentas.SEPARAOR2);
            str2 = sb2.toString();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str2);
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(this.c);
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(this.d);
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(this.j);
        sb3.append(UtilsCuentas.SEPARAOR2);
        String sb4 = sb3.toString();
        byte[][] statByte = getStatByte();
        for (int i3 = 0; i3 < 4; i3++) {
            if (statByte[i3] != null) {
                sb = new StringBuilder();
                sb.append(sb4);
                sb.append(new String(Hex.encode(statByte[i3])));
                str = UtilsCuentas.SEPARAOR2;
            } else {
                sb = new StringBuilder();
                sb.append(sb4);
                str = "null ";
            }
            sb.append(str);
            sb4 = sb.toString();
        }
        return sb4;
    }
}
