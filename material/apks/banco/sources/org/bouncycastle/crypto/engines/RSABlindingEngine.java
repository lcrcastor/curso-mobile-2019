package org.bouncycastle.crypto.engines;

import java.math.BigInteger;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSABlindingParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;

public class RSABlindingEngine implements AsymmetricBlockCipher {
    private RSACoreEngine a = new RSACoreEngine();
    private RSAKeyParameters b;
    private BigInteger c;
    private boolean d;

    private BigInteger a(BigInteger bigInteger) {
        return bigInteger.multiply(this.c.modPow(this.b.getExponent(), this.b.getModulus())).mod(this.b.getModulus());
    }

    private BigInteger b(BigInteger bigInteger) {
        BigInteger modulus = this.b.getModulus();
        return bigInteger.multiply(this.c.modInverse(modulus)).mod(modulus);
    }

    public int getInputBlockSize() {
        return this.a.a();
    }

    public int getOutputBlockSize() {
        return this.a.b();
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof ParametersWithRandom) {
            cipherParameters = ((ParametersWithRandom) cipherParameters).getParameters();
        }
        RSABlindingParameters rSABlindingParameters = (RSABlindingParameters) cipherParameters;
        this.a.a(z, rSABlindingParameters.getPublicKey());
        this.d = z;
        this.b = rSABlindingParameters.getPublicKey();
        this.c = rSABlindingParameters.getBlindingFactor();
    }

    public byte[] processBlock(byte[] bArr, int i, int i2) {
        BigInteger a2 = this.a.a(bArr, i, i2);
        return this.a.a(this.d ? a(a2) : b(a2));
    }
}
