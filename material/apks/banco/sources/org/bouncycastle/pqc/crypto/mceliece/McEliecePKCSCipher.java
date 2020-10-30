package org.bouncycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.MessageEncryptor;
import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.bouncycastle.pqc.math.linearalgebra.GF2Vector;
import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.GoppaCode;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.bouncycastle.pqc.math.linearalgebra.Vector;

public class McEliecePKCSCipher implements MessageEncryptor {
    public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.1";
    McElieceKeyParameters a;
    private SecureRandom b;
    private int c;
    public int cipherTextSize;
    private int d;
    private int e;
    public int maxPlainTextSize;

    private GF2Vector a(byte[] bArr) {
        byte[] bArr2 = new byte[(this.maxPlainTextSize + ((this.d & 7) != 0 ? 1 : 0))];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        bArr2[bArr.length] = 1;
        return GF2Vector.OS2VP(this.d, bArr2);
    }

    private byte[] a(GF2Vector gF2Vector) {
        byte[] encoded = gF2Vector.getEncoded();
        int length = encoded.length - 1;
        while (length >= 0 && encoded[length] == 0) {
            length--;
        }
        if (encoded[length] != 1) {
            throw new Exception("Bad Padding: invalid ciphertext");
        }
        byte[] bArr = new byte[length];
        System.arraycopy(encoded, 0, bArr, 0, length);
        return bArr;
    }

    public int getKeySize(McElieceKeyParameters mcElieceKeyParameters) {
        if (mcElieceKeyParameters instanceof McEliecePublicKeyParameters) {
            return ((McEliecePublicKeyParameters) mcElieceKeyParameters).getN();
        }
        if (mcElieceKeyParameters instanceof McEliecePrivateKeyParameters) {
            return ((McEliecePrivateKeyParameters) mcElieceKeyParameters).getN();
        }
        throw new IllegalArgumentException("unsupported type");
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (z) {
            if (cipherParameters instanceof ParametersWithRandom) {
                ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
                this.b = parametersWithRandom.getRandom();
                this.a = (McEliecePublicKeyParameters) parametersWithRandom.getParameters();
            } else {
                this.b = new SecureRandom();
                this.a = (McEliecePublicKeyParameters) cipherParameters;
            }
            initCipherEncrypt((McEliecePublicKeyParameters) this.a);
            return;
        }
        this.a = (McEliecePrivateKeyParameters) cipherParameters;
        initCipherDecrypt((McEliecePrivateKeyParameters) this.a);
    }

    public void initCipherDecrypt(McEliecePrivateKeyParameters mcEliecePrivateKeyParameters) {
        this.c = mcEliecePrivateKeyParameters.getN();
        this.d = mcEliecePrivateKeyParameters.getK();
        this.maxPlainTextSize = this.d >> 3;
        this.cipherTextSize = this.c >> 3;
    }

    public void initCipherEncrypt(McEliecePublicKeyParameters mcEliecePublicKeyParameters) {
        this.b = this.b != null ? this.b : new SecureRandom();
        this.c = mcEliecePublicKeyParameters.getN();
        this.d = mcEliecePublicKeyParameters.getK();
        this.e = mcEliecePublicKeyParameters.getT();
        this.cipherTextSize = this.c >> 3;
        this.maxPlainTextSize = this.d >> 3;
    }

    public byte[] messageDecrypt(byte[] bArr) {
        GF2Vector OS2VP = GF2Vector.OS2VP(this.c, bArr);
        McEliecePrivateKeyParameters mcEliecePrivateKeyParameters = (McEliecePrivateKeyParameters) this.a;
        GF2mField field = mcEliecePrivateKeyParameters.getField();
        PolynomialGF2mSmallM goppaPoly = mcEliecePrivateKeyParameters.getGoppaPoly();
        GF2Matrix sInv = mcEliecePrivateKeyParameters.getSInv();
        Permutation p1 = mcEliecePrivateKeyParameters.getP1();
        Permutation p2 = mcEliecePrivateKeyParameters.getP2();
        GF2Matrix h = mcEliecePrivateKeyParameters.getH();
        PolynomialGF2mSmallM[] qInv = mcEliecePrivateKeyParameters.getQInv();
        Permutation rightMultiply = p1.rightMultiply(p2);
        GF2Vector gF2Vector = (GF2Vector) OS2VP.multiply(rightMultiply.computeInverse());
        GF2Vector syndromeDecode = GoppaCode.syndromeDecode((GF2Vector) h.rightMultiply((Vector) gF2Vector), field, goppaPoly, qInv);
        GF2Vector gF2Vector2 = (GF2Vector) ((GF2Vector) gF2Vector.add(syndromeDecode)).multiply(p1);
        GF2Vector gF2Vector3 = (GF2Vector) syndromeDecode.multiply(rightMultiply);
        return a((GF2Vector) sInv.leftMultiply((Vector) gF2Vector2.extractRightVector(this.d)));
    }

    public byte[] messageEncrypt(byte[] bArr) {
        GF2Vector a2 = a(bArr);
        return ((GF2Vector) ((McEliecePublicKeyParameters) this.a).getG().leftMultiply((Vector) a2).add(new GF2Vector(this.c, this.e, this.b))).getEncoded();
    }
}
