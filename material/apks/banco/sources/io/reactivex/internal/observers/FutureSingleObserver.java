package io.reactivex.internal.observers;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public final class FutureSingleObserver<T> extends CountDownLatch implements SingleObserver<T>, Disposable, Future<T> {
    T a;
    Throwable b;
    final AtomicReference<Disposable> c = new AtomicReference<>();

    public void dispose() {
    }

    public FutureSingleObserver() {
        super(1);
    }

    public boolean cancel(boolean z) {
        Disposable disposable;
        do {
            disposable = (Disposable) this.c.get();
            if (disposable == this || disposable == DisposableHelper.DISPOSED) {
                return false;
            }
        } while (!this.c.compareAndSet(disposable, DisposableHelper.DISPOSED));
        if (disposable != null) {
            disposable.dispose();
        }
        countDown();
        return true;
    }

    public boolean isCancelled() {
        return DisposableHelper.isDisposed((Disposable) this.c.get());
    }

    public boolean isDone() {
        return getCount() == 0;
    }

    public T get() {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            await();
        }
        if (isCancelled()) {
            throw new CancellationException();
        }
        Throwable th = this.b;
        if (th == null) {
            return this.a;
        }
        throw new ExecutionException(th);
    }

    public T get(long j, TimeUnit timeUnit) {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            if (!await(j, timeUnit)) {
                throw new TimeoutException();
            }
        }
        if (isCancelled()) {
            throw new CancellationException();
        }
        Throwable th = this.b;
        if (th == null) {
            return this.a;
        }
        throw new ExecutionException(th);
    }

    public void onSubscribe(Disposable disposable) {
        DisposableHelper.setOnce(this.c, disposable);
    }

    public void onSuccess(T t) {
        Disposable disposable = (Disposable) this.c.get();
        if (disposable != DisposableHelper.DISPOSED) {
            this.a = t;
            this.c.compareAndSet(disposable, this);
            countDown();
        }
    }

    public void onError(Throwable th) {
        Disposable disposable;
        do {
            disposable = (Disposable) this.c.get();
            if (disposable == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.b = th;
        } while (!this.c.compareAndSet(disposable, this));
        countDown();
    }

    public boolean isDisposed() {
        return isDone();
    }
}
