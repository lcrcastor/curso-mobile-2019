package org.bouncycastle.crypto.generators;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.math.BigInteger;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.MacDerivationFunction;
import org.bouncycastle.crypto.params.KDFCounterParameters;
import org.bouncycastle.crypto.params.KeyParameter;

public class KDFCounterBytesGenerator implements MacDerivationFunction {
    private static final BigInteger a = BigInteger.valueOf(2147483647L);
    private static final BigInteger b = BigInteger.valueOf(2);
    private final Mac c;
    private final int d;
    private byte[] e;
    private byte[] f;
    private int g;
    private byte[] h;
    private int i;
    private byte[] j = new byte[this.d];

    public KDFCounterBytesGenerator(Mac mac) {
        this.c = mac;
        this.d = mac.getMacSize();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001d, code lost:
        r5.h[r5.h.length - 3] = (byte) (r0 >>> 16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0029, code lost:
        r5.h[r5.h.length - 2] = (byte) (r0 >>> 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0035, code lost:
        r5.h[r5.h.length - 1] = (byte) r0;
        r5.c.update(r5.e, 0, r5.e.length);
        r5.c.update(r5.h, 0, r5.h.length);
        r5.c.update(r5.f, 0, r5.f.length);
        r5.c.doFinal(r5.j, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0064, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a() {
        /*
            r5 = this;
            int r0 = r5.i
            int r1 = r5.d
            int r0 = r0 / r1
            int r0 = r0 + 1
            byte[] r1 = r5.h
            int r1 = r1.length
            r2 = 0
            switch(r1) {
                case 1: goto L_0x0035;
                case 2: goto L_0x0029;
                case 3: goto L_0x001d;
                case 4: goto L_0x0016;
                default: goto L_0x000e;
            }
        L_0x000e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Unsupported size of counter i"
            r0.<init>(r1)
            throw r0
        L_0x0016:
            byte[] r1 = r5.h
            int r3 = r0 >>> 24
            byte r3 = (byte) r3
            r1[r2] = r3
        L_0x001d:
            byte[] r1 = r5.h
            byte[] r3 = r5.h
            int r3 = r3.length
            int r3 = r3 + -3
            int r4 = r0 >>> 16
            byte r4 = (byte) r4
            r1[r3] = r4
        L_0x0029:
            byte[] r1 = r5.h
            byte[] r3 = r5.h
            int r3 = r3.length
            int r3 = r3 + -2
            int r4 = r0 >>> 8
            byte r4 = (byte) r4
            r1[r3] = r4
        L_0x0035:
            byte[] r1 = r5.h
            byte[] r3 = r5.h
            int r3 = r3.length
            int r3 = r3 + -1
            byte r0 = (byte) r0
            r1[r3] = r0
            org.bouncycastle.crypto.Mac r0 = r5.c
            byte[] r1 = r5.e
            byte[] r3 = r5.e
            int r3 = r3.length
            r0.update(r1, r2, r3)
            org.bouncycastle.crypto.Mac r0 = r5.c
            byte[] r1 = r5.h
            byte[] r3 = r5.h
            int r3 = r3.length
            r0.update(r1, r2, r3)
            org.bouncycastle.crypto.Mac r0 = r5.c
            byte[] r1 = r5.f
            byte[] r3 = r5.f
            int r3 = r3.length
            r0.update(r1, r2, r3)
            org.bouncycastle.crypto.Mac r0 = r5.c
            byte[] r1 = r5.j
            r0.doFinal(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.generators.KDFCounterBytesGenerator.a():void");
    }

    public int generateBytes(byte[] bArr, int i2, int i3) {
        int i4 = this.i + i3;
        if (i4 < 0 || i4 >= this.g) {
            StringBuilder sb = new StringBuilder();
            sb.append("Current KDFCTR may only be used for ");
            sb.append(this.g);
            sb.append(" bytes");
            throw new DataLengthException(sb.toString());
        }
        if (this.i % this.d == 0) {
            a();
        }
        int i5 = this.i % this.d;
        int min = Math.min(this.d - (this.i % this.d), i3);
        System.arraycopy(this.j, i5, bArr, i2, min);
        this.i += min;
        int i6 = i3 - min;
        while (true) {
            i2 += min;
            if (i6 <= 0) {
                return i3;
            }
            a();
            min = Math.min(this.d, i6);
            System.arraycopy(this.j, 0, bArr, i2, min);
            this.i += min;
            i6 -= min;
        }
    }

    public Mac getMac() {
        return this.c;
    }

    public void init(DerivationParameters derivationParameters) {
        if (!(derivationParameters instanceof KDFCounterParameters)) {
            throw new IllegalArgumentException("Wrong type of arguments given");
        }
        KDFCounterParameters kDFCounterParameters = (KDFCounterParameters) derivationParameters;
        this.c.init(new KeyParameter(kDFCounterParameters.getKI()));
        this.e = kDFCounterParameters.getFixedInputDataCounterPrefix();
        this.f = kDFCounterParameters.getFixedInputDataCounterSuffix();
        int r = kDFCounterParameters.getR();
        this.h = new byte[(r / 8)];
        BigInteger multiply = b.pow(r).multiply(BigInteger.valueOf((long) this.d));
        this.g = multiply.compareTo(a) == 1 ? SubsamplingScaleImageView.TILE_SIZE_AUTO : multiply.intValue();
        this.i = 0;
    }
}
