package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

class zzc<TResult> implements zzf<TResult> {
    private final Executor a;
    /* access modifiers changed from: private */
    public final Object b = new Object();
    /* access modifiers changed from: private */
    public OnCompleteListener<TResult> c;

    public zzc(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.a = executor;
        this.c = onCompleteListener;
    }

    public void a() {
        synchronized (this.b) {
            this.c = null;
        }
    }

    public void a(@NonNull final Task<TResult> task) {
        synchronized (this.b) {
            if (this.c != null) {
                this.a.execute(new Runnable() {
                    public void run() {
                        synchronized (zzc.this.b) {
                            if (zzc.this.c != null) {
                                zzc.this.c.onComplete(task);
                            }
                        }
                    }
                });
            }
        }
    }
}
