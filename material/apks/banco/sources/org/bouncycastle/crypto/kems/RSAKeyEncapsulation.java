package org.bouncycastle.crypto.kems;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.KeyEncapsulation;
import org.bouncycastle.crypto.params.KDFParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.util.BigIntegers;

public class RSAKeyEncapsulation implements KeyEncapsulation {
    private static final BigInteger a = BigInteger.valueOf(0);
    private static final BigInteger b = BigInteger.valueOf(1);
    private DerivationFunction c;
    private SecureRandom d;
    private RSAKeyParameters e;

    public RSAKeyEncapsulation(DerivationFunction derivationFunction, SecureRandom secureRandom) {
        this.c = derivationFunction;
        this.d = secureRandom;
    }

    public CipherParameters decrypt(byte[] bArr, int i) {
        return decrypt(bArr, 0, bArr.length, i);
    }

    public CipherParameters decrypt(byte[] bArr, int i, int i2, int i3) {
        if (!this.e.isPrivate()) {
            throw new IllegalArgumentException("Private key required for decryption");
        }
        BigInteger modulus = this.e.getModulus();
        BigInteger exponent = this.e.getExponent();
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, bArr2.length);
        return generateKey(modulus, new BigInteger(1, bArr2).modPow(exponent, modulus), i3);
    }

    public CipherParameters encrypt(byte[] bArr, int i) {
        return encrypt(bArr, 0, i);
    }

    public CipherParameters encrypt(byte[] bArr, int i, int i2) {
        if (this.e.isPrivate()) {
            throw new IllegalArgumentException("Public key required for encryption");
        }
        BigInteger modulus = this.e.getModulus();
        BigInteger exponent = this.e.getExponent();
        BigInteger createRandomInRange = BigIntegers.createRandomInRange(a, modulus.subtract(b), this.d);
        byte[] asUnsignedByteArray = BigIntegers.asUnsignedByteArray((modulus.bitLength() + 7) / 8, createRandomInRange.modPow(exponent, modulus));
        System.arraycopy(asUnsignedByteArray, 0, bArr, i, asUnsignedByteArray.length);
        return generateKey(modulus, createRandomInRange, i2);
    }

    /* access modifiers changed from: protected */
    public KeyParameter generateKey(BigInteger bigInteger, BigInteger bigInteger2, int i) {
        this.c.init(new KDFParameters(BigIntegers.asUnsignedByteArray((bigInteger.bitLength() + 7) / 8, bigInteger2), null));
        byte[] bArr = new byte[i];
        this.c.generateBytes(bArr, 0, bArr.length);
        return new KeyParameter(bArr);
    }

    public void init(CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof RSAKeyParameters)) {
            throw new IllegalArgumentException("RSA key required");
        }
        this.e = (RSAKeyParameters) cipherParameters;
    }
}
