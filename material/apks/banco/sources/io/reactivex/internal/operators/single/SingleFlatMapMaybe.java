package io.reactivex.internal.operators.single;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleFlatMapMaybe<T, R> extends Maybe<R> {
    final SingleSource<? extends T> a;
    final Function<? super T, ? extends MaybeSource<? extends R>> b;

    static final class FlatMapMaybeObserver<R> implements MaybeObserver<R> {
        final AtomicReference<Disposable> a;
        final MaybeObserver<? super R> b;

        FlatMapMaybeObserver(AtomicReference<Disposable> atomicReference, MaybeObserver<? super R> maybeObserver) {
            this.a = atomicReference;
            this.b = maybeObserver;
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

        public void onComplete() {
            this.b.onComplete();
        }
    }

    static final class FlatMapSingleObserver<T, R> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = -5843758257109742742L;
        final MaybeObserver<? super R> a;
        final Function<? super T, ? extends MaybeSource<? extends R>> b;

        FlatMapSingleObserver(MaybeObserver<? super R> maybeObserver, Function<? super T, ? extends MaybeSource<? extends R>> function) {
            this.a = maybeObserver;
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
                MaybeSource maybeSource = (MaybeSource) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null MaybeSource");
                if (!isDisposed()) {
                    maybeSource.subscribe(new FlatMapMaybeObserver(this, this.a));
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                onError(th);
            }
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }
    }

    public SingleFlatMapMaybe(SingleSource<? extends T> singleSource, Function<? super T, ? extends MaybeSource<? extends R>> function) {
        this.b = function;
        this.a = singleSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        this.a.subscribe(new FlatMapSingleObserver(maybeObserver, this.b));
    }
}
