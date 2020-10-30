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
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2m;

public class McElieceCCA2KeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.2";
    private McElieceCCA2KeyGenerationParameters a;
    private int b;
    private int c;
    private int d;
    private int e;
    private SecureRandom f;
    private boolean g = false;

    private void a() {
        init(new McElieceCCA2KeyGenerationParameters(new SecureRandom(), new McElieceCCA2Parameters()));
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
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
        int numRows = gF2Matrix.getNumRows();
        McElieceCCA2PublicKeyParameters mcElieceCCA2PublicKeyParameters = new McElieceCCA2PublicKeyParameters("1.3.6.1.4.1.8301.3.1.3.4.2", this.c, this.d, gF2Matrix, this.a.getParameters());
        McElieceCCA2PrivateKeyParameters mcElieceCCA2PrivateKeyParameters = new McElieceCCA2PrivateKeyParameters("1.3.6.1.4.1.8301.3.1.3.4.2", this.c, numRows, gF2mField, polynomialGF2mSmallM, permutation, createCanonicalCheckMatrix, squareRootMatrix, this.a.getParameters());
        return new AsymmetricCipherKeyPair((AsymmetricKeyParameter) mcElieceCCA2PublicKeyParameters, (AsymmetricKeyParameter) mcElieceCCA2PrivateKeyParameters);
    }

    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.a = (McElieceCCA2KeyGenerationParameters) keyGenerationParameters;
        this.f = new SecureRandom();
        this.b = this.a.getParameters().getM();
        this.c = this.a.getParameters().getN();
        this.d = this.a.getParameters().getT();
        this.e = this.a.getParameters().getFieldPoly();
        this.g = true;
    }
}
