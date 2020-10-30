package com.google.android.gms.tasks;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzra;
import com.google.android.gms.internal.zzrb;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

final class zzh<TResult> extends Task<TResult> {
    private final Object a = new Object();
    private final zzg<TResult> b = new zzg<>();
    private boolean c;
    private TResult d;
    private Exception e;

    static class zza extends zzra {
        private final List<WeakReference<zzf<?>>> a = new ArrayList();

        private zza(zzrb zzrb) {
            super(zzrb);
            this.yY.zza("TaskOnStopCallback", (zzra) this);
        }

        public static zza a(Activity activity) {
            zzrb zzs = zzs(activity);
            zza zza = (zza) zzs.zza("TaskOnStopCallback", zza.class);
            return zza == null ? new zza(zzs) : zza;
        }

        public <T> void a(zzf<T> zzf) {
            synchronized (this.a) {
                this.a.add(new WeakReference(zzf));
            }
        }

        @MainThread
        public void onStop() {
            synchronized (this.a) {
                for (WeakReference weakReference : this.a) {
                    zzf zzf = (zzf) weakReference.get();
                    if (zzf != null) {
                        zzf.a();
                    }
                }
                this.a.clear();
            }
        }
    }

    zzh() {
    }

    private void a() {
        zzac.zza(this.c, (Object) "Task is not yet complete");
    }

    private void b() {
        zzac.zza(!this.c, (Object) "Task is already complete");
    }

    private void c() {
        synchronized (this.a) {
            if (this.c) {
                this.b.a((Task<TResult>) this);
            }
        }
    }

    public void a(@NonNull Exception exc) {
        zzac.zzb(exc, (Object) "Exception must not be null");
        synchronized (this.a) {
            b();
            this.c = true;
            this.e = exc;
        }
        this.b.a((Task<TResult>) this);
    }

    public void a(TResult tresult) {
        synchronized (this.a) {
            b();
            this.c = true;
            this.d = tresult;
        }
        this.b.a((Task<TResult>) this);
    }

    @NonNull
    public Task<TResult> addOnCompleteListener(@NonNull Activity activity, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        zzc zzc = new zzc(TaskExecutors.MAIN_THREAD, onCompleteListener);
        this.b.a((zzf<TResult>) zzc);
        zza.a(activity).a((zzf<T>) zzc);
        c();
        return this;
    }

    @NonNull
    public Task<TResult> addOnCompleteListener(@NonNull OnCompleteListener<TResult> onCompleteListener) {
        return addOnCompleteListener(TaskExecutors.MAIN_THREAD, onCompleteListener);
    }

    @NonNull
    public Task<TResult> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.b.a((zzf<TResult>) new zzc<TResult>(executor, onCompleteListener));
        c();
        return this;
    }

    @NonNull
    public Task<TResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        zzd zzd = new zzd(TaskExecutors.MAIN_THREAD, onFailureListener);
        this.b.a((zzf<TResult>) zzd);
        zza.a(activity).a((zzf<T>) zzd);
        c();
        return this;
    }

    @NonNull
    public Task<TResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return addOnFailureListener(TaskExecutors.MAIN_THREAD, onFailureListener);
    }

    @NonNull
    public Task<TResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.b.a((zzf<TResult>) new zzd<TResult>(executor, onFailureListener));
        c();
        return this;
    }

    @NonNull
    public Task<TResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        zze zze = new zze(TaskExecutors.MAIN_THREAD, onSuccessListener);
        this.b.a((zzf<TResult>) zze);
        zza.a(activity).a((zzf<T>) zze);
        c();
        return this;
    }

    @NonNull
    public Task<TResult> addOnSuccessListener(@NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        return addOnSuccessListener(TaskExecutors.MAIN_THREAD, onSuccessListener);
    }

    @NonNull
    public Task<TResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.b.a((zzf<TResult>) new zze<TResult>(executor, onSuccessListener));
        c();
        return this;
    }

    @NonNull
    public <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation) {
        zzh zzh = new zzh();
        this.b.a((zzf<TResult>) new zza<TResult>(executor, continuation, zzh));
        c();
        return zzh;
    }

    @NonNull
    public <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        zzh zzh = new zzh();
        this.b.a((zzf<TResult>) new zzb<TResult>(executor, continuation, zzh));
        c();
        return zzh;
    }

    @Nullable
    public Exception getException() {
        Exception exc;
        synchronized (this.a) {
            exc = this.e;
        }
        return exc;
    }

    public TResult getResult() {
        TResult tresult;
        synchronized (this.a) {
            a();
            if (this.e != null) {
                throw new RuntimeExecutionException(this.e);
            }
            tresult = this.d;
        }
        return tresult;
    }

    public <X extends Throwable> TResult getResult(@NonNull Class<X> cls) {
        TResult tresult;
        synchronized (this.a) {
            a();
            if (cls.isInstance(this.e)) {
                throw ((Throwable) cls.cast(this.e));
            } else if (this.e != null) {
                throw new RuntimeExecutionException(this.e);
            } else {
                tresult = this.d;
            }
        }
        return tresult;
    }

    public boolean isComplete() {
        boolean z;
        synchronized (this.a) {
            z = this.c;
        }
        return z;
    }

    public boolean isSuccessful() {
        boolean z;
        synchronized (this.a) {
            z = this.c && this.e == null;
        }
        return z;
    }
}
