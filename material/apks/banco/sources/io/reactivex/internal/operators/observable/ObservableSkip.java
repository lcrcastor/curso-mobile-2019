package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public final class ObservableSkip<T> extends AbstractObservableWithUpstream<T, T> {
    final long a;

    static final class SkipObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> a;
        long b;
        Disposable c;

        SkipObserver(Observer<? super T> observer, long j) {
            this.a = observer;
            this.b = j;
        }

        public void onSubscribe(Disposable disposable) {
            this.c = disposable;
            this.a.onSubscribe(this);
        }

        public void onNext(T t) {
            if (this.b != 0) {
                this.b--;
            } else {
                this.a.onNext(t);
            }
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void onComplete() {
            this.a.onComplete();
        }

        public void dispose() {
            this.c.dispose();
        }

        public boolean isDisposed() {
            return this.c.isDisposed();
        }
    }

    public ObservableSkip(ObservableSource<T> observableSource, long j) {
        super(observableSource);
        this.a = j;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new SkipObserver(observer, this.a));
    }
}
