package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

@GwtIncompatible
@Beta
public final class JdkFutureAdapters {

    static class ListenableFutureAdapter<V> extends ForwardingFuture<V> implements ListenableFuture<V> {
        private static final ThreadFactory a = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ListenableFutureAdapter-thread-%d").build();
        private static final Executor b = Executors.newCachedThreadPool(a);
        private final Executor c;
        /* access modifiers changed from: private */
        public final ExecutionList d;
        private final AtomicBoolean e;
        /* access modifiers changed from: private */
        public final Future<V> f;

        ListenableFutureAdapter(Future<V> future) {
            this(future, b);
        }

        ListenableFutureAdapter(Future<V> future, Executor executor) {
            this.d = new ExecutionList();
            this.e = new AtomicBoolean(false);
            this.f = (Future) Preconditions.checkNotNull(future);
            this.c = (Executor) Preconditions.checkNotNull(executor);
        }

        /* access modifiers changed from: protected */
        public Future<V> delegate() {
            return this.f;
        }

        public void addListener(Runnable runnable, Executor executor) {
            this.d.add(runnable, executor);
            if (this.e.compareAndSet(false, true)) {
                if (this.f.isDone()) {
                    this.d.execute();
                    return;
                }
                this.c.execute(new Runnable() {
                    public void run() {
                        try {
                            Uninterruptibles.getUninterruptibly(ListenableFutureAdapter.this.f);
                        } catch (Throwable unused) {
                        }
                        ListenableFutureAdapter.this.d.execute();
                    }
                });
            }
        }
    }

    public static <V> ListenableFuture<V> listenInPoolThread(Future<V> future) {
        if (future instanceof ListenableFuture) {
            return (ListenableFuture) future;
        }
        return new ListenableFutureAdapter(future);
    }

    public static <V> ListenableFuture<V> listenInPoolThread(Future<V> future, Executor executor) {
        Preconditions.checkNotNull(executor);
        if (future instanceof ListenableFuture) {
            return (ListenableFuture) future;
        }
        return new ListenableFutureAdapter(future, executor);
    }

    private JdkFutureAdapters() {
    }
}
