package org.bouncycastle.pqc.crypto.gmss;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Vector;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.encoders.Hex;

public class Treehash {
    private int a;
    private Vector b;
    private Vector c;
    private byte[] d;
    private byte[] e;
    private byte[] f;
    private int g;
    private int h;
    private boolean i;
    private boolean j;
    private boolean k;
    private Digest l;

    public Treehash(Vector vector, int i2, Digest digest) {
        this.b = vector;
        this.a = i2;
        this.d = null;
        this.i = false;
        this.j = false;
        this.k = false;
        this.l = digest;
        this.f = new byte[this.l.getDigestSize()];
        this.e = new byte[this.l.getDigestSize()];
    }

    public Treehash(Digest digest, byte[][] bArr, int[] iArr) {
        this.l = digest;
        this.a = iArr[0];
        this.g = iArr[1];
        this.h = iArr[2];
        if (iArr[3] == 1) {
            this.j = true;
        } else {
            this.j = false;
        }
        if (iArr[4] == 1) {
            this.i = true;
        } else {
            this.i = false;
        }
        if (iArr[5] == 1) {
            this.k = true;
        } else {
            this.k = false;
        }
        this.c = new Vector();
        for (int i2 = 0; i2 < this.g; i2++) {
            this.c.addElement(Integers.valueOf(iArr[i2 + 6]));
        }
        this.d = bArr[0];
        this.e = bArr[1];
        this.f = bArr[2];
        this.b = new Vector();
        for (int i3 = 0; i3 < this.g; i3++) {
            this.b.addElement(bArr[i3 + 3]);
        }
    }

    public void destroy() {
        this.i = false;
        this.j = false;
        this.d = null;
        this.g = 0;
        this.h = -1;
    }

    public byte[] getFirstNode() {
        return this.d;
    }

    public int getFirstNodeHeight() {
        return this.d == null ? this.a : this.h;
    }

    public int getLowestNodeHeight() {
        return this.d == null ? this.a : this.g == 0 ? this.h : Math.min(this.h, ((Integer) this.c.lastElement()).intValue());
    }

    public byte[] getSeedActive() {
        return this.e;
    }

    public byte[][] getStatByte() {
        byte[][] bArr = (byte[][]) Array.newInstance(byte.class, new int[]{this.g + 3, this.l.getDigestSize()});
        bArr[0] = this.d;
        bArr[1] = this.e;
        bArr[2] = this.f;
        for (int i2 = 0; i2 < this.g; i2++) {
            bArr[i2 + 3] = (byte[]) this.b.elementAt(i2);
        }
        return bArr;
    }

    public int[] getStatInt() {
        int[] iArr = new int[(this.g + 6)];
        iArr[0] = this.a;
        iArr[1] = this.g;
        iArr[2] = this.h;
        if (this.j) {
            iArr[3] = 1;
        } else {
            iArr[3] = 0;
        }
        if (this.i) {
            iArr[4] = 1;
        } else {
            iArr[4] = 0;
        }
        if (this.k) {
            iArr[5] = 1;
        } else {
            iArr[5] = 0;
        }
        for (int i2 = 0; i2 < this.g; i2++) {
            iArr[i2 + 6] = ((Integer) this.c.elementAt(i2)).intValue();
        }
        return iArr;
    }

    public Vector getTailStack() {
        return this.b;
    }

    public void initialize() {
        if (!this.k) {
            PrintStream printStream = System.err;
            StringBuilder sb = new StringBuilder();
            sb.append("Seed ");
            sb.append(this.a);
            sb.append(" not initialized");
            printStream.println(sb.toString());
            return;
        }
        this.c = new Vector();
        this.g = 0;
        this.d = null;
        this.h = -1;
        this.i = true;
        System.arraycopy(this.f, 0, this.e, 0, this.l.getDigestSize());
    }

    public void initializeSeed(byte[] bArr) {
        System.arraycopy(bArr, 0, this.f, 0, this.l.getDigestSize());
        this.k = true;
    }

    public void setFirstNode(byte[] bArr) {
        if (!this.i) {
            initialize();
        }
        this.d = bArr;
        this.h = this.a;
        this.j = true;
    }

    public String toString() {
        StringBuilder sb;
        String str;
        String str2 = "Treehash    : ";
        for (int i2 = 0; i2 < this.g + 6; i2++) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(getStatInt()[i2]);
            sb2.append(UtilsCuentas.SEPARAOR2);
            str2 = sb2.toString();
        }
        for (int i3 = 0; i3 < this.g + 3; i3++) {
            if (getStatByte()[i3] != null) {
                sb = new StringBuilder();
                sb.append(str2);
                sb.append(new String(Hex.encode(getStatByte()[i3])));
                str = UtilsCuentas.SEPARAOR2;
            } else {
                sb = new StringBuilder();
                sb.append(str2);
                str = "null ";
            }
            sb.append(str);
            str2 = sb.toString();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str2);
        sb3.append("  ");
        sb3.append(this.l.getDigestSize());
        return sb3.toString();
    }

    public void update(GMSSRandom gMSSRandom, byte[] bArr) {
        PrintStream printStream;
        String str;
        if (this.j) {
            printStream = System.err;
            str = "No more update possible for treehash instance!";
        } else if (!this.i) {
            printStream = System.err;
            str = "Treehash instance not initialized before update";
        } else {
            byte[] bArr2 = new byte[this.l.getDigestSize()];
            gMSSRandom.nextSeed(this.e);
            if (this.d == null) {
                this.d = bArr;
                this.h = 0;
            } else {
                int i2 = 0;
                while (this.g > 0 && i2 == ((Integer) this.c.lastElement()).intValue()) {
                    byte[] bArr3 = new byte[(this.l.getDigestSize() << 1)];
                    System.arraycopy(this.b.lastElement(), 0, bArr3, 0, this.l.getDigestSize());
                    this.b.removeElementAt(this.b.size() - 1);
                    this.c.removeElementAt(this.c.size() - 1);
                    System.arraycopy(bArr, 0, bArr3, this.l.getDigestSize(), this.l.getDigestSize());
                    this.l.update(bArr3, 0, bArr3.length);
                    bArr = new byte[this.l.getDigestSize()];
                    this.l.doFinal(bArr, 0);
                    i2++;
                    this.g--;
                }
                this.b.addElement(bArr);
                this.c.addElement(Integers.valueOf(i2));
                this.g++;
                if (((Integer) this.c.lastElement()).intValue() == this.h) {
                    byte[] bArr4 = new byte[(this.l.getDigestSize() << 1)];
                    System.arraycopy(this.d, 0, bArr4, 0, this.l.getDigestSize());
                    System.arraycopy(this.b.lastElement(), 0, bArr4, this.l.getDigestSize(), this.l.getDigestSize());
                    this.b.removeElementAt(this.b.size() - 1);
                    this.c.removeElementAt(this.c.size() - 1);
                    this.l.update(bArr4, 0, bArr4.length);
                    this.d = new byte[this.l.getDigestSize()];
                    this.l.doFinal(this.d, 0);
                    this.h++;
                    this.g = 0;
                }
            }
            if (this.h == this.a) {
                this.j = true;
            }
            return;
        }
        printStream.println(str);
    }

    public void updateNextSeed(GMSSRandom gMSSRandom) {
        gMSSRandom.nextSeed(this.f);
    }

    public boolean wasFinished() {
        return this.j;
    }

    public boolean wasInitialized() {
        return this.i;
    }
}
