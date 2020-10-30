package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DHValidationParameters;

public class DHParametersGenerator {
    private static final BigInteger d = BigInteger.valueOf(2);
    private int a;
    private int b;
    private SecureRandom c;

    public DHParameters generateParameters() {
        BigInteger[] a2 = DHParametersHelper.a(this.a, this.b, this.c);
        BigInteger bigInteger = a2[0];
        BigInteger bigInteger2 = a2[1];
        DHParameters dHParameters = new DHParameters(bigInteger, DHParametersHelper.a(bigInteger, bigInteger2, this.c), bigInteger2, d, (DHValidationParameters) null);
        return dHParameters;
    }

    public void init(int i, int i2, SecureRandom secureRandom) {
        this.a = i;
        this.b = i2;
        this.c = secureRandom;
    }
}
