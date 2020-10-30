package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleFlatMap<T, R> extends Single<R> {
    final SingleSource<? extends T> a;
    final Function<? super T, ? extends SingleSource<? extends R>> b;

    static final class SingleFlatMapCallback<T, R> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = 3258103020495908596L;
        final SingleObserver<? super R> a;
        final Function<? super T, ? extends SingleSource<? extends R>> b;

        static final class FlatMapSingleObserver<R> implements SingleObserver<R> {
            final AtomicReference<Disposable> a;
            final SingleObserver<? super R> b;

            FlatMapSingleObserver(AtomicReference<Disposable> atomicReference, SingleObserver<? super R> singleObserver) {
                this.a = atomicReference;
                this.b = singleObserver;
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this.a, disposable);
            }

            public void onSuccess(R r) {
                this.b.onSuccess(r);
            }

            public void onError(Throwable th) {
                this.b.onError(th);
            }
        }

        SingleFlatMapCallback(SingleObserver<? super R> singleObserver, Function<? super T, ? extends SingleSource<? extends R>> function) {
            this.a = singleObserver;
            this.b = function;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.a.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            try {
                SingleSource singleSource = (SingleSource) ObjectHelper.requireNonNull(this.b.apply(t), "The single returned by the mapper is null");
                if (!isDisposed()) {
                    singleSource.subscribe(new FlatMapSingleObserver(this, this.a));
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.a.onError(th);
            }
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }
    }

    public SingleFlatMap(SingleSource<? extends T> singleSource, Function<? super T, ? extends SingleSource<? extends R>> function) {
        this.b = function;
        this.a = singleSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super R> singleObserver) {
        this.a.subscribe(new SingleFlatMapCallback(singleObserver, this.b));
    }
}
