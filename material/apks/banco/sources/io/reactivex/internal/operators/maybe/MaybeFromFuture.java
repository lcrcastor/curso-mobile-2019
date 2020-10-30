package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class MaybeFromFuture<T> extends Maybe<T> {
    final Future<? extends T> a;
    final long b;
    final TimeUnit c;

    public MaybeFromFuture(Future<? extends T> future, long j, TimeUnit timeUnit) {
        this.a = future;
        this.b = j;
        this.c = timeUnit;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        Object obj;
        Disposable empty = Disposables.empty();
        maybeObserver.onSubscribe(empty);
        if (!empty.isDisposed()) {
            try {
                if (this.b <= 0) {
                    obj = this.a.get();
                } else {
                    obj = this.a.get(this.b, this.c);
                }
                if (!empty.isDisposed()) {
                    if (obj == null) {
                        maybeObserver.onComplete();
                    } else {
                        maybeObserver.onSuccess(obj);
                    }
                }
            } catch (InterruptedException e) {
                if (!empty.isDisposed()) {
                    maybeObserver.onError(e);
                }
            } catch (ExecutionException e2) {
                if (!empty.isDisposed()) {
                    maybeObserver.onError(e2.getCause());
                }
            } catch (TimeoutException e3) {
                if (!empty.isDisposed()) {
                    maybeObserver.onError(e3);
                }
            }
        }
    }
}
