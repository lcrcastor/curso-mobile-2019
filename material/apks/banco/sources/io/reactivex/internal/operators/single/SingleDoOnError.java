package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;

public final class SingleDoOnError<T> extends Single<T> {
    final SingleSource<T> a;
    final Consumer<? super Throwable> b;

    final class DoOnError implements SingleObserver<T> {
        private final SingleObserver<? super T> b;

        DoOnError(SingleObserver<? super T> singleObserver) {
            this.b = singleObserver;
        }

        public void onSubscribe(Disposable disposable) {
            this.b.onSubscribe(disposable);
        }

        public void onSuccess(T t) {
            this.b.onSuccess(t);
        }

        public void onError(Throwable th) {
            try {
                SingleDoOnError.this.b.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.b.onError(th);
        }
    }

    public SingleDoOnError(SingleSource<T> singleSource, Consumer<? super Throwable> consumer) {
        this.a = singleSource;
        this.b = consumer;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new DoOnError(singleObserver));
    }
}
