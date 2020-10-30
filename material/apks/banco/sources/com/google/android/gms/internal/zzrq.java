package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zzf;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class zzrq {
    private static final zzqe<?>[] b = new zzqe[0];
    final Set<zzqe<?>> a;
    private final zzb c;
    private final Map<com.google.android.gms.common.api.Api.zzc<?>, zze> d;
    private final zze e;
    /* access modifiers changed from: private */
    public zzc f;

    static class zza implements DeathRecipient, zzb {
        private final WeakReference<zzqe<?>> a;
        private final WeakReference<zzf> b;
        private final WeakReference<IBinder> c;

        private zza(zzqe<?> zzqe, zzf zzf, IBinder iBinder) {
            this.b = new WeakReference<>(zzf);
            this.a = new WeakReference<>(zzqe);
            this.c = new WeakReference<>(iBinder);
        }

        private void a() {
            zzqe zzqe = (zzqe) this.a.get();
            zzf zzf = (zzf) this.b.get();
            if (!(zzf == null || zzqe == null)) {
                zzf.remove(zzqe.zzaqf().intValue());
            }
            IBinder iBinder = (IBinder) this.c.get();
            if (iBinder != null) {
                iBinder.unlinkToDeath(this, 0);
            }
        }

        public void a(zzqe<?> zzqe) {
            a();
        }

        public void binderDied() {
            a();
        }
    }

    interface zzb {
        void a(zzqe<?> zzqe);
    }

    interface zzc {
        void a();
    }

    public zzrq(zze zze) {
        this.a = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
        this.c = new zzb() {
            public void a(zzqe<?> zzqe) {
                zzrq.this.a.remove(zzqe);
                if (!(zzqe.zzaqf() == null || zzrq.a(zzrq.this) == null)) {
                    zzrq.a(zzrq.this).remove(zzqe.zzaqf().intValue());
                }
                if (zzrq.this.f != null && zzrq.this.a.isEmpty()) {
                    zzrq.this.f.a();
                }
            }
        };
        this.f = null;
        this.d = null;
        this.e = zze;
    }

    public zzrq(Map<com.google.android.gms.common.api.Api.zzc<?>, zze> map) {
        this.a = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
        this.c = new zzb() {
            public void a(zzqe<?> zzqe) {
                zzrq.this.a.remove(zzqe);
                if (!(zzqe.zzaqf() == null || zzrq.a(zzrq.this) == null)) {
                    zzrq.a(zzrq.this).remove(zzqe.zzaqf().intValue());
                }
                if (zzrq.this.f != null && zzrq.this.a.isEmpty()) {
                    zzrq.this.f.a();
                }
            }
        };
        this.f = null;
        this.d = map;
        this.e = null;
    }

    static /* synthetic */ zzf a(zzrq zzrq) {
        return null;
    }

    private static void a(zzqe<?> zzqe, zzf zzf, IBinder iBinder) {
        if (zzqe.isReady()) {
            zzqe.zza((zzb) new zza(zzqe, zzf, iBinder));
            return;
        }
        if (iBinder == null || !iBinder.isBinderAlive()) {
            zzqe.zza((zzb) null);
        } else {
            zza zza2 = new zza(zzqe, zzf, iBinder);
            zzqe.zza((zzb) zza2);
            try {
                iBinder.linkToDeath(zza2, 0);
                return;
            } catch (RemoteException unused) {
            }
        }
        zzqe.cancel();
        zzf.remove(zzqe.zzaqf().intValue());
    }

    /* access modifiers changed from: 0000 */
    public void a(zzqe<? extends Result> zzqe) {
        this.a.add(zzqe);
        zzqe.zza(this.c);
    }

    public void dump(PrintWriter printWriter) {
        printWriter.append(" mUnconsumedApiCalls.size()=").println(this.a.size());
    }

    public void release() {
        zzqe[] zzqeArr;
        IBinder iBinder;
        zze zze;
        for (zzqe zzqe : (zzqe[]) this.a.toArray(b)) {
            zzqe.zza((zzb) null);
            if (zzqe.zzaqf() != null) {
                zzqe.zzaqs();
                if (this.e != null) {
                    zze = this.e;
                } else if (this.d != null) {
                    zze = (zze) this.d.get(((com.google.android.gms.internal.zzqc.zza) zzqe).zzapp());
                } else {
                    Log.wtf("UnconsumedApiCalls", "Could not get service broker binder", new Exception());
                    iBinder = null;
                    a(zzqe, null, iBinder);
                }
                iBinder = zze.zzaps();
                a(zzqe, null, iBinder);
            } else if (!zzqe.zzaqq()) {
            }
            this.a.remove(zzqe);
        }
    }

    public void zza(zzc zzc2) {
        if (this.a.isEmpty()) {
            zzc2.a();
        }
        this.f = zzc2;
    }

    public void zzasw() {
        for (zzqe zzaa : (zzqe[]) this.a.toArray(b)) {
            zzaa.zzaa(new Status(8, "The connection to Google Play services was lost"));
        }
    }

    public boolean zzasx() {
        for (zzqe isReady : (zzqe[]) this.a.toArray(b)) {
            if (!isReady.isReady()) {
                return true;
            }
        }
        return false;
    }
}
