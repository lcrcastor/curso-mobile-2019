package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

class zzb<TResult, TContinuationResult> implements OnFailureListener, OnSuccessListener<TContinuationResult>, zzf<TResult> {
    private final Executor a;
    /* access modifiers changed from: private */
    public final Continuation<TResult, Task<TContinuationResult>> b;
    /* access modifiers changed from: private */
    public final zzh<TContinuationResult> c;

    public zzb(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation, @NonNull zzh<TContinuationResult> zzh) {
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
                    Task task = (Task) zzb.this.b.then(task);
                    if (task == null) {
                        zzb.this.onFailure(new NullPointerException("Continuation returned null"));
                        return;
                    }
                    task.addOnSuccessListener(TaskExecutors.a, (OnSuccessListener<? super TResult>) zzb.this);
                    task.addOnFailureListener(TaskExecutors.a, (OnFailureListener) zzb.this);
                } catch (RuntimeExecutionException e) {
                    if (e.getCause() instanceof Exception) {
                        b2 = zzb.this.c;
                        r0 = (Exception) e.getCause();
                    } else {
                        b2 = zzb.this.c;
                        r0 = e;
                    }
                    b2.a(r0);
                } catch (Exception e2) {
                    zzb.this.c.a(e2);
                }
            }
        });
    }

    public void onFailure(@NonNull Exception exc) {
        this.c.a(exc);
    }

    public void onSuccess(TContinuationResult tcontinuationresult) {
        this.c.a(tcontinuationresult);
    }
}
