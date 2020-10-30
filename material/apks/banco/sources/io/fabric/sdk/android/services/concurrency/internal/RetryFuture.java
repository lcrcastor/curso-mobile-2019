package io.fabric.sdk.android.services.concurrency.internal;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

class RetryFuture<T> extends AbstractFuture<T> implements Runnable {
    RetryState a;
    private final RetryThreadPoolExecutor b;
    private final Callable<T> c;
    private final AtomicReference<Thread> d = new AtomicReference<>();

    RetryFuture(Callable<T> callable, RetryState retryState, RetryThreadPoolExecutor retryThreadPoolExecutor) {
        this.c = callable;
        this.a = retryState;
        this.b = retryThreadPoolExecutor;
    }

    public void run() {
        if (!isDone() && this.d.compareAndSet(null, Thread.currentThread())) {
            try {
                set(this.c.call());
            } catch (Throwable th) {
                this.d.getAndSet(null);
                throw th;
            }
            this.d.getAndSet(null);
        }
    }

    private RetryPolicy a() {
        return this.a.getRetryPolicy();
    }

    private Backoff b() {
        return this.a.getBackoff();
    }

    private int c() {
        return this.a.getRetryCount();
    }

    /* access modifiers changed from: protected */
    public void interruptTask() {
        Thread thread = (Thread) this.d.getAndSet(null);
        if (thread != null) {
            thread.interrupt();
        }
    }
}
