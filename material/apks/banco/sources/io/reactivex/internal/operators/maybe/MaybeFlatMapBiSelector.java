package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeFlatMapBiSelector<T, U, R> extends AbstractMaybeWithUpstream<T, R> {
    final Function<? super T, ? extends MaybeSource<? extends U>> a;
    final BiFunction<? super T, ? super U, ? extends R> b;

    static final class FlatMapBiMainObserver<T, U, R> implements MaybeObserver<T>, Disposable {
        final Function<? super T, ? extends MaybeSource<? extends U>> a;
        final InnerObserver<T, U, R> b;

        static final class InnerObserver<T, U, R> extends AtomicReference<Disposable> implements MaybeObserver<U> {
            private static final long serialVersionUID = -2897979525538174559L;
            final MaybeObserver<? super R> a;
            final BiFunction<? super T, ? super U, ? extends R> b;
            T c;

            InnerObserver(MaybeObserver<? super R> maybeObserver, BiFunction<? super T, ? super U, ? extends R> biFunction) {
                this.a = maybeObserver;
                this.b = biFunction;
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            public void onSuccess(U u) {
                T t = this.c;
                this.c = null;
                try {
                    this.a.onSuccess(ObjectHelper.requireNonNull(this.b.apply(t, u), "The resultSelector returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.a.onError(th);
                }
            }

            public void onError(Throwable th) {
                this.a.onError(th);
            }

            public void onComplete() {
                this.a.onComplete();
            }
        }

        FlatMapBiMainObserver(MaybeObserver<? super R> maybeObserver, Function<? super T, ? extends MaybeSource<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
            this.b = new InnerObserver<>(maybeObserver, biFunction);
            this.a = function;
        }

        public void dispose() {
            DisposableHelper.dispose(this.b);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) this.b.get());
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this.b, disposable)) {
                this.b.a.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            try {
                MaybeSource maybeSource = (MaybeSource) ObjectHelper.requireNonNull(this.a.apply(t), "The mapper returned a null MaybeSource");
                if (DisposableHelper.replace(this.b, null)) {
                    this.b.c = t;
                    maybeSource.subscribe(this.b);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.b.a.onError(th);
            }
        }

        public void onError(Throwable th) {
            this.b.a.onError(th);
        }

        public void onComplete() {
            this.b.a.onComplete();
        }
    }

    public MaybeFlatMapBiSelector(MaybeSource<T> maybeSource, Function<? super T, ? extends MaybeSource<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        super(maybeSource);
        this.a = function;
        this.b = biFunction;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        this.source.subscribe(new FlatMapBiMainObserver(maybeObserver, this.a, this.b));
    }
}
