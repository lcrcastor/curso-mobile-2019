package org.bouncycastle.math.ec.tools;

import com.crashlytics.android.beta.Beta;
import cz.msebera.android.httpclient.message.TokenParser;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECFieldElement.Fp;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.BigIntegers;

public class DiscoverEndomorphisms {
    private static BigInteger a(BigInteger bigInteger) {
        BigInteger shiftRight = bigInteger.shiftRight(bigInteger.bitLength() / 2);
        while (true) {
            BigInteger shiftRight2 = shiftRight.add(bigInteger.divide(shiftRight)).shiftRight(1);
            if (shiftRight2.equals(shiftRight)) {
                return shiftRight2;
            }
            shiftRight = shiftRight2;
        }
    }

    private static void a(String str) {
        X9ECParameters byName = ECNamedCurveTable.getByName(str);
        if (byName == null) {
            PrintStream printStream = System.err;
            StringBuilder sb = new StringBuilder();
            sb.append("Unknown curve: ");
            sb.append(str);
            printStream.println(sb.toString());
            return;
        }
        ECCurve curve = byName.getCurve();
        if (ECAlgorithms.isFpCurve(curve)) {
            BigInteger characteristic = curve.getField().getCharacteristic();
            if (curve.getA().isZero() && characteristic.mod(ECConstants.THREE).equals(ECConstants.ONE)) {
                PrintStream printStream2 = System.out;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Curve '");
                sb2.append(str);
                sb2.append("' has a 'GLV Type B' endomorphism with these parameters: ");
                printStream2.println(sb2.toString());
                a(byName);
            }
        }
    }

    private static void a(String str, Object obj) {
        StringBuffer stringBuffer = new StringBuffer("  ");
        stringBuffer.append(str);
        while (stringBuffer.length() < 20) {
            stringBuffer.append(TokenParser.SP);
        }
        stringBuffer.append("= ");
        stringBuffer.append(obj.toString());
        System.out.println(stringBuffer.toString());
    }

    private static void a(X9ECParameters x9ECParameters) {
        BigInteger n = x9ECParameters.getN();
        BigInteger b = b(n, ECConstants.ONE, ECConstants.ONE);
        BigInteger[] b2 = b(n, b);
        BigInteger[] bigIntegerArr = {b2[2], b2[3].negate()};
        BigInteger[] a = a(new BigInteger[]{b2[0], b2[1].negate()}, new BigInteger[]{b2[4], b2[5].negate()});
        if (!a(a, n) && a(bigIntegerArr[0], bigIntegerArr[1])) {
            BigInteger bigInteger = bigIntegerArr[0];
            BigInteger bigInteger2 = bigIntegerArr[1];
            BigInteger divide = bigInteger.add(bigInteger2.multiply(b)).divide(n);
            BigInteger[] a2 = a(new BigInteger[]{divide.abs(), bigInteger2.abs()});
            BigInteger bigInteger3 = a2[0];
            BigInteger bigInteger4 = a2[1];
            if (divide.signum() < 0) {
                bigInteger3 = bigInteger3.negate();
            }
            if (bigInteger2.signum() > 0) {
                bigInteger4 = bigInteger4.negate();
            }
            if (!divide.multiply(bigInteger3).subtract(bigInteger2.multiply(bigInteger4)).equals(ECConstants.ONE)) {
                throw new IllegalStateException();
            }
            BigInteger subtract = bigInteger4.multiply(n).subtract(bigInteger3.multiply(b));
            BigInteger negate = bigInteger3.negate();
            BigInteger negate2 = subtract.negate();
            BigInteger add = a(n.subtract(ECConstants.ONE)).add(ECConstants.ONE);
            BigInteger[] b3 = b(a(negate, add, bigInteger2), a(negate2, add, bigInteger));
            if (b3 != null) {
                for (BigInteger bigInteger5 = b3[0]; bigInteger5.compareTo(b3[1]) <= 0; bigInteger5 = bigInteger5.add(ECConstants.ONE)) {
                    BigInteger[] bigIntegerArr2 = {subtract.add(bigInteger5.multiply(bigInteger)), bigInteger3.add(bigInteger5.multiply(bigInteger2))};
                    if (c(bigIntegerArr2, a)) {
                        a = bigIntegerArr2;
                    }
                }
            }
        }
        ECPoint normalize = x9ECParameters.getG().normalize();
        ECPoint normalize2 = normalize.multiply(b).normalize();
        if (!normalize.getYCoord().equals(normalize2.getYCoord())) {
            throw new IllegalStateException("Derivation of GLV Type B parameters failed unexpectedly");
        }
        BigInteger characteristic = x9ECParameters.getCurve().getField().getCharacteristic();
        BigInteger divide2 = characteristic.divide(ECConstants.THREE);
        do {
        } while (BigIntegers.createRandomInRange(ECConstants.TWO, characteristic.subtract(ECConstants.TWO), new SecureRandom()).modPow(divide2, characteristic).equals(ECConstants.ONE));
        ECFieldElement fromBigInteger = x9ECParameters.getCurve().fromBigInteger(ECConstants.TWO.modPow(divide2, characteristic));
        if (!normalize.getXCoord().multiply(fromBigInteger).equals(normalize2.getXCoord())) {
            fromBigInteger = fromBigInteger.square();
            if (!normalize.getXCoord().multiply(fromBigInteger).equals(normalize2.getXCoord())) {
                throw new IllegalStateException("Derivation of GLV Type B parameters failed unexpectedly");
            }
        }
        BigInteger subtract2 = bigIntegerArr[0].multiply(a[1]).subtract(bigIntegerArr[1].multiply(a[0]));
        int bitLength = (n.bitLength() + 16) - (n.bitLength() & 7);
        BigInteger e = e(a[1].shiftLeft(bitLength), subtract2);
        BigInteger negate3 = e(bigIntegerArr[1].shiftLeft(bitLength), subtract2).negate();
        a(Beta.TAG, (Object) fromBigInteger.toBigInteger().toString(16));
        a("Lambda", (Object) b.toString(16));
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        sb.append(bigIntegerArr[0].toString(16));
        sb.append(", ");
        sb.append(bigIntegerArr[1].toString(16));
        sb.append(" }");
        a("v1", (Object) sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("{ ");
        sb2.append(a[0].toString(16));
        sb2.append(", ");
        sb2.append(a[1].toString(16));
        sb2.append(" }");
        a("v2", (Object) sb2.toString());
        a("(OPT) g1", (Object) e.toString(16));
        a("(OPT) g2", (Object) negate3.toString(16));
        a("(OPT) bits", (Object) Integer.toString(bitLength));
    }

    private static boolean a(BigInteger bigInteger, BigInteger bigInteger2) {
        return bigInteger.gcd(bigInteger2).equals(ECConstants.ONE);
    }

    private static boolean a(BigInteger[] bigIntegerArr, BigInteger bigInteger) {
        return c(bigIntegerArr[0].abs().max(bigIntegerArr[1].abs()), bigInteger);
    }

    private static BigInteger[] a(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        return d(bigInteger.subtract(bigInteger2).divide(bigInteger3), bigInteger.add(bigInteger2).divide(bigInteger3));
    }

    private static BigInteger[] a(BigInteger[] bigIntegerArr) {
        boolean z = bigIntegerArr[0].compareTo(bigIntegerArr[1]) < 0;
        if (z) {
            b(bigIntegerArr);
        }
        BigInteger bigInteger = bigIntegerArr[0];
        BigInteger bigInteger2 = bigIntegerArr[1];
        BigInteger bigInteger3 = ECConstants.ONE;
        BigInteger bigInteger4 = ECConstants.ZERO;
        BigInteger bigInteger5 = ECConstants.ZERO;
        BigInteger bigInteger6 = ECConstants.ONE;
        BigInteger bigInteger7 = bigInteger;
        BigInteger bigInteger8 = bigInteger2;
        BigInteger bigInteger9 = bigInteger7;
        while (bigInteger8.compareTo(ECConstants.ONE) > 0) {
            BigInteger[] divideAndRemainder = bigInteger9.divideAndRemainder(bigInteger8);
            BigInteger bigInteger10 = divideAndRemainder[0];
            BigInteger bigInteger11 = bigInteger8;
            bigInteger8 = divideAndRemainder[1];
            bigInteger9 = bigInteger11;
            BigInteger bigInteger12 = bigInteger4;
            bigInteger4 = bigInteger3.subtract(bigInteger10.multiply(bigInteger4));
            bigInteger3 = bigInteger12;
            BigInteger bigInteger13 = bigInteger6;
            bigInteger6 = bigInteger5.subtract(bigInteger10.multiply(bigInteger6));
            bigInteger5 = bigInteger13;
        }
        if (bigInteger8.signum() <= 0) {
            throw new IllegalStateException();
        }
        BigInteger[] bigIntegerArr2 = {bigInteger4, bigInteger6};
        if (z) {
            b(bigIntegerArr2);
        }
        return bigIntegerArr2;
    }

    private static BigInteger[] a(BigInteger[] bigIntegerArr, BigInteger[] bigIntegerArr2) {
        return c(bigIntegerArr, bigIntegerArr2) ? bigIntegerArr : bigIntegerArr2;
    }

    private static BigInteger b(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        BigInteger bigInteger4 = new Fp(bigInteger, bigInteger2.multiply(bigInteger2).subtract(bigInteger3.shiftLeft(2)).mod(bigInteger)).sqrt().toBigInteger();
        if (!bigInteger4.testBit(0)) {
            bigInteger4 = bigInteger.subtract(bigInteger4);
        }
        return bigInteger4.shiftRight(1);
    }

    private static void b(BigInteger[] bigIntegerArr) {
        BigInteger bigInteger = bigIntegerArr[0];
        bigIntegerArr[0] = bigIntegerArr[1];
        bigIntegerArr[1] = bigInteger;
    }

    private static BigInteger[] b(BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger bigInteger3 = ECConstants.ZERO;
        BigInteger bigInteger4 = ECConstants.ONE;
        BigInteger bigInteger5 = bigInteger3;
        BigInteger bigInteger6 = bigInteger2;
        BigInteger bigInteger7 = bigInteger;
        while (true) {
            BigInteger[] divideAndRemainder = bigInteger7.divideAndRemainder(bigInteger6);
            BigInteger bigInteger8 = divideAndRemainder[0];
            BigInteger bigInteger9 = divideAndRemainder[1];
            BigInteger subtract = bigInteger5.subtract(bigInteger8.multiply(bigInteger4));
            if (c(bigInteger6, bigInteger)) {
                return new BigInteger[]{bigInteger7, bigInteger5, bigInteger6, bigInteger4, bigInteger9, subtract};
            }
            bigInteger7 = bigInteger6;
            bigInteger5 = bigInteger4;
            bigInteger6 = bigInteger9;
            bigInteger4 = subtract;
        }
    }

    private static BigInteger[] b(BigInteger[] bigIntegerArr, BigInteger[] bigIntegerArr2) {
        BigInteger max = bigIntegerArr[0].max(bigIntegerArr2[0]);
        BigInteger min = bigIntegerArr[1].min(bigIntegerArr2[1]);
        if (max.compareTo(min) > 0) {
            return null;
        }
        return new BigInteger[]{max, min};
    }

    private static boolean c(BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger abs = bigInteger.abs();
        BigInteger abs2 = bigInteger2.abs();
        int bitLength = abs2.bitLength();
        int bitLength2 = abs.bitLength() * 2;
        return bitLength2 + -1 <= bitLength && (bitLength2 < bitLength || abs.multiply(abs).compareTo(abs2) < 0);
    }

    private static boolean c(BigInteger[] bigIntegerArr, BigInteger[] bigIntegerArr2) {
        boolean z = false;
        BigInteger abs = bigIntegerArr[0].abs();
        BigInteger abs2 = bigIntegerArr[1].abs();
        BigInteger abs3 = bigIntegerArr2[0].abs();
        BigInteger abs4 = bigIntegerArr2[1].abs();
        boolean z2 = abs.compareTo(abs3) < 0;
        if (z2 == (abs2.compareTo(abs4) < 0)) {
            return z2;
        }
        if (abs.multiply(abs).add(abs2.multiply(abs2)).compareTo(abs3.multiply(abs3).add(abs4.multiply(abs4))) < 0) {
            z = true;
        }
        return z;
    }

    private static BigInteger[] d(BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger.compareTo(bigInteger2) <= 0) {
            return new BigInteger[]{bigInteger, bigInteger2};
        }
        return new BigInteger[]{bigInteger2, bigInteger};
    }

    private static BigInteger e(BigInteger bigInteger, BigInteger bigInteger2) {
        boolean z = bigInteger.signum() != bigInteger2.signum();
        BigInteger abs = bigInteger.abs();
        BigInteger abs2 = bigInteger2.abs();
        BigInteger divide = abs.add(abs2.shiftRight(1)).divide(abs2);
        return z ? divide.negate() : divide;
    }

    public static void main(String[] strArr) {
        if (strArr.length < 1) {
            System.err.println("Expected a list of curve names as arguments");
            return;
        }
        for (String a : strArr) {
            a(a);
        }
    }
}
