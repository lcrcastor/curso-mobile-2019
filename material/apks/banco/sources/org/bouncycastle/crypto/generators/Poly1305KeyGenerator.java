package org.bouncycastle.crypto.generators;

import com.google.common.base.Ascii;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;

public class Poly1305KeyGenerator extends CipherKeyGenerator {
    private static void a(byte b, byte b2) {
        if ((b & (b2 ^ -1)) != 0) {
            throw new IllegalArgumentException("Invalid format for r portion of Poly1305 key.");
        }
    }

    public static void checkKey(byte[] bArr) {
        if (bArr.length != 32) {
            throw new IllegalArgumentException("Poly1305 key must be 256 bits.");
        }
        a(bArr[19], Ascii.SI);
        a(bArr[23], Ascii.SI);
        a(bArr[27], Ascii.SI);
        a(bArr[31], Ascii.SI);
        a(bArr[20], -4);
        a(bArr[24], -4);
        a(bArr[28], -4);
    }

    public static void clamp(byte[] bArr) {
        if (bArr.length != 32) {
            throw new IllegalArgumentException("Poly1305 key must be 256 bits.");
        }
        bArr[19] = (byte) (bArr[19] & Ascii.SI);
        bArr[23] = (byte) (bArr[23] & Ascii.SI);
        bArr[27] = (byte) (bArr[27] & Ascii.SI);
        bArr[31] = (byte) (bArr[31] & Ascii.SI);
        bArr[20] = (byte) (bArr[20] & -4);
        bArr[24] = (byte) (bArr[24] & -4);
        bArr[28] = (byte) (bArr[28] & -4);
    }

    public byte[] generateKey() {
        byte[] generateKey = super.generateKey();
        clamp(generateKey);
        return generateKey;
    }

    public void init(KeyGenerationParameters keyGenerationParameters) {
        super.init(new KeyGenerationParameters(keyGenerationParameters.getRandom(), 256));
    }
}
