package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.ForOverride;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractTransformFuture<I, O, F, T> extends TrustedFuture<O> implements Runnable {
    @Nullable
    ListenableFuture<? extends I> a;
    @Nullable
    F b;

    static final class AsyncTransformFuture<I, O> extends AbstractTransformFuture<I, O, AsyncFunction<? super I, ? extends O>, ListenableFuture<? extends O>> {
        AsyncTransformFuture(ListenableFuture<? extends I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction) {
            super(listenableFuture, asyncFunction);
        }

        /* access modifiers changed from: 0000 */
        public ListenableFuture<? extends O> a(AsyncFunction<? super I, ? extends O> asyncFunction, @Nullable I i) {
            ListenableFuture<? extends O> apply = asyncFunction.apply(i);
            Preconditions.checkNotNull(apply, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)?");
            return apply;
        }

        /* access modifiers changed from: 0000 */
        public void a(ListenableFuture<? extends O> listenableFuture) {
            setFuture(listenableFuture);
        }
    }

    static final class TransformFuture<I, O> extends AbstractTransformFuture<I, O, Function<? super I, ? extends O>, O> {
        TransformFuture(ListenableFuture<? extends I> listenableFuture, Function<? super I, ? extends O> function) {
            super(listenableFuture, function);
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public O a(Function<? super I, ? extends O> function, @Nullable I i) {
            return function.apply(i);
        }

        /* access modifiers changed from: 0000 */
        public void a(@Nullable O o) {
            set(o);
        }
    }

    /* access modifiers changed from: 0000 */
    @ForOverride
    @Nullable
    public abstract T a(F f, @Nullable I i);

    /* access modifiers changed from: 0000 */
    @ForOverride
    public abstract void a(@Nullable T t);

    static <I, O> ListenableFuture<O> a(ListenableFuture<I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction) {
        AsyncTransformFuture asyncTransformFuture = new AsyncTransformFuture(listenableFuture, asyncFunction);
        listenableFuture.addListener(asyncTransformFuture, MoreExecutors.directExecutor());
        return asyncTransformFuture;
    }

    static <I, O> ListenableFuture<O> a(ListenableFuture<I> listenableFuture, AsyncFunction<? super I, ? extends O> asyncFunction, Executor executor) {
        Preconditions.checkNotNull(executor);
        AsyncTransformFuture asyncTransformFuture = new AsyncTransformFuture(listenableFuture, asyncFunction);
        listenableFuture.addListener(asyncTransformFuture, MoreExecutors.a(executor, (AbstractFuture<?>) asyncTransformFuture));
        return asyncTransformFuture;
    }

    static <I, O> ListenableFuture<O> a(ListenableFuture<I> listenableFuture, Function<? super I, ? extends O> function) {
        Preconditions.checkNotNull(function);
        TransformFuture transformFuture = new TransformFuture(listenableFuture, function);
        listenableFuture.addListener(transformFuture, MoreExecutors.directExecutor());
        return transformFuture;
    }

    static <I, O> ListenableFuture<O> a(ListenableFuture<I> listenableFuture, Function<? super I, ? extends O> function, Executor executor) {
        Preconditions.checkNotNull(function);
        TransformFuture transformFuture = new TransformFuture(listenableFuture, function);
        listenableFuture.addListener(transformFuture, MoreExecutors.a(executor, (AbstractFuture<?>) transformFuture));
        return transformFuture;
    }

    AbstractTransformFuture(ListenableFuture<? extends I> listenableFuture, F f) {
        this.a = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
        this.b = Preconditions.checkNotNull(f);
    }

    public final void run() {
        ListenableFuture<? extends I> listenableFuture = this.a;
        F f = this.b;
        boolean z = true;
        boolean isCancelled = isCancelled() | (listenableFuture == null);
        if (f != null) {
            z = false;
        }
        if (!isCancelled && !z) {
            this.a = null;
            this.b = null;
            try {
                try {
                    a(a(f, (I) Futures.getDone(listenableFuture)));
                } catch (UndeclaredThrowableException e) {
                    setException(e.getCause());
                } catch (Throwable th) {
                    setException(th);
                }
            } catch (CancellationException unused) {
                cancel(false);
            } catch (ExecutionException e2) {
                setException(e2.getCause());
            } catch (RuntimeException e3) {
                setException(e3);
            } catch (Error e4) {
                setException(e4);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void afterDone() {
        a((Future<?>) this.a);
        this.a = null;
        this.b = null;
    }
}
