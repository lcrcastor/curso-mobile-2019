package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.digests.SkeinEngine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.SkeinParameters;
import org.bouncycastle.crypto.params.SkeinParameters.Builder;

public class SkeinMac implements Mac {
    public static final int SKEIN_1024 = 1024;
    public static final int SKEIN_256 = 256;
    public static final int SKEIN_512 = 512;
    private SkeinEngine a;

    public SkeinMac(int i, int i2) {
        this.a = new SkeinEngine(i, i2);
    }

    public SkeinMac(SkeinMac skeinMac) {
        this.a = new SkeinEngine(skeinMac.a);
    }

    public int doFinal(byte[] bArr, int i) {
        return this.a.doFinal(bArr, i);
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append("Skein-MAC-");
        sb.append(this.a.getBlockSize() * 8);
        sb.append("-");
        sb.append(this.a.getOutputSize() * 8);
        return sb.toString();
    }

    public int getMacSize() {
        return this.a.getOutputSize();
    }

    public void init(CipherParameters cipherParameters) {
        SkeinParameters skeinParameters;
        if (cipherParameters instanceof SkeinParameters) {
            skeinParameters = (SkeinParameters) cipherParameters;
        } else if (cipherParameters instanceof KeyParameter) {
            skeinParameters = new Builder().setKey(((KeyParameter) cipherParameters).getKey()).build();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid parameter passed to Skein MAC init - ");
            sb.append(cipherParameters.getClass().getName());
            throw new IllegalArgumentException(sb.toString());
        }
        if (skeinParameters.getKey() == null) {
            throw new IllegalArgumentException("Skein MAC requires a key parameter.");
        }
        this.a.init(skeinParameters);
    }

    public void reset() {
        this.a.reset();
    }

    public void update(byte b) {
        this.a.update(b);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.a.update(bArr, i, i2);
    }
}
