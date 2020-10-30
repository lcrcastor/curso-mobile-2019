package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;

public final class SingleDoOnSuccess<T> extends Single<T> {
    final SingleSource<T> a;
    final Consumer<? super T> b;

    final class DoOnSuccess implements SingleObserver<T> {
        private final SingleObserver<? super T> b;

        DoOnSuccess(SingleObserver<? super T> singleObserver) {
            this.b = singleObserver;
        }

        public void onSubscribe(Disposable disposable) {
            this.b.onSubscribe(disposable);
        }

        public void onSuccess(T t) {
            try {
                SingleDoOnSuccess.this.b.accept(t);
                this.b.onSuccess(t);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.b.onError(th);
            }
        }

        public void onError(Throwable th) {
            this.b.onError(th);
        }
    }

    public SingleDoOnSuccess(SingleSource<T> singleSource, Consumer<? super T> consumer) {
        this.a = singleSource;
        this.b = consumer;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new DoOnSuccess(singleObserver));
    }
}
