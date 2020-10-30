package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableFlattenIterable<T, R> extends AbstractObservableWithUpstream<T, R> {
    final Function<? super T, ? extends Iterable<? extends R>> a;

    static final class FlattenIterableObserver<T, R> implements Observer<T>, Disposable {
        final Observer<? super R> a;
        final Function<? super T, ? extends Iterable<? extends R>> b;
        Disposable c;

        FlattenIterableObserver(Observer<? super R> observer, Function<? super T, ? extends Iterable<? extends R>> function) {
            this.a = observer;
            this.b = function;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.c != DisposableHelper.DISPOSED) {
                try {
                    Observer<? super R> observer = this.a;
                    for (Object requireNonNull : (Iterable) this.b.apply(t)) {
                        try {
                            try {
                                observer.onNext(ObjectHelper.requireNonNull(requireNonNull, "The iterator returned a null value"));
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                this.c.dispose();
                                onError(th);
                                return;
                            }
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            this.c.dispose();
                            onError(th2);
                            return;
                        }
                    }
                } catch (Throwable th3) {
                    Exceptions.throwIfFatal(th3);
                    this.c.dispose();
                    onError(th3);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.c == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = DisposableHelper.DISPOSED;
            this.a.onError(th);
        }

        public void onComplete() {
            if (this.c != DisposableHelper.DISPOSED) {
                this.c = DisposableHelper.DISPOSED;
                this.a.onComplete();
            }
        }

        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        public void dispose() {
            this.c.dispose();
            this.c = DisposableHelper.DISPOSED;
        }
    }

    public ObservableFlattenIterable(ObservableSource<T> observableSource, Function<? super T, ? extends Iterable<? extends R>> function) {
        super(observableSource);
        this.a = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new FlattenIterableObserver(observer, this.a));
    }
}
