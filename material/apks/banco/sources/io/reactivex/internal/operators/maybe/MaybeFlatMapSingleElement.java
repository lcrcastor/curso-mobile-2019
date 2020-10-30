package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.annotations.Experimental;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

@Experimental
public final class MaybeFlatMapSingleElement<T, R> extends Maybe<R> {
    final MaybeSource<T> a;
    final Function<? super T, ? extends SingleSource<? extends R>> b;

    static final class FlatMapMaybeObserver<T, R> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = 4827726964688405508L;
        final MaybeObserver<? super R> a;
        final Function<? super T, ? extends SingleSource<? extends R>> b;

        FlatMapMaybeObserver(MaybeObserver<? super R> maybeObserver, Function<? super T, ? extends SingleSource<? extends R>> function) {
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
                ((SingleSource) ObjectHelper.requireNonNull(this.b.apply(t), "The mapper returned a null SingleSource")).subscribe(new FlatMapSingleObserver(this, this.a));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                onError(th);
            }
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void onComplete() {
            this.a.onComplete();
        }
    }

    static final class FlatMapSingleObserver<R> implements SingleObserver<R> {
        final AtomicReference<Disposable> a;
        final MaybeObserver<? super R> b;

        FlatMapSingleObserver(AtomicReference<Disposable> atomicReference, MaybeObserver<? super R> maybeObserver) {
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
    }

    public MaybeFlatMapSingleElement(MaybeSource<T> maybeSource, Function<? super T, ? extends SingleSource<? extends R>> function) {
        this.a = maybeSource;
        this.b = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        this.a.subscribe(new FlatMapMaybeObserver(maybeObserver, this.b));
    }
}
