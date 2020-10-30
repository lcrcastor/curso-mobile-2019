package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public final class FutureObserver<T> extends CountDownLatch implements Observer<T>, Disposable, Future<T> {
    T a;
    Throwable b;
    final AtomicReference<Disposable> c = new AtomicReference<>();

    public void dispose() {
    }

    public FutureObserver() {
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

    public void onNext(T t) {
        if (this.a != null) {
            ((Disposable) this.c.get()).dispose();
            onError(new IndexOutOfBoundsException("More than one element received"));
            return;
        }
        this.a = t;
    }

    public void onError(Throwable th) {
        Disposable disposable;
        if (this.b == null) {
            this.b = th;
            do {
                disposable = (Disposable) this.c.get();
                if (disposable == this || disposable == DisposableHelper.DISPOSED) {
                    RxJavaPlugins.onError(th);
                    return;
                }
            } while (!this.c.compareAndSet(disposable, this));
            countDown();
            return;
        }
        RxJavaPlugins.onError(th);
    }

    public void onComplete() {
        Disposable disposable;
        if (this.a == null) {
            onError(new NoSuchElementException("The source is empty"));
            return;
        }
        do {
            disposable = (Disposable) this.c.get();
            if (disposable == this || disposable == DisposableHelper.DISPOSED) {
                return;
            }
        } while (!this.c.compareAndSet(disposable, this));
        countDown();
    }

    public boolean isDisposed() {
        return isDone();
    }
}
