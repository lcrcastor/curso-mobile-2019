package okhttp3.internal.http2;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import cz.msebera.android.httpclient.message.TokenParser;
import java.io.IOException;
import okhttp3.internal.Util;
import okio.ByteString;

public final class Http2 {
    static final ByteString a = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    static final String[] b = new String[64];
    static final String[] c = new String[256];
    private static final String[] d = {"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};

    static {
        int[] iArr;
        for (int i = 0; i < c.length; i++) {
            c[i] = Util.format("%8s", Integer.toBinaryString(i)).replace(TokenParser.SP, TarjetasConstants.ULT_NUM_AMEX);
        }
        b[0] = "";
        b[1] = "END_STREAM";
        int[] iArr2 = {1};
        b[8] = "PADDED";
        for (int i2 : iArr2) {
            String[] strArr = b;
            int i3 = i2 | 8;
            StringBuilder sb = new StringBuilder();
            sb.append(b[i2]);
            sb.append("|PADDED");
            strArr[i3] = sb.toString();
        }
        b[4] = "END_HEADERS";
        b[32] = "PRIORITY";
        b[36] = "END_HEADERS|PRIORITY";
        for (int i4 : new int[]{4, 32, 36}) {
            for (int i5 : iArr2) {
                String[] strArr2 = b;
                int i6 = i5 | i4;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(b[i5]);
                sb2.append('|');
                sb2.append(b[i4]);
                strArr2[i6] = sb2.toString();
                String[] strArr3 = b;
                int i7 = i6 | 8;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(b[i5]);
                sb3.append('|');
                sb3.append(b[i4]);
                sb3.append("|PADDED");
                strArr3[i7] = sb3.toString();
            }
        }
        for (int i8 = 0; i8 < b.length; i8++) {
            if (b[i8] == null) {
                b[i8] = c[i8];
            }
        }
    }

    private Http2() {
    }

    static IllegalArgumentException a(String str, Object... objArr) {
        throw new IllegalArgumentException(Util.format(str, objArr));
    }

    static IOException b(String str, Object... objArr) {
        throw new IOException(Util.format(str, objArr));
    }

    static String a(boolean z, int i, int i2, byte b2, byte b3) {
        String format = b2 < d.length ? d[b2] : Util.format("0x%02x", Byte.valueOf(b2));
        String a2 = a(b2, b3);
        String str = "%s 0x%08x %5d %-13s %s";
        Object[] objArr = new Object[5];
        objArr[0] = z ? "<<" : ">>";
        objArr[1] = Integer.valueOf(i);
        objArr[2] = Integer.valueOf(i2);
        objArr[3] = format;
        objArr[4] = a2;
        return Util.format(str, objArr);
    }

    static String a(byte b2, byte b3) {
        String str;
        if (b3 == 0) {
            return "";
        }
        switch (b2) {
            case 2:
            case 3:
            case 7:
            case 8:
                return c[b3];
            case 4:
            case 6:
                return b3 == 1 ? "ACK" : c[b3];
            default:
                if (b3 < b.length) {
                    str = b[b3];
                } else {
                    str = c[b3];
                }
                if (b2 != 5 || (b3 & 4) == 0) {
                    return (b2 != 0 || (b3 & 32) == 0) ? str : str.replace("PRIORITY", "COMPRESSED");
                }
                return str.replace("HEADERS", "PUSH_PROMISE");
        }
    }
}
