package io.reactivex.internal.operators.observable;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableReduceMaybe<T> extends Maybe<T> {
    final ObservableSource<T> a;
    final BiFunction<T, T, T> b;

    static final class ReduceObserver<T> implements Observer<T>, Disposable {
        final MaybeObserver<? super T> a;
        final BiFunction<T, T, T> b;
        boolean c;
        T d;
        Disposable e;

        ReduceObserver(MaybeObserver<? super T> maybeObserver, BiFunction<T, T, T> biFunction) {
            this.a = maybeObserver;
            this.b = biFunction;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.e, disposable)) {
                this.e = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.c) {
                T t2 = this.d;
                if (t2 == null) {
                    this.d = t;
                    return;
                }
                try {
                    this.d = ObjectHelper.requireNonNull(this.b.apply(t2, t), "The reducer returned a null value");
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.e.dispose();
                    onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = true;
            this.d = null;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.c) {
                this.c = true;
                T t = this.d;
                this.d = null;
                if (t != null) {
                    this.a.onSuccess(t);
                } else {
                    this.a.onComplete();
                }
            }
        }

        public void dispose() {
            this.e.dispose();
        }

        public boolean isDisposed() {
            return this.e.isDisposed();
        }
    }

    public ObservableReduceMaybe(ObservableSource<T> observableSource, BiFunction<T, T, T> biFunction) {
        this.a = observableSource;
        this.b = biFunction;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe(new ReduceObserver(maybeObserver, this.b));
    }
}
