package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ArrayCompositeDisposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ObservableTakeUntil<T, U> extends AbstractObservableWithUpstream<T, T> {
    final ObservableSource<? extends U> a;

    final class TakeUntil implements Observer<U> {
        private final ArrayCompositeDisposable b;
        private final SerializedObserver<T> c;

        TakeUntil(ArrayCompositeDisposable arrayCompositeDisposable, SerializedObserver<T> serializedObserver) {
            this.b = arrayCompositeDisposable;
            this.c = serializedObserver;
        }

        public void onSubscribe(Disposable disposable) {
            this.b.setResource(1, disposable);
        }

        public void onNext(U u) {
            this.b.dispose();
            this.c.onComplete();
        }

        public void onError(Throwable th) {
            this.b.dispose();
            this.c.onError(th);
        }

        public void onComplete() {
            this.b.dispose();
            this.c.onComplete();
        }
    }

    static final class TakeUntilObserver<T> extends AtomicBoolean implements Observer<T> {
        private static final long serialVersionUID = 3451719290311127173L;
        final Observer<? super T> a;
        final ArrayCompositeDisposable b;
        Disposable c;

        TakeUntilObserver(Observer<? super T> observer, ArrayCompositeDisposable arrayCompositeDisposable) {
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
            this.a.onNext(t);
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

    public ObservableTakeUntil(ObservableSource<T> observableSource, ObservableSource<? extends U> observableSource2) {
        super(observableSource);
        this.a = observableSource2;
    }

    public void subscribeActual(Observer<? super T> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        ArrayCompositeDisposable arrayCompositeDisposable = new ArrayCompositeDisposable(2);
        TakeUntilObserver takeUntilObserver = new TakeUntilObserver(serializedObserver, arrayCompositeDisposable);
        observer.onSubscribe(arrayCompositeDisposable);
        this.a.subscribe(new TakeUntil(arrayCompositeDisposable, serializedObserver));
        this.source.subscribe(takeUntilObserver);
    }
}
