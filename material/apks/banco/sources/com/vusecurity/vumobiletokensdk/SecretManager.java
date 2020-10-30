package com.vusecurity.vumobiletokensdk;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.security.SecureRandom;
import java.util.zip.CRC32;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.apache.bzip2.BZip2Constants;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.modes.SICBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

class SecretManager {
    private static final int[] a = {1, 10, 100, 1000, 10000, BZip2Constants.baseBlockSize, 1000000, 10000000, 100000000};

    static class BouncyCastleAESCounterMode {
        private BouncyCastleAESCounterMode() {
        }

        /* access modifiers changed from: private */
        public String a(String str, String str2, int i) {
            if (i != 128 && i != 192 && i != 256) {
                return "Error: Must be a key mode of either 128, 192, 256 bits";
            }
            if (str == null || str2 == null) {
                return "Error: cipher and/or key equals null";
            }
            int i2 = i / 8;
            byte[] decode = Base64.decode(str, 0);
            byte[] copyOf = Arrays.copyOf(str2.getBytes(), i2);
            BufferedBlockCipher bufferedBlockCipher = new BufferedBlockCipher(new SICBlockCipher(new AESEngine()));
            bufferedBlockCipher.reset();
            bufferedBlockCipher.init(true, new ParametersWithIV(new KeyParameter(a(copyOf, i2).getEncoded()), new IvParameterSpec(Arrays.copyOf(Arrays.copyOf(decode, 8), i2 / 2)).getIV()));
            byte[] bArr = new byte[bufferedBlockCipher.getOutputSize(decode.length - 8)];
            bufferedBlockCipher.doFinal(bArr, bufferedBlockCipher.processBytes(decode, 8, decode.length - 8, bArr, 0));
            return new String(bArr);
        }

        /* access modifiers changed from: private */
        public String b(String str, String str2, int i) {
            if (i != 128 && i != 192 && i != 256) {
                return "Error: Must be a key mode of either 128, 192, 256 bits";
            }
            if (str == null || str2 == null) {
                return "Error: cipher and/or key equals null";
            }
            int i2 = i / 8;
            byte[] bytes = str.getBytes();
            byte[] copyOf = Arrays.copyOf(str2.getBytes(), i2);
            BufferedBlockCipher bufferedBlockCipher = new BufferedBlockCipher(new SICBlockCipher(new AESEngine()));
            bufferedBlockCipher.reset();
            SecretKey a = a(copyOf, i2);
            IvParameterSpec a2 = a(i2);
            bufferedBlockCipher.init(true, new ParametersWithIV(new KeyParameter(a.getEncoded()), a2.getIV()));
            byte[] bArr = new byte[bufferedBlockCipher.getOutputSize(bytes.length)];
            bufferedBlockCipher.doFinal(bArr, bufferedBlockCipher.processBytes(bytes, 0, bytes.length, bArr, 0));
            byte[] copyOf2 = Arrays.copyOf(a2.getIV(), 8);
            byte[] bArr2 = new byte[(copyOf2.length + bArr.length)];
            System.arraycopy(copyOf2, 0, bArr2, 0, copyOf2.length);
            System.arraycopy(bArr, 0, bArr2, copyOf2.length, bArr.length);
            return Base64.encodeToString(bArr2, 0);
        }

        private SecretKey a(byte[] bArr, int i) {
            try {
                SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
                BufferedBlockCipher bufferedBlockCipher = new BufferedBlockCipher(new AESEngine());
                bufferedBlockCipher.init(true, new KeyParameter(secretKeySpec.getEncoded()));
                byte[] bArr2 = new byte[bufferedBlockCipher.getOutputSize(secretKeySpec.getEncoded().length)];
                bufferedBlockCipher.doFinal(bArr2, bufferedBlockCipher.processBytes(secretKeySpec.getEncoded(), 0, secretKeySpec.getEncoded().length, bArr2, 0));
                int i2 = i / 2;
                System.arraycopy(bArr2, 0, bArr2, i2, i2);
                return new SecretKeySpec(bArr2, "AES");
            } catch (Throwable unused) {
                return null;
            }
        }

        private IvParameterSpec a(int i) {
            int i2 = i / 2;
            byte[] bArr = new byte[i2];
            new SecureRandom().nextBytes(bArr);
            return new IvParameterSpec(Arrays.copyOf(Arrays.copyOf(bArr, 8), i2));
        }
    }

    public static long a(double d) {
        return (long) (d / 40.0d);
    }

    public static long b(long j) {
        return j * 40;
    }

    SecretManager() {
    }

    public static String a(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        String d = d(str, str2);
        if (!d.matches("[A-Za-z0-9\\s]+")) {
            return null;
        }
        String str3 = d.split(UtilsCuentas.SEPARAOR2)[0];
        String str4 = d.split(UtilsCuentas.SEPARAOR2)[1];
        if (c(str3, str4)) {
            return str4;
        }
        return null;
    }

    public static String b(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        String a2 = a(str);
        StringBuilder sb = new StringBuilder();
        sb.append(a2);
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(str);
        return e(sb.toString(), str2);
    }

    public static String a(byte[] bArr, long j) {
        return a(bArr, a((double) a(j)), 6);
    }

    public static long a(long j) {
        return (System.currentTimeMillis() / 1000) + j;
    }

    public static long c(long j) {
        long a2 = a(j);
        return b(a((double) a2) + 1) - a2;
    }

    private static String a(byte[] bArr, long j, int i) {
        if (6 < i || i > 10) {
            throw new IllegalArgumentException("Number of digits not within range: 6 < digits > 10");
        } else if (bArr == null || bArr.length == 0) {
            throw new IllegalArgumentException("Shared secret shouldn't be null or empty");
        } else {
            byte[] b = b(bArr, j);
            byte b2 = b[b.length - 1] & Ascii.SI;
            String num = Integer.toString(((b[b2 + 3] & UnsignedBytes.MAX_VALUE) | ((((b[b2] & Ascii.DEL) << Ascii.CAN) | ((b[b2 + 1] & UnsignedBytes.MAX_VALUE) << Ascii.DLE)) | ((b[b2 + 2] & UnsignedBytes.MAX_VALUE) << 8))) % a[i]);
            while (num.length() < i) {
                StringBuilder sb = new StringBuilder();
                sb.append("0");
                sb.append(num);
                num = sb.toString();
            }
            return num;
        }
    }

    public static byte[] b(byte[] bArr, long j) {
        HMac hMac = new HMac(new SHA1Digest());
        byte[] bArr2 = new byte[hMac.getMacSize()];
        hMac.init(new KeyParameter(bArr));
        hMac.update(d(j), 0, 8);
        hMac.doFinal(bArr2, 0);
        return bArr2;
    }

    public static byte[] d(long j) {
        byte[] bArr = new byte[8];
        bArr[7] = (byte) ((int) j);
        bArr[6] = (byte) ((int) (j >> 8));
        bArr[5] = (byte) ((int) (j >> 16));
        bArr[4] = (byte) ((int) (j >> 24));
        bArr[3] = (byte) ((int) (j >> 32));
        bArr[2] = (byte) ((int) (j >> 40));
        bArr[1] = (byte) ((int) (j >> 48));
        bArr[0] = (byte) ((int) (j >> 56));
        return bArr;
    }

    public static boolean c(String str, String str2) {
        byte[] bytes = str2.getBytes();
        CRC32 crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        if (crc32.getValue() == Long.parseLong(str)) {
            return true;
        }
        return false;
    }

    public static String a(String str) {
        byte[] bytes = str.getBytes();
        CRC32 crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return Long.valueOf(crc32.getValue()).toString();
    }

    public static String d(String str, String str2) {
        String str3 = "";
        try {
            return new BouncyCastleAESCounterMode().a(str, str2, 256);
        } catch (Throwable unused) {
            Log.e(SecretManager.class.getName(), "Failed to decrypt seed");
            return str3;
        }
    }

    public static String e(String str, String str2) {
        String str3 = "";
        try {
            return new BouncyCastleAESCounterMode().b(str, str2, 256);
        } catch (Throwable unused) {
            Log.e(SecretManager.class.getName(), "Failed to encrypt seed");
            return str3;
        }
    }
}
