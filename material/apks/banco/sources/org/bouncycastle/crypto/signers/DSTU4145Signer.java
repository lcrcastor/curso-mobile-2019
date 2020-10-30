package org.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DSA;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECMultiplier;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.util.Arrays;

public class DSTU4145Signer implements DSA {
    private static final BigInteger a = BigInteger.valueOf(1);
    private ECKeyParameters b;
    private SecureRandom c;

    private static BigInteger a(BigInteger bigInteger, int i) {
        return bigInteger.bitLength() > i ? bigInteger.mod(a.shiftLeft(i)) : bigInteger;
    }

    private static BigInteger a(BigInteger bigInteger, SecureRandom secureRandom) {
        return new BigInteger(bigInteger.bitLength() - 1, secureRandom);
    }

    private static BigInteger a(BigInteger bigInteger, ECFieldElement eCFieldElement) {
        return a(eCFieldElement.toBigInteger(), bigInteger.bitLength() - 1);
    }

    private static ECFieldElement a(ECCurve eCCurve, byte[] bArr) {
        return eCCurve.fromBigInteger(a(new BigInteger(1, Arrays.reverse(bArr)), eCCurve.getFieldSize()));
    }

    /* access modifiers changed from: protected */
    public ECMultiplier createBasePointMultiplier() {
        return new FixedPointCombMultiplier();
    }

    public BigInteger[] generateSignature(byte[] bArr) {
        ECDomainParameters parameters = this.b.getParameters();
        ECCurve curve = parameters.getCurve();
        ECFieldElement a2 = a(curve, bArr);
        if (a2.isZero()) {
            a2 = curve.fromBigInteger(a);
        }
        BigInteger n = parameters.getN();
        BigInteger d = ((ECPrivateKeyParameters) this.b).getD();
        ECMultiplier createBasePointMultiplier = createBasePointMultiplier();
        while (true) {
            BigInteger a3 = a(n, this.c);
            ECFieldElement affineXCoord = createBasePointMultiplier.multiply(parameters.getG(), a3).normalize().getAffineXCoord();
            if (!affineXCoord.isZero()) {
                BigInteger a4 = a(n, a2.multiply(affineXCoord));
                if (a4.signum() != 0) {
                    BigInteger mod = a4.multiply(d).add(a3).mod(n);
                    if (mod.signum() != 0) {
                        return new BigInteger[]{a4, mod};
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        ECKeyParameters eCKeyParameters;
        if (z) {
            if (cipherParameters instanceof ParametersWithRandom) {
                ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
                this.c = parametersWithRandom.getRandom();
                cipherParameters = parametersWithRandom.getParameters();
            } else {
                this.c = new SecureRandom();
            }
            eCKeyParameters = (ECPrivateKeyParameters) cipherParameters;
        } else {
            eCKeyParameters = (ECPublicKeyParameters) cipherParameters;
        }
        this.b = eCKeyParameters;
    }

    public boolean verifySignature(byte[] bArr, BigInteger bigInteger, BigInteger bigInteger2) {
        boolean z = false;
        if (bigInteger.signum() > 0) {
            if (bigInteger2.signum() <= 0) {
                return false;
            }
            ECDomainParameters parameters = this.b.getParameters();
            BigInteger n = parameters.getN();
            if (bigInteger.compareTo(n) < 0) {
                if (bigInteger2.compareTo(n) >= 0) {
                    return false;
                }
                ECCurve curve = parameters.getCurve();
                ECFieldElement a2 = a(curve, bArr);
                if (a2.isZero()) {
                    a2 = curve.fromBigInteger(a);
                }
                ECPoint normalize = ECAlgorithms.sumOfTwoMultiplies(parameters.getG(), bigInteger2, ((ECPublicKeyParameters) this.b).getQ(), bigInteger).normalize();
                if (normalize.isInfinity()) {
                    return false;
                }
                if (a(n, a2.multiply(normalize.getAffineXCoord())).compareTo(bigInteger) == 0) {
                    z = true;
                }
            }
        }
        return z;
    }
}
