package org.bouncycastle.crypto.generators;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.math.BigInteger;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.MacDerivationFunction;
import org.bouncycastle.crypto.params.KDFDoublePipelineIterationParameters;
import org.bouncycastle.crypto.params.KeyParameter;

public class KDFDoublePipelineIterationBytesGenerator implements MacDerivationFunction {
    private static final BigInteger a = BigInteger.valueOf(2147483647L);
    private static final BigInteger b = BigInteger.valueOf(2);
    private final Mac c;
    private final int d;
    private byte[] e;
    private int f;
    private byte[] g;
    private boolean h;
    private int i;
    private byte[] j = new byte[this.d];
    private byte[] k = new byte[this.d];

    public KDFDoublePipelineIterationBytesGenerator(Mac mac) {
        this.c = mac;
        this.d = mac.getMacSize();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0049, code lost:
        r5.g[r5.g.length - 3] = (byte) (r0 >>> 16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0055, code lost:
        r5.g[r5.g.length - 2] = (byte) (r0 >>> 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0061, code lost:
        r5.g[r5.g.length - 1] = (byte) r0;
        r5.c.update(r5.g, 0, r5.g.length);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a() {
        /*
            r5 = this;
            int r0 = r5.i
            r1 = 0
            if (r0 != 0) goto L_0x0017
            org.bouncycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.e
            byte[] r3 = r5.e
            int r3 = r3.length
        L_0x000c:
            r0.update(r2, r1, r3)
            org.bouncycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.j
            r0.doFinal(r2, r1)
            goto L_0x001f
        L_0x0017:
            org.bouncycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.j
            byte[] r3 = r5.j
            int r3 = r3.length
            goto L_0x000c
        L_0x001f:
            org.bouncycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.j
            byte[] r3 = r5.j
            int r3 = r3.length
            r0.update(r2, r1, r3)
            boolean r0 = r5.h
            if (r0 == 0) goto L_0x0075
            int r0 = r5.i
            int r2 = r5.d
            int r0 = r0 / r2
            int r0 = r0 + 1
            byte[] r2 = r5.g
            int r2 = r2.length
            switch(r2) {
                case 1: goto L_0x0061;
                case 2: goto L_0x0055;
                case 3: goto L_0x0049;
                case 4: goto L_0x0042;
                default: goto L_0x003a;
            }
        L_0x003a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Unsupported size of counter i"
            r0.<init>(r1)
            throw r0
        L_0x0042:
            byte[] r2 = r5.g
            int r3 = r0 >>> 24
            byte r3 = (byte) r3
            r2[r1] = r3
        L_0x0049:
            byte[] r2 = r5.g
            byte[] r3 = r5.g
            int r3 = r3.length
            int r3 = r3 + -3
            int r4 = r0 >>> 16
            byte r4 = (byte) r4
            r2[r3] = r4
        L_0x0055:
            byte[] r2 = r5.g
            byte[] r3 = r5.g
            int r3 = r3.length
            int r3 = r3 + -2
            int r4 = r0 >>> 8
            byte r4 = (byte) r4
            r2[r3] = r4
        L_0x0061:
            byte[] r2 = r5.g
            byte[] r3 = r5.g
            int r3 = r3.length
            int r3 = r3 + -1
            byte r0 = (byte) r0
            r2[r3] = r0
            org.bouncycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.g
            byte[] r3 = r5.g
            int r3 = r3.length
            r0.update(r2, r1, r3)
        L_0x0075:
            org.bouncycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.e
            byte[] r3 = r5.e
            int r3 = r3.length
            r0.update(r2, r1, r3)
            org.bouncycastle.crypto.Mac r0 = r5.c
            byte[] r2 = r5.k
            r0.doFinal(r2, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.generators.KDFDoublePipelineIterationBytesGenerator.a():void");
    }

    public int generateBytes(byte[] bArr, int i2, int i3) {
        int i4 = this.i + i3;
        if (i4 < 0 || i4 >= this.f) {
            StringBuilder sb = new StringBuilder();
            sb.append("Current KDFCTR may only be used for ");
            sb.append(this.f);
            sb.append(" bytes");
            throw new DataLengthException(sb.toString());
        }
        if (this.i % this.d == 0) {
            a();
        }
        int i5 = this.i % this.d;
        int min = Math.min(this.d - (this.i % this.d), i3);
        System.arraycopy(this.k, i5, bArr, i2, min);
        this.i += min;
        int i6 = i3 - min;
        while (true) {
            i2 += min;
            if (i6 <= 0) {
                return i3;
            }
            a();
            min = Math.min(this.d, i6);
            System.arraycopy(this.k, 0, bArr, i2, min);
            this.i += min;
            i6 -= min;
        }
    }

    public Mac getMac() {
        return this.c;
    }

    public void init(DerivationParameters derivationParameters) {
        if (!(derivationParameters instanceof KDFDoublePipelineIterationParameters)) {
            throw new IllegalArgumentException("Wrong type of arguments given");
        }
        KDFDoublePipelineIterationParameters kDFDoublePipelineIterationParameters = (KDFDoublePipelineIterationParameters) derivationParameters;
        this.c.init(new KeyParameter(kDFDoublePipelineIterationParameters.getKI()));
        this.e = kDFDoublePipelineIterationParameters.getFixedInputData();
        int r = kDFDoublePipelineIterationParameters.getR();
        this.g = new byte[(r / 8)];
        boolean useCounter = kDFDoublePipelineIterationParameters.useCounter();
        int i2 = SubsamplingScaleImageView.TILE_SIZE_AUTO;
        if (useCounter) {
            BigInteger multiply = b.pow(r).multiply(BigInteger.valueOf((long) this.d));
            if (multiply.compareTo(a) != 1) {
                i2 = multiply.intValue();
            }
        }
        this.f = i2;
        this.h = kDFDoublePipelineIterationParameters.useCounter();
        this.i = 0;
    }
}
