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

public class McEliecePointchevalCipher implements MessageEncryptor {
    public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.2.2";
    McElieceCCA2KeyParameters a;
    private Digest b;
    private SecureRandom c;
    private int d;
    private int e;
    private int f;

    /* access modifiers changed from: protected */
    public int decryptOutputSize(int i) {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int encryptOutputSize(int i) {
        return 0;
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
            initCipherEncrypt((McElieceCCA2PublicKeyParameters) this.a);
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

    public void initCipherEncrypt(McElieceCCA2PublicKeyParameters mcElieceCCA2PublicKeyParameters) {
        this.c = this.c != null ? this.c : new SecureRandom();
        this.b = mcElieceCCA2PublicKeyParameters.getParameters().getDigest();
        this.d = mcElieceCCA2PublicKeyParameters.getN();
        this.e = mcElieceCCA2PublicKeyParameters.getK();
        this.f = mcElieceCCA2PublicKeyParameters.getT();
    }

    public byte[] messageDecrypt(byte[] bArr) {
        int i = (this.d + 7) >> 3;
        int length = bArr.length - i;
        byte[][] split = ByteUtils.split(bArr, i);
        byte[] bArr2 = split[0];
        byte[] bArr3 = split[1];
        GF2Vector[] decryptionPrimitive = McElieceCCA2Primitives.decryptionPrimitive((McElieceCCA2PrivateKeyParameters) this.a, GF2Vector.OS2VP(this.d, bArr2));
        byte[] encoded = decryptionPrimitive[0].getEncoded();
        GF2Vector gF2Vector = decryptionPrimitive[1];
        DigestRandomGenerator digestRandomGenerator = new DigestRandomGenerator(new SHA1Digest());
        digestRandomGenerator.addSeedMaterial(encoded);
        byte[] bArr4 = new byte[length];
        digestRandomGenerator.nextBytes(bArr4);
        for (int i2 = 0; i2 < length; i2++) {
            bArr4[i2] = (byte) (bArr4[i2] ^ bArr3[i2]);
        }
        this.b.update(bArr4, 0, bArr4.length);
        byte[] bArr5 = new byte[this.b.getDigestSize()];
        this.b.doFinal(bArr5, 0);
        if (Conversions.a(this.d, this.f, bArr5).equals(gF2Vector)) {
            return ByteUtils.split(bArr4, length - (this.e >> 3))[0];
        }
        throw new Exception("Bad Padding: Invalid ciphertext.");
    }

    public byte[] messageEncrypt(byte[] bArr) {
        int i = this.e >> 3;
        byte[] bArr2 = new byte[i];
        this.c.nextBytes(bArr2);
        GF2Vector gF2Vector = new GF2Vector(this.e, this.c);
        byte[] encoded = gF2Vector.getEncoded();
        byte[] concatenate = ByteUtils.concatenate(bArr, bArr2);
        this.b.update(concatenate, 0, concatenate.length);
        byte[] bArr3 = new byte[this.b.getDigestSize()];
        this.b.doFinal(bArr3, 0);
        byte[] encoded2 = McElieceCCA2Primitives.encryptionPrimitive((McElieceCCA2PublicKeyParameters) this.a, gF2Vector, Conversions.a(this.d, this.f, bArr3)).getEncoded();
        DigestRandomGenerator digestRandomGenerator = new DigestRandomGenerator(new SHA1Digest());
        digestRandomGenerator.addSeedMaterial(encoded);
        byte[] bArr4 = new byte[(bArr.length + i)];
        digestRandomGenerator.nextBytes(bArr4);
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr4[i2] = (byte) (bArr4[i2] ^ bArr[i2]);
        }
        for (int i3 = 0; i3 < i; i3++) {
            int length = bArr.length + i3;
            bArr4[length] = (byte) (bArr4[length] ^ bArr2[i3]);
        }
        return ByteUtils.concatenate(encoded2, bArr4);
    }
}
