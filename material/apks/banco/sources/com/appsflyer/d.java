package com.appsflyer;

import android.content.Context;
import android.support.annotation.NonNull;
import java.lang.ref.WeakReference;

final class d {
    private final Object a;
    private long b;
    private String c;

    d() {
    }

    static void a(Context context) {
        Context applicationContext = context.getApplicationContext();
        AFLogger.afInfoLog("onBecameBackground");
        AppsFlyerLib.getInstance().b();
        AFLogger.afInfoLog("callStatsBackground background call");
        AppsFlyerLib.getInstance().a(new WeakReference<>(applicationContext));
        y a2 = y.a();
        if (a2.g()) {
            a2.c();
            if (applicationContext != null) {
                y.a(applicationContext.getPackageName(), applicationContext.getPackageManager());
            }
            a2.d();
        } else {
            AFLogger.afDebugLog("RD status is OFF");
        }
        AFExecutor.getInstance().b();
    }

    d(long j, String str) {
        this.a = new Object();
        this.b = 0;
        this.c = "";
        this.b = j;
        this.c = str;
    }

    d(String str) {
        this(System.currentTimeMillis(), str);
    }

    @NonNull
    static d a(String str) {
        if (str == null) {
            return new d(0, "");
        }
        String[] split = str.split(",");
        if (split.length < 2) {
            return new d(0, "");
        }
        return new d(Long.parseLong(split[0]), split[1]);
    }

    /* access modifiers changed from: 0000 */
    public final boolean a(d dVar) {
        return a(dVar.b, dVar.c);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0029, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(long r8, java.lang.String r10) {
        /*
            r7 = this;
            java.lang.Object r0 = r7.a
            monitor-enter(r0)
            r1 = 0
            if (r10 == 0) goto L_0x0028
            java.lang.String r2 = r7.c     // Catch:{ all -> 0x0025 }
            boolean r2 = r10.equals(r2)     // Catch:{ all -> 0x0025 }
            if (r2 != 0) goto L_0x0028
            long r2 = r7.b     // Catch:{ all -> 0x0025 }
            r4 = 0
            long r4 = r8 - r2
            r2 = 2000(0x7d0, double:9.88E-321)
            int r6 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            r2 = 1
            if (r6 <= 0) goto L_0x001c
            r3 = 1
            goto L_0x001d
        L_0x001c:
            r3 = 0
        L_0x001d:
            if (r3 == 0) goto L_0x0028
            r7.b = r8     // Catch:{ all -> 0x0025 }
            r7.c = r10     // Catch:{ all -> 0x0025 }
            monitor-exit(r0)     // Catch:{ all -> 0x0025 }
            return r2
        L_0x0025:
            r8 = move-exception
            monitor-exit(r0)
            throw r8
        L_0x0028:
            monitor-exit(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.d.a(long, java.lang.String):boolean");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b);
        sb.append(",");
        sb.append(this.c);
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public final String a() {
        return this.c;
    }
}
