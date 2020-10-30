package io.reactivex.internal.operators.completable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public final class CompletableToObservable<T> extends Observable<T> {
    final CompletableSource a;

    static final class ObserverCompletableObserver implements CompletableObserver {
        private final Observer<?> a;

        ObserverCompletableObserver(Observer<?> observer) {
            this.a = observer;
        }

        public void onComplete() {
            this.a.onComplete();
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void onSubscribe(Disposable disposable) {
            this.a.onSubscribe(disposable);
        }
    }

    public CompletableToObservable(CompletableSource completableSource) {
        this.a = completableSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        this.a.subscribe(new ObserverCompletableObserver(observer));
    }
}
