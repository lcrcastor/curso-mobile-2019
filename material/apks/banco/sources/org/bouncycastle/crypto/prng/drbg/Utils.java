package org.bouncycastle.crypto.prng.drbg;

import com.google.common.primitives.UnsignedBytes;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.Hashtable;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.util.Integers;

class Utils {
    static final Hashtable a = new Hashtable();

    static {
        a.put(CommonUtils.SHA1_INSTANCE, Integers.valueOf(128));
        a.put("SHA-224", Integers.valueOf(192));
        a.put("SHA-256", Integers.valueOf(256));
        a.put("SHA-384", Integers.valueOf(256));
        a.put("SHA-512", Integers.valueOf(256));
        a.put("SHA-512/224", Integers.valueOf(192));
        a.put("SHA-512/256", Integers.valueOf(256));
    }

    Utils() {
    }

    static int a(Digest digest) {
        return ((Integer) a.get(digest.getAlgorithmName())).intValue();
    }

    static int a(Mac mac) {
        String algorithmName = mac.getAlgorithmName();
        return ((Integer) a.get(algorithmName.substring(0, algorithmName.indexOf("/")))).intValue();
    }

    static boolean a(byte[] bArr, int i) {
        return bArr != null && bArr.length > i;
    }

    static byte[] a(Digest digest, byte[] bArr, int i) {
        byte[] bArr2 = new byte[((i + 7) / 8)];
        int length = bArr2.length / digest.getDigestSize();
        byte[] bArr3 = new byte[digest.getDigestSize()];
        int i2 = 0;
        int i3 = 1;
        for (int i4 = 0; i4 <= length; i4++) {
            digest.update((byte) i3);
            digest.update((byte) (i >> 24));
            digest.update((byte) (i >> 16));
            digest.update((byte) (i >> 8));
            digest.update((byte) i);
            digest.update(bArr, 0, bArr.length);
            digest.doFinal(bArr3, 0);
            System.arraycopy(bArr3, 0, bArr2, bArr3.length * i4, bArr2.length - (bArr3.length * i4) > bArr3.length ? bArr3.length : bArr2.length - (bArr3.length * i4));
            i3++;
        }
        int i5 = i % 8;
        if (i5 != 0) {
            int i6 = 8 - i5;
            int i7 = 0;
            while (i2 != bArr2.length) {
                byte b = bArr2[i2] & UnsignedBytes.MAX_VALUE;
                bArr2[i2] = (byte) ((i7 << (8 - i6)) | (b >>> i6));
                i2++;
                i7 = b;
            }
        }
        return bArr2;
    }
}
