package org.bouncycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.GoppaCode;
import org.bouncycastle.pqc.math.linearalgebra.GoppaCode.MaMaPe;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2m;

public class McElieceKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private McElieceKeyGenerationParameters a;
    private int b;
    private int c;
    private int d;
    private int e;
    private SecureRandom f;
    private boolean g = false;

    private void a() {
        a(new McElieceKeyGenerationParameters(new SecureRandom(), new McElieceParameters()));
    }

    private void a(KeyGenerationParameters keyGenerationParameters) {
        this.a = (McElieceKeyGenerationParameters) keyGenerationParameters;
        this.f = new SecureRandom();
        this.b = this.a.getParameters().getM();
        this.c = this.a.getParameters().getN();
        this.d = this.a.getParameters().getT();
        this.e = this.a.getParameters().getFieldPoly();
        this.g = true;
    }

    private AsymmetricCipherKeyPair b() {
        if (!this.g) {
            a();
        }
        GF2mField gF2mField = new GF2mField(this.b, this.e);
        PolynomialGF2mSmallM polynomialGF2mSmallM = new PolynomialGF2mSmallM(gF2mField, this.d, 'I', this.f);
        PolynomialGF2mSmallM[] squareRootMatrix = new PolynomialRingGF2m(gF2mField, polynomialGF2mSmallM).getSquareRootMatrix();
        GF2Matrix createCanonicalCheckMatrix = GoppaCode.createCanonicalCheckMatrix(gF2mField, polynomialGF2mSmallM);
        MaMaPe computeSystematicForm = GoppaCode.computeSystematicForm(createCanonicalCheckMatrix, this.f);
        GF2Matrix secondMatrix = computeSystematicForm.getSecondMatrix();
        Permutation permutation = computeSystematicForm.getPermutation();
        GF2Matrix gF2Matrix = (GF2Matrix) secondMatrix.computeTranspose();
        GF2Matrix extendLeftCompactForm = gF2Matrix.extendLeftCompactForm();
        int numRows = gF2Matrix.getNumRows();
        GF2Matrix[] createRandomRegularMatrixAndItsInverse = GF2Matrix.createRandomRegularMatrixAndItsInverse(numRows, this.f);
        Permutation permutation2 = new Permutation(this.c, this.f);
        GF2Matrix gF2Matrix2 = (GF2Matrix) ((GF2Matrix) createRandomRegularMatrixAndItsInverse[0].rightMultiply((Matrix) extendLeftCompactForm)).rightMultiply(permutation2);
        McEliecePublicKeyParameters mcEliecePublicKeyParameters = new McEliecePublicKeyParameters("1.3.6.1.4.1.8301.3.1.3.4.1", this.c, this.d, gF2Matrix2, this.a.getParameters());
        McEliecePublicKeyParameters mcEliecePublicKeyParameters2 = mcEliecePublicKeyParameters;
        McEliecePrivateKeyParameters mcEliecePrivateKeyParameters = new McEliecePrivateKeyParameters("1.3.6.1.4.1.8301.3.1.3.4.1", this.c, numRows, gF2mField, polynomialGF2mSmallM, createRandomRegularMatrixAndItsInverse[1], permutation, permutation2, createCanonicalCheckMatrix, squareRootMatrix, this.a.getParameters());
        return new AsymmetricCipherKeyPair((AsymmetricKeyParameter) mcEliecePublicKeyParameters2, (AsymmetricKeyParameter) mcEliecePrivateKeyParameters);
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        return b();
    }

    public void init(KeyGenerationParameters keyGenerationParameters) {
        a(keyGenerationParameters);
    }
}
