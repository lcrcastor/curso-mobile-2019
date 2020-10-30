package org.bouncycastle.crypto.kems;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.KeyEncapsulation;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.KDFParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECMultiplier;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

public class ECIESKeyEncapsulation implements KeyEncapsulation {
    private static final BigInteger a = BigInteger.valueOf(1);
    private DerivationFunction b;
    private SecureRandom c;
    private ECKeyParameters d;
    private boolean e;
    private boolean f;
    private boolean g;

    public ECIESKeyEncapsulation(DerivationFunction derivationFunction, SecureRandom secureRandom) {
        this.b = derivationFunction;
        this.c = secureRandom;
        this.e = false;
        this.f = false;
        this.g = false;
    }

    public ECIESKeyEncapsulation(DerivationFunction derivationFunction, SecureRandom secureRandom, boolean z, boolean z2, boolean z3) {
        this.b = derivationFunction;
        this.c = secureRandom;
        this.e = z;
        this.f = z2;
        this.g = z3;
    }

    /* access modifiers changed from: protected */
    public ECMultiplier createBasePointMultiplier() {
        return new FixedPointCombMultiplier();
    }

    public CipherParameters decrypt(byte[] bArr, int i) {
        return decrypt(bArr, 0, bArr.length, i);
    }

    public CipherParameters decrypt(byte[] bArr, int i, int i2, int i3) {
        if (!(this.d instanceof ECPrivateKeyParameters)) {
            throw new IllegalArgumentException("Private key required for encryption");
        }
        ECPrivateKeyParameters eCPrivateKeyParameters = (ECPrivateKeyParameters) this.d;
        ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
        ECCurve curve = parameters.getCurve();
        BigInteger n = parameters.getN();
        BigInteger h = parameters.getH();
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        ECPoint decodePoint = curve.decodePoint(bArr2);
        if (this.e || this.f) {
            decodePoint = decodePoint.multiply(h);
        }
        BigInteger d2 = eCPrivateKeyParameters.getD();
        if (this.e) {
            d2 = d2.multiply(h.modInverse(n)).mod(n);
        }
        byte[] encoded = decodePoint.multiply(d2).normalize().getAffineXCoord().getEncoded();
        if (this.g) {
            encoded = Arrays.concatenate(bArr2, encoded);
        }
        this.b.init(new KDFParameters(encoded, null));
        byte[] bArr3 = new byte[i3];
        this.b.generateBytes(bArr3, 0, bArr3.length);
        return new KeyParameter(bArr3);
    }

    public CipherParameters encrypt(byte[] bArr, int i) {
        return encrypt(bArr, 0, i);
    }

    public CipherParameters encrypt(byte[] bArr, int i, int i2) {
        if (!(this.d instanceof ECPublicKeyParameters)) {
            throw new IllegalArgumentException("Public key required for encryption");
        }
        ECPublicKeyParameters eCPublicKeyParameters = (ECPublicKeyParameters) this.d;
        ECDomainParameters parameters = eCPublicKeyParameters.getParameters();
        ECCurve curve = parameters.getCurve();
        BigInteger n = parameters.getN();
        BigInteger h = parameters.getH();
        BigInteger createRandomInRange = BigIntegers.createRandomInRange(a, n, this.c);
        ECPoint[] eCPointArr = {createBasePointMultiplier().multiply(parameters.getG(), createRandomInRange), eCPublicKeyParameters.getQ().multiply(this.e ? createRandomInRange.multiply(h).mod(n) : createRandomInRange)};
        curve.normalizeAll(eCPointArr);
        ECPoint eCPoint = eCPointArr[0];
        ECPoint eCPoint2 = eCPointArr[1];
        byte[] encoded = eCPoint.getEncoded();
        System.arraycopy(encoded, 0, bArr, i, encoded.length);
        byte[] encoded2 = eCPoint2.getAffineXCoord().getEncoded();
        if (this.g) {
            encoded2 = Arrays.concatenate(encoded, encoded2);
        }
        this.b.init(new KDFParameters(encoded2, null));
        byte[] bArr2 = new byte[i2];
        this.b.generateBytes(bArr2, 0, bArr2.length);
        return new KeyParameter(bArr2);
    }

    public void init(CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof ECKeyParameters)) {
            throw new IllegalArgumentException("EC key required");
        }
        this.d = (ECKeyParameters) cipherParameters;
    }
}
