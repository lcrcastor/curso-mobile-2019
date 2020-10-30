package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

class zze<TResult> implements zzf<TResult> {
    private final Executor a;
    /* access modifiers changed from: private */
    public final Object b = new Object();
    /* access modifiers changed from: private */
    public OnSuccessListener<? super TResult> c;

    public zze(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.a = executor;
        this.c = onSuccessListener;
    }

    public void a() {
        synchronized (this.b) {
            this.c = null;
        }
    }

    public void a(@NonNull final Task<TResult> task) {
        if (task.isSuccessful()) {
            synchronized (this.b) {
                if (this.c != null) {
                    this.a.execute(new Runnable() {
                        public void run() {
                            synchronized (zze.this.b) {
                                if (zze.this.c != null) {
                                    zze.this.c.onSuccess(task.getResult());
                                }
                            }
                        }
                    });
                }
            }
        }
    }
}
