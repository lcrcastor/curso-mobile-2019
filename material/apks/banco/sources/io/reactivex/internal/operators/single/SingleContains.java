package io.reactivex.internal.operators.single;

import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;

public final class SingleContains<T> extends io.reactivex.Single<Boolean> {
    final SingleSource<T> a;
    final Object b;
    final BiPredicate<Object, Object> c;

    final class Single implements SingleObserver<T> {
        private final SingleObserver<? super Boolean> b;

        Single(SingleObserver<? super Boolean> singleObserver) {
            this.b = singleObserver;
        }

        public void onSubscribe(Disposable disposable) {
            this.b.onSubscribe(disposable);
        }

        public void onSuccess(T t) {
            try {
                this.b.onSuccess(Boolean.valueOf(SingleContains.this.c.test(t, SingleContains.this.b)));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.b.onError(th);
            }
        }

        public void onError(Throwable th) {
            this.b.onError(th);
        }
    }

    public SingleContains(SingleSource<T> singleSource, Object obj, BiPredicate<Object, Object> biPredicate) {
        this.a = singleSource;
        this.b = obj;
        this.c = biPredicate;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        this.a.subscribe(new Single(singleObserver));
    }
}
