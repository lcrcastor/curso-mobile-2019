package org.bouncycastle.crypto.paddings;

import com.google.common.primitives.UnsignedBytes;
import java.security.SecureRandom;
import org.bouncycastle.crypto.InvalidCipherTextException;

public class PKCS7Padding implements BlockCipherPadding {
    public int addPadding(byte[] bArr, int i) {
        byte length = (byte) (bArr.length - i);
        while (i < bArr.length) {
            bArr[i] = length;
            i++;
        }
        return length;
    }

    public String getPaddingName() {
        return "PKCS7";
    }

    public void init(SecureRandom secureRandom) {
    }

    public int padCount(byte[] bArr) {
        byte b = bArr[bArr.length - 1] & UnsignedBytes.MAX_VALUE;
        if (b > bArr.length || b == 0) {
            throw new InvalidCipherTextException("pad block corrupted");
        }
        for (int i = 1; i <= b; i++) {
            if (bArr[bArr.length - i] != b) {
                throw new InvalidCipherTextException("pad block corrupted");
            }
        }
        return b;
    }
}
