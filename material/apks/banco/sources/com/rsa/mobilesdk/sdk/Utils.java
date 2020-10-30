package com.rsa.mobilesdk.sdk;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.common.primitives.UnsignedBytes;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

class Utils {
    private static DecimalFormat a = a();

    public static String a(String str) {
        return str != null ? str : "unavailable";
    }

    Utils() {
    }

    public static synchronized String a(double d) {
        String format;
        synchronized (Utils.class) {
            format = a.format(d);
        }
        return format;
    }

    private static DecimalFormat a() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.US);
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setMinusSign('-');
        return new DecimalFormat("###.########;-###", decimalFormatSymbols);
    }

    static String a(Date date) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(date);
    }

    public static int a(Properties properties, String str, int i) {
        int i2;
        if (properties == null) {
            return i;
        }
        Object obj = properties.get(str);
        if (obj instanceof String) {
            String str2 = (String) properties.get(str);
            if (obj != null) {
                try {
                    i2 = Integer.parseInt(str2);
                } catch (NumberFormatException unused) {
                }
                return i2;
            }
        }
        i2 = i;
        return i2;
    }

    public static boolean a(Properties properties, String str, boolean z) {
        if (properties == null) {
            return z;
        }
        return a(properties, str, z ? 1 : 0) != 0;
    }

    public static String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(r0 * 2);
        for (byte b : bArr) {
            byte b2 = b & UnsignedBytes.MAX_VALUE;
            if (b2 < 16) {
                stringBuffer.append(TarjetasConstants.ULT_NUM_AMEX);
            }
            stringBuffer.append(Integer.toHexString(b2));
        }
        return stringBuffer.toString().toUpperCase();
    }

    public static boolean b(byte[] bArr) {
        if (!(bArr == null || bArr.length == 0)) {
            try {
                SecureRandom.getInstance("SHA1PRNG").nextBytes(bArr);
                return true;
            } catch (NoSuchAlgorithmException unused) {
            }
        }
        return false;
    }
}
