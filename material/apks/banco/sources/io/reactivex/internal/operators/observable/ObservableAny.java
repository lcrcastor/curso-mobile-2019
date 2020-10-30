package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableAny<T> extends AbstractObservableWithUpstream<T, Boolean> {
    final Predicate<? super T> a;

    static final class AnyObserver<T> implements Observer<T>, Disposable {
        final Observer<? super Boolean> a;
        final Predicate<? super T> b;
        Disposable c;
        boolean d;

        AnyObserver(Observer<? super Boolean> observer, Predicate<? super T> predicate) {
            this.a = observer;
            this.b = predicate;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.d) {
                try {
                    if (this.b.test(t)) {
                        this.d = true;
                        this.c.dispose();
                        this.a.onNext(Boolean.valueOf(true));
                        this.a.onComplete();
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.c.dispose();
                    onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.d) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.d = true;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.d) {
                this.d = true;
                this.a.onNext(Boolean.valueOf(false));
                this.a.onComplete();
            }
        }

        public void dispose() {
            this.c.dispose();
        }

        public boolean isDisposed() {
            return this.c.isDisposed();
        }
    }

    public ObservableAny(ObservableSource<T> observableSource, Predicate<? super T> predicate) {
        super(observableSource);
        this.a = predicate;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super Boolean> observer) {
        this.source.subscribe(new AnyObserver(observer, this.a));
    }
}
