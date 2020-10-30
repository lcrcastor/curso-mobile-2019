package org.bouncycastle.crypto.modes;

import com.google.common.primitives.SignedBytes;
import java.io.ByteArrayOutputStream;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

public class CCMBlockCipher implements AEADBlockCipher {
    private BlockCipher a;
    private int b;
    private boolean c;
    private byte[] d;
    private byte[] e;
    private int f;
    private CipherParameters g;
    private byte[] h;
    private ExposedByteArrayOutputStream i = new ExposedByteArrayOutputStream();
    private ExposedByteArrayOutputStream j = new ExposedByteArrayOutputStream();

    class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
        public ExposedByteArrayOutputStream() {
        }

        public byte[] a() {
            return this.buf;
        }
    }

    public CCMBlockCipher(BlockCipher blockCipher) {
        this.a = blockCipher;
        this.b = blockCipher.getBlockSize();
        this.h = new byte[this.b];
        if (this.b != 16) {
            throw new IllegalArgumentException("cipher required with a block size of 16.");
        }
    }

    private int a() {
        return this.i.size() + (this.e == null ? 0 : this.e.length);
    }

    private int a(byte[] bArr, int i2, int i3, byte[] bArr2) {
        CBCBlockCipherMac cBCBlockCipherMac = new CBCBlockCipherMac(this.a, this.f * 8);
        cBCBlockCipherMac.init(this.g);
        byte[] bArr3 = new byte[16];
        if (b()) {
            bArr3[0] = (byte) (bArr3[0] | SignedBytes.MAX_POWER_OF_TWO);
        }
        int i4 = 2;
        bArr3[0] = (byte) (bArr3[0] | ((((cBCBlockCipherMac.getMacSize() - 2) / 2) & 7) << 3));
        bArr3[0] = (byte) (bArr3[0] | (((15 - this.d.length) - 1) & 7));
        System.arraycopy(this.d, 0, bArr3, 1, this.d.length);
        int i5 = i3;
        int i6 = 1;
        while (i5 > 0) {
            bArr3[bArr3.length - i6] = (byte) (i5 & 255);
            i5 >>>= 8;
            i6++;
        }
        cBCBlockCipherMac.update(bArr3, 0, bArr3.length);
        if (b()) {
            int a2 = a();
            if (a2 < 65280) {
                cBCBlockCipherMac.update((byte) (a2 >> 8));
                cBCBlockCipherMac.update((byte) a2);
            } else {
                cBCBlockCipherMac.update(-1);
                cBCBlockCipherMac.update(-2);
                cBCBlockCipherMac.update((byte) (a2 >> 24));
                cBCBlockCipherMac.update((byte) (a2 >> 16));
                cBCBlockCipherMac.update((byte) (a2 >> 8));
                cBCBlockCipherMac.update((byte) a2);
                i4 = 6;
            }
            if (this.e != null) {
                cBCBlockCipherMac.update(this.e, 0, this.e.length);
            }
            if (this.i.size() > 0) {
                cBCBlockCipherMac.update(this.i.a(), 0, this.i.size());
            }
            int i7 = (i4 + a2) % 16;
            if (i7 != 0) {
                while (i7 != 16) {
                    cBCBlockCipherMac.update(0);
                    i7++;
                }
            }
        }
        cBCBlockCipherMac.update(bArr, i2, i3);
        return cBCBlockCipherMac.doFinal(bArr2, 0);
    }

    private boolean b() {
        return a() > 0;
    }

    public int doFinal(byte[] bArr, int i2) {
        int processPacket = processPacket(this.j.a(), 0, this.j.size(), bArr, i2);
        reset();
        return processPacket;
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getAlgorithmName());
        sb.append("/CCM");
        return sb.toString();
    }

    public byte[] getMac() {
        byte[] bArr = new byte[this.f];
        System.arraycopy(this.h, 0, bArr, 0, bArr.length);
        return bArr;
    }

    public int getOutputSize(int i2) {
        int size = i2 + this.j.size();
        if (this.c) {
            return size + this.f;
        }
        if (size < this.f) {
            return 0;
        }
        return size - this.f;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.a;
    }

    public int getUpdateOutputSize(int i2) {
        return 0;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        CipherParameters cipherParameters2;
        this.c = z;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            this.d = aEADParameters.getNonce();
            this.e = aEADParameters.getAssociatedText();
            this.f = aEADParameters.getMacSize() / 8;
            cipherParameters2 = aEADParameters.getKey();
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.d = parametersWithIV.getIV();
            this.e = null;
            this.f = this.h.length / 2;
            cipherParameters2 = parametersWithIV.getParameters();
        } else {
            throw new IllegalArgumentException("invalid parameters passed to CCM");
        }
        if (cipherParameters2 != null) {
            this.g = cipherParameters2;
        }
        if (this.d == null || this.d.length < 7 || this.d.length > 13) {
            throw new IllegalArgumentException("nonce must have length from 7 to 13 octets");
        }
        reset();
    }

    public void processAADByte(byte b2) {
        this.i.write(b2);
    }

    public void processAADBytes(byte[] bArr, int i2, int i3) {
        this.i.write(bArr, i2, i3);
    }

    public int processByte(byte b2, byte[] bArr, int i2) {
        this.j.write(b2);
        return 0;
    }

    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (bArr.length < i2 + i3) {
            throw new DataLengthException("Input buffer too short");
        }
        this.j.write(bArr, i2, i3);
        return 0;
    }

    public int processPacket(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (this.g == null) {
            throw new IllegalStateException("CCM cipher unitialized.");
        }
        int length = 15 - this.d.length;
        if (length >= 4 || i3 < (1 << (length * 8))) {
            byte[] bArr3 = new byte[this.b];
            bArr3[0] = (byte) ((length - 1) & 7);
            System.arraycopy(this.d, 0, bArr3, 1, this.d.length);
            SICBlockCipher sICBlockCipher = new SICBlockCipher(this.a);
            sICBlockCipher.init(this.c, new ParametersWithIV(this.g, bArr3));
            if (this.c) {
                int i5 = this.f + i3;
                if (bArr2.length < i5 + i4) {
                    throw new OutputLengthException("Output buffer too short.");
                }
                a(bArr, i2, i3, this.h);
                sICBlockCipher.processBlock(this.h, 0, this.h, 0);
                int i6 = i2;
                int i7 = i4;
                while (true) {
                    int i8 = i2 + i3;
                    if (i6 < i8 - this.b) {
                        sICBlockCipher.processBlock(bArr, i6, bArr2, i7);
                        i7 += this.b;
                        i6 += this.b;
                    } else {
                        byte[] bArr4 = new byte[this.b];
                        int i9 = i8 - i6;
                        System.arraycopy(bArr, i6, bArr4, 0, i9);
                        sICBlockCipher.processBlock(bArr4, 0, bArr4, 0);
                        System.arraycopy(bArr4, 0, bArr2, i7, i9);
                        System.arraycopy(this.h, 0, bArr2, i4 + i3, this.f);
                        return i5;
                    }
                }
            } else if (i3 < this.f) {
                throw new InvalidCipherTextException("data too short");
            } else {
                int i10 = i3 - this.f;
                if (bArr2.length < i10 + i4) {
                    throw new OutputLengthException("Output buffer too short.");
                }
                int i11 = i2 + i10;
                System.arraycopy(bArr, i11, this.h, 0, this.f);
                sICBlockCipher.processBlock(this.h, 0, this.h, 0);
                for (int i12 = this.f; i12 != this.h.length; i12++) {
                    this.h[i12] = 0;
                }
                int i13 = i2;
                int i14 = i4;
                while (i13 < i11 - this.b) {
                    sICBlockCipher.processBlock(bArr, i13, bArr2, i14);
                    i14 += this.b;
                    i13 += this.b;
                }
                byte[] bArr5 = new byte[this.b];
                int i15 = i10 - (i13 - i2);
                System.arraycopy(bArr, i13, bArr5, 0, i15);
                sICBlockCipher.processBlock(bArr5, 0, bArr5, 0);
                System.arraycopy(bArr5, 0, bArr2, i14, i15);
                byte[] bArr6 = new byte[this.b];
                a(bArr2, i4, i10, bArr6);
                if (Arrays.constantTimeAreEqual(this.h, bArr6)) {
                    return i10;
                }
                throw new InvalidCipherTextException("mac check in CCM failed");
            }
        } else {
            throw new IllegalStateException("CCM packet too large for choice of q.");
        }
    }

    public byte[] processPacket(byte[] bArr, int i2, int i3) {
        int i4;
        if (this.c) {
            i4 = this.f + i3;
        } else if (i3 < this.f) {
            throw new InvalidCipherTextException("data too short");
        } else {
            i4 = i3 - this.f;
        }
        byte[] bArr2 = new byte[i4];
        processPacket(bArr, i2, i3, bArr2, 0);
        return bArr2;
    }

    public void reset() {
        this.a.reset();
        this.i.reset();
        this.j.reset();
    }
}
