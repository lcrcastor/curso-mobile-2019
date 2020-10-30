package org.bouncycastle.crypto.modes;

import android.support.v4.media.session.PlaybackStateCompat;
import com.google.common.base.Ascii;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.StreamBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.ParametersWithSBox;

public class GCFBBlockCipher extends StreamBlockCipher {
    private static final byte[] a = {105, 0, 114, 34, 100, -55, 4, 35, -115, 58, -37, -106, 70, -23, 42, -60, Ascii.CAN, -2, -84, -108, 0, -19, 7, Ascii.DC2, -64, -122, -36, -62, -17, 76, -87, 43};
    private final CFBBlockCipher b;
    private KeyParameter c;
    private long d = 0;
    private boolean e;

    public GCFBBlockCipher(BlockCipher blockCipher) {
        super(blockCipher);
        this.b = new CFBBlockCipher(blockCipher, blockCipher.getBlockSize() * 8);
    }

    /* access modifiers changed from: protected */
    public byte calculateByte(byte b2) {
        if (this.d > 0 && this.d % PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID == 0) {
            BlockCipher underlyingCipher = this.b.getUnderlyingCipher();
            underlyingCipher.init(false, this.c);
            byte[] bArr = new byte[32];
            underlyingCipher.processBlock(a, 0, bArr, 0);
            underlyingCipher.processBlock(a, 8, bArr, 8);
            underlyingCipher.processBlock(a, 16, bArr, 16);
            underlyingCipher.processBlock(a, 24, bArr, 24);
            this.c = new KeyParameter(bArr);
            underlyingCipher.init(true, this.c);
            byte[] currentIV = this.b.getCurrentIV();
            underlyingCipher.processBlock(currentIV, 0, currentIV, 0);
            this.b.init(this.e, new ParametersWithIV(this.c, currentIV));
        }
        this.d++;
        return this.b.calculateByte(b2);
    }

    public String getAlgorithmName() {
        String algorithmName = this.b.getAlgorithmName();
        StringBuilder sb = new StringBuilder();
        sb.append(algorithmName.substring(0, algorithmName.indexOf(47) - 1));
        sb.append("/G");
        sb.append(algorithmName.substring(algorithmName.indexOf(47) + 1));
        return sb.toString();
    }

    public int getBlockSize() {
        return this.b.getBlockSize();
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.d = 0;
        this.b.init(z, cipherParameters);
        this.e = z;
        if (cipherParameters instanceof ParametersWithIV) {
            cipherParameters = ((ParametersWithIV) cipherParameters).getParameters();
        }
        if (cipherParameters instanceof ParametersWithRandom) {
            cipherParameters = ((ParametersWithRandom) cipherParameters).getParameters();
        }
        if (cipherParameters instanceof ParametersWithSBox) {
            cipherParameters = ((ParametersWithSBox) cipherParameters).getParameters();
        }
        this.c = (KeyParameter) cipherParameters;
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        processBytes(bArr, i, this.b.getBlockSize(), bArr2, i2);
        return this.b.getBlockSize();
    }

    public void reset() {
        this.d = 0;
        this.b.reset();
    }
}
