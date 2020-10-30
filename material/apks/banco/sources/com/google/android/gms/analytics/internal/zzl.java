package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.CampaignTrackingService;
import com.google.android.gms.analytics.zza;
import com.google.android.gms.analytics.zze;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzmi;
import com.google.android.gms.internal.zzmj;
import com.google.android.gms.internal.zzmm;
import com.google.android.gms.internal.zzmr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

class zzl extends zzd {
    private boolean a;
    private final zzj b;
    private final zzah c;
    private final zzag d;
    private final zzi e;
    private long f = Long.MIN_VALUE;
    private final zzt g;
    private final zzt h;
    private final zzal i;
    private long j;
    private boolean k;

    protected zzl(zzf zzf, zzg zzg) {
        super(zzf);
        zzac.zzy(zzg);
        this.d = zzg.b(zzf);
        this.b = zzg.zzm(zzf);
        this.c = zzg.zzn(zzf);
        this.e = zzg.zzo(zzf);
        this.i = new zzal(zzaan());
        this.g = new zzt(zzf) {
            public void a() {
                zzl.this.p();
            }
        };
        this.h = new zzt(zzf) {
            public void a() {
                zzl.this.q();
            }
        };
    }

    private void a(zzh zzh, zzmj zzmj) {
        zzac.zzy(zzh);
        zzac.zzy(zzmj);
        zza zza = new zza(zzaal());
        zza.zzdo(zzh.zzabg());
        zza.enableAdvertisingIdCollection(zzh.zzabh());
        zze zzxi = zza.zzxi();
        zzmr zzmr = (zzmr) zzxi.zzb(zzmr.class);
        zzmr.zzee("data");
        zzmr.zzas(true);
        zzxi.zza((zzg) zzmj);
        zzmm zzmm = (zzmm) zzxi.zzb(zzmm.class);
        zzmi zzmi = (zzmi) zzxi.zzb(zzmi.class);
        for (Entry entry : zzh.zzm().entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if ("an".equals(str)) {
                zzmi.setAppName(str2);
            } else if ("av".equals(str)) {
                zzmi.setAppVersion(str2);
            } else if ("aid".equals(str)) {
                zzmi.setAppId(str2);
            } else if ("aiid".equals(str)) {
                zzmi.setAppInstallerId(str2);
            } else if ("uid".equals(str)) {
                zzmr.setUserId(str2);
            } else {
                zzmm.set(str, str2);
            }
        }
        zzb("Sending installation campaign to", zzh.zzabg(), zzmj);
        zzxi.zzp(zzaas().zzafe());
        zzxi.zzya();
    }

    private boolean b(String str) {
        return getContext().checkCallingOrSelfPermission(str) == 0;
    }

    private void o() {
        String str;
        zzyl();
        Context context = zzaal().getContext();
        if (!zzaj.zzaw(context)) {
            zzes("AnalyticsReceiver is not registered or is disabled. Register the receiver for reliable dispatching on non-Google Play devices. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!zzak.zzax(context)) {
            zzet("AnalyticsService is not registered or is disabled. Analytics service at risk of not starting. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!CampaignTrackingReceiver.zzaw(context)) {
            str = "CampaignTrackingReceiver is not registered, not exported or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.";
        } else if (!CampaignTrackingService.zzax(context)) {
            str = "CampaignTrackingService is not registered or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.";
        } else {
            return;
        }
        zzes(str);
    }

    /* access modifiers changed from: private */
    public void p() {
        a((zzw) new zzw() {
            public void zzf(Throwable th) {
                zzl.this.l();
            }
        });
    }

    /* access modifiers changed from: private */
    public void q() {
        try {
            this.b.h();
            l();
        } catch (SQLiteException e2) {
            zzd("Failed to delete stale hits", e2);
        }
        this.h.a(zzaap().zzadv());
    }

    private boolean r() {
        boolean z = false;
        if (this.k) {
            return false;
        }
        if (zzaap().zzact() && !zzaap().zzacu()) {
            return false;
        }
        if (m() > 0) {
            z = true;
        }
        return z;
    }

    private void s() {
        zzv zzaar = zzaar();
        if (zzaar.zzaed() && !zzaar.zzfl()) {
            long k2 = k();
            if (k2 != 0 && Math.abs(zzaan().currentTimeMillis() - k2) <= zzaap().zzadd()) {
                zza("Dispatch alarm scheduled (ms)", Long.valueOf(zzaap().zzadc()));
                zzaar.schedule();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0027, code lost:
        if (r6 > 0) goto L_0x0036;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void t() {
        /*
            r10 = this;
            r10.s()
            long r0 = r10.m()
            com.google.android.gms.analytics.internal.zzai r2 = r10.zzaas()
            long r2 = r2.zzafg()
            r4 = 0
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x002a
            com.google.android.gms.common.util.zze r6 = r10.zzaan()
            long r6 = r6.currentTimeMillis()
            long r8 = r6 - r2
            long r2 = java.lang.Math.abs(r8)
            long r6 = r0 - r2
            int r2 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r2 <= 0) goto L_0x002a
            goto L_0x0036
        L_0x002a:
            com.google.android.gms.analytics.internal.zzr r2 = r10.zzaap()
            long r2 = r2.zzada()
            long r6 = java.lang.Math.min(r2, r0)
        L_0x0036:
            java.lang.String r0 = "Dispatch scheduled (ms)"
            java.lang.Long r1 = java.lang.Long.valueOf(r6)
            r10.zza(r0, r1)
            com.google.android.gms.analytics.internal.zzt r0 = r10.g
            boolean r0 = r0.c()
            if (r0 == 0) goto L_0x005b
            r0 = 1
            com.google.android.gms.analytics.internal.zzt r2 = r10.g
            long r2 = r2.b()
            long r4 = r6 + r2
            long r0 = java.lang.Math.max(r0, r4)
            com.google.android.gms.analytics.internal.zzt r2 = r10.g
            r2.b(r0)
            return
        L_0x005b:
            com.google.android.gms.analytics.internal.zzt r0 = r10.g
            r0.a(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzl.t():void");
    }

    private void u() {
        v();
        w();
    }

    private void v() {
        if (this.g.c()) {
            zzep("All hits dispatched or no network/service. Going to power save mode");
        }
        this.g.d();
    }

    private void w() {
        zzv zzaar = zzaar();
        if (zzaar.zzfl()) {
            zzaar.cancel();
        }
    }

    public long a(zzh zzh, boolean z) {
        zzac.zzy(zzh);
        zzaax();
        zzyl();
        try {
            this.b.a();
            this.b.a(zzh.zzabf(), zzh.zzxs());
            long a2 = this.b.a(zzh.zzabf(), zzh.zzxs(), zzh.zzabg());
            if (!z) {
                zzh.zzr(a2);
            } else {
                zzh.zzr(a2 + 1);
            }
            this.b.a(zzh);
            this.b.b();
            try {
                return a2;
            } catch (SQLiteException e2) {
                zze("Failed to end transaction", e2);
                return a2;
            }
        } catch (SQLiteException e3) {
            zze("Failed to update Analytics property", e3);
            try {
            } catch (SQLiteException e4) {
                zze("Failed to end transaction", e4);
            }
            return -1;
        } finally {
            try {
                this.b.c();
            } catch (SQLiteException e5) {
                zze("Failed to end transaction", e5);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        zzaax();
        zzac.zza(!this.a, (Object) "Analytics backend already started");
        this.a = true;
        zzaaq().zzg(new Runnable() {
            public void run() {
                zzl.this.b();
            }
        });
    }

    public void a(long j2) {
        zzi.zzyl();
        zzaax();
        if (j2 < 0) {
            j2 = 0;
        }
        this.f = j2;
        l();
    }

    public void a(zzab zzab) {
        zzac.zzy(zzab);
        zzi.zzyl();
        zzaax();
        if (this.k) {
            zzeq("Hit delivery not possible. Missing network permissions. See http://goo.gl/8Rd3yj for instructions");
        } else {
            zza("Delivering hit", zzab);
        }
        zzab b2 = b(zzab);
        f();
        if (this.e.zzb(b2)) {
            zzeq("Hit sent to the device AnalyticsService for delivery");
        } else if (zzaap().zzact()) {
            zzaao().zza(b2, "Service unavailable on package side");
        } else {
            try {
                this.b.a(b2);
                l();
            } catch (SQLiteException e2) {
                zze("Delivery failed to save hit to a database", e2);
                zzaao().zza(b2, "deliver: failed to insert hit to database");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(zzh zzh) {
        zzyl();
        zzb("Sending first hit to property", zzh.zzabg());
        if (!zzaas().zzaff().a(zzaap().zzady())) {
            String zzafi = zzaas().zzafi();
            if (!TextUtils.isEmpty(zzafi)) {
                zzmj zza = zzao.zza(zzaao(), zzafi);
                zzb("Found relevant installation campaign", zza);
                a(zzh, zza);
            }
        }
    }

    public void a(zzw zzw) {
        a(zzw, this.j);
    }

    public void a(final zzw zzw, final long j2) {
        zzi.zzyl();
        zzaax();
        long zzafg = zzaas().zzafg();
        zzb("Dispatching local hits. Elapsed time since last dispatch (ms)", Long.valueOf(zzafg != 0 ? Math.abs(zzaan().currentTimeMillis() - zzafg) : -1));
        if (!zzaap().zzact()) {
            f();
        }
        try {
            if (i()) {
                zzaaq().zzg(new Runnable() {
                    public void run() {
                        zzl.this.a(zzw, j2);
                    }
                });
                return;
            }
            zzaas().zzafh();
            l();
            if (zzw != null) {
                zzw.zzf(null);
            }
            if (this.j != j2) {
                this.d.c();
            }
        } catch (Throwable th) {
            zze("Local dispatch failed", th);
            zzaas().zzafh();
            l();
            if (zzw != null) {
                zzw.zzf(th);
            }
        }
    }

    public void a(String str) {
        zzac.zzhz(str);
        zzyl();
        zzaam();
        zzmj zza = zzao.zza(zzaao(), str);
        if (zza == null) {
            zzd("Parsing failed. Ignoring invalid campaign data", str);
            return;
        }
        String zzafi = zzaas().zzafi();
        if (str.equals(zzafi)) {
            zzes("Ignoring duplicate install campaign");
        } else if (!TextUtils.isEmpty(zzafi)) {
            zzd("Ignoring multiple install campaigns. original, new", zzafi, str);
        } else {
            zzaas().zzfc(str);
            if (zzaas().zzaff().a(zzaap().zzady())) {
                zzd("Campaign received too late, ignoring", zza);
                return;
            }
            zzb("Received installation campaign", zza);
            for (zzh a2 : this.b.d(0)) {
                a(a2, zza);
            }
        }
    }

    public void a(boolean z) {
        l();
    }

    /* access modifiers changed from: 0000 */
    public zzab b(zzab zzab) {
        if (!TextUtils.isEmpty(zzab.zzaeu())) {
            return zzab;
        }
        Pair zzafm = zzaas().zzafj().zzafm();
        if (zzafm == null) {
            return zzab;
        }
        Long l = (Long) zzafm.second;
        String str = (String) zzafm.first;
        String valueOf = String.valueOf(l);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(str).length());
        sb.append(valueOf);
        sb.append(":");
        sb.append(str);
        String sb2 = sb.toString();
        HashMap hashMap = new HashMap(zzab.zzm());
        hashMap.put("_m", sb2);
        return zzab.zza(this, zzab, hashMap);
    }

    /* access modifiers changed from: protected */
    public void b() {
        zzaax();
        if (!zzaap().zzact()) {
            o();
        }
        zzaas().zzafe();
        if (!b("android.permission.ACCESS_NETWORK_STATE")) {
            zzet("Missing required android.permission.ACCESS_NETWORK_STATE. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            n();
        }
        if (!b("android.permission.INTERNET")) {
            zzet("Missing required android.permission.INTERNET. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            n();
        }
        if (zzak.zzax(getContext())) {
            zzep("AnalyticsService registered in the app manifest and enabled");
        } else if (zzaap().zzact()) {
            zzet("Device AnalyticsService not registered! Hits will not be delivered reliably.");
        } else {
            zzes("AnalyticsService not registered in the app manifest. Hits might not be delivered reliably. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!this.k && !zzaap().zzact() && !this.b.g()) {
            f();
        }
        l();
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        zzyl();
        this.j = zzaan().currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void d() {
        zzyl();
        if (!zzaap().zzact()) {
            h();
        }
    }

    public void e() {
        zzi.zzyl();
        zzaax();
        zzep("Service disconnected");
    }

    /* access modifiers changed from: protected */
    public void f() {
        if (!this.k && zzaap().zzacv() && !this.e.isConnected()) {
            if (this.i.a(zzaap().zzadq())) {
                this.i.a();
                zzep("Connecting to service");
                if (this.e.connect()) {
                    zzep("Connected to service");
                    this.i.b();
                    d();
                }
            }
        }
    }

    public void g() {
        zzi.zzyl();
        zzaax();
        if (!zzaap().zzact()) {
            zzep("Delete all hits from local store");
            try {
                this.b.d();
                this.b.e();
                l();
            } catch (SQLiteException e2) {
                zzd("Failed to delete hits from store", e2);
            }
        }
        f();
        if (this.e.zzabj()) {
            zzep("Device service unavailable. Can't clear hits stored on the device service.");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x004d A[LOOP:1: B:16:0x004d->B:24:?, LOOP_START] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0049 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void h() {
        /*
            r5 = this;
            com.google.android.gms.analytics.zzi.zzyl()
            r5.zzaax()
            r5.zzaam()
            com.google.android.gms.analytics.internal.zzr r0 = r5.zzaap()
            boolean r0 = r0.zzacv()
            if (r0 != 0) goto L_0x0018
            java.lang.String r0 = "Service client disabled. Can't dispatch local hits to device AnalyticsService"
            r5.zzes(r0)
        L_0x0018:
            com.google.android.gms.analytics.internal.zzi r0 = r5.e
            boolean r0 = r0.isConnected()
            if (r0 != 0) goto L_0x0026
            java.lang.String r0 = "Service not connected"
            r5.zzep(r0)
            return
        L_0x0026:
            com.google.android.gms.analytics.internal.zzj r0 = r5.b
            boolean r0 = r0.g()
            if (r0 == 0) goto L_0x002f
            return
        L_0x002f:
            java.lang.String r0 = "Dispatching local hits to device AnalyticsService"
            r5.zzep(r0)
        L_0x0034:
            com.google.android.gms.analytics.internal.zzj r0 = r5.b     // Catch:{ SQLiteException -> 0x007d }
            com.google.android.gms.analytics.internal.zzr r1 = r5.zzaap()     // Catch:{ SQLiteException -> 0x007d }
            int r1 = r1.zzade()     // Catch:{ SQLiteException -> 0x007d }
            long r1 = (long) r1     // Catch:{ SQLiteException -> 0x007d }
            java.util.List r0 = r0.b(r1)     // Catch:{ SQLiteException -> 0x007d }
            boolean r1 = r0.isEmpty()     // Catch:{ SQLiteException -> 0x007d }
            if (r1 == 0) goto L_0x004d
            r5.l()     // Catch:{ SQLiteException -> 0x007d }
            return
        L_0x004d:
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto L_0x0034
            r1 = 0
            java.lang.Object r1 = r0.get(r1)
            com.google.android.gms.analytics.internal.zzab r1 = (com.google.android.gms.analytics.internal.zzab) r1
            com.google.android.gms.analytics.internal.zzi r2 = r5.e
            boolean r2 = r2.zzb(r1)
            if (r2 != 0) goto L_0x0066
            r5.l()
            return
        L_0x0066:
            r0.remove(r1)
            com.google.android.gms.analytics.internal.zzj r2 = r5.b     // Catch:{ SQLiteException -> 0x0073 }
            long r3 = r1.zzaep()     // Catch:{ SQLiteException -> 0x0073 }
            r2.c(r3)     // Catch:{ SQLiteException -> 0x0073 }
            goto L_0x004d
        L_0x0073:
            r0 = move-exception
            java.lang.String r1 = "Failed to remove hit that was send for delivery"
            r5.zze(r1, r0)
            r5.u()
            return
        L_0x007d:
            r0 = move-exception
            java.lang.String r1 = "Failed to read hits from store"
            r5.zze(r1, r0)
            r5.u()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzl.h():void");
    }

    /* access modifiers changed from: protected */
    public boolean i() {
        zzi.zzyl();
        zzaax();
        zzep("Dispatching a batch of local hits");
        boolean a2 = true ^ this.c.a();
        if (!(!this.e.isConnected() && !zzaap().zzact()) || !a2) {
            long max = (long) Math.max(zzaap().zzade(), zzaap().zzadf());
            ArrayList arrayList = new ArrayList();
            long j2 = 0;
            while (true) {
                this.b.a();
                arrayList.clear();
                try {
                    List<zzab> b2 = this.b.b(max);
                    if (b2.isEmpty()) {
                        zzep("Store is empty, nothing to dispatch");
                        u();
                        try {
                            this.b.b();
                            this.b.c();
                            return false;
                        } catch (SQLiteException e2) {
                            zze("Failed to commit local dispatch transaction", e2);
                            u();
                            return false;
                        }
                    } else {
                        zza("Hits loaded from store. count", Integer.valueOf(b2.size()));
                        for (zzab zzaep : b2) {
                            if (zzaep.zzaep() == j2) {
                                zzd("Database contains successfully uploaded hit", Long.valueOf(j2), Integer.valueOf(b2.size()));
                                u();
                                try {
                                    this.b.b();
                                    this.b.c();
                                    return false;
                                } catch (SQLiteException e3) {
                                    zze("Failed to commit local dispatch transaction", e3);
                                    u();
                                    return false;
                                }
                            }
                        }
                        if (this.e.isConnected() && !zzaap().zzact()) {
                            zzep("Service connected, sending hits to the service");
                            while (true) {
                                if (!b2.isEmpty()) {
                                    zzab zzab = (zzab) b2.get(0);
                                    if (!this.e.zzb(zzab)) {
                                        break;
                                    }
                                    j2 = Math.max(j2, zzab.zzaep());
                                    b2.remove(zzab);
                                    zzb("Hit sent do device AnalyticsService for delivery", zzab);
                                    try {
                                        this.b.c(zzab.zzaep());
                                        arrayList.add(Long.valueOf(zzab.zzaep()));
                                    } catch (SQLiteException e4) {
                                        zze("Failed to remove hit that was send for delivery", e4);
                                        u();
                                        try {
                                            return false;
                                        } catch (SQLiteException e5) {
                                            zze("Failed to commit local dispatch transaction", e5);
                                            u();
                                            return false;
                                        }
                                    } finally {
                                        try {
                                            this.b.b();
                                            this.b.c();
                                        } catch (SQLiteException e6) {
                                            zze("Failed to commit local dispatch transaction", e6);
                                            u();
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                        if (this.c.a()) {
                            List<Long> a3 = this.c.a(b2);
                            for (Long longValue : a3) {
                                j2 = Math.max(j2, longValue.longValue());
                            }
                            try {
                                this.b.a(a3);
                                arrayList.addAll(a3);
                            } catch (SQLiteException e7) {
                                zze("Failed to remove successfully uploaded hits", e7);
                                u();
                                try {
                                    this.b.b();
                                    this.b.c();
                                    return false;
                                } catch (SQLiteException e8) {
                                    zze("Failed to commit local dispatch transaction", e8);
                                    u();
                                    return false;
                                }
                            }
                        }
                        if (arrayList.isEmpty()) {
                            try {
                                return false;
                            } catch (SQLiteException e9) {
                                zze("Failed to commit local dispatch transaction", e9);
                                u();
                                return false;
                            }
                        } else {
                            try {
                                this.b.b();
                                this.b.c();
                            } catch (SQLiteException e10) {
                                zze("Failed to commit local dispatch transaction", e10);
                                u();
                                return false;
                            }
                        }
                    }
                } catch (SQLiteException e11) {
                    zzd("Failed to read hits from persisted store", e11);
                    u();
                    try {
                        this.b.b();
                        this.b.c();
                        return false;
                    } catch (SQLiteException e12) {
                        zze("Failed to commit local dispatch transaction", e12);
                        u();
                        return false;
                    }
                }
            }
        } else {
            zzep("No network or service available. Will retry later");
            return false;
        }
    }

    public void j() {
        zzi.zzyl();
        zzaax();
        zzeq("Sync dispatching local hits");
        long j2 = this.j;
        if (!zzaap().zzact()) {
            f();
        }
        while (i()) {
            try {
            } catch (Throwable th) {
                zze("Sync local dispatch failed", th);
                l();
            }
        }
        zzaas().zzafh();
        l();
        if (this.j != j2) {
            this.d.c();
        }
    }

    public long k() {
        zzi.zzyl();
        zzaax();
        try {
            return this.b.i();
        } catch (SQLiteException e2) {
            zze("Failed to get min/max hit times from local store", e2);
            return 0;
        }
    }

    public void l() {
        boolean z;
        zzaal().zzyl();
        zzaax();
        if (r() && !this.b.g()) {
            if (!((Boolean) zzy.cN.get()).booleanValue()) {
                this.d.a();
                z = this.d.e();
            } else {
                z = true;
            }
            if (z) {
                t();
                return;
            }
            u();
            s();
            return;
        }
        this.d.b();
        u();
    }

    public long m() {
        if (this.f != Long.MIN_VALUE) {
            return this.f;
        }
        long zzadb = zzaap().zzadb();
        if (zzxv().zzaek()) {
            zzadb = ((long) zzxv().zzagb()) * 1000;
        }
        return zzadb;
    }

    public void n() {
        zzaax();
        zzyl();
        this.k = true;
        this.e.disconnect();
        l();
    }

    /* access modifiers changed from: protected */
    public void zzym() {
        this.b.initialize();
        this.c.initialize();
        this.e.initialize();
    }
}
