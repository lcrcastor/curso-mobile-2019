package org.bouncycastle.math.ec;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECPoint.F2m;

public class WTauNafMultiplier extends AbstractECMultiplier {
    private F2m a(F2m f2m, ZTauElement zTauElement, PreCompInfo preCompInfo, byte b, byte b2) {
        return a(f2m, Tnaf.a(b2, zTauElement, 4, BigInteger.valueOf(16), Tnaf.a(b2, 4), b == 0 ? Tnaf.a : Tnaf.c), preCompInfo);
    }

    private static F2m a(F2m f2m, byte[] bArr, PreCompInfo preCompInfo) {
        F2m[] f2mArr;
        ECCurve.F2m f2m2 = (ECCurve.F2m) f2m.getCurve();
        byte byteValue = f2m2.getA().toBigInteger().byteValue();
        if (preCompInfo == null || !(preCompInfo instanceof WTauNafPreCompInfo)) {
            f2mArr = Tnaf.a(f2m, byteValue);
            WTauNafPreCompInfo wTauNafPreCompInfo = new WTauNafPreCompInfo();
            wTauNafPreCompInfo.setPreComp(f2mArr);
            f2m2.setPreCompInfo(f2m, "bc_wtnaf", wTauNafPreCompInfo);
        } else {
            f2mArr = ((WTauNafPreCompInfo) preCompInfo).getPreComp();
        }
        F2m f2m3 = (F2m) f2m.getCurve().getInfinity();
        for (int length = bArr.length - 1; length >= 0; length--) {
            f2m3 = Tnaf.a(f2m3);
            byte b = bArr[length];
            if (b != 0) {
                f2m3 = b > 0 ? f2m3.addSimple(f2mArr[b]) : f2m3.subtractSimple(f2mArr[-b]);
            }
        }
        return f2m3;
    }

    /* access modifiers changed from: protected */
    public ECPoint multiplyPositive(ECPoint eCPoint, BigInteger bigInteger) {
        if (!(eCPoint instanceof F2m)) {
            throw new IllegalArgumentException("Only ECPoint.F2m can be used in WTauNafMultiplier");
        }
        F2m f2m = (F2m) eCPoint;
        ECCurve.F2m f2m2 = (ECCurve.F2m) f2m.getCurve();
        int m = f2m2.getM();
        byte byteValue = f2m2.getA().toBigInteger().byteValue();
        byte a = f2m2.a();
        byte b = byteValue;
        return a(f2m, Tnaf.a(bigInteger, m, b, f2m2.b(), a, 10), f2m2.getPreCompInfo(f2m, "bc_wtnaf"), b, a);
    }
}
