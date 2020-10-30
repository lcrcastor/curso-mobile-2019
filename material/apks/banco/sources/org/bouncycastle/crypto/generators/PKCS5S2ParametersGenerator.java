package org.bouncycastle.crypto.generators;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class PKCS5S2ParametersGenerator extends PBEParametersGenerator {
    private Mac a;
    private byte[] b;

    public PKCS5S2ParametersGenerator() {
        this(new SHA1Digest());
    }

    public PKCS5S2ParametersGenerator(Digest digest) {
        this.a = new HMac(digest);
        this.b = new byte[this.a.getMacSize()];
    }

    private void a(byte[] bArr, int i, byte[] bArr2, byte[] bArr3, int i2) {
        if (i == 0) {
            throw new IllegalArgumentException("iteration count must be at least 1.");
        }
        if (bArr != null) {
            this.a.update(bArr, 0, bArr.length);
        }
        this.a.update(bArr2, 0, bArr2.length);
        this.a.doFinal(this.b, 0);
        System.arraycopy(this.b, 0, bArr3, i2, this.b.length);
        for (int i3 = 1; i3 < i; i3++) {
            this.a.update(this.b, 0, this.b.length);
            this.a.doFinal(this.b, 0);
            for (int i4 = 0; i4 != this.b.length; i4++) {
                int i5 = i2 + i4;
                bArr3[i5] = (byte) (bArr3[i5] ^ this.b[i4]);
            }
        }
    }

    private byte[] a(int i) {
        int macSize = this.a.getMacSize();
        int i2 = ((i + macSize) - 1) / macSize;
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[(i2 * macSize)];
        this.a.init(new KeyParameter(this.password));
        int i3 = 0;
        for (int i4 = 1; i4 <= i2; i4++) {
            int i5 = 3;
            while (true) {
                byte b2 = (byte) (bArr[i5] + 1);
                bArr[i5] = b2;
                if (b2 != 0) {
                    break;
                }
                i5--;
            }
            a(this.salt, this.iterationCount, bArr, bArr2, i3);
            i3 += macSize;
        }
        return bArr2;
    }

    public CipherParameters generateDerivedMacParameters(int i) {
        return generateDerivedParameters(i);
    }

    public CipherParameters generateDerivedParameters(int i) {
        int i2 = i / 8;
        return new KeyParameter(a(i2), 0, i2);
    }

    public CipherParameters generateDerivedParameters(int i, int i2) {
        int i3 = i / 8;
        int i4 = i2 / 8;
        byte[] a2 = a(i3 + i4);
        return new ParametersWithIV(new KeyParameter(a2, 0, i3), a2, i3, i4);
    }
}
