package com.appsflyer;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.security.MessageDigest;
import java.util.Formatter;

/* renamed from: com.appsflyer.r reason: case insensitive filesystem */
final class C0096r {
    C0096r() {
    }

    public static String a(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(CommonUtils.SHA1_INSTANCE);
            instance.reset();
            instance.update(str.getBytes("UTF-8"));
            return a(instance.digest());
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Error turning ");
            sb.append(str.substring(0, 6));
            sb.append(".. to SHA1");
            AFLogger.afErrorLog(sb.toString(), e);
            return null;
        }
    }

    public static String b(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            instance.reset();
            instance.update(str.getBytes("UTF-8"));
            return a(instance.digest());
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Error turning ");
            sb.append(str.substring(0, 6));
            sb.append(".. to MD5");
            AFLogger.afErrorLog(sb.toString(), e);
            return null;
        }
    }

    public static String c(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                stringBuffer.append(Integer.toString((b & UnsignedBytes.MAX_VALUE) + Ascii.NUL, 16).substring(1));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Error turning ");
            sb.append(str.substring(0, 6));
            sb.append(".. to SHA-256");
            AFLogger.afErrorLog(sb.toString(), e);
            return null;
        }
    }

    private static String a(byte[] bArr) {
        Formatter formatter = new Formatter();
        for (byte valueOf : bArr) {
            formatter.format("%02x", new Object[]{Byte.valueOf(valueOf)});
        }
        String obj = formatter.toString();
        formatter.close();
        return obj;
    }
}
