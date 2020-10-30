package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzac;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class Tasks {

    static final class zza implements zzb {
        private final CountDownLatch a;

        private zza() {
            this.a = new CountDownLatch(1);
        }

        public void a() {
            this.a.await();
        }

        public boolean a(long j, TimeUnit timeUnit) {
            return this.a.await(j, timeUnit);
        }

        public void onFailure(@NonNull Exception exc) {
            this.a.countDown();
        }

        public void onSuccess(Object obj) {
            this.a.countDown();
        }
    }

    interface zzb extends OnFailureListener, OnSuccessListener<Object> {
    }

    static final class zzc implements zzb {
        private final Object a = new Object();
        private final int b;
        private final zzh<Void> c;
        private int d;
        private int e;
        private Exception f;

        public zzc(int i, zzh<Void> zzh) {
            this.b = i;
            this.c = zzh;
        }

        private void a() {
            if (this.d + this.e == this.b) {
                if (this.f == null) {
                    this.c.a(null);
                    return;
                }
                zzh<Void> zzh = this.c;
                int i = this.e;
                int i2 = this.b;
                StringBuilder sb = new StringBuilder(54);
                sb.append(i);
                sb.append(" out of ");
                sb.append(i2);
                sb.append(" underlying tasks failed");
                zzh.a((Exception) new ExecutionException(sb.toString(), this.f));
            }
        }

        public void onFailure(@NonNull Exception exc) {
            synchronized (this.a) {
                this.e++;
                this.f = exc;
                a();
            }
        }

        public void onSuccess(Object obj) {
            synchronized (this.a) {
                this.d++;
                a();
            }
        }
    }

    private Tasks() {
    }

    private static <TResult> TResult a(Task<TResult> task) {
        if (task.isSuccessful()) {
            return task.getResult();
        }
        throw new ExecutionException(task.getException());
    }

    private static void a(Task<?> task, zzb zzb2) {
        task.addOnSuccessListener(TaskExecutors.a, (OnSuccessListener<? super TResult>) zzb2);
        task.addOnFailureListener(TaskExecutors.a, (OnFailureListener) zzb2);
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task) {
        zzac.zzavb();
        zzac.zzb(task, (Object) "Task must not be null");
        if (task.isComplete()) {
            return a(task);
        }
        zza zza2 = new zza();
        a(task, zza2);
        zza2.a();
        return a(task);
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task, long j, @NonNull TimeUnit timeUnit) {
        zzac.zzavb();
        zzac.zzb(task, (Object) "Task must not be null");
        zzac.zzb(timeUnit, (Object) "TimeUnit must not be null");
        if (task.isComplete()) {
            return a(task);
        }
        zza zza2 = new zza();
        a(task, zza2);
        if (zza2.a(j, timeUnit)) {
            return a(task);
        }
        throw new TimeoutException("Timed out waiting for Task");
    }

    public static <TResult> Task<TResult> call(@NonNull Callable<TResult> callable) {
        return call(TaskExecutors.MAIN_THREAD, callable);
    }

    public static <TResult> Task<TResult> call(@NonNull Executor executor, @NonNull final Callable<TResult> callable) {
        zzac.zzb(executor, (Object) "Executor must not be null");
        zzac.zzb(callable, (Object) "Callback must not be null");
        final zzh zzh = new zzh();
        executor.execute(new Runnable() {
            public void run() {
                try {
                    zzh.this.a(callable.call());
                } catch (Exception e) {
                    zzh.this.a(e);
                }
            }
        });
        return zzh;
    }

    public static <TResult> Task<TResult> forException(@NonNull Exception exc) {
        zzh zzh = new zzh();
        zzh.a(exc);
        return zzh;
    }

    public static <TResult> Task<TResult> forResult(TResult tresult) {
        zzh zzh = new zzh();
        zzh.a(tresult);
        return zzh;
    }

    public static Task<Void> whenAll(Collection<? extends Task<?>> collection) {
        if (collection.isEmpty()) {
            return forResult(null);
        }
        for (Task task : collection) {
            if (task == null) {
                throw new NullPointerException("null tasks are not accepted");
            }
        }
        zzh zzh = new zzh();
        zzc zzc2 = new zzc(collection.size(), zzh);
        for (Task a : collection) {
            a(a, zzc2);
        }
        return zzh;
    }

    public static Task<Void> whenAll(Task<?>... taskArr) {
        return taskArr.length == 0 ? forResult(null) : whenAll((Collection<? extends Task<?>>) Arrays.asList(taskArr));
    }
}
