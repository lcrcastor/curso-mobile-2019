package org.bouncycastle.crypto.modes;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.StreamBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class GOFBBlockCipher extends StreamBlockCipher {
    boolean a = true;
    int b;
    int c;
    private byte[] d;
    private byte[] e;
    private byte[] f;
    private int g;
    private final int h;
    private final BlockCipher i;

    public GOFBBlockCipher(BlockCipher blockCipher) {
        super(blockCipher);
        this.i = blockCipher;
        this.h = blockCipher.getBlockSize();
        if (this.h != 8) {
            throw new IllegalArgumentException("GCTR only for 64 bit block ciphers");
        }
        this.d = new byte[blockCipher.getBlockSize()];
        this.e = new byte[blockCipher.getBlockSize()];
        this.f = new byte[blockCipher.getBlockSize()];
    }

    private int a(byte[] bArr, int i2) {
        return ((bArr[i2 + 3] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK) + ((bArr[i2 + 2] << Ascii.DLE) & 16711680) + ((bArr[i2 + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) + (bArr[i2] & UnsignedBytes.MAX_VALUE);
    }

    private void a(int i2, byte[] bArr, int i3) {
        bArr[i3 + 3] = (byte) (i2 >>> 24);
        bArr[i3 + 2] = (byte) (i2 >>> 16);
        bArr[i3 + 1] = (byte) (i2 >>> 8);
        bArr[i3] = (byte) i2;
    }

    /* access modifiers changed from: protected */
    public byte calculateByte(byte b2) {
        if (this.g == 0) {
            if (this.a) {
                this.a = false;
                this.i.processBlock(this.e, 0, this.f, 0);
                this.b = a(this.f, 0);
                this.c = a(this.f, 4);
            }
            this.b += 16843009;
            this.c += 16843012;
            a(this.b, this.e, 0);
            a(this.c, this.e, 4);
            this.i.processBlock(this.e, 0, this.f, 0);
        }
        byte[] bArr = this.f;
        int i2 = this.g;
        this.g = i2 + 1;
        byte b3 = (byte) (b2 ^ bArr[i2]);
        if (this.g == this.h) {
            this.g = 0;
            System.arraycopy(this.e, this.h, this.e, 0, this.e.length - this.h);
            System.arraycopy(this.f, 0, this.e, this.e.length - this.h, this.h);
        }
        return b3;
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.i.getAlgorithmName());
        sb.append("/GCTR");
        return sb.toString();
    }

    public int getBlockSize() {
        return this.h;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        BlockCipher blockCipher;
        this.a = true;
        this.b = 0;
        this.c = 0;
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            if (iv.length < this.d.length) {
                System.arraycopy(iv, 0, this.d, this.d.length - iv.length, iv.length);
                for (int i2 = 0; i2 < this.d.length - iv.length; i2++) {
                    this.d[i2] = 0;
                }
            } else {
                System.arraycopy(iv, 0, this.d, 0, this.d.length);
            }
            reset();
            if (parametersWithIV.getParameters() != null) {
                blockCipher = this.i;
                cipherParameters = parametersWithIV.getParameters();
            } else {
                return;
            }
        } else {
            reset();
            if (cipherParameters != null) {
                blockCipher = this.i;
            } else {
                return;
            }
        }
        blockCipher.init(true, cipherParameters);
    }

    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        processBytes(bArr, i2, this.h, bArr2, i3);
        return this.h;
    }

    public void reset() {
        this.a = true;
        this.b = 0;
        this.c = 0;
        System.arraycopy(this.d, 0, this.e, 0, this.d.length);
        this.g = 0;
        this.i.reset();
    }
}
