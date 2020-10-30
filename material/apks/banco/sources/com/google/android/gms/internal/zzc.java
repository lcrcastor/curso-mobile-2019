package com.google.android.gms.internal;

import android.os.Process;
import com.google.android.gms.internal.zzb.zza;
import java.util.concurrent.BlockingQueue;

public class zzc extends Thread {
    private static final boolean a = zzs.DEBUG;
    private final BlockingQueue<zzk<?>> b;
    /* access modifiers changed from: private */
    public final BlockingQueue<zzk<?>> c;
    private final zzb d;
    private final zzn e;
    private volatile boolean f = false;

    public zzc(BlockingQueue<zzk<?>> blockingQueue, BlockingQueue<zzk<?>> blockingQueue2, zzb zzb, zzn zzn) {
        super("VolleyCacheDispatcher");
        this.b = blockingQueue;
        this.c = blockingQueue2;
        this.d = zzb;
        this.e = zzn;
    }

    public void quit() {
        this.f = true;
        interrupt();
    }

    public void run() {
        if (a) {
            zzs.zza("start new dispatcher", new Object[0]);
        }
        Process.setThreadPriority(10);
        this.d.initialize();
        while (true) {
            try {
                final zzk zzk = (zzk) this.b.take();
                zzk.zzc("cache-queue-take");
                if (zzk.isCanceled()) {
                    zzk.a("cache-discard-canceled");
                } else {
                    zza zza = this.d.zza(zzk.zzg());
                    if (zza == null) {
                        zzk.zzc("cache-miss");
                    } else if (zza.zza()) {
                        zzk.zzc("cache-hit-expired");
                        zzk.zza(zza);
                    } else {
                        zzk.zzc("cache-hit");
                        zzm zza2 = zzk.zza(new zzi(zza.data, zza.zzf));
                        zzk.zzc("cache-hit-parsed");
                        if (!zza.zzb()) {
                            this.e.zza(zzk, zza2);
                        } else {
                            zzk.zzc("cache-hit-refresh-needed");
                            zzk.zza(zza);
                            zza2.zzbh = true;
                            this.e.zza(zzk, zza2, new Runnable() {
                                public void run() {
                                    try {
                                        zzc.this.c.put(zzk);
                                    } catch (InterruptedException unused) {
                                    }
                                }
                            });
                        }
                    }
                    this.c.put(zzk);
                }
            } catch (InterruptedException unused) {
                if (this.f) {
                    return;
                }
            }
        }
    }
}
