package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.common.util.zzw;
import com.google.android.gms.common.util.zzz;

public class zzxb {
    private static String a = "WakeLock";
    private static String b = "*gcore*:";
    private static boolean c = false;
    private final WakeLock d;
    private WorkSource e;
    private final int f;
    private final String g;
    private final String h;
    private final String i;
    private final Context j;
    private boolean k;
    private int l;
    private int m;

    public zzxb(Context context, int i2, String str) {
        this(context, i2, str, null, context == null ? null : context.getPackageName());
    }

    @SuppressLint({"UnwrappedWakeLock"})
    public zzxb(Context context, int i2, String str, String str2, String str3) {
        this(context, i2, str, str2, str3, null);
    }

    @SuppressLint({"UnwrappedWakeLock"})
    public zzxb(Context context, int i2, String str, String str2, String str3, String str4) {
        this.k = true;
        zzac.zzh(str, "Wake lock name can NOT be empty");
        this.f = i2;
        this.h = str2;
        this.i = str4;
        this.j = context.getApplicationContext();
        if (!"com.google.android.gms".equals(context.getPackageName())) {
            String valueOf = String.valueOf(b);
            String valueOf2 = String.valueOf(str);
            this.g = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        } else {
            this.g = str;
        }
        this.d = ((PowerManager) context.getSystemService("power")).newWakeLock(i2, str);
        if (zzz.zzcp(this.j)) {
            if (zzw.zzij(str3)) {
                str3 = context.getPackageName();
            }
            this.e = zzz.zzy(context, str3);
            zzc(this.e);
        }
    }

    private String a(String str, boolean z) {
        return (!this.k || !z) ? this.h : str;
    }

    private void a(WorkSource workSource) {
        try {
            this.d.setWorkSource(workSource);
        } catch (IllegalArgumentException e2) {
            Log.wtf(a, e2.toString());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        if (r11.m == 1) goto L_0x001f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0015, code lost:
        if (r0 == false) goto L_0x0017;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.lang.String r12) {
        /*
            r11 = this;
            boolean r0 = r11.b(r12)
            java.lang.String r6 = r11.a(r12, r0)
            monitor-enter(r11)
            boolean r12 = r11.k     // Catch:{ all -> 0x0043 }
            r10 = 1
            if (r12 == 0) goto L_0x0017
            int r12 = r11.l     // Catch:{ all -> 0x0043 }
            int r12 = r12 - r10
            r11.l = r12     // Catch:{ all -> 0x0043 }
            if (r12 == 0) goto L_0x001f
            if (r0 != 0) goto L_0x001f
        L_0x0017:
            boolean r12 = r11.k     // Catch:{ all -> 0x0043 }
            if (r12 != 0) goto L_0x0041
            int r12 = r11.m     // Catch:{ all -> 0x0043 }
            if (r12 != r10) goto L_0x0041
        L_0x001f:
            com.google.android.gms.common.stats.zzh r1 = com.google.android.gms.common.stats.zzh.zzaxf()     // Catch:{ all -> 0x0043 }
            android.content.Context r2 = r11.j     // Catch:{ all -> 0x0043 }
            android.os.PowerManager$WakeLock r12 = r11.d     // Catch:{ all -> 0x0043 }
            java.lang.String r3 = com.google.android.gms.common.stats.zzf.zza(r12, r6)     // Catch:{ all -> 0x0043 }
            r4 = 8
            java.lang.String r5 = r11.g     // Catch:{ all -> 0x0043 }
            java.lang.String r7 = r11.i     // Catch:{ all -> 0x0043 }
            int r8 = r11.f     // Catch:{ all -> 0x0043 }
            android.os.WorkSource r12 = r11.e     // Catch:{ all -> 0x0043 }
            java.util.List r9 = com.google.android.gms.common.util.zzz.zzb(r12)     // Catch:{ all -> 0x0043 }
            r1.zza(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0043 }
            int r12 = r11.m     // Catch:{ all -> 0x0043 }
            int r12 = r12 - r10
            r11.m = r12     // Catch:{ all -> 0x0043 }
        L_0x0041:
            monitor-exit(r11)     // Catch:{ all -> 0x0043 }
            return
        L_0x0043:
            r12 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0043 }
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzxb.a(java.lang.String):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        if (r12.m == 0) goto L_0x001f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0015, code lost:
        if (r0 == false) goto L_0x0017;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.lang.String r13, long r14) {
        /*
            r12 = this;
            boolean r0 = r12.b(r13)
            java.lang.String r6 = r12.a(r13, r0)
            monitor-enter(r12)
            boolean r13 = r12.k     // Catch:{ all -> 0x0044 }
            if (r13 == 0) goto L_0x0017
            int r13 = r12.l     // Catch:{ all -> 0x0044 }
            int r1 = r13 + 1
            r12.l = r1     // Catch:{ all -> 0x0044 }
            if (r13 == 0) goto L_0x001f
            if (r0 != 0) goto L_0x001f
        L_0x0017:
            boolean r13 = r12.k     // Catch:{ all -> 0x0044 }
            if (r13 != 0) goto L_0x0042
            int r13 = r12.m     // Catch:{ all -> 0x0044 }
            if (r13 != 0) goto L_0x0042
        L_0x001f:
            com.google.android.gms.common.stats.zzh r1 = com.google.android.gms.common.stats.zzh.zzaxf()     // Catch:{ all -> 0x0044 }
            android.content.Context r2 = r12.j     // Catch:{ all -> 0x0044 }
            android.os.PowerManager$WakeLock r13 = r12.d     // Catch:{ all -> 0x0044 }
            java.lang.String r3 = com.google.android.gms.common.stats.zzf.zza(r13, r6)     // Catch:{ all -> 0x0044 }
            r4 = 7
            java.lang.String r5 = r12.g     // Catch:{ all -> 0x0044 }
            java.lang.String r7 = r12.i     // Catch:{ all -> 0x0044 }
            int r8 = r12.f     // Catch:{ all -> 0x0044 }
            android.os.WorkSource r13 = r12.e     // Catch:{ all -> 0x0044 }
            java.util.List r9 = com.google.android.gms.common.util.zzz.zzb(r13)     // Catch:{ all -> 0x0044 }
            r10 = r14
            r1.zza(r2, r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x0044 }
            int r13 = r12.m     // Catch:{ all -> 0x0044 }
            int r13 = r13 + 1
            r12.m = r13     // Catch:{ all -> 0x0044 }
        L_0x0042:
            monitor-exit(r12)     // Catch:{ all -> 0x0044 }
            return
        L_0x0044:
            r13 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x0044 }
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzxb.a(java.lang.String, long):void");
    }

    private boolean b(String str) {
        return !TextUtils.isEmpty(str) && !str.equals(this.h);
    }

    public void acquire(long j2) {
        if (!zzs.zzaxn() && this.k) {
            String str = a;
            String str2 = "Do not acquire with timeout on reference counted WakeLocks before ICS. wakelock: ";
            String valueOf = String.valueOf(this.g);
            Log.wtf(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
        a((String) null, j2);
        this.d.acquire(j2);
    }

    public boolean isHeld() {
        return this.d.isHeld();
    }

    public void release() {
        a((String) null);
        this.d.release();
    }

    public void setReferenceCounted(boolean z) {
        this.d.setReferenceCounted(z);
        this.k = z;
    }

    public void zzc(WorkSource workSource) {
        if (workSource != null && zzz.zzcp(this.j)) {
            if (this.e != null) {
                this.e.add(workSource);
            } else {
                this.e = workSource;
            }
            a(this.e);
        }
    }
}
