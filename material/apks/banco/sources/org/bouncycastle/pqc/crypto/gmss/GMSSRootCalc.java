package org.bouncycastle.pqc.crypto.gmss;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Vector;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.encoders.Hex;

public class GMSSRootCalc {
    private int a;
    private int b;
    private Treehash[] c;
    private Vector[] d;
    private byte[] e;
    private byte[][] f;
    private int g;
    private Vector h;
    private Vector i;
    private Digest j;
    private GMSSDigestProvider k;
    private int[] l;
    private boolean m;
    private boolean n;
    private int o;
    private int p;

    public GMSSRootCalc(int i2, int i3, GMSSDigestProvider gMSSDigestProvider) {
        this.a = i2;
        this.k = gMSSDigestProvider;
        this.j = gMSSDigestProvider.get();
        this.b = this.j.getDigestSize();
        this.g = i3;
        this.l = new int[i2];
        this.f = (byte[][]) Array.newInstance(byte.class, new int[]{i2, this.b});
        this.e = new byte[this.b];
        this.d = new Vector[(this.g - 1)];
        for (int i4 = 0; i4 < i3 - 1; i4++) {
            this.d[i4] = new Vector();
        }
    }

    public GMSSRootCalc(Digest digest, byte[][] bArr, int[] iArr, Treehash[] treehashArr, Vector[] vectorArr) {
        this.j = this.k.get();
        this.k = this.k;
        this.a = iArr[0];
        this.b = iArr[1];
        this.g = iArr[2];
        this.o = iArr[3];
        this.p = iArr[4];
        if (iArr[5] == 1) {
            this.n = true;
        } else {
            this.n = false;
        }
        if (iArr[6] == 1) {
            this.m = true;
        } else {
            this.m = false;
        }
        int i2 = iArr[7];
        this.l = new int[this.a];
        for (int i3 = 0; i3 < this.a; i3++) {
            this.l[i3] = iArr[i3 + 8];
        }
        this.i = new Vector();
        for (int i4 = 0; i4 < i2; i4++) {
            this.i.addElement(Integers.valueOf(iArr[this.a + 8 + i4]));
        }
        this.e = bArr[0];
        this.f = (byte[][]) Array.newInstance(byte.class, new int[]{this.a, this.b});
        int i5 = 0;
        while (i5 < this.a) {
            int i6 = i5 + 1;
            this.f[i5] = bArr[i6];
            i5 = i6;
        }
        this.h = new Vector();
        for (int i7 = 0; i7 < i2; i7++) {
            this.h.addElement(bArr[this.a + 1 + i7]);
        }
        this.c = GMSSUtils.a(treehashArr);
        this.d = GMSSUtils.a(vectorArr);
    }

    public byte[][] getAuthPath() {
        return GMSSUtils.a(this.f);
    }

    public Vector[] getRetain() {
        return GMSSUtils.a(this.d);
    }

    public byte[] getRoot() {
        return Arrays.clone(this.e);
    }

    public Vector getStack() {
        Vector vector = new Vector();
        Enumeration elements = this.h.elements();
        while (elements.hasMoreElements()) {
            vector.addElement(elements.nextElement());
        }
        return vector;
    }

    public byte[][] getStatByte() {
        int size = this.h == null ? 0 : this.h.size();
        byte[][] bArr = (byte[][]) Array.newInstance(byte.class, new int[]{this.a + 1 + size, 64});
        bArr[0] = this.e;
        int i2 = 0;
        while (i2 < this.a) {
            int i3 = i2 + 1;
            bArr[i3] = this.f[i2];
            i2 = i3;
        }
        for (int i4 = 0; i4 < size; i4++) {
            bArr[this.a + 1 + i4] = (byte[]) this.h.elementAt(i4);
        }
        return bArr;
    }

    public int[] getStatInt() {
        int size = this.h == null ? 0 : this.h.size();
        int[] iArr = new int[(this.a + 8 + size)];
        iArr[0] = this.a;
        iArr[1] = this.b;
        iArr[2] = this.g;
        iArr[3] = this.o;
        iArr[4] = this.p;
        if (this.n) {
            iArr[5] = 1;
        } else {
            iArr[5] = 0;
        }
        if (this.m) {
            iArr[6] = 1;
        } else {
            iArr[6] = 0;
        }
        iArr[7] = size;
        for (int i2 = 0; i2 < this.a; i2++) {
            iArr[i2 + 8] = this.l[i2];
        }
        for (int i3 = 0; i3 < size; i3++) {
            iArr[this.a + 8 + i3] = ((Integer) this.i.elementAt(i3)).intValue();
        }
        return iArr;
    }

    public Treehash[] getTreehash() {
        return GMSSUtils.a(this.c);
    }

    public void initialize(Vector vector) {
        this.c = new Treehash[(this.a - this.g)];
        for (int i2 = 0; i2 < this.a - this.g; i2++) {
            this.c[i2] = new Treehash(vector, i2, this.k.get());
        }
        this.l = new int[this.a];
        this.f = (byte[][]) Array.newInstance(byte.class, new int[]{this.a, this.b});
        this.e = new byte[this.b];
        this.h = new Vector();
        this.i = new Vector();
        this.m = true;
        this.n = false;
        for (int i3 = 0; i3 < this.a; i3++) {
            this.l[i3] = -1;
        }
        this.d = new Vector[(this.g - 1)];
        for (int i4 = 0; i4 < this.g - 1; i4++) {
            this.d[i4] = new Vector();
        }
        this.o = 3;
        this.p = 0;
    }

    public void initializeTreehashSeed(byte[] bArr, int i2) {
        this.c[i2].initializeSeed(bArr);
    }

    public String toString() {
        String str = "";
        int size = this.h == null ? 0 : this.h.size();
        String str2 = str;
        for (int i2 = 0; i2 < this.a + 8 + size; i2++) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(getStatInt()[i2]);
            sb.append(UtilsCuentas.SEPARAOR2);
            str2 = sb.toString();
        }
        for (int i3 = 0; i3 < this.a + 1 + size; i3++) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(new String(Hex.encode(getStatByte()[i3])));
            sb2.append(UtilsCuentas.SEPARAOR2);
            str2 = sb2.toString();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str2);
        sb3.append("  ");
        sb3.append(this.k.get().getDigestSize());
        return sb3.toString();
    }

    public void update(byte[] bArr) {
        if (this.n) {
            System.out.print("Too much updates for Tree!!");
        } else if (!this.m) {
            System.err.println("GMSSRootCalc not initialized!");
        } else {
            int[] iArr = this.l;
            iArr[0] = iArr[0] + 1;
            if (this.l[0] == 1) {
                System.arraycopy(bArr, 0, this.f[0], 0, this.b);
            } else if (this.l[0] == 3 && this.a > this.g) {
                this.c[0].setFirstNode(bArr);
            }
            if ((this.l[0] - 3) % 2 == 0 && this.l[0] >= 3 && this.a == this.g) {
                this.d[0].insertElementAt(bArr, 0);
            }
            if (this.l[0] == 0) {
                this.h.addElement(bArr);
                this.i.addElement(Integers.valueOf(0));
                return;
            }
            byte[] bArr2 = new byte[this.b];
            byte[] bArr3 = new byte[(this.b << 1)];
            System.arraycopy(bArr, 0, bArr2, 0, this.b);
            int i2 = 0;
            while (this.h.size() > 0 && i2 == ((Integer) this.i.lastElement()).intValue()) {
                System.arraycopy(this.h.lastElement(), 0, bArr3, 0, this.b);
                this.h.removeElementAt(this.h.size() - 1);
                this.i.removeElementAt(this.i.size() - 1);
                System.arraycopy(bArr2, 0, bArr3, this.b, this.b);
                this.j.update(bArr3, 0, bArr3.length);
                bArr2 = new byte[this.j.getDigestSize()];
                this.j.doFinal(bArr2, 0);
                i2++;
                if (i2 < this.a) {
                    int[] iArr2 = this.l;
                    iArr2[i2] = iArr2[i2] + 1;
                    if (this.l[i2] == 1) {
                        System.arraycopy(bArr2, 0, this.f[i2], 0, this.b);
                    }
                    if (i2 >= this.a - this.g) {
                        if (i2 == 0) {
                            System.out.println("M���P");
                        }
                        if ((this.l[i2] - 3) % 2 == 0 && this.l[i2] >= 3) {
                            this.d[i2 - (this.a - this.g)].insertElementAt(bArr2, 0);
                        }
                    } else if (this.l[i2] == 3) {
                        this.c[i2].setFirstNode(bArr2);
                    }
                }
            }
            this.h.addElement(bArr2);
            this.i.addElement(Integers.valueOf(i2));
            if (i2 == this.a) {
                this.n = true;
                this.m = false;
                this.e = (byte[]) this.h.lastElement();
            }
        }
    }

    public void update(byte[] bArr, byte[] bArr2) {
        if (this.p < this.a - this.g && this.o - 2 == this.l[0]) {
            initializeTreehashSeed(bArr, this.p);
            this.p++;
            this.o *= 2;
        }
        update(bArr2);
    }

    public boolean wasFinished() {
        return this.n;
    }

    public boolean wasInitialized() {
        return this.m;
    }
}
