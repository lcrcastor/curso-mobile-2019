package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public final class CompletableFromObservable<T> extends Completable {
    final ObservableSource<T> a;

    static final class CompletableFromObservableObserver<T> implements Observer<T> {
        final CompletableObserver a;

        public void onNext(T t) {
        }

        CompletableFromObservableObserver(CompletableObserver completableObserver) {
            this.a = completableObserver;
        }

        public void onSubscribe(Disposable disposable) {
            this.a.onSubscribe(disposable);
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void onComplete() {
            this.a.onComplete();
        }
    }

    public CompletableFromObservable(ObservableSource<T> observableSource) {
        this.a = observableSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new CompletableFromObservableObserver(completableObserver));
    }
}
