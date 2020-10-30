package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;

public class DESedeEngine extends DESEngine {
    protected static final int BLOCK_SIZE = 8;
    private int[] a = null;
    private int[] b = null;
    private int[] c = null;
    private boolean d;

    public String getAlgorithmName() {
        return "DESede";
    }

    public int getBlockSize() {
        return 8;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        int[] iArr;
        if (!(cipherParameters instanceof KeyParameter)) {
            StringBuilder sb = new StringBuilder();
            sb.append("invalid parameter passed to DESede init - ");
            sb.append(cipherParameters.getClass().getName());
            throw new IllegalArgumentException(sb.toString());
        }
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        if (key.length == 24 || key.length == 16) {
            this.d = z;
            byte[] bArr = new byte[8];
            System.arraycopy(key, 0, bArr, 0, bArr.length);
            this.a = generateWorkingKey(z, bArr);
            byte[] bArr2 = new byte[8];
            System.arraycopy(key, 8, bArr2, 0, bArr2.length);
            this.b = generateWorkingKey(!z, bArr2);
            if (key.length == 24) {
                byte[] bArr3 = new byte[8];
                System.arraycopy(key, 16, bArr3, 0, bArr3.length);
                iArr = generateWorkingKey(z, bArr3);
            } else {
                iArr = this.a;
            }
            this.c = iArr;
            return;
        }
        throw new IllegalArgumentException("key size must be 16 or 24 bytes.");
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        DESedeEngine dESedeEngine;
        int i3;
        byte[] bArr3;
        int[] iArr;
        if (this.a == null) {
            throw new IllegalStateException("DESede engine not initialised");
        } else if (i + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 8 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        } else {
            byte[] bArr4 = new byte[8];
            if (this.d) {
                dESedeEngine = this;
                byte[] bArr5 = bArr4;
                dESedeEngine.desFunc(this.a, bArr, i, bArr5, 0);
                i3 = 0;
                bArr3 = bArr4;
                dESedeEngine.desFunc(this.b, bArr3, 0, bArr5, 0);
                iArr = this.c;
            } else {
                dESedeEngine = this;
                byte[] bArr6 = bArr4;
                dESedeEngine.desFunc(this.c, bArr, i, bArr6, 0);
                i3 = 0;
                bArr3 = bArr4;
                dESedeEngine.desFunc(this.b, bArr3, 0, bArr6, 0);
                iArr = this.a;
            }
            dESedeEngine.desFunc(iArr, bArr3, i3, bArr2, i2);
            return 8;
        }
    }

    public void reset() {
    }
}
