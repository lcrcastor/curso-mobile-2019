package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableDelaySubscriptionOther<T, U> extends Observable<T> {
    final ObservableSource<? extends T> a;
    final ObservableSource<U> b;

    final class DelayObserver implements Observer<U> {
        final SequentialDisposable a;
        final Observer<? super T> b;
        boolean c;

        final class OnComplete implements Observer<T> {
            OnComplete() {
            }

            public void onSubscribe(Disposable disposable) {
                DelayObserver.this.a.update(disposable);
            }

            public void onNext(T t) {
                DelayObserver.this.b.onNext(t);
            }

            public void onError(Throwable th) {
                DelayObserver.this.b.onError(th);
            }

            public void onComplete() {
                DelayObserver.this.b.onComplete();
            }
        }

        DelayObserver(SequentialDisposable sequentialDisposable, Observer<? super T> observer) {
            this.a = sequentialDisposable;
            this.b = observer;
        }

        public void onSubscribe(Disposable disposable) {
            this.a.update(disposable);
        }

        public void onNext(U u) {
            onComplete();
        }

        public void onError(Throwable th) {
            if (this.c) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.c = true;
            this.b.onError(th);
        }

        public void onComplete() {
            if (!this.c) {
                this.c = true;
                ObservableDelaySubscriptionOther.this.a.subscribe(new OnComplete());
            }
        }
    }

    public ObservableDelaySubscriptionOther(ObservableSource<? extends T> observableSource, ObservableSource<U> observableSource2) {
        this.a = observableSource;
        this.b = observableSource2;
    }

    public void subscribeActual(Observer<? super T> observer) {
        SequentialDisposable sequentialDisposable = new SequentialDisposable();
        observer.onSubscribe(sequentialDisposable);
        this.b.subscribe(new DelayObserver(sequentialDisposable, observer));
    }
}
