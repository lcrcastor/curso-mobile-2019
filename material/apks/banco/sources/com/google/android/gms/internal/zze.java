package com.google.android.gms.internal;

import android.os.Handler;
import java.util.concurrent.Executor;

public class zze implements zzn {
    private final Executor a;

    class zza implements Runnable {
        private final zzk b;
        private final zzm c;
        private final Runnable d;

        public zza(zzk zzk, zzm zzm, Runnable runnable) {
            this.b = zzk;
            this.c = zzm;
            this.d = runnable;
        }

        public void run() {
            if (this.b.isCanceled()) {
                this.b.a("canceled-at-delivery");
                return;
            }
            if (this.c.isSuccess()) {
                this.b.zza(this.c.result);
            } else {
                this.b.zzc(this.c.zzbg);
            }
            if (this.c.zzbh) {
                this.b.zzc("intermediate-response");
            } else {
                this.b.a("done");
            }
            if (this.d != null) {
                this.d.run();
            }
        }
    }

    public zze(final Handler handler) {
        this.a = new Executor() {
            public void execute(Runnable runnable) {
                handler.post(runnable);
            }
        };
    }

    public void zza(zzk<?> zzk, zzm<?> zzm) {
        zza(zzk, zzm, null);
    }

    public void zza(zzk<?> zzk, zzm<?> zzm, Runnable runnable) {
        zzk.zzu();
        zzk.zzc("post-response");
        this.a.execute(new zza(zzk, zzm, runnable));
    }

    public void zza(zzk<?> zzk, zzr zzr) {
        zzk.zzc("post-error");
        this.a.execute(new zza(zzk, zzm.zzd(zzr), null));
    }
}
