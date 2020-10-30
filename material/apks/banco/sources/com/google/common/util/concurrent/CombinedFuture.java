package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import javax.annotation.Nullable;

@GwtCompatible
final class CombinedFuture<V> extends AggregateFuture<Object, V> {

    final class CombinedFutureRunningState extends RunningState {
        private CombinedFutureInterruptibleTask c;

        /* access modifiers changed from: 0000 */
        public void a(boolean z, int i, @Nullable Object obj) {
        }

        CombinedFutureRunningState(ImmutableCollection<? extends ListenableFuture<? extends Object>> immutableCollection, boolean z, CombinedFutureInterruptibleTask combinedFutureInterruptibleTask) {
            super(immutableCollection, z, false);
            this.c = combinedFutureInterruptibleTask;
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            CombinedFutureInterruptibleTask combinedFutureInterruptibleTask = this.c;
            if (combinedFutureInterruptibleTask != null) {
                combinedFutureInterruptibleTask.d();
            } else {
                Preconditions.checkState(CombinedFuture.this.isDone());
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            super.a();
            this.c = null;
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            CombinedFutureInterruptibleTask combinedFutureInterruptibleTask = this.c;
            if (combinedFutureInterruptibleTask != null) {
                combinedFutureInterruptibleTask.e();
            }
        }
    }

    final class AsyncCallableInterruptibleTask extends CombinedFutureInterruptibleTask {
        private final AsyncCallable<V> d;

        public AsyncCallableInterruptibleTask(AsyncCallable<V> asyncCallable, Executor executor) {
            super(executor);
            this.d = (AsyncCallable) Preconditions.checkNotNull(asyncCallable);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            CombinedFuture.this.setFuture(this.d.call());
        }
    }

    final class CallableInterruptibleTask extends CombinedFutureInterruptibleTask {
        private final Callable<V> d;

        public CallableInterruptibleTask(Callable<V> callable, Executor executor) {
            super(executor);
            this.d = (Callable) Preconditions.checkNotNull(callable);
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            CombinedFuture.this.set(this.d.call());
        }
    }

    abstract class CombinedFutureInterruptibleTask extends InterruptibleTask {
        private final Executor a;
        volatile boolean b = true;

        /* access modifiers changed from: 0000 */
        public abstract void a();

        public CombinedFutureInterruptibleTask(Executor executor) {
            this.a = (Executor) Preconditions.checkNotNull(executor);
        }

        /* access modifiers changed from: 0000 */
        public final void b() {
            this.b = false;
            if (!CombinedFuture.this.isDone()) {
                try {
                    a();
                } catch (ExecutionException e) {
                    CombinedFuture.this.setException(e.getCause());
                } catch (CancellationException unused) {
                    CombinedFuture.this.cancel(false);
                } catch (Throwable th) {
                    CombinedFuture.this.setException(th);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final boolean c() {
            return CombinedFuture.this.wasInterrupted();
        }

        /* access modifiers changed from: 0000 */
        public final void d() {
            try {
                this.a.execute(this);
            } catch (RejectedExecutionException e) {
                if (this.b) {
                    CombinedFuture.this.setException(e);
                }
            }
        }
    }

    CombinedFuture(ImmutableCollection<? extends ListenableFuture<?>> immutableCollection, boolean z, Executor executor, AsyncCallable<V> asyncCallable) {
        a(new CombinedFutureRunningState(immutableCollection, z, new AsyncCallableInterruptibleTask(asyncCallable, executor)));
    }

    CombinedFuture(ImmutableCollection<? extends ListenableFuture<?>> immutableCollection, boolean z, Executor executor, Callable<V> callable) {
        a(new CombinedFutureRunningState(immutableCollection, z, new CallableInterruptibleTask(callable, executor)));
    }
}
