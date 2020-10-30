package org.bouncycastle.asn1.ua;

import java.math.BigInteger;
import java.util.Random;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECPoint;

public abstract class DSTU4145PointEncoder {
    private static ECFieldElement a(ECCurve eCCurve, ECFieldElement eCFieldElement) {
        ECFieldElement eCFieldElement2;
        if (eCFieldElement.isZero()) {
            return eCFieldElement;
        }
        ECFieldElement fromBigInteger = eCCurve.fromBigInteger(ECConstants.ZERO);
        Random random = new Random();
        int fieldSize = eCFieldElement.getFieldSize();
        do {
            ECFieldElement fromBigInteger2 = eCCurve.fromBigInteger(new BigInteger(fieldSize, random));
            ECFieldElement eCFieldElement3 = eCFieldElement;
            eCFieldElement2 = fromBigInteger;
            for (int i = 1; i <= fieldSize - 1; i++) {
                ECFieldElement square = eCFieldElement3.square();
                eCFieldElement2 = eCFieldElement2.square().add(square.multiply(fromBigInteger2));
                eCFieldElement3 = square.add(eCFieldElement);
            }
            if (!eCFieldElement3.isZero()) {
                return null;
            }
        } while (eCFieldElement2.square().add(eCFieldElement2).isZero());
        return eCFieldElement2;
    }

    private static ECFieldElement a(ECFieldElement eCFieldElement) {
        ECFieldElement eCFieldElement2 = eCFieldElement;
        for (int i = 1; i < eCFieldElement.getFieldSize(); i++) {
            eCFieldElement2 = eCFieldElement2.square().add(eCFieldElement);
        }
        return eCFieldElement2;
    }

    public static ECPoint decodePoint(ECCurve eCCurve, byte[] bArr) {
        ECFieldElement fromBigInteger = eCCurve.fromBigInteger(BigInteger.valueOf((long) (bArr[bArr.length - 1] & 1)));
        ECFieldElement fromBigInteger2 = eCCurve.fromBigInteger(new BigInteger(1, bArr));
        if (!a(fromBigInteger2).equals(eCCurve.getA())) {
            fromBigInteger2 = fromBigInteger2.addOne();
        }
        ECFieldElement eCFieldElement = null;
        if (fromBigInteger2.isZero()) {
            eCFieldElement = eCCurve.getB().sqrt();
        } else {
            ECFieldElement a = a(eCCurve, fromBigInteger2.square().invert().multiply(eCCurve.getB()).add(eCCurve.getA()).add(fromBigInteger2));
            if (a != null) {
                if (!a(a).equals(fromBigInteger)) {
                    a = a.addOne();
                }
                eCFieldElement = fromBigInteger2.multiply(a);
            }
        }
        if (eCFieldElement != null) {
            return eCCurve.createPoint(fromBigInteger2.toBigInteger(), eCFieldElement.toBigInteger());
        }
        throw new IllegalArgumentException("Invalid point compression");
    }

    public static byte[] encodePoint(ECPoint eCPoint) {
        ECPoint normalize = eCPoint.normalize();
        ECFieldElement affineXCoord = normalize.getAffineXCoord();
        byte[] encoded = affineXCoord.getEncoded();
        if (!affineXCoord.isZero()) {
            if (a(normalize.getAffineYCoord().divide(affineXCoord)).isOne()) {
                int length = encoded.length - 1;
                encoded[length] = (byte) (encoded[length] | 1);
                return encoded;
            }
            int length2 = encoded.length - 1;
            encoded[length2] = (byte) (encoded[length2] & 254);
        }
        return encoded;
    }
}
