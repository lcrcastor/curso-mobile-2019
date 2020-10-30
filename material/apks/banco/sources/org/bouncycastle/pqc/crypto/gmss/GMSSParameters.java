package org.bouncycastle.pqc.crypto.gmss;

import org.bouncycastle.util.Arrays;

public class GMSSParameters {
    private int a;
    private int[] b;
    private int[] c;
    private int[] d;

    public GMSSParameters(int i) {
        if (i <= 10) {
            int[] iArr = {10};
            a(iArr.length, iArr, new int[]{3}, new int[]{2});
        } else if (i <= 20) {
            int[] iArr2 = {10, 10};
            a(iArr2.length, iArr2, new int[]{5, 4}, new int[]{2, 2});
        } else {
            int[] iArr3 = {10, 10, 10, 10};
            a(iArr3.length, iArr3, new int[]{9, 9, 9, 3}, new int[]{2, 2, 2, 2});
        }
    }

    public GMSSParameters(int i, int[] iArr, int[] iArr2, int[] iArr3) {
        a(i, iArr, iArr2, iArr3);
    }

    private void a(int i, int[] iArr, int[] iArr2, int[] iArr3) {
        boolean z;
        String str = "";
        this.a = i;
        if (this.a == iArr2.length && this.a == iArr.length && this.a == iArr3.length) {
            z = true;
        } else {
            str = "Unexpected parameterset format";
            z = false;
        }
        String str2 = str;
        boolean z2 = z;
        for (int i2 = 0; i2 < this.a; i2++) {
            if (iArr3[i2] < 2 || (iArr[i2] - iArr3[i2]) % 2 != 0) {
                str2 = "Wrong parameter K (K >= 2 and H-K even required)!";
                z2 = false;
            }
            if (iArr[i2] < 4 || iArr2[i2] < 2) {
                str2 = "Wrong parameter H or w (H > 3 and w > 1 required)!";
                z2 = false;
            }
        }
        if (z2) {
            this.b = Arrays.clone(iArr);
            this.c = Arrays.clone(iArr2);
            this.d = Arrays.clone(iArr3);
            return;
        }
        throw new IllegalArgumentException(str2);
    }

    public int[] getHeightOfTrees() {
        return Arrays.clone(this.b);
    }

    public int[] getK() {
        return Arrays.clone(this.d);
    }

    public int getNumOfLayers() {
        return this.a;
    }

    public int[] getWinternitzParameter() {
        return Arrays.clone(this.c);
    }
}
