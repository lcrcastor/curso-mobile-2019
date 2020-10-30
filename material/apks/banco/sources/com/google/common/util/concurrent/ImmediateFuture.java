package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
abstract class ImmediateFuture<V> implements ListenableFuture<V> {
    private static final Logger a = Logger.getLogger(ImmediateFuture.class.getName());

    @GwtIncompatible
    static class ImmediateSuccessfulCheckedFuture<V, X extends Exception> extends ImmediateFuture<V> implements CheckedFuture<V, X> {
        @Nullable
        private final V a;

        ImmediateSuccessfulCheckedFuture(@Nullable V v) {
            this.a = v;
        }

        public V get() {
            return this.a;
        }

        public V checkedGet() {
            return this.a;
        }

        public V checkedGet(long j, TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            return this.a;
        }
    }

    static class ImmediateSuccessfulFuture<V> extends ImmediateFuture<V> {
        static final ImmediateSuccessfulFuture<Object> a = new ImmediateSuccessfulFuture<>(null);
        @Nullable
        private final V b;

        ImmediateSuccessfulFuture(@Nullable V v) {
            this.b = v;
        }

        public V get() {
            return this.b;
        }
    }

    static final class ImmediateCancelledFuture<V> extends TrustedFuture<V> {
        ImmediateCancelledFuture() {
            cancel(false);
        }
    }

    @GwtIncompatible
    static class ImmediateFailedCheckedFuture<V, X extends Exception> extends ImmediateFuture<V> implements CheckedFuture<V, X> {
        private final X a;

        ImmediateFailedCheckedFuture(X x) {
            this.a = x;
        }

        public V get() {
            throw new ExecutionException(this.a);
        }

        public V checkedGet() {
            throw this.a;
        }

        public V checkedGet(long j, TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            throw this.a;
        }
    }

    static final class ImmediateFailedFuture<V> extends TrustedFuture<V> {
        ImmediateFailedFuture(Throwable th) {
            setException(th);
        }
    }

    public boolean cancel(boolean z) {
        return false;
    }

    public abstract V get();

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return true;
    }

    ImmediateFuture() {
    }

    public void addListener(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            Logger logger = a;
            Level level = Level.SEVERE;
            StringBuilder sb = new StringBuilder();
            sb.append("RuntimeException while executing runnable ");
            sb.append(runnable);
            sb.append(" with executor ");
            sb.append(executor);
            logger.log(level, sb.toString(), e);
        }
    }

    public V get(long j, TimeUnit timeUnit) {
        Preconditions.checkNotNull(timeUnit);
        return get();
    }
}
