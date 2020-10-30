package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

class zzd<TResult> implements zzf<TResult> {
    private final Executor a;
    /* access modifiers changed from: private */
    public final Object b = new Object();
    /* access modifiers changed from: private */
    public OnFailureListener c;

    public zzd(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.a = executor;
        this.c = onFailureListener;
    }

    public void a() {
        synchronized (this.b) {
            this.c = null;
        }
    }

    public void a(@NonNull final Task<TResult> task) {
        if (!task.isSuccessful()) {
            synchronized (this.b) {
                if (this.c != null) {
                    this.a.execute(new Runnable() {
                        public void run() {
                            synchronized (zzd.this.b) {
                                if (zzd.this.c != null) {
                                    zzd.this.c.onFailure(task.getException());
                                }
                            }
                        }
                    });
                }
            }
        }
    }
}
