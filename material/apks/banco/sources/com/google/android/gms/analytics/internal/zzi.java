package com.google.android.gms.analytics.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.stats.zzb;
import java.util.Collections;

public class zzi extends zzd {
    /* access modifiers changed from: private */
    public final zza a = new zza();
    private zzac b;
    private final zzt c;
    private zzal d;

    public class zza implements ServiceConnection {
        private volatile zzac b;
        private volatile boolean c;

        protected zza() {
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(9:14|15|16|17|(2:25|26)(3:27|28|(1:30)(1:31))|32|33|34|35) */
        /* JADX WARNING: Can't wrap try/catch for region: R(9:20|21|22|23|(0)(0)|32|33|34|35) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x003a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x0073 */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x0043 A[SYNTHETIC, Splitter:B:25:0x0043] */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x0057 A[SYNTHETIC, Splitter:B:27:0x0057] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onServiceConnected(android.content.ComponentName r3, android.os.IBinder r4) {
            /*
                r2 = this;
                java.lang.String r3 = "AnalyticsServiceConnection.onServiceConnected"
                com.google.android.gms.common.internal.zzac.zzhq(r3)
                monitor-enter(r2)
                if (r4 != 0) goto L_0x0016
                com.google.android.gms.analytics.internal.zzi r3 = com.google.android.gms.analytics.internal.zzi.this     // Catch:{ all -> 0x0014 }
                java.lang.String r4 = "Service connected with null binder"
                r3.zzet(r4)     // Catch:{ all -> 0x0014 }
                r2.notifyAll()     // Catch:{ all -> 0x007c }
                monitor-exit(r2)     // Catch:{ all -> 0x007c }
                return
            L_0x0014:
                r3 = move-exception
                goto L_0x0078
            L_0x0016:
                r3 = 0
                java.lang.String r0 = r4.getInterfaceDescriptor()     // Catch:{ RemoteException -> 0x003a }
                java.lang.String r1 = "com.google.android.gms.analytics.internal.IAnalyticsService"
                boolean r1 = r1.equals(r0)     // Catch:{ RemoteException -> 0x003a }
                if (r1 == 0) goto L_0x0032
                com.google.android.gms.analytics.internal.zzac r4 = com.google.android.gms.analytics.internal.zzac.zza.zzbm(r4)     // Catch:{ RemoteException -> 0x003a }
                com.google.android.gms.analytics.internal.zzi r3 = com.google.android.gms.analytics.internal.zzi.this     // Catch:{ RemoteException -> 0x0030 }
                java.lang.String r0 = "Bound to IAnalyticsService interface"
                r3.zzep(r0)     // Catch:{ RemoteException -> 0x0030 }
                r3 = r4
                goto L_0x0041
            L_0x0030:
                r3 = r4
                goto L_0x003a
            L_0x0032:
                com.google.android.gms.analytics.internal.zzi r4 = com.google.android.gms.analytics.internal.zzi.this     // Catch:{ RemoteException -> 0x003a }
                java.lang.String r1 = "Got binder with a wrong descriptor"
                r4.zze(r1, r0)     // Catch:{ RemoteException -> 0x003a }
                goto L_0x0041
            L_0x003a:
                com.google.android.gms.analytics.internal.zzi r4 = com.google.android.gms.analytics.internal.zzi.this     // Catch:{ all -> 0x0014 }
                java.lang.String r0 = "Service connect failed to get IAnalyticsService"
                r4.zzet(r0)     // Catch:{ all -> 0x0014 }
            L_0x0041:
                if (r3 != 0) goto L_0x0057
                com.google.android.gms.common.stats.zzb r3 = com.google.android.gms.common.stats.zzb.zzawu()     // Catch:{ IllegalArgumentException -> 0x0073 }
                com.google.android.gms.analytics.internal.zzi r4 = com.google.android.gms.analytics.internal.zzi.this     // Catch:{ IllegalArgumentException -> 0x0073 }
                android.content.Context r4 = r4.getContext()     // Catch:{ IllegalArgumentException -> 0x0073 }
                com.google.android.gms.analytics.internal.zzi r0 = com.google.android.gms.analytics.internal.zzi.this     // Catch:{ IllegalArgumentException -> 0x0073 }
                com.google.android.gms.analytics.internal.zzi$zza r0 = r0.a     // Catch:{ IllegalArgumentException -> 0x0073 }
                r3.zza(r4, r0)     // Catch:{ IllegalArgumentException -> 0x0073 }
                goto L_0x0073
            L_0x0057:
                boolean r4 = r2.c     // Catch:{ all -> 0x0014 }
                if (r4 != 0) goto L_0x0071
                com.google.android.gms.analytics.internal.zzi r4 = com.google.android.gms.analytics.internal.zzi.this     // Catch:{ all -> 0x0014 }
                java.lang.String r0 = "onServiceConnected received after the timeout limit"
                r4.zzes(r0)     // Catch:{ all -> 0x0014 }
                com.google.android.gms.analytics.internal.zzi r4 = com.google.android.gms.analytics.internal.zzi.this     // Catch:{ all -> 0x0014 }
                com.google.android.gms.analytics.zzi r4 = r4.zzaaq()     // Catch:{ all -> 0x0014 }
                com.google.android.gms.analytics.internal.zzi$zza$1 r0 = new com.google.android.gms.analytics.internal.zzi$zza$1     // Catch:{ all -> 0x0014 }
                r0.<init>(r3)     // Catch:{ all -> 0x0014 }
                r4.zzg(r0)     // Catch:{ all -> 0x0014 }
                goto L_0x0073
            L_0x0071:
                r2.b = r3     // Catch:{ all -> 0x0014 }
            L_0x0073:
                r2.notifyAll()     // Catch:{ all -> 0x007c }
                monitor-exit(r2)     // Catch:{ all -> 0x007c }
                return
            L_0x0078:
                r2.notifyAll()     // Catch:{ all -> 0x007c }
                throw r3     // Catch:{ all -> 0x007c }
            L_0x007c:
                r3 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x007c }
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzi.zza.onServiceConnected(android.content.ComponentName, android.os.IBinder):void");
        }

        public void onServiceDisconnected(final ComponentName componentName) {
            zzac.zzhq("AnalyticsServiceConnection.onServiceDisconnected");
            zzi.this.zzaaq().zzg(new Runnable() {
                public void run() {
                    zzi.this.a(componentName);
                }
            });
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(6:9|10|11|12|13|(1:15)) */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0075, code lost:
            return r0;
         */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x005e */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.google.android.gms.analytics.internal.zzac zzabm() {
            /*
                r6 = this;
                com.google.android.gms.analytics.internal.zzi r0 = com.google.android.gms.analytics.internal.zzi.this
                r0.zzyl()
                android.content.Intent r0 = new android.content.Intent
                java.lang.String r1 = "com.google.android.gms.analytics.service.START"
                r0.<init>(r1)
                android.content.ComponentName r1 = new android.content.ComponentName
                java.lang.String r2 = "com.google.android.gms"
                java.lang.String r3 = "com.google.android.gms.analytics.service.AnalyticsService"
                r1.<init>(r2, r3)
                r0.setComponent(r1)
                com.google.android.gms.analytics.internal.zzi r1 = com.google.android.gms.analytics.internal.zzi.this
                android.content.Context r1 = r1.getContext()
                java.lang.String r2 = "app_package_name"
                java.lang.String r3 = r1.getPackageName()
                r0.putExtra(r2, r3)
                com.google.android.gms.common.stats.zzb r2 = com.google.android.gms.common.stats.zzb.zzawu()
                monitor-enter(r6)
                r3 = 0
                r6.b = r3     // Catch:{ all -> 0x0076 }
                r4 = 1
                r6.c = r4     // Catch:{ all -> 0x0076 }
                com.google.android.gms.analytics.internal.zzi r4 = com.google.android.gms.analytics.internal.zzi.this     // Catch:{ all -> 0x0076 }
                com.google.android.gms.analytics.internal.zzi$zza r4 = r4.a     // Catch:{ all -> 0x0076 }
                r5 = 129(0x81, float:1.81E-43)
                boolean r0 = r2.zza(r1, r0, r4, r5)     // Catch:{ all -> 0x0076 }
                com.google.android.gms.analytics.internal.zzi r1 = com.google.android.gms.analytics.internal.zzi.this     // Catch:{ all -> 0x0076 }
                java.lang.String r2 = "Bind to service requested"
                java.lang.Boolean r4 = java.lang.Boolean.valueOf(r0)     // Catch:{ all -> 0x0076 }
                r1.zza(r2, r4)     // Catch:{ all -> 0x0076 }
                r1 = 0
                if (r0 != 0) goto L_0x0050
                r6.c = r1     // Catch:{ all -> 0x0076 }
                monitor-exit(r6)     // Catch:{ all -> 0x0076 }
                return r3
            L_0x0050:
                com.google.android.gms.analytics.internal.zzi r0 = com.google.android.gms.analytics.internal.zzi.this     // Catch:{ InterruptedException -> 0x005e }
                com.google.android.gms.analytics.internal.zzr r0 = r0.zzaap()     // Catch:{ InterruptedException -> 0x005e }
                long r4 = r0.zzadp()     // Catch:{ InterruptedException -> 0x005e }
                r6.wait(r4)     // Catch:{ InterruptedException -> 0x005e }
                goto L_0x0065
            L_0x005e:
                com.google.android.gms.analytics.internal.zzi r0 = com.google.android.gms.analytics.internal.zzi.this     // Catch:{ all -> 0x0076 }
                java.lang.String r2 = "Wait for service connect was interrupted"
                r0.zzes(r2)     // Catch:{ all -> 0x0076 }
            L_0x0065:
                r6.c = r1     // Catch:{ all -> 0x0076 }
                com.google.android.gms.analytics.internal.zzac r0 = r6.b     // Catch:{ all -> 0x0076 }
                r6.b = r3     // Catch:{ all -> 0x0076 }
                if (r0 != 0) goto L_0x0074
                com.google.android.gms.analytics.internal.zzi r1 = com.google.android.gms.analytics.internal.zzi.this     // Catch:{ all -> 0x0076 }
                java.lang.String r2 = "Successfully bound to service but never got onServiceConnected callback"
                r1.zzet(r2)     // Catch:{ all -> 0x0076 }
            L_0x0074:
                monitor-exit(r6)     // Catch:{ all -> 0x0076 }
                return r0
            L_0x0076:
                r0 = move-exception
                monitor-exit(r6)     // Catch:{ all -> 0x0076 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzi.zza.zzabm():com.google.android.gms.analytics.internal.zzac");
        }
    }

    protected zzi(zzf zzf) {
        super(zzf);
        this.d = new zzal(zzf.zzaan());
        this.c = new zzt(zzf) {
            public void a() {
                zzi.this.b();
            }
        };
    }

    private void a() {
        this.d.a();
        this.c.a(zzaap().zzado());
    }

    /* access modifiers changed from: private */
    public void a(ComponentName componentName) {
        zzyl();
        if (this.b != null) {
            this.b = null;
            zza("Disconnected from device AnalyticsService", componentName);
            c();
        }
    }

    /* access modifiers changed from: private */
    public void a(zzac zzac) {
        zzyl();
        this.b = zzac;
        a();
        zzxu().a();
    }

    /* access modifiers changed from: private */
    public void b() {
        zzyl();
        if (isConnected()) {
            zzep("Inactivity, disconnecting from device AnalyticsService");
            disconnect();
        }
    }

    private void c() {
        zzxu().zzaai();
    }

    public boolean connect() {
        zzyl();
        zzaax();
        if (this.b != null) {
            return true;
        }
        zzac zzabm = this.a.zzabm();
        if (zzabm == null) {
            return false;
        }
        this.b = zzabm;
        a();
        return true;
    }

    public void disconnect() {
        zzyl();
        zzaax();
        try {
            zzb.zzawu().zza(getContext(), this.a);
        } catch (IllegalArgumentException | IllegalStateException unused) {
        }
        if (this.b != null) {
            this.b = null;
            c();
        }
    }

    public boolean isConnected() {
        zzyl();
        zzaax();
        return this.b != null;
    }

    public boolean zzabj() {
        zzyl();
        zzaax();
        zzac zzac = this.b;
        if (zzac == null) {
            return false;
        }
        try {
            zzac.zzaaf();
            a();
            return true;
        } catch (RemoteException unused) {
            zzep("Failed to clear hits from AnalyticsService");
            return false;
        }
    }

    public boolean zzb(zzab zzab) {
        zzac.zzy(zzab);
        zzyl();
        zzaax();
        zzac zzac = this.b;
        if (zzac == null) {
            return false;
        }
        try {
            zzac.zza(zzab.zzm(), zzab.zzaeq(), zzab.zzaes() ? zzaap().zzadh() : zzaap().zzadi(), Collections.emptyList());
            a();
            return true;
        } catch (RemoteException unused) {
            zzep("Failed to send hits to AnalyticsService");
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void zzym() {
    }
}
