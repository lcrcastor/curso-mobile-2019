package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableScan<T> extends AbstractObservableWithUpstream<T, T> {
    final BiFunction<T, T, T> a;

    static final class ScanObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> a;
        final BiFunction<T, T, T> b;
        Disposable c;
        T d;
        boolean e;

        ScanObserver(Observer<? super T> observer, BiFunction<T, T, T> biFunction) {
            this.a = observer;
            this.b = biFunction;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void dispose() {
            this.c.dispose();
        }

        public boolean isDisposed() {
            return this.c.isDisposed();
        }

        public void onNext(T t) {
            if (!this.e) {
                Observer<? super T> observer = this.a;
                T t2 = this.d;
                if (t2 == null) {
                    this.d = t;
                    observer.onNext(t);
                } else {
                    try {
                        T requireNonNull = ObjectHelper.requireNonNull(this.b.apply(t2, t), "The value returned by the accumulator is null");
                        this.d = requireNonNull;
                        observer.onNext(requireNonNull);
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.c.dispose();
                        onError(th);
                    }
                }
            }
        }

        public void onError(Throwable th) {
            if (this.e) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.e = true;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.e) {
                this.e = true;
                this.a.onComplete();
            }
        }
    }

    public ObservableScan(ObservableSource<T> observableSource, BiFunction<T, T, T> biFunction) {
        super(observableSource);
        this.a = biFunction;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new ScanObserver(observer, this.a));
    }
}
