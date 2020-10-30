package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzac;

public final class zzrd<L> {
    private final zza a;
    private volatile L b;
    private final zzb<L> c;

    final class zza extends Handler {
        public zza(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            boolean z = true;
            if (message.what != 1) {
                z = false;
            }
            zzac.zzbs(z);
            zzrd.this.a((zzc) message.obj);
        }
    }

    public static final class zzb<L> {
        private final L a;
        private final String b;

        private zzb(L l, String str) {
            this.a = l;
            this.b = str;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof zzb)) {
                return false;
            }
            zzb zzb = (zzb) obj;
            return this.a == zzb.a && this.b.equals(zzb.b);
        }

        public int hashCode() {
            return (System.identityHashCode(this.a) * 31) + this.b.hashCode();
        }
    }

    public interface zzc<L> {
        void zzarg();

        void zzt(L l);
    }

    zzrd(@NonNull Looper looper, @NonNull L l, @NonNull String str) {
        this.a = new zza(looper);
        this.b = zzac.zzb(l, (Object) "Listener must not be null");
        this.c = new zzb<>(l, zzac.zzhz(str));
    }

    /* access modifiers changed from: 0000 */
    public void a(zzc<? super L> zzc2) {
        L l = this.b;
        if (l == null) {
            zzc2.zzarg();
            return;
        }
        try {
            zzc2.zzt(l);
        } catch (RuntimeException e) {
            zzc2.zzarg();
            throw e;
        }
    }

    public void clear() {
        this.b = null;
    }

    public void zza(zzc<? super L> zzc2) {
        zzac.zzb(zzc2, (Object) "Notifier must not be null");
        this.a.sendMessage(this.a.obtainMessage(1, zzc2));
    }

    @NonNull
    public zzb<L> zzasr() {
        return this.c;
    }
}
