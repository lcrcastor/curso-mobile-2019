package org.bouncycastle.pqc.crypto.gmss;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.Vector;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.bouncycastle.pqc.crypto.gmss.util.WinternitzOTSVerify;
import org.bouncycastle.pqc.crypto.gmss.util.WinternitzOTSignature;

public class GMSSKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    public static final String OID = "1.3.6.1.4.1.8301.3.1.3.3";
    private GMSSRandom a;
    private Digest b;
    private byte[][] c;
    private byte[][] d;
    private byte[][] e;
    private GMSSDigestProvider f;
    private int g;
    private int h;
    private boolean i = false;
    private GMSSParameters j;
    private int[] k;
    private int[] l;
    private int[] m;
    private GMSSKeyGenerationParameters n;

    public GMSSKeyPairGenerator(GMSSDigestProvider gMSSDigestProvider) {
        this.f = gMSSDigestProvider;
        this.b = gMSSDigestProvider.get();
        this.g = this.b.getDigestSize();
        this.a = new GMSSRandom(this.b);
    }

    private AsymmetricCipherKeyPair a() {
        byte[] bArr;
        Vector vector;
        byte[] bArr2;
        if (!this.i) {
            b();
        }
        byte[][][] bArr3 = new byte[this.h][][];
        byte[][][] bArr4 = new byte[(this.h - 1)][][];
        Treehash[][] treehashArr = new Treehash[this.h][];
        Treehash[][] treehashArr2 = new Treehash[(this.h - 1)][];
        Vector[] vectorArr = new Vector[this.h];
        Vector[] vectorArr2 = new Vector[(this.h - 1)];
        Vector[][] vectorArr3 = new Vector[this.h][];
        Vector[][] vectorArr4 = new Vector[(this.h - 1)][];
        for (int i2 = 0; i2 < this.h; i2++) {
            bArr3[i2] = (byte[][]) Array.newInstance(byte.class, new int[]{this.k[i2], this.g});
            treehashArr[i2] = new Treehash[(this.k[i2] - this.m[i2])];
            if (i2 > 0) {
                int i3 = i2 - 1;
                bArr4[i3] = (byte[][]) Array.newInstance(byte.class, new int[]{this.k[i2], this.g});
                treehashArr2[i3] = new Treehash[(this.k[i2] - this.m[i2])];
            }
            vectorArr[i2] = new Vector();
            if (i2 > 0) {
                vectorArr2[i2 - 1] = new Vector();
            }
        }
        byte[][] bArr5 = (byte[][]) Array.newInstance(byte.class, new int[]{this.h, this.g});
        byte[][] bArr6 = (byte[][]) Array.newInstance(byte.class, new int[]{this.h - 1, this.g});
        byte[][] bArr7 = (byte[][]) Array.newInstance(byte.class, new int[]{this.h, this.g});
        int i4 = 0;
        while (i4 < this.h) {
            byte[][] bArr8 = bArr6;
            Treehash[][] treehashArr3 = treehashArr2;
            System.arraycopy(this.c[i4], 0, bArr7[i4], 0, this.g);
            i4++;
            bArr6 = bArr8;
            treehashArr2 = treehashArr3;
        }
        Treehash[][] treehashArr4 = treehashArr2;
        byte[][] bArr9 = bArr6;
        this.e = (byte[][]) Array.newInstance(byte.class, new int[]{this.h - 1, this.g});
        int i5 = this.h - 1;
        while (i5 >= 0) {
            GMSSRootCalc gMSSRootCalc = new GMSSRootCalc(this.k[i5], this.m[i5], this.f);
            try {
                if (i5 == this.h - 1) {
                    bArr = null;
                    vector = vectorArr[i5];
                    bArr2 = bArr7[i5];
                } else {
                    bArr = bArr5[i5 + 1];
                    vector = vectorArr[i5];
                    bArr2 = bArr7[i5];
                }
                gMSSRootCalc = a(bArr, vector, bArr2, i5);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            int i6 = 0;
            while (i6 < this.k[i5]) {
                Vector[] vectorArr5 = vectorArr;
                byte[][][] bArr10 = bArr3;
                System.arraycopy(gMSSRootCalc.getAuthPath()[i6], 0, bArr3[i5][i6], 0, this.g);
                i6++;
                vectorArr = vectorArr5;
                bArr3 = bArr10;
            }
            byte[][][] bArr11 = bArr3;
            Vector[] vectorArr6 = vectorArr;
            vectorArr3[i5] = gMSSRootCalc.getRetain();
            treehashArr[i5] = gMSSRootCalc.getTreehash();
            System.arraycopy(gMSSRootCalc.getRoot(), 0, bArr5[i5], 0, this.g);
            i5--;
            vectorArr = vectorArr6;
            bArr3 = bArr11;
        }
        byte[][][] bArr12 = bArr3;
        Vector[] vectorArr7 = vectorArr;
        int i7 = this.h - 2;
        while (i7 >= 0) {
            int i8 = i7 + 1;
            GMSSRootCalc a2 = a(vectorArr2[i7], bArr7[i8], i8);
            int i9 = 0;
            while (i9 < this.k[i8]) {
                Vector[][] vectorArr8 = vectorArr3;
                System.arraycopy(a2.getAuthPath()[i9], 0, bArr4[i7][i9], 0, this.g);
                i9++;
                vectorArr3 = vectorArr8;
            }
            Vector[][] vectorArr9 = vectorArr3;
            vectorArr4[i7] = a2.getRetain();
            treehashArr4[i7] = a2.getTreehash();
            System.arraycopy(a2.getRoot(), 0, bArr9[i7], 0, this.g);
            System.arraycopy(bArr7[i8], 0, this.d[i7], 0, this.g);
            i7--;
            vectorArr3 = vectorArr9;
        }
        Vector[][] vectorArr10 = vectorArr3;
        GMSSPublicKeyParameters gMSSPublicKeyParameters = new GMSSPublicKeyParameters(bArr5[0], this.j);
        byte[][] bArr13 = this.c;
        byte[][] bArr14 = this.d;
        byte[][] bArr15 = this.e;
        GMSSParameters gMSSParameters = this.j;
        GMSSDigestProvider gMSSDigestProvider = this.f;
        byte[][] bArr16 = bArr15;
        GMSSPrivateKeyParameters gMSSPrivateKeyParameters = r3;
        GMSSPrivateKeyParameters gMSSPrivateKeyParameters2 = new GMSSPrivateKeyParameters(bArr13, bArr14, bArr12, bArr4, treehashArr, treehashArr4, vectorArr7, vectorArr2, vectorArr10, vectorArr4, bArr9, bArr16, gMSSParameters, gMSSDigestProvider);
        return new AsymmetricCipherKeyPair((AsymmetricKeyParameter) gMSSPublicKeyParameters, (AsymmetricKeyParameter) gMSSPrivateKeyParameters);
    }

    private GMSSRootCalc a(Vector vector, byte[] bArr, int i2) {
        byte[] bArr2 = new byte[this.h];
        GMSSRootCalc gMSSRootCalc = new GMSSRootCalc(this.k[i2], this.m[i2], this.f);
        gMSSRootCalc.initialize(vector);
        int i3 = 3;
        int i4 = 0;
        for (int i5 = 0; i5 < (1 << this.k[i2]); i5++) {
            if (i5 == i3 && i4 < this.k[i2] - this.m[i2]) {
                gMSSRootCalc.initializeTreehashSeed(bArr, i4);
                i3 *= 2;
                i4++;
            }
            gMSSRootCalc.update(new WinternitzOTSignature(this.a.nextSeed(bArr), this.f.get(), this.l[i2]).getPublicKey());
        }
        if (gMSSRootCalc.wasFinished()) {
            return gMSSRootCalc;
        }
        System.err.println("N�chster Baum noch nicht fertig konstruiert!!!");
        return null;
    }

    private GMSSRootCalc a(byte[] bArr, Vector vector, byte[] bArr2, int i2) {
        byte[] bArr3;
        byte[] bArr4 = new byte[this.g];
        byte[] bArr5 = new byte[this.g];
        byte[] nextSeed = this.a.nextSeed(bArr2);
        GMSSRootCalc gMSSRootCalc = new GMSSRootCalc(this.k[i2], this.m[i2], this.f);
        gMSSRootCalc.initialize(vector);
        if (i2 == this.h - 1) {
            bArr3 = new WinternitzOTSignature(nextSeed, this.f.get(), this.l[i2]).getPublicKey();
        } else {
            this.e[i2] = new WinternitzOTSignature(nextSeed, this.f.get(), this.l[i2]).getSignature(bArr);
            bArr3 = new WinternitzOTSVerify(this.f.get(), this.l[i2]).Verify(bArr, this.e[i2]);
        }
        gMSSRootCalc.update(bArr3);
        int i3 = 3;
        int i4 = 0;
        for (int i5 = 1; i5 < (1 << this.k[i2]); i5++) {
            if (i5 == i3 && i4 < this.k[i2] - this.m[i2]) {
                gMSSRootCalc.initializeTreehashSeed(bArr2, i4);
                i3 *= 2;
                i4++;
            }
            gMSSRootCalc.update(new WinternitzOTSignature(this.a.nextSeed(bArr2), this.f.get(), this.l[i2]).getPublicKey());
        }
        if (gMSSRootCalc.wasFinished()) {
            return gMSSRootCalc;
        }
        System.err.println("Baum noch nicht fertig konstruiert!!!");
        return null;
    }

    private void b() {
        int[] iArr = {10, 10, 10, 10};
        initialize(new GMSSKeyGenerationParameters(new SecureRandom(), new GMSSParameters(iArr.length, iArr, new int[]{3, 3, 3, 3}, new int[]{2, 2, 2, 2})));
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        return a();
    }

    public void init(KeyGenerationParameters keyGenerationParameters) {
        initialize(keyGenerationParameters);
    }

    public void initialize(int i2, SecureRandom secureRandom) {
        GMSSKeyGenerationParameters gMSSKeyGenerationParameters;
        if (i2 <= 10) {
            int[] iArr = {10};
            gMSSKeyGenerationParameters = new GMSSKeyGenerationParameters(secureRandom, new GMSSParameters(iArr.length, iArr, new int[]{3}, new int[]{2}));
        } else if (i2 <= 20) {
            int[] iArr2 = {10, 10};
            gMSSKeyGenerationParameters = new GMSSKeyGenerationParameters(secureRandom, new GMSSParameters(iArr2.length, iArr2, new int[]{5, 4}, new int[]{2, 2}));
        } else {
            int[] iArr3 = {10, 10, 10, 10};
            gMSSKeyGenerationParameters = new GMSSKeyGenerationParameters(secureRandom, new GMSSParameters(iArr3.length, iArr3, new int[]{9, 9, 9, 3}, new int[]{2, 2, 2, 2}));
        }
        initialize(gMSSKeyGenerationParameters);
    }

    public void initialize(KeyGenerationParameters keyGenerationParameters) {
        this.n = (GMSSKeyGenerationParameters) keyGenerationParameters;
        this.j = new GMSSParameters(this.n.getParameters().getNumOfLayers(), this.n.getParameters().getHeightOfTrees(), this.n.getParameters().getWinternitzParameter(), this.n.getParameters().getK());
        this.h = this.j.getNumOfLayers();
        this.k = this.j.getHeightOfTrees();
        this.l = this.j.getWinternitzParameter();
        this.m = this.j.getK();
        this.c = (byte[][]) Array.newInstance(byte.class, new int[]{this.h, this.g});
        this.d = (byte[][]) Array.newInstance(byte.class, new int[]{this.h - 1, this.g});
        SecureRandom secureRandom = new SecureRandom();
        for (int i2 = 0; i2 < this.h; i2++) {
            secureRandom.nextBytes(this.c[i2]);
            this.a.nextSeed(this.c[i2]);
        }
        this.i = true;
    }
}
