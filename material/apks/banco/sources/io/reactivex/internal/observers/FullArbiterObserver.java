package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.ObserverFullArbiter;

public final class FullArbiterObserver<T> implements Observer<T> {
    final ObserverFullArbiter<T> a;
    Disposable b;

    public FullArbiterObserver(ObserverFullArbiter<T> observerFullArbiter) {
        this.a = observerFullArbiter;
    }

    public void onSubscribe(Disposable disposable) {
        if (DisposableHelper.validate(this.b, disposable)) {
            this.b = disposable;
            this.a.setDisposable(disposable);
        }
    }

    public void onNext(T t) {
        this.a.onNext(t, this.b);
    }

    public void onError(Throwable th) {
        this.a.onError(th, this.b);
    }

    public void onComplete() {
        this.a.onComplete(this.b);
    }
}
