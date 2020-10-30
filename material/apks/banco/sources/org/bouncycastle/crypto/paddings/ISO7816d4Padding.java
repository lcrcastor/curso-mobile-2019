package org.bouncycastle.crypto.paddings;

import com.google.common.primitives.UnsignedBytes;
import java.security.SecureRandom;
import org.bouncycastle.crypto.InvalidCipherTextException;

public class ISO7816d4Padding implements BlockCipherPadding {
    public int addPadding(byte[] bArr, int i) {
        int length = bArr.length - i;
        bArr[i] = UnsignedBytes.MAX_POWER_OF_TWO;
        while (true) {
            i++;
            if (i >= bArr.length) {
                return length;
            }
            bArr[i] = 0;
        }
    }

    public String getPaddingName() {
        return "ISO7816-4";
    }

    public void init(SecureRandom secureRandom) {
    }

    public int padCount(byte[] bArr) {
        int length = bArr.length - 1;
        while (length > 0 && bArr[length] == 0) {
            length--;
        }
        if (bArr[length] == Byte.MIN_VALUE) {
            return bArr.length - length;
        }
        throw new InvalidCipherTextException("pad block corrupted");
    }
}
