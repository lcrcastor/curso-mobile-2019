package com.appsflyer;

import android.content.Context;
import android.support.annotation.NonNull;

final class j {

    static final class d {
        static final j a = new j();
    }

    j() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0045, code lost:
        if (r9 != null) goto L_0x005a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0057, code lost:
        if (60000 >= (r2.getTime() - r9.getTime())) goto L_0x005a;
     */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static android.location.Location a(@android.support.annotation.NonNull android.content.Context r9) {
        /*
            r0 = 0
            java.lang.String r1 = "location"
            java.lang.Object r1 = r9.getSystemService(r1)     // Catch:{ Throwable -> 0x005d }
            android.location.LocationManager r1 = (android.location.LocationManager) r1     // Catch:{ Throwable -> 0x005d }
            java.lang.String r2 = "network"
            r3 = 2
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ Throwable -> 0x005d }
            java.lang.String r4 = "android.permission.ACCESS_FINE_LOCATION"
            r5 = 0
            r3[r5] = r4     // Catch:{ Throwable -> 0x005d }
            java.lang.String r4 = "android.permission.ACCESS_COARSE_LOCATION"
            r6 = 1
            r3[r6] = r4     // Catch:{ Throwable -> 0x005d }
            boolean r3 = a(r9, r3)     // Catch:{ Throwable -> 0x005d }
            if (r3 == 0) goto L_0x0023
            android.location.Location r2 = r1.getLastKnownLocation(r2)     // Catch:{ Throwable -> 0x005d }
            goto L_0x0024
        L_0x0023:
            r2 = r0
        L_0x0024:
            java.lang.String r3 = "gps"
            java.lang.String[] r4 = new java.lang.String[r6]     // Catch:{ Throwable -> 0x005d }
            java.lang.String r6 = "android.permission.ACCESS_FINE_LOCATION"
            r4[r5] = r6     // Catch:{ Throwable -> 0x005d }
            boolean r9 = a(r9, r4)     // Catch:{ Throwable -> 0x005d }
            if (r9 == 0) goto L_0x0037
            android.location.Location r9 = r1.getLastKnownLocation(r3)     // Catch:{ Throwable -> 0x005d }
            goto L_0x0038
        L_0x0037:
            r9 = r0
        L_0x0038:
            if (r9 != 0) goto L_0x003e
            if (r2 != 0) goto L_0x003e
            r9 = r0
            goto L_0x005a
        L_0x003e:
            if (r9 != 0) goto L_0x0043
            if (r2 == 0) goto L_0x0043
            goto L_0x0059
        L_0x0043:
            if (r2 != 0) goto L_0x0047
            if (r9 != 0) goto L_0x005a
        L_0x0047:
            long r3 = r2.getTime()     // Catch:{ Throwable -> 0x005d }
            long r5 = r9.getTime()     // Catch:{ Throwable -> 0x005d }
            r1 = 0
            long r7 = r3 - r5
            r3 = 60000(0xea60, double:2.9644E-319)
            int r1 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r1 >= 0) goto L_0x005a
        L_0x0059:
            r9 = r2
        L_0x005a:
            if (r9 == 0) goto L_0x005d
            r0 = r9
        L_0x005d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.j.a(android.content.Context):android.location.Location");
    }

    private static boolean a(@NonNull Context context, @NonNull String[] strArr) {
        for (String a : strArr) {
            if (a.a(context, a)) {
                return true;
            }
        }
        return false;
    }
}
