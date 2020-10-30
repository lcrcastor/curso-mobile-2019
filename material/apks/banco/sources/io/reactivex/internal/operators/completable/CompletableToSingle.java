package io.reactivex.internal.operators.completable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import java.util.concurrent.Callable;

public final class CompletableToSingle<T> extends Single<T> {
    final CompletableSource a;
    final Callable<? extends T> b;
    final T c;

    final class ToSingle implements CompletableObserver {
        private final SingleObserver<? super T> b;

        ToSingle(SingleObserver<? super T> singleObserver) {
            this.b = singleObserver;
        }

        public void onComplete() {
            T t;
            if (CompletableToSingle.this.b != null) {
                try {
                    t = CompletableToSingle.this.b.call();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.b.onError(th);
                    return;
                }
            } else {
                t = CompletableToSingle.this.c;
            }
            if (t == null) {
                this.b.onError(new NullPointerException("The value supplied is null"));
            } else {
                this.b.onSuccess(t);
            }
        }

        public void onError(Throwable th) {
            this.b.onError(th);
        }

        public void onSubscribe(Disposable disposable) {
            this.b.onSubscribe(disposable);
        }
    }

    public CompletableToSingle(CompletableSource completableSource, Callable<? extends T> callable, T t) {
        this.a = completableSource;
        this.c = t;
        this.b = callable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new ToSingle(singleObserver));
    }
}
