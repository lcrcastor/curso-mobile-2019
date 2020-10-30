package org.bouncycastle.crypto.modes.gcm;

import java.util.Vector;
import org.bouncycastle.util.Arrays;

public class Tables1kGCMExponentiator implements GCMExponentiator {
    private Vector a;

    private void a(int i) {
        int size = this.a.size();
        if (size <= i) {
            int[] iArr = (int[]) this.a.elementAt(size - 1);
            do {
                iArr = Arrays.clone(iArr);
                GCMUtil.a(iArr, iArr);
                this.a.addElement(iArr);
                size++;
            } while (size <= i);
        }
    }

    public void exponentiateX(long j, byte[] bArr) {
        int[] a2 = GCMUtil.a();
        int i = 0;
        while (j > 0) {
            if ((j & 1) != 0) {
                a(i);
                GCMUtil.a(a2, (int[]) this.a.elementAt(i));
            }
            i++;
            j >>>= 1;
        }
        GCMUtil.a(a2, bArr);
    }

    public void init(byte[] bArr) {
        int[] a2 = GCMUtil.a(bArr);
        if (this.a == null || !Arrays.areEqual(a2, (int[]) this.a.elementAt(0))) {
            this.a = new Vector(8);
            this.a.addElement(a2);
        }
    }
}
