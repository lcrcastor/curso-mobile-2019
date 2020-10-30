package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableAllSingle<T> extends Single<Boolean> implements FuseToObservable<Boolean> {
    final ObservableSource<T> a;
    final Predicate<? super T> b;

    static final class AllObserver<T> implements Observer<T>, Disposable {
        final SingleObserver<? super Boolean> a;
        final Predicate<? super T> b;
        Disposable c;
        boolean d;

        AllObserver(SingleObserver<? super Boolean> singleObserver, Predicate<? super T> predicate) {
            this.a = singleObserver;
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
                    if (!this.b.test(t)) {
                        this.d = true;
                        this.c.dispose();
                        this.a.onSuccess(Boolean.valueOf(false));
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
                this.a.onSuccess(Boolean.valueOf(true));
            }
        }

        public void dispose() {
            this.c.dispose();
        }

        public boolean isDisposed() {
            return this.c.isDisposed();
        }
    }

    public ObservableAllSingle(ObservableSource<T> observableSource, Predicate<? super T> predicate) {
        this.a = observableSource;
        this.b = predicate;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        this.a.subscribe(new AllObserver(singleObserver, this.b));
    }

    public Observable<Boolean> fuseToObservable() {
        return RxJavaPlugins.onAssembly((Observable<T>) new ObservableAll<T>(this.a, this.b));
    }
}
