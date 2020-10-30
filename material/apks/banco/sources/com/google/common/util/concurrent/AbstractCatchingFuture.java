package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.ForOverride;
import java.lang.Throwable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import javax.annotation.Nullable;

@GwtCompatible
abstract class AbstractCatchingFuture<V, X extends Throwable, F, T> extends TrustedFuture<V> implements Runnable {
    @Nullable
    ListenableFuture<? extends V> a;
    @Nullable
    Class<X> b;
    @Nullable
    F c;

    static final class CatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, Function<? super X, ? extends V>, V> {
        CatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> cls, Function<? super X, ? extends V> function) {
            super(listenableFuture, cls, function);
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public V a(Function<? super X, ? extends V> function, X x) {
            return function.apply(x);
        }

        /* access modifiers changed from: 0000 */
        public void a(@Nullable V v) {
            set(v);
        }
    }

    static final class AsyncCatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, AsyncFunction<? super X, ? extends V>, ListenableFuture<? extends V>> {
        AsyncCatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> cls, AsyncFunction<? super X, ? extends V> asyncFunction) {
            super(listenableFuture, cls, asyncFunction);
        }

        /* access modifiers changed from: 0000 */
        public ListenableFuture<? extends V> a(AsyncFunction<? super X, ? extends V> asyncFunction, X x) {
            ListenableFuture<? extends V> apply = asyncFunction.apply(x);
            Preconditions.checkNotNull(apply, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)?");
            return apply;
        }

        /* access modifiers changed from: 0000 */
        public void a(ListenableFuture<? extends V> listenableFuture) {
            setFuture(listenableFuture);
        }
    }

    /* access modifiers changed from: 0000 */
    @ForOverride
    @Nullable
    public abstract T a(F f, X x);

    /* access modifiers changed from: 0000 */
    @ForOverride
    public abstract void a(@Nullable T t);

    static <X extends Throwable, V> ListenableFuture<V> a(ListenableFuture<? extends V> listenableFuture, Class<X> cls, Function<? super X, ? extends V> function) {
        CatchingFuture catchingFuture = new CatchingFuture(listenableFuture, cls, function);
        listenableFuture.addListener(catchingFuture, MoreExecutors.directExecutor());
        return catchingFuture;
    }

    static <V, X extends Throwable> ListenableFuture<V> a(ListenableFuture<? extends V> listenableFuture, Class<X> cls, Function<? super X, ? extends V> function, Executor executor) {
        CatchingFuture catchingFuture = new CatchingFuture(listenableFuture, cls, function);
        listenableFuture.addListener(catchingFuture, MoreExecutors.a(executor, (AbstractFuture<?>) catchingFuture));
        return catchingFuture;
    }

    static <X extends Throwable, V> ListenableFuture<V> a(ListenableFuture<? extends V> listenableFuture, Class<X> cls, AsyncFunction<? super X, ? extends V> asyncFunction) {
        AsyncCatchingFuture asyncCatchingFuture = new AsyncCatchingFuture(listenableFuture, cls, asyncFunction);
        listenableFuture.addListener(asyncCatchingFuture, MoreExecutors.directExecutor());
        return asyncCatchingFuture;
    }

    static <X extends Throwable, V> ListenableFuture<V> a(ListenableFuture<? extends V> listenableFuture, Class<X> cls, AsyncFunction<? super X, ? extends V> asyncFunction, Executor executor) {
        AsyncCatchingFuture asyncCatchingFuture = new AsyncCatchingFuture(listenableFuture, cls, asyncFunction);
        listenableFuture.addListener(asyncCatchingFuture, MoreExecutors.a(executor, (AbstractFuture<?>) asyncCatchingFuture));
        return asyncCatchingFuture;
    }

    AbstractCatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> cls, F f) {
        this.a = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
        this.b = (Class) Preconditions.checkNotNull(cls);
        this.c = Preconditions.checkNotNull(f);
    }

    public final void run() {
        Throwable th;
        ListenableFuture<? extends V> listenableFuture = this.a;
        Class<X> cls = this.b;
        F f = this.c;
        boolean z = false;
        boolean z2 = (listenableFuture == null) | (cls == null);
        if (f == null) {
            z = true;
        }
        if (!(z | z2) && !isCancelled()) {
            Object obj = null;
            this.a = null;
            this.b = null;
            this.c = null;
            try {
                obj = Futures.getDone(listenableFuture);
                th = null;
            } catch (ExecutionException e) {
                th = (Throwable) Preconditions.checkNotNull(e.getCause());
            } catch (Throwable th2) {
                th = th2;
            }
            if (th == null) {
                set(obj);
            } else if (!Platform.a(th, cls)) {
                setException(th);
            } else {
                try {
                    a(a(f, th));
                } catch (Throwable th3) {
                    setException(th3);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void afterDone() {
        a((Future<?>) this.a);
        this.a = null;
        this.b = null;
        this.c = null;
    }
}
