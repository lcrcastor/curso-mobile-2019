package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.params.ElGamalParameters;

public class ElGamalParametersGenerator {
    private int a;
    private int b;
    private SecureRandom c;

    public ElGamalParameters generateParameters() {
        BigInteger[] a2 = DHParametersHelper.a(this.a, this.b, this.c);
        BigInteger bigInteger = a2[0];
        return new ElGamalParameters(bigInteger, DHParametersHelper.a(bigInteger, a2[1], this.c));
    }

    public void init(int i, int i2, SecureRandom secureRandom) {
        this.a = i;
        this.b = i2;
        this.c = secureRandom;
    }
}
