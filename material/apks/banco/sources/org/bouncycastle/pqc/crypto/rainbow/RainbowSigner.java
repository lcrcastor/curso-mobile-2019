package org.bouncycastle.pqc.crypto.rainbow;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.MessageSigner;
import org.bouncycastle.pqc.crypto.rainbow.util.ComputeInField;
import org.bouncycastle.pqc.crypto.rainbow.util.GF2Field;

public class RainbowSigner implements MessageSigner {
    int a;
    RainbowKeyParameters b;
    private SecureRandom c;
    private short[] d;
    private ComputeInField e = new ComputeInField();

    private short[] a(byte[] bArr) {
        short[] sArr = new short[this.a];
        int i = 0;
        int i2 = 0;
        while (i < bArr.length) {
            sArr[i] = (short) bArr[i2];
            sArr[i] = (short) (sArr[i] & 255);
            i2++;
            i++;
            if (i >= sArr.length) {
                return sArr;
            }
        }
        return sArr;
    }

    private short[] a(Layer[] layerArr, short[] sArr) {
        short[] sArr2 = new short[sArr.length];
        short[] multiplyMatrix = this.e.multiplyMatrix(((RainbowPrivateKeyParameters) this.b).getInvA1(), this.e.addVect(((RainbowPrivateKeyParameters) this.b).getB1(), sArr));
        for (int i = 0; i < layerArr[0].getVi(); i++) {
            this.d[i] = (short) this.c.nextInt();
            this.d[i] = (short) (this.d[i] & 255);
        }
        return multiplyMatrix;
    }

    private short[] a(short[] sArr) {
        short[][] coeffQuadratic = ((RainbowPublicKeyParameters) this.b).getCoeffQuadratic();
        short[][] coeffSingular = ((RainbowPublicKeyParameters) this.b).getCoeffSingular();
        short[] coeffScalar = ((RainbowPublicKeyParameters) this.b).getCoeffScalar();
        short[] sArr2 = new short[coeffQuadratic.length];
        int length = coeffSingular[0].length;
        for (int i = 0; i < coeffQuadratic.length; i++) {
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                int i4 = i3;
                for (int i5 = i2; i5 < length; i5++) {
                    sArr2[i] = GF2Field.addElem(sArr2[i], GF2Field.multElem(coeffQuadratic[i][i4], GF2Field.multElem(sArr[i2], sArr[i5])));
                    i4++;
                }
                sArr2[i] = GF2Field.addElem(sArr2[i], GF2Field.multElem(coeffSingular[i][i2], sArr[i2]));
                i2++;
                i3 = i4;
            }
            sArr2[i] = GF2Field.addElem(sArr2[i], coeffScalar[i]);
        }
        return sArr2;
    }

    public byte[] generateSignature(byte[] bArr) {
        boolean z;
        Layer[] layers = ((RainbowPrivateKeyParameters) this.b).getLayers();
        int length = layers.length;
        this.d = new short[((RainbowPrivateKeyParameters) this.b).getInvA2().length];
        byte[] bArr2 = new byte[layers[length - 1].getViNext()];
        short[] a2 = a(bArr);
        do {
            z = false;
            try {
                short[] a3 = a(layers, a2);
                int i = 0;
                int i2 = 0;
                while (i < length) {
                    short[] sArr = new short[layers[i].getOi()];
                    short[] sArr2 = new short[layers[i].getOi()];
                    int i3 = i2;
                    for (int i4 = 0; i4 < layers[i].getOi(); i4++) {
                        sArr[i4] = a3[i3];
                        i3++;
                    }
                    short[] solveEquation = this.e.solveEquation(layers[i].plugInVinegars(this.d), sArr);
                    if (solveEquation == null) {
                        throw new Exception("LES is not solveable!");
                    }
                    for (int i5 = 0; i5 < solveEquation.length; i5++) {
                        this.d[layers[i].getVi() + i5] = solveEquation[i5];
                    }
                    i++;
                    i2 = i3;
                }
                short[] multiplyMatrix = this.e.multiplyMatrix(((RainbowPrivateKeyParameters) this.b).getInvA2(), this.e.addVect(((RainbowPrivateKeyParameters) this.b).getB2(), this.d));
                for (int i6 = 0; i6 < bArr2.length; i6++) {
                    bArr2[i6] = (byte) multiplyMatrix[i6];
                }
                z = true;
                continue;
            } catch (Exception unused) {
            }
        } while (!z);
        return bArr2;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        RainbowKeyParameters rainbowKeyParameters;
        if (!z) {
            rainbowKeyParameters = (RainbowPublicKeyParameters) cipherParameters;
        } else if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.c = parametersWithRandom.getRandom();
            this.b = (RainbowPrivateKeyParameters) parametersWithRandom.getParameters();
            this.a = this.b.getDocLength();
        } else {
            this.c = new SecureRandom();
            rainbowKeyParameters = (RainbowPrivateKeyParameters) cipherParameters;
        }
        this.b = rainbowKeyParameters;
        this.a = this.b.getDocLength();
    }

    public boolean verifySignature(byte[] bArr, byte[] bArr2) {
        short[] sArr = new short[bArr2.length];
        for (int i = 0; i < bArr2.length; i++) {
            sArr[i] = (short) (((short) bArr2[i]) & 255);
        }
        short[] a2 = a(bArr);
        short[] a3 = a(sArr);
        if (a2.length != a3.length) {
            return false;
        }
        boolean z = true;
        for (int i2 = 0; i2 < a2.length; i2++) {
            z = z && a2[i2] == a3[i2];
        }
        return z;
    }
}
