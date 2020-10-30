package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.NoSuchElementException;

public final class ObservableLastSingle<T> extends Single<T> {
    final ObservableSource<T> a;
    final T b;

    static final class LastObserver<T> implements Observer<T>, Disposable {
        final SingleObserver<? super T> a;
        final T b;
        Disposable c;
        T d;

        LastObserver(SingleObserver<? super T> singleObserver, T t) {
            this.a = singleObserver;
            this.b = t;
        }

        public void dispose() {
            this.c.dispose();
            this.c = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.c == DisposableHelper.DISPOSED;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.d = t;
        }

        public void onError(Throwable th) {
            this.c = DisposableHelper.DISPOSED;
            this.d = null;
            this.a.onError(th);
        }

        public void onComplete() {
            this.c = DisposableHelper.DISPOSED;
            T t = this.d;
            if (t != null) {
                this.d = null;
                this.a.onSuccess(t);
                return;
            }
            T t2 = this.b;
            if (t2 != null) {
                this.a.onSuccess(t2);
            } else {
                this.a.onError(new NoSuchElementException());
            }
        }
    }

    public ObservableLastSingle(ObservableSource<T> observableSource, T t) {
        this.a = observableSource;
        this.b = t;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new LastObserver(singleObserver, this.b));
    }
}
