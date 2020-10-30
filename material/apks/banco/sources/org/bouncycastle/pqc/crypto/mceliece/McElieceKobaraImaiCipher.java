package org.bouncycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.prng.DigestRandomGenerator;
import org.bouncycastle.pqc.crypto.MessageEncryptor;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.bouncycastle.pqc.math.linearalgebra.GF2Vector;
import org.bouncycastle.pqc.math.linearalgebra.IntegerFunctions;

public class McElieceKobaraImaiCipher implements MessageEncryptor {
    public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.2.3";
    public static final byte[] PUBLIC_CONSTANT = "a predetermined public constant".getBytes();
    McElieceCCA2KeyParameters a;
    private Digest b;
    private SecureRandom c;
    private int d;
    private int e;
    private int f;

    private void a(McElieceCCA2PublicKeyParameters mcElieceCCA2PublicKeyParameters) {
        this.b = mcElieceCCA2PublicKeyParameters.getParameters().getDigest();
        this.d = mcElieceCCA2PublicKeyParameters.getN();
        this.e = mcElieceCCA2PublicKeyParameters.getK();
        this.f = mcElieceCCA2PublicKeyParameters.getT();
    }

    public int getKeySize(McElieceCCA2KeyParameters mcElieceCCA2KeyParameters) {
        if (mcElieceCCA2KeyParameters instanceof McElieceCCA2PublicKeyParameters) {
            return ((McElieceCCA2PublicKeyParameters) mcElieceCCA2KeyParameters).getN();
        }
        if (mcElieceCCA2KeyParameters instanceof McElieceCCA2PrivateKeyParameters) {
            return ((McElieceCCA2PrivateKeyParameters) mcElieceCCA2KeyParameters).getN();
        }
        throw new IllegalArgumentException("unsupported type");
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (z) {
            if (cipherParameters instanceof ParametersWithRandom) {
                ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
                this.c = parametersWithRandom.getRandom();
                this.a = (McElieceCCA2PublicKeyParameters) parametersWithRandom.getParameters();
            } else {
                this.c = new SecureRandom();
                this.a = (McElieceCCA2PublicKeyParameters) cipherParameters;
            }
            a((McElieceCCA2PublicKeyParameters) this.a);
            return;
        }
        this.a = (McElieceCCA2PrivateKeyParameters) cipherParameters;
        initCipherDecrypt((McElieceCCA2PrivateKeyParameters) this.a);
    }

    public void initCipherDecrypt(McElieceCCA2PrivateKeyParameters mcElieceCCA2PrivateKeyParameters) {
        this.b = mcElieceCCA2PrivateKeyParameters.getParameters().getDigest();
        this.d = mcElieceCCA2PrivateKeyParameters.getN();
        this.e = mcElieceCCA2PrivateKeyParameters.getK();
        this.f = mcElieceCCA2PrivateKeyParameters.getT();
    }

    public byte[] messageDecrypt(byte[] bArr) {
        byte[] bArr2;
        int i = this.d >> 3;
        if (bArr.length < i) {
            throw new Exception("Bad Padding: Ciphertext too short.");
        }
        int digestSize = this.b.getDigestSize();
        int i2 = this.e >> 3;
        int length = bArr.length - i;
        if (length > 0) {
            byte[][] split = ByteUtils.split(bArr, length);
            bArr2 = split[0];
            bArr = split[1];
        } else {
            bArr2 = new byte[0];
        }
        GF2Vector[] decryptionPrimitive = McElieceCCA2Primitives.decryptionPrimitive((McElieceCCA2PrivateKeyParameters) this.a, GF2Vector.OS2VP(this.d, bArr));
        byte[] encoded = decryptionPrimitive[0].getEncoded();
        GF2Vector gF2Vector = decryptionPrimitive[1];
        if (encoded.length > i2) {
            encoded = ByteUtils.subArray(encoded, 0, i2);
        }
        byte[] concatenate = ByteUtils.concatenate(ByteUtils.concatenate(bArr2, Conversions.a(this.d, this.f, gF2Vector)), encoded);
        int length2 = concatenate.length - digestSize;
        byte[][] split2 = ByteUtils.split(concatenate, digestSize);
        byte[] bArr3 = split2[0];
        byte[] bArr4 = split2[1];
        byte[] bArr5 = new byte[this.b.getDigestSize()];
        this.b.update(bArr4, 0, bArr4.length);
        this.b.doFinal(bArr5, 0);
        for (int i3 = digestSize - 1; i3 >= 0; i3--) {
            bArr5[i3] = (byte) (bArr5[i3] ^ bArr3[i3]);
        }
        DigestRandomGenerator digestRandomGenerator = new DigestRandomGenerator(new SHA1Digest());
        digestRandomGenerator.addSeedMaterial(bArr5);
        byte[] bArr6 = new byte[length2];
        digestRandomGenerator.nextBytes(bArr6);
        for (int i4 = length2 - 1; i4 >= 0; i4--) {
            bArr6[i4] = (byte) (bArr6[i4] ^ bArr4[i4]);
        }
        if (bArr6.length < length2) {
            throw new Exception("Bad Padding: invalid ciphertext");
        }
        byte[][] split3 = ByteUtils.split(bArr6, length2 - PUBLIC_CONSTANT.length);
        byte[] bArr7 = split3[0];
        if (ByteUtils.equals(split3[1], PUBLIC_CONSTANT)) {
            return bArr7;
        }
        throw new Exception("Bad Padding: invalid ciphertext");
    }

    public byte[] messageEncrypt(byte[] bArr) {
        int digestSize = this.b.getDigestSize();
        int i = this.e >> 3;
        int bitLength = (IntegerFunctions.binomial(this.d, this.f).bitLength() - 1) >> 3;
        int length = ((i + bitLength) - digestSize) - PUBLIC_CONSTANT.length;
        if (bArr.length > length) {
            length = bArr.length;
        }
        int length2 = PUBLIC_CONSTANT.length + length;
        int i2 = ((length2 + digestSize) - i) - bitLength;
        byte[] bArr2 = new byte[length2];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        System.arraycopy(PUBLIC_CONSTANT, 0, bArr2, length, PUBLIC_CONSTANT.length);
        byte[] bArr3 = new byte[digestSize];
        this.c.nextBytes(bArr3);
        DigestRandomGenerator digestRandomGenerator = new DigestRandomGenerator(new SHA1Digest());
        digestRandomGenerator.addSeedMaterial(bArr3);
        byte[] bArr4 = new byte[length2];
        digestRandomGenerator.nextBytes(bArr4);
        for (int i3 = length2 - 1; i3 >= 0; i3--) {
            bArr4[i3] = (byte) (bArr4[i3] ^ bArr2[i3]);
        }
        byte[] bArr5 = new byte[this.b.getDigestSize()];
        this.b.update(bArr4, 0, bArr4.length);
        this.b.doFinal(bArr5, 0);
        for (int i4 = digestSize - 1; i4 >= 0; i4--) {
            bArr5[i4] = (byte) (bArr5[i4] ^ bArr3[i4]);
        }
        byte[] concatenate = ByteUtils.concatenate(bArr5, bArr4);
        byte[] bArr6 = new byte[0];
        if (i2 > 0) {
            bArr6 = new byte[i2];
            System.arraycopy(concatenate, 0, bArr6, 0, i2);
        }
        byte[] bArr7 = new byte[bitLength];
        System.arraycopy(concatenate, i2, bArr7, 0, bitLength);
        byte[] bArr8 = new byte[i];
        System.arraycopy(concatenate, bitLength + i2, bArr8, 0, i);
        byte[] encoded = McElieceCCA2Primitives.encryptionPrimitive((McElieceCCA2PublicKeyParameters) this.a, GF2Vector.OS2VP(this.e, bArr8), Conversions.a(this.d, this.f, bArr7)).getEncoded();
        return i2 > 0 ? ByteUtils.concatenate(bArr6, encoded) : encoded;
    }
}
