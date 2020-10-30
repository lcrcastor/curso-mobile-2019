package org.bouncycastle.pqc.jcajce.spec;

import java.security.spec.KeySpec;
import java.util.Vector;
import org.bouncycastle.pqc.crypto.gmss.GMSSLeaf;
import org.bouncycastle.pqc.crypto.gmss.GMSSParameters;
import org.bouncycastle.pqc.crypto.gmss.GMSSRootCalc;
import org.bouncycastle.pqc.crypto.gmss.GMSSRootSig;
import org.bouncycastle.pqc.crypto.gmss.Treehash;
import org.bouncycastle.util.Arrays;

public class GMSSPrivateKeySpec implements KeySpec {
    private int[] a;
    private byte[][] b;
    private byte[][] c;
    private byte[][][] d;
    private byte[][][] e;
    private Treehash[][] f;
    private Treehash[][] g;
    private Vector[] h;
    private Vector[] i;
    private Vector[][] j;
    private Vector[][] k;
    private byte[][][] l;
    private GMSSLeaf[] m;
    private GMSSLeaf[] n;
    private GMSSLeaf[] o;
    private int[] p;
    private GMSSParameters q;
    private byte[][] r;
    private GMSSRootCalc[] s;
    private byte[][] t;
    private GMSSRootSig[] u;

    public GMSSPrivateKeySpec(int[] iArr, byte[][] bArr, byte[][] bArr2, byte[][][] bArr3, byte[][][] bArr4, Treehash[][] treehashArr, Treehash[][] treehashArr2, Vector[] vectorArr, Vector[] vectorArr2, Vector[][] vectorArr3, Vector[][] vectorArr4, byte[][][] bArr5, GMSSLeaf[] gMSSLeafArr, GMSSLeaf[] gMSSLeafArr2, GMSSLeaf[] gMSSLeafArr3, int[] iArr2, byte[][] bArr6, GMSSRootCalc[] gMSSRootCalcArr, byte[][] bArr7, GMSSRootSig[] gMSSRootSigArr, GMSSParameters gMSSParameters) {
        this.a = iArr;
        this.b = bArr;
        this.c = bArr2;
        this.d = bArr3;
        this.e = bArr4;
        this.f = treehashArr;
        this.g = treehashArr2;
        this.h = vectorArr;
        this.i = vectorArr2;
        this.j = vectorArr3;
        this.k = vectorArr4;
        this.l = bArr5;
        this.m = gMSSLeafArr;
        this.n = gMSSLeafArr2;
        this.o = gMSSLeafArr3;
        this.p = iArr2;
        this.r = bArr6;
        this.s = gMSSRootCalcArr;
        this.t = bArr7;
        this.u = gMSSRootSigArr;
        this.q = gMSSParameters;
    }

    private static Vector[] a(Vector[] vectorArr) {
        if (vectorArr == null) {
            return null;
        }
        Vector[] vectorArr2 = new Vector[vectorArr.length];
        for (int i2 = 0; i2 != vectorArr.length; i2++) {
            vectorArr2[i2] = new Vector(vectorArr[i2]);
        }
        return vectorArr2;
    }

    private static GMSSLeaf[] a(GMSSLeaf[] gMSSLeafArr) {
        if (gMSSLeafArr == null) {
            return null;
        }
        GMSSLeaf[] gMSSLeafArr2 = new GMSSLeaf[gMSSLeafArr.length];
        System.arraycopy(gMSSLeafArr, 0, gMSSLeafArr2, 0, gMSSLeafArr.length);
        return gMSSLeafArr2;
    }

    private static GMSSRootCalc[] a(GMSSRootCalc[] gMSSRootCalcArr) {
        if (gMSSRootCalcArr == null) {
            return null;
        }
        GMSSRootCalc[] gMSSRootCalcArr2 = new GMSSRootCalc[gMSSRootCalcArr.length];
        System.arraycopy(gMSSRootCalcArr, 0, gMSSRootCalcArr2, 0, gMSSRootCalcArr.length);
        return gMSSRootCalcArr2;
    }

    private static GMSSRootSig[] a(GMSSRootSig[] gMSSRootSigArr) {
        if (gMSSRootSigArr == null) {
            return null;
        }
        GMSSRootSig[] gMSSRootSigArr2 = new GMSSRootSig[gMSSRootSigArr.length];
        System.arraycopy(gMSSRootSigArr, 0, gMSSRootSigArr2, 0, gMSSRootSigArr.length);
        return gMSSRootSigArr2;
    }

    private static Treehash[] a(Treehash[] treehashArr) {
        if (treehashArr == null) {
            return null;
        }
        Treehash[] treehashArr2 = new Treehash[treehashArr.length];
        System.arraycopy(treehashArr, 0, treehashArr2, 0, treehashArr.length);
        return treehashArr2;
    }

    private static byte[][] a(byte[][] bArr) {
        if (bArr == null) {
            return null;
        }
        byte[][] bArr2 = new byte[bArr.length][];
        for (int i2 = 0; i2 != bArr.length; i2++) {
            bArr2[i2] = Arrays.clone(bArr[i2]);
        }
        return bArr2;
    }

    private static Vector[][] a(Vector[][] vectorArr) {
        if (vectorArr == null) {
            return null;
        }
        Vector[][] vectorArr2 = new Vector[vectorArr.length][];
        for (int i2 = 0; i2 != vectorArr.length; i2++) {
            vectorArr2[i2] = a(vectorArr[i2]);
        }
        return vectorArr2;
    }

    private static Treehash[][] a(Treehash[][] treehashArr) {
        if (treehashArr == null) {
            return null;
        }
        Treehash[][] treehashArr2 = new Treehash[treehashArr.length][];
        for (int i2 = 0; i2 != treehashArr.length; i2++) {
            treehashArr2[i2] = a(treehashArr[i2]);
        }
        return treehashArr2;
    }

    private static byte[][][] a(byte[][][] bArr) {
        if (bArr == null) {
            return null;
        }
        byte[][][] bArr2 = new byte[bArr.length][][];
        for (int i2 = 0; i2 != bArr.length; i2++) {
            bArr2[i2] = a(bArr[i2]);
        }
        return bArr2;
    }

    public byte[][][] getCurrentAuthPath() {
        return a(this.d);
    }

    public Vector[][] getCurrentRetain() {
        return a(this.j);
    }

    public byte[][] getCurrentRootSig() {
        return a(this.t);
    }

    public byte[][] getCurrentSeed() {
        return a(this.b);
    }

    public Vector[] getCurrentStack() {
        return a(this.h);
    }

    public Treehash[][] getCurrentTreehash() {
        return a(this.f);
    }

    public GMSSParameters getGmssPS() {
        return this.q;
    }

    public int[] getIndex() {
        return Arrays.clone(this.a);
    }

    public byte[][][] getKeep() {
        return a(this.l);
    }

    public int[] getMinTreehash() {
        return Arrays.clone(this.p);
    }

    public byte[][][] getNextAuthPath() {
        return a(this.e);
    }

    public GMSSLeaf[] getNextNextLeaf() {
        return a(this.m);
    }

    public GMSSRootCalc[] getNextNextRoot() {
        return a(this.s);
    }

    public byte[][] getNextNextSeed() {
        return a(this.c);
    }

    public Vector[][] getNextRetain() {
        return a(this.k);
    }

    public byte[][] getNextRoot() {
        return a(this.r);
    }

    public GMSSRootSig[] getNextRootSig() {
        return a(this.u);
    }

    public Vector[] getNextStack() {
        return a(this.i);
    }

    public Treehash[][] getNextTreehash() {
        return a(this.g);
    }

    public GMSSLeaf[] getUpperLeaf() {
        return a(this.n);
    }

    public GMSSLeaf[] getUpperTreehashLeaf() {
        return a(this.o);
    }
}
