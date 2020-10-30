package org.bouncycastle.pqc.crypto.gmss;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.MessageSigner;
import org.bouncycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.bouncycastle.pqc.crypto.gmss.util.GMSSUtil;
import org.bouncycastle.pqc.crypto.gmss.util.WinternitzOTSVerify;
import org.bouncycastle.pqc.crypto.gmss.util.WinternitzOTSignature;
import org.bouncycastle.util.Arrays;

public class GMSSSigner implements MessageSigner {
    GMSSKeyParameters a;
    private GMSSUtil b = new GMSSUtil();
    private byte[] c;
    private Digest d;
    private int e;
    private int f;
    private Digest g;
    private WinternitzOTSignature h;
    private GMSSDigestProvider i;
    private int[] j;
    private byte[][][] k;
    private byte[][] l;
    private GMSSParameters m;
    private GMSSRandom n;
    private SecureRandom o;

    public GMSSSigner(GMSSDigestProvider gMSSDigestProvider) {
        this.i = gMSSDigestProvider;
        this.d = gMSSDigestProvider.get();
        this.g = this.d;
        this.e = this.d.getDigestSize();
        this.n = new GMSSRandom(this.d);
    }

    private void a() {
        this.d.reset();
        GMSSPrivateKeyParameters gMSSPrivateKeyParameters = (GMSSPrivateKeyParameters) this.a;
        if (gMSSPrivateKeyParameters.isUsed()) {
            throw new IllegalStateException("Private key already used");
        } else if (gMSSPrivateKeyParameters.getIndex(0) >= gMSSPrivateKeyParameters.getNumLeafs(0)) {
            throw new IllegalStateException("No more signatures can be generated");
        } else {
            this.m = gMSSPrivateKeyParameters.getParameters();
            this.f = this.m.getNumOfLayers();
            byte[] bArr = new byte[this.e];
            byte[] bArr2 = new byte[this.e];
            System.arraycopy(gMSSPrivateKeyParameters.getCurrentSeeds()[this.f - 1], 0, bArr2, 0, this.e);
            this.h = new WinternitzOTSignature(this.n.nextSeed(bArr2), this.i.get(), this.m.getWinternitzParameter()[this.f - 1]);
            byte[][][] currentAuthPaths = gMSSPrivateKeyParameters.getCurrentAuthPaths();
            this.k = new byte[this.f][][];
            for (int i2 = 0; i2 < this.f; i2++) {
                this.k[i2] = (byte[][]) Array.newInstance(byte.class, new int[]{currentAuthPaths[i2].length, this.e});
                for (int i3 = 0; i3 < currentAuthPaths[i2].length; i3++) {
                    System.arraycopy(currentAuthPaths[i2][i3], 0, this.k[i2][i3], 0, this.e);
                }
            }
            this.j = new int[this.f];
            System.arraycopy(gMSSPrivateKeyParameters.getIndex(), 0, this.j, 0, this.f);
            this.l = new byte[(this.f - 1)][];
            for (int i4 = 0; i4 < this.f - 1; i4++) {
                byte[] subtreeRootSig = gMSSPrivateKeyParameters.getSubtreeRootSig(i4);
                this.l[i4] = new byte[subtreeRootSig.length];
                System.arraycopy(subtreeRootSig, 0, this.l[i4], 0, subtreeRootSig.length);
            }
            gMSSPrivateKeyParameters.markUsed();
        }
    }

    private void b() {
        this.d.reset();
        GMSSPublicKeyParameters gMSSPublicKeyParameters = (GMSSPublicKeyParameters) this.a;
        this.c = gMSSPublicKeyParameters.getPublicKey();
        this.m = gMSSPublicKeyParameters.getParameters();
        this.f = this.m.getNumOfLayers();
    }

    public byte[] generateSignature(byte[] bArr) {
        byte[] bArr2 = new byte[this.e];
        byte[] signature = this.h.getSignature(bArr);
        byte[] concatenateArray = this.b.concatenateArray(this.k[this.f - 1]);
        byte[] intToBytesLittleEndian = this.b.intToBytesLittleEndian(this.j[this.f - 1]);
        byte[] bArr3 = new byte[(intToBytesLittleEndian.length + signature.length + concatenateArray.length)];
        System.arraycopy(intToBytesLittleEndian, 0, bArr3, 0, intToBytesLittleEndian.length);
        System.arraycopy(signature, 0, bArr3, intToBytesLittleEndian.length, signature.length);
        System.arraycopy(concatenateArray, 0, bArr3, intToBytesLittleEndian.length + signature.length, concatenateArray.length);
        byte[] bArr4 = new byte[0];
        for (int i2 = (this.f - 1) - 1; i2 >= 0; i2--) {
            byte[] concatenateArray2 = this.b.concatenateArray(this.k[i2]);
            byte[] intToBytesLittleEndian2 = this.b.intToBytesLittleEndian(this.j[i2]);
            byte[] bArr5 = new byte[bArr4.length];
            System.arraycopy(bArr4, 0, bArr5, 0, bArr4.length);
            bArr4 = new byte[(bArr5.length + intToBytesLittleEndian2.length + this.l[i2].length + concatenateArray2.length)];
            System.arraycopy(bArr5, 0, bArr4, 0, bArr5.length);
            System.arraycopy(intToBytesLittleEndian2, 0, bArr4, bArr5.length, intToBytesLittleEndian2.length);
            System.arraycopy(this.l[i2], 0, bArr4, bArr5.length + intToBytesLittleEndian2.length, this.l[i2].length);
            System.arraycopy(concatenateArray2, 0, bArr4, bArr5.length + intToBytesLittleEndian2.length + this.l[i2].length, concatenateArray2.length);
        }
        byte[] bArr6 = new byte[(bArr3.length + bArr4.length)];
        System.arraycopy(bArr3, 0, bArr6, 0, bArr3.length);
        System.arraycopy(bArr4, 0, bArr6, bArr3.length, bArr4.length);
        return bArr6;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (z) {
            if (cipherParameters instanceof ParametersWithRandom) {
                ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
                this.o = parametersWithRandom.getRandom();
                this.a = (GMSSPrivateKeyParameters) parametersWithRandom.getParameters();
            } else {
                this.o = new SecureRandom();
                this.a = (GMSSPrivateKeyParameters) cipherParameters;
            }
            a();
            return;
        }
        this.a = (GMSSPublicKeyParameters) cipherParameters;
        b();
    }

    public boolean verifySignature(byte[] bArr, byte[] bArr2) {
        this.g.reset();
        int i2 = this.f - 1;
        int i3 = 0;
        while (i2 >= 0) {
            WinternitzOTSVerify winternitzOTSVerify = new WinternitzOTSVerify(this.i.get(), this.m.getWinternitzParameter()[i2]);
            int signatureLength = winternitzOTSVerify.getSignatureLength();
            int bytesToIntLittleEndian = this.b.bytesToIntLittleEndian(bArr2, i3);
            int i4 = i3 + 4;
            byte[] bArr3 = new byte[signatureLength];
            System.arraycopy(bArr2, i4, bArr3, 0, signatureLength);
            int i5 = i4 + signatureLength;
            byte[] Verify = winternitzOTSVerify.Verify(bArr, bArr3);
            if (Verify == null) {
                System.err.println("OTS Public Key is null in GMSSSignature.verify");
                return false;
            }
            byte[][] bArr4 = (byte[][]) Array.newInstance(byte.class, new int[]{this.m.getHeightOfTrees()[i2], this.e});
            int i6 = i5;
            for (byte[] arraycopy : bArr4) {
                System.arraycopy(bArr2, i6, arraycopy, 0, this.e);
                i6 += this.e;
            }
            byte[] bArr5 = new byte[this.e];
            int length = (1 << bArr4.length) + bytesToIntLittleEndian;
            byte[] bArr6 = Verify;
            for (int i7 = 0; i7 < bArr4.length; i7++) {
                byte[] bArr7 = new byte[(this.e << 1)];
                if (length % 2 == 0) {
                    System.arraycopy(bArr6, 0, bArr7, 0, this.e);
                    System.arraycopy(bArr4[i7], 0, bArr7, this.e, this.e);
                } else {
                    System.arraycopy(bArr4[i7], 0, bArr7, 0, this.e);
                    System.arraycopy(bArr6, 0, bArr7, this.e, bArr6.length);
                    length--;
                }
                length /= 2;
                this.d.update(bArr7, 0, bArr7.length);
                bArr6 = new byte[this.d.getDigestSize()];
                this.d.doFinal(bArr6, 0);
            }
            i2--;
            i3 = i6;
            bArr = bArr6;
        }
        return Arrays.areEqual(this.c, bArr);
    }
}
