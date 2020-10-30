package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.common.internal.zzq;
import cz.msebera.android.httpclient.message.TokenParser;

public class zzse {
    private final String a;
    private final String b;
    private final zzq c;
    private final int d;

    private zzse(String str, String str2) {
        this.b = str2;
        this.a = str;
        this.c = new zzq(str);
        this.d = a();
    }

    public zzse(String str, String... strArr) {
        this(str, a(strArr));
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int a() {
        /*
            r9 = this;
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 7
            r2 = 2
            r3 = 23
            if (r0 != r3) goto L_0x0089
            java.lang.String r0 = "log.tag."
            java.lang.String r3 = r9.a
            java.lang.String r3 = java.lang.String.valueOf(r3)
            int r4 = r3.length()
            if (r4 == 0) goto L_0x001b
            java.lang.String r0 = r0.concat(r3)
            goto L_0x0021
        L_0x001b:
            java.lang.String r3 = new java.lang.String
            r3.<init>(r0)
            r0 = r3
        L_0x0021:
            java.lang.String r0 = java.lang.System.getProperty(r0)
            if (r0 == 0) goto L_0x0089
            r3 = -1
            int r4 = r0.hashCode()
            r5 = 6
            r6 = 3
            r7 = 5
            r8 = 4
            switch(r4) {
                case -880503115: goto L_0x0070;
                case 2251950: goto L_0x0066;
                case 2656902: goto L_0x005c;
                case 64921139: goto L_0x0052;
                case 66247144: goto L_0x0048;
                case 1069090146: goto L_0x003e;
                case 1940088646: goto L_0x0034;
                default: goto L_0x0033;
            }
        L_0x0033:
            goto L_0x007a
        L_0x0034:
            java.lang.String r4 = "ASSERT"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x007a
            r0 = 5
            goto L_0x007b
        L_0x003e:
            java.lang.String r4 = "VERBOSE"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x007a
            r0 = 0
            goto L_0x007b
        L_0x0048:
            java.lang.String r4 = "ERROR"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x007a
            r0 = 4
            goto L_0x007b
        L_0x0052:
            java.lang.String r4 = "DEBUG"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x007a
            r0 = 1
            goto L_0x007b
        L_0x005c:
            java.lang.String r4 = "WARN"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x007a
            r0 = 3
            goto L_0x007b
        L_0x0066:
            java.lang.String r4 = "INFO"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x007a
            r0 = 2
            goto L_0x007b
        L_0x0070:
            java.lang.String r4 = "SUPPRESS"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x007a
            r0 = 6
            goto L_0x007b
        L_0x007a:
            r0 = -1
        L_0x007b:
            switch(r0) {
                case 0: goto L_0x0088;
                case 1: goto L_0x0087;
                case 2: goto L_0x0086;
                case 3: goto L_0x0085;
                case 4: goto L_0x0084;
                case 5: goto L_0x0083;
                case 6: goto L_0x007f;
                default: goto L_0x007e;
            }
        L_0x007e:
            return r8
        L_0x007f:
            r0 = 2147483647(0x7fffffff, float:NaN)
            return r0
        L_0x0083:
            return r1
        L_0x0084:
            return r5
        L_0x0085:
            return r7
        L_0x0086:
            return r8
        L_0x0087:
            return r6
        L_0x0088:
            return r2
        L_0x0089:
            if (r1 < r2) goto L_0x0096
            java.lang.String r0 = r9.a
            boolean r0 = android.util.Log.isLoggable(r0, r2)
            if (r0 != 0) goto L_0x0096
            int r2 = r2 + 1
            goto L_0x0089
        L_0x0096:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzse.a():int");
    }

    private static String a(String... strArr) {
        if (strArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (String str : strArr) {
            if (sb.length() > 1) {
                sb.append(",");
            }
            sb.append(str);
        }
        sb.append(']');
        sb.append(TokenParser.SP);
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public String format(String str, Object... objArr) {
        if (objArr != null && objArr.length > 0) {
            str = String.format(str, objArr);
        }
        return this.b.concat(str);
    }

    public void zza(String str, Object... objArr) {
        if (zzbf(2)) {
            Log.v(this.a, format(str, objArr));
        }
    }

    public boolean zzbf(int i) {
        return this.d <= i;
    }

    public void zze(String str, Object... objArr) {
        Log.i(this.a, format(str, objArr));
    }

    public void zzf(String str, Object... objArr) {
        Log.w(this.a, format(str, objArr));
    }
}
