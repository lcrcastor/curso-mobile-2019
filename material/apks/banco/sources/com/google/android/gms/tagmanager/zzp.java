package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Looper;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.internal.zzaff;
import com.google.android.gms.internal.zzah.zzj;
import com.google.android.gms.internal.zzqe;

public class zzp extends zzqe<ContainerHolder> {
    /* access modifiers changed from: private */
    public final com.google.android.gms.common.util.zze b;
    private final zzd c;
    private final Looper d;
    /* access modifiers changed from: private */
    public final zzcl e;
    private final int f;
    private final Context g;
    private final TagManager h;
    private final String i;
    /* access modifiers changed from: private */
    public final zzq j;
    private zzf k;
    private zzaff l;
    /* access modifiers changed from: private */
    public volatile zzo m;
    /* access modifiers changed from: private */
    public volatile boolean n;
    /* access modifiers changed from: private */
    public zzj o;
    /* access modifiers changed from: private */
    public long p;
    private String q;
    private zze r;
    private zza s;

    /* renamed from: com.google.android.gms.tagmanager.zzp$1 reason: invalid class name */
    class AnonymousClass1 {
    }

    interface zza {
        boolean a(Container container);
    }

    class zzb implements zzbn<com.google.android.gms.internal.zzafe.zza> {
        private zzb() {
        }

        /* synthetic */ zzb(zzp zzp, AnonymousClass1 r2) {
            this();
        }

        public void a() {
        }

        public void a(com.google.android.gms.internal.zzafe.zza zza) {
            zzj zzj;
            if (zza.aJk != null) {
                zzj = zza.aJk;
            } else {
                com.google.android.gms.internal.zzah.zzf zzf = zza.zzxr;
                zzj zzj2 = new zzj();
                zzj2.zzxr = zzf;
                zzj2.zzxq = null;
                zzj2.zzxs = zzf.version;
                zzj = zzj2;
            }
            zzp.this.a(zzj, zza.aJj, true);
        }

        public void a(com.google.android.gms.tagmanager.zzbn.zza zza) {
            if (!zzp.this.n) {
                zzp.this.a(0);
            }
        }
    }

    class zzc implements zzbn<zzj> {
        private zzc() {
        }

        /* synthetic */ zzc(zzp zzp, AnonymousClass1 r2) {
            this();
        }

        public void a() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0074, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(com.google.android.gms.internal.zzah.zzj r6) {
            /*
                r5 = this;
                com.google.android.gms.tagmanager.zzp r0 = com.google.android.gms.tagmanager.zzp.this
                com.google.android.gms.tagmanager.zzq r0 = r0.j
                r0.zzcel()
                com.google.android.gms.tagmanager.zzp r0 = com.google.android.gms.tagmanager.zzp.this
                monitor-enter(r0)
                com.google.android.gms.internal.zzah$zzf r1 = r6.zzxr     // Catch:{ all -> 0x0075 }
                if (r1 != 0) goto L_0x003a
                com.google.android.gms.tagmanager.zzp r1 = com.google.android.gms.tagmanager.zzp.this     // Catch:{ all -> 0x0075 }
                com.google.android.gms.internal.zzah$zzj r1 = r1.o     // Catch:{ all -> 0x0075 }
                com.google.android.gms.internal.zzah$zzf r1 = r1.zzxr     // Catch:{ all -> 0x0075 }
                if (r1 != 0) goto L_0x0030
                java.lang.String r6 = "Current resource is null; network resource is also null"
                com.google.android.gms.tagmanager.zzbo.e(r6)     // Catch:{ all -> 0x0075 }
                com.google.android.gms.tagmanager.zzp r6 = com.google.android.gms.tagmanager.zzp.this     // Catch:{ all -> 0x0075 }
                com.google.android.gms.tagmanager.zzq r6 = r6.j     // Catch:{ all -> 0x0075 }
                long r1 = r6.zzcej()     // Catch:{ all -> 0x0075 }
                com.google.android.gms.tagmanager.zzp r6 = com.google.android.gms.tagmanager.zzp.this     // Catch:{ all -> 0x0075 }
                r6.a(r1)     // Catch:{ all -> 0x0075 }
                monitor-exit(r0)     // Catch:{ all -> 0x0075 }
                return
            L_0x0030:
                com.google.android.gms.tagmanager.zzp r1 = com.google.android.gms.tagmanager.zzp.this     // Catch:{ all -> 0x0075 }
                com.google.android.gms.internal.zzah$zzj r1 = r1.o     // Catch:{ all -> 0x0075 }
                com.google.android.gms.internal.zzah$zzf r1 = r1.zzxr     // Catch:{ all -> 0x0075 }
                r6.zzxr = r1     // Catch:{ all -> 0x0075 }
            L_0x003a:
                com.google.android.gms.tagmanager.zzp r1 = com.google.android.gms.tagmanager.zzp.this     // Catch:{ all -> 0x0075 }
                com.google.android.gms.tagmanager.zzp r2 = com.google.android.gms.tagmanager.zzp.this     // Catch:{ all -> 0x0075 }
                com.google.android.gms.common.util.zze r2 = r2.b     // Catch:{ all -> 0x0075 }
                long r2 = r2.currentTimeMillis()     // Catch:{ all -> 0x0075 }
                r4 = 0
                r1.a(r6, r2, r4)     // Catch:{ all -> 0x0075 }
                com.google.android.gms.tagmanager.zzp r1 = com.google.android.gms.tagmanager.zzp.this     // Catch:{ all -> 0x0075 }
                long r1 = r1.p     // Catch:{ all -> 0x0075 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0075 }
                r4 = 58
                r3.<init>(r4)     // Catch:{ all -> 0x0075 }
                java.lang.String r4 = "setting refresh time to current time: "
                r3.append(r4)     // Catch:{ all -> 0x0075 }
                r3.append(r1)     // Catch:{ all -> 0x0075 }
                java.lang.String r1 = r3.toString()     // Catch:{ all -> 0x0075 }
                com.google.android.gms.tagmanager.zzbo.v(r1)     // Catch:{ all -> 0x0075 }
                com.google.android.gms.tagmanager.zzp r1 = com.google.android.gms.tagmanager.zzp.this     // Catch:{ all -> 0x0075 }
                boolean r1 = r1.c()     // Catch:{ all -> 0x0075 }
                if (r1 != 0) goto L_0x0073
                com.google.android.gms.tagmanager.zzp r1 = com.google.android.gms.tagmanager.zzp.this     // Catch:{ all -> 0x0075 }
                r1.a(r6)     // Catch:{ all -> 0x0075 }
            L_0x0073:
                monitor-exit(r0)     // Catch:{ all -> 0x0075 }
                return
            L_0x0075:
                r6 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0075 }
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzp.zzc.a(com.google.android.gms.internal.zzah$zzj):void");
        }

        public void a(com.google.android.gms.tagmanager.zzbn.zza zza) {
            zzp zzp;
            Result zzef;
            if (zza == com.google.android.gms.tagmanager.zzbn.zza.SERVER_UNAVAILABLE_ERROR) {
                zzp.this.j.zzcek();
            }
            synchronized (zzp.this) {
                if (!zzp.this.isReady()) {
                    if (zzp.this.m != null) {
                        zzp = zzp.this;
                        zzef = zzp.this.m;
                    } else {
                        zzp = zzp.this;
                        zzef = zzp.this.zzc(Status.wb);
                    }
                    zzp.zzc(zzef);
                }
            }
            zzp.this.a(zzp.this.j.zzcej());
        }
    }

    class zzd implements com.google.android.gms.tagmanager.zzo.zza {
        private zzd() {
        }

        /* synthetic */ zzd(zzp zzp, AnonymousClass1 r2) {
            this();
        }

        public String zzcdy() {
            return zzp.this.b();
        }

        public void zzcea() {
            if (zzp.this.e.a()) {
                zzp.this.a(0);
            }
        }

        public void zzoy(String str) {
            zzp.this.a(str);
        }
    }

    interface zze extends Releasable {
        void a(long j, String str);

        void a(zzbn<zzj> zzbn);

        void a(String str);
    }

    interface zzf extends Releasable {
        com.google.android.gms.internal.zzafg.zzc a(int i);

        void a();

        void a(com.google.android.gms.internal.zzafe.zza zza);

        void a(zzbn<com.google.android.gms.internal.zzafe.zza> zzbn);
    }

    zzp(Context context, TagManager tagManager, Looper looper, String str, int i2, zzf zzf2, zze zze2, zzaff zzaff, com.google.android.gms.common.util.zze zze3, zzcl zzcl, zzq zzq) {
        super(looper == null ? Looper.getMainLooper() : looper);
        this.g = context;
        this.h = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.d = looper;
        this.i = str;
        this.f = i2;
        this.k = zzf2;
        this.r = zze2;
        this.l = zzaff;
        this.c = new zzd(this, null);
        this.o = new zzj();
        this.b = zze3;
        this.e = zzcl;
        this.j = zzq;
        if (c()) {
            a(zzcj.a().c());
        }
    }

    public zzp(Context context, TagManager tagManager, Looper looper, String str, int i2, zzt zzt) {
        Context context2 = context;
        String str2 = str;
        zzcv zzcv = new zzcv(context2, str2);
        zzcu zzcu = new zzcu(context2, str2, zzt);
        zzaff zzaff = new zzaff(context2);
        com.google.android.gms.common.util.zze zzaxj = zzh.zzaxj();
        zzbm zzbm = new zzbm(1, 5, 900000, LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS, "refreshing", zzh.zzaxj());
        this(context2, tagManager, looper, str2, i2, zzcv, zzcu, zzaff, zzaxj, zzbm, new zzq(context2, str2));
        this.l.zzqz(zzt.a());
    }

    /* access modifiers changed from: private */
    public synchronized void a(long j2) {
        if (this.r == null) {
            zzbo.zzdf("Refresh requested, but no network load scheduler.");
        } else {
            this.r.a(j2, this.o.zzxs);
        }
    }

    /* access modifiers changed from: private */
    public synchronized void a(zzj zzj) {
        if (this.k != null) {
            com.google.android.gms.internal.zzafe.zza zza2 = new com.google.android.gms.internal.zzafe.zza();
            zza2.aJj = this.p;
            zza2.zzxr = new com.google.android.gms.internal.zzah.zzf();
            zza2.aJk = zzj;
            this.k.a(zza2);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0076, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(com.google.android.gms.internal.zzah.zzj r11, long r12, boolean r14) {
        /*
            r10 = this;
            monitor-enter(r10)
            if (r14 == 0) goto L_0x0008
            boolean r14 = r10.n     // Catch:{ all -> 0x0006 }
            goto L_0x0008
        L_0x0006:
            r11 = move-exception
            goto L_0x0077
        L_0x0008:
            boolean r14 = r10.isReady()     // Catch:{ all -> 0x0006 }
            if (r14 == 0) goto L_0x0014
            com.google.android.gms.tagmanager.zzo r14 = r10.m     // Catch:{ all -> 0x0006 }
            if (r14 != 0) goto L_0x0014
            monitor-exit(r10)
            return
        L_0x0014:
            r10.o = r11     // Catch:{ all -> 0x0006 }
            r10.p = r12     // Catch:{ all -> 0x0006 }
            com.google.android.gms.tagmanager.zzq r14 = r10.j     // Catch:{ all -> 0x0006 }
            long r0 = r14.zzcei()     // Catch:{ all -> 0x0006 }
            r2 = 0
            long r4 = r10.p     // Catch:{ all -> 0x0006 }
            r14 = 0
            long r6 = r4 + r0
            com.google.android.gms.common.util.zze r14 = r10.b     // Catch:{ all -> 0x0006 }
            long r4 = r14.currentTimeMillis()     // Catch:{ all -> 0x0006 }
            r14 = 0
            long r8 = r6 - r4
            long r0 = java.lang.Math.min(r0, r8)     // Catch:{ all -> 0x0006 }
            long r0 = java.lang.Math.max(r2, r0)     // Catch:{ all -> 0x0006 }
            r10.a(r0)     // Catch:{ all -> 0x0006 }
            com.google.android.gms.tagmanager.Container r14 = new com.google.android.gms.tagmanager.Container     // Catch:{ all -> 0x0006 }
            android.content.Context r3 = r10.g     // Catch:{ all -> 0x0006 }
            com.google.android.gms.tagmanager.TagManager r0 = r10.h     // Catch:{ all -> 0x0006 }
            com.google.android.gms.tagmanager.DataLayer r4 = r0.getDataLayer()     // Catch:{ all -> 0x0006 }
            java.lang.String r5 = r10.i     // Catch:{ all -> 0x0006 }
            r2 = r14
            r6 = r12
            r8 = r11
            r2.<init>(r3, r4, r5, r6, r8)     // Catch:{ all -> 0x0006 }
            com.google.android.gms.tagmanager.zzo r11 = r10.m     // Catch:{ all -> 0x0006 }
            if (r11 != 0) goto L_0x005d
            com.google.android.gms.tagmanager.zzo r11 = new com.google.android.gms.tagmanager.zzo     // Catch:{ all -> 0x0006 }
            com.google.android.gms.tagmanager.TagManager r12 = r10.h     // Catch:{ all -> 0x0006 }
            android.os.Looper r13 = r10.d     // Catch:{ all -> 0x0006 }
            com.google.android.gms.tagmanager.zzp$zzd r0 = r10.c     // Catch:{ all -> 0x0006 }
            r11.<init>(r12, r13, r14, r0)     // Catch:{ all -> 0x0006 }
            r10.m = r11     // Catch:{ all -> 0x0006 }
            goto L_0x0062
        L_0x005d:
            com.google.android.gms.tagmanager.zzo r11 = r10.m     // Catch:{ all -> 0x0006 }
            r11.a(r14)     // Catch:{ all -> 0x0006 }
        L_0x0062:
            boolean r11 = r10.isReady()     // Catch:{ all -> 0x0006 }
            if (r11 != 0) goto L_0x0075
            com.google.android.gms.tagmanager.zzp$zza r11 = r10.s     // Catch:{ all -> 0x0006 }
            boolean r11 = r11.a(r14)     // Catch:{ all -> 0x0006 }
            if (r11 == 0) goto L_0x0075
            com.google.android.gms.tagmanager.zzo r11 = r10.m     // Catch:{ all -> 0x0006 }
            r10.zzc(r11)     // Catch:{ all -> 0x0006 }
        L_0x0075:
            monitor-exit(r10)
            return
        L_0x0077:
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzp.a(com.google.android.gms.internal.zzah$zzj, long, boolean):void");
    }

    private void a(final boolean z) {
        this.k.a((zzbn<com.google.android.gms.internal.zzafe.zza>) new zzb<com.google.android.gms.internal.zzafe.zza>(this, null));
        this.r.a((zzbn<zzj>) new zzc<zzj>(this, null));
        com.google.android.gms.internal.zzafg.zzc a = this.k.a(this.f);
        if (a != null) {
            TagManager tagManager = this.h;
            Looper looper = this.d;
            Container container = new Container(this.g, this.h.getDataLayer(), this.i, 0, a);
            this.m = new zzo(tagManager, looper, container, this.c);
        }
        this.s = new zza() {
            private Long c;

            private long a() {
                if (this.c == null) {
                    this.c = Long.valueOf(zzp.this.j.zzcei());
                }
                return this.c.longValue();
            }

            public boolean a(Container container) {
                return z ? container.getLastRefreshTime() + a() >= zzp.this.b.currentTimeMillis() : !container.isDefault();
            }
        };
        if (c()) {
            this.r.a(0, "");
        } else {
            this.k.a();
        }
    }

    /* access modifiers changed from: private */
    public boolean c() {
        zzcj a = zzcj.a();
        return (a.b() == zza.CONTAINER || a.b() == zza.CONTAINER_DEBUG) && this.i.equals(a.d());
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(String str) {
        this.q = str;
        if (this.r != null) {
            this.r.a(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized String b() {
        return this.q;
    }

    public void zzceb() {
        com.google.android.gms.internal.zzafg.zzc a = this.k.a(this.f);
        if (a != null) {
            Container container = new Container(this.g, this.h.getDataLayer(), this.i, 0, a);
            zzc(new zzo(this.h, this.d, container, new com.google.android.gms.tagmanager.zzo.zza() {
                public String zzcdy() {
                    return zzp.this.b();
                }

                public void zzcea() {
                    zzbo.zzdf("Refresh ignored: container loaded as default only.");
                }

                public void zzoy(String str) {
                    zzp.this.a(str);
                }
            }));
        } else {
            String str = "Default was requested, but no default container was found";
            zzbo.e(str);
            zzc(zzc(new Status(10, str, null)));
        }
        this.r = null;
        this.k = null;
    }

    public void zzcec() {
        a(false);
    }

    public void zzced() {
        a(true);
    }

    /* access modifiers changed from: protected */
    /* renamed from: zzef */
    public ContainerHolder zzc(Status status) {
        if (this.m != null) {
            return this.m;
        }
        if (status == Status.wb) {
            zzbo.e("timer expired: setting result to failure");
        }
        return new zzo(status);
    }
}
