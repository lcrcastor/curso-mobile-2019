package io.fabric.sdk.android.services.concurrency.internal;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class RetryThreadPoolExecutor extends ScheduledThreadPoolExecutor {
    private final RetryPolicy a;
    private final Backoff b;

    public RetryThreadPoolExecutor(int i, RetryPolicy retryPolicy, Backoff backoff) {
        this(i, Executors.defaultThreadFactory(), retryPolicy, backoff);
    }

    public RetryThreadPoolExecutor(int i, ThreadFactory threadFactory, RetryPolicy retryPolicy, Backoff backoff) {
        super(i, threadFactory);
        if (retryPolicy == null) {
            throw new NullPointerException("retry policy must not be null");
        } else if (backoff == null) {
            throw new NullPointerException("backoff must not be null");
        } else {
            this.a = retryPolicy;
            this.b = backoff;
        }
    }

    public Future<?> scheduleWithRetry(Runnable runnable) {
        return a(Executors.callable(runnable));
    }

    public <T> Future<T> scheduleWithRetry(Runnable runnable, T t) {
        return a(Executors.callable(runnable, t));
    }

    public <T> Future<T> scheduleWithRetry(Callable<T> callable) {
        return a(callable);
    }

    private <T> Future<T> a(Callable<T> callable) {
        if (callable == null) {
            throw new NullPointerException();
        }
        RetryFuture retryFuture = new RetryFuture(callable, new RetryState(this.b, this.a), this);
        execute(retryFuture);
        return retryFuture;
    }

    public RetryPolicy getRetryPolicy() {
        return this.a;
    }

    public Backoff getBackoff() {
        return this.b;
    }
}
