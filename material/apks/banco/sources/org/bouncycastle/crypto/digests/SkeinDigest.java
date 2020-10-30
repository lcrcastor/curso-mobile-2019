package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.params.SkeinParameters;
import org.bouncycastle.util.Memoable;

public class SkeinDigest implements ExtendedDigest, Memoable {
    public static final int SKEIN_1024 = 1024;
    public static final int SKEIN_256 = 256;
    public static final int SKEIN_512 = 512;
    private SkeinEngine a;

    public SkeinDigest(int i, int i2) {
        this.a = new SkeinEngine(i, i2);
        init(null);
    }

    public SkeinDigest(SkeinDigest skeinDigest) {
        this.a = new SkeinEngine(skeinDigest.a);
    }

    public Memoable copy() {
        return new SkeinDigest(this);
    }

    public int doFinal(byte[] bArr, int i) {
        return this.a.doFinal(bArr, i);
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append("Skein-");
        sb.append(this.a.getBlockSize() * 8);
        sb.append("-");
        sb.append(this.a.getOutputSize() * 8);
        return sb.toString();
    }

    public int getByteLength() {
        return this.a.getBlockSize();
    }

    public int getDigestSize() {
        return this.a.getOutputSize();
    }

    public void init(SkeinParameters skeinParameters) {
        this.a.init(skeinParameters);
    }

    public void reset() {
        this.a.reset();
    }

    public void reset(Memoable memoable) {
        this.a.reset(((SkeinDigest) memoable).a);
    }

    public void update(byte b) {
        this.a.update(b);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.a.update(bArr, i, i2);
    }
}
