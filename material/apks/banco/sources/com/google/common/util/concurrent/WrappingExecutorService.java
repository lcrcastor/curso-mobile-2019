package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@GwtIncompatible
@CanIgnoreReturnValue
abstract class WrappingExecutorService implements ExecutorService {
    private final ExecutorService a;

    /* access modifiers changed from: protected */
    public abstract <T> Callable<T> a(Callable<T> callable);

    protected WrappingExecutorService(ExecutorService executorService) {
        this.a = (ExecutorService) Preconditions.checkNotNull(executorService);
    }

    /* access modifiers changed from: protected */
    public Runnable a(Runnable runnable) {
        final Callable a2 = a(Executors.callable(runnable, null));
        return new Runnable() {
            public void run() {
                try {
                    a2.call();
                } catch (Exception e) {
                    Throwables.throwIfUnchecked(e);
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private final <T> ImmutableList<Callable<T>> a(Collection<? extends Callable<T>> collection) {
        Builder builder = ImmutableList.builder();
        for (Callable a2 : collection) {
            builder.add((Object) a(a2));
        }
        return builder.build();
    }

    public final void execute(Runnable runnable) {
        this.a.execute(a(runnable));
    }

    public final <T> Future<T> submit(Callable<T> callable) {
        return this.a.submit(a((Callable) Preconditions.checkNotNull(callable)));
    }

    public final Future<?> submit(Runnable runnable) {
        return this.a.submit(a(runnable));
    }

    public final <T> Future<T> submit(Runnable runnable, T t) {
        return this.a.submit(a(runnable), t);
    }

    public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) {
        return this.a.invokeAll(a(collection));
    }

    public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) {
        return this.a.invokeAll(a(collection), j, timeUnit);
    }

    public final <T> T invokeAny(Collection<? extends Callable<T>> collection) {
        return this.a.invokeAny(a(collection));
    }

    public final <T> T invokeAny(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) {
        return this.a.invokeAny(a(collection), j, timeUnit);
    }

    public final void shutdown() {
        this.a.shutdown();
    }

    public final List<Runnable> shutdownNow() {
        return this.a.shutdownNow();
    }

    public final boolean isShutdown() {
        return this.a.isShutdown();
    }

    public final boolean isTerminated() {
        return this.a.isTerminated();
    }

    public final boolean awaitTermination(long j, TimeUnit timeUnit) {
        return this.a.awaitTermination(j, timeUnit);
    }
}
