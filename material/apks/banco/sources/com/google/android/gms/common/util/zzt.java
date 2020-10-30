package com.google.android.gms.common.util;

import android.os.Binder;
import android.os.Process;

public class zzt {
    private static String a;

    /* JADX WARNING: Removed duplicated region for block: B:20:0x004d A[SYNTHETIC, Splitter:B:20:0x004d] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0061 A[SYNTHETIC, Splitter:B:29:0x0061] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(int r5) {
        /*
            r0 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0040, all -> 0x003e }
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ IOException -> 0x0040, all -> 0x003e }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0040, all -> 0x003e }
            r4 = 25
            r3.<init>(r4)     // Catch:{ IOException -> 0x0040, all -> 0x003e }
            java.lang.String r4 = "/proc/"
            r3.append(r4)     // Catch:{ IOException -> 0x0040, all -> 0x003e }
            r3.append(r5)     // Catch:{ IOException -> 0x0040, all -> 0x003e }
            java.lang.String r5 = "/cmdline"
            r3.append(r5)     // Catch:{ IOException -> 0x0040, all -> 0x003e }
            java.lang.String r5 = r3.toString()     // Catch:{ IOException -> 0x0040, all -> 0x003e }
            r2.<init>(r5)     // Catch:{ IOException -> 0x0040, all -> 0x003e }
            r1.<init>(r2)     // Catch:{ IOException -> 0x0040, all -> 0x003e }
            java.lang.String r5 = r1.readLine()     // Catch:{ IOException -> 0x003c }
            java.lang.String r5 = r5.trim()     // Catch:{ IOException -> 0x003c }
            if (r1 == 0) goto L_0x005c
            r1.close()     // Catch:{ Exception -> 0x0031 }
            return r5
        L_0x0031:
            r0 = move-exception
            java.lang.String r1 = "ProcessUtils"
            java.lang.String r2 = r0.getMessage()
            android.util.Log.w(r1, r2, r0)
            return r5
        L_0x003c:
            r5 = move-exception
            goto L_0x0042
        L_0x003e:
            r5 = move-exception
            goto L_0x005f
        L_0x0040:
            r5 = move-exception
            r1 = r0
        L_0x0042:
            java.lang.String r2 = "ProcessUtils"
            java.lang.String r3 = r5.getMessage()     // Catch:{ all -> 0x005d }
            android.util.Log.e(r2, r3, r5)     // Catch:{ all -> 0x005d }
            if (r1 == 0) goto L_0x005b
            r1.close()     // Catch:{ Exception -> 0x0051 }
            goto L_0x005b
        L_0x0051:
            r5 = move-exception
            java.lang.String r1 = "ProcessUtils"
            java.lang.String r2 = r5.getMessage()
            android.util.Log.w(r1, r2, r5)
        L_0x005b:
            r5 = r0
        L_0x005c:
            return r5
        L_0x005d:
            r5 = move-exception
            r0 = r1
        L_0x005f:
            if (r0 == 0) goto L_0x006f
            r0.close()     // Catch:{ Exception -> 0x0065 }
            goto L_0x006f
        L_0x0065:
            r0 = move-exception
            java.lang.String r1 = "ProcessUtils"
            java.lang.String r2 = r0.getMessage()
            android.util.Log.w(r1, r2, r0)
        L_0x006f:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.zzt.a(int):java.lang.String");
    }

    public static String zzaxx() {
        return a(Binder.getCallingPid());
    }

    public static String zzaxy() {
        if (a == null) {
            a = a(Process.myPid());
        }
        return a;
    }
}
