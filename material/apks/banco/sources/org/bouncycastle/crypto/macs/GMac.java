package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class GMac implements Mac {
    private final GCMBlockCipher a;
    private final int b;

    public GMac(GCMBlockCipher gCMBlockCipher) {
        this.a = gCMBlockCipher;
        this.b = 128;
    }

    public GMac(GCMBlockCipher gCMBlockCipher, int i) {
        this.a = gCMBlockCipher;
        this.b = i;
    }

    public int doFinal(byte[] bArr, int i) {
        try {
            return this.a.doFinal(bArr, i);
        } catch (InvalidCipherTextException e) {
            throw new IllegalStateException(e.toString());
        }
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getUnderlyingCipher().getAlgorithmName());
        sb.append("-GMAC");
        return sb.toString();
    }

    public int getMacSize() {
        return this.b / 8;
    }

    public void init(CipherParameters cipherParameters) {
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            this.a.init(true, new AEADParameters((KeyParameter) parametersWithIV.getParameters(), this.b, iv));
            return;
        }
        throw new IllegalArgumentException("GMAC requires ParametersWithIV");
    }

    public void reset() {
        this.a.reset();
    }

    public void update(byte b2) {
        this.a.processAADByte(b2);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.a.processAADBytes(bArr, i, i2);
    }
}
