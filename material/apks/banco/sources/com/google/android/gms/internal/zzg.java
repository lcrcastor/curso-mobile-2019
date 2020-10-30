package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.net.TrafficStats;
import android.os.Build.VERSION;
import android.os.Process;
import android.os.SystemClock;
import java.util.concurrent.BlockingQueue;

public class zzg extends Thread {
    private final BlockingQueue<zzk<?>> a;
    private final zzf b;
    private final zzb c;
    private final zzn d;
    private volatile boolean e = false;

    public zzg(BlockingQueue<zzk<?>> blockingQueue, zzf zzf, zzb zzb, zzn zzn) {
        super("VolleyNetworkDispatcher");
        this.a = blockingQueue;
        this.b = zzf;
        this.c = zzb;
        this.d = zzn;
    }

    @TargetApi(14)
    private void a(zzk<?> zzk) {
        if (VERSION.SDK_INT >= 14) {
            TrafficStats.setThreadStatsTag(zzk.zzf());
        }
    }

    private void a(zzk<?> zzk, zzr zzr) {
        this.d.zza(zzk, zzk.zzb(zzr));
    }

    public void quit() {
        this.e = true;
        interrupt();
    }

    public void run() {
        String str;
        Process.setThreadPriority(10);
        while (true) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            try {
                zzk zzk = (zzk) this.a.take();
                try {
                    zzk.zzc("network-queue-take");
                    if (zzk.isCanceled()) {
                        str = "network-discard-cancelled";
                    } else {
                        a(zzk);
                        zzi zza = this.b.zza(zzk);
                        zzk.zzc("network-http-complete");
                        if (!zza.zzaa || !zzk.zzv()) {
                            zzm zza2 = zzk.zza(zza);
                            zzk.zzc("network-parse-complete");
                            if (zzk.zzq() && zza2.zzbf != null) {
                                this.c.zza(zzk.zzg(), zza2.zzbf);
                                zzk.zzc("network-cache-written");
                            }
                            zzk.zzu();
                            this.d.zza(zzk, zza2);
                        } else {
                            str = "not-modified";
                        }
                    }
                    zzk.a(str);
                } catch (zzr e2) {
                    e2.a(SystemClock.elapsedRealtime() - elapsedRealtime);
                    a(zzk, e2);
                } catch (Exception e3) {
                    zzs.zza(e3, "Unhandled exception %s", e3.toString());
                    zzr zzr = new zzr((Throwable) e3);
                    zzr.a(SystemClock.elapsedRealtime() - elapsedRealtime);
                    this.d.zza(zzk, zzr);
                }
            } catch (InterruptedException unused) {
                if (this.e) {
                    return;
                }
            }
        }
    }
}
