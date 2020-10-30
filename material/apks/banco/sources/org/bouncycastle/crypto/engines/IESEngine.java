package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.BasicAgreement;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.KeyParser;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.generators.EphemeralKeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.IESParameters;
import org.bouncycastle.crypto.params.IESWithCipherParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class IESEngine {
    BasicAgreement a;
    DerivationFunction b;
    Mac c;
    BufferedBlockCipher d;
    byte[] e;
    boolean f;
    CipherParameters g;
    CipherParameters h;
    IESParameters i;
    byte[] j;
    private EphemeralKeyPairGenerator k;
    private KeyParser l;
    private byte[] m;

    public IESEngine(BasicAgreement basicAgreement, DerivationFunction derivationFunction, Mac mac) {
        this.a = basicAgreement;
        this.b = derivationFunction;
        this.c = mac;
        this.e = new byte[mac.getMacSize()];
        this.d = null;
    }

    public IESEngine(BasicAgreement basicAgreement, DerivationFunction derivationFunction, Mac mac, BufferedBlockCipher bufferedBlockCipher) {
        this.a = basicAgreement;
        this.b = derivationFunction;
        this.c = mac;
        this.e = new byte[mac.getMacSize()];
        this.d = bufferedBlockCipher;
    }

    private void a(CipherParameters cipherParameters) {
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.m = parametersWithIV.getIV();
            cipherParameters = parametersWithIV.getParameters();
        } else {
            this.m = null;
        }
        this.i = (IESParameters) cipherParameters;
    }

    private byte[] a(byte[] bArr, int i2, int i3) {
        byte[] bArr2;
        byte[] bArr3;
        BufferedBlockCipher bufferedBlockCipher;
        CipherParameters keyParameter;
        if (this.d == null) {
            byte[] bArr4 = new byte[i3];
            bArr2 = new byte[(this.i.getMacKeySize() / 8)];
            byte[] bArr5 = new byte[(bArr4.length + bArr2.length)];
            this.b.generateBytes(bArr5, 0, bArr5.length);
            if (this.j.length != 0) {
                System.arraycopy(bArr5, 0, bArr2, 0, bArr2.length);
                System.arraycopy(bArr5, bArr2.length, bArr4, 0, bArr4.length);
            } else {
                System.arraycopy(bArr5, 0, bArr4, 0, bArr4.length);
                System.arraycopy(bArr5, i3, bArr2, 0, bArr2.length);
            }
            byte[] bArr6 = new byte[i3];
            for (int i4 = 0; i4 != i3; i4++) {
                bArr6[i4] = (byte) (bArr[i2 + i4] ^ bArr4[i4]);
            }
            bArr3 = bArr6;
        } else {
            byte[] bArr7 = new byte[(((IESWithCipherParameters) this.i).getCipherKeySize() / 8)];
            bArr2 = new byte[(this.i.getMacKeySize() / 8)];
            byte[] bArr8 = new byte[(bArr7.length + bArr2.length)];
            this.b.generateBytes(bArr8, 0, bArr8.length);
            System.arraycopy(bArr8, 0, bArr7, 0, bArr7.length);
            System.arraycopy(bArr8, bArr7.length, bArr2, 0, bArr2.length);
            if (this.m != null) {
                bufferedBlockCipher = this.d;
                keyParameter = new ParametersWithIV(new KeyParameter(bArr7), this.m);
            } else {
                bufferedBlockCipher = this.d;
                keyParameter = new KeyParameter(bArr7);
            }
            bufferedBlockCipher.init(true, keyParameter);
            bArr3 = new byte[this.d.getOutputSize(i3)];
            int processBytes = this.d.processBytes(bArr, i2, i3, bArr3, 0);
            i3 = processBytes + this.d.doFinal(bArr3, processBytes);
        }
        byte[] encodingV = this.i.getEncodingV();
        byte[] bArr9 = new byte[4];
        if (!(this.j.length == 0 || encodingV == null)) {
            Pack.intToBigEndian(encodingV.length * 8, bArr9, 0);
        }
        byte[] bArr10 = new byte[this.c.getMacSize()];
        this.c.init(new KeyParameter(bArr2));
        this.c.update(bArr3, 0, bArr3.length);
        if (encodingV != null) {
            this.c.update(encodingV, 0, encodingV.length);
        }
        if (this.j.length != 0) {
            this.c.update(bArr9, 0, bArr9.length);
        }
        this.c.doFinal(bArr10, 0);
        byte[] bArr11 = new byte[(this.j.length + i3 + bArr10.length)];
        System.arraycopy(this.j, 0, bArr11, 0, this.j.length);
        System.arraycopy(bArr3, 0, bArr11, this.j.length, i3);
        System.arraycopy(bArr10, 0, bArr11, this.j.length + i3, bArr10.length);
        return bArr11;
    }

    private byte[] b(byte[] bArr, int i2, int i3) {
        int i4;
        byte[] bArr2;
        byte[] bArr3;
        BufferedBlockCipher bufferedBlockCipher;
        CipherParameters keyParameter;
        if (i3 <= this.i.getMacKeySize() / 8) {
            throw new InvalidCipherTextException("Length of input must be greater than the MAC");
        }
        if (this.d == null) {
            byte[] bArr4 = new byte[((i3 - this.j.length) - this.c.getMacSize())];
            bArr2 = new byte[(this.i.getMacKeySize() / 8)];
            byte[] bArr5 = new byte[(bArr4.length + bArr2.length)];
            this.b.generateBytes(bArr5, 0, bArr5.length);
            if (this.j.length != 0) {
                System.arraycopy(bArr5, 0, bArr2, 0, bArr2.length);
                System.arraycopy(bArr5, bArr2.length, bArr4, 0, bArr4.length);
            } else {
                System.arraycopy(bArr5, 0, bArr4, 0, bArr4.length);
                System.arraycopy(bArr5, bArr4.length, bArr2, 0, bArr2.length);
            }
            byte[] bArr6 = new byte[bArr4.length];
            for (int i5 = 0; i5 != bArr4.length; i5++) {
                bArr6[i5] = (byte) (bArr[(this.j.length + i2) + i5] ^ bArr4[i5]);
            }
            byte[] bArr7 = bArr6;
            i4 = bArr4.length;
            bArr3 = bArr7;
        } else {
            byte[] bArr8 = new byte[(((IESWithCipherParameters) this.i).getCipherKeySize() / 8)];
            bArr2 = new byte[(this.i.getMacKeySize() / 8)];
            byte[] bArr9 = new byte[(bArr8.length + bArr2.length)];
            this.b.generateBytes(bArr9, 0, bArr9.length);
            System.arraycopy(bArr9, 0, bArr8, 0, bArr8.length);
            System.arraycopy(bArr9, bArr8.length, bArr2, 0, bArr2.length);
            if (this.m != null) {
                bufferedBlockCipher = this.d;
                keyParameter = new ParametersWithIV(new KeyParameter(bArr8), this.m);
            } else {
                bufferedBlockCipher = this.d;
                keyParameter = new KeyParameter(bArr8);
            }
            bufferedBlockCipher.init(false, keyParameter);
            bArr3 = new byte[this.d.getOutputSize((i3 - this.j.length) - this.c.getMacSize())];
            int processBytes = this.d.processBytes(bArr, i2 + this.j.length, (i3 - this.j.length) - this.c.getMacSize(), bArr3, 0);
            i4 = processBytes + this.d.doFinal(bArr3, processBytes);
        }
        byte[] encodingV = this.i.getEncodingV();
        byte[] bArr10 = new byte[4];
        if (!(this.j.length == 0 || encodingV == null)) {
            Pack.intToBigEndian(encodingV.length * 8, bArr10, 0);
        }
        int i6 = i2 + i3;
        byte[] copyOfRange = Arrays.copyOfRange(bArr, i6 - this.c.getMacSize(), i6);
        byte[] bArr11 = new byte[copyOfRange.length];
        this.c.init(new KeyParameter(bArr2));
        this.c.update(bArr, i2 + this.j.length, (i3 - this.j.length) - bArr11.length);
        if (encodingV != null) {
            this.c.update(encodingV, 0, encodingV.length);
        }
        if (this.j.length != 0) {
            this.c.update(bArr10, 0, bArr10.length);
        }
        this.c.doFinal(bArr11, 0);
        if (Arrays.constantTimeAreEqual(copyOfRange, bArr11)) {
            return Arrays.copyOfRange(bArr3, 0, i4);
        }
        throw new InvalidCipherTextException("Invalid MAC.");
    }

    public BufferedBlockCipher getCipher() {
        return this.d;
    }

    public Mac getMac() {
        return this.c;
    }

    public void init(AsymmetricKeyParameter asymmetricKeyParameter, CipherParameters cipherParameters, KeyParser keyParser) {
        this.f = false;
        this.g = asymmetricKeyParameter;
        this.l = keyParser;
        a(cipherParameters);
    }

    public void init(AsymmetricKeyParameter asymmetricKeyParameter, CipherParameters cipherParameters, EphemeralKeyPairGenerator ephemeralKeyPairGenerator) {
        this.f = true;
        this.h = asymmetricKeyParameter;
        this.k = ephemeralKeyPairGenerator;
        a(cipherParameters);
    }

    public void init(boolean z, CipherParameters cipherParameters, CipherParameters cipherParameters2, CipherParameters cipherParameters3) {
        this.f = z;
        this.g = cipherParameters;
        this.h = cipherParameters2;
        this.j = new byte[0];
        a(cipherParameters3);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00a2  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00a7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] processBlock(byte[] r6, int r7, int r8) {
        /*
            r5 = this;
            boolean r0 = r5.f
            if (r0 == 0) goto L_0x001f
            org.bouncycastle.crypto.generators.EphemeralKeyPairGenerator r0 = r5.k
            if (r0 == 0) goto L_0x0058
            org.bouncycastle.crypto.generators.EphemeralKeyPairGenerator r0 = r5.k
            org.bouncycastle.crypto.EphemeralKeyPair r0 = r0.generate()
            org.bouncycastle.crypto.AsymmetricCipherKeyPair r1 = r0.getKeyPair()
            org.bouncycastle.crypto.params.AsymmetricKeyParameter r1 = r1.getPrivate()
            r5.g = r1
            byte[] r0 = r0.getEncodedPublicKey()
        L_0x001c:
            r5.j = r0
            goto L_0x0058
        L_0x001f:
            org.bouncycastle.crypto.KeyParser r0 = r5.l
            if (r0 == 0) goto L_0x0058
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream
            r0.<init>(r6, r7, r8)
            org.bouncycastle.crypto.KeyParser r1 = r5.l     // Catch:{ IOException -> 0x003c }
            org.bouncycastle.crypto.params.AsymmetricKeyParameter r1 = r1.readKey(r0)     // Catch:{ IOException -> 0x003c }
            r5.h = r1     // Catch:{ IOException -> 0x003c }
            int r0 = r0.available()
            int r0 = r8 - r0
            int r0 = r0 + r7
            byte[] r0 = org.bouncycastle.util.Arrays.copyOfRange(r6, r7, r0)
            goto L_0x001c
        L_0x003c:
            r6 = move-exception
            org.bouncycastle.crypto.InvalidCipherTextException r7 = new org.bouncycastle.crypto.InvalidCipherTextException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r0 = "unable to recover ephemeral public key: "
            r8.append(r0)
            java.lang.String r0 = r6.getMessage()
            r8.append(r0)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8, r6)
            throw r7
        L_0x0058:
            org.bouncycastle.crypto.BasicAgreement r0 = r5.a
            org.bouncycastle.crypto.CipherParameters r1 = r5.g
            r0.init(r1)
            org.bouncycastle.crypto.BasicAgreement r0 = r5.a
            org.bouncycastle.crypto.CipherParameters r1 = r5.h
            java.math.BigInteger r0 = r0.calculateAgreement(r1)
            org.bouncycastle.crypto.BasicAgreement r1 = r5.a
            int r1 = r1.getFieldSize()
            byte[] r0 = org.bouncycastle.util.BigIntegers.asUnsignedByteArray(r1, r0)
            byte[] r1 = r5.j
            int r1 = r1.length
            if (r1 == 0) goto L_0x008e
            byte[] r1 = r5.j
            int r1 = r1.length
            int r2 = r0.length
            int r1 = r1 + r2
            byte[] r1 = new byte[r1]
            byte[] r2 = r5.j
            byte[] r3 = r5.j
            int r3 = r3.length
            r4 = 0
            java.lang.System.arraycopy(r2, r4, r1, r4, r3)
            byte[] r2 = r5.j
            int r2 = r2.length
            int r3 = r0.length
            java.lang.System.arraycopy(r0, r4, r1, r2, r3)
            r0 = r1
        L_0x008e:
            org.bouncycastle.crypto.params.KDFParameters r1 = new org.bouncycastle.crypto.params.KDFParameters
            org.bouncycastle.crypto.params.IESParameters r2 = r5.i
            byte[] r2 = r2.getDerivationV()
            r1.<init>(r0, r2)
            org.bouncycastle.crypto.DerivationFunction r0 = r5.b
            r0.init(r1)
            boolean r0 = r5.f
            if (r0 == 0) goto L_0x00a7
            byte[] r6 = r5.a(r6, r7, r8)
            return r6
        L_0x00a7:
            byte[] r6 = r5.b(r6, r7, r8)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.engines.IESEngine.processBlock(byte[], int, int):byte[]");
    }
}
