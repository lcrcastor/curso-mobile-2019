package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import javax.annotation.Nullable;

@GwtCompatible
class TrustedListenableFutureTask<V> extends TrustedFuture<V> implements RunnableFuture<V> {
    private TrustedFutureInterruptibleTask a;

    final class TrustedFutureInterruptibleTask extends InterruptibleTask {
        private final Callable<V> b;

        TrustedFutureInterruptibleTask(Callable<V> callable) {
            this.b = (Callable) Preconditions.checkNotNull(callable);
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            if (!TrustedListenableFutureTask.this.isDone()) {
                try {
                    TrustedListenableFutureTask.this.set(this.b.call());
                } catch (Throwable th) {
                    TrustedListenableFutureTask.this.setException(th);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean c() {
            return TrustedListenableFutureTask.this.wasInterrupted();
        }

        public String toString() {
            return this.b.toString();
        }
    }

    static <V> TrustedListenableFutureTask<V> a(Callable<V> callable) {
        return new TrustedListenableFutureTask<>(callable);
    }

    static <V> TrustedListenableFutureTask<V> a(Runnable runnable, @Nullable V v) {
        return new TrustedListenableFutureTask<>(Executors.callable(runnable, v));
    }

    TrustedListenableFutureTask(Callable<V> callable) {
        this.a = new TrustedFutureInterruptibleTask<>(callable);
    }

    public void run() {
        TrustedFutureInterruptibleTask trustedFutureInterruptibleTask = this.a;
        if (trustedFutureInterruptibleTask != null) {
            trustedFutureInterruptibleTask.run();
        }
    }

    /* access modifiers changed from: protected */
    public void afterDone() {
        super.afterDone();
        if (wasInterrupted()) {
            TrustedFutureInterruptibleTask trustedFutureInterruptibleTask = this.a;
            if (trustedFutureInterruptibleTask != null) {
                trustedFutureInterruptibleTask.e();
            }
        }
        this.a = null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" (delegate = ");
        sb.append(this.a);
        sb.append(")");
        return sb.toString();
    }
}
