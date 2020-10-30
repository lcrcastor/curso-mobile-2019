package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ArrayCompositeDisposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;

public final class ObservableSkipUntil<T, U> extends AbstractObservableWithUpstream<T, T> {
    final ObservableSource<U> a;

    final class SkipUntil implements Observer<U> {
        Disposable a;
        private final ArrayCompositeDisposable c;
        private final SkipUntilObserver<T> d;
        private final SerializedObserver<T> e;

        SkipUntil(ArrayCompositeDisposable arrayCompositeDisposable, SkipUntilObserver<T> skipUntilObserver, SerializedObserver<T> serializedObserver) {
            this.c = arrayCompositeDisposable;
            this.d = skipUntilObserver;
            this.e = serializedObserver;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.a, disposable)) {
                this.a = disposable;
                this.c.setResource(1, disposable);
            }
        }

        public void onNext(U u) {
            this.a.dispose();
            this.d.d = true;
        }

        public void onError(Throwable th) {
            this.c.dispose();
            this.e.onError(th);
        }

        public void onComplete() {
            this.d.d = true;
        }
    }

    static final class SkipUntilObserver<T> implements Observer<T> {
        final Observer<? super T> a;
        final ArrayCompositeDisposable b;
        Disposable c;
        volatile boolean d;
        boolean e;

        SkipUntilObserver(Observer<? super T> observer, ArrayCompositeDisposable arrayCompositeDisposable) {
            this.a = observer;
            this.b = arrayCompositeDisposable;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.c, disposable)) {
                this.c = disposable;
                this.b.setResource(0, disposable);
            }
        }

        public void onNext(T t) {
            if (this.e) {
                this.a.onNext(t);
            } else if (this.d) {
                this.e = true;
                this.a.onNext(t);
            }
        }

        public void onError(Throwable th) {
            this.b.dispose();
            this.a.onError(th);
        }

        public void onComplete() {
            this.b.dispose();
            this.a.onComplete();
        }
    }

    public ObservableSkipUntil(ObservableSource<T> observableSource, ObservableSource<U> observableSource2) {
        super(observableSource);
        this.a = observableSource2;
    }

    public void subscribeActual(Observer<? super T> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        ArrayCompositeDisposable arrayCompositeDisposable = new ArrayCompositeDisposable(2);
        serializedObserver.onSubscribe(arrayCompositeDisposable);
        SkipUntilObserver skipUntilObserver = new SkipUntilObserver(serializedObserver, arrayCompositeDisposable);
        this.a.subscribe(new SkipUntil(arrayCompositeDisposable, skipUntilObserver, serializedObserver));
        this.source.subscribe(skipUntilObserver);
    }
}
