package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseException;
import java.util.Map;

public abstract class zzpy {
    public final int lN;
    public final int wf;

    static abstract class zza extends zzpy {
        protected final SparseArray<Map<com.google.android.gms.internal.zzrd.zzb<?>, zzri>> wg;
        protected final TaskCompletionSource<Void> wh;

        public zza(int i, int i2, TaskCompletionSource<Void> taskCompletionSource, SparseArray<Map<com.google.android.gms.internal.zzrd.zzb<?>, zzri>> sparseArray) {
            super(i, i2);
            this.wg = sparseArray;
            this.wh = taskCompletionSource;
        }

        private void a(RemoteException remoteException) {
            zzx(new Status(8, remoteException.getLocalizedMessage(), null));
        }

        public boolean cancel() {
            this.wh.setException(new com.google.android.gms.common.api.zza(Status.wc));
            return true;
        }

        public void zza(SparseArray<zzrq> sparseArray) {
        }

        /* access modifiers changed from: protected */
        public abstract void zza(com.google.android.gms.common.api.Api.zzb zzb);

        public final void zzb(com.google.android.gms.common.api.Api.zzb zzb) {
            try {
                zza(zzb);
            } catch (DeadObjectException e) {
                a(e);
                throw e;
            } catch (RemoteException e2) {
                a(e2);
            }
        }

        public void zzx(@NonNull Status status) {
            this.wh.setException(new com.google.android.gms.common.api.zza(status));
        }
    }

    public static class zzb<A extends com.google.android.gms.internal.zzqc.zza<? extends Result, com.google.android.gms.common.api.Api.zzb>> extends zzpy {
        protected final A wi;

        public zzb(int i, int i2, A a) {
            super(i, i2);
            this.wi = a;
        }

        public boolean cancel() {
            return this.wi.zzaqq();
        }

        public void zza(SparseArray<zzrq> sparseArray) {
            zzrq zzrq = (zzrq) sparseArray.get(this.wf);
            if (zzrq != null) {
                zzrq.a((zzqe<? extends Result>) this.wi);
            }
        }

        public void zzb(com.google.android.gms.common.api.Api.zzb zzb) {
            this.wi.zzb(zzb);
        }

        public void zzx(@NonNull Status status) {
            this.wi.zzz(status);
        }
    }

    public static final class zzc extends zza {
        public final zzrh<com.google.android.gms.common.api.Api.zzb> wj;
        public final zzrr<com.google.android.gms.common.api.Api.zzb> wk;

        public zzc(int i, zzri zzri, TaskCompletionSource<Void> taskCompletionSource, SparseArray<Map<com.google.android.gms.internal.zzrd.zzb<?>, zzri>> sparseArray) {
            super(i, 3, taskCompletionSource, sparseArray);
            this.wj = zzri.wj;
            this.wk = zzri.wk;
        }

        public /* bridge */ /* synthetic */ boolean cancel() {
            return super.cancel();
        }

        public /* bridge */ /* synthetic */ void zza(SparseArray sparseArray) {
            super.zza(sparseArray);
        }

        public void zza(com.google.android.gms.common.api.Api.zzb zzb) {
            this.wj.zza(zzb, this.wh);
            Map map = (Map) this.wg.get(this.wf);
            if (map == null) {
                map = new ArrayMap(1);
                this.wg.put(this.wf, map);
            }
            String valueOf = String.valueOf(this.wj.zzasr());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 12);
            sb.append("registered: ");
            sb.append(valueOf);
            Log.d("reg", sb.toString());
            if (this.wj.zzasr() != null) {
                map.put(this.wj.zzasr(), new zzri(this.wj, this.wk));
            }
        }

        public /* bridge */ /* synthetic */ void zzx(@NonNull Status status) {
            super.zzx(status);
        }
    }

    public static final class zzd<TResult> extends zzpy {
        private static final Status c = new Status(8, "Connection to Google Play services was lost while executing the API call.");
        private final zzro<com.google.android.gms.common.api.Api.zzb, TResult> a;
        private final TaskCompletionSource<TResult> b;

        public zzd(int i, int i2, zzro<com.google.android.gms.common.api.Api.zzb, TResult> zzro, TaskCompletionSource<TResult> taskCompletionSource) {
            super(i, i2);
            this.b = taskCompletionSource;
            this.a = zzro;
        }

        public void zzb(com.google.android.gms.common.api.Api.zzb zzb) {
            try {
                this.a.zzb(zzb, this.b);
            } catch (DeadObjectException e) {
                zzx(c);
                throw e;
            } catch (RemoteException unused) {
                zzx(c);
            }
        }

        public void zzx(@NonNull Status status) {
            TaskCompletionSource<TResult> taskCompletionSource;
            Exception firebaseApiNotAvailableException;
            if (status.getStatusCode() == 8) {
                taskCompletionSource = this.b;
                firebaseApiNotAvailableException = new FirebaseException(status.getStatusMessage());
            } else {
                taskCompletionSource = this.b;
                firebaseApiNotAvailableException = new FirebaseApiNotAvailableException(status.getStatusMessage());
            }
            taskCompletionSource.setException(firebaseApiNotAvailableException);
        }
    }

    public static final class zze extends zza {
        public final zzrr<com.google.android.gms.common.api.Api.zzb> wn;

        public zze(int i, zzrr<com.google.android.gms.common.api.Api.zzb> zzrr, TaskCompletionSource<Void> taskCompletionSource, SparseArray<Map<com.google.android.gms.internal.zzrd.zzb<?>, zzri>> sparseArray) {
            super(i, 4, taskCompletionSource, sparseArray);
            this.wn = zzrr;
        }

        public /* bridge */ /* synthetic */ boolean cancel() {
            return super.cancel();
        }

        public /* bridge */ /* synthetic */ void zza(SparseArray sparseArray) {
            super.zza(sparseArray);
        }

        public void zza(com.google.android.gms.common.api.Api.zzb zzb) {
            Map map = (Map) this.wg.get(this.wf);
            if (map == null || this.wn.zzasr() == null) {
                Log.wtf("UnregisterListenerTask", "Received call to unregister a listener without a matching registration call.", new Exception());
                this.wh.setException(new com.google.android.gms.common.api.zza(Status.wa));
                return;
            }
            map.remove(this.wn.zzasr());
            this.wn.zzc(zzb, this.wh);
        }

        public /* bridge */ /* synthetic */ void zzx(@NonNull Status status) {
            super.zzx(status);
        }
    }

    public zzpy(int i, int i2) {
        this.wf = i;
        this.lN = i2;
    }

    public boolean cancel() {
        return true;
    }

    public void zza(SparseArray<zzrq> sparseArray) {
    }

    public abstract void zzb(com.google.android.gms.common.api.Api.zzb zzb2);

    public abstract void zzx(@NonNull Status status);
}
