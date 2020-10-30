package com.google.android.gms.analytics.internal;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import java.lang.Thread.UncaughtExceptionHandler;
import org.bouncycastle.crypto.tls.CipherSuite;

public class zzf {
    private static volatile zzf a;
    private final Context b;
    private final Context c;
    private final zze d;
    private final zzr e;
    private final zzaf f;
    private final zzi g;
    private final zzb h;
    private final zzv i;
    private final zzap j;
    private final zzai k;
    private final GoogleAnalytics l;
    private final zzn m;
    private final zza n;
    private final zzk o;
    private final zzu p;

    protected zzf(zzg zzg) {
        zzaf zzaao;
        StringBuilder sb;
        String str;
        Context applicationContext = zzg.getApplicationContext();
        zzac.zzb(applicationContext, (Object) "Application context can't be null");
        Context zzaaz = zzg.zzaaz();
        zzac.zzy(zzaaz);
        this.b = applicationContext;
        this.c = zzaaz;
        this.d = zzg.zzh(this);
        this.e = zzg.zzg(this);
        zzaf zzf = zzg.zzf(this);
        zzf.initialize();
        this.f = zzf;
        if (zzaap().zzact()) {
            zzaao = zzaao();
            String str2 = zze.VERSION;
            sb = new StringBuilder(String.valueOf(str2).length() + 33);
            sb.append("Google Analytics ");
            sb.append(str2);
            str = " is starting up.";
        } else {
            zzaao = zzaao();
            String str3 = zze.VERSION;
            sb = new StringBuilder(String.valueOf(str3).length() + CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA);
            sb.append("Google Analytics ");
            sb.append(str3);
            str = " is starting up. To enable debug logging on a device run:\n  adb shell setprop log.tag.GAv4 DEBUG\n  adb logcat -s GAv4";
        }
        sb.append(str);
        zzaao.zzer(sb.toString());
        zzai zzq = zzg.zzq(this);
        zzq.initialize();
        this.k = zzq;
        zzap zze = zzg.zze(this);
        zze.initialize();
        this.j = zze;
        zzb zzl = zzg.zzl(this);
        zzn zzd = zzg.zzd(this);
        zza zzc = zzg.zzc(this);
        zzk zzb = zzg.zzb(this);
        zzu zza = zzg.zza(this);
        zzi zzba = zzg.zzba(applicationContext);
        zzba.zza(zzaay());
        this.g = zzba;
        GoogleAnalytics zzi = zzg.zzi(this);
        zzd.initialize();
        this.m = zzd;
        zzc.initialize();
        this.n = zzc;
        zzb.initialize();
        this.o = zzb;
        zza.initialize();
        this.p = zza;
        zzv zzp = zzg.zzp(this);
        zzp.initialize();
        this.i = zzp;
        zzl.initialize();
        this.h = zzl;
        if (zzaap().zzact()) {
            zzaao().zzb("Device AnalyticsService version", zze.VERSION);
        }
        zzi.initialize();
        this.l = zzi;
        zzl.start();
    }

    private void a(zzd zzd) {
        zzac.zzb(zzd, (Object) "Analytics service not created/initialized");
        zzac.zzb(zzd.isInitialized(), (Object) "Analytics service not initialized");
    }

    public static zzf zzaz(Context context) {
        zzac.zzy(context);
        if (a == null) {
            synchronized (zzf.class) {
                if (a == null) {
                    zze zzaxj = zzh.zzaxj();
                    long elapsedRealtime = zzaxj.elapsedRealtime();
                    zzf zzf = new zzf(new zzg(context));
                    a = zzf;
                    GoogleAnalytics.zzxr();
                    long elapsedRealtime2 = zzaxj.elapsedRealtime() - elapsedRealtime;
                    long longValue = ((Long) zzy.cU.get()).longValue();
                    if (elapsedRealtime2 > longValue) {
                        zzf.zzaao().zzc("Slow initialization (ms)", Long.valueOf(elapsedRealtime2), Long.valueOf(longValue));
                    }
                }
            }
        }
        return a;
    }

    public Context getContext() {
        return this.b;
    }

    public zze zzaan() {
        return this.d;
    }

    public zzaf zzaao() {
        a(this.f);
        return this.f;
    }

    public zzr zzaap() {
        return this.e;
    }

    public zzi zzaaq() {
        zzac.zzy(this.g);
        return this.g;
    }

    public zzv zzaar() {
        a(this.i);
        return this.i;
    }

    public zzai zzaas() {
        a(this.k);
        return this.k;
    }

    public zzk zzaav() {
        a(this.o);
        return this.o;
    }

    public zzu zzaaw() {
        return this.p;
    }

    /* access modifiers changed from: protected */
    public UncaughtExceptionHandler zzaay() {
        return new UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable th) {
                zzaf zzaba = zzf.this.zzaba();
                if (zzaba != null) {
                    zzaba.zze("Job execution failed", th);
                }
            }
        };
    }

    public Context zzaaz() {
        return this.c;
    }

    public zzaf zzaba() {
        return this.f;
    }

    public GoogleAnalytics zzabb() {
        zzac.zzy(this.l);
        zzac.zzb(this.l.isInitialized(), (Object) "Analytics instance not initialized");
        return this.l;
    }

    public zzai zzabc() {
        if (this.k == null || !this.k.isInitialized()) {
            return null;
        }
        return this.k;
    }

    public zza zzabd() {
        a(this.n);
        return this.n;
    }

    public zzn zzabe() {
        a(this.m);
        return this.m;
    }

    public zzb zzxu() {
        a(this.h);
        return this.h;
    }

    public zzap zzxv() {
        a(this.j);
        return this.j;
    }

    public void zzyl() {
        zzi.zzyl();
    }
}
