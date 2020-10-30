package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;

public class NullEngine implements BlockCipher {
    protected static final int DEFAULT_BLOCK_SIZE = 1;
    private boolean a;
    private final int b;

    public NullEngine() {
        this(1);
    }

    public NullEngine(int i) {
        this.b = i;
    }

    public String getAlgorithmName() {
        return "Null";
    }

    public int getBlockSize() {
        return this.b;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.a = true;
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (!this.a) {
            throw new IllegalStateException("Null engine not initialised");
        } else if (this.b + i > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (this.b + i2 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else {
            for (int i3 = 0; i3 < this.b; i3++) {
                bArr2[i2 + i3] = bArr[i + i3];
            }
            return this.b;
        }
    }

    public void reset() {
    }
}
