package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

class zza<TResult, TContinuationResult> implements zzf<TResult> {
    private final Executor a;
    /* access modifiers changed from: private */
    public final Continuation<TResult, TContinuationResult> b;
    /* access modifiers changed from: private */
    public final zzh<TContinuationResult> c;

    public zza(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation, @NonNull zzh<TContinuationResult> zzh) {
        this.a = executor;
        this.b = continuation;
        this.c = zzh;
    }

    public void a() {
        throw new UnsupportedOperationException();
    }

    public void a(@NonNull final Task<TResult> task) {
        this.a.execute(new Runnable() {
            public void run() {
                zzh b2;
                try {
                    zza.this.c.a(zza.this.b.then(task));
                } catch (RuntimeExecutionException e) {
                    if (e.getCause() instanceof Exception) {
                        b2 = zza.this.c;
                        r0 = (Exception) e.getCause();
                    } else {
                        b2 = zza.this.c;
                        r0 = e;
                    }
                    b2.a(r0);
                } catch (Exception e2) {
                    zza.this.c.a(e2);
                }
            }
        });
    }
}
