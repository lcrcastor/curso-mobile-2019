package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;

public final class SingleMap<T, R> extends Single<R> {
    final SingleSource<? extends T> a;
    final Function<? super T, ? extends R> b;

    static final class MapSingleObserver<T, R> implements SingleObserver<T> {
        final SingleObserver<? super R> a;
        final Function<? super T, ? extends R> b;

        MapSingleObserver(SingleObserver<? super R> singleObserver, Function<? super T, ? extends R> function) {
            this.a = singleObserver;
            this.b = function;
        }

        public void onSubscribe(Disposable disposable) {
            this.a.onSubscribe(disposable);
        }

        public void onSuccess(T t) {
            try {
                this.a.onSuccess(this.b.apply(t));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                onError(th);
            }
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }
    }

    public SingleMap(SingleSource<? extends T> singleSource, Function<? super T, ? extends R> function) {
        this.a = singleSource;
        this.b = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super R> singleObserver) {
        this.a.subscribe(new MapSingleObserver(singleObserver, this.b));
    }
}
