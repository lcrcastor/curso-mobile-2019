package io.reactivex.internal.operators.observable;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableElementAtMaybe<T> extends Maybe<T> implements FuseToObservable<T> {
    final ObservableSource<T> a;
    final long b;

    static final class ElementAtObserver<T> implements Observer<T>, Disposable {
        final MaybeObserver<? super T> a;
        final long b;
        Disposable c;
        long d;
        boolean e;

        ElementAtObserver(MaybeObserver<? super T> maybeObserver, long j) {
            this.a = maybeObserver;
            this.b = j;
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
                long j = this.d;
                if (j == this.b) {
                    this.e = true;
                    this.c.dispose();
                    this.a.onSuccess(t);
                    return;
                }
                this.d = j + 1;
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

    public ObservableElementAtMaybe(ObservableSource<T> observableSource, long j) {
        this.a = observableSource;
        this.b = j;
    }

    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe(new ElementAtObserver(maybeObserver, this.b));
    }

    public Observable<T> fuseToObservable() {
        ObservableElementAt observableElementAt = new ObservableElementAt(this.a, this.b, null, false);
        return RxJavaPlugins.onAssembly((Observable<T>) observableElementAt);
    }
}
